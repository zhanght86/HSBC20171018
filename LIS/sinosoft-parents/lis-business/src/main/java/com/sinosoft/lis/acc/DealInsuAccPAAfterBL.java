package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:投连后续处理账户赎回AR实现
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:sinosoft
 * </p>
 * 
 * @author:ck
 * @version 1.0
 */
public class DealInsuAccPAAfterBL extends DealInsuAccAfter {
private static Logger logger = Logger.getLogger(DealInsuAccPAAfterBL.class);
	public DealInsuAccPAAfterBL() {
	}

	private VData mResult = new VData();

	/** 传出数据的容器 */
	public LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();

	private LOPolAfterDealSchema _LOPolAfterDealSchema = new LOPolAfterDealSchema();

	Reflections tReflections = new Reflections();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	
    private LCInsureAccDB mLCInsureAccDB = new LCInsureAccDB();
    
    private LCInsureAccClassDB mLCInsureAccclassDB = new LCInsureAccClassDB();
    
    private LPInsureAccSet _LPInsureAccSet = new LPInsureAccSet();
    
    private LPInsureAccClassSet _LPInsureAccClassSet = new LPInsureAccClassSet();
    
    private LPInsureAccTraceSet _LPInsureAccTraceSet = new LPInsureAccTraceSet();
    
    private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();
    
    private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
    
    private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();

	private GlobalInput mGlobalInput = new GlobalInput();
	
	private MMap map = new MMap();

	public String mCurrentDate = PubFun.getCurrentDate();

	public String mCurrentTime = PubFun.getCurrentTime();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/* 根据LOPolAfterDeal表中的账户信息进行处理 */
	public boolean dealAfter(GlobalInput tGlobalInput,
			LOPolAfterDealSchema tLOPolAfterDealSchema) {
		/* PA后续处理逻辑 */
		String _DealDate = tLOPolAfterDealSchema.getDealDate();
		mGlobalInput = tGlobalInput;


		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(tLOPolAfterDealSchema.getAccAlterNo());
		tLPEdorItemDB.setEdorType(tLOPolAfterDealSchema.getBusyType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() == 0) {
			CError.buildErr(this, "批改项目信息查询失败!");
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "保全受理信息查询失败!");
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		//////////////////////标记处理\\\\\\\\\\\\\\\\\\\\\\\\\\
		_LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
		_LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
		_LOPolAfterDealSchema.setState("2");// 手续处理完成
		map.put(_LOPolAfterDealSchema, "UPDATE");
		//\\\\\\\\\\\\\\\\\\\\标记完成///////////////////////
		
		///////////////////////检查是否需要扣除手续费\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	 	LCPolSchema mainLCPolSchema = new LCPolSchema();
	 	LCPolDB mainLCPolDB = new LCPolDB();
	 	mainLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
	 	mainLCPolSchema = mainLCPolDB.query().get(1);
	 	TLbqForFee tTLbqForFee =new TLbqForFee();
	 	double douCalFee = -1;	 	
	 	try {
				douCalFee = tTLbqForFee.GetCalFee(0,mLPEdorItemSchema.getEdorNo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String CalFee= "";
			if(douCalFee == 0)
			{
				CalFee = "变更无手续费";
				logger.debug(CalFee);
				VData tVData = new VData();
				tVData.add(map);
				PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(tVData, "")) {
					CError.buildErr(this, "批单号为"+mLPEdorItemSchema.getEdorNo()+"的保单后续处理更新数据失败!");
					return false;
				}
				return true;
			}	
			CalFee = "变更手续费为"+String.valueOf(douCalFee)+"元";
			logger.debug(CalFee);
		//\\\\\\\\\\\\\\\\\\\\\检查完毕////////////////////////////////////////
		
		//定义总帐户价值
		double mainmoney = 0;
			
			
		////////////读取LCInsureAcc，LCInsureAccClass，在LP表中备份\\\\\\\\\\\\\
			//处理Acc
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();	
			mLCInsureAccDB.setPolNo(mainLCPolSchema.getPolNo());
			tLCInsureAccSet = mLCInsureAccDB.query();
			
			for(int i=1;i<=tLCInsureAccSet.size();i++)
			{
				LPInsureAccSchema lzLPInsureAccSchema = new LPInsureAccSchema();
				tReflections.transFields(lzLPInsureAccSchema,tLCInsureAccSet.get(i));
				lzLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				lzLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				lzLPInsureAccSchema.setMakeDate(mCurrentDate);
				lzLPInsureAccSchema.setMakeTime(mCurrentTime);
				lzLPInsureAccSchema.setModifyDate(mCurrentDate);
				lzLPInsureAccSchema.setModifyTime(mCurrentTime);
				_LPInsureAccSet.add(lzLPInsureAccSchema);
			}
			
			//处理Class
			LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();				
			mLCInsureAccclassDB.setPolNo(mainLCPolSchema.getPolNo());			
			tLCInsureAccClassSet = mLCInsureAccclassDB.query();			
			
			for(int i=1;i<=tLCInsureAccClassSet.size();i++)
			{
				
				LPInsureAccClassSchema lzLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tReflections.transFields(lzLPInsureAccClassSchema,tLCInsureAccClassSet.get(i));
				lzLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				lzLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				lzLPInsureAccClassSchema.setMakeDate(mCurrentDate);
				lzLPInsureAccClassSchema.setMakeTime(mCurrentTime);
				lzLPInsureAccClassSchema.setModifyDate(mCurrentDate);
				lzLPInsureAccClassSchema.setModifyTime(mCurrentTime);
				_LPInsureAccClassSet.add(lzLPInsureAccClassSchema);
				//累加账户价值
				mainmoney = mainmoney+ tLCInsureAccClassSet.get(i).getInsuAccBala();
			}			
            
			
		//\\\\\\\\\\\\\\\\\\\\\\备份处理完毕///////////////////////////////////
			//////////////////////////////判断保单状态和帐户余额\\\\\\\\\\\\\\\\\\\\\\\\
			if(mainmoney<25)
			{
				LCPolSchema lzLCPolSchema= new LCPolSchema();
				LCPolDB lzLCPolDB = new LCPolDB();
				lzLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
				lzLCPolSchema = lzLCPolDB.query().get(1);
				if(lzLCPolSchema.getAppFlag().equals("4"))
				{
					
					//////////////////////标记处理\\\\\\\\\\\\\\\\\\\\\\\\\\
					_LOPolAfterDealSchema = tLOPolAfterDealSchema.getSchema();
					_LOPolAfterDealSchema.setDealDate(PubFun.getCurrentDate());
					_LOPolAfterDealSchema.setState("2");// 手续处理完成
					map.put(_LOPolAfterDealSchema, "UPDATE");
					//\\\\\\\\\\\\\\\\\\\\标记完成///////////////////////
					if (!updateAccInfo()) {
						CError.buildErr(this, "批单号为"+mLPEdorItemSchema.getEdorNo()+"的保单后续处理更新数据失败!");
						return false;
					}
					return true;
				}
				else
				{
					CError.buildErr(this, "保单号为"+mLPEdorItemSchema.getContNo()+"的保单后续处理帐户余额不足!");
					return false;
				}
				
			}
			//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\
			map.put(_LPInsureAccSet, "DELETE&INSERT");
			map.put(_LPInsureAccClassSet, "DELETE&INSERT"); 
			String tLimit = PubFun.getNoLimit(mainLCPolSchema.getManageCom());	
	    /////////////插入LPInsureAccTrace,LCInsureAccTrace更新LCInsureAcc，LCInsureAccClass\\\\\\\\\
		//循环LCInsureAccClass
		for(int i=1;i<=tLCInsureAccClassSet.size();i++)
		{
			if(tLCInsureAccClassSet.get(i).getInsuAccBala()==0)
			{
				continue;
			}
			LPInsureAccTraceSchema lzLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			tReflections.transFields(lzLPInsureAccTraceSchema, tLCInsureAccClassSet.get(i));
			lzLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			lzLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());			
			String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			lzLPInsureAccTraceSchema.setSerialNo(SerialNo);
			lzLPInsureAccTraceSchema.setMoneyType("SX");
			lzLPInsureAccTraceSchema.setBusyType("PA");
			lzLPInsureAccTraceSchema.setMoney(-tLCInsureAccClassSet.get(i).getInsuAccBala()*douCalFee/mainmoney);
			lzLPInsureAccTraceSchema.setState("1");//问题问题！！
			lzLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
			lzLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
			lzLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
			lzLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
			lzLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
			lzLPInsureAccTraceSchema.setAccAlterType("3");
			lzLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
			lzLPInsureAccTraceSchema.setFeeCode("000000");
			lzLPInsureAccTraceSchema.setUnitCount(-tLCInsureAccClassSet.get(i).getUnitCount()*douCalFee/mainmoney);
			lzLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			lzLPInsureAccTraceSchema.setOtherType("2");
			
			LCInsureAccTraceSchema lzLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tReflections.transFields(lzLCInsureAccTraceSchema,lzLPInsureAccTraceSchema);
			lzLCInsureAccTraceSchema.setShouldValueDate(mCurrentDate);
			lzLCInsureAccTraceSchema.setValueDate(mCurrentDate);
			lzLCInsureAccTraceSchema.setState("1");//问题问题！！！
					
			_LPInsureAccTraceSet.add(lzLPInsureAccTraceSchema)	;
			_LCInsureAccTraceSet.add(lzLCInsureAccTraceSchema)	;
		}
		map.put(_LPInsureAccTraceSet, "DELETE&INSERT");
		map.put(_LCInsureAccTraceSet, "DELETE&INSERT");
//		更新LCInsureAccClass，LCInsureAcc	
		_LCInsureAccSet = PubInsuAccFun.createAccByTrace(_LCInsureAccTraceSet);	
		_LCInsureAccClassSet = PubInsuAccFun.createAccClassByTrace(_LCInsureAccTraceSet);
		map.put(_LCInsureAccSet, "UPDATE");
		map.put(_LCInsureAccClassSet, "UPDATE");		
		//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\处理完毕完毕////////////////////////////////////
			
			
		
			if (!updateAccInfo()) {
				CError.buildErr(this, "批单号为的"+mLPEdorItemSchema.getEdorNo()+"保单后续处理更新数据失败!");
				return false;
			}
		

		
		return true;
	}

	/* 更新账户信息 */
	public boolean updateAccInfo() {
		VData tVData = new VData();

		// 准备公共提交数据
		if (map != null && map.keySet().size() > 0)
			tVData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			return false;
		}
		return true;
	}


		

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}
}
