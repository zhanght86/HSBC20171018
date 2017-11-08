

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Attribute.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Referenced classes of package org.jdom:
//            DataConversionException, IllegalNameException, Namespace, Verifier

public class Attribute implements Serializable, Cloneable {

	protected String name;
	protected transient Namespace namespace;
	protected String value;

	public Attribute(String name, String value) {
		/* 151 */this(name, value, Namespace.NO_NAMESPACE);
	}

	public Attribute(String name, String prefix, String uri, String value) {
		/* 133 */this(name, value, Namespace.getNamespace(prefix, uri));
	}

	public Attribute(String name, String value, Namespace namespace) {
		String reason;
		/* 106 */if ((reason = Verifier.checkAttributeName(name)) != null) {
			/* 107 */throw new IllegalNameException(name, "attribute", reason);
		}
		/* 110 */if (namespace == null) {
			/* 111 */namespace = Namespace.NO_NAMESPACE;
		}
		/* 114 */this.name = name;
		/* 115 */setValue(value);
		/* 116 */this.namespace = namespace;
	}

	public final Object clone() {
		/* 353 */Attribute attribute = new Attribute(name, value, namespace);
		/* 355 */return attribute;
	}

	public final boolean equals(Object ob) {
		/* 331 */return ob == this;
	}

	public boolean getBooleanValue() throws DataConversionException {
		/* 452 */if (value.equalsIgnoreCase("true")
				|| value.equalsIgnoreCase("on")
				|| value.equalsIgnoreCase("yes")) {
			/* 455 */return true;
		}
		/* 456 */if (value.equalsIgnoreCase("false")
				|| value.equalsIgnoreCase("off")
				|| value.equalsIgnoreCase("no")) {
			/* 459 */return false;
		} else {
			/* 461 */throw new DataConversionException(name, "boolean");
		}
	}

	public double getDoubleValue() throws DataConversionException {
		/* 432 */try {
			/* 432 */return Double.valueOf(value).doubleValue();
		}
		/* 435 */catch (NumberFormatException _ex) {
			/* 436 */throw new DataConversionException(name, "double");
		}
	}

	public float getFloatValue() throws DataConversionException {
		/* 412 */try {
			/* 412 */return Float.valueOf(value).floatValue();
		}
		/* 415 */catch (NumberFormatException _ex) {
			/* 416 */throw new DataConversionException(name, "float");
		}
	}

	public int getIntValue() throws DataConversionException {
		/* 374 */try {
			/* 374 */return Integer.parseInt(value);
		}
		/* 376 */catch (NumberFormatException _ex) {
			/* 377 */throw new DataConversionException(name, "int");
		}
	}

	public long getLongValue() throws DataConversionException {
		/* 393 */try {
			/* 393 */return Long.parseLong(value);
		}
		/* 395 */catch (NumberFormatException _ex) {
			/* 396 */throw new DataConversionException(name, "long");
		}
	}

	public String getName() {
		/* 175 */return name;
	}

	public Namespace getNamespace() {
		/* 254 */return namespace;
	}

	public String getNamespacePrefix() {
		/* 229 */return namespace.getPrefix();
	}

	public String getNamespaceURI() {
		/* 242 */return namespace.getURI();
	}

	public String getQualifiedName() {
		/* 200 */StringBuffer qname = new StringBuffer();
		/* 203 */String prefix = namespace.getPrefix();
		/* 204 */if (prefix != null && !prefix.equals("")) {
			/* 205 */qname.append(prefix).append(":");
		}
		/* 209 */qname.append(name);
		/* 211 */return qname.toString();
	}

	public final String getSerializedForm() {
		/* 312 */return getQualifiedName() + "=\"" + value + "\"";
	}

	public String getValue() {
		/* 267 */return value;
	}

	public final int hashCode() {
		/* 342 */return super.hashCode();
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		/* 480 */in.defaultReadObject();
		/* 482 */namespace = Namespace.getNamespace((String) in.readObject(),
				(String) in.readObject());
	}

	public Attribute setValue(String value) {
		/* 279 */this.value = value;
		/* 280 */return this;
	}

	public final String toString() {
		/* 295 */return "[Attribute: " + getSerializedForm() + "]";
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		/* 469 */out.defaultWriteObject();
		/* 473 */out.writeObject(namespace.getPrefix());
		/* 474 */out.writeObject(namespace.getURI());
	}
}
