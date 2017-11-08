package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.db.RnewIssuePolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.RnewIndUWMasterSet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author lh
 * @version 1.0
 */

public class RnewNoticeF1PBL implements PrintService {
private static Logger logger = Logger.getLogger(RnewNoticeF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	private String ReasonInfo1 = "您无需补交保险费！";
	private String lys_Flag = "0";
	private String lys_Flag_main = "0";
	private String lys_Flag_ab = "0";
	// private String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	// 业务处理相关变量
	/** 全局数据 */
	
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String mOperate = "";
	private String mRealPayMoney = "";
	private String strPolNo = ""; // 合同号
	private MMap map = new MMap();
	private VData mInputData = new VData();
	private String mPrtSeq="";

	public RnewNoticeF1PBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
					&& !cOperate.equals("PRINT2")&&!cOperate.equals("PRINTdis")
					&& !cOperate.equals("PRINTwai")&&!cOperate.equals("PRINTepi")
					&& !cOperate.equals("PRINTbre")&&!cOperate.equals("PRINTche")
					&& !cOperate.equals("PRINTtum")&&!cOperate.equals("PRINTbab")
					&& !cOperate.equals("PRINTabr")&&!cOperate.equals("PRINTpar")
					&& !cOperate.equals("PRINTpar")&&!cOperate.equals("PRINTaf1")
					&& !cOperate.equals("PRINTaf2")&&!cOperate.equals("PRINTali")
					&& !cOperate.equals("PRINTcsi")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();
				// tongmeng 2007-10-18 modify
				// 增加业务员问题件的打印和发放.
				// 修改打印核保通知书逻辑
				// 1-查询打印管理表
				LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

				tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
				if (tLOPRTManagerDB.getInfo() == false) {
					mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					throw new Exception("在取得打印队列中数据时发生错误");
				}
				mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
				// message！
				// 查询问题件表
				/*
				 * String tsql = "select BackObjType from RnewIssuePol where
				 * NeedPrint = 'Y' " + " and PrtSeq = '" +
				 * mLOPRTManagerSchema.getOldPrtSeq() + "'"; ExeSQL tExeSQL =
				 * new ExeSQL(); String tBackObjType = ""; tBackObjType =
				 * tExeSQL.getOneValue(tsql);
				 * logger.debug("@@tBackObjType:" + tBackObjType);
				 */
				if (mLOPRTManagerSchema.getCode().equals("45")) 
				{
					// 准备所有要打印的数据
					// 发放给客户的问题件
					logger.debug("打印核保通知书");
					// getPrintData();
					getPrintData_HB();
				}

				else if (mLOPRTManagerSchema.getCode().equals("14")) {
					// BackObjType ='3'
					// 发放给业务员的问题件
					//
					logger.debug("打印发放给业务员的问题件");
					getPrintData_agent();
				} else {
					buildError("submitData", "打印数据有问题.");
					return false;
				}

			}
			if (cOperate.equals("PRINT2")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData1();
				// UpdateData(); //更新数据库write by yaory

			}
			if (cOperate.equals("PRINTdis"))
			{
				mResult.clear();
				//// 准备所有要打印的数据
				getPrintData_dis(); //add by liuqh2008-11-21 
			}
			if (cOperate.equals("PRINTwai")){
				mResult.clear();
                // 准备所有要打印的数据
				getPrintData_wai();
			}
			if (cOperate.equals("PRINTepi")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_epi();
			}
			if (cOperate.equals("PRINTabr")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_abr();
			}
			if (cOperate.equals("PRINTaf1")){
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_af1();
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		double sumprem = 0;
		String mSumPrem = "";
		double dif = 0;
		boolean PremRate = false; // 打印加费部分的判断标志
		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("RateFault.vts", "printer"); // 最好紧接着就初始化xml文档

		LCContDB tLCGrpContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

		tLCGrpContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		LCContSet tempLCContSet = tLCGrpContDB.query();

		if (tempLCContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCGrpContDB.setSchema(tempLCContSet.get(1));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCGrpContDB.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在查询代理人信息时出错");
		}
		mLAAgentSchema = tLAAgentDB.getSchema();

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
			throw new Exception("在查询代理机构时出错");
		}
		String strPolNo = tLCGrpContDB.getContNo();
		// 查询暂缴费表,查询缴费金额
		String tStrSQL = "select sum(paymoney) from ljtempfee where "
				+ " tempfeetype in ('0','6','1') and otherno ='"
				+ "?strPolNo?"
				+ "' "
				+ " and (enteraccdate is not null and enteraccdate<>'3000-01-01') and confdate is null";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tStrSQL);
		sqlbv1.put("strPolNo", strPolNo);
		mRealPayMoney = tExeSQL.getOneValue(sqlbv1);
		if (mRealPayMoney.equals(null) || mRealPayMoney.equals("0")
				|| mRealPayMoney.equals("")) {
			mRealPayMoney = "0.00";
		}

		logger.debug("实际交的金额:" + mRealPayMoney);

		tStrSQL = "select BankAccNo from LJTempFeeClass where "
				+ " OtherNo  = '" + "?strPolNo?" + "' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tStrSQL);
		sqlbv2.put("strPolNo", strPolNo);
		SSRS MMSSRS = new SSRS();
		MMSSRS = tExeSQL.execSQL(sqlbv2);
		String BankNo = "";
		if (MMSSRS != null && MMSSRS.getMaxRow() > 0) {
			BankNo = MMSSRS.GetText(1, 1);
		}

		String q_sql = "";
		q_sql = "select IssueCont,IssueType from RnewIssuePol where BackObjType = '2'   and NeedPrint = 'Y' and PrtSeq  = '"
				+ "?PrtSeq?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(q_sql);
		sqlbv3.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
		logger.debug("问题件中所执行的sql是" + q_sql);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv3);

		if ((q_ssrs.GetText(1, 1).equals("0")
				|| q_ssrs.GetText(1, 1).trim().equals("") || q_ssrs.GetText(1,
				1).equals("null"))) {
			logger.debug("没有问题件记录");
		} else {
			logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");

		}

		// ListTable tlistTable = new ListTable();
		String IssueCont = "";

		// tlistTable.setName("QUESTION");
		for (int i = 1; i <= q_ssrs.getMaxRow(); i++) {
			IssueCont = IssueCont + i + "." + q_ssrs.GetText(i, 1) + "\n";
			if (q_ssrs.GetText(i, 2).equals("01")
					|| q_ssrs.GetText(i, 2).equals("02")
					|| q_ssrs.GetText(i, 2).equals("03")) {
				PremRate = true;
			}
		}

		// 当费率错误时，查询保费保额等信息

		String[] RiskTitle = new String[5];

		RiskTitle[0] = "Type";
		RiskTitle[1] = "RiskCode";
		RiskTitle[2] = "RiskName";
		RiskTitle[3] = "Prem";
		RiskTitle[4] = "Amnt";

		ListTable tRiskListTable = new ListTable();
		String strRisk[] = null;
		tRiskListTable.setName("MAIN");

		ListTable tFeeListTable = new ListTable();
		String strFee[] = null;
		tFeeListTable.setName("FEE");

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(strPolNo);

		tLCPolSet = tLCPolDB.query();
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			strRisk = new String[5];
			if (tLCPolSet.get(j).getPolNo().equals(
					tLCPolSet.get(j).getMainPolNo())) {
				strRisk[0] = "险种1";
				strRisk[1] = tLCPolSet.get(j).getRiskCode();
				strRisk[2] = getRiskName(tLCPolSet.get(j).getRiskCode());
				strRisk[3] = mDecimalFormat.format(new Double(tLCPolSet.get(j)
						.getAmnt()));
				strRisk[4] = "￥ "
						+ mDecimalFormat.format(new Double(tLCPolSet.get(j)
								.getPrem()));
				sumprem = sumprem + tLCPolSet.get(j).getPrem();
				mSumPrem = "￥ " + mDecimalFormat.format(sumprem);
				tRiskListTable.add(strRisk);
			}
		}
		String subriskno = "";
		int sriskno = 2;
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			strRisk = new String[5];
			if (!tLCPolSet.get(j).getPolNo().equals(
					tLCPolSet.get(j).getMainPolNo())) {
				subriskno = sriskno + "";
				strRisk[0] = "险种" + subriskno;
				strRisk[1] = tLCPolSet.get(j).getRiskCode();
				strRisk[2] = getRiskName(tLCPolSet.get(j).getRiskCode());
				strRisk[3] = mDecimalFormat.format(new Double(tLCPolSet.get(j)
						.getAmnt()));
				strRisk[4] = "￥ "
						+ mDecimalFormat.format(new Double(tLCPolSet.get(j)
								.getPrem()));
				sumprem = sumprem + tLCPolSet.get(j).getPrem();
				mSumPrem = "￥ " + mDecimalFormat.format(sumprem);
				tRiskListTable.add(strRisk);
				sriskno = sriskno + 1;
			}
		}

		String SQL = "select (select (case subriskflag when 'M' then '险种1' when 'S' then '险种' end) from lmriskapp a where a.riskcode = b.riskcode), b.riskcode,b.paymoney from LJTempFee b where "
				+ " (TempFeeType in ('0','6','1')and otherno in (select contno from lccont where ContNo='"
				+ "?strPolNo?" + "'))";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(SQL);
		sqlbv4.put("strPolNo", strPolNo);
		SSRS CNMSSRS = new SSRS();
		CNMSSRS = q_exesql.execSQL(sqlbv4);

		for (int j = 1; j <= CNMSSRS.getMaxRow(); j++) {
			if (CNMSSRS.GetText(j, 1).equals("险种1")) {
				strFee = new String[4];
				strFee[0] = "险种1";
				strFee[1] = CNMSSRS.GetText(j, 2);
				strFee[2] = getRiskName(CNMSSRS.GetText(j, 2));
				// strFee[3] = CNMSSRS.GetText(j, 3);
				strFee[3] = "￥ "
						+ mDecimalFormat.format(new Double(CNMSSRS
								.GetText(j, 3)));
				tFeeListTable.add(strFee);
			}
		}
		sriskno = 2;
		for (int j = 1; j <= CNMSSRS.getMaxRow(); j++) {
			if (CNMSSRS.GetText(j, 1).equals("险种")) {
				strFee = new String[4];
				subriskno = sriskno + "";
				strFee[0] = CNMSSRS.GetText(j, 1) + subriskno;
				strFee[1] = CNMSSRS.GetText(j, 2);
				strFee[2] = getRiskName(CNMSSRS.GetText(j, 2));
				// strFee[3] = CNMSSRS.GetText(j, 3) + "元";
				strFee[3] = "￥ "
						+ mDecimalFormat.format(new Double(CNMSSRS
								.GetText(j, 3)));
				tFeeListTable.add(strFee);
				sriskno = sriskno + 1;
			}
		}

		TextTag texttag = new TextTag();

		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		tStrSQL = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tStrSQL);
		sqlbv5.put("contno", tLCGrpContDB.getContNo());
		SSRS PolSSRS = new SSRS();
		PolSSRS = tExeSQL.execSQL(sqlbv5);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错！");
			return;
		}

		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(tLCGrpContDB.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("BankNo", mLAComSchema.getName()); // 代理机构
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		String Period = "";
		String tSql = "select payintv,payyears from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("contno", tLCGrpContDB.getContNo());
		SSRS PeriodSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		PeriodSSRS = tempExeSQL.execSQL(sqlbv6);

		xmlExport.addDisplayControl("displayfee");
		if (PeriodSSRS.GetText(1, 1).equals("0")) {
			Period = "趸交";
		} else {
			Period = PeriodSSRS.GetText(1, 2) + "年";
		}
		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部 Sumprem : 取用标准的格式：0.00
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = tLCGrpContDB.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		// String mSumprem = "";

		texttag.add("BarCode1", "UN002");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.AppntName", tLCGrpContDB.getAppntName());
		texttag.add("AppntName", tLCGrpContDB.getAppntName());
		texttag.add("LCCont.InsuredName", tLCGrpContDB.getInsuredName());
		texttag.add("PrtNo", tLCGrpContDB.getPrtNo());
		texttag.add("LCContl.Operator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("LCCont.ProposalContNo", tLCGrpContDB.getContNo());

		texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB.getAgentCode()));
		texttag.add("LCCont.InsuredName", tLCGrpContDB.getInsuredName());
		texttag.add("LAAgent.AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageComName", tManageComName);
		texttag.add("LABranchGroup.Name", getComName(tLCGrpContDB
				.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("Bank", getBankName(tLCGrpContDB.getBankCode()));
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("Operator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("BankAccount", tLCGrpContDB.getBankAccNo());
		texttag.add("Period", Period);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("SysDate", df.format(new Date()));
		texttag.add("IssueCont", IssueCont);

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		// xmlExport.addListTable(tlistTable, strArr);
		if (PremRate == true) {
			xmlExport.addDisplayControl("displayMAIN"); // 模版上的加费部分的控制标记
			xmlExport.addListTable(tRiskListTable, RiskTitle); // 保存加费信息
			xmlExport.addListTable(tFeeListTable, RiskTitle);
			logger.debug("SumPrem=" + tLCGrpContDB.getPrem());
			TextTag ttexttag = new TextTag();
			// mSumprem = mDecimalFormat.format(sumprem);

			ttexttag.add("SumPrem", mSumPrem);
			double Sumprem = tLCGrpContDB.getPrem();
			String tRealPayMoney = "￥ "
					+ mDecimalFormat.format(new Double(mRealPayMoney));
			ttexttag.add("PaidPrem", tRealPayMoney);
			if (mRealPayMoney.equals(null) || (mRealPayMoney).trim().equals("")
					|| mRealPayMoney.equals("0")) {
				ttexttag.add("Dif", "需缴保费: " + mSumPrem);
				dif = sumprem;
			} else {
				sumprem = sumprem - Double.parseDouble(mRealPayMoney);
				if (sumprem >= 0) {
					String mSumprem = "需缴保费: " + "￥ "
							+ mDecimalFormat.format(sumprem);
					ttexttag.add("Dif", mSumprem);
					dif = sumprem;
				} else {
					String mSumprem = "需退保费: " + "￥ "
							+ mDecimalFormat.format(-sumprem);
					ttexttag.add("Dif", mSumprem);
					dif = sumprem;
				}
			}
			if (ttexttag.size() > 0) {
				xmlExport.addTextTag(ttexttag);
			}
		}

		if (dif > 0) {
			xmlExport.addDisplayControl("displayType"); // 模版上的加费部分的控制标记
		}
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	private void getPrintData_agent() throws Exception {
		XmlExport xmlExport = new XmlExport();// 新建一个XmlExport的实例
		xmlExport.createDocument("RnewNotice.vts", "");// 最好紧接着就初始化xml文档

		LCContDB tLCGrpContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();// get all message！

		tLCGrpContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		LCContSet tempLCContSet = tLCGrpContDB.query();

		if (tempLCContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCGrpContDB.setSchema(tempLCContSet.get(1));

		String strContNo = tLCGrpContDB.getContNo();
		RnewIssuePolDB tRnewIssuePolDB = new RnewIssuePolDB();
		// tRnewIssuePolDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
		// tRnewIssuePolDB.setBackObjType("3");
		// tRnewIssuePolDB.setNeedPrint("Y");
		// tRnewIssuePolDB.setContNo(strContNo);
		RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();
		String tSQL = "";
		String InputOperator = tLCGrpContDB.getInputOperator();//录入员		
		String UWOperator = tLCGrpContDB.getUWOperator();//核保员
		String ApproveOperator = tLCGrpContDB.getApproveCode();//复核员
		
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tSQL = "select * from es_doc_main where doccode='"+"?doccode?"+"'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSQL);
		sqlbv7.put("doccode", tLCGrpContDB.getPrtNo());
		tES_DOC_MAINSet.set(tES_DOC_MAINDB.executeQuery(sqlbv7));
		if (tES_DOC_MAINDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tES_DOC_MAINDB.mErrors);
			throw new Exception("在取得扫描员数据时发生错误");
		}
		String ScanOperator = "无";
		if(tES_DOC_MAINSet.size()>0)
		    ScanOperator = tES_DOC_MAINSet.get(1).getScanOperator();//扫描员
		
		tSQL = "select * from RnewIssuePol where BackObjType = '3' and NeedPrint = 'Y' and state in ('0','1') "
				+ " and contno='" + "?strContNo?" + "' ";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSQL);
		sqlbv8.put("strContNo", strContNo);
		tRnewIssuePolSet.set(tRnewIssuePolDB.executeQuery(sqlbv8));
		if (tRnewIssuePolDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tRnewIssuePolDB.mErrors);
			throw new Exception("在取得问题件中数据时发生错误");
		}
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("QUESTION");
		String content = "";
		for (int i = 1; i <= tRnewIssuePolSet.size(); i++) {
			//strArr = new String[1];
			//strArr[0] = i + "." + tRnewIssuePolSet.get(i).getIssueCont();			
			//tlistTable.add(strArr);
			content= i + "." + tRnewIssuePolSet.get(i).getIssueCont();	
			getContent(tlistTable, content, 40);
		}
		strArr = new String[1];
		strArr[0] = "QUESTION";

		TextTag texttag = new TextTag();

		texttag.add("AppntName", tLCGrpContDB.getAppntName());
		texttag.add("PrtNo", tLCGrpContDB.getPrtNo());
		texttag.add("AgentName", getAgentName(tLCGrpContDB.getAgentCode()));
		texttag.add("AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCGrpContDB.getManageCom()));
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("CurrentDay", df.format(new Date()));
		texttag.add("InputOperator", InputOperator);
		texttag.add("UWOperator", UWOperator);
		texttag.add("ScanOperator", ScanOperator);
		texttag.add("ApproveOperator", ApproveOperator);

		if (texttag.size() > 0)
			xmlExport.addTextTag(texttag);
		xmlExport.addListTable(tlistTable, strArr);
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	private void getPrintData1() throws Exception {

		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("GrpRateFault.vts", "printer"); // 最好紧接着就初始化xml文档

		String mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		logger.debug("=============" + mLOPRTManagerSchema.getPrtSeq());
		String temprtseq = mLOPRTManagerSchema.getPrtSeq();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误!");
		}
		// 需要判断是否已经打印？！
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all
			// message！

			// 打印时传入的是主险投保单的投保单号
			tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
			logger.debug("test by yaory:"
					+ mLOPRTManagerSchema.getOtherNo()); // 合同号
			String no = mLOPRTManagerSchema.getOtherNo();
			if (!tLCGrpContDB.getInfo()) {
				mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				throw new Exception("在获取保单信息时出错!!");
			}
			LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
			mLCGrpContSchema = tLCGrpContDB.getSchema();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("getprintData", "无效的打印状态");
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("getprintData", "该打印请求不是在请求状态");
			}

			// 投保人地址和邮编
			LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
			logger.debug("号码======" + no);
			tLCGrpAppntDB.setGrpContNo(no);
			if (tLCGrpAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误;;;");
			}

			String mAgentCode = mLCGrpContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误!!!");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				// throw new Exception("在取得打印队列中数据时发生错误!!!!");

			}

			strPolNo = tLCGrpContDB.getGrpContNo();
			// logger.debug("测试 by
			// yaory"+mLOPRTManagerSchema.getPrtSeq());
			String q_sql = "";
			q_sql = "select IssueCont from LCGrpIssuePol where grpcontno='"
					+ "?strPolNo?" + "' and PrtSeq='"
					+ "?PrtSeq?"
					+ "' and NeedPrint = 'Y' and replyresult is null"; // Gaoht
			// modify
			// 新契约打印不走工作流，只打印未回复问题
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(q_sql);
			sqlbv9.put("strPolNo", strPolNo);
			sqlbv9.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
			ExeSQL q_exesql = new ExeSQL();
			logger.debug("SQL------>" + q_sql);
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv9);
			String strArr[] = null;
			ListTable tlistTable = new ListTable();
			if (q_ssrs != null && q_ssrs.getMaxRow() > 0
					&& q_ssrs.getMaxCol() > 0) {
				if ((q_ssrs.GetText(1, 1).equals("0")
						|| q_ssrs.GetText(1, 1).trim().equals("") || q_ssrs
						.GetText(1, 1).equals("null"))) {
					logger.debug("没有问题件记录");
				} else {
					logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");

				}

				tlistTable.setName("QUESTION");
				for (int i = 1; i <= q_ssrs.getMaxRow(); i++) {
					strArr = new String[1];

					strArr[0] = i + "." + q_ssrs.GetText(i, 1);
					tlistTable.add(strArr);
				}
			}

			else {

				buildError("getprintData", "没有问题件记录！");
				return;

			}

			TextTag texttag = new TextTag();
			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部 Sumprem : 取用标准的格式：0.00
			 * ==========================================================================
			 */
			// 中支机构名称
			String tManageComCode = tLCGrpContDB.getManageCom();
			if (tLCGrpContDB.getManageCom().length() > 6) {
				tManageComCode = tLCGrpContDB.getManageCom().substring(0, 6);
			}

			String tManageComName = getComName(tManageComCode);

			texttag.add("BarCode1", "UN061");
			texttag
					.add(
							"BarCodeParam1",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
			texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
			texttag
					.add(
							"BarCodeParam2",
							"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

			texttag.add("AppntName", GetAppntname(tLCGrpContDB.getGrpContNo()));
			texttag.add("LCCont.AppntName", GetAppntname(tLCGrpContDB
					.getGrpContNo()));
			texttag.add("LCCont.ProposalContNo", tLCGrpContDB.getGrpContNo());
			texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB
					.getAgentCode()));
			texttag.add("LAAgent.AgentCode", tLCGrpContDB.getAgentCode());
			texttag.add("ManageComName", tManageComName);
			texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
			texttag.add("Operator", mLOPRTManagerSchema.getReqOperator());
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			texttag.add("SysDate", df.format(new Date()));

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			xmlExport.addListTable(tlistTable, strArr);
			// xmlExport.outputDocumentToFile("e:\\", "t2HZM");
			mResult.clear();
			mResult.addElement(xmlExport);
		}
	}

	// tongmeng 2007-11-12 modify
	// 修改,使用5.3的核保通知书打印逻辑
	private boolean getPrintData_HB() {
		LCPolSet gLCPolSet = new LCPolSet();
		// 根据印刷号查询打印队列中的纪录

		ExeSQL tExeSQL = new ExeSQL();
		
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");

			return false;
		}
		//zy 2009-05-15 回复日期
		//首先取出投保单生效日期
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(" select distinct(cvalidate) from lcpol where contno='"+"?contno?"+"' and appflag='9' ");
		sqlbv10.put("contno", mLOPRTManagerSchema.getOtherNo());
		String tCvalidate = tExeSQL.getOneValue(sqlbv10);
		String senddate = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
		logger.debug("投保单生效日:"+tCvalidate);
		logger.debug("通知书发送日+10天:"+senddate);
		String replyDate = "";
		if(tCvalidate.compareTo(senddate)<0)
		{
			 replyDate = tCvalidate;
		}
		else
		{
			replyDate = senddate;
		}
		
		String ReplyYear = replyDate.substring(0,4);
		String ReplyMon =  replyDate.substring(5,7);
		String ReplyDay =  replyDate.substring(8,10);
		
		//查询工作流数据等
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		if(mLOPRTManagerSchema.getCode().equals("45"))
//			tLWMissionDB.setActivityID("0000007004");
//		tLWMissionDB.setProcessID("0000000007");
//		tLWMissionDB.setMissionProp2(mLOPRTManagerSchema.getOtherNo());
//		tLWMissionDB.setMissionProp3(PrtSeq);
//		LWMissionSet tLWMissionSet = tLWMissionDB.query();
//		if(tLWMissionSet!=null && tLWMissionSet.size()<1) {
//			mErrors.copyAllErrors(tLWMissionDB.mErrors);
//			CError.buildErr(this, "在取得工作流表数据时发生错误");
//
//			return false;
//		}
//		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);	
		String tContNo = mLOPRTManagerSchema.getOtherNo();	
		LCContDB tLCContDB = new LCContDB();
		String strSQL = "SELECT * FROM LCCont WHERE ContNo = '"
				+ "?tContNo?" + "'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(strSQL);
		sqlbv11.put("tContNo", tContNo);
		LCContSet tempLCContSet = tLCContDB.executeQuery(sqlbv11);

		if (tempLCContSet==null || tempLCContSet.size() == 0) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "在LPCont表中找不到相关信息");
			return false;
		}

//		int m;
//		int i;
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = tempLCContSet.get(1);

		String tAgentCode = tLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode);

		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			CError.buildErr(this, "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		
//		*************************被保人信息**************************
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema = tLCInsuredDB.getSchema(); // 保存被保人信息
		tLCInsuredDB.setContNo(tLCContSchema.getContNo());
		tLCInsuredDB.setInsuredNo(tLCContSchema.getInsuredNo());	
		if (!tLCInsuredDB.getInfo()) {
			mErrors.copyAllErrors(tLCInsuredDB.mErrors);
			CError.buildErr(this, "在取得LCInsured的数据时发生错误");	
			return false;
		}	
		tLCInsuredSchema = tLCInsuredDB.getSchema(); // 保存被保人信息
		
		boolean SpecFlag = false;		
		boolean ChangePolFlag = false;
		boolean IssuePolFlag = false;
		boolean Reason = false;//承保计划变更和加费原因
		String content = "";
		
		//是否有特约
		String[] SpecTitle = new String[1];
		SpecTitle[0] = "SpecInfo";      
        
		ListTable tSpecListTable = new ListTable();
		tSpecListTable.setName("SPECINFO"); // 对应模版特别约定部分的行对象名
		String tForSpec = "select speccontent from lccspec where  contno='"+"?tContNo?"+"' and needprint='Y'"
						+ " AND TRIM(SpecContent) IS NOT NULL"
						+ " and prtflag ='0' "						
						+ " ORDER BY ModifyDate, ModifyTime DESC";;
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tForSpec);
		sqlbv12.put("tContNo", tContNo);
		SSRS tSSRSForSpec = new SSRS();
		tSSRSForSpec = tExeSQL.execSQL(sqlbv12);
		logger.debug("查询特约SQL:" + tForSpec);
		SSRS yssrs = new SSRS();
		ExeSQL yExeSQL = new ExeSQL();
		yssrs = yExeSQL.execSQL(sqlbv12);

		if (yssrs.getMaxRow() == 0) {
			SpecFlag = false;
		} else {
			int n=0;
			for(int j=1;j<=yssrs.getMaxRow();j++)
			if (!((yssrs.GetText(j, 1).equals("")) || (yssrs.GetText(j, 1) == null))) {
				SpecFlag = true;//特约标志
				n++;
//				content = n +"、" + yssrs.GetText(j, 1);
//				getContent(tSpecListTable, content ,50);
				//zy 2009-05-16 调整显示方式
				if(yssrs.getMaxRow()==1)
					content=yssrs.GetText(j, 1);
				else
					content = n +"." + yssrs.GetText(j, 1);
				getContent(tSpecListTable, content ,50);
					
			}
		}
		logger.debug("是否有特约标志**********    "+SpecFlag);
		
        //是否有问题件
		String[] IssueTitle = new String[1];
		IssueTitle[0] = "IssueInfo";      
        
		ListTable tIssueListTable = new ListTable();
		tIssueListTable.setName("Issue"); // 对应模版特别约定部分的行对象名
		String tForIssue = "select issuecont from Rnewissuepol where  contno='"+"?tContNo?"+"' and needprint='Y'"
						+ " and Backobjtype='2' AND TRIM(issuecont) IS NOT NULL"	
						+ " and state='0' "
						+ " ORDER BY ModifyDate, ModifyTime DESC";;
						SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
						sqlbv13.sql(tForIssue);
						sqlbv13.put("tContNo", tContNo);	
		SSRS tSSRSForIssue = new SSRS();
		tSSRSForIssue = tExeSQL.execSQL(sqlbv13);
		logger.debug("查询问题件SQL:" + tForIssue);
		if (tSSRSForIssue.getMaxRow() == 0) {
			IssuePolFlag = false;
		} else {
			int n=0;
			for(int j=1;j<=tSSRSForIssue.getMaxRow();j++)
			if (!((tSSRSForIssue.GetText(j, 1).equals("")) || (tSSRSForIssue.GetText(j, 1) == null))) {
				IssuePolFlag = true;//特约标志
				n++;
//				content = n +"、" + tSSRSForIssue.GetText(j, 1);
//				getContent(tIssueListTable, content ,50);
				//zy 2009-05-16 调整显示方式
				if(tSSRSForIssue.getMaxRow()==1)
					content=tSSRSForIssue.GetText(j, 1);
				else
					content = n +"." + tSSRSForIssue.GetText(j, 1);
				
				getContent(tIssueListTable, content ,50);
			}
		}
		logger.debug("是否有问题件标志**********    "+IssuePolFlag);
		
		
		ListTable tChangeListTable = new ListTable();
		tChangeListTable.setName("CHANGE"); // 对应模版问题部分的行对象名		
		String[] ChangeTitle = new String[1];
		ChangeTitle[0]= "Change";
		
		//是否有承保计划变更和加费
		// 是否有承保计划变更
		String strSql = "select count(1) from LCUWMaster where contno='" + "?tContNo?" + "' and uwtype='1' and ChangePolFlag='1' ";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(strSql);
		sqlbv14.put("tContNo", tContNo);
		tExeSQL = new ExeSQL();
		int rs_change = Integer.parseInt(tExeSQL.getOneValue(sqlbv14));
		if (rs_change > 0) {		    
			ChangePolFlag = true;
		}	

		// 是否有加费
		//二核加费只能对附加险加费，所以加上appflag='9'
		strSql = "select count(a.polno) from lcprem a ,lcpol b where a.polno=b.polno and b.appflag='9' and a.contno='"+ "?tContNo?"
						+ "' and payplancode like '000000%%' ";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(strSql);
		sqlbv15.put("tContNo", tContNo);
		tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv15));
		if (rs_addfee > 0) {		    
			ChangePolFlag = true;
		}
		logger.debug("是否有承保计划变更标志**********    "+ChangePolFlag);
		if(ChangePolFlag == true)
		{
			//取承保计划变更和加费原因
			RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
			tRnewIndUWMasterDB.setContNo(tLCContSchema.getContNo());
			tRnewIndUWMasterDB.setInsuredNo(tLCContSchema.getInsuredNo());
			tRnewIndUWMasterDB.setUWType("1");
			RnewIndUWMasterSet tRnewIndUWMasterSet = tRnewIndUWMasterDB.query();
			if (tRnewIndUWMasterSet == null || tRnewIndUWMasterSet.size()<1) {
				mErrors.copyAllErrors(tRnewIndUWMasterDB.mErrors);
				CError.buildErr(this, "在取得RnewIndUWMaster的数据时发生错误");
				return false;
			}
			RnewIndUWMasterSchema tRnewIndUWMasterSchema = tRnewIndUWMasterSet.get(1);
			if(tRnewIndUWMasterSchema.getUWIdea()!=null && !tRnewIndUWMasterSchema.getUWIdea().equals(""))
			{
				Reason = true;
				content = "  " + tRnewIndUWMasterSchema.getUWIdea();//取变更和加费原因
				getContent(tChangeListTable, content ,50);
			}
		}
		logger.debug("是否有承保计划变更和加费原因**********    "+ChangePolFlag);				
		//************************end***********************

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();		

		// 1-险种信息：
		logger.debug("***************开始查询本次保全前的保单信息***********************");
		ListTable tRiskListTable = new ListTable();
//		String[] RiskInfoTitle = new String[9];
//		String[] RiskInfo = new String[9];
		String[] RiskInfoTitle = new String[8];
		String[] RiskInfo = new String[8];
		tRiskListTable.setName("MAIN"); // 对应模版投保信息部分的行对象名
		RiskInfoTitle[0] = "RiskName"; // 险种名称
		RiskInfoTitle[1] = "Amnt"; // 保险金额
		RiskInfoTitle[2] = "PayYears"; // 缴费年期
		RiskInfoTitle[3] = "PayIntv"; // 缴费方式（间隔）
		RiskInfoTitle[4] = "Prem"; // 保费
		RiskInfoTitle[5] = "JobAddPrem"; // 职业加费
		RiskInfoTitle[6] = "HealthAddPrem"; // 健康加费
//		RiskInfoTitle[7] = "LiveAddPrem"; // 居住地加费
//		RiskInfoTitle[8] = "HobbyAddPrem"; // 爱好加费
		//zy 2009-05-16 将居住地加费、爱好加费调整为其他交费
		RiskInfoTitle[7] = "LiveAddPrem"; // 其他交费
		
		double oldSumPrem = 0.00; // 合计保费
		double oldSumJobAddFee = 0.00; // 合计加费       
		double oldSumLiveAddFee = 0.00;
		double oldSumHobbyAddFee = 0.00;
		double oldSumHealthAddFee = 0.00; // 最后的合计金额	
		LMRiskDB tLMRiskDB;
		String sTemp;
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS tempSSRS = new SSRS();
		
			String Sql = "select * from LCPol where contno='"
			+ "?tContNo?" + "' " 
			+ " and appflag='1' order by mainpolno,polno  ";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(Sql);
			sqlbv16.put("tContNo", tContNo);
			LCPolDB tempLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema_sub = new LCPolSchema();
			LCPolSet tLCPolSet_sub = tempLCPolDB.executeQuery(sqlbv16);
		
			if (tLCPolSet_sub != null) {
				gLCPolSet.set(tLCPolSet_sub); // 保存附加险保单集合,后用
		
				for (int n = 1; n <= gLCPolSet.size(); n++) {
					tLCPolSchema_sub = gLCPolSet.get(n);
//					RiskInfo = new String[9];
					RiskInfo = new String[8];
					tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLCPolSchema_sub.getRiskCode());
		
					if (!tLMRiskDB.getInfo()) {
						mErrors.copyAllErrors(tLMRiskDB.mErrors);
						CError.buildErr(this, "在取得LMRisk的数据时发生错误");		
						return false;
					}
		
					RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
					RiskInfo[1] = mDecimalFormat.format(tLCPolSchema_sub.getAmnt()); // 保险金额
		
					if ((tLCPolSchema_sub.getPayEndYear() == 1000)
							&& tLCPolSchema_sub.getPayEndYearFlag().equals("A")) {
						RiskInfo[2] = "终生"; // 交费年期
					} else {
						RiskInfo[2] = (new Integer(tLCPolSchema_sub.getPayYears()))
								.toString(); // 交费年期
					}
		
					sTemp = "";
		
					if (tLCPolSchema_sub.getPayIntv() == -1) {
						sTemp = "不定期交费";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 0) {
						sTemp = "趸交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 1) {
						sTemp = "月交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 3) {
						sTemp = "季交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 6) {
						sTemp = "半年交";
					}
		
					if (tLCPolSchema_sub.getPayIntv() == 12) {
						sTemp = "年交";
					}
		
					RiskInfo[3] = sTemp; // 交费方式
					RiskInfo[4] = mDecimalFormat.format(tLCPolSchema_sub
							.getStandPrem()); // 保费				
					oldSumPrem = oldSumPrem + tLCPolSchema_sub.getStandPrem(); // 原保费合计
		
					// 险种的职业加费
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype='02'");
					sqlbv17.put("PolNo", tLCPolSchema_sub.getPolNo());
					sqlbv17.put("date1", tLCPolSchema_sub.getPaytoDate());
					sqlbv17.put("date2", tLCPolSchema_sub.getPaytoDate());
					tempSSRS = tempExeSQL.execSQL(sqlbv17);
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计职业加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[5] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumJobAddFee = oldSumJobAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[5] = "0.00";
						}
					}
					
					// 险种的健康加费
					SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
					sqlbv18.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype='01'");
					sqlbv18.put("PolNo", tLCPolSchema_sub.getPolNo());
					sqlbv18.put("date1", tLCPolSchema_sub.getPaytoDate());
					sqlbv18.put("date2", tLCPolSchema_sub.getPaytoDate());
					tempSSRS = tempExeSQL.execSQL(sqlbv18);
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {								
							logger.debug("累计健康加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[6] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumHealthAddFee = oldSumHealthAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[6] = "0.00";
						}
					}
					
					// 险种的居住地加费
		/*			tempSSRS = tempExeSQL
							.execSQL("select nvl(sum(Prem),0) from LCPrem where PolNo='"
									+ tLCPolSchema_sub.getPolNo()
									+ "' and PayPlanCode like '000000%' " 
									+" and paystartdate<='"+tLCPolSchema_sub.getPaytoDate()+"'  and payenddate>='"+tLCPolSchema_sub.getPaytoDate()+"'"
									+" and payplantype='03'");
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计居住地加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[7] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumLiveAddFee = oldSumLiveAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[7] = "0.00";
						}
					}
					
					// 险种的爱好加费
					tempSSRS = tempExeSQL
							.execSQL("select nvl(sum(Prem),0) from LCPrem where PolNo='"
									+ tLCPolSchema_sub.getPolNo()
									+ "' and PayPlanCode like '000000%' " 
									+" and paystartdate<='"+tLCPolSchema_sub.getPaytoDate()+"'  and payenddate>='"+tLCPolSchema_sub.getPaytoDate()+"'"
									+" and payplantype='04'");
		
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("累计爱好加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[8] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumHobbyAddFee = oldSumHobbyAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[8] = "0.00";
						}
					} 
					*/
					//zy 2009-05-16 其他加费
					SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
					sqlbv19.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype not in ('01','02')");
					sqlbv19.put("PolNo", tLCPolSchema_sub.getPolNo());
					sqlbv19.put("date1", tLCPolSchema_sub.getPaytoDate());
					sqlbv19.put("date2", tLCPolSchema_sub.getPaytoDate());
					tempSSRS = tempExeSQL.execSQL(sqlbv19);
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							logger.debug("其他加费是"
									+ tempSSRS.GetText(1, 1));
							RiskInfo[7] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							oldSumLiveAddFee = oldSumLiveAddFee
									+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							RiskInfo[7] = "0.00";
						}
					}
					
					tRiskListTable.add(RiskInfo); // 加入险种信息
				}
			}
		
			oldSumPrem = Double.parseDouble(mDecimalFormat.format(oldSumPrem)); // 转换计算后的保费(规定的精度)		
			oldSumHealthAddFee = Double.parseDouble(mDecimalFormat.format(oldSumHealthAddFee)); // 转换计算后的保费(规定的精度)
			oldSumJobAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumJobAddFee));
			oldSumLiveAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumLiveAddFee));
			oldSumHobbyAddFee = Double.parseDouble(mDecimalFormat
					.format(oldSumHobbyAddFee));
			
			double SumPrem = 0.00;
			double SumJobAddFee = 0.00;
			double SumLiveAddFee = 0.00;
			double SumHobbyAddFee = 0.00;
			double SumHealthAddFee = 0.00; // 最后的合计金额
			//zy 2009-05-16 调整后的保费合计
			double AvgPrem=0.00;
			ListTable tChangePolListTable = new ListTable();
//			String[] ChangePol = new String[9];
//			String[] ChangePolTitle = new String[9];
			String[] ChangePol = new String[8];
			String[] ChangePolTitle = new String[8];

			ChangePolTitle[0] = "RiskName"; // 险种名称
			ChangePolTitle[1] = "Amnt"; // 保险金额
			ChangePolTitle[2] = "PayYears"; // 缴费年期
			ChangePolTitle[3] = "PayIntv"; // 缴费方式（间隔）
			ChangePolTitle[4] = "Prem"; // 保费
			ChangePolTitle[5] = "JobAddPrem"; // 职业加费
			ChangePolTitle[6] = "HealthAddPrem"; // 健康加费
			ChangePolTitle[7] = "LivePrem"; // 居住地加费 其他交费
//			ChangePolTitle[8] = "HobbyPrem"; // 爱好加费
			tChangePolListTable.setName("ChangePol"); // 对应模版加费部分的行对象名
		if(ChangePolFlag ==true)
		{
			// 4-现在的保单信息
			// 上面补充的保险计划变更已经查询
		//	String AddFeeReason = tLCCUWMasterDB.getAddPremReason(); // 得到加费原因,后用

			logger.debug("***************开始查询现在的保单信息***********************");						
			
				Sql = "select * from LCPol where contno='"
				+ "?tContNo?" + "' and ((appflag='1' and payintv>0) or(appflag='9' and payintv=0 and rnewflag='-1' and uwflag<>'1')) order by appflag,mainpolno,polno ";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(Sql);
				sqlbv20.put("tContNo", tContNo);
				LCPolDB tempLCPolDB2 = new LCPolDB();
				LCPolSchema tLCPolSchema_sub2 = new LCPolSchema();
				LCPolSet tLCPolSet_sub2 = tempLCPolDB2.executeQuery(sqlbv20);
			
				LCPolSet gLCPolSet2 = new LCPolSet();
				if (tLCPolSet_sub2 != null) {
					gLCPolSet2.set(tLCPolSet_sub2); // 保存附加险保单集合,后用
			
					for (int n = 1; n <= gLCPolSet2.size(); n++) {
						tLCPolSchema_sub2 = gLCPolSet2.get(n);
//						ChangePol = new String[9];
						ChangePol = new String[8];
						tLMRiskDB = new LMRiskDB();
						tLMRiskDB.setRiskCode(tLCPolSchema_sub2.getRiskCode());
			
						if (!tLMRiskDB.getInfo()) {
							mErrors.copyAllErrors(tLMRiskDB.mErrors);
							CError.buildErr(this, "在取得LMRisk的数据时发生错误");
			
							return false;
						}
			
						ChangePol[0] = tLMRiskDB.getRiskName(); // 险种名称
						ChangePol[1] = mDecimalFormat.format(tLCPolSchema_sub2.getAmnt()); // 保险金额
			
						if ((tLCPolSchema_sub2.getPayEndYear() == 1000)
								&& tLCPolSchema_sub2.getPayEndYearFlag().equals("A")) {
							ChangePol[2] = "终生"; // 交费年期
						} else {
							ChangePol[2] = (new Integer(tLCPolSchema_sub2.getPayYears()))
									.toString(); // 交费年期
						}
			
						sTemp = "";
			
						if (tLCPolSchema_sub2.getPayIntv() == -1) {
							sTemp = "不定期交费";
						}
			
						if (tLCPolSchema_sub2.getPayIntv() == 0) {
							sTemp = "趸交";
						}
			
						if (tLCPolSchema_sub2.getPayIntv() == 1) {
							sTemp = "月交";
						}
			
						if (tLCPolSchema_sub2.getPayIntv() == 3) {
							sTemp = "季交";
						}
			
						if (tLCPolSchema_sub2.getPayIntv() == 6) {
							sTemp = "半年交";
						}
			
						if (tLCPolSchema_sub2.getPayIntv() == 12) {
							sTemp = "年交";
						}
			
						ChangePol[3] = sTemp; // 交费方式
						ChangePol[4] = mDecimalFormat.format(tLCPolSchema_sub2
								.getStandPrem()); // 保费				
						SumPrem = SumPrem + tLCPolSchema_sub2.getStandPrem(); // 原保费合计
			
						// 险种的职业加费
						SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
						sqlbv21.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype='02'");
						sqlbv21.put("PolNo", tLCPolSchema_sub2.getPolNo());
						sqlbv21.put("date1", tLCPolSchema_sub2.getPaytoDate());
						sqlbv21.put("date2", tLCPolSchema_sub.getPaytoDate());
						tempSSRS = tempExeSQL.execSQL(sqlbv21);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计职业加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[5] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumJobAddFee = SumJobAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[5] = "0.00";
							}
						}
						
						// 险种的健康加费
						SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
						sqlbv22.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where  PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype='01'");
						sqlbv22.put("PolNo", tLCPolSchema_sub2.getPolNo());
						sqlbv22.put("date1", tLCPolSchema_sub2.getPaytoDate());
						sqlbv22.put("date2", tLCPolSchema_sub.getPaytoDate());
						tempSSRS = tempExeSQL.execSQL(sqlbv22);
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {								
								logger.debug("累计健康加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[6] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumHealthAddFee = SumHealthAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[6] = "0.00";
							}
						}
						
						// 险种的居住地加费
/*						tempSSRS = tempExeSQL
								.execSQL("select nvl(sum(Prem),0) from LCPrem where  PolNo='"
										+ tLCPolSchema_sub2.getPolNo()
										+ "' and PayPlanCode like '000000%' " 
									+" and paystartdate<='"+tLCPolSchema_sub2.getPaytoDate()+"'  and payenddate>='"+tLCPolSchema_sub.getPaytoDate()+"'"
									+" and payplantype='03'");
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计居住地加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[7] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumLiveAddFee = SumLiveAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[7] = "0.00";
							}
						}
						
						// 险种的爱好加费
						tempSSRS = tempExeSQL
								.execSQL("select nvl(sum(Prem),0) from LCPrem where  PolNo='"
										+ tLCPolSchema_sub2.getPolNo()
										+ "' and PayPlanCode like '000000%' " 
									+" and paystartdate<='"+tLCPolSchema_sub2.getPaytoDate()+"'  and payenddate>='"+tLCPolSchema_sub.getPaytoDate()+"'"
									+" and payplantype='04'");
			
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("累计爱好加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[8] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumHobbyAddFee = SumHobbyAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[8] = "0.00";
							}
						}
						*/
						//zy 2009-05-16 其他加费
						SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
						sqlbv23.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where  PolNo='"
								+ "?PolNo?"
								+ "' and PayPlanCode like '000000%' " 
							+" and paystartdate<='"+"?date1?"+"'  and payenddate>='"+"?date2?"+"'"
							+" and payplantype not in ('01','02') ");
						sqlbv23.put("PolNo", tLCPolSchema_sub2.getPolNo());
						sqlbv23.put("date1", tLCPolSchema_sub2.getPaytoDate());
						sqlbv23.put("date2", tLCPolSchema_sub.getPaytoDate());
						tempSSRS = tempExeSQL.execSQL(sqlbv23);
	
						if (tempSSRS.MaxCol > 0) {
							if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
									.GetText(1, 1).trim().equals(""))) {
								logger.debug("其他加费是"
										+ tempSSRS.GetText(1, 1));
								ChangePol[7] = mDecimalFormat.format(Double
										.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
								SumLiveAddFee = SumLiveAddFee
										+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
							} else {
								ChangePol[7] = "0.00";
							}
						}
						
						tChangePolListTable.add(ChangePol); // 加入险种信息
					}
				}
			
				SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)		
				SumHealthAddFee = Double.parseDouble(mDecimalFormat.format(SumHealthAddFee)); // 转换计算后的保费(规定的精度)
				SumJobAddFee = Double.parseDouble(mDecimalFormat
						.format(SumJobAddFee));
				SumLiveAddFee = Double.parseDouble(mDecimalFormat
						.format(SumLiveAddFee));
				SumHobbyAddFee = Double.parseDouble(mDecimalFormat
						.format(SumHobbyAddFee));
				AvgPrem = Double.parseDouble(mDecimalFormat
						.format(SumPrem +SumHealthAddFee+SumJobAddFee+SumLiveAddFee));
		}
//******************************准备打印需要的数据********************************************
//********************************************************************************************
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("RnewNUWNotice.vts", "printer"); // 最好紧接着就初始化xml文档

		// 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";

		// 模版自上而下的元素
		logger.debug("mLOPRTManagerSchema code:"
				+ mLOPRTManagerSchema.getCode());	
		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("AppntName", tLCContSchema.getAppntName()); // 投保人名称		
		texttag.add("ContNo", tLCContSchema.getContNo()); // 印刷号
		texttag.add("SysDate", SysDate);		
		texttag.add("xCvalidate", tLCContSchema.getPaytoDate()); // 投保人名称		
		String date = tLCContSchema.getCValiDate();
		date = date + "-";
		String ldate = StrTool.decodeStr(date, "-", 1) + "年"
		+ StrTool.decodeStr(date, "-", 2) + "月"
		+ StrTool.decodeStr(date, "-", 3) + "日";
		texttag.add("Date", ldate);
		texttag.add("InsuredName", tLCInsuredSchema.getName()); // 被保人名称
		texttag.add("InsuredIDNo", tLCInsuredSchema.getIDNo()); // 被保人号码		
		xmlexport.addDisplayControl("displayold"); // 显示投保险种信息
		xmlexport.addListTable(tRiskListTable, RiskInfoTitle); 
		texttag.add("oldSumPrem", mDecimalFormat.format(oldSumPrem)); // 合计保费
		texttag.add("oldSumJobAddFee", mDecimalFormat.format(oldSumJobAddFee)); // 合计职业加费
		texttag.add("oldSumHealthAddFee", mDecimalFormat.format(oldSumHealthAddFee)); // 合计健康加费
		texttag.add("oldSumLiveAddFee", mDecimalFormat.format(oldSumLiveAddFee));
		texttag.add("oldSumHobbyAddFee", mDecimalFormat.format(oldSumHobbyAddFee));
		// zy 2009-05-16 增加回复日期
		texttag.add("ReplyYear", ReplyYear);
		texttag.add("ReplyMon", ReplyMon);
		texttag.add("ReplyDay", ReplyDay);
		if(SpecFlag == true||ChangePolFlag == true)
		{
		   xmlexport.addDisplayControl("display");
		   xmlexport.addDisplayControl("display2");
		   xmlexport.addDisplayControl("displaySure");
		}
		if(SpecFlag == true)
		{
			xmlexport.addDisplayControl("displayspec"); // 显示特约信息
			xmlexport.addListTable(tSpecListTable, SpecTitle); 
		}		
		if(IssuePolFlag == true)
		{
			xmlexport.addDisplayControl("displayIssue"); // 显示特约信息
			xmlexport.addListTable(tIssueListTable, IssueTitle); 
		}	
		
		if(ChangePolFlag == true)
		{
			if(Reason == true)
			{
				xmlexport.addDisplayControl("displaychangepolreason"); // 显示承保计划变更原因信息
			    xmlexport.addListTable(tChangeListTable, ChangeTitle); 
			}
			xmlexport.addDisplayControl("displaychangepol"); // 显示承保计划变更信息
			xmlexport.addListTable(tChangePolListTable, ChangePolTitle); 
			texttag.add("SumPrem", mDecimalFormat.format(SumPrem)); // 合计保费
			texttag.add("SumJobAddFee", mDecimalFormat.format(SumJobAddFee)); // 合计职业加费
			texttag.add("SumHealthAddFee", mDecimalFormat.format(SumHealthAddFee)); // 合计健康加费
			texttag.add("SumLiveAddFee", mDecimalFormat.format(SumLiveAddFee));
			texttag.add("SumHobbyAddFee", mDecimalFormat.format(SumHobbyAddFee));
			//zy 2009-05-16
			texttag.add("SumAddFee", mDecimalFormat.format(SumPrem+SumJobAddFee+SumHealthAddFee+SumLiveAddFee-oldSumPrem-oldSumJobAddFee)); // 补交保险金额
			//zy 2009-05-09 平均每期保费
			texttag.add("AvgPrem", mDecimalFormat.format(AvgPrem));
		}		
		
		texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", tLCContSchema.getAgentCode()); // 代理人业务号
		texttag.add("ManageCom", tLCContSchema.getManageCom()); // 营业机构
		texttag.add("PrtSeq", PrtSeq); // 流水号
		// 核保师代码 核保<--核保师代码
		ExeSQL xxExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		String tSql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = " select operator from LCUWMaster where contno='" + "?tContNo?" + "' and uwtype='1' and rownum=1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = " select operator from LCUWMaster where contno='" + "?tContNo?" + "' and uwtype='1' limit 0,1 ";
		}
		sqlbv24.sql(tSql);
		sqlbv24.put("tContNo", tContNo);
		String xxOperator=xxExeSQL.getOneValue(sqlbv24);
		texttag.add("UWCode", xxOperator);
		String phone = "";
		if(mLAAgentSchema.getPhone()!=null && !mLAAgentSchema.getPhone().equals(""))
		    phone = mLAAgentSchema.getPhone();
		else
			phone = mLAAgentSchema.getMobile();
		texttag.add("Phone", phone);		

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}
	
	  //问卷的打印 add by liuqh 2008-11-21  残疾问卷
	private void getPrintData_dis() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";

			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "DisabilityData.vts";
			logger.debug("测试");

			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例

			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}
	//问卷 add by liuqh 2008-11-21  腰椎疾病问卷
	private void getPrintData_wai() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "WaistData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	//问卷 add by liuqh 2008-11-21  癫痫病问卷
	private void getPrintData_epi() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "EpilepsyData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	
	//问卷 add by liuqh 2008-11-21  出国人员问卷
	private void getPrintData_abr() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "AbroadData.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}
	//问卷 add by liuqh 2008-11-21  财务情况补充问卷（甲）
	private void getPrintData_af1() throws Exception{
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String remark = "";
			
			String[] MeetTitle = new String[2];
			MeetTitle[0] = "ID";
			MeetTitle[1] = "ITEM";
			ListTable tListTable = new ListTable();
			String str[] = null;
			tListTable.setName("WJ"); // 对应模版体检部分的行对象名
			
			remark = "Addfinance1Data.vts";
			logger.debug("测试");
			
			// ////////////////////end write by yaory-2007-7-4-16:39////
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			
			// xmlExport.createDocument("Meet.vts", ""); //最好紧接着就初始化xml文档
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
			+ StrTool.getDay() + "日";
			
			//testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);
			
		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");
			
		}
	}

	// 下面是一些辅助函数

	/**
	 * 根据印刷号码查询扫描员
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private String getScaner(String tPrtNO) {
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(tPrtNO);

		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tES_DOC_MAINSet = tES_DOC_MAINDB.query();

		if (tES_DOC_MAINSet == null) {
			buildError("getScaner", "没有找到扫描员！");

			return null;
		}

		if (tES_DOC_MAINSet.size() == 0) {
			buildError("getScaner", "没有找到扫描员！");

			return null;
		}

		ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
		tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);

		return tES_DOC_MAINSchema.getScanOperator();
	}
	
	// 下面是一些辅助函数

	/**
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content ,int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr ;
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
			nSpecReasonLen -= nMaxCharsInOneLine;
			
			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
		}

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}

	private String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得主险LMRisk的数据时发生错误");
		}

		return tLMRiskDB.getRiskShortName();
	}

	private String GetAppntname(String no) {
		String name = "";
		String q_sql = "";
		q_sql = "select name from lcgrpappnt where grpcontno='" + "?no?" + "'";
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(q_sql);
		sqlbv25.put("no", no);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv25);
		if (q_ssrs.MaxRow > 0) {
			name = q_ssrs.GetText(1, 1);
		}
		return name;
	}

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			// throw new Exception("在取得LDCom的数据时发生错误");
		}
		return tLDComDB.getShortName();
	}

	private String getBankName(String strBankCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strBankCode);
		tLDCodeDB.setCodeType("bank");
		if (tLDCodeDB.getInfo()) {
			return tLDCodeDB.getCodeName();
		} else {
			return tLDCodeDB.getCodeName();
		}
	}

	private void UpdateData() {
		map.put("update LCGrpIssuePol set needprint='N' where grpcontno='"
				+ strPolNo + "' and issuetype not in ('99','15')", "UPDATE");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");
		mInputData.clear();
		mInputData.add(map);
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);

		}

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

	private void jbInit() throws Exception {
	}

}
