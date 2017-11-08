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
 * @date     : 2006-05-09
 * @direction: 团体保全保单质押贷款提交
 ******************************************************************************/

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class GrpEdorLNDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorLNDetailBLF.class);
	// public GrpEdorLNDetailBLF() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	// 输出数据
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
		// logger.debug("\t@> GrpEdorLNDetailBLF.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorLNDetailBLF.submitData() : 无法获取 InputData 数据！");
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
		if (!pubSubmit())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorLNDetailBLF.submitData() 成功");
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
		// logger.debug("\t@> GrpEdorLNDetailBLF.dealData() 开始");

		GrpEdorLNDetailBL tGrpEdorLNDetailBL = new GrpEdorLNDetailBL();
		if (!tGrpEdorLNDetailBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tGrpEdorLNDetailBL.getErrors());
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> GrpEdorLNDetailBLF.dealData() : GrpEdorLNDetailBL.submitData() 失败！");
			return false;
		}
		rResultData = new VData();
		rResultData = tGrpEdorLNDetailBL.getResult();
		tGrpEdorLNDetailBL = null;

		// logger.debug("\t@> GrpEdorLNDetailBLF.dealData() 成功");
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
		// logger.debug("\t@> GrpEdorLNDetailBLF.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> GrpEdorLNDetailBLF.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		// 垃圾处理
		tPubSubmit = null;

		// logger.debug("\t@> GrpEdorLNDetailBLF.pubSubmit() 成功");
		return true;
	} // function pubSubmit end

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
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GrpEdorLNDetailBLF tGrpEdorLNDetailBLF = new GrpEdorLNDetailBLF();
	// } //function main end
	// ==========================================================================

} // class GrpEdorLNDetailBLF end
