package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

import com.f1j.ss.*;

/**
 * <p>ClassName: ReAgentPlcDownBL1 </p>
 * <p>Description: 在职单孤儿单应收清单生成 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @author: duanyh
 * @version: 1.0
 * @date: 2007-8-24
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.text.*;

public class LRPolListCDownBL
{
private static Logger logger = Logger.getLogger(LRPolListCDownBL.class);

  public CErrors mErrors = new CErrors();
  private TransferData mTransferData = new TransferData();
  private GlobalInput mGlobalInput = new GlobalInput();
  private String mOperate = "";
  private String mManageCom = "";
  private String mAgentCode = "";
  private String mStartDate = "";
  private String mEndDate = "";
  private String mType1 = "";   //1：孤儿单；2：在职单；3：不区分
  private String mType2 = "";   //1：二次；2：三次；3：不区分
  private String mType3 = "";   //1：续期件；2：续保件；3：不区分
  private String mType4 = "";   //1：年交；0：非年交
  private String mBranchType = "";   //4：续收外勤；99：收展
  private String mFileModeDesc = "ReAgentAnyDown1.xls"; //描述的模版名称
  private String mXSExcTemplate = ""; //描述的模版读取路径
  private String mFilePathDesc = "XSCreatListPath"; //描述的生成结果文件存放路径
  private String mFilePath = ""; //通过描述得到的文件路径
  private String mFileName = ""; //要生成的文件名
  private String payintv_sql = "";//交费类型sql
  private int mCount = 10000; //一次循环处理10000条记录
  private int mCurrentRow = 1; //行数
  private String mDelFlag = ""; //处理类型标志 0：按机构；1：按个人
  private BookModelImpl m_book = new BookModelImpl();
  private DecimalFormat mDecimalFormat = new DecimalFormat();
  private VData CVData = new VData();
  ExeSQL mExeSQL = new ExeSQL();
  SSRS mSSRS = new SSRS();  //存放需要生成清单的代理人编码集合

  private String currentdate = PubFun.getCurrentDate();
  private String currenttime = PubFun.getCurrentTime();

  public LRPolListCDownBL()
  {
  }
  public static void main(String[] args)
  {
      LRPolListCDownBL tLRPolListCDownBL =new LRPolListCDownBL();
      VData tVData=new VData();
      TransferData tTransferData =new TransferData();
      tTransferData.setNameAndValue("ManageCom","8613");
      tTransferData.setNameAndValue("AgentCode","8613014999");
      tTransferData.setNameAndValue("StartDate","2009-8-1");
      tTransferData.setNameAndValue("EndDate","2009-8-31");
      tTransferData.setNameAndValue("Type1","2");  //清单类型  1|孤儿单|^2|在职单|^3|不区分"
      tTransferData.setNameAndValue("Type2","2");  //交费次数  1|二次|^2|三次|^3|不区分"
      tTransferData.setNameAndValue("Type3","1");  //续期续保  1|续期件|^2|续保件|^3|不区分
      tTransferData.setNameAndValue("Type4","1");  //交费类型  1|年交|^0|非年交
      tTransferData.setNameAndValue("BranchType","4");
      tTransferData.setNameAndValue("Operator","111");
      GlobalInput tGI=new GlobalInput();
      tVData.add(tTransferData);
      tVData.add(tGI);
      tLRPolListCDownBL.submitData(tVData,"");
  }

  public boolean submitData(VData cInputData, String cOperator)
  {
    if (!getInputData(cInputData))
    {
      return false;
    }

    if (!dealData(mSSRS))
    {
      return false;
    }
    return true;
  }
  private String getPolState(String tContNo,String tPolNo){
    ExeSQL tExeSQL  = new ExeSQL();
    String tPolState = "select statetype,state from lccontstate where contno='"+"?tContNo?"+"' and (polno='"+"?tPolNo?"+"' or polno='000000') order by startdate desc";
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    sqlbv1.sql(tPolState);
    sqlbv1.put("tContNo", tContNo);
    sqlbv1.put("tPolNo", tPolNo);
    SSRS tSSRS = new SSRS();
    tSSRS = tExeSQL.execSQL(sqlbv1);
    if (tSSRS.getMaxRow()>0){
      if (tSSRS.GetText(1,2).equals("1")){
        if (tSSRS.GetText(1,1).equals("Available")){
          tPolState = "失效";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("PayPrem")){
          tPolState = "垫交";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Terminate")){
          tPolState = "终止";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Loan")){
          tPolState = "有效";
          return tPolState;
        }
        if (tSSRS.GetText(1,1).equals("Lost")){
          tPolState = "有效";
          return tPolState;
        }
        tPolState = "其他";
        return tPolState;
      }else{
        tPolState = "有效";
      }
    }else{
      tPolState = "未知";
    }
    return tPolState;
  }
  /**
   * 得到外部传入的参数
   * @param sInputData
   * @return
   */
  private boolean getInputData(VData sInputData)
  {
    mTransferData = (TransferData) sInputData.getObjectByObjectName("TransferData",0);
    mGlobalInput = (GlobalInput) sInputData.getObjectByObjectName("GlobalInput",0);

    if ((mTransferData == null) || (mGlobalInput == null))
    {
      this.buildError("getInputData", "没有得到传入的数据");
      return false;
    }
    mManageCom = (String) mTransferData.getValueByName("ManageCom");
    mAgentCode = (String) mTransferData.getValueByName("AgentCode");
    mStartDate = (String) mTransferData.getValueByName("StartDate");
    mEndDate = (String) mTransferData.getValueByName("EndDate");
    mType1 = (String) mTransferData.getValueByName("Type1");
    mType2 = (String) mTransferData.getValueByName("Type2");
    mType3 = (String) mTransferData.getValueByName("Type3");
    mType4 = (String) mTransferData.getValueByName("Type4");
    mBranchType = (String) mTransferData.getValueByName("BranchType");
    mXSExcTemplate = (String) mTransferData.getValueByName("XSExcTemplate");
    if(mType3.equals("2"))
    {
       mType2="3";
    }
    mOperate = (String) mTransferData.getValueByName("Operator");
    //若前台只录入了管理机构，则生成该管理机构下所有代理人的应收清单，首先取出代理人编码
//    String sql = "select distinct(b.agentcode) from lcprem a, LCPol b where a.polno = b.polno"
//	       +" and a.PaytoDate >= '"+this.mStartDate+"'and a.PaytoDate <= '"+this.mEndDate+"' "
//	       +" and b.appflag = '1' and b.salechnl = '02' and length(trim(a.dutycode)) = 6 and length(trim(a.payplancode)) = 6"
//	       +" and (b.StopFlag = '0' or b.StopFlag is null) and a.GrpPolNo = '00000000000000000000'";
    String sql =" select a.agentnew from lrascription a where  "
    +" a.paytodate>='"+"?paytodate?"+"' and a.paytodate<='"+"?payto?"+"' "
    +" and exists(select 1 from laagent where agentcode=a.agentnew and branchtype='"+"?mBranchType?"+"')";
    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
     sqlbv2.sql(sql);
     sqlbv2.put("paytodate", this.mStartDate);
     sqlbv2.put("payto", this.mEndDate);
     sqlbv2.put("mBranchType", mBranchType);
    String sql2 =" select a.agentcode from lradimascription a where  "
    +" a.paytodate>='"+"?paytodate?"+"' and a.paytodate<='"+"?payto?"+"' "
    +" and exists(select 1 from laagent where agentcode=a.agentcode and branchtype='"+"?mBranchType?"+"')";
    sqlbv2.sql(sql2);
    sqlbv2.put("paytodate", this.mStartDate);
    sqlbv2.put("payto", this.mEndDate);
    sqlbv2.put("mBranchType", mBranchType);
    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sql = sql + " and a.ManageCom like concat('"+"?mManageCom?"+"','%' )";
      sqlbv2.sql(sql);
      sqlbv2.put("mManageCom", mManageCom);
      sql2 = sql2 + " and a.ManageCom like concat('"+"?mManageCom?"+"','%' )";
      sqlbv2.sql(sql2);
      sqlbv2.put("mManageCom", mManageCom);
      mDelFlag="0";
    }
    if(mAgentCode != null && (!mAgentCode.equals("")))
    {
       sql = sql + "and a.agentnew = '"+"?mAgentCode?"+"'";
       sqlbv2.sql(sql);
       sqlbv2.put("mAgentCode", this.mAgentCode);
       sql2 = sql2 + "and a.agentcode = '"+"?mAgentCode?"+"'";
       sqlbv2.sql(sql2);
       sqlbv2.put("mAgentCode", mAgentCode);
      mDelFlag="1";
    }
    sql = sql+" union "+sql2;
    mSSRS = mExeSQL.execSQL(sqlbv2);
    logger.debug("需要处理mSSRS.getMaxRow()："+mSSRS.getMaxRow());

    if (mSSRS.getMaxRow()==0)
    {
        buildError("getInputData", "没有符合条件的员工！");
        return false;
    }
    return true;
  }

  /**
   * 具体的业务处理，根据传入的条件生成相应的清单文件
   * @param szFunc
   * @param szErrMsg
   */
  private boolean dealData(SSRS cSSRS)
  {
    //循环处理每一个代理人
    logger.debug("需要处理的代理人个数："+cSSRS.getMaxRow());
    try
    {
      //获取文件生成路径以及文件名
      if (!checkDesc(mDelFlag))
        {
          return false;
	    }
      m_book.read(this.mXSExcTemplate + this.mFileModeDesc,new com.f1j.ss.ReadParams());
      m_book.setSheetSelected(0, true);
      m_book.setCol(0);
      mCurrentRow = 1;
      //确定交费类型
      payintv_sql="";
      if(mType4.equals("1"))
      {
    	  payintv_sql=" and a.payintv=12 ";
      }
      else
      {
    	  payintv_sql=" and a.payintv in (1,3,6) ";
      }
      logger.debug("交费类型语句："+payintv_sql);
      for(int i=1; i <= cSSRS.getMaxRow(); i++)
	   {
		  String tAgentCode = "";
		  tAgentCode = cSSRS.GetText(i,1);
	      if((mType1.equals("1"))||(mType1.equals("3")))  //孤儿单
	        {
	            if((mType3.equals("1"))||(mType3.equals("3")))  //续期件
	             {
	                 if((mType2.equals("1"))||(mType2.equals("3")))  //二次
	                    {
	                         //1--孤儿单续期二次清单
	                         if(!DealReConTwo(tAgentCode))
	                           {
	                               return false;
	                           }
	                     }
	                 if((mType2.equals("2"))||(mType2.equals("3")))
	                    {
	                         //2--孤儿单续期三次清单
	                        if(!DealReConThree(tAgentCode))
	                          {
	                               return false;
	                          }
	                     }
	              }
	            if((mType3.equals("2"))||(mType3.equals("3")))  //续保件
	              {
	                  //3--孤儿单需要续保的主险清单
	                  if(!DealReMainPol(tAgentCode))
	                   {
	                      return false;
	                   }


	                   //4--孤儿单需要续保的附加险清单
	                  if(!DealReSubPol(tAgentCode))
	                   {
	                      return false;
	                   }

	              }
	        }

	       if((mType1.equals("2"))||(mType1.equals("3")))  //在职单
	        {
	            if((mType3.equals("1"))||(mType3.equals("3")))  //续期件
	              {
	                  if((mType2.equals("1"))||(mType2.equals("3")))  //二次
	                     {
	                         //5--在职单续期二次清单
	                         if(!DealAdimConTwo(tAgentCode))
	                          {
	                              return false;
	                          }
	                      }
	                  if((mType2.equals("2"))||(mType2.equals("3")))
	                     {
	                          //6--在职单续期三次清单
	                         if(!DealAdimConThree(tAgentCode))
	                          {
	                              return false;
	                          }
	                      }
	               }
	             if((mType3.equals("2"))||(mType3.equals("3")))  //续保件
	               {
	                   //7--在职单需要续保的主险清单
	                   if(!DealAdimMainPol(tAgentCode))
	                     {
	                         return false;
	                     }

	                    //8--在职单需要续保的附加险
	                   if(!DealAdimSubPol(tAgentCode))
	                     {
	                         return false;
	                     }

	                }
	        }
	      }
      m_book.setEntry(mCurrentRow,  0, "制表人:"+mGlobalInput.Operator); //制表人
      m_book.setEntry(mCurrentRow,  1, "制表日期:"+currentdate); //制表时间
      //mFilePath = "D:\\TEMP\\";//本地测试用
      logger.debug("@@"+mFilePath +"##"+mFileName);
      //生成文件
      m_book.write(mFilePath + mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
    }
    catch(Exception ex)
    {
      this.buildError("",ex.toString());
      logger.debug("@@@ex:"+ex.toString());
      return false;
    }
    return true;
  }

  /**
   * 孤儿单续期二次清单
   * @param tAgentCode
   * @return
   */
  private boolean DealReConTwo(String tAgentCode)
  {
    logger.debug("孤儿单续期二次清单处理！");

    String sqlhead = "select b.polno from lcprem a,LCPol b ";
    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='" +"?mStartDate?"+ "' and a.PaytoDate<='" + "?mEndDate?"
		  + "' and a.PaytoDate<a.PayEndDate  ";
 
    sqlStr = sqlStr + payintv_sql + "  and a.paytimes = 1 and b.appflag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' ";
    //sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
        + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno  "
        + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
        + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno "
        + " union select 1 from LLReportReason t where (customerno =b.appntno or customerno=b.insuredno) "
		+ " and substr(ReasonCode, 2) = '02')" ;//排除当前状态下发生死亡报案
    sqlStr = sqlStr + " and b.agentcode = '" +"?tAgentCode?"+ "' and exists(select 1 from lrascription where contno=a.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') "
	   + " union select 1 from lrascriptionb where contno=a.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') ";
    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and b.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
    sqlbv3.sql(sqlStr);
    sqlbv3.put("mStartDate", mStartDate);
    sqlbv3.put("mEndDate", mEndDate);
    sqlbv3.put("tAgentCode", tAgentCode);
    sqlbv3.put("date", this.mStartDate);
    sqlbv3.put("mManageCom", mManageCom);
    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
    logger.debug("查询孤儿单续期二次主险:" + SQL_PolNo);
    mExeSQL = new ExeSQL();
    sqlbv3.sql(SQL_PolNo);
    SSRS tSSRS = mExeSQL.execSQL(sqlbv3);
    logger.debug("SQL_PolNO: "+SQL_PolNo);

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"孤儿单续期二次主险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是孤儿单主险续期二次数据***");
    }

   logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
   for (int i = 1; i <= tSSRS.getMaxRow(); i++)
   {
		tLCPolSchema = new LCPolSchema();
		tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
		tLCPolDB.getInfo();
		tLCPolSchema.setSchema(tLCPolDB.getSchema());

		if(!judgeTempFee(tLCPolSchema.getContNo()))
		{
		  continue;
		}

		if (!dealSinglePol(tLCPolSchema,"0"))
		{
		  return false;
		}
   }
    return true;
  }

  /**
   * 孤儿单续期三次清单
   * @param tAgentCode
   * @return
   */
  private boolean DealReConThree(String tAgentCode)
  {
    logger.debug("孤儿单续期三次清单处理！");
    String sqlhead = "select b.polno from lcprem a,LCPol b ";
    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='" +"?mStartDate?"+ "' and a.PaytoDate<='" + "?mStartDate?"
		  + "' and a.PaytoDate<a.PayEndDate  ";
    sqlStr = sqlStr + payintv_sql + " and a.paytimes >= 2 and b.appflag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol)))  ";
    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' ";
    //sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
    + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno  "
    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
    + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno "
    + " union select 1 from LLReportReason t where (customerno =b.appntno or customerno=b.insuredno) "
	+ " and substr(ReasonCode, 2) = '02')" ;//排除当前状态下发生死亡报案
    sqlStr = sqlStr + " and b.agentcode = '" +"?tAgentCode?"+ "' and exists(select 1 from lrascription where contno=a.contno and ascriptiondate=concat(substr(to_date('"+"?this.mStartDate?"+"','yyyy-mm-dd'),1,7),'-01') "
	   + " union select 1 from lrascriptionb where contno=a.contno and ascriptiondate=concat(substr(to_date('"+"?this.mStartDate?"+"','yyyy-mm-dd'),1,7),'-01')) ";
    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and b.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
    sqlbv4.sql(sqlStr);
    sqlbv4.put("mStartDate", mStartDate);
    sqlbv4.put("mEndDate", mEndDate);
    sqlbv4.put("tAgentCode", tAgentCode);
    sqlbv4.put("date", this.mStartDate);
    sqlbv4.put("mManageCom", mManageCom);
    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
    logger.debug("查询孤儿单续期三次主险:" + SQL_PolNo);
    mExeSQL = new ExeSQL();
    sqlbv4.sql(SQL_PolNo);
    SSRS tSSRS = mExeSQL.execSQL(sqlbv4);
    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"孤儿单续期三次主险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是孤儿单主险续期三次数据***");
    }

    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
    {
		tLCPolSchema = new LCPolSchema();
		tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
		tLCPolDB.getInfo();
		tLCPolSchema.setSchema(tLCPolDB.getSchema());

		//logger.debug("polno:"+tLCPolSchema.getPolNo()+"   managecom:"+tLCPolSchema.getManageCom());

		if(!judgeTempFee(tLCPolSchema.getPolNo()))
		{
		  continue;
		}

		if (!dealSinglePol(tLCPolSchema,"0"))
		{
		  return false;
		}
    }
    return true;
  }

  /**
   * 孤儿单需要续保的主险清单
   * @param tAgentCode
   * @return
   */
  private boolean DealReMainPol(String tAgentCode)
  {
    logger.debug("孤儿单需要续保的主险清单处理");
    String sqlhead = "select b.polno from lcprem a,LCPol b ";
    String sqlStr = " where a.polno=b.polno and a.PaytoDate>='"+ "?mStartDate?" +"' and a.PaytoDate<='" + "?mEndDate?"
		  + "' and a.PaytoDate=a.PayEndDate   ";
    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) "
                     +" and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6 ";
    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and b.agentcode='" + "?tAgentCode?" + "'   ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'";
    sqlStr = sqlStr + " and b.RnewFlag=-1  and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
    sqlStr = sqlStr + " and exists(select '1' from lrascription where contno=b.contno union select '1' from lrascriptionb where contno=b.contno )";
    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and b.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
    sqlbv5.sql(sqlStr);
    sqlbv5.put("mStartDate", mStartDate);
    sqlbv5.put("mEndDate", mEndDate);
    sqlbv5.put("tAgentCode", tAgentCode);
    sqlbv5.put("mManageCom", mManageCom);
    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
    sqlbv5.sql(SQL_PolNo);
    logger.debug("查询孤儿单需要续保的主险:" + SQL_PolNo);
    mExeSQL = new ExeSQL();
    SSRS tSSRS = mExeSQL.execSQL(sqlbv5);
    logger.debug("SQL_PolNO: "+SQL_PolNo);

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"孤儿单需要续保的主险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是孤儿单主险自动续保数据***");
    }
    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
    {
		tLCPolSchema = new LCPolSchema();
		tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tSSRS.GetText(i, 1));

		tLCPolDB.getInfo();
		tLCPolSchema.setSchema(tLCPolDB.getSchema());

		if(judgeTempFee(tLCPolSchema.getPolNo())==false)
		{
		  continue;
		}
		if (dealSingleRnewPol(tLCPolSchema,"0") == false)
		{
		  return false;
		}
    }
    return true;
  }

  /**
   * 添加每个保单的信息-针对自动续保主险
   * @param tLCPolSchema
   * @param t_book
   * @param n
   * @return
   */
  private boolean dealSingleRnewPol(LCPolSchema tLCPolSchema,String Flag)
  {
    //Flag 0:孤儿单 ；1:在职单
    int t = 0;
    int subNum = 0;
    String AgentName = ""; //业务员姓名
    String InsuName = ""; //被保人姓名
    String AppName = ""; //  客户姓名
    String SamePerson = "N";
    String AgentGroup ="";
    LCContSchema tLCContSchema = new LCContSchema();
    LCContDB tLCContDB = new LCContDB();
    tLCContDB.setContNo(tLCPolSchema.getContNo());
    if(!tLCContDB.getInfo())
    {
    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
    	return false;
    }
    tLCContSchema = tLCContDB.getSchema();

    try
    {
      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
		.trim();
      AppName = tLCPolSchema.getAppntName().trim();
      InsuName = tLCPolSchema.getInsuredName().trim();
      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
      {
	SamePerson = "Y";
      }

      //如有数据错误，可以写在excel里
      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
      AgentGroup =GetAgentGroup(tLCPolSchema);
      String AgentBranch = findAgentBranch(AgentGroup);
      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
      m_book.setEntry(mCurrentRow, t + 4, tLCPolSchema.getAppntName()); //客户姓名
      String AppIDNo="";
      String InsuredIDNo="";
      ExeSQL pExeSQL = new ExeSQL();
      SSRS pssrs_idno =new SSRS();
      SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
      sqlbv6.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
				 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
      sqlbv6.put("contno", tLCPolSchema.getContNo());
      sqlbv6.put("appntno", tLCPolSchema.getAppntNo());
      sqlbv6.put("InsuredNo", tLCPolSchema.getInsuredNo());
      pssrs_idno=pExeSQL.execSQL(sqlbv6);
      AppIDNo=pssrs_idno.GetText(1,1);
      InsuredIDNo=pssrs_idno.GetText(1,2);
      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
      String PayMode = findPayMode(tLCPolSchema.getContNo(),
    		  tLCContSchema.getPayLocation());
      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

      m_book.setEntry(mCurrentRow, t + 40,
		      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构

      double mainPolPrem = 0;
      String tGetNoticeNo = "";
      //查询出主险自己的保费信息
      String SQL_SubPol =
	  "select b.GetNoticeNo,sum(a.SumDuePayMoney) from ljspayperson a,ljspay b, lcpol c "
	  + " where a.GetNoticeNo = b.GetNoticeNo  "
	  + " and a.polno = c.polno " + " and c.polno = c.mainpolno "
	  + " and b.otherno='" + "?otherno?"
	  + "' and  b.othernotype='2' group by b.GetNoticeNo";
      SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
      sqlbv7.sql(SQL_SubPol);
      sqlbv7.put("otherno", tLCPolSchema.getContNo());
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS = tExeSQL.execSQL(sqlbv7);
      double sumprem = 0.0; //保费合计
      if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
      {
		tGetNoticeNo = tSSRS.GetText(1, 1);
		mainPolPrem = Double.parseDouble(tSSRS.GetText(1, 2));
		sumprem = sumprem + mainPolPrem;
        m_book.setEntry(mCurrentRow, t + 10, String.valueOf(mainPolPrem)); //主险保费
      }
      else
      {
        m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
      }
      m_book.setEntry(mCurrentRow, t + 9,
		      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种




      double tLeavingMoney = 0; //预收金额
      double tNewLeavingMoney = 0; //余额
      double tActualPayMoney = 0; //实缴金额

      LCPolSet tLCPolSet = findSubPol(tLCPolSchema);
      if (tLCPolSet.size() > 0)
      {
	String prem = "0";

	//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
	for (int i = 1; i <= tLCPolSet.size(); i++)
	{
	  tLeavingMoney += tLCPolSet.get(i).getLeavingMoney();
	  SQL_SubPol = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
	      + "?riskcode?"
	      + "' and PayType='ZC'  ";
	  SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
      sqlbv8.sql(SQL_SubPol);
      sqlbv8.put("riskcode", tLCPolSet.get(i).getRiskCode());
	  SQL_SubPol = SQL_SubPol + " and GetNoticeNo='"
	      + tGetNoticeNo + "'";
	  tExeSQL = new ExeSQL();
	  tSSRS = tExeSQL.execSQL(sqlbv8);
	  if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
	  {
	    prem = tSSRS.GetText(1, 1);
	  }
	  m_book.setEntry(mCurrentRow , t + 11 +2*(i-1),
			  ChangeCodetoName.getRiskName(tLCPolSet.get(i)
			  .getRiskCode())); //附险险种

	  m_book.setEntry(mCurrentRow , t + 12 +2*(i-1), prem); //附险保费
	  sumprem = sumprem + Double.parseDouble(prem);
	}
      }

      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计
      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(tLeavingMoney)); //预收金额
      if ((tLeavingMoney - sumprem) > 0)
      {
	tActualPayMoney = 0;
	tNewLeavingMoney = tLeavingMoney - sumprem;
      }
      else
      {
	tActualPayMoney = sumprem - tLeavingMoney;
	tNewLeavingMoney = 0;
      }
      m_book.setEntry(mCurrentRow, t + 27,
		      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
      m_book.setEntry(mCurrentRow, t + 28,
		      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

      //投保人信息
      LCAppntDB tLCAppntDB = new LCAppntDB();
      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
      if (tLCAppntDB.getInfo() == true)
      {
    	LCAddressDB tLCAddressDB = new LCAddressDB();
    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
    	if (tLCAddressDB.getInfo() == true)
        {
			m_book.setEntry(mCurrentRow, t + 29,
					tLCAddressDB.getPostalAddress()); //收费地址
			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
			m_book.setEntry(mCurrentRow, t + 39, ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
			m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
        }
      }
      //被保人身份证号
      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
      String[] BankInfo = findBankInfo(tLCPolSchema);
      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
      m_book.setEntry(mCurrentRow, t + 35,
		      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
      String AgentTel = "";
      if (tLAAgentDB.getInfo() == true)
      {
	AgentTel = tLAAgentDB.getPhone();
	if ((AgentTel == null) || AgentTel.equals(""))
	{
	  AgentTel = tLAAgentDB.getMobile();
	}
	if (AgentTel == null)
	{
	  AgentTel = "";
	}
      }
      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话

      m_book.setEntry(mCurrentRow, t + 38, String.valueOf("1")); //交费期数
      m_book.setEntry(mCurrentRow, t + 44, tLCPolSchema.getPrtNo());
      //--------------------
      //新增交费年期,交费次数
      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
      String tPayCount = "";
      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
      tLMRiskAppDB.getInfo();
      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?polno?" +"'" ;
        SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(sqlPayCount);
        sqlbv9.put("polno", tLCPolSchema.getPolNo());
        tPayCount = tExeSQL.getOneValue(sqlbv9);
      }else{
        tPayCount = "1";
      }
      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
      //--------------------

      LACommisionDB tLACommisionDB = new LACommisionDB();
      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
      LACommisionSet tLACommisionSet = tLACommisionDB.query();
      if (tLACommisionSet.size() > 0)
      {
	LAAgentDB oldAgentDB = new LAAgentDB();
	oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
	if (oldAgentDB.getInfo() == true)
	{
	  String oldAgentPhone = oldAgentDB.getPhone();
	  if (oldAgentPhone == null)
	  {
	    oldAgentPhone = "";
	  }
	  String oldAgentMobile = oldAgentDB.getMobile();
	  if (oldAgentMobile == null)
	  {
	    oldAgentMobile = "";
	  }
	  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
	  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
	  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
	}
      }
      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
      {
         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
         if (tLRAdimAscriptionSet.size() > 0)
         {
            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///原业务员手机
            String getname="";
            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
            SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
            sqlbv10.sql(getname);
            sqlbv10.put("agentcode", tLRAdimAscriptionSet.get(1).getAgentCode());
            ExeSQL xExeSQL = new ExeSQL();
            m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv10)); ///原业务员手机
            m_book.setEntry(mCurrentRow, t + 53, tLRAdimAscriptionSet.get(1).getZipCode());
            m_book.setEntry(mCurrentRow, t + 54, tLRAdimAscriptionSet.get(1).getAddCode());
         }
      }else{
         LRAscriptionDB tlrascriptionDB = new LRAscriptionDB();
         tlrascriptionDB.setPolNo(tLCPolSchema.getPolNo());
         LRAscriptionSet tLRAscriptionSet = tlrascriptionDB.query();
         if (tLRAscriptionSet.size() > 0)
         {
            m_book.setEntry(mCurrentRow, t + 53, tLRAscriptionSet.get(1).getZipCode());
            m_book.setEntry(mCurrentRow, t + 54, tLRAscriptionSet.get(1).getAddCode());
         }
      }

      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
      {
         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
      }
      else
      {
         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
      }

    }
    catch (Exception ex)
    {
      try
      {
	m_book.setEntry(mCurrentRow, t + 0,
			"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
	mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
      }
      catch (Exception ex2)
      {
	buildError("dealSinglePol", ex.toString());
	return false;
      }
      return true;
    }
 //   mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
    return true;
  }

  /**
   * 孤儿单需要续保的附加险
   * @param tAgentCode
   * @return
   */
  private boolean DealReSubPol(String tAgentCode)
  {
    logger.debug("孤儿单需要续保的附加险清单处理");
    String sqlhead = "select b.polno from lcprem a,LCPol b ";
    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='"+"?mStartDate?"+"' and a.PaytoDate<='" + "?mEndDate?"
		  + "' and a.PaytoDate=a.PayEndDate and b.agentcode = '"+"?tAgentCode?"+"'";
    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) "
                     +" and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
	   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode ) "//主险未做过续保
	   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
    sqlStr = sqlStr + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'";
    sqlStr = sqlStr + " and RnewFlag=-1 ";
    sqlStr = sqlStr + " and exists(select '1' from lrascription where contno=b.contno union select '1' from lrascriptionb where contno=b.contno )";
    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and b.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
    sqlbv11.put("mStartDate", mStartDate);
    sqlbv11.put("mEndDate", mEndDate);
    sqlbv11.put("tAgentCode", tAgentCode);
    sqlbv11.put("mManageCom", mManageCom); 
    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
    sqlbv11.sql(SQL_PolNo);
    logger.debug("查询孤儿单附加险自动续保:" + SQL_PolNo);
    mExeSQL = new ExeSQL();
    SSRS tSSRS = mExeSQL.execSQL(sqlbv11);
  
    logger.debug("SQL_PolNO: "+SQL_PolNo);

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"孤儿单附加险自动续保无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是孤儿单附加险自动续保数据(其主险不需要续期缴费和续保)***");
    }

    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
    {
	  //判断主险是续期或自动续保-跳过
	  if (!judgeMainRisk(tSSRS.GetText(i, 2), tSSRS.GetText(i, 3)))
	  {
	    continue;
      }
      tLCPolSchema = new LCPolSchema();
      tLCPolDB = new LCPolDB();
      tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
      tLCPolDB.getInfo();
      tLCPolSchema.setSchema(tLCPolDB.getSchema());

      boolean Flag = false;
      for (int t = 0; t < CVData.size(); t++)
      {
		if (tLCPolDB.getPolNo().equals((String) CVData.get(t)))
		{
		  Flag = true;
	    }
      }
      if (Flag)
      {
		//如果已经处理循环下一条
		continue;
      }
      if(judgeTempFee(tSSRS.GetText(i, 2))==false)
      {
	    continue;
      }

      if (dealSingleSubPol(tLCPolSchema,"0") == false)
      {
	    return false;
      }
    }
    return true;
  }

  /**
   * 处理单个需要自动续保的附加险的清单数据
   * @param tLCPolSchema
   * @return
   */
  private boolean dealSingleSubPol(LCPolSchema tLCPolSchema,String Flag)
  {
    //Flag 0:孤儿单 ；1:在职单
    int t = 0;
    int subNum = 0;
    String AgentName = ""; //业务员姓名
    String InsuName = ""; //被保人姓名
    String AppName = ""; //  客户姓名
    String SamePerson = "N";
    String AgentGroup ="";
    LCContSchema tLCContSchema = new LCContSchema();
    LCContDB tLCContDB = new LCContDB();
    tLCContDB.setContNo(tLCPolSchema.getContNo());
    if(!tLCContDB.getInfo())
    {
    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
    	return false;
    }
    tLCContSchema = tLCContDB.getSchema();

    try
    {
      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
		.trim();
      AppName = tLCPolSchema.getAppntName().trim();
      InsuName = tLCPolSchema.getInsuredName().trim();
      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
      {
	SamePerson = "Y";
      }

      //找到该报单的投保单号码，后面用于查询续收员信息
      logger.debug(
	  "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      String sqlstrtemp = "select ProposalNo from lcrnewstatehistory where contno='"
			+ "?contno?" + "' and riskcode='"+"?riskcode?"+"' order by paytodate desc";
      logger.debug("sqlstrtemp:" + sqlstrtemp);
      ExeSQL TTexesql = new ExeSQL();
      SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
      sqlbv12.sql(sqlstrtemp);
      sqlbv12.put("contno", tLCPolSchema.getContNo());
      sqlbv12.put("riskcode", tLCPolSchema.getRiskCode());
      String temppolno = TTexesql.getOneValue(sqlbv12);
      if ((temppolno == null) || temppolno.equals(""))
      {
	return false;
      }

      //如有数据错误，可以写在excel里
      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
      AgentGroup =GetAgentGroup(tLCPolSchema);
      String AgentBranch = findAgentBranch(AgentGroup);
      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
      m_book.setEntry(mCurrentRow, t + 4, tLCPolSchema.getAppntName()); //客户姓名
      String AppIDNo="";
      String InsuredIDNo="";
      ExeSQL pExeSQL = new ExeSQL();
      SSRS pssrs_idno =new SSRS();
      SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
      sqlbv13.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
				 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
      sqlbv13.put("contno", tLCPolSchema.getContNo());
      sqlbv13.put("appntno", tLCPolSchema.getAppntNo());
      sqlbv13.put("InsuredNo", tLCPolSchema.getInsuredNo());
      pssrs_idno=pExeSQL.execSQL(sqlbv13);
      AppIDNo=pssrs_idno.GetText(1,1);
      InsuredIDNo=pssrs_idno.GetText(1,2);
      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
      String PayMode = findPayMode(tLCPolSchema.getContNo(),
    		  tLCContSchema.getPayLocation());
      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

      m_book.setEntry(mCurrentRow, t + 40,
		      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
      //--------------------
      //新增交费年期,交费次数
      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
      String tPayCount = "";
      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
      tLMRiskAppDB.getInfo();
      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?polno?" +"'" ;
        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql(sqlPayCount);
        sqlbv14.put("polno", tLCPolSchema.getPolNo());
        tPayCount = pExeSQL.getOneValue(sqlbv14);
      }else{
        tPayCount = "1";
      }
      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
      //--------------------

      //主险趸缴的也得显示主险名称，保费项填趸缴
      m_book.setEntry(mCurrentRow, t + 9,
                      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种
      m_book.setEntry(mCurrentRow, t + 10, String.valueOf("趸交")); //主险保费

      double sumprem = 0.0; //保费合计
      String getnoticeno_sql =
	  "select otherno,getnoticeno from ljspay where getnoticeno in ( select getnoticeno from ljspayperson where polno='"
	  + "?polno?" + "')";
      SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
      sqlbv15.sql(getnoticeno_sql);
      sqlbv15.put("polno",temppolno);
      String tGetNoticeNo = "";
      String tContNo = "";
      ExeSQL mainpolno_exe = new ExeSQL();
      SSRS Temp_SSRS = new SSRS();
      Temp_SSRS = mainpolno_exe.execSQL(sqlbv15);
      if ((Temp_SSRS != null) && (Temp_SSRS.getMaxRow() > 0))
      {
    	  tContNo = Temp_SSRS.GetText(1, 1);
	      tGetNoticeNo = Temp_SSRS.GetText(1, 2);
      }
      else
      {
	    String tMainPolNo_Sql="select contno from lcpol where polno ='"+"?polno?"+"'";
	    SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	      sqlbv16.sql(tMainPolNo_Sql);
	      sqlbv16.put("polno",tLCPolSchema.getPolNo());
	    ExeSQL tExe = new ExeSQL();
	    tContNo = tExe.getOneValue(sqlbv16);
      }
      LCPolDB tLCPolDB = new LCPolDB();
      tLCPolDB.setContNo(tContNo);
      if (tLCPolDB.query().size()==0)
      {
	     return false;
      }

      LCPolSet tLCPolSet = new LCPolSet();
      tLCPolSet = this.findSubPol3(tLCPolDB.getSchema());

      double tLeavingMoney = 0; //原余额
      double tNewLeavingMoney = 0; //新余额
      double tActualPayMoney = 0; //实交保费

      if (tLCPolSet.size() > 0)
	  {
		String prem = "0";

		//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
		for (int i = 1; i <= tLCPolSet.size(); i++)
		{
			if (String.valueOf(tLCPolSet.get(i).getLeavingMoney()) != null)
			  {
			    tLeavingMoney = tLeavingMoney + tLCPolSet.get(i).getLeavingMoney();
			  }
			String SQL_SubPol = "";
			ExeSQL tExeSQL = new ExeSQL();
			SQL_SubPol = "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
			    + "?riskcode?" + "' and PayType='ZC'";
			SQL_SubPol = SQL_SubPol + " and GetNoticeNo='"
			    + tGetNoticeNo + "'";
			 SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		      sqlbv17.sql(SQL_SubPol);
		      sqlbv17.put("riskcode",tLCPolSet.get(i).getRiskCode());
			tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv17);
			if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
			{
			  prem = tSSRS.GetText(1, 1);
			}

		    m_book.setEntry(mCurrentRow , t + 11+2*(i-1),
				ChangeCodetoName.getRiskName(tLCPolSet.get(i)
				.getRiskCode())); //附险险种


		    m_book.setEntry(mCurrentRow , t + 12+2*(i-1), prem); //附险保费

	        if(tSSRS.GetText(1,1).equals("0"))
	        {
	           m_book.setEntry(mCurrentRow ,t+12+2*(i-1),"附加险续保保费未催收："+ getRnewFailReason(tLCPolSet.get(i)));//主险催收失败的原因
	        }
		    sumprem = sumprem + Double.parseDouble(prem);

		    this.CVData.addElement(tLCPolSet.get(i).getPolNo());
		}
	  }

      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计
      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(tLeavingMoney)); //预收金额
      if ((tLeavingMoney - sumprem) > 0)
      {
	tActualPayMoney = 0;
	tNewLeavingMoney = tLeavingMoney - sumprem;
      }
      else
      {
	tActualPayMoney = sumprem - tLeavingMoney;
	tNewLeavingMoney = 0;
      }
      m_book.setEntry(mCurrentRow, t + 27,
		      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
      m_book.setEntry(mCurrentRow, t + 28,
		      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

      //投保人信息
      LCAppntDB tLCAppntDB = new LCAppntDB();
      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
      if (tLCAppntDB.getInfo() == true)
      {
    	LCAddressDB tLCAddressDB = new LCAddressDB();
    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
    	if (tLCAddressDB.getInfo() == true)
        {
			m_book.setEntry(mCurrentRow, t + 29,
					tLCAddressDB.getPostalAddress()); //收费地址
			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
			m_book.setEntry(mCurrentRow, t + 39,
					ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
			m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
        }
      }
      //此处新加被保人身份证号
      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
      String[] BankInfo = findBankInfo(tLCPolSchema);
      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
      m_book.setEntry(mCurrentRow, t + 35,
		      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
      String AgentTel = "";
      if (tLAAgentDB.getInfo() == true)
      {
	AgentTel = tLAAgentDB.getPhone();
	if ((AgentTel == null) || AgentTel.equals(""))
	{
	  AgentTel = tLAAgentDB.getMobile();
	}
	if (AgentTel == null)
	{
	  AgentTel = "";
	}
      }
      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话
      LJAPayDB tLJAPayDB = new LJAPayDB();
      tLJAPayDB.setIncomeNo(tLCPolSchema.getPolNo());
      tLJAPayDB.setIncomeType("2");
      LJAPaySet tLJAPaySet = tLJAPayDB.query();
      m_book.setEntry(mCurrentRow, t + 38,
		      String.valueOf(tLJAPaySet.size())); //交费期数

      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构
      m_book.setEntry(mCurrentRow, t + 44, tLCPolSchema.getPrtNo());

      LACommisionDB tLACommisionDB = new LACommisionDB();
      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
      LACommisionSet tLACommisionSet = tLACommisionDB.query();
      if (tLACommisionSet.size() > 0)
      {
	LAAgentDB oldAgentDB = new LAAgentDB();
	oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
	if (oldAgentDB.getInfo() == true)
	{
	  String oldAgentPhone = oldAgentDB.getPhone();
	  if (oldAgentPhone == null)
	  {
	    oldAgentPhone = "";
	  }
	  String oldAgentMobile = oldAgentDB.getMobile();
	  if (oldAgentMobile == null)
	  {
	    oldAgentMobile = "";
	  }
	  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
	  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
	  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
	}
      }
      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
      {
         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
         if (tLRAdimAscriptionSet.size() > 0)
         {
            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///原业务员手机
            String getname="";
            
            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
            ExeSQL xExeSQL = new ExeSQL();
            SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
		      sqlbv18.sql(getname);
		      sqlbv18.put("agentcode",tLRAdimAscriptionSet.get(1).getAgentCode());
           m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv18)); ///原业务员手机
         }

      }

      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
      {
         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
      }
      else
      {
         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
      }


    }
    catch (Exception ex)
    {
      try
      {
	m_book.setEntry(mCurrentRow, t + 0,
			"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
	mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
      }
      catch (Exception ex2)
      {
	buildError("dealSinglePol", ex.toString());
	return false;
      }
      return true;
    }

 //   mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
    return true;
  }

  /**
   * 特殊处理主险趸交,查询附加险
   * @param tLCPolSchema
   * @return
   */
  private LCPolSet findSubPol3(LCPolSchema tLCPolSchema)
  {
    logger.debug("in findSubPol()");
    LCPolDB tLCPolDB = new LCPolDB();
    String strSQL = "select * from lcpol where mainpolno='"
		  + "?mainpolno?" + "' ";
    strSQL = strSQL + "and polno!='" + "?mainpolno?" + "' ";
    strSQL = strSQL + "and AppFlag='1'  ";
    strSQL = strSQL
	   + "and ((PaytoDate = PayEndDate and RnewFlag = '-1' and exists (select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag='Y')) or PayToDate < PayEndDate)";
    SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
    sqlbv19.sql(strSQL);
    sqlbv19.put("mainpolno", tLCPolSchema.getPolNo());
    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv19);

    return tLCPolSet;
  }

  /**
   * 判断主险是否符合条件：非续期，非续保，未到期
   * @param tPolNo   主险号
   * @param tPayEndDate  附加险终交日期
   * @return
   */
  private boolean judgeMainRisk(String tPolNo, String tPayEndDate)
  {
    LCPolDB tLCPolDB = new LCPolDB();
    String strSQL = "select * from lcpol where polno='" + "?tPolNo?" + "' ";
    strSQL = strSQL + " and appflag='1'";
    strSQL = strSQL + " and RnewFlag!=-1 "; //不是自动续保的
    strSQL = strSQL + " and payintv=0 "; //不是续期的
    strSQL = strSQL + " and payenddate>'" + tPayEndDate + "'"; //未到期的
    SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
    sqlbv20.sql(strSQL);
    sqlbv20.put("tPolNo", tPolNo);
    //如果不符合条件-则返回false
    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv20);
    if (tLCPolSet.size() == 0)
    {
      return false;
    }

    return true;
  }

  /**
   * 在职单续期二次清单
   * @param tAgentCode
   * @return
   */
  private boolean DealAdimConTwo(String tAgentCode)
  {
    logger.debug("在职单续期二次清单处理！");
    //首先取出续收外勤自己收展的保单
    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='"+"?age?"+"'"
     +  " and z.mainpolno = z.polno and z.payintv=12 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

    //然后是续收外勤名下的在职单
    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
     +" and z.agentcode='"+"?age?"+"' and z.ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') ";

    //确定查询逻辑
    String sqlStr = " and exists(select 1 from lcprem a,LCPol b where a.polno=b.polno and  a.PaytoDate>='" +"?pay?"+ "' and a.PaytoDate<='" + "?eng?"
		  + "' and a.PaytoDate<a.PayEndDate  ";
    sqlStr = sqlStr + payintv_sql + "  and a.paytimes = 1 and b.appflag='1'  and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno )";
   // sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno  "
    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno "
    +"  union select 1 from LLReportReason t,lcpol o  where (t.customerno =o.appntno or t.customerno=o.insuredno)"
    +"   and o.contno=z.contno and o.polno=o.mainpolno and substr(t.ReasonCode, 2) = '02') ";//排除死亡报案
    sqlStr = sqlStr + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?adate?"+"','yyyy-mm-dd'),1,7),'-01') "
	   + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?adate?"+"','yyyy-mm-dd'),1,7),'-01')) ";

    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and z.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
    sqlbv15.sql(sqlStr);
    sqlbv15.put("age", tAgentCode);
    sqlbv15.put("date", this.mStartDate);
    sqlbv15.put("pay", mStartDate);
    sqlbv15.put("eng", mEndDate);
    sqlbv15.put("adate", this.mStartDate);
    sqlbv15.put("adate", this.mStartDate);
    sqlbv15.put("mManageCom", mManageCom);
    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
    logger.debug("查询在职单续期二次主险:" + SQL_Count);

    String SQL_PolNo = "select q.no from ("+SQL_Count
		     + " ) q order by q.xdate ";
    logger.debug("SQL_PolNO: "+SQL_PolNo);
    sqlbv15.sql(SQL_PolNo);
    SSRS tSSRS = mExeSQL.execSQL(sqlbv15);

    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"在职单续期二次主险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是在职单主险续期二次数据***");
    }

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    mExeSQL = new ExeSQL();

    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
	{
		tLCPolSchema = new LCPolSchema();
		tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
		tLCPolDB.getInfo();
		tLCPolSchema.setSchema(tLCPolDB.getSchema());

		if(!judgeTempFee(tLCPolSchema.getPolNo()))
		{
		  continue;
		}

		if (!dealSinglePol(tLCPolSchema,"1"))
		{
		  return false;
		}
	}

    return true;
  }

  /**
   * 在职单续期三次清单
   * @param tAgentCode
   * @return
   */
  private boolean DealAdimConThree(String tAgentCode)
  {
	    logger.debug("在职单续期三次清单处理！");
	    //首先取出续收外勤自己收展的保单
	    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='"+"?tAgentCode?"+"'"
	     +  " and z.mainpolno = z.polno and z.payintv=12 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";
         SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
         sqlbv16.sql(sqlhead1);
         sqlbv16.put("tAgentCode", tAgentCode);
	    //然后是续收外勤名下的在职单
	    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
	     +" and z.agentcode='"+"?tAgentCode?"+"' and z.ascriptiondate=concat(substr(to_date('?sub?','yyyy-mm-dd'),1,7),'-01') ";
	    sqlbv16.sql(sqlhead2);
        sqlbv16.put("tAgentCode", tAgentCode);
        sqlbv16.put("sub", this.mStartDate);

	    //确定查询逻辑
	    String sqlStr = " and exists(select 1 from lcprem a,LCPol b where a.polno=b.polno and  a.PaytoDate>='" +"?mStartDate?"+ "' and a.PaytoDate<='" + "?mEndDate?"
			  + "' and a.PaytoDate<a.PayEndDate  ";
	    sqlStr = sqlStr + payintv_sql + "  and a.paytimes >=2 and b.appflag='1'  and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
	    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno )";
	   // sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
	    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno  "
	    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno "
	    +"  union select 1 from LLReportReason t,lcpol o  where (t.customerno =o.appntno or t.customerno=o.insuredno)"
	    +"   and o.contno=z.contno and o.polno=o.mainpolno and substr(t.ReasonCode, 2) = '02') ";//排除死亡报案
	    sqlStr = sqlStr + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') "
		   + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01')) ";

	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and z.ManageCom like concat('"+"?mManageCom?"+"','%')";
	    }
	    sqlbv16.sql(sqlStr);
        sqlbv16.put("mStartDate", mStartDate);
        sqlbv16.put("mEndDate", mEndDate);
        sqlbv16.put("date", this.mStartDate);
        sqlbv16.put("mManageCom", mManageCom);
        
	    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
	    logger.debug("查询在职单续期三次主险:" + SQL_Count);

	    String SQL_PolNo = "select q.no from ("+SQL_Count
			     + " ) q order by q.xdate ";
	    logger.debug("SQL_PolNO: "+SQL_PolNo);
	    sqlbv16.sql(SQL_PolNo);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv16);

	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"在职单续期三次主险无数据，跳过");
	        return true;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
	      addPrompt("***以下是在职单主险续期三次数据***");
	    }

	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    mExeSQL = new ExeSQL();

	    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
	    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
		{
			tLCPolSchema = new LCPolSchema();
			tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
			tLCPolDB.getInfo();
			tLCPolSchema.setSchema(tLCPolDB.getSchema());

			if(!judgeTempFee(tLCPolSchema.getPolNo()))
			{
			  continue;
			}

			if (!dealSinglePol(tLCPolSchema,"1"))
			{
			  return false;
			}
		}

	    return true;
	  }

  /**
   * 在职单需要续保的主险清单
   * @param tAgentCode
   * @return
   */
  private boolean DealAdimMainPol(String tAgentCode)
  {
    logger.debug("在职单需要续保的主险清单处理");
    //首先取出续收外勤自己收展的保单
    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='"+"?tAgentCode?"+"'"
     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";
    SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
    sqlbv17.sql(sqlhead1);
    sqlbv17.put("tAgentCode", tAgentCode);
    //然后是续收外勤名下的在职单
    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
     +" and z.agentcode='"+"?tAgentCode?"+"' and z.ascriptiondate= concat(substr(to_date('"+"?substr?"+"','yyyy-mm-dd'),1,7),'-01') ";
    sqlbv17.sql(sqlhead2);
    sqlbv17.put("tAgentCode", tAgentCode);
    sqlbv17.put("substr", this.mStartDate);
    String sqlStr = " and exists (select 1 from lcprem a,LCPol b ";
    sqlStr = sqlStr +  " where a.polno=b.polno and a.PaytoDate>='"+ "?mStartDate?" +"' and a.PaytoDate<='" + "?mEndDate?"
		  + "' and a.PaytoDate=a.PayEndDate   ";
    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and length(trim(a.dutycode))=6  and length(trim(a.payplancode))=6 ";
    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo  ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'  and a.contno=z.contno and b.contno=z.contno ";
    sqlStr = sqlStr + " and b.RnewFlag=-1  and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y')) "
    + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') "
	     + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01')) ";

    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and z.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    sqlbv17.sql(sqlStr);
    sqlbv17.put("mStartDate", mStartDate);
    sqlbv17.put("mEndDate", mEndDate);
    sqlbv17.put("date", this.mStartDate);
    sqlbv17.put("mManageCom", mManageCom);
    
    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
    sqlbv17.sql(SQL_Count);
    logger.debug("查询在职单需要续保的主险清单:" + SQL_Count);

    String SQL_PolNo = "select q.no from ("+SQL_Count
		     + " ) q order by q.xdate ";
    sqlbv17.sql(SQL_PolNo);
    logger.debug("@@@@SQL_PolNo:"+SQL_PolNo);
    SSRS tSSRS = mExeSQL.execSQL(sqlbv17);

    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"在职单续期二次主险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
      addPrompt("***以下是在职单主险自动续保数据***");
    }

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();

    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
	{
		tLCPolSchema = new LCPolSchema();
		tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tSSRS.GetText(i, 1));

		tLCPolDB.getInfo();
		tLCPolSchema.setSchema(tLCPolDB.getSchema());

		if(judgeTempFee(tLCPolSchema.getPolNo())==false)
		{
		  continue;
		}
		if (dealSingleRnewPol(tLCPolSchema,"1") == false)
		{
		  return false;
		}
	}
    return true;
  }

  /**
   * 在职单需要续保的附加险清单
   * @param tAgentCode
   * @return
   */
  private boolean DealAdimSubPol(String tAgentCode)
  {
    logger.debug("在职单需要续保的附加险清单处理");
    //首先取出续收外勤自己收展的保单
    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='"+"?tAgentCode?"+"'"
     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";
    SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
    sqlbv19.sql(sqlhead1);
    sqlbv19.put("tAgentCode", tAgentCode);
    //然后是续收外勤名下的在职单
    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
     +" and z.agentcode='"+"?tAgentCode?"+"' and z.ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') ";
    sqlbv19.sql(sqlhead2);
    sqlbv19.put("tAgentCode", tAgentCode);
    sqlbv19.put("date", this.mStartDate);
    //确定查询逻辑
    String sqlStr = " and exists (select 1 from lcprem a,LCPol b ";
    sqlStr =sqlStr +  " where a.polno=b.polno and  a.PaytoDate>='"+"?mStartDate?"+"' and a.PaytoDate<='" + "?mEndDate?"
		  + "' and a.PaytoDate=a.PayEndDate ";
    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
	   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode) "//主险未做过续保
	   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
    sqlStr = sqlStr + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno ";
    sqlStr = sqlStr + " and RnewFlag=-1 ) "
           + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01') "
	            + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('"+"?date?"+"','yyyy-mm-dd'),1,7),'-01')) ";

    if(mManageCom != null &&(!mManageCom.equals("")))
    {
      sqlStr = sqlStr + " and z.ManageCom like concat('"+"?mManageCom?"+"','%')";
    }
    sqlbv19.sql(sqlStr);
    sqlbv19.put("mStartDate", mStartDate);
    sqlbv19.put("mEndDate", mEndDate);
    sqlbv19.put("date", this.mStartDate);
    sqlbv19.put("mManageCom", mManageCom);
    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
    logger.debug("查询在职单需要续保的附加险清单:" + SQL_Count);

    String SQL_PolNo = "select q.no from ("+SQL_Count
		     + " ) q order by q.xdate ";
    sqlbv19.sql(SQL_PolNo);
    logger.debug("@@@SQL_PolNo:"+SQL_PolNo);
    SSRS tSSRS = mExeSQL.execSQL(sqlbv19);

    if (tSSRS == null ||tSSRS.getMaxRow()==0)
    {
        logger.debug(tAgentCode+"在职单需要续保的附加险无数据，跳过");
        return true;
    }
    if (tSSRS.getMaxRow() > 0)
    {
        addPrompt("***以下是在职单附加险自动续保数据(其主险不需要续期缴费和续保)***");
      }

    LCPolDB tLCPolDB = new LCPolDB();
    LCPolSchema tLCPolSchema = new LCPolSchema();

    for (int i = 1; i <= tSSRS.getMaxRow(); i++)
    {
	  //判断主险是续期或自动续保-跳过
	  if (!judgeMainRisk(tSSRS.GetText(i, 2), tSSRS.GetText(i, 3)))
	  {
	    continue;
      }
      tLCPolSchema = new LCPolSchema();
      tLCPolDB = new LCPolDB();
      tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
      tLCPolDB.getInfo();
      tLCPolSchema.setSchema(tLCPolDB.getSchema());

      boolean Flag = false;
      for (int t = 0; t < CVData.size(); t++)
      {
		if (tLCPolDB.getPolNo().equals((String) CVData.get(t)))
		{
		   Flag = true;
	    }
      }
      if (Flag)
      {
		//如果已经处理循环下一条
		continue;
      }
      if(judgeTempFee(tSSRS.GetText(i, 2))==false)
      {
	    continue;
      }

      if (dealSingleSubPol(tLCPolSchema,"1") == false)
      {
	    return false;
      }
      }
    return true;
  }

  private String GetAgentGroup(LCPolSchema tLCPolSchema){
	  String tAgentGroup="";
	  String tAgentCode =tLCPolSchema.getAgentCode();
	  //查询代理人相应的服务部信息
	  ExeSQL tExeSQL =new ExeSQL();
	  SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
	    sqlbv20.sql("select agentgroup from laagent where agentcode ='"+"?tAgentCode?"+"'");
	    sqlbv20.put("tAgentCode", tAgentCode);
	  tAgentGroup =tExeSQL.getOneValue(sqlbv20);
	  if("".equals(tAgentGroup)){
		  tAgentGroup =tLCPolSchema.getAgentGroup();
	  }
	  return tAgentGroup;
  }

  /**
   * 添加每个保单的信息-针对续期主险
   * @param tLCPolSchema
   * @param t_book
   * @param n
   * @return
   */
  private boolean dealSinglePol(LCPolSchema tLCPolSchema,String Flag)
  {
    //Flag 0:孤儿单 ；1:在职单
    logger.debug("开始填充数据");
    int t = 0;
    int subNum = 0;
    String AgentName = ""; //业务员姓名
    String InsuName = ""; //被保人姓名
    String AppName = ""; //  客户姓名
    String SamePerson = "N";
    String AgentGroup ="";
    LCContSchema tLCContSchema = new LCContSchema();
    LCContDB tLCContDB = new LCContDB();
    tLCContDB.setContNo(tLCPolSchema.getContNo());
    if(!tLCContDB.getInfo())
    {
    	logger.debug("查找保单"+tLCPolSchema.getContNo()+"合同表信息失败。");
    	return false;
    }
    tLCContSchema = tLCContDB.getSchema();

    try
    {
      AgentName = ChangeCodetoName.getAgentName(tLCPolSchema.getAgentCode())
		.trim();
      AppName = tLCPolSchema.getAppntName().trim();
      InsuName = tLCPolSchema.getInsuredName().trim();
      if (AgentName.equals(AppName) || AgentName.equals(InsuName))
      {
	    SamePerson = "Y";
      }

      //如有数据错误，可以写在excel里
      m_book.setEntry(mCurrentRow, t + 0, tLCPolSchema.getManageCom()); //管理机构
      m_book.setEntry(mCurrentRow, t + 1, AgentName); //业务员姓名
      m_book.setEntry(mCurrentRow, t + 2, tLCPolSchema.getAgentCode()); //业务员代码
      AgentGroup =GetAgentGroup(tLCPolSchema);
      String AgentBranch = findAgentBranch(AgentGroup);
      m_book.setEntry(mCurrentRow, t + 3, AgentBranch); //业务员组别
      m_book.setEntry(mCurrentRow, t + 4, AppName); //客户姓名
      //增加客户身份证号,被投保人身份证号
      String AppIDNo="";
      String InsuredIDNo="";
      ExeSQL pExeSQL = new ExeSQL();
      SSRS pssrs_idno =new SSRS();
      SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
      sqlbv21.sql(" select a.idno,b.idno from lcappnt a,lcinsured b where a.contno=b.contno and a.contno="+"?contno?"
				 +" and a.appntno='"+"?appntno?"+"' and b.InsuredNo='"+"?InsuredNo?"+"'");
      sqlbv21.put("contno", tLCPolSchema.getContNo());
      sqlbv21.put("appntno", tLCPolSchema.getAppntNo());
      sqlbv21.put("InsuredNo", tLCPolSchema.getInsuredNo());
      pssrs_idno=pExeSQL.execSQL(sqlbv21);
      AppIDNo=pssrs_idno.GetText(1,1);
      InsuredIDNo=pssrs_idno.GetText(1,2);
      m_book.setEntry(mCurrentRow, t + 5, AppIDNo); //客户(投保人)身份证号

      m_book.setEntry(mCurrentRow, t + 6, tLCPolSchema.getContNo()); //保单号
      m_book.setEntry(mCurrentRow, t + 7, tLCPolSchema.getPaytoDate()); //缴费日期
      String PayMode = findPayMode(tLCPolSchema.getContNo(),
    		  tLCContSchema.getPayLocation());
      m_book.setEntry(mCurrentRow, t + 8, String.valueOf(PayMode)); //缴费方式

      m_book.setEntry(mCurrentRow, t + 40,
		      ChangeCodetoName.getSaleChnl(tLCPolSchema.getSaleChnl())); //销售渠道
      m_book.setEntry(mCurrentRow, t + 41, SamePerson); //代理人和投保人和被保人是否同一人标记
      m_book.setEntry(mCurrentRow, t + 42, tLCPolSchema.getAgentCom()); //代理机构
      //--------------------
      //新增交费年期,交费次数
      m_book.setEntry(mCurrentRow, t + 55, new Integer(tLCPolSchema.getPayYears()).toString()); //交费年期
      String tPayCount = "";
      LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
      tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
      tLMRiskAppDB.getInfo();
      if (tLMRiskAppDB.getRiskPeriod().equals("L")&&(tLCPolSchema.getPayIntv()!=0)){
        String sqlPayCount = "select  paytimes+1 from lcprem where polno ='" + "?tLCPolSchema?" +"'" ;
        SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
        sqlbv22.sql(sqlPayCount);
        sqlbv22.put("tLCPolSchema", tLCPolSchema.getPolNo());
        tPayCount = pExeSQL.getOneValue(sqlbv22);
      }else{
        tPayCount = "1";
      }
      m_book.setEntry(mCurrentRow, t + 56, tPayCount); //交费次数
      m_book.setEntry(mCurrentRow, t + 59,getPolState(tLCPolSchema.getContNo(),tLCPolSchema.getPolNo()));//保单状态
      m_book.setEntry(mCurrentRow, t + 60,tLCPolSchema.getContNo());//合同号
      //--------------------

      double mainPolPrem = 0;
      mainPolPrem = getMainPolPrem(tLCPolSchema.getPolNo());
      LJSPayDB tLJSPayDB = new LJSPayDB();
      tLJSPayDB.setOtherNo(tLCPolSchema.getContNo());
      tLJSPayDB.setOtherNoType("2");
      LJSPaySet tLJSPaySet = tLJSPayDB.query();
      m_book.setEntry(mCurrentRow, t + 9,
		      ChangeCodetoName.getRiskName(tLCPolSchema.getRiskCode())); //主险险种
      double sumprem = 0.0; //保费合计
      if ((tLJSPaySet == null) || (tLJSPaySet.size() == 0))
      {
         m_book.setEntry(mCurrentRow,t+10,"主险续保保费未催收："+ getRnewFailReason(tLCPolSchema));//主险催收失败的原因
      }
      else
      {
         m_book.setEntry(mCurrentRow, t + 10, String.valueOf(mainPolPrem)); //主险保费
         sumprem = sumprem + mainPolPrem;
      }



      String prem = "0";

      double mLeavingMoney = tLCPolSchema.getLeavingMoney(); //预收金额
      double tActualPayMoney = 0; //实缴金额
      double tNewLeavingMoney = 0; //余额

      LCPolSet tLCPolSet = findSubPol(tLCPolSchema);
      int i = 0;
      int j = 0;
      if (tLCPolSet.size() > 0)
      {
	//如果多于一条，则在excel的下一行的同一列位置显示，同时mSubNum增长
	for (i = 1; i <= tLCPolSet.size(); i++)
	{
	  mLeavingMoney += tLCPolSet.get(i).getLeavingMoney(); //合计所有险种下的余额

	  String SQL_SubPol =
	      "select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from LJSPayPerson where riskcode='"
	      + "?riskcode?"
	      + "' and PayType='ZC'  ";
	  SQL_SubPol = SQL_SubPol
	      + "and getnoticeno in (select getnoticeno from ljspay where othernotype='2' and otherno='"
	      + "?tLCPolSchema?" + "')";
	  SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
      sqlbv24.sql(SQL_SubPol);
      sqlbv24.put("riskcode",  tLCPolSet.get(i).getRiskCode());
      sqlbv24.put("tLCPolSchema", tLCPolSchema.getContNo());
	  ExeSQL tExeSQL = new ExeSQL();
	  SSRS tSSRS = tExeSQL.execSQL(sqlbv24);
	  if ((tSSRS != null) && (tSSRS.getMaxRow() > 0))
	  {
	    prem = tSSRS.GetText(1, 1);
	  }
	  m_book.setEntry(mCurrentRow , t + 11 +2*(i-1),
			  ChangeCodetoName.getRiskName(tLCPolSet.get(i)
			  .getRiskCode())); //附险险种
	  m_book.setEntry(mCurrentRow, t + 12 +2*(i-1), prem); //附险保费
	  sumprem = sumprem + Double.parseDouble(prem);
	}
	subNum = (subNum + tLCPolSet.size()) - 1;
      }

      m_book.setEntry(mCurrentRow, t + 25, String.valueOf(sumprem)); //保费合计

      m_book.setEntry(mCurrentRow, t + 26, String.valueOf(mLeavingMoney)); //预收金额
      if ((mLeavingMoney - sumprem) > 0)
      {
	tActualPayMoney = 0;
	tNewLeavingMoney = mLeavingMoney - sumprem;
      }
      else
      {
	tActualPayMoney = sumprem - mLeavingMoney;
	tNewLeavingMoney = 0;
      }
      m_book.setEntry(mCurrentRow, t + 27,
		      mDecimalFormat.format(tActualPayMoney)); //实交保费合计
      m_book.setEntry(mCurrentRow, t + 28,
		      mDecimalFormat.format(tNewLeavingMoney)); //余额扣除本次保费合计

      //投保人信息
      LCAppntDB tLCAppntDB = new LCAppntDB();
      tLCAppntDB.setContNo(tLCPolSchema.getContNo());
      tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
      if (tLCAppntDB.getInfo() == true)
      {
    	LCAddressDB tLCAddressDB = new LCAddressDB();
    	tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
    	tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
    	if (tLCAddressDB.getInfo() == true)
        {
			m_book.setEntry(mCurrentRow, t + 29,
					tLCAddressDB.getPostalAddress()); //收费地址
			m_book.setEntry(mCurrentRow, t + 30, tLCAddressDB.getZipCode()); //邮编
			m_book.setEntry(mCurrentRow, t + 31, tLCAddressDB.getPhone()); //电话
			m_book.setEntry(mCurrentRow, t + 39,
					ChangeCodetoName.getSexName(tLCAppntDB.getAppntSex())); //性别
			m_book.setEntry(mCurrentRow, t + 43, tLCAddressDB.getMobile()); //投保人手机
                        m_book.setEntry(mCurrentRow, t + 57, tLCAddressDB.getHomePhone());//家电
                        m_book.setEntry(mCurrentRow, t + 58, tLCAddressDB.getCompanyPhone());//公司电
        }
      }
      //此处新加被保人身份证号
      m_book.setEntry(mCurrentRow, t + 32, tLCPolSchema.getInsuredName()); //被保人姓名
      String[] BankInfo = findBankInfo(tLCPolSchema);
      m_book.setEntry(mCurrentRow, t + 33, InsuredIDNo); //被保人身份证号

      m_book.setEntry(mCurrentRow, t + 34, BankInfo[0]); //银行帐号
      m_book.setEntry(mCurrentRow, t + 35,
		      ChangeCodetoName.getBankCodeName(BankInfo[1])); //银行编码
      m_book.setEntry(mCurrentRow, t + 36, BankInfo[2]); //户名

      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tLCPolSchema.getAgentCode());
      String AgentTel = "";
      if (tLAAgentDB.getInfo() == true)
      {
	AgentTel = tLAAgentDB.getPhone();
	if ((AgentTel == null) || AgentTel.equals(""))
	{
	  AgentTel = tLAAgentDB.getMobile();
	}
	if (AgentTel == null)
	{
	  AgentTel = "";
	}
      }
      m_book.setEntry(mCurrentRow, t + 37, AgentTel); //代理人电话

      ExeSQL tExeSQL = new ExeSQL();
      String tSql = "select max(PayTimes) from LCPrem where PolNo='"
		  + "?PolNo?" + "'";
      SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
      sqlbv25.sql(tSql);
      sqlbv25.put("PolNo", tLCPolSchema.getPolNo());
      String tPayTimes = tExeSQL.getOneValue(sqlbv25);
      m_book.setEntry(mCurrentRow, t + 38, tPayTimes); //缴费次数
      m_book.setEntry(mCurrentRow, t + 44,
		      String.valueOf(tLCPolSchema.getPrtNo())); //印刷号
/*      "Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
      改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
*/
      tSql = " select (case count(*) when 0 then '正常' else '垫交' end) from lccontstate where  contno ='"+"?contno?"+"' and (polno='"
	   + "?polno?" + "' or polno='000000') and statetype='PayPrem' and state='1' and enddate is null ";
      SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
      sqlbv26.sql(tSql);
      sqlbv26.put("contno", tLCPolSchema.getContNo());
      sqlbv26.put("polno", tLCPolSchema.getPolNo());
      String djFlag = tExeSQL.getOneValue(sqlbv26);
      m_book.setEntry(mCurrentRow, t + 45, djFlag); //垫交标记

      LACommisionDB tLACommisionDB = new LACommisionDB();
      tLACommisionDB.setPolNo(tLCPolSchema.getPolNo());
      tLACommisionDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
      LACommisionSet tLACommisionSet = tLACommisionDB.query();
      if (tLACommisionSet.size() > 0)
      {
	LAAgentDB oldAgentDB = new LAAgentDB();
	oldAgentDB.setAgentCode(tLACommisionSet.get(1).getAgentCode());
	if (oldAgentDB.getInfo() == true)
	{
	  String oldAgentPhone = oldAgentDB.getPhone();
	  if (oldAgentPhone == null)
	  {
	    oldAgentPhone = "";
	  }
	  String oldAgentMobile = oldAgentDB.getMobile();
	  if (oldAgentMobile == null)
	  {
	    oldAgentMobile = "";
	  }
	  m_book.setEntry(mCurrentRow, t + 46, oldAgentDB.getName()); ///原业务员姓名
	  m_book.setEntry(mCurrentRow, t + 47, oldAgentPhone); ///原业务员电话
	  m_book.setEntry(mCurrentRow, t + 48, oldAgentMobile); ///原业务员手机
	}
      }
      if(Flag.equals("1"))  //若为在职单，须加上续收外勤工号与姓名
      {
         LRAdimAscriptionDB tlradimascriptionDB = new LRAdimAscriptionDB();
         tlradimascriptionDB.setPolNo(tLCPolSchema.getPolNo());
         LRAdimAscriptionSet tLRAdimAscriptionSet = tlradimascriptionDB.query();
         if (tLRAdimAscriptionSet.size() > 0)
         {
            m_book.setEntry(mCurrentRow, t + 49, tLRAdimAscriptionSet.get(1).getAgentCode()); ///续收外勤工号
            String getname="";
            getname=" select name from laagent where agentcode='"+"?agentcode?"+"' ";
            SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
            sqlbv27.sql(getname);
            sqlbv27.put("agentcode", tLRAdimAscriptionSet.get(1).getAgentCode());
            ExeSQL xExeSQL = new ExeSQL();
            m_book.setEntry(mCurrentRow, t + 50, xExeSQL.getOneValue(sqlbv27)); ///续收外勤姓名
            m_book.setEntry(mCurrentRow, t + 53, tLRAdimAscriptionSet.get(1).getZipCode());
            m_book.setEntry(mCurrentRow, t + 54, tLRAdimAscriptionSet.get(1).getAddCode());
         }
      }else{
         LRAscriptionDB tlrascriptionDB = new LRAscriptionDB();
         tlrascriptionDB.setPolNo(tLCPolSchema.getPolNo());
         LRAscriptionSet tLRAscriptionSet = tlrascriptionDB.query();
         if (tLRAscriptionSet.size() > 0)
         {
           m_book.setEntry(mCurrentRow, t + 53, tLRAscriptionSet.get(1).getZipCode());
           m_book.setEntry(mCurrentRow, t + 54, tLRAscriptionSet.get(1).getAddCode());
         }
      }
      if((tLCPolSchema.getAutoPayFlag()!= null)&&(tLCPolSchema.getAutoPayFlag().equals("1")))
      {
         m_book.setEntry(mCurrentRow, t + 51, "是"); ///客户垫交意愿
      }
      else
      {
         m_book.setEntry(mCurrentRow, t + 51, "否"); ///客户垫交意愿
      }

      //加上交费类型
      if(tLCPolSchema.getPayIntv()==12)
      {
    	  m_book.setEntry(mCurrentRow, t + 52, "年交"); ///交费类型
      }
      else if (tLCPolSchema.getPayIntv()==6)
      {
    	  m_book.setEntry(mCurrentRow, t + 52, "半年交"); ///交费类型
      }
      else if (tLCPolSchema.getPayIntv()==3)
      {
    	  m_book.setEntry(mCurrentRow, t + 52, "季交"); ///交费类型
      }
      else
      {
    	  m_book.setEntry(mCurrentRow, t + 52, "月交"); ///交费类型
      }
    }
    catch (Exception ex)
    {
      try
      {
	m_book.setEntry(mCurrentRow, t + 0,
			"***处理保单" + tLCPolSchema.getPolNo() + " 出错：" + ex); //管理机构
	mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
      }
      catch (Exception ex2)
      {
	buildError("dealSinglePol", ex.toString());
	return false;
      }
      return true;
    }
    //mCurrentRow = mCurrentRow + subNum; //如果有超过1个以上附加险，添加新的行存放第二个（以上）附加险信息
    mCurrentRow = mCurrentRow + 1; //处理完每条纪录后添加一行
    return true;
  }

  /**
   * 得到展业机构外部编码--中文名称
   * @param tAgentGroup
   * @return
   */
  private String findAgentBranch(String tAgentGroup)
  {
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    tLABranchGroupDB.setAgentGroup(tAgentGroup);
    if (tLABranchGroupDB.getInfo() == false)
    {
      return "unknow";
    }
    else
    {
      return tLABranchGroupDB.getName();
    }
  }

  /**
* 得到催收失败的原因
* @param tLCPolSchema
* @return 催收失败的原因
*/
private String getRnewFailReason(LCPolSchema tLCPolSchema)
{
  String tReason = "";
  ExeSQL tExeSQL = new ExeSQL();
  String tSQL = "";
  if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	  tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='"+"?PrtNo?"+"' and Riskcode='"+"?risk?"+"' and ErrNo=1 and Rownum=1 ";
  }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	  tSQL = "select ErrInfo from LCRnewErrLog where PrtNo='"+"?PrtNo?"+"' and Riskcode='"+"?risk?"+"' and ErrNo=1 limit 0,1 ";
  }
  SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
  sqlbv28.sql(tSQL);
  sqlbv28.put("PrtNo", tLCPolSchema.getPrtNo());
  sqlbv28.put("risk", tLCPolSchema.getRiskCode());
  tReason = tExeSQL.getOneValue(sqlbv28);
  return tReason;
  }

  /**
   * 交费方式
   * @param tPolNo
   * @param PayLocation
   * @return
   */
  private String findPayMode(String tContNo, String PayLocation)
  {
    if (PayLocation == null)
    {
      return findPayMode2(tContNo);
    }
    else
    {
      return ChangeCodetoName.getPayLocationName(PayLocation);
    }
  }

  /**
   * 找交费方式
   * @param tPolNo
   * @return
   */
  private String findPayMode2(String tContNo)
  {
    String SQL = "select tempfeeno from LJTempFee where otherno='" + "?tContNo?"
	       + "' and confflag='1' order by makedate desc";
    SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
    sqlbv29.sql(SQL);
    sqlbv29.put("tContNo", tContNo);
    ExeSQL tExeSQL = new ExeSQL();
    SSRS tSSRS = tExeSQL.execSQL(sqlbv29);
    if (tSSRS == null)
    {
      return "unknow";
    }
    String tempfeeno = tSSRS.GetText(1, 1);

    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
    tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
    LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
    if (tLJTempFeeClassSet.size() == 0)
    {
      return "unknow";
    }
    else
    {
      return ChangeCodetoName.getPayModeName(tLJTempFeeClassSet.get(1)
	  .getPayMode());
    }
  }

  /**
   * 查找银行信息
   * @param tLCPolSchema
   * @return
   */
  private String[] findBankInfo(LCPolSchema tLCPolSchema)
  {
    if (tLCPolSchema.getPayLocation() == null)
    {
      return findBankInfo(tLCPolSchema.getContNo());
    }
    else
    {
      LCContDB tLCContDB = new LCContDB();
      tLCContDB.setContNo(tLCPolSchema.getContNo());

      String[] bankInfo = new String[3];
      bankInfo[0] = tLCContDB.query().get(1).getBankAccNo();
      bankInfo[1] = tLCContDB.query().get(1).getBankCode();
      bankInfo[2] = tLCContDB.query().get(1).getAccName();
      return bankInfo;
    }
  }

  /**
   * 找银行信息
   * @param tPolNo
   * @return
   */
  private String[] findBankInfo(String tContNo)
  {
    String[] bankInfo = new String[3];
    String SQL = "select tempfeeno from LJTempFee where otherno='" + "?otherno?"
	       + "' and confflag='1' order by makedate desc";
    SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
    sqlbv30.sql(SQL);
    sqlbv30.put("otherno", tContNo);
    ExeSQL tExeSQL = new ExeSQL();
    SSRS tSSRS = tExeSQL.execSQL(sqlbv30);
    if (tSSRS == null)
    {
      return bankInfo;
    }
    String tempfeeno = tSSRS.GetText(1, 1);

    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
    tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
    LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
    if (tLJTempFeeClassSet.size() == 0)
    {
      return bankInfo;
    }
    else
    {
      bankInfo[0] = tLJTempFeeClassSet.get(1).getBankAccNo();
      bankInfo[1] = tLJTempFeeClassSet.get(1).getBankCode();
      bankInfo[2] = tLJTempFeeClassSet.get(1).getAccName();
    }
    return bankInfo;
  }

  /**
   * 得到续期保单对应某个日期应该交纳的费用--从保费项查询--因为保单纪录所有阶段应该缴纳的总和
   * @param tPolNo   主险号
   * @return
   */
  private double getMainPolPrem(String tPolNo)
  {
    String sql = "select sum(prem) from LCPrem where polno='" + "?tPolNo?" 
	       + "' and PayEndDate>'" + "?mEndDate?" + "' "
               + "and to_char(PayStartDate,'yyyymm')<=to_char(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),'yyyymm') and char_length(trim(dutycode))<>10";
    ExeSQL tExeSQL = new ExeSQL();
    SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
    sqlbv31.sql(sql);
    sqlbv31.put("tPolNo", tPolNo);
    sqlbv31.put("mEndDate", mEndDate);
    sqlbv31.put("mStartDate", mStartDate);
    SSRS tSSRS = tExeSQL.execSQL(sqlbv31);
    double sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
    return sumPrem;
  }

  /**
   * 不同类型的数据之间添加说明
   * @return
   */
  private boolean addPrompt(String sPrompt)
  {
    try
    {
      m_book.setEntry(mCurrentRow, 0, sPrompt);
      mCurrentRow = mCurrentRow + 1;
    }
    catch (Exception ex)
    {
      buildError("addPrompt","添加说明“"+sPrompt+"失败");
      return false;
    }
    return true;
  }


  //判断该保单是否存在暂交费，存在不在清单中显示
  public boolean judgeTempFee(String  tContNo)
  {
    String sql = " select 1 from ljspay a,ljtempfee b where a.getnoticeno=b.tempfeeno and a.otherno='"+"?tContNo?"+"'";
    SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
    sqlbv32.sql(sql);
    sqlbv32.put("tContNo", tContNo);
    ExeSQL tExe = new ExeSQL();
    if(tExe.getOneValue(sqlbv32).equals("1"))
    {
      return false;
    }
    return true;
  }

  /**
   * 填充数据前的准备工作：获取文件生成路径和生成文件名
   * @return
   */
  private boolean checkDesc(String mDelFlag)
  {
    LDSysVarDB tLDSysVarDB = new LDSysVarDB();

    //取清单模版文件存放路径（即要生成文件的存放路径）
    tLDSysVarDB = new LDSysVarDB();
    tLDSysVarDB.setSysVar(mFilePathDesc);
    if (tLDSysVarDB.getInfo() == false)
    {
      buildError("checkDesc", "LDSysVar取文件路径(" + mFilePathDesc + ")描述失败");
      return false;
    }
    mFilePath = tLDSysVarDB.getSysVarValue();
    //mFilePath = "D:\\TEMP\\";//本地测试用
    mFileName = getFileName(mDelFlag);
    return true;
  }

  /**
   * 查询续期需要自动续保缴费的附加险
   * @param tLCPolSchema
   * @return
   */
  private LCPolSet findSubPol(LCPolSchema tLCPolSchema)
  {

    LCPolDB tLCPolDB = new LCPolDB();
    String strSQL = "select * from lcpol where mainpolno='"
		  + "?mainpolno?" + "' ";
    strSQL = strSQL + "and polno!='" + "?polno?" + "' ";
    strSQL = strSQL + "and AppFlag='1' and paytodate='"
	   + "?paytodate?" + "' ";
    strSQL = strSQL
	   + "and ((PaytoDate = PayEndDate and RnewFlag = '-1' and exists (select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag='Y')) or PayToDate < PayEndDate)";
    SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
    sqlbv33.sql(strSQL);
    sqlbv33.put("mainpolno", tLCPolSchema.getPolNo());
    sqlbv33.put("polno", tLCPolSchema.getPolNo());
    sqlbv33.put("paytodate", tLCPolSchema.getPaytoDate());
    LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv33);

    return tLCPolSet;
  }

  /**
   * 生成文件名：ReAgentAnyDown1_86110000_200709.xls
   * @param cAgentCode
   * @return
   */
  private String getFileName(String mDelFlag)
  {
    String filename = "";
    if(this.mBranchType.equals("4"))
    {
    	filename = "ReAgentAnyDown1_";
    }
    else
    {
    	filename = "ReAgentAnyDown99_";
    }

    if(mDelFlag.equals("0"))
    {
      filename = filename + mManageCom + "_";
    }
    else
    {
      filename = filename + mAgentCode + "_";
    }
    String  startdate_Sql = " select substr(to_char(to_date('"+"?mStartDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
    SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
    sqlbv34.sql(startdate_Sql);
    sqlbv34.put("mStartDate", mStartDate);
	String  enddate_Sql = " select substr(to_char(to_date('"+"?mEndDate?"+"','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
    SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
	    sqlbv35.sql(enddate_Sql);
	    sqlbv35.put("mEndDate", mEndDate);
	ExeSQL ttExeSQL = new ExeSQL();
	mStartDate=ttExeSQL.getOneValue(sqlbv34);
	mEndDate=ttExeSQL.getOneValue(sqlbv35);

    filename=filename+mStartDate+"_"+mEndDate+"_"+mType1+"_"+mType2+"_"+mType3+"_"+mType4;
    filename = filename + ".xls";
    logger.debug("生成文件名:" + filename);
    return filename;
  }

  /**
   * 错误处理
   * @param szFunc
   * @param szErrMsg
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError();
    cError.moduleName = "LRPolListCDownBL";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

}
