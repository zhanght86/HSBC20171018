/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claim.LLClaimUpdateRptNoBL;
import com.sinosoft.lis.claimgrp.LLReportBL;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAccidentSubSchema;
import com.sinosoft.lis.schema.LLReportRelaSchema;
import com.sinosoft.lis.schema.LLCaseRelaSchema;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.lis.schema.LLReportReasonSchema;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:理赔立案信息提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @todo 1 提交BL层处理的数据
 *       2 修改工作流当前节点属性
 * @version 1.0
 */
public class LLClaimRegisterBLF
{
private static Logger logger = Logger.getLogger(LLClaimRegisterBLF.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 提交数据容器 */
    private MMap map = new MMap();

    private TransferData mTransferData = new TransferData();

    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
    private LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();

    private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

    private String mMissionID;
    private String mSubMissionID;
    private String mActivityID;
    private String mRptorState;
    private String mAccDate;
    /** 增人标志   用来区别是立案保存还是新增出险人*/
    private String mAddPersonFlag;

    public LLClaimRegisterBLF()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug("----------BLF after getInputData----------");
        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------BLF after checkInputData----------");
        //进行业务处理,得到业务处理返回值
        if (!dealData())
        {
            return false;
        }
        logger.debug("----------BLF after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------BLF after prepareOutputData----------");

        //if(cOperate.equals("UPDATE")){
        
        PubSubmit tPubSubmit = new PubSubmit();
	        if (!tPubSubmit.submitData(mInputData, ""))
	        {
	            // @@错误处理
	            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	            CError tError = new CError();
	            tError.moduleName = "LLClaimRegisterBLF";
	            tError.functionName = "submitData";
	            tError.errorMessage = "数据提交失败!";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
        //}
        //mInputData = null;
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("--start getInputData()");

        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData",0);
        mMissionID = (String) mTransferData.getValueByName("MissionID"); //获得当前工作任务的任务ID
        mAddPersonFlag = (String) mTransferData.getValueByName("AddPersonFlag"); //获得当前工作任务的任务ID
        mSubMissionID = (String) mTransferData.getValueByName(
                    "SubMissionID");
        logger.debug("增人标志============"+mAddPersonFlag);
        if (mMissionID == null)
        {
            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBLF";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输数据中的必要参数MissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mSubMissionID == null)
        {
            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBLF";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输数据中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 校验传入的信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        return true;
    }

    /**
     * 提交到BL处理返回处理完数据
     * @return boolean
     */
    private boolean dealData()
    {
        logger.debug("----------BLF dealData BEGIN----------");
        boolean tReturn = false;

        LLClaimRegisterBL tLLClaimRegisterBL = new LLClaimRegisterBL();
        if (!tLLClaimRegisterBL.submitData(mInputData,mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimRegisterBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBLF";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError);
            mResult.clear();
            mInputData = null;
            tReturn = false;
        }
        else
        {
        	logger.debug("-----start BLF getData from BL");
    		
    		//获得返回结果
    		mInputData.clear();
    		mInputData= tLLClaimRegisterBL.getResult();
    			
    		map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
    		mTransferData = null;
    		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
    		mTransferData.setNameAndValue("GrpFlag", "1");
    			
    		logger.debug("BLF getData -- 操作类型:"+mOperate);
    			
    		//先报案,后立案件执行理赔换号处理
    		if(mOperate.equals("INSERT")&&!"1".equals(mAddPersonFlag)){
    			//调用换号处理类执行理赔换号
    			LLClaimUpdateRptNoBL tLLClaimUpdateRptNoBL=new LLClaimUpdateRptNoBL();
    			if (!tLLClaimUpdateRptNoBL.submitData(mInputData, mOperate)) {
    					
    				// @@错误处理
    				this.mErrors.copyAllErrors(tLLClaimUpdateRptNoBL.mErrors);
    				mResult.clear();
    				return false;
    			} 
    			else
    			{
    				//获得处理结果
    				mResult.clear();
    				mResult= tLLClaimUpdateRptNoBL.getResult();
    				mInputData.clear();
        	        mInputData= tLLClaimUpdateRptNoBL.getResult();
    			}
    		}
    		else
    		{
    			//修改记录或新增立案时直接获得处理结果
    			mResult.clear();
    			mResult= tLLClaimRegisterBL.getResult();
    			
    		}
            //更新LWMission
//            if (!updateMyMission())
//            {
//                tReturn = false;
//            }
            //tempVData.clear();
            tReturn = true;
        }
        return tReturn;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            //mInputData.clear();
            //mInputData.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterBLF";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 更改工作流LWMission表中当前节点的相关属性
     * @return boolean
     */
    private boolean updateMyMission()
    {
        //更新LWMission表
        LWMissionDB tLWMissionDB = new LWMissionDB();
        tLWMissionDB.setMissionID(mMissionID);
        tLWMissionDB.setSubMissionID(mSubMissionID);
        tLWMissionDB.setActivityID("0000009015");
        tLWMissionDB.getInfo();
        mLWMissionSchema = tLWMissionDB.getSchema();

        mLWMissionSchema.setMissionProp2(mRptorState);
        mLWMissionSchema.setMissionProp6(mAccDate);

        map.put(mLWMissionSchema, "DELETE&INSERT");

        return true;
    }
}
