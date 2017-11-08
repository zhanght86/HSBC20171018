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
 * @direction: 保全项目 PF (保单解挂) 提交
 ******************************************************************************/

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorPFDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPFDetailBLF.class);
	// public PEdorPFDetailBLF() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
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
		// logger.debug("\t@> PEdorPFDetailBLF.submitData() 开始");
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
		if (!pubSubmit())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorPFDetailBLF.submitData() 成功");
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
		// logger.debug("\t@> PEdorPFDetailBLF.getInputData() 开始");
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorPFDetailBLF.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}
		// logger.debug("\t@> PEdorPFDetailBLF.getInputData() 成功");
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
		// logger.debug("\t@> PEdorPFDetailBLF.checkData() 开始");
		// logger.debug("\t@> PEdorPFDetailBLF.checkData() 成功");
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
		// logger.debug("\t@> PEdorPFDetailBLF.dealData() 开始");
		PEdorPLDetailBL tPEdorPLDetailBL = new PEdorPLDetailBL();
		if (!tPEdorPLDetailBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tPEdorPLDetailBL.mErrors);
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> PEdorPFDetailBLF.dealData() : PEdorPLDetailBL.submitData() 失败！");
			return false;
		}
		rResult = new VData();
		rResult = tPEdorPLDetailBL.getResult();
		// 垃圾处理
		tPEdorPLDetailBL = null;
		// logger.debug("\t@> PEdorPFDetailBLF.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 提交本类的处理结果到数据库
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean pubSubmit() {
		// logger.debug("\t@> LPLiveInquiryBL.pubSubmit() 开始");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResult, "")) {
			CError.buildErr(this, "保存提交的数据到数据库失败！");
			logger.debug("\t@> PEdorPFDetailBLF.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		// 垃圾处理
		tPubSubmit = null;
		// return to submitData
		// logger.debug("\t@> LPLiveInquiryBL.pubSubmit() 成功");
		return true;
	} // function pubSubmit end

	// ==========================================================================

	/**
	 * 返回处理结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResult;
	} // function getResult end

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
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// PEdorPFDetailBLF tPEdorPFDetailBLF = new PEdorPFDetailBLF();
	// } //function main end
	// ==========================================================================

} // class PEdorPFDetailBLF end
