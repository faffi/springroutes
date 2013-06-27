package com.duder.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/25/13
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MappedMethodParameterAnnotation
{
	private String annotationName;
	private Map<String,String> annotationValues = new HashMap<String,String>();
	public MappedMethodParameterAnnotation(final String annotationName)
	{
		this.annotationName = annotationName;
	}
	public void addAnnotationValue(final String k, final String v)
	{
		this.annotationValues.put(k,v);
	}
	public Map<String,String> getAnnotationValues()
	{
		return Collections.unmodifiableMap(annotationValues);
	}
}
