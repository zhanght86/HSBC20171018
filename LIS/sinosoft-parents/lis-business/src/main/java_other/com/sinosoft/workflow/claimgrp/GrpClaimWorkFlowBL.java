package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;


import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.claimgrp.*;

/**
 * <p>Title:理赔工作流 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SinoSoft</p>
 * @author ZL
 * @version 1.0
 */
public class GrpClaimWorkFlowBL
{
private static Logger logger = Logger.getLogger(GrpClaimWorkFlowBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往前台提交数据的容器 */
    private VData mResult = new VData();

    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mGlobalInput = new GlobalInput();

    private TransferData mTransferData = new TransferData();

    //提交数据打包类
    private MMap map = new MMap();

    /**工作流引擎 */
    ActivityOperator mActivityOperator = new ActivityOperator();

    /** 数据操作字符串 */
    private String mOperater;
    private String mManageCom;
    private String mOperate;

    /**是否提交标志**/
    private String flag;
    private boolean mFlag = true;

    LWMissionSchema mLWMissionSchema = new LWMissionSchema();

    public GrpClaimWorkFlowBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("------GrpClaimWorkFlowBL BEG------");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }
        logger.debug("---GrpClaimWorkFlowBL After getInputData---");

        // 数据操作业务处理
        if (!dealData())
        {
            return false;
        }
        logger.debug("---GrpClaimWorkFlowBL After dealData---");

        //准备给后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("---GrpClaimWorkFlowBL After prepareOutputData---");

        //如果置相应的标志位，不提交
        if (mFlag)
        {

            logger.debug("Start GrpClaimWorkFlowBL Submit......");

            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, ""))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                CError tError = new CError();
                tError.moduleName = "ClaimWorkFlowBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            logger.debug("------GrpClaimWorkFlowBL END------");
        }
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
        mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput",
                0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData",
                0);
        mInputData = cInputData;
        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "GrpClaimWorkFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //获得操作员编码
        mOperater = mGlobalInput.Operator;
        if ((mOperater == null) || mOperater.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "GrpClaimWorkFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operater失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //获得登陆机构编码
        mManageCom = mGlobalInput.ManageCom;
        if ((mManageCom == null) || mManageCom.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "GrpClaimWorkFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        mOperate = cOperate;
        if ((mOperate == null) || mOperate.trim().equals(""))
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCPolDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "GrpClaimWorkFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operate任务节点编码失败!";
            this.mErrors.addOneError(tError);

            return false;
        }
        flag = (String) mTransferData.getValueByName("flag");
        if (flag != null)
        {
            if (flag.equals("N"))
            {
                mFlag = false;
            }
        }

        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        //创建团体报案初始节点
        if (mOperate.trim().equals("9999999999"))
        {
            if (!Execute9999999999())
            {
                // @@错误处理
            	CError.buildErr(this, mActivityOperator.mErrors.getLastError());
                return false;
            }
        }
        else if (mOperate.trim().equals("9999999998"))
        {
            //通过批量导入创建团体立案起始节点
            if (!Execute9999999998())
            {
                // @@错误处理
            	CError.buildErr(this, mActivityOperator.mErrors.getLastError());
                return false;
            }
        }
        else if (mOperate.trim().equals("9899999999"))
        {
            //创建团体立案起始节点
            if (!Execute9899999999())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("8999999999"))
        {
            //创建团体呈报起始节点
            if (!Execute8999999999())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("8899999999"))
        {
            //创建团体调查报告申请起始节点
            if (!Execute8899999999())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009015"))
        {
            //立案确认
            if (!Execute0000005015())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009035"))
        {
            //审核确认
            if (!Execute0000005035())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009105"))
        {
            //消亡呈报节点
            if (!Execute01())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009145"))
        {
            //消亡调查过程节点，并根据判断是否生成结论节点
            if (!Execute0000005145())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009165"))
        {
            //消亡调查结论节点<机构层面>,并判断是否生成下一个节点<赔案层面>
            if (!Execute0000005165()) {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }

        else if (mOperate.trim().equals("0000009175"))
        {
            //消亡调查结论节点<赔案层面>
            if (!Execute0000005175()) {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }

        else if (mOperate.trim().equals("0000009075"))
        {
            //消亡理赔结案节点
            if (!Execute01()) {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("Create||ScondUWNode"))
        {
            //创建团体[发起二核节点]
            if (!ExecuteCreateScondUWNode())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        else if (mOperate.trim().equals("0000009505"))
        {
             //Finish||ToClaim--------与赔案有关，核保完成，转向理赔岗，//消亡理赔二核节点
             //Finish||ToWFEdor-------核保完成，转向保全岗，//消亡理赔二核节点，生成“保全工作流二核处理起始节点”
            if (!Execute0000005505())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }


        else
        {
            //执行理赔工作流其他一般节点任务
            if (!Execute())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }

        return true;
    }

    /**
     * 执行理赔工作流其他一般节点任务
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute()
    {
        logger.debug("------Goto Execute()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();

        //获得当前工作任务的任务ID
        String tMissionID = (String) mTransferData.getValueByName("MissionID");
        String tSubMissionID = (String) mTransferData.getValueByName(
                "SubMissionID");
        if (tMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute";
            tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        if (tSubMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        try
        {
            if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
                                                  mOperate, mInputData))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
            //获得执行节点任务后返回的结果
            tVData = mActivityOperator.getResult();
            if (tVData != null)
            {
                for (int i = 0; i < tVData.size(); i++)
                {
                    VData tempVData = new VData();
                    tempVData = (VData) tVData.get(i);
                    cInputData.add(tempVData);
                }
            }

            //产生团体下一个任务节点
            if (tActivityOperator.CreateNextMission(tMissionID, tSubMissionID,
                    mOperate, mInputData))
            {
                VData tempVData = new VData();
                tempVData = tActivityOperator.getResult();
                if ((tempVData != null) && (tempVData.size() > 0))
                {
                    cInputData.add(tempVData);
                    tempVData = null;
                }
            }
            else
            {
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }

            tActivityOperator = new ActivityOperator();
            if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
                                                mOperate, mInputData))
            {
                VData tempVData = new VData();
                tempVData = tActivityOperator.getResult();
                if ((tempVData != null) && (tempVData.size() > 0))
                {
                    cInputData.add(tempVData);
                    tempVData = null;
                }
            }
            else
            {
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "dealData";
            tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------End Execute()------");
        return true;
    }

    /**
     * 节点一般处理方法.只执行理赔节点任务,并消亡节点
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute01()
    {
        logger.debug("------Goto Execute01()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();

        //获得当前工作任务的任务ID
        String tMissionID = (String) mTransferData.getValueByName("MissionID");
        String tSubMissionID = (String) mTransferData.getValueByName(
                "SubMissionID");
        String ActivityID = (String)mTransferData.getValueByName("ActivityID");
        if (tMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000000001";
            tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        if (tSubMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000000001";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        try
        {
//            if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
//                                                  mOperate, mInputData))
//            {
//                // @@错误处理
//                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//                return false;
//            }
//            //获得执行节点任务后返回的结果
//            tVData = mActivityOperator.getResult();
//            if (tVData != null)
//            {
//                for (int i = 0; i < tVData.size(); i++)
//                {
//                    VData tempVData = new VData();
//                    tempVData = (VData) tVData.get(i);
//                    cInputData.add(tempVData);
//                }
//            }

            //删除当前节点
            tActivityOperator = new ActivityOperator();
            if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
                                                ActivityID, mInputData))
            {
                VData tempVData = new VData();
                tempVData = tActivityOperator.getResult();
                if ((tempVData != null) && (tempVData.size() > 0))
                {
                    cInputData.add(tempVData);
                    tempVData = null;
                }
            }
            else
            {
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000000001";
            tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------End Execute01()------");
        return true;
    }

    /**
     * 创建团体理赔起始任务节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute9999999999()
    {
        logger.debug("----------创建团体理赔起始任务节点 BEGIN----------");
        boolean tReturn = false;
        VData cInputData = new VData();
        VData tVData = new VData();

        //调用业务逻辑处理类，返回处理完数据
        LLGrpReportBL tLLGrpReportBL = new LLGrpReportBL();
        if (tLLGrpReportBL.submitData(mInputData, mOperate))
        {
            VData tempVData1 = new VData();
            
            //获得报案处理类的处理结果
            tempVData1 = tLLGrpReportBL.getResult();
            cInputData.add(tempVData1);
            //向前台返回处理完的参数以便查询工作流
            mTransferData = null;
            mTransferData = (TransferData) tempVData1.getObjectByObjectName(
                    "TransferData", 0);
            mResult.add(mTransferData);

            //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
            ActivityOperator tActivityOperator = new ActivityOperator();
            try
            {
                //产生团体报案确认节点
                if (tActivityOperator.CreateStartMission_NoScan("0000000009","0000009005", tempVData1))
                {
                    VData tempVData2 = new VData();
                    tempVData2 = tActivityOperator.getResult();
                    cInputData.add(tempVData2);
                    tempVData2 = null;
                    tReturn = true;
                }
                else
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    tReturn = false;
                }
            }
            catch (Exception ex)
            {
            	
            	  CError.buildErr(this,"工作流引擎工作出现异常!");
                  tReturn = false;
            }

            tempVData1 = null;
        }
        else
        {
            // @@错误处理
        	  CError.buildErr(this,"数据提交失败!");
              mResult.clear();
              tReturn = false;
        }

        //准备提交数据
        mInputData.clear();
        mInputData = cInputData;
        return tReturn;
    }
    
    
    /**
     * 通过批量导入的形式创建团体理赔起始任务节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute9999999998()
    {
        logger.debug("----------通过批量导入的形式创建团体理赔起始任务节点 BEGIN----------");
        boolean tReturn = false;

        
        VData cInputData = new VData();
        cInputData.add(mInputData.clone());

        //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
        ActivityOperator tActivityOperator = new ActivityOperator();
        try
        {
            //产生团体报案确认节点
            if (tActivityOperator.CreateStartMission_NoScan("0000000009","0000009005", mInputData))
            {
                    VData tempVData = new VData();
                    tempVData = tActivityOperator.getResult();
                    cInputData.add(tempVData);
                    tempVData = null;
                    tReturn = true;
            }
            else
            {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    tReturn = false;
            }
         }
         catch (Exception ex)
         {
            	
             CError.buildErr(this,"工作流引擎工作出现异常!");
             tReturn = false;
         }

         //准备提交数据
         mInputData.clear();
         mInputData = cInputData;

         return tReturn;
    }
    

    /**
     * 创建团体理赔起始任务节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute9899999999()
    {
        logger.debug("----------创建团体理赔立案起始任务节点 BEGIN----------");
        boolean tReturn = false;
        VData cInputData = new VData();
        VData tVData = new VData();

        //调用业务逻辑处理类，返回处理完数据
        LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new LLGrpClaimRegisterBL();
        if (tLLGrpClaimRegisterBL.submitData(mInputData, mOperate))
        {
            VData tempVData1 = new VData();
            tempVData1 = tLLGrpClaimRegisterBL.getResult();
            cInputData.add(tempVData1);
            //向前台返回处理完的参数以便查询工作流
            mTransferData = null;
            mTransferData = (TransferData) tempVData1.getObjectByObjectName(
                    "TransferData", 0);
            mResult.add(mTransferData);

            //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
            ActivityOperator tActivityOperator = new ActivityOperator();
            try
            {
                //产生团体报案确认节点
                if (tActivityOperator.CreateStartMission_NoScan("0000000005",
                        "0000005015", tempVData1))
                {
                    VData tempVData2 = new VData();
                    tempVData2 = tActivityOperator.getResult();
                    cInputData.add(tempVData2);
                    tempVData2 = null;
                    tReturn = true;
                }
                else
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    tReturn = false;
                }
            }
            catch (Exception ex)
            {
                // @@错误处理
//                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "ClaimWorkFlowBL";
//                tError.functionName = "Execute9899999999";
//                tError.errorMessage = "工作流引擎工作出现异常!";
//                this.mErrors.addOneError(tError);
                CError.buildErr(this, "创建团体理赔起始任务工作流引擎工作出现异常!");
                tReturn = false;
            }

            tempVData1 = null;
        }
        else
        {
            // @@错误处理
//            this.mErrors.copyAllErrors(tLLGrpClaimRegisterBL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "ClaimWorkFlowBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
            CError.buildErr(this, "创建团体理赔起始任务数据提交失败!");
            mResult.clear();
            tReturn = false;
        }
        mInputData.clear();
        mInputData = cInputData;
        return tReturn;
    }

    /**
     * 创建团体理赔呈报任务节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute8999999999()
    {
        logger.debug("------Goto Execute8999999999()------");
        boolean tReturn = false;
        VData cInputData = new VData();
        VData tVData = new VData();

        //调用业务逻辑处理类，返回处理完数据
        LLGrpSubmitApplyBL tLLGrpSubmitApplyBL = new LLGrpSubmitApplyBL();
        if (tLLGrpSubmitApplyBL.submitData(mInputData, "INSERT"))
        {
            VData tempVData1 = new VData();
            tempVData1 = tLLGrpSubmitApplyBL.getResult();
            cInputData.add(tempVData1);

            //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
            ActivityOperator tActivityOperator = new ActivityOperator();
            try
            {
                //产生团体报案确认节点
                if (tActivityOperator.CreateStartMission_NoScan("0000000005",
                        "0000005105", tempVData1))
                {
                    VData tempVData2 = new VData();
                    tempVData2 = tActivityOperator.getResult();
                    cInputData.add(tempVData2);
                    tempVData2 = null;
                    tReturn = true;
                }
                else
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    tReturn = false;
                }
            }
            catch (Exception ex)
            {
                // @@错误处理
//                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "ClaimWorkFlowBL";
//                tError.functionName = "Execute8999999999";
//                tError.errorMessage = "工作流引擎工作出现异常!";
//                this.mErrors.addOneError(tError);
                CError.buildErr(this, "创建团体理赔呈报任务工作流引擎工作出现异常!");
                tReturn = false;
            }

            tempVData1 = null;
        }
        else
        {
            // @@错误处理
//            this.mErrors.copyAllErrors(tLLGrpSubmitApplyBL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "ClaimWorkFlowBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
            CError.buildErr(this, "创建团体理赔呈报任务数据提交失败!");
            mResult.clear();
            tReturn = false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------End Execute8999999999()------");
        return tReturn;
    }


    /**
     * 创建团体理赔调查报告任务节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute8899999999()
    {
        logger.debug("------Goto Execute8899999999()------");
        boolean tReturn = false;
        VData cInputData = new VData();
        VData tVData = new VData();

        //调用业务逻辑处理类，返回处理完数据
        LLGrpInqApplyBL tLLGrpInqApplyBL = new LLGrpInqApplyBL();
        if (tLLGrpInqApplyBL.submitData(mInputData, "INSERT"))
        {
            VData tempVData1 = new VData();
            tempVData1 = tLLGrpInqApplyBL.getResult();
            cInputData.add(tempVData1);

            //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
            ActivityOperator tActivityOperator = new ActivityOperator();
            try
            {
                //产生团体报案确认节点
                if (tActivityOperator.CreateStartMission("0000000005",
                        "0000005125", tempVData1))
                {
                    VData tempVData2 = new VData();
                    tempVData2 = tActivityOperator.getResult();
                    cInputData.add(tempVData2);
                    tempVData2 = null;
                    tReturn = true;
                }
                else
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    tReturn = false;
                }
            }
            catch (Exception ex)
            {
                // @@错误处理
//                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "ClaimWorkFlowBL";
//                tError.functionName = "Execute8899999999";
//                tError.errorMessage = "工作流引擎工作出现异常!";
//                this.mErrors.addOneError(tError);
            	CError.buildErr(this, "创建团体理赔调查任务工作流引擎工作出现异常!");
                tReturn = false;
            }

            tempVData1 = null;
        }
        else
        {
            // @@错误处理
//            this.mErrors.copyAllErrors(tLLGrpInqApplyBL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "ClaimWorkFlowBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
        	CError.buildErr(this, "创建团体理赔调查任务数据提交失败!");
            mResult.clear();
            tReturn = false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------End Execute8899999999()------");
        return tReturn;
    }

    /**
     * 理赔立案节点处理方法:1、立案通过时，走正常工作流
     * 理赔立案节点处理方法:2、不予立案时，只执行节点任务,并消亡节点
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute0000005015()
    {
        logger.debug("------Goto Execute0000005015()------");
        VData cInputData = new VData();

        String tRgtConclusion = (String) mTransferData.getValueByName("RgtConclusion"); //立案结论
        if (tRgtConclusion == null)
        {
            // @@错误处理
//            CError tError = new CError();
//            tError.moduleName = "GrpClaimWorkFlowBL";
//            tError.functionName = "Execute0000005015";
//            tError.errorMessage = "前台传输数据TransferData中的必要参数RgtConclusion失败!";
//            this.mErrors.addOneError(tError);
        	CError.buildErr(this, "创建团体理赔立案节点任务数据传输失败!");
            return false;
        }

        try
        {
            if (tRgtConclusion.equals("01"))
            {
                logger.debug("-----立案通过------");
                if (!Execute())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
            else if (tRgtConclusion.equals("02"))
            {
                logger.debug("-----不予立案------");
                if (!Execute01())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005015";
            tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("------End Execute0000005015()------");
        return true;
    }

    /**
     * 理赔审核节点处理方法:1、只执行审核节点任务,并消亡节点
     * 理赔审核节点处理方法:2、判断是生成下一个节点
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute0000005035()
    {
        logger.debug("------Goto Execute0000005035()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();
        String tClmNo = (String) mTransferData.getValueByName("RptNo"); //赔案号
        String tBudgetFlag = (String) mTransferData.getValueByName("BudgetFlag"); //预付标志
        if (tClmNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005035";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            //查询审核结论
            String strSQL = "";
            strSQL = " select AuditConclusion from LLClaimUWMain where "
                   + " ClmNO='" + tClmNo + "'";
            ExeSQL exesql = new ExeSQL();
            String tResult = exesql.getOneValue(strSQL);
            logger.debug("审核结论为: " + tResult);
           // if (tResult.equals("0") || tResult.equals("1") || tBudgetFlag.equals("1"))
           //需要审批的案件才生成下个节点 2005-11-14 pd
            if (tResult.equals("4")|| tBudgetFlag.equals("1"))
            {
                //消亡审核节点，并生成下一个节点
                logger.debug("-----可以生成下一个节点------");
                if (!Execute())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
            else
            {
                //消亡审核节点（不生成下一个节点）
                logger.debug("-----不可以生成下一个节点------");
                if (!Execute01())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005035";
            tError.errorMessage = "工作流引擎执行审核节点任务出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("------End Execute0000005035()------");
        return true;
    }

    /**
     * 理赔过程录入节点处理方法:1、只执行理赔过程录入节点任务,并消亡节点
     * 理赔过程录入节点处理方法:2、判断是生成下一个节点
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute0000005145()
    {
        logger.debug("------Goto Execute0000005145()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();
        String tClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
        String tInqNo = (String) mTransferData.getValueByName("InqNo"); //调查序号
        String tBatNo = (String) mTransferData.getValueByName("BatNo"); //调查批次
        String tInqDept = (String) mTransferData.getValueByName("InqDept"); //调查机构

        if (tClmNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005145";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (tInqNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005145";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (tBatNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005145";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (tInqDept == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005145";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            String strSQL = ""; //用于判断是否可以生成下一个节点(调查结论)
            strSQL = "select ClmNO,InqNo,BatNo,CustomerNo,Customername,VIPFlag,InqDept,InitPhase,InqRCode,InqItem from llInqapply"
                     + " where ClmNO='" + tClmNo + "'"
                     + " and InqNo!='" + tInqNo + "'"
                     + " and BatNo='" + tBatNo + "'"
                     + " and InqDept='" + tInqDept + "'"
                     + " and InqState='0'";
            logger.debug("--strSQL=" + strSQL);
            ExeSQL exesql = new ExeSQL();
            String tResult = exesql.getOneValue(strSQL);
            logger.debug("-----判断事件是否可以生成下一个节点tResult.length()= " +
                               tResult.length());
            if ((tResult.length() == 0))
            {
                //消亡调查过程录入节点，并生成下一个节点
                logger.debug("-----可以生成下一个节点------");
                if (!Execute())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
            else
            {
                //消亡调查过程录入节点（不生成下一个节点）
                logger.debug("-----不可以生成下一个节点------");
                if (!Execute01())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005145";
            tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("------End Execute0000005145()------");
        return true;
    }



    /**
     * 理赔过程录入节点处理方法:1、执行理赔机构结论录入节点任务,并消亡节点
     * 理赔过程录入节点处理方法:2、判断是生成下一个节点（赔案层面的调查结论）
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute0000005165()
    {
        logger.debug("------Goto Execute0000005165()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();
        LLInqConclusionSchema tLLInqConclusionSchema = new LLInqConclusionSchema();
        tLLInqConclusionSchema = (LLInqConclusionSchema) mInputData.getObjectByObjectName(
            "LLInqConclusionSchema", 0);

        String tClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
        //获得当前工作任务的任务ID
        String tMissionID = (String) mTransferData.getValueByName("MissionID");
        String tSubMissionID = (String) mTransferData.getValueByName(
                "SubMissionID");

        if (tClmNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005165";
            tError.errorMessage = "前台传输数据中的赔案号失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (tMissionID == null ||tSubMissionID==null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005175";
            tError.errorMessage = "前台传输数据工作流节点信息中的必要参数失败!";
            this.mErrors.addOneError(tError);

            return false;
        }
        try
        {
             //用于判断是否可以生成下一个节点(赔案层面调查结论)

             //检查  未完成的机构调查结论数目，如大于零，则不允许生成
             LLInqConclusionDB tLLInqConclusionDB =new LLInqConclusionDB();
             LLInqConclusionSet tLLInqConclusionSet =new LLInqConclusionSet();
             String tConNo=tLLInqConclusionSchema.getConNo();
             String strSql="select * from llinqconclusion where 1=1  "
                           + " and ClmNO='" + tClmNo + "'"
                           + " and ConNo!='" + tConNo + "'"
                           + " and FiniFlag='0' and ColFlag='2' "
                           + " order by clmno";
             tLLInqConclusionSet.set(tLLInqConclusionDB.executeQuery(strSql));
            logger.debug("-----未完成的机构调查结论数目==== " +
                              tLLInqConclusionSet.size());

             //判断 “赔案层调查结论” 节点是否存在，如果存在同样也不允许生成
             String strlwmisSql="select * from lwmission where 1=1"
                                +" and processid='0000000005'"
                                +" and activityid='0000005175'"
                                +" and missionprop6='0'"
                                +" and missionprop1='"+tClmNo+"'";
             LWMissionDB tLWMissionDB=new LWMissionDB();
             LWMissionSet tLWMissionSet = new LWMissionSet();
             tLWMissionSet.set(tLWMissionDB.executeQuery(strlwmisSql));
             logger.debug("-----是否存在 赔案层 结论节点==== " +tLWMissionSet.size());

            if (tLLInqConclusionSet.size()==0 && tLWMissionSet.size()==0)
            {
                //消亡机构调查结论录入节点，并生成下一个节点

                logger.debug("-----可以生成下一个节点------");
                mTransferData.removeByName("transact");
                mTransferData.setNameAndValue("transact","UPDATE&&INSERT");
                if (!Execute())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
            else
            {
                //消亡机构调查结论录入节点（不生成下一个节点）
                logger.debug("-----不可以生成下一个节点------");
                mTransferData.removeByName("transact");
                mTransferData.setNameAndValue("transact","UPDATE");
                if (!Execute01())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.
                                               mErrors);
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005165";
            tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }

        logger.debug("------End Execute0000005165()------");
        return true;
    }


    /**
     * 节点一般处理方法.只执行赔案层面调查结论节点任务,并消亡节点
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean Execute0000005175()
    {
        logger.debug("------Goto Execute0000005175()------");
        VData cInputData = new VData();
        VData tVData = new VData();
        ActivityOperator tActivityOperator = new ActivityOperator();

        //获得当前工作任务的任务ID
        String tMissionID = (String) mTransferData.getValueByName("MissionID");
        String tSubMissionID = (String) mTransferData.getValueByName(
                "SubMissionID");

        if (tMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005175";
            tError.errorMessage = "前台传输数据TransferData中的必要参数MissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        if (tSubMissionID == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005175";
            tError.errorMessage = "前台传输数据TransferData中的必要参数SubMissionID失败!";
            this.mErrors.addOneError(tError);

            return false;
        }

        try
        {
            if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
                                                  mOperate, mInputData))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
            //获得执行节点任务后返回的结果
            tVData = mActivityOperator.getResult();
            if (tVData != null)
            {
                for (int i = 0; i < tVData.size(); i++)
                {
                    VData tempVData = new VData();
                    tempVData = (VData) tVData.get(i);
                    cInputData.add(tempVData);
                }
            }

            //删除当前节点
            tActivityOperator = new ActivityOperator();
            if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
                                                mOperate, mInputData))
            {
                VData tempVData = new VData();
                tempVData = tActivityOperator.getResult();
                if ((tempVData != null) && (tempVData.size() > 0))
                {
                    cInputData.add(tempVData);
                    tempVData = null;
                }
            }
            else
            {
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(mActivityOperator.mErrors);
            CError tError = new CError();
            tError.moduleName = "ClaimWorkFlowBL";
            tError.functionName = "Execute0000005175";
            tError.errorMessage = "工作流引擎执行新契约活动表任务出错!";
            this.mErrors.addOneError(tError);

            return false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------End Execute0000005175()------");
        return true;
    }

    /*
     * 创建团体[发起二核]节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean ExecuteCreateScondUWNode()
    {
        logger.debug("------创建团体[发起二核]节点  Begin------");
        boolean tReturn = false;
        VData cInputData = new VData();
        VData tVData = new VData();

        //调用业务逻辑处理类，返回处理完数据
        LLGrpSecondUWBL tLLGrpSecondUWBL = new LLGrpSecondUWBL();
        if (tLLGrpSecondUWBL.submitData(mInputData, mOperate)) {
            VData tempVData1 = new VData();
            tempVData1 = tLLGrpSecondUWBL.getResult();
//            MMap tMap = new MMap();
//            tMap = (MMap) tempVData1.getObjectByObjectName("MMap", 0);
//            cInputData.add(tMap);
            cInputData.add(tempVData1);
            mTransferData = null;
            mTransferData = (TransferData) tempVData1.getObjectByObjectName(
                    "TransferData", 0);
            String tClaimRelFlag = (String) mTransferData.getValueByName(
                    "ClaimRelFlag");
            //首先生成节点，由于第一次创建团体，没有触发服务类，在下面直接调用业务处理类
            try
            {
                //只有0---相关的合同号  或者 //只有1---无关的合同号--------创建团体一条任务
                if (tClaimRelFlag.equals("0") || tClaimRelFlag.equals("1"))
                {
                    logger.debug("------创建团体一条任务,相关标志------" +
                                       mTransferData.getValueByName(
                            "ClaimRelFlag"));
                    //产生团体[发起二核]节点
                    ActivityOperator tActivityOperator = new ActivityOperator();
                    if (tActivityOperator.CreateStartMission("0000000005",
                            "0000005505", tempVData1)) {
                        VData tempVData2 = new VData();
                        tempVData2 = tActivityOperator.getResult();
//                        MMap tMap2 = new MMap();
//                        tMap2 = (MMap) tempVData2.getObjectByObjectName("MMap",
//                                0);
//                        cInputData.add(tMap2);
                        cInputData.add(tempVData2);
                        tempVData2 = null;
                        tReturn = true;
                    } else {
                        // @@错误处理
                        this.mErrors.copyAllErrors(mActivityOperator.
                                mErrors);
                        tReturn = false;
                    }

                }
                //“0---相关”“1---无关”都存在，-创建团体两条任务
                if (tClaimRelFlag.equals("2"))
                {
                    logger.debug("------创建团体两条任务,相关标志------");
                    logger.debug("------创建团体第一条任务-------------");
                    ActivityOperator tActivityOperator = new ActivityOperator();
                    mTransferData.removeByName("ClaimRelFlag");
                    mTransferData.setNameAndValue("ClaimRelFlag", "0");
                    //产生团体[发起二核]节点
                    logger.debug("------相关标志------" +
                                       mTransferData.
                                       getValueByName("ClaimRelFlag"));
                    if (tActivityOperator.CreateStartMission("0000000005",
                            "0000005505", tempVData1)) {
                        VData tempVData2 = new VData();
                        tempVData2 = tActivityOperator.getResult();
//                        MMap tMap2 = new MMap();
//                        tMap2 = (MMap) tempVData2.getObjectByObjectName("MMap",
//                                0);
//                        cInputData.add(tMap2);
                        cInputData.add(tempVData2);
                        tempVData2 = null;
                        tReturn = true;
                    } else {
                        // @@错误处理
                        this.mErrors.copyAllErrors(mActivityOperator.
                                mErrors);
                        tReturn = false;
                    }
                    logger.debug("------创建团体第二条任务------");
                    ActivityOperator ttActivityOperator = new ActivityOperator();
                    mTransferData.removeByName("ClaimRelFlag");
                    mTransferData.setNameAndValue("ClaimRelFlag", "1");
                    logger.debug("------相关标志------" +
                                       mTransferData.
                                       getValueByName("ClaimRelFlag"));
                    //产生团体[发起二核]节点
                    if (ttActivityOperator.CreateStartMission("0000000005",
                            "0000005505", tempVData1)) {
                        VData tempVData3 = new VData();
                        tempVData3 = ttActivityOperator.getResult();
//                        MMap tMap2 = new MMap();
//                        tMap2 = (MMap) tempVData2.getObjectByObjectName("MMap",
//                                0);
//                        cInputData.add(tMap2);
                        cInputData.add(tempVData3);
                        tempVData3 = null;
                        tReturn = true;
                    } else {
                        // @@错误处理
                        this.mErrors.copyAllErrors(mActivityOperator.
                                mErrors);
                        tReturn = false;
                    }

                }

            } catch (Exception ex) {
                // @@错误处理
//                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//                CError tError = new CError();
//                tError.moduleName = "ClaimWorkFlowBL";
//                tError.functionName = "Execute";
//                tError.errorMessage = "工作流引擎工作出现异常!";
//                this.mErrors.addOneError(tError);
            	CError.buildErr(this, "创建团体理赔二核任务工作流引擎工作出现异常!");
                tReturn = false;
            }

            tempVData1 = null;
        } else {
            // @@错误处理
//            this.mErrors.copyAllErrors(tLLGrpSecondUWBL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "ClaimWorkFlowBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
            CError.buildErr(this, "团体理赔二核任务数据提交失败!");
            mResult.clear();
            tReturn = false;
        }
        mInputData.clear();
        mInputData = cInputData;
        logger.debug("------创建团体[发起二核]节点  end------");
        return tReturn;
    }


    /*
     * [理赔人工核保完成]节点,同时调用业务处理类保存业务数据
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true

     */
    private boolean Execute0000005505()
    {

        logger.debug("------Goto Execute0000005505()------");
        String mTransact=(String ) mInputData.getObjectByObjectName("String",0);
        //Finish||ToClaim--------核保完成，转向理赔岗，//消亡理赔二核节点
        if (mTransact.trim().equals("Finish||ToClaim"))
        {
            if (!Execute01())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                return false;
            }
        }
        //Finish||ToWFEdor-------核保完成，转向保全岗，//消亡理赔二核节点，生成“保全工作流二核处理起始节点”
        if (mTransact.trim().equals("Finish||ToWFEdor"))
        {
            VData cInputData = new VData();
            VData tVData = new VData();
            ActivityOperator tActivityOperator = new ActivityOperator();
            //获得当前工作任务的任务ID
            String tMissionID = (String) mTransferData.getValueByName("MissionID");
            String tSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
            if (tMissionID == null||tSubMissionID == null)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "ClaimWorkFlowBL";
                tError.functionName = "Execute";
                tError.errorMessage = "前台传输数据TransferData中的必要参数失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            try
            {
                if (!mActivityOperator.ExecuteMission(tMissionID, tSubMissionID,
                                                      mOperate, mInputData))
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    return false;
                }
                //获得执行节点任务后返回的结果
                tVData = mActivityOperator.getResult();
                if (tVData != null)
                {
                    for (int i = 0; i < tVData.size(); i++)
                    {
                        VData tempVData = new VData();
                        tempVData = (VData) tVData.get(i);
                        VData tempVData1 = new VData();
                        for (int j = 0; j < tempVData.size(); j++)
                        {
                            tempVData1 = new VData();
                            tempVData1 = (VData) tempVData.get(j);
                            tempVData1.add(mGlobalInput);
                            tActivityOperator = new ActivityOperator();
                            if (tActivityOperator.CreateStartMission(
                                    "0000000000","0000000000", tempVData1))
                            {
                                VData tempVData2 = new VData();
                                tempVData2 = tActivityOperator.getResult();
                                if ((tempVData2 != null) &&(tempVData2.size() > 0))
                                {
                                    cInputData.add(tempVData2);
//                                    tempVData2.clear();
                                }
                            }
                            else
                            {
                                this.mErrors.copyAllErrors(mActivityOperator.
                                        mErrors);
                                return false;
                            }

                        }
                    }
                }

                tActivityOperator = new ActivityOperator();
                if (tActivityOperator.DeleteMission(tMissionID, tSubMissionID,
                                                    mOperate, mInputData))
                {
                    VData tempVData = new VData();
                    tempVData = tActivityOperator.getResult();
                    if ((tempVData != null) && (tempVData.size() > 0))
                    {
                        cInputData.add(tempVData);
                        tempVData = null;
                    }
                }
                else
                {
                    this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                    return false;
                }
            }
            catch (Exception ex)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mActivityOperator.mErrors);
                CError tError = new CError();
                tError.moduleName = "ClaimWorkFlowBL";
                tError.functionName = "dealData";
                tError.errorMessage = "工作流引擎执行理赔活动表任务出错!";
                this.mErrors.addOneError(tError);

                return false;
            }
            mInputData.clear();
            mInputData = cInputData;
            return true;

        }

        return true;
    }

    /**
     * 准备需要保存的数据
     */
    private boolean prepareOutputData()
    {
        try
        {
        	logger.debug("mInputData.size():"+mInputData.size());
            //把所有需要提交的map融合到一个map，统一使用pubsubmit提交
            for (int i = 0; i < mInputData.size(); i++)
            {
                VData tData = new VData();
                tData = (VData) mInputData.get(i);
                MMap tmap = new MMap();
                tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
                map.add(tmap);
            }
            mInputData.clear();
            mInputData.add(map);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public VData getResult()
    {
        return mResult;
    }
}
