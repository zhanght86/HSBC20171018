package com.sinosoft.lis.agentprint;
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
//import com.sinosoft.lis.llcase.*;
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
public class AppBirthdayBillBL
{
private static Logger logger = Logger.getLogger(AppBirthdayBillBL.class);

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
  String mManageCom = "";//统计机构
  String mSaleChnl = "";//保单渠道
  String mPayIntv = "";//交费类别
  String mPolType = "";//保单属性
  String String_SaleChnl = ""; //渠道类型
  private String templatepath="";//模板路径
  private String currentdate = PubFun.getCurrentDate();

  String mCurrentDate = "";
  int SumCount = 0;

  private GlobalInput mG = new GlobalInput();
  public AppBirthdayBillBL() {}

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
    String salechnl_sql="";
    String_SaleChnl="";
    if(mSaleChnl.equals("1")) //个险
    {
    	salechnl_sql=" and a.salechnl='02' ";
    	String_SaleChnl="个险";
    }
    else if(mSaleChnl.equals("2")) //银代
    {
    	salechnl_sql= " and a.salechnl='03' "
           + "and a.agentcom is not null and substr(a.agentcom,1,1)='1' "  ; //银代代理机构第一位为1
    	String_SaleChnl="银代";
    }
    else if(mSaleChnl.equals("3")) //中介
    {
    	salechnl_sql= " and a.salechnl in ('03','05','06','08','09') "
           + "and exists (select 1 from lacom where agentcom = a.agentcom and branchtype='7') ";
    	String_SaleChnl="中介";
    }
    else if(mSaleChnl.equals("4")) //联办
    {
    	salechnl_sql= " and a.agentcom like '86%' and char_length(a.agentcom) = 16 and	substr(a.agentcom, 9, 1) = '8' ";
    	String_SaleChnl="联办";
    }
    else if(mSaleChnl.equals("5")) //收展
    {
    	salechnl_sql= " and a.salechnl ='10' ";
    	String_SaleChnl="收展";
    }
    // "Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
    //改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
    //"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
    //改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
    // "concat只使用两个参数，改造样例如下：
    //Oracle：select 'a'||'b'||'c'||'d' from dual
    //改造为：select concat(concat(concat('a','b'),'c'),'d') from dual"
    //concat(concat(e.mobile,'--'),e.phone)

    //标记
    String main_sql = " select a.managecom, a.agentcode,(select name from laagent where agentcode = a.agentcode), "
    +" b.appntname,(case count(b.appntsex) when 0 then '男' else '女' end), b.appntbirthday,a.contno,a.cvalidate, a.paytodate, "
    +" (select riskname from lmriskapp where riskcode = a.riskcode),a.prem, "
    +" (select sum(prem) from lcpol where mainpolno = a.mainpolno and appflag='1' "
    //+ "and substr(polstate, 0, 2) in ('00', '01')"
    + "),a.payintv, "
    +" (select (case count(*) when 0 then '正常' else '垫交' end) from lccontstate where  contno =a.contno and (polno=a.polno or polno='000000') and statetype='PayPrem' and state='1' and enddate is null ), "
    +" e.postaladdress, e.zipcode, "
  //  +" (case when (e.mobile) is not null then (e.mobile) else 1 end) ='1' then e.phone else (case when (e.phone) is not null then (e.phone) else 0 end)='1' then e.mobile else concat(concat( e.mobile,'--'),e.phone) end  end ,"
    +" case when (case when e.mobile is null then '1' else e.mobile end) ='1' then e.phone else (case when (case when e.mobile is null then '1' else e.mobile end)='1' then e.mobile else concat(concat(e.mobile,'--'),e.phone) end  ) end ,"
    +" a.salechnl, "
    +" (select name from lacom where agentcom = a.agentcom), "
    +" (select d.name from lrascription c, laagent d where c.agentnew = d.agentcode "
    +" and c.oldpolno = a.polno and c.paytodate = a.paytodate	union "
    +" select d.name	from lradimascription c, laagent d "
    +" where c.agentcode = d.agentcode and c.polno = a.polno "
    +" and c.paytodate = a.paytodate), "
    +" (select c.agentnew	from lrascription c where c.oldpolno = a.polno "
    +" and c.paytodate = a.paytodate	union "
    +" select c.agentcode	from lradimascription c "
    +" where c.polno = a.polno and c.paytodate = a.paytodate), "
    +" (case (select distinct 'X' from lrascription  f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1' "
    +" union   select distinct 'X' from lrascriptionb f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1') when 'X' then '孤儿单' else '在职单' end) "
    //+" decode((select distinct 'X' from lrascription  f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1' "
    //+" union   select distinct 'X' from lrascriptionb f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1'),'X','孤儿单','在职单') "
    +" from lcpol a, lcappnt b,lcaddress e where a.appntno = b.appntno and b.appntno=e.customerno and b.addressno=e.addressno "
    +"  and a.contno = b.contno and a.polno = a.mainpolno "
    +" and a.appflag = '1' "+ salechnl_sql +  getPayIntv(mPayIntv) + getPolType(mPolType)
    //+" and substr(a.polstate, 0, 2) in ('00', '01') "
    +" and substr(to_char(b.appntbirthday,'yyyy-mm-dd'),6,5)>='"+"?substr?"+"' and substr(to_char(b.appntbirthday,'yyyy-mm-dd'),6,5)<='"+"?substr1?"+"' "
    +" and a.managecom like concat('"+"?substr3?"+"','%') "
    +" group by a.managecom,a.agentcode,b.appntname,b.appntbirthday,a.contno,a.cvalidate,a.paytodate,a.riskcode,a.prem,a.mainpolno,a.payintv,a.polno,e.postaladdress,e.zipcode,e.mobile,e.phone,a.salechnl,a.agentcom "
    +" order by a.managecom,substr(b.appntbirthday, 6, 5) ";
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(main_sql);
    sqlbv1.put("substr", mStartDate);
    sqlbv1.put("substr1", mEndDate);
    sqlbv1.put("substr3", mManageCom);
    logger.debug("查询主要信息的语句是"+main_sql);

    ExeSQL main_exesql = new ExeSQL();
    SSRS main_ssrs = main_exesql.execSQL(sqlbv1);

    String[][] result= new String[main_ssrs.getMaxRow()][22]; //用来装最后的结果数据
    if(main_ssrs.getMaxRow()>0)
    {
        for(int count=0;count<=main_ssrs.getMaxRow()-1;count++)
        {
        	for(int x=0;x<=21;x++)
        	{
                //根据上述信息查询
        		result[count][x] = main_ssrs.GetText(count+1,x+1);
        		//result[count][12]  //缴费类别
        		if(main_ssrs.GetText(count+1,13).equals("1"))
        		{
        			result[count][12]= "月缴"; //缴费类别
        		}
        		else if(main_ssrs.GetText(count+1,13).equals("3"))
        		{
        			result[count][12]= "季缴"; //缴费类别
        		}
        		else if(main_ssrs.GetText(count+1,13).equals("12"))
        		{
        			result[count][12]= "年缴"; //缴费类别
        		}
        		else if(main_ssrs.GetText(count+1,13).equals("0"))
        		{
        			result[count][12]= "趸交"; //缴费类别
        		}

        		result[count][17]=String_SaleChnl;  //销售渠道
        	}
        }
        //将数组中为null的项置为""
	   if(!trans(result,main_ssrs.getMaxRow(),11))
	    {
	         logger.debug("将数组中为null的项置为空时出错");
	         this.buildError("trans", "将数组中为null的项置为空时出错");
	         return false;
	    }

        TransferData tempTransferData=new TransferData();
        //输入表头等信息
        tempTransferData.setNameAndValue("&managecom",mManageCom);
        tempTransferData.setNameAndValue("&salechnl",String_SaleChnl);
        tempTransferData.setNameAndValue("&tjdate",mStartDate+"至"+mEndDate);
        tempTransferData.setNameAndValue("&makedate",this.currentdate);
        tempTransferData.setNameAndValue("&operator",this.mG.Operator);

        HashReport tHashReport = new HashReport();
        String tpath = "";
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar("XSCreatListPath");
        if (!tLDSysVarDB.getInfo())
        {
          return false;
        }
        tpath = tLDSysVarDB.getSysVarValue();
//        tpath = "D:\\公司资料\\源程序\\myproject\\临时文件\\";
        String tFileName = "AppBirthdayBill" + "_"+ mManageCom + "_"+ mSaleChnl + "_" + mPayIntv + "_" + mPolType + "_" + mStartDate + "_" + mEndDate ;
        tHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		          templatepath + "AppBirthdayBill.xls",
                                       result,tempTransferData);
        logger.debug("Eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeed");
    }
    return true;
  }
  /**
    * 保单类型sql
    * @param PolType (保单类型，在职单1，孤儿单2，不区分3)
    * @return
    */
  private String getPolType(String tPolType)
  {
    String PolType_sql = "";
    if (tPolType.equals("1"))
    {
      PolType_sql = " and not exists (select 'X' from lrascription  f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1' "
                   +"                 union all "
                   +"                 select 'X' from lrascriptionb f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1') ";
    }
    else if (tPolType.equals("2"))
    {
      PolType_sql = " and exists (select 'X' from lrascription  f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1' "
                   +"             union all "
                   +"             select 'X' from lrascriptionb f where f.contno=a.contno and f.riskcode=a.riskcode and f.ascriptiondate>='2005-12-1') ";
    }
    else if (tPolType.equals("3"))
    {
      PolType_sql = "";
    }
    return PolType_sql;
  }
  /**
    * 交费类别sql
    * @param PayIntv (交费类别，趸交1，期交2，不区分3)
    * @return
    */
  private String getPayIntv(String tPayIntv)
  {
    String PayIntv_sql = "";
    if (tPayIntv.equals("1"))
    {
      PayIntv_sql = " and a.payintv='0' ";
    }
    else if (tPayIntv.equals("2"))
    {
      PayIntv_sql = " and a.payintv<>'0' ";
    }
    else if (tPayIntv.equals("3"))
    {
      PayIntv_sql = "";
    }
    return PayIntv_sql;
  }

  private boolean getInputData(VData cInputData)
  {
    // 暂交费查询条件
	mManageCom = (String)cInputData.get(0);
	mSaleChnl = (String)cInputData.get(1);
        mPayIntv = (String)cInputData.get(2);
        mPolType = (String)cInputData.get(3);
    mStartDate = (String)cInputData.get(4);
    mEndDate = (String)cInputData.get(5);
    templatepath=(String)cInputData.get(6);

    mG.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

  private boolean trans(String[][] result,int count1,int count2)  //将数组中为null的置为"" ,count1为数组的行数,count2为数组列数
  {
     int a=0;
     int b=0;
     a=count1;
     b=count2;
     for(int i=0;i<a;i++)
     {
       for(int j=0;j<b;j++)
       {
          if(result[i][j]== null)
          {
             result[i][j]="";
          }
       }
     }
     return true;
  }

  private void buildError(String szFunc, String szErrMsg)
  {
      CError cError = new CError();
      cError.moduleName = "AppBirthdayBillBL";
      cError.functionName = szFunc;
      cError.errorMessage = szErrMsg;
      this.mErrors.addOneError(cError);
  }


  public static void main(String[] args)
  {
    String mManageCom = "86130002";
    String mSaleChnl = "1";
    String mPayIntv = "1";
    String mPolType = "2";
    String mStartDate = "04-01";
    String mEndDate = "04-02";
    String templatepath = "D:\\公司资料\\源程序\\myproject\\临时文件\\";
    GlobalInput tG = new GlobalInput();
    tG.Operator="001";
    tG.ManageCom="86";
    AppBirthdayBillBL aAppBirthdayBillBL = new AppBirthdayBillBL();
    VData tVData = new VData();
    tVData.add(mManageCom);
    tVData.add(mSaleChnl);
    tVData.add(mPayIntv);
    tVData.add(mPolType);
    tVData.add(mStartDate);
    tVData.add(mEndDate);
    tVData.add(templatepath);
    tVData.add(tG);
    aAppBirthdayBillBL.submitData(tVData,"PRINT");
  }
}
