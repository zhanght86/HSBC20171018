package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPPolOtherDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
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

/*******************************************************************************
 * <p>
 * Title: 团体保全-车牌号更改项
 * </p>
 * <p>
 * Description: 团体保全-车牌号更改打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @todo : 保全-更改车牌号码-打印
 * @author : liuxiaosong (liuxs@sinosoft.com.cn)
 * @version : 1.00 1.01
 * @date : 2006-09-26 2006-09-29 更新记录： 更新人 更新日期 更新原因/内容
 ******************************************************************************/
public class GrpEdorNCPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorNCPrintBL.class);
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
	public static final String FormulaOneVTS = "GrpEdorNC.vts";

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\n\t@> GrpEdorEAPrintBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\n\t@> GrpEdorEAPrintBL.submitData() : 无法获取 InputData 数据！");
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
		if (!printBatchBill()) {
			return false;
		}
		if (!printCarsBill()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
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
		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\n\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\n\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// XmlExport
		rXmlExport = new XmlExport();
		rXmlExport = (XmlExport) rInputData.getObjectByObjectName("XmlExport",
				0);
		if (rXmlExport == null) {
			CError.buildErr(this, "无法获取 XML 数据信息！");
			logger.debug("\n\t@> GrpEdorEAPrintBL.getInputData() : 无法获取 XmlExport 数据！");
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

		// logger.debug("\n\t@> GrpEdorEAPrintBL.getInputData() 成功");
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
		// logger.debug("\n\t@> GrpEdorEAPrintBL.checkData() 开始");

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

		// logger.debug("\n\t@> GrpEdorEAPrintBL.checkData() 成功");
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
		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
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
			CError.buildErr(this, "查询团体合同表获取团体单位名称出现异常！");
			return false;
		}
		tTextTag.add("GrpName", sGrpName);
		tTextTag.add("EdorNo", rLPGrpEdorItemSchema.getEdorNo());
		tTextTag.add("GrpContNo", rLPGrpEdorItemSchema.getGrpContNo());

		// ----------------------------------------------------------------------

		// 表格NC的赋值
		rXmlExport.addDisplayControl("displayNC");

		// 获得NC项目变更总人数
		try {
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			LPPolOtherDB tLPPolOtherDB = new LPPolOtherDB();
			tLPPolOtherDB.setEdorNo(rLPGrpEdorItemSchema.getEdorAcceptNo());
			tLPPolOtherDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
			tLPPolOtherDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
			int count = tLPGrpEdorItemDB.getCount();

			tTextTag.add("NC.NCCount", count);
		} catch (Exception ex) {
			logger.debug(ex.toString());
			CError.buildErr(this, "获取NC项目变更总人数时出现异常！");
			return false;
		}
		// ----------------------------------------------------------------------

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
		// 输出数据
		rXmlExport.addTextTag(tTextTag);
		// 垃圾处理
		tExeSQL = null;
		tTextTag = null;

		// logger.debug("\n\t@> GrpEdorEAPrintBL.printBatchBill() 成功");
		return true;
	} // function printBatchBill end

	// ==========================================================================

	/**
	 * 生成车辆清单需要的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean printCarsBill() {
		// logger.debug("\n\t@> GrpEdorEAPrintBL.printNamesBill() 开始");

		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		String QuerySQL = new String("");

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		TextTag tTextTag = new TextTag();

		String carNoField = ""; // 映射车牌号最终目标字段名
		String shellNoField = ""; // 映射车架号最终目标字段名
		String seatCountField = ""; // 映射座位号最终目标字段名
		// ----------------------------------------------------------------------

		// 输出文档
		XmlExport tXmlPrint = new XmlExport();
		try {
			tXmlPrint.createDocument(FormulaOneVTS, "printer"); // 初始化 XML 文档
		} catch (Exception ex) {
			CError.buildErr(this, "初始化打印模板错误！");
			logger.debug("\n\t@> GrpEdorEAPrintBL.printNamesBill() : 设置 FormulaOne VTS 文件异常！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 题头字段的赋值
		QuerySQL = "select GrpName " + "from LCGrpCont " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		// logger.debug(QuerySQL);
		String sGrpName = new String(""); // 单位名称
		try {
			sGrpName = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体合同表获取团体单位名称出现异常！");
			return false;
		}
		tTextTag.add("NCbill.GrpContNo", rLPGrpEdorItemSchema.getGrpContNo()); // 团单号
		tTextTag.add("NCbill.GrpName", sGrpName); // 单位名称
		tTextTag.add("NCbill.ValiDate", rLPGrpEdorItemSchema.getEdorValiDate()); // 生效日期
		tTextTag.add("NCbill.EdorNo", rLPGrpEdorItemSchema.getEdorNo()); // 批单号

		// ----------------------------------------------------------------------

		// 中间表格的赋值
		// 获得车牌号映射字段
		carNoField = BqNameFun.mapped("LCPolOtherFieldDesc", "CarNo",
				"LCPolOther");
		// 获得车架号映射字段
		shellNoField = BqNameFun.mapped("LCPolOtherFieldDesc", "ShelfNo",
				"LCPolOther");
		// 获得坐位号映射字段
		seatCountField = BqNameFun.mapped("LCPolOtherFieldDesc", "SeatCount",
				"LCPolOther");

		QuerySQL = ""; // 以免多次使用造成混乱
		tSSRS = null; // 以免多次使用造成混乱

		// ----------------------------------------------------------------------

		// 清单列顺序为：车辆序号 新车牌号 架子号 核定座位数 原车牌号 新车牌号
		QuerySQL = "select p." + carNoField + ", c." + shellNoField + ", c."
				+ seatCountField + ", c." + carNoField + ", p." + carNoField
				+ " from Lppolother p, lcpolother c "
				+ "where c.polno = p.polno " + "and c.dutycode = p.dutycode "
				+ "and p.edorno = '" + rLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "'and p.edortype = 'NC' " // rLPGrpEdorItemSchema.getEdorType()
				+ "and p.grpcontno = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		tSSRS = tExeSQL.execSQL(QuerySQL);

		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "查询车辆信息失败");
			return false;
		}

		// 生成清单列表表头
		String[] tListTitle = new String[6];
		for (int iTitle = 0; iTitle < 6; iTitle++) {
			tListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tListContentTable = new ListTable(); // 清单内容数据容器
		tListContentTable.setName("BILL");
		String strLine[] = null;
		// 填充清单内容数据容器
		int j; // 第二维计数
		int temp = tSSRS.getMaxRow(); // 记录总数
		for (int i = 1; i <= temp; i++) {
			strLine = new String[6];
			for (j = 0; j < 6; j++) {
				strLine[j] = tSSRS.GetText(i, j + 1);
			}
			tListContentTable.add(strLine);
		}

		tXmlPrint.addListTable(tListContentTable, tListTitle); // 完成填充

		// ----------------------------------------------------------------------

		// 底端字段的赋值
		tTextTag.add("NCbill.ApproveOperator", CodeNameQuery
				.getOperator(rLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		tTextTag.add("NCbill.Operator", CodeNameQuery
				.getOperator(rLPGrpEdorItemSchema.getOperator())); // 经办人
		QuerySQL = "select AgentCode " + "from LCGrpCont " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		// logger.debug(QuerySQL);
		String sAgentCode = new String(""); // 业务员编码
		try {
			sAgentCode = tExeSQL.getOneValue(QuerySQL);
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体合同表获取代理人编码出现异常！");
			return false;
		}
		if (sAgentCode != null && !sAgentCode.trim().equals("")) {
			String[] arrAgentInfo = BqNameFun.getAllNames(sAgentCode);
			tTextTag.add("NCbill.AgentCode", sAgentCode);
			tTextTag
					.add("NCbill.AgentName", arrAgentInfo[BqNameFun.AGENT_NAME]); // 业务员姓名
		}

		// ----------------------------------------------------------------------

		tXmlPrint.addTextTag(tTextTag);

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

		// logger.debug("\n\t@> GrpEdorEAPrintBL.printNamesBill() 成功");
		return true;
	} // function printCarsBill end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		// logger.debug("\n\t@> GrpEdorEAPrintBL.prepareOutputData() 开始");

		rResultData = new VData();
		rResultData.addElement(rXmlExport);
		rResultData.add(rMap);

		// logger.debug("\n\t@> GrpEdorEAPrintBL.prepareOutputData() 成功");
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
	 * 本类测试函数
	 */
	// public static void main(String[] args)
	// {
	// //局部变量
	// logger.debug("======start GrpEdorNCPrintBL test========");
	// LPGrpEdorItemSchema tLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
	// GlobalInput mGlobalInput = new GlobalInput();
	// VData tVData = new VData();
	//
	// //Globalinput
	// mGlobalInput.ManageCom = "86";
	// mGlobalInput.Operator = "wangyan4";
	// tVData.add(mGlobalInput);
	//
	// //LPGrpEdorItemSchema
	// tLPGrpEdorItemSchema.setEdorNo("6020060404000024");
	// tLPGrpEdorItemSchema.setEdorType("NC");
	// tLPGrpEdorItemSchema.setGrpContNo("00200603300003");
	// tLPGrpEdorItemSchema.setEdorAcceptNo("6120060404000009");
	// tVData.add(tLPGrpEdorItemSchema);
	//
	// XmlExport tXmlExport = new XmlExport();
	// try
	// {
	// tXmlExport.createDocument(FormulaOneVTS, "printer"); //初始化 XML 文档
	// }
	// catch (Exception ex)
	// {
	// logger.debug(
	// "\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne VTS 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// GrpEdorNCPrintBL tGrpEdorNCPrintBL = new GrpEdorNCPrintBL();
	// if (tGrpEdorNCPrintBL.submitData(tVData, "PRINTPT"))
	// {
	// logger.debug("========GrpEdorNCPrintBL test successful=========");
	// }
	// } //function main() end
	// //==========================================================================

}
