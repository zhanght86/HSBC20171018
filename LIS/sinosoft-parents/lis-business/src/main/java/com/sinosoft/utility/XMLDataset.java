/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Vector;

import org.jdom.Element;

/**
 * 为保单打印所做的XML文件生成类
 * <p>
 * Title: Life Information System
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author kevin
 * @version 1.0
 */
public class XMLDataset {
private static Logger logger = Logger.getLogger(XMLDataset.class);
	private Element _Element;
	private CErrors mErrors = new CErrors();

	public XMLDataset() {
		_Element = new Element("DATASET");
		if (!createControlInfo()) {
			_Element = null;
		}
	}

	public  XMLDataset(String tControlInfo) {
		_Element = new Element("DATASET");
		if (!createNewControlInfo(tControlInfo)) {
			_Element = null;
		}
	}

	/**
	 * 在传入的DATASET元素下面创建我们的元素
	 * 
	 * @param element
	 *            Element
	 */
	protected XMLDataset(Element element) {
		_Element = element;
		if (!createControlInfo()) {
			_Element = null;
		}
	}

	/**
	 * 创建Control标签
	 * 
	 * @return boolean
	 */
	private boolean createControlInfo() {
		if (_Element == null) {
			return false;
		}

		Element elementControl = new Element("CONTROL");

		_Element.addContent(elementControl);

		elementControl.addContent(new Element("CONTTYPE").addContent(""));
		elementControl.addContent(new Element("TEMPLATE").addContent(""));
		elementControl.addContent(new Element("PRINTER").addContent(""));
		elementControl.addContent(new Element("DISPLAY").addContent(""));


		return true;
	}

	/**
	 * 创建Control标签
	 * 
	 * @return boolean
	 */
	private boolean createNewControlInfo(String tControl) {
		if (_Element == null) {
			return false;
		}

		Element elementControl = new Element(tControl);

		_Element.addContent(elementControl);

		//添加几个控制类型
		elementControl.addContent(new Element("RiskType"));
		elementControl.addContent(new Element("FamilyType"));
		elementControl.addContent(new Element("SaleChnl"));

		return true;
	}
	/**
	 * 得到内部元素的克隆
	 * 
	 * @return Element
	 */
	public Element getElement() {
		if (_Element == null) {
			return null;
		}

		return (Element) (_Element.clone());
	}

	/**
	 * 
	 * 得到内部元素的引用
	 * 
	 * @return
	 */
	public Element getRealElement() {
		if (_Element == null) {
			return null;
		}

		return _Element;
	}

	/**
	 * 在XML文件中加入一个显示控制数据
	 * 
	 * @param strName
	 *            String 控制标签的名字
	 * @return XMLDataset
	 */
	public XMLDataset addDisplayControl(String strName) {
		if (_Element == null) {
			return null;
		}

		Element elementControl = _Element.getChild("CONTROL").getChild(
				"DISPLAY");
		elementControl.addContent(new Element(strName).addContent("1"));

		return this;
	}

	/**
	 * 设置这个Dataset所用的险种类型，用以区分银代万能
	 * 
	 * @param strRiskType
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setRiskType(String strRiskType) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("RiskType").addContent(strRiskType);
		return this;
	}

	/**
	 * 设置这个Dataset保单的类型，家庭单，多主险还是普通保单
	 * 
	 * @param strPrinter
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setFamilyType(String strFamilyType) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("FamilyType").addContent(strFamilyType);
		return this;
	}

	/**
	 * 设置这个Dataset所用的销售渠道
	 * 
	 * @param strContType
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setSaleChnl(String strSaleChnl) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("SaleChnl").addContent(strSaleChnl);
		return this;
	}

	
	/**
	 * 设置这个Dataset所用的模板名
	 * 
	 * @param strTemplate
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setTemplate(String strTemplate) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("TEMPLATE").setText(strTemplate);
		return this;
	}

	/**
	 * 设置这个Dataset所用的打印机名
	 * 
	 * @param strPrinter
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setPrinter(String strPrinter) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("PRINTER").setText(strPrinter);
		return this;
	}

	/**
	 * 设置这个Dataset所用的打印机名
	 * 
	 * @param strContType
	 *            String
	 * @return XMLDataset
	 */
	public XMLDataset setContType(String strContType) {
		if (_Element == null) {
			return null;
		}

		_Element.getChild("CONTROL").getChild("CONTTYPE").setText(strContType);
		return this;
	}
	/**
	 * 添加xml对象
	 * 
	 * @param xmlDataObject
	 *            XMLDataObject
	 * @return boolean
	 */
	public boolean addDataObject(XMLDataObject xmlDataObject) {
		// 如果对象的标识为空，让XMLDataset自动产生一个对象标识
		if (xmlDataObject.getDataObjectID().equals("")) {
			xmlDataObject.setDataObjectID("");
		}

		return xmlDataObject.addDataTo(_Element);
	}

	public boolean addSchema(Schema schema) {
		return addSchema(schema, "0");
	}

	/**
	 * 根据传入的Schema数据，往xml里面添加对象
	 * 
	 * @param schema
	 *            Schema
	 * @param String
	 *            tFlag 是否传入*BakSchema 1 是，0 否 add by pst on 2007-11-1
	 * @return boolean
	 */
	public boolean addSchema(Schema schema, String tFlag) {
		mErrors.clearErrors();

		if (schema == null) {
			buildError("addSchema", "参数为空");
			return false;
		}

		if (schema.getClass().getSuperclass().getName().equals(
				"com.sinosoft.lis.Schema")) {
			buildError("addSchema", "参数不是Schema的直接子类");
			return false;
		}

		String strClassName = schema.getClass().getName();

		// get rid of package name : com.sinosoft.lis.schema.
		strClassName = strClassName
				.substring(strClassName.lastIndexOf(".") + 1);


		// get rid of "schema" : LAAgentSchema -> LAAgent
		if ("1".equals(tFlag)) {
			strClassName = strClassName.substring(0, strClassName
					.indexOf("BakSchema"));
		} else {
			strClassName = strClassName.substring(0, strClassName
					.indexOf("Schema"));
		}

		Field fields[] = schema.getClass().getDeclaredFields();

		for (int nIndex = 0; nIndex < fields.length; nIndex++) {
			String strFieldName = fields[nIndex].getName();

			if (strFieldName.equals("FIELDNUM") || strFieldName.equals("PK")
					|| strFieldName.equals("mErrors")
					|| strFieldName.equals("fDate")) {
				continue;
			}

			String strFieldType = fields[nIndex].getType().getName();
			String strFieldValue = schema.getV(strFieldName);

			if (strFieldType.equals("float") && !strFieldValue.equals("-100.0")) {
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(strFieldValue));

			} else if (strFieldType.equals("int")
					&& !strFieldValue.equals("-100")) {
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(strFieldValue));

			} else if (strFieldType.equals("double")
					&& !strFieldValue.equals("-100")) {
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(strFieldValue));

			} else if (strFieldType.equals("java.lang.String")
					&& !strFieldValue.equals("null")) {
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(strFieldValue));

			} else if (strFieldType.equals("java.util.Date")
					&& !strFieldValue.equals("null")) {
				if (strFieldValue.indexOf('-') != -1) {
					strFieldValue = strFieldValue.substring(0, 4) + "年"
							+ strFieldValue.substring(5, 7) + "月"
							+ strFieldValue.substring(8, 10) + "日";
				}
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(strFieldValue));

			} else {
				_Element.addContent(new Element(strClassName + "."
						+ strFieldName).addContent(""));

			}
		}
		return true;
	}

	public boolean addSchemaSet(SchemaSet schemaSet, String strID) {
		return addSchemaSet(schemaSet, strID, "0");
	}

	/**
	 * 将SchemaSet中的第n个Schema信息放入到xml对象
	 * 
	 * @param schemaSet
	 *            SchemaSet
	 * @param strID
	 *            String
	 * @param String
	 *            tFlag 是否传入*BakSet 1 是，0 否 add by pst on 2007-11-1
	 * @return boolean
	 */
	public boolean addSchemaSet(SchemaSet schemaSet, String strID, String tFlag) {
		mErrors.clearErrors();

		if (schemaSet == null || strID == null) {
			buildError("addSchemaSet", "参数有错");
			return false;
		}

		if (schemaSet.getClass().getSuperclass().getName().equals(
				"com.sinosoft.lis.SchemaSet")) {
			buildError("addSchemaSet", "参数不是SchemaSet的直接子类");
			return false;
		}

		String strClassName = schemaSet.getClass().getName();

		// get rid of package name : com.sinosoft.lis.vschema.
		strClassName = strClassName
				.substring(strClassName.lastIndexOf(".") + 1);


		// get rid of "Set" : LAAgentSet -> LAAgent
		// tFlag 是否传入*BakSet 1 是，0 否 add by pst on 2007-11-1
		if ("1".equals(tFlag)) {
			strClassName = strClassName.substring(0, strClassName
					.indexOf("BakSet"));
		} else {
			strClassName = strClassName.substring(0, strClassName
					.indexOf("Set"));
		}

		Element eleList = null;

		if (strID.equals("")) {
			eleList = new Element(strClassName);
		} else {
			eleList = new Element(strID);
		}

		_Element.addContent(eleList);

		// get the columns' name
		// tFlag 是否传入*BakSchema 1 是，0 否 add by pst on 2007-11-1
		if ("1".equals(tFlag)) {
			strClassName = "com.sinosoft.lis.schema." + strClassName
					+ "BakSchema";
		} else {
			strClassName = "com.sinosoft.lis.schema." + strClassName + "Schema";
		}
		Field fields[] = null;

		Vector vFields = new Vector();

		try {
			fields = Class.forName(strClassName).getDeclaredFields();
		} catch (ClassNotFoundException ex) {
			buildError("addSchemaSet", "找不到对应的Schema类");
			return false;
		}

		Element eleHead = new Element("HEAD");
		eleList.addContent(eleHead);

		String strColName = "";
		int nColNum = 0;

		// first, we get col needed
		for (int nIndex = 0; nIndex < fields.length; nIndex++) {
			String strFieldName = fields[nIndex].getName();

			if (strFieldName.equals("FIELDNUM") || strFieldName.equals("PK")
					|| strFieldName.equals("mErrors")
					|| strFieldName.equals("fDate")) {
				continue;
			} else {
				strColName = "COL" + String.valueOf(nColNum++);
				eleHead.addContent(new Element(strColName)
						.addContent(strFieldName));
				vFields.add(fields[nIndex]);
			}
		}

		// then, we get row value
		for (int nIndex = 0; nIndex < schemaSet.size(); nIndex++) {

			Element eleRow = new Element("ROW");
			eleList.addContent(eleRow);

			Schema schema = (Schema) schemaSet.getObj(nIndex + 1);

			nColNum = 0;
			for (int nCols = 0; nCols < vFields.size(); nCols++) {
				String strFieldName = ((Field) vFields.get(nCols)).getName();
				String strFieldType = ((Field) vFields.get(nCols)).getType()
						.getName();
				String strFieldValue = schema.getV(strFieldName);

				strColName = "COL" + String.valueOf(nColNum++);
				if (strFieldType.equals("float")
						&& !strFieldValue.equals("-100.0")) {
					eleRow.addContent(new Element(strColName)
							.addContent(strFieldValue));
				} else if (strFieldType.equals("double")
						&& !strFieldValue.equals("-100.0")) {
					eleRow.addContent(new Element(strColName)
							.addContent(strFieldValue));
				} else if (strFieldType.equals("int")
						&& !strFieldValue.equals("-100")) {
					eleRow.addContent(new Element(strColName)
							.addContent(strFieldValue));
				} else if (strFieldType.equals("java.lang.String")
						&& !strFieldValue.equals("null")) {
					eleRow.addContent(new Element(strColName)
							.addContent(strFieldValue));
				} else if (strFieldType.equals("java.util.Date")
						&& !strFieldValue.equals("null")) {
					if (strFieldValue.indexOf('-') != -1) {
						strFieldValue = strFieldValue.substring(0, 4) + "年"
								+ strFieldValue.substring(5, 7) + "月"
								+ strFieldValue.substring(8, 10) + "日";
					}
					eleRow.addContent(new Element(strColName)
							.addContent(strFieldValue));
				} else {
					eleRow.addContent(new Element(strColName).addContent(""));
				}

			}
			// end of for(int nCols = 0; ...
		}
		// end of for(int nIndex = 0; ...

		return true;
	}

	/**
	 * 从现有的XMLDataset对象产生出一个新的XMLDataset对象
	 * 
	 * @return Object
	 */
	public Object clone() {
		if (_Element == null) {
			return null;
		}

		return new XMLDataset(this.getElement());
	}

	/**
	 * 构建错误信息
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "XmlExportHelper";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		mErrors.addOneError(cError);
	}
}
