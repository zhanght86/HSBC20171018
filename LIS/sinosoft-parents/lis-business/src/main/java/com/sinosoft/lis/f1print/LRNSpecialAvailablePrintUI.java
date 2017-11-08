package com.sinosoft.lis.f1print;
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
 * @date     : 2005-12-05
 * @direction: 保单特殊复效打印
 ******************************************************************************/

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class LRNSpecialAvailablePrintUI implements BusinessService{
private static Logger logger = Logger.getLogger(LRNSpecialAvailablePrintUI.class);
	// public LRNSpecialAvailablePrintUI() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData = new VData();
	private String rOperation = new String();
	// 全局变量
	private VData rResultData = new VData();

	// ==========================================================================

	/**
	 * submitData(VData cInputData, String cOperate)
	 * 
	 * @param :
	 *            VData
	 * @param :
	 *            String
	 * @return : boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.submitData()
		// 开始");
		// 接受参数
		rInputData = (VData) cInputData.clone();
		rOperation = (cOperate != null) ? cOperate.trim() : "";
		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!printData())
			return false;
		collectGarbage();
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.submitData()
		// 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	private boolean getInputData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.getInputData()
		// 开始");
		// 将操作数据拷贝到本类中
		try {
			rInputData = (VData) rInputData.clone();
		} catch (Exception ex) {
			CError.buildErr(this, "无法获取用户登录机构信息和录入的数据信息！");
			logger.debug("\t@> LRNSpecialAvailablePrintUI.getInputData() : InputData.clone() 失败！");
			ex.printStackTrace();
			return false;
		}
		// return to submitData
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.getInputData()
		// 成功");
		return true;
	} // function getInputData end

	// ==========================================================================

	private boolean checkData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintBL.checkData() 开始");
		if (!rOperation.equalsIgnoreCase("Print")) {
			CError.buildErr(this, "未知的操作请求！");
			logger.debug("\t@> LRNSpecialAvailablePrintUI.checkData() : submitData(cInputData, cOperate) 中 cOperate 必须为 \"PRINT\"！");
			return false;
		}
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.checkData() 成功");
		return true;
	} // function checkData end

	// ==========================================================================

	private boolean printData() {
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.printData() 开始");
		// 调用 LRNSpecialAvailablePrintBL 处理数据
		LRNSpecialAvailablePrintBL tLRNSpecialAvailablePrintBL = new LRNSpecialAvailablePrintBL();
		if (!tLRNSpecialAvailablePrintBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tLRNSpecialAvailablePrintBL.getErrors());
			CError.buildErr(this, "调用后台类处理提交的数据失败！");
			logger.debug("\t@> LRNSpecialAvailablePrintUI.printData() : LRNSpecialAvailablePrintBL.submitData() 失败！");
			return false;
		} else
			rResultData = tLRNSpecialAvailablePrintBL.getResult();
		// return to submitData
		// logger.debug("\t@> LRNSpecialAvailablePrintUI.printData() 成功");
		return true;
	} // function printData end

	// ==========================================================================

	/**
	 * 返回处理结果
	 * 
	 * @param :
	 *            null
	 * @return : VData
	 */
	public VData getResult() {
		return this.rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回错误信息
	 * 
	 * @param :
	 *            null
	 * @return : CErrors
	 */
	public CErrors getErrors() {
		return this.mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理运行时产生的垃圾
	 * 
	 * @param :
	 *            null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 测试主程序业务方法
	 * 
	 * @param :
	 *            String[]
	 */
	// public static void main(String args[])
	// {
	// LRNSpecialAvailablePrintUI tLRNSpecialAvailablePrintUI = new
	// LRNSpecialAvailablePrintUI();
	// } //function main end
	// ==========================================================================

} // class LRNSpecialAvailablePrintUI end
