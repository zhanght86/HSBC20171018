

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   JDOMException.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.PrintStream;
import java.io.PrintWriter;

public class JDOMException extends Exception {

	protected Throwable rootCause;

	public JDOMException() {
		/* 86 */super("Error occurred in JDOM application.");
	}

	public JDOMException(String message) {
		/* 98 */super(message);
	}

	public JDOMException(String message, Throwable rootCause) {
		/* 114 */super(message);
		/* 115 */this.rootCause = rootCause;
	}

	public String getMessage() {
		/* 128 */if (rootCause != null) {
			/* 129 */return super.getMessage() + ": " + rootCause.getMessage();
		} else {
			/* 131 */return super.getMessage();
		}
	}

	public Throwable getRootCause() {
		/* 189 */return rootCause;
	}

	public void printStackTrace() {
		/* 143 */super.printStackTrace();
		/* 144 */if (rootCause != null) {
			/* 145 */System.err.print("Root cause: ");
			/* 146 */rootCause.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream s) {
		/* 158 */super.printStackTrace(s);
		/* 159 */if (rootCause != null) {
			/* 160 */s.print("Root cause: ");
			/* 161 */rootCause.printStackTrace(s);
		}
	}

	public void printStackTrace(PrintWriter w) {
		/* 173 */super.printStackTrace(w);
		/* 174 */if (rootCause != null) {
			/* 175 */w.print("Root cause: ");
			/* 176 */rootCause.printStackTrace(w);
		}
	}
}
