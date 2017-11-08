package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 缴费形式变更回退处理确认类
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
 * @CreateDate 2005-10-10
 * @version 1.0
 */
public class PEdorPCBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorPCBackConfirmBL.class);
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

	public PEdorPCBackConfirmBL() {
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
		logger.debug("=== 缴费信息变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setModifyDate(CurrDate);
		tLCContSchema.setModifyTime(CurrTime);

//		LCContDB tLCContDB = new LCContDB();
//		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//		if (!tLCContDB.getInfo()) {
//			CError.buildErr(this, "查询保单信息失败!");
//			return false;
//		}
//		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
//		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPContSchema.setModifyDate(CurrDate);
//		tLPContSchema.setModifyTime(CurrTime);
		map.put(tLCContSchema, "DELETE&INSERT");
//		map.put(tLPContSchema, "DELETE&INSERT");

		LPAppntDB tLPAppntDB = new LPAppntDB();
		LPAppntSet tLPAppntSet = new LPAppntSet();
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPAppntSet = tLPAppntDB.query();
		if (tLPAppntSet != null && tLPAppntSet.size() > 0) {
//			LCAppntDB tLCAppntDB = new LCAppntDB();
//			LCAppntSet tLCAppntSet = new LCAppntSet();
//			tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
//			tLCAppntSet = tLCAppntDB.query();
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
//			LPAppntSchema tLPAppntSchema = new LPAppntSchema();
			tRef.transFields(tLCAppntSchema, tLPAppntSet.get(1));
			tLCAppntSchema.setModifyDate(PubFun.getCurrentDate());
			tLCAppntSchema.setModifyTime(PubFun.getCurrentTime());
//			tRef.transFields(tLPAppntSchema, tLCAppntSet.get(1));
//			tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			tLPAppntSchema.setModifyDate(PubFun.getCurrentDate());
//			tLPAppntSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(tLCAppntSchema, "DELETE&INSERT");
//			map.put(tLPAppntSchema, "DELETE&INSERT");
		}

		// 缴费形式变更，续期撤销
//		LJSPayDB tLJSPayDB = new LJSPayDB();
//		tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
//		tLJSPayDB.setOtherNoType("2");
//		LJSPaySet tLJSPaySet = tLJSPayDB.query();
//		if (tLJSPayDB.mErrors.needDealError()) {
//			CError.buildErr(this, "续期应收信息查询失败!");
//			return false;
//		}
//		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
//			TransferData tTransferData = new TransferData();
//			tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
//			VData tVData = new VData();
//			tVData.add(tLCContSchema);
//			tVData.add(tTransferData);
//			tVData.add(mGlobalInput);
//
//			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
//			if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")) {
//				mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
//				return false;
//			}
//			tVData.clear();
//			tVData = tIndiDueFeeCancelBL.getResult();
//			MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
//			if (tMMap != null) {
//				map.add(tMMap);
//			}
//		} else {
//			logger.debug("== 没有续期应收数据 保单号：　"
//					+ mLPEdorItemSchema.getContNo() + "==");
//		}

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
		PEdorPCBackConfirmBL pedorpcbackconfirmbl = new PEdorPCBackConfirmBL();
	}
}
