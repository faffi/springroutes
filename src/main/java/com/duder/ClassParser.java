package com.duder;

import com.duder.domain.MappedClass;
import com.duder.domain.MappedMethod;
import com.duder.domain.MappedMethodAnnotation;
import com.duder.helpers.ASTNodeTypePredicate;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassParser
{

	//use ASTParse to parse string
	public static MappedClass parse(String str)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		//cu.accept(new ScanningASTVisitor());

		final MappedClass mc = new MappedClass();

		List<AbstractTypeDeclaration> types = cu.types();


		//Find the class declaration
		final AbstractTypeDeclaration td = Iterables.find(types,ASTNodeTypePredicate.create(ASTNode.TYPE_DECLARATION),null);
		if (td == null)
		{
			return null;
		}
		mc.setName(td.getName().toString());
		mc.setClassPkg(cu.getPackage().getName().toString());
		final MarkerAnnotation controllerAnnotation = (MarkerAnnotation) Iterables.find(td.modifiers(),new Predicate<ASTNode>()
		{
			@Override
			public boolean apply(ASTNode o)
			{
				return o.getNodeType() == ASTNode.MARKER_ANNOTATION &&
						((MarkerAnnotation) o).getTypeName().toString().equals("Controller");
			}
		}, null);

		//Make sure we want this class, all relevant classes will have the @Controller annotation
		if (controllerAnnotation == null)
		{
			return null;
		}

		final Annotation classReqMappingAnnotation = (Annotation) Iterables.find(td.modifiers(), new Predicate<ASTNode>()
		{
			@Override
			public boolean apply(ASTNode astNode)
			{
				switch (astNode.getNodeType())
				{
					case ASTNode.SINGLE_MEMBER_ANNOTATION:
					{
						//Found @RequestMapping("/asdf")
						return ((SingleMemberAnnotation) astNode).getTypeName().toString().equals("RequestMapping");
					}
					case ASTNode.NORMAL_ANNOTATION:
					{
						final NormalAnnotation na = (NormalAnnotation) astNode;
						if (na.getTypeName().toString().equals("RequestMapping"))
						{
							for (final MemberValuePair p : (List<MemberValuePair>) na.values())
							{
								if (p.getName().toString().equals("value"))    //Found @RequestMapping(value = "/asdf")
									return true;
							}
						}
						break;
					}
				}
				return false;
				//************** CONTROLLERS WILL NOT ALWAYS HAVE A RequestMapping ANNOTATION ********
				/*return (astNode.getNodeType() == ASTNode.SINGLE_MEMBER_ANNOTATION && ((SingleMemberAnnotation) astNode).getTypeName().toString().equals("RequestMapping"))
						||astNode.getNodeType() == ASTNode.NORMAL_ANNOTATION && ((NormalAnnotation) astNode).values();*/
			}
		}, null);

		//We found a class that has the @RequestMapping annotation
		if (classReqMappingAnnotation != null)
		{
			String reqMapAnnot = "";
			//TODO: make sure this doesn't bork if the RequestMapping holds an array
			if (classReqMappingAnnotation.getNodeType() == ASTNode.SINGLE_MEMBER_ANNOTATION)
			{
				reqMapAnnot = ((SingleMemberAnnotation) classReqMappingAnnotation).getValue().toString();
			}
			else if (classReqMappingAnnotation.getNodeType() == ASTNode.NORMAL_ANNOTATION)
			{
				final NormalAnnotation na = (NormalAnnotation) classReqMappingAnnotation;
				final List<MemberValuePair> annotationPairs = na.values();
				final MemberValuePair mvp = Iterables.find(annotationPairs , new Predicate<MemberValuePair>()
				{
					@Override
					public boolean apply(MemberValuePair memberValuePair)
					{
						return memberValuePair.getName().toString().equals("value");
					}
				});
				reqMapAnnot = mvp.getValue().toString();
			}
			mc.setControllerRequestMapping(reqMapAnnot.replace("\"",""));
			//mc.setControllerRequestMapping(classReqMappingAnnotation.getValue().toString().replace("\"", "")); //Returns "/findings" with the quotes
		}

		//Filter all method declarations based off node type and transform to a list of body declarations
		final List<MethodDeclaration> mds = Lists.newArrayList(Iterables.transform(Iterables.filter(td.bodyDeclarations(), ASTNodeTypePredicate.create(ASTNode.METHOD_DECLARATION)), new Function<BodyDeclaration, MethodDeclaration>()
		{
			@Override
			public MethodDeclaration apply(org.eclipse.jdt.core.dom.BodyDeclaration bodyDeclaration)
			{
				return (MethodDeclaration) bodyDeclaration;
			}
		}));

		//Remove it if it doesn't have a RequestMapping modifier
		for (final MethodDeclaration md : mds)
		{

			final MappedMethod mm = new MappedMethod(md.getName().toString());
			final IExtendedModifier requestMapping = (IExtendedModifier) Iterables.find(md.modifiers(), new Predicate<IExtendedModifier>()
			{
				@Override
				public boolean apply(org.eclipse.jdt.core.dom.IExtendedModifier iExtendedModifier)
				{
					//System.out.println(iExtendedModifier.getClass());
					return ((iExtendedModifier instanceof NormalAnnotation) && ((NormalAnnotation) iExtendedModifier).getTypeName().toString().equals("RequestMapping"))
							|| 	((iExtendedModifier instanceof SingleMemberAnnotation) && ((SingleMemberAnnotation)iExtendedModifier).getTypeName().toString().equals("RequestMapping"));
				}
			}, null);
			if (null != requestMapping) //method is a request mapping
			{
				if (requestMapping instanceof NormalAnnotation)
				{
					final NormalAnnotation na = (NormalAnnotation) requestMapping;
					final Map<String,String> map = new HashMap<String, String>()                //turn the requestMapping values into a map
					{{
							for (MemberValuePair s : (List<MemberValuePair>) na.values())
								put(s.getName().toString(), s.getValue().toString());
					}};
					final MappedMethodAnnotation mma = new MappedMethodAnnotation(na.getTypeName().toString(), map);
					mm.setHttpMethodRestriction(map.get("method")); //Add the HTTP method retriction if set
					mm.addAnnotation(mma);  //Node that this is currently the only annotation being added to the list.
				}
				else if (requestMapping instanceof SingleMemberAnnotation)
				{
					//These happen when we have a request mapping without an http restriction added, making it a single value annotation as opposed to the normal annotation above.
					final SingleMemberAnnotation sma = (SingleMemberAnnotation) requestMapping;
					final MappedMethodAnnotation mma = new MappedMethodAnnotation(sma.getTypeName().toString(), new HashMap<String,String>(){{ put("value",sma.getValue().toString()); }});
					mm.addAnnotation(mma);  //Node that this is currently the only annotation being added to the list.
				}
				mc.addMethod(mm);
			}
		}
		return mc;
	}


	//read file content into a string
	public static String readFileToString(String filePath) throws IOException
	{
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1)
		{
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}
}