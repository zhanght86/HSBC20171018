package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全核保加费通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：wangyan
 * @version：1.0
 * @CreateDate：2005-08-23
 */
public class EdorAddChargePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(EdorAddChargePrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private XmlExportNew tXmlExport = new XmlExportNew();
	private String[] Title = new String[5];

	private String mContNo; // 保单号
	private String mManageName; // 保单归属地
	private String mAppName; // 投保人姓名
	private String mInsuredName; // 被保人姓名
	private String mBank; // 银行及储蓄所
	private String mGroup; // 业务分部及业务组
	private String mDep; // 营销服务部及营业部
	private String mAgentCode; // 代理人代码
	private String mAgent; // 代理人姓名
	private String mAppntName; // 申请人
	private String mContent; // 特别约定
	private double SumPrem = 0.00; // 合计加费金额
	private String mApprove; // 核保员
	private String mMakeDate; // 时间

	private String mEdorNo; // 批单号
	private String mEdorType; // 批改类型

	public static final String VTS_NAME = "EdorAddCharge.vts"; // 模板名称

	public EdorAddChargePrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BdsxNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 从数据库获得数据
		if (!getBaseData()) {
			return false;
		}
		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		// 传入对象为空？
		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		// 打印流水号（通知书号）为空？
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		// 打印记录为空？
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String mNoType = mLOPRTManagerSchema.getOtherNoType();
		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorNo = mLOPRTManagerSchema.getStandbyFlag1();
		mEdorType = mLOPRTManagerSchema.getStandbyFlag2();

		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals("BQ81")) {
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传送单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getBaseData() {
		// 查询保单信息
		LPContDB tLPContDB = new LPContDB();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		if (!tLPContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "EdorAddChargePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询保单批改信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPContSchema = tLPContDB.getSchema();
		mAppName = tLPContSchema.getAppntName(); // 投保人姓名
		mInsuredName = tLPContSchema.getInsuredName(); // 被保人姓名
		if (mAppName == null || mAppName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "EdorSpecPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "申请人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String[] allArr = BqNameFun.getAllNames(tLPContSchema.getAgentCode());
		if (tLPContSchema.getSaleChnl().equals("3")) {
			LAComSchema tLAComSchema = new LAComSchema();
			LAComDB tLAComDB = new LAComDB();
			tLAComDB.setAgentCom(tLPContSchema.getAgentCom());
			// Modified By QianLy on 2006-09-13 --------
			if (tLAComDB.getInfo()) {
				tLAComSchema = tLAComDB.getSchema(); // 保存银行代码信息
				mBank = tLAComSchema.getName(); // 代理机构
				// CError tError = new CError();
				// tError.moduleName = "EdorAddChargePrintBL";
				// tError.functionName = "getBaseData";
				// tError.errorMessage = "在取得LACom的数据时发生错误!";
				// this.mErrors.addOneError(tError);
				// return false;
			} else {
				mBank = "";
			}
			// ------------

			mGroup = allArr[BqNameFun.DEPART] + allArr[BqNameFun.TEAM]; // 业务分部及业务组.
		}
		mDep = allArr[BqNameFun.SALE_SERVICE] + allArr[BqNameFun.DEPART]; // 营销服务部及营业部
		// mManageName = allArr[BqNameFun.SALE_SERVICE];
		mManageName = allArr[BqNameFun.CENTER_BRANCH]; // 中支
		mAgent = allArr[BqNameFun.AGENT_NAME]; // 业务人姓名
		mAgentCode = tLPContSchema.getAgentCode(); // 业务员编号
		tXmlExport.createDocument("保全核保加费通知书"); // 初始化xml文档
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLPContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			tXmlExport.setUserLanguage(uLanguage);
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		dealAppntAddCharge(tLPContSchema);
		dealInsuredAddCharge(tLPContSchema);
		mApprove = tLPContSchema.getUWOperator(); // 核保人
		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);
		return true;
	}

	private boolean preparePrintData() {

		// tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素

		texttag.add("LCCont.ContNo", mContNo); // 保单号
		texttag.add("ManageComName", mManageName); // 保单归属地
		texttag.add("LCCont.AppntName", mAppName); // 投保人姓名
		texttag.add("LCCont.InsuredName", mInsuredName); // 被保人姓名
		texttag.add("BankNo", mBank); // 银行及储蓄所
		texttag.add("AgentGroup", mGroup); // 营业分部及业务组
		texttag.add("LABranchGroup.Name", mDep); // 营销服务部及营业部
		texttag.add("LAAgent.Name", mAgent); // 业务员姓名
		texttag.add("LAAgent.AgentCode", mAgentCode); // 业务员编号
		texttag.add("AppntName", mAppName); // 申请人姓名
		String SumPrem1;
		SumPrem1 = BqNameFun.getRound(SumPrem);
		texttag.add("SumPrem", SumPrem1);

		/**
		 * ********* add by lizhuo at 2006-3-13
		 * **************************************
		 */
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String iSQL = "select sum(getmoney) from ljsgetendorse where 1=1"
				+ " and endorsementno = '" + "?mEdorNo?" + "'" + " and contno = '"
				+ "?mContNo?" + "'"
				+ " and (othernotype = '6' or subfeeoperationtype like '%UW%')";
		sqlbv1.sql(iSQL);
		sqlbv1.put("mEdorNo", mEdorNo);
		sqlbv1.put("mContNo", mContNo);
		String GetMoney = tExeSQL.getOneValue(sqlbv1);
		if (GetMoney != null && !"".equals(GetMoney)) {
			tXmlExport.addDisplayControl("displayGetMoney");
			tXmlExport.addDisplayControl("displayHead2");
			texttag.add("GetMoney", GetMoney);
			// iSQL = "select sum(getmoney) from ljsgetendorse where 1=1"
			// + " and endorsementno = '" + mEdorNo + "'"
			// + " and contno = '" + mContNo + "'"
			// + " and subfeeoperationtype like '%UW%'"
			// + " and feefinatype = 'LX'";
			// String SumLX = tExeSQL.getOneValue(iSQL);
			// if(SumLX != null && !"".equals(SumLX)){
			// tXmlExport.addDisplayControl("displaySumLX");
			// texttag.add("SumLX",SumLX);
			// }
		} else {
			tXmlExport.addDisplayControl("displayHead1");
		}
		/**
		 * ****** end
		 * **************************************************************
		 */

		// 合计金额
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator()); // 核保人
		texttag.add("SysDate", mMakeDate); // 日期

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	private void dealAppntAddCharge(LPContSchema cLPContSchema) {
		LPContSchema mLPContSchema = new LPContSchema();
		mLPContSchema = cLPContSchema;
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVV--------投保人加费部分-------START--------VVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// 投保人职业加费
		// 主险
		boolean flagapp1 = false;
		boolean flagapp2 = false;
		String tSql;
		ExeSQL tempExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		tSql = "select a.riskcode,sum(b.prem) from lppol a ,lpprem b where b.contno= '"
				+ "?contno?"
				+ "' and b.edorno = '"
				+ "?edorno?"
				+ "' and b.edortype = '"
				+ "?edortype?"
				+ "' and b.addfeedirect = '01' and b.payplancode like '000000%' and b.payplantype = '02' "
				+ " and a.polno = b.polno and b.polno in (select c.polno from lppol c where c.polno=c.mainpolno and c.contno='"
				+ "?contno?" + "') group by riskcode";
		sqlbv2.sql(tSql);
		sqlbv2.put("contno", mLPContSchema.getContNo());
		sqlbv2.put("edorno", mLPContSchema.getEdorNo());
		sqlbv2.put("edortype", mLPContSchema.getEdorType());
		SSRS temp7SSRS = new SSRS(); // 投保人职业加费
		temp7SSRS = tempExeSQL.execSQL(sqlbv2);

		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";

		ListTable ListTable1 = new ListTable();
		ListTable1.setName("AppendWork");
		if (temp7SSRS != null && temp7SSRS.getMaxRow() > 0
				&& temp7SSRS.getMaxCol() > 0) {
			if (!(temp7SSRS.GetText(1, 2).equals("0")
					|| temp7SSRS.GetText(1, 2).trim().equals("") || temp7SSRS
					.GetText(1, 2).equals("null"))) {
				flagapp1 = true;

				LMRiskDB t1LMRiskDB = new LMRiskDB();
				LMRiskSchema t1LMRiskSchema = new LMRiskSchema();
				t1LMRiskDB.setRiskCode(temp7SSRS.GetText(1, 1));
				t1LMRiskSchema = t1LMRiskDB.getSchema();

				String[] stra = new String[5];
				stra[0] = "险种1:"; // 主险还是附加险
				stra[1] = temp7SSRS.GetText(1, 1); // 险种代码
				stra[2] = t1LMRiskSchema.getRiskShortName(); // 险种名称
				stra[3] = "￥" + BqNameFun.getRound(temp7SSRS.GetText(1, 2)); // 职业加费金额
				stra[4] = "";

				SumPrem = SumPrem + Double.parseDouble(temp7SSRS.GetText(1, 2));
				ListTable1.add(stra);
				tXmlExport.addDisplayControl("displayAppWork");
			}
		}

		// 投保人健康加费
		// 主险
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		tSql = "select a.riskcode,sum(b.prem) from lppol a ,lpprem b where b.contno = '"
				+ "?contno?"
				+ "' and b.edorno = '"
				+ "?edorno?"
				+ "' and b.edortype = '"
				+ "?edortype?"
				+ "' and b.addfeedirect = '01' and b.payplancode like '000000%' and b.payplantype = '01'"
				+ " and a.polno = b.polno and b.polno in (select c.polno from lppol c where c.polno = c.mainpolno and c.contno = '"
				+ "?contno?" + "') group by riskcode ";
		logger.debug(tSql);
		sqlbv3.sql(tSql);
		sqlbv3.put("contno", mLPContSchema.getContNo());
		sqlbv3.put("edorno", mLPContSchema.getEdorNo());
		sqlbv3.put("edortype", mLPContSchema.getEdorType());
		SSRS temp8SSRS = new SSRS(); // 投保人健康加费
		temp8SSRS = tempExeSQL.execSQL(sqlbv3);

		ListTable ListTable2 = new ListTable();
		ListTable2.setName("AppendHealth");
		if (temp8SSRS != null && temp8SSRS.getMaxRow() > 0
				&& temp8SSRS.getMaxCol() > 0) {
			if (!(temp8SSRS.GetText(1, 2).equals("0")
					|| temp8SSRS.GetText(1, 2).trim().equals("") || temp8SSRS
					.GetText(1, 2).equals("null"))) {
				flagapp2 = true;

				LMRiskDB t2LMRiskDB = new LMRiskDB();
				LMRiskSchema t2LMRiskSchema = new LMRiskSchema();

				t2LMRiskDB.setRiskCode(temp8SSRS.GetText(1, 1));
				t2LMRiskSchema = t2LMRiskDB.getSchema();

				String[] strb = new String[5];
				strb[0] = "险种1:"; // 主险还是附加险
				strb[1] = temp8SSRS.GetText(1, 1); // 险种代码
				strb[2] = t2LMRiskSchema.getRiskShortName(); // 险种名称
				strb[3] = "￥" + BqNameFun.getRound(temp8SSRS.GetText(1, 2)); // 健康加费金额
				strb[4] = "";

				SumPrem = SumPrem + Double.parseDouble(temp8SSRS.GetText(1, 2));
				ListTable2.add(strb);
				tXmlExport.addDisplayControl("displayAppHealth");
			}
		}
		logger.debug("flagapp1=" + flagapp1);
		logger.debug("flagapp2=" + flagapp2);

		if (flagapp1 == true) {
			tXmlExport.addDisplayControl("displayAppWork"); // 模版上投保人职业加费部分的控制标记
			tXmlExport.addListTable(ListTable1, Title); // 保存投保人职业加费信息
		}

		if (flagapp2 == true) {
			tXmlExport.addDisplayControl("displayAppHealth"); // 模版上投保人健康加费部分的控制标记
			tXmlExport.addListTable(ListTable2, Title); // 保存投保人健康加费信息
		}

		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVV---------投保人加费部分-----END----VVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
	}

	private void dealInsuredAddCharge(LPContSchema cLPContSchema) {
		LPContSchema mLPContSchema = new LPContSchema();
		mLPContSchema = cLPContSchema;
		ExeSQL tempExeSQL = new ExeSQL();
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVV--------被保人加费部分-------START--------VVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// 判断是否有联生险
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String tSql = "select a.polno from lppol a where a.contno='"
				+ "?contno?"
				+ "' and a.edorno = '"
				+ "?edorno?"
				+ "' and a.edortype = '"
				+ "?edortype?"
				+ "' and a.RiskCode in (select b.riskcode from lmriskapp b where b.insuredflag='M'"
				+ " and riskprop='I')";
		sqlbv4.sql(tSql);
		sqlbv4.put("contno", mLPContSchema.getContNo());
		sqlbv4.put("edorno", mLPContSchema.getEdorNo());
		sqlbv4.put("edortype", mLPContSchema.getEdorType());
		SSRS temp9SSRS = new SSRS();
		temp9SSRS = tempExeSQL.execSQL(sqlbv4);

		// 查询该合同下有几个被保险人。
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setContNo(mLPContSchema.getContNo());
		tLPInsuredDB.setEdorNo(mLPContSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPContSchema.getEdorType());
		LPInsuredSet tempLPInsuredSet = tLPInsuredDB.query();
		LCInsuredSet tempLCInsuredSet = new LCInsuredSet();
		int i = 2 * tempLPInsuredSet.size();// 每个被保险人有职业和健康加费两种。
		if (tempLPInsuredSet.size() == 0) {
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mLPContSchema.getContNo());
			tempLCInsuredSet = tLCInsuredDB.query();
			i = 2 * tempLCInsuredSet.size();
		}

		ListTable[] tPremListTable = new ListTable[i];

		boolean[] Flag = new boolean[i];
		SSRS temp1SSRS = new SSRS(); // 所有主险
		SSRS temp3SSRS = new SSRS(); // 该主险下的所有附加险

		SSRS temp2SSRS = new SSRS(); // 某一主险下的职业加费
		SSRS temp5SSRS = new SSRS(); // 某一主险下的健康加费

		SSRS temp4SSRS = new SSRS(); // 某一附加险下的职业加费
		SSRS temp6SSRS = new SSRS(); // 某一附加险下的健康加费

		SSRS tempASSRS = new SSRS(); // 备用
		SSRS tempBSSRS = new SSRS(); // 备用

		int number, j;
		int x = 0;
		String[][] strIW = new String[20][5];

		if (temp9SSRS.getMaxRow() == 0) { // 该合同号下--没有联生险。
			// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
			// VVVVVVVVVV--------被保险人部分(多被保人情况)-------START------VVVVVVVVVVVVVVVVVVV
			// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
			for (number = 0, j = 0; number < i / 2; number++, j = j + 2) {
				if (tempLPInsuredSet.size() != 0) {
					// 第一被保人
					LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
					mLPInsuredSchema = tempLPInsuredSet.get(number + 1)
							.getSchema();
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					tSql = "select PolNo , riskcode from LPPol where ContNo = '"
							+ "?ContNo?"
							+ "' and EdorNo = '"
							+ "?EdorNo?"
							+ "' and EdorType = '"
							+ "?EdorType?"
							+ "' and InsuredNo = '"
							+ "?InsuredNo?"
							+ "'and PolNo = MainPolNo";
					sqlbv5.sql(tSql);
					sqlbv5.put("ContNo", mLPContSchema.getContNo());
					sqlbv5.put("EdorNo", mLPContSchema.getEdorNo());
					sqlbv5.put("EdorType", mLPContSchema.getEdorType());
					sqlbv5.put("InsuredNo", mLPInsuredSchema.getInsuredNo());
					temp1SSRS = tempExeSQL.execSQL(sqlbv5);
				} else {
					// 第一被保人
					LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
					mLCInsuredSchema = tempLCInsuredSet.get(number + 1)
							.getSchema();
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					tSql = "select PolNo , riskcode from LCPol where ContNo = '"
							+ "?ContNo?"
							+ "' and InsuredNo = '"
							+ "?InsuredNo?"
							+ "'and PolNo = MainPolNo";
					sqlbv.sql(tSql);
					sqlbv.put("ContNo", mLPContSchema.getContNo());
					sqlbv.put("InsuredNo", mLCInsuredSchema.getInsuredNo());
					temp1SSRS = tempExeSQL.execSQL(sqlbv);
				}

				logger.debug("j=" + j);
				tPremListTable[j] = new ListTable();
				tPremListTable[j].setName("InsuredWork" + number);// 对应模版被保险人职业加费部分的行对象名

				tPremListTable[j + 1] = new ListTable();
				tPremListTable[j + 1].setName("InsuredHealth" + number); // 对应模版被保险人健康加费部分的行对象名

				for (int MRisk = 0; MRisk < temp1SSRS.getMaxRow(); MRisk++) {
					// 取第一主险
					// 2-职业加费 PayPlanType = 02
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					tSql = "select prem,payplanType from LPPrem where PolNo='"
							+ "?PolNo?"
							+ "' and EdorNo = '"
							+ "?EdorNo?"
							+ "' and EdorType = '"
							+ "?EdorType?"
							+ "'and PayPlanCode like '000000%'"
							+ " and AddFeeDirect = '02'"
							+ " and PayPlanType in('02','04') order by payplantype desc,payenddate desc";
					sqlbv6.sql(tSql);
					sqlbv6.put("PolNo", temp1SSRS.GetText(MRisk + 1, 1));
					sqlbv6.put("EdorNo", mLPContSchema.getEdorNo());
					sqlbv6.put("EdorType", mLPContSchema.getEdorType());
					temp2SSRS = tempExeSQL.execSQL(sqlbv6);
					if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
							&& temp2SSRS.getMaxCol() > 0) {
						if (!(temp2SSRS.GetText(1, 1).equals("0")
								|| temp2SSRS.GetText(1, 1).trim().equals("") || temp2SSRS
								.GetText(1, 1).equals("null"))) {
							Flag[j] = true;
							LMRiskDB tLMRiskDB = new LMRiskDB();
							tLMRiskDB.setRiskCode(temp1SSRS.GetText(MRisk + 1,
									2));
							logger.debug("RiskCode="
									+ temp1SSRS.GetText(MRisk + 1, 2));
							if (!tLMRiskDB.getInfo()) {
								CError tError = new CError();
								tError.moduleName = "EdorAddChargePrintBL";
								tError.functionName = "getBaseData";
								tError.errorMessage = "在取得LMRisk的数据时发生错误!";
								this.mErrors.addOneError(tError);
							}

							LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();
							int xh = MRisk + 1;
							logger.debug("x=" + x);
							strIW[x][0] = "险种" + xh + ":"; // 主险还是附加险
							strIW[x][1] = temp1SSRS.GetText(MRisk + 1, 2); // 险种代码
							strIW[x][2] = tLMRiskSchema.getRiskShortName(); // 险种名称
							strIW[x][3] = "￥"
									+ BqNameFun.getRound(temp2SSRS
											.GetText(1, 1)); // 职业加费金额
							strIW[x][4] = "";

							SumPrem = SumPrem
									+ Double.parseDouble(temp2SSRS
											.GetText(1, 1));
							tPremListTable[j].add(strIW[x]);
							x++;
						}
					}

					// 健康加费 PayPlanType = 01
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					tSql = "select prem,payplanType from LPPrem where PolNo='"
							+ "?PolNo?"
							+ "' and EdorNo = '"
							+ "?EdorNo?"
							+ "' and EdorType = '"
							+ "?EdorType?"
							+ "'and PayPlanCode like '000000%'"
							+ " and AddFeeDirect = '02' "
							+ " and PayPlanType in('01','03') order by payplantype desc,payenddate desc";
					sqlbv7.sql(tSql);
					sqlbv7.put("PolNo", temp1SSRS.GetText(MRisk + 1, 1));
					sqlbv7.put("EdorNo", mLPContSchema.getEdorNo());
					sqlbv7.put("EdorType", mLPContSchema.getEdorType());
					temp5SSRS = tempExeSQL.execSQL(sqlbv7);
					if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
							&& temp5SSRS.getMaxCol() > 0) {
						if (!(temp5SSRS.GetText(1, 1).equals("0")
								|| temp5SSRS.GetText(1, 1).trim().equals("") || temp5SSRS
								.GetText(1, 1).equals("null"))) {
							Flag[j + 1] = true;
							LMRiskDB tLMRiskDB = new LMRiskDB();
							tLMRiskDB.setRiskCode(temp1SSRS.GetText(MRisk + 1,
									2));
							if (!tLMRiskDB.getInfo()) {
								CError tError = new CError();
								tError.moduleName = "EdorAddChargePrintBL";
								tError.functionName = "getBaseData";
								tError.errorMessage = "在取得LMRisk的数据时发生错误!";
								this.mErrors.addOneError(tError);
							}
							LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();
							logger.debug(tLMRiskSchema.getRiskShortName());
							int xh = MRisk + 1;
							strIW[x][0] = "险种" + xh + ":"; // 主险还是附加险
							strIW[x][1] = temp1SSRS.GetText(MRisk + 1, 2); // 险种代码
							strIW[x][2] = tLMRiskSchema.getRiskShortName(); // 险种名称
							strIW[x][3] = "￥"
									+ BqNameFun.getRound(temp5SSRS
											.GetText(1, 1)); // 健康加费金额
							strIW[x][4] = "";

							logger.debug(temp5SSRS.GetText(1, 1));
							SumPrem = SumPrem
									+ Double.parseDouble(temp5SSRS
											.GetText(1, 1));
							tPremListTable[j + 1].add(strIW[x]);
							x++;
						}
					}

					// 查询此主险下的附加险
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					tSql = "select PolNo,riskcode from LPPol where MainPolNo='"
							+ "?MainPolNo?"
							+ "' and EdorNo = '" + "?EdorNo?"
							+ "' and EdorType = '"
							+ "?EdorType?"
							+ "'and PolNo<>MainPolNo";
					sqlbv8.sql(tSql);
					sqlbv8.put("MainPolNo", temp1SSRS.GetText(MRisk + 1, 1));
					sqlbv8.put("EdorNo", mLPContSchema.getEdorNo());
					sqlbv8.put("EdorType", mLPContSchema.getEdorType());
					temp3SSRS = tempExeSQL.execSQL(sqlbv8);

					for (int ARisk = 0; ARisk < temp3SSRS.getMaxRow(); ARisk++) {
						// 取第一附加险
						// 2-职业加费 PayPlanType = 02
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						tSql = "select sum(prem),payplanType from LPPrem where PolNo='"
								+ "?PolNo?"
								+ "' and EdorNo = '"
								+ "?EdorNo?"
								+ "' and EdorType = '"
								+ "?EdorType?"
								+ "'and PayPlanCode like '000000%'"
								+ " and AddFeeDirect = '02' "
								+ "and PayPlanType in('02','04') group by payplanType";
						sqlbv9.sql(tSql);
						sqlbv9.put("PolNo", temp3SSRS.GetText(ARisk + 1, 1));
						sqlbv9.put("EdorNo", mLPContSchema.getEdorNo());
						sqlbv9.put("EdorType", mLPContSchema.getEdorType());
						temp4SSRS = tempExeSQL.execSQL(sqlbv9);
						if (temp4SSRS != null && temp4SSRS.getMaxRow() > 0
								&& temp2SSRS.getMaxCol() > 0) {
							if (!(temp4SSRS.GetText(1, 1).equals("0")
									|| temp4SSRS.GetText(1, 1).trim()
											.equals("") || temp4SSRS.GetText(1,
									1).equals("null"))) {
								Flag[j] = true;
								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp3SSRS.GetText(
										ARisk + 1, 2));
								if (!tLMRiskDB.getInfo()) {
									CError tError = new CError();
									tError.moduleName = "EdorAddChargePrintBL";
									tError.functionName = "getBaseData";
									tError.errorMessage = "在取得LMRisk的数据时发生错误!";
									this.mErrors.addOneError(tError);
								}
								LMRiskSchema tLMRiskSchema = tLMRiskDB
										.getSchema();
								int xh = ARisk + 1 + temp1SSRS.getMaxRow();
								strIW[x][0] = "险种" + xh + ":"; // 主险还是附加险
								strIW[x][1] = temp3SSRS.GetText(ARisk + 1, 2); // 险种代码
								strIW[x][2] = tLMRiskSchema.getRiskShortName(); // 险种名称
								strIW[x][3] = "￥"
										+ BqNameFun.getRound(temp4SSRS.GetText(
												1, 1)); // 职业加费金额
								strIW[x][4] = "";

								SumPrem = SumPrem
										+ Double.parseDouble(temp4SSRS.GetText(
												1, 1));
								tPremListTable[j].add(strIW[x]);
								x++;
							}
						}
						SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
						// 健康加费
						tSql = "select sum(prem),payplanType from LPPrem where PolNo='"
								+ "?PolNo?"
								+ "' and EdorNo = '"
								+ "?EdorNo?"
								+ "' and EdorType = '"
								+ "?EdorType?"
								+ "'and PayPlanCode like '000000%'"
								+ " and AddFeeDirect = '02' "
								+ " and PayPlanType in('01','03') group by payplanType";
						sqlbv10.sql(tSql);
						sqlbv10.put("PolNo", temp3SSRS.GetText(ARisk + 1, 1));
						sqlbv10.put("EdorNo", mLPContSchema.getEdorNo());
						sqlbv10.put("EdorType", mLPContSchema.getEdorType());
						temp6SSRS = tempExeSQL.execSQL(sqlbv10);

						if (temp6SSRS != null && temp6SSRS.getMaxRow() > 0
								&& temp6SSRS.getMaxCol() > 0) {
							if (!(temp6SSRS.GetText(1, 1).equals("0")
									|| temp6SSRS.GetText(1, 1).trim()
											.equals("") || temp6SSRS.GetText(1,
									1).equals("null"))) {
								Flag[j + 1] = true;
								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp3SSRS.GetText(
										ARisk + 1, 2));
								if (!tLMRiskDB.getInfo()) {
									CError tError = new CError();
									tError.moduleName = "EdorAddChargePrintBL";
									tError.functionName = "getBaseData";
									tError.errorMessage = "在取得LMRisk的数据时发生错误!";
									this.mErrors.addOneError(tError);
								}

								LMRiskSchema tLMRiskSchema = tLMRiskDB
										.getSchema();
								int xh = ARisk + 1 + temp5SSRS.getMaxRow();
								strIW[x][0] = "险种" + xh + ":"; // 主险还是加险
								strIW[x][1] = temp3SSRS.GetText(ARisk + 1, 2); // 险种代码
								strIW[x][2] = tLMRiskSchema.getRiskShortName(); // 险种名称
								strIW[x][3] = "￥"
										+ BqNameFun.getRound(temp6SSRS.GetText(
												1, 1)); // 健康加费金额
								strIW[x][4] = "";

								SumPrem = SumPrem
										+ Double.parseDouble(temp6SSRS.GetText(
												1, 1));
								tPremListTable[j + 1].add(strIW[x]);
								x++;
							}
						}
					}
				}
			}
		}
		// end of “非联生险”。

		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVV--------被保险人部分(联生险情况)-------START------VVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// 第一被保险人职业加费
		// 主险
		else {
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType in ('02','04') group by a.riskcode,b.AddFeeDirect ";
			sqlbv11.sql(tSql);
			sqlbv11.put("polno", temp9SSRS.GetText(1, 1));
			sqlbv11.put("edorno", mLPContSchema.getEdorNo());
			sqlbv11.put("edortype", mLPContSchema.getEdorType());
			temp1SSRS = tempExeSQL.execSQL(sqlbv11);// 联生主险

			tPremListTable[0] = new ListTable();
			tPremListTable[0].setName("InsuredWork0");
			if (temp1SSRS != null && temp1SSRS.getMaxRow() > 0
					&& temp1SSRS.getMaxCol() > 0) {
				if (!(temp1SSRS.GetText(1, 2).equals("0")
						|| temp1SSRS.GetText(1, 2).trim().equals("") || temp1SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[0] = true;

					LMRiskDB t3LMRiskDB = new LMRiskDB();
					LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

					t3LMRiskDB.setRiskCode(temp1SSRS.GetText(1, 1));
					t3LMRiskSchema = t3LMRiskDB.getSchema();

					String[] strc = new String[5];
					strc[0] = "险种1:"; // 主险还是附加险
					strc[1] = temp1SSRS.GetText(1, 1); // 险种代码
					strc[2] = t3LMRiskSchema.getRiskShortName(); // 险种名称
					strc[3] = "￥" + BqNameFun.getRound(temp1SSRS.GetText(1, 2)); // 健康加费金额
					strc[4] = "";
					if (temp1SSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(temp1SSRS.GetText(1, 2));
					}
					tPremListTable[0].add(strc);
				}
			}
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			// 附加险
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.mainpolno='"
					+ "?mainpolno?"
					+ "' and a.polno <> a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.polno =a.polno"
					+ " and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType in ('02','04') group by a.riskcode,b.AddFeeDirect";
			sqlbv12.sql(tSql);
			sqlbv12.put("mainpolno", temp9SSRS.GetText(1, 1));
			sqlbv12.put("contno", mLPContSchema.getContNo());
			sqlbv12.put("edorno", mLPContSchema.getEdorNo());
			sqlbv12.put("edortype", mLPContSchema.getEdorType());
			temp2SSRS = tempExeSQL.execSQL(sqlbv12);// 该主险下的所有附加险
			if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
					&& temp2SSRS.getMaxCol() > 0) {
				String[][] strd = new String[20][5];
				for (int f = 0; f < temp2SSRS.getMaxRow(); f++) {
					if (!(temp2SSRS.GetText(f + 1, 2).equals("0")
							|| temp2SSRS.GetText(f + 1, 2).trim().equals("") || temp2SSRS
							.GetText(f + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp2SSRS.GetText(f + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();
						int xh = f + 1 + temp1SSRS.getMaxRow();
						strd[f][0] = "险种" + xh + ":"; // 主险还是附加险
						strd[f][1] = temp2SSRS.GetText(f + 1, 1); // 险种代码
						strd[f][2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
						strd[f][3] = "￥"
								+ BqNameFun.getRound(temp2SSRS
										.GetText(f + 1, 2)); // 健康加费金额
						strd[f][4] = "";
						if (temp2SSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(temp2SSRS.GetText(
											f + 1, 2));
						}
						tPremListTable[0].add(strd[f]);
					}
				}
			}
			// 第一被保险人健康加费
			// 主险
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType in ('01','03') group by a.riskcode,b.AddFeeDirect ";
			sqlbv13.sql(tSql);
			sqlbv13.put("polno", temp9SSRS.GetText(1, 1));
			sqlbv13.put("edorno", mLPContSchema.getEdorNo());
			sqlbv13.put("edortype", mLPContSchema.getEdorType());
			temp3SSRS = tempExeSQL.execSQL(sqlbv13);// 联生主险

			tPremListTable[1] = new ListTable();
			tPremListTable[1].setName("InsuredHealth0");
			if (temp3SSRS != null && temp3SSRS.getMaxRow() > 0
					&& temp3SSRS.getMaxCol() > 0) {
				if (!(temp3SSRS.GetText(1, 2).equals("0")
						|| temp3SSRS.GetText(1, 2).trim().equals("") || temp3SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[1] = true;

					LMRiskDB t4LMRiskDB = new LMRiskDB();
					LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

					t4LMRiskDB.setRiskCode(temp3SSRS.GetText(1, 1));
					t4LMRiskSchema = t4LMRiskDB.getSchema();

					String[] stre = new String[5];
					stre[0] = "险种1:"; // 主险还是附加险
					stre[1] = temp3SSRS.GetText(1, 1); // 险种代码
					stre[2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
					stre[3] = "￥" + temp3SSRS.GetText(1, 2); // 健康加费金额
					stre[4] = "";
					if (temp3SSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(temp3SSRS.GetText(1, 2));
					}
					tPremListTable[1].add(stre);
				}
			}
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			// 附加险
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.mainpolno='"
					+ "?mainpolno?"
					+ "' and a.polno <> a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.polno =a.polno"
					+ " and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType in ('01','03') group by a.riskcode,b.AddFeeDirect";
			sqlbv14.sql(tSql);
			sqlbv14.put("mainpolno", temp9SSRS.GetText(1, 1));
			sqlbv14.put("contno", mLPContSchema.getContNo());
			sqlbv14.put("edorno", mLPContSchema.getEdorNo());
			sqlbv14.put("edortype", mLPContSchema.getEdorType());
			temp4SSRS = tempExeSQL.execSQL(sqlbv14);// 该主险下的所有附加险
			if (temp4SSRS != null && temp4SSRS.getMaxRow() > 0
					&& temp4SSRS.getMaxCol() > 0) {
				String[][] strf = new String[20][5];
				for (int g = 0; g < temp4SSRS.getMaxRow(); g++) {
					if (!(temp4SSRS.GetText(g + 1, 2).equals("0")
							|| temp4SSRS.GetText(g + 1, 2).trim().equals("") || temp4SSRS
							.GetText(g + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp4SSRS.GetText(g + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();
						int xh = g + 1 + temp3SSRS.getMaxRow();
						strf[g][0] = "险种" + xh + ":"; // 主险还是附加险
						strf[g][1] = temp4SSRS.GetText(g + 1, 1); // 险种代码
						strf[g][2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
						strf[g][3] = "￥"
								+ BqNameFun.getRound(temp4SSRS
										.GetText(g + 1, 2)); // 健康加费金额
						strf[g][4] = "";
						if (temp4SSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(temp4SSRS.GetText(
											g + 1, 2));
						}
						tPremListTable[1].add(strf[g]);
					}
				}
			}

			// 第二被保险人职业加费
			// 主险
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno =concat('?polno?','?contno?')"
					+ " and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType in ('02','04') group by a.riskcode,b.AddFeeDirect ";
			sqlbv15.sql(tSql);
			sqlbv15.put("polno", temp9SSRS.GetText(1, 1));
			sqlbv15.put("contno", mLPContSchema.getContNo());
			sqlbv15.put("edorno", mLPContSchema.getEdorNo());
			sqlbv15.put("edortype", mLPContSchema.getEdorType());
			temp5SSRS = tempExeSQL.execSQL(sqlbv15);// 联生主险
			tPremListTable[2] = new ListTable();
			tPremListTable[2].setName("InsuredWork1");
			if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
					&& temp5SSRS.getMaxCol() > 0) {
				if (!(temp5SSRS.GetText(1, 2).equals("0")
						|| temp5SSRS.GetText(1, 2).trim().equals("") || temp5SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[2] = true;

					LMRiskDB t3LMRiskDB = new LMRiskDB();
					LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

					t3LMRiskDB.setRiskCode(temp5SSRS.GetText(1, 1));
					t3LMRiskSchema = t3LMRiskDB.getSchema();

					String[] strg = new String[5];
					strg[0] = "险种1:"; // 主险还是附加险
					strg[1] = temp5SSRS.GetText(1, 1); // 险种代码
					strg[2] = t3LMRiskSchema.getRiskShortName(); // 险种名称
					strg[3] = "￥" + BqNameFun.getRound(temp5SSRS.GetText(1, 2)); // 健康加费金额
					strg[4] = "";
					if (temp5SSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(temp5SSRS.GetText(1, 2));
					}
					tPremListTable[2].add(strg);
				}
			}
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			// 附加险
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.mainpolno='"
					+ "?mainpolno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.polno =a.polno"
					+ " and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType in ('02','04') group by a.riskcode,b.AddFeeDirect";
			sqlbv16.sql(tSql);
			sqlbv16.put("mainpolno", temp9SSRS.GetText(1, 1));
			sqlbv16.put("contno", mLPContSchema.getContNo());
			sqlbv16.put("edorno", mLPContSchema.getEdorNo());
			sqlbv16.put("edortype", mLPContSchema.getEdorType());
			temp6SSRS = tempExeSQL.execSQL(sqlbv16);// 该主险下的所有附加险
			if (temp6SSRS != null && temp6SSRS.getMaxRow() > 0
					&& temp6SSRS.getMaxCol() > 0) {
				String[][] strh = new String[20][5];
				for (int p = 0; p < temp6SSRS.getMaxRow(); p++) {
					if (!(temp6SSRS.GetText(p + 1, 2).equals("0")
							|| temp6SSRS.GetText(p + 1, 2).trim().equals("") || temp6SSRS
							.GetText(p + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp6SSRS.GetText(p + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();
						int xh = p + 1 + temp5SSRS.getMaxRow();
						strh[p][0] = "险种" + xh + ":"; // 主险还是附加险
						strh[p][1] = temp6SSRS.GetText(p + 1, 1); // 险种代码
						strh[p][2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
						strh[p][3] = "￥"
								+ BqNameFun.getRound(temp6SSRS
										.GetText(p + 1, 2)); // 健康加费金额
						strh[p][4] = "";
						if (temp6SSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(temp6SSRS.GetText(
											p + 1, 2));
						}
						tPremListTable[2].add(strh[p]);
					}
				}
			}

			// 第二被保险人健康加费
			// 主险
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType in ('01','03') group by a.riskcode,b.AddFeeDirect ";
			sqlbv17.sql(tSql);
			sqlbv17.put("polno", temp9SSRS.GetText(1, 1));
			sqlbv17.put("edorno", mLPContSchema.getEdorNo());
			sqlbv17.put("edortype", mLPContSchema.getEdorType());
			tempASSRS = tempExeSQL.execSQL(sqlbv17); // 联生主险
			tPremListTable[3] = new ListTable();
			tPremListTable[3].setName("InsuredHealth1");
			if (tempASSRS != null && tempASSRS.getMaxRow() > 0
					&& tempASSRS.getMaxCol() > 0) {
				if (!(tempASSRS.GetText(1, 2).equals("0")
						|| tempASSRS.GetText(1, 2).trim().equals("") || tempASSRS
						.GetText(1, 2).equals("null"))) {
					Flag[3] = true;

					LMRiskDB t4LMRiskDB = new LMRiskDB();
					LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

					t4LMRiskDB.setRiskCode(tempASSRS.GetText(1, 1));
					t4LMRiskSchema = t4LMRiskDB.getSchema();

					String[] stri = new String[5];
					stri[0] = "险种1:"; // 主险还是附加险
					stri[1] = tempASSRS.GetText(1, 1); // 险种代码
					stri[2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
					stri[3] = "￥" + BqNameFun.getRound(tempASSRS.GetText(1, 2)); // 健康加费金额
					stri[4] = "";
					if (tempASSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(tempASSRS.GetText(1, 2));
					}
					tPremListTable[3].add(stri);
				}
			}
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			// 附加险
			tSql = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lppol a,lpprem b where a.mainpolno='"
					+ "?mainpolno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' and b.edorno = '"
					+ "?edorno?"
					+ "' and b.edortype = '"
					+ "?edortype?"
					+ "' and b.polno =a.polno"
					+ " and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType in ('01','03') group by a.riskcode,b.AddFeeDirect";
			sqlbv18.sql(tSql);
			sqlbv18.put("mainpolno", temp9SSRS.GetText(1, 1));
			sqlbv18.put("contno", mLPContSchema.getContNo());
			sqlbv18.put("edorno", mLPContSchema.getEdorNo());
			sqlbv18.put("edortype", mLPContSchema.getEdorType());
			tempBSSRS = tempExeSQL.execSQL(sqlbv18); // 该主险下的所有附加险
			if (tempBSSRS != null && tempBSSRS.getMaxRow() > 0
					&& tempBSSRS.getMaxCol() > 0) {
				String[][] strj = new String[20][5];
				for (int h = 0; h < tempBSSRS.getMaxRow(); h++) {
					if (!(tempBSSRS.GetText(h + 1, 2).equals("0")
							|| tempBSSRS.GetText(h + 1, 2).trim().equals("") || tempBSSRS
							.GetText(h + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(tempBSSRS.GetText(h + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();
						int xh = h + 1 + tempASSRS.getMaxRow();
						strj[h][0] = "险种" + xh + ":"; // 主险还是附加险
						strj[h][1] = tempBSSRS.GetText(h + 1, 1); // 险种代码
						strj[h][2] = t4LMRiskSchema.getRiskShortName(); // 险种名称
						strj[h][3] = "￥"
								+ BqNameFun.getRound(tempBSSRS
										.GetText(h + 1, 2)); // 健康加费金额
						strj[h][4] = "";
						if (tempBSSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(tempBSSRS.GetText(
											h + 1, 2));
						}
						tPremListTable[3].add(strj[h]);
					}
				}
			}
		}
		String name1 = "";
		String name2 = "";
		for (int k = 0, t = 0; k < i / 2; k++, t = t + 2) {
			if (Flag[t] == true) {
				name1 = "displayInsWork" + String.valueOf(k);
				tXmlExport.addDisplayControl(name1);
				tXmlExport.addListTable(tPremListTable[t], Title);
			}
			if (Flag[t + 1] == true) {
				name2 = "displayInsHealth" + String.valueOf(k);
				tXmlExport.addDisplayControl(name2);
				tXmlExport.addListTable(tPremListTable[t + 1], Title);
			}
		}

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		EdorAddChargePrintBL tEdorAddChargePrintBL = new EdorAddChargePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "810000000045160";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);

		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tEdorAddChargePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
