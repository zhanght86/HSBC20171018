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
 * @date     : 2005-12-31, 2006-08-17
 * @direction: 保全抽检查询待抽检到抽检工作流
 ******************************************************************************/

import com.sinosoft.lis.db.LPSpotCheckTrackDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPSpotCheckTrackSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorSpotCheckQueryUpdMissionBL {
private static Logger logger = Logger.getLogger(PEdorSpotCheckQueryUpdMissionBL.class);
	// public PEdorSpotCheckQueryUpdMissionBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
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
		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.submitData()
		// 开始");

		// 接收参数
		if (cInputData == null) {
			logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.submitData() : 无法获取 InputData 数据！");
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
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
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.submitData()
		// 结束");
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
		// logger.debug("\t@>
		// PEdorSpotCheckQueryUpdMissionBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 保全受理号
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "需要抽检的保全受理号不能为空！");
			return false;
		}

		// 抽检标记
		String sCheckFlag = (String) rTransferData
				.getValueByName("NeedCheckFlag");
		if (sCheckFlag == null || sCheckFlag.trim().equals("")) {
			CError.buildErr(this, "无法获取抽检标记信息！");
			return false;
		}

		// logger.debug("\t@>
		// PEdorSpotCheckQueryUpdMissionBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.dealData()
		// 开始");

		rMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// ----------------------------------------------------------------------

		// 接收传递变量
		String sEdorAcceptNo = (String) rTransferData
				.getValueByName("EdorAcceptNo");
		String sCheckFlag = (String) rTransferData
				.getValueByName("NeedCheckFlag");

		// ----------------------------------------------------------------------

		// 如果需要抽检
		if (sCheckFlag.equals("1")) {
			// 查询 LPSpotCheckTrack
			LPSpotCheckTrackDB tLPSpotCheckTrackDB = new LPSpotCheckTrackDB();
			tLPSpotCheckTrackDB.setEdorAcceptNo(sEdorAcceptNo);
			int nCheckTimes = tLPSpotCheckTrackDB.getCount();
			tLPSpotCheckTrackDB = null;
			// 插入 LPSpotCheckTrack
			LPSpotCheckTrackSchema tLPSpotCheckTrackSchema = new LPSpotCheckTrackSchema();
			tLPSpotCheckTrackSchema.setCheckTimes(nCheckTimes + 1);
			tLPSpotCheckTrackSchema.setEdorAcceptNo(sEdorAcceptNo);
			tLPSpotCheckTrackSchema.setCheckStartDate(sCurrentDate);
			tLPSpotCheckTrackSchema.setOperator(rGlobalInput.Operator);
			tLPSpotCheckTrackSchema.setMakeDate(sCurrentDate);
			tLPSpotCheckTrackSchema.setMakeTime(sCurrentTime);
			tLPSpotCheckTrackSchema.setModifyDate(sCurrentDate);
			tLPSpotCheckTrackSchema.setModifyTime(sCurrentTime);
			rMap.put(tLPSpotCheckTrackSchema, "INSERT");
			tLPSpotCheckTrackSchema = null;
		}

		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.dealData()
		// 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 本类准备提交后台的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.outputData()
		// 开始");

		rResultData = new VData();
		rResultData.add(rMap);
		rResultData.add(rTransferData);

		// logger.debug("\t@> PEdorSpotCheckQueryUpdMissionBL.outputData()
		// 结束");
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
		if (rTransferData != null)
			rTransferData = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	// ==========================================================================


} // class PEdorSpotCheckQueryUpdMissionBL end
