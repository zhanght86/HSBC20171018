package com.sinosoft.lis.easyscan;

import org.apache.log4j.Logger;

import org.jdom.*;
import org.jdom.output.XMLOutputter;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.ES_DOC_ScanNoSchema;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan从中心请求获取扫描批次号
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wellhi
 * @version 1.0
 * 
 * @version 1.1 2009-5-20 陶宇 增加批次号码申请表
 *          用户申请完批次号后记录在批次号码申请表中,只有申请过的批次号使用之后,才可以继续申请
 */

public class GetScanNoBL {
	private static Logger logger = Logger.getLogger(GetScanNoBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private static final String CON_XML_ROOT = "DATA";
	private static final String CON_XML_SCANNO = "ScanNo";
	private static final String CON_XML_SCANNO_COUNT = "COUNT";
	private static final String CON_XML_RETURN = "RETURN";
	// private String mManageCom = "";
	// private String mOperator = "";
	// private String mOperate = "";
	// private String strScanNo = "";
	// private String strBussNo = "";
	// private String strBussType = "";
	// private String strSubType = "";
	// private String strManageCom = "";
	// private String strOperator = "";
	// private String strStartDate = "";
	// private String strEndDate = "";
	private String mManageCom;
	private String mOperator;
	private String mOperate;
	private String strScanNo;
	private String strBussNo;
	private String strBussType;
	private String strSubType;
	private String strStartDate;
	private String strEndDate;
	private String strBussType2;

	public GetScanNoBL() {
	}

	// 入参处理
	private boolean getInputData() {
		GlobalInput nGlobalInput = (GlobalInput) mInputData.get(0);
		mOperator = nGlobalInput.Operator;
		mManageCom = nGlobalInput.ManageCom;
		mOperate = (String) mInputData.get(1);
		strScanNo = (String) mInputData.get(2);
		strBussNo = (String) mInputData.get(3);
		strBussType = (String) mInputData.get(4);
		strSubType = (String) mInputData.get(5);
		strStartDate = (String) mInputData.get(6);
		strEndDate = (String) mInputData.get(7);
		strBussType2 = (String) mInputData.get(8);
		return true;
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		// 进行业务处理
		if (!getInputData()) {
			return false;
		}
		if (!dealData()) {
			tReturn = false;
		}
		return tReturn;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		try {
			// 获取新的批次号
			if (mOperate.equals("0")) {
				if (!getNewScanNo()) {
					return false;
				}
			} else if (mOperate.equals("1")) {// 查询历史批次号
				if (!getHisScanNo()) {
					return false;
				}
			} else if (mOperate.equals("2")) {
				if (!getLastScanNo()) {
					return false;
				}
			} else {
				// 一般不会出现
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

	/**
	 * @return
	 */
	private boolean getNewScanNo() {
		logger.debug("GetScanNoBL: Start getting new ScanNo  ...");
		Element root = new Element(CON_XML_ROOT);
		Element scanNo = new Element(CON_XML_SCANNO);
		Element docRet = new Element(CON_XML_RETURN);
		String strNumber = "0";
		String strMessage = "";
		String strScanNo = "";
		Element column;
		// 检查当前用户是否有已申请但未使用的批次号,如果有不能再申请
		String strSQL = "select scanno from es_doc_scanno where scanOperator = '"
				+ "?scanOperator?"
				+ "' and scanno not in(select scanno from es_doc_main)";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("scanOperator", this.mOperator);
		ExeSQL tExeSQL = new ExeSQL();
		String countScanNo = tExeSQL.getOneValue(sqlbv1);
		if (countScanNo != null && !"".equals(countScanNo)) {
			strNumber = "-500";
			strMessage = "申请失败:有未使用的批次号[" + countScanNo + "],请在历史批次号中查询!";
			logger.debug("申请失败:有未使用的批次号,请在历史批次号中查询!");
		} else {

			// 检查今日是否有申请该类型的批次号
			// if (getThisDayScanNo()) {
			// return false;
			// }
			// 获取最大批次号
			strScanNo = getMaxNo("ScanNo");
			if (!strScanNo.equals("")) {
				scanNo.setText(strScanNo);
				root.addContent(scanNo);
			} else {
				logger.debug("EasyScan获取批次号出错。");
				strNumber = "-500";
				strMessage = "EasyScan获取批次号出错!";
			}
		}

		// 添加返回的错误
		// 错误代码
		column = new Element("NUMBER");
		column = column.setText(strNumber);
		docRet.addContent(column);

		column = new Element("MESSAGE");
		column = column.setText(strMessage);
		docRet.addContent(column);
		root.addContent(docRet);
		// 错误信息
		try {
			Document doc = new Document(root);
			XMLOutputter out = new XMLOutputter();
			// 把doc转换为OutputStream
			String strXML = out.outputString(doc);
			logger.debug(strXML);
			mResult.add(strXML);
		} catch (Exception e) {
			logger.debug("EasyScan获取批次号出错。Exception:" + e.toString());
		}
		if (!"-500".equals(strNumber)) {
			// 将生成的号码存放在扫描批次号申请表中 2007-11-20 V1.1
			ES_DOC_ScanNoSchema tES_DOC_ScanNoSchema = new ES_DOC_ScanNoSchema();
			tES_DOC_ScanNoSchema.setScanNo(strScanNo);
			tES_DOC_ScanNoSchema.setScanOperator(mOperator);
			tES_DOC_ScanNoSchema.setManageCom(mManageCom);
			tES_DOC_ScanNoSchema.setOperator(mOperator);
			tES_DOC_ScanNoSchema.setMakeDate(PubFun.getCurrentDate());
			tES_DOC_ScanNoSchema.setMakeTime(PubFun.getCurrentTime());
			tES_DOC_ScanNoSchema.setModifyDate(PubFun.getCurrentDate());
			tES_DOC_ScanNoSchema.setModifyTime(PubFun.getCurrentTime());
			MMap tMMap = new MMap();
			VData tVData = new VData();
			tMMap.put(tES_DOC_ScanNoSchema, "INSERT");
			tVData.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "INSERT")) {
				logger.debug("GetScanNoBL: 保存申请的批次号码失败!");
			}
		}
		logger.debug("GetScanNoBL: End of getting new ScanNo.");
		return true;
	}

	private boolean getThisDayScanNo() {
		logger.debug("GetThisDayScanNoBL: Start getting Today ScanNo  ...");

		Element root = new Element(CON_XML_ROOT);
		Element docRet = new Element(CON_XML_RETURN);
		String strNumber = "0";
		String strMessage = "";

		StringBuffer bufSQL = new StringBuffer(
				"SELECT DISTINCT ScanNo,COUNT(ScanNo) FROM es_doc_main");// edited
																			// by
																			// zxm
																			// 06.02.25
		bufSQL.append(" WHERE ManageCom like ");
		bufSQL.append("concat(?mManageCom?,'%')");
		bufSQL.append(" AND BussType='");
		bufSQL.append(strBussType2);
		bufSQL.append("' AND MakeDate='");
		bufSQL.append("?MakeDate?");
		bufSQL.append("' GROUP BY ScanNo ORDER BY ScanNo DESC");

		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(bufSQL.toString());
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("MakeDate", PubFun.getCurrentDate().toString());
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv2);
		if (nSSRS.getMaxRow() > 0) {
			strNumber = "-3500";
			strMessage = "该业务类型的批次号今日已被申请，请通过历史批次号查询功能获取您所需的批次号。";
		} else {
			return false;
		}
		// 添加返回的错误
		// 错误代码
		Element column = new Element("NUMBER");
		column = column.setText(strNumber);
		docRet.addContent(column);
		// 错误信息
		column = new Element("MESSAGE");
		column = column.setText(strMessage);
		docRet.addContent(column);
		root.addContent(docRet);

		try {
			Document doc = new Document(root);
			XMLOutputter out = new XMLOutputter();
			// 把doc转换为OutputStream
			String strXML = out.outputString(doc);
			mResult.add(strXML);
		} catch (Exception e) {
			logger.debug("EasyScan查询历史批次号出错。Exception:" + e.toString());
		}
		logger.debug("GetScanNoBL: End of getting today ScanNo.");
		return true;
	}

	private boolean getHisScanNo() {
		logger.debug("GetScanNoBL: Start getting history ScanNo  ...");
		// ES_DOC_MAINSchema nES_DOC_MAINSchema=new ES_DOC_MAINSchema();

		Element root = new Element(CON_XML_ROOT);
		Element docRet = new Element(CON_XML_RETURN);
		String strNumber = "0";
		String strMessage = "";

		// String strSQL =
		// "SELECT DISTINCT ScanNo FROM es_doc_main WHERE trim(ManageCom)='" +
		// mManageCom + "' and trim(ScanOperator)='" + mOperator +
		// "' order by ScanNo DESC";
		/*
		 * 单证号码类型： 11 投保单印刷号PrtNo 12 团单号码GrpContNo 21 理赔申请号 31 保全申请号
		 */
		StringBuffer bufSQL = new StringBuffer("SELECT ScanNo,COUNT(ScanNo)");
		bufSQL.append(" FROM ( SELECT");
		bufSQL.append(" DISTINCT m.DocCode,m.ScanNo");
		bufSQL.append(" FROM es_doc_main m,es_doc_relation r");
		bufSQL.append(" WHERE r.BussNoType in ('11','12','13','15','23','31')");
		bufSQL.append(" AND m.DocCode=r.BussNo AND m.ManageCom like ");
		if (mManageCom.length() == 2)
			bufSQL.append("concat(?mManageCom?,'%')");
		else if (mManageCom.length() >= 4)
			bufSQL.append("concat(?mManageComSub?,'%')");
		else
			bufSQL.append("'A%'");
		if (mOperator != null && !mOperator.equals("")) {
			bufSQL.append(" AND m.ScanOperator='");
			bufSQL.append("?mOperator?'");
		}
		if (strScanNo != null && !strScanNo.equals("")) {
			bufSQL.append(" AND m.ScanNo='");
			bufSQL.append("?strScanNo?'");
		}
		if (strBussNo != null && !strBussNo.equals("")) {
			bufSQL.append(" AND m.DocCode='");
			bufSQL.append("?strBussNo?'");
		}
		if (strBussType != null && !strBussType.equals("")) {
			bufSQL.append(" AND m.BussType='");
			bufSQL.append("?strBussType?'");
		}
		if (strSubType != null && !strSubType.equals("")) {
			bufSQL.append(" AND m.SubType='");
			bufSQL.append("?strSubType?'");
		}
		if (strStartDate != null && !strStartDate.equals("")) {
			if (strEndDate != null && !strEndDate.equals("")) {
				bufSQL.append(" AND m.MakeDate BETWEEN'");
				bufSQL.append("?strStartDate?");
				bufSQL.append("' AND '");
				bufSQL.append("?strEndDate?'");
			} else {
				bufSQL.append(" AND m.MakeDate='");
				bufSQL.append("?strStartDate?'");
			}
		} else {
			if (strEndDate != null && !strEndDate.equals("")) {
				bufSQL.append(" AND m.MakeDate='");
				bufSQL.append("?strEndDate?'");
			}
		}
		bufSQL.append(" GROUP BY m.DocCode,m.ScanNo");
		bufSQL.append(" UNION ");
		bufSQL.append(" select '0' as DocCode ,scanNo from es_doc_ScanNo where");
		bufSQL.append(" ManageCom like ");
		if (mManageCom.length() == 2)
			bufSQL.append("concat(?mManageCom?,'%')");
		else if (mManageCom.length() >= 4)
			bufSQL.append("concat(?mManageComSub?,'%')");
		else
			bufSQL.append("'A%'");
		if (mOperator != null && !mOperator.equals("")) {
			bufSQL.append(" AND ScanOperator='");
			bufSQL.append("?mOperator?'");
		}
		if (strScanNo != null && !strScanNo.equals("")) {
			bufSQL.append(" AND ScanNo='");
			bufSQL.append("?strScanNo?'");
		}
		if (strStartDate != null && !strStartDate.equals("")) {
			if (strEndDate != null && !strEndDate.equals("")) {
				bufSQL.append(" AND MakeDate BETWEEN'");
				bufSQL.append("?strStartDate?");
				bufSQL.append("' AND '");
				bufSQL.append("?strEndDate?'");
			} else {
				bufSQL.append(" AND MakeDate='");
				bufSQL.append("?strStartDate?'");
			}
		} else {
			if (strEndDate != null && !strEndDate.equals("")) {
				bufSQL.append(" AND MakeDate='");
				bufSQL.append("?strEndDate?'");
			}
		}
		bufSQL.append(" and scanNo not in(select distinct scanno from es_doc_main)");
		bufSQL.append(" ORDER BY DocCode,ScanNo");
		bufSQL.append(" ) g GROUP BY ScanNo ORDER BY ScanNo DESC");
		
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(bufSQL.toString());
		sqlbv3.put("mManageCom", mManageCom);
		sqlbv3.put("mManageComSub", mManageCom.substring(0, 4));
		sqlbv3.put("mOperator", mOperator);
		sqlbv3.put("strScanNo", strScanNo);
		sqlbv3.put("strBussNo", strBussNo);
		sqlbv3.put("strBussType", strBussType);
		sqlbv3.put("strSubType", strSubType);
		sqlbv3.put("strStartDate", strStartDate);
		sqlbv3.put("strEndDate", strEndDate);
		
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv3);
		if (nSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
				Element scanNo = new Element(CON_XML_SCANNO);
				// Element scanNoCount = new
				// Element(CON_XML_SCANNO_COUNT);//edited by zxm
				String strScanNo = nSSRS.GetText(i, 1);
				String strScanNoCount = nSSRS.GetText(i, 2);// edited by zxm
															// 06.02.27
				if (strScanNo != null && !strScanNo.equals("")
						&& !strScanNo.equals("null")) {
					scanNo.setText(strScanNo);
					// scanNoCount.setText(strScanNoCount);
					Attribute scanNoCount = new Attribute(CON_XML_SCANNO_COUNT,
							strScanNoCount);
					scanNo.addAttribute(scanNoCount);
					root.addContent(scanNo);
				} else {
					logger.debug("EasyScan查询历史批次号出错。");
					strNumber = "-500";
					strMessage = "EasyScan查询历史批次号出错!";
					break;
				}
			}
		} else {
			strNumber = "-500";
			// strMessage = "没有找到管理机构 [" + mManageCom + "] 的操作员 [" + mOperator +
			// "] 相应的历史批次号，请申请新的批次号!";
			strMessage = "未找到符合条件的批次号，请更改查询条件再次进行查询.或者申请新的批次号。\n\n"
					+ "您的查询条件是:\n" + "管理机构 [" + mManageCom + "]；\n操作员 ["
					+ mOperator + "]；\n" + "扫描批次号 [" + strScanNo + "]；\n业务号码 ["
					+ strBussNo + "]；\n" + "操作日期介于 [" + strStartDate + "] 和 ["
					+ strEndDate + "]";
		}
		// 添加返回的错误
		// 错误代码
		Element column = new Element("NUMBER");
		column = column.setText(strNumber);
		docRet.addContent(column);
		// 错误信息
		column = new Element("MESSAGE");
		column = column.setText(strMessage);
		docRet.addContent(column);
		root.addContent(docRet);

		try {
			Document doc = new Document(root);
			XMLOutputter out = new XMLOutputter();
			// 把doc转换为OutputStream
			String strXML = out.outputString(doc);
			mResult.add(strXML);
		} catch (Exception e) {
			logger.debug("EasyScan查询历史批次号出错。Exception:" + e.toString());
		}
		logger.debug("GetScanNoBL: End of getting history ScanNo.");
		return true;
	}

	// 生成流水号，包含错误处理
	private String getMaxNo(String cNoType) {
		// logger.debug("业务类型是：" +
		// mInputData.elementAt(8).toString());//只要把业务类型输出即可
		// logger.debug("业务类型是：" +
		// PubFun.getCurrentDate().toString());//只要把业务类型输出即可
		// String nType=mInputData.elementAt(8).toString();
		// String tBp="";
		// String strNo;
		VData nVData = new VData();
		nVData.setSize(5);
		nVData.setElementAt(strBussType2, 0);
		String strNo = PubFun1.CreateMaxNo(cNoType, mManageCom, nVData);
		// String strNo1 = PubFun.getCurrentDate().toString(); // strNo =
		// PubFun1.CreateMaxNo(cNoType, mManageCom,tBp);
		if (strNo.equals("") || strNo.equals("0")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UploadPrepareBL";
			tError.functionName = "getNewScanNo";
			tError.errorNo = "-500";
			tError.errorMessage = "生成流水号失败!";
			this.mErrors.addOneError(tError);
			strNo = "";
		}
		return strNo;
	}

	/**
	 * 得到当前用户最近一次使用的扫描批次号
	 * */
	private boolean getLastScanNo() {
		logger.debug("GetScanNoBL->getLastScanNo: Start getting last ScanNo  ...");
		Element root = new Element(CON_XML_ROOT);
		Element scanNo = new Element(CON_XML_SCANNO);
		Element docRet = new Element(CON_XML_RETURN);
		String strNumber = "0";
		String strMessage = "";
		String strScanNo = "";
		Element column;
		// 从批次号申请表及单证扫描表中查询当前用户最近一次使用的批次号,如果没有查到,返回空
		// 一个用户可能使用了其他用户的扫描批次号 此sql比较复杂,如有更好的办法再做修改
		String strSQL = "select scanno,makedate,maketime from es_doc_scanno where scanOperator = '"
				+ "?scanOperator?"
				+ "'"
				+ " union "
				+ " select scanno,makedate,maketime from es_doc_main where scanOperator = '"
				+ "?scanOperator?"
				+ "' and makedate >= (select max(makedate) from es_doc_scanno where scanOperator = '"
				+ "?scanOperator?"
				+ "')"
				+ " order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(strSQL);
		sqlbv4.put("scanOperator", this.mOperator);
		ExeSQL tExeSQL = new ExeSQL();
		strScanNo = tExeSQL.getOneValue(sqlbv4);
		if (strScanNo == null || "".equals(strScanNo)) {
			strScanNo = "";
			strNumber = "-500";
			strMessage = "没有得到最近一次使用的批次号,请申请新批次号!";
			logger.debug("没有得到最近一次使用的批次号");
		}
		logger.debug("countScanNo:" + strScanNo);
		// 返回得到的扫描批次号
		scanNo.setText(strScanNo);
		root.addContent(scanNo);
		// 添加返回的错误
		// 错误代码
		column = new Element("NUMBER");
		column = column.setText(strNumber);
		docRet.addContent(column);

		column = new Element("MESSAGE");
		column = column.setText(strMessage);
		docRet.addContent(column);
		root.addContent(docRet);
		// 错误信息
		try {
			Document doc = new Document(root);
			XMLOutputter out = new XMLOutputter();
			// 把doc转换为OutputStream
			String strXML = out.outputString(doc);
			logger.debug(strXML);
			mResult.add(strXML);
		} catch (Exception e) {
			logger.debug("EasyScan获取最近使用的批次号出错。Exception:" + e.toString());
		}
		logger.debug("GetScanNoBL->getLastScanNo: End of getting Last ScanNo.");
		return true;
	}

	public static void main(String[] args) {
		GetScanNoBL nGetScanNoBL = new GetScanNoBL();
		VData nVData = new VData();
		nGetScanNoBL.submitData(nVData, "0");
		nGetScanNoBL.submitData(nVData, "1");
	}

}
