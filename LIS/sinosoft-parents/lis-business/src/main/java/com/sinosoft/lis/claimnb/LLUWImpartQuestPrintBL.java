package com.sinosoft.lis.claimnb;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class LLUWImpartQuestPrintBL implements PrintService {
	private static Logger logger = Logger
			.getLogger(LLUWImpartQuestPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 输入的查询sql语句
	private String mSql = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	public LLUWImpartQuestPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		logger.debug("PrtNo=" + PrtSeq);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		// boolean PEFlag = false; // 打印体检件部分的判断标志
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
		// tXmlExport.createDocument("ImpartQuest.vts", "printer"); //
		// 最好紧接着就初始化xml文档
		tXmlExport.createDocument("补充告知问卷");
		texttag.add("Appnt", mLCContSchema.getAppntName()); // 投保人邮政编码
		texttag.add("Insured", mLCContSchema.getInsuredName()); // 投保人地址
		texttag.add("ContNo", mLCContSchema.getPrtNo());
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(tXmlExport);
		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String getComName(String strComCode) {
		mSql = "select CodeName from LDcode where Code = '" + "?comcode?"
				+ "' and CodeType = 'station'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mSql);
		sqlbv.put("comcode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv);

	}

	@SuppressWarnings("unused")
	private String getIDNO(String customerno) {
		mSql = "select idno from ldperson where customerno = '" + "?customerno?"
				+ "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(mSql);
		sqlbv1.put("customerno", customerno);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv1);

	}

	/**
	 * 获取性别
	 * 
	 * @param StrSex
	 *            String
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String getSexName(String StrSex) {
		mSql = "select CodeName from LDcode where Code = '" + "?StrSex?"
				+ "' and CodeType = 'sex'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(mSql);
		sqlbv2.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv2);

	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	/**
	 * 获取体检人职业
	 */
	@SuppressWarnings("unused")
	private String getWork(String StrWork) {
		mSql = "select occupationname from ldoccupation where occupationcode='"
				+ "?StrWork?" + "' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(mSql);
		sqlbv3.put("StrWork", StrWork);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv3);

	}

}
