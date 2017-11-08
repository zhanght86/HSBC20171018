package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhangtao
 * @version  : 1.00
 * @date     : 2007-06-18
 * @direction: 保全保险期限变更提交
 ******************************************************************************/

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorYCDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorYCDetailBLF.class);
	// public PEdorBCDetailBLF() {}

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
		// logger.debug("\t@> PEdorBCDetailBLF.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorBCDetailBLF.submitData() : 无法获取 InputData 数据！");
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

		// logger.debug("\t@> PEdorBCDetailBLF.submitData() 成功");
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
		// logger.debug("\t@> PEdorBCDetailBLF.dealData() 开始");

		PEdorYCDetailBL tPEdorYCDetailBL = new PEdorYCDetailBL();
		if (!tPEdorYCDetailBL.submitData(rInputData, rOperation)) {
			mErrors.copyAllErrors(tPEdorYCDetailBL.getErrors());
			CError.buildErr(this, "处理提交的数据失败！");
			logger.debug("\t@> PEdorYCDetailBLF.dealData() : PEdorYCDetailBL.submitData() 失败！");
			return false;
		}
		rResultData = new VData();
		rResultData = tPEdorYCDetailBL.getResult();
		tPEdorYCDetailBL = null;

		// logger.debug("\t@> PEdorYCDetailBLF.dealData() 成功");
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
		// logger.debug("\t@> PEdorYCDetailBLF.pubSubmit() 开始");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(rResultData, rOperation)) {
			CError.buildErr(this, "保存提交的信息到数据库失败！");
			logger.debug("\t@> PEdorYCDetailBLF.pubSubmit() : PubSubmit.submitData() 失败！");
			return false;
		}
		tPubSubmit = null;

		// logger.debug("\t@> PEdorYCDetailBLF.pubSubmit() 成功");
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
	// PEdorYCDetailBLF tPEdorBCDetailBLF = new PEdorYCDetailBLF();
	// } //function main end
	// ==========================================================================

} // class PEdorYCDetailBLF end
