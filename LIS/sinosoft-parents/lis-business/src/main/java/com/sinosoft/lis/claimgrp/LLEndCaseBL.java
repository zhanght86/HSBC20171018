/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.claimgrp.LLClaimConfirmPassBL;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.vschema.LLCaseSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔结案处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLEndCaseBL
{
private static Logger logger = Logger.getLogger(LLEndCaseBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mMissionID = PubFun.getCurrentTime();
    private String mSubMissionID = PubFun.getCurrentTime();

    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
    private TransferData mTransferData = new TransferData();
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

    private GlobalInput mG = new GlobalInput();
    private String mClmNo = "";
    
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LP0002")) {
			return false;
		}
		return true;
	}

    public LLEndCaseBL()
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
        logger.debug("----------LLEndCaseBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
            return false;
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        
		try
		{
			//锁表 并发控制09-06-06
			boolean tLockFlag =true;
			if (!lockNo(mClmNo)) {
				logger.debug("锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				tLockFlag = false;
				//mPubLock.unLock();
				return false;
			}
			
	        //进行业务处理
	        if (!dealData(cOperate))
	        {
	            return false;
	        }
	        logger.debug("----------after dealData----------");
	        //准备往后台的数据
	        if (!prepareOutputData())
	        {
	            return false;
	        }
	        logger.debug("----------after prepareOutputData----------");
	        PubSubmit tPubSubmit = new PubSubmit();
	        if (!tPubSubmit.submitData(mInputData, cOperate))
	        {
	            // @@错误处理
	            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	            CError tError = new CError();
	            tError.moduleName = "LLEndCaseBL";
	            tError.functionName = "submitData";
	            tError.errorMessage = "数据提交失败!";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
	        mInputData = null;
			mPubLock.unLock();
		}
		
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("--start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

        mClmNo = (String) mTransferData.getValueByName("ClmNo");
        mMissionID = (String) mTransferData.getValueByName("MissionID");
        mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");

        if (mClmNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLEndCaseBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的赔案号为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----");
        boolean tReturn = false;

        //更新纪录
        if (cOperate.equals("UPDATE"))
        {
            //案件核赔表
            LLClaimDB tLLClaimDB = new LLClaimDB();
            tLLClaimDB.setClmNo(mClmNo);
            tLLClaimDB.getInfo();
            mLLClaimSchema = tLLClaimDB.getSchema();
            mLLClaimSchema.setClmState("60"); //赔案状态置为已结案
            mLLClaimSchema.setOperator(mG.Operator);
            //mLLClaimSchema.setEndCaseDate(CurrentDate);
            mLLClaimSchema.setModifyDate(CurrentDate);
            mLLClaimSchema.setModifyTime(CurrentTime);

            //立案表
            LLRegisterDB tLLRegisterDB = new LLRegisterDB();
            tLLRegisterDB.setRgtNo(mClmNo);
            tLLRegisterDB.getInfo();
            mLLRegisterSchema = tLLRegisterDB.getSchema();
            mLLRegisterSchema.setClmState("60"); //赔案状态置为已结案
            mLLRegisterSchema.setEndCaseFlag("1");
            //mLLRegisterSchema.setEndCaseDate(CurrentDate);
            mLLRegisterSchema.setModifyDate(CurrentDate);
            mLLRegisterSchema.setModifyTime(CurrentTime);

            map.put(mLLRegisterSchema, "DELETE&INSERT");
            map.put(mLLClaimSchema, "DELETE&INSERT");
            
            
          //赔案明细表
			String sql1 = " update llclaimpolicy set ClmState = '60',modifydate='"+CurrentDate+"' where"
				+ " clmno = '" + mClmNo + "'";
			
			map.put(sql1, "UPDATE");
			
			// 更新工作流 2005-10-22 14:44 周磊
			updateMyMission();
			
			// 使保单结算,合同处理等生效,并生成财务数据
			LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
			if (!tLLClaimConfirmPassBL.submitData(mInputData, "")) {
				// @@错误处理
				CError.buildErr(this, "结案保存失败,"+tLLClaimConfirmPassBL.mErrors.getLastError());
				mResult.clear();
				mInputData = null;
				return false;
			} 
			else
			{
				mResult = tLLClaimConfirmPassBL.getResult();
				MMap mMMap = new MMap();
				mMMap =(MMap)mResult.getObjectByObjectName("MMap", 0);
				map.add(mMMap);
				mInputData.add(mMMap);
				mMMap=null;
			}
			
			
            //解除保单挂起
            LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
            if (!tLLLcContReleaseBL.submitData(mInputData,""))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLLcContReleaseBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "解除保单挂起失败!";
                this.mErrors .addOneError(tError);
                return false;
            }
            else
            {
                VData tempVData = new VData();
                tempVData = tLLLcContReleaseBL.getResult();
                MMap tMap = new MMap();
                tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
                map.add(tMap);
                tMap=null;
            }

            //更改死亡标志
            if (!dealDeath())
            {
                return false;
            }
        }

        return true;
    }
    
    
    /**
     * 针对死亡案件更改死亡日期和标志
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean dealDeath()
    {
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(mClmNo);
        LLCaseSet tLLCaseSet = tLLCaseDB.query();
        if (tLLCaseSet == null && tLLCaseSet.size() < 1)
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmAfterInitService";
            tError.functionName = "dealData";
            tError.errorMessage = "查询分案信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 1; i <= tLLCaseSet.size(); i++)
        {
            String tCNo = tLLCaseSet.get(i).getCustomerNo();
            LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
            tLLAppClaimReasonDB.setCaseNo(mClmNo);
            tLLAppClaimReasonDB.setRgtNo(mClmNo);
            tLLAppClaimReasonDB.setCustomerNo(tCNo);
            LLAppClaimReasonSet tLLAppClaimReasonSet = tLLAppClaimReasonDB.
                    query();
            if (tLLAppClaimReasonSet == null && tLLAppClaimReasonSet.size() < 1)
            {
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询赔案理赔类型信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++)
            {
                String tCode = tLLAppClaimReasonSet.get(j).getReasonCode().
                               substring(1, 3);
                if (tCode.equals("02")) //死亡
                {
                    //更改立案分案表
                    String sql3 = " update llcase set DieFlag = '1',"
                                  + " DeathDate = AccDate where"
                                  + " CaseNo = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql3, "UPDATE");
                    
                    //更改报案分案表
                    String sql4 = " update LLSubReport set DieFlag = '1',"
                                  + " DieDate = AccDate where"
                                  + " SUBRPTNO = '" + mClmNo + "'"
                                  + " and CustomerNo = '" + tCNo + "'";
                    map.put(sql4, "UPDATE");
                    
                    //更改客户表
                    String sql5 = " update LDPerson set DeathDate = to_date('"
                                  + tLLCaseSet.get(i).getAccDate()
                                  + "','yyyy-mm-dd') where"
                                  + " CustomerNo = '" + tCNo + "'";
                    map.put(sql5, "UPDATE");

                    break;
                }
            }
        }
        return true;
    }

    
	/**
	 * 更改工作流LWMission表中当前节点的相关属性
	 * 
	 * @return boolean
	 */
	private boolean updateMyMission() {
		// 更新LWMission表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID("0000009075");
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.getInfo();
		mLWMissionSchema = tLWMissionDB.getSchema();
		mLWMissionSchema.setMissionProp2("60");
		map.put(mLWMissionSchema, "UPDATE");

		return true;
	}

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLEndCaseBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
