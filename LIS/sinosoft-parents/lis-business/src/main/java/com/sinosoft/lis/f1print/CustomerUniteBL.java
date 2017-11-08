/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lh
 * @version 1.0
 */
public class CustomerUniteBL implements PrintService {
private static Logger logger = Logger.getLogger(CustomerUniteBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 取得的保单号码
	// private String mContNo = "";
	private String IssueCont = "";
	private String AppntName = "";
	private String qCustomerNo = "";

	// 输入的查询sql语句
	private String mSql = "";

	// 取得的延期承保原因
	// private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LDComSchema mLDComSchema = new LDComSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	public CustomerUniteBL() {
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
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("PRINT2")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (cOperate.equals("PRINT")) {
			if (!getPrintData()) {
				return false;
			}
		} else {
			if (!getPrintData1()) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// BodyCheckPrintBL tBodyCheckPrintBL = new BodyCheckPrintBL ();
		// tBodyCheckPrintBL.getSexName("1");
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
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		boolean PEFlag = false; // 打印体检件部分的判断标志
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		int m, i;
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		// 取得管理机构名称
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLCContSchema.getManageCom());
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得LDCom的数据时发生错误");
			return false;
		}
		mLDComSchema = tLDComDB.getSchema(); // 保存管理机构信息

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}
		String q_sql = "";
		String t_sql = "";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		q_sql = "select IssueCont,QuestionObjName,QuestionObj,OperatePos,standbyflag1 from LCIssuePol where BackObjType in ('1','2') and IssueType='99'  and NeedPrint = 'Y' and PrtSeq  = '"
				+ "?PrtSeq?" + "'";
		sqlbv1.sql(q_sql);
		sqlbv1.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		SSRS t_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv1);

		if (q_ssrs.getMaxRow() == 0) {

			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "没有问题件");
			return false;

		}

		String strRisk[] = null;
		String[] PETitle = new String[2];
		PETitle[0] = "relateCont";
		PETitle[1] = "relateAgentName";

		ListTable tListTable = new ListTable();
		if (!(q_ssrs.GetText(1, 1).equals("0")
				|| q_ssrs.GetText(1, 1).trim().equals("") || q_ssrs.GetText(1,
				1).equals("null")))

		{
			qCustomerNo = q_ssrs.GetText(1, 3);
			IssueCont = q_ssrs.GetText(1, 1);
			logger.debug("IssueCont=" + IssueCont);
			AppntName = q_ssrs.GetText(1, 2);
			logger.debug("AppntName=" + AppntName);
			String tCustomerNo = q_ssrs.GetText(1, 5);
			logger.debug("tCustomerNo=" + tCustomerNo);
			String tflag = q_ssrs.GetText(1, 4);
			logger.debug(tflag);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			t_sql = "select contno,agentcode from lccont where contno in "
					+ "(select contno from lcappnt  where appntno in('"
					+ "?qCustomerNo?" + "','" + "?tCustomerNo?" + "') union "
					+ "select contno from lcinsured where insuredno in('"
					+ "?qCustomerNo?" + "','" + "?tCustomerNo?" + "'))";
			sqlbv2.sql(t_sql);
			sqlbv2.put("qCustomerNo", qCustomerNo);
			sqlbv2.put("tCustomerNo", tCustomerNo);
			logger.debug("t_sql=" + t_sql);
			t_ssrs = q_exesql.execSQL(sqlbv2);

			if (t_ssrs != null && t_ssrs.getMaxRow() > 0
					&& t_ssrs.getMaxCol() > 0) {
				if ((t_ssrs.GetText(1, 1).equals("0")
						|| t_ssrs.GetText(1, 1).trim().equals("") || t_ssrs
						.GetText(1, 1).equals("null"))) {

					mErrors.copyAllErrors(t_ssrs.mErrors);
					buildError("outputXML", "在取得合同数据时发生错误");
					return false;
				}
			}

			tListTable.setName("CONT");
			for (i = 1; i <= t_ssrs.getMaxRow(); i++) {
				strRisk = new String[2];
				strRisk[0] = t_ssrs.GetText(i, 1); // 相关保单号码

				strRisk[1] = getAgentName(t_ssrs.GetText(i, 2)); // 相关保单业务员名字
				tListTable.add(strRisk);

			}

		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("ClientCombinat.vts", "");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		sqlbv3.sql(tSql);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		SSRS PolSSRS = new SSRS();
		PolSSRS = q_exesql.execSQL(sqlbv3);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return false;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错！");
			return false;
		}

		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("BankNo", mLAComSchema.getName()); // 代理机构
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}
		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		// 营业销售部名称
		String tBranchGroupCode = mLCContSchema.getManageCom();
		String tBranchGroupName = getComName(tBranchGroupCode);

		texttag.add("BarCode1", "UN013");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCContSchema.getContNo());
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("LABranchGroup.Name", tBranchGroupName + " "
				+ getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		texttag.add("IssueCont", IssueCont);
		texttag.add("Operator", mLCContSchema.getOperator());
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tListTable, PETitle);
		// xmlexport.outputDocumentToFile("e:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private boolean getPrintData1() {
		logger.debug("进入我的地盘");
		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		boolean PEFlag = false; // 打印体检件部分的判断标志
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		int m, i;
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			buildError("outputXML", "在取得LCGrpCont的数据时发生错误");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		// 取得管理机构名称
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLCGrpContSchema.getManageCom());
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得LDCom的数据时发生错误");
			return false;
		}
		mLDComSchema = tLDComDB.getSchema(); // 保存管理机构信息

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (tLCGrpAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mAgentCode = mLCGrpContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}
		String q_sql = "";
		String t_sql = "";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
	q_sql = "select IssueCont,QuestionObjName,QuestionObj,OperatePos from LCGrpIssuePol where BackObjType in ('1','2') and IssueType='99' and grpcontno  = '"
				+ "?grpcontno?" + "'";
		sqlbv4.sql(q_sql);
		sqlbv4.put("grpcontno", mLCGrpContSchema.getGrpContNo());
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		SSRS t_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv4);

		if (q_ssrs.getMaxRow() == 0) {

			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "没有问题件");
			return false;

		}

		String strRisk[] = null;
		String[] PETitle = new String[2];
		PETitle[0] = "info";

		ListTable tListTable = new ListTable();
		if (!(q_ssrs.GetText(1, 1).equals("0")
				|| q_ssrs.GetText(1, 1).trim().equals("") || q_ssrs.GetText(1,
				1).equals("null")))

		{
			IssueCont = q_ssrs.GetText(1, 1);
			logger.debug("IssueCont=" + IssueCont);
			AppntName = q_ssrs.GetText(1, 2);
			logger.debug("AppntName=" + AppntName);
			String tCustomerNo = q_ssrs.GetText(1, 3);
			logger.debug("tCustomerNo=" + tCustomerNo);
			String tflag = q_ssrs.GetText(1, 4);
			logger.debug(tflag);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			t_sql = " select issuecont from lcgrpissuepol b where b.grpcontno='"
					+ "?grpcontno?"
					+ "' and b.issuetype='99'";
			sqlbv5.sql(t_sql);
			sqlbv5.put("grpcontno", mLCGrpContSchema.getGrpContNo());
			logger.debug("t_sql=" + t_sql);
			t_ssrs = q_exesql.execSQL(sqlbv5);
			if ((t_ssrs.GetText(1, 1).equals("0")
					|| t_ssrs.GetText(1, 1).trim().equals("") || t_ssrs
					.GetText(1, 1).equals("null"))) {
				logger.debug("没有客户合并问题件记录");
			}

			tListTable.setName("INFO");
			for (i = 1; i <= t_ssrs.getMaxRow(); i++) {
				strRisk = new String[2];
				strRisk[0] = t_ssrs.GetText(i, 1); // 相关信息
				tListTable.add(strRisk);

			}

		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpClientCombinat.vts", "");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom();
		if (mLCContSchema.getManageCom().length() > 6) {
			tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		}
		String tManageComName = getComName(tManageComCode);
		// 营业销售部名称
		String tBranchGroupCode = mLCContSchema.getManageCom();
		String tBranchGroupName = getComName(tBranchGroupCode);

		texttag.add("BarCode1", "UN062");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCGrpContSchema.getGrpContNo()); //
		texttag.add("LCCont.AppntName", AppntName); //
		texttag.add("AppntName", AppntName); //
		texttag.add("ManageComName", tManageComName); //
		texttag.add("LABranchGroup.Name", tBranchGroupName); //
		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); //
		texttag.add("LCCont.AgentCode", mLCGrpContSchema.getAgentCode()); //
		texttag.add("IssueCont", IssueCont);
		texttag.add("Operator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tListTable, PETitle);
		// xmlexport.outputDocumentToFile("e:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		mSql = "select CodeName from LDcode where Code = '" + "?strComCode?"
				+ "' and CodeType = 'station'";
		sqlbv6.sql(mSql);
		sqlbv6.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv6);

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

	private String getAgentName(String strAgentCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			return null;
		}
		return tLAAgentDB.getName();
	}

}
