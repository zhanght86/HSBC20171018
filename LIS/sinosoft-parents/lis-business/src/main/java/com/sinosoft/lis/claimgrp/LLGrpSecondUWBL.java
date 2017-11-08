package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
 /**
 * <p>Title: 提起二次核保</p>
 * <p>Description: 提起二次核保 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author zhangxing
 * @version 1.0
 */

public class LLGrpSecondUWBL
{
private static Logger logger = Logger.getLogger(LLGrpSecondUWBL.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 *//** 往工作流引擎中传输数据的容器 */
    private VData mResult = new VData();
    private GlobalInput mG = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private LLCUWBatchSet mLLCUWBatchSet = new LLCUWBatchSet();
    private LLCUWBatchSchema mLLCUWBatchSchema = new LLCUWBatchSchema();
    private MMap map = new MMap();
    /** 数据操作字符串 */
    private String mOperator;
    private String mManageCom;
    private String mOperate;
    private String mCurrentDate=PubFun.getCurrentDate();
    private String mCurrentTime=PubFun.getCurrentTime();
    public LLGrpSecondUWBL()
    {
    }
    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
            {return false;}

        //校验是否有未打印的体检通知书
        if (!checkData())
           { return false;}

        logger.debug("Start  dealData...");

        //进行业务处理
        if (!dealData())
           { return false;}

        logger.debug("dealData successful!");

        //准备往后台的数据
        if (!prepareOutputData())
            {return false;}

        logger.debug("Start  Submit...");
         return true;
    }

    /**
     * 校验业务数据
     * @return
     */
    private boolean checkData()
    {

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象
        //获得全局公共数据
        mG=(GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mLLCUWBatchSet = (LLCUWBatchSet) cInputData.
                               getObjectByObjectName("LLCUWBatchSet", 0);
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
       // mInputData = cInputData;
        if (mG == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLGrpSecondUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        mOperator = mG.Operator;
        if (mOperator == null || mOperator.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLGrpSecondUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据mOperator失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得管理机构编码
        mManageCom = mG.ManageCom;
        if (mManageCom == null || mManageCom.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLGrpSecondUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据mOperator失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }


    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
      if(!prepareLLCUW())
        {
            return false;
        }
       return true;
    }

    private boolean prepareLLCUW()
    {
        //生成批次号<方法待定>
        //logger.debug(mLLCUWBatchSet.size());
        String tLimit = PubFun.getNoLimit(mG.ManageCom);
        String tBatNo =PubFun1.CreateMaxNo("CaseNo", tLimit);
        logger.debug("------生成批次号--tBatNo---------"+tBatNo);

        for(int j=1;j<=mLLCUWBatchSet.size();j++)
        {
            //查询 集体合同号码、总单投保单号码，
            LCContDB tLCContDB = new LCContDB();
            tLCContDB.setContNo(mLLCUWBatchSet.get(j).getContNo());
            if(!tLCContDB.getInfo())
            {
                CError tError = new CError();
                tError.moduleName = "LCContDB";
                tError.functionName = "prepareLLCUW";
                tError.errorMessage = "前台传输全局公共数据ContNo失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
             mLLCUWBatchSet.get(j).setGrpContNo(tLCContDB.getGrpContNo());//集体合同号码
             mLLCUWBatchSet.get(j).setProposalContNo(tLCContDB.getProposalContNo());//总单投保单号码


             //查询合同是否与当前赔案相关，相关则标志(ClaimRelFlag=0--相关;ClaimRelFlag=1---不相关)
//            String strSQL="select contno from llclaimpolicy where 1=1"
//                          +" and clmno='"+mLLCUWBatchSet.get(j).getCaseNo() +"'"
//                          +" and contno='"+mLLCUWBatchSet.get(j).getContNo() +"'";
            LLClaimPolicyDB tLLClaimPolicyDB=new LLClaimPolicyDB();
            LLClaimPolicySet tLLClaimPolicySet=new LLClaimPolicySet();
            tLLClaimPolicyDB.setCaseNo(mLLCUWBatchSet.get(j).getCaseNo());
            tLLClaimPolicyDB.setContNo(mLLCUWBatchSet.get(j).getContNo());
            tLLClaimPolicySet.set(tLLClaimPolicyDB.query());
            if(tLLClaimPolicySet==null ||tLLClaimPolicySet.size()==0)
            {
                mLLCUWBatchSet.get(j).setClaimRelFlag("1");//不相关标志
            }
            else
            {
                mLLCUWBatchSet.get(j).setClaimRelFlag("0");//相关标志
            }

            ////赔案号、被保人客户号、合同号码 由前台传入
            mLLCUWBatchSet.get(j).setBatNo(tBatNo);//批次号
            mLLCUWBatchSet.get(j).setUWNo(1);//核保顺序号
            mLLCUWBatchSet.get(j).setState("0");//状态
            mLLCUWBatchSet.get(j).setOperator(mG.Operator);
            mLLCUWBatchSet.get(j).setMakeDate(mCurrentDate);
            mLLCUWBatchSet.get(j).setMakeTime(mCurrentTime);
            mLLCUWBatchSet.get(j).setModifyDate(mCurrentDate);
            mLLCUWBatchSet.get(j).setModifyTime(mCurrentTime);
            mLLCUWBatchSet.get(j).setManageCom(mG.ManageCom);
        }
        map.put(mLLCUWBatchSet,"INSERT");
        mTransferData.removeByName("BatNo");
        mTransferData.setNameAndValue("BatNo",tBatNo);//批次号

        return true;
    }

    private boolean prepareNodeData()
    {
        //修改及判断工作任务生成方法 0---相关，1---无关，2----都存在
        String m = "0"; //判断是否存在 0---相关，[m=0不存在；m=1不存在]
        String n = "0"; //判断是否存在 1---无关，[n=0不存在；n=1不存在]
        String tClaimRelFlag = ""; //赔案相关标志
        for (int j = 1; j <= mLLCUWBatchSet.size(); j++)
        {
            if (mLLCUWBatchSet.get(j).getClaimRelFlag() == "0") {m ="1";}
            if (mLLCUWBatchSet.get(j).getClaimRelFlag() == "1") {n ="1";}
        }
        if (m == "1" && n == "0")
        { //
            tClaimRelFlag = "0"; ////只有0---相关的合同号
        }
        else if (m == "0" && n == "1")
        { //
            tClaimRelFlag = "1"; //只有1---无关的合同号
        }
        else if (m == "1" && n == "1")
        { //
            tClaimRelFlag = "2"; //“0---相关”“1---无关”都存在
        }
        else
        {
            CError tError = new CError();
            tError.moduleName = "LLGrpSecondUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输数据相关标志失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mTransferData.removeByName("ClaimRelFlag");
        mTransferData.setNameAndValue("ClaimRelFlag",tClaimRelFlag);//赔案相关标志
        return true;
    }
    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        if(!prepareNodeData())
        {
            CError tError = new CError();
            tError.moduleName = "LLGrpSecondUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "准备节点数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mResult.clear();
         mResult.add(mG);
        mResult.add(map);//业务数据包
        mResult.add(mTransferData);//工作流数据
        return true;
    }
    /**
     * 返回结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 返回错误
     * @return VData
     */
    public CErrors getErrors()
    {
        return mErrors;
    }

}
