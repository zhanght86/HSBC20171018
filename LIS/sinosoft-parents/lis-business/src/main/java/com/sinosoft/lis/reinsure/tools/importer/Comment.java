

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Comment.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;

// Referenced classes of package org.jdom:
//            IllegalDataException, Verifier

public class Comment implements Serializable, Cloneable {

	protected String text;

	public Comment(String text) {
		/* 92 */setText(text);
	}

	public final Object clone() {
		/* 194 */Comment comment = new Comment(text);
		/* 195 */return comment;
	}

	public final boolean equals(Object ob) {
		/* 172 */return ob == this;
	}

	public final String getSerializedForm() {
		/* 154 */return "<!--" + text + "-->";
	}

	public String getText() {
		/* 104 */return text;
	}

	public final int hashCode() {
		/* 183 */return super.hashCode();
	}

	public Comment setText(String text) {
		String reason;
		/* 117 */if ((reason = Verifier.checkCommentData(text)) != null) {
			/* 118 */throw new IllegalDataException(text, "comment", reason);
		} else {
			/* 121 */this.text = text;
			/* 122 */return this;
		}
	}

	public final String toString() {
		/* 137 */return "[Comment: " + getSerializedForm() + "]";
	}
}
