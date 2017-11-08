package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
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
 * Description: 投保人信息变更保全回退处理确认类
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
 * @CreateDate 2005-10-18
 * @version 1.0
 */
public class PEdorACBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorACBackConfirmBL.class);
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

	public PEdorACBackConfirmBL() {
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
		logger.debug("=== 投保人信息变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		// Cont
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

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);

		map.put(tLCContSchema, "DELETE&INSERT");
		map.put(tLPContSchema, "DELETE&INSERT");

		// Appnt
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPAppntDB.getInfo()) {
			CError.buildErr(this, "查询原投保人信息失败!");
			return false;
		}
		tRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema());
		tLCAppntSchema.setModifyDate(CurrDate);
		tLCAppntSchema.setModifyTime(CurrTime);
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			CError.buildErr(this, "查询投保人信息失败!");
			return false;
		}
		tRef.transFields(tLPAppntSchema, tLCAppntDB.getSchema());
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setModifyDate(CurrDate);
		tLPAppntSchema.setModifyTime(CurrTime);

		map.put(tLCAppntSchema, "DELETE&INSERT");
		map.put(tLPAppntSchema, "DELETE&INSERT");

		// Person
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();

		LPPersonDB tLPPersonDB = new LPPersonDB();
		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPersonDB.setCustomerNo(tLCAppntSchema.getAppntNo());
		if (!tLPPersonDB.getInfo()) {
			CError.buildErr(this, "查询投保人原客户信息失败!");
			return false;
		}
		tRef.transFields(tLDPersonSchema, tLPPersonDB.getSchema());
		tLDPersonSchema.setModifyDate(CurrDate);
		tLDPersonSchema.setModifyTime(CurrTime);
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tLCAppntSchema.getAppntNo());
		if (!tLDPersonDB.getInfo()) {
			CError.buildErr(this, "查询投保人客户信息失败!");
			return false;
		}
		tRef.transFields(tLPPersonSchema, tLDPersonDB.getSchema());
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPersonSchema.setModifyDate(CurrDate);
		tLPPersonSchema.setModifyTime(CurrTime);

		map.put(tLDPersonSchema, "DELETE&INSERT");
		map.put(tLPPersonSchema, "DELETE&INSERT");

		// Insured relationtoappnt
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsuredSet = tLPInsuredDB.query();
		LCInsuredSchema tLCInsuredSchema;
		LPInsuredSchema tLPInsuredSchema;
		if (tLPInsuredSet.size() > 0) {
			for (int i = 1; i <= tLPInsuredSet.size(); i++) {
				tLCInsuredSchema = new LCInsuredSchema();
				tRef.transFields(tLCInsuredSchema, tLPInsuredSet.get(i));
				tLCInsuredSchema.setModifyDate(CurrDate);
				tLCInsuredSchema.setModifyTime(CurrTime);
				aLCInsuredSet.add(tLCInsuredSchema);
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
				if (!tLCInsuredDB.getInfo()) {
					CError.buildErr(this, "查询被包人信息失败!");
					return false;
				}
				tLPInsuredSchema = new LPInsuredSchema();
				tRef.transFields(tLPInsuredSchema, tLCInsuredDB.getSchema());
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPInsuredSchema.setModifyDate(CurrDate);
				tLPInsuredSchema.setModifyTime(CurrTime);
				aLPInsuredSet.add(tLPInsuredSchema);
			}
			map.put(aLCInsuredSet, "DELETE&INSERT");
			map.put(aLPInsuredSet, "DELETE&INSERT");
		}

		// Address
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();

		LPAddressDB tLPAddressDB = new LPAddressDB();
		LPAddressSet tLPAddressSet = new LPAddressSet();
		tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressSet = tLPAddressDB.query();
		if (tLPAddressSet.size() < 1) {
			CError.buildErr(this, "查询原地址信息失败!");
			return false;
		}
		tRef.transFields(tLCAddressSchema, tLPAddressSet.get(1));
		tLCAddressSchema.setModifyDate(CurrDate);
		tLCAddressSchema.setModifyTime(CurrTime);
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(tLCAddressSchema.getCustomerNo());
		tLCAddressDB.setAddressNo(tLCAddressSchema.getAddressNo());
		if (!tLCAddressDB.getInfo()) {
			CError.buildErr(this, "查询地址信息失败!");
			return false;
		}
		tRef.transFields(tLPAddressSchema, tLCAddressDB.getSchema());
		tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressSchema.setModifyDate(CurrDate);
		tLPAddressSchema.setModifyTime(CurrTime);

		map.put(tLCAddressSchema, "DELETE&INSERT");
		map.put(tLPAddressSchema, "DELETE&INSERT");

		// Account

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
		PEdorACBackConfirmBL pedoracbackconfirmbl = new PEdorACBackConfirmBL();
	}
}
