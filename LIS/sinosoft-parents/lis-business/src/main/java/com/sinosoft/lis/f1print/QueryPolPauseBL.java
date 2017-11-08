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
public class QueryPolPauseBL
{
private static Logger logger = Logger.getLogger(QueryPolPauseBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  String mStartDate = "";//统计起期
  String mEndDate = "";//统计止期
  String mStation = "";//统计机构
  
  private String currentdate = PubFun.getCurrentDate();
  
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
  public QueryPolPauseBL() {}

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
    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
    xmlexport.createDocument("QueryPolPause.vts","printer");

    ListTable alistTable = new ListTable();
    alistTable.setName("QUERY");

    String main_sql = " select b.managecom,(select shortname from ldcom where comcode=b.managecom), "
       +" (select name from lacom where agentcom=b.agentcom ),b.appntname, "
       +" b.polno,(select riskname from lmriskapp where  riskcode=b.riskcode), "
       +" b.prem,(select (case when sum(prem) is not null then sum(prem)  else 0 end) from lcpol where mainpolno=b.polno and polno<>mainpolno ), "
       +" (select sum(prem) from lcpol where mainpolno=b.polno ), "
       +" d.postaladdress,d.zipcode,d.phone,d.mobile, "
       +" (select name from laagent where agentcode=b.agentcode),b.agentcode,a.makedate "
       +" from LOPRTManager a,lcpol b,LCAppnt c,lcaddress d  where a.otherno=b.contno "
       +" and c.appntno=d.customerno and c.addressno=d.addressno "
       +" and b.polno=b.mainpolno and b.contno=c.contno "
       +" and a.ManageCom like concat('"+"?mStation?"+"','%') "
       +"  and a.code = '42' and a.StateFlag = '0' "
       +" and a.makedate>='"+"?mStartDate?"+"' and a.makedate<='"+"?mEndDate?"+"'"
       +" order by b.managecom,a.makedate desc ";
    
    logger.debug("查询主要信息的语句是"+main_sql);
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    sqlbv.sql(main_sql);
    sqlbv.put("mStation", mStation);
    sqlbv.put("mStartDate", mStartDate);
    sqlbv.put("mEndDate", mEndDate);
    ExeSQL main_exesql = new ExeSQL();
    SSRS main_ssrs = main_exesql.execSQL(sqlbv);

    if(main_ssrs.getMaxRow()>0)
    {
        for(int count=1;count<=main_ssrs.getMaxRow();count++)
        {
        	String[] cols = new String[16];
        	for(int x=0;x<=15;x++)
        	{
                //根据上述信息查询     
                cols[x] = main_ssrs.GetText(count,x+1);
        	}
        	alistTable.add(cols);
        }
        
        String[] b_col = new String[16];
        xmlexport.addDisplayControl("displayinfo");
        xmlexport.addListTable(alistTable, b_col);

        //新增投保人邮编和通信地址
        texttag.add("ComCode", mStation);//统计机构
        texttag.add("tjdate", mStartDate+"至"+mEndDate);//统计期间

        texttag.add("creatdate",this.currentdate);//制表时间

        if (texttag.size()>0)
          xmlexport.addTextTag(texttag);
        xmlexport.outputDocumentToFile("e:\\","QueryPolPause");//输出xml文档到文件
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
    mStartDate = (String)cInputData.get(0);
    mEndDate = (String)cInputData.get(1);
    mStation = (String)cInputData.get(2);
    mG.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    
    if(mStation.equals("")||mStation == null)
    {
    	mStation=mG.ManageCom;
    }
    return true;
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
