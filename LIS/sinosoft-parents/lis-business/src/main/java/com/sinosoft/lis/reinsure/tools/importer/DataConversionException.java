

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DataConversionException.java

package com.sinosoft.lis.reinsure.tools.importer;

// Referenced classes of package org.jdom:
//            JDOMException

public class DataConversionException extends JDOMException {

	public DataConversionException(String name, String dataType) {
		/* 83 */super("The XML construct " + name
				+ " could not be converted to a " + dataType);
	}
}
