package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.ES_DOC_DEFDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.ES_DOC_DEFSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.ES_DOC_DEFSet;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan中心设置下载
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wellhi
 * @version 1.0
 */

public class GetCenterSettingBL {
private static Logger logger = Logger.getLogger(GetCenterSettingBL.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private GlobalInput tGI = new GlobalInput();
	private VData mResult = new VData();
	String mRtnCode = "0";
	String mRtnDesc = "";
	private static final String CON_BUSSTYPE_TB = "TB";
	private static final String CON_BUSSTYPE_BQ = "BQ";
	private static final String CON_BUSSTYPE_LP = "LP";
	private static final String CON_BUSSTYPE_OF = "OF";
	private static final String CON_BUSSTYPE_XB = "XB";
	private static final String CON_BUSSTYPE_CM = "CM";
	private static final String CON_BUSSTYPE_CP = "CP";
	private static final String CON_BUSSTYPE_HM = "HM";
	private static final String CON_XML_ROOT = "DATA";
	private static final String CON_XML_CENTERSETTING = "CenterSetting";

	private static final String XML_NODE_BUSSTYPE = "BussType";
	private static final String XML_NODE_SUBTYPE = "SubType";

	private static final String XML_NODE_COMPANY = "Company";
	private static final String XML_NODE_MANAGECOM = "ManageCom";
	private static final String CON_XML_RET_NAME = "RETURN";

	private static final String XML_ATTR_CODE = "code";
	private static final String XML_ATTR_NAME = "name";
	private static final String XML_ATTR_PAPERTYPE = "papertype";
	private static final String XML_ATTR_CODEFLAG = "codeflag";
	private static final String XML_ATTR_NEWCASEFLAG = "newcaseflag";
	private static final String XML_ATTR_VERSION = "version";
	private static final String XML_ATTR_CODELEN = "codelen";

	public GetCenterSettingBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		// 进行业务处理
		if (!dealData()) {
			tReturn = false;
		}
		return tReturn;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		try {
			// 获取入参
			if (!getInputData()) {
				return false;
			}
			// 返回数据
			if (!getReturnData()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getReturnData() {
		logger.debug("GetCenterSettingBL: start get Return Data  ...");
		try {
			// 创建标准XML文档，"DATA"作为根节点
			Element root = new Element(CON_XML_ROOT);
			Element cRoot = new Element(CON_XML_CENTERSETTING);
			// 投保
			Element tb = getBussType("0", CON_BUSSTYPE_TB);
			if (tb != null) {
				cRoot.addContent(tb);
			}
			tb = getBussType("0", CON_BUSSTYPE_TB + "1");
			if (tb != null) {
				cRoot.addContent(tb);
			}
			tb = getBussType("0", CON_BUSSTYPE_TB + "2");
			if (tb != null) {
				cRoot.addContent(tb);
			}
			tb = getBussType("0", CON_BUSSTYPE_TB + "3");
			if (tb != null) {
				cRoot.addContent(tb);
			}
			tb = getBussType("0", CON_BUSSTYPE_TB + "4");
			if (tb != null) {
				cRoot.addContent(tb);
			}
			// 保全
			Element bq = getBussType("0", CON_BUSSTYPE_BQ);
			if (bq != null) {
				cRoot.addContent(bq);
			}
			// 理赔
			Element lp = getBussType("0", CON_BUSSTYPE_LP);
			if (lp != null) {
				cRoot.addContent(lp);
			}
			// 财务
			Element of = getBussType("0", CON_BUSSTYPE_OF);
			if (of != null) {
				cRoot.addContent(of);
			}
			// 财务
			Element xb = getBussType("0", CON_BUSSTYPE_XB);
			if (xb != null) {
				cRoot.addContent(xb);
			}
			// 健康管理
			Element hm = getBussType("0", CON_BUSSTYPE_HM);
			if (hm != null) {
				cRoot.addContent(hm);
			}
			// 公共资料
			Element cm = getBussType("0", CON_BUSSTYPE_CM);
			if (cm != null) {
				cRoot.addContent(cm);
			}
			// 呈批
			Element cp = getBussType("0", CON_BUSSTYPE_CP);
			if (cp != null) {
				cRoot.addContent(cp);
			}
			// 未知类型
			Element ud = getUndefineType();// getBussType("0",
			// CON_BUSSTYPE_UD);
			if (ud != null) {
				cRoot.addContent(ud);
			}

			if (tb == null && bq == null && lp == null && hm == null
					&& cm == null && ud == null && cp == null) {
				logger.debug("There is not any valid BussType exist!");
				return false;
			}
			// 添加管理机构
			Element com = getCom();
			if (com != null) {
				cRoot.addContent(com);
			} else {
				logger.debug("There is not any valid ManageCom exist!");
				return false;
			}
			// 中心定义的设置
			Element cn = getCenterSetting().getCopy("CenterSetting");
			if (cn != null) {
				List cnList = cn.getChildren();
				int intCount = cnList.size();
				for (int i = 0; i < intCount; i++) {
					Element list = (Element) cnList.get(i);
					cRoot.addContent(list.getCopy(list.getName()));
				}
			} else {
				logger.debug("There is not any valid CenterSetting exist in EasyScan.xml!");
				return false;
			}

			root.addContent(cRoot);
			// 处理RETURN
			Element docRet = new Element(CON_XML_RET_NAME);
			String strNumber = "0"; // (String) vData.get(3);
			Element column = new Element("NUMBER");
			column = column.setText(strNumber);
			docRet.addContent(column);

			String strMessage = ""; // (String) vData.get(4);
			column = new Element("MESSAGE");
			column = column.setText(strMessage);
			docRet.addContent(column);
			root.addContent(docRet);

			Document doc = new Document(root);
			XMLOutputter out = new XMLOutputter();
			// 把doc转换为OutputStream
			String strXML = out.outputString(doc);
			mResult.add(strXML);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception in GetCenterSettingBL->getReturnData");
			logger.debug("Exception:" + ex.toString());

			// ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QueryDocumentBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	// 获取业务类型和单证细类
	private Element getBussType(String ValidFlag, String strBussType) {
		ES_DOC_DEFDB tES_DOC_DEFDB = new ES_DOC_DEFDB();
		// ES_DOC_DEFSet tES_DOC_DEFSet;// = new ES_DOC_DEFSet();
		// tES_DOC_DEFDB.setValidFlag(ValidFlag);
		// tES_DOC_DEFDB.setBussType(strBussType);
		// tES_DOC_DEFSet = tES_DOC_DEFDB.query();
		String strSQL = "select * from ES_DOC_DEF where BussType='"
				+ "?strBussType?" + "' and ValidFlag='" + "?ValidFlag?"
				+ "' order by subtype";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("strBussType", strBussType);
		sqlbv1.put("ValidFlag", ValidFlag);
		ES_DOC_DEFSet tES_DOC_DEFSet = tES_DOC_DEFDB.executeQuery(sqlbv1);
		logger.debug("BussType=" + strBussType + ";Count="
				+ tES_DOC_DEFSet.size());
		if (tES_DOC_DEFSet.size() == 0) {
			return null;
		}

		int intSize = tES_DOC_DEFSet.size();
		Element bussType = new Element(XML_NODE_BUSSTYPE);
		ES_DOC_DEFSchema tES_DOC_DEFSchema = tES_DOC_DEFSet.get(1);
		bussType.addAttribute(XML_ATTR_CODE, tES_DOC_DEFSchema.getBussType());
		bussType.addAttribute(XML_ATTR_NAME, tES_DOC_DEFSchema
				.getBussTypeName());
		// bussType.addAttribute(XML_ATTR_PAPERTYPE,tES_DOC_DEFSchema.getPaperType());
		for (int i = 0; i < intSize; i++) {
			ES_DOC_DEFSchema mES_DOC_DEFSchema = tES_DOC_DEFSet.get(i + 1);
			Element subType = new Element(XML_NODE_SUBTYPE);
			subType.addAttribute(XML_ATTR_CODE, mES_DOC_DEFSchema.getSubType());
			subType.addAttribute(XML_ATTR_NAME, mES_DOC_DEFSchema
					.getSubTypeName());
			subType.addAttribute(XML_ATTR_PAPERTYPE, mES_DOC_DEFSchema
					.getPaperType());
			subType.addAttribute(XML_ATTR_CODEFLAG, mES_DOC_DEFSchema
					.getCodeFlag());
			subType.addAttribute(XML_ATTR_NEWCASEFLAG, mES_DOC_DEFSchema
					.getNewCaseFlag());
			subType.addAttribute(XML_ATTR_VERSION, mES_DOC_DEFSchema
					.getVersion());
			subType.addAttribute(XML_ATTR_CODELEN, ""
					+ mES_DOC_DEFSchema.getCodeLen());
			//
			bussType.addContent(subType);
		}
		return bussType;
	}

	// 未知类型处理
	private Element getUndefineType() {
		Element bussType = new Element(XML_NODE_BUSSTYPE);

		bussType.addAttribute(XML_ATTR_CODE, "BUSS_TYPE_UD");
		bussType.addAttribute(XML_ATTR_NAME, "未知类型");
		Element subType = new Element(XML_NODE_SUBTYPE);
		subType.addAttribute(XML_ATTR_CODE, "SUB_TYPE_UD");
		subType.addAttribute(XML_ATTR_NAME, "未知类型");
		subType.addAttribute(XML_ATTR_PAPERTYPE, "");
		subType.addAttribute(XML_ATTR_CODEFLAG, "1");
		subType.addAttribute(XML_ATTR_NEWCASEFLAG, "1");
		subType.addAttribute(XML_ATTR_VERSION, "");
		subType.addAttribute(XML_ATTR_CODELEN, "0");
		bussType.addContent(subType);

		return bussType;
	}

	// 获取管理机构信息
	private Element getCom() {
		LDComDB tLDComDB = new LDComDB();
		LDComSet tLDComSet = new LDComSet();
		String strManageCom = tGI.ManageCom;
		if (strManageCom.length() > 6)
			strManageCom = strManageCom.substring(0, 6);
		String strSQL = "select * from ldcom where comcode like concat(?strManageCom?,'%')"
				+ " and exists(select * from es_com_server where managecom=comcode) "
				+ " order by comcode";
		logger.debug("GetManageCom SQL = " + strSQL);
		
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("strManageCom", strManageCom);
		tLDComSet = tLDComDB.executeQuery(sqlbv2);

		// tLDComSet = tLDComDB.query();
		if (tLDComSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetCenterSettingBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-1";
			tError.errorMessage = "查询数据库出现错误:"
					+ tLDComDB.mErrors.getFirstError();
			this.mErrors.addOneError(tError);
			return null;
		}

		int intSize = tLDComSet.size();
		Element com = new Element(XML_NODE_COMPANY);
		for (int i = 0; i < intSize; i++) {
			LDComSchema mLDComSchema = tLDComSet.get(i + 1);
			Element manageCom = new Element(XML_NODE_MANAGECOM);
			manageCom.addAttribute(XML_ATTR_CODE, mLDComSchema.getComCode());
			manageCom.addAttribute(XML_ATTR_NAME, mLDComSchema.getName());
			com.addContent(manageCom);
		}
		return com;
	}

	private Element getCenterSetting() {
		try {
			logger.debug("----- Reading EasyScanConfig File ----");
			SAXBuilder builder = new SAXBuilder();
			String configFile = "EasyScan.xml"; // com/sinosoft/lis/easyscan/
			logger.debug("EasyScanConfigFile=" + configFile);
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream(configFile);
			Document doc = builder.build(in);

			Element root = doc.getRootElement();
			Element center = root.getChild(CON_XML_CENTERSETTING); // (CON_XML_CENTERSETTING);
			return center;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 入参处理
	private boolean getInputData() {
		boolean tReturn = true;

		logger.debug("QueryIndexBL: Start get Input Data ...");
		// 获取入参
		tGI = (GlobalInput) mInputData.get(0);
		logger.debug("tGI  ManageCom is " + tGI.ManageCom);
		return tReturn;
	}
}
