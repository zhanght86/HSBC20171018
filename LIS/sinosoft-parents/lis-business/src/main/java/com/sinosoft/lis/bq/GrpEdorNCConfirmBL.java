package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolOtherDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPPolOtherDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPPolOtherSchema;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPPolOtherSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/*******************************************************************************
 * <p>
 * Title: 团体保全-车牌号更改项
 * </p>
 * <p>
 * Description: 团体保全-车牌号更改项确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * Company: 中科软科技股份有限公司
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @todo : 保全-更改车牌号码-ConfirmBL
 * @author : liuxiaosong (liuxs@sinosoft.com.cn)
 * @version : 1.00 1.01
 * @date : 2006-09-26 2006-09-29 更新记录： 更新人 更新日期 更新原因/内容
 ******************************************************************************/
public class GrpEdorNCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorNCConfirmBL.class);
	// ==========================================================================
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// --------------------------------------------------------------------------

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	// ==========================================================================

	public GrpEdorNCConfirmBL() {
	}

	// ==========================================================================

	/**
	 * 服务于高层类的入口函数
	 * 
	 * @param cInputData
	 *            cOperate
	 * @return boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--==  Grp NC Confirm  ==--");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		return true;
	}

	// ==========================================================================

	/**
	 * 获得外部数据
	 */
	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}
		return true;
	}

	// ==========================================================================

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		// 如果保全项有错误，则不予以生效
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "团险保全项目信息不存在!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1); // 获得全部字段

		return true;
	}

	// ==========================================================================

	/**
	 * 本类核心数据处理方法
	 */
	private boolean dealData() {
		Reflections ref = new Reflections(); // 字段数据传递器
		String CurrDate;
		String CurrTime;
		/**
		 * 取出当前所有P数据
		 */
		LPPolOtherDB tLPPolOtherDB = new LPPolOtherDB();
		LCPolOtherDB tLCPolOtherDB = new LCPolOtherDB();

		tLPPolOtherDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo()); // 团单号
		tLPPolOtherDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo()); // 批单号
		tLPPolOtherDB.setEdorType("NC"); // 保全项目

		LPPolOtherSet curLPPolOtherSet = tLPPolOtherDB.query();
		if (tLPPolOtherDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体保单信息失败!");
			return false;
		}
		if (curLPPolOtherSet == null || curLPPolOtherSet.size() < 1) {
			CError.buildErr(this, "团体保单信息不存在!");
			return false;
		}
		// ----------------------------------------------------------------------
		/**
		 * C,P互换
		 */
		int temp = curLPPolOtherSet.size();
		for (int i = 1; i <= temp; i++) {
			LPPolOtherSchema curLPPolOtherSchema = curLPPolOtherSet.get(i); // 当前的P表数据
			LCPolOtherSchema curLCPolOtherSchema = new LCPolOtherSchema(); // 当前的C表数据

			tLCPolOtherDB.setDutyCode(curLPPolOtherSchema.getDutyCode());
			tLCPolOtherDB.setPolNo(curLPPolOtherSchema.getPolNo());
			LCPolOtherSet curLCPolOtherSet = tLCPolOtherDB.query();
			curLCPolOtherSchema = curLCPolOtherSet.get(1);

			CurrDate = PubFun.getCurrentDate();
			CurrTime = PubFun.getCurrentTime();

			// C表换至P表
			LPPolOtherSchema newLPPolOtherSchema = new LPPolOtherSchema();// 新P表数据
			ref.transFields(newLPPolOtherSchema, curLCPolOtherSchema);// C->P
			newLPPolOtherSchema.setEdorNo(curLPPolOtherSchema.getEdorNo());// transFields之后缺少该字段
			newLPPolOtherSchema.setEdorType(curLPPolOtherSchema.getEdorType());// transFields之后缺少该字段

			newLPPolOtherSchema.setModifyDate(CurrDate);
			newLPPolOtherSchema.setModifyTime(CurrTime);
			mMap.put(newLPPolOtherSchema, "UPDATE");

			// P表换至C表
			LCPolOtherSchema newLCPolOtherSchema = new LCPolOtherSchema();// 新C表数据
			ref.transFields(newLCPolOtherSchema, curLPPolOtherSchema);// P->C
			newLCPolOtherSchema.setModifyDate(CurrDate);
			newLCPolOtherSchema.setModifyTime(CurrTime);
			mMap.put(newLCPolOtherSchema, "DELETE&INSERT");

		}
		// ----------------------------------------------------------------------

		mResult.add(mMap);
		return true;
	}

	// ==========================================================================

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 测试
	 */
	public static void main(String args[]) {
		// 测试用数据
		GlobalInput tGlobalInput = new GlobalInput();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		/*
		 * 设置GlobalInput
		 */
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		/*
		 * 设置LPGrpEdorItemSchema
		 */
		tLPGrpEdorItemSchema.setEdorNo("6020060404000024"); // 批单号
		tLPGrpEdorItemSchema.setGrpContNo("00200603300003"); // 团单号码
		tLPGrpEdorItemSchema.setEdorAcceptNo("6120060404000009"); // 保全受理号
		tLPGrpEdorItemSchema.setEdorType("NC"); // 批改类型
		/*
		 * 设置LCPolOtherSchema
		 */
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPGrpEdorItemSchema);
		// 提交数据, 测试
		GrpEdorNCConfirmBL tGrpEdorNCConfirmBL = new GrpEdorNCConfirmBL();
		if (tGrpEdorNCConfirmBL.submitData(tVData, "")) {
			logger.debug("==========GrpEdorNCConfirm is Sccessfull==========");
		} else {
			logger.debug("==========GrpEdorNCConfirm is not Sccessfull==========");
		}
	}

}
