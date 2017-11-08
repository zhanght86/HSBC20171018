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
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPolOriginalDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
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
import com.sinosoft.lis.schema.LCPolOriginalSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolOriginalSet;
import com.sinosoft.lis.vschema.LCPolSet;
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
import com.sinosoft.utility.XmlExportNew;

public class OperatorNoticeF1PBL implements PrintService {
	private static Logger logger = Logger.getLogger(OperatorNoticeF1PBL.class);

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

	private String mPrtSeq = "";

	private String mQOperate[] = null;

	public OperatorNoticeF1PBL() {
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
		// 问卷传入两个信息
		if (cOperate.indexOf("-") != -1) {
			mQOperate = cOperate.split("-");
			cOperate = mQOperate[0];
			logger.debug("问卷的模板是：" + mQOperate[1]);
		}
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
					&& !cOperate.equals("PRINT2")
					&& !cOperate.equals("PRINTdis")
					&& !cOperate.equals("PRINTwai")
					&& !cOperate.equals("PRINTepi")
					&& !cOperate.equals("PRINTbre")
					&& !cOperate.equals("PRINTche")
					&& !cOperate.equals("PRINTtum")
					&& !cOperate.equals("PRINTbab")
					&& !cOperate.equals("PRINTabr")
					&& !cOperate.equals("PRINTpar")
					&& !cOperate.equals("PRINTpar")
					&& !cOperate.equals("PRINTaf1")
					&& !cOperate.equals("PRINTaf2")
					&& !cOperate.equals("PRINTali")
					&& !cOperate.equals("PRINTcsi")
					&& !cOperate.equals("PRINTd1")
					&& !cOperate.equals("PRINTd2")
					&& !cOperate.equals("PRINTd3")
					&& !cOperate.equals("PRINTd4")
					&& !cOperate.equals("PRINTd5")
					&& !cOperate.equals("PRINTd6")
					&& !cOperate.equals("PRINTd7")
					&& !cOperate.equals("PRINTd8")
					&& !cOperate.equals("PRINTd9")
					&& !cOperate.equals("PRINTd10")
					&& !cOperate.equals("PRINTd11")
					&& !cOperate.equals("PRINTHQ")// 能够表示所有健康问卷
					&& !cOperate.equals("PRINTFQ")// 能够表示所有财务问卷
					&& !cOperate.equals("PRINTOQ")// 能够表示所有职业问卷
			) {
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
				 * String tsql = "select BackObjType from LCIssuePol where
				 * NeedPrint = 'Y' " + " and PrtSeq = '" +
				 * mLOPRTManagerSchema.getOldPrtSeq() + "'"; ExeSQL tExeSQL =
				 * new ExeSQL(); String tBackObjType = ""; tBackObjType =
				 * tExeSQL.getOneValue(tsql); logger.debug("@@tBackObjType:" +
				 * tBackObjType);
				 */
				if (mLOPRTManagerSchema.getCode().equals("TB89")
						|| mLOPRTManagerSchema.getCode().equals("TB90")) {
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
			// 问卷打印
			// add by liuqh 2009-04-02
			if (cOperate.equals("PRINTHQ") || cOperate.equals("PRINTOQ")
					|| cOperate.equals("PRINTFQ")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData_QuestionNaire();
				// UpdateData(); //更新数据库write by yaory

			}
			if (cOperate.equals("PRINT2")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData1();
				// UpdateData(); //更新数据库write by yaory

			}
			if (cOperate.equals("PRINTdis")) {
				mResult.clear();
				// // 准备所有要打印的数据
				getPrintData_dis(); // add by liuqh2008-11-21
			}
			if (cOperate.equals("PRINTwai")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_wai();
			}
			if (cOperate.equals("PRINTepi")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_epi();
			}
			if (cOperate.equals("PRINTabr")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_abr();
			}
			if (cOperate.equals("PRINTaf1")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_af1();
			}
			if (cOperate.equals("PRINTd1")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d1();
			}
			if (cOperate.equals("PRINTd2")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d2();
			}
			if (cOperate.equals("PRINTd3")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d3();
			}
			if (cOperate.equals("PRINTd4")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d4();
			}
			if (cOperate.equals("PRINTd5")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d5();
			}
			if (cOperate.equals("PRINTd6")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d6();
			}
			if (cOperate.equals("PRINTd7")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d7();
			}
			if (cOperate.equals("PRINTd8")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d8();
			}
			if (cOperate.equals("PRINTd9")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData_d9();
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
		q_sql = "select IssueCont,IssueType from LCIssuePol where BackObjType = '2'   and NeedPrint = 'Y' and PrtSeq  = '"
				+ "?PrtSeq?" + "'";
		logger.debug("问题件中所执行的sql是" + q_sql);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(q_sql);
		sqlbv3.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
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
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		String tSQL = "";
		String InputOperator = tLCGrpContDB.getInputOperator();// 录入员
		String UWOperator = tLCGrpContDB.getUWOperator();// 核保员
		String ApproveOperator = tLCGrpContDB.getApproveCode();// 复核员

		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
		tSQL = "select * from es_doc_main where doccode='"
				+ "?doccode?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSQL);
		sqlbv7.put("doccode", tLCGrpContDB.getPrtNo());
		tES_DOC_MAINSet.set(tES_DOC_MAINDB.executeQuery(sqlbv7));
		if (tES_DOC_MAINDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tES_DOC_MAINDB.mErrors);
			throw new Exception("在取得扫描员数据时发生错误");
		}
		String ScanOperator = "无";
		if (tES_DOC_MAINSet.size() > 0)
			ScanOperator = tES_DOC_MAINSet.get(1).getScanOperator();// 扫描员

		tSQL = "select * from LCIssuePol where BackObjType = '3' and NeedPrint = 'Y' and state in ('0','1') "
				+ " and contno='" + "?strContNo?" + "' ";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSQL);
		sqlbv8.put("strContNo", strContNo);
		tLCIssuePolSet.set(tLCIssuePolDB.executeQuery(sqlbv8));
		if (tLCIssuePolDB.mErrors.needDealError() == true) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			throw new Exception("在取得问题件中数据时发生错误");
		}
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("QUESTION");
		String content = "";
		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			// zy 2009-05-14 如果为1条记录则不加序号
			if (tLCIssuePolSet.size() == 1) {
				content = tLCIssuePolSet.get(i).getIssueCont();
				getContent(tlistTable, content, 40);
			} else {
				content = i + "." + tLCIssuePolSet.get(i).getIssueCont();
				getContent(tlistTable, content, 40);
			}
		}
		strArr = new String[1];
		strArr[0] = "QUESTION";
		// zy 2009-05-14 回复日期
		String replyDate = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(),
				30, "D", "");
		String ReplyYear = replyDate.substring(0, 4);
		String ReplyMon = replyDate.substring(5, 7);
		String ReplyDay = replyDate.substring(8, 10);
		String printName = "业务员通知书";
		
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExportNew的实例
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCGrpContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);//用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

		TextTag texttag = new TextTag();
		texttag.add("AppntName", tLCGrpContDB.getAppntName());
		texttag.add("PrtNo", tLCGrpContDB.getPrtNo());

		String thead = getAgentName(tLCGrpContDB.getAgentCode());
		String tSQLagent = "select sex from laagent where agentcode='"
				+ "?agentcode?" + "' ";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSQLagent);
		sqlbv9.put("agentcode", tLCGrpContDB.getAgentCode());
		SSRS tagent = new SSRS();
		ExeSQL yExeSQL = new ExeSQL();
		tagent = yExeSQL.execSQL(sqlbv9);
		if (tagent.getMaxRow() > 0) {
			thead = PrintTool.getHead(thead, tagent.GetText(1, 1), uLanguage);
		}

		texttag.add("AgentName", thead);
		texttag.add("AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCGrpContDB.getManageCom()));
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("CurrentDay", df.format(new Date()));
		texttag.add("InputOperator", InputOperator);
		texttag.add("UWOperator", UWOperator);
		texttag.add("ScanOperator", ScanOperator);
		texttag.add("ApproveOperator", ApproveOperator);
		// zy 2009-05-14 回复日期
		texttag.add("ReplyYear", ReplyYear);
		texttag.add("ReplyMon", ReplyMon);
		texttag.add("ReplyDay", ReplyDay);

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
			logger.debug("test by yaory:" + mLOPRTManagerSchema.getOtherNo()); // 合同号
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
			ExeSQL q_exesql = new ExeSQL();
			logger.debug("SQL------>" + q_sql);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(q_sql);
			sqlbv10.put("strPolNo", strPolNo);
			sqlbv10.put("PrtSeq", mLOPRTManagerSchema.getOldPrtSeq());
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv10);
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

		String PrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");

			return false;
		}


		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		// ***********************获得客户号等*********************88
				String tCustomerNo = mLOPRTManagerSchema.getStandbyFlag5();
				String tCustomerNoType = mLOPRTManagerSchema.getStandbyFlag4();
				String tSpecFlag = mLOPRTManagerSchema.getStandbyFlag2();
				String tChangePolFlag = mLOPRTManagerSchema.getStandbyFlag6();
				String tAddFeeFlag = mLOPRTManagerSchema.getStandbyFlag7();
		// ************************end***********************		

		boolean ChangePolFlag = false; // 打印保险计划变更标记
		boolean payClientFee = false; // 因保险计划变更，给客户退费标记
		boolean getClientFee = false; // 因保险计划变更，客户需要补交费用标记
		double ChangeFee = 0.00; // 因保险计划变更，客户需要补交费用或退费
		double SpecAddFeeSum = 0.00; // 和保险计划变更一起的客户的加费
		String ChangePolReason = ""; // 保险计划变更的原因

		boolean QuestionFlag = false; // 打印问题件部分的判断标志
		boolean AddFeeFlag = false; // 打印加费部分的判断标志
		boolean SpecFlag = false; // 打印特别约定部分的判断标志

		LCContDB tLCContDB = new LCContDB();
		String strSQL = "SELECT * FROM LCCont WHERE ProposalContNo = '"
				+ "?ProposalContNo?" + "'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(strSQL);
		sqlbv11.put("ProposalContNo", mLOPRTManagerSchema.getOtherNo());
		LCContSet tempLCContSet = tLCContDB.executeQuery(sqlbv11);

		if (tempLCContSet.size() == 0) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在LCCont表中找不到相关信息");

			return false;
		}

		int m;
		int i;
		tLCContDB.setSchema(tempLCContSet.get(1));
		LCContSchema tLCContSchema = new LCContSchema();
		// if( !tLCPolDB.getInfo() ) {
		// mErrors.copyAllErrors(tLCPolDB.mErrors);
		// buildError("outputXML", "在取得LCPol的数据时发生错误");
		// return false;
		// }
		/*
		 * if (!tLCPolDB.getPolNo().equals(tLCPolDB.getMainPolNo())) { // 查询主险保单
		 * mLCPolSchema = queryMainPol(tLCPolDB.getMainPolNo());
		 * 
		 * if (mLCPolSchema == null) { buildError("outputXML", "没有查询到主险保单");
		 * 
		 * return false; } } else { mLCPolSchema = tLCPolDB.getSchema(); //
		 * 保存主险投保单信息 }
		 */
		tLCContSchema = tLCContDB.getSchema();

		String tAgentCode = tLCContSchema.getAgentCode();

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode);

		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");

			return false;
		}

		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		// *************************被保人信息**************************
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		if ("A".equals(tCustomerNoType)) {
			tLCInsuredDB.setContNo(tLCContSchema.getContNo());
			tLCInsuredDB.setInsuredNo(tLCContSchema.getInsuredNo());

			if (!tLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				buildError("outputXML", "在取得LCInsured的数据时发生错误");

				return false;
			}

			tLCInsuredSchema = tLCInsuredDB.getSchema(); // 保存被保人信息
		} else {
			// ************************主被保人信息**************************
			tLCInsuredDB.setContNo(tLCContSchema.getContNo());
			tLCInsuredDB.setInsuredNo(tCustomerNo);

			if (!tLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				buildError("outputXML", "在取得LCInsured的数据时发生错误");

				return false;
			}

			tLCInsuredSchema = tLCInsuredDB.getSchema(); // 保存代理人信息
		}

		// 1-险种信息：
		ListTable tRiskListTable = new ListTable();
		String[] RiskInfoTitle = new String[6];
		String[] RiskInfo = new String[6];
		tRiskListTable.setName("MAIN"); // 对应模版投保信息部分的行对象名
		RiskInfoTitle[0] = "RiskName"; // 险种名称
		RiskInfoTitle[1] = "Amnt"; // 保险金额
		RiskInfoTitle[2] = "PayYears"; // 缴费年期
		RiskInfoTitle[3] = "PayIntv"; // 缴费方式（间隔）
		RiskInfoTitle[4] = "Prem"; // 保费
		RiskInfoTitle[5] = "JobAddPrem"; // 职业加费

		double oldSumPrem = 0.00; // 合计保费
		double oldSumJobAddPrem = 0.00; // 合计加费

		// 1 取原始投保单险种信息 待修改
		LMRiskDB tLMRiskDB;
		String sTemp;
		SSRS tempSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();

		LCPolOriginalDB tLCPolOriginalDB = new LCPolOriginalDB();
		LCPolOriginalSet tLCPolOriginalSet = new LCPolOriginalSet();
		LCPolOriginalSchema tLCPolOriginalSchema = new LCPolOriginalSchema();
		String Sql = "select * from LCPolOriginal where contno='"
				+ "?contno?" + "' ";
		if (tCustomerNoType.equals("I"))// 对投保人显示所有险种信息
			Sql = Sql + " and insuredno='" + "?tCustomerNo?" + "'";
		Sql = Sql + " order by mainpolno,polno ";// 查询原始投保单表
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(Sql);
		sqlbv12.put("contno", tLCContSchema.getContNo());
		sqlbv12.put("tCustomerNo", tCustomerNo);
		tLCPolOriginalSet = tLCPolOriginalDB.executeQuery(sqlbv12);

		if (tLCPolOriginalSet != null) {

			for (int n = 1; n <= tLCPolOriginalSet.size(); n++) {
				tLCPolOriginalSchema = tLCPolOriginalSet.get(n);
				RiskInfo = new String[6];
				tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLCPolOriginalSchema.getRiskCode());

				if (!tLMRiskDB.getInfo()) {
					mErrors.copyAllErrors(tLMRiskDB.mErrors);
					buildError("outputXML", "在取得LMRisk的数据时发生错误");

					return false;
				}

				RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
				RiskInfo[1] = mDecimalFormat.format(tLCPolOriginalSchema
						.getAmnt()); // 保险金额

				if ((tLCPolOriginalSchema.getPayEndYear() == 1000)
						&& tLCPolOriginalSchema.getPayEndYearFlag().equals("A")) {
					RiskInfo[2] = "终生"; // 交费年期
				} else {
					RiskInfo[2] = (new Integer(tLCPolOriginalSchema
							.getPayYears())).toString(); // 交费年期
				}

				sTemp = "";

				if (tLCPolOriginalSchema.getPayIntv() == -1) {
					sTemp = "不定期交费";
				}

				if (tLCPolOriginalSchema.getPayIntv() == 0) {
					sTemp = "趸交";
				}

				if (tLCPolOriginalSchema.getPayIntv() == 1) {
					sTemp = "月交";
				}

				if (tLCPolOriginalSchema.getPayIntv() == 3) {
					sTemp = "季交";
				}

				if (tLCPolOriginalSchema.getPayIntv() == 6) {
					sTemp = "半年交";
				}

				if (tLCPolOriginalSchema.getPayIntv() == 12) {
					sTemp = "年交";
				}

				RiskInfo[3] = sTemp; // 交费方式
				// RiskInfo[4] = mDecimalFormat.format(tLCPolOriginalSchema
				// .getStandPrem()); // 保费
				// add by zy 2009-05-08 保费调整为已到帐的暂收费信息
				String tFeeSql = "select (case when sum(paymoney) is not null then sum(paymoney)  else 0 end) from ljtempfee where otherno='"
						+ "?PrtNo?"
						+ "' and riskcode='"
						+ "?RiskCode?"
						+ "' and confmakedate is not null  and confdate is null and tempfeetype='1' ";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(tFeeSql);
				sqlbv13.put("PrtNo", tLCPolOriginalSchema.getPrtNo());
				sqlbv13.put("RiskCode", tLCPolOriginalSchema.getRiskCode());
				ExeSQL tExeSQL = new ExeSQL();
				double tFee = 0.00; // 已到帐的保险费x
				if (tExeSQL.execSQL(sqlbv13).getMaxRow() > 0) {
					tFee = Double.parseDouble(tExeSQL.execSQL(sqlbv13).GetText(
							1, 1));
				}
				RiskInfo[4] = mDecimalFormat.format(tFee);
				// oldSumPrem = oldSumPrem +
				// tLCPolOriginalSchema.getStandPrem(); // 原保费合计
				oldSumPrem = oldSumPrem + tFee;

				// 险种的加费
				RiskInfo[5] = mDecimalFormat.format(tLCPolOriginalSchema
						.getJobAddFee()); // 保费
				oldSumJobAddPrem = oldSumJobAddPrem
						+ tLCPolOriginalSchema.getJobAddFee(); // 原保费合计
				tRiskListTable.add(RiskInfo); // 加入险种信息
			}
		}

		oldSumPrem = Double.parseDouble(mDecimalFormat.format(oldSumPrem)); // 转换计算后的保费合计(规定的精度)
		oldSumJobAddPrem = Double.parseDouble(mDecimalFormat
				.format(oldSumJobAddPrem)); // 转换计算后加费合计(规定的精度)

		// 2 是否有承保计划变更
		int SN = 0; // 页面排序序号
		String content = "";
		boolean ChangeReason = false;// 是否存在承保计划变更原因标志

		ListTable tChangeListTable = new ListTable();
		tChangeListTable.setName("CHANGE"); // 对应模版问题部分的行对象名

		String[] ChangeTitle = new String[1];
		ChangeTitle[0] = "Change";

		// addition:如果有保险计划变更，就不显示问题件->改为有相应信息则显示 2008-12-3 modify
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(tLCContSchema.getContNo());
		//
		// if (!tLCCUWMasterDB.getInfo()) {
		// mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
		// buildError("outputXML", "在取得LCUWMaster的数据时发生错误");
		//
		// return false;
		// }
		//        
		// if (tLCCUWMasterDB.getChangePolFlag() != null) // 保险计划变更标记
		// {
		// if (tLCCUWMasterDB.getChangePolFlag().equals("1")
		// || tLCCUWMasterDB.getChangePolFlag().equals("2")) {
		// SN = 1;
		// ChangePolFlag = true;
		// ChangePolReason = tLCCUWMasterDB.getChangePolReason(); // 变更原因
		// if(!ChangePolReason.equals(""))
		// {
		// ChangeReason = true;
		// content = "1、" + ChangePolReason;
		// getContent(tChangeListTable, content ,50);
		// }
		//				
		// }
		// }
		//		
		// //**************** 取加费原因****************************
		// if (tLCCUWMasterDB.getAddPremFlag() != null) // 保险计划变更标记
		// {
		// if (tLCCUWMasterDB.getAddPremFlag().equals("1")) {
		// ChangePolFlag = true;
		// AddFeeFlag = true;
		// }
		// }

		// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		// String tSQL = "select addpremreason from lcuwmaster where contno='"
		// + tLCContSchema.getContNo() + "' ";
		// SSRS yssrs = new SSRS();
		// ExeSQL yExeSQL = new ExeSQL();
		// yssrs = yExeSQL.execSQL(tSQL);
		//
		// if (yssrs.getMaxRow() == 0) {
		// SpecFlag = false;
		// } else {
		// int k=0;
		// int n=0;
		// if(!ChangePolReason.equals(""))
		// k=1;
		// for(int j=1;j<=yssrs.getMaxRow();j++)
		// if (!((yssrs.GetText(j, 1).equals("")) || (yssrs.GetText(j, 1) ==
		// null))) {
		// ChangeReason = true;
		// n++;
		// content = n +k +"、" + yssrs.GetText(j, 1);
		// getContent(tChangeListTable, content ,50);
		// }
		// }
		// *********************************************************

		// boolean bSpecReason = false;
		// // tongmeng 2007-12-27 add
		// // 判断本次是否该发特约
		//
		// if ((strSpecReason == null) || strSpecReason.equals("")) {
		// bSpecReason = false;
		// } else {
		// bSpecReason = true;
		// }
		if (tCustomerNoType.equals("I")) {
			// zy 2009-05-15 如果不属于计划变更和加费但是核保结论调整则也显示未调整的险种信息
			/*
			 * String cSql = "select count(*) from LCPol where contno='" +
			 * tLCContSchema.getContNo() + "' and uwflag in ('1','2','a') and
			 * insuredno='" +tCustomerNo+ "' and mainpolno<>polno "; String
			 * tHBChangeFlag="0"; ExeSQL tExeSQL = new ExeSQL();
			 * if(Integer.parseInt(tExeSQL.getOneValue(cSql))>0) { tHBChangeFlag =
			 * "1"; }
			 */
			// 是否有承保计划变更和加费
			if (tChangePolFlag.equals("1") || tAddFeeFlag.equals("1")) {
				ChangePolFlag = true;

				LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB();
				tLCIndUWMasterDB.setContNo(tLCContSchema.getContNo());
				tLCIndUWMasterDB.setInsuredNo(tCustomerNo);

				if (!tLCIndUWMasterDB.getInfo()) {
					mErrors.copyAllErrors(tLCIndUWMasterDB.mErrors);
					buildError("outputXML", "在取得LCIndUWMaster的数据时发生错误");

					return false;
				}
				if (tLCIndUWMasterDB.getUWIdea() != null
						&& (!tLCIndUWMasterDB.getUWIdea().equals(""))) {
					ChangeReason = true;
					content = tLCIndUWMasterDB.getUWIdea();// 取变更和加费原因
					getContent(tChangeListTable, content, 50);
				}
			}
		}

		logger.debug("是否有承保计划变更标志**********    " + ChangePolFlag);

		// if (ChangePolFlag == false) {
		// 3-问题信息
		String[] QuestionTitle = new String[1];
		QuestionTitle[0] = "Question";
		ListTable tQuestionListTable = new ListTable();

		tQuestionListTable.setName("QUESTION"); // 对应模版问题部分的行对象名

		String q_sql = "";
		String q_sql_printFlag = "";
		// 查询待打印的问题件
		q_sql = "select IssueCont from LCIssuePol where BackObjType = '2' and NeedPrint = 'Y' "
				+ " and ProposalContNo = '"
				+ "?ProposalContNo?"
				+ "' and questionobj='"
				+ "?tCustomerNo?"
				+ "' and ((questionobjtype<>'0' and '"
				+ "?tCustomerNoType?"
				+ "' ='I')"
				+ " or (questionobjtype='0' and '"
				+ "?tCustomerNoType?" + "' ='A'))" + " and state in ('0','1') ";

		if (mLOPRTManagerSchema.getCode().equals("TB89")) {
			q_sql_printFlag = " and standbyflag2='Y' ";// 打印类
		} else if (mLOPRTManagerSchema.getCode().equals("TB90")) {
			q_sql_printFlag = " and standbyflag2='N'";// 非打印类
		}
		q_sql = q_sql + q_sql_printFlag;
		logger.debug("问题件中所执行的sql是" + q_sql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(q_sql);
		sqlbv14.put("ProposalContNo", tLCContSchema.getProposalContNo());
		sqlbv14.put("tCustomerNo", tCustomerNo);
		sqlbv14.put("tCustomerNoType", tCustomerNoType);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv14);

		if (q_ssrs.getMaxRow() == 0) {
			logger.debug("没有问题件记录");
			QuestionFlag = false;// 问题件不显示
		} else {
			logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");
			QuestionFlag = true;
			// add by zy 2009-05-08
			if (q_ssrs.getMaxRow() == 1) {
				getContent(tQuestionListTable, q_ssrs.GetText(1, 1), 50);
			} else {

				for (i = 1; i <= q_ssrs.getMaxRow(); i++) {
					content = i + "." + q_ssrs.GetText(i, 1); // 问题件内容
					getContent(tQuestionListTable, content, 50);
				}
			}
		}

		SN = q_ssrs.getMaxRow(); // 页面排序序号
		// }

		// 4-现在的投保单信息，并查询有无加费，有加费信息则需要打印承保计划变更
		// 上面补充的保险计划变更已经查询
		// String AddFeeReason = tLCCUWMasterDB.getAddPremReason(); // 得到加费原因,后用

		logger.debug("***************开始查询现在的投保单信息***********************");
		double SumPrem = 0.00;
		double SumJobAddFee = 0.00;
		double SumLiveAddFee = 0.00;
		double SumHobbyAddFee = 0.00;
		double SumHealthAddFee = 0.00; // 最后的合计金额
		double AvgPrem = 0.00; // 平均每期保费,存放当期保费
		ListTable tChangePolListTable = new ListTable();
		String[] ChangePol = new String[9];
		String[] ChangePolTitle = new String[9];

		ChangePolTitle[0] = "RiskName"; // 险种名称
		ChangePolTitle[1] = "Amnt"; // 保险金额
		ChangePolTitle[2] = "PayYears"; // 缴费年期
		ChangePolTitle[3] = "PayIntv"; // 缴费方式（间隔）
		ChangePolTitle[4] = "Prem"; // 保费
		ChangePolTitle[5] = "JobAddPrem"; // 职业加费
		ChangePolTitle[6] = "HealthAddPrem"; // 健康加费
		// add by zy 2009-05-08
		ChangePolTitle[7] = "OtherPrem";// 其他交费，除职业交费和健康交费之外的加费
		ChangePolTitle[8] = "SumPrem";// 保险费合计
		// ChangePolTitle[7] = "LivePrem"; // 居住地加费
		// ChangePolTitle[8] = "HobbyPrem"; // 爱好加费
		tChangePolListTable.setName("ChangePol"); // 对应模版加费部分的行对象名

		if (tCustomerNoType.equals("I")) {
			// tongmeng 2009-03-26
			// 不打印 延期,拒保,撤保 的险种
			Sql = "select * from LCPol where contno='"
					+ "?contno?" + "' "
					+ " and uwflag not in ('1','2','a') " + " and insuredno='"
					+ "?tCustomerNo?" + "'" + " order by mainpolno,polno ";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(Sql);
			sqlbv15.put("contno", tLCContSchema.getContNo());
			sqlbv15.put("tCustomerNo", tCustomerNo);
			LCPolDB tempLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema_sub = new LCPolSchema();
			LCPolSet tLCPolSet_sub = tempLCPolDB.executeQuery(sqlbv15);

			if (tLCPolSet_sub != null) {
				gLCPolSet.set(tLCPolSet_sub); // 保存附加险投保单集合,后用

				for (int n = 1; n <= gLCPolSet.size(); n++) {
					tLCPolSchema_sub = gLCPolSet.get(n);
					ChangePol = new String[9];
					tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLCPolSchema_sub.getRiskCode());

					if (!tLMRiskDB.getInfo()) {
						mErrors.copyAllErrors(tLMRiskDB.mErrors);
						buildError("outputXML", "在取得LMRisk的数据时发生错误");

						return false;
					}

					ChangePol[0] = tLMRiskDB.getRiskName(); // 险种名称
					ChangePol[1] = mDecimalFormat.format(tLCPolSchema_sub
							.getAmnt()); // 保险金额

					if ((tLCPolSchema_sub.getPayEndYear() == 1000)
							&& tLCPolSchema_sub.getPayEndYearFlag().equals("A")) {
						ChangePol[2] = "终生"; // 交费年期
					} else {
						ChangePol[2] = (new Integer(tLCPolSchema_sub
								.getPayYears())).toString(); // 交费年期
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

					ChangePol[3] = sTemp; // 交费方式
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where polno='"
							+ "?polno?"
							+ "' "
							+ " and PayPlanCode not like '000000%'");
					sqlbv16.put("polno", tLCPolSchema_sub.getPolNo());
					tempSSRS = tempExeSQL.execSQL(sqlbv16);
					if (tempSSRS.MaxCol > 0)
						ChangePol[4] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 保费
					else
						ChangePol[4] = "0.00";
					SumPrem = SumPrem + Double.parseDouble(ChangePol[4]); // 原保费合计

					// 险种的职业加费
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='02'");
					sqlbv17.put("PolNo", tLCPolSchema_sub.getPolNo());
					tempSSRS = tempExeSQL.execSQL(sqlbv17);

					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							// logger.debug("侯志敏的程序307");
							logger.debug("累计职业加费是" + tempSSRS.GetText(1, 1));
							ChangePol[5] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							SumJobAddFee = SumJobAddFee
									+ Double
											.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							ChangePol[5] = "0.00";
						}
					}

					// 险种的健康加费
					SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
					sqlbv18.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype='01'");
					sqlbv18.put("PolNo", tLCPolSchema_sub.getPolNo());
					tempSSRS = tempExeSQL.execSQL(sqlbv18);

					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							// logger.debug("侯志敏的程序307");

							logger.debug("累计健康加费是" + tempSSRS.GetText(1, 1));
							ChangePol[6] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							SumHealthAddFee = SumHealthAddFee
									+ Double
											.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							ChangePol[6] = "0.00";
						}
					}

					// 险种的居住地加费
					/*
					 * tempSSRS = tempExeSQL .execSQL("select nvl(sum(Prem),0)
					 * from LCPrem where PolNo='" + tLCPolSchema_sub.getPolNo() + "'
					 * and PayPlanCode like '000000%' and payplantype='03'");
					 * 
					 * if (tempSSRS.MaxCol > 0) { if (!(tempSSRS.GetText(1,
					 * 1).equals("0") || tempSSRS .GetText(1,
					 * 1).trim().equals(""))) { // logger.debug("侯志敏的程序307");
					 * logger.debug("累计居住地加费是" + tempSSRS.GetText(1, 1));
					 * ChangePol[7] = mDecimalFormat.format(Double
					 * .parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
					 * SumLiveAddFee = SumLiveAddFee +
					 * Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计 }
					 * else { ChangePol[7] = "0.00"; } }
					 *  // 险种的爱好加费 tempSSRS = tempExeSQL .execSQL("select
					 * nvl(sum(Prem),0) from LCPrem where PolNo='" +
					 * tLCPolSchema_sub.getPolNo() + "' and PayPlanCode like
					 * '000000%' and payplantype='04'");
					 * 
					 * if (tempSSRS.MaxCol > 0) { if (!(tempSSRS.GetText(1,
					 * 1).equals("0") || tempSSRS .GetText(1,
					 * 1).trim().equals(""))) { // logger.debug("侯志敏的程序307");
					 * logger.debug("累计爱好加费是" + tempSSRS.GetText(1, 1));
					 * ChangePol[8] = mDecimalFormat.format(Double
					 * .parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
					 * SumHobbyAddFee = SumHobbyAddFee +
					 * Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计 }
					 * else { ChangePol[8] = "0.00"; } }
					 */
					// 险种的其他加费
					SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
					sqlbv19.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'  and payplantype not in ('01','02')");
					sqlbv19.put("PolNo", tLCPolSchema_sub.getPolNo());
					tempSSRS = tempExeSQL.execSQL(sqlbv19);

					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							// logger.debug("侯志敏的程序307");
							logger.debug("累计其他加费是" + tempSSRS.GetText(1, 1));
							ChangePol[7] = mDecimalFormat.format(Double
									.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
							SumLiveAddFee = SumLiveAddFee
									+ Double
											.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						} else {
							ChangePol[7] = "0.00";
						}
					}

					ChangePol[8] = mDecimalFormat.format(Double
							.parseDouble(ChangePol[4])
							+ Double.parseDouble(ChangePol[5])
							+ Double.parseDouble(ChangePol[6])
							+ Double.parseDouble(ChangePol[7])); // 保险费合计

					tChangePolListTable.add(ChangePol); // 加入险种信息
				}
			}

			SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)
			SumHealthAddFee = Double.parseDouble(mDecimalFormat
					.format(SumHealthAddFee)); // 转换计算后的保费(规定的精度)
			SumJobAddFee = Double.parseDouble(mDecimalFormat
					.format(SumJobAddFee));
			SumLiveAddFee = Double.parseDouble(mDecimalFormat
					.format(SumLiveAddFee));
			// SumHobbyAddFee = Double.parseDouble(mDecimalFormat
			// .format(SumHobbyAddFee));
			AvgPrem = Double.parseDouble(mDecimalFormat.format(SumPrem
					+ SumHealthAddFee + SumJobAddFee + SumLiveAddFee));

		}

		String replyDate = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(),
				30, "D", "");
		String ReplyYear = replyDate.substring(0, 4);
		String ReplyMon = replyDate.substring(5, 7);
		String ReplyDay = replyDate.substring(8, 10);
		/**
		 * logger.debug("开始查询加费:"); // 3-2取出加费部分的数据 //
		 * logger.debug("对于主险的信息只有一条记录，而对应附加险的信息可以有多条记录，所以在附加险时要进行循环处理！！！");
		 * 
		 * 
		 * SSRS tSSRS = new SSRS(); ExeSQL tExeSQL = new ExeSQL();
		 *  // 取投保单加费信息(利用前面查询得到的附险投保单集合) for (int n = 1; n <= gLCPolSet.size();
		 * n++) { logger.debug("2003-04-25 hzm 附加险的信息！！！"); LCPolSchema
		 * tLCPolSchema_Sub = gLCPolSet.get(n); Sql = "select
		 * nvl(sum(StandPrem),0) from LCPrem where PolNo='" +
		 * tLCPolSchema_Sub.getPolNo() + "' and PayPlanCode like '000000%'";
		 * tSSRS = tExeSQL.execSQL(Sql);
		 * 
		 * if (tSSRS.MaxCol > 0) { if (!(tSSRS.GetText(1, 1).equals("0") ||
		 * tSSRS .GetText(1, 1).trim().equals(""))) { ChangePolFlag =
		 * false;//加费属于承保计划变更
		 * 
		 * tLMRiskDB = new LMRiskDB();
		 * tLMRiskDB.setRiskCode(tLCPolSchema_Sub.getRiskCode());
		 * 
		 * if (!tLMRiskDB.getInfo()) { mErrors.copyAllErrors(tLMRiskDB.mErrors);
		 * buildError("outputXML", "在取得LMRisk的数据时发生错误");
		 * 
		 * return false; }
		 * 
		 * AddFeeFlag = true; // 加费标记为真 AddFee = new String[4]; AddFee[0] =
		 * tLMRiskDB.getRiskName(); // 险种名称 AddFee[1] =
		 * mDecimalFormat.format(tLCPolSchema_Sub .getAmnt()); // 保额 AddFee[2] =
		 * mDecimalFormat.format(tLCPolSchema_Sub .getStandPrem()); // 保费
		 * AddFee[3] = mDecimalFormat.format(Double
		 * .parseDouble(tSSRS.GetText(1, 1))); // 累计加费
		 * tAddFeeListTable.add(AddFee);
		 * 
		 * logger.debug("2003-04-25 lys 附加险的信息！！！");
		 * 
		 * String t_sql_f = "select nvl(sum(paymoney),0) from LJTempFee where (
		 * OtherNo = '" + tLCContSchema.getContNo() + "' or OtherNo = '" +
		 * tLCContSchema.getPrtNo() + "') and RiskCode = '" +
		 * tLCPolSchema_Sub.getRiskCode() + "'";
		 * 
		 * SSRS t_ssrs_f = new SSRS(); t_ssrs_f = tExeSQL.execSQL(t_sql_f);
		 * 
		 * double temp_fee_f = 0;
		 * 
		 * if (t_ssrs_f.getMaxRow() == 0) { logger.debug("lys附件险的暂交费的记录为空！！");
		 * temp_fee_f = 0; } else { logger.debug("lys附件险的暂交费的金额是" +
		 * t_ssrs_f.GetText(1, 1)); temp_fee_f =
		 * Double.parseDouble(t_ssrs_f.GetText(1, 1)); }
		 * 
		 * double temp_fee_f_sum = 0; temp_fee_f_sum =
		 * (Double.parseDouble(AddFee[2]) + Double .parseDouble(AddFee[3])) -
		 * temp_fee_f; logger.debug("lys与客户之间发生的现金交易额是" + temp_fee_f_sum);
		 * 
		 * if (temp_fee_f_sum > 0) { AddFeeInfo = new String[2]; // lys
		 * AddFeeInfo[0] = tLMRiskDB.getRiskName(); // lys AddFeeInfo[1] =
		 * mDecimalFormat .format(temp_fee_f_sum); logger.debug("实际要保户交的费用是" +
		 * AddFeeInfo[1]);
		 * 
		 * tAddFeeInfoListTable.add(AddFeeInfo); SumAddFee = SumAddFee +
		 * Double.parseDouble(tSSRS.GetText(1, 1)); // 加费合计 SumAddFeeInfo =
		 * SumAddFeeInfo + temp_fee_f_sum; oldSumPrem = oldSumPrem +
		 * tLCPolSchema_Sub.getStandPrem(); // 原保险费 lys_Flag = "1";
		 * lys_Flag_main = "0"; ReasonInfo1 = "您要补交的保险费用如下:"; }
		 * 
		 * if (temp_fee_f_sum <= 0) { lys_Flag = "0"; lys_Flag_ab = "1";
		 * logger.debug("附件险中没有要补交的数据"); lys_Flag_main = "您无需补交保险费！！！";
		 * 
		 * if (lys_Flag_main.equals("1")) { logger.debug("您无需补交保险费");
		 * ReasonInfo1 = "您无需补交保险费！"; } } } } }
		 * 
		 * if (AddFeeFlag) { SN = SN + 1; // 序号加1 }
		 */
		// if(lys_Flag_main.equals("1"))
		// {
		// logger.debug("您无需补交保险费");
		// ReasonInfo1 = "您无需补交保险费！";
		// }
		// int SpecNo=0; //加费的序号
		// 4-特别约定信息
		// String []SpecTitle=new String[2];
		// SpecTitle[0]= "ID";
		// SpecTitle[1] = "SpecInfo";
		String[] SpecTitle = new String[1];
		SpecTitle[0] = "SpecInfo";

		ListTable tSpecListTable = new ListTable();
		tSpecListTable.setName("SPECINFO"); // 对应模版特别约定部分的行对象名
		if (tSpecFlag.equals("1")) {
			String t_sql = "";

			t_sql = "SELECT SpecContent FROM LCCSpec WHERE proposalcontno="
					+ "(select proposalcontno from lccont where contno='"
					+ "?contno?" + "') and  needprint='Y'"
					+ " AND TRIM(SpecContent) IS NOT NULL"
					+ " and prtflag in ('0','1') "
					+ " and (customerno is null or (customerno='" + "?tCustomerNo?"
					+ "' and '" + "?tCustomerNoType?" + "' ='I'))"
					+ " ORDER BY ModifyDate, ModifyTime DESC";
			logger.debug("查询特约SQL:" + t_sql);
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(t_sql);
			sqlbv20.put("contno", tLCContSchema.getContNo());
			sqlbv20.put("tCustomerNo", tCustomerNo);
			sqlbv20.put("tCustomerNoType", tCustomerNoType);
			SSRS yssrs = new SSRS();
			ExeSQL yExeSQL = new ExeSQL();
			yssrs = yExeSQL.execSQL(sqlbv20);

			if (yssrs.getMaxRow() == 0) {
				SpecFlag = false;
			} else {
				int n = 0;
				// add by zy 2009-05-08 如果为多条记录则以1.2.3显示
				if (yssrs.getMaxRow() == 1) {
					SpecFlag = true;// 特约标志
					n++;
					content = yssrs.GetText(1, 1);
					getContent(tSpecListTable, content, 50);
				} else {
					for (int j = 1; j <= yssrs.getMaxRow(); j++) {
						if (!((yssrs.GetText(j, 1).equals("")) || (yssrs
								.GetText(j, 1) == null))) {
							SpecFlag = true;// 特约标志
							n++;
							content = n + "." + yssrs.GetText(j, 1);
							getContent(tSpecListTable, content, 50);
						}
					}
				}
			}
		}

		/**
		 * // 取特约原因 LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB(); String tSQL =
		 * "select * from lcuwmaster where proposalno in " + " (select
		 * proposalno from lcpol where contno='" + tLCContSchema.getContNo() + "'
		 * and mainpolno=polno) " + " "; String strSpecReason = ""; String
		 * tSQL_Spec = "select count(*) from lcspec where contno='" +
		 * tLCContSchema.getContNo() + "' and prtflag in ('0','1') "; ExeSQL
		 * tExeSQL = new ExeSQL(); String tResult =
		 * tExeSQL.getOneValue(tSQL_Spec); if (tResult == null ||
		 * tResult.equals("")) { CError.buildErr(this, "查询特约信息出错!"); return
		 * false; } else if (Integer.parseInt(tResult) > 0) { strSpecReason =
		 * tLCUWMasterDB.executeQuery(tSQL).get(1) .getSpecReason(); } //
		 * ???????????????
		 * 
		 * boolean bSpecReason = false; // tongmeng 2007-12-27 add // 判断本次是否该发特约
		 * 
		 * if ((strSpecReason == null) || strSpecReason.equals("")) {
		 * bSpecReason = false; } else { bSpecReason = true; }
		 */
		// ******************************准备打印需要的数据********************************************
		// ********************************************************************************************
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String  printName = "";
		XmlExportNew xmlExport = new XmlExportNew(); // 新建一个XmlExport的实例

		// zy 由于客户描述的模板是两个模板，所以必须进行区分！！！
		if (mLOPRTManagerSchema.getCode().equals("TB89")) {
			printName = "核保通知书(甲类)";
		} else if (mLOPRTManagerSchema.getCode().equals("TB90")) {
			printName = "核保通知书(乙类)";
		}
		xmlExport.createDocument(printName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);//用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

		// 生成-年-月-日格式的日期
		String SysDate = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());

		// 模版自上而下的元素
		logger.debug("mLOPRTManagerSchema code:"
				+ mLOPRTManagerSchema.getCode());
		if (mLOPRTManagerSchema.getCode().equals("TB89")) {
			texttag.add("IFPrintFlag", "甲"); // 是否打印标志
		} else if (mLOPRTManagerSchema.getCode().equals("TB90")) {
			texttag.add("IFPrintFlag", "乙"); // 是否打印标志
		}

		String thead = PrintTool.getHead(tLCContSchema.getAppntName(), tLCContSchema
				.getAppntSex(), uLanguage);

		texttag.add("AppntName", thead); // 投保人
		texttag.add("PrtNo", tLCContSchema.getPrtNo()); // 印刷号
		texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", tLCContSchema.getAgentCode()); // 代理人业务号
		texttag.add("ManageCom", getComName(tLCContSchema.getManageCom())); // 营业机构
		texttag.add("PrtSeq", PrtSeq); // 流水号

		// zy 2009-05-09 增加回复日期
		texttag.add("ReplyYear", ReplyYear);
		texttag.add("ReplyMon", ReplyMon);
		texttag.add("ReplyDay", ReplyDay);

		texttag.add("SysDate", SysDate);
		// 核保师代码 核保<--核保师代码
		texttag.add("UWCode", mLOPRTManagerSchema.getReqOperator());
		// add by guoxiang at 2003-12-15:扫描，录入，复核，核保<--核保师代码
		texttag.add("scanner", getScaner(tLCContSchema.getPrtNo())); // 扫描
		texttag.add("InputOperator", tLCContSchema.getInputOperator()); // 录入
		texttag.add("ApproveCode", tLCContSchema.getApproveCode()); // 复核

		// 加入原始投保单信息
		texttag.add("InsuredName", tLCInsuredSchema.getName()); // 被保人名称
		texttag.add("InsuredIDNo", tLCInsuredSchema.getIDNo()); // 被保人号码
		String t_sql = "SELECT OccupationName FROM LDOccupation WHERE OccupationCode='"
				+ "?OccupationCode?" + "'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(t_sql);
		sqlbv21.put("OccupationCode", tLCInsuredSchema.getOccupationCode());
		logger.debug("查询职业SQL:" + t_sql);
		SSRS yssrs = new SSRS();
		ExeSQL yExeSQL = new ExeSQL();
		String tOccupationName = "";
		yssrs = yExeSQL.execSQL(sqlbv21);
		if (yssrs.getMaxRow() == 0) {
			tOccupationName = "";
		} else {
			tOccupationName = yssrs.GetText(1, 1);
		}
		texttag.add("InsuredJob", tOccupationName); // 被保人职业
		texttag.add("InsuredJobOther", tLCInsuredSchema.getPluralityType()); // 被保人兼职
		texttag.add("InsuredJobCode", tLCInsuredSchema.getOccupationCode()); // 被保人职业代码

		xmlExport.addListTable(tRiskListTable, RiskInfoTitle); // 保存原始投保单信息
		texttag.add("oldSumPrem", mDecimalFormat.format(oldSumPrem)); // 合计保费
		texttag
				.add("oldSumJobAddPrem", mDecimalFormat
						.format(oldSumJobAddPrem)); // 合计职业加费
		// 特约、加费、承保计划变更不打印到非打印类通知书
		if (tCustomerNoType.equals("I")
				&& mLOPRTManagerSchema.getCode().equals("TB89")) // 如果是被保人
		{
			xmlExport.addDisplayControl("displayold"); // 显示投保险种信息
		}
		if ((SpecFlag == true || ChangePolFlag == true)
				&& mLOPRTManagerSchema.getCode().equals("TB89"))
			xmlExport.addDisplayControl("display"); // 模版上的特别约定部分的控制标记

		// 加入特约信息
		if (SpecFlag == true && mLOPRTManagerSchema.getCode().equals("TB89")) {
			xmlExport.addDisplayControl("displayspec"); // 模版上的特别约定部分的控制标记
			xmlExport.addListTable(tSpecListTable, SpecTitle); // 保存特别约定信息
		}

		// 加入承保计划变更信息
		if (ChangePolFlag == true
				&& mLOPRTManagerSchema.getCode().equals("TB89")) // 如果是保险计划变更类型的核保
		{
			if (ChangeReason == true) // 如果是保险计划变更类型的核保
			{
				xmlExport.addDisplayControl("displaychangepolreason"); // 保险计划变更原因
				xmlExport.addListTable(tChangeListTable, ChangeTitle); // 保险计划变更后添加的元素
			}

			xmlExport.addDisplayControl("displaychangepol"); // 保险计划模版上的保险计划的部分的控标记
			xmlExport.addListTable(tChangePolListTable, ChangePolTitle); // 保险计划变更后添加的元素

			// texttag.add("ChangePolReason", ChangePolReason); // 变更原因和加费原因
			texttag.add("SumPrem", mDecimalFormat.format(SumPrem)); // 合计保费
			texttag.add("SumJobAddFee", mDecimalFormat.format(SumJobAddFee)); // 合计职业加费
			texttag.add("SumHealthAddFee", mDecimalFormat
					.format(SumHealthAddFee)); // 合计健康加费
			texttag.add("SumLiveAddFee", mDecimalFormat.format(SumLiveAddFee));
			texttag
					.add("SumHobbyAddFee", mDecimalFormat
							.format(SumHobbyAddFee));
			// texttag.add("SumAddFee",
			// mDecimalFormat.format(SumPrem+SumJobAddFee+SumHealthAddFee+SumLiveAddFee+SumHobbyAddFee-oldSumPrem-oldSumJobAddPrem));
			// // 补交保险金额
			texttag.add("SumAddFee", mDecimalFormat.format(SumPrem
					+ SumJobAddFee + SumHealthAddFee + SumLiveAddFee
					- oldSumPrem - oldSumJobAddPrem)); // 补交保险金额
			// zy 2009-05-09 平均每期保费
			texttag.add("AvgPrem", mDecimalFormat.format(AvgPrem));
		}

		// 保存问题信息
		if (QuestionFlag == true) {
			xmlExport.addDisplayControl("displayquestion"); // 模版上的问题部分的控制标记
			xmlExport.addListTable(tQuestionListTable, QuestionTitle); // 保存问题信息
		}

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		/**
		 * // 保存特约原因 if (bSpecReason == true &&
		 * mLOPRTManagerSchema.getCode().equals("TB89")) {
		 * xmlexport.addDisplayControl("displayspecreason"); // 显示特约原因
		 * 
		 * ListTable ltSpecReason = new ListTable(); int nMaxCharsInOneLine =
		 * 50; // The max number of chars that one // line can contain int
		 * nSpecReasonLen = 0; String[] strArr = null;
		 * 
		 * ltSpecReason.setName("SPECREASON"); strSpecReason = " " +
		 * strSpecReason + "故本保单作如下特别约定："; nSpecReasonLen =
		 * strSpecReason.length();
		 * 
		 * while (nSpecReasonLen > nMaxCharsInOneLine) { strArr = new String[1];
		 * strArr[0] = strSpecReason.substring(0, nMaxCharsInOneLine);
		 * 
		 * strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
		 * nSpecReasonLen -= nMaxCharsInOneLine;
		 * 
		 * ltSpecReason.add(strArr); }
		 * 
		 * if (nSpecReasonLen > 0) { strArr = new String[1]; strArr[0] =
		 * strSpecReason;
		 * 
		 * ltSpecReason.add(strArr); }
		 * 
		 * strArr = new String[1]; strArr[0] = "REASON";
		 * 
		 * xmlexport.addListTable(ltSpecReason, strArr); }
		 */
		// 保存保险计划调整
		// 保存其它信息
		mResult.clear();
		mResult.addElement(xmlExport);

		return true;
	}

	// 问卷的打印 add by liuqh 2008-11-21 残疾问卷
	private void getPrintData_dis() throws Exception {
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

			// testtag 数据
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

	// 问卷 add by liuqh 2008-11-21 腰椎疾病问卷
	private void getPrintData_wai() throws Exception {
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

			// testtag 数据
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

	// 问卷 add by liuqh 2008-11-21 癫痫病问卷
	private void getPrintData_epi() throws Exception {
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

			// testtag 数据
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

	// 问卷 modify by duanyh 2009-04-01 出国人员问卷
	private void getPrintData_abr() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("AbroadData.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	// 问卷 modify by duanyh 2009-04-01 健康问卷
	private void getPrintData_QuestionNaire() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();
			// 在加载问卷模板时 通过查询ldcode
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument(mQOperate[1], ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	// 问卷 modify by duanyh 2009-04-01 职业问卷
	// 问卷 add by liuqh 2008-11-21 财务情况补充问卷（甲）
	private void getPrintData_af1() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Addfinance1Data.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 危险职业问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d1() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			// String[] MeetTitle = new String[2];
			// MeetTitle[0] = "ID";
			// MeetTitle[1] = "ITEM";
			// ListTable tListTable = new ListTable();
			// tListTable.setName("WJ");

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("DangerOccupation.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			// xmlExport.addListTable(tListTable, MeetTitle); // 保存问题信息
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 机动车驾驶执照持有者问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d2() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Driver.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 赛车问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d3() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Racing.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 跳伞问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d4() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Jumping.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 爬山问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d5() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Climbing.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 危险运动问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d6() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("DangerSports.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-01 海运问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d7() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("OceanShipping.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-02 航空问卷
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d8() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Aviation.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); // 业务员编码.
			texttag.add("PrtNo", tPrtNo); // 印刷号
			texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlExport);

		} else {
			buildError("MeetF1BL", "已经打印面见通知书！");

		}
	}

	/**
	 * add by duanyh 2009-04-02 财务情况补充问卷（乙）
	 * 
	 * @throws Exception
	 */
	private void getPrintData_d9() throws Exception {
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		if (tLOPRTManagerDB.getStateFlag().equals("0")) {
			String tPrtNo = tLOPRTManagerDB.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setProposalContNo(tPrtNo);
			LCContSet tempLCContSet = tLCContDB.query();

			if (tempLCContSet.size() != 1) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在查询合同信息时出错！");
			}

			tLCContDB.setSchema(tempLCContSet.get(1));

			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
			if (!tLAAgentDB.getInfo()) {
				mErrors.copyAllErrors(tLAAgentDB.mErrors);
				throw new Exception("在查询代理人信息时出错");
			}
			mLAAgentSchema = tLAAgentDB.getSchema();

			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("Addfinance2Data.vts", ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			// texttag.add("AgentName", mLAAgentSchema.getName()); // 业务员
			// texttag.add("AgentCode", mLAAgentSchema.getAgentCode()); //
			// 业务员编码.
			// texttag.add("PrtNo", tPrtNo); // 印刷号
			// texttag.add("AppntName", tLCContDB.getAppntName()); // 业务员编码.
			// texttag.add("InsuredName", tLCContDB.getInsuredName()); // 业务员编码.

			// testtag 数据
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
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
	private void getContent(ListTable tListTable, String content,
			int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr;
		String content1 = "";
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content1 = strSpecReason.substring(0, nMaxCharsInOneLine);
			int enterInd = content1.indexOf("\r\n");
			if (enterInd != -1) {
				content = strSpecReason.substring(0, enterInd);
				strSpecReason = strSpecReason.substring(enterInd + 2);
				nSpecReasonLen -= enterInd + 2;

				strArr = new String[1];
				strArr[0] = content;
				tListTable.add(strArr);
			} else {
				content = strSpecReason.substring(0, nMaxCharsInOneLine);
				strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
				nSpecReasonLen -= nMaxCharsInOneLine;

				strArr = new String[1];
				strArr[0] = content;
				tListTable.add(strArr);
			}
		}

		if (nSpecReasonLen > 0) {
			int enterInd = strSpecReason.indexOf("\r\n");
			if (enterInd != -1) {
				content = strSpecReason.substring(0, enterInd);
				content1 = strSpecReason.substring(enterInd + 2);

				strArr = new String[1];
				strArr[0] = content;
				tListTable.add(strArr);
				String[] strArr1 = new String[1];
				strArr1[0] = content1;
				tListTable.add(strArr1);

			} else {
				strArr = new String[1];
				strArr[0] = strSpecReason;
				tListTable.add(strArr);
			}
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
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(q_sql);
		sqlbv22.put("no", no);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv22);
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
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql("update LCGrpIssuePol set needprint='N' where grpcontno='"
				+ "?strPolNo?" + "' and issuetype not in ('99','15')");
		sqlbv23.put("strPolNo", strPolNo);
		map.put(sqlbv23, "UPDATE");
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
