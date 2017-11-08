/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07, 1.08, 1.09
 * @date     : 2005-11-24, 2005-12-17, 2005-12-22, 2006-02-16, 2006-03-10, 2006-09-14, 2006-10-24, 2006-11-01, 2006-11-06, 2006-12-28
 * @direction: 保全生调结果保存
 ******************************************************************************/

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPLiveInquiryDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPLiveInquirySchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorLiveInquiryBL {
private static Logger logger = Logger.getLogger(PEdorLiveInquiryBL.class);
	// public PEdorLiveInquiryBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPLiveInquirySchema rLPLiveInquirySchema;
	// 私有变量
	private LDPersonSchema rLDPersonSchema;
	// 输出数据
	private MMap rMap;
	private VData rResultData;
	// 日期时间
	private String sCurrentDate = PubFun.getCurrentDate();
	private String sCurrentTime = PubFun.getCurrentTime();

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorLiveInquiryBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorLiveInquiryBL.submitData() : 无法获取 InputData 数据！");
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
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		if (!pubSubmit())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorLiveInquiryBL.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> PEdorLiveInquiryBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorLiveInquiryBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPLiveInquirySchema
		rLPLiveInquirySchema = new LPLiveInquirySchema();
		rLPLiveInquirySchema = (LPLiveInquirySchema) rInputData
				.getObjectByObjectName("LPLiveInquirySchema", 0);
		if (rLPLiveInquirySchema == null) {
			CError.buildErr(this, "无法获取待生调客户信息！");
			logger.debug("\t@> PEdorLiveInquiryBL.getInputData() : 无法获取 LPLiveInquirySchema 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 客户号码
		String sCustomerNo = rLPLiveInquirySchema.getCustomerNo();
		if (sCustomerNo == null || sCustomerNo.trim().equals("")) {
			CError.buildErr(this, "无法获取客户号码信息！");
			return false;
		}

		// 生调日期
		String sInquiryDate = rLPLiveInquirySchema.getInquiryDate();
		if (sInquiryDate == null || sInquiryDate.trim().equals("")) {
			CError.buildErr(this, "无法获取生调日期信息！");
			return false;
		}

		// 生调结果
		String sInquiryResult = rLPLiveInquirySchema.getInquiryResult();
		if (sInquiryResult == null || sInquiryResult.trim().equals("")) {
			CError.buildErr(this, "无法获取生调结果信息！");
			return false;
		}

		// 生调员
		String sInquiryOperator = rLPLiveInquirySchema.getInquiryOperator();
		if (sInquiryOperator == null || sInquiryOperator.trim().equals("")) {
			CError.buildErr(this, "无法获取生调员信息！");
			return false;
		}

		// ----------------------------------------------------------------------

		String sCurrentDate = PubFun.getCurrentDate();
		String sDiedDate = rLPLiveInquirySchema.getDiedDate();

		// 生调日期必须小于当前日期
		if (sInquiryDate.compareTo(sCurrentDate) > 0) {
			CError.buildErr(this, "生调日期必须小于当前日期！");
			return false;
		}

		// 如果客户已死亡
		if (sInquiryResult != null && sInquiryResult.trim().equals("1")) {
			if (sDiedDate == null || sDiedDate.trim().equals("")) {
				CError.buildErr(this, "客户已死亡, 必须录入死亡日期！");
				return false;
			} else if (sDiedDate.compareTo(sCurrentDate) > 0) {
				CError.buildErr(this, "客户已死亡, 死亡日期必须小于当前日期！");
				return false;
			} else if (sDiedDate.compareTo(sInquiryDate) > 0) {
				CError.buildErr(this, "客户已死亡, 死亡日期必须小于生调日期！");
				return false;
			}
		}

		// logger.debug("\t@> PEdorLiveInquiryBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> PEdorLiveInquiryBL.checkData() 开始");

		// 查询 LDPerson
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(rLPLiveInquirySchema.getCustomerNo());
		LDPersonSet tLDPersonSet = new LDPersonSet();
		try {
			tLDPersonSet = tLDPersonDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询个人客户表发生异常！");
			return false;
		}
		if (tLDPersonSet == null || tLDPersonSet.size() <= 0) {
			CError.buildErr(this, "在个人客户表中找不到待操作的纪录！");
			return false;
		} else {
			rLDPersonSchema = new LDPersonSchema();
			rLDPersonSchema = tLDPersonSet.get(1);
		}
		tLDPersonDB = null;
		tLDPersonSet = null;

		// logger.debug("\t@> PEdorLiveInquiryBL.checkData() 结束");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorLiveInquiryBL.dealData() 开始");

		rMap = new MMap();

		// ----------------------------------------------------------------------

		if (!createInquiryTrack()) {
			return false;
		}

		// ----------------------------------------------------------------------

		// 默认 CustomerNo = InsuredNo; AppntNo 待定
		if (rLPLiveInquirySchema.getInquiryResult().equals("0")) {
			return doCustomerLive();
		} else if (rLPLiveInquirySchema.getInquiryResult().equals("1")) {
			return doCustomerDied();
		} else {
			CError.buildErr(this, "未知的生调结果类型。请录入正确的值！");
			return false;
		}

		// logger.debug("\t@> PEdorLiveInquiryBL.dealData() 结束");
	} // function dealData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> PEdorLiveInquiryBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorLiveInquiryBL.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================

	/**
	 * 提交本类的处理结果到数据库
	 * 
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// logger.debug("\t@> PEdorLiveInquiryBL.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorLiveInquiryBL.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;

		// logger.debug("\t@> PEdorLiveInquiryBL.pubSubmit() 结束");
		return true;
	} // function pubSubmit end

	// ==========================================================================

	/**
	 * 创建客户的生调轨迹
	 * 
	 * @return boolean
	 */
	private boolean createInquiryTrack() {
		// 查询 LPLiveInquiry
		LPLiveInquiryDB tLPLiveInquiryDB = new LPLiveInquiryDB();
		tLPLiveInquiryDB.setCustomerNo(rLPLiveInquirySchema.getCustomerNo());
		int nInquiryTimes = 1;
		try {
			nInquiryTimes = tLPLiveInquiryDB.getCount() + 1;
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全客户生调轨迹表发生异常！");
			return false;
		}
		tLPLiveInquiryDB = null;

		// ----------------------------------------------------------------------

		// 插入 LPLiveInquiry
		LPLiveInquirySchema tLPLiveInquirySchemaNew = new LPLiveInquirySchema();
		PubFun.copySchema(tLPLiveInquirySchemaNew, rLPLiveInquirySchema);
		tLPLiveInquirySchemaNew.setInquiryTimes(nInquiryTimes);
		tLPLiveInquirySchemaNew.setOperator(rGlobalInput.Operator);
		tLPLiveInquirySchemaNew.setMakeDate(sCurrentDate);
		tLPLiveInquirySchemaNew.setMakeTime(sCurrentTime);
		tLPLiveInquirySchemaNew.setModifyDate(sCurrentDate);
		tLPLiveInquirySchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLPLiveInquirySchemaNew, "INSERT");
		tLPLiveInquirySchemaNew = null;

		return true;
	} // function createInquiryTrack end

	// ==========================================================================

	/**
	 * 处理客户存活的情况
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doCustomerLive() {
		String UpdateSQL = new String("");

		// ------------------------------------------------------------------

		// 更新 LDPerson
		rLDPersonSchema.setDeathDate("");
		rLDPersonSchema.setOperator(rGlobalInput.Operator);
		rLDPersonSchema.setModifyDate(sCurrentDate);
		rLDPersonSchema.setModifyTime(sCurrentTime);
		rMap.put(rLDPersonSchema, "UPDATE");

		// ------------------------------------------------------------------

		// 更新 LJSGetDraw
		UpdateSQL = "update LJSGetDraw " + "set RReportFlag = '0', "
				+ "Operator = '?Operator?', "
				+ "ModifyDate = '?sCurrentDate?', " + "ModifyTime = '?sCurrentTime?' " + "where 1 = 1 " + "and PolNo in "
				+ "(select PolNo from LCPol " + "where 1 = 1 "
				+ "and InsuredNo = '?InsuredNo?') " + "and GetDate <= '?GetDate?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(UpdateSQL);
		sbv.put("Operator", rGlobalInput.Operator);
		sbv.put("sCurrentDate", sCurrentDate);
		sbv.put("sCurrentTime", sCurrentTime);
		sbv.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		sbv.put("GetDate", rLPLiveInquirySchema.getInquiryDate());
		// logger.debug(UpdateSQL);
		rMap.put(sbv, "UPDATE");

		// ------------------------------------------------------------------

		// 更新 LCGet; 死亡后又录入生存的情况太复杂, 暂不考虑
		// UpdateSQL = "update LCGet a "
		// + "set a.GetEndState = '0', "
		// + "a.Operator = '" + rGlobalInput.Operator + "', "
		// + "a.ModifyDate = '" + sCurrentDate + "', "
		// + "a.ModifyTime = '" + sCurrentTime + "' "
		// + "where 1 = 1 "
		// + "and a.UrgeGetFlag = 'Y' "
		// + "and a.LiveGetType = '0' "
		// + "and a.PolNo in "
		// + "(select PolNo "
		// + "from LCPol "
		// + "where 1 = 1 "
		// + "and InsuredNo = '" + rLPLiveInquirySchema.getCustomerNo() + "')";
		// //logger.debug(UpdateSQL);
		// rMap.put(UpdateSQL, "UPDATE");

		// ------------------------------------------------------------------

		// 更新 LCContState; 死亡后又录入生存的情况太复杂, 暂不考虑
		// UpdateSQL = "update LCContState "
		// + "set State = '0', "
		// + "StartDate = '" + rLPLiveInquirySchema.getInquiryDate() + "', "
		// + "EndDate = '', "
		// + "Operator = '" + rGlobalInput.Operator + "', "
		// + "ModifyDate = '" + sCurrentDate + "', "
		// + "ModifyTime = '" + sCurrentTime + "' "
		// + "where 1 = 1 "
		// + "and InsuredNo = '" + rLPLiveInquirySchema.getCustomerNo() + "' "
		// + "and PolNo in "
		// + "(select PolNo "
		// + "from LCPol "
		// + "where 1 = 1 "
		// + "and InsuredNo = '" + rLPLiveInquirySchema.getCustomerNo() + "') "
		// + "and StateType = 'Terminate' "
		// + "and StateReason = '09' "
		// + "and State = '1' "
		// + "and EndDate is null";
		// //logger.debug(UpdateSQL);
		// rMap.put(UpdateSQL, "UPDATE");

		return true;
	} // function doCustomerLive end

	// ==========================================================================

	/**
	 * 处理客户死亡的情况
	 * 
	 * @return boolean
	 */
	private boolean doCustomerDied() {
		String UpdateSQL = new String("");
		String DeleteSQL = new String("");

		// ------------------------------------------------------------------

		// 更新 LDPerson
		rLDPersonSchema.setDeathDate(rLPLiveInquirySchema.getDiedDate());
		rLDPersonSchema.setOperator(rGlobalInput.Operator);
		rLDPersonSchema.setModifyDate(sCurrentDate);
		rLDPersonSchema.setModifyTime(sCurrentTime);
		rMap.put(rLDPersonSchema, "UPDATE");

		// ------------------------------------------------------------------

		// 删除 LJSGet
		DeleteSQL = "delete from LJSGet " + "where 1 = 1 "
				+ "and GetNoticeNo in " + "(select GetNoticeNo "
				+ "from LJSGetDraw " + "where 1 = 1 " + "and PolNo in "
				+ "(select PolNo " + "from LCPol " + "where 1 = 1 "
				+ "and InsuredNo = '?InsuredNo?') " + "and GetDate >= '?GetDate?')";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		sbv1.put("GetDate", rLPLiveInquirySchema.getDiedDate());
		// logger.debug(DeleteSQL);
		rMap.put(sbv1, "DELETE");

		// ------------------------------------------------------------------

		// 删除 LJSGetDraw
		DeleteSQL = "delete from LJSGetDraw " + "where 1 = 1 "
				+ "and PolNo in " + "(select PolNo " + "from LCPol "
				+ "where 1 = 1 " + "and InsuredNo = '?InsuredNo?') "
				+ "and GetDate >= '?GetDate?'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		sbv2.put("GetDate", rLPLiveInquirySchema.getDiedDate());
		rMap.put(sbv2, "DELETE");

		// ------------------------------------------------------------------

		// 更新 LCGet
		UpdateSQL = "update LCGet a " + "set a.GetEndState = '1', "
				+ "a.Operator = '?Operator?', "
				+ "a.ModifyDate = '?sCurrentDate?', "
				+ "a.ModifyTime = '?sCurrentTime?' "
				+ "where 1 = 1 "
				+ "and a.UrgeGetFlag = 'Y' "
				+ "and a.LiveGetType = '0' "
				+ "and a.PolNo in "
				+ "(select PolNo "
				+ "from LCPol "
				+ "where 1 = 1 "
				+ "and InsuredNo = '?InsuredNo?' "
				+ "and AppFlag = '1') "
				+ "and not exists "
				+ "(select 'X' "
				+ "from LMDutyGetAlive "
				+ "where 1 = 1 "
				+ "and GetDutyCode = a.GetDutyCode "
				+ "and GetDutyKind = a.GetDutyKind " + "and MaxGetCount >= 10)";
		// logger.debug(UpdateSQL);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(UpdateSQL);
		sbv3.put("Operator", rGlobalInput.Operator);
		sbv3.put("sCurrentDate", sCurrentDate);
		sbv3.put("sCurrentTime", sCurrentTime);
		sbv3.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		rMap.put(sbv3, "UPDATE");

		// ------------------------------------------------------------------

		// 更新 LCCont
		UpdateSQL = "update LCCont a " + "set a.AppFlag = '4', "
				+ "a.Operator = '?Operator?', "
				+ "a.ModifyDate = '?sCurrentDate?', "
				+ "a.ModifyTime = '?sCurrentTime?' "
				+ "where 1 = 1 "
				+ "and a.InsuredNo = '?InsuredNo?' "
				+ "and a.AppFlag = '1' "
				+ "and not exists "
				+ "(select 'X' "
				+ "from LCGet b "
				+ "where 1 = 1 "
				+ "and b.ContNo = a.ContNo "
				+ "and b.InsuredNo = a.InsuredNo "
				+ "and b.UrgeGetFlag = 'Y' "
				+ "and b.LiveGetType = '0' "
				+ "and b.LiveGetType = '0' "
				+ "and exists "
				+ "(select 'X' "
				+ "from LMDutyGetAlive "
				+ "where 1 = 1 "
				+ "and GetDutyCode = b.GetDutyCode "
				+ "and GetDutyKind = b.GetDutyKind "
				+ "and MaxGetCount >= 10))";
		// logger.debug(UpdateSQL);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(UpdateSQL);
		sbv4.put("Operator", rGlobalInput.Operator);
		sbv4.put("sCurrentDate", sCurrentDate);
		sbv4.put("sCurrentTime", sCurrentTime);
		sbv4.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		rMap.put(sbv4, "UPDATE");

		// ------------------------------------------------------------------

		// 更新 LCPol
		UpdateSQL = "update LCPol a " + "set a.AppFlag = '4', "
				+ "a.Operator = '?Operator?', "
				+ "a.ModifyDate = '?sCurrentDate?', "
				+ "a.ModifyTime = '?sCurrentTime?' "
				+ "where 1 = 1 "
				+ "and a.InsuredNo = '?InsuredNo?' "
				+ "and a.AppFlag = '1' "
				+ "and not exists "
				+ "(select 'X' "
				+ "from LCGet b "
				+ "where 1 = 1 "
				+ "and b.ContNo = a.ContNo "
				+ "and b.PolNo = a.PolNo "
				+ "and b.InsuredNo = a.InsuredNo "
				+ "and b.UrgeGetFlag = 'Y' "
				+ "and b.LiveGetType = '0' "
				+ "and exists "
				+ "(select 'X' "
				+ "from LMDutyGetAlive "
				+ "where 1 = 1 "
				+ "and GetDutyCode = b.GetDutyCode "
				+ "and GetDutyKind = b.GetDutyKind "
				+ "and MaxGetCount >= 10))";
		// logger.debug(UpdateSQL);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(UpdateSQL);
		sbv5.put("Operator", rGlobalInput.Operator);
		sbv5.put("sCurrentDate", sCurrentDate);
		sbv5.put("sCurrentTime", sCurrentTime);
		sbv5.put("InsuredNo", rLPLiveInquirySchema.getCustomerNo());
		rMap.put(sbv5, "UPDATE");

		// ------------------------------------------------------------------

		// 查询 LCPol
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setInsuredNo(rLPLiveInquirySchema.getCustomerNo());
		LCPolSet tLCPolSet = new LCPolSet();
		try {
			tLCPolSet = tLCPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约个单险种表出现异常！");
			return false;
		}
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);

				// ----------------------------------------------------------

				// 查询 LCContState
				LCContStateDB tLCContStateDB = new LCContStateDB();
				tLCContStateDB.setContNo(tLCPolSchema.getContNo());
				tLCContStateDB.setPolNo(tLCPolSchema.getPolNo());
				tLCContStateDB.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLCContStateDB.setStateType("Terminate");
				tLCContStateDB.setStateReason("09");
				LCContStateSet tLCContStateSet = new LCContStateSet();
				try {
					tLCContStateSet = tLCContStateDB.query();
				} catch (Exception ex) {
					CError.buildErr(this, "查询客户生调保单终止状态信息出现异常！");
					return false;
				}
				if (tLCContStateSet == null || tLCContStateSet.size() == 0) {
					// 从 LCPol 生成 LCContState
					LCContStateSchema tLCContStateSchemaNew = new LCContStateSchema();
					PubFun.copySchema(tLCContStateSchemaNew, tLCPolSchema);
					tLCContStateSchemaNew.setStateType("Terminate");
					tLCContStateSchemaNew.setState("1");
					tLCContStateSchemaNew.setStateReason("09"); // 死亡终止
					tLCContStateSchemaNew.setStartDate(rLPLiveInquirySchema
							.getDiedDate());
					tLCContStateSchemaNew.setEndDate("");
					tLCContStateSchemaNew.setOperator(rGlobalInput.Operator);
					tLCContStateSchemaNew.setMakeDate(sCurrentDate);
					tLCContStateSchemaNew.setMakeTime(sCurrentTime);
					tLCContStateSchemaNew.setModifyDate(sCurrentDate);
					tLCContStateSchemaNew.setModifyTime(sCurrentTime);
					rMap.put(tLCContStateSchemaNew, "DELETE&INSERT");
					tLCContStateSchemaNew = null;
				} else {
					// 更新 LCContState
					LCContStateSet tLCContStateSetNew = new LCContStateSet();
					for (int k = 1; k <= tLCContStateSet.size(); k++) {
						LCContStateSchema tLCContStateSchemaNew = new LCContStateSchema();
						tLCContStateSchemaNew = tLCContStateSet.get(k);
						tLCContStateSchemaNew.setState("1");
						tLCContStateSchemaNew.setStartDate(rLPLiveInquirySchema
								.getDiedDate());
						tLCContStateSchemaNew.setEndDate("");
						tLCContStateSchemaNew
								.setOperator(rGlobalInput.Operator);
						tLCContStateSchemaNew.setModifyDate(sCurrentDate);
						tLCContStateSchemaNew.setModifyTime(sCurrentTime);
						tLCContStateSetNew.add(tLCContStateSchemaNew);
						tLCContStateSchemaNew = null;
					}
					rMap.put(tLCContStateSetNew, "UPDATE");
					tLCContStateSetNew = null;
				}
				tLCContStateDB = null;
				tLCContStateSet = null;
				tLCPolSchema = null;
			}
		}
		tLCPolDB = null;
		tLCPolSet = null;

		return true;
	} // function doCustomerDied end

	// ==========================================================================

	/**
	 * 查询被保人期间实付纪录的次数 这里用来查询被保险人死亡之后发生的的实付纪录
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @return int
	 */
	public int getHasPayedCount(String sInsuredNo, String sStartDate,
			String sEndDate) {
		// logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedCount() 开始");

		int nHasPayedCount = 0;
		if (sInsuredNo == null || sInsuredNo.trim().equals("")
				|| sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "被保险人号码、统计起期为空。查询被保人期间实付纪录失败！");
			return -1;
		}
		if (sEndDate == null || sEndDate.trim().equals("")) {
			sEndDate = PubFun.getCurrentDate();
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select count(*) " + "from LJAGetDraw " + "where 1 = 1 "
				+ "and PolNo in " + "(select PolNo " + "from LCPol "
				+ "where 1 = 1 " + "and InsuredNo = '?sInsuredNo?' "
				+ "and AppFlag = '1') " + "and GetDate between to_date('?sStartDate?', 'yyyy-mm-dd') " + "and to_date('?sEndDate?', 'yyyy-mm-dd')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(QuerySQL);
		sbv6.put("sInsuredNo", sInsuredNo);
		sbv6.put("sStartDate", sStartDate);
		sbv6.put("sEndDate", sEndDate);
		

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		String sHasPayedCount = new String("");
		try {
			sHasPayedCount = tExeSQL.getOneValue(sbv6);
			nHasPayedCount = Integer.parseInt(sHasPayedCount);
		} catch (Exception ex) {
			logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedCount() : 查询并转换被保人期间实付纪录发生异常！");
			return -1;
		}
		tExeSQL = null;

		// logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedCount() 结束");
		return nHasPayedCount;
	} // function getHasPayedCount end

	// ==========================================================================

	/**
	 * 查询被保人期间实付纪录的合同号 这里用来查询被保险人死亡之后发生的的实付纪录
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @return String[][]
	 */
	public String[][] getHasPayedContNo(String sInsuredNo, String sStartDate,
			String sEndDate) {
		// logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedContNo() 开始");

		String[][] arrHasPayedContNo = new String[0][0];
		if (sInsuredNo == null || sInsuredNo.trim().equals("")
				|| sStartDate == null || sStartDate.trim().equals("")) {
			CError.buildErr(this, "被保险人号码、统计起期为空。查询被保人期间实付纪录失败！");
			return null;
		}
		if (sEndDate == null || sEndDate.trim().equals("")) {
			sEndDate = PubFun.getCurrentDate();
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select distinct ContNo " + "from LJAGetDraw "
				+ "where 1 = 1 " + "and PolNo in " + "(select PolNo "
				+ "from LCPol " + "where 1 = 1 " + "and InsuredNo = '?sInsuredNo?' " + "and AppFlag = '1') "
				+ "and GetDate between to_date('?sStartDate?', 'yyyy-mm-dd') " + "and to_date('?sEndDate?', 'yyyy-mm-dd')";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(QuerySQL);
		sbv7.put("sInsuredNo", sInsuredNo);
		sbv7.put("sStartDate", sStartDate);
		sbv7.put("sEndDate", sEndDate);
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sbv7);
		} catch (Exception ex) {
			logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedContNo() : 查询被保人期间实付纪录的合同号发生异常！");
			return null;
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			arrHasPayedContNo = new String[tSSRS.getMaxRow()][tSSRS.getMaxCol()];
			for (int i = 0; i < tSSRS.getMaxRow(); i++) {
				try {
					arrHasPayedContNo[i][0] = tSSRS.GetText(i + 1, 1);
				} catch (Exception ex) {
					logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedContNo() : 查询被保人期间实付纪录的合同号赋值给数组异常！");
					return null;
				}
			} // end for
		}
		tExeSQL = null;
		tSSRS = null;

		// logger.debug("\t@> PEdorLiveInquiryBL.getHasPayedContNo() 结束");
		return arrHasPayedContNo;
	} // function getHasPayedContNo end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rLPLiveInquirySchema != null)
			rLPLiveInquirySchema = null;
		if (rLDPersonSchema != null)
			rLDPersonSchema = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// //GlobalInput
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.Operator = "XinYQ";
	// tGlobalInput.ManageCom = "86";
	// //LPLiveInquirySchema
	// LPLiveInquirySchema tLPLiveInquirySchema = new LPLiveInquirySchema();
	// tLPLiveInquirySchema.setCustomerNo("000001620");
	// tLPLiveInquirySchema.setInquiryDate("2005-11-24");
	// tLPLiveInquirySchema.setInquiryResult("0");
	// tLPLiveInquirySchema.setInquiryOperator("XinYQ");
	// tLPLiveInquirySchema.setRemark("XinYQ 编码测试纪录");
	// //VData
	// VData tVData = new VData();
	// tVData.addElement(tGlobalInput);
	// tVData.addElement(tLPLiveInquirySchema);
	// //提交数据, 测试
	// PEdorLiveInquiryBL tPEdorLiveInquiryBL = new PEdorLiveInquiryBL();
	// if (!tPEdorLiveInquiryBL.submitData(tVData, "OPERATION"))
	// logger.debug("\t@> PEdorLiveInquiryBL.main() : 保全生调结果录入保存失败");
	// else
	// logger.debug("\t@> PEdorLiveInquiryBL.main() : 保全生调结果录入保存成功");
	// } //function main end
	// ==========================================================================

} // class PEdorLiveInquiryBL end
