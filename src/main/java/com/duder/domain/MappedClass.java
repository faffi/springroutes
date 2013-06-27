package com.duder.domain;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/20/13
 * Time: 9:41 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 *  @Controller
    @RequestMapping("/findings")
    @RequestMapping({"/findings","more_findings"})
    public class FindingsController
 */
public class MappedClass
{
	private String name;
	private String classPkg;

	private List<String> controllerRequestMapping = new ArrayList<String>();

	private List<MappedMethod> methods = new ArrayList<MappedMethod>();

	public MappedClass()
	{
	}

	public MappedClass(String name)
	{
		this.name = name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setClassPkg(String classPkg)
	{
		this.classPkg = classPkg;
	}

	public void setControllerRequestMapping(String controllerRequestMapping)
	{
		if (controllerRequestMapping.startsWith("{"))
		{
			if (controllerRequestMapping.endsWith("}"))
			{
				//Array identified..
				final String mappings[] = controllerRequestMapping.substring(1,controllerRequestMapping.length()-1).split(",");
				for (final String s: mappings)
				{
					this.controllerRequestMapping.add(s.trim().startsWith("/") ? s.trim() : "/" + s.trim());
				}
			}
		}
		else
		{
			this.controllerRequestMapping.add(controllerRequestMapping);
		}
	}

	public void addMethod(final MappedMethod m)
	{
		methods.add(m);
	}

	public String getName()
	{
		return name;
	}

	public String getClassPkg()
	{
		return classPkg;
	}

	public List<String> getControllerRequestMapping()
	{
		return controllerRequestMapping;
	}

	public List<MappedMethod> getMethods()
	{
		return methods;
	}

	@Override
	public String toString()
	{
		return "MappedClass{" +
				"name='" + name + '\'' +
				", classPkg='" + classPkg + '\'' +
				", controllerRequestMapping='" + controllerRequestMapping + '\'' +
				", methods=" + methods +
				'}';
	}
}
