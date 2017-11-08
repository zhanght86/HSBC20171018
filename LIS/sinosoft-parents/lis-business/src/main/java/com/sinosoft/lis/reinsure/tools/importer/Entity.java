

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Entity.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// Referenced classes of package org.jdom:
//            Comment, Element

public class Entity implements Serializable, Cloneable {

	protected String name;
	protected List content;

	public Entity(String name) {
		/* 94 */this.name = name;
		/* 95 */content = new LinkedList();
	}

	public Entity addChild(String s) {
		/* 335 */content.add(s);
		/* 337 */return this;
	}

	public Entity addChild(Element element) {
		/* 320 */content.add(element);
		/* 322 */return this;
	}

	public Entity addText(String text) {
		/* 305 */content.add(text);
		/* 307 */return this;
	}

	public final Object clone() {
		/* 409 */Entity entity = new Entity(name);
		/* 410 */entity.content = (List) ((LinkedList) content).clone();
		/* 412 */return entity;
	}

	public final boolean equals(Object ob) {
		/* 387 */return ob == this;
	}

	public List getChildren() {
		/* 268 */List childElements = new LinkedList();
		/* 270 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 272 */Object obj = i.next();
			/* 273 */if (obj instanceof Element) {
				/* 274 */childElements.add(obj);
			}
		}

		/* 278 */return childElements;
	}

	public String getContent() {
		/* 122 */StringBuffer textContent = new StringBuffer();
		/* 124 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 126 */Object obj = i.next();
			/* 127 */if (obj instanceof String) {
				/* 128 */textContent.append((String) obj);
			}
		}

		/* 132 */return textContent.toString();
	}

	public List getMixedContent() {
		/* 219 */return content;
	}

	public String getName() {
		/* 107 */return name;
	}

	public final String getSerializedForm() {
		/* 369 */return "&" + name + ";";
	}

	public boolean hasMixedContent() {
		/* 171 */boolean hasText = false;
		/* 172 */boolean hasElements = false;
		/* 173 */boolean hasComments = false;
		/* 175 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 177 */Object obj = i.next();
			/* 178 */if (obj instanceof String) {
				/* 179 */if (hasElements || hasComments) {
					/* 181 */return true;
				}
				/* 183 */hasText = true;
			} else
			/* 184 */if (obj instanceof Element) {
				/* 185 */if (hasText || hasComments) {
					/* 187 */return true;
				}
				/* 189 */hasElements = true;
			} else
			/* 190 */if (obj instanceof Comment) {
				/* 191 */if (hasText || hasElements) {
					/* 193 */return true;
				}
				/* 195 */hasComments = true;
			}
		}

		/* 199 */return false;
	}

	public final int hashCode() {
		/* 398 */return super.hashCode();
	}

	public Entity setChildren(List children) {
		/* 292 */return setMixedContent(children);
	}

	public Entity setContent(String textContent) {
		/* 147 */content.clear();
		/* 148 */content.add(textContent);
		/* 150 */return this;
	}

	public Entity setMixedContent(List mixedContent) {
		/* 236 */content = mixedContent;
		/* 238 */return this;
	}

	public final String toString() {
		/* 352 */return "[Entity: " + getSerializedForm() + "]";
	}
}
