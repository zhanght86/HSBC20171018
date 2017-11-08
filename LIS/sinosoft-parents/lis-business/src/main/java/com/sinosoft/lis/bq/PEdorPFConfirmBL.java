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
 * @date     : 2006-01-22
 * @direction: 保全项目 PF (保单解挂) 确认
 ******************************************************************************/

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorPFConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorPFConfirmBL.class);
	// public PEdorPFConfirmBL() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	//public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	// 全局变量
	private VData rResult;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorPFConfirmBL.submitData() 开始");
		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorPFDetailBLF.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!dealData())
			return false;
		// 垃圾处理
		collectGarbage();
		// logger.debug("\t@> PEdorPFConfirmBL.submitData() 成功");
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
		// logger.debug("\t@> PEdorPFConfirmBL.getInputData() 开始");
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorPFDetailBLF.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}
		// logger.debug("\t@> PEdorPFConfirmBL.getInputData() 成功");
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
		// logger.debug("\t@> PEdorPFConfirmBL.checkData() 开始");
		// logger.debug("\t@> PEdorPFConfirmBL.checkData() 成功");
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
		// logger.debug("\t@> PEdorPFConfirmBL.dealData() 开始");
		PEdorPLConfirmBL tPEdorPLConfirmBL = new PEdorPLConfirmBL();
		if (!tPEdorPLConfirmBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tPEdorPLConfirmBL.mErrors);
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> PEdorPFConfirmBL.dealData() : PEdorPLConfirmBL.submitData() 失败！");
			return false;
		}
		rResult = new VData();
		rResult = tPEdorPLConfirmBL.getResult();
		// 垃圾处理
		tPEdorPLConfirmBL = null;
		// logger.debug("\t@> PEdorPFConfirmBL.prepareOutputData() 成功");
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
		return rResult;
	} // function getResult end

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
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// PEdorPFConfirmBL tPEdorPFConfirmBL = new PEdorPFConfirmBL();
	// } //function main end
	// ==========================================================================

} // class PEdorPFConfirmBL end
