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
 * @version  : 1.00, 1.01
 * @date     : 2006-07-06, 2006-07-11
 * @direction: 团单保全人工核保分单层结果保存
 ******************************************************************************/

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GEdorContManuUWBL {
private static Logger logger = Logger.getLogger(GEdorContManuUWBL.class);
	// public GEdorContManuUWBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GEdorContManuUWBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GEdorContManuUWBL.submitData() : 无法获取 InputData 数据！");
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
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GEdorContManuUWBL.submitData() 成功");
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
		// logger.debug("\t@> GEdorContManuUWBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GEdorContManuUWBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取用户提交的信息！");
			logger.debug("\t@> GEdorContManuUWBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全受理号信息！");
			return false;
		}

		// logger.debug("\t@> GEdorContManuUWBL.getInputData() 成功");
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
		// logger.debug("\t@> GEdorContManuUWBL.checkData() 开始");

		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");

		// ----------------------------------------------------------------------

		// 查询 LPEdorApp
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
		}
		// 垃圾处理
		tLPEdorAppSet = null;
		tLPEdorAppDB = null;

		// logger.debug("\t@> GEdorContManuUWBL.checkData() 成功");
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
		// logger.debug("\t@> GEdorContManuUWBL.dealData() 开始");

		// 接收数据变量
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		String sEdorNo = (String) rTransferData.getValueByName("EdorNo");
		String sContNo = (String) rTransferData.getValueByName("ContNo");
		String sUWState = (String) rTransferData.getValueByName("UWState");
		String sUWIdea = (String) rTransferData.getValueByName("UWIdea");

		// ----------------------------------------------------------------------

		if (sEdorNo == null || sEdorNo.trim().equals("")) {
			if (!doBatchPassAll(sEdorAcceptNo)) {
				CError.buildErr(this, "批量核保通过失败！");
				return false;
			}
		} else {
			if (!doSingleContUW(sEdorNo, sContNo, sUWState, sUWIdea)) {
				CError.buildErr(this, "个单保全核保失败！");
				return false;
			}
		}

		// logger.debug("\t@> GEdorContManuUWBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 进行团单下的单张个单核保
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doSingleContUW(String sEdorNo, String sContNo,
			String sUWState, String sUWIdea) {
		// logger.debug("\t@> GEdorContManuUWBL.doSingleContUW() 开始");

		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");

		// 批单号
		if (sEdorNo == null || sEdorNo.trim().equals("")) {
			CError.buildErr(this, "无法获取单张个单核保的批单号信息！");
			return false;
		}

		// 保单号
		if (sContNo == null || sContNo.trim().equals("")) {
			CError.buildErr(this, "无法获取单张个单核保的保单号信息！");
			return false;
		}

		// 核保结论
		if (sUWState == null || sUWState.trim().equals("")) {
			CError.buildErr(this, "无法获取单张个单核保的核保结论信息！");
			return false;
		}

		// ----------------------------------------------------------------------

		// LPEdorMainSchema
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tLPEdorMainSchema.setEdorAcceptNo(sEdorAcceptNo);
		tLPEdorMainSchema.setEdorNo(sEdorNo);
		tLPEdorMainSchema.setContNo(sContNo);
		// TransferData
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", sUWState);
		tTransferData.setNameAndValue("UWIdea", sUWIdea);
		// VData
		VData tVData = new VData();
		tVData.addElement(rGlobalInput);
		tVData.addElement(tLPEdorMainSchema);
		tVData.addElement(tTransferData);
		// 垃圾处理
		tLPEdorMainSchema = null;
		tTransferData = null;

		// 复用个单处理
		PEdorContManuUWBL tPEdorContManuUWBL = new PEdorContManuUWBL();
		if (!tPEdorContManuUWBL.submitData(tVData, rOperation)) {
			mErrors.copyAllErrors(tPEdorContManuUWBL.mErrors);
			CError.buildErr(this, "调用保全个单核保失败！");
			logger.debug("\t@> GEdorContManuUWBL.doSingleContUW() : PEdorContManuUWBL.submitData() 失败！");
			return false;
		}
		tPEdorContManuUWBL = null;
		tVData = null;

		// logger.debug("\t@> GEdorContManuUWBL.doSingleContUW() 成功");
		return true;
	} // function doSingleContUW end

	// ==========================================================================

	/**
	 * 进行团单下的个单批量核保
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean doBatchPassAll(String sEdorAcceptNo) {
		// logger.debug("\t@> GEdorContManuUWBL.doBatchPassAll() 开始");

		// 查询 LPEdorItem
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(sEdorAcceptNo);
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全个人项目表出现异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "该团体保单下没有需要核保的个人项目！");
			logger.debug("\t@> GEdorContManuUWBL.doBatchPassAll() : 出错的 EdorAcceptNo 是 "
							+ sEdorAcceptNo);
			return false;
		} else {
			// 复用单张个单核保循环处理
			for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
				LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema = tLPEdorItemSet.get(i);
				String sEdorNo = tLPEdorItemSchema.getEdorNo();
				String sContNo = tLPEdorItemSchema.getContNo();
				String sUWState = "9";
				String sUWIdea = "批量核保";
				if (!doSingleContUW(sEdorNo, sContNo, sUWState, sUWIdea)) {
					CError.buildErr(this, "该团单下的某个单核保通过失败！");
					logger.debug("\t@> GEdorContManuUWBL.doBatchPassAll() : 出错的 ContNo 是 "
									+ sContNo);
					return false;
				}
				tLPEdorItemSchema = null;
			}
		}
		tLPEdorItemSet = null;
		tLPEdorItemDB = null;

		// logger.debug("\t@> GEdorContManuUWBL.doBatchPassAll() 成功");
		return true;
	} // function doBatchPassAll end

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
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GEdorContManuUWBL tGEdorContManuUWBL = new GEdorContManuUWBL();
	// } //function main end
	// ==========================================================================

} // class GEdorContManuUWBL end
