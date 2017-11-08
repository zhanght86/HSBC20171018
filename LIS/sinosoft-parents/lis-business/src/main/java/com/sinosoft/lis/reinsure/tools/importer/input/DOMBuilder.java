

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   DOMBuilder.java

package com.sinosoft.lis.reinsure.tools.importer.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.adapters.DOMAdapter;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.reinsure.tools.importer.CDATA;
import com.sinosoft.lis.reinsure.tools.importer.Comment;
import com.sinosoft.lis.reinsure.tools.importer.DocType;
import com.sinosoft.lis.reinsure.tools.importer.Document;
import com.sinosoft.lis.reinsure.tools.importer.Element;
import com.sinosoft.lis.reinsure.tools.importer.Entity;
import com.sinosoft.lis.reinsure.tools.importer.JDOMException;
import com.sinosoft.lis.reinsure.tools.importer.Namespace;
import com.sinosoft.lis.reinsure.tools.importer.ProcessingInstruction;

public class DOMBuilder {

	private static final String DEFAULT_ADAPTER_CLASS = "org.jdom.adapters.XercesDOMAdapter";
	private boolean validate;
	private String adapterClass;
	private Map prefixedNamespaces;

	public DOMBuilder() {
		/* 162 */this("org.jdom.adapters.XercesDOMAdapter", false);
	}

	public DOMBuilder(String adapterClass) {
		/* 140 */this(adapterClass, false);
	}

	public DOMBuilder(String adapterClass, boolean validate) {
		/* 124 */this.adapterClass = adapterClass;
		/* 125 */this.validate = validate;
		/* 126 */prefixedNamespaces = new HashMap();
		/* 127 */prefixedNamespaces.put("xml", Namespace.XML_NAMESPACE);
	}

	public DOMBuilder(boolean validate) {
		/* 152 */this("org.jdom.adapters.XercesDOMAdapter", validate);
	}

	public Document build(File file) throws JDOMException {
		/* 214 */try {
			/* 214 */FileInputStream in = new FileInputStream(file);
			/* 216 */return build(((InputStream) (in)));
		}
		/* 217 */catch (FileNotFoundException e) {
			/* 218 */throw new JDOMException(e.getMessage(), e);
		}
	}

	public Document build(InputStream in) throws JDOMException {
		/* 180 */Document doc = new Document(null);
		/* 182 */try {
			/* 182 */DOMAdapter adapter = (DOMAdapter) Class.forName(
					adapterClass).newInstance();
			/* 188 */org.w3c.dom.Document domDoc = adapter.getDocument(in,
					validate);
			/* 191 */buildTree(domDoc, doc, null, true);
		}
		/* 193 */catch (Exception e) {
			/* 194 */throw new JDOMException(e.getMessage(), e);
		}
		/* 197 */return doc;
	}

	public Document build(URL url) throws JDOMException {
		/* 236 */try {
			/* 236 */return build(url.openStream());
		}
		/* 238 */catch (IOException e) {
			/* 239 */throw new JDOMException(e.getMessage(), e);
		}
	}

	public Document build(org.w3c.dom.Document domDocument) {
		/* 252 */Document doc = new Document(null);
		/* 254 */buildTree(domDocument, doc, null, true);
		/* 256 */return doc;
	}

	public Element build(org.w3c.dom.Element domElement) {
		/* 268 */Document doc = new Document(null);
		/* 270 */buildTree(domElement, doc, null, true);
		/* 271 */return doc.getRootElement();
	}

	private void buildTree(Node node, Document doc, Element current,
			boolean atRoot) {
		/* 292 */switch (node.getNodeType()) {
		/* 292 */case 2: // '\002'
			/* 292 */
		case 6: // '\006'
			/* 292 */
		default:
			break;

		/* 294 */case 9: // '\t'
		{
			/* 294 */NodeList nodes = node.getChildNodes();
			/* 295 */int i = 0;
			/* 295 */for (int size = nodes.getLength(); i < size; i++) {
				/* 296 */buildTree(nodes.item(i), doc, current, true);
			}

			/* 298 */break;
		}

		/* 302 */case 1: // '\001'
		{
			/* 302 */NamedNodeMap attributeList = node.getAttributes();
			/* 303 */List elementAttributes = new LinkedList();
			/* 304 */int i = 0;
			/* 304 */for (int size = attributeList.getLength(); i < size; i++) {
				/* 305 */Node att = attributeList.item(i);
				/* 308 */if (att.getNodeName().startsWith("xmlns:")) {
					/* 310 */String uri = att.getNodeValue();
					String prefix;
					int colon;
					/* 312 */if ((colon = att.getNodeName().indexOf(":")) != -1) {
						/* 313 */prefix = att.getNodeName()
								.substring(colon + 1);
					} else {
						/* 315 */prefix = "";
					}
					/* 319 */Namespace ns = Namespace.getNamespace(prefix, uri);
					/* 321 */if (!prefix.equals("")) {
						/* 322 */prefixedNamespaces.put(prefix, ns);
					}
				} else {
					/* 327 */elementAttributes.add(att);
				}
			}

			/* 332 */Element element = null;
			/* 333 */String qualifiedName = node.getNodeName();
			int split;
			/* 335 */if ((split = qualifiedName.indexOf(":")) != -1) {
				/* 336 */String prefix = qualifiedName.substring(0, split);
				/* 337 */String localName = qualifiedName.substring(split + 1);
				/* 338 */String uri = ((Namespace) prefixedNamespaces
						.get(prefix)).getURI();
				/* 339 */element = new Element(localName, prefix, uri);
			} else {
				/* 341 */element = new Element(qualifiedName);
			}
			/* 344 */if (atRoot) {
				/* 346 */doc.setRootElement(element);
			} else {
				/* 349 */current.addContent(element);
			}
			/* 353 */i = 0;
			/* 353 */for (int size = elementAttributes.size(); i < size; i++) {
				/* 354 */Node att = (Node) elementAttributes.get(i);
				/* 357 */if (!att.getNodeName().startsWith("xmlns:")) {
					/* 358 */element.addAttribute(att.getNodeName(),
							att.getNodeValue());
				}
			}

			/* 364 */NodeList children = node.getChildNodes();
			/* 365 */i = 0;
			/* 365 */for (int size = children.getLength(); i < size; i++) {
				/* 366 */buildTree(children.item(i), doc, element, false);
			}

			/* 368 */break;
		}

		/* 371 */case 3: // '\003'
		{
			/* 371 */String text = node.getNodeValue();
			/* 372 */current.addContent(text);
			/* 373 */break;
		}

		/* 376 */case 4: // '\004'
		{
			/* 376 */String cdata = node.getNodeValue();
			/* 377 */current.addContent(new CDATA(cdata));
			/* 378 */break;
		}

		/* 382 */case 7: // '\007'
		{
			/* 382 */if (atRoot) {
				/* 383 */doc.addContent(new ProcessingInstruction(node
						.getNodeName(), node.getNodeValue()));
			} else {
				/* 387 */current.addContent(new ProcessingInstruction(node
						.getNodeName(), node.getNodeValue()));
			}
			/* 391 */break;
		}

		/* 394 */case 8: // '\b'
		{
			/* 394 */if (atRoot) {
				/* 395 */doc.addContent(new Comment(node.getNodeValue()));
			} else {
				/* 397 */current.addContent(new Comment(node.getNodeValue()));
			}
			/* 399 */break;
		}

		/* 402 */case 5: // '\005'
		{
			/* 402 */Entity entity = new Entity(node.getNodeName());
			/* 405 */entity.setContent(node.getFirstChild().getNodeValue());
			/* 407 */current.addContent(entity);
			/* 408 */break;
		}

		/* 415 */case 10: // '\n'
		{
			/* 415 */DocumentType domDocType = (DocumentType) node;
			/* 416 */String publicID = domDocType.getPublicId();
			/* 417 */String systemID = domDocType.getSystemId();
			/* 419 */DocType docType = new DocType(domDocType.getName());
			/* 420 */if (publicID != null && !publicID.equals("")) {
				/* 421 */docType.setPublicID(publicID);
			}
			/* 423 */if (systemID != null && !systemID.equals("")) {
				/* 424 */docType.setSystemID(systemID);
			}
			/* 427 */doc.setDocType(docType);
			break;
		}
		}
	}
}
