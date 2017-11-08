package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import org.jdom.Element;

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

public class DMField {
private static Logger logger = Logger.getLogger(DMField.class);

	public static final int FT_DATA = 0;
	public static final int FT_DATA_ORG = 1;

	protected String _szName = "";
	protected String _szMemo = "";
	protected String _szValue = "";
	protected int _nType = -1;

	private DMField() {
	}

	/**
	 * Create a field object from a node object
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public DMField(Element element, DMDataSource dmDS) throws Exception {
		setMemo(element.getAttributeValue("memo"));
		setName(element.getAttributeValue("name"));

		int nType = Integer.parseInt(element.getAttributeValue("type"));
		String szDataField = element.getAttributeValue("dataField");

		switch (nType) {
		case FT_DATA:
			setValue(dmDS.getFieldValue(szDataField));
			break;
		case FT_DATA_ORG:
			setValue(szDataField);
			break;
		default:
			throw new Exception("Unsupport field type");
		}
	}

	public String getMemo() {
		return _szMemo;
	}

	public String getName() {
		return _szName;
	}

	public int getType() {
		return _nType;
	}

	public String getValue() {
		return _szValue;
	}

	public void output(Element element) throws Exception {
		element.addContent(new Element(_szName).addContent(_szValue));
	}

	public void setMemo(String szMemo) {
		_szMemo = szMemo == null ? "" : szMemo;
	}

	public void setName(String szName) {
		_szName = szName == null ? "" : szName;
	}

	public void setType(int nType) {
		_nType = nType;
	}

	public void setValue(String szValue) {
		_szValue = szValue == null ? "" : szValue;
	}
}
