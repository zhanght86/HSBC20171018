package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

/**
 * <p>
 * Title: DataMine
 * </p>
 * <p>
 * Description: Use template to get data that print job needed
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Kevin
 * @version 1.0
 */

public class DMParser {
private static Logger logger = Logger.getLogger(DMParser.class);

	private Hashtable _hashDSSet = new Hashtable(); // DataSource set
	private Hashtable _hashData = null; // Data for initial dataset
	private Element _eleRoot = null;

	public DMParser() {
	}

	/**
	 * Set template inputstream and initial dataset for parsing
	 * 
	 * @param ins :
	 *            a template file inputstream
	 * @param hashData :
	 *            data for initial dataset
	 * @throws Exception
	 */
	public void setInputer(InputStream ins, Hashtable hashData)
			throws Exception {
		DOMBuilder builder = new DOMBuilder();
		_eleRoot = builder.build(ins).getRootElement();

		_hashData = hashData;
	}

	/**
	 * Parse and append child to element
	 * 
	 * @param element
	 *            the element that we want to add data to
	 * @throws Exception
	 */
	public void addDataTo(Element element) throws Exception {
		List list = _eleRoot.getChild("DataSourceSet")
				.getChildren("DataSource");

		_hashDSSet.clear();

		for (int nIndex = 0; nIndex < list.size(); nIndex++) {
			DMDataSource DMDS = new DMDataSourceImpl((Element) list.get(nIndex));

			_hashDSSet.put(DMDS.getID(), DMDS);
		}

		attachTo(element);
	}

	/**
	 * Private method to do actual attaching job
	 * 
	 * @param element
	 *            the element that we want to add data to
	 * @throws Exception
	 */
	private void attachTo(Element element) throws Exception {
		List list = _eleRoot.getChildren("FieldSet");

		// for each FieldSet, do something ...
		for (int nIndex = 0; nIndex < list.size(); nIndex++) {
			Element tElement = (Element) list.get(nIndex);
			DMDataSourceImpl tDMDataSourceImpl = new DMDataSourceImpl(_hashData);
			DMFieldSet dmFieldSet = new DMFieldSet(tElement, tDMDataSourceImpl,
					_hashDSSet);

			dmFieldSet.output(element);
		}
	}

	public static void main(String args[]) {
		DMParser dmParser = new DMParser();
		Hashtable hashData = new Hashtable();

		try {
			hashData.put("_GRPPOLNO", "86130020040220000002");
			dmParser.setInputer(new FileInputStream(
					"E:\\work\\ui\\f1print\\template\\DM241801.xml"), hashData);

			Element element = new Element("DataSet");
			dmParser.addDataTo(element);
			XMLOutputter output = new XMLOutputter("  ", true, "UTF-8");
			output.output(new Document(element), new FileOutputStream(
					"E:\\work\\ui\\f1print\\template\\print_data11.xml"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
