package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 唐沛 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-05-10
 * @direction: 团体保全保单保险责任中止确认
 * Rewrited By QianLy on 2006-10-27
 ******************************************************************************/

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorSDConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorSDConfirmBL.class);
	// public GrpEdorSDConfirmBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema;
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCContSet mLCContSet = new LCContSet();

	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPContSet mLPContSet = new LPContSet();

	private String mEdorAcceptNo;
	private String mEdorNo;
	private String mEdorType;
	private String mGrpContNo;
	// 输出数据
	private MMap rMap = new MMap();
	private VData rResultData;

	Reflections tReflections = new Reflections();
	String sCurrentDate = PubFun.getCurrentDate();
	String sCurrentTime = PubFun.getCurrentTime();

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorSDConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorSDConfirmBL.submitData() : 无法获取 InputData 数据！");
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
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorSDConfirmBL.submitData() 成功");
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
		// logger.debug("\t@> GrpEdorSDConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorSDConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (mLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorSDConfirmBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		mEdorAcceptNo = mLPGrpEdorItemSchema.getEdorAcceptNo();
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		mEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		if (mEdorNo == null || mEdorNo.equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		mEdorType = mLPGrpEdorItemSchema.getEdorType();
		if (mEdorType == null || mEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 合同号码
		mGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		if (mGrpContNo == null || mGrpContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// logger.debug("\t@> GrpEdorSDConfirmBL.getInputData() 成功");
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
		// logger.debug("\t@> GrpEdorSDConfirmBL.checkData() 开始");

		// 检查团体保全项目明细表
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
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
			mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));
		}
		// 垃圾处理
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// logger.debug("\t@> GrpEdorSDConfirmBL.checkData() 成功");
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
		// logger.debug("\t@> GrpEdorSDConfirmBL.dealData() 开始");

		// ----------------------------------------------------------------------

		// 查询 LPGrpContState
		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		tLPGrpContStateDB.setEdorNo(mEdorNo);
		tLPGrpContStateDB.setEdorType(mEdorType);
		tLPGrpContStateDB.setGrpContNo(mGrpContNo);
		tLPGrpContStateDB.setStateType("DutyStop"); // 复用注意修改
		tLPGrpContStateDB.setState("1");
		LPGrpContStateSet tLPGrpContStateSet = new LPGrpContStateSet();
		try {
			tLPGrpContStateSet = tLPGrpContStateDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全团体保单状态表出现异常！");
			return false;
		}
		if (tLPGrpContStateSet == null || tLPGrpContStateSet.size() <= 0) {
			CError.buildErr(this, "在保全团体保单状态表中找不到待操作保单！");
			return false;
		}
		// 垃圾处理
		tLPGrpContStateDB = null;

		// ----------------------------------------------------------------------

		// 查询 LCGrpContState
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(mGrpContNo);
		tLCGrpContStateDB.setStateType("DutyStop"); // 复用注意修改
		tLCGrpContStateDB.setState("0");
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		try {
			tLCGrpContStateSet = tLCGrpContStateDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体保单状态表出现异常！");
			return false;
		}
		// 垃圾处理
		tLCGrpContStateDB = null;

		// ----------------------------------------------------------------------

		String DeleteSQL = new String("");

		// 删除 LPGrpContState
		DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + mEdorNo + "' " + "and EdorType = '"
				+ mEdorType + "' " + "and GrpContNo = '" + mGrpContNo + "' "
				+ "and StateType = 'DutyStop'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// 删除 LCGrpContState
		DeleteSQL = "delete from LCGrpContState " + "where 1 = 1 "
				+ "and GrpContNo = '" + mGrpContNo + "' "
				+ "and StateType = 'DutyStop'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 插入 LPGrpContState
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
			for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tReflections.transFields(tLPGrpContStateSchema,
						tLCGrpContStateSchema);
				tLPGrpContStateSchema.setEdorNo(mEdorNo);
				tLPGrpContStateSchema.setEdorType(mEdorType);
				tLCGrpContStateSchema.setOperator(rGlobalInput.Operator);
				tLPGrpContStateSchema.setModifyDate(sCurrentDate);
				tLPGrpContStateSchema.setModifyTime(sCurrentTime);
				rMap.put(tLPGrpContStateSchema, "INSERT");
				tLCGrpContStateSchema = null;
				tLPGrpContStateSchema = null;
			}
		}

		// ----------------------------------------------------------------------

		// 插入 LCGrpContState
		if (tLPGrpContStateSet != null && tLPGrpContStateSet.size() > 0) {
			for (int k = 1; k <= tLPGrpContStateSet.size(); k++) {
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tLPGrpContStateSchema = tLPGrpContStateSet.get(k);
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tReflections.transFields(tLCGrpContStateSchema,
						tLPGrpContStateSchema);
				tLCGrpContStateSchema.setOperator(rGlobalInput.Operator);
				tLCGrpContStateSchema.setModifyDate(sCurrentDate);
				tLCGrpContStateSchema.setModifyTime(sCurrentTime);
				rMap.put(tLCGrpContStateSchema, "INSERT");
				tLPGrpContStateSchema = null;
				tLCGrpContStateSchema = null;
			}
		}
		// ----------------------------------------------------------------------
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.1 LCGrpPol表和LPGrpPol表互换
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tLPGrpPolDB.setEdorNo(mEdorNo);
		tLPGrpPolDB.setEdorType(mEdorType);
		tLPGrpPolDB.setGrpContNo(mGrpContNo);
		tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet == null || tLPGrpPolSet.size() < 1) {
			CError.buildErr(this, "没有得到LPGrpPol表信息");
			return false;
		}
		for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLPGrpPolSchema = tLPGrpPolSet.get(i);
			tReflections.transFields(tLCGrpPolSchema, tLPGrpPolSchema);
			tLCGrpPolSchema.setModifyDate(sCurrentDate);
			tLCGrpPolSchema.setModifyTime(sCurrentTime);
			mLCGrpPolSet.add(tLCGrpPolSchema);
		}
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() < 1) {
			CError.buildErr(this, "没有得到LCGrpPol表信息");
			return false;
		}
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			tReflections.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
			tLPGrpPolSchema.setEdorNo(mEdorNo);
			tLPGrpPolSchema.setEdorType(mEdorType);
			tLPGrpPolSchema.setModifyDate(sCurrentDate);
			tLPGrpPolSchema.setModifyTime(sCurrentTime);
			mLPGrpPolSet.add(tLPGrpPolSchema);
		}
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.2 LCPol表和LPPol表互换
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		tLPPolDB.setGrpContNo(mGrpContNo);
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() < 1) {
			CError.buildErr(this, "没有得到LPPol表信息");
			return false;
		}
		for (int j = 1; j <= tLPPolSet.size(); j++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLPPolSchema = tLPPolSet.get(j);
			tReflections.transFields(tLCPolSchema, tLPPolSchema);
			tLCPolSchema.setModifyDate(sCurrentDate);
			tLCPolSchema.setModifyTime(sCurrentTime);
			mLCPolSet.add(tLCPolSchema);
		}

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setGrpContNo(mGrpContNo);
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() < 1) {
			CError.buildErr(this, "没有得到LCPol表信息");
			return false;
		}
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(j);
			tReflections.transFields(tLPPolSchema, tLCPolSchema);
			tLPPolSchema.setEdorNo(mEdorNo);
			tLPPolSchema.setEdorType(mEdorType);
			tLPPolSchema.setModifyDate(sCurrentDate);
			tLPPolSchema.setModifyTime(sCurrentTime);
			mLPPolSet.add(tLPPolSchema);
		}
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.3 LCPrem表和LPPrem表互换
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPPremDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet == null || tLPPremSet.size() < 1) {
			CError.buildErr(this, "没有得到LPPrem表信息");
			return false;
		}
		for (int j = 1; j <= tLPPremSet.size(); j++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLPPremSchema = tLPPremSet.get(j);
			tReflections.transFields(tLCPremSchema, tLPPremSchema);
			tLCPremSchema.setModifyDate(sCurrentDate);
			tLCPremSchema.setModifyTime(sCurrentTime);
			mLCPremSet.add(tLCPremSchema);
		}

		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremDB.setGrpContNo(mGrpContNo);
		tLCPremSet = tLCPremDB.query();
		if (tLCPremSet == null || tLCPremSet.size() < 1) {
			CError.buildErr(this, "没有得到LCPrem表信息");
			return false;
		}
		for (int j = 1; j <= tLCPremSet.size(); j++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(j);
			tReflections.transFields(tLPPremSchema, tLCPremSchema);
			tLPPremSchema.setEdorNo(mEdorNo);
			tLPPremSchema.setEdorType(mEdorType);
			tLPPremSchema.setModifyDate(sCurrentDate);
			tLPPremSchema.setModifyTime(sCurrentTime);
			mLPPremSet.add(tLPPremSchema);
		}

		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.4 LCGet表和LPGet表互换
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet == null || tLPGetSet.size() < 1) {
			CError.buildErr(this, "没有得到LPGet表信息");
			return false;
		}
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLPGetSchema = tLPGetSet.get(j);
			tReflections.transFields(tLCGetSchema, tLPGetSchema);
			tLCGetSchema.setModifyDate(sCurrentDate);
			tLCGetSchema.setModifyTime(sCurrentTime);
			mLCGetSet.add(tLCGetSchema);
		}

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setGrpContNo(mGrpContNo);
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet == null || tLCGetSet.size() < 1) {
			CError.buildErr(this, "没有得到LCGet表信息");
			return false;
		}
		for (int j = 1; j <= tLCGetSet.size(); j++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema = tLCGetSet.get(j);
			tReflections.transFields(tLPGetSchema, tLCGetSchema);
			tLPGetSchema.setEdorNo(mEdorNo);
			tLPGetSchema.setEdorType(mEdorType);
			tLPGetSchema.setModifyDate(sCurrentDate);
			tLPGetSchema.setModifyTime(sCurrentTime);
			mLPGetSet.add(tLPGetSchema);
		}
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.5 LCCont表和LPCont表互换 No.6
		 * LCDuty表和LPDuty表互换 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LPContDB tLPContDB = new LPContDB();
		LPContSet tLPContSet = new LPContSet();
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		tLPContDB.setGrpContNo(mGrpContNo);
		tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() < 1) {
			CError.buildErr(this, "没有得到LPCont表信息");
			return false;
		}
		for (int j = 1; j <= tLPContSet.size(); j++) {
			LPContSchema tLPContSchema = new LPContSchema();
			LCContSchema tLCContSchema = new LCContSchema();
			tLPContSchema = tLPContSet.get(j);
			tReflections.transFields(tLCContSchema, tLPContSchema);
			tLCContSchema.setModifyDate(sCurrentDate);
			tLCContSchema.setModifyTime(sCurrentTime);
			mLCContSet.add(tLCContSchema);

			chgLCDuty(tLPContSchema.getContNo());
		}

		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContDB.setGrpContNo(mGrpContNo);
		tLCContSet = tLCContDB.query();
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError.buildErr(this, "没有得到LCCont表信息");
			return false;
		}
		for (int j = 1; j <= tLCContSet.size(); j++) {
			LPContSchema tLPContSchema = new LPContSchema();
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContSet.get(j);
			tReflections.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(mEdorNo);
			tLPContSchema.setEdorType(mEdorType);
			tLPContSchema.setModifyDate(sCurrentDate);
			tLPContSchema.setModifyTime(sCurrentTime);
			mLPContSet.add(tLPContSchema);

			chgLPDuty(tLCContSchema.getContNo());
		}

		rMap.put(mLPGrpEdorItemSchema, "UPDATE");
		rMap.put(mLCGrpPolSet, "UPDATE");
		rMap.put(mLCPolSet, "UPDATE");
		rMap.put(mLCPremSet, "UPDATE");
		rMap.put(mLCDutySet, "UPDATE");
		rMap.put(mLCGetSet, "UPDATE");
		rMap.put(mLCContSet, "UPDATE");

		rMap.put(mLPGrpPolSet, "UPDATE");
		rMap.put(mLPPolSet, "UPDATE");
		rMap.put(mLPPremSet, "UPDATE");
		rMap.put(mLPDutySet, "UPDATE");
		rMap.put(mLPGetSet, "UPDATE");
		rMap.put(mLPContSet, "UPDATE");

		// ----------------------------------------------------------------------

		// 垃圾处理
		tReflections = null;
		tLCGrpContStateSet = null;
		tLPGrpContStateSet = null;

		// ----------------------------------------------------------------------

		// 准备返回需要的数据
		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorSDConfirmBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/*
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 更新LPDuty表
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	private boolean chgLCDuty(String contNo) {
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutyDB.setEdorNo(mEdorNo);
		tLPDutyDB.setEdorType(mEdorType);
		tLPDutyDB.setContNo(contNo);
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet == null || tLPDutySet.size() < 1) {
			CError.buildErr(this, "没有得到LPDuty表信息");
			return false;
		}
		for (int j = 1; j <= tLPDutySet.size(); j++) {
			LPDutySchema tLPDutySchema = new LPDutySchema();
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLPDutySchema = tLPDutySet.get(j);
			tReflections.transFields(tLCDutySchema, tLPDutySchema);
			tLCDutySchema.setModifyDate(sCurrentDate);
			tLCDutySchema.setModifyTime(sCurrentTime);
			mLCDutySet.add(tLCDutySchema);
		}
		return true;
	}

	/*
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 更新LCDuty表
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	private boolean chgLPDuty(String contNo) {
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setContNo(contNo);
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet == null || tLCDutySet.size() < 1) {
			CError.buildErr(this, "没有得到LCDuty表信息");
			return false;
		}
		for (int j = 1; j <= tLCDutySet.size(); j++) {
			LPDutySchema tLPDutySchema = new LPDutySchema();
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = tLCDutySet.get(j);
			tReflections.transFields(tLPDutySchema, tLCDutySchema);
			tLPDutySchema.setEdorNo(mEdorNo);
			tLPDutySchema.setEdorType(mEdorType);
			tLPDutySchema.setModifyDate(sCurrentDate);
			tLPDutySchema.setModifyTime(sCurrentTime);
			mLPDutySet.add(tLPDutySchema);
		}
		return true;
	}

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
		if (rInputData != null) {
			rInputData = null;
		}
		if (rGlobalInput != null) {
			rGlobalInput = null;
		}
		if (mLPGrpEdorItemSchema != null) {
			mLPGrpEdorItemSchema = null;
		}
		if (rMap != null) {
			rMap = null;
		}
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GrpEdorSDConfirmBL tGrpEdorSDConfirmBL = new GrpEdorSDConfirmBL();
	// } //function main end
	// ==========================================================================

} // class GrpEdorSDConfirmBL end
