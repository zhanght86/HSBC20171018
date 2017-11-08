/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.certify.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 投保业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class WaitReasonInputBL {
private static Logger logger = Logger.getLogger(WaitReasonInputBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
    private String mOperate;
    
    /** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData(); 
    private Reflections mReflections = new Reflections();
    private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
    private LBMissionSchema mLBMissionSchema = new LBMissionSchema();
    private String mWaitReason;
    private String mSubMissionID;
    private String mMissionID;
    private String mContent;
    private String mContNo;
    private String mUniteNo;
    //add by lzf 
    private LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
    private LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
   
    public WaitReasonInputBL() {
    }

    public static void main(String[] args) {
       
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) {
            return false;
        }

        //数据操作校验
        if (!checkData()) {
            return false;
        }
        
      //get lock
        //为了防止死锁，要把被等待的投保单也进行加锁
      //同时对正在等待的投保单也进行加锁
        String[] lockNo = new String[1];
        //lockNo[0] = mContNo;
        //lockNo[1] = mLWMissionSchema.getMissionProp2();        
        lockNo[0] = mLWMissionSchema.getMissionProp2();
        logger.debug("**********加锁："+lockNo[0]);
        
		if (!CheckLock(lockNo))
		{
			logger.debug("加锁失败!");
			CError.buildErr(this, "该投保单正在被其他人员操作，请您稍后再试!");
			this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
			return false;
		}
		
		try
		{
			//进行业务处理
	        if (!dealData1()) {           
	            return false;
	        }      

	        //准备往后台的数据
	        if (!prepareOutputData()) {
	            return false;
	        }
	        
	        WaitReasonInputBLS tWaitReasonInputBLS = new WaitReasonInputBLS();
	        tWaitReasonInputBLS.submitData(mResult, cOperate);

	        //如果有需要处理的错误，则返回
	        if (tWaitReasonInputBLS.mErrors.needDealError()) {
	            // @@错误处理
	            this.mErrors.copyAllErrors(tWaitReasonInputBLS.mErrors);
	            CError.buildErr(this, "数据提交失败!");
	            return false;
	        }      
	        
		} catch(Exception e)
		{		
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;			
		} finally {
			//解锁
			// 使用新的加锁逻辑
			mPubLock.unLock();
		}  
		
        return true;
    }   

    /**
     * 数据处理校验
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean checkData() {

    	// 获得当前工作任务的任务ID
		mWaitReason = (String) mTransferData.getValueByName("WaitReason");
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mContent = (String) mTransferData.getValueByName("Content");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mUniteNo = (String) mTransferData.getValueByName("UniteNo");
		
		if (mMissionID == null || mMissionID.equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数MissionID失败!");
			return false;
		}
		if (mSubMissionID == null || mSubMissionID.equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
			return false;
		}
		if (mContNo == null || mContNo.equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据TransferData中的必要参数SubMissionID失败!");
			return false;
		}
		//==================
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select activityid from lwactivity where functionid='10010028'");
		String mActivityID = new ExeSQL().getOneValue(sqlbv1);
		LWMissionDB tLWMissionDB = new LWMissionDB();
//		tLWMissionDB.setActivityID("0000001100");
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "查询lwmission表出错!");
			return false;
		}		
		//=================ln 2008-10-10 modify		
		mLWMissionSchema = tLWMissionSet.get(1);
		
        return true;
    }
    
    /**
     * 根据前面的输入数据，进行逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData1() {
    	
    	//back 备份		
//		 String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//		 mReflections.transFields(mLBMissionSchema,mLWMissionSchema);
//		 mLBMissionSchema.setSerialNo(tSerielNo);
//		 //mLBMissionSchema.setActivityStatus("3");//节点任务执行完毕
//		 mLBMissionSchema.setLastOperator(mGlobalInput.Operator);
//		 mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//		 mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//		
		//update
		mLWMissionSchema.setMissionProp21(mUniteNo);
		mLWMissionSchema.setMissionProp18("6");//核保状态为核保等待
		mLWMissionSchema.setMissionProp8(PubFun.getCurrentDate());//核保状态改变日期
		mLWMissionSchema.setMissionProp9(PubFun.getCurrentTime());//核保状态改变时间
		mLWMissionSchema.setMissionProp22(mWaitReason);//核保等待原因
		mLWMissionSchema.setMissionProp23(mContent);//核保员说明		
		mLWMissionSchema.setLastOperator(mGlobalInput.Operator);
		mLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		mLWMissionSchema.setModifyTime(PubFun.getCurrentTime());							
		
		//更新LCCUWMaster表的uwsate字段为6
		LCCUWMasterDB tLCCUWMasterDB=new LCCUWMasterDB();	
		
		tLCCUWMasterDB.setContNo(mContNo);
		if(!tLCCUWMasterDB.getInfo()){
			// @@错误处理
				CError.buildErr(this, "LCCUWMaster表取数失败!");
				return false;
		}		
		 tLCCUWMasterSchema=tLCCUWMasterDB.getSchema();
		 tLCCUWMasterSchema.setUWState("6");
		 tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	
		 //更新LCCUWSub表的uwstate字段为6
		 LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		
		 tLCCUWSubDB.setContNo(mContNo);
		 tLCCUWSubDB.setUWNo(tLCCUWMasterSchema.getUWNo());
         if(!tLCCUWSubDB.getInfo()){
        	// @@错误处理
 			CError.buildErr(this, "LCCUWSub表取数失败!");
 			return false;
		   }
         tLCCUWSubSchema= tLCCUWSubDB.getSchema();
		
		 tLCCUWSubSchema.setUWState("6");
		 tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
	
    	//check after get lock
//    	String tSql="select * from lwmission where activityid='0000001100' "
//    		+ " and missionid='" + mMissionID + "' and submissionid='" + mSubMissionID + "' "
//    		+ " and missionprop18 in ('1','2','3')" ;
    	String tSql="select * from lwmission where activityid in (select activityid from lwactivity where functionid='10010028') "
        		+ " and missionid='" + "?missionid?" + "' and submissionid='" + "?submissionid?" + "' "
        		+ " and exists(select 1 from lccuwmaster where contno = lwmission.missionprop2 and uwstate in('1','2','3'))" ;
    	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    	sqlbv1.sql(tSql);
    	sqlbv1.put("missionid", mMissionID);
    	sqlbv1.put("submissionid", mSubMissionID);
    	LWMissionDB tLWMissionDB = new LWMissionDB();
    	LWMissionSet tLWMissionSet =  tLWMissionDB.executeQuery(sqlbv1);
		if (tLWMissionDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError.buildErr(this, "工作流表查询失败!");
			return false;
		}
		if (tLWMissionSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "投保单核保状态已经改变且不符合核保等待的条件!");
			return false;
		}
    	
    	//back 备份		
//		 String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
//		 mReflections.transFields(mLBMissionSchema,mLWMissionSchema);
//		 mLBMissionSchema.setSerialNo(tSerielNo);
//		 //mLBMissionSchema.setActivityStatus("3");//节点任务执行完毕
//		 mLBMissionSchema.setLastOperator(mGlobalInput.Operator);
//		 mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
//		 mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
//		
		//update
		mLWMissionSchema.setMissionProp21(mContNo);
		mLWMissionSchema.setMissionProp18("6");//核保状态为核保等待
		mLWMissionSchema.setMissionProp8(PubFun.getCurrentDate());//核保状态改变日期
		mLWMissionSchema.setMissionProp9(PubFun.getCurrentTime());//核保状态改变时间
		mLWMissionSchema.setMissionProp22(mWaitReason);//核保等待原因
		mLWMissionSchema.setMissionProp23(mContent);//核保员说明		
		mLWMissionSchema.setLastOperator(mGlobalInput.Operator);
		mLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		mLWMissionSchema.setModifyTime(PubFun.getCurrentTime());							
		
		//更新LCCUWMaster表的uwsate字段为6
		LCCUWMasterDB tLCCUWMasterDB=new LCCUWMasterDB();	
		
		tLCCUWMasterDB.setContNo(mContNo);
		if(!tLCCUWMasterDB.getInfo()){
			// @@错误处理
				CError.buildErr(this, "LCCUWMaster表取数失败!");
				return false;
		}		
		tLCCUWMasterSchema=tLCCUWMasterDB.getSchema();
		 tLCCUWMasterSchema.setUWState("6");
		 tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		 logger.debug("tLCCUWMasterSchema.getUWNo()==="+tLCCUWMasterSchema.getUWNo());
		 //更新LCCUWSub表的uwstate字段为3
		 LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		
		 tLCCUWSubDB.setContNo(mContNo);
		 tLCCUWSubDB.setUWNo(tLCCUWMasterSchema.getUWNo());
         if(!tLCCUWSubDB.getInfo()){
        	// @@错误处理
 			CError.buildErr(this, "LCCUWSub表取数失败!");
 			return false;
		   }
         tLCCUWSubSchema= tLCCUWSubDB.getSchema();
		
		 tLCCUWSubSchema.setUWState("6");
		 tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		 tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
        return true;
    }   
    
    /**并发控制
	 * 校验是否可以进行操作
	 */
	private boolean CheckLock(String[] ContNo) {

		if (!mPubLock.lock(ContNo, "LC0004" ,"AutoLock")) {
			return false;
		}
		return true;
	}

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
       
    	// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}    			

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() {
    	mResult.clear();

		// 修改相关工作流同步执行完毕的任务节点表数据
		if (mLWMissionSchema != null) {
			mResult.add(mLWMissionSchema);
		}

		// 添加相关工作流同步执行完毕的任务节点备份表数据
		if (mLBMissionSchema != null) {
			mResult.add(mLBMissionSchema);
		}		
// add by lzf
		if(tLCCUWMasterSchema != null){
			mResult.add(tLCCUWMasterSchema);
		}
		if(tLCCUWSubSchema != null){
			mResult.add(tLCCUWSubSchema);
		}
		return true;
    }

    public VData getResult() {
        return mResult;
    }
    
}
