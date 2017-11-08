

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   CDATA.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;

public class CDATA implements Serializable, Cloneable {

	protected String text;

	public CDATA(String text) {
		/* 93 */this.text = text;
	}

	public final Object clone() {
		/* 189 */CDATA clone = new CDATA(text);
		/* 190 */return clone;
	}

	public final boolean equals(Object ob) {
		/* 167 */return ob == this;
	}

	public final String getSerializedForm() {
		/* 149 */return "<![CDATA[" + text + "]]>";
	}

	public String getText() {
		/* 105 */return text;
	}

	public final int hashCode() {
		/* 178 */return super.hashCode();
	}

	public void setText(String text) {
		/* 117 */this.text = text;
	}

	public final String toString() {
		/* 132 */return "[CDATA: " + getSerializedForm() + "]";
	}
}
