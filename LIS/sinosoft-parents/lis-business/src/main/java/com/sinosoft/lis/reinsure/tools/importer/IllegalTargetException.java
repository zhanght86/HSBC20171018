

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   IllegalTargetException.java

package com.sinosoft.lis.reinsure.tools.importer;

public class IllegalTargetException extends IllegalArgumentException {

	public IllegalTargetException(String target) {
		/* 99 */super("The name \"" + target
				+ "\" is not legal for JDOM/XML Processing Instructions.");
	}

	public IllegalTargetException(String target, String reason) {
		/* 80 */super("The target \"" + target
				+ "\" is not legal for JDOM/XML Processing Instrucitons: "
				+ reason + ".");
	}
}
