

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DocType.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;

public class DocType implements Serializable, Cloneable {

	protected String elementName;
	protected String publicID;
	protected String systemID;

	public DocType(String elementName) {
		/* 138 */this(elementName, "", "");
	}

	public DocType(String elementName, String systemID) {
		/* 125 */this(elementName, "", systemID);
	}

	public DocType(String elementName, String publicID, String systemID) {
		/* 107 */this.elementName = elementName;
		/* 108 */this.publicID = publicID;
		/* 109 */this.systemID = systemID;
	}

	public final Object clone() {
		/* 297 */DocType docType = new DocType(elementName, publicID, systemID);
		/* 299 */return docType;
	}

	public final boolean equals(Object ob) {
		/* 275 */return ob == this;
	}

	public String getElementName() {
		/* 150 */return elementName;
	}

	public String getPublicID() {
		/* 163 */return publicID;
	}

	public final String getSerializedForm() {
		/* 238 */boolean hasPublic = false;
		/* 240 */StringBuffer serForm = (new StringBuffer()).append(
				"<!DOCTYPE ").append(elementName);
		/* 244 */if (publicID != null && !publicID.equals("")) {
			/* 245 */serForm.append(" PUBLIC \"").append(publicID).append("\"");
			/* 248 */hasPublic = true;
		}
		/* 251 */if (systemID != null && !systemID.equals("")) {
			/* 252 */if (!hasPublic) {
				/* 253 */serForm.append(" SYSTEM");
			}
			/* 255 */serForm.append(" \"").append(systemID).append("\"");
		}
		/* 259 */serForm.append(">");
		/* 261 */return serForm.toString();
	}

	public String getSystemID() {
		/* 191 */return systemID;
	}

	public final int hashCode() {
		/* 286 */return super.hashCode();
	}

	public DocType setPublicID(String publicID) {
		/* 176 */this.publicID = publicID;
		/* 178 */return this;
	}

	public DocType setSystemID(String systemID) {
		/* 204 */this.systemID = systemID;
		/* 206 */return this;
	}

	public final String toString() {
		/* 221 */return "[DocType: " + getSerializedForm() + "]";
	}
}
