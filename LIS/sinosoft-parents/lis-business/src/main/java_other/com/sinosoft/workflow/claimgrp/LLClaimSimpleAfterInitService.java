package com.sinosoft.workflow.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.workflowengine.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.claimgrp.LLReportBL;
import com.sinosoft.lis.claimgrp.LLClaimAuditBL;
import com.sinosoft.lis.claimgrp.LLClaimConfirmBL;
import com.sinosoft.lis.claimgrp.LLClaimSimpleBL;
import com.sinosoft.lis.claimgrp.LLClaimConfirmPassBL;
import com.sinosoft.lis.claimgrp.LLClaimPopedomSetBL;

/**
 * <p>Title: 简易案件确认服务类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SinoSoft</p>
 * @author zl
 * @version 1.0
 */

public class LLClaimSimpleAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimSimpleAfterInitService.class);
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
    private String mMissionID;
    private String mSubMissionID;
    private String mClmNo = "";
    private String mAuditFlag = "";
    private String mMaxLevel = ""; //最高权限
    private String mStandpay = "";
    private String mAdjpay = "";

    public LLClaimSimpleAfterInitService()
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
            return false;

        //校验传入数据
        if (!checkData())
            return false;

        logger.debug("Start  dealData...");

        //进行业务处理
        if (!dealData())
            return false;

        logger.debug("dealData successful!");

        //为工作流下一节点属性字段准备数据
        if (!prepareTransferData())
            return false;

        //准备往后台的数据
        if (!prepareOutputData())
            return false;

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
        mClmNo = (String) mTransferData.getValueByName("RptNo");
        mAuditFlag = (String) mTransferData.getValueByName("AuditFlag");
        mInputData = cInputData;
        mOperate = cOperate;

        if (mGlobalInput == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "AskUWSimpleAfterInitService";
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
            tError.moduleName = "LLClaimSimpleAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据Operate失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得当前工作任务的任务ID
        mMissionID = (String) mTransferData.getValueByName("MissionID");
        if (mMissionID == null)
        {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLClaimSimpleAfterInitService";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输业务数据中MissionID失败!";
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

        //处理业务操作
//        LLClaimSimpleBL tLLClaimSimpleBL = new LLClaimSimpleBL();
//        if (!tLLClaimSimpleBL.submitData(mInputData,"UPDATE"))
//        {
//            // @@错误处理
//            this.mErrors.copyAllErrors(tLLClaimSimpleBL.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimSimpleAfterInitService";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors .addOneError(tError);
//            mResult.clear();
//            mInputData = null;
//            tReturn = false;
//        }
//        else
//        {
//            mInputData = null;
//            mInputData = tLLClaimSimpleBL.getResult();
//            logger.debug("-----start Service getData from BL");
//            map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
//            tReturn = true;
//        }

        /**---------------------------------------------------------------------BEG
         * 功能：根据简易案件结论为结论分别提交处理
         * 处理：1 简易案件结论为不通过时,为审核节点增加"来自"属性,分为B简易案件结论为进入
         *        审核因为赔案金额为正数，C审批进入审核因为赔案金额为负数,D来自简易案件
         *      2 简易案件结论为通过时,处理财务数据，详细见同审批
         *      3 计算审核权限
         *--------------------------------------------------------------------*/
        if (mAuditFlag.equals("1"))
        {
            //简易案件结论为不通过,工作流流入审核,赔案状态已经为审核,无需更改
            //为公共传输数据集合中添加工作流下一节点属性字段数据
            mTransferData.setNameAndValue("ComeWhere", "D");
            mTransferData.removeByName("RptorState");
            mTransferData.setNameAndValue("RptorState","30");

            //No.1.1 准备权限计算所需参数
            String tSelectSql1 = "";//理算金额
            tSelectSql1 = "select nvl(a.realpay,0) from llclaim a where "
                        + "a.clmno = '" + mClmNo + "'";
            ExeSQL tExeSQL1 = new ExeSQL();
            mStandpay = tExeSQL1.getOneValue(tSelectSql1);
            if (mStandpay == null)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询理算金额失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            String tSelectSql2 = "";//调整后金额
            tSelectSql2 = "select nvl(b.beadjsum,0) from llregister b where "
                        + "b.rgtno = '" + mClmNo + "'";
            ExeSQL tExeSQL2 = new ExeSQL();
            mAdjpay = tExeSQL2.getOneValue(tSelectSql2);
            if (mAdjpay == null)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "查询调整后金额失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            mTransferData.setNameAndValue("standpay", mStandpay);
            mTransferData.setNameAndValue("adjpay", mAdjpay);

            //No.1.2 计算审核权限
            LLClaimPopedomSetBL tLLClaimPopedomSetBL = new
                                                       LLClaimPopedomSetBL();
            if (!tLLClaimPopedomSetBL.submitData(mInputData, ""))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimPopedomSetBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "dealData";
                tError.errorMessage = "计算审核权限失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            else
            {
                logger.debug(
                        "-----start Service getData from LLClaimPopedomSetBL");
                VData tVData = new VData();
                MMap tMap = new MMap();
                tVData = tLLClaimPopedomSetBL.getResult();
                tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
                TransferData tTransferData = new TransferData();
                tTransferData = (TransferData) tVData.getObjectByObjectName(
                        "TransferData", 0);
                mMaxLevel = (String) tTransferData.getValueByName("Popedom");
                //为公共传输数据集合中添加工作流下一节点属性字段数据
                mTransferData.setNameAndValue("Popedom", mMaxLevel); //审核模块权限
                map.add(tMap);
            }
        }
        else
        {
            //No.2.1 简易案件结论为通过,同审核处理
            LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
            if (!tLLClaimConfirmPassBL.submitData(mInputData,""))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimConfirmPassBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimSimpleAfterInitService";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors .addOneError(tError);
                mResult.clear();
                mInputData = null;
                return false;
            }
            else
            {
                VData tVDate = new VData();
                tVDate = tLLClaimConfirmPassBL.getResult();
                logger.debug("-----start Service getData from BL");
                MMap tMap = new MMap();
                tMap = (MMap) tVDate.getObjectByObjectName("MMap", 0);
                map.add(tMap);
            }

            //No.2.2 更改赔案状态为结案
            String sql1 = " update LLRegister set ClmState = '50' where"
                        + " RgtNo = '" + mClmNo + "'";
            map.put(sql1,"UPDATE");
            String sql2 = " update llclaim set ClmState = '50' where"
                        + " clmno = '" + mClmNo + "'";
            map.put(sql2,"UPDATE");
            String sql33 = " update llcase set RgtState = '50' where"
                        + " caseno = '" + mClmNo + "'";
            map.put(sql33,"UPDATE");

            //为公共传输数据集合中添加工作流下一节点属性字段数据,结案要回到立案机构
            String sql3 = " select MngCom from LLRegister where"
                        + " RgtNo = '" + mClmNo + "'";
            ExeSQL tExeSQL = new ExeSQL();
            String tMngCom = tExeSQL.getOneValue(sql3);
            if (tMngCom != null)
            {
                mTransferData.removeByName("MngCom");
                mTransferData.setNameAndValue("MngCom", tMngCom);
            }
        }
        //----------------------------------------------------------------------END

        return true;
    }

    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        mResult.add(map);
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
