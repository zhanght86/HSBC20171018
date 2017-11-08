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
 * @date     : 2006-05-09, 2006-06-08, 2006-08-21
 * @direction: 团体保全保单质押贷款清偿确认
 ******************************************************************************/

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPLoanDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOReturnLoanSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOReturnLoanSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPLoanSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorRFConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorRFConfirmBL.class);
	// public GrpEdorRFConfirmBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPGrpEdorItemSchema rLPGrpEdorItemSchema;
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
		// logger.debug("\t@> GrpEdorRFConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorRFConfirmBL.submitData() : 无法获取 InputData 数据！");
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
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorRFConfirmBL.submitData() 结束");
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
		// logger.debug("\t@> GrpEdorRFConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorRFConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorRFConfirmBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
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

		// logger.debug("\t@> GrpEdorRFConfirmBL.getInputData() 结束");
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
		// logger.debug("\t@> GrpEdorRFConfirmBL.checkData() 开始");

		// 查询 LPGrpEdorItem
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
			CError.buildErr(this, "查询团单保全项目明细表出现异常！");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在团单保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		}
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// logger.debug("\t@> GrpEdorRFConfirmBL.checkData() 结束");
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
		// logger.debug("\t@> GrpEdorRFConfirmBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPGrpEdorItemSchema
		rLPGrpEdorItemSchema.setEdorState("6");
		rLPGrpEdorItemSchema.setModifyDate(sCurrentDate);
		rLPGrpEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPGrpEdorItemSchema, "UPDATE");

		// ----------------------------------------------------------------------

		// 删除 LOLoan
		DeleteSQL = "delete from LOLoan " + "where 1 = 1 " + "and ContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' " + "and EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and LoanType = '0'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPLoan
		LPLoanDB tLPLoanDB = new LPLoanDB();
		tLPLoanDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPLoanDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPLoanDB.setContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLPLoanDB.setLoanType("0");
		LPLoanSet tLPLoanSet = new LPLoanSet();
		try {
			tLPLoanSet = tLPLoanDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全贷款业务表出现异常！");
			return false;
		}
		if (tLPLoanSet == null || tLPLoanSet.size() <= 0) {
			CError.buildErr(this, "在保全贷款业务表中找不到待操作保单！");
			return false;
		} else {
			// 拷贝 LPLoan 到 LOLoan
			LOLoanSet tLOLoanSetNew = new LOLoanSet();
			for (int i = 1; i <= tLPLoanSet.size(); i++) {
				LPLoanSchema tLPLoanSchema = new LPLoanSchema();
				tLPLoanSchema = tLPLoanSet.get(i);
				LOLoanSchema tLOLoanSchemaNew = new LOLoanSchema();
				tReflections.transFields(tLOLoanSchemaNew, tLPLoanSchema);
				tLOLoanSchemaNew.setOperator(rGlobalInput.Operator);
				tLOLoanSchemaNew.setModifyDate(sCurrentDate);
				tLOLoanSchemaNew.setModifyTime(sCurrentTime);
				tLOLoanSetNew.add(tLOLoanSchemaNew);
				tLOLoanSchemaNew = null;
				tLPLoanSchema = null;
			}
			rMap.put(tLOLoanSetNew, "INSERT");
			tLOLoanSetNew = null;
		}

		// ----------------------------------------------------------------------

		// 删除 LPLoan
		DeleteSQL = "delete from LPLoan " + "where 1 = 1 " + "and EdorNo = '"
				+ rLPGrpEdorItemSchema.getEdorNo() + "' " + "and EdorType = '"
				+ rLPGrpEdorItemSchema.getEdorType() + "' " + "and ContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and LoanType = '0'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LOLoan
		LOLoanDB tLOLoanDB = new LOLoanDB();
		tLOLoanDB.setContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLOLoanDB.setLoanType("0");
		tLOLoanDB.setPayOffFlag("0");
		LOLoanSet tLOLoanSet = new LOLoanSet();
		try {
			tLOLoanSet = tLOLoanDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全贷款业务表出现异常！");
			return false;
		}
		if (tLOLoanSet != null && tLOLoanSet.size() > 0) {
			// 拷贝 LOLoan 到 LPLoan
			LPLoanSet tLPLoanSetNew = new LPLoanSet();
			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				tLOLoanSchema = tLOLoanSet.get(i);
				LPLoanSchema tLPLoanSchemaNew = new LPLoanSchema();
				tReflections.transFields(tLPLoanSchemaNew, tLOLoanSchema);
				tLPLoanSchemaNew.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
				tLPLoanSchemaNew
						.setEdorType(rLPGrpEdorItemSchema.getEdorType());
				tLPLoanSchemaNew.setOperator(rGlobalInput.Operator);
				tLPLoanSchemaNew.setModifyDate(sCurrentDate);
				tLPLoanSchemaNew.setModifyTime(sCurrentTime);
				tLPLoanSetNew.add(tLPLoanSchemaNew);
				tLPLoanSchemaNew = null;
				tLOLoanSchema = null;
			}
			rMap.put(tLPLoanSetNew, "INSERT");
			tLPLoanSetNew = null;
		}

		// ----------------------------------------------------------------------

		// 删除 LOReturnLoan
		DeleteSQL = "delete from LOReturnLoan " + "where 1 = 1 "
				+ "and ContNo = '" + rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and LoanType = '0'";
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPReturnLoan
		LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();
		tLPReturnLoanDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPReturnLoanDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPReturnLoanDB.setContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLPReturnLoanDB.setLoanType("0");
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
		try {
			tLPReturnLoanSet = tLPReturnLoanDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全还款业务表出现异常！");
			return false;
		}
		if (tLPReturnLoanSet == null || tLPReturnLoanSet.size() <= 0) {
			CError.buildErr(this, "在保全还款业务表中找不到待操作保单！");
			return false;
		} else {
			// 拷贝 LPReturnLoan 到 LOReturnLoan
			LOReturnLoanSet tLOReturnLoanSetNew = new LOReturnLoanSet();
			for (int i = 1; i <= tLPReturnLoanSet.size(); i++) {
				LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
				tLPReturnLoanSchema = tLPReturnLoanSet.get(i);
				LOReturnLoanSchema tLOReturnLoanSchemaNew = new LOReturnLoanSchema();
				tReflections.transFields(tLOReturnLoanSchemaNew,
						tLPReturnLoanSchema);
				tLOReturnLoanSchemaNew.setOperator(rGlobalInput.Operator);
				tLOReturnLoanSchemaNew.setModifyDate(sCurrentDate);
				tLOReturnLoanSchemaNew.setModifyTime(sCurrentTime);
				tLOReturnLoanSetNew.add(tLOReturnLoanSchemaNew);
				tLOReturnLoanSchemaNew = null;
				tLPReturnLoanSchema = null;
			}
			rMap.put(tLOReturnLoanSetNew, "INSERT");
			tLOReturnLoanSetNew = null;
		}
		tLPReturnLoanDB = null;
		tLPReturnLoanSet = null;

		// ----------------------------------------------------------------------

		// 删除 LCGrpContState
		DeleteSQL = "delete from LCGrpContState " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + "and StateType = 'Loan'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPGrpContState
		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		tLPGrpContStateDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateDB.setStateType("Loan"); // 复用注意修改
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
		} else {
			// 拷贝 LPGrpContState 到 LCGrpContState
			LCGrpContStateSet tLCGrpContStateSetNew = new LCGrpContStateSet();
			for (int i = 1; i <= tLPGrpContStateSet.size(); i++) {
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tLPGrpContStateSchema = tLPGrpContStateSet.get(i);
				LCGrpContStateSchema tLCGrpContStateSchemaNew = new LCGrpContStateSchema();
				tReflections.transFields(tLCGrpContStateSchemaNew,
						tLPGrpContStateSchema);
				tLCGrpContStateSchemaNew.setOperator(rGlobalInput.Operator);
				tLCGrpContStateSchemaNew.setModifyDate(sCurrentDate);
				tLCGrpContStateSchemaNew.setModifyTime(sCurrentTime);
				tLCGrpContStateSetNew.add(tLCGrpContStateSetNew);
				tLCGrpContStateSchemaNew = null;
				tLPGrpContStateSchema = null;
			}
			rMap.put(tLCGrpContStateSetNew, "INSERT");
			tLCGrpContStateSetNew = null;
		}
		tLPGrpContStateDB = null;
		tLPGrpContStateSet = null;

		// ----------------------------------------------------------------------

		// 删除 LPGrpContState
		DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EdorType = '" + rLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and GrpContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and StateType = 'Loan'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCGrpContState
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("Loan"); // 复用注意修改
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		try {
			tLCGrpContStateSet = tLCGrpContStateDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约团体保单状态表出现异常！");
			return false;
		}
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
			// 拷贝 LCGrpContState 到 LPGrpContState
			LPGrpContStateSet tLPGrpContStateSetNew = new LPGrpContStateSet();
			for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema = tLCGrpContStateSet.get(i);
				LPGrpContStateSchema tLPGrpContStateSchemaNew = new LPGrpContStateSchema();
				tReflections.transFields(tLPGrpContStateSchemaNew,
						tLCGrpContStateSchema);
				tLPGrpContStateSchemaNew.setEdorNo(rLPGrpEdorItemSchema
						.getEdorNo());
				tLPGrpContStateSchemaNew.setEdorType(rLPGrpEdorItemSchema
						.getEdorType());
				tLPGrpContStateSchemaNew.setOperator(rGlobalInput.Operator);
				tLPGrpContStateSchemaNew.setModifyDate(sCurrentDate);
				tLPGrpContStateSchemaNew.setModifyTime(sCurrentTime);
				tLPGrpContStateSetNew.add(tLPGrpContStateSchemaNew);
				tLPGrpContStateSchemaNew = null;
				tLCGrpContStateSchema = null;
			}
			rMap.put(tLPGrpContStateSetNew, "INSERT");
			tLPGrpContStateSetNew = null;
		}
		tLCGrpContStateDB = null;
		tLCGrpContStateSet = null;

		// ----------------------------------------------------------------------

		// 垃圾处理
		tReflections = null;

		// logger.debug("\t@> GrpEdorRFConfirmBL.dealData() 结束");
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
		// logger.debug("\t@> GrpEdorRFConfirmBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorRFConfirmBL.outputData() 结束");
		return true;
	} // function outputData end

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
	// GrpEdorRFConfirmBL tGrpEdorRFConfirmBL = new GrpEdorRFConfirmBL();
	// } //function main end
	// ==========================================================================

} // class GrpEdorRFConfirmBL end
