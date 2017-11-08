package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.f1j.ss.*;

public class ReAdvancePremQueryBL {
private static Logger logger = Logger.getLogger(ReAdvancePremQueryBL.class);
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

//取得的时间
  private GlobalInput mGlobalInput = new GlobalInput() ;
  private String mManageCom="";
  private String mBranchType="";
  private String mComLevel="";
  private String mStartDate="";
  private String mEndDate="";
  private String mYearMonth="";
  private String mTemplatePath="";//模板路径

  public ReAdvancePremQueryBL(){ }

  public static void main(String[] args){
    String managecom = "86";
    String comlevel ="2";
    String startDay = "2009-12-01";
    String endDay = "2009-12-31";
    String yearmonth ="200912";
    String template = "D:\\integrate\\ui\\f1print\\exceltemplate\\";
    String branchtype="1";
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.Operator="001";
    VData tVData = new VData();
    tVData.addElement(managecom);
    tVData.addElement(comlevel);
    tVData.addElement(startDay);
    tVData.addElement(endDay);
    tVData.addElement(yearmonth);
    tVData.addElement(template);
    tVData.addElement(branchtype);
    tVData.addElement(tGlobalInput);
    ReAdvancePremQueryBL tReAdvancePremQueryBL = new ReAdvancePremQueryBL();
    tReAdvancePremQueryBL.submitData(tVData,"");
  }

  /**
    *  传输数据的公共方法
    */
  public boolean submitData(VData cInputData, String cOperate){
    if( !getInputData(cInputData) )
      return false;

    mResult.clear();

   // 准备所有要打印的数据
    if( !queryData() )
      return false;
    return true;
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
  private boolean getInputData(VData cInputData){
    mManageCom=(String)cInputData.get(0);
    mComLevel=(String)cInputData.get(1);
    mStartDate=(String)cInputData.get(2);
    mEndDate=(String)cInputData.get(3);
    mYearMonth=(String)cInputData.get(4);
    mTemplatePath=(String)cInputData.get(5);
    mBranchType=(String)cInputData.get(6);
    mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }
  public VData getResult(){
    return this.mResult;
  }
  private boolean queryData(){
    String tSaleChnl = "";
    String tRiskCode = "";
    String tTitle = "";
    String tOperator =mGlobalInput.Operator ;//得到统计人
    String strArr[] = null;
    ListTable tListTable = new ListTable();
    tListTable.setName("xq");
    String tSQLStr="select startdate,enddate from lastatsegment where yearmonth='"+"?yearmonth?"+"' and stattype='1'";
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(tSQLStr);
    sqlbv1.put("yearmonth", mYearMonth);
    ExeSQL tExeSQL = new ExeSQL();
    SSRS tSSRS= new SSRS();
    tSSRS=tExeSQL.execSQL(sqlbv1);
    String tMonthStartDate=tSSRS.GetText(1,1);
    String tMonthEndDate = tSSRS.GetText(1,2);
     //SQL语句预处理
    int tComLen=0;
    tComLen=Integer.parseInt(mComLevel)*2;
    logger.debug("机构长度为:"+tComLen);

    switch (new Integer(mBranchType).intValue()){
      case 1: //个险
        tSaleChnl = "salechnl='02'";
        tRiskCode = " 1=1 ";
        tTitle = "个代续期暂收报表明细";
        break;
      case 2: //中介
        tSaleChnl = " agentcom in (select agentcom from lacom where substr(agentcom,1,2)='86' and (substr(agentcom,9,1)='2' or substr(agentcom,9,1)='9') )";
        tRiskCode = "riskcode not in ('211301', '211203', '211402', '211501', '211601', '211602', '211603','211604', '211605', '211606', '211608', '211801', '212401', '221701','221702', '221801', '221802', '241801', '241802', '241803', '311603','312201', '312202')  ";
        tTitle = "中介续期暂收报表明细";
        break;
      case 3: //联办
        tSaleChnl = " agentcom in (select agentcom from lacom where substr(agentcom,1,2)='86' and substr(agentcom,9,1)='8')";
        tRiskCode = "riskcode not in ('211301', '211203', '211402', '211501', '211601', '211602', '211603','211604', '211605', '211606', '211608', '211801', '212401', '221701','221702', '221801', '221802', '241801', '241802', '241803', '311603','312201', '312202')  ";
        tTitle = "联办续期暂收报表明细";
        break;
      case 4: //银代
        tSaleChnl = " agentcom in (select agentcom from lacom where branchtype='3')";
        tRiskCode = " 1=1 ";
        tTitle = "银代续期暂收报表明细";
        break;
      case 5: //收展
        tSaleChnl = " salechnl='10'";
        tRiskCode = " 1=1 ";
        tTitle = "收展续期暂收报表明细";
        break;
    }
  /*  "concat只使用两个参数，改造样例如下：
       Oracle：select 'a'||'b'||'c'||'d' from dual
              改造为：select concat(concat(concat('a','b'),'c'),'d') from dual"
  */
    //主键SQL
    String tKeySQL  = "select comcode from ldcom a where comcode like concat('"+"?like?"+"','%') "
		    + "and char_length(comcode) = "+"?comcode?" +" order by comcode " ;
    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    sqlbv2.sql(tKeySQL);
    sqlbv2.put("like", mManageCom.trim());
    sqlbv2.put("comcode", String.valueOf(tComLen));
    //机构名称
    String tNameSQL = "select comcode,shortname from ldcom a where comcode like concat('"+"?like1?"+"','%') "
                    + "and char_length(comcode) = "+"?comcode1?"+" ";

    SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
    sqlbv3.sql(tNameSQL);
    sqlbv3.put("like1", mManageCom.trim());
    sqlbv3.put("comcode1", String.valueOf(tComLen));
    //处理界面输入的时间限制条件
    String tStartDate="";
    String tEndDate="";

    if(mStartDate.equals(""))
      tStartDate=" and 1=1";
    else
      tStartDate=" and b.makedate >= '?mStartDate?'";

    if(mEndDate.equals(""))
      tEndDate=" and 1=1";
    else
      tEndDate=" and b.makedate <= '?mEndDate?'";

    //二次预收
    //注意此处的预收是指能够在当月核销成为实收的保费
    String tSQLStr2 = " select substr(b.managecom,1,"+"?substr?"+"),count(a.otherno),sum( a.sumduepaymoney) from ljspay a, ljtempfee b "
                    + " where b.otherno = a.otherno and b.tempfeeno = a.getnoticeno "
                    + " and a.startpaydate >= '"+"?startpaydate?"+"' and a.startpaydate<='"+"?startpaydate1?"+"' "
                    + " and b.confflag = '0' and b.tempfeetype = '2' "
                    + " and exists (select 1 from ljspayperson where paycount = 2 and char_length(dutycode) = 6 and contno = a.otherno and getnoticeno = a.getnoticeno) "
                    + " and exists (select 1 from lcpol c where grppolno='00000000000000000000' and "+tSaleChnl+" and payintv = 12 and exists (select 1 from lmriskapp where riskcode=c.riskcode and "+tRiskCode+" ) and contno = b.otherno) "+tStartDate+tEndDate
                    + " group by substr(b.managecom,1,"+"?substr?"+")" ;
    logger.debug("二次预收:"+tSQLStr2);
    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
    sqlbv4.sql(tSQLStr2);
    sqlbv4.put("substr", String.valueOf(tComLen));
    sqlbv4.put("startpaydate", tMonthStartDate);
    sqlbv4.put("startpaydate1", tMonthEndDate);
    sqlbv4.put("like1", mManageCom.trim());
    sqlbv4.put("mStartDate", mStartDate);
    sqlbv4.put("mEndDate", mEndDate);
    //三次预收
    String tSQLStr3 = " select substr(b.managecom,1,"+"?substr1?"+"),count(a.otherno),sum( a.sumduepaymoney) from ljspay a, ljtempfee b "
                    + " where b.otherno = a.otherno and b.tempfeeno = a.getnoticeno "
                    + " and a.startpaydate >= '"+"?startpaydate1?"+"' and a.startpaydate<='"+"?startpaydate2?"+"' "
                    + " and b.confflag = '0' and b.tempfeetype = '2' "
                    + " and exists (select 1 from ljspayperson where paycount = 3 and char_length(dutycode) = 6 and contno = a.otherno and getnoticeno = a.getnoticeno) "
                    + " and exists (select 1 from lcpol c where grppolno='00000000000000000000' and "+tSaleChnl+" and payintv = 12 and exists (select 1 from lmriskapp where riskcode=c.riskcode and "+tRiskCode+" ) and contno = b.otherno) "+tStartDate+tEndDate
                    + " group by substr(b.managecom,1,"+"?like2?"+")" ;
    logger.debug("三次预收:"+tSQLStr3);
    SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
    sqlbv5.sql(tSQLStr3);
    sqlbv5.put("substr1", String.valueOf(tComLen));
    sqlbv5.put("startpaydate1", tMonthStartDate);
    sqlbv5.put("startpaydate2", tMonthEndDate);
    sqlbv5.put("like2", mManageCom.trim());
    sqlbv5.put("mStartDate", mStartDate);
    sqlbv5.put("mEndDate", mEndDate);
    //四次预收
    String tSQLStr4 = " select substr(b.managecom,1,"+"?substr2?"+"),count(a.otherno),sum( a.sumduepaymoney) from ljspay a, ljtempfee b "
                    + " where b.otherno = a.otherno and b.tempfeeno = a.getnoticeno "
                    + " and a.startpaydate >= '"+"?startpaydate2?"+"' and a.startpaydate<='"+"?startpaydate3?"+"' "
                    + " and b.confflag = '0' and b.tempfeetype = '2' "
                    + " and exists (select 1 from ljspayperson where paycount >= 4 and char_length(dutycode) = 6 and contno = a.otherno and getnoticeno = a.getnoticeno) "
                    + " and exists (select 1 from lcpol c where grppolno='00000000000000000000' and "+tSaleChnl+" and payintv = 12 and exists (select 1 from lmriskapp where riskcode=c.riskcode and "+tRiskCode+" ) and contno = b.otherno) "+tStartDate+tEndDate
                    + " group by substr(b.managecom,1,"+"?substr2?"+")" ;
    logger.debug("四次预收:"+tSQLStr4);
    SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    sqlbv6.sql(tSQLStr3);
    sqlbv6.put("substr2", String.valueOf(tComLen));
    sqlbv6.put("startpaydate2", tMonthStartDate);
    sqlbv6.put("startpaydate3", tMonthEndDate);
    sqlbv6.put("mStartDate", mStartDate);
    sqlbv6.put("mEndDate", mEndDate);
    //开始调用ZHashReport
    VData tVData = new VData();
    tVData.add(sqlbv3);
    tVData.add(sqlbv4);
    tVData.add(sqlbv5);
    tVData.add(sqlbv6);
    ZHashReport tZHashReport = new ZHashReport(sqlbv2,tVData);
    tZHashReport.setColumnType(1,tZHashReport.StringType);
    tZHashReport.setDoformat(false);
    String[][] tStringResult = tZHashReport.calItem();
    logger.debug("tStringResult:"+tStringResult.length);

    try{
      double tNum2 = 0.00;
      double tFee2 = 0.00;
      double tNum3 = 0.00;
      double tFee3 = 0.00;
      double tNum4 = 0.00;
      double tFee4 = 0.00;
      double tRowSumNum = 0.00;
      double tRowSumFee = 0.00;
      String[][] tResult= new String[tStringResult.length+1][10]; //用来装最后的结果数据
      for(int i=0;i<tStringResult.length;i++){
        strArr = new String[10];
        strArr[0] = tStringResult[i][0];//机构代码
        strArr[1] = tStringResult[i][1];//机构名称
        strArr[2] = tStringResult[i][2];//二次预收件数
        strArr[3] = tStringResult[i][3];//二次预收保费
        strArr[4] = tStringResult[i][4];//三次预收件数
        strArr[5] = tStringResult[i][5];//三次预收保费
        strArr[6] = tStringResult[i][6];//四次预收件数
        strArr[7] = tStringResult[i][7];//四次预收保费

        double tColSumNum = 0.00;
        double tColSumFee = 0.00;
        tColSumNum = ReportPubFun.functionDouble(tStringResult[i][2])+ReportPubFun.functionDouble(tStringResult[i][4])+ReportPubFun.functionDouble(tStringResult[i][6]);
        strArr[8] = ReportPubFun.functionJD(tColSumNum, "0.00");//合计预收件数

        tColSumFee = ReportPubFun.functionDouble(tStringResult[i][3])+ReportPubFun.functionDouble(tStringResult[i][5])+ReportPubFun.functionDouble(tStringResult[i][7]);
        strArr[9] = ReportPubFun.functionJD(tColSumFee, "0.00");//合计预收保费

        tNum2 += ReportPubFun.functionDouble(strArr[2]);
        tFee2 += ReportPubFun.functionDouble(strArr[3]);
        tNum3 += ReportPubFun.functionDouble(strArr[4]);
        tFee3 += ReportPubFun.functionDouble(strArr[5]);
        tNum4 += ReportPubFun.functionDouble(strArr[6]);
        tFee4 += ReportPubFun.functionDouble(strArr[7]);
        tRowSumNum += ReportPubFun.functionDouble(strArr[8]);
        tRowSumFee += ReportPubFun.functionDouble(strArr[9]);

        tListTable.add(strArr);
        tResult[i]=strArr;  //将此行赋值给结果数据集合中的一行
      }

      strArr = new String[10];
      strArr[0] = "合计";
      strArr[1] = "";
      strArr[2] = ReportPubFun.functionJD(tNum2, "0.00");
      strArr[3] = ReportPubFun.functionJD(tFee2, "0.00");
      strArr[4] = ReportPubFun.functionJD(tNum3, "0.00");
      strArr[5] = ReportPubFun.functionJD(tFee3, "0.00");
      strArr[6] = ReportPubFun.functionJD(tNum4, "0.00");
      strArr[7] = ReportPubFun.functionJD(tFee4, "0.00");
      strArr[8] = ReportPubFun.functionJD(tRowSumNum, "0.00");
      strArr[9] = ReportPubFun.functionJD(tRowSumFee, "0.00");

      tListTable.add(strArr);
      tResult[tStringResult.length]=strArr;

      TransferData tTransferData=new TransferData();
      String tDateTime = PubFun.getCurrentDate()+" "+PubFun.getCurrentTime();
      tTransferData.setNameAndValue("&operator", tOperator);
      tTransferData.setNameAndValue("&tjdate", mStartDate+"--"+mEndDate);
      tTransferData.setNameAndValue("&yearmonth", mYearMonth);
      tTransferData.setNameAndValue("&scdate",tDateTime);
      tTransferData.setNameAndValue("&title", tTitle);

      HashReport tHashReport = new HashReport();
      String tPath = "";
      LDSysVarDB tLDSysVarDB = new LDSysVarDB();
      tLDSysVarDB.setSysVar("XSCreatListPath");
      if (!tLDSysVarDB.getInfo())
        return false;
      tPath = tLDSysVarDB.getSysVarValue();

      String tFileName = "XQAdvancePrem" + "_" + mBranchType + "_" + mManageCom + "_" + mComLevel+"_" + mStartDate + "_" + mEndDate +"_"+mYearMonth + ".xls";
      tHashReport.outputArrayToFile1(tPath + tFileName,mTemplatePath + "XQAdvancePrem.xls",tResult, tTransferData);
      XmlExport tXmlExport=new XmlExport();//新建一个XmlExport的实例
      tXmlExport.createDocument("XQAdvancePrem.vts","printer");//最好紧接着就初始化xml文档

      TextTag tTextTag=new TextTag();//新建一个TextTag的实例
      tTextTag.add("operator",tOperator);
      tTextTag.add("tjdate",mStartDate+"--"+mEndDate);
      tTextTag.add("yearmonth",mYearMonth);
      tTextTag.add("scdate",tDateTime);
      tTextTag.add("title", tTitle);
      tXmlExport.addTextTag(tTextTag);
      tXmlExport.addListTable(tListTable, strArr);
      mResult.addElement(tXmlExport);
    }catch(Exception ex){
      ex.printStackTrace();
    }
    return true;
  }
}


