package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.*;
import java.text.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BonusNoticeGrpPolListBL
{
private static Logger logger = Logger.getLogger(BonusNoticeGrpPolListBL.class);

    public CErrors mErrors=new CErrors();
    private VData mResult = new VData();

    private GlobalInput mGlobalInput =new GlobalInput() ;
    private LOBonusGrpPolParmSchema mLOBonusGrpPolParmSchema = new LOBonusGrpPolParmSchema();

    private String mOperate="";
    private String CurrentDate = PubFun.getCurrentDate();
    /*转换精确位数的对象   */
    private String FORMATMODOL="0.00";//保费保额计算出来后的精确位数
    private DecimalFormat mDecimalFormat=new DecimalFormat(FORMATMODOL);//数字转换对象
    private int mCount=100; //每次循环处理的纪录数
    public BonusNoticeGrpPolListBL()
    {
    }
    public static void main(String[] args)
    {
        BonusNoticeGrpPolListBL bonusNoticeGrpPolListBL1 = new BonusNoticeGrpPolListBL();
        GlobalInput tGlobalInput =new GlobalInput();
        tGlobalInput.Operator="001";
        tGlobalInput.ComCode="86110000";
        tGlobalInput.ManageCom="86110000";
        LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema =new LOBonusGrpPolParmSchema();
        tLOBonusGrpPolParmSchema.setGrpPolNo("86110020030220000022");
        tLOBonusGrpPolParmSchema.setFiscalYear("2003");
        VData tVData=new VData();
        tVData.add(tLOBonusGrpPolParmSchema);
        tVData.add(tGlobalInput);
        bonusNoticeGrpPolListBL1.submitData(tVData,"CONFIRM");
    }

    /**
  * 传输数据的公共方法
  * @param cInputData
  * @param cOperate
  * @return
  */
 public boolean submitData(VData cInputData, String cOperate) {
   mOperate = cOperate;
   try {
     if( !cOperate.equals("CONFIRM") &&
         !cOperate.equals("PRINT")) {
       buildError("submitData", "不支持的操作字符串");
       return false;
     }

     if( !getInputData(cInputData) ) {
       return false;
     }

     if( cOperate.equals("CONFIRM") )
     {
       mResult.clear();
       // 准备所有要打印的数据
       getPrintData();
     }
     return true;

   } catch (Exception ex) {
     ex.printStackTrace();
     buildError("submitData", ex.toString());
     return false;
   }
 }

 /**
  * 根据前面的输入数据，进行BL逻辑处理
  * 如果在处理过程中出错，则返回false,否则返回true
  */
 private boolean dealData() {
     return true;
 }

 /**
  * 从输入数据中得到所有对象
  * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  */
 private boolean getInputData(VData cInputData) {
   //全局变量
   mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
   mLOBonusGrpPolParmSchema.setSchema((LOBonusGrpPolParmSchema)cInputData.getObjectByObjectName("LOBonusGrpPolParmSchema",0));

   if( mGlobalInput==null||mLOBonusGrpPolParmSchema==null )
   {
     buildError("getInputData", "没有得到足够的信息！");
     return false;
   }

   return true;
 }
//得到返回值
 public VData getResult() {
   return this.mResult;
 }

 private void buildError(String szFunc, String szErrMsg) {
   CError cError = new CError( );

   cError.moduleName = "RefuseAppF1PBL";
   cError.functionName = szFunc;
   cError.errorMessage = szErrMsg;
   this.mErrors.addOneError(cError);
 }

// 准备所有要打印的数据
 private void getPrintData()
     throws Exception
 {
   XmlExport xmlExport = new XmlExport();//新建一个XmlExport的实例
   xmlExport.createDocument("BonusNoticeGrpList.vts", "");//最好紧接着就初始化xml文档

   String tGrpPolNo=mLOBonusGrpPolParmSchema.getGrpPolNo();
   int tFiscalYear=mLOBonusGrpPolParmSchema.getFiscalYear();
   LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();

   tLCGrpPolDB.setGrpPolNo(mLOBonusGrpPolParmSchema.getGrpPolNo());
   if( !tLCGrpPolDB.getInfo() )
   {
     mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
     throw new Exception("在获取团体保单信息时出错！");
   }
   LCGrpPolSchema tLCGrpPolSchema=tLCGrpPolDB.getSchema();

   LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema =new LOBonusGrpPolParmSchema();
   LOBonusGrpPolParmDB tLOBonusGrpPolParmDB=new LOBonusGrpPolParmDB();
   tLOBonusGrpPolParmDB.setGrpPolNo(tGrpPolNo);
   tLOBonusGrpPolParmDB.setFiscalYear(tFiscalYear);
   if( !tLOBonusGrpPolParmDB.getInfo() )
   {
     mErrors.copyAllErrors(tLOBonusGrpPolParmDB.mErrors);
     throw new Exception("在获取团体保单红利参数表信息时出错！");
   }
   tLOBonusGrpPolParmSchema=tLOBonusGrpPolParmDB.getSchema();

   TextTag texttag = new TextTag();
   texttag.add("GrpPolNo", tLCGrpPolSchema.getGrpPolNo());
   texttag.add("GrpName", tLCGrpPolSchema.getGrpName());


   //1-续保缴费险种信息：
   ListTable tRiskListTable = new ListTable();
   String []RiskInfoTitle = new String[14];
   String []RiskInfo = new String[14];
   tRiskListTable.setName("BONUS");//对应模版投保信息部分的行对象名
   RiskInfoTitle[0]="Sequence";//序号
   RiskInfoTitle[1]="InsuredName";    //被保险人
   RiskInfoTitle[2]="LastYearMoney";//上年帐户资金余额
   RiskInfoTitle[3]="ThisYearPay";    //本年交费
   RiskInfoTitle[4]="PerInputMoney";    //个人帐户转入金额
   RiskInfoTitle[5]="PerOutMoney";    //个人帐户转出金额
   RiskInfoTitle[6]="CorPayInst";    //单位交费累计受益
   RiskInfoTitle[7]="PerPayInst";    //个人交费累计受益
   RiskInfoTitle[8]="CorPayBonus";    //单位交费红利
   RiskInfoTitle[9]="PerPayBonus";    //个人交费红利
   RiskInfoTitle[10]="CorPayManFee";    //单位交费管理费
   RiskInfoTitle[11]="PerPayManFee";    //个人交费管理费
   RiskInfoTitle[12]="CorPayAccMony";    //单位交费帐户余额
   RiskInfoTitle[13]="PerPayAccMony";    //个人交费帐户余额

   String sqlStr="select PolNo,InsuredName from LCPol where grppolno='"+tLCGrpPolSchema.getGrpPolNo()+"' and appflag='1' and exists (select 1 from lcinsureacc where polno = lcpol.polno and state not in ('1','4'))";
   ExeSQL tExeSQL = new ExeSQL();
   SSRS tSSRS=tExeSQL.execSQL(sqlStr);
   for( int i = 1; i <=tSSRS.getMaxRow(); i++ )
   {
       String tPolNo=tSSRS.GetText(i,1);
       String tInsuredName=tSSRS.GetText(i,2);
       //计算上年结转
       double LastYearSumMoney=0.0;
       LastYearSumMoney=calLastYearSumMoney(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年交费
       double CurrentYearFee=0.0;
       CurrentYearFee=calCurrentYearFee(tLOBonusGrpPolParmSchema,tPolNo,tLCGrpPolSchema);
       //计算本年帐户转入资金
       double CurrentYearAccInFee=0.0;
       CurrentYearAccInFee=calCurrentYearAccInFee(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年帐户转入资金
       double CurrentYearAccOutFee=0.0;
       CurrentYearAccOutFee=calCurrentYearAccOutFee(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年累积收益-单位交费
       double CurrentYearIntGrp=0.0;
       CurrentYearIntGrp=calInterestMoneyForGrp(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年累积收益-个人交费
       double CurrentYearIntPol=0.0;
       CurrentYearIntPol=calInterestMoneyForPol(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年分配红利-单位交费
       double CurrentYearBonusGrp=0.0;
       CurrentYearBonusGrp=calBonusMoneyForGrp(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年分配红利-个人交费
       double CurrentYearBonusPol=0.0;
       CurrentYearBonusPol=calBonusMoneyForPol(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年管理费-单位交费
       double CurrentYearManFeeGrp=0.0;
       CurrentYearManFeeGrp=calManFeeForGrp(tLOBonusGrpPolParmSchema,tPolNo,tLCGrpPolSchema);
       //计算本年管理费-个人交费
       double CurrentYearManFeePol=0.0;
       CurrentYearManFeePol=calManFeeForPol(tLOBonusGrpPolParmSchema,tPolNo,tLCGrpPolSchema);
       //计算本年帐户资金余额-单位交费
       double CurrentYearSumMoneyFeeGrp=0.0;
       CurrentYearSumMoneyFeeGrp=calLastYearSumMoneyForGrp(tLOBonusGrpPolParmSchema,tPolNo);
       //计算本年帐户资金余额-个人交费
       double CurrentYearSumMoneyFeePol=0.0;
       CurrentYearSumMoneyFeePol=calLastYearSumMoneyForPol(tLOBonusGrpPolParmSchema,tPolNo);

       RiskInfo = new String[14];
       RiskInfo[0]=String.valueOf(i);//序号
       RiskInfo[1]=tInsuredName;    //被保险人
       RiskInfo[2]=String.valueOf(LastYearSumMoney);//上年帐户资金余额
       RiskInfo[3]=String.valueOf(CurrentYearFee);    //本年交费
       RiskInfo[4]=String.valueOf(CurrentYearAccInFee);    //个人帐户转入金额
       RiskInfo[5]=String.valueOf(CurrentYearAccOutFee);    //个人帐户转出金额
       RiskInfo[6]=String.valueOf(CurrentYearIntGrp);    //单位交费累计受益
       RiskInfo[7]=String.valueOf(CurrentYearIntPol);    //个人交费累计受益
       RiskInfo[8]=String.valueOf(CurrentYearBonusGrp);    //单位交费红利
       RiskInfo[9]=String.valueOf(CurrentYearBonusPol);    //个人交费红利
       RiskInfo[10]=String.valueOf(CurrentYearManFeeGrp);   //单位交费管理费
       RiskInfo[11]=String.valueOf(CurrentYearManFeePol);   //个人交费管理费
       RiskInfo[12]=String.valueOf(CurrentYearSumMoneyFeeGrp);    //单位交费帐户余额
       RiskInfo[13]=String.valueOf(CurrentYearSumMoneyFeePol);    //个人交费帐户余额

       tRiskListTable.add(RiskInfo);        //加入主险信息
   }

   if (texttag.size()>0)
     xmlExport.addTextTag(texttag);

   xmlExport.addListTable(tRiskListTable, RiskInfoTitle);
   mResult.clear();
   mResult.addElement(xmlExport);

 }

 /**
  * 求上年结转金额--保费,利息,红利
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calLastYearSumMoney(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate<'"+LastPolDate+"' or (PayDate='"+LastPolDate+"' and MoneyType='HL'))" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
 }

 /**
  * 求本年保费转入-保费
  * @return
  */
 private double calCurrentYearFee(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo,LCGrpPolSchema tLCGrpPolSchema)
 {

     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate>='"+LastPolDate+"' and PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"')"
                  +" and MoneyType='BF'" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     double sMoney=sumMoney/(1-tLCGrpPolSchema.getManageFeeRate());
     return sMoney;

 }

 /**
  * 求本年帐户资金转入--保费和利息,其他转入资金
  * @return
  */
 private double calCurrentYearAccInFee(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate>='"+LastPolDate+"' and PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"')"
                  //+" and (MoneyType='BF' or MoneyType='LX' or MoneyType='ZJ' )"
                  +" and  Money>0" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
 }

 /**
  * 求本年帐户资金转出--保费和利息,其他转入资金
  * @return
  */
 private double calCurrentYearAccOutFee(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {

     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate>='"+LastPolDate+"' and PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"')"
                  //+" and (MoneyType='ZJ') "
                  +" and  Money<0" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;

 }

 /**
  * 求上年度利息-单位交费帐户
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calInterestMoneyForGrp(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
	 String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and MoneyType='LX'"
                  +" and payplancode in ('601101','631101','601103','631103') "
                  +" and PayDate>'"+LastPolDate+"'" 
                  +" and PayDate<='"+tLOBonusGrpPolParmSchema.getFiscalYear()+"-12-31"+"'" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;

 }

 /**
  * 求上年度利息-个人交费帐户
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calInterestMoneyForPol(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
	 String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and MoneyType='LX'"
                  +" and payplancode in ('601102','631102')  "
                  +" and PayDate>'"+LastPolDate+"'" 
                  +" and PayDate<='"+tLOBonusGrpPolParmSchema.getFiscalYear()+"-12-31"+"'" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
 }

 /**
  * 计算本年分配红利-单位交费
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calBonusMoneyForGrp(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and PayDate='"+tLOBonusGrpPolParmSchema.getSGetDate()+"'"
                  +" and MoneyType='HL' and payplancode in ('601101','631101','601103','631103')" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
 }

 /**
  * 计算本年分配红利-个人交费
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calBonusMoneyForPol(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0)  from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and PayDate='"+tLOBonusGrpPolParmSchema.getSGetDate()+"'"
                  +" and MoneyType='HL' and payplancode in ('601102','631102') " ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
 }

 /**
  * 求本年管理费－集体交费
  * @return
  */
 private double calManFeeForGrp(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo,LCGrpPolSchema tLCGrpPolSchema)
 {
     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate>='"+LastPolDate+"' and PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"')"
                  +" and MoneyType='BF' and payplancode in ('601101','631101','601103','631103')" ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     double sMoney=sumMoney/(1-tLCGrpPolSchema.getManageFeeRate());
     double sManMoney=sMoney*tLCGrpPolSchema.getManageFeeRate();
     return sManMoney;
 }

 /**
  * 求本年管理费－个人交费
  * @return
  */
 private double calManFeeForPol(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo,LCGrpPolSchema tLCGrpPolSchema)
 {

     String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and (PayDate>='"+LastPolDate+"' and PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"')"
                  +" and MoneyType='BF' and  payplancode in ('601102','631102') " ;
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     double sMoney=sumMoney/(1-tLCGrpPolSchema.getManageFeeRate());
     double sManMoney=sMoney*tLCGrpPolSchema.getManageFeeRate();
     return sManMoney;
 }

 /**
  * 计算本年帐户资金余额-单位交费
  * @param tLOBonusGrpPolParmSchema
  * @return
  */
 private double calLastYearSumMoneyForGrp(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
 {
     //String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
     ExeSQL tExeSQL = new ExeSQL();
     String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                  +" and ( "
                  +" (PayDate='"+tLOBonusGrpPolParmSchema.getSGetDate()+"' and MoneyType='HL')"
                  +" or (PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"') "
                  +" )"
                  +" and  payplancode in ('601101','631101','601103','631103') ";
     logger.debug(strSQL);
     SSRS tSSRS=tExeSQL.execSQL(strSQL);
     String strMoney=tSSRS.GetText(1,1);
     double sumMoney=Double.parseDouble(strMoney);
     return sumMoney;
  }

  /**
   * 计算本年帐户资金余额-个人交费
   * @param tLOBonusGrpPolParmSchema
   * @return
   */
  private double calLastYearSumMoneyForPol(LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema,String tPolNo)
  {
      //String LastPolDate=PubFun.calDate(tLOBonusGrpPolParmSchema.getSGetDate(),-1,"Y",null);
      ExeSQL tExeSQL = new ExeSQL();
      String strSQL="select nvl(sum(Money),0) from LCInsureAccTrace where  PolNo='"+tPolNo+"'"
                   +" and ( "
                   +" (PayDate='"+tLOBonusGrpPolParmSchema.getSGetDate()+"' and MoneyType='HL')"
                   +" or (PayDate<'"+tLOBonusGrpPolParmSchema.getSGetDate() +"') "
                   +" )"
                   +" and payplancode in ('601102','631102') ";
      logger.debug(strSQL);
      SSRS tSSRS=tExeSQL.execSQL(strSQL);
      String strMoney=tSSRS.GetText(1,1);
      double sumMoney=Double.parseDouble(strMoney);
      return sumMoney;
  }

}
