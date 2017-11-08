package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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

public class BufeeF1PBL implements PrintService {
private static Logger logger = Logger.getLogger(BufeeF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
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

	public BufeeF1PBL() {
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
					&& !cOperate.equals("PRINT2")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();

			}
			if (cOperate.equals("PRINT2")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData1();

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

		cError.moduleName = "BufeeF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		double sumprem = 0;
		String mSumPrem = "";
		double dif = 0;
		boolean PremRate = true; // 打印加费部分的判断标志

		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("Bufee.vts", "printer"); // 最好紧接着就初始化xml文档

		LCContDB tLCGrpContDB = new LCContDB();
		// LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		// if (tLOPRTManagerDB.getInfo() == false) {
		// mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
		// throw new Exception("在取得打印队列中数据时发生错误");
		// }

		// mLOPRTManagerSchema = tLOPRTManagerDB;

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
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tStrSQL = "select sum(PayMoney) from LJTempFee where 1=1"
				+ "    and confflag = '0'"
				+ "   and (EnterAccDate is not null and EnterAccDate <> to_date('3000-01-01','yyyy-mm-dd'))"
				+ " and (TempFeeType in ('0','6','1')and otherno in (select contno from lccont where ContNo='"
				+ "?strPolNo?" + "'))";
		sqlbv1.sql(tStrSQL);
		sqlbv1.put("strPolNo", strPolNo);
		ExeSQL tExeSQL = new ExeSQL();

		mRealPayMoney = tExeSQL.getOneValue(sqlbv1);
		if (mRealPayMoney.equals(null) || mRealPayMoney.equals("0")
				|| mRealPayMoney.equals("")) {
			mRealPayMoney = "0.00";
		} else {
			xmlExport.addDisplayControl("displaymoney");
		}

		logger.debug("实际交的金额:" + mRealPayMoney);

		tStrSQL = "select BankAccNo from LJTempFeeClass where "
				+ " OtherNo  = '" + "?strPolNo?" + "' ";
		sqlbv1.sql(tStrSQL);
		sqlbv1.put("strPolNo", strPolNo);
		SSRS MMSSRS = new SSRS();
		MMSSRS = tExeSQL.execSQL(sqlbv1);
		String BankNo = "";
		if (MMSSRS != null && MMSSRS.getMaxRow() > 0) {
			BankNo = MMSSRS.GetText(1, 1);
		}

		String q_sql = "";
		// q_sql = "select IssueCont,IssueType from LCIssuePol where BackObjType
		// = '2' and NeedPrint = 'Y' and PrtSeq = '"
		// + mLOPRTManagerSchema.getOldPrtSeq()
		// + "'";
		// logger.debug("问题件中所执行的sql是" + q_sql);
		ExeSQL q_exesql = new ExeSQL();
		// SSRS q_ssrs = new SSRS();
		// q_ssrs = q_exesql.execSQL(q_sql);
		//
		// if ((q_ssrs.GetText(1, 1).equals("0") ||
		// q_ssrs.GetText(1, 1).trim().equals("") ||
		// q_ssrs.GetText(1, 1).equals("null"))) {
		// logger.debug("没有问题件记录");
		// } else {
		// logger.debug("问题中有" + q_ssrs.getMaxRow() + "条记录！！！");
		//
		// }

		// ListTable tlistTable = new ListTable();
		// String strArr[] = null;
		//
		// tlistTable.setName("QUESTION");
		// for (int i = 1; i <= q_ssrs.getMaxRow(); i++) {
		// strArr = new String[1];
		//
		// strArr[0] = i + "." + q_ssrs.GetText(i, 1);
		// tlistTable.add(strArr);
		// if (q_ssrs.GetText(i, 2).equals("01") ||
		// q_ssrs.GetText(i, 2).equals("02") ||
		// q_ssrs.GetText(i, 2).equals("03")) {
		// PremRate = true;
		// }
		// }

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
				strRisk[3] = "" + tLCPolSet.get(j).getAmnt();
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
			if ((!tLCPolSet.get(j).getPolNo().equals(
					tLCPolSet.get(j).getMainPolNo()))
					&& (!tLCPolSet.get(j).getUWFlag().equals("1")
							&& !tLCPolSet.get(j).getUWFlag().equals("2") && !tLCPolSet
							.get(j).getUWFlag().equals("a"))) {
				subriskno = sriskno + "";
				strRisk[0] = "险种" + subriskno;
				strRisk[1] = tLCPolSet.get(j).getRiskCode();
				strRisk[2] = getRiskName(tLCPolSet.get(j).getRiskCode());
				strRisk[3] = "" + tLCPolSet.get(j).getAmnt();
				strRisk[4] = "￥ "
						+ mDecimalFormat.format(new Double(tLCPolSet.get(j)
								.getPrem()));
				sumprem = sumprem + tLCPolSet.get(j).getPrem();
				mSumPrem = "￥ " + mDecimalFormat.format(sumprem);
				tRiskListTable.add(strRisk);
				sriskno = sriskno + 1;
			}
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String SQL = "select (select (case subriskflag when 'M' then '险种1' when 'S' then '险种' end) from lmriskapp a where a.riskcode = b.riskcode), b.riskcode,b.paymoney from LJTempFee b where "
				+ " b.enteraccdate is not null and (TempFeeType in ('0','6','1')and otherno in (select contno from lccont where ContNo='"
				+ "?strPolNo?" + "'))";
		sqlbv3.sql(SQL);
		sqlbv3.put("strPolNo", strPolNo);
		SSRS CNMSSRS = new SSRS();
		CNMSSRS = q_exesql.execSQL(sqlbv3);

		if (CNMSSRS != null && CNMSSRS.getMaxRow() > 0) {
			for (int j = 1; j <= CNMSSRS.getMaxRow(); j++) {
				if (CNMSSRS.GetText(j, 1).equals("险种1")) {
					strFee = new String[4];
					strFee[0] = CNMSSRS.GetText(j, 1);
					strFee[1] = CNMSSRS.GetText(j, 2);
					strFee[2] = getRiskName(CNMSSRS.GetText(j, 2));
					// strFee[3] = CNMSSRS.GetText(j, 3);
					strFee[3] = "￥ "
							+ mDecimalFormat.format(new Double(CNMSSRS.GetText(
									j, 3)));
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
							+ mDecimalFormat.format(new Double(CNMSSRS.GetText(
									j, 3)));
					tFeeListTable.add(strFee);
					sriskno = sriskno + 1;
				}
			}
			xmlExport.addListTable(tFeeListTable, RiskTitle);
		}

		TextTag texttag = new TextTag();

		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		sqlbv4.sql(tSql);
		sqlbv4.put("contno", tLCGrpContDB.getContNo());
		SSRS PolSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		PolSSRS = tempExeSQL.execSQL(sqlbv4);
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
		tSql = "select payintv,payyears from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		sqlbv4.sql(tSql);
		sqlbv4.put("contno", tLCGrpContDB.getContNo());
		SSRS PeriodSSRS = new SSRS();
		PeriodSSRS = tempExeSQL.execSQL(sqlbv4);

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
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		texttag.add("BarCode1", "UN016");
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
		// texttag.add("PrtNo", tLCGrpContDB.getPrtNo());
		// texttag.add("LCContl.Operator",
		// mLOPRTManagerSchema.getReqOperator());
		texttag.add("LCCont.ProposalContNo", tLCGrpContDB.getContNo());
		texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB.getAgentCode()));
		texttag.add("LCCont.InsuredName", tLCGrpContDB.getInsuredName());
		texttag.add("LAAgent.AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageComName", tManageComName);
		texttag.add("LABranchGroup.Name", getComName(tLCGrpContDB
				.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("Bank", getBankName(tLCGrpContDB.getBankCode()));
		// texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		// texttag.add("Operator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("BankAccount", BankNo);
		texttag.add("Period", Period);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("SysDate", df.format(new Date()));

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		// xmlExport.addListTable(tlistTable, strArr);
		logger.debug("SumPrem=" + tLCGrpContDB.getPrem());
		TextTag ttexttag = new TextTag();
		// mSumprem = mDecimalFormat.format(sumprem);

		ttexttag.add("SumPrem", "￥ " + mLOPRTManagerSchema.getStandbyFlag1());
		double Sumprem = tLCGrpContDB.getPrem();
		String tRealPayMoney = "￥ "
				+ mDecimalFormat.format(new Double(mRealPayMoney));
		ttexttag.add("PaidPrem", tRealPayMoney);
		if (mRealPayMoney.equals(null) || (mRealPayMoney).trim().equals("")
				|| mRealPayMoney.equals("0")) {
			ttexttag.add("Dif", mLOPRTManagerSchema.getStandbyFlag1());
			dif = sumprem;
		} else {
			sumprem = Double.parseDouble(mLOPRTManagerSchema.getStandbyFlag1())
					- Double.parseDouble(mRealPayMoney);
			String mSumprem = "￥ " + mDecimalFormat.format(sumprem);

			ttexttag.add("Dif", mSumprem);
			dif = sumprem;
		}
		if (ttexttag.size() > 0) {
			xmlExport.addTextTag(ttexttag);
		}
		if (PremRate == true && mLOPRTManagerSchema.getStandbyFlag2() == null) {
			xmlExport.addDisplayControl("displayMAIN"); // 模版上的加费部分的控制标记
			xmlExport.addListTable(tRiskListTable, RiskTitle); // 保存加费信息
		}

		if (dif > 0) {
			xmlExport.addDisplayControl("displayType"); // 模版上的加费部分的控制标记
		}
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	private void getPrintData1() throws Exception {

		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("GrpRateFault.vts", "printer"); // 最好紧接着就初始化xml文档

		String mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		logger.debug("测试 呵呵，，，===" + mLOPRTManagerSchema.getPrtSeq());
		String temprtseq = mLOPRTManagerSchema.getPrtSeq();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);

		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
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
				throw new Exception("在获取保单信息时出错！");
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

				throw new Exception("在取得打印队列中数据时发生错误");

			}
			mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
			if (!tLABranchGroupDB.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDB.mErrors);

				throw new Exception("在取得打印队列中数据时发生错误");

			}

			strPolNo = tLCGrpContDB.getGrpContNo();
			// logger.debug("测试 by
			// yaory"+mLOPRTManagerSchema.getPrtSeq());
			String q_sql = "";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			q_sql = "select IssueCont from LCGrpIssuePol where grpcontno='"
					+ "?strPolNo?"
					+ "' and issuetype not in ('99','15') and NeedPrint = 'Y'"; //
			sqlbv6.sql(q_sql);
			sqlbv6.put("strPolNo", strPolNo);
			ExeSQL q_exesql = new ExeSQL();
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv6);
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

			texttag.add("BarCode1", "UN016");
			texttag
					.add(
							"BarCodeParam1",
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

	// 下面是一些辅助函数

	private String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得主险LMRisk的数据时发生错误");
		}

		return tLMRiskDB.getRiskShortName();
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

	private String GetAppntname(String no) {
		String name = "";
		String q_sql = "";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		q_sql = "select name from lcgrpappnt where grpcontno='" + "?no?" + "'";
		sqlbv7.sql(q_sql);
		sqlbv7.put("no", no);
		ExeSQL q_exesql = new ExeSQL();
		SSRS q_ssrs = new SSRS();
		q_ssrs = q_exesql.execSQL(sqlbv7);
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

	private String getComName(String strComCode) throws Exception {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			throw new Exception("在取得LDCom的数据时发生错误");
		}
		return tLDComDB.getName();
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
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("update LCGrpIssuePol set needprint='N' where grpcontno='"
				+ "?strPolNo?" + "' and issuetype not in ('99','15')");
		sqlbv8.put("strPolNo", strPolNo);
		map.put(sqlbv8, "UPDATE");
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

	private void jbInit() throws Exception {
	}

}
