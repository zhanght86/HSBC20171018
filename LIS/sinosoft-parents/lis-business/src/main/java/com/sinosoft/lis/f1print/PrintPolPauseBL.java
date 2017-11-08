package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.claim.*;
import java.util.*;
import java.text.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 打印保单效力中止清单程序</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft</p>
 * @author LiuYansong
 * @version 1.0
 */
public class PrintPolPauseBL
{
private static Logger logger = Logger.getLogger(PrintPolPauseBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  String mGetNoticeNo = "";//交费通知书号码
  String mMainPolNo = "";
  String mAppntName = "";
  String mComCode="";
  String mbeforeDate = "";
  double mSumMoney=0.00;
  String PauseDate = "";//保单效力中止日期
  String RenewDate = "";//保单效力恢复日期
  String mCurrentDate = "";
  int SumCount = 0;

  LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
  LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema();
  LDComSchema mLDComSchema = new LDComSchema();
  private GlobalInput mG = new GlobalInput();
  public PrintPolPauseBL() {}

  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    mInputData = (VData)cInputData.clone() ;
    this.mOperate = cOperate;
    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
      return false;
    if(!getPrintData())
      return false;
    //查询所有的信息



    return true;
  }
  private boolean getPrintData()
  {
    //对打印的信息进行附值操作
    ListTable tlistTable = new ListTable();
    tlistTable.setName("MODE");

    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
    xmlexport.createDocument("PolPause.vts","printer");

    ListTable alistTable = new ListTable();
    alistTable.setName("INFO");

    //String main_sql =  "select PolNo,AppntNo,paydate,LastPayToDate,SumDuePayMoney,RiskCode ,PayCount from ljspayperson where polno in "
      //              +"(select polno from lcpol where mainpolno ='"+mMainPolNo+"' ) order by PolNo  ";
    String main_sql = " select PolNo,AppntNo,PayDate,LastPayToDate , sum(SumDuePayMoney),RiskCode ,PayCount "
                    +" from ljspayperson where GetNoticeNo = '"+"?mGetNoticeNo?"+"' "
                    +" group by PolNo,AppntNo,PayDate,LastPayToDate, RiskCode,PayCount Order By PolNo ";
    logger.debug("查询主要信息的语句是"+main_sql);
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    sqlbv.sql(main_sql);
    sqlbv.put("mGetNoticeNo", mGetNoticeNo);
    ExeSQL main_exesql = new ExeSQL();
    SSRS main_ssrs = main_exesql.execSQL(sqlbv);
    if(main_ssrs.getMaxRow()>0)
    {
        for(int count=1;count<=main_ssrs.getMaxRow();count++)
        {
          mSumMoney = mSumMoney + Double.parseDouble(main_ssrs.GetText(count,5));
          //根据上述信息查询
          String mRiskName = getRiskName(main_ssrs.GetText(count,6));
          String[] cols = new String[4];
          cols[0] = main_ssrs.GetText(count,1);//保单号码
          cols[1] = mRiskName;//险种名称
          cols[3] =String.valueOf(main_ssrs.GetText(count,5));//保费
          cols[2] = String.valueOf(main_ssrs.GetText(count,7)) ;//交费期数
          logger.debug("保单号码是"+cols[0]);
          logger.debug("险种名称是"+cols[1]);
          logger.debug("保费是"+cols[3]);
          alistTable.add(cols);
        }
        PremBankPubFun tPremBankPubFun = new PremBankPubFun();
        mLCAppntIndSchema = tPremBankPubFun.getAppntInfo(main_ssrs.GetText(1,2));
        PauseDate = getnextDate(main_ssrs.GetText(1,3));
        RenewDate = getRemewDate(main_ssrs.GetText(1,4));
        mLDComSchema = getLDComInfo(mG.ManageCom);//获得管理机构的语句

        mCurrentDate = LLClaimPubFunBL.Display_Year(PubFun.getCurrentDate());//当前日期

        String[] b_col = new String[4];
        xmlexport.addDisplayControl("displayinfo");
        xmlexport.addListTable(alistTable, b_col);

        //新增投保人邮编和通信地址
        texttag.add("ZipCode", mLCAppntIndSchema.getZipCode());//投保人邮编
        texttag.add("Address", mLCAppntIndSchema.getPostalAddress());//投保人地址

        texttag.add("AppntName",mLCAppntIndSchema.getName());//投保人姓名
        texttag.add("MainPolNo",main_ssrs.GetText(1,1));//主险保单号码
        texttag.add("PayDate",LLClaimPubFunBL.Display_Year(main_ssrs.GetText(1,3)));//paydate
        texttag.add("SumMoney",mSumMoney);
        texttag.add("PauseDate",LLClaimPubFunBL.Display_Year(PauseDate));
        texttag.add("RenewDate",LLClaimPubFunBL.Display_Year(RenewDate));
        texttag.add("Phone",mLDComSchema.getPhone());
        texttag.add("ComName",mLDComSchema.getName());
        texttag.add("Date1",mCurrentDate);
        texttag.add("SumCount",main_ssrs.getMaxRow());

        if (texttag.size()>0)
          xmlexport.addTextTag(texttag);
        xmlexport.outputDocumentToFile("e:\\","PolPause");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);
    }
    return true;
  }
  public VData getResult()
  {
  	return mResult;
  }

  private boolean getInputData(VData cInputData)
  {
    // 暂交费查询条件
    mGetNoticeNo = (String)cInputData.get(0);
    mG.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

  //    得到前一天的日期
  private String getnextDate(String StartDate)
  {
    String tDate  = "";
    FDate tFDate = new FDate();
    Date aDate = new Date();
    Date cDate = new Date();
    aDate = tFDate.getDate(StartDate);
    Date bDate = new Date();
    bDate = PubFun.calDate(aDate,1,"D",cDate);
    tDate = tFDate.getString(bDate);

    return tDate;
  }
  private String getRemewDate(String mCurPayToDate)
  {
    //获得两年后的对应日
    String tDate  = "";
    FDate tFDate = new FDate();
    Date aDate = new Date();
    Date cDate = new Date();
    aDate = tFDate.getDate(mCurPayToDate);
    Date bDate = new Date();
    bDate = PubFun.calDate(aDate,2,"Y",aDate);

    tDate = tFDate.getString(bDate);
//    logger.debug("两年后的日期是"+tDate);
    return tDate;
  }
  //查询险种名称的函数
  private String getRiskName(String tRiskCode)
  {
    String tRiskName = "";
    LMRiskDB tLMRiskDB = new LMRiskDB();
    tLMRiskDB.setRiskCode(tRiskCode);
    tLMRiskDB.getInfo();
    tRiskName = tLMRiskDB.getSchema().getRiskName();
    return tRiskName;
  }
  private LDComSchema getLDComInfo(String aComCode)
  {
    LDComSchema tLDComSchema = new LDComSchema();
    LDComDB tLDComDB = new LDComDB();
    tLDComDB.setComCode(aComCode);
    tLDComDB.getInfo();
    tLDComSchema.setSchema(tLDComDB.getSchema());
    return tLDComSchema;
  }


  public static void main(String[] args)
  {
    String tGetNoticeNo = "86110020040310010184";
    GlobalInput tG = new GlobalInput();
    tG.Operator="001";
    tG.ManageCom="86110000";
    PrintPolPauseBL aPrintPolPauseBL = new PrintPolPauseBL();
    VData tVData = new VData();
    tVData.add(tGetNoticeNo);
    tVData.add(tG);
    aPrintPolPauseBL.submitData(tVData,"PRINT");
  }
}
