

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   IllegalDataException.java

package com.sinosoft.lis.reinsure.tools.importer;

public class IllegalDataException extends IllegalArgumentException {

	public IllegalDataException(String data, String construct) {
		/* 103 */super("The data \"" + data + "\" is not legal for a JDOM "
				+ construct + ".");
	}

	public IllegalDataException(String data, String construct, String reason) {
		/* 81 */super("The data \"" + data + "\" is not legal for a JDOM "
				+ construct + ": " + reason + ".");
	}
}
