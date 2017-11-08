package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
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
public class PEdorBCBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorBCBackConfirmBL.class);
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

	public PEdorBCBackConfirmBL() {
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
		logger.debug("=== 受益人回退确认生效 ===");
		Reflections tRef = new Reflections();

		LCBnfSet aLCBnfSet = new LCBnfSet();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LPBnfDB tLPBnfDB = new LPBnfDB();
		LPBnfSet tLPBnfSet = new LPBnfSet();
		tLPBnfDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPBnfDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPBnfSet = tLPBnfDB.query();

		if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
			for (int i = 1; i <= tLPBnfSet.size(); i++) {
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				tRef.transFields(tLCBnfSchema, tLPBnfSet.get(i));
				tLCBnfSchema.setModifyDate(CurrDate);
				tLCBnfSchema.setModifyTime(CurrTime);
				aLCBnfSet.add(tLCBnfSchema);
			}
			map.put(tLPBnfSet, "DELETE");
		}

		LCBnfDB tLCBnfDB = new LCBnfDB();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		tLCBnfDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCBnfSet = tLCBnfDB.query();
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			for (int i = 1; i <= tLCBnfSet.size(); i++) {
				LPBnfSchema tLPBnfSchema = new LPBnfSchema();
				tRef.transFields(tLPBnfSchema, tLCBnfSet.get(i));
				tLPBnfSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPBnfSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPBnfSchema.setModifyDate(CurrDate);
				tLPBnfSchema.setModifyTime(CurrTime);
				aLPBnfSet.add(tLPBnfSchema);
			}
			map.put(tLCBnfSet, "DELETE");
		}
		map.put(aLCBnfSet, "DELETE&INSERT");
		map.put(aLPBnfSet, "DELETE&INSERT");
		
		ValidateEdorData2 mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		//采用新的方式进行保全数据回退
	    String[] chgTables = {"LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured","LCCSpec"};
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
		PEdorBCBackConfirmBL pedorbcbackconfirmbl = new PEdorBCBackConfirmBL();
	}
}
