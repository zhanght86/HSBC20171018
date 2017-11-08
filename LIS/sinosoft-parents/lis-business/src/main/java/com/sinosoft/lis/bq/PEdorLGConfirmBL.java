package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOReturnLoanSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;


public class PEdorLGConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorLGConfirmBL.class);
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
	
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	
	private GlobalInput mGlobalInput = new GlobalInput();
	
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorLGConfirmBL() {
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
		return true;
	}

	private boolean prepareData() {
		Reflections tRef = new Reflections();


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
			//add by jiaqiangli 财务接口应付凭证数据提取 p表数据由于保全申请与保全确认不在同一时点
			aLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
			//add by jiaqiangli 财务接口应付凭证数据提取 p表数据由于保全申请与保全确认不在同一时点
			
			aLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
			aLCInsureAccTraceSchema.setModifyTime(mCurrentTime);

			aLPInsureAccTraceSet.add(aLPInsureAccTraceSchema);
			aLCInsureAccTraceSet.add(aLCInsureAccTraceSchema);
		}
		mMap.put(aLPInsureAccTraceSet,"DELETE&INSERT");
		mMap.put(aLCInsureAccTraceSet, "DELETE&INSERT");		
		
		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		LPInsureAccClassSet tLPInsureAccClassSet = tLPInsureAccClassDB.query();
		for(int i=1;i<=tLPInsureAccClassSet.size();i++)
		{
			LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			PubFun.copySchema(tLCInsureAccClassSchema, tLPInsureAccClassSet.get(i));
			mMap.put(tLCInsureAccClassSchema,"DELETE&INSERT");
			
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setSchema(tLCInsureAccClassSchema);
			if(!tLCInsureAccClassDB.getInfo())
			{
				return false;
			}
			LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			PubFun.copySchema(tLPInsureAccClassSchema, tLCInsureAccClassDB.getSchema());
			tLPInsureAccClassSchema.setEdorNo(tLPInsureAccClassSet.get(i).getEdorNo());
			tLPInsureAccClassSchema.setEdorType(tLPInsureAccClassSet.get(i).getEdorType());
			mMap.put(tLPInsureAccClassSchema,"DELETE&INSERT");
		}
		
		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		LPInsureAccSet tLPInsureAccSet =tLPInsureAccDB.query();
		for(int i=1;i<=tLPInsureAccSet.size();i++)
		{
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			PubFun.copySchema(tLCInsureAccSchema, tLPInsureAccSet.get(i));
			mMap.put(tLCInsureAccSchema, "DELETE&INSERT");
			
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setSchema(tLCInsureAccSchema);
			if(!tLCInsureAccDB.getInfo())
			{
				return false;
			}
			LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
			PubFun.copySchema(tLPInsureAccSchema, tLCInsureAccDB.getSchema());
			tLPInsureAccSchema.setEdorNo(tLPInsureAccSet.get(i).getEdorNo());
			tLPInsureAccSchema.setEdorType(tLPInsureAccSet.get(i).getEdorType());
			mMap.put(tLPInsureAccSchema, "DELETE&INSERT");
		}
		
		
		LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();
		tLPReturnLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPReturnLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
		tLPReturnLoanSet = tLPReturnLoanDB.query(); 
	    if(tLPReturnLoanSet.size()==1)
	    {
			LPReturnLoanSchema tLPReturnLoanSchema = tLPReturnLoanSet.get(1);
			LOReturnLoanSchema tLOReturnLoanSchema = new LOReturnLoanSchema();
			tRef.transFields(tLOReturnLoanSchema,
					tLPReturnLoanSchema);
			tLOReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
			tLOReturnLoanSchema.setModifyDate(this.mCurrentDate);
			tLOReturnLoanSchema.setModifyTime(this.mCurrentTime);
			//mMap.put(tLPReturnLoanSchema, "DELETE");
			mMap.put(tLOReturnLoanSchema, "DELETE&INSERT");
			
			LOLoanDB rLOLoanDB = new LOLoanDB();
			LOLoanSet rLOLoanSet = new LOLoanSet();
			rLOLoanDB.setEdorNo(tLPReturnLoanSchema.getLoanNo());
			rLOLoanDB.setContNo(tLPReturnLoanSchema.getContNo());
			rLOLoanDB.setLoanType("0");
			rLOLoanDB.setPayOffFlag("0");
			rLOLoanSet = rLOLoanDB.query();
			
			if (rLOLoanSet.size() < 1 || rLOLoanSet == null) {
				mErrors.addOneError(new CError("查询以往借款数据信息失败!"));
				return false;
			}
			
			LOLoanSchema tLOLoanSchema = new LOLoanSchema();
			tLOLoanSchema = rLOLoanSet.get(1);
			LPLoanSchema tLPLoanSchema = new LPLoanSchema();
			tRef.transFields(tLPLoanSchema, tLOLoanSchema);
			tLPLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPLoanSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			mMap.put(tLPLoanSchema, "INSERT");
			
//			tLOLoanSchema.setSumMoney(tLOLoanSchema.getSumMoney()-tLOReturnLoanSchema.getReturnMoney());//减去本息合计
//			tLOLoanSchema.setLeaveMoney(tLOLoanSchema.getLeaveMoney()-tLOReturnLoanSchema.getReturnMoney()
//					-tLOReturnLoanSchema.getReturnInterest());
			tLOLoanSchema.setLeaveMoney(tLOLoanSchema.getLeaveMoney()-tLOReturnLoanSchema.getReturnMoney());
			tLOLoanSchema.setModifyDate(this.mCurrentDate);
			tLOLoanSchema.setModifyTime(this.mCurrentTime);
			mMap.put(tLOLoanSchema, "UPDATE");
	    }
	    else if(tLPReturnLoanSet.size()>1)
	    {
	    	mErrors.addOneError(new CError("抵扣借款信息查询失败！"));
	    	return false;
	    }

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
