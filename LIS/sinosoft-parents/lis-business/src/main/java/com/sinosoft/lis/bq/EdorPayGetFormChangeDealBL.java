package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2006-03-20, 2006-05-24, 2006-06-28
 * @direction: 保全收付费方式变更结果保存
 ******************************************************************************/

import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class EdorPayGetFormChangeDealBL implements BusinessService{
private static Logger logger = Logger.getLogger(EdorPayGetFormChangeDealBL.class);
	// public EdorPayGetFormChangeDealBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	// 本类变量
	private LJSPaySchema rLJSPaySchema;
	private LJAGetSchema rLJAGetSchema;
	private LPEdorAppSchema rLPEdorAppSchema;
	// 输出数据
	private MMap rMap;
	private VData rResultData;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.submitData()
		// 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> EdorPayGetFormChangeDealBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.submitData()
		// 成功");
		rResultData.clear();
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
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.getInputData()
		// 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> EdorPayGetFormChangeDealBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取用户提交的信息！");
			logger.debug("\t@> EdorPayGetFormChangeDealBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 变更类型
		String sChangeType = (String) rTransferData
				.getValueByName("ChangeType");
		if (sChangeType == null || sChangeType.trim().equals("")) {
			CError.buildErr(this, "无法获取变更类型信息！");
			return false;
		}

		// 保全受理号
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全受理号信息！");
			return false;
		}

		// 领取通知书号
		String sGetNoticeNo = (String) rTransferData
				.getValueByName("GetNoticeNo");
		if (sGetNoticeNo == null || sGetNoticeNo.trim().equals("")) {
			CError.buildErr(this, "无法获取领取通知书号信息！");
			return false;
		}

		// 收付费方式
		String sFormType = (String) rTransferData.getValueByName("FormType");
		if (sFormType == null || sFormType.trim().equals("")) {
			CError.buildErr(this, "无法获取收付费方式信息！");
			return false;
		}

		// 银行划款或网上支付
		if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) {
			String sBankCode = (String) rTransferData
					.getValueByName("BankCode");
			String sBankAccNo = (String) rTransferData
					.getValueByName("BankAccNo");
			String sAccName = (String) rTransferData.getValueByName("AccName");
			if (sBankCode == null || sBankCode.trim().equals("")
					|| sBankAccNo == null || sBankAccNo.trim().equals("")
					|| sAccName == null || sAccName.trim().equals("")) {
				CError.buildErr(this, "银行划款或网上支付必须录入完整的银行帐户信息！");
				return false;
			}
		}

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.getInputData()
		// 成功");
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
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.checkData() 开始");

		String sChangeType = (String) rTransferData
				.getValueByName("ChangeType");
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		String sGetNoticeNo = (String) rTransferData
				.getValueByName("GetNoticeNo");
		String sActuGetNo = (String) rTransferData
		.getValueByName("ActuGetNo");
		String sFormType = (String) rTransferData.getValueByName("FormType");

		// ----------------------------------------------------------------------

		// 检查保全申请总表
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(sEdorAcceptNo);
		LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
		try {
			tLPEdorAppSet = tLPEdorAppDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全申请总表出现异常！");
			return false;
		}
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0) {
			CError.buildErr(this, "在保全申请总表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPEdorAppSchema = new LPEdorAppSchema();
			rLPEdorAppSchema.setSchema(tLPEdorAppSet.get(1));
		}
		// 垃圾处理
		tLPEdorAppSet = null;
		tLPEdorAppDB = null;

		// ----------------------------------------------------------------------

		// 检查批改状态
		// String sEdorState = rLPEdorAppSchema.getEdorState();
		// if (sEdorState.equals("0") || sEdorState.equals("4") ||
		// sEdorState.equals("7") || sEdorState.equals("8") ||
		// sEdorState.equals("9") || sEdorState.equals("b"))
		// {
		// //select * from LDCode where CodeType = 'edorstate'
		// String QuerySQL = "select distinct CodeName from LDCode where
		// CodeType = 'edorstate' and Code = '" + sEdorState +"'";
		// ExeSQL tExeSQL = new ExeSQL();
		// String sStateName = tExeSQL.getOneValue(QuerySQL);
		// tExeSQL = null;
		// CError.buildErr(this, "该保全目前为" + sStateName + "状态。不允许变更！");
		// return false;
		// }

		// ----------------------------------------------------------------------


		// ----------------------------------------------------------------------

		// 检查是否银行在途
		if (sChangeType.trim().equals("1")) // 收费
		{
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setGetNoticeNo(sGetNoticeNo);
			tLJSPayDB.setOtherNo(sEdorAcceptNo);
			tLJSPayDB.setOtherNoType("10");
			LJSPaySet tLJSPaySet = new LJSPaySet();
			try {
				tLJSPaySet = tLJSPayDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询应收总表判断是否银行在途出现异常！");
				return false;
			}
			if (tLJSPaySet == null || tLJSPaySet.size() <= 0) {
				CError.buildErr(this, "在应收总表中找不到待操作批单的纪录！");
				return false;
			} else {
				rLJSPaySchema = new LJSPaySchema();
				rLJSPaySchema.setSchema(tLJSPaySet.get(1));
			}
			// 在途标记
			if (rLJSPaySchema.getBankOnTheWayFlag() != null
					&& rLJSPaySchema.getBankOnTheWayFlag().trim().equals("1")) {
				CError.buildErr(this, "银行在途，暂不允许修改收费方式！");
				return false;
			}
			// 垃圾处理
			tLJSPaySet = null;
			tLJSPayDB = null;
		} else if (sChangeType.trim().equals("2")) // 付费
		{

			// ===add===zhangtao===2007-01-29===团体付费方式控制===============END==============

			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setGetNoticeNo(sGetNoticeNo);
			tLJAGetDB.setActuGetNo(sActuGetNo);
			//modidfy by jiaqiangli 2009-02-22 ljaget.otherno=lpedorapp.edorconfno 为归档号
			tLJAGetDB.setOtherNo(rLPEdorAppSchema.getEdorConfNo());
			tLJAGetDB.setOtherNoType("10");
			LJAGetSet tLJAGetSet = new LJAGetSet();
			try {
				tLJAGetSet = tLJAGetDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询实收总表判断是否银行在途出现异常！");
				return false;
			}
			if (tLJAGetSet == null || tLJAGetSet.size() <= 0) {
				CError.buildErr(this, "在实收总表中找不到待操作批单的纪录！");
				return false;
			} else {
				rLJAGetSchema = new LJAGetSchema();
				rLJAGetSchema.setSchema(tLJAGetSet.get(1));
			}
			// 在途标记
			if (rLJAGetSchema.getBankOnTheWayFlag() != null
					&& rLJAGetSchema.getBankOnTheWayFlag().trim().equals("1")) {
				CError.buildErr(this, "银行在途，暂不允许修改付费方式！");
				return false;
			}
			// 是否到帐
			if (rLJAGetSchema.getEnterAccDate() != null
					&& !rLJAGetSchema.getEnterAccDate().trim().equals("")) {
				CError.buildErr(this, "财务已到帐，不允许修改付费方式！");
				return false;
			}
			// 垃圾处理
			tLJAGetSet = null;
			tLJAGetDB = null;
		} else {
			CError.buildErr(this, "未知的收付费方式变更类型！");
			return false;
		}

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.checkData() 成功");
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
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.dealData() 开始");

		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		// 接收数据变量
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		String sGetNoticeNo = (String) rTransferData
				.getValueByName("GetNoticeNo");
		String sActuGetNo = (String) rTransferData
		.getValueByName("ActuGetNo");
		String sChangeType = (String) rTransferData
				.getValueByName("ChangeType");
		String sFormType = (String) rTransferData.getValueByName("FormType");
		String sBankCode = (String) rTransferData.getValueByName("BankCode");
		String sBankAccNo = (String) rTransferData.getValueByName("BankAccNo");
		String sAccName = (String) rTransferData.getValueByName("AccName");
		String sPayGetName = (String) rTransferData
				.getValueByName("PayGetName");
		String sPersonID = (String) rTransferData.getValueByName("PersonID");

		// ----------------------------------------------------------------------

		// 取出变更轨迹的最大值
//		LPPayGetChangeTrackDB tLPPayGetChangeTrackDB = new LPPayGetChangeTrackDB();
//		tLPPayGetChangeTrackDB.setGetNoticeNo(sGetNoticeNo);
//		tLPPayGetChangeTrackDB.setEdorAcceptNo(sEdorAcceptNo);
//		LPPayGetChangeTrackSet tLPPayGetChangeTrackSet = new LPPayGetChangeTrackSet();
//		try {
//			tLPPayGetChangeTrackSet = tLPPayGetChangeTrackDB.query();
//		} catch (Exception ex) {
//			CError.buildErr(this, "查询收付费方式变更轨迹表出现异常！");
//			return false;
//		}
//		int nChangeTimes = 1; // 默认第一次变更
//		if (tLPPayGetChangeTrackSet != null) {
//			nChangeTimes = tLPPayGetChangeTrackSet.size() + 1;
//		}
//		// 垃圾处理
////		tLPPayGetChangeTrackSet = null;
////		tLPPayGetChangeTrackDB = null;

		// ----------------------------------------------------------------------

//		LPPayGetChangeTrackSchema tLPPayGetChangeTrackSchema = new LPPayGetChangeTrackSchema();
//		tLPPayGetChangeTrackSchema.setGetNoticeNo(sGetNoticeNo);
//		tLPPayGetChangeTrackSchema.setEdorAcceptNo(sEdorAcceptNo);
//		tLPPayGetChangeTrackSchema.setChangeTimes(nChangeTimes);
//		tLPPayGetChangeTrackSchema
//				.setManageCom(rLPEdorAppSchema.getManageCom());
//		tLPPayGetChangeTrackSchema.setOtherNo(rLPEdorAppSchema.getOtherNo());
//		tLPPayGetChangeTrackSchema.setOtherNoType(rLPEdorAppSchema
//				.getOtherNoType());

		// 分情况记录原收付费方式
		if (sChangeType.trim().equals("1")) // 收费
		{
			// 轨迹表
//			tLPPayGetChangeTrackSchema.setChangeType("1");
//			tLPPayGetChangeTrackSchema.setOldPayGetForm(rLPEdorAppSchema
//					.getPayForm());
//			tLPPayGetChangeTrackSchema.setOldBankCode(rLJSPaySchema
//					.getBankCode());
//			tLPPayGetChangeTrackSchema.setOldBankAccNo(rLJSPaySchema
//					.getBankAccNo());
//			tLPPayGetChangeTrackSchema
//					.setOldAccName(rLJSPaySchema.getAccName());
//			tLPPayGetChangeTrackSchema.setOldDrawer(rLPEdorAppSchema
//					.getPayGetName());
//			tLPPayGetChangeTrackSchema.setOldDrawerID(rLPEdorAppSchema
//					.getPersonID());
			// 应收表
			if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) // 银行划款或网上支付
			{
				rLJSPaySchema.setBankCode(sBankCode);
				rLJSPaySchema.setBankAccNo(sBankAccNo);
				rLJSPaySchema.setAccName(sAccName);
				
			} else {
				// 不是银行划款或网上支付则清空下列三项
				rLJSPaySchema.setBankCode("");
				rLJSPaySchema.setBankAccNo("");
				rLJSPaySchema.setAccName("");
			}
			rLJSPaySchema.setModifyDate(sCurrentDate);
			rLJSPaySchema.setModifyTime(sCurrentTime);
			
			
			rMap.put(rLJSPaySchema, "UPDATE"); // MMAP -> 1
			
			// 保全申请总表赋值
			if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) // 银行划款或网上支付
			{
				rLPEdorAppSchema.setBankCode(sBankCode);
				rLPEdorAppSchema.setBankAccNo(sBankAccNo);
				rLPEdorAppSchema.setAccName(sAccName);
			} else {
				// 不是银行划款或网上支付则清空下列三项
				rLPEdorAppSchema.setBankCode("");
				rLPEdorAppSchema.setBankAccNo("");
				rLPEdorAppSchema.setAccName("");
			}
			rLPEdorAppSchema.setPayForm(sFormType);
			rLPEdorAppSchema.setGetForm(sFormType);
			rLPEdorAppSchema.setPayGetName(sPayGetName);
			rLPEdorAppSchema.setPersonID(sPersonID);
			rLPEdorAppSchema.setOperator(rGlobalInput.Operator);
			rLPEdorAppSchema.setModifyDate(sCurrentDate);
			rLPEdorAppSchema.setModifyTime(sCurrentTime);
			// 更新表
			rMap.put(rLPEdorAppSchema, "UPDATE"); // MMAP -> 4
		} else if (sChangeType.trim().equals("2")) // 付费
		{
			// 轨迹表
//			tLPPayGetChangeTrackSchema.setChangeType("2");
//			tLPPayGetChangeTrackSchema.setOldPayGetForm(rLPEdorAppSchema
//					.getGetForm());
//			tLPPayGetChangeTrackSchema.setOldBankCode(rLJAGetSchema
//					.getBankCode());
//			tLPPayGetChangeTrackSchema.setOldBankAccNo(rLJAGetSchema
//					.getBankAccNo());
//			tLPPayGetChangeTrackSchema
//					.setOldAccName(rLJAGetSchema.getAccName());
//			tLPPayGetChangeTrackSchema.setOldDrawer(rLPEdorAppSchema
//					.getPayGetName());
//			tLPPayGetChangeTrackSchema.setOldDrawerID(rLPEdorAppSchema
//					.getPersonID());
			// 实付表
			if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) // 银行划款或网上支付
			{
				rLJAGetSchema.setBankCode(sBankCode);
				rLJAGetSchema.setBankAccNo(sBankAccNo);
				rLJAGetSchema.setAccName(sAccName);
				
			} else {
				// 不是银行划款或网上支付则清空下列三项
				rLJAGetSchema.setBankCode("");
				rLJAGetSchema.setBankAccNo("");
				rLJAGetSchema.setAccName("");
			}
			rLJAGetSchema.setPayMode(sFormType);
			rLJAGetSchema.setDrawer(sPayGetName);
			rLJAGetSchema.setDrawerID(sPersonID);
			rLJAGetSchema.setModifyDate(sCurrentDate);
			rLJAGetSchema.setModifyTime(sCurrentTime);
			rMap.put(rLJAGetSchema, "UPDATE"); // MMAP -> 2
		}

		// 变更后的收付费方式
//		tLPPayGetChangeTrackSchema.setNewPayGetForm(sFormType);
//		if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) // 银行划款或网上支付
//		{
//			tLPPayGetChangeTrackSchema.setNewBankCode(sBankCode);
//			tLPPayGetChangeTrackSchema.setNewBankAccNo(sBankAccNo);
//			tLPPayGetChangeTrackSchema.setNewAccName(sAccName);
//		}
//		tLPPayGetChangeTrackSchema.setNewDrawer(sPayGetName);
//		tLPPayGetChangeTrackSchema.setNewDrawerID(sPersonID);
//		// 常规字段的赋值
//		tLPPayGetChangeTrackSchema.setOperator(rGlobalInput.Operator);
//		tLPPayGetChangeTrackSchema.setMakeDate(sCurrentDate);
//		tLPPayGetChangeTrackSchema.setMakeTime(sCurrentTime);
//		tLPPayGetChangeTrackSchema.setModifyDate(sCurrentDate);
//		tLPPayGetChangeTrackSchema.setModifyTime(sCurrentTime);
//		rMap.put(tLPPayGetChangeTrackSchema, "INSERT"); // MMAP -> 3

		// ----------------------------------------------------------------------

		// 保全申请总表赋值
//		if (sFormType.trim().equals("4") || sFormType.trim().equals("7")) // 银行划款或网上支付
//		{
//			rLPEdorAppSchema.setBankCode(sBankCode);
//			rLPEdorAppSchema.setBankAccNo(sBankAccNo);
//			rLPEdorAppSchema.setAccName(sAccName);
//		} else {
//			// 不是银行划款或网上支付则清空下列三项
//			rLPEdorAppSchema.setBankCode("");
//			rLPEdorAppSchema.setBankAccNo("");
//			rLPEdorAppSchema.setAccName("");
//		}
//		rLPEdorAppSchema.setPayForm(sFormType);
//		rLPEdorAppSchema.setGetForm(sFormType);
//		rLPEdorAppSchema.setPayGetName(sPayGetName);
//		rLPEdorAppSchema.setPersonID(sPersonID);
//		rLPEdorAppSchema.setOperator(rGlobalInput.Operator);
//		rLPEdorAppSchema.setModifyDate(sCurrentDate);
//		rLPEdorAppSchema.setModifyTime(sCurrentTime);
//		// 更新表
//		rMap.put(rLPEdorAppSchema, "UPDATE"); // MMAP -> 4

		// ----------------------------------------------------------------------

		// 插入打印管理表
//		String sNoneLimitNo = PubFun.getNoLimit(rGlobalInput.ManageCom);
//		String sResultMaxNo = PubFun1.CreateMaxNo("PRTSEQNO", sNoneLimitNo);
//		if (sResultMaxNo == null || sResultMaxNo.trim().equals("")) {
//			CError.buildErr(this, "生成唯一性的变更通知书打印流水号失败！");
//			return false;
//		}
//		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
//		tLOPRTManagerSchema.setPrtSeq(sResultMaxNo);
//		tLOPRTManagerSchema.setOtherNo(sEdorAcceptNo);
//		tLOPRTManagerSchema.setOtherNoType("06");
//		tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorPayGetForm);
//		tLOPRTManagerSchema.setManageCom(rGlobalInput.ManageCom);
//		tLOPRTManagerSchema.setReqCom(rGlobalInput.ManageCom);
//		tLOPRTManagerSchema.setReqOperator(rGlobalInput.Operator);
//		tLOPRTManagerSchema.setPrtType("0");
//		tLOPRTManagerSchema.setStateFlag("0");
//		tLOPRTManagerSchema.setPatchFlag("0");
//		tLOPRTManagerSchema.setStandbyFlag1(sGetNoticeNo);
//		tLOPRTManagerSchema.setStandbyFlag2(rLPEdorAppSchema.getOtherNo());
//		tLOPRTManagerSchema.setStandbyFlag3(rLPEdorAppSchema.getOtherNoType());
//		tLOPRTManagerSchema.setStandbyFlag4(String.valueOf(nChangeTimes)); // 变更次数
//		tLOPRTManagerSchema.setStandbyFlag5(sChangeType); // 变更类型
//		tLOPRTManagerSchema.setStandbyFlag6(String.valueOf(rLPEdorAppSchema
//				.getGetMoney()));
//		tLOPRTManagerSchema.setMakeDate(sCurrentDate);
//		tLOPRTManagerSchema.setMakeTime(sCurrentTime);
//		rMap.put(tLOPRTManagerSchema, "INSERT"); // MMAP -> 5
//		tLOPRTManagerSchema = null;

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.outputData()
		// 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.outputData()
		// 成功");
		return true;
	} // function prepareOutputData end

	// ==========================================================================

	/**
	 * 提交本类的处理结果到数据库
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// logger.debug("\t@> EdorPayGetFormChangeDealBL.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> EdorPayGetFormChangeDealBL.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		// 垃圾处理
		tPubSubmit = null;

		// logger.debug("\t@> EdorPayGetFormChangeDealBL.pubSubmit() 成功");
		return true;
	} // function pubSubmit end

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
		if (rTransferData != null)
			rTransferData = null;
		if (rLJSPaySchema != null)
			rLJSPaySchema = null;
		if (rLJAGetSchema != null)
			rLJAGetSchema = null;
		if (rLPEdorAppSchema != null)
			rLPEdorAppSchema = null;
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
	// EdorPayGetFormChangeDealBL tEdorPayGetFormChangeDealBL = new
	// EdorPayGetFormChangeDealBL();
	// } //function main end
	// ==========================================================================

} // class EdorPayGetFormChangeDealBL end
