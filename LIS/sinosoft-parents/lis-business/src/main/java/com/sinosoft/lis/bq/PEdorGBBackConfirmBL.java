package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
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
 * Description: 领取年龄变更保全回退确认类
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
public class PEdorGBBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorGBBackConfirmBL.class);
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

	public PEdorGBBackConfirmBL() {
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
		logger.debug("=== 领取年龄变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		// C表
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCGetSet aLCGetSet = new LCGetSet();

		// P表
		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPGetSet aLPGetSet = new LPGetSet();

		// 查询险种表
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1) {
			CError.buildErr(this, "查询原险种信息失败!");
			return false;
		}
		
		//保全回退直接那回退项目的P表数据覆盖C表，无需对P表进行任何操作
		//因为MS不需要回退的回退
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			// 将P表中数据放到C表中[险种表]
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyDate(CurrDate);
			tLCPolSchema.setModifyTime(CurrTime);
			aLCPolSet.add(tLCPolSchema);

			// 查询C表数据[险种表]
//			LCPolDB tLCPolDB = new LCPolDB();
//			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
//			if (!tLCPolDB.getInfo()) {
//				mErrors.copyAllErrors(tLCPolDB.mErrors);
//				mErrors.addOneError(new CError("查询险种保单表失败！"));
//				return false;
//			}
//			// 将C表中数据放到P表中[险种表]
//			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
//			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			tLPPolSchema.setModifyDate(CurrDate);
//			tLPPolSchema.setModifyTime(CurrTime);
//			aLPPolSet.add(tLPPolSchema);

			LCDutySet tLCDutySet = new LCDutySet();
			LCGetSet tLCGetSet = new LCGetSet();
			// 得到责任信息
			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPDutySet = tLPDutyDB.query();
			if (tLPDutySet.size() < 1) {
				CError.buildErr(this, "查询原责任信息失败!");
				return false;
			}
			for (int j = 1; j <= tLPDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(j).getSchema());
				tLCDutySchema.setModifyDate(CurrDate);
				tLCDutySchema.setModifyTime(CurrTime);
				aLCDutySet.add(tLCDutySchema);
			}
			// 原责任信息
//			if (tLPDutySet != null && tLPDutySet.size() > 0) {
//				LCDutyDB tLCDutyDB = new LCDutyDB();
//				tLCDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
//				tLCDutySet = tLCDutyDB.query();
//				for (int j = 1; j <= tLCDutySet.size(); j++) {
//					LPDutySchema tLPDutySchema = new LPDutySchema();
//					tRef.transFields(tLPDutySchema, tLCDutySet.get(j)
//							.getSchema());
//					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPDutySchema.setModifyDate(CurrDate);
//					tLPDutySchema.setModifyTime(CurrTime);
//					aLPDutySet.add(tLPDutySchema);
//				}
//			}

			// 得到给付项信息
			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPGetSet = tLPGetDB.query();
			for (int j = 1; j <= tLPGetSet.size(); j++) {
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
				tLCGetSchema.setModifyDate(CurrDate);
				tLCGetSchema.setModifyTime(CurrTime);
				aLCGetSet.add(tLCGetSchema);
			}

			// 原给付项信息
//			if (tLPGetSet != null && tLPGetSet.size() > 0) {
//				LCGetDB tLCGetDB = new LCGetDB();
//				tLCGetDB.setPolNo(tLPPolSet.get(i).getPolNo());
//				tLCGetSet = tLCGetDB.query();
//				for (int j = 1; j <= tLCGetSet.size(); j++) {
//					LPGetSchema tLPGetSchema = new LPGetSchema();
//					tRef
//							.transFields(tLPGetSchema, tLCGetSet.get(j)
//									.getSchema());
//					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPGetSchema.setModifyDate(CurrDate);
//					tLPGetSchema.setModifyTime(CurrTime);
//					aLPGetSet.add(tLPGetSchema);
//				}
//			}
		}

		map.put(aLCPolSet, "UPDATE");
		map.put(aLCDutySet, "UPDATE");
		map.put(aLCGetSet, "UPDATE");
//		map.put(aLPPolSet, "UPDATE");
//		map.put(aLPDutySet, "UPDATE");
//		map.put(aLPGetSet, "UPDATE");

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
		PEdorGBBackConfirmBL pedorgbbackconfirmbl = new PEdorGBBackConfirmBL();
	}
}
