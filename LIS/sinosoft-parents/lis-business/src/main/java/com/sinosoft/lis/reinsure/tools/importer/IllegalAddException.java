

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   IllegalAddException.java

package com.sinosoft.lis.reinsure.tools.importer;

// Referenced classes of package org.jdom:
//            Attribute, Element, Document

public class IllegalAddException extends IllegalArgumentException {

	public IllegalAddException(String reason) {
		/* 139 */super(reason);
	}

	public IllegalAddException(Document base, Element added, String reason) {
		/* 123 */super("The element \"" + added.getQualifiedName()
				+ "\" could not be added as the root of the document: "
				+ reason);
	}

	public IllegalAddException(Element base, Attribute added, String reason) {
		/* 82 */super("The attribute \"" + added.getQualifiedName()
				+ "\" could not be added to the element \""
				+ base.getQualifiedName() + "\": " + reason);
	}

	public IllegalAddException(Element base, Element added, String reason) {
		/* 104 */super("The element \"" + added.getQualifiedName()
				+ "\" could not be added as a child of \""
				+ base.getQualifiedName() + "\": " + reason);
	}
}
