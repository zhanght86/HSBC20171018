package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 保单补发回退确认类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorLRBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorLRBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorLRBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 保单补发回退确认生效 ===");

		// ----------增加了在保单层P表和C表的互换add by ssx 2008-08-07--------
		Reflections tReflections = new Reflections();

		// 检查LPCont表
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询保全合同表发生异常！");
			return false;
		}
		LPContSchema tLPContSchemaOld = tLPContDB.getSchema();

		LCContDB tLCContDB = new LCContDB();

		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		LCContSchema tLCContSchemaOld = tLCContDB.getSchema();
		LCContSchema tLCContSchemaNew = new LCContSchema();
		// 拷贝LPCONT到LCONT
		try {
			tReflections.transFields(tLCContSchemaNew, tLPContSchemaOld);
		} catch (Exception ex) {
			CError.buildErr(this, "拷贝保全合同表到合同表失败！");
			return false;
		}

		tLCContSchemaNew.setMakeDate(tLCContSchemaOld.getMakeDate());
		tLCContSchemaNew.setMakeTime(tLCContSchemaOld.getMakeTime());
		tLCContSchemaNew.setModifyDate(PubFun.getCurrentDate());
		tLCContSchemaNew.setModifyTime(PubFun.getCurrentTime());
		map.put(tLCContSchemaNew, "DELETE&INSERT");

		// ---------------------contstate的回退start--------------------
		// 查询lPContState
		LPContStateDB tLPContStateDB = new LPContStateDB();
		tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContStateDB.setContNo(mLPEdorItemSchema.getContNo());
		LPContStateSet tLPContStateSet = tLPContStateDB.query();
		// 判断是否生成相关的状态操作 将C表的最近的状态删除,将上一状态的EndDate置为null
		if (tLPContStateSet != null && tLPContStateSet.size() > 0) {
			map.put(tLPContStateSet, "DELETE");
			// 查询LCContState
			LCContStateDB tLCContStateDB = null;
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			for (int i = 1; i <= tLPContStateSet.size(); i++) {
				tLCContStateDB = new LCContStateDB();
				tLPContStateSchema = tLPContStateSet.get(i);
				tLCContStateDB.setContNo(tLPContStateSchema.getContNo());
				tLCContStateDB.setInsuredNo(tLPContStateSchema.getInsuredNo());
				tLCContStateDB.setPolNo(tLPContStateSchema.getPolNo());
				tLCContStateDB.setStateType(tLPContStateSchema.getStateType());
				tLCContStateDB.setStartDate(tLPContStateSchema.getStartDate());
				if (!tLCContStateDB.getInfo()) {
					CError.buildErr(this, "查询保单状态信息失败!");
					return false;
				}
				tLCContStateSchema = tLCContStateDB.getSchema();
				String tEndDate = tLCContStateSchema.getEndDate();
				if (tEndDate == null || tEndDate.equals("")) {
					map.put(tLCContStateSchema, "DELETE");
				} else {
					tLCContStateSchema.setEndDate("");
					tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
					tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					map.put(tLCContStateSchema, "UPDATE");
				}
			}
		}

		// ---------------------contstate的回退End------------------------------
		// --------------------add end-----------明天奥运不放假!-----------------

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorLRBackConfirmBL pedorlrbackconfirmbl = new PEdorLRBackConfirmBL();
	}
}
