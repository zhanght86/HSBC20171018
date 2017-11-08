package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
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
 * Description: 减保回退处理确认类
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
 * @CreateDate 2005-09-26
 */
public class PEdorPTBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorPTBackConfirmBL.class);
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
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCPolSet mLCPolSet = new LCPolSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	
	private LPContSchema mLPContSchema = new LPContSchema();
	private LCContSchema mLCContSchema = new LCContSchema();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();


	public PEdorPTBackConfirmBL() {
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
		logger.debug("=== 减保回退确认生效 ===");
		Reflections tRef = new Reflections();

//		LPPolSchema aLPPolSchema = new LPPolSchema();
//		LCPolSchema aLCPolSchema = new LCPolSchema();
//
//		LPDutySchema aLPDutySchema = new LPDutySchema();
//		LCDutySchema aLCDutySchema = new LCDutySchema();
//
//		LPGetSchema aLPGetSchema = new LPGetSchema();
//		LCGetSchema aLCGetSchema = new LCGetSchema();
//
//		LPPremSchema aLPPremSchema = new LPPremSchema();
//		LCPremSchema aLCPremSchema = new LCPremSchema();
//
//		LPPolDB tLPPolDB = new LPPolDB();
//		LPPolSet tLPPolSet = new LPPolSet();
//		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPPolSet = tLPPolDB.query();
//		for (int i = 1; i <= tLPPolSet.size(); i++) {
//			// Pol
//			String PolNo = tLPPolSet.get(i).getPolNo();
//			aLPPolSchema = new LPPolSchema();
//			aLCPolSchema = new LCPolSchema();
//			tRef.transFields(aLCPolSchema, tLPPolSet.get(i));
//			aLCPolSchema.setModifyDate(CurrDate);
//			aLCPolSchema.setModifyTime(CurrTime);
//
//			LCPolDB tLCPolDB = new LCPolDB();
//			tLCPolDB.setPolNo(PolNo);
//			if (!tLCPolDB.getInfo()) {
//				CError.buildErr(this, "查询险种表失败!险种号" + tLCPolDB.getPolNo());
//				return false;
//			}
//			tRef.transFields(aLPPolSchema, tLCPolDB.getSchema());
//			aLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			aLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			aLPPolSchema.setModifyDate(CurrDate);
//			aLPPolSchema.setModifyTime(CurrTime);
//
//			mLCPolSet.add(aLCPolSchema);
//			mLPPolSet.add(aLPPolSchema);
//
//			// Duty
//			LPDutyDB tLPDutyDB = new LPDutyDB();
//			tLPDutyDB.setPolNo(PolNo);
//			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPDutySet tLPDutySet = new LPDutySet();
//			tLPDutySet = tLPDutyDB.query();
//			if (tLPDutySet == null || tLPDutySet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPDutySet.size(); j++) {
//				aLCDutySchema = new LCDutySchema();
//				tRef.transFields(aLCDutySchema, tLPDutySet.get(j));
//				aLCDutySchema.setModifyDate(CurrDate);
//				aLCDutySchema.setModifyTime(CurrTime);
//				mLCDutySet.add(aLCDutySchema);
//			}
//
//			LCDutyDB tLCDutyDB = new LCDutyDB();
//			LCDutySet tLCDutySet = new LCDutySet();
//			tLCDutyDB.setPolNo(PolNo);
//			tLCDutySet = tLCDutyDB.query();
//			if (tLCDutySet == null || tLCDutySet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCDutySet.size(); j++) {
//				aLPDutySchema = new LPDutySchema();
//				tRef.transFields(aLPDutySchema, tLCDutySet.get(j));
//				aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPDutySchema.setModifyDate(CurrDate);
//				aLPDutySchema.setModifyTime(CurrTime);
//				mLPDutySet.add(aLPDutySchema);
//			}
//
//			// Prem
//			LPPremDB tLPPremDB = new LPPremDB();
//			tLPPremDB.setPolNo(PolNo);
//			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPPremSet tLPPremSet = new LPPremSet();
//			tLPPremSet = tLPPremDB.query();
//			if (tLPPremSet == null || tLPPremSet.size() < 1) {
//				CError.buildErr(this, "查询险种保费信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPPremSet.size(); j++) {
//				aLCPremSchema = new LCPremSchema();
//				tRef.transFields(aLCPremSchema, tLPPremSet.get(j));
//				aLCPremSchema.setModifyDate(CurrDate);
//				aLCPremSchema.setModifyTime(CurrTime);
//				mLCPremSet.add(aLCPremSchema);
//			}
//			LCPremDB tLCPremDB = new LCPremDB();
//			LCPremSet tLCPremSet = new LCPremSet();
//			tLCPremDB.setPolNo(PolNo);
//			tLCPremSet = tLCPremDB.query();
//			if (tLCPremSet == null || tLCPremSet.size() < 1) {
//				CError.buildErr(this, "查询险种保费信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCPremSet.size(); j++) {
//				aLPPremSchema = new LPPremSchema();
//				tRef.transFields(aLPPremSchema, tLCPremSet.get(j));
//				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPPremSchema.setModifyDate(CurrDate);
//				aLPPremSchema.setModifyTime(CurrTime);
//				mLPPremSet.add(aLPPremSchema);
//			}
//
//			// Get
//			LPGetDB tLPGetDB = new LPGetDB();
//			tLPGetDB.setPolNo(PolNo);
//			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPGetSet tLPGetSet = new LPGetSet();
//			tLPGetSet = tLPGetDB.query();
//			if (tLPGetSet == null || tLPGetSet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPGetSet.size(); j++) {
//				aLCGetSchema = new LCGetSchema();
//				tRef.transFields(aLCGetSchema, tLPGetSet.get(j));
//				aLCGetSchema.setModifyDate(CurrDate);
//				aLCGetSchema.setModifyTime(CurrTime);
//				mLCGetSet.add(aLCGetSchema);
//			}
//			LCGetDB tLCGetDB = new LCGetDB();
//			LCGetSet tLCGetSet = new LCGetSet();
//			tLCGetDB.setPolNo(PolNo);
//			tLCGetSet = tLCGetDB.query();
//			if (tLCGetSet == null || tLCGetSet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCGetSet.size(); j++) {
//				aLPGetSchema = new LPGetSchema();
//				tRef.transFields(aLPGetSchema, tLCGetSet.get(j));
//				aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPGetSchema.setModifyDate(CurrDate);
//				aLPGetSchema.setModifyTime(CurrTime);
//				mLPGetSet.add(aLPGetSchema);
//			}
//		}
//		
//		LPContDB tLPContDB = new LPContDB();
//		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		if (!tLPContDB.getInfo()) {
//			CError.buildErr(this, "查询保单信息失败!");
//			return false;
//		}
//		tRef.transFields(mLCContSchema, tLPContDB.getSchema());
//		mLCContSchema.setModifyDate(CurrDate);
//		mLCContSchema.setModifyTime(CurrTime);
//		LCContDB tLCContDB = new LCContDB();
//		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//		if (!tLCContDB.getInfo()) {
//			CError.buildErr(this, "查询保单信息失败!");
//			return false;
//		}
//		tRef.transFields(mLPContSchema, tLCContDB.getSchema());
//		mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		mLPContSchema.setModifyDate(CurrDate);
//		mLPContSchema.setModifyTime(CurrTime);
//		
//		map.put(mLCContSchema, "DELETE&INSERT");
//		map.put(mLPContSchema, "DELETE&INSERT");
//
//		map.put(mLCPolSet, "DELETE&INSERT");
//		map.put(mLPPolSet, "DELETE&INSERT");
//		map.put(mLCDutySet, "DELETE&INSERT");
//		map.put(mLPDutySet, "DELETE&INSERT");
//		map.put(mLCPremSet, "DELETE&INSERT");
//		map.put(mLPPremSet, "DELETE&INSERT");
//		map.put(mLCGetSet, "DELETE&INSERT");
//		map.put(mLPGetSet, "DELETE&INSERT");
		
		ValidateEdorData2 mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		//采用新的方式进行保全数据回退
	    String[] chgTables = {"LCCont","LCPol","LCDuty","LCPrem","LCGet"};
	    mValidateEdorData.backConfirmData(chgTables);
	    map.add(mValidateEdorData.getMap());

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
		PEdorPTBackConfirmBL pedorptbackconfirmbl = new PEdorPTBackConfirmBL();
	}
}
