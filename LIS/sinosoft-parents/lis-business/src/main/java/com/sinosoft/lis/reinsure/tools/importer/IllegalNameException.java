

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   IllegalNameException.java

package com.sinosoft.lis.reinsure.tools.importer;

public class IllegalNameException extends IllegalArgumentException {

	public IllegalNameException(String name, String construct) {
		/* 106 */super("The name \"" + name + "\" is not legal for JDOM/XML "
				+ construct + "s.");
	}

	public IllegalNameException(String name, String construct, String reason) {
		/* 83 */super("The name \"" + name + "\" is not legal for JDOM/XML "
				+ construct + "s: " + reason + ".");
	}
}
