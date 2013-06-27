package com.duder.helpers;

import com.google.common.base.Predicate;
import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Created with IntelliJ IDEA.
 * User: faffi
 * Date: 6/20/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASTNodeTypePredicate implements Predicate<ASTNode>
{
	private int nodeType = -1;

	public ASTNodeTypePredicate(final int nodeType)
	{
		this.nodeType = nodeType;
	}

	@Override
	public boolean apply(ASTNode astNode)
	{
		return astNode.getNodeType() == nodeType;
	}

	public static ASTNodeTypePredicate create(final int nodeType)
	{
		return new ASTNodeTypePredicate(nodeType);
	}
}
