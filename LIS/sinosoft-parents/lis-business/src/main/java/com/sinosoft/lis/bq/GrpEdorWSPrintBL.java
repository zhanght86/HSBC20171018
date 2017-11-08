package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 周磊 <
 * @version  : 1.00
 * @date     : 2006-10-16
 * @Modified By 周磊 on 2006-12-28 for ST 576：产品组合输出
 * @direction: 团体保全新增附件险(WS)批单和人名清单打印
 ******************************************************************************/

import java.util.Hashtable;
import java.util.Vector;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

// ******************************************************************************

public class GrpEdorWSPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorWSPrintBL.class);
	public GrpEdorWSPrintBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// public GrpEdorWSPrintBL() {}

	// ==========================================================================

	// 公共数据，错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 全局变量，输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema rLPGrpEdorItemSchema;
	private XmlExport rXmlExport;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	// FormulaOne 打印模板文件
	public static final String FormulaOneVTS = "GrpEdorNameListWS.vts";

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorWSPrintBL.submitData() 开始");

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

		// logger.debug("\t@> GrpEdorWSPrintBL.submitData() 成功");
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
		// logger.debug("\t@> GrpEdorWSPrintBL.getInputData() 开始");

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

		// logger.debug("\t@> GrpEdorWSPrintBL.getInputData() 成功");
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
		// logger.debug("\t@> GrpEdorWSPrintBL.checkData() 开始");

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

		// logger.debug("\t@> GrpEdorWSPrintBL.checkData() 成功");
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
		// logger.debug("\t@> GrpEdorWSPrintBL.printBatchBill() 开始");

		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
		// SSRS tSSRS = new SSRS();
		SSRS tSS = new SSRS();
		SSRS tS = new SSRS();
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
		tTextTag.add("GrpName", sGrpName);// 现单位名称
		tTextTag.add("EdorNo", rLPGrpEdorItemSchema.getEdorNo());// 团体批单号
		tTextTag.add("GrpContNo", rLPGrpEdorItemSchema.getGrpContNo());// 团体保单号

		// ----------------------------------------------------------------------

		// 表格本次缴费合计的赋值
		rXmlExport.addDisplayControl("displayWS");

		QuerySQL = "select nvl(sum(GetMoney), 0) " + "from LjsGetEndorse a "
				+ "where 1 = 1 " + "and a.EndorsementNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and a.FeeOperationType = '"
				+ rLPGrpEdorItemSchema.getEdorType() + "' "
				+ "and a.GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";

		// logger.debug(QuerySQL);

		String sumMoney = new String("");
		try {
			sumMoney = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体缴费合计出现异常！");
			return false;
		}

		if (sumMoney == null || sumMoney.equals("")) {
			sumMoney = "0.00";
		}
		tTextTag.add("SumAmount", BqNameFun.chgMoney(sumMoney));// 本次交费合计

		// ----------------------------------------------------------------------
		// 表格人数合计赋值
		QuerySQL = "select count(InsuredNo) " + "from LPEdorItem a "
				+ "where 1 = 1 " + "and a.EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' ";
		// logger.debug(QuerySQL);
		String ppCount = tExeSQL.getOneValue(QuerySQL);
		if (ppCount == null || ppCount.equals("")) {
			ppCount = "0.00";
		}
		tTextTag.add("PpCount", ppCount); // 本次涉及人数合计

		QuerySQL = " select (select riskshortname from lmrisk where riskcode = p.riskcode),"
				+ " (mult + amnt),"// 在LPGrpPol表里保额和份数必有一项为0
				+ " (select cvalidate"
				+ " from lppol"
				+ " where grppolno = p.grppolno and edortype = p.edortype  and edorno = p.edorno"
				+ " and rownum = 1),"
				+ " (select enddate - 1"
				+ " from lppol"
				+ " where grppolno = p.grppolno and edortype = p.edortype  and edorno = p.edorno"
				+ " and rownum = 1),"
				+ " (select nvl(Sum(GetMoney), 0)"
				+ " from ljsgetendorse"
				+ " where grppolno = p.grppolno"
				+ " and feeoperationtype = p.edortype"
				+ " and endorsementno = p.edorno"
				+ " and subfeeoperationtype like '%"
				+ BqCode.Pay_Prem
				+ "%') p1,"
				+ " (select nvl(Sum(GetMoney), 0)"
				+ " from ljsgetendorse"
				+ " where grppolno = p.grppolno"
				+ " and feeoperationtype = p.edortype"
				+ " and endorsementno = p.edorno"
				+ " and subfeeoperationtype like '%"
				+ BqCode.Pay_InsurAddPremHealth
				+ "%') p2,"
				+ " (select nvl(Sum(GetMoney), 0)"
				+ " from ljsgetendorse"
				+ " where grppolno = p.grppolno"
				+ " and feeoperationtype = p.edortype"
				+ " and endorsementno = p.edorno"
				+ " and subfeeoperationtype like '%"
				+ BqCode.Get_AddPremOccupation
				+ "%') p3"
				+ " from lpgrppol p"
				+ " where 1 = 1"
				+ " and p.edortype = '"
				+ rLPGrpEdorItemSchema.getEdorType()
				+ "'"
				+ " and p.edorno = '"
				+ rLPGrpEdorItemSchema.getEdorNo()
				+ "'"
				+ " and p.grpcontno = '"
				+ rLPGrpEdorItemSchema.getGrpContNo()
				+ "'" + "";

		logger.debug(QuerySQL);
		try {
			tSS = tExeSQL.execSQL(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询获取新增附件险相关保费信息出现异常！");
			return false;
		}

		if (tSS == null || tSS.getMaxRow() < 1) {
			CError.buildErr(this, "查询新增附加险信息失败!");
			return false;
		}

		String[] tContListTitle = new String[7];
		for (int iTitle = 0; iTitle < 7; iTitle++) {
			tContListTitle[iTitle] = "WS" + String.valueOf(iTitle);
		}
		ListTable tContListTable = new ListTable();
		tContListTable.setName("WS");
		String strLine[] = null;
		for (int i = 1; i <= tSS.getMaxRow(); i++) {
			strLine = new String[7];
			strLine[0] = tSS.GetText(i, 1);
			strLine[1] = BqNameFun.chgMoney(tSS.GetText(i, 2));
			strLine[2] = tSS.GetText(i, 3);
			strLine[3] = tSS.GetText(i, 4);
			strLine[4] = BqNameFun.chgMoney(tSS.GetText(i, 5));
			strLine[5] = BqNameFun.chgMoney(tSS.GetText(i, 6));
			strLine[6] = BqNameFun.chgMoney(tSS.GetText(i, 7));
			tContListTable.add(strLine);
		}
		rXmlExport.addListTable(tContListTable, tContListTitle);

		// ----------------------------------------------------------------------
		// 核保特约

		QuerySQL = "   select PolNo " + "	 from LPPol "
				+ "	where GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + "	  and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo()
				+ "'";

		tS = tExeSQL.execSQL(QuerySQL);
		String[] strNSS = null;
		ListTable tNSSListTable = new ListTable();
		if (tS != null || tS.getMaxRow() > 0) {
			for (int j = 1; j <= tS.getMaxRow(); j++) {
				tNSSListTable.setName("NSS");

				strNSS = new String[2];
				strNSS[0] = tExeSQL
						.getOneValue("select riskshortname from lppol a, lmrisk b where a.polno = '"
								+ tS.GetText(j, 1)
								+ "' and a.riskcode = b.riskcode");
				strNSS[1] = tExeSQL
						.getOneValue("select speccontent from LPSpec where 1=1 "
								+ " and SerialNo = (select max(SerialNo) from lpspec where polno = '"
								+ tS.GetText(j, 1)
								+ "' and edorno = '"
								+ rLPGrpEdorItemSchema.getEdorAppNo() + "')");
				if (!"".equals(strNSS[1])) {
					tNSSListTable.add(strNSS);
					rXmlExport.addDisplayControl("displayNSSpec");
				}
			}
		}

		// -------------------------------------------------------------
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
		// tSSRS = null;
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
		// logger.debug("\t@> GrpEdorEAPrintBL.printNamesBill() 开始");

		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SSRS tSS = new SSRS();
		// SSRS tSS1 = new SSRS();
		// SSRS tSS2 = new SSRS();
		Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
		Hashtable RiskTable = new Hashtable();
		TextTag tTextTag = new TextTag();

		// ----------------------------------------------------------------------

		// 显示整单统计信息的数据
		// double dSumBaseCashValue = 0.00; //基本险应退金额
		// double dSumBonusCashValue = 0.00; //累计红利保额应退金额
		// double dSumFinaBonus = 0.00; //终了红利
		// double dTotalPayMoney = 0.00; //总的应退费用金额

		// ----------------------------------------------------------------------

		// 输出文档
		XmlExport tXmlPrint = new XmlExport();
		try {
			tXmlPrint.createDocument(FormulaOneVTS, "printer"); // 初始化 XML
			// 文档
		} catch (Exception ex) {
			CError.buildErr(this, "初始化打印模板错误！");
			logger.debug("\t@> GrpEdorEAPrintBL.printNamesBill() : 设置 FormulaOne VTS 文件异常！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 题头字段的赋值
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
		tTextTag.add("GrpName", sGrpName); // 现单位名称
		tTextTag.add("EdorNo", rLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		tTextTag.add("GrpContNo", rLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		tTextTag.add("EdorValiDate", rLPGrpEdorItemSchema.getEdorValiDate()); // 生效日

		// ----------------------------------------------------------------------

		// 中间表格的赋值
		// 查询被保人

		LPEdorItemDB rLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet rLPEdorItemSet = new LPEdorItemSet();
		rLPEdorItemDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		rLPEdorItemDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		rLPEdorItemDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());

		try {
			rLPEdorItemSet = rLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团体下个人保全项目明细表出现异常！");
			return false;
		}

		ListTable tListTable = new ListTable();
		tListTable.setName("BILL");
		ListTable ttlListTable = new ListTable();
		ttlListTable.setName("BILLRISK");

		// ListTitle
		int VTS_COLUMN = 8;// 模板中的横向字段个数，包括序号列,在此定义,便于维护
		String[] arrListTitle = new String[VTS_COLUMN]; // 不使用
		// tSSRS.getMaxCol(),
		// 因为还有值没有取到
		try {
			for (int j = 0; j < arrListTitle.length; j++) {
				arrListTitle[j] = "Bill" + String.valueOf(j);
			}
		} catch (Exception ex) {
			CError.buildErr(this, "初始化列表标题错误！");
			logger.debug("\t@> GrpEdorWSPrintBL.printNamesBill() : ListTitle[] 赋值异常！");
			return false;
		}

		// ------------------------------------------------------------------

		// ListTable
		String[] arrOutResult = null;

		// for (int PersonNo = 1; PersonNo <= rLPEdorItemSet.size(); PersonNo++)
		// {
		//
		// LPEdorItemSchema rLPEdorItemSchema = null;
		// rLPEdorItemSchema = rLPEdorItemSet.get(PersonNo);
		// ----------------------------------------------------------------------
		// 判断是否险种组合
		if (checkContPlan(rLPGrpEdorItemSchema.getGrpContNo())) {
			// ----------------------------------------------------------------------
			// 产品组合

			QuerySQL = " select distinct CSNo,ISNo,ISName,Duty,AddRisk,Amnt,Prem,AddFee from (select "
					+ " (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) CSNo,"
					+ " b.insuredno ISNo,"
					+ " b.insuredname ISName,"
					+ " (select dutycode || dutyname from lmduty where dutycode = a.dutycode) Duty,"
					+ " (select riskshortname from lmrisk where riskcode = e.riskcode) AddRisk,"
					+ " (case c.amntflag when '1' then a.amnt else a.mult end) Amnt, "
					+ " a.prem Prem,"
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')) AddFee" // 加费
					+ "   from LPDuty a,LPCont b,LMDuty c,LCDuty d,LPPol e"
					+ "  where 1=1 "
					+ "    and e.mainpolno <> e.polno"
					+ "    and a.edorno = '"
					+ rLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ "    and a.edortype = '"
					+ rLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "    and e.grpcontno = '"
					+ rLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ "    and a.edorno = b.edorno "
					+ "    and a.contno = b.contno "
					+ "    and a.contno = d.contno "
					+ "    and a.contno = e.contno "
					+ "     and c.dutycode = d.dutycode "
					+ " )"
					+ " order by CSNo asc";

			logger.debug(QuerySQL);

			try {
				tSSRS = tExeSQL.execSQL(QuerySQL);
			} catch (Exception ex) {
				CError.buildErr(this, "查询获取责任相关信息出现异常！");
				return false;
			}
		} else {
			// ----------------------------------------------------------------------
			// 非产品组合

			QuerySQL = " select distinct CSNo,ISNo,ISName,Risk,AddPol,Amnt,Prem,AddFee from (select "
					+ " (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) CSNo,"
					+ " b.insuredno ISNo,"
					+ " b.insuredname ISName,"
					+ " (select origriskcode || riskshortname from lmrisk where riskcode in (select distinct riskcode from lcpol where mainpolno = polno and grpcontno = '"
					+ rLPGrpEdorItemSchema.getGrpContNo()
					+ "')) Risk,"
					+ " (select origriskcode || riskshortname from lmrisk where riskcode = a.riskcode) AddPol,"
					+ " (case c.amntflag when '1' then a.amnt else a.mult end) Amnt, "
					+ " a.prem Prem,"
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')) AddFee" // 加费
					+ "   from LPPol a,LPCont b,LMDuty c,LCDuty d"
					+ "  where 1=1 "
					+ "    and a.mainpolno <> a.polno"
					+ "    and a.edorno = '"
					+ rLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ "    and a.edortype = '"
					+ rLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "    and a.grpcontno = '"
					+ rLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ "    and a.edorno = b.edorno "
					+ "    and a.contno = b.contno "
					+ "     and a.polno = d.polno "
					+ "     and c.dutycode = d.dutycode "
					+ " )"
					+ " order by CSNo asc";

			logger.debug(QuerySQL);

			try {
				tSSRS = tExeSQL.execSQL(QuerySQL);
			} catch (Exception ex) {
				CError.buildErr(this, "查询获取险种相关信息出现异常！");
				return false;
			}
		}
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			arrOutResult = new String[VTS_COLUMN];
			for (int j = 0; j < VTS_COLUMN; j++) {
				if (j >= 5 && j <= 7) {
					arrOutResult[j] = BqNameFun.getRoundMoney(tSSRS.GetText(i,
							j + 1));
				} else {
					arrOutResult[j] = tSSRS.GetText(i, j + 1);
				}
			}

			// 添加到小计处理
			if (!vct.contains(arrOutResult[4])) {
				vct.add(arrOutResult[4]);
			}

			// ------------------------------------------------------------
			if (!BqNameFun.dealHashTable(RiskTable, arrOutResult[4] + "Prem",
					arrOutResult[6])
					|| !BqNameFun.dealHashTable(RiskTable, arrOutResult[4]
							+ "AddFee", arrOutResult[7])
					|| !BqNameFun.dealHashTable(RiskTable, "SumMoney",
							arrOutResult[6])
					|| !BqNameFun.dealHashTable(RiskTable, "SumMoney",
							arrOutResult[7])) {
				return false;
			}

			tListTable.add(arrOutResult);

		}
		// 输出新增附加险数据
		tXmlPrint.addListTable(tListTable, arrListTitle);

		// 处理小计信息

		int VTS_COLUMN_XJ = 4; // 小计中的列数
		String[] tListTitle = new String[VTS_COLUMN_XJ]; // 不使用
		// tSSRS.getMaxCol(),
		// 因为还有值没有取到
		try {
			for (int j = 0; j < tListTitle.length; j++) {
				tListTitle[j] = "Billrisk" + String.valueOf(j);
			}
		} catch (Exception ex) {
			CError.buildErr(this, "初始化列表标题错误！");
			logger.debug("\t@> GrpEdorWSPrintBL.printNamesBill() : ListTitle[] 赋值异常！");
			return false;
		}

		// ------------------------------------------------------------------

		// ListTable

		String[] ttOutResult = null;
		// double SumMoney = 0.00; //合计金额

		for (int i = 1; i <= vct.size(); i++) {
			ttOutResult = new String[VTS_COLUMN_XJ];
			ttOutResult[0] = vct.get(i - 1).toString();
			ttOutResult[1] = "－－";
			ttOutResult[2] = BqNameFun.getRoundMoney((String) RiskTable
					.get(ttOutResult[0] + "Prem") == null ? "0.00"
					: (String) RiskTable.get(ttOutResult[0] + "Prem"));
			ttOutResult[3] = BqNameFun.getRoundMoney((String) RiskTable
					.get(ttOutResult[0] + "AddFee") == null ? "0.00"
					: (String) RiskTable.get(ttOutResult[0] + "AddFee"));
			// SumMoney += Math.abs(Double.parseDouble(arrOutResult[2]))
			// + Math.abs(Double.parseDouble(arrOutResult[3]));
			ttlListTable.add(ttOutResult);
		}

		// ------------------------------------------------------------------

		// 输出数据
		tXmlPrint.addListTable(ttlListTable, tListTitle);

		// 添加合计
		tTextTag.add("SumGetMoney", BqNameFun.getRoundMoney((String) RiskTable
				.get("SumMoney") == null ? "0.00" : (String) RiskTable
				.get("SumMoney")));

		// }
		tXmlPrint.addTextTag(tTextTag);
		// ----------------------------------------------------------------------

		// 底端字段的赋值

		// 复核人
		tTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(rLPGrpEdorItemSchema.getApproveOperator()));
		// 经办人
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

			tTextTag.add("AgentCode", sAgentCode);// 业务员编号
			tTextTag.add("AgentName", arrAgentInfo[BqNameFun.AGENT_NAME]);// 业务员姓名
		}

		// ----------------------------------------------------------------------

		tXmlPrint.addTextTag(tTextTag);
		// logger.debug("\t@> 打印成功");
		// tXmlPrint.outputDocumentToFile("D:\\print\\", "GrpWSNameList");//Add
		// By for test on 2006-9-27
		// ----------------------------------------------------------------------

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
		tExeSQL = null;
		tSSRS = null;
		tTextTag = null;
		tXmlPrint = null;
		vct = null;
		RiskTable = null;

		// logger.debug("\t@> GrpEdorEAPrintBL.printNamesBill() 成功");
		return true;
	} // function printNamesBill end

	// ==========================================================================

	/**
	 * 判断是否是组合产品
	 * 
	 * @param String
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkContPlan(String GrpContNo) {
		// logger.debug("\t@> GrpEdorWSPrintBL.checkContPlan() 开始");

		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		QuerySQL = " Select PlanType " + " From LCContPlan "
				+ " Where GrpContNo='" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";

		tSSRS = tExeSQL.execSQL(QuerySQL);

		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			if (tSSRS.GetText(1, 1).equals("6"))// 6为组合险种，返回true

				// logger.debug("\t@> GrpEdorWSPrintBL.checkContPlan()
				// 成功");
				return true;
		}
		return false;// 其他情况都不是组合险种，返回false
	}// function checkContPlan end

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
		mGlobalInput.ManageCom = "86210000";
		mGlobalInput.Operator = "001";
		VData tVData = new VData();
		tVData.add(mGlobalInput);

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo("6120070330000004");
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

		GrpEdorWSPrintBL tGrpEdorPrintBL = new GrpEdorWSPrintBL();
		if (tGrpEdorPrintBL.submitData(tVData, "PRINTEA")) {
			logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
		}
	}

	private void jbInit() throws Exception {
	}


} // class GrpEdorWSPrintBL end
