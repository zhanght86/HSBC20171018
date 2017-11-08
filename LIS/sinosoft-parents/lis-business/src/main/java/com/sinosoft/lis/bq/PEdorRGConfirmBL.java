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
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-03-10, 2006-03-14, 2006-08-12, 2006-10-17
 * @direction: 保全生存领取追回确认
 ******************************************************************************/

import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorRGConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRGConfirmBL.class);
	// public PEdorRGConfirmBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	//public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private LPEdorItemSchema rLPEdorItemSchema;
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
		// logger.debug("\t@> PEdorRGConfirmBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorRGConfirmBL.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> PEdorRGConfirmBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorRGConfirmBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorRGConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPEdorItemSchema
		rLPEdorItemSchema = new LPEdorItemSchema();
		rLPEdorItemSchema = (LPEdorItemSchema) rInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (rLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorRGConfirmBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = rLPEdorItemSchema.getEdorAcceptNo();
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
			return false;
		}

		// 批单号码
		String sEdorNo = rLPEdorItemSchema.getEdorNo();
		if (sEdorNo == null || sEdorNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批单号码信息！");
			return false;
		}

		// 批改类型
		String sEdorType = rLPEdorItemSchema.getEdorType();
		if (sEdorType == null || sEdorType.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的批改类型信息！");
			return false;
		}

		// 合同号码
		String sContNo = rLPEdorItemSchema.getContNo();
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的合同号码信息！");
			return false;
		}

		// 保单号码
		String sPolNo = rLPEdorItemSchema.getPolNo();
		if (sPolNo == null || sPolNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的保单号码信息！");
			return false;
		}

		// 客户号码
		String sInsuredNo = rLPEdorItemSchema.getInsuredNo();
		if (sInsuredNo == null || sInsuredNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全项目的客户号码信息！");
			return false;
		}

		// logger.debug("\t@> PEdorRGConfirmBL.getInputData() 结束");
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
		// logger.debug("\t@> PEdorRGConfirmBL.checkData() 开始");

		// 查询 LPEdorItem
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(rLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(rLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(rLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(rLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setPolNo(rLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setInsuredNo(rLPEdorItemSchema.getInsuredNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全项目明细表发生异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			rLPEdorItemSchema = tLPEdorItemSet.get(1);
		}
		tLPEdorItemDB = null;
		tLPEdorItemSet = null;

		// ----------------------------------------------------------------------

		// 查询 LDPerson
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(rLPEdorItemSchema.getInsuredNo());
		LDPersonSet tLDPersonSet = new LDPersonSet();
		try {
			tLDPersonSet = tLDPersonDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询客户表信息发生异常！");
			return false;
		}
		if (tLDPersonSet == null || tLDPersonSet.size() <= 0) {
			CError.buildErr(this, "客户表中不存在此客户！");
			return false;
		} else {
			// 检查客户是否死亡
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLDPersonSchema = tLDPersonSet.get(1);
			String sDeathDate = tLDPersonSchema.getDeathDate();
			if (sDeathDate == null || sDeathDate.trim().equals("")) {
				CError.buildErr(this, "该客户目前未死亡，不能进行领取追回！");
				return false;
			}
			tLDPersonSchema = null;
		}
		tLDPersonDB = null;
		tLDPersonSet = null;

		// logger.debug("\t@> PEdorRGConfirmBL.checkData() 结束");
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
		// logger.debug("\t@> PEdorRGConfirmBL.dealData() 开始");

		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 更新 LPEdorItem
		rLPEdorItemSchema.setEdorState("6");
		rLPEdorItemSchema.setModifyDate(sCurrentDate);
		rLPEdorItemSchema.setModifyTime(sCurrentTime);
		rMap.put(rLPEdorItemSchema, "UPDATE");

		// logger.debug("\t@> PEdorRGConfirmBL.dealData() 结束");
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
		// logger.debug("\t@> PEdorRGConfirmBL.outputData() 开始");

		rResultData = new VData();
		rResultData.add(rMap);

		// logger.debug("\t@> PEdorRGConfirmBL.outputData() 结束");
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
		if (rLPEdorItemSchema != null)
			rLPEdorItemSchema = null;
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
	// PEdorRGConfirmBL tPEdorRGConfirmBL = new PEdorRGConfirmBL();
	// } //function main end
	// ==========================================================================

} // class PEdorRGConfirmBL end
