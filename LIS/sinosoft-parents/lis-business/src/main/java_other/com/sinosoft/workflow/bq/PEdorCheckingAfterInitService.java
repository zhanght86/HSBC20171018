package com.sinosoft.workflow.bq;
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
 * @date     : 2005-12-30
 * @direction: 保全抽检查询待抽检到抽检工作流
 ******************************************************************************/

import com.sinosoft.lis.bq.PEdorSpotCheckDealBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

// ******************************************************************************

public class PEdorCheckingAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorCheckingAfterInitService.class);
	// public PEdorCheckingAfterInitService() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 全局变量
	private String rOperation;
	// 输出数据
	private TransferData rTransferData = new TransferData();
	private VData rResultData = new VData();

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorCheckingAfterInitService.submitData()
		// 开始");
		// 接收数据
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorCheckingAfterInitService.submitData() : InputData = null ！");
			return false;
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";
		// 业务处理
		PEdorSpotCheckDealBL tPEdorSpotCheckDealBL = new PEdorSpotCheckDealBL();
		if (!tPEdorSpotCheckDealBL.submitData(cInputData, rOperation)) {
			mErrors.copyAllErrors(tPEdorSpotCheckDealBL.getErrors());
			return false;
		}
		// 处理结果
		rResultData = tPEdorSpotCheckDealBL.getResult();
		// 垃圾处理
		tPEdorSpotCheckDealBL = null;
		// logger.debug("\t@> PEdorCheckingAfterInitService.submitData()
		// 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return this.rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回经过本类处理的传输数据
	 * 
	 * @param null
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return this.rTransferData;
	} // function getReturnTransferData end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return this.mErrors;
	} // function getErrors end

	// ==========================================================================


} // class PEdorCheckingAfterInitService end
