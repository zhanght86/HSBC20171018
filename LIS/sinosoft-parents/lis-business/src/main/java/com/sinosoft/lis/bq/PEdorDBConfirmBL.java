package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;


public class PEdorDBConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorDBConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap mMap = new MMap();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;
	
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	
	private GlobalInput mGlobalInput = new GlobalInput();
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorDBConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据操作业务处理
		if (!prepareData()) {
			return false;
		}
		logger.debug("after dealData...");

		return true;
	}
	
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		return true;
	}

	private boolean prepareData() {
		Reflections tRef = new Reflections();

	  	//采用新的方式进行 CP 互换
	    String[] chgTables = {"LCInsureAcc","LCInsureAccClass"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());

		// 账户分类表 lcInsureAcctrace lpInsureAcctrace
		LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
		LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
		tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccTraceDB.setSchema(tLPInsureAccTraceSchema);
		tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
		if (tLPInsureAccTraceSet.size() < 1) {
			CError.buildErr(this, "查询LPInsureAccTrace表失败!");
			return false;
		}
		LCInsureAccTraceSet aLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LPInsureAccTraceSet aLPInsureAccTraceSet = new LPInsureAccTraceSet();
		
		for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
			LCInsureAccTraceSchema aLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			LPInsureAccTraceSchema aLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			aLPInsureAccTraceSchema = tLPInsureAccTraceSet.get(i);
			tRef.transFields(aLCInsureAccTraceSchema, aLPInsureAccTraceSchema);
			aLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
			aLCInsureAccTraceSchema.setMakeTime("00:00:00");
			aLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
			aLCInsureAccTraceSchema.setModifyTime(mCurrentTime);

			aLPInsureAccTraceSet.add(aLPInsureAccTraceSchema);
			aLCInsureAccTraceSet.add(aLCInsureAccTraceSchema);
		}
		mMap.put(aLPInsureAccTraceSet,"DELETE&INSERT");
		mMap.put(aLCInsureAccTraceSet, "DELETE&INSERT");			

		//财务已经放在公共模块里统一处理了

		mResult.clear();
		mResult.add(mMap);
		return true;
	}


	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
