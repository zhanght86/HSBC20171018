package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>

 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>

 *
 * @version  : 1.00
 * @date     : 2006-05-30
 * @direction: 团体保全养老金给付确认处理类
 ******************************************************************************/

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpEdorAGConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorAGConfirmBL.class);

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 输入数据
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput;
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema;

	// 输出数据
	/** 往界面传输数据的容器 */
	private MMap mMap;
	private VData mResult;

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public GrpEdorAGConfirmBL() {
	}

	// ==========================================================================
	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> GrpEdorFMConfirmBL.submitData() 开始");
		// 将操作数据拷贝到本类中
		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> GrpEdorAGConfirmBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			mInputData = new VData();
			mInputData = (VData) cInputData.clone();
		}
		mOperate = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!prepareData()) {
			return false;
		}
		// this.setOperate("CONFIRM||AG");
		logger.debug("after GrpEdorGBConfirmBL...");

		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> GrpEdorFMConfirmBL.submitData() 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// logger.debug("\t@> GrpEdorAGConfirmBL.getInputData() 开始");
		// GlobalInput
		mGlobalInput = new GlobalInput();
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> GrpEdorAGConfirmBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPGrpEdorItemSchema
		mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		if (mLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> GrpEdorAGConfirmBL.getInputData() : 无法获取 LPGrpEdorItemSchema 数据！");
			return false;
		}
		// ----------------------------------------------------------------------

		// //保全受理号
		// String sEdorAcceptNo = rLPGrpEdorItemSchema.getEdorAcceptNo();
		// if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取保全项目的保全受理号信息！");
		// return false;
		// }
		//
		// //批单号码
		// String sEdorNo = rLPGrpEdorItemSchema.getEdorNo();
		// if (sEdorNo == null || sEdorNo.equals(""))
		// {
		// CError.buildErr(this, "无法获取保全项目的批单号码信息！");
		// return false;
		// }
		//
		// //批改类型
		// String sEdorType = rLPGrpEdorItemSchema.getEdorType();
		// if (sEdorType == null || sEdorType.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取保全项目的批改类型信息！");
		// return false;
		// }
		//
		// //合同号码
		// String sGrpContNo = rLPGrpEdorItemSchema.getGrpContNo();
		// if (sGrpContNo == null || sGrpContNo.trim().equals(""))
		// {
		// CError.buildErr(this, "无法获取保全项目的保单号码信息！");
		// return false;
		// }

		// logger.debug("\t@> GrpEdorFMConfirmBL.getInputData() 成功");
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
		// logger.debug("\t@> GrpEdorFMConfirmBL.checkData() 开始");

		// 检查团体保全项目明细表
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		try {
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团体保全项目明细表出现异常！");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在团体保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));
		}
		// 垃圾处理
		tLPGrpEdorItemDB = null;
		tLPGrpEdorItemSet = null;

		// logger.debug("\t@> GrpEdorAGConfirmBL.checkData() 成功");
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
		// logger.debug("\t@> GrpEdorFMConfirmBL.dealData() 开始");

		mMap = new MMap();

		// ----------------------------------------------------------------------

		// 检查个单保全项目明细表
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询个单保全项目明细表出现异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在个单保全项目明细表中找不到待操作批单的纪录！");
			return false;
		}
		// 垃圾处理
		tLPEdorItemDB = null;

		// ----------------------------------------------------------------------

		// 循环调用个单保全
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tLPEdorItemSchema);
			PEdorAGConfirmBL tPEdorAGConfirmBL = new PEdorAGConfirmBL();
			if (!tPEdorAGConfirmBL.submitData(tVData, mOperate)) {
				mErrors.copyAllErrors(tPEdorAGConfirmBL.mErrors);
				CError.buildErr(this, "处理提交的数据失败！");
				logger.debug("\t@> GrpEdorAGConfirmBL.dealData() : PEdorAGConfirmBL.submitData() 失败！");
				logger.debug("\t   出错的被保人号是 : "
						+ tLPEdorItemSchema.getInsuredNo());
				return false;
			} else {
				VData tTempVData = new VData();
				tTempVData = tPEdorAGConfirmBL.getResult();
				MMap tTempMap = new MMap();
				tTempMap = (MMap) tTempVData.getObjectByObjectName("MMap", 0);
				if (tTempMap == null) {
					CError.buildErr(this, "处理个人保全项目数据失败！");
					logger.debug("\t   出错的被保人号是 : "
							+ tLPEdorItemSchema.getInsuredNo());
					return false;
				} else {
					mMap.add(tTempMap);
				}
				tTempVData = null;
				tTempMap = null;
			}
			tLPEdorItemSchema = null;
			tVData = null;
			tPEdorAGConfirmBL = null;
		}
		// 垃圾处理
		tLPEdorItemSet = null;

		// ----------------------------------------------------------------------

		// 更新状态
		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		// logger.debug("\t@> GrpEdorAGConfirmBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	// 准备返回需要的数据
	private boolean prepareData() {
		mResult = new VData();
		mResult.add(mMap);
		// logger.debug("\t@> GrpEdorAGConfirmBL.prepareData() 成功");
		return true;
	} // function prepareData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperate() {
		return this.mOperate;
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
		if (mInputData != null)
			mInputData = null;
		if (mGlobalInput != null)
			mGlobalInput = null;
		if (mLPGrpEdorItemSchema != null)
			mLPGrpEdorItemSchema = null;
		if (mMap != null)
			mMap = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// GrpEdorFMConfirmBL tGrpEdorFMConfirmBL = new GrpEdorFMConfirmBL();
	// } //function main end
	// ==========================================================================
} // class GrpEdorFMConfirmBL end

