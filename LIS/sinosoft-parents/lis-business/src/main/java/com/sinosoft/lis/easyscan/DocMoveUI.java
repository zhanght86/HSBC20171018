/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-06
 * @direction: 影像迁移提交
 ******************************************************************************/

package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class DocMoveUI {
private static Logger logger = Logger.getLogger(DocMoveUI.class);
	// public DocMoveUI() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> DocMoveUI.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> DocMoveUI.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!dealData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> DocMoveUI.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> DocMoveUI.dealData() 开始");

		DocMoveBL tDocMoveBL = new DocMoveBL();
		if (!tDocMoveBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tDocMoveBL.getErrors());
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> DocMoveUI.dealData() : DocMoveBL.submitData() 失败！");
			return false;
		}
		tDocMoveBL = null;

		// logger.debug("\t@> DocMoveUI.dealData() 结束");
		return true;
	} // function dealData end

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
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	public static void main(String[] args) {
		// GlobalInput
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		// TransferData
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OldManageCom", "863200");
		tTransferData.setNameAndValue("NewManageCom", "86");
		tTransferData.setNameAndValue("StartDate", "2005-09-05");
		tTransferData.setNameAndValue("EndDate", "2005-09-05");
		tTransferData.setNameAndValue("DocID", "11133");
		// VData
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		DocMoveUI tDocMoveUI = new DocMoveUI();
		if (tDocMoveUI.submitData(tVData, "RESEND")) {
			logger.debug("OK了啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
		}
	} // function main end

	// ==========================================================================


} // class DocMoveUI end
