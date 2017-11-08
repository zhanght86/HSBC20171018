package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.text.*;
import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 2.0 by jiaqiangli
 */

public class ContGrpInsuredExcelBL {
private static Logger logger = Logger.getLogger(ContGrpInsuredExcelBL.class);

  public ContGrpInsuredExcelBL() {
  }
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  //取得的时间
  private String StartDay="";
  private String EndDay="";
  private String CalManageCom="";

  //含M00 1表示是 0表示否
  private String tIsHaveM00 = "";

  private String mFilePathDesc = "InsuInfoCreat"; //生成文件的路径,待修改
  private String mFilePathSouce = "InsuInfoMode"; //模版文件路径,待修改
  private String mFilePath = ""; //生成文件路径
  private String mModePath = "";//模版路径
  
  private String Grppolno="";
  private String ManageCom="";
  private String InsuredNo="";
  private String Name="";
  private String IDNo="";
  private String ContPlanCode="";
  private String mStrRealPath = "";

  String strArr[] = new String[23];
//  private String strArr[] = new String[15];
  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate)
  {
    // 得到外部传入的数据，将数据备份到本类中
    if( !getInputData(cInputData) ) {
      return false;
    }
    mResult.clear();
    // 准备所有要打印的数据
    if( !queryDataOther() ) {
      return false;
    }
    return true;
  }
  private void dealError(String FuncName,String ErrMsg) {
    CError error = new CError();
    error.moduleName = "SummitMarketingTeam";
    error.errorMessage = ErrMsg.trim();
    error.functionName = FuncName.trim();
    this.mErrors.addOneError(error);
  }
  /**
     * 取模版存放路径
     * @return
     */
    private boolean checkDesc()
    {
      logger.debug("@@@@@@@@@@@@@@@@@@@@@");
      LDSysVarDB tLDSysVarDB = null;

      //取模版路径
      tLDSysVarDB = new LDSysVarDB();
      tLDSysVarDB.setSysVar(this.mFilePathSouce);
      if (tLDSysVarDB.getInfo() == false)
      {
        dealError("checkDesc", "LDSysVar取文件路径(" + mFilePathSouce + ")描述失败");
        return false;
      }
      //模版路径
      this.mModePath = this.mStrRealPath + "" + tLDSysVarDB.getSysVarValue();
      logger.debug("mModePath:"+mModePath);
      return true;
  }
  private boolean getTempPath()
  {
    //获取生成文件路径
    if(!checkDesc())
    {
      return false;
    }
    return true;
  }
  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    //全局变量
		Grppolno=(String)cInputData.get(0);
		ManageCom=(String)cInputData.get(1);
		InsuredNo=(String)cInputData.get(2);
		Name=(String)cInputData.get(3);
		IDNo=(String)cInputData.get(4);
		ContPlanCode=(String)cInputData.get(5);
		this.mFilePath = (String)cInputData.get(6);
		mStrRealPath = (String)cInputData.get(7);
   
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
    //获取模版和生成文件的路径
    if(!getTempPath())
    {
      logger.debug("获取模版和生成文件的路径出错");
      return false;
    }

    return true;
  }
  public VData getResult()
  {
    return this.mResult;
  }

  private boolean queryDataOther()
  {
    // 0-期初人力 1-期末人力 2-当日规模保费 3-累计规模保费 4-当日标准保费 5-累计标准保费
    // 6-标准件数 7-出单人力 8-年累计规模保费 9-年累计标保 10-当日期交保费 11-累计期交保费 12-年累计承保期交保费


    //String cols = "机构编码;机构;期初人力;期末人力;
    //规模保费当日;标准保费达成当日;
//规模保费累计;标准保费达成累计;
//期交保费达成当日;期交保费达成累计;标准件数;出单人力;年规模保费;年标准保费";


    try{
      String tSQL_key1 = "";
      tSQL_key1 =" select A.a,A.b,A.c,A.d,A.e,A.f,A.g,A.h,A.i,A.j,A.k,A.l, "
          + " (select occupationname from ldoccupation where occupationcode=A.k), "
          + " A.n,A.o,A.q from ( "
					 + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,a.insuredappage f "
					 + " ,a.riskcode g,b.contplancode h,a.prem i,a.amnt j, "
          + " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k "
          + " ,b.occupationtype l,'' m职业名称, "
          + " a.floatrate n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p,(select currname from ldcurrency where 1 = 1 and currcode = a.currency)  q "
          + " from lcpol a, lcinsured b "
          + " where a.insuredno = b.insuredno and a.contno = b.contno "
          + " and b.prtno = '"+Grppolno+"' "
          + ReportPubFun.getWherePartLike("b.ManageCom", ManageCom)
          + ReportPubFun.getWherePart( "b.InsuredNo",InsuredNo )
		  + ReportPubFun.getWherePart( "b.IDNo",IDNo )
		  + ReportPubFun.getWherePart("b.ContPlanCode",ContPlanCode)
		  + " and b.Name like '%"+Name+"%' " 
          //有险种,连带被保人数据查询 
        + " union "
          + " select a.polno a,b.prtno b,b.insuredno c,b.name d,b.sex e,get_age(b.birthday,(select cvalidate from lcgrpcont where grpcontno =a.grpcontno)) f "
          + " ,'连带被保人' g,''h,0 i,0 j, "
        //+ " (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno and contno=a.contno) k, "
        + " b.occupationcode k, "
        + " b.occupationtype,'' m职业名称, "
          + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,b.contno p,(select currname from ldcurrency where 1 = 1 and currcode = a.currency) q "
        + " from lcpol a, lcinsured b,lcinsuredrelated c  "
        + " where b.insuredno = c.customerno and a.contno = b.contno and a.polno=c.polno "
        + " and b.prtno = '"+Grppolno+"' "
        + ReportPubFun.getWherePartLike( "b.ManageCom",ManageCom )
        + ReportPubFun.getWherePart( "b.InsuredNo",InsuredNo )
		+ ReportPubFun.getWherePart( "b.IDNo",IDNo )
		+ ReportPubFun.getWherePart( "b.ContPlanCode",ContPlanCode)
		+ " and b.Name like '%"+Name+"%' " 
        //没险种,没有连带被保人数据 
        + " union "
          + " select b.prtno a,b.prtno b,b.insuredno c,b.name d,b.sex e, "
					 + " decode((select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno), "
          + " null,0,(select year(polapplydate) - year(b.birthday) from lccont where grpcontno = b.grpcontno and insuredno = b.insuredno)) f, "
					 + " '' g,'' h,0 i,0 j, "
					 + " (select max(occupationcode) from lcinsured where lcinsured.insuredno = b.insuredno) k, "
					 + " b.occupationtype l,''m职业名称, "
					 + " 0 n,nvl((select cvalidate from lccont where contno = b.contno),to_date('')) o,contno p, '' q "
          + " from lcinsured b "
					 + " where not exists (select '1' from lcpol where grpcontno = b.grpcontno and insuredno = b.insuredno) "
					 + " and not exists (select '1' from lcinsuredrelated where polno in (select polno from lcpol where grpcontno=b.grpcontno and customerno=b.insuredno))"
					 + " and b.prtno = '"+Grppolno+"' "
          + ReportPubFun.getWherePartLike( "b.ManageCom",ManageCom)
          + " and b.Name like '%"+Name+"%' " 
          + " ) A order by A.p,A.a,A.c ";
//      String tSQL_key2 = "";
//      tSQL_key1 = "select a.polno,b.prtno,b.insuredno,b.name,b.sex,a.insuredappage," +
//      		     " a.riskcode, b.contplancode," +
//      		     " a.prem,a.amnt,(select max(occupationcode) from lcinsured where lcinsured.insuredno = a.insuredno),(select max(occupationtype) from lcpol " +
//      		     " where lcpol.grpcontno = a.grpcontno), (select max(occupationname) from LDOccupation where OccupationCode in (select occupationcode from lcinsured where lcinsured.insuredno = a.insuredno))," +
//      		     " a.floatrate,a.cvalidate  from lcpol a, lcinsured b where a.insuredno=b.insuredno and a.prtno=b.prtno and a.prtno='"+Grppolno+"' ";
//
//      tSQL_key2 = " union " +
//      		     " select b.prtno, b.prtno, b.insuredno, b.name,b.sex,nvl((select year(polapplydate)-year(b.birthday) from lccont where grpcontno=b.grpcontno and insuredno=b.insuredno),0),'', b.contplancode, 0,0,(select max(occupationcode) from lcinsured  " +
//      		     " where lcinsured.insuredno = b.insuredno), b.occupationtype, (select max(occupationname) from LDOccupation where ldoccupation.occupationcode = b.occupationcode), 0, to_date('') " +
//      		     " from lcinsured b where b.grpcontno='"+Grppolno+"'    and not exists  (select '1' from lcpol where grpcontno =b.grpcontno and insuredno=b.insuredno)  " ;
//      		 
//      if(!Name.equals("")||!(Name == null))
//      {
//    	  tSQL_key1 = tSQL_key1+ " and b.Name like '%"+Name+"%'";
//    	  tSQL_key2 = tSQL_key2+ " and b.Name like '%"+Name+"%'";
//      }
//      tSQL_key1 =tSQL_key1
//      			 + ReportPubFun.getWherePartLike("b.ManageCom", ManageCom)
//      		     + ReportPubFun.getWherePart("b.Insuredno", InsuredNo)
//      		 	 + ReportPubFun.getWherePart("b.IDNo", IDNo)
//      		 	 + ReportPubFun.getWherePart("b.ContPlanCode", ContPlanCode);
//      
//      tSQL_key2 = tSQL_key2
//                 + ReportPubFun.getWherePartLike("b.ManageCom", ManageCom)
//                 + ReportPubFun.getWherePart("b.Insuredno", InsuredNo)
//                 + ReportPubFun.getWherePart("b.IDNo", IDNo)
//                 + ReportPubFun.getWherePart("b.ContPlanCode", ContPlanCode)
//                 + "order by insuredno ";
//      
//      tSQL_key1 =tSQL_key1+tSQL_key2;
      logger.debug("报表查询sql是: "+tSQL_key1);

//            ZHashReport tZHashReport = new ZHashReport(tSQL_key1);
//            tZHashReport.setColumnType(1,tZHashReport.StringType);
//            tZHashReport.setColumnType(2,tZHashReport.StringType);
//            tZHashReport.setColumnType(3,tZHashReport.StringType);
//            tZHashReport.setColumnType(4,tZHashReport.StringType);
//            tZHashReport.setColumnType(5,tZHashReport.StringType);
//            tZHashReport.setColumnType(6,tZHashReport.StringType);
//            tZHashReport.setColumnType(7,tZHashReport.StringType);
//            tZHashReport.setColumnType(8,tZHashReport.StringType);
//            tZHashReport.setColumnType(9,tZHashReport.StringType);
//            tZHashReport.setColumnType(10,tZHashReport.StringType);
//            tZHashReport.setColumnType(11,tZHashReport.StringType);
//            tZHashReport.setColumnType(12,tZHashReport.StringType);
//            tZHashReport.setColumnType(13,tZHashReport.StringType);
//            tZHashReport.setColumnType(14,tZHashReport.StringType);
//            tZHashReport.setDoformat(false);
//            tZHashReport.setSumColumn(false);
            SSRS tSSRS = new SSRS();
            ExeSQL tExeSQL = new ExeSQL();
            tSSRS = tExeSQL.execSQL(tSQL_key1);
            String result[][]=tSSRS.getAllData();
//            for (int i =0;i<=tSSRS.getAllData().MaxRow;i++)
//            {
//            	for(int j=0;j<=tSSRS.MaxCol;j++)
//            	{
//            		 result[i][j]=tSSRS.GetText(i, j);
//            		 logger.debug("result["+i+"]["+j+"]"+result[i][j]);
//            		 logger.debug("tSSRS.GetText("+i+", "+j+")"+tSSRS.GetText(i, j));
//            	}
//            }
               //表头
               TransferData tTitleTransferData = new TransferData();
               tTitleTransferData.setNameAndValue("Grppolno", Grppolno);
               tTitleTransferData.setNameAndValue("ManageCom",this.mGlobalInput.ManageCom);


               tTitleTransferData.setNameAndValue("Operator",this.mGlobalInput.Operator);//制表人
               tTitleTransferData.setNameAndValue("CurrentDT",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());//制表日期时间

                     HashReport tHashReport = new HashReport();
                           //生成路径，模版路径，多维数组
                           String tCreatePath = "";
                           String tModelPath = "";
                           //生成文件名命名规范：报表名_级别_月份.xls
                           //注意：此处还需要在生成文件名加些参数。。。。
                           tCreatePath = this.mFilePath;//this.mFilePath+ManageCom+"_"+Grppolno+"_"+PubFun.getCurrentDate()+".xls";
                           tModelPath = this.mModePath+"ContGrpInsuInfo.xls";
                           
                           
                           logger.debug("["+tModelPath+"]");
                    logger.debug(tCreatePath);
                    logger.debug(tModelPath);
                    try {
                      //有表头
                      tHashReport.outputArrayToFile1(tCreatePath, tModelPath,result, tTitleTransferData);
                    }
                    catch(Exception ex) {
                      ex.printStackTrace();
                    }
      logger.debug("data end");
    }
    catch(Exception ex)
    {
      // @@错误处理
    	CError.buildErr(this,"生成Excel文件时失败!");
      return false;
    }
    logger.debug("end");
    return true;
  }
  public static void main(String[] args) {
	  ContGrpInsuredExcelBL tContGrpInsuredExcelBL = new ContGrpInsuredExcelBL();
    /*StartDay=(String)cInputData.get(0);
    EndDay=(String)cInputData.get(1);
    CalManageCom=(String)cInputData.get(2);
    tIsHaveM00=(String)cInputData.get(3);
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;*/
    String tStartDay = "2007-12-21";
    String tEndDay = "2007-12-31";
    String tCalManageCom = "4";
    String tIsHaveM00 = "1";
    GlobalInput tGI = new GlobalInput();
    tGI.ManageCom = "8635";
    tGI.Operator = "AutoPrt";
    VData tVData = new VData();
    logger.debug("hasdfj;lsdafjsdaf"+PubFun.getCurrentDate());

    tVData.addElement(tStartDay);
    tVData.addElement(tEndDay);
    tVData.addElement(tCalManageCom);
    tVData.addElement(tIsHaveM00);
    tVData.addElement(tGI);

    tContGrpInsuredExcelBL.submitData(tVData,"");
  }
}
