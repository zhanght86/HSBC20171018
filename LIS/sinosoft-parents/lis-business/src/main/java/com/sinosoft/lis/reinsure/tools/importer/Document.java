

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Document.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Referenced classes of package org.jdom:
//            Comment, DocType, Element, IllegalAddException, 
//            PartialList, ProcessingInstruction

public class Document implements Serializable, Cloneable {

	protected List content;
	protected Element rootElement;
	protected DocType docType;

	public Document(Element rootElement) {
		/* 136 */this(rootElement, null);
	}

	public Document(Element rootElement, DocType docType) {
		/* 115 */this.rootElement = rootElement;
		/* 116 */this.docType = docType;
		/* 117 */content = new LinkedList();
		/* 119 */if (rootElement != null) {
			/* 120 */rootElement.setDocument(this);
			/* 121 */content.add(rootElement);
		}
	}

	/**
	 * @deprecated Method addComment is deprecated
	 */

	public Document addComment(Comment c) {
		/* 558 */return addContent(c);
	}

	public Document addContent(Comment comment) {
		/* 375 */content.add(comment);
		/* 377 */return this;
	}

	public Document addContent(Element element) {
		/* 389 */if (getRootElement() != null) {
			/* 390 */throw new IllegalAddException(this, element,
					"The document already has a root element");
		} else {
			/* 393 */setRootElement(element);
			/* 395 */return this;
		}
	}

	public Document addContent(ProcessingInstruction pi) {
		/* 361 */content.add(pi);
		/* 363 */return this;
	}

	/**
	 * @deprecated Method addProcessingInstruction is deprecated
	 */

	public Document addProcessingInstruction(String target, String data) {
		/* 572 */return addContent(new ProcessingInstruction(target, data));
	}

	/**
	 * @deprecated Method addProcessingInstruction is deprecated
	 */

	public Document addProcessingInstruction(String target, Map data) {
		/* 579 */return addContent(new ProcessingInstruction(target, data));
	}

	/**
	 * @deprecated Method addProcessingInstruction is deprecated
	 */

	public Document addProcessingInstruction(ProcessingInstruction pi) {
		/* 565 */return addContent(pi);
	}

	public final Object clone() {
		/* 529 */Document doc = new Document(null);
		/* 531 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 532 */Object obj = i.next();
			/* 533 */if (obj instanceof Element) {
				/* 534 */Element e = (Element) obj;
				/* 535 */doc.addContent((Element) e.clone());
			} else
			/* 537 */if (obj instanceof Comment) {
				/* 538 */Comment c = (Comment) obj;
				/* 539 */doc.addContent((Comment) c.clone());
			} else
			/* 541 */if (obj instanceof ProcessingInstruction) {
				/* 542 */ProcessingInstruction pi = (ProcessingInstruction) obj;
				/* 543 */doc.addContent((ProcessingInstruction) pi.clone());
			}
		}

		/* 547 */if (docType != null) {
			/* 548 */doc.docType = (DocType) docType.clone();
		}
		/* 551 */return doc;
	}

	public final boolean equals(Object ob) {
		/* 507 */return ob == this;
	}

	public DocType getDocType() {
		/* 192 */return docType;
	}

	public List getMixedContent() {
		/* 408 */return content;
	}

	public ProcessingInstruction getProcessingInstruction(String target) {
		/* 276 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 277 */Object obj = i.next();
			/* 278 */if ((obj instanceof ProcessingInstruction)
					&& ((ProcessingInstruction) obj).getTarget().equals(target)) {
				/* 280 */return (ProcessingInstruction) obj;
			}
		}

		/* 286 */return null;
	}

	public List getProcessingInstructions() {
		/* 222 */PartialList pis = new PartialList(content);
		/* 224 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 225 */Object obj = i.next();
			/* 226 */if (obj instanceof ProcessingInstruction) {
				/* 227 */pis.addPartial(obj);
			}
		}

		/* 231 */return pis;
	}

	public List getProcessingInstructions(String target) {
		/* 248 */PartialList pis = new PartialList(content);
		/* 250 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 251 */Object obj = i.next();
			/* 252 */if ((obj instanceof ProcessingInstruction)
					&& ((ProcessingInstruction) obj).getTarget().equals(target)) {
				/* 254 */pis.addPartial(obj);
			}
		}

		/* 259 */return pis;
	}

	public Element getRootElement() {
		/* 150 */return rootElement;
	}

	public final String getSerializedForm() {
		/* 492 */throw new RuntimeException(
				"Document.getSerializedForm() is not yet implemented");
	}

	public final int hashCode() {
		/* 518 */return super.hashCode();
	}

	public boolean removeProcessingInstruction(String target) {
		/* 298 */ProcessingInstruction pi = getProcessingInstruction(target);
		/* 299 */if (pi == null) {
			/* 300 */return false;
		} else {
			/* 303 */return content.remove(pi);
		}
	}

	/**
	 * @deprecated Method removeProcessingInstruction is deprecated
	 */

	public boolean removeProcessingInstruction(ProcessingInstruction pi) {
		/* 586 */return getMixedContent().remove(pi);
	}

	public boolean removeProcessingInstructions(String target) {
		/* 316 */boolean deletedSome = false;
		/* 318 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 319 */Object obj = i.next();
			/* 320 */if ((obj instanceof ProcessingInstruction)
					&& ((ProcessingInstruction) obj).getTarget().equals(target)) {
				/* 322 */deletedSome = true;
				/* 323 */i.remove();
			}
		}

		/* 328 */return deletedSome;
	}

	public Document setDocType(DocType docType) {
		/* 204 */this.docType = docType;
		/* 206 */return this;
	}

	public Document setMixedContent(List content) {
		/* 424 */content.clear();
		/* 425 */rootElement = null;
		/* 427 */for (Iterator i = content.iterator(); i.hasNext();) {
			/* 428 */Object obj = i.next();
			/* 429 */if (obj instanceof Element) {
				/* 430 */addContent((Element) obj);
			} else
			/* 432 */if (obj instanceof Comment) {
				/* 433 */addContent((Comment) obj);
			} else
			/* 435 */if (obj instanceof ProcessingInstruction) {
				/* 436 */addContent((ProcessingInstruction) obj);
			} else {
				/* 439 */throw new IllegalAddException(
						"A Document may contain only objects of type Element, Comment, and ProcessingInstruction");
			}
		}

		/* 445 */return this;
	}

	public Document setProcessingInstructions(List pis) {
		/* 342 */List current = getProcessingInstructions();
		/* 343 */for (Iterator i = current.iterator(); i.hasNext(); i.remove()) {
		}
		/* 347 */content.addAll(pis);
		/* 349 */return this;
	}

	public Document setRootElement(Element rootElement) {
		/* 165 */int rootLocation = content.size();
		/* 166 */if (this.rootElement != null) {
			/* 167 */this.rootElement.setDocument(null);
			/* 168 */rootLocation = content.indexOf(this.rootElement);
			/* 169 */content.remove(rootLocation);
		}
		/* 172 */if (rootElement != null) {
			/* 173 */rootElement.setDocument(this);
			/* 174 */content.add(rootLocation, rootElement);
		}
		/* 177 */this.rootElement = rootElement;
		/* 179 */return this;
	}

	public final String toString() {
		/* 460 */StringBuffer stringForm = (new StringBuffer())
				.append("[Document: ");
		/* 463 */if (docType != null) {
			/* 464 */stringForm.append(docType.toString()).append(" ");
		} else {
			/* 467 */stringForm.append(" No DOCTYPE declaration. ");
		}
		/* 470 */if (rootElement != null) {
			/* 471 */stringForm.append("Root - ")
					.append(rootElement.toString());
		} else {
			/* 474 */stringForm.append(" No Root Element.");
		}
		/* 477 */stringForm.append("]");
		/* 479 */return stringForm.toString();
	}
}
