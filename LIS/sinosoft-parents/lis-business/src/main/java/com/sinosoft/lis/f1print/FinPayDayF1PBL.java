package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.text.*;

public class FinPayDayF1PBL {
private static Logger logger = Logger.getLogger(FinPayDayF1PBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
      public CErrors mErrors=new CErrors();
      private VData mResult = new VData();
      //取得的时间
      private String mDay[]=null;
      //输入的查询sql语句
      private String msql="";
      private String nsql="";
      //业务处理相关变量
      /** 全局数据 */
      private GlobalInput mGlobalInput =new GlobalInput() ;

  public FinPayDayF1PBL() {
  }
  /**
传输数据的公共方法
*/
  public boolean submitData(VData cInputData, String cOperate)
  {
    if( !cOperate.equals("CONFIRM") &&
          !cOperate.equals("PRINT") ) {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }
    // 得到外部传入的数据，将数据备份到本类中
    if( !getInputData(cInputData) ) {
      return false;
    }

    mResult.clear();

    // 准备所有要打印的数据
    if( cOperate.equals("PRINT") )
    {
    if( !getPrintData() ) {
      return false;
    }
    }
    else if( cOperate.equals("CONFIRM") )
    {
      if( !getPrintData2() ) {
      return false;}
    }
    return true;
  }

  public static void main(String[] args)
  {
  }

  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    return true;
  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
//全局变量
    mDay=(String[])cInputData.get(0);
    mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));

    if( mGlobalInput==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }

    return true;
  }

  public VData getResult()
  {
    return this.mResult;
  }

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "FinPayDayF1PBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private boolean getPrintData()
  {
    LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
    LJFIGetSet tLJFIGetSet = new LJFIGetSet();
    SSRS tSSRS = new SSRS();
    SSRS nSSRS = new SSRS();
    double SumMoney = 0;
    long SumNumber=0;
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    msql="select OtherNoType,PayMode,sum(GetMoney),sum(1) from LJFIGet where MakeDate>='"+"?date1?"+"' "
    		+ "and MakeDate<='"+"?date2?"+"' and ManageCom like concat('"+"?ManageCom?"+"','%') Group By OtherNoType,PayMode order by OtherNoType";
    sqlbv1.sql(msql);
    sqlbv1.put("date1", mDay[0]);
    sqlbv1.put("date2", mDay[1]);
    sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
    ExeSQL tExeSQL = new ExeSQL();
    tSSRS=tExeSQL.execSQL(sqlbv1);
    ListTable tlistTable = new ListTable();
    String strArr[] = null;
    tlistTable.setName("Pay");
    for (int i=1;i<=tSSRS.MaxRow;i++)
    {
      strArr = new String[4];
      for (int j=1;j<=tSSRS.MaxCol;j++)
      {
        if (j==1)
        {
        if(tSSRS.GetText(i,j).equals("1")||tSSRS.GetText(i,j).equals("2"))
             {strArr[j-1]="生存领取";      }
        else if(tSSRS.GetText(i,j).equals("4"))
             {strArr[j-1]="暂交费退费";      }
        else if(tSSRS.GetText(i,j).equals("5"))
             {strArr[j-1]="赔案给付";      }
        else if(tSSRS.GetText(i,j).equals("6"))
             {strArr[j-1]="其它给付";      }
        else if(tSSRS.GetText(i,j).equals("7"))
        	{strArr[j-1]="红利给付";      }
        else if(tSSRS.GetText(i,j).equals("8"))
        {
          strArr[j-1] = "集体其他给付";
        }
        else if(tSSRS.GetText(i,j).equals("9"))
        {
          strArr[j-1] = "续期回退";
        }
        else if(tSSRS.GetText(i,j).equals("10"))
        {
          strArr[j-1] = "保全给付";
        }	
      }
      if (j==2)
      {
        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCode(tSSRS.GetText(i,j));
        tLDCodeDB.setCodeType("paymode");
        tLDCodeDB.getInfo();
        strArr[j-1]=tLDCodeDB.getCodeName();
      }
      if (j==3)
        {
          strArr[j-1]=tSSRS.GetText(i,j);
          String strSum = new DecimalFormat("0.00").format(Double.valueOf(strArr[j-1]));
          strArr[j-1] = strSum;
          //SumMoney = SumMoney + Double.valueOf(PubFun.getInt(strArr[j-1])).doubleValue();
          SumMoney = SumMoney + Double.parseDouble(strArr[j-1]);
        }
        if (j==4)
        {
          strArr[j-1]=tSSRS.GetText(i,j);
          SumNumber = SumNumber + Long.valueOf(strArr[j-1]).longValue();
        }
      }
      tlistTable.add(strArr);
    }
    String tSumMoney = String.valueOf(SumMoney);
    tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
    strArr = new String[4];
    strArr[0] = "OtherNoType";strArr[1] ="PayMode"; strArr[2] ="Money";strArr[3] = "Mult";
    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
    xmlexport.createDocument("FinPayDay.vts","printer");//最好紧接着就初始化xml文档
    texttag.add("StartDate",mDay[0]);
    texttag.add("EndDate",mDay[1]);
    texttag.add("SumMoney",tSumMoney);
    texttag.add("SumNumber",SumNumber);
    if (texttag.size()>0)
      xmlexport.addTextTag(texttag);
    xmlexport.addListTable(tlistTable, strArr);
    //xmlexport.outputDocumentToFile("e:\\","test");//输出xml文档到文件
    mResult.clear();
    mResult.addElement(xmlexport);
    return true;
  }
  private boolean getPrintData2()
  {
    LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
    LJFIGetSet tLJFIGetSet = new LJFIGetSet();
    SSRS tSSRS = new SSRS();
    SSRS nSSRS = new SSRS();
    double SumMoney = 0;
    long SumNumber=0;
    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    msql="select OtherNoType,PayMode,sum(GetMoney),sum(1) from LJFIGet where MakeDate>='"+"?date1?"+"' and MakeDate<='"+"?date2?"+"' and Operator='"+"?Operator?"+"' and ManageCom like concat('"+"?ManageCom?"+"','%') Group By OtherNoType,PayMode order by OtherNoType";
    sqlbv2.sql(msql);
    sqlbv2.put("date1", mDay[0]);
    sqlbv2.put("date2", mDay[1]);
    sqlbv2.put("Operator", mGlobalInput.Operator);
    sqlbv2.put("ManageCom", mGlobalInput.ManageCom);
    ExeSQL tExeSQL = new ExeSQL();
    tSSRS=tExeSQL.execSQL(sqlbv2);
    ListTable tlistTable = new ListTable();
    String strArr[] = null;
    tlistTable.setName("Pay");
    for (int i=1;i<=tSSRS.MaxRow;i++)
    {
      strArr = new String[4];
      for (int j=1;j<=tSSRS.MaxCol;j++)
      {
        if (j==1)
        {
        if(tSSRS.GetText(i,j).equals("1")||tSSRS.GetText(i,j).equals("2"))
             {strArr[j-1]="生存领取";      }
        else if(tSSRS.GetText(i,j).equals("4"))
             {strArr[j-1]="暂交费退费";      }
        else if(tSSRS.GetText(i,j).equals("5"))
             {strArr[j-1]="赔案给付";      }
        else if(tSSRS.GetText(i,j).equals("6"))
             {strArr[j-1]="其它给付";      }
        else if(tSSRS.GetText(i,j).equals("7"))
        	{strArr[j-1]="红利给付";      }
        else if(tSSRS.GetText(i,j).equals("9"))
        	{strArr[j-1]="续期回退";      }
        else if(tSSRS.GetText(i,j).equals("10"))
        	{strArr[j-1]="保全给付";      }
      }
      if (j==2)
      {
        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCode(tSSRS.GetText(i,j));
        tLDCodeDB.setCodeType("paymode");
        tLDCodeDB.getInfo();
        strArr[j-1]=tLDCodeDB.getCodeName();
      }
      if (j==3)
        {
          strArr[j-1]=tSSRS.GetText(i,j);
          String strSum = new DecimalFormat("0.00").format(Double.valueOf(strArr[j-1]));
          strArr[j-1] = strSum;
          //SumMoney = SumMoney + Double.valueOf(PubFun.getInt(strArr[j-1])).doubleValue();
          SumMoney = SumMoney + Double.parseDouble(strArr[j-1]);
        }
        if (j==4)
        {
          strArr[j-1]=tSSRS.GetText(i,j);
          SumNumber = SumNumber + Long.valueOf(strArr[j-1]).longValue();
        }
      }
      tlistTable.add(strArr);
    }
    String tSumMoney = String.valueOf(SumMoney);
    tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
    strArr = new String[4];
    strArr[0] = "OtherNoType";strArr[1] ="PayMode"; strArr[2] ="Money";strArr[3] = "Mult";

    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
    xmlexport.createDocument("FinOpeDay.vts","printer");//最好紧接着就初始化xml文档
    texttag.add("StartDate",mDay[0]);
    texttag.add("EndDate",mDay[1]);
    texttag.add("Operator",mGlobalInput.Operator);
    texttag.add("SumMoney",tSumMoney);
    texttag.add("SumNumber",SumNumber);
    if (texttag.size()>0)
      xmlexport.addTextTag(texttag);
    xmlexport.addListTable(tlistTable, strArr);
    //xmlexport.outputDocumentToFile("e:\\","test");//输出xml文档到文件
    mResult.clear();
    mResult.addElement(xmlexport);
    return true;

  }
}
