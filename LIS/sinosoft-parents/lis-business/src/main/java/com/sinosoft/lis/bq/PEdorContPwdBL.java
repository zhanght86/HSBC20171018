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
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07, 1.08
 * @date     : 2006-06-26, 2006-08-24, 2006-11-06, 2006-11-16, 2006-11-29, 2006-12-04, 2006-12-07, 2007-03-20, 2007-04-16
 * @direction: 保全保单密码挂失、解挂、修改、取消、校验
 * @comment  : 猪的生日 :)
 ******************************************************************************/


import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.encrypt.LisEncrypt;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorContPwdBL {
private static Logger logger = Logger.getLogger(PEdorContPwdBL.class);
	// public PEdorContPwdBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	private LCContSet rLCContSet;
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
		// logger.debug("\t@> PEdorContPwdBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorContPwdBL.submitData() : 无法获取 InputData 数据！");
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
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		if (!pubSubmit())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorContPwdBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorContPwdBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorContPwdBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorContPwdBL.dealData() 开始");

		rMap = new MMap();

		// ----------------------------------------------------------------------

		if (!isEnableEdorContPwd()) {
			CError.buildErr(this, "当前系统未启用保单密码功能。不允许操作！");
			return false;
		}

		// ----------------------------------------------------------------------

		if (rOperation.equalsIgnoreCase("Lost")) {
			if (!doLostContPwd())
				return false;
		} else if (rOperation.equalsIgnoreCase("Unlock")) {
			if (!doUnlockContPwd())
				return false;
		} else if (rOperation.equalsIgnoreCase("Change")) {
			if (!doChangeContPwd())
				return false;
		} else if (rOperation.equalsIgnoreCase("Delete")) {
			if (!doDeleteContPwd())
				return false;
		} else if (rOperation.equalsIgnoreCase("Verify")) {
			if (!doVerifyContPwd())
				return false;
		} else {
			CError.buildErr(this, "未知的保单密码操作类型！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 进行保单密码挂失
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doLostContPwd() {
		// logger.debug("\t@> PEdorContPwdBL.doLostContPwd() 开始");

		if (!getInputTransferData())
			return false;

		// ----------------------------------------------------------------------

		// 接收数据变量
		String sContNo = (String) rTransferData.getValueByName("ContNo");

		// ----------------------------------------------------------------------

		if (!isPContExists(sContNo))
			return false;

		// ----------------------------------------------------------------------

		if (!isPContHasPwd(sContNo)) {
			CError.buildErr(this, "该保单尚未设置密码或密码为空，不需要挂失密码！");
			return false;
		}

		if (isInPwdLostState(sContNo)) {
			CError.buildErr(this, "该保单已经挂失密码，不允许重复挂失！");
			return false;
		}

		if (!createPwdState(sContNo, "1")) {
			CError.buildErr(this, "创建保单 " + sContNo + " 密码挂失状态失败！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.doLostContPwd() 结束");
		return true;
	} // function doLostContPwd end

	// ==========================================================================

	/**
	 * 进行保单密码解挂
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doUnlockContPwd() {
		// logger.debug("\t@> PEdorContPwdBL.doUnlockContPwd() 开始");

		if (!getInputTransferData())
			return false;

		// ----------------------------------------------------------------------

		// 接收数据变量
		String sContNo = (String) rTransferData.getValueByName("ContNo");

		// ----------------------------------------------------------------------

		if (!isPContExists(sContNo))
			return false;

		// ----------------------------------------------------------------------

		if (!isPContHasPwd(sContNo)) {
			CError.buildErr(this, "该保单尚未设置密码或密码为空，不需要解挂密码！");
			return false;
		}

		if (!isInPwdUnlockState(sContNo)) {
			CError.buildErr(this, "保单尚未挂失密码，不允许解挂！");
			return false;
		}

		if (!isOutLostState(sContNo)) {
			CError.buildErr(this, "该保单密码已经挂失, 满七日才可解挂密码！");
			return false;
		}

		if (!createPwdState(sContNo, "0")) {
			CError.buildErr(this, "创建保单 " + sContNo + " 密码解挂状态失败！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.doUnlockContPwd() 结束");
		return true;
	} // function doUnlockContPwd end

	// ==========================================================================

	/**
	 * 设定新的保单密码
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doChangeContPwd() {
		// logger.debug("\t@> PEdorContPwdBL.doChangeContPwd() 开始");

		if (!getInputTransferData())
			return false;

		// ----------------------------------------------------------------------

		// 接收数据变量
		String sContNo = (String) rTransferData.getValueByName("ContNo");
		String sOldPassword = (String) rTransferData
				.getValueByName("OldPassword");
		String sNewPassword = (String) rTransferData
				.getValueByName("NewPassword");

		// ----------------------------------------------------------------------

		if (!isPContExists(sContNo))
			return false;

		// ----------------------------------------------------------------------

		if (!isOutLostState(sContNo)) {
			CError.buildErr(this, "该保单密码已经挂失, 满七日才可更改密码！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 校验当前保单密码
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = getLCContSchema(sContNo);
		if (tLCContSchema == null) {
			return false;
		}
		if (sOldPassword == null || sOldPassword.trim().equals("")) {
			if (tLCContSchema.getPassword() != null) {
				CError.buildErr(this, "保单当前密码不为空，请输入正确的密码！");
				return false;
			}
		} else {
			sOldPassword = LisEncrypt.encrypt(sOldPassword);
			boolean isRightPassword = sOldPassword.equals(tLCContSchema
					.getPassword());
			if (!chkErrorPwdTimes(sContNo, isRightPassword)) {
				return false;
			}
			if (!isRightPassword) {
				CError.buildErr(this, "保单当前密码有误。不允许变更！");
				return false;
			}
		}

		// ----------------------------------------------------------------------

		// 生成 EdorNo, 准备插入 LPCont
		String sNoneLimitNo = PubFun.getNoLimit(rGlobalInput.ManageCom);
		String sResultMaxNo = PubFun1.CreateMaxNo("EdorAppNo", sNoneLimitNo);
		if (sResultMaxNo == null || sResultMaxNo.trim().equals("")) {
			CError.buildErr(this, "生成唯一性的保全批单号失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 插入 LPCont
		LPContSchema tLPContSchemaNew = new LPContSchema();
		PubFun.copySchema(tLPContSchemaNew, tLCContSchema);
		tLPContSchemaNew.setEdorNo(sResultMaxNo);
		tLPContSchemaNew.setEdorType("PW"); // 没有描述进 LMEdorItem, 仅标示是修改保单密码产生
		// tLPContSchemaNew.setOperator(rGlobalInput.Operator);
		tLPContSchemaNew.setModifyDate(sCurrentDate);
		tLPContSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLPContSchemaNew, "INSERT");
		tLPContSchemaNew = null;

		// ----------------------------------------------------------------------

		// 加密保存新的密码
		if (sNewPassword == null || sNewPassword.trim().equals("")) {
			tLCContSchema.setPassword("");
		} else {
			sNewPassword = LisEncrypt.encrypt(sNewPassword);
			tLCContSchema.setPassword(sNewPassword);
		}
		tLCContSchema.setOperator(rGlobalInput.Operator);
		tLCContSchema.setModifyDate(sCurrentDate);
		tLCContSchema.setModifyTime(sCurrentTime);
		rMap.put(tLCContSchema, "UPDATE");
		tLCContSchema = null;

		// ----------------------------------------------------------------------

		// 创建密码解挂状态
		if (!createPwdState(sContNo, "0")) {
			CError.buildErr(this, "创建保单 " + sContNo + " 密码解挂状态失败！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.doChangeContPwd() 结束");
		return true;
	} // function doChangeContPwd end

	// ==========================================================================

	/**
	 * 取消保单密码
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doDeleteContPwd() {
		// logger.debug("\t@> PEdorContPwdBL.doDeleteContPwd() 开始");

		if (!getInputTransferData())
			return false;

		// ----------------------------------------------------------------------

		// 接收数据变量
		String sContNo = (String) rTransferData.getValueByName("ContNo");

		// ----------------------------------------------------------------------

		if (!isPContExists(sContNo))
			return false;

		// ----------------------------------------------------------------------

		if (!isPContHasPwd(sContNo)) {
			CError.buildErr(this, "该保单尚未设置密码或密码为空，不需要删除密码！");
			return false;
		}

		if (!isInPwdLostState(sContNo)) {
			CError.buildErr(this, "该保单尚未挂失密码，不允许删除！");
			return false;
		}

		if (!isOutLostState(sContNo)) {
			CError.buildErr(this, "该保单密码已经挂失, 满七日才可删除密码！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 获取 LCCont
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = getLCContSchema(sContNo);
		if (tLCContSchema == null) {
			CError.buildErr(this, "该保单号在系统中不存在！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 生成 EdorNo, 准备插入 LPCont
		String sNoneLimitNo = PubFun.getNoLimit(rGlobalInput.ManageCom);
		String sResultMaxNo = PubFun1.CreateMaxNo("EdorAppNo", sNoneLimitNo);
		if (sResultMaxNo == null || sResultMaxNo.trim().equals("")) {
			CError.buildErr(this, "生成唯一性的保全批单号失败！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 插入 LPCont
		LPContSchema tLPContSchemaNew = new LPContSchema();
		PubFun.copySchema(tLPContSchemaNew, tLCContSchema);
		tLPContSchemaNew.setEdorNo(sResultMaxNo);
		tLPContSchemaNew.setEdorType("PW"); // 没有描述进 LMEdorItem, 仅标示是修改保单密码产生
		// tLPContSchemaNew.setOperator(rGlobalInput.Operator);
		tLPContSchemaNew.setModifyDate(sCurrentDate);
		tLPContSchemaNew.setModifyTime(sCurrentTime);
		rMap.put(tLPContSchemaNew, "INSERT");
		tLPContSchemaNew = null;

		// ----------------------------------------------------------------------

		// 更新 LCCont
		tLCContSchema.setPassword("");
		tLCContSchema.setOperator(rGlobalInput.Operator);
		tLCContSchema.setModifyDate(sCurrentDate);
		tLCContSchema.setModifyTime(sCurrentTime);
		rMap.put(tLCContSchema, "UPDATE");
		tLCContSchema = null;

		// ----------------------------------------------------------------------

		// 创建密码解挂状态
		if (!createPwdState(sContNo, "0")) {
			CError.buildErr(this, "创建保单 " + sContNo + " 密码解挂状态失败！");
			return false;
		}

		// logger.debug("\t@> PEdorContPwdBL.doDeleteContPwd() 结束");
		return true;
	} // function doDeleteContPwd end

	// ==========================================================================

	/**
	 * 进行保单密码解挂
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doVerifyContPwd() {
		// logger.debug("\t@> PEdorContPwdBL.doVerifyContPwd() 开始");

		if (!getInputLCContSet())
			return false;

		// ----------------------------------------------------------------------

		// 执行密码校验
		for (int i = 1; i <= rLCContSet.size(); i++) {
			LCContSchema tLCContSchemaTmp = new LCContSchema();
			tLCContSchemaTmp = rLCContSet.get(i);
			String sContNo = tLCContSchemaTmp.getContNo();
			if (sContNo == null || sContNo.trim().equals("")) {
				CError.buildErr(this, "需要校验密码的保单号不能为空！");
				return false;
			}

			// ------------------------------------------------------------------

			// 只要保单密码已挂失, 则不允许变更保全项目
			if (isInPwdLostState(sContNo)) {
				CError.buildErr(this, "保单 " + sContNo + " 已经挂失密码，请先对其解挂！");
				return false;
			}

			// ------------------------------------------------------------------

			LCContSchema tLCContSchemaOld = new LCContSchema();
			tLCContSchemaOld = getLCContSchema(sContNo);
			if (tLCContSchemaOld == null) {
				CError.buildErr(this, "需要校验密码的保单不存在！");
				return false;
			}
			String sOldPassword = tLCContSchemaOld.getPassword();
			String sTmpPassword = tLCContSchemaTmp.getPassword();
			sTmpPassword = LisEncrypt.encrypt(sTmpPassword);
			boolean isRightPassword = sOldPassword.equals(sTmpPassword);
			if (!chkErrorPwdTimes(sContNo, isRightPassword)) {
				return false;
			}
			if (!isRightPassword) {
				CError.buildErr(this, "保单号 " + sContNo + " 密码有误。请重新输入！");
				return false;
			}
			tLCContSchemaOld = null;
			tLCContSchemaTmp = null;
		}

		// logger.debug("\t@> PEdorContPwdBL.doVerifyContPwd() 结束");
		return true;
	} // function doVerifyContPwd end

	// ==========================================================================

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> PEdorContPwdBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorContPwdBL.outputData() 结束");
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
		// logger.debug("\t@> PEdorContPwdBL.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorContPwdBL.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;

		// logger.debug("\t@> PEdorContPwdBL.pubSubmit() 结束");
		return true;
	} // function pubSubmit end

	// ==========================================================================

	/**
	 * 获取输入数据的 TransferData
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputTransferData() {
		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取用户提交的信息！");
			logger.debug("\t@> PEdorContPwdBL.getInputTransferData() : 无法获取 TransferData 数据！");
			return false;
		}

		// 保单号码
		String sContNo = (String) rTransferData.getValueByName("ContNo");
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取待处理的保单号码信息！");
			return false;
		}

		return true;
	} // function getInputTransferData end

	// ==========================================================================

	/**
	 * 获取输入数据的 LCContSet
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputLCContSet() {
		rLCContSet = new LCContSet();
		rLCContSet = (LCContSet) rInputData.getObjectByObjectName("LCContSet",
				0);
		if (rLCContSet == null || rLCContSet.size() == 0) {
			CError.buildErr(this, "无法获取用户提交的保单及密码信息！");
			logger.debug("\t@> PEdorContPwdBL.getInputLCContSet() : 无法获取 LCContSet 数据！");
			return false;
		}

		return true;
	} // function getInputLCContSet end

	// ==========================================================================

	/**
	 * 检查系统是否启用保单密码
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean isEnableEdorContPwd() {
		String QuerySQL = new String("");
		QuerySQL = "select SysVarValue from LDSysVar where SysVar = 'EnableEdorContPwd'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		ExeSQL tExeSQL = new ExeSQL();
		String sEdorContPwdState = new String("");
		try {
			sEdorContPwdState = tExeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "检查系统是否启用保单密码功能出现异常！");
			return false;
		}
		if (sEdorContPwdState != null && sEdorContPwdState.trim().equals("1")) {
			return true;
		}
		tExeSQL = null;

		return false;
	} // function isEnableEdorContPwd end

	// ==========================================================================

	/**
	 * 检查保单是否存在
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean isPContExists(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单是否存在的参数合同号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 查询 LCCont, 使用 SSRS 先查询一次是因为 DB.query 不支持 like
		String QuerySQL = new String("");
		QuerySQL = "select 'X' " + "from LCCont " + "where 1 = 1 "
				+ "and ContNo = '?sContNo?' " + "and ManageCom like concat('?ManageCom?','%') " + "and AppFlag = '1'";
		// logger.debug(QuerySQL);
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(QuerySQL);
        sqlbv.put("sContNo", sContNo);
        sqlbv.put("ManageCom", rGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		try {
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询当前管理机构保单信息出现异常！");
			return false;
		}
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			CError.buildErr(this, "请确认该保单号存在、属于当前管理机构且保单状态为有效！");
			return false;
		}
		tExeSQL = null;
		tSSRS = null;

		return true;
	} // function isPContExists end

	// ==========================================================================

	/**
	 * 查询获取保单对应的 Schema
	 * 
	 * @param String
	 * @return LCContSchema
	 */
	private LCContSchema getLCContSchema(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单是否存在的参数合同号不能为空！");
			return null;
		}

		// ----------------------------------------------------------------------

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(sContNo);
		tLCContDB.setAppFlag("1");
		LCContSet tLCContSet = new LCContSet();
		LCContSchema tLCContSchema = new LCContSchema();
		try {
			tLCContSet = tLCContDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约合同表出现异常！");
			return null;
		}
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			CError.buildErr(this, "在新契约合同表中找不到待操作保单的纪录！");
			return null;
		} else {
			tLCContSchema = tLCContSet.get(1);
		}
		tLCContDB = null;
		tLCContSet = null;

		return tLCContSchema;
	} // function getLCContSchema end

	// ==========================================================================

	/**
	 * 检查保单密码是否为空
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean isPContHasPwd(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单密码是否为空的参数合同号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = getLCContSchema(sContNo);
		if (tLCContSchema == null) {
			CError.buildErr(this, "检查保单密码是否为空的参数合同号不存在！");
			return false;
		} else {
			String sPassword = tLCContSchema.getPassword();
			if (sPassword == null || sPassword.trim().equals("")) {
				return false;
			}
		}

		return true;
	} // function isPContHasPwd end

	// ==========================================================================

	/**
	 * 检查保单是否处于密码挂失状态
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean isInPwdLostState(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单是否处于密码挂失状态的参数合同号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select 'X' " + "from LCContState " + "where 1 = 1 "
				+ "and ContNo = '?sContNo?' "
				+ "and StateType = 'Password' " + "and State = '1' "
				+ "and EndDate is null";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sContNo", sContNo);
		
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		String sPwdState = new String("");
		try {
			sPwdState = tExeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询保单密码挂失状态出现异常！");
			return false;
		}
		if (sPwdState == null || sPwdState.trim().equals("")) {
			return false;
		}
		tExeSQL = null;

		return true;
	} // function isInPwdLostState end

	// ==========================================================================

	/**
	 * 检查保单是否处于密码解挂状态
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean isInPwdUnlockState(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单是否处于密码解挂状态的参数合同号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select 'X' " + "from LCContState " + "where 1 = 1 "
				+ "and ContNo = '?sContNo?' "
				+ "and StateType = 'Password' " + "and State = '0' "
				+ "and EndDate is null";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sContNo", sContNo);
		
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		String sPwdState = new String("");
		try {
			sPwdState = tExeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询保单密码解挂状态出现异常！");
			return false;
		}
		if (sPwdState != null && !sPwdState.trim().equals("")) {
			return false;
		}
		tExeSQL = null;

		return true;
	} // function isInPwdUnlockState end

	// ==========================================================================

	/**
	 * 检查保单密码是否已挂失满七日
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean isOutLostState(String sContNo) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单密码是否已挂失满七日的参数合同号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select 'X' " + "from LCContState " + "where 1 = 1 "
				+ "and ContNo = '?sContNo?' "
				+ "and StateType = 'Password' " + "and State = '1' "
				+ "and datediff(to_date('?sCurrentDate?','yyyy-mm-dd'),StartDate) < 7 " + "and EndDate is null";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sContNo", sContNo);
		sqlbv.put("sCurrentDate", sCurrentDate);
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		ExeSQL tExeSQL = new ExeSQL();
		String sPwdState = new String("");
		try {
			sPwdState = tExeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询保单密码挂失状态出现异常！");
			return false;
		}
		if (sPwdState != null && !sPwdState.trim().equals("")) {
			return false;
		}
		tExeSQL = null;

		return true;
	} // function isOutLostPwdState end

	// ==========================================================================

	/**
	 * 如果保单密码输入五次错误则进入密码挂失状态
	 * 
	 * @param String
	 * @return boolean
	 */
	private boolean chkErrorPwdTimes(String sContNo, boolean isRightPwd) {
		// 校验参数数据
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "检查保单密码输入错误次数的参数保单号不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		QuerySQL = "select * " + "from LPCont " + "where EdorType = 'PW' "
				+ "and ContNo = '?sContNo?' "
				+ "order by ModifyDate desc, ModifyTime desc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sContNo", sContNo);
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		// 查询 LPCont
		LPContDB tLPContDB = new LPContDB();
		LPContSet tLPContSet = new LPContSet();
		LPContSchema tLPContSchema = new LPContSchema();
		try {
			tLPContSet = tLPContDB.executeQuery(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询最新的密码修改轨迹获取输入错误次数信息出现异常！");
			return false;
		}
		if (tLPContSet != null && tLPContSet.size() > 0) {
			tLPContSchema = tLPContSet.get(1);
		}
		if (tLPContSchema != null) {
			int nErrorPwdTimes = 0;
			// 采用 LPCont.State 记录错误次数, 暂没有更好的办法
			if (isRightPwd) {
				tLPContSchema.setState("");
			} else {
				String sErrorPwdTimes = new String("");
				sErrorPwdTimes = tLPContSchema.getState();
				if (sErrorPwdTimes != null && !sErrorPwdTimes.trim().equals("")) {
					try {
						nErrorPwdTimes = Integer.parseInt(sErrorPwdTimes);
					} catch (Exception ex) {
					}
				}
				nErrorPwdTimes += 1;
				tLPContSchema.setState(String.valueOf(nErrorPwdTimes));
			}
			// 上一次的纪录操作信息不能修改, 否则会造成轨迹紊乱
			// tLPContSchema.setOperator(rGlobalInput.Operator);
			// tLPContSchema.setModifyDate(sCurrentDate);
			// tLPContSchema.setModifyTime(sCurrentTime);

			// ------------------------------------------------------------------

			// 将错误次数提交更新数据库
			MMap tMap = new MMap();
			tMap.put(tLPContSchema, "UPDATE");
			VData tVData = new VData();
			tVData.add(rGlobalInput);
			tVData.add(tMap);
			tMap = null;
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, rOperation)) {
				CError.buildErr(this, "保存提交的信息到数据库失败！");
				logger.debug("\t@> PEdorContPwdBL.chkErrorPwdTimes() : PubSubmit.submitData() 失败！");
				return false;
			}
			tPubSubmit = null;
			tVData = null;

			// ------------------------------------------------------------------

			// 五次错误则创建密码挂失状态
			if (!isRightPwd && nErrorPwdTimes >= 5) {
				if (!createPwdState(sContNo, "1")) {
					CError.buildErr(this, "创建保单 " + sContNo + " 密码挂失状态失败！");
					return false;
				}
				CError
						.buildErr(this, "保单号 " + sContNo
								+ " 密码五次输入不对。已进入密码挂失状态！");
				return false;
			}
		}
		tLPContDB = null;
		tLPContSet = null;
		tLPContSchema = null;

		return true;
	} // function isOutLostPwdState end

	// ==========================================================================

	/**
	 * 创建保单密码状态
	 * 
	 * @param String
	 * @param String
	 * @return boolean
	 */
	private boolean createPwdState(String sContNo, String sState) {
		MMap tMap = new MMap();

		// ----------------------------------------------------------------------

		if (sContNo == null || sContNo.trim().equals("")) {
			logger.debug("\t@> PEdorContPwdBL.createPwdState() : 创建保单密码状态的合同号码不能为空！");
			return false;
		}

		if (sState == null || sState.trim().equals("")) {
			logger.debug("\t@> PEdorContPwdBL.createPwdState() : 创建保单密码状态的状态信息不能为空！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 查询 LCContState
		LCContStateDB tLCContStateDB = new LCContStateDB();
		tLCContStateDB.setContNo(sContNo);
		tLCContStateDB.setStateType("Password");
		LCContStateSet tLCContStateSet = new LCContStateSet();
		try {
			tLCContStateSet = tLCContStateDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保单密码状态信息出现异常！");
			return false;
		}
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			// 更新 LCContState
			LCContStateSet tLCContStateSetNew = new LCContStateSet();
			for (int k = 1; k <= tLCContStateSet.size(); k++) {
				LCContStateSchema tLCContStateSchemaNew = new LCContStateSchema();
				tLCContStateSchemaNew = tLCContStateSet.get(k);
				String sEndDate = tLCContStateSchemaNew.getEndDate();
				if (sEndDate == null || sEndDate.trim().equals("")) {
					sEndDate = PubFun.calDate(sCurrentDate, -1, "D", null);
					tLCContStateSchemaNew.setEndDate(sEndDate);
					tLCContStateSchemaNew.setOperator(rGlobalInput.Operator);
					tLCContStateSchemaNew.setModifyDate(sCurrentDate);
					tLCContStateSchemaNew.setModifyTime(sCurrentTime);
					tLCContStateSetNew.add(tLCContStateSchemaNew);
					tLCContStateSchemaNew = null;
				}
			}
			tMap.put(tLCContStateSetNew, "UPDATE");
			tLCContStateSetNew = null;
		}

		// ----------------------------------------------------------------------

		// 插入 LCContState
		LCContStateSchema tLCContStateSchemaNew = new LCContStateSchema();
		tLCContStateSchemaNew.setContNo(sContNo);
		tLCContStateSchemaNew.setPolNo("000000");
		tLCContStateSchemaNew.setInsuredNo("000000");
		tLCContStateSchemaNew.setStateType("Password");
		tLCContStateSchemaNew.setState(sState);
		tLCContStateSchemaNew.setStateReason("");
		tLCContStateSchemaNew.setStartDate(sCurrentDate);
		tLCContStateSchemaNew.setEndDate("");
		tLCContStateSchemaNew.setOperator(rGlobalInput.Operator);
		tLCContStateSchemaNew.setMakeDate(sCurrentDate);
		tLCContStateSchemaNew.setMakeTime(sCurrentTime);
		tLCContStateSchemaNew.setModifyDate(sCurrentDate);
		tLCContStateSchemaNew.setModifyTime(sCurrentTime);
		tMap.put(tLCContStateSchemaNew, "DELETE&INSERT");
		tLCContStateSchemaNew = null;

		// ----------------------------------------------------------------------

		// 将状态马上提交数据库
		VData tVData = new VData();
		tVData.add(rGlobalInput);
		tVData.add(tMap);
		tMap = null;
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorContPwdBL.createPwdState() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;
		tVData = null;

		return true;
	} // function creatPwdContState end

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
		if (rLCContSet != null)
			rLCContSet = null;
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
	// tGlobalInput.ManageCom = "86";
	// tGlobalInput.Operator = "XinYQ";
	// //TransferData
	// TransferData tTransferData = new TransferData();
	// tTransferData.setNameAndValue("ContNo", "HB090536091002253");
	// tTransferData.setNameAndValue("OldPassword", "");
	// tTransferData.setNameAndValue("NewPassword", "12345");
	// //VData
	// VData tVData = new VData();
	// tVData.add(tGlobalInput);
	// tVData.add(tTransferData);
	// //PEdorContPwdBL
	// PEdorContPwdBL tPEdorContPwdBL = new PEdorContPwdBL();
	// if (!tPEdorContPwdBL.submitData(tVData, "Change"))
	// {
	// logger.debug(tPEdorContPwdBL.getErrors().getFirstError());
	// }
	// } //function main end
	// ==========================================================================

} // class PEdorContPwdBL end
