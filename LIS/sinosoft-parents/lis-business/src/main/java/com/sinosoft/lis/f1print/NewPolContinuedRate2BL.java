package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: PolContinuedRateBL.java</p>
 * <p>Description: 期交保单（个单、中介、银代渠道）机构退保统计</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: SinoSoft</p>
 * @author Fuqx
 * @version 1.0
 */

public class NewPolContinuedRate2BL
{
private static Logger logger = Logger.getLogger(NewPolContinuedRate2BL.class);

 public CErrors mErrors = new CErrors();
 private VData mResult = new VData();
 private GlobalInput mGI = new GlobalInput();
 private String mStartDate;  //保单生效开始日期
 private String mEndDate;    //保单生效结束日期
 private String mManageType ="2";  //管理机构的类型，如省级分公司(管理机构为4位,如8611)为2，省级公司下的分公司(861100)为3,营销服务部(86110000)为4
 private String mSalechnlType;  //销售渠道类型
 private String mReportType;   //统计报表类型
 private String mPremType;     //是否包含还垫保费  1－包括，0－不包括
 private String mRiskType;  //是否区分险种 1-区分，2-不区分
 private String mRiskCode="";   //险种代码
 private String mActEndDate="";  //实收截止日期
 private int mManageLen;  //记录管理机构的长度 根据管理机构的类型(mManageType) mManageLen=2*mManageType
 private String mSalechnlSql;
 private String mRiskCodeSql;
 private String mReportTypeSql;
 private String mActuPaySql = " ";
 private String mActEndDatesql = "";
 private String mTitle;
 private int mMonth;  //根据统计报表类型(mReportType),如果mReportType=1,则mMonth=14 如果mReportType=2,则mMonth=26
 private int mPayCount;
 private String mManageCom;
 private int mCount=1;
 DecimalFormat mDecimalFormat = new DecimalFormat("0.000000");
 DecimalFormat mDecimalFormat1 = new DecimalFormat("0.0000");
 private String templatepath="";//模板路径
 public NewPolContinuedRate2BL()
 {
 }

 public boolean submitData(VData cInputData, String cOperate)
 {
  if(!getInputData(cInputData,cOperate))
     return false;
  if(!checkData())
     return false;
  if(mRiskType.equals("2"))  //不区分险种统计，维持原来程序不变
  {
	  logger.debug("不区分险种统计！！");
	  if(("2".equals(mSalechnlType)||"4".equals(mSalechnlType)) && ("4".equals(mManageType)||"5".equals(mManageType)))   //代理机构继续率统计
	  {
	     if(!dealLAComData_norisk())
	        return false;
	  }
	  else  //处理个险以及中介二级机构继续率
	  {
	    if(!dealData_norisk())
	       return false;
	  }
  }
  else  //区分险种统计
  {
	  logger.debug("区分险种统计！！");
	  if(("2".equals(mSalechnlType)||"4".equals(mSalechnlType)) && ("4".equals(mManageType)||"5".equals(mManageType)))   //代理机构继续率统计
	  {
	     if(!dealLAComData_risk())
	        return false;
	  }
	  else  //处理个险以及中介二级机构继续率
	  {
	    if(!dealData_risk())
	       return false;
	  }

  }
  return true;
 }

 private boolean getInputData(VData cInputData,String cOperate)
 {
   if( !cOperate.equals("PRINT") )
   {
    CError tError = new CError();
    tError.moduleName = "PolContinuedRateBL";
    tError.functionName = "getInputData";
    tError.errorMessage = "不支持的操作类型!";
    this.mErrors.addOneError(tError);
    return false;
   }
   mGI = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput",8);
   if(mGI == null || mGI.ManageCom == null)
   {
     CError tError = new CError();
     tError.moduleName = "PolContinuedRateBL";
     tError.functionName = "getInputData";
     tError.errorMessage = "管理机构为空!";
     this.mErrors.addOneError(tError);
     return false;
   }
   mManageCom = mGI.ManageCom;
   mStartDate = (String)cInputData.get(0);
   mEndDate = (String)cInputData.get(1);
   mManageType = (String)cInputData.get(2);
   mSalechnlType = (String)cInputData.get(3);
   mReportType = (String)cInputData.get(4);
   mPremType  = (String)cInputData.get(5);
   mRiskType  = (String)cInputData.get(6);
   if(mRiskType.equals("1"))
   {
     mRiskCode  = (String)cInputData.get(7);
   }
   mActEndDate = (String)cInputData.get(8);
   if(mStartDate ==null||mEndDate==null||mManageType==null||mSalechnlType==null||mReportType==null||mRiskType==null)
   {
     CError tError = new CError();
     tError.moduleName = "PolContinuedRateBL";
     tError.functionName = "getInputData";
     tError.errorMessage = "数据传输失败!";
     this.mErrors.addOneError(tError);
     return false;
   }
   templatepath=(String)cInputData.get(9);
   //将前台传进来的时间格式化为10位的
   String date_sql="";
   ExeSQL date_exesql = new ExeSQL();
   date_sql=" select to_char(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),to_char(to_date('"+"?mEndDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),to_char(to_date('"+"?mActEndDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual ";
   SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
   sqlbv1.sql(date_sql);
   sqlbv1.put("mStartDate", mStartDate);
   sqlbv1.put("mEndDate", mEndDate);
   sqlbv1.put("mActEndDate", mActEndDate);
   mStartDate = date_exesql.execSQL(sqlbv1).GetText(1, 1);
   mEndDate = date_exesql.execSQL(sqlbv1).GetText(1, 2);
   mActEndDate = date_exesql.execSQL(sqlbv1).GetText(1, 3);
   logger.debug("mStartDate="+mStartDate+"~~~mEndDate="+mEndDate+"~~~mActEndDate="+mActEndDate);

   return true;
 }

 private boolean checkData()
 {
   if(!"2".equals(mManageType)&&!"3".equals(mManageType)&&!"4".equals(mManageType)&&!"5".equals(mManageType))
   {
     CError tError = new CError();
     tError.moduleName = "PolContinuedRateBL";
     tError.functionName = "checkData";
     tError.errorMessage = "机构类型错误!";
     this.mErrors.addOneError(tError);
     return false;
   }
   else
   {
     if("2".equals(mManageType))
     {
       mTitle="二级机构";
     }
     if("3".equals(mManageType))
     {
       mTitle="三级机构";
     }
     if("4".equals(mManageType))
     {
       mTitle="四级机构";
     }
     mManageLen = 2 * Integer.parseInt(mManageType);
   }
   logger.debug("***mManageLen: "+mManageLen);

   if(!"1".equals(mSalechnlType)&&!"2".equals(mSalechnlType)&&!"3".equals(mSalechnlType)&&!"4".equals(mSalechnlType)&&!"5".equals(mSalechnlType))
    {
      CError tError = new CError();
      tError.moduleName = "PolContinuedRateBL";
      tError.functionName = "checkData";
      tError.errorMessage = "销售渠道类型错误!";
      this.mErrors.addOneError(tError);
      return false;
    }
   else
   {
    if("1".equals(mSalechnlType))    //个险
    {
     mSalechnlSql=" and SaleChnl='02' ";
     mTitle=mTitle+"个险期缴";
    }
    else if("3".equals(mSalechnlType))  //收展
    {
    	 mSalechnlSql=" and SaleChnl='10' ";
         mTitle=mTitle+"收展期缴";
    }
    else if("2".equals(mSalechnlType) &&( "2".equals(mManageType)|| "3".equals(mManageType))) //中介二/三级管理机构（按照保单的管理机构）
    {
      mSalechnlSql=" and SaleChnl in ('03','05','06','08','09') and a.grppolno='00000000000000000000'  "
            + " and exists (select 1 from lacom where agentcom = a.agentcom and branchtype='7') "
            +" and a.riskcode <>'312201' ";  //中介期交统计不金玉满堂险种

      mTitle=mTitle+"中介期缴";
    }
    else if("2".equals(mSalechnlType) &&( "4".equals(mManageType)|| "5".equals(mManageType))) //中介机构继续率统计（按照保单的中介机构）
    {
      mSalechnlSql=" and SaleChnl in ('03','05','06','08','09') and a.grppolno='00000000000000000000'  "
            + " and exists (select 1 from lacom where agentcom = a.agentcom and branchtype='7') "
            +" and a.riskcode <>'312201' ";  //中介期交统计不金玉满堂险种
      if( "4".equals(mManageType))
      {
         mTitle = "一级中介机构期缴";
         mManageLen=13;
      }
      else
      {
        mTitle = "二级中介机构期缴";
        mManageLen = 16;
      }
    }
    else if("4".equals(mSalechnlType) &&( "2".equals(mManageType)|| "3".equals(mManageType))) //联办二/三级管理机构（按照保单的管理机构）
    {
      mSalechnlSql=" and a.grppolno='00000000000000000000'  "
            + " and exists (select 1 from laagent x,lacom y,lacommision z "
            +" where z.polno=a.polno and x.agentcode=z.agentcode and z.agentcom=y.agentcom "
            +" and (x.branchtype = '6' or y.agentcom in (select agentcom from lacom	where channeltype = 'E' "
            +" and (agentcom like '86%' and length(trim(agentcom)) = 16 and	substr(agentcom, 9, 1) = '8' or "
            +" agentcom in ('20130000000021', '20110000005001'))) or (x.branchtype = '2' and x.name like '%LB%'))) "
            ;


      mTitle=mTitle+"联办期缴";
    }
    else if("4".equals(mSalechnlType) &&( "4".equals(mManageType)|| "5".equals(mManageType))) //联办机构继续率统计（按照保单的中介机构）
    {
    	mSalechnlSql=" and a.grppolno='00000000000000000000'  "
    		+ " and exists (select 1 from laagent x,lacom y,lacommision z "
            +" where z.polno=a.polno and x.agentcode=z.agentcode and z.agentcom=y.agentcom "
            +" and ( x.branchtype = '6' or y.agentcom in (select agentcom from lacom	where channeltype = 'E' "
            +" and (agentcom like '86%' and length(trim(agentcom)) = 16 and	substr(agentcom, 9, 1) = '8' or "
            +" agentcom in ('20130000000021', '20110000005001'))) or (x.branchtype = '2' and x.name like '%LB%')))"
            ;
      if( "4".equals(mManageType))
      {
         mTitle = "一级联办机构期缴";
         mManageLen=13;
      }
      else
      {
        mTitle = "二级联办机构期缴";
        mManageLen = 16;
      }
    }
    else if("5".equals(mSalechnlType)) //银保二/三级管理机构（按照保单的管理机构）
    {
      mSalechnlSql=" and a.grppolno='00000000000000000000'  "
            + " and exists (select 1 from lacom where agentcom=a.agentcom and branchtype='3') ";


      mTitle=mTitle+"银保期缴";
    }
   }
   logger.debug("***mSalechnlSql: "+mSalechnlSql);

   if("0".equals(mPremType))   //当不包含垫交保费时
   {
     mActuPaySql = " and p5!=1 ";
   }

   logger.debug("***mActuPaySql: "+mActuPaySql);

   if(!"1".equals(mReportType)&&!"2".equals(mReportType)&&!"3".equals(mReportType)&&!"4".equals(mReportType))
     {
       CError tError = new CError();
       tError.moduleName = "PolContinuedRateBL";
       tError.functionName = "checkData";
       tError.errorMessage = "报表类型错误!";
       this.mErrors.addOneError(tError);
       return false;
    }
   else
   {
    if("1".equals(mReportType) )   //13个月继续率
    {
//    	if("1".equals(mReportType))
//    	{
    		mMonth=14;
    		mTitle=mTitle+"13";
//    	}
//    	else
//    	{
//    		mMonth=26;
//    		mTitle=mTitle+"13(口径二)";
//    	}
      mPayCount=1;
    }
    else if("2".equals(mReportType) )     //25个月继续率
    {
//    	if("2".equals(mReportType))
//    	{
    		mMonth=26;
    		mTitle=mTitle+"25";
    		mPayCount=2;
//    	}
//    	else
//    	{
//    		mMonth=38;
//    		mTitle=mTitle+"25(口径二)";
//    	}
//    	mPayCount=2;
    }
    else if("3".equals(mReportType))     //37个月继续率
    {
    	mMonth=38;
        mPayCount=3;
        mTitle=mTitle+"37";
    }
    else if("4".equals(mReportType))
    {
		mMonth=26;
		mTitle=mTitle+"25(口径二新)";
		mPayCount=2;
    }
    //加入实收截止日期，需要区分是正常交费还是垫交，垫交件用还垫日期
    mActEndDatesql=" and (p5!=1 and tmakedate<='"+"?mActEndDate?"+"' or p5=1 and calcdate<='"+"?mActEndDate?"+"') ";

    if("1".equals(mReportType)||"2".equals(mReportType)||"3".equals(mReportType))
    {
    	mReportTypeSql= " (select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
        + " (select (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+this.mActEndDatesql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
        ;
    }
    else if("4".equals(mReportType)) //此口径下，分母为13个月实收保费，其中不包括复效，还垫保单保费。
    {
    	mReportTypeSql= " (select (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) from lacommision where payyear="+(this.mPayCount-1)+" and commdire='1' and polno=a.polno  and p5!=1  and not exists(select 1 from ljapayperson where payno=lacommision.receiptno and payaimclass='4')) firstmoney , "
            + " (select (case when sum(transmoney) is not null then sum(transmoney)  else 0 end) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+this.mActEndDatesql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
            ;
    }
   }
   mRiskCodeSql=" ";  //加入险种限制信息
   if(mRiskType.equals("1")&&!mRiskCode.equals(""))
   {
	   mRiskCodeSql=" and a.riskcode='"+"?mRiskCode?"+"' ";
   }

   logger.debug("***mMonth: "+mMonth);
   logger.debug("***mPayCount: "+mPayCount);
   logger.debug("***mTitle: "+mTitle);


   return true;
 }

 //管理机构选项下统计(不区分险种)
 private boolean dealData_norisk()
 {
  try
  {
   int m;
   String tsql = "";
   String strArr[] = null;
   tsql = "select * from LDCom where char_length(trim(ComCode))="+"?mManageLen?"+" and comcode<>'8699' and comcode >='"+"?mManageCom0?"+"' and comcode <='"+"?mManageCom9?"+"' order by comcode";
//   tsql = "select * from LDCom where length(trim(ComCode))="+mManageLen+" and comcode<>'8699' order by comcode";
   SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
   sqlbv2.sql(tsql);
   sqlbv2.put("mManageLen", mManageLen);
   sqlbv2.put("mManageCom0", PubFun.RCh(mManageCom,"0",mManageLen));
   sqlbv2.put("mManageCom9", PubFun.RCh(mManageCom,"9",mManageLen));
   logger.debug("Comcode_SQL: "+tsql);
   LDComDB tLDComDB = new LDComDB();
   LDComSet tLDComSet = new LDComSet();
   LDComSchema tLDComSchema;
   tLDComSet.set(tLDComDB.executeQuery(sqlbv2));
   if (tLDComDB.mErrors.needDealError() == true)
   {
    mErrors.copyAllErrors(tLDComDB.mErrors);
    CError cError = new CError( );
    cError.moduleName = "PolContinuedRateBL";
    cError.functionName = "dealData";
    cError.errorMessage = "PolContinuedRateBL在读取数据库时发生错误";
    mErrors.addOneError(cError);
    return false;
   }
   int row = tLDComSet.size()+1;
   logger.debug("分公司管理机构数目："+tLDComSet.size());
   strArr = new String[5];
   String StringArray[][] = new String[row][5] ;
   Hashtable tHashtable = new Hashtable();
   for (m=0;m<tLDComSet.size();m++)
   {
    tLDComSchema = new LDComSchema();
    tLDComSchema.setSchema(tLDComSet.get(m+1));
    logger.debug(tLDComSchema.getComCode()+":"+tLDComSchema.getShortName());
    StringArray[m][0]=tLDComSchema.getShortName();
    StringArray[m][1]=tLDComSchema.getComCode();
    tHashtable.put(tLDComSchema.getComCode(),new Integer(m));
   }
   StringArray[m][0]="合  计 ";
   StringArray[m][1]="";

   //初始化二维数组中的值
   for( int i=0 ; i<row ; i++ )
    {
     for(int j=2 ; j<5 ; j++ )
     {
       if(j==4)
       {
       StringArray[i][j]="0.0000";   //比率取四位小数
       }
      else
      {
       StringArray[i][j]="0.00";    //一般值取两位小数
      }
    }
   }

   ExeSQL tExeSQL = new ExeSQL();
//
   String tSQL = "";
   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	   tSQL = "select substr(managecom,1,"+"?mManageLen?"+") submanage,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select  /*+index( a IDX_LCPOL_CVALIDATE)*/ managecom, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               + "and PayIntV=12 "
               //+ "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select  managecom, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by substr(managecom,1,"+"?mManageLen?"+")"
              ;
   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	   tSQL = "select substr(managecom,1,"+"?mManageLen?"+") submanage,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select managecom, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               + "and PayIntV=12 "
               //+ "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select  managecom, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by substr(managecom,1,"+"?mManageLen?"+")"
              ;
   }
				   SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				   sqlbv3.sql(tSQL);
				   sqlbv3.put("mManageLen", mManageLen);
				   sqlbv3.put("mActEndDate", mActEndDate);
				   sqlbv3.put("mMonth", mMonth);
				   sqlbv3.put("mStartDate", mStartDate);
				   sqlbv3.put("mEndDate", mEndDate);
				   sqlbv3.put("mManageCom0", PubFun.RCh(mManageCom,"0",8));
				   sqlbv3.put("mManageCom9", PubFun.RCh(mManageCom,"9",8));
      logger.debug("*****SQL: "+tSQL);
//      SSRS tSSRS = this.getPerRate(mReportType, mManageCom,tLDComSet);
   		SSRS tSSRS = new SSRS();
      tSSRS = tExeSQL.execSQL(sqlbv3);
      //合并个险续期数据
     for(m=1;m<=tSSRS.getMaxRow();m++)
     {
      int tIndex = ((Integer)tHashtable.get(tSSRS.GetText(m,1))).intValue();
      StringArray[tIndex][2] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(m,2)));
      StringArray[tIndex][3] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(m,3)));
      StringArray[tIndex][4] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(m,4)));
     }

     //计算合计行各列的值
     for(int j=2 ; j<5 ; j++ )
     {
       for( int i=0 ; i<row-1 ; i++ )
       {
        StringArray[row-1][j]=String.valueOf(Double.parseDouble(StringArray[row-1][j])+Double.parseDouble(StringArray[i][j]));
       }
       if(j==4)   //如果是比例列，其值为 合计实收/合计首期承保保费，并取4位小数；如果一般的值，直接取三位小数
       {
        StringArray[row-1][j] = mDecimalFormat1.format(Double.parseDouble(StringArray[row-1][j-1])/(Double.parseDouble(StringArray[row-1][j-2])+0.0000000001)); //防止首期保费合计为0
       }
       else
       {
         StringArray[row-1][j] = mDecimalFormat.format(Double.parseDouble(StringArray[row-1][j]));
       }
    }

    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例

    if("2".equals(mReportType))   //25个月继续率
    {
     xmlexport.createDocument("PolContinuedRate1.vts","printer");
    }
    else
    {
     xmlexport.createDocument("PolContinuedRate.vts","printer");
    }
    ListTable alistTable = new ListTable();
    alistTable.setName("INFO");

    String[][] result= new String[row][5]; //用来装最后的结果数据
    for(int i=0;i<row;i++)
    {
     strArr = new String[5];
     strArr = StringArray[i];
     alistTable.add(strArr);
     result[i]=strArr;
    }

    xmlexport.addDisplayControl("displayinfo");
    xmlexport.addListTable(alistTable, strArr);
    texttag.add("SysDate",PubFun.getCurrentDate());
    texttag.add("StartDate",mStartDate);
    texttag.add("EndDate",mEndDate);
    texttag.add("Title",mTitle);

    if (texttag.size()>0)
      xmlexport.addTextTag(texttag);
//    xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
    mResult.clear();
    mResult.addElement(xmlexport);

    if(!trans(result,row,5))
       {
          logger.debug("将数组中为null的项置为空时出错");
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "trans";
          tError.errorMessage = "将数组中为null的项置为空时出错!";
          this.mErrors.addOneError(tError);

          return false;
       }


    TransferData tempTransferData=new TransferData();
    //输入表头等信息
    tempTransferData.setNameAndValue("&count", mTitle);
    tempTransferData.setNameAndValue("&tjdate", PubFun.getCurrentDate());
    tempTransferData.setNameAndValue("&cbqj",mStartDate + "至" + mEndDate);

    HashReport xHashReport = new HashReport();
    String tpath = "";
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
    tLDSysVarDB.setSysVar("XSCreatListPath");
    if (!tLDSysVarDB.getInfo()) {
      return false;
    }
    tpath = tLDSysVarDB.getSysVarValue();
    String tFileName="";
    //tpath= "D:\\temp\\";
     if(mReportType.equals("2"))
        {
           tFileName="PolContinuedRate1" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
            "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

         xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		 templatepath + "PolContinuedRate1.xls",
                                  result, tempTransferData);

        }
     else if(mReportType.equals("4"))
     {
         tFileName="PolContinuedRate3" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
           "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

        xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		templatepath + "PolContinuedRate3.xls",
                                 result, tempTransferData);

     }
    else
      {
          tFileName="PolContinuedRate" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
            "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

         xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		 templatepath + "PolContinuedRate.xls",
                                  result, tempTransferData);

      }

    //tpath = "D:\\TEMP\\";



  }
  catch(Exception ex)
  {
    ex.printStackTrace();
    CError tError = new CError();
    tError.errorMessage = "统计时发生异常！";
    this.mErrors.addOneError(tError);
    return false;
   }
  return true;
 }


 //管理机构选项下统计(区分险种)
 private boolean dealData_risk()
 {
  try
  {
    double tSumFirstPrem =0;  //首期保费之和
	double tSumActuPrem = 0; //实收保费之和

	ListTable alistTable = new ListTable();
    alistTable.setName("INFO");

    ExeSQL tExeSQL = new ExeSQL();
    String tSQL = "";
    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	tSQL = " select (select shortname from ldcom where trim(comcode)=submanage ),submanage,(select riskname from lmriskapp where riskcode=y ),sumfirstmoney,sumsecondmoney,rate from ("
	               +"select substr(managecom,1,"+"?mManageLen?"+") submanage,riskcode y ,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
                + "( "
                + "(select  /*+index( a IDX_LCPOL_CVALIDATE)*/ managecom,riskcode, "
//                + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//                + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
                +this.mReportTypeSql
                + "from lcpol a "
                + "where AppFlag='1' "
                + "and PayIntV=12 "
                //+ "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
                + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
                + "and GrpPolNo='00000000000000000000' "
                //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
                +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
                +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
                +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
                +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
                +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
                +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
                + mSalechnlSql
                + mRiskCodeSql
                + "and cvalidate>='"+"?mStartDate?"+"' "
                + "and cvalidate<='"+"?mEndDate?"+"' "
                + "and managecom >='"+"?mManageCom0?"+"' "
                + "and managecom <='"+"?mManageCom9?"+"' "
                + ") "
                + "union all "
                + "(select  managecom,riskcode, "
//                + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//                + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
                +this.mReportTypeSql
                + "from lcpol a "
                + "where AppFlag='4' "
                + "and PayIntV=12 "
                + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
                //+ "and substr(polstate,1,2) not in ('03','05') "
                + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
                + "and GrpPolNo='00000000000000000000' "
                + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
                + mSalechnlSql
                + mRiskCodeSql
                + "and cvalidate>='"+"?mStartDate?"+"' "
                + "and cvalidate<='"+"?mEndDate?"+"' "
                + "and managecom >='"+"?mManageCom0?"+"' "
                + "and managecom <='"+"?mManageCom9?"+"' "
                + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
                + ") "
                + ") group by substr(managecom,1,"+"?mManageLen?"+"),riskcode ) d"
               ;
    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	tSQL = " select (select shortname from ldcom where trim(comcode)=submanage ),submanage,(select riskname from lmriskapp where riskcode=y ),sumfirstmoney,sumsecondmoney,rate from ("
	               +"select substr(managecom,1,"+"?mManageLen?"+") submanage,riskcode y ,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
                + "( "
                + "(select  managecom,riskcode, "
//                + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//                + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
                +this.mReportTypeSql
                + "from lcpol a "
                + "where AppFlag='1' "
                + "and PayIntV=12 "
                //+ "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
                + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
                + "and GrpPolNo='00000000000000000000' "
                //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
                +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
                +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
                +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
                +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
                +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
                +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
                + mSalechnlSql
                + mRiskCodeSql
                + "and cvalidate>='"+"?mStartDate?"+"' "
                + "and cvalidate<='"+"?mEndDate?"+"' "
                + "and managecom >='"+"?mManageCom0?"+"' "
                + "and managecom <='"+"?mManageCom9?"+"' "
                + ") "
                + "union all "
                + "(select  managecom,riskcode, "
//                + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//                + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno   "+this.mActuPaySql+") secondmoney "
                +this.mReportTypeSql
                + "from lcpol a "
                + "where AppFlag='4' "
                + "and PayIntV=12 "
                + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
                //+ "and substr(polstate,1,2) not in ('03','05') "
                + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
                + "and GrpPolNo='00000000000000000000' "
                + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
                + mSalechnlSql
                + mRiskCodeSql
                + "and cvalidate>='"+"?mStartDate?"+"' "
                + "and cvalidate<='"+"?mEndDate?"+"' "
                + "and managecom >='"+"?mManageCom0?"+"' "
                + "and managecom <='"+"?mManageCom9?"+"' "
                + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
                + ") "
                + ") group by substr(managecom,1,"+"?mManageLen?"+"),riskcode ) d"
               ;
    }
       SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
	   sqlbv4.sql(tSQL);
	   sqlbv4.put("mManageLen", mManageLen);
	   sqlbv4.put("mActEndDate", mActEndDate);
	   sqlbv4.put("mRiskCode", mRiskCode);
	   sqlbv4.put("mMonth", mMonth);
	   sqlbv4.put("mStartDate", mStartDate);
	   sqlbv4.put("mEndDate", mEndDate);
	   sqlbv4.put("mManageCom0", PubFun.RCh(mManageCom,"0",8));
	   sqlbv4.put("mManageCom9", PubFun.RCh(mManageCom,"9",8));
      logger.debug("*****SQL: "+tSQL);
   	  SSRS tSSRS = new SSRS();
      tSSRS = tExeSQL.execSQL(sqlbv4);
      mCount = tSSRS.getMaxRow();
      logger.debug("tSSRS查询的结果是"+tSSRS.getMaxRow());
      if(tSSRS.getMaxRow()<=0)
       {
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "checkData";
          tError.errorMessage = "没有所要查询的数据!";
          this.mErrors.addOneError(tError);
          return false;

       }

      String[][] result= new String[tSSRS.getMaxRow()+1][6]; //用来装最后的结果数据
      for(int main_count=1;main_count <= tSSRS.getMaxRow(); main_count++)
       {
          String[] cols = new String[6];

           cols[0] = tSSRS.GetText(main_count,1);//机构名称
           cols[1] = tSSRS.GetText(main_count,2);//机构代码
           cols[2] = tSSRS.GetText(main_count,3);//险种名称
           cols[3] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,4)));//首期保费
           cols[4] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,5)));//实收保费
           cols[5] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(main_count,6)));//实收保费与承保保费的比例
           tSumFirstPrem +=Double.parseDouble(tSSRS.GetText(main_count,4));
           tSumActuPrem +=Double.parseDouble(tSSRS.GetText(main_count,5));
           alistTable.add(cols);
           result[main_count-1]=cols;

      }
      String[] b_col = new String[6];
      b_col[0]= "合  计 ";
      b_col[1]= "";
      b_col[2]= "";
      b_col[3]= mDecimalFormat.format(tSumFirstPrem);
      b_col[4]= mDecimalFormat.format(tSumActuPrem);
      b_col[5]= mDecimalFormat1.format(Double.parseDouble(b_col[4])/(Double.parseDouble(b_col[3])+0.000000001));  //防止分母为0

      alistTable.add(b_col);
      result[tSSRS.getMaxRow()]=b_col;

    TextTag texttag=new TextTag();//新建一个TextTag的实例
    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例

    if("2".equals(mReportType))   //25个月继续率
    {
     xmlexport.createDocument("PolContinuedRiskRate1.vts","printer");
    }
    else
    {
     xmlexport.createDocument("PolContinuedRiskRate.vts","printer");
    }


    xmlexport.addDisplayControl("displayinfo");
    xmlexport.addListTable(alistTable, b_col);
    texttag.add("SysDate",PubFun.getCurrentDate());
    texttag.add("StartDate",mStartDate);
    texttag.add("EndDate",mEndDate);
    texttag.add("Title",mTitle);

    if (texttag.size()>0)
      xmlexport.addTextTag(texttag);
//    xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
    mResult.clear();
    mResult.addElement(xmlexport);

    if(!trans(result,tSSRS.getMaxRow()+1,6))
       {
          logger.debug("将数组中为null的项置为空时出错");
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "trans";
          tError.errorMessage = "将数组中为null的项置为空时出错!";
          this.mErrors.addOneError(tError);

          return false;
       }


    TransferData tempTransferData=new TransferData();
    //输入表头等信息
    tempTransferData.setNameAndValue("&count", mTitle);
    tempTransferData.setNameAndValue("&tjdate", PubFun.getCurrentDate());
    tempTransferData.setNameAndValue("&cbqj",mStartDate + "至" + mEndDate);

    HashReport xHashReport = new HashReport();
    String tpath = "";
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();
    tLDSysVarDB.setSysVar("XSCreatListPath");
    if (!tLDSysVarDB.getInfo()) {
      return false;
    }
    tpath = tLDSysVarDB.getSysVarValue();
    String tFileName="";
    //tpath= "D:\\temp\\";
     if(mReportType.equals("2"))
        {
           tFileName="PolContinuedRiskRate1" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
            "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

         xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		 templatepath + "PolContinuedRiskRate1.xls",
                                  result, tempTransferData);

        }
     else if(mReportType.equals("4"))
     {
    	 tFileName="PolContinuedRiskRate3" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
         "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

      xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		  templatepath + "PolContinuedRiskRate3.xls",
                               result, tempTransferData);

     }
    else
      {
          tFileName="PolContinuedRiskRate" + "_" + mStartDate+"_"+mEndDate + "_" + mSalechnlType +
            "_" + mManageType+"_"+mReportType+"_"+mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

         xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		 templatepath + "PolContinuedRiskRate.xls",
                                  result, tempTransferData);

      }

    //tpath = "D:\\TEMP\\";



  }
  catch(Exception ex)
  {
    ex.printStackTrace();
    CError tError = new CError();
    tError.errorMessage = "统计时发生异常！";
    this.mErrors.addOneError(tError);
    return false;
   }
  return true;
 }

 /**
  * 处理中介机构的继续率统计(不区分险种)
  */
 private boolean dealLAComData_norisk()
 {
  try
  {
   double tSumFirstPrem =0;  //首期保费之和
   double tSumActuPrem = 0; //实收保费之和
   TextTag texttag=new TextTag();//新建一个TextTag的实例
   XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
   ListTable alistTable = new ListTable();
   alistTable.setName("INFO");

   if("2".equals(mReportType))   //25个月继续率
   {
    mTitle =mTitle + "个月实收保费";
    xmlexport.createDocument("PolContinuedLAComRate1.vts","printer");
   }
   else
   {
    mTitle =mTitle + "个月继续率";
    xmlexport.createDocument("PolContinuedLAComRate.vts","printer");
   }

   ExeSQL tExeSQL = new ExeSQL();
   String tSQL = "";
   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	   tSQL =   "select (select name from lacom where agentcom=x) agentname,sumfirstmoney,sumsecondmoney,rate from "
               + " (select  x,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select  /*+index( a IDX_LCPOL_CVALIDATE)*/ substr(agentcom,1,"+"?mManageLen?"+") x, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + "and PayIntV=12 "
              // + "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select  substr(agentcom,1,"+"?mManageLen?"+") x, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by x ) d"
               ;
   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	   tSQL =   "select (select name from lacom where agentcom=x) agentname,sumfirstmoney,sumsecondmoney,rate from "
               + " (select  x,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select substr(agentcom,1,"+"?mManageLen?"+") x, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + "and PayIntV=12 "
              // + "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select  substr(agentcom,1,"+"?mManageLen?"+") x, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by x ) d"
               ;
   }
   
   SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
   sqlbv5.sql(tSQL);
   sqlbv5.put("mManageLen", mManageLen);
   sqlbv5.put("mActEndDate", mActEndDate);
   sqlbv5.put("mMonth", mMonth);
   sqlbv5.put("mStartDate", mStartDate);
   sqlbv5.put("mEndDate", mEndDate);
   sqlbv5.put("mManageCom0", PubFun.RCh(mManageCom,"0",8));
   sqlbv5.put("mManageCom9", PubFun.RCh(mManageCom,"9",8));
//      logger.debug("*****SQL: "+tSQL);
//      SSRS tSSRS =this.getAgentComRate(this.mReportType,this.mManageCom);
       SSRS tSSRS = new SSRS();
       tSSRS = tExeSQL.execSQL(sqlbv5);
      //合并个险续期数据
      mCount = tSSRS.getMaxRow();
      logger.debug("tSSRS查询的结果是"+tSSRS.getMaxRow());
      if(tSSRS.getMaxRow()<=0)
       {
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "checkData";
          tError.errorMessage = "没有所要查询的数据!";
          this.mErrors.addOneError(tError);
          return false;

       }
       String[][] result= new String[tSSRS.getMaxRow()+1][4]; //用来装最后的结果数据
       for(int main_count=1;main_count <= tSSRS.getMaxRow(); main_count++)
        {
           String[] cols = new String[4];

            cols[0] = tSSRS.GetText(main_count,1);//代理机构名称
            cols[1] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,2)));//首期保费
            cols[2] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,3)));//实收保费
            cols[3] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(main_count,4)));//实收保费与承保保费的比例
            tSumFirstPrem +=Double.parseDouble(tSSRS.GetText(main_count,2));
            tSumActuPrem +=Double.parseDouble(tSSRS.GetText(main_count,3));
            alistTable.add(cols);
            result[main_count-1]=cols;

       }
       String[] b_col = new String[4];
       b_col[0]= "合  计 ";
       b_col[1]= mDecimalFormat.format(tSumFirstPrem);
       b_col[2]= mDecimalFormat.format(tSumActuPrem);
       b_col[3]= mDecimalFormat1.format(Double.parseDouble(b_col[2])/(Double.parseDouble(b_col[1])+0.000000001));  //防止分母为0

       alistTable.add(b_col);
       result[tSSRS.getMaxRow()]=b_col;

       logger.debug("开始生成文件！");
       xmlexport.addDisplayControl("displayinfo");
       xmlexport.addListTable(alistTable, b_col);

      texttag.add("SysDate",PubFun.getCurrentDate());
      texttag.add("StartDate",mStartDate);
      texttag.add("EndDate",mEndDate);
      texttag.add("Title",mTitle);

      if (texttag.size()>0)
        xmlexport.addTextTag(texttag);
//     xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
     mResult.clear();
     mResult.addElement(xmlexport);

     if(!trans(result,tSSRS.getMaxRow()+1,4))
       {
          logger.debug("将数组中为null的项置为空时出错");
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "trans";
          tError.errorMessage = "将数组中为null的项置为空时出错!";
          this.mErrors.addOneError(tError);

          return false;
       }


     TransferData tempTransferData=new TransferData();
     //输入表头等信息
     tempTransferData.setNameAndValue("&count", mTitle);
     tempTransferData.setNameAndValue("&tjdate", PubFun.getCurrentDate());
     tempTransferData.setNameAndValue("&cbqj", mStartDate + "至" + mEndDate);

     HashReport xHashReport = new HashReport();
     String tpath = "";
     LDSysVarDB tLDSysVarDB = new LDSysVarDB();
     tLDSysVarDB.setSysVar("XSCreatListPath");
     if (!tLDSysVarDB.getInfo())
     {
       return false;
     }
     tpath = tLDSysVarDB.getSysVarValue();
     //本地测试用
     //tpath = "d:\\temp\\";
     String tFileName = "";

     if (mReportType.equals("2"))
     {
       tFileName = "PolContinuedLAComRate1" + "_" + mStartDate + "_" + mEndDate + "_" +
           mSalechnlType +
           "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

       xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		   templatepath + "PolContinuedLAComRate1.xls",
                                      result, tempTransferData);

     }
     else if(mReportType.equals("4"))
     {
    	 tFileName = "PolContinuedLAComRate3" + "_" + mStartDate + "_" + mEndDate + "_" +
         mSalechnlType +
         "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

       xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		   templatepath + "PolContinuedLAComRate3.xls",
                                    result, tempTransferData);

     }
     else
     {
       tFileName = "PolContinuedLAComRate" + "_" + mStartDate + "_" + mEndDate + "_" +
           mSalechnlType +
           "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+ mActEndDate+"_"+this.mGI.ComCode;

       xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		   templatepath + "PolContinuedLAComRate.xls",
                                      result, tempTransferData);

     }


  }
  catch(Exception ex)
  {
    ex.printStackTrace();
    CError tError = new CError();
    tError.errorMessage = "统计时发生异常！";
    this.mErrors.addOneError(tError);
    return false;
   }
  return true;
 }


 /**
  * 处理中介机构的继续率统计(区分险种)
  */
 private boolean dealLAComData_risk()
 {
  try
  {
   double tSumFirstPrem =0;  //首期保费之和
   double tSumActuPrem = 0; //实收保费之和
   TextTag texttag=new TextTag();//新建一个TextTag的实例
   XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
   ListTable alistTable = new ListTable();
   alistTable.setName("INFO");

   if("2".equals(mReportType))   //25个月继续率
   {
    mTitle =mTitle + "个月实收保费";
    xmlexport.createDocument("PolContinuedLAComRiskRate1.vts","printer");
   }
   else
   {
    mTitle =mTitle + "个月继续率";
    xmlexport.createDocument("PolContinuedLAComRiskRate.vts","printer");
   }

   ExeSQL tExeSQL = new ExeSQL();
   String tSQL ="";
   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	   tSQL =   "select (select name from lacom where agentcom=x) agentname,(select riskname from lmriskapp where riskcode=y) triskname,sumfirstmoney,sumsecondmoney,rate from "
               + " (select  x,y,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select  /*+index( a IDX_LCPOL_CVALIDATE)*/ substr(agentcom,1,"+"?mManageLen?"+") x,riskcode y, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + "and PayIntV=12 "
              // + "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + mSalechnlSql
               + mRiskCodeSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select   substr(agentcom,1,"+"?mManageLen?"+") x,riskcode y, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno  and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + mRiskCodeSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by x,y ) d"
               ;
   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	   tSQL =   "select (select name from lacom where agentcom=x) agentname,(select riskname from lmriskapp where riskcode=y) triskname,sumfirstmoney,sumsecondmoney,rate from "
               + " (select  x,y,sum(firstmoney)/10000 sumfirstmoney,sum(secondmoney)/10000 sumsecondmoney, (case sum(firstmoney) when 0 then 0 else sum(secondmoney) end)/(case sum(firstmoney) when 0 then 1 else sum(firstmoney) end) rate from "
               + "( "
               + "(select  substr(agentcom,1,"+"?mManageLen?"+") x,riskcode y, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno  "+this.mActuPaySql+") secondmoney "  //扣除增额交清和自动垫交的实收,如果垫交保单已经还垫并且还垫在统计的时间段类,也算实收
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='1' "
               //+ "  and not exists (select 1 from ldsystrace where polstate = '4001' and valiflag = '1' and makedate<=add_months(a.cvalidate, "+mMonth+") and polno=a.polno) "
               +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno  "
               +"    union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
               +"        and substr(y.ReasonCode, 2) = '02' and  x.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and x.contno =a.contno "
               +"    union select 1  from LLReportReason t  where (customerno =a.appntno or customerno=a.insuredno)"
               +"        and  t.makedate<=add_months(a.cvalidate, "+"?mMonth?"+") and substr(ReasonCode, 2) = '02') "
               + "and PayIntV=12 "
              // + "and substr(polstate,1,2) not in ('03','04','05') "  //非终止,非豁免,非暂停件
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + mSalechnlSql
               + mRiskCodeSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + ") "
               + "union all "
               + "(select   substr(agentcom,1,"+"?mManageLen?"+") x,riskcode y, "
//               + "(select nvl(sum(sumactupaymoney),0) from ljapayperson where polno=a.polno and paycount=1 and paytype='ZC') firstmoney, "
//               + "(select nvl(sum(transmoney),0) from lacommision where payyear="+this.mPayCount+" and commdire='1' and polno=a.polno "+this.mActuPaySql+") secondmoney "
               +this.mReportTypeSql
               + "from lcpol a "
               + "where AppFlag='4' "
               + "and PayIntV=12 "
               + "and not exists(select 1 from lpedoritem where contno=a.contno  and edortype='WT' and edorstate='0') "
               //+ "and substr(polstate,1,2) not in ('03','05') "
               + "and exists(select 1 from lcduty c where a.contno=c.contno and (c.FreeFlag != '1' or c.FreeFlag is null))"  //非豁免
               + "and GrpPolNo='00000000000000000000' "
               + "and not exists(select 1 from lpedorapp c,lpedoritem d where  c.edoracceptno=d.edoracceptno and  d.contno=a.contno and d.edortype='XT' and c.apptype='6' and c.switchchnltype='02' and c.confdate<=add_months(a.cvalidate, "+"?mMonth?"+")) "//"年交保费继续率统计"报表中，凡因理赔解决发生的过失退保，不计入分母统计(保全项目GT,原因为"业务需要解除合同")
               + mSalechnlSql
               + mRiskCodeSql
               + "and cvalidate>='"+"?mStartDate?"+"' "
               + "and cvalidate<='"+"?mEndDate?"+"' "
               + "and managecom >='"+"?mManageCom0?"+"' "
               + "and managecom <='"+"?mManageCom9?"+"' "
               + "and not exists(select 1 from llclaimpolicy c ,lccontstate  l where c.polno=l.polno and l.statetype='Terminate' and l.statereason='04' and l.polno=a.polno and c.EndCaseDate<=add_months(a.cvalidate,"+"?mMonth?"+")) "
               + ") "
               + ") group by x,y ) d"
               ;
   }
   
   SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
   sqlbv6.sql(tSQL);
   sqlbv6.put("mManageLen", mManageLen);
   sqlbv6.put("mActEndDate", mActEndDate);
   sqlbv6.put("mRiskCode", mRiskCode);
   sqlbv6.put("mMonth", mMonth);
   sqlbv6.put("mStartDate", mStartDate);
   sqlbv6.put("mEndDate", mEndDate);
   sqlbv6.put("mManageCom0", PubFun.RCh(mManageCom,"0",8));
   sqlbv6.put("mManageCom9", PubFun.RCh(mManageCom,"9",8));
//      logger.debug("*****SQL: "+tSQL);
//      SSRS tSSRS =this.getAgentComRate(this.mReportType,this.mManageCom);
       SSRS tSSRS = new SSRS();
       tSSRS = tExeSQL.execSQL(sqlbv6);
      //合并个险续期数据
      mCount = tSSRS.getMaxRow();
      logger.debug("tSSRS查询的结果是"+tSSRS.getMaxRow());
      if(tSSRS.getMaxRow()<=0)
       {
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "checkData";
          tError.errorMessage = "没有所要查询的数据!";
          this.mErrors.addOneError(tError);
          return false;

       }
       String[][] result= new String[tSSRS.getMaxRow()+1][5]; //用来装最后的结果数据
       for(int main_count=1;main_count <= tSSRS.getMaxRow(); main_count++)
        {
           String[] cols = new String[5];

            cols[0] = tSSRS.GetText(main_count,1);//代理机构名称
            cols[1] = tSSRS.GetText(main_count,2);//险种名称
            cols[2] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,3)));//首期保费
            cols[3] = mDecimalFormat.format(Double.parseDouble(tSSRS.GetText(main_count,4)));//实收保费
            cols[4] = mDecimalFormat1.format(Double.parseDouble(tSSRS.GetText(main_count,5)));//实收保费与承保保费的比例
            tSumFirstPrem +=Double.parseDouble(tSSRS.GetText(main_count,3));
            tSumActuPrem +=Double.parseDouble(tSSRS.GetText(main_count,4));
            alistTable.add(cols);
            result[main_count-1]=cols;

       }
       String[] b_col = new String[5];
       b_col[0]= "合  计 ";
       b_col[1]= "";
       b_col[2]= mDecimalFormat.format(tSumFirstPrem);
       b_col[3]= mDecimalFormat.format(tSumActuPrem);
       b_col[4]= mDecimalFormat1.format(Double.parseDouble(b_col[3])/(Double.parseDouble(b_col[2])+0.000000001));  //防止分母为0

       alistTable.add(b_col);
       result[tSSRS.getMaxRow()]=b_col;

       logger.debug("开始生成文件！");
       xmlexport.addDisplayControl("displayinfo");
       xmlexport.addListTable(alistTable, b_col);

      texttag.add("SysDate",PubFun.getCurrentDate());
      texttag.add("StartDate",mStartDate);
      texttag.add("EndDate",mEndDate);
      texttag.add("Title",mTitle);

      if (texttag.size()>0)
        xmlexport.addTextTag(texttag);
//     xmlexport.outputDocumentToFile("e:/","PolContinuedRate");//输出xml文档到文件
     mResult.clear();
     mResult.addElement(xmlexport);

     if(!trans(result,tSSRS.getMaxRow()+1,5))
       {
          logger.debug("将数组中为null的项置为空时出错");
          CError tError = new CError();
          tError.moduleName = "PolContinuedRateBL";
          tError.functionName = "trans";
          tError.errorMessage = "将数组中为null的项置为空时出错!";
          this.mErrors.addOneError(tError);

          return false;
       }


     TransferData tempTransferData=new TransferData();
     //输入表头等信息
     tempTransferData.setNameAndValue("&count", mTitle);
     tempTransferData.setNameAndValue("&tjdate", PubFun.getCurrentDate());
     tempTransferData.setNameAndValue("&cbqj", mStartDate + "至" + mEndDate);

     HashReport xHashReport = new HashReport();
     String tpath = "";
     LDSysVarDB tLDSysVarDB = new LDSysVarDB();
     tLDSysVarDB.setSysVar("XSCreatListPath");
     if (!tLDSysVarDB.getInfo())
     {
       return false;
     }
     tpath = tLDSysVarDB.getSysVarValue();
     //本地测试用
     //tpath = "d:\\temp\\";
     String tFileName = "";

     if (mReportType.equals("2"))
     {
       tFileName = "PolContinuedLAComRiskRate1" + "_" + mStartDate + "_" + mEndDate + "_" +
           mSalechnlType +
           "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

       xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		   templatepath + "PolContinuedLAComRiskRate1.xls",
                                      result, tempTransferData);

     }
     else if(mReportType.equals("4"))
     {
    	 tFileName = "PolContinuedLAComRiskRate3" + "_" + mStartDate + "_" + mEndDate + "_" +
         mSalechnlType +
         "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

         xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
        		 templatepath + "PolContinuedLAComRiskRate3.xls",
                                    result, tempTransferData);

     }
     else
     {
       tFileName = "PolContinuedLAComRiskRate" + "_" + mStartDate + "_" + mEndDate + "_" +
           mSalechnlType +
           "_" + mManageType + "_" + mReportType + "_" + mPremType+"_"+mRiskCode+"_"+ mActEndDate+"_"+this.mGI.ComCode;

       xHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
    		   templatepath + "PolContinuedLAComRiskRate.xls",
                                      result, tempTransferData);

     }


  }
  catch(Exception ex)
  {
    ex.printStackTrace();
    CError tError = new CError();
    tError.errorMessage = "统计时发生异常！";
    this.mErrors.addOneError(tError);
    return false;
   }
  return true;
 }
// private SSRS getAgentComRate(String tRateType,String tManageCom)
// {
//	 SSRS result_ssrs = new SSRS(4);
//
//	 return result_ssrs;
// }

 public VData getResult()
 {
   return this.mResult;
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

 public static void main(String[] args)
 {
  NewPolContinuedRate2BL PolContinuedRateBL1 = new NewPolContinuedRate2BL();
   VData tVData = new VData();
//    VData mResult = new VData();
    GlobalInput tG = new GlobalInput();
    tG.ManageCom="86";
    tG.Operator ="001";
    tG.ComCode="86";
    String StartDate ="2008-12-01";
    String EndDate ="2008-12-31";
    String ManageType ="3";
    String SalechnlType ="5";
    String ReportType = "1";
    String PremType = "1";
    String RiskType = "2";
    String ActEndDate = "";
//    tVData.addElement(tG.ComCode);
    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(ManageType);
    tVData.addElement(SalechnlType);
    tVData.addElement(ReportType);
    tVData.addElement(PremType);
    tVData.addElement(RiskType);
    tVData.addElement("");
    tVData.addElement("2010-02-02");
    tVData.addElement("D:\\公司资料\\执行任务\\");
    tVData.addElement(tG);
    PolContinuedRateBL1.submitData(tVData,"PRINT");

 }
}
