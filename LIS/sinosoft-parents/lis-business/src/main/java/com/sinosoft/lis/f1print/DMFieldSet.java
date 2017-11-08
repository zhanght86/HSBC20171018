package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.Hashtable;
import java.util.List;

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

public class DMFieldSet {
private static Logger logger = Logger.getLogger(DMFieldSet.class);

	public static final int FST_ONE_ROW = 0;
	public static final int FST_MUL_ROW = 1;

	private String _szName = "";
	private String _szMemo = "";
	private int _nType = -1;
	private DMDataSource _dmDataSource = null;
	private Hashtable _hashDSSet = null;
	private Element _element = null;

	/**
	 * You can create a fieldset object by calling fromNode function
	 */
	private DMFieldSet() {
	}

	/**
	 * Create a fieldset object from a node object
	 * 
	 * @param node
	 * @param dmDS
	 * @param hashDSSet
	 * @throws Exception
	 */
	public DMFieldSet(Element element, DMDataSource dmDS, Hashtable hashDSSet)
			throws Exception {
		setMemo(element.getAttributeValue("memo"));
		setName(element.getAttributeValue("name"));
		setType(Integer.parseInt(element.getAttributeValue("type")));

		// String szDSID = XPathAPI.eval(node, "DataSourceRef/ID").toString();
		String szDSID = element.getChild("DataSourceRef").getChild("ID")
				.getText();

		List list = element.getChild("DataSourceRef").getChildren("Param");

		String szParams[] = new String[list.size()];

		// Parse all params for datasource
		for (int nIndex = 0; nIndex < list.size(); nIndex++) {
			Element eleParam = (Element) list.get(nIndex);

			String szParamType = eleParam.getAttributeValue("type");
			String szData = eleParam.getAttributeValue("dataField");

			if (szParamType.equals("0")) {
				szData = dmDS.getFieldValue(szData);
			}

			szParams[nIndex] = szData;
		}

		_dmDataSource = ((DMDataSource) hashDSSet.get(szDSID)).Clone();
		_dmDataSource.setParams(szParams);
		_dmDataSource.fetch();

		_element = element;
		_hashDSSet = hashDSSet;
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

	private void parseOneRow(Element element) throws Exception {
		// Select all sub nodes of this field set
		List list = _element.getChildren();

		for (int nIndex = 0; nIndex < list.size(); nIndex++) {
			String szName = ((Element) list.get(nIndex)).getName();

			if (szName.equals("Field")) {

				DMField dmField = new DMField((Element) list.get(nIndex),
						_dmDataSource);
				// logger.debug("Field : " + dmField.getName());
				dmField.output(element);

			} else if (szName.equals("FieldSet")) {

				DMFieldSet dmFieldSet = new DMFieldSet((Element) list
						.get(nIndex), _dmDataSource, _hashDSSet);
				// logger.debug("FieldSet : " + dmFieldSet.getName());
				dmFieldSet.output(element);

			} else if (szName.equals("DataSourceRef")) {
				continue;
			}
		}
	}

	public void output(Element element) throws Exception {
		switch (_nType) {
		case FST_ONE_ROW:
			_dmDataSource.next();
			parseOneRow(element);
			break;

		case FST_MUL_ROW:

			if (null == _szName || _szName.equals("")) {
				throw new Exception(
						"Fieldset's name attribute can't be blank with type='1'");
			}

			Element eleTable = element.getChild(_szName);

			if (eleTable == null) {
				eleTable = new Element(_szName);
				element.addContent(eleTable);
			}

			while (_dmDataSource.next()) {
				Element eleRow = new Element("ROW");
				eleTable.addContent(eleRow);
				parseOneRow(eleRow);
			}
			break;

		default:
			throw new Exception("Unsupported fieldset type");
		}
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
}
