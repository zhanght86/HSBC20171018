/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


/**
 * 管理费处理
 * <p>Title: </p>
 * <p>Description: 接收前台传入的管理费数据 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 朱向峰
 * @version 1.0
 */
public class GrpFeeUI
{
private static Logger logger = Logger.getLogger(GrpFeeUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();


    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();


    /** 数据操作字符串 */
    private String mOperate;


//业务处理相关变量
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LCGrpFeeSet mLCGrpFeeSet = new LCGrpFeeSet();
    private LCGrpFeeParamSet mLCGrpFeeParamSet = new LCGrpFeeParamSet();
    public GrpFeeUI()
    {
    }


    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;
        //进行业务处理
        if (!dealData())
            return false;
        //准备往后台的数据
        if (!prepareOutputData())
            return false;
        GrpFeeBL tGrpFeeBL = new GrpFeeBL();
        logger.debug("Start GrpFee UI Submit...");
        tGrpFeeBL.submitData(mInputData, mOperate);
        logger.debug("End GrpFee UI Submit...");
        //如果有需要处理的错误，则返回
        if (tGrpFeeBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tGrpFeeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "GrpFeeUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mOperate.equals("QUERY||MAIN"))
        {
            this.mResult.clear();
            this.mResult = tGrpFeeBL.getResult();
        }
        mInputData = null;
        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * @return boolean
     * 如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(this.mGlobalInput);
            mInputData.add(this.mLCGrpFeeSet);
            mInputData.add(this.mLCGrpFeeParamSet);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpFeeUI";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     * @return boolean
     */
    private static boolean dealData()
    {

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @param cInputData VData
     * @return boolean
     * 如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        this.mLCGrpFeeSet.set((LCGrpFeeSet) cInputData.
                              getObjectByObjectName("LCGrpFeeSet", 0));

        //Modify By Shuzhan 不需进行该处理
        /*LCGrpFeeSchema tLCGrpFeeSchema;
        ExeSQL tExeSQL=new ExeSQL();

        for(int i=1;i<=mLCGrpFeeSet.size();i++)
        {
           tLCGrpFeeSchema=new LCGrpFeeSchema();
           tLCGrpFeeSchema=mLCGrpFeeSet.get(i);
            //如果设置的是外缴，那么不用设置收费来源
            //首先判断是不是万能，只有万能产品才有收费来源需求。
            String tRiskCode="1"+tLCGrpFeeSchema.getInsuAccNo().substring(0,2);
            logger.debug("RiskCode:"+tRiskCode);
            LMRiskAppDB tLMRiskAppDB=new LMRiskAppDB();
            LMRiskAppSet tLMRiskAppSet=new LMRiskAppSet();
            tLMRiskAppDB.setRiskCode(tRiskCode);
            tLMRiskAppSet=tLMRiskAppDB.query();
            if(tLMRiskAppSet.get(1).getBonusFlag().equals("2"))
            {
                if(tLCGrpFeeSchema.getFeeCalCode()==null || tLCGrpFeeSchema.getFeeCalCode().equals(""))
                {
                           CError tError = new CError();
                           tError.moduleName = "GrpFeeUI";
                           tError.functionName = "getInputData";
                           tError.errorMessage = "请选择缴费账户！";
                           this.mErrors.addOneError(tError);
                           return false;

                }
                //法人账户不可以的收费来源不能指定为个人账户
           SSRS tSSRS=new SSRS();
           String tsql="select owner from lmriskinsuacc where insuaccno =(select feecode from lmriskfee where insuaccno='"+tLCGrpFeeSchema.getInsuAccNo()+"')";
           tSSRS=tExeSQL.execSQL(tsql);
           if(tSSRS.MaxRow>0)
           {
               String towner=tSSRS.GetText(1,1);
               if(towner!=null && towner.equals("1"))
               {
                 if(tLCGrpFeeSchema.getFeeCalCode()!=null && tLCGrpFeeSchema.getFeeCalCode().equals("00"))
                 {
                          CError tError = new CError();
                          tError.moduleName = "GrpFeeUI";
                          tError.functionName = "getInputData";
                          tError.errorMessage = "法人账户的收费来源只能是法人账户！";
                          this.mErrors.addOneError(tError);
                          return false;

                 }

               }
           }


            }else{
                if (tLCGrpFeeSchema.getFeeCalCode() == null ||
                        tLCGrpFeeSchema.getFeeCalCode().equals("")) {

                    } else {
                        CError tError = new CError();
                        tError.moduleName = "GrpFeeUI";
                        tError.functionName = "getInputData";
                        tError.errorMessage = "目前只有万能产品支持指定收费来源！";
                        this.mErrors.addOneError(tError);
                        return false;

                    }

            }


        }*/
        this.mLCGrpFeeParamSet.set((LCGrpFeeParamSet) cInputData.
                                   getObjectByObjectName(
                "LCGrpFeeParamSet", 0));
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpFeeUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    public static void main(String[] args)
    {
//        LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
//        LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
//
//        tLCGrpFeeSchema.setGrpPolNo("0001");
//        tLCGrpFeeSchema.setGrpContNo("86110020040120000104");
//        tLCGrpFeeSchema.setRiskCode("001");
//        tLCGrpFeeSchema.setPayPlanCode("00000000");
//        tLCGrpFeeSchema.setInsuAccNo("1");
//        tLCGrpFeeSchema.setFeeCalMode("01");
//        tLCGrpFeeSet.add(tLCGrpFeeSchema);
//
//        GlobalInput tG = new GlobalInput();
//        tG.ComCode = "8611";
//        tG.ManageCom = "8611";
//        tG.Operator = "actest";
//
//        VData tVData = new VData();
//        tVData.add(tG);
//        tVData.add(tLCGrpFeeSet);
//
//        GrpFeeUI tGrpFeeUI = new GrpFeeUI();
//        tGrpFeeUI.submitData(tVData, "INSERT||MAIN");
    }
}
