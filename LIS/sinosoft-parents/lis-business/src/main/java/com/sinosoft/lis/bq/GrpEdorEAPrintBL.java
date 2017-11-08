package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : QianLy <QianLy@sinosoft.com.cn>
 * @version  : 1.3
 * @date     : 2006-06-20
 * @Modified By QianLy on 2006-10-19 for QC 7316 and 7317
 * @Modified By QianLy on 2006-11-24
 * @Modified By QianLy on 2006-12-19 支持产品组合
 * @direction: 团体保全公司解约批单和人名清单打印
 ******************************************************************************/

import java.util.Hashtable;
import java.util.Vector;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpEdorEAPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorEAPrintBL.class);
	// public GrpEdorEAPrintBL() {}

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema rLPGrpEdorItemSchema;
	private XmlExport rXmlExport;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	// FormulaOne 打印模板文件
	public static final String FormulaOneVTS = "GrpEdorNameListEA.vts";

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorEAPrintBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorEAPrintBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!printBatchBill())
			return false;
		if (!printNamesBill())
			return false;
		if (!prepareOutputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorEAPrintBL.submitData() 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> GrpEdorEAPrintBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// XmlExport
		rXmlExport = new XmlExport();
		rXmlExport = (XmlExport) rInputData.getObjectByObjectName("XmlExport",
				0);
		if (rXmlExport == null) {
			CError.buildErr(this, "无法获取 XML 数据信息！");
			logger.debug("\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 XmlExport 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = rLPGrpEdorItemSchema.getEdorAcceptNo();
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		String sEdorNo = rLPGrpEdorItemSchema.getEdorNo();
		if (sEdorNo == null || sEdorNo.equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		String sEdorType = rLPGrpEdorItemSchema.getEdorType();
		if (sEdorType == null || sEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 合同号码
		String sGrpContNo = rLPGrpEdorItemSchema.getGrpContNo();
		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// logger.debug("\t@> GrpEdorEAPrintBL.getInputData() 成功");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> GrpEdorEAPrintBL.checkData() 开始");

		// 检查团体保全项目明细表
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(rLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		try {
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团体保全项目明细表出现异常！");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在团体保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));
		}
		// 垃圾处理
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// logger.debug("\t@> GrpEdorEAPrintBL.checkData() 成功");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 生成打印批单需要的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean printBatchBill() {
		// logger.debug("\t@> GrpEdorEAPrintBL.printBatchBill() 开始");

		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SSRS tSS = new SSRS();
		TextTag tTextTag = new TextTag();

		// ----------------------------------------------------------------------

		// 题头字段的赋值
		rXmlExport.addDisplayControl("displayHead");
		QuerySQL = "select GrpName " + "from LCGrpCont " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		// logger.debug(QuerySQL);
		String sGrpName = new String("");
		try {
			sGrpName = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体合同表获取团体单位名称出现异常！");
			return false;
		}
		tTextTag.add("GrpName", sGrpName);
		tTextTag.add("EdorNo", rLPGrpEdorItemSchema.getEdorNo());
		tTextTag.add("GrpContNo", rLPGrpEdorItemSchema.getGrpContNo());

		// ----------------------------------------------------------------------

		// 表格主险的赋值
		rXmlExport.addDisplayControl("displayEA");

		QuerySQL = "select (select RiskShortName " + "from LMRisk "
				+ "where 1 = 1 " + "and RiskCode = a.RiskCode), "
				+ "(select nvl(sum(GetMoney), 0) " + "from LJSGetEndorse "
				+ "where 1 = 1 " + "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_BaseCashValue
				+ "%'), " // 基本保额应退金额
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_AddPremHealth
				+ "%'), " // 健康加费退费金额
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_AddPremOccupation
				+ "%'), " // 职业加费退费金额
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_BonusCashValue
				+ "%'), " // 累计红利保额应退金额
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_FinaBonus
				+ "%'), " // 终了红利
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_MorePrem
				+ "%'), " // 多收保费退还
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_LoanCorpus
				+ "%'), " // 保单质押贷款本金
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_LoanCorpusInterest
				+ "%'), " // 保单质押贷款利息
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType in("
				+ "'"
				+ BqCode.Get_BaseCashValue
				+ "', "
				+ "'"
				+ BqCode.Get_AddPremHealth
				+ "', "
				+ "'"
				+ BqCode.Get_AddPremOccupation
				+ "', "
				+ "'"
				+ BqCode.Get_BonusCashValue
				+ "', "
				+ "'"
				+ BqCode.Get_FinaBonus
				+ "', "
				+ "'"
				+ BqCode.Get_MorePrem
				+ "', "
				+ "'"
				+ BqCode.Get_LoanCorpus
				+ "', "
				+ "'"
				+ BqCode.Get_LoanCorpusInterest
				+ "'"
				+ ")) "
				+ "from LPGrpPol a "
				+ "where 1 = 1 "
				+ "and a.GrpContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ "and a.EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and a.EdorType = '"
				+ rLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and exists (select 'X' "
				+ "from LMRiskApp "
				+ "where 1 = 1 "
				+ "and RiskCode = a.RiskCode "
				+ "and SubRiskFlag = 'M')";
		// logger.debug(QuerySQL);
		try {
			tSSRS = tExeSQL.execSQL(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询获取主险相关退费信息出现异常！");
			return false;
		}
		double sumMoney = 0.00;
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			tTextTag.add("EA.Reason", rLPGrpEdorItemSchema.getEdorReason());// Add
																			// By
																			// QianLy
																			// on
																			// 2006-10-19
																			// for
																			// QC
																			// 7317
			tTextTag.add("EA.MainPolName", tSSRS.GetText(1, 1));
			tTextTag.add("EA.MainPolRefund", BqNameFun.chgMoney(tSSRS.GetText(
					1, 2)));
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 3)).equals("0.00")) {// Add
																			// By
																			// QianLy
																			// on
																			// 2006-12-01
				rXmlExport.addDisplayControl("displayEAHA");// Add By QianLy on
															// 2006-12-01
				tTextTag.add("EA.HeathPrem", BqNameFun.chgMoney(tSSRS.GetText(
						1, 3)));
			}
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 4)).equals("0.00")) {// Add
																			// By
																			// QianLy
																			// on
																			// 2006-12-01
				rXmlExport.addDisplayControl("displayEAOA");// Add By QianLy on
															// 2006-12-01
				tTextTag.add("EA.OccupationPrem", BqNameFun.chgMoney(tSSRS
						.GetText(1, 4)));
			}
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 5)).equals("0.00")) {// Add
																			// By
																			// QianLy
																			// on
																			// 2006-11-24
				rXmlExport.addDisplayControl("displayEABns");// Add By QianLy
																// on 2006-11-24
				tTextTag.add("EA.TotalBonus", BqNameFun.chgMoney(tSSRS.GetText(
						1, 5)));
				tTextTag.add("EA.FinalBonus", BqNameFun.chgMoney(tSSRS.GetText(
						1, 6)));
			}
			// tTextTag.add("EA.ExcessivePrem",
			// BqNameFun.chgMoney(tSSRS.GetText(1, 7)));
			sumMoney = Math.abs(Double.parseDouble(tSSRS.GetText(1, 10)));
		}

		// ----------------------------------------------------------------------
		// 表格附加险赋值
		QuerySQL = "select distinct (select RiskShortName "
				+ "from LMRisk "
				+ "where 1 = 1 "
				+ "and RiskCode = a.RiskCode), "
				+ "'附加险', "
				+ "(select nvl(sum(GetMoney), 0) "
				+ "from LJSGetEndorse "
				+ "where 1 = 1 "
				+ "and EndorsementNo = a.EdorNo "
				+ "and FeeOperationType = a.EdorType "
				+ "and GrpPolNo = a.GrpPolNo "
				+ "and SubFeeOperationType like '"
				+ BqCode.Get_BaseCashValue
				+ "%') " // 基本保额应退金额
				+ "from LPPol a " + "where 1 = 1 " + "and a.EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and a.EdorType = '" + rLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and a.GrpContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and exists (select 'X' " + "from LMRiskApp "
				+ "where 1 = 1 " + "and RiskCode = a.RiskCode "
				+ "and SubRiskFlag = 'S')";
		// logger.debug(QuerySQL);
		try {
			tSS = tExeSQL.execSQL(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询获取附加险相关退费信息出现异常！");
			return false;
		}
		if (tSS != null && tSS.getMaxRow() > 0) {
			rXmlExport.addDisplayControl("displayEADP");// Add By QianLy on
														// 2006-10-19 for QC
														// 7317
			// ListTitle
			String[] arrListTitle = new String[tSS.getMaxCol()];
			try {
				for (int j = 0; j < tSS.getMaxCol(); j++) {
					arrListTitle[j] = "F1_Title_" + String.valueOf(j);
				}
			} catch (Exception ex) {
				CError.buildErr(this, "初始化列表标题错误！");
				logger.debug("\t@> GrpEdorEAPrintBL.printBatchBill() : ListTitle[] 赋值异常！");
				return false;
			}

			// ------------------------------------------------------------------

			// ListTable
			ListTable tListTable = new ListTable();
			tListTable.setName("EA.AddonPolProp");
			String[] arrOutResult;
			try {
				for (int k = 1; k <= tSS.getMaxRow(); k++) {
					arrOutResult = new String[arrListTitle.length];
					arrOutResult[0] = tSS.GetText(k, 1);
					arrOutResult[1] = tSS.GetText(k, 2);
					arrOutResult[2] = BqNameFun.chgMoney(tSS.GetText(k, 3));
					sumMoney += Math.abs(Double.parseDouble(tSS.GetText(k, 3)));
					tListTable.add(arrOutResult);
				}
			} catch (Exception ex) {
				CError.buildErr(this, "获取记录集到表格错误！");
				logger.debug("\t@> GrpEdorEAPrintBL.printBatchBill() : ListTable[] 赋值异常！");
				return false;
			}

			// ------------------------------------------------------------------

			// 输出数据
			rXmlExport.addListTable(tListTable, arrListTitle);
			tListTable = null;
		}
		// ----------------------------------------------------------------------
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 8)).equals("0.00")
					|| !BqNameFun.chgMoney(tSSRS.GetText(1, 9)).equals("0.00")) {// Add
																					// By
																					// QianLy
																					// on
																					// 2006-12-01
				rXmlExport.addDisplayControl("displayEAKF");
			}
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 8)).equals("0.00")) {// Add
																			// By
																			// QianLy
																			// on
																			// 2006-12-01
				rXmlExport.addDisplayControl("displayEALB");
				tTextTag.add("EA.LoanMoney", BqNameFun.chgMoney(tSSRS.GetText(
						1, 8)));
			}
			if (!BqNameFun.chgMoney(tSSRS.GetText(1, 9)).equals("0.00")) {// Add
																			// By
																			// QianLy
																			// on
																			// 2006-12-01
				rXmlExport.addDisplayControl("displayEALX");
				tTextTag.add("EA.LoanInterest", BqNameFun.chgMoney(tSSRS
						.GetText(1, 9)));
			}
			rXmlExport.addDisplayControl("displayEAST");
			tTextTag.add("EA.ActuPayMoney", BqNameFun.chgMoney(sumMoney));// Add
																			// By
																			// QianLy
																			// on
																			// 2006-10-19
																			// for
																			// QC
																			// 7317
		}
		// 底端字段的赋值
		rXmlExport.addDisplayControl("displayTail");
		tTextTag.add("EdorValiDate", rLPGrpEdorItemSchema.getEdorValiDate());
		tTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(rLPGrpEdorItemSchema.getApproveOperator()));
		tTextTag.add("Operator", CodeNameQuery.getOperator(rLPGrpEdorItemSchema
				.getOperator()));
		QuerySQL = "select AgentCode " + "from LCGrpCont " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		// logger.debug(QuerySQL);
		String sAgentCode = new String("");
		try {
			sAgentCode = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体合同表获取代理人编码出现异常！");
			return false;
		}
		if (sAgentCode != null && !sAgentCode.trim().equals("")) {
			String[] arrAgentInfo = BqNameFun.getAllNames(sAgentCode);
			tTextTag.add("LAAgent.Name", arrAgentInfo[BqNameFun.AGENT_NAME]);
			tTextTag.add("LAAgent.AgentCode", sAgentCode);
		}

		// ----------------------------------------------------------------------

		rXmlExport.addTextTag(tTextTag);
		// 垃圾处理
		tExeSQL = null;
		tSSRS = null;
		tTextTag = null;

		// logger.debug("\t@> GrpEdorEAPrintBL.printBatchBill() 成功");
		return true;
	} // function printBatchBill end

	// ==========================================================================

	/**
	 * 生成人名清单需要的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean printNamesBill() {
		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		String QuerySQL = new String("");
		ExeSQL tES = new ExeSQL();
		SSRS tSSRS = new SSRS();
		TextTag tTextTag = new TextTag();

		// 输出文档
		XmlExport tXmlPrint = new XmlExport();
		try {
			tXmlPrint.createDocument(FormulaOneVTS, "printer"); // 初始化 XML 文档
		} catch (Exception ex) {
			CError.buildErr(this, "初始化打印模板错误！");
			logger.debug("\t@> GrpEdorEAPrintBL.printNamesBill() : 设置 FormulaOne VTS 文件异常！");
			return false;
		}

		// 题头字段的赋值
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		if (tLCGrpContDB.query() == null || tLCGrpContDB.query().size() < 1) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.query().get(1);

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		tTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		tTextTag.add("EdorNo", rLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		tTextTag.add("GrpContNo", tLCGrpContSchema.getGrpContNo()); // 团体保单号
		tTextTag.add("EdorValiDate", rLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		tTextTag.add("Operator", CodeNameQuery.getOperator(rLPGrpEdorItemSchema
				.getOperator())); // 经办人
		tTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(rLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		tTextTag.add("AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		tTextTag.add("AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		Hashtable RiskTable = new Hashtable();

		String isMulProd = BqNameFun.isMulProd(tLCGrpContSchema.getGrpContNo());
		if (isMulProd.toUpperCase().equals("FAILED")) {
			CError.buildErr(this, "查询团单是否是险种组合失败!");
			return false;
		}
		String SQL = "";
		if (isMulProd.toUpperCase().equals("FALSE")) {
			SQL = "select yx,kh,xm,xz,jb,lj,zl,jf,jb+lj+zl+jf xj,jg from ("
					+ " select (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) yx,"
					+ " b.insuredno kh,"
					+ " b.insuredname xm,"
					+ " (select origriskcode || riskshortname from lmrisk where riskcode = a.riskcode) xz,"
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
					+ BqCode.Get_BaseCashValue
					+ "%') jb," // 基本保额应退金额
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
					+ BqCode.Get_BonusCashValue
					+ "%') lj," // 累计红利保额应退金额
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
					+ BqCode.Get_FinaBonus
					+ "%') zl," // 终了红利
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')) jf," // 加费
					+ " b.managecom jg"
					+ " from lppol a,lpcont b"
					+ " where 1=1 "
					+ " and b.edorno = '"
					+ rLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ " and b.edortype = '"
					+ rLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ " and b.grpcontno = '"
					+ rLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ " and a.edorno = b.edorno"
					+ " and a.contno = b.contno"
					+ " order by yx asc" + " )";
		} else if (isMulProd.toUpperCase().equals("TRUE")) {
			SQL = " select c1,c2,c3,c4,nvl(c5,0),nvl(c6,0),nvl(c7,0),nvl(c8,0),nvl(c5,0)+nvl(c6,0)+nvl(c7,0)+nvl(c8,0) c9,c10 from ("
					+ " select (select CustomerSeqNo"
					+ "           from lcinsured"
					+ "          where contno = a.contno"
					+ "            and insuredno = a.insuredno) c1,"
					+ "        a.insuredno c2,"
					+ "        a.insuredname c3,"
					+ "        (select dutyname from lmduty where dutycode = c.dutycode) c4,"
					+ "        (select nvl(sum(getmoney), 0)"
					+ "           from ljsgetendorse"
					+ "          where endorsementno = b.edorno"
					+ "            and feeoperationtype = b.edortype"
					+ "            and contno = b.contno"
					+ "            and polno = b.polno"
					+ "            and dutycode = c.dutycode"
					+ "            and subfeeoperationtype like '%"
					+ BqCode.Get_BaseCashValue
					+ "%'"
					+ "          group by contno, polno, dutycode) c5,"
					+ "        (select nvl(sum(getmoney), 0)"
					+ "           from ljsgetendorse"
					+ "          where endorsementno = b.edorno"
					+ "            and feeoperationtype = b.edortype"
					+ "            and contno = b.contno"
					+ "            and polno = b.polno"
					+ "            and dutycode = c.dutycode"
					+ "            and subfeeoperationtype like '%"
					+ BqCode.Get_BonusCashValue
					+ "%'"
					+ "          group by contno, polno, dutycode) c6,"
					+ "        (select nvl(sum(getmoney), 0)"
					+ "           from ljsgetendorse"
					+ "          where endorsementno = b.edorno"
					+ "            and feeoperationtype = b.edortype"
					+ "            and contno = b.contno"
					+ "            and polno = b.polno"
					+ "            and dutycode = c.dutycode"
					+ "            and subfeeoperationtype like '%"
					+ BqCode.Get_FinaBonus
					+ "%'"
					+ "          group by contno, polno, dutycode) c7,"
					+ "        (select nvl(sum(getmoney), 0)"
					+ "           from ljsgetendorse"
					+ "          where endorsementno = b.edorno"
					+ "            and feeoperationtype = b.edortype"
					+ "            and contno = b.contno"
					+ "            and polno = b.polno"
					+ "            and dutycode = c.dutycode"
					+ "            and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')"
					+ "          group by contno, polno, dutycode) c8,"
					+ "        a.managecom c10"
					+ "   from lppol b,"
					+ "        lpcont a,"
					+ "        lcduty c"
					+ "  where b.contno = a.contno"
					+ "    and b.edorno = a.edorno"
					+ "    and b.polno = c.polno"
					+ "    and b.edorno = '"
					+ rLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ "    and b.edortype = '"
					+ rLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "    and b.grpcontno = '"
					+ rLPGrpEdorItemSchema.getGrpContNo() + "'" + "    )" + "";
		}

		tSSRS = tES.execSQL(SQL);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError.buildErr(this, "查询被保人退保详细信息失败!");
			return false;
		}
		int VTS_COLUMN = 10;// 模板中的横向字段个数，包括序号列,在此定义,便于维护
		String[] tContListTitle = new String[VTS_COLUMN];
		for (int iTitle = 0; iTitle < VTS_COLUMN; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strLine = new String[VTS_COLUMN];
			for (int j = 0; j < VTS_COLUMN; j++) {
				if (j >= 4 && j <= 8) {
					strLine[j] = BqNameFun.getRoundMoney(tSSRS
							.GetText(i, j + 1));
				} else {
					strLine[j] = tSSRS.GetText(i, j + 1);
				}
			}
			// 添加到小计处理
			if (!vct.contains(strLine[3])) {
				vct.add(strLine[3]);
			}
			// ------------------------------------------------------------
			if (!BqNameFun.dealHashTable(RiskTable, strLine[3]
					+ "BaseCashValue", strLine[4])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "BonusCashValue", strLine[5])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "FinaBonus", strLine[6])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "AddFee", strLine[7])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "GetMoney", strLine[8])
					|| !BqNameFun.dealHashTable(RiskTable, "SumGetMoney",
							strLine[8])) {
				return false;
			}
			tContListTable.add(strLine);
		}
		tXmlPrint.addListTable(tContListTable, tContListTitle);

		// 处理小计信息
		int VTS_COLUMN_XJ = 6;// 小计中的列数
		String[] xContListTitle = new String[VTS_COLUMN_XJ];
		for (int tTitle = 0; tTitle < VTS_COLUMN_XJ; tTitle++) {
			xContListTitle[tTitle] = "Billrisk" + String.valueOf(tTitle);
		}
		ListTable xContListTable = new ListTable();
		xContListTable.setName("BILLRISK");
		String[] riskLine;

		for (int i = 1; i <= vct.size(); i++) {
			riskLine = new String[VTS_COLUMN_XJ];
			riskLine[0] = vct.get(i - 1).toString();
			riskLine[1] = BqNameFun.getRoundMoney((String) RiskTable
					.get(riskLine[0] + "BaseCashValue") == null ? "0.00"
					: (String) RiskTable.get(riskLine[0] + "BaseCashValue"));
			riskLine[2] = BqNameFun.getRoundMoney((String) RiskTable
					.get(riskLine[0] + "BonusCashValue") == null ? "0.00"
					: (String) RiskTable.get(riskLine[0] + "BonusCashValue"));
			riskLine[3] = BqNameFun.getRoundMoney((String) RiskTable
					.get(riskLine[0] + "FinaBonus") == null ? "0.00"
					: (String) RiskTable.get(riskLine[0] + "FinaBonus"));
			riskLine[4] = BqNameFun.getRoundMoney((String) RiskTable
					.get(riskLine[0] + "AddFee") == null ? "0.00"
					: (String) RiskTable.get(riskLine[0] + "AddFee"));
			riskLine[5] = BqNameFun.getRoundMoney((String) RiskTable
					.get(riskLine[0] + "GetMoney") == null ? "0.00"
					: (String) RiskTable.get(riskLine[0] + "GetMoney"));
			xContListTable.add(riskLine);
		}
		tXmlPrint.addListTable(xContListTable, xContListTitle);

		tTextTag.add("SumGetMoney", BqNameFun.chgMoney((String) RiskTable
				.get("SumGetMoney") == null ? "0.00" : (String) RiskTable
				.get("SumGetMoney")));
		tXmlPrint.addTextTag(tTextTag);
		vct = null;
		RiskTable = null;
		tXmlPrint.outputDocumentToFile("D:\\qly\\XMLDoc\\", "QlyGrpEANameList");

		// 删除 LPEdorPrint2
		String DeleteSQL = new String("");
		DeleteSQL = "delete from LPEdorPrint2 " + "where 1 = 1 "
				+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 插入 LPEdorPrint2
		LPEdorPrint2Schema tLPEdorPrint2Schema = new LPEdorPrint2Schema();
		tLPEdorPrint2Schema.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPEdorPrint2Schema.setManageCom(rLPGrpEdorItemSchema.getManageCom());
		tLPEdorPrint2Schema.setPrtFlag("N");
		tLPEdorPrint2Schema.setPrtTimes(0);
		tLPEdorPrint2Schema.setEdorInfo(tXmlPrint.getInputStream());
		tLPEdorPrint2Schema.setOperator(rGlobalInput.Operator);
		tLPEdorPrint2Schema.setMakeDate(sCurrentDate);
		tLPEdorPrint2Schema.setMakeTime(sCurrentTime);
		tLPEdorPrint2Schema.setModifyDate(sCurrentDate);
		tLPEdorPrint2Schema.setModifyTime(sCurrentTime);
		rMap.put(tLPEdorPrint2Schema, "BLOBINSERT");
		tLPEdorPrint2Schema = null;

		// ----------------------------------------------------------------------

		// 垃圾处理
		tES = null;
		tSSRS = null;
		tTextTag = null;
		tXmlPrint = null;

		// logger.debug("\t@> GrpEdorEAPrintBL.printNamesBill() 成功");
		return true;
	} // function printNamesBill end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		// logger.debug("\t@> GrpEdorEAPrintBL.prepareOutputData() 开始");

		rResultData = new VData();
		rResultData.addElement(rXmlExport);
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorEAPrintBL.prepareOutputData() 成功");
		return true;
	} // function prepareOutputData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rLPGrpEdorItemSchema != null)
			rLPGrpEdorItemSchema = null;
		if (rXmlExport != null)
			rXmlExport = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		VData tVData = new VData();
		tVData.add(mGlobalInput);

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo("6120091219000005");
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
		tVData.add(tLPGrpEdorItemSchema);

		XmlExport tXmlExport = new XmlExport();
		try {
			tXmlExport.createDocument(FormulaOneVTS, "printer"); // 初始化 XML
																	// 文档
		} catch (Exception ex) {
			logger.debug("\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne VTS 文件异常！");
		}
		tVData.add(tXmlExport);

		GrpEdorEAPrintBL tGrpEdorPrintBL = new GrpEdorEAPrintBL();
		if (tGrpEdorPrintBL.submitData(tVData, "PRINTEA")) {
			logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		}
	}


} // class GrpEdorEAPrintBL end
