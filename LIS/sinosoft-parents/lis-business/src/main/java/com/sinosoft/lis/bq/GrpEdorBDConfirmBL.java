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
 * @date     : 2006-07-11
 * @direction: 团体保全保单质押银行贷款清偿确认
 ******************************************************************************/

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorBDConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorBDConfirmBL.class);
	// public GrpEdorBDConfirmBL() {}

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
		// logger.debug("\t@> GrpEdorBDConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorBDConfirmBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> GrpEdorBDConfirmBL.submitData() 成功");
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
		// logger.debug("\t@> GrpEdorBDConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorBDConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		rLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		rLPGrpEdorItemSchema = (LPGrpEdorItemSchema) rInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (rLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorBDConfirmBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
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

		// logger.debug("\t@> GrpEdorBDConfirmBL.getInputData() 成功");
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
		// logger.debug("\t@> GrpEdorBDConfirmBL.checkData() 开始");

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

		// logger.debug("\t@> GrpEdorBDConfirmBL.checkData() 成功");
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
		// logger.debug("\t@> GrpEdorBDConfirmBL.dealData() 开始");

		rMap = new MMap();
		Reflections tReflections = new Reflections();
		String DeleteSQL = new String("");
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 查询 LPGrpContState
		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		tLPGrpContStateDB.setEdorNo(rLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateDB.setEdorType(rLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateDB.setStateType("BankLoan"); // 复用注意修改
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
		tLCGrpContStateDB.setGrpContNo(rLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("BankLoan"); // 复用注意修改
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

		// 删除 LCGrpContState
		DeleteSQL = "delete from LCGrpContState " + "where 1 = 1 "
				+ "and GrpContNo = '" + rLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + "and StateType = 'BankLoan'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 拷贝 LPGrpContState 到 LCGrpContState
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

		// 删除 LPGrpContState
		DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + rLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EdorType = '" + rLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and GrpContNo = '"
				+ rLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and StateType = 'BankLoan'"; // 复用注意修改
		// logger.debug(DeleteSQL);
		rMap.put(DeleteSQL, "DELETE");

		// ----------------------------------------------------------------------

		// 拷贝 LCGrpContState 到 LPGrpContState
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
			for (int j = 1; j <= tLCGrpContStateSet.size(); j++) {
				LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
				tLCGrpContStateSchema = tLCGrpContStateSet.get(j);
				LPGrpContStateSchema tLPGrpContStateSchema = new LPGrpContStateSchema();
				tReflections.transFields(tLPGrpContStateSchema,
						tLCGrpContStateSchema);
				tLPGrpContStateSchema.setEdorNo(rLPGrpEdorItemSchema
						.getEdorNo());
				tLPGrpContStateSchema.setEdorType(rLPGrpEdorItemSchema
						.getEdorType());
				tLCGrpContStateSchema.setOperator(rGlobalInput.Operator);
				tLPGrpContStateSchema.setModifyDate(sCurrentDate);
				tLPGrpContStateSchema.setModifyTime(sCurrentTime);
				rMap.put(tLPGrpContStateSchema, "INSERT");
				tLCGrpContStateSchema = null;
				tLPGrpContStateSchema = null;
			}
		}

		// ----------------------------------------------------------------------

		// 垃圾处理
		tReflections = null;
		tLCGrpContStateSet = null;
		tLPGrpContStateSet = null;

		// logger.debug("\t@> GrpEdorBDConfirmBL.dealData() 成功");
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
		// logger.debug("\t@> GrpEdorBDConfirmBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> GrpEdorBDConfirmBL.outputData() 成功");
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
	// GrpEdorBDConfirmBL tGrpEdorBDConfirmBL = new GrpEdorBDConfirmBL();
	// } //function main end
	// ==========================================================================

} // class GrpEdorBDConfirmBL end
