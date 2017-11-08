package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCAccountSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPAccountSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 投保人变更保全回退处理确认类
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
public class PEdorAEBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorAEBackConfirmBL.class);
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

	public PEdorAEBackConfirmBL() {
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
		logger.debug("=== 投保人变更回退确认生效 ===");
		Reflections tRef = new Reflections();



		// 帐户表
		LCAccountSet tLCAccountSet = new LCAccountSet();
		LPAccountSet tLPAccountSet = new LPAccountSet();
		LPAccountSchema tLPAccountSchema = new LPAccountSchema();
		tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPAccountDB tLPAccountDB = new LPAccountDB();
		tLPAccountDB.setSchema(tLPAccountSchema);
		tLPAccountSet = tLPAccountDB.query();
		if (tLPAccountSet.size() > 0) {
			for (int j = 1; j <= tLPAccountSet.size(); j++) {
				LPAccountSchema aLPAccountSchema = tLPAccountSet.get(j);
				LCAccountSchema tLCAccountSchema = new LCAccountSchema();
				tLPAccountSchema = new LPAccountSchema();

				LCAccountDB aLCAccountDB = new LCAccountDB();
				aLCAccountDB.setCustomerNo(aLPAccountSchema.getCustomerNo());
				aLCAccountDB.setBankAccNo(aLPAccountSchema.getBankAccNo());
				aLCAccountDB.setBankCode(aLPAccountSchema.getBankCode());
				aLCAccountDB.setAccName(aLPAccountSchema.getAccName());
				if (aLCAccountDB.getInfo()) {
					LCAccountSchema aLCAccountSchema = aLCAccountDB.getSchema();
					// tRef.transFields(tLPAccountSchema, aLCAccountSchema);
					// tLPAccountSchema.setEdorNo(aLPAccountSchema.getEdorNo());
					// tLPAccountSchema.setEdorType(aLPAccountSchema.getEdorType());

					// 转换成保单个人信息。
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					// map.put(tLPAccountSchema, "UPDATE");
					map.put(tLCAccountSchema, "UPDATE");
				} else {
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					logger.debug(tLCAccountSchema.encode());
					tLCAccountSchema.setMakeDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					map.put(tLCAccountSchema, "INSERT");
					// map.put(aLPAccountSchema, "DELETE");
				}
			}
		}


		ValidateEdorData2 mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		//采用新的方式进行保全数据回退
	    String[] chgTables = {"LDPerson","LCInsureAcc","LCInsureAccClass","LCInsureAccFee","LCInsureAccClassFee","LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured","LCCSpec"};
	    mValidateEdorData.backConfirmData(chgTables);
	    map.add(mValidateEdorData.getMap());
	    //处理保费表
	    map.add(BqNameFun.DealPrem4BackConfirm(mLPEdorItemSchema));
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
		PEdorAEBackConfirmBL pedoraebackconfirmbl = new PEdorAEBackConfirmBL();
	}
}
