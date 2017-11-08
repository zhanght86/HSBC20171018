package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.lis.operfee.ShowDueFeeList;

import java.sql.Connection;
import com.sinosoft.lis.reagent.LRPolListCDownUI;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class XQDutyReportAuto extends TaskThread
{
private static Logger logger = Logger.getLogger(XQDutyReportAuto.class);



        public XQDutyReportAuto() {
        }

        public CErrors mErrors = new CErrors();
        private String mDate=null;


        public boolean dealMain()
        {
          /*业务处理逻辑*/
          logger.debug("start XQDutyReportAuto!!");
          logger.debug("进入业务逻辑处理,开始提数计算。!");

          GlobalInput tG = new GlobalInput();
          tG.Operator = "auto";
          tG.ManageCom = "86";
          String date_sql = "";
          String YearMonth = "";
          
          String StartDate = "";  //统计起期
          String EndDate = "";    //统计止期
          
          String LLast_StartDate = "";  //宽末起期
          String LLast_EndDate = "";    //宽末止期
          
          String Last_StartDate = "";  //宽一起期
          String Last_EndDate = "";    //宽一止期
          
          String Cur_StartDate = "";  //当月起期
          String Cur_EndDate = "";    //当月止期
          
          String Next_StartDate = "";  //下月起期
          String Next_EndDate = "";    //下月止期
          
          ExeSQL tExeSQL = new ExeSQL();
          LRPolListCDownUI tLRPolListCDownUI = new LRPolListCDownUI();
          tExeSQL = new ExeSQL();
          //获取模板路径
          String mModePath = "";
          LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
          tLDSysVarDB2.setSysVar("LAAutoReport");
          if (!tLDSysVarDB2.getInfo()) 
          {
          	logger.debug("获取模板路径失败");
          }
          mModePath = tLDSysVarDB2.getSysVarValue();
          
          String str_com=" select comcode from ldcom where comcode like '86%' and char_length(comcode)=6  order by comcode ";
          SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
          sqlbv1.sql(str_com);
          //str_com="select '86110000' from dual";
          SSRS SSRS_com = new SSRS();
          SSRS_com = tExeSQL.execSQL(sqlbv1);

          //宽末报表运行   月初每周三、周五自动运行  	25号后，天天运行
          int LLast_count =0;
          String LLast_check1=" select (select count(*) from dual where to_char(now(),'D')=4 ) "
           + "+ (select count(*) from dual  where to_char(now(),'D')=6 ) "
           + "+ (select count(*) from dual where substr(now(),9,2)>25)  from dual";
          SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
          sqlbv2.sql(LLast_check1);
          LLast_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
          if(LLast_count>0)
          {  //符合条件的话需要生成相应的宽末报表
        	  logger.debug("开始宽末应收报表自动生成！");
        	  //获取宽末起止期
        	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),-2),'yyyymm') " ;
        	  SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        	  sqlbv3.sql(date_sql);
        	  SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv3);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	          
        	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	
	              if(!tLRPolListCDownUI.submitData(tVData,"CONFIRM")) 
	              {
	                logger.debug("管理机构" + tManageCom + "续收外勤应收清单生成失败，原因是：" +
	                		tLRPolListCDownUI.mErrors.
	                                   getFirstError());
	                this.buildError("dealdata", "业务处理出错！");
	                continue;
	              }
	          }
          }
          //然后是宽一报表  每周四 一次	
          int Last_count =0;
          String Last_check=" select count(*) from dual where to_char(now(),'D')=5  ";
          SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
          sqlbv4.sql(Last_check);
          Last_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
          if(Last_count>0)
          {  //符合条件的话需要生成相应的宽一报表
        	  logger.debug("开始宽一应收报表自动生成！");
        	  //获取起止期
        	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),-1),'yyyymm') " ;
        	  SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        	  sqlbv5.sql(date_sql);
        	  SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv5);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	          
        	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	
	              if(!tLRPolListCDownUI.submitData(tVData,"CONFIRM")) 
	              {
	                logger.debug("管理机构" + tManageCom + "续收外勤应收清单生成失败，原因是：" +
	                		tLRPolListCDownUI.mErrors.
	                                   getFirstError());
	                this.buildError("dealdata", "业务处理出错！");
	                continue;
	              }
	          }
          }
          
          //然后是当月报表  每周一 一次	
          int Cur_count =0;
          String Cur_check=" select count(*) from dual where to_char(now(),'D')=2  ";
          SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
          sqlbv6.sql(Cur_check);
          Cur_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv6));
          if(Cur_count>0)
          {  //符合条件的话需要生成相应的当月报表
        	  logger.debug("开始当月应收报表自动生成！");
        	  //获取起止期
        	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(now(),'yyyymm') " ;
        	  SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        	  sqlbv7.sql(date_sql);
        	  SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv7);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	          
        	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	
	              if(!tLRPolListCDownUI.submitData(tVData,"CONFIRM")) 
	              {
	                logger.debug("管理机构" + tManageCom + "续收外勤应收清单生成失败，原因是：" +
	                		tLRPolListCDownUI.mErrors.
	                                   getFirstError());
	                this.buildError("dealdata", "业务处理出错！");
	                continue;
	              }
	          }
          }

          //然后是下月报表  每月21号、28号一次	
          int Next_count =0;
          String Next_check=" select (select count(*) from dual where substr(now(),9,2)=21) "
              + "+ (select count(*) from dual where substr(now(),9,2)=28)  from dual ";
          SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
          sqlbv8.sql(Next_check);
          Next_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv8));
          if(Next_count>0)
          {  //符合条件的话需要生成相应的下月报表
        	  logger.debug("开始下月应收报表自动生成！");
        	  //获取起止期
        	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),1),'yyyymm') " ;
        	  SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
        	  sqlbv9.sql(date_sql);
        	  SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv9);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	          
        	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	
	              if(!tLRPolListCDownUI.submitData(tVData,"CONFIRM")) 
	              {
	                logger.debug("管理机构" + tManageCom + "续收外勤应收清单生成失败，原因是：" +
	                		tLRPolListCDownUI.mErrors.
	                                   getFirstError());
	                this.buildError("dealdata", "业务处理出错！");
	                continue;
	              }
	          }
          }
          logger.debug("end XQDutyReportAuto!!");
          return true;
        }

        /**
       *错误生成函数
       */
      private void buildError(String szFunc, String szErrMsg)
      {
         CError cError = new CError();
         cError.moduleName = "XQDutyReportAuto";
         cError.functionName = szFunc;
         cError.errorMessage = szErrMsg;
         this.mErrors.addOneError(cError);
      }

        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
              XQDutyReportAuto tXQDutyReportAuto = new XQDutyReportAuto();
              //本地测试
//              if(!tXQDutyReportAuto.dealMain())
//              {
//                logger.debug("失败，原因为："+tXQDutyReportAuto.mErrors.getFirstError());
//              }
              tXQDutyReportAuto.run();
        }
}
