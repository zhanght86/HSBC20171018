package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author z
 * @version 1.0
 */

import java.util.*;
import java.text.*;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class BonusNoticeGrpBL {
private static Logger logger = Logger.getLogger(BonusNoticeGrpBL.class);


  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();

  private VData mResult = new VData();

  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  //private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
  private LOBonusGrpPolParmSchema mLOBonusGrpPolParmSchema = new LOBonusGrpPolParmSchema();
  private TransferData mTransferData = new TransferData();
  private String mFiscalYear = "";

  private String mOperate="";
  private String CurrentDate = PubFun.getCurrentDate();
  /*转换精确位数的对象   */
  private String FORMATMODOL="0.00";//保费保额计算出来后的精确位数
  private DecimalFormat mDecimalFormat=new DecimalFormat(FORMATMODOL);//数字转换对象
  public BonusNoticeGrpBL() {
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
      if( !cOperate.equals("CONFIRM") && !cOperate.equals("PRINT"))
      {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }

      // 得到外部传入的数据，将数据备份到本类中（不管有没有operate,都要执行这一部）
      if( !getInputData(cInputData) ) {
        return false;
      }

      if( cOperate.equals("CONFIRM") )
      {
        mResult.clear();
        // 准备所有要打印的数据
        getPrintData();
      }
      else if( cOperate.equals("PRINT") )
      {
//        if( !saveData(cInputData) )  {
//          return false;
//        }
      }
      return true;

    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("submitData", ex.toString());
      return false;
    }
  }



  public static void main(String[] args) {

      GlobalInput tG=new GlobalInput();
      tG.ManageCom="86";
      tG.Operator="001";
      LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
      tLOPRTManagerSchema.setPrtSeq("86110020040810002451");
      VData tVData = new VData();
      VData mResult = new VData();

      tVData.addElement(tG);
      tVData.addElement(tLOPRTManagerSchema);

      BonusNoticeGrpUI tBonusNoticeGrpUI = new BonusNoticeGrpUI();
      if(!tBonusNoticeGrpUI.submitData(tVData,"CONFIRM"))
      {

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
    mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

    if( mGlobalInput==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }
    if(mTransferData != null)
    {
      mFiscalYear = (String)mTransferData.getValueByName("FiscalYear");
    }
    if(mFiscalYear.equals(""))
    {
      mFiscalYear = String.valueOf(Integer.parseInt(StrTool.getYear()) - 1);
    }

    return true;
  }
//得到返回值
  public VData getResult() {
    return this.mResult;
  }

  private void buildError(String szFunc, String szErrMsg) {
    CError cError = new CError( );

    cError.moduleName = "BonusNoticeGrpBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

// 准备所有要打印的数据
  private void getPrintData() throws Exception
  {
    XmlExport xmlExport = new XmlExport();//新建一个XmlExport的实例
    xmlExport.createDocument("BonusNoticeGrp.vts", "");//最好紧接着就初始化xml文档

    LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
    LOBonusGrpPolParmDB tLOBonusGrpPolParmDB =new LOBonusGrpPolParmDB();
//    LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
//
//    tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
//    if(tLOPRTManagerDB.getInfo()==false)
//    {
//      mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
//      throw new Exception("在取得打印队列中数据时发生错误");
//    }
//    mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

    tLCGrpPolDB.setGrpPolNo(mLOBonusGrpPolParmSchema.getGrpPolNo());
    if( !tLCGrpPolDB.getInfo() )
    {
      mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
      throw new Exception("在获取团体保单信息时出错！");
    }
    LCGrpPolSchema tLCGrpPolSchema=tLCGrpPolDB.getSchema();

    tLOBonusGrpPolParmDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
    tLOBonusGrpPolParmDB.setFiscalYear(mFiscalYear);
    if( !tLOBonusGrpPolParmDB.getInfo() )
    {
      mErrors.copyAllErrors(tLOBonusGrpPolParmDB.mErrors);
      throw new Exception("在获取团体保单红利参数表信息时出错！");
    }
    LOBonusGrpPolParmSchema tLOBonusGrpPolParmSchema=tLOBonusGrpPolParmDB.getSchema();






    TextTag texttag = new TextTag();
    texttag.add("GrpContNo", tLCGrpPolSchema.getGrpContNo());
    texttag.add("GrpName", tLCGrpPolSchema.getGrpName());
    texttag.add("RiskName", ChangeCodetoName.getRiskName(tLCGrpPolSchema.getRiskCode()));
    texttag.add("CValiDate", tLCGrpPolSchema.getCValiDate());
    texttag.add("FiscalYear", tLOBonusGrpPolParmSchema.getFiscalYear());
    
    String CurrentYearDate = tLOBonusGrpPolParmSchema.getFiscalYear()+"-12-31";
    String LastYearDate=PubFun.calDate(CurrentYearDate,-1,"Y",null);
    //计算上年资金积累
    double LastYearSumMoney=0.0;
    LastYearSumMoney=calYearSumMoney(tLCGrpPolSchema.getGrpPolNo(),LastYearDate);
    texttag.add("LastYearDate", BqNameFun.getChDate(LastYearDate));
    texttag.add("LastYearSumMoney", PubFun.round(LastYearSumMoney,2));
    //计算本年资金积累
    double CurrentYearFee=0.0;
    CurrentYearFee=calYearSumMoney(tLCGrpPolSchema.getGrpPolNo(),CurrentYearDate);
    texttag.add("CurrentYearDate", BqNameFun.getChDate(CurrentYearDate));
    texttag.add("CurrentYearFee", PubFun.round(CurrentYearFee,2));

    //计算上年度利息
    double InterestMoney=0.0;
    InterestMoney=calInterestMoney(tLCGrpPolSchema.getGrpPolNo(),LastYearDate,CurrentYearDate);
    texttag.add("InterestMoney", PubFun.round(InterestMoney,2));
    texttag.add("BonuseMoney", PubFun.round(tLOBonusGrpPolParmSchema.getSumBonus(),2));
    //收益总额
    double dSumGetMoney = InterestMoney+tLOBonusGrpPolParmSchema.getSumBonus();
    texttag.add("SumGetMoney", PubFun.round(dSumGetMoney,2));
    //计算参保人数
    int StartYearPeoples=0;
    int EndYearPeoples = 0;
    String tCurrentYearStartDate = tLOBonusGrpPolParmSchema.getFiscalYear()+"-01-01";
    String tCurrentYearEndDate = tLOBonusGrpPolParmSchema.getFiscalYear()+"-12-31";
    StartYearPeoples=calSumPeoples(tLCGrpPolSchema.getGrpPolNo(),tCurrentYearStartDate);
    EndYearPeoples=calSumPeoples(tLCGrpPolSchema.getGrpPolNo(),tCurrentYearEndDate);
    texttag.add("CurrentYearStartDate", BqNameFun.getChDate(tCurrentYearStartDate));
    texttag.add("StartYearPeoples", StartYearPeoples);
    texttag.add("CurrentYearEndDate", BqNameFun.getChDate(tCurrentYearEndDate));
    texttag.add("EndYearPeoples", EndYearPeoples);
    //累计支出
    double SumPayMoney=0.0;
    SumPayMoney=calPolOutFee(tLCGrpPolSchema.getGrpPolNo(),tCurrentYearStartDate,tCurrentYearEndDate);
    texttag.add("SumPayMoney", PubFun.round(SumPayMoney,2));
    //累计交费
    double SumGetMoney=0;
    SumGetMoney = calPolGetMoney(tLCGrpPolSchema.getGrpPolNo(),tCurrentYearStartDate,tCurrentYearEndDate);
    texttag.add("SumGetMoney", PubFun.round(SumGetMoney,2));
    

    if (texttag.size()>0)
      xmlExport.addTextTag(texttag);

    mResult.clear();
    mResult.addElement(xmlExport);

  }

  private double calPolGetMoney(String tGrpPolNo, String tCurrentYearStartDate, String tCurrentYearEndDate) {
	  ExeSQL tExeSQL = new ExeSQL();
	  String strSQL =" select nvl(sum(sumactupaymoney),0)"
		  			+" from ljapaygrp"
		  			+" where grppolno = '"+tGrpPolNo+"'"
		  			+" and confdate >= '"+tCurrentYearStartDate+"'"
		  			+" and confdate <= '"+tCurrentYearEndDate+"'" ;
	  SSRS tSSRS=tExeSQL.execSQL(strSQL);
	  String strMoney=tSSRS.GetText(1,1);
	  double sumMoney=Double.parseDouble(strMoney);
	  return sumMoney;
}

private double calPolOutFee(String tGrpPolNo, String tCurrentYearStartDate, String tCurrentYearEndDate) {
	  ExeSQL tExeSQL = new ExeSQL();
	  String strSQL =" select nvl(sum(j.getmoney),0)"
		  			+" from ljagetendorse j, lpgrpedoritem p,lcpol c"
		  			+" where j.endorsementno = p.edorno"
		  			+" and p.edorstate = '0'"
		  			+" and c.polno = j.polno"
		  			+" and c.grpcontno = p.grpcontno"
		  			+" and c.grppolno = '"+tGrpPolNo+"'"
		  			+" and p.edorvalidate <= '"+tCurrentYearEndDate+"'"
		  			+" and p.edorvalidate >= '"+tCurrentYearStartDate+"'"
		  			+" and p.edortype in ('GT', 'AT', 'AZ', 'AX','JB')" ;
	  SSRS tSSRS=tExeSQL.execSQL(strSQL);
	  String strMoney=tSSRS.GetText(1,1);
	  double sumMoney=Double.parseDouble(strMoney);
	  return sumMoney;


}

private int calSumPeoples(String tGrpPolNo, String tCurrentDate) {
	  ExeSQL tExeSQL = new ExeSQL();
	  String strSQL = "select nvl(count(distinct insuredno),0) from lcpol p where p.grppolno = '"+tGrpPolNo+"'" 
	  		    	+ " and p.cvalidate <= '"+tCurrentDate+"' and p.poltypeflag <> '2' "
	  		    	+ " and not exists (select 1  from lpedoritem  where polno = p.polno and edortype in ('GT', 'AT', 'AZ', 'AX','GA')"
	  		    	+ "and edorstate = '0' and edorvalidate <= '"+tCurrentDate+"')" ;
	  SSRS tSSRS=tExeSQL.execSQL(strSQL);
	  String strRet=tSSRS.GetText(1,1);
	  int Peoples = Integer.parseInt(strRet);
	return Peoples;
}

private double calYearSumMoney(String grpPolNo, String YearDate) {

	  ExeSQL tExeSQL = new ExeSQL();
	  String strSQL ="select nvl(sum(money),0) from lcinsureacctrace i where i.grppolno = '"+grpPolNo+"'"
                    + " and i.paydate <= '"+YearDate+"' and not exists (select 1 from lpedoritem where polno = i.polno  and edortype in ('GT', 'AT', 'AZ', 'AX','GA') and edorstate = '0' and edorvalidate <= '"+YearDate+"')" ;
	  logger.debug(strSQL);
	  SSRS tSSRS=tExeSQL.execSQL(strSQL);
	  String strMoney=tSSRS.GetText(1,1);
	  double sumMoney=Double.parseDouble(strMoney);
	  return sumMoney;

}



  /**
   * 求上年度利息
   * @param tLOBonusGrpPolParmSchema
   * @return
   */
  private double calInterestMoney(String tGrpPolNo,String tLastYearDate,String tThisYearDate)
  {
      ExeSQL tExeSQL = new ExeSQL();
      String strSQL= "select nvl(sum(money),0) from lcinsureacctrace i where i.grppolno = '"+tGrpPolNo+"'"
    	  		   + " and i.paydate <= '"+tThisYearDate+"' and i.paydate > '"+tLastYearDate+"' and i.moneytype = 'LX'"
    	  		   + " and not exists (select 1  from lpedoritem where polno = i.polno and edortype in ('GT', 'AT', 'AZ', 'AX','GA')"
    	  		   + " and edorstate = '0' and edorvalidate <= '"+tThisYearDate+"')" ;
      SSRS tSSRS=tExeSQL.execSQL(strSQL);
      String strMoney=tSSRS.GetText(1,1);
      double sumMoney=Double.parseDouble(strMoney);
      return sumMoney;

  }



}

