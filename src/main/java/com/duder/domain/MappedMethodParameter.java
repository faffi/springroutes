package com.duder.domain;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/20/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * public void exportHTML(@PathVariable String templateId, HttpServletResponse res)
 */
public class MappedMethodParameter
{
	String name;        //variable name
	String type;        //variable type
	List<String> modifiers = new ArrayList<String>();

	public MappedMethodParameter(String name, String type, List<String> modifiers)
	{
		this.name = name;
		this.type = type;
		this.modifiers = modifiers;
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public List<String> getModifiers()
	{
		return modifiers;
	}

	@Override
	public String toString()
	{
		return "MappedMethodParameter{" +
				"name='" + name + '\'' +
				", type='" + type + '\'' +
				", modifiers=" + modifiers +
				'}';
	}
}
