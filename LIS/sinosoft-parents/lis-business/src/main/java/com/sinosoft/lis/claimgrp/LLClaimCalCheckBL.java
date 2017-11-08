package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 计算步骤一：理赔计算前的数据校验</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalCheckBL
{
private static Logger logger = Logger.getLogger(LLClaimCalCheckBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

    private String mAccNo    = "";     //事件号
    private String mAccDate  = "";     //意外事故发生日期
    private String mClmNo    = "";     //赔案号
    private String mCusNo    = "";     //客户号
    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
    private String mGetDutyKind;       //理赔类型
    private String mGetDutyCode;       //责任编码


    public LLClaimCalCheckBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------理算步骤一-----数据校验-----LLClaimCalCheckBL测试-----开始----------");

//        Reflections rf = new Reflections();
//        rf.transFields(mLLClaimPolicySchema,LLCasePolicyDB.getSchema());

        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }


        if (!dealData())
        {
            return false;
        }


//        if (!pubSubmit())
//        {
//            return false;
//        }

        logger.debug("----------理算步骤一-----数据校验-----LLClaimCalCheckBL测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {

        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期
        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人总投保单,2-集体总单

        //logger.debug("this.mCusNo"+this.mCusNo);
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        //校验事件信息
        if (!getLLAccidentInfo())
        {
            return false;
        }

        //校验事件赔案关联信息
        if (!this.getLLCaseRelaInfo())
        {
            return false;
        }

        //校验立案信息
        if (!getLLRegisterInfo())
        {
            return false;
        }

        //校验立案分案信息
        if (!this.getLLCaseInfo())
        {
            return false;
        }

        return true;

    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－开始－－－－－－－进行数据校验－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 目的：找出该赔案对应的事件信息
     * @return
     */
    private boolean getLLAccidentInfo()
    {
        logger.debug("------进入getLLAccidentInfo函数------");
        logger.debug("------得到事件号：------"+this.mAccNo);
        logger.debug("------得到出险日期：------"+this.mAccDate);
        LLAccidentDB tLLAccidentDB = new LLAccidentDB();
        String tSql = "select * from LLAccident where 1 = 1 "
            +" and AccNo in "
                +" (select AccNo from LLAccidentSub where AccNo = '"+this.mAccNo+"') ";
            //+" and AccDate = to_date('"+this.mAccDate+ "','yyyy-mm-dd') ";
    logger.debug("------得到事件号：------"+tSql);

        LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(tSql);
         logger.debug("------得到LLAccident：------"+tLLAccidentSet.size());
        if (tLLAccidentSet == null || tLLAccidentSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLAccidentDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "getLLAccidentInfo";
            tError.errorMessage = "在LLAccident表中没有查找到相关的事件信息，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;

    }


    /**
     * 目的：找出该赔案对应的事件赔案关联信息
     * @return
     */
    private boolean getLLCaseRelaInfo()
    {

        LLCaseRelaDB tLLCaseRelaDB = new LLCaseRelaDB();
        tLLCaseRelaDB.setCaseRelaNo(this.mAccNo);                   //事件号
        tLLCaseRelaDB.setCaseNo(this.mClmNo);                       //赔案号
        LLCaseRelaSet tLLCaseRelaSet = tLLCaseRelaDB.query();
        if (tLLCaseRelaSet == null || tLLCaseRelaSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLCaseRelaSet.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "getLLCaseRelaInfo";
            tError.errorMessage = "在LLCaseRela表中没有查找到事件赔案关联信息，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;

    }


    /**
     * 查找并判断是否有立案信息
     * @return
     */
    private boolean getLLRegisterInfo()
    {
        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(this.mClmNo);
        tLLRegisterDB.getInfo();

        if (tLLRegisterDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "getLLRegisterInfo";
            tError.errorMessage = "在LLRegister表中没有找到立案信息，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        mLLRegisterSchema=tLLRegisterDB.getSchema();
        
        return true;
    }

    /**
     * 判断立案分案的赔案状态，RgtState
     * @return
     */
    private boolean getLLCaseInfo()
    {
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(this.mClmNo);

        LLCaseSet tLLCaseDBSet = tLLCaseDB.query();
        if (tLLCaseDBSet == null || tLLCaseDBSet.size() == 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLCaseDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "getLLCaseInfo";
            tError.errorMessage = "在LLCaser表中没有找到立案分案信息，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {

        mInputData.clear();
        mInputData.add(mMMap);
        mResult.add(mLLRegisterSchema);
        
        return true;
    }

    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－结束－－－－－－－进行数据校验－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    public VData getResult()
    {
        return mResult;
    }


    public static void main(String[] args)
    {


        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";


        String tAccNo = "80000000630";
        String tClmNo = "90000000690";
        String tAccDate = "2005-05-30";
        String tCusNo = "0000497310";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("CusNo",tCusNo);
        tTransferData.setNameAndValue("ContType","1");  //总单类型,1-个人总投保单,2-集体总单

        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        tLLCaseSchema.setCaseNo(tClmNo);
        tLLCaseSchema.setRgtNo(tClmNo);
        tLLCaseSchema.setCustomerNo(tCusNo);

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);
        tVData.addElement(tLLCaseSchema);


        LLClaimCalCheckBL tLLClaimCalCheckBL = new LLClaimCalCheckBL();
        tLLClaimCalCheckBL.submitData(tVData, "");


    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－以下程序为废弃不用或者没有进行整理－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 【废弃不用】
     * 目的：从分案收据明细中查询事故者发生费用总金额
     * @return double
     * 【废弃不用】
     */
    private double getInpVal()
    {
        double tInpval = 0;

        String sql = "select sum(fee) from LLCaseReceipt where MainFeeNo in "
            + " (select MainFeeNo from LLFeeMain where  clmNo='" + this.mClmNo
//            + "' and caserelano='" + this.mLLClaimPolicySchema.getCaseRelaNo() + "'"
          //  +"' and FeeType='0'"
          //加描述表中的信息
            + " and FeeItemCode in (select Feecode from LMDutyGetFeeRela where getdutykind='"
            + this.mGetDutyKind + "' and getdutycode='" +
            this.mGetDutyCode + "'))";

        ExeSQL tExeSQL = new ExeSQL();
        String tFee = tExeSQL.getOneValue(sql);

        if (tFee != null && tFee.length() > 0)
        {
            return Double.parseDouble(tFee);
        }

        return tInpval;
    }



    /**
     * 【废弃不用】
     * 目的：计算各个类别的项目金额
     * @return double
     * 【废弃不用】
     */
    private SSRS getAvaliFee()
    {

        //* Fee--费用金额
        //* PreAmnt--先期给付
        //* SelfAmnt--自费金额
        //* 不合理费用--RefuseAmnt
        double tInpval = 0;
        String sql =
          "select sum(fee),sum(preamnt),sum(selfamnt),sum(refuseamnt)" +
              " from LLCaseReceipt where MainFeeNo in "
              + " (select MainFeeNo from LLFeeMain where  claimno='" + this.mClmNo
              //+ "' and caserelano='" +  this.mLLClaimPolicySchema.getCaseRelaNo() + "')"
              //  +"' and FeeType='0'"
              //加描述表中的信息
              + " and FeeItemCode in (select Feecode from LMDutyGetFeeRela where getdutykind='"
              + this.mGetDutyKind + "' and getdutycode='" + this.mGetDutyCode + "')";

        //原始金额，调整金额，理算金额，
//        String tSql = "select sum(OriSum),sum(AdjSum),sum(CalSum)"
        ExeSQL exesql = new ExeSQL();
        return exesql.execSQL(sql);
    }


    /**
     * 【废弃不用】
     * 目的：计算保单在责任下的累计赔付金额
     * 描述：
     * @param pPolNo String
     * @param pDutyCode String
     * @return double
     * 【废弃不用】
     */
    private double getSumDutyPay(String pPolNo, String pDutyCode)
    {
        LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();

        String tSql = "select * from llclaimdetail where polno='" +
                      pPolNo + "' and dutycode ='" + pDutyCode + "' ";
        tSql = tSql +
                  " and clmno in(select clmno from llclaim where ClmState='2' or ClmState='3')";

        logger.debug("ClaimCalAutoBL--getSumDutyPay()--"+tSql);

        tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tSql);
        logger.debug("LLClaimDetailSet.size=" + tLLClaimDetailSet.size());

        double tSumDuty = 0;
        for (int k = 1; k <= tLLClaimDetailSet.size(); k++)
        {
            LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
            tLLClaimDetailSchema = tLLClaimDetailSet.get(k);
            tSumDuty = tSumDuty + tLLClaimDetailSchema.getRealPay();
        }
        return tSumDuty;
    }






//    /**
//     * 保险人个人帐户的帐户金额
//     * @return double
//     */
//    private double getInsuAccBala()
//    {
//      double tInsuAccBala = 0;
//      tInsuAccBala = Double.parseDouble(new DecimalFormat("0.00").format(
//          tInsuAccBala));
//      LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
//      LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
//      tLCInsureAccDB.setPolNo(mLCPolSchema.getPolNo());
//      tLCInsureAccSet.set(tLCInsureAccDB.query());
//      for (int i = 1; i <= tLCInsureAccSet.size(); i++)
//      {
//        LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
//        tLCInsureAccSchema.setSchema(tLCInsureAccSet.get(i));
//        if (tLCInsureAccSchema.getAccType().equals("004"))
//        {
//          tInsuAccBala = tInsuAccBala + tLCInsureAccSchema.getInsuAccBala();
//        }
//      }
//      return tInsuAccBala;
//    }




//    //计算历次交费的本息和
//    private double getPremIntSum()
//    {
//      double tReturnGet = 0.0;
//      LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
//      LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
//      tLJAPayPersonDB.setPolNo(mLCPolSchema.getPolNo());
//      tLJAPayPersonDB.setPayType("ZC");
//      tLJAPayPersonSet = tLJAPayPersonDB.query();
////      LMLoanDB tLMLoanDB = new LMLoanDB();
////      tLMLoanDB.setRiskCode(mLCPolSchema.getRiskCode());
////      tLMLoanDB.getInfo();
//      double rate = 0.025;
//      AccountManage tAccountManage = new AccountManage();
//      for (int i = 1; i <= tLJAPayPersonSet.size(); i++)
//      {
//        double interest = 0.0;
//        LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
//        tLJAPayPersonSchema = tLJAPayPersonSet.get(i);
//        interest = tAccountManage.
//            getInterest(tLJAPayPersonSchema.
//                        getSumActuPayMoney(),
//                        tLJAPayPersonSchema.
//                        getConfDate(),
//                        mLLRegisterSchema.
//                        getAccidentDate(),
//                        rate, "1", "2", "D");
//        tReturnGet += tLJAPayPersonSchema.getSumActuPayMoney() + interest;
//      }
//      return tReturnGet;
//    }



//    /**
//     * 开始领取但尚未领完的年金
//     * @return double
//     */
//    private double getRemainAnn()
//    {
//      SynLCLBGetBL aSynLCLBGetBL = new SynLCLBGetBL();
//      aSynLCLBGetBL.setPolNo(this.mLCPolSchema.getPolNo());
//
////      LCGetDB tLCGetDB = new LCGetDB();
//      LCGetSet tLCGetSet = new LCGetSet();
////      tLCGetDB.setPolNo(this.mLCPolSchema.getPolNo());
//
//      //tLCGetSet=tLCGetDB.query();
//      tLCGetSet = aSynLCLBGetBL.query();
//      double tReturnMoney = 0;
//      for (int i = 1; i <= tLCGetSet.size(); i++)
//      {
//        LCGetSchema tLCGetSchema = new LCGetSchema();
//        tLCGetSchema = tLCGetSet.get(i);
//        if (tLCGetSchema.getLiveGetType().equals("1"))
//        {
//          continue;
//        }
//        FDate tFDate = new FDate();
//        if (tFDate.getDate(tLCGetSchema.getGetStartDate()).
//            after(tFDate.getDate(mLLRegisterSchema.getAccidentDate())))
//        {
//          continue;
//        }
//
//        if (tLCGetSchema.getGetIntv() == 0)
//        {
//          continue;
//        }
//
//        int NoneGetNum = PubFun.calInterval(tLCGetSchema.getGettoDate(),
//                                            tLCGetSchema.getGetEndDate(),
//                                            "M") / tLCGetSchema.getGetIntv();
//        tReturnMoney += NoneGetNum * tLCGetSchema.getStandMoney();
//      }
//
//      return tReturnMoney;
//    }






//  private double getDoorPostFee() //门诊费用
//  {
//    double hospFee = 0;
//    hospFee =
//        Double.parseDouble(
//            new DecimalFormat("0.00").format(hospFee));
//    int i;
//    String sql = "select sum(fee) from LLCaseReceipt where MainFeeNo in "
//        + "(select MainFeeNo from LLFeeMain where  caseno='" +
//        mLLCaseSchema.getCaseNo()
//        + "' and caserelano='" +
//        this.mLLClaimPolicySchema.getCaseRelaNo()
//        + "' and FeeType='0'"
//        //加描述表中的信息
//        +
//        " and FeeItemCode in (select Feecode from LMDutyGetFeeRela where getdutykind='"
//        + this.mGetDutyKind + "' and getdutycode='" +
//        this.mGetDutyCode + "'))";
//
//    ExeSQL exesql = new ExeSQL();
//    String res = exesql.getOneValue(sql);
//    if (res != null && res.length() > 0)
//    {
//      return Double.parseDouble(res);
//    }
//
//    return hospFee;
//  }



//  private double getInHospFee() //住院费用
//  {
//    double hospFee = 0;
//    hospFee = Double.parseDouble(new DecimalFormat("0.00").format(hospFee));
//    String sql = "select sum(fee) from LLCaseReceipt where MainFeeNo in "
//        + "(select MainFeeNo from LLFeeMain where  caseno='" +
//        mLLCaseSchema.getCaseNo()
//        + "' and caserelano='" +
//        this.mLLClaimPolicySchema.getCaseRelaNo()
//        + "' and FeeType='1'"
//        //加描述表中的信息
//        +
//        " and FeeItemCode in (select Feecode from LMDutyGetFeeRela where getdutykind='"
//        + this.mGetDutyKind + "' and getdutycode='" +
//        this.mGetDutyCode + "'))";
//
//    ExeSQL exesql = new ExeSQL();
//    String res = exesql.getOneValue(sql);
//    if (res != null && res.length() > 0)
//    {
//      return Double.parseDouble(res);
//    }
//
//    return hospFee;
//  }


//  /**
//   * 计算各项费用
//   * @return boolean
//   */
//  private boolean calItemFee()
//  {
//    String[] sqltable =
//        {
//        "llfeeotheritem", "LLCaseDrug"};
//    for (int i = 0; i < sqltable.length; i++)
//    {
//      String sql = " select avliflag,sum(feemoney) from  " + sqltable[i]
//          + " where caseno='aa' "
//          + " and mainfeeno in ("
//          +
//          " select mainfeeno from llfeemain where caseno='aa' "
//          + " and caserelano in ( select caserelano from llcasepolicy where caseno='aa' and polno='bb')"
//          + ")"
//          + " group by avliflag   order by avliflag ";
//      ExeSQL execsql = new ExeSQL();
//      SSRS ssrs = execsql.execSQL(sql);
//      if (ssrs != null)
//      {
//        for (int j = 1; j <= ssrs.getMaxRow(); j++)
//        {
//          String avliflag = ssrs.GetText(j, 1);
//          double money = Double.parseDouble(ssrs.GetText(j, 2));
//          if ("0".equals(avliflag))
//          {
//            //正常给付
//            //  mLLClaimPolicySchema.set
//          }
//          if ("1".equals(avliflag))
//          {
//            //先期给付
//            mLLClaimPolicySchema.setPreGiveAmnt(PubFun.setPrecision(
//                mLLClaimPolicySchema.getPreGiveAmnt() + money,
//                "0.00"));
//          }
//
//          if ("2".equals(avliflag))
//          {
//            mLLClaimPolicySchema.setSelfGiveAmnt(PubFun.
//                                                 setPrecision(
//                mLLClaimPolicySchema.
//                getSelfGiveAmnt() + money, "0.00"));
//          }
//          if ("3".equals(avliflag))
//          {
//            mLLClaimPolicySchema.setRefuseAmnt(PubFun.setPrecision(
//                mLLClaimPolicySchema.getRefuseAmnt() + money,
//                "0.00"));
//          }
//
//        }
//      }
//    }
//    return true;
//  }


}
