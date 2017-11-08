

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Namespace.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.jdom:
//            Attribute, Element, IllegalNameException, Verifier

public final class Namespace {

	private static HashMap namespaces;
	private static HashMap mappings;
	public static final Namespace NO_NAMESPACE;
	public static final Namespace XML_NAMESPACE;
	private String prefix;
	private String uri;

	private Namespace(String prefix, String uri) {
		/* 257 */this.prefix = prefix;
		/* 258 */this.uri = uri;
	}

	public boolean equals(Object ob) {
		/* 295 */if (ob == null) {
			/* 296 */return false;
		}
		/* 299 */if (ob instanceof Namespace) {
			/* 300 */Namespace ns = (Namespace) ob;
			/* 302 */if (ns.getURI().equals(uri)
					&& ns.getPrefix().equals(prefix)) {
				/* 303 */return true;
			}
		}
		/* 306 */return false;
	}

	public static Namespace getNamespace(String uri) {
		/* 205 */return getNamespace("", uri);
	}

	public static Namespace getNamespace(String prefix, String uri) {
		/* 150 */if (prefix == null || prefix.trim().equals("")) {
			/* 151 */prefix = "";
		}
		/* 153 */if (uri == null || uri.trim().equals("")) {
			/* 154 */uri = "";
		}
		/* 158 */if (prefix.equals("xml")) {
			/* 159 */return XML_NAMESPACE;
		}
		/* 163 */if (namespaces.containsKey(uri)) {
			/* 164 */return (Namespace) namespaces.get(uri);
		}
		String reason;
		/* 169 */if ((reason = Verifier.checkNamespacePrefix(prefix)) != null) {
			/* 170 */throw new IllegalNameException(prefix, "Namespace prefix",
					reason);
		}
		/* 172 */if ((reason = Verifier.checkNamespaceURI(uri)) != null) {
			/* 173 */throw new IllegalNameException(uri, "Namespace URI",
					reason);
		}
		/* 177 */if (!prefix.equals("") && uri.equals("")) {
			/* 178 */throw new IllegalNameException("", "namespace",
					"Namespace URIs must be non-null and non-empty Strings.");
		}
		/* 183 */if (namespaces.containsKey(uri)) {
			/* 184 */return (Namespace) namespaces.get(prefix + "&" + uri);
		} else {
			/* 188 */mappings.put(prefix, uri);
			/* 189 */Namespace ns = new Namespace(prefix, uri);
			/* 190 */namespaces.put(prefix + "&" + uri, ns);
			/* 191 */return ns;
		}
	}

	public static Namespace getNamespace(String prefix, Element context) {
		/* 222 */if (context == null) {
			/* 223 */return null;
		}
		/* 226 */Namespace ns = context.getNamespace();
		/* 227 */if (ns.getPrefix().equals(prefix)) {
			/* 228 */return ns;
		}
		/* 231 */List attributes = context.getAttributes();
		/* 232 */for (Iterator iterator = attributes.iterator(); iterator
				.hasNext();) {
			/* 234 */Attribute a = (Attribute) iterator.next();
			/* 235 */ns = a.getNamespace();
			/* 236 */if (ns.getPrefix().equals(prefix)) {
				/* 237 */return ns;
			}
		}

		/* 242 */return getNamespace(prefix, context.getParent());
	}

	public String getPrefix() {
		/* 269 */return prefix;
	}

	public String getURI() {
		/* 280 */return uri;
	}

	public int hashCode() {
		/* 334 */return (prefix + uri).hashCode();
	}

	public String toString() {
		/* 318 */return "[Namespace: prefix " + prefix + " is mapped to URI \""
				+ uri + "\"]";
	}

	static {
		/* 97 */NO_NAMESPACE = new Namespace("", "");
		/* 100 */XML_NAMESPACE = new Namespace("xml",
				"http://www.w3.org/XML/1998/namespace");
		/* 117 */namespaces = new HashMap();
		/* 118 */mappings = new HashMap();
		/* 121 */namespaces.put("", NO_NAMESPACE);
		/* 122 */mappings.put("", "");
		/* 123 */mappings.put("xml", "http://www.w3.org/XML/1998/namespace");
		/* 124 */namespaces.put("xml&http://www.w3.org/XML/1998/namespace",
				XML_NAMESPACE);
	}
}
