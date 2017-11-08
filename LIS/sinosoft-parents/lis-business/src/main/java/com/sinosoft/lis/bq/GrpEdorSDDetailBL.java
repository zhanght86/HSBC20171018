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
 * @version  : 1.00
 * @date     : 2006-05-10
 * @direction: 团体保全保单保险责任中止明细
 ******************************************************************************/

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorSDDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorSDDetailBL.class);
	// public GrpEdorSDDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema;
	private TransferData mTransferData = new TransferData();
	private String mEndDate = "";
	/** 修改数据 */
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPContSet mLPContSet = new LPContSet();

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
		// logger.debug("\t@> GrpEdorSDDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorSDDetailBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> GrpEdorSDDetailBL.submitData() 成功");
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
		// logger.debug("\t@> GrpEdorSDDetailBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorSDDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (mLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorSDDetailBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = mLPGrpEdorItemSchema.getEdorAcceptNo();
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		String sEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		if (sEdorNo == null || sEdorNo.equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		String sEdorType = mLPGrpEdorItemSchema.getEdorType();
		if (sEdorType == null || sEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 合同号码
		String sGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		if (sGrpContNo == null || sGrpContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		mTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null || mTransferData.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorSDDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "TransferData 数据为空!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEndDate = (String) mTransferData.getValueByName("EndDate");

		// logger.debug("\t@> GrpEdorSDDetailBL.getInputData() 成功");
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
		// logger.debug("\t@> GrpEdorSDDetailBL.checkData() 开始");

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

		// logger.debug("\t@> GrpEdorSDDetailBL.checkData() 成功");
		return true;
	}

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> GrpEdorSDDetailBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 删除 LPGrpContState
		DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EdorType = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCGrpContState
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("DutyStop"); // 复用注意修改
		tLCGrpContStateDB.setState("0");
		tLCGrpContStateDB.setEndDate("");
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		try {
			tLCGrpContStateSet = tLCGrpContStateDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团体保单状态表出现异常！");
			return false;
		}
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
			for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tReflections.transFields(tLPGrpContStateSchema,
						tLCGrpContStateSchema);
				tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				// 结束上一状态
				String sEndDate = PubFun.calDate(mEndDate, -1, "D", null);
				tLPGrpContStateSchema.setEndDate(sEndDate);
				// 常规字段赋值
				tLPGrpContStateSchema.setOperator(rGlobalInput.Operator);
				tLPGrpContStateSchema.setModifyDate(sCurrentDate);
				tLPGrpContStateSchema.setModifyTime(sCurrentTime);
				rMap.put(tLPGrpContStateSchema, "INSERT");
				// 垃圾处理
				tLCGrpContStateSchema = null;
				tLPGrpContStateSchema = null;
			}
		}
		// 垃圾处理
		tLCGrpContStateDB = null;
		tLCGrpContStateSet = null;

		// ----------------------------------------------------------------------

		// 创建新的状态
		LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
		tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateSchema.setGrpPolNo("000000");
		tLPGrpContStateSchema.setStateType("DutyStop"); // 复用注意修改
		tLPGrpContStateSchema.setState("1");
		tLPGrpContStateSchema.setStartDate(mEndDate);
		tLPGrpContStateSchema.setEndDate("");
		tLPGrpContStateSchema.setOperator(rGlobalInput.Operator);
		tLPGrpContStateSchema.setMakeDate(sCurrentDate);
		tLPGrpContStateSchema.setMakeTime(sCurrentTime);
		tLPGrpContStateSchema.setModifyDate(sCurrentDate);
		tLPGrpContStateSchema.setModifyTime(sCurrentTime);
		rMap.put(tLPGrpContStateSchema, "INSERT");
		tLPGrpContStateSchema = null;
		// ----------------------------------------------------------------------

		// 修改LCGrpPol、LCCont、LCPol、LCDuty、LCGet、LCPrem
		/*
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.1 更新LCGrpPol表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
		mLCGrpPolSet = tLCGrpPolDB.query();
		for (int n = 1; n <= mLCGrpPolSet.size(); n++) {
			String tGrpPolno = mLCGrpPolSet.get(n).getGrpPolNo();

			LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

			mLCGrpPolSchema = mLCGrpPolSet.get(n);
			LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
			tReflections.transFields(mLPGrpPolSchema, mLCGrpPolSchema);
			mLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			mLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			mLPGrpPolSchema.setPayEndDate(mEndDate);
			mLPGrpPolSchema.setPaytoDate(mEndDate);
			// mLPGrpPolSchema.setMakeDate(sCurrentDate);
			// mLPGrpPolSchema.setMakeTime(sCurrentTime);
			mLPGrpPolSchema.setModifyDate(sCurrentDate);
			mLPGrpPolSchema.setModifyTime(sCurrentTime);
			mLPGrpPolSchema.setOperator(rGlobalInput.Operator);
			// mLPGrpPolSchema.setManageCom(rGlobalInput.ManageCom);
			mLPGrpPolSet.add(mLPGrpPolSchema);
			String tSql = "";

			/*
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.2 插入LCPol表
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tSql = "select * from lcpol where grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' and grppolno = '" + tGrpPolno + "'";
			tLCPolSet = tLCPolDB.executeQuery(tSql);
			String tContNo = "";

			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				LPPolSchema tLPPolSchema = new LPPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				tReflections.transFields(tLPPolSchema, tLCPolSchema);

				String tPolNo = tLCPolSet.get(i).getPolNo();
				tContNo = tLCPolSet.get(i).getContNo();
				tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				// tLPPolSchema.setMakeDate(sCurrentDate);
				// tLPPolSchema.setMakeTime(sCurrentTime);
				tLPPolSchema.setModifyDate(sCurrentDate);
				tLPPolSchema.setModifyTime(sCurrentTime);
				tLPPolSchema.setPayEndDate(mEndDate);
				tLPPolSchema.setPaytoDate(mEndDate);
				tLPPolSchema.setEndDate(mEndDate);
				tLPPolSchema.setAcciEndDate(mEndDate);
				tLPPolSchema.setOperator(rGlobalInput.Operator);
				// tLPPolSchema.setManageCom(rGlobalInput.ManageCom);
				mLPPolSet.add(tLPPolSchema);
				/*
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.3 插入LCDuty表
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();

				tSql = "select * from lcduty where polno ='" + tPolNo + "'";
				tLCDutySet = tLCDutyDB.executeQuery(tSql);
				if (tLCDutySet.size() < 1) {
					mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCDuty表失败！");
					return false;
				}
				for (int t = 1; t <= tLCDutySet.size(); t++) {
					LCDutySchema tLCDutySchema = new LCDutySchema();
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tLCDutySchema = tLCDutySet.get(t);
					tReflections.transFields(tLPDutySchema, tLCDutySchema);
					tLPDutySchema.setPayEndDate(mEndDate);
					tLPDutySchema.setPaytoDate(mEndDate);
					// tLPDutySchema.setMakeDate(sCurrentDate);
					// tLPDutySchema.setMakeTime(sCurrentTime);
					tLPDutySchema.setModifyDate(sCurrentDate);
					tLPDutySchema.setModifyTime(sCurrentTime);
					tLPDutySchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPGrpEdorItemSchema
							.getEdorType());
					tLPDutySchema.setOperator(rGlobalInput.Operator);
					mLPDutySet.add(tLPDutySchema);
				}
				/*
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.4 插入LCPrem表
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				tSql = "select * from lcprem where polno ='" + tPolNo + "'";
				tLCPremSet = tLCPremDB.executeQuery(tSql);
				if (tLCPremSet.size() < 1) {
					mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCPrem表失败！");
					return false;
				}
				for (int t = 1; t <= tLCPremSet.size(); t++) {
					LCPremSchema tLCPremSchema = new LCPremSchema();
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tLCPremSchema = tLCPremSet.get(t);
					tReflections.transFields(tLPPremSchema, tLCPremSchema);
					tLPPremSchema.setPayEndDate(mEndDate);
					tLPPremSchema.setPaytoDate(mEndDate);
					// tLPPremSchema.setMakeDate(sCurrentDate);
					// tLPPremSchema.setMakeTime(sCurrentTime);
					tLPPremSchema.setModifyDate(sCurrentDate);
					tLPPremSchema.setModifyTime(sCurrentTime);
					tLPPremSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPGrpEdorItemSchema
							.getEdorType());
					tLPPremSchema.setOperator(rGlobalInput.Operator);
					// tLPPremSchema.setManageCom(rGlobalInput.ManageCom);
					mLPPremSet.add(tLPPremSchema);
				}

				/*
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.5 插入LCGet表
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				tSql = "select * from lcget where polno ='" + tPolNo + "'";
				tLCGetSet = tLCGetDB.executeQuery(tSql);
				if (tLCGetSet.size() < 1) {
					mErrors.addOneError("查询个人险种号[" + tPolNo + "]的LCGet表失败！");
					return false;
				}
				for (int t = 1; t <= tLCGetSet.size(); t++) {
					LCGetSchema tLCGetSchema = new LCGetSchema();
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tLCGetSchema = tLCGetSet.get(t);
					tReflections.transFields(tLPGetSchema, tLCGetSchema);
					tLPGetSchema.setGetEndDate(mEndDate);
					// tLPGetSchema.setMakeDate(sCurrentDate);
					// tLPGetSchema.setMakeTime(sCurrentTime);
					tLPGetSchema.setModifyDate(sCurrentDate);
					tLPGetSchema.setModifyTime(sCurrentTime);
					tLPGetSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
					tLPGetSchema
							.setEdorType(mLPGrpEdorItemSchema.getEdorType());
					tLPGetSchema.setOperator(rGlobalInput.Operator);
					// tLPGetSchema.setManageCom(rGlobalInput.ManageCom);
					mLPGetSet.add(tLPGetSchema);
				}
			}
		}
		rMap.put(mLPGrpPolSet, "DELETE&INSERT");
		rMap.put(mLPPolSet, "DELETE&INSERT");
		rMap.put(mLPGetSet, "DELETE&INSERT");
		rMap.put(mLPDutySet, "DELETE&INSERT");
		rMap.put(mLPPremSet, "DELETE&INSERT");
		// rMap.put(mLPContSet, "DELETE&INSERT");

		String SQL = "insert into lpcont (select '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "','"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "',a.* from lccont a where contno in (select distinct contno from lppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'))";
		rMap.put("delete from lpcont where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'", "DELETE");
		rMap.put(SQL, "INSERT");
		rMap.put("update lpcont a set paytodate='" + mEndDate
				+ "',ModifyDate='" + sCurrentDate + "',ModifyTime='"
				+ sCurrentTime + "',Operator = '" + rGlobalInput.Operator
				+ "' where edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "'", "UPDATE");

		// ----------------------------------------------------------------------

		// 垃圾处理
		tReflections = null;

		// ----------------------------------------------------------------------

		// 准备返回需要的数据
		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorSDDetailBL.dealData() 成功");
		return true;
	} // function dealData end

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
		if (rInputData != null) {
			rInputData = null;
		}
		if (rGlobalInput != null) {
			rGlobalInput = null;
		}
		if (mLPGrpEdorItemSchema != null) {
			mLPGrpEdorItemSchema = null;
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
	// GrpEdorSDDetailBL tGrpEdorSDDetailBL = new GrpEdorSDDetailBL();
	// } //function main end
	// ==========================================================================

} // class GrpEdorSDDetailBL end
