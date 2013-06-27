package com.duder;

import com.duder.domain.MappedClass;
import com.duder.domain.MappedMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/20/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpringRouter
{
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			printe("At least one directory argument required");
			System.exit(1);
		}
		final List<MappedClass> mappedClassList = new ArrayList<MappedClass>();
		for (final String s : args)
		{
			final File sourceDir = new File(s);
			if (!sourceDir.exists() || !sourceDir.isDirectory())
			{
				printe(sourceDir + " doesn't exist or is not a valid directory.");
			}

			print("Scanning Directory: " + s);
			final Collection<File> files = FileUtils.listFiles(sourceDir, new RegexFileFilter("^(.*)java$"), DirectoryFileFilter.DIRECTORY);
			for (final File f : files)
			{
				try
				{
					final String sauce = readFileToString(f);
					final MappedClass mc = ClassParser.parse(sauce);
					if (null != mc)
						mappedClassList.add(mc);
				}
				catch (Exception e)
				{
					printe("Failed to read file: " + f.getName());
				}
			}
			for (final MappedClass mc : mappedClassList)
			{
				printMappedClass(mc);
			}
		}
	}
	private static void printMappedClass(final MappedClass mc)
	{
		System.out.println(String.format("Class: %s.%s",mc.getClassPkg(), mc.getName()));
		for (final MappedMethod mm : mc.getMethods())
		{
			//No controller mapping means the method RequestMapping will suffice
			if (mc.getControllerRequestMapping().size() == 0)
			{
				System.out.printf("\t%-16s %-16s\t\t\t%-128s\n", mm.getHttpMethodRestriction(), mm.getRequestMappingURL(), mm.getSignature());
			}
			else
			{
				for (final String controllerMapping : mc.getControllerRequestMapping())
				{
					System.out.printf("\t%-16s %-32s\t\t\t%-128s\n", mm.getHttpMethodRestriction(), controllerMapping + mm.getRequestMappingURL(), mm.getSignature());
				}
			}
		}
		System.out.println();
	}
	private static String readFileToString(final File f) throws Exception
	{
		final StringBuilder fileData = new StringBuilder(1000);
		final BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));

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

	private static void print(String e)
	{
		System.out.println(String.format("[+] %s", e));
	}
	private static void printe(String e)
	{
		System.err.println(String.format("[-] %s", e));
	}
}
