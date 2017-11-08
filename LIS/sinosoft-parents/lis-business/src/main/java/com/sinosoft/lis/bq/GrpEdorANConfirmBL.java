package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.lis.vschema.LCGrpAppntSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LPGrpAppntSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全投保单位信息变更处理
 * </p>
 * 
 * <p>
 * Description: 团体保全投保单位信息变更保全确认类
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
 * @CreateDate 2005-11-18
 * @version 1.0
 */
public class GrpEdorANConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorANConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorANConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp AN Confirm----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("aftergetInputData Grp AN:::");

		if (!checkData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		logger.debug("afterdealDate Grp AN:::");

		// 数据准备操作（dealData())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||AN");

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}
		return true;
	}

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return true;
	}

	private boolean dealData() {
		Reflections tRef = new Reflections();

		String AppntNo = "";
		String AddressNo = "";

		LCGrpContSet aLCGrpContSet = new LCGrpContSet();
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();
		LPGrpAppntSet aLPGrpAppntSet = new LPGrpAppntSet();

		LCGrpContSchema tLCGrpContSchema;
		LPGrpContSchema tLPGrpContSchema;
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		LPGrpContSet tLPGrpContSet = new LPGrpContSet();
		tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContSet = tLPGrpContDB.query();
		if (tLPGrpContSet.size() < 1) {
			CError.buildErr(this, "查询变更团体保单信息失败!");
			return false;
		}
		for (int i = 1; i <= tLPGrpContSet.size(); i++) {
			tLCGrpContSchema = new LCGrpContSchema();
			tRef.transFields(tLCGrpContSchema, tLPGrpContSet.get(i));
			aLCGrpContSet.add(tLCGrpContSchema);
			AppntNo = tLCGrpContSchema.getAppntNo();
			AddressNo = tLCGrpContSchema.getAddressNo();

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tLPGrpContSet.get(i).getGrpContNo());
			if (!tLCGrpContDB.getInfo()) {
				CError.buildErr(this, "查询保全集体保单表信息失败!");
				return false;
			}
			tLPGrpContSchema = new LPGrpContSchema();
			tRef.transFields(tLPGrpContSchema, tLCGrpContDB.getSchema());
			tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpContSchema.setOperator(mGlobalInput.Operator);
			tLPGrpContSchema.setModifyDate(CurrDate);
			tLPGrpContSchema.setModifyTime(CurrTime);
			aLPGrpContSet.add(tLPGrpContSchema);

			LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
			LPGrpAppntSchema tLPGrpAppntSchema = new LPGrpAppntSchema();
			LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
			tLPGrpAppntDB.setGrpContNo(tLPGrpContSet.get(i).getGrpContNo());
			tLPGrpAppntDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAppntDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			if (!tLPGrpAppntDB.getInfo()) {
				CError.buildErr(this, "查询变更团单投保人表失败!");
				return false;
			}
			tRef.transFields(tLCGrpAppntSchema, tLPGrpAppntDB.getSchema());
			aLCGrpAppntSet.add(tLCGrpAppntSchema);
			LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
			tLCGrpAppntDB.setGrpContNo(tLPGrpContSet.get(i).getGrpContNo());
			if (!tLCGrpAppntDB.getInfo()) {
				CError.buildErr(this, "查询保全团单投保人表信息失败!");
				return false;
			}
			tRef.transFields(tLPGrpAppntSchema, tLCGrpAppntDB.getSchema());
			tLPGrpAppntSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAppntSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpAppntSchema.setOperator(mGlobalInput.Operator);
			tLPGrpAppntSchema.setModifyDate(CurrDate);
			tLPGrpAppntSchema.setModifyTime(CurrTime);
			aLPGrpAppntSet.add(tLPGrpAppntSchema);
		}
		// mMap.put(aLCGrpContSet, "DELETE&INSERT");
		mMap.put(aLCGrpContSet, "UPDATE");
		mMap.put(aLPGrpContSet, "DELETE&INSERT");
		mMap.put(aLCGrpAppntSet, "DELETE&INSERT");
		mMap.put(aLPGrpAppntSet, "DELETE&INSERT");

		LDGrpSchema tLDGrpSchema = new LDGrpSchema();
		LPGrpSchema tLPGrpSchema = new LPGrpSchema();
		LPGrpDB tLPGrpDB = new LPGrpDB();
		tLPGrpDB.setCustomerNo(AppntNo);
		tLPGrpDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpDB.getInfo()) {
			CError.buildErr(this, "查询更新团体客户表信息失败!");
			return false;
		}
		tRef.transFields(tLDGrpSchema, tLPGrpDB.getSchema());
		mMap.put(tLDGrpSchema, "DELETE&INSERT");
		LDGrpDB tLDGrpDB = new LDGrpDB();
		tLDGrpDB.setCustomerNo(AppntNo);
		if (!tLDGrpDB.getInfo()) {
			CError.buildErr(this, "查询团体客户表失败!客户号：" + AppntNo);
			return false;
		}
		tRef.transFields(tLPGrpSchema, tLDGrpDB.getSchema());
		tLPGrpSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpSchema.setOperator(mGlobalInput.Operator);
		tLPGrpSchema.setModifyDate(CurrDate);
		tLPGrpSchema.setModifyTime(CurrTime);
		mMap.put(tLPGrpSchema, "DELETE&INSERT");

		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
		LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();
		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		tLPGrpAddressDB.setCustomerNo(AppntNo);
		tLPGrpAddressDB.setAddressNo(AddressNo);
		tLPGrpAddressDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAddressDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpAddressDB.getInfo()) {
			CError.buildErr(this, "查询更新团体客户表信息失败!");
			return false;
		}
		tRef.transFields(tLCGrpAddressSchema, tLPGrpAddressDB.getSchema());
		mMap.put(tLCGrpAddressSchema, "DELETE&INSERT");
		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setCustomerNo(AppntNo);
		tLCGrpAddressDB.setAddressNo(AddressNo);
		if (!tLCGrpAddressDB.getInfo()) {
			CError.buildErr(this, "查询团单客户地址信息失败!");
			return false;
		}
		tRef.transFields(tLPGrpAddressSchema, tLCGrpAddressDB.getSchema());
		tLPGrpAddressSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAddressSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpAddressSchema.setOperator(mGlobalInput.Operator);
		tLPGrpAddressSchema.setModifyDate(CurrDate);
		tLPGrpAddressSchema.setModifyTime(CurrTime);
		mMap.put(tLPGrpAddressSchema, "DELETE&INSERT");

		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}
}
