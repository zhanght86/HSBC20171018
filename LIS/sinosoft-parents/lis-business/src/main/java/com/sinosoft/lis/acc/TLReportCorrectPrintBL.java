package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.BqBill;
import com.sinosoft.lis.f1print.BqNameFun;

import java.lang.*;
import java.util.*;

//******************************************************************************
//签报纠错报告

public class TLReportCorrectPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(TLReportCorrectPrintBL.class);
	// public EdorPayGetFormChangePrintBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();

	// 输入数据
	private VData rInputData;

	private String rOperation;

	private String mRiskCode;

	private String mInsuAccNo;

	private String mStartdate;

	private GlobalInput rGlobalInput;

	private LOAccUnitPriceSchema rLOAccUnitPriceSchema = new LOAccUnitPriceSchema();

	private TransferData mTransferData = new TransferData();

	// 本类变量
	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();


	// 输出数据
	private XmlExport rXmlExport;

	private VData rResultData;

	// FormulaOne 打印模板文件
	public static final String FormulaOneVTS = "TLReportCorrect.vts";

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 *
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("\t@> InsuAccReportCorrectPrintBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> InsuAccReportCorrectPrintBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!outputData()) {
			return false;
		}
		// 存入LOREPORTMANAGER
		// if (!saveoutputData()) {
		// return false;
		// }

		// 垃圾处理
		collectGarbage();

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
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> AccountDelayPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		rLOAccUnitPriceSchema = (LOAccUnitPriceSchema) rInputData
				.getObjectByObjectName("LOAccUnitPriceSchema", 0);
		if (rLOAccUnitPriceSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mInsuAccNo = rLOAccUnitPriceSchema.getInsuAccNo();
		//mRiskCode = rLOAccUnitPriceSchema.getRiskCode();
		mStartdate = rLOAccUnitPriceSchema.getStartDate();
		return true;
	}

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 *
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 *
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {

		logger.debug("\t@> InsuAccReportCorrectPrintBL.dealData() 开始");
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		SSRS tssrs = new SSRS();

		TextTag tTextTag = new TextTag();

		// ----------------------------------------------------------------------
		// 输出 XML 文档
		rXmlExport = new XmlExport();
		try {
			rXmlExport.createDocument(FormulaOneVTS, "printer"); // 初始化 XML
			// 文档
		} catch (Exception ex) {
			CError.buildErr(this, "初始化打印模板错误！");
			logger.debug("\t@> InsuAccReportCorrectPrintBL.dealData() : 设置 FormulaOne VTS 文件异常！");
			return false;
		}

		// 表头：计价日期、帐户名称、单位价格
		sql = " select m.insuaccname, r.unitpricebuy, r.unitpricesell "
				+ "  from loaccunitprice r, lmriskinsuacc m "
				+ " where m.insuaccno = r.insuaccno and r.state = '0' "
				+ "  and r.insuaccno = '" + "?mInsuAccNo?"
				+ "' and Startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mInsuAccNo", mInsuAccNo);
		sqlbv.put("mStartdate", mStartdate);
		
		tssrs = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "单位价格查询失败!");
			return false;
		}
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			CError.buildErr(this, "单位价格查询失败!");
			return false;
		}
		tTextTag.add("ValueDate", mStartdate); // 计价日
		tTextTag.add("InsuAccName", tssrs.GetText(1, 1));// 账户名称
		tTextTag.add("UnitPriceBuy", // 买入价
				BqNameFun.getRound(tssrs.GetText(1, 2), "0.0000"));
		tTextTag.add("UnitPriceSell", // 卖出价
				BqNameFun.getRound(tssrs.GetText(1, 3), "0.0000"));
		
		sql = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?mInsuAccNo?"+"' And currency =a.currcode)";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(sql);
        sqlbv1.put("mInsuAccNo", mInsuAccNo);
		tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv1));

		ListTable tListTable = new ListTable(); // 分项记录
		tListTable.setName("AccCorrent");
		String[] tTitle = new String[10];
		for (int i = 0; i < 10; i++) {
			tTitle[i] = "Title" + i;
		}

		ListTable tReTraceListTable = new ListTable(); // 分项记录
		tReTraceListTable.setName("AccTrace");
		String[] tReTraceTitle = new String[5];
		for (int i = 0; i < 4; i++) {
			tReTraceTitle[i] = "Title" + i;
		}
		
		double dSumUnitCount = 0.0; // 合计
		double dSumTransferMoney = 0.0; // 合计

		// 循环险种进行统计
		sql = " select m.riskcode, m.riskshortname from lmrisktoacc a, lmrisk m  "
				+ " where m.riskcode = a.riskcode and a.insuaccno = '"
				+ "?mInsuAccNo?" + "'";
	    SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(sql);
		sqlbv11.put("mInsuAccNo", mInsuAccNo);
		SSRS riskSSRS = tExeSQL.execSQL(sqlbv11);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "险种查询失败!");
			return false;
		}
		if (riskSSRS == null || riskSSRS.getMaxRow() < 1) {
			CError.buildErr(this, "险种查询失败!");
			return false;
		}

		ListTable riskSumListTable = new ListTable(); // 合计记录
		riskSumListTable.setName("RiskSum");
		String[] tTitleRiskSum = new String[3];
		for (int i = 0; i < 3; i++) {
			tTitleRiskSum[i] = "TitleRiskSum" + i;
		}
		for (int k = 1; k <= riskSSRS.getMaxRow(); k++) {
			double dRiskSumUnitCount = 0.0; // 险种合计
			double dRiskSumTransferMoney = 0.0; // 险种合计

			// 取EB操作
			sql = " select distinct (select riskshortname from lmrisk m where m.riskcode = cur.riskcode),"//险种
					+ " bef.managecom,"//分公司
					+ " bef.contno," // 保单号
					+ " bef.paydate," // --申请处理日期
					+ " bef.valuedate,"//出错日期
					+ " (case when (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = bef.insuaccno and t.startdate = bef.valuedate) is not null then (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = bef.insuaccno and t.startdate = bef.valuedate)  else 0 end)," // --错误发生日的价格
					//+ " (select sum(unitcount) from lcinsureacctrace tr where tr.polno = cur.polno and tr.insuaccno = cur.insuaccno and tr.valuedate < '"
					//+ mStartdate
					//+ "' or (tr.valuedate= '"+mStartdate+"' and maketime<cur.maketime))," // --当前投资单位数
					+ " (select sum(unitcount) from lcinsureacctrace tr where tr.polno = cur.polno and tr.insuaccno = cur.insuaccno and tr.valuedate = '"
					+ "?mStartdate?"
					+ "' and busytype='EB' and accaltertype<>'7')," // --申请单位数
					+ " (select sum(money) from lcinsureacctrace tr where tr.polno = cur.polno and tr.insuaccno = cur.insuaccno and tr.valuedate = '"
					+ "?mStartdate?"
					+ "' and busytype='EB' and accaltertype<>'7')," // --申请金额
					+ " cur.valuedate,"//实际处理日期
					+ " (case when (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate) is not null then (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate)  else 0 end)" // --实际处理日的价格
					//+ " (select sum(unitcount) from lcinsureacctrace tr where tr.polno = cur.polno and tr.insuaccno = cur.insuaccno and tr.valuedate <= '"
					//+ mStartdate
					//+ "' )," // --调整后的投资单位数
					//+ " (select distinct p.edorreason from lpedoritem p where p.contno = cur.contno and p.edortype = cur.busytype and p.edorvalidate = cur.paydate), " // --出错原因
					//+ " (select riskshortname from lmrisk m where m.riskcode = cur.riskcode) "
					+ " from lcinsureacctrace cur, lcinsureacctrace bef"
					+ " where 1=1 "
					+ " and cur.accalterno = bef.serialno "
					+ " and cur.busytype = 'EB' and cur.accaltertype<>'7' and cur.state <>'0' "
					+ " and cur.valuedate =to_date('" + "?mStartdate?" + "','yyyy-mm-dd')"
					+ " and cur.insuaccno = '" + "?mInsuAccNo?"
					+ "'"
					+ " and cur.riskcode = '" + "?riskcode?"
					+ "'"
					+ " order by bef.managecom,bef.contno ";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("mStartdate", mStartdate);
			sqlbv2.put("mInsuAccNo", mInsuAccNo);
			sqlbv2.put("riskcode", riskSSRS.GetText(k, 1));
			tssrs = tExeSQL.execSQL(sqlbv2);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "单位价格查询失败!");
				return false;
			}
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				double tTransferCount1; // 延迟
				double tTransferCount2; // EB
				double tTransferCount; // 调整
				double tShouldPrice;
				double tValuePrice;
				double tTransferMoney;

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					String[] strArr = new String[11]; // 保单列表
					strArr[0] = tssrs.GetText(i, 1); // 险种
					strArr[1] = tssrs.GetText(i, 2); // 分公司
					strArr[2] = tssrs.GetText(i, 3); // 保单号
					strArr[3] = tssrs.GetText(i, 4); // 申请处理日
					strArr[4] = tssrs.GetText(i, 5); //出错日期
					strArr[5] = BqNameFun.getRound(tssrs.GetText(i, 6),"0.0000"); // 错误日期价格
					tShouldPrice = Double.parseDouble(tssrs.GetText(i, 6)); // 错误日期价格
					strArr[6] = BqNameFun.getRound(tssrs.GetText(i, 7),"0.0000"); // 申请单位数
					tTransferCount=Double.parseDouble(BqNameFun.getRound(tssrs.GetText(i, 7),"0.000000"));//申请单位数 以6位计算
					strArr[7] = BqNameFun.getRound(tssrs.GetText(i, 8),"0.00"); // 申请金额
					strArr[8] = tssrs.GetText(i, 9); // 实际处理日
					strArr[9] = BqNameFun.getRound(tssrs.GetText(i, 10),"0.0000");//实际处理日价格
					tValuePrice = Double.parseDouble(tssrs.GetText(1, 10)); // 修正日价格
					tTransferMoney = tTransferCount* (tValuePrice - tShouldPrice); // 公司损失金额
					strArr[10]=BqNameFun.getRound(tTransferMoney,"0.00"); // 公司损失金额

					tListTable.add(strArr); // 插入一条记录

					// 险种合计
					dRiskSumUnitCount += tTransferCount; // 单位数合计
					dRiskSumTransferMoney += tTransferMoney; // 损失金额合计

					// 合计
					dSumUnitCount += tTransferCount; // 单位数合计
					dSumTransferMoney += tTransferMoney; // 损失金额合计
				}
			}
			// 取帐户轨迹调涨操作损益
			sql = " select distinct (select riskshortname from lmrisk m where m.riskcode = a.riskcode),"//险种
					+ " managecom,"//分公司
					+ " contno," // 保单号
					+ " valuedate,"//实际处理日期
					+ " (select (case when sum(money) is not null then sum(money)  else 0 end) from lcinsureacctrace tr where tr.polno = a.polno and tr.insuaccno = a.insuaccno and tr.valuedate = '"
					+ "?mStartdate?"
					+ "' and busytype='EB' and accaltertype='7'), " // --损益金额
					+ " (select (case when sum(unitcount) is not null then sum(unitcount)  else 0 end) from lcinsureacctrace tr where tr.polno = a.polno and tr.insuaccno = a.insuaccno and tr.valuedate = '"
					+ "?mStartdate?"
					+ "' and busytype='EB' and accaltertype='7') " // --损益单位数
					+ " from lcinsureacctrace a "
					+ " where 1=1 "
					+ " and a.busytype = 'EB' and accaltertype='7' and a.state <>'0' "
					+ " and a.valuedate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')"
					+ " and a.insuaccno = '" + "?mInsuAccNo?"
					+ "'"
					+ " and a.riskcode = '" + "?riskcode?"
					+ "'"
					+ " order by a.managecom,a.contno ";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("mStartdate", mStartdate);
			sqlbv3.put("mInsuAccNo", mInsuAccNo);
			sqlbv3.put("riskcode", riskSSRS.GetText(k, 1));
			tssrs = tExeSQL.execSQL(sqlbv3);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "单位价格查询失败!");
				return false;
			}
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				double tTransferMoney;
				double tTransferCount;

				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					String[] strArr = new String[6]; // 保单列表
					strArr[0] = tssrs.GetText(i, 1); // 险种
					strArr[1] = tssrs.GetText(i, 2); // 分公司
					strArr[2] = tssrs.GetText(i, 3); // 保单号
					strArr[3] = tssrs.GetText(i, 4); // 处理日
					strArr[4] = tssrs.GetText(i, 5); // 公司损溢金额
					strArr[5] = BqNameFun.getRound(tssrs.GetText(i, 6),"0.0000"); // 申请单位数
					tTransferMoney = Double.parseDouble(tssrs.GetText(i, 5)); // 公司损失金额
					tTransferCount = Double.parseDouble(BqNameFun.getRound(tssrs.GetText(i, 6),"0.0000"));
					tReTraceListTable.add(strArr); // 插入一条记录

					// 险种合计
					dRiskSumUnitCount += tTransferCount; // 单位数合计
					dRiskSumTransferMoney += tTransferMoney; // 损失金额合计
					// 合计
					dSumUnitCount += tTransferCount; // 单位数合计
					dSumTransferMoney += tTransferMoney; // 损失金额合计
				}
			}
			if(dRiskSumUnitCount!=0 || dRiskSumTransferMoney!=0)
			{
				String[] strArrRiskSum = new String[3]; // 险种合计
				strArrRiskSum[0] = riskSSRS.GetText(k, 2); // 险种名称
				strArrRiskSum[1] = BqNameFun.getRound(dRiskSumUnitCount,"0.0000");
				strArrRiskSum[2] = BqNameFun.getRound(dRiskSumTransferMoney,"0.00");
				riskSumListTable.add(strArrRiskSum);
			}
		}

		ListTable sumListTable = new ListTable(); // 合计记录
		sumListTable.setName("Sum");
		String[] tTitleSum = new String[2];
		for (int i = 0; i < 2; i++) {
			tTitleSum[i] = "TitleSum" + i;
		}
		String[] strArrSum = new String[2]; // 合计
		strArrSum[0] = BqNameFun.getRound(dSumUnitCount,"0.0000");
		strArrSum[1] = BqNameFun.getRound(dSumTransferMoney,"0.00");
		sumListTable.add(strArrSum); // 插入合计数
		tssrs = null;

		// 输出数据
		rXmlExport.addTextTag(tTextTag); // 输出表头
		rXmlExport.addListTable(tListTable, tTitle); //
		rXmlExport.addListTable(sumListTable, tTitleSum); //
		rXmlExport.addListTable(riskSumListTable, tTitleRiskSum); //
		rXmlExport.addListTable(tReTraceListTable, tReTraceTitle); //

		// 垃圾处理
		tTextTag = null;
		tExeSQL = null;

		logger.debug("\t@> InsuAccReportCorrectPrintBL.dealData() 成功");
		return true;
	} // function printData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 *
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> AccountCorrectPrintBL.outputData() 开始");

		rResultData = new VData();
		rResultData.addElement(rXmlExport);

		// logger.debug("\t@> AccountCorrectPrintBL.outputData() 成功");
		return true;
	} // function prepareOutputData end

	// ==========================================================================

	/**
	 * 将输出数据保存在LOREPORTMANAGER表中
	 *
	 * @param null
	 * @return boolean
	 */
	private boolean saveoutputData() {
		// LOReportManagerSchema
		LOReportManagerSchema tLOReportManagerSchema = new LOReportManagerSchema();
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 20); // 生成印刷流水号
		tLOReportManagerSchema.setPrtSeq(tPrtSeq);
		tLOReportManagerSchema.setReportType("1"); // 报表类型
		tLOReportManagerSchema.setReportCode("tl_005"); // 报表编码
		tLOReportManagerSchema.setPrtFlag("0");
		tLOReportManagerSchema.setPrtTimes(0);
		tLOReportManagerSchema.setFilters("86"); // 统计条件
		tLOReportManagerSchema.setCreateManageCom("86"); // 可能需要修改，定义成变量
		tLOReportManagerSchema.setManageCom("86");
		tLOReportManagerSchema.setTemplate(FormulaOneVTS); // 模板
		tLOReportManagerSchema.setOperator("001");
		tLOReportManagerSchema.setMakeDate(CurrentDate);
		tLOReportManagerSchema.setMakeTime(CurrentTime);
		tLOReportManagerSchema.setModifyDate(CurrentDate);
		tLOReportManagerSchema.setModifyTime(CurrentTime);
		tLOReportManagerSchema.setReportInfo(rXmlExport.getInputStream());
		// LOReportParamSchema
		LOReportParamSchema tLOReportParamSchema = new LOReportParamSchema();
		tLOReportParamSchema.setPrtSeq(tPrtSeq);
		tLOReportParamSchema.setParamName("InsuAccNo");
		tLOReportParamSchema.setParamValue(mInsuAccNo);
		tLOReportParamSchema.setOperator("001");
		tLOReportParamSchema.setMakeDate(CurrentDate);
		tLOReportParamSchema.setMakeTime(CurrentTime);
		tLOReportParamSchema.setModifyDate(CurrentDate);
		tLOReportParamSchema.setModifyTime(CurrentTime);

		LOReportParamSchema tLOReportParamSchema1 = new LOReportParamSchema();
		tLOReportParamSchema1.setPrtSeq(tPrtSeq);
		tLOReportParamSchema1.setParamName("ValueDate");
		tLOReportParamSchema1.setParamValue(mStartdate);
		tLOReportParamSchema1.setOperator("001");
		tLOReportParamSchema1.setMakeDate(CurrentDate);
		tLOReportParamSchema1.setMakeTime(CurrentTime);
		tLOReportParamSchema1.setModifyDate(CurrentDate);
		tLOReportParamSchema1.setModifyTime(CurrentTime);

		// 提交后台
		MMap tmap = new MMap();
		tmap.put(tLOReportParamSchema, "INSERT");
		tmap.put(tLOReportParamSchema1, "INSERT");
		tmap.put(tLOReportManagerSchema, "BLOBINSERT");

		VData pVData = new VData();
		PubSubmit tPubSubmit = new PubSubmit();
		pVData.add(tmap);
		if (!tPubSubmit.submitData(pVData, "PRINT")) {
			logger.debug("信息提交失败！");
			return false;
		}

		return true;
	} // function saveoutputData end

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
	} // function getResult end

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
		if (rInputData != null) {
			rInputData = null;
		}
		if (rGlobalInput != null) {
			rGlobalInput = null;
		}
		if (rXmlExport != null) {
			rXmlExport = null;
		}
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 *
	 * @param String[]
	 */
	public static void main(String[] args) {
		logger.debug("test start:");
		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("InsuAccNo", "890000");
		tTransferData.setNameAndValue("ValueDate", "2007-6-15");
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		TLReportCorrectPrintBL tInsuAccReportCorrectPrintBL = new TLReportCorrectPrintBL();
		if (!tInsuAccReportCorrectPrintBL.submitData(tVData, "")) {
			logger.debug("test failed:"
					+ tInsuAccReportCorrectPrintBL.mErrors.getFirstError()
							.toString());
		}
		logger.debug("test end");

	} // function main end

	// ==========================================================================

} // class EdorPayGetFormChangePrintBL end
