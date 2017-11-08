

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Element.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package org.jdom:
//            Attribute, CDATA, Comment, Entity, 
//            IllegalAddException, IllegalNameException, Namespace, PartialList, 
//            Verifier, Document, ProcessingInstruction

public class Element implements Serializable, Cloneable {

	protected String name;
	protected transient Namespace namespace;
	protected Element parent;
	protected Document document;
	protected List attributes;
	protected List content;

	public Element(String name) {
		/* 169 */this(name, Namespace.NO_NAMESPACE);
	}

	public Element(String name, String uri) {
		/* 186 */this(name, Namespace.getNamespace("", uri));
	}

	public Element(String name, String prefix, String uri) {
		/* 202 */this(name, Namespace.getNamespace(prefix, uri));
	}

	public Element(String name, Namespace namespace) {
		String reason;
		/* 145 */if ((reason = Verifier.checkElementName(name)) != null) {
			/* 146 */throw new IllegalNameException(name, "element", reason);
		}
		/* 149 */this.name = name;
		/* 151 */if (namespace == null) {
			/* 152 */namespace = Namespace.NO_NAMESPACE;
		}
		/* 155 */this.namespace = namespace;
		/* 157 */document = null;
	}

	public Element addAttribute(String name, String value) {
		/* 1205 */return addAttribute(new Attribute(name, value));
	}

	public Element addAttribute(Attribute attribute) {
		/* 1181 */if (getAttribute(attribute.getName(),
				attribute.getNamespace()) != null) {
			/* 1182 */throw new IllegalAddException(this, attribute,
					"Duplicate attributes are not allowed.");
		}
		/* 1186 */if (attributes == null) {
			/* 1187 */attributes = new LinkedList();
		}
		/* 1190 */attributes.add(attribute);
		/* 1191 */return this;
	}

	/**
	 * @deprecated Method addChild is deprecated
	 */

	public Element addChild(String text) {
		/* 1447 */return addContent(text);
	}

	/**
	 * @deprecated Method addChild is deprecated
	 */

	public Element addChild(Comment comment) {
		/* 1475 */return addContent(comment);
	}

	/**
	 * @deprecated Method addChild is deprecated
	 */

	public Element addChild(Element element) {
		/* 1454 */return addContent(element);
	}

	/**
	 * @deprecated Method addChild is deprecated
	 */

	public Element addChild(Entity entity) {
		/* 1468 */return addContent(entity);
	}

	/**
	 * @deprecated Method addChild is deprecated
	 */

	public Element addChild(ProcessingInstruction pi) {
		/* 1461 */return addContent(pi);
	}

	public Element addContent(String text) {
		/* 829 */if (content == null) {
			/* 830 */content = new LinkedList();
		}
		/* 833 */if (content.size() > 0) {
			/* 834 */Object ob = content.get(content.size() - 1);
			/* 835 */if (ob instanceof String) {
				/* 836 */text = (String) ob + text;
				/* 837 */content.remove(ob);
			}
		}
		/* 840 */content.add(text);
		/* 841 */return this;
	}

	public Element addContent(CDATA cdata) {
		/* 915 */if (content == null) {
			/* 916 */content = new LinkedList();
		}
		/* 919 */content.add(cdata);
		/* 920 */return this;
	}

	public Element addContent(Comment comment) {
		/* 932 */if (content == null) {
			/* 933 */content = new LinkedList();
		}
		/* 936 */content.add(comment);
		/* 937 */return this;
	}

	public Element addContent(Element element) {
		/* 853 */if (element.isRootElement()) {
			/* 854 */throw new IllegalAddException(this, element,
					"The element already has an existing parent (the document root)");
		}
		/* 857 */if (element.getParent() != null) {
			/* 858 */throw new IllegalAddException(this, element,
					"The element already has an existing parent \""
							+ element.getParent().getQualifiedName() + "\"");
		}
		/* 863 */if (content == null) {
			/* 864 */content = new LinkedList();
		}
		/* 867 */element.setParent(this);
		/* 868 */content.add(element);
		/* 869 */return this;
	}

	public Element addContent(Entity entity) {
		/* 898 */if (content == null) {
			/* 899 */content = new LinkedList();
		}
		/* 902 */content.add(entity);
		/* 903 */return this;
	}

	public Element addContent(ProcessingInstruction pi) {
		/* 881 */if (content == null) {
			/* 882 */content = new LinkedList();
		}
		/* 885 */content.add(pi);
		/* 886 */return this;
	}

	public final Object clone() {
		/* 1338 */Element element = new Element(name, namespace);
		/* 1340 */if (attributes != null) {
			/* 1341 */List list = new LinkedList();
			/* 1342 */for (Iterator i = attributes.iterator(); i.hasNext(); list
					.add(((Attribute) i.next()).clone())) {
			}
			/* 1345 */element.attributes = list;
		}
		/* 1348 */if (content != null) {
			/* 1349 */for (Iterator i = content.iterator(); i.hasNext();) {
				/* 1350 */Object obj = i.next();
				/* 1351 */if (obj instanceof String) {
					/* 1352 */element.addContent((String) obj);
				} else
				/* 1353 */if (obj instanceof Comment) {
					/* 1354 */element.addContent((Comment) ((Comment) obj)
							.clone());
				} else
				/* 1355 */if (obj instanceof Entity) {
					/* 1356 */element.addContent((Entity) ((Entity) obj)
							.clone());
				} else
				/* 1357 */if (obj instanceof Element) {
					/* 1358 */element.addContent((Element) ((Element) obj)
							.clone());
				} else
				/* 1359 */if (obj instanceof CDATA) {
					/* 1360 */element.addContent((CDATA) ((CDATA) obj).clone());
				}
			}

		}
		/* 1366 */element.setParent(null);
		/* 1368 */return element;
	}

	public final boolean equals(Object ob) {
		/* 1316 */return this == ob;
	}

	public Attribute getAttribute(String name) {
		/* 1090 */return getAttribute(name, Namespace.NO_NAMESPACE);
	}

	public Attribute getAttribute(String name, Namespace ns) {
		/* 1104 */if (attributes == null) {
			/* 1105 */return null;
		}
		/* 1108 */String uri = ns.getURI();
		/* 1109 */for (Iterator i = attributes.iterator(); i.hasNext();) {
			/* 1111 */Attribute att = (Attribute) i.next();
			/* 1112 */if (att.getNamespaceURI().equals(uri)
					&& att.getName().equals(name)) {
				/* 1114 */return att;
			}
		}

		/* 1119 */return null;
	}

	public String getAttributeValue(String name) {
		/* 1133 */return getAttributeValue(name, Namespace.NO_NAMESPACE);
	}

	public String getAttributeValue(String name, Namespace ns) {
		/* 1148 */Attribute attrib = getAttribute(name, ns);
		/* 1149 */if (attrib == null) {
			/* 1150 */return null;
		} else {
			/* 1152 */return attrib.getValue();
		}
	}

	public List getAttributes() {
		/* 1071 */if (attributes == null) {
			/* 1072 */attributes = new LinkedList();
		}
		/* 1075 */PartialList atts = new PartialList(attributes, this);
		/* 1076 */atts.addAllPartial(attributes);
		/* 1077 */return atts;
	}

	public Element getChild(String name) {
		/* 816 */return getChild(name, Namespace.NO_NAMESPACE);
	}

	public Element getChild(String name, Namespace ns) {
		/* 783 */if (content == null) {
			/* 784 */return null;
		}
		/* 787 */String uri = ns.getURI();
		/* 788 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 790 */Object obj = i.next();
			/* 791 */if (obj instanceof Element) {
				/* 792 */Element element = (Element) obj;
				/* 793 */if (element.getNamespaceURI().equals(uri)
						&& element.getName().equals(name)) {
					/* 795 */return element;
				}
			}
		}

		/* 801 */return null;
	}

	public String getChildText(String name) {
		/* 479 */Element child = getChild(name);
		/* 480 */if (child == null) {
			/* 481 */return null;
		} else {
			/* 483 */return child.getText();
		}
	}

	public String getChildText(String name, Namespace ns) {
		/* 517 */Element child = getChild(name, ns);
		/* 518 */if (child == null) {
			/* 519 */return null;
		} else {
			/* 521 */return child.getText();
		}
	}

	public String getChildTextTrim(String name) {
		/* 498 */Element child = getChild(name);
		/* 499 */if (child == null) {
			/* 500 */return null;
		} else {
			/* 502 */return child.getTextTrim();
		}
	}

	public String getChildTextTrim(String name, Namespace ns) {
		/* 538 */Element child = getChild(name, ns);
		/* 539 */if (child == null) {
			/* 540 */return null;
		} else {
			/* 542 */return child.getTextTrim();
		}
	}

	public List getChildren() {
		/* 677 */if (content == null) {
			/* 678 */content = new LinkedList();
		}
		/* 681 */PartialList elements = new PartialList(content, this);
		/* 683 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 685 */Object obj = i.next();
			/* 686 */if (obj instanceof Element) {
				/* 687 */elements.addPartial(obj);
			}
		}

		/* 691 */return elements;
	}

	public List getChildren(String name) {
		/* 727 */return getChildren(name, Namespace.NO_NAMESPACE);
	}

	public List getChildren(String name, Namespace ns) {
		/* 750 */PartialList children = new PartialList(getChildren(), this);
		/* 752 */if (content != null) {
			/* 753 */String uri = ns.getURI();
			/* 754 */for (Iterator i = content.iterator(); i.hasNext();) {
				/* 756 */Object obj = i.next();
				/* 757 */if (obj instanceof Element) {
					/* 758 */Element element = (Element) obj;
					/* 759 */if (element.getNamespaceURI().equals(uri)
							&& element.getName().equals(name)) {
						/* 761 */children.addPartial(element);
					}
				}
			}

		}
		/* 767 */return children;
	}

	/**
	 * @deprecated Method getContent is deprecated
	 */

	public String getContent() {
		/* 1413 */return getTextTrim();
	}

	/**
	 * @deprecated Method getContent is deprecated
	 */

	public String getContent(boolean preserveWhitespace) {
		/* 1401 */if (preserveWhitespace) {
			/* 1402 */return getText();
		} else {
			/* 1405 */return getTextTrim();
		}
	}

	public Element getCopy(String name) {
		/* 232 */return getCopy(name, Namespace.NO_NAMESPACE);
	}

	public Element getCopy(String name, Namespace ns) {
		/* 216 */Element clone = (Element) clone();
		/* 217 */clone.namespace = ns;
		/* 218 */clone.name = name;
		/* 220 */return clone;
	}

	public Document getDocument() {
		/* 383 */if (isRootElement()) {
			/* 384 */return document;
		}
		/* 387 */if (getParent() != null) {
			/* 388 */return getParent().getDocument();
		} else {
			/* 391 */return null;
		}
	}

	public List getMixedContent() {
		/* 621 */if (content == null) {
			/* 622 */content = new LinkedList();
		}
		/* 625 */PartialList result = new PartialList(content, this);
		/* 626 */result.addAllPartial(content);
		/* 627 */return result;
	}

	/**
	 * @deprecated Method getMixedContent is deprecated
	 */

	public List getMixedContent(boolean preserveWhitespace) {
		/* 1427 */PartialList result = new PartialList(content, this);
		/* 1428 */if (preserveWhitespace) {
			/* 1429 */result.addAllPartial(content);
		} else {
			/* 1431 */for (Iterator i = content.iterator(); i.hasNext();) {
				/* 1432 */Object obj = i.next();
				/* 1433 */if (obj instanceof String) {
					/* 1434 */result.addPartial(((String) obj).trim());
				} else {
					/* 1436 */result.addPartial(obj);
				}
			}

		}
		/* 1440 */return result;
	}

	public String getName() {
		/* 245 */return name;
	}

	public Namespace getNamespace() {
		/* 257 */return namespace;
	}

	public String getNamespacePrefix() {
		/* 271 */return namespace.getPrefix();
	}

	public String getNamespaceURI() {
		/* 284 */return namespace.getURI();
	}

	public Element getParent() {
		/* 323 */return parent;
	}

	public String getQualifiedName() {
		/* 300 */if (namespace.getPrefix().equals("")) {
			/* 301 */return getName();
		} else {
			/* 304 */return namespace.getPrefix() + ":" + name;
		}
	}

	public final String getSerializedForm() {
		/* 1302 */throw new RuntimeException(
				"Element.getSerializedForm() is not yet implemented");
	}

	public String getText() {
		/* 408 */if (content == null || content.size() < 1
				|| content.get(0) == null) {
			/* 409 */return "";
		}
		/* 413 */if (content.size() == 1 && (content.get(0) instanceof String)) {
			/* 414 */return (String) content.get(0);
		}
		/* 418 */StringBuffer textContent = new StringBuffer();
		/* 419 */boolean hasText = false;
		/* 421 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 423 */Object obj = i.next();
			/* 424 */if (obj instanceof String) {
				/* 425 */textContent.append((String) obj);
				/* 426 */hasText = true;
			} else
			/* 427 */if (obj instanceof CDATA) {
				/* 428 */textContent.append(((CDATA) obj).getText());
				/* 429 */hasText = true;
			}
		}

		/* 433 */if (!hasText) {
			/* 434 */return "";
		} else {
			/* 436 */return textContent.toString();
		}
	}

	public String getTextTrim() {
		/* 452 */String text = getText();
		/* 454 */StringBuffer textContent = new StringBuffer();
		/* 455 */for (StringTokenizer tokenizer = new StringTokenizer(text); tokenizer
				.hasMoreTokens();) {
			/* 457 */String str = tokenizer.nextToken();
			/* 458 */textContent.append(str);
			/* 459 */if (tokenizer.hasMoreTokens()) {
				/* 460 */textContent.append(" ");
			}
		}

		/* 464 */return textContent.toString();
	}

	public boolean hasMixedContent() {
		/* 581 */if (content == null) {
			/* 582 */return false;
		}
		/* 585 */Class prevClass = null;
		/* 586 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 588 */Object obj = i.next();
			/* 589 */Class newClass = obj.getClass();
			/* 591 */if (newClass != prevClass) {
				/* 592 */if (prevClass != null) {
					/* 593 */return true;
				}
				/* 595 */prevClass = newClass;
			}
		}

		/* 599 */return false;
	}

	public final int hashCode() {
		/* 1327 */return super.hashCode();
	}

	public boolean isRootElement() {
		/* 354 */return document != null;
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		/* 1386 */in.defaultReadObject();
		/* 1388 */namespace = Namespace.getNamespace((String) in.readObject(),
				(String) in.readObject());
	}

	public boolean removeAttribute(String name) {
		/* 1241 */return removeAttribute(name, Namespace.NO_NAMESPACE);
	}

	public boolean removeAttribute(String name, String uri) {
		/* 1219 */for (Iterator i = attributes.iterator(); i.hasNext();) {
			/* 1221 */Attribute att = (Attribute) i.next();
			/* 1222 */if (att.getNamespaceURI().equals(uri)
					&& att.getName().equals(name)) {
				/* 1224 */i.remove();
				/* 1225 */return true;
			}
		}

		/* 1228 */return false;
	}

	public boolean removeAttribute(String name, Namespace ns) {
		/* 1255 */if (attributes == null) {
			/* 1256 */return false;
		}
		/* 1259 */String uri = ns.getURI();
		/* 1260 */for (Iterator i = attributes.iterator(); i.hasNext();) {
			/* 1262 */Attribute att = (Attribute) i.next();
			/* 1263 */if (att.getNamespaceURI().equals(uri)
					&& att.getName().equals(name)) {
				/* 1265 */i.remove();
				/* 1266 */return true;
			}
		}

		/* 1269 */return false;
	}

	public boolean removeChild(String name) {
		/* 951 */return removeChild(name, Namespace.NO_NAMESPACE);
	}

	public boolean removeChild(String name, Namespace ns) {
		/* 966 */if (content == null) {
			/* 967 */return false;
		}
		/* 970 */String uri = ns.getURI();
		/* 971 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 973 */Object obj = i.next();
			/* 974 */if (obj instanceof Element) {
				/* 975 */Element element = (Element) obj;
				/* 976 */if (element.getNamespaceURI().equals(uri)
						&& element.getName().equals(name)) {
					/* 978 */element.setParent(null);
					/* 979 */i.remove();
					/* 980 */return true;
				}
			}
		}

		/* 986 */return false;
	}

	/**
	 * @deprecated Method removeChild is deprecated
	 */

	public boolean removeChild(Comment comment) {
		/* 1503 */return removeContent(comment);
	}

	/**
	 * @deprecated Method removeChild is deprecated
	 */

	public boolean removeChild(Element element) {
		/* 1482 */return removeContent(element);
	}

	/**
	 * @deprecated Method removeChild is deprecated
	 */

	public boolean removeChild(Entity entity) {
		/* 1496 */return removeContent(entity);
	}

	/**
	 * @deprecated Method removeChild is deprecated
	 */

	public boolean removeChild(ProcessingInstruction pi) {
		/* 1489 */return removeContent(pi);
	}

	public boolean removeChildren() {
		/* 1046 */if (content != null) {
			/* 1047 */for (Iterator i = content.iterator(); i.hasNext();) {
				/* 1049 */Object obj = i.next();
				/* 1050 */if (obj instanceof Element) {
					/* 1051 */i.remove();
				}
			}

		}
		/* 1056 */return true;
	}

	public boolean removeChildren(String name) {
		/* 1000 */return removeChildren(name, Namespace.NO_NAMESPACE);
	}

	public boolean removeChildren(String name, Namespace ns) {
		/* 1015 */if (content == null) {
			/* 1016 */return false;
		}
		/* 1019 */String uri = ns.getURI();
		/* 1020 */boolean deletedSome = false;
		/* 1021 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 1023 */Object obj = i.next();
			/* 1024 */if (obj instanceof Element) {
				/* 1025 */Element element = (Element) obj;
				/* 1026 */if (element.getNamespaceURI().equals(uri)
						&& element.getName().equals(name)) {
					/* 1028 */element.setParent(null);
					/* 1029 */i.remove();
					/* 1030 */deletedSome = true;
				}
			}
		}

		/* 1035 */return deletedSome;
	}

	/**
	 * @deprecated Method removeContent is deprecated
	 */

	public boolean removeContent(Comment comment) {
		/* 1572 */if (content == null) {
			/* 1573 */return false;
		} else {
			/* 1576 */return content.remove(comment);
		}
	}

	/**
	 * @deprecated Method removeContent is deprecated
	 */

	public boolean removeContent(Element element) {
		/* 1516 */if (content == null) {
			/* 1517 */return false;
		}
		/* 1520 */if (content.remove(element)) {
			/* 1521 */element.setParent(null);
			/* 1522 */return true;
		} else {
			/* 1524 */return false;
		}
	}

	/**
	 * @deprecated Method removeContent is deprecated
	 */

	public boolean removeContent(Entity entity) {
		/* 1555 */if (content == null) {
			/* 1556 */return false;
		} else {
			/* 1559 */return content.remove(entity);
		}
	}

	/**
	 * @deprecated Method removeContent is deprecated
	 */

	public boolean removeContent(ProcessingInstruction pi) {
		/* 1538 */if (content == null) {
			/* 1539 */return false;
		} else {
			/* 1542 */return content.remove(pi);
		}
	}

	public Element setAttributes(List attributes) {
		/* 1166 */this.attributes = attributes;
		/* 1167 */return this;
	}

	public Element setChildren(List children) {
		/* 705 */return setMixedContent(children);
	}

	/**
	 * @deprecated Method setContent is deprecated
	 */

	public Element setContent(String text) {
		/* 1420 */return setText(text);
	}

	protected Element setDocument(Document document) {
		/* 367 */this.document = document;
		/* 369 */return this;
	}

	public Element setMixedContent(List mixedContent) {
		/* 641 */if (content != null) {
			/* 642 */content.clear();
		} else {
			/* 644 */content = new LinkedList();
		}
		/* 647 */content.addAll(mixedContent);
		/* 648 */return this;
	}

	protected Element setParent(Element parent) {
		/* 335 */this.parent = parent;
		/* 337 */return this;
	}

	public Element setText(String text) {
		/* 558 */if (content != null) {
			/* 559 */content.clear();
		} else {
			/* 561 */content = new LinkedList();
		}
		/* 563 */content.add(text);
		/* 565 */return this;
	}

	public final String toString() {
		/* 1284 */StringBuffer stringForm = (new StringBuffer())
				.append("[Element: <").append(getQualifiedName())
				.append(" />]");
		/* 1289 */return stringForm.toString();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		/* 1375 */out.defaultWriteObject();
		/* 1379 */out.writeObject(namespace.getPrefix());
		/* 1380 */out.writeObject(namespace.getURI());
	}
}
