/**
 * <p>Title: 理赔人工核保 “核保完成”服务类 </p>
 * <p>Description: </p>
 * <p>Company: SinoSoft</p>
 * @author yuejw
 */

package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.workflowengine.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.claimgrp.*;

public class LLSecondManuUWFinishAfterInitService implements AfterInitService
{
private static Logger logger = Logger.getLogger(LLSecondManuUWFinishAfterInitService.class);

     /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** 提交数据容器 */
    private MMap map = new MMap();
    //private VData mIputData = new VData();
    private TransferData mTransferData = new TransferData();
    /** 数据操作字符串 */
    private String mOperater;
    private String mManageCom;
    private String mOperate;
    private String mTransact;
    private String mMissionID;
    private String mSubMissionID;

    private LLCUWMasterSchema mLLCUWMasterSchema=new LLCUWMasterSchema();
    private LLCUWMasterSet mLLCUWMasterSet=new LLCUWMasterSet();

    public LLSecondManuUWFinishAfterInitService()
    {
    }
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
           { return false;}
        //校验传入数据
        if (!checkData())
            {return false;}
        logger.debug("Start  dealData...");
        //进行业务处理
        if (!dealData())
            {return false;}
        logger.debug("dealData successful!");
        //为工作流下一节点属性字段准备数据
        if (!prepareTransferData())
           { return false;}
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
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
            //获取页面信息<个人合同理陪核保最近结果表>
        mLLCUWMasterSet=(LLCUWMasterSet) cInputData.getObjectByObjectName("LLCUWMasterSet", 0);//按类取值
        mTransact=(String ) cInputData.getObjectByObjectName("String",0);
        mInputData = cInputData;

        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWFinishAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        mOperater = mGlobalInput.Operator;
        if (mOperater == null || mOperater.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWFinishAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operater失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得当前工作任务的任务ID
        mMissionID = (String) mTransferData.getValueByName("MissionID");
        mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
        if (mMissionID == null ||mSubMissionID==null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWFinishAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输业务数据中失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        logger.debug("----------Service dealData BEGIN----------");

        logger.debug("-----###############-----------"+mTransact);
        boolean tReturn = false;

        //检查该任务下的所有赔案是否都下了核保结论
        //检验险种结论是否完成，根据（分案号--批次号---赔案相关标志），从个人理赔合同批次表---LLCUWBatch 取出该任务下所有 合同号
        //然后以“分案号 and 批次号”查询 个人合同理陪核保最近结果表--- LLCUWMaster 是否存在记录
        LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
        LLCUWBatchDB tLLCUWBatchDB = new LLCUWBatchDB();
        String tCaseno=(String) mTransferData.getValueByName("CaseNo");
        String tBatNo=(String) mTransferData.getValueByName("BatNo");
        String tClaimRelFlag=(String) mTransferData.getValueByName("ClaimRelFlag");
        tLLCUWBatchDB.setCaseNo(tCaseno);
        tLLCUWBatchDB.setBatNo(tBatNo);
        tLLCUWBatchDB.setClaimRelFlag(tClaimRelFlag);

        tLLCUWBatchSet.set(tLLCUWBatchDB.query());
        logger.debug("-----------该合同下保单险种号码数目---" + tLLCUWBatchSet.size());

        String tstate = "complete"; //险种结论全部下完标志----自定义
        String struncomplete = "该合同下未下核保结论的合同号码:";
        for (int i = 1; i <= tLLCUWBatchSet.size(); i++)
        {
            LLCUWMasterDB tLLCUWMasterDB = new LLCUWMasterDB();
            tLLCUWMasterDB.setCaseNo(tCaseno);
            tLLCUWMasterDB.setBatNo(tBatNo);
            tLLCUWMasterDB.setContNo(tLLCUWBatchSet.get(i).getContNo());
            tLLCUWMasterDB.getInfo();
            if (tLLCUWMasterDB.getCount() == 0)
            {
                struncomplete = struncomplete + "--" +
                                tLLCUWBatchSet.get(i).getContNo();
                tstate = "uncomplete";
            }
        }

        if (tstate.equals("uncomplete"))
        {
            CError tError = new CError();
            tError.moduleName = "";
            tError.functionName = "dealtData";
            tError.errorMessage = struncomplete;
            this.mErrors.addOneError(tError);
            return false;
        }


        //与赔案有关，核保完成，转向理赔岗
        if(mTransact.equals("Finish||ToClaim"))
        {
            mResult.add(mTransferData);
        }
        //与赔案无关，核保完成，转向保全岗
      if(mTransact.equals("Finish||ToWFEdor"))
      {


          //为每个保单或合同生成一个节点，准备数据
          logger.debug("------准备生成的保全节点书目为-----"+mLLCUWMasterSet.size());
          TransferData tTransferData = new TransferData();
//          mResult.clear();
          VData tVData=new VData();
          for(int index=1;index<=mLLCUWMasterSet.size();index++)
          {
              tTransferData = new TransferData();
              tVData=new VData();
              tTransferData.setNameAndValue("EdorAcceptNo","");//保全受理号
              tTransferData.setNameAndValue("CaseNo", mLLCUWMasterSet.get(index).getCaseNo());
              tTransferData.setNameAndValue("ContNo", mLLCUWMasterSet.get(index).getContNo());
              tTransferData.setNameAndValue("ManageCom",mGlobalInput.ManageCom);//管理机构
              tTransferData.setNameAndValue("MissionID", mTransferData.getValueByName("MissionID"));
              tTransferData.setNameAndValue("SubMissionID", mTransferData.getValueByName("SubMissionID"));
              tTransferData.setNameAndValue("ActivityID", mTransferData.getValueByName("ActivityID"));
              tVData.add(tTransferData);
              mResult.add(tVData);
          }
      }


        return true;
    }

    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        return true;
    }

    /**
     * 为公共传输数据集合中添加工作流下一节点属性字段数据
     * @return
     */
    private boolean prepareTransferData()
    {
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public TransferData getReturnTransferData()
    {
        return mTransferData;
    }

    public CErrors getErrors()
    {
        return mErrors;
    }
}
