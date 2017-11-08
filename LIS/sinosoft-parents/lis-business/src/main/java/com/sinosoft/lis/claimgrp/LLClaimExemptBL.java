/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;

import java.util.Date;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:豁免处理提交信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: yuejw
 * @version 1.0
 */
public class LLClaimExemptBL
{
private static Logger logger = Logger.getLogger(LLClaimExemptBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private GlobalInput mG = new GlobalInput();
    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private LLClaimDetailSchema mLLClaimDetailSchema=new LLClaimDetailSchema();
    private LCPremSchema mLCPremSchema=new LCPremSchema();
    private LLExemptSchema mLLExemptSchema=new LLExemptSchema();



    public LLClaimExemptBL()
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
        logger.debug("----------LLClaimExemptBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        mOperate = cOperate;

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
            tError.moduleName = "LLClaimExemptBL";
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
        logger.debug("--start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLLClaimDetailSchema=(LLClaimDetailSchema) mInputData.getObjectByObjectName("LLClaimDetailSchema", 0);
        mLLExemptSchema=(LLExemptSchema) mInputData.getObjectByObjectName("LLExemptSchema", 0);

        if (mG == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的mG信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(mOperate.equals("LLExempt||Get") && mLLClaimDetailSchema==null)
        {
            CError tError = new CError();
           tError.moduleName = "LLClaimExemptBL";
           tError.functionName = "getInputData";
           tError.errorMessage = "接受的用于提取豁免信息的数据全部为空!";
           this.mErrors.addOneError(tError);
           return false;

        }
        if(mOperate.equals("LLExempt||Save") && mLLExemptSchema==null)
        {
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的用于修改豁免信息的数据全部为空!";
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
        logger.debug("----------begin checkInputData----------");
        try
        {
            //非空字段检验
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }

    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----"+cOperate);
        try
        {
            logger.debug("-----------------------------------------------------------------");
            //[提取豁免信息]按钮动作----根据LLClaimDetail中理赔类型为109--意外豁免，209--疾病豁免的赔案，
            //对PolNo保单号，DutyCode责任编码进行分组，根据分组的结果，提取LCPrem的全部数据放入到LLExempt表中
            if (cOperate.equals("LLExempt||Get"))
            {

                String tClmNo = mLLClaimDetailSchema.getClmNo();
                String tSqlDel = "delete from LLExempt where ClmNo='"+tClmNo+"'";
                map.put(tSqlDel, "DELETE");

                String tSqlSel = "select a.* from LCPrem a where 1=1 "
                    +" and a.polno in "
                        +" (select distinct polno from llclaimdetail where clmno='" + tClmNo +"'"
                        +" and getdutykind in('109','209') and GiveType !='1')"
                    ;

                LCPremDB tLCPremDB = new LCPremDB();
                LCPremSet tLCPremSet = tLCPremDB.executeQuery(tSqlSel);
                if(tLCPremDB.mErrors.needDealError())
                {
                     CError tError = new CError();
                     tError.moduleName = "LLClaimExemptBL";
                     tError.functionName = "dealdata";
                     tError.errorMessage = "查询险种保单号["+tLCPremDB.getPolNo()+"]的缴费信息失败!";
                     this.mErrors.addOneError(tError);
                     return false;
                }


                LLExemptSet tLLExemptSet = new LLExemptSet();
                for (int j = 1; j <= tLCPremSet.size(); j++)
                {
                    LLExemptSchema tLLExemptSchema = new LLExemptSchema();
                    LCPremSchema tLCPremSchema = tLCPremSet.get(j);

                    if (tLCPremSchema.getPayIntv() == 0 )
                    {

                        CError tError = new CError();
                        tError.moduleName = "LLClaimExemptBL";
                        tError.functionName = "dealdata";
                        tError.errorMessage = "险种保单号["+tLCPremSchema.getPolNo()+"]的缴费方式为趸交,不能进行豁免!";
                        this.mErrors.addOneError(tError);
                        logger.debug("--险种保单号["+tLCPremSchema.getPolNo()+"]的缴费方式为趸交,不能进行豁免!");
                        continue;
                    }
                    Reflections tRef = new Reflections();
                    tRef.transFields(tLLExemptSchema,tLCPremSchema);

                    tLLExemptSchema.setClmNo(tClmNo); //[赔案号]
                    tLLExemptSchema.setOperator(mG.Operator);
                    tLLExemptSchema.setMakeDate(CurrentDate);
                    tLLExemptSchema.setMakeTime(CurrentTime);
                    tLLExemptSchema.setModifyDate(CurrentDate);
                    tLLExemptSchema.setModifyTime(CurrentTime);
                    tLLExemptSchema.setManageCom(mG.ManageCom);
                    tLLExemptSet.add(tLLExemptSchema);
                }



                logger.debug("--获取豁免信息["+tLCPremSet.size()+"]--"+tSqlSel);
                logger.debug("-----------------------------------------------------------------");
                map.put(tLLExemptSet, "DELETE&INSERT");
            }

            //[修改保存豁免信息]按钮动作----修改
            if (cOperate.equals("LLExempt||Save"))
            {
                LLExemptSchema tLLExemptSchema = new LLExemptSchema();
                LLExemptDB tLLExemptDB = new LLExemptDB();
                tLLExemptDB.setClmNo(mLLExemptSchema.getClmNo()); //传入赔案号
                tLLExemptDB.setPolNo(mLLExemptSchema.getPolNo()); //保单险种号码
                tLLExemptDB.setDutyCode(mLLExemptSchema.getDutyCode()); //责任编码
                tLLExemptDB.setPayPlanCode(mLLExemptSchema.getPayPlanCode()); //交费计划编码
                tLLExemptDB.getInfo();
                tLLExemptSchema.setSchema(tLLExemptDB.getSchema());
                tLLExemptSchema.setFreeFlag(mLLExemptSchema.getFreeFlag());//免交标志
                tLLExemptSchema.setFreeRate(mLLExemptSchema.getFreeRate());//免交比率
                tLLExemptSchema.setFreeStartDate(mLLExemptSchema.getFreeStartDate());//免交起期
                tLLExemptSchema.setFreeEndDate(mLLExemptSchema.getFreeEndDate());//免交止期
                tLLExemptSchema.setExemptReason(mLLExemptSchema.getExemptReason());//豁免原因
                tLLExemptSchema.setExemptDesc(mLLExemptSchema.getExemptDesc());//豁免描述

                //计算“豁免期数----ExemptPeriod”
                LLClaimPubFunBL tLLClaimPubFunBL=new LLClaimPubFunBL();
                String tFreeStartDate=tLLExemptSchema.getFreeStartDate();
                String tFreeEndDate=tLLExemptSchema.getFreeEndDate();
                String tPayIntv= String.valueOf(tLLExemptSchema.getPayIntv());
//               int tMonth= PubFun.calInterval(tLLExemptSchema.getFreeStartDate(), tLLExemptSchema.getFreeEndDate(),"M");
               double tExemptPeriod=0;
               if(tLLExemptSchema.getFreeFlag().equals("0"))//0--不免交
               {
                   tExemptPeriod=0;
               }
               else
               {
                   //tExemptPeriod=tLLClaimPubFunBL.getLCPremPeriodTimes(tFreeStartDate,tFreeEndDate,tPayIntv);
               }
               tLLExemptSchema.setExemptPeriod(String.valueOf(tExemptPeriod));
               tLLExemptSchema.setModifyDate(CurrentDate);
               tLLExemptSchema.setModifyTime(CurrentTime);
               map.put(tLLExemptSchema, "DELETE&INSERT");
            }

            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptBL";
            tError.functionName = "dealData";
            tError.errorMessage = "在处理输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
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
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
    }


    public static void main(String[] args)
    {



        LLReportSchema tLLReportSchema = new LLReportSchema();


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */


        String tClmNo = "90000001810";
        String tOperate = "LLExempt||Get";

        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setCaseNo(tClmNo);
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();


        if (tLLClaimDetailSet.size()==0)
        {
            logger.debug("----------------------无事故信息-----------------------");
            return;
        }
        LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(1);


        GlobalInput tG = new GlobalInput();
        tG.Operator=tLLClaimDetailSchema.getOperator();
        tG.ManageCom = tLLClaimDetailSchema.getMngCom();
        tG.ComCode = tLLClaimDetailSchema.getMngCom();

        LLExemptSchema tLLExemptSchema = new LLExemptSchema();

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tLLClaimDetailSchema);
        tVData.addElement(tLLExemptSchema);

        LLClaimExemptBL tLLClaimExemptBL = new LLClaimExemptBL();
        tLLClaimExemptBL.submitData(tVData,tOperate);
        int n = tLLClaimExemptBL.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
                String Content = "";
                Content = Content + "提示信息: " + tLLClaimExemptBL.mErrors.getError(i).errorMessage;
                logger.debug(Content);
        }

    }


}
