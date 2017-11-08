/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.schema.LLClaimUWMDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;
import com.sinosoft.lis.db.LLAppClaimReasonDB;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.db.LLClaimPolicyDB;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.vschema.LLClaimDutyFeeSet;
import com.sinosoft.lis.db.LLClaimDutyFeeDB;
import com.sinosoft.lis.vschema.LLCaseRelaSet;
import com.sinosoft.lis.db.LLCaseRelaDB;
import com.sinosoft.lis.vschema.LLReportRelaSet;
import com.sinosoft.lis.db.LLReportRelaDB;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLAppealDB;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.vschema.LJAGetSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 申请申诉确认业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */

public class LLAppealConfirmBL
{
private static Logger logger = Logger.getLogger(LLAppealConfirmBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private TransferData mTransferData = new TransferData();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private LLAppealSchema mLLAppealSchema = new LLAppealSchema();
    private LJAGetSet mLJAGetSet = new LJAGetSet();
    private Reflections ref= new Reflections();

    private GlobalInput mG = new GlobalInput();
    private String mClmNo = "";

    public LLAppealConfirmBL()
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
        logger.debug("----------LLAppealConfirmBL begin submitData----------");
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

        //进行业务处理
        if (!dealData())
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
            tError.moduleName = "LLAppealConfirmBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
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
        logger.debug("---start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mClmNo = (String) mTransferData.getValueByName("ClaimNo");

        if (mClmNo == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAppealConfirmBL";
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
    private boolean dealData()
    {
        /**---------------------------------------------------------------------BEG
         * No.1 冲减保额
         *----------------------------------------------------------------------*/
        //取得原赔案号,并更新表
        LLAppealSchema tLLAppealSchema = new LLAppealSchema();
        LLAppealDB tLLAppealDB = new LLAppealDB();
        tLLAppealDB.setAppealNo(mClmNo);
        tLLAppealDB.getInfo();
        tLLAppealSchema = tLLAppealDB.getSchema();
        tLLAppealSchema.setAppealState("1");

        map.put(tLLAppealSchema, "DELETE&INSERT");

        logger.debug("##########当前赔案号为:"+mClmNo);
        logger.debug("##########原赔案号为:"+tLLAppealSchema.getCaseNo());

        //查询赔付明细表
        LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        String tSql = " select * from LLClaimDetail where 1=1"
                      + " and ClmNo = '" + tLLAppealSchema.getCaseNo() + "'"
                      + " and substr(GetDutyKind,2,2) <> '09'";
        tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tSql);
        if (tLLClaimDetailSet == null || tLLClaimDetailSet.size() == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAppealApplyBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询赔付明细表信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 1; i <= tLLClaimDetailSet.size(); i++)
        {
            //取得新旧给付金额
            double tOldPay = tLLClaimDetailSet.get(i).getRealPay();
            LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
            LLClaimDetailDB ttLLClaimDetailDB = new LLClaimDetailDB();
            ttLLClaimDetailDB.setClmNo(mClmNo);
            ttLLClaimDetailDB.setCaseNo(mClmNo);
            ttLLClaimDetailDB.setPolNo(tLLClaimDetailSet.get(i).getPolNo());
            ttLLClaimDetailDB.setCaseRelaNo(tLLClaimDetailSet.get(i).getCaseRelaNo());
            ttLLClaimDetailDB.setGetDutyCode(tLLClaimDetailSet.get(i).
                                             getGetDutyCode());
            ttLLClaimDetailDB.setGetDutyKind(tLLClaimDetailSet.get(i).
                                             getGetDutyKind());
            ttLLClaimDetailDB.getInfo();
            tLLClaimDetailSchema = ttLLClaimDetailDB.getSchema();
            double tCurrentPay = tLLClaimDetailSchema.getRealPay();

            //领取项表已领金额
            LCGetSchema tLCGetSchema = new LCGetSchema();
            LCGetDB tLCGetDB = new LCGetDB();
            tLCGetDB.setPolNo(tLLClaimDetailSet.get(i).getPolNo());
            tLCGetDB.setGetDutyCode(tLLClaimDetailSet.get(i).getGetDutyCode());
            tLCGetDB.setDutyCode(tLLClaimDetailSet.get(i).getDutyCode());
            tLCGetDB.getInfo();
            tLCGetSchema = tLCGetDB.getSchema();
            double tSumMoney = tLCGetSchema.getSumMoney();

            //计算更新领取项表
            logger.debug("##########当前第"+i+"项申诉赔付金额为:"+tCurrentPay);
            logger.debug("##########原该项赔付金额为:"+tOldPay);
            logger.debug("##########当前已领金额为:"+tSumMoney);
            tSumMoney = tSumMoney + tCurrentPay - tOldPay;
            logger.debug("##########更新后的金额为:"+tSumMoney);
            tLCGetSchema.setSumMoney(tSumMoney);

            map.put(tLCGetSchema, "DELETE&INSERT");
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * No.2 生成财务数据处理
         *----------------------------------------------------------------------*/
        if (!dealData05())
        {
            return false;
        }

        return true;
    }

    /**
     * 生成财务数据处理
     * a) 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
     *    LJAGetClaim赔付实付表后，删除应付数据
     * b) 实付号：PubFun1.CreateMaxNo("GETNO", StrLimit);
     * c) 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付
     * d) 将LLBalance表中的，PayFlag金支付标志置为1，已支付
     */
    private boolean dealData05()
    {
        /**---------------------------------------------------------------------BEG
         * No.1 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
         *      LJAGetClaim赔付实付表后，删除应付数据
         *----------------------------------------------------------------------*/
        //应付总表
        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setOtherNo(mClmNo);
        tLJSGetDB.setOtherNoType("5");
        LJSGetSet tLJSGetSet = new LJSGetSet();
        tLJSGetSet = tLJSGetDB.query();
        if (tLJSGetSet != null && tLJSGetSet.size() != 0)
        {
            LJAGetSchema tLJAGetSchema = new LJAGetSchema();
            mLJAGetSet.add(tLJAGetSchema);
            ref.transFields(mLJAGetSet, tLJSGetSet);
            for (int j = 1; j <= tLJSGetSet.size(); j++)
            {
                String tNo = PubFun1.CreateMaxNo("GETNO", 10); //生成实付号
                mLJAGetSet.get(j).setActuGetNo(tNo);
                mLJAGetSet.get(j).setOperator(mG.Operator);
                mLJAGetSet.get(j).setMakeDate(CurrentDate);
                mLJAGetSet.get(j).setMakeTime(CurrentTime);
                mLJAGetSet.get(j).setModifyDate(CurrentDate);
                mLJAGetSet.get(j).setModifyTime(CurrentTime);
                mLJAGetSet.get(j).setOperState("0");
    //                mLJAGetSet.get(j).setConfDate(CurrentDate);//财务确认日期

                //赔付应付表
                LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
                LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
                tLJSGetClaimDB.setOtherNo(mClmNo);
                tLJSGetClaimDB.setOtherNoType("5");
                tLJSGetClaimDB.setGetNoticeNo(tLJSGetSet.get(j).getGetNoticeNo());
                LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
                tLJSGetClaimSet = tLJSGetClaimDB.query();
                if (tLJSGetClaimSet != null && tLJSGetClaimSet.size() != 0)
                {
                    LJAGetClaimSchema tLJAGetClaimSchema = new
                            LJAGetClaimSchema();
                    tLJAGetClaimSet.add(tLJAGetClaimSchema);
                    ref.transFields(tLJAGetClaimSet, tLJSGetClaimSet);
                    for (int k = 1; k <= tLJSGetClaimSet.size(); k++)
                    {
                        tLJAGetClaimSet.get(k).setActuGetNo(tNo);
                        tLJAGetClaimSet.get(k).setOPConfirmCode(mG.Operator);
                        tLJAGetClaimSet.get(k).setOPConfirmDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setOPConfirmTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setOperator(mG.Operator);
                        tLJAGetClaimSet.get(k).setMakeDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setMakeTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setModifyDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setModifyTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setOperState("0");
    //                        tLJAGetClaimSet.get(k).setConfDate(CurrentDate);//财务确认日期

                        //如果支付金额为零,就不添加到实付表
                        if (tLJAGetClaimSet.get(k).getPay() != 0)
                        {
                            map.put(tLJAGetClaimSet.get(k), "DELETE&INSERT");
                        }

                    }
                    map.put(tLJSGetClaimSet, "DELETE");

                    //如果总给付金额为零,就不添加到实付表
                    if (mLJAGetSet.get(j).getSumGetMoney() != 0)
                    {
                        map.put(mLJAGetSet.get(j), "DELETE&INSERT");
                    }

                }
            }
            map.put(tLJSGetSet, "DELETE");
        }
        //----------------------------------------------------------------------END
        /**---------------------------------------------------------------------BEG
         * No.2 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付
         * --------------------------------------------------------------------*/
        String tUpdateSql1 = "";
        tUpdateSql1 = "update llbnf a set a.casepayflag = '1' where "
                      + "a.clmno = '" + mClmNo + "'";
        map.put(tUpdateSql1, "UPDATE");
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * No.3 将LLBalance表中的，PayFlag金支付标志置为1，已支付
         * --------------------------------------------------------------------*/
        String tUpdateSql2 = "";
        tUpdateSql2 = "update LLBalance a set a.Payflag = '1' where "
                      + "a.clmno = '" + mClmNo + "'";
        map.put(tUpdateSql2, "UPDATE");
        //----------------------------------------------------------------------END
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
            tError.moduleName = "LLAppealConfirmBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
