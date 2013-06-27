package com.duder.domain;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/20/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @RequestMapping(value = "/export/{templateId}/html", method = RequestMethod.GET)
 *
 */
public class MappedMethodAnnotation
{
	String name;
	Map<String,String> annotationParms = new HashMap<String, String>();

	public MappedMethodAnnotation(String name)
	{
		this.name = name;
	}

	public MappedMethodAnnotation(String annotationName, Map<String,String> annotationParameters)
	{
		this.name = annotationName;
		this.annotationParms.putAll(annotationParameters);
	}

	public String getName()
	{
		return name;
	}

	public Map<String, String> getAnnotationParms()
	{
		return annotationParms;
	}

	@Override
	public String toString()
	{
		return "MappedMethodAnnotation{" +
				"name='" + name + '\'' +
				", annotationParms=" + annotationParms +
				'}';
	}
}
