
package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import java.text.*;

public class AdvancePremQueryBL {
private static Logger logger = Logger.getLogger(AdvancePremQueryBL.class);
	public CErrors mErrors=new CErrors();
	 private VData mResult = new VData();
	
	private GlobalInput mGlobalInput = new GlobalInput() ;
	  private String tManageCom="";
	  private String tComLevel="";
	  private String tdate="";
	  private String tBranchType="";
	  private String templatepath="";//模板路径
	



	  public AdvancePremQueryBL()
	  {
	  }

	  
	  public static void main(String[] args) {
	    AdvancePremQueryBL tAdvancePremQueryBL = new AdvancePremQueryBL();
	    VData tVData = new VData();
	    GlobalInput tG = new GlobalInput();
	    tG.ManageCom = "86";
	    tG.Operator = "AutoAS";
	    tVData.addElement("86");
	    tVData.addElement("2");
	    tVData.addElement("2008-05-25");
	    tVData.addElement("4");
	    tVData.addElement(tG);
	    tAdvancePremQueryBL.submitData(tVData,"");
	

	    try {
	      if (!tAdvancePremQueryBL.submitData(tVData,"")) {
	        System.err.println(tAdvancePremQueryBL.mErrors.getFirstError());
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace(System.err);
	    }
	    logger.debug("end to batch ascription");
	  }
	  
	  public boolean submitData(VData cInputData, String cOperate)
	  {
//	  得到外部传入的数据，将数据备份到本类中
	     if( !getInputData(cInputData) )
	      {
	         return false;
	      }

	    mResult.clear();


	     if( !queryData() )
	      {
	         return false;
	      }
	     
	     return true;
	  }
	  private boolean getInputData(VData cInputData)
	  {
//	 全局变量
	     tManageCom=(String)cInputData.get(0);
	     tComLevel=(String)cInputData.get(1);
	     tdate=(String)cInputData.get(2);
	     tBranchType=(String)cInputData.get(3);
	     templatepath=(String)cInputData.get(4);
	     String[] tDate11 = new String[3];
	     tDate11 = tdate.split("-");
	     if(tDate11[1].length()==1) 
	     {
	    	 tDate11[1]="0"+tDate11[1];
	     }
	     if(tDate11[2].length()==1) 
	     {
	    	 tDate11[2]="0"+tDate11[2];
	     }
	     String date_sql=" select  concat(substr(to_char(to_date("+"?date1?"+",'yyyymm'),'yyyy-mm-dd'),1,8),'?date2?')  from dual ";
	     SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		 sqlbv.sql(date_sql);
		 sqlbv.put("date1", tDate11[0]+tDate11[1]);
		 sqlbv.put("date2", tDate11[2]);
	     ExeSQL ttExeSQL = new ExeSQL();
	     tdate = ttExeSQL.getOneValue(sqlbv);
	     
	     mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	     return true;
	  }
	  public VData getResult()
	  {
	     return this.mResult;}
	  
	  private boolean queryData()
	  {	      String Tman =mGlobalInput.Operator ;//得到统计人
	     
	      ListTable tlistTable = new ListTable();
	      tlistTable.setName("xq4");
	     
	     // texttag.add("StartDay",tStartDate+"--"+tEndDate);
	      
	     
	     //SQL语句预处理
	      int Con=0;
	      Con=Integer.parseInt(tComLevel)*2;
	      logger.debug("机构长度为:"+Con);

      
             
 //主键SQL
	    String Key_sql = "select comcode from ldcom a "
	                  +"where comcode like concat('?ManageCom?','%') "
			          +"and char_length(trim(comcode)) = ?comcodechar_length? order by comcode " ;
	    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	    sqlbv1.sql(Key_sql);
	    sqlbv1.put("ManageCom",tManageCom.trim());
	    sqlbv1.put("comcodechar_length",String.valueOf(Con));
	    
	    logger.debug("Key_sql["+Key_sql+"]");
 // 机构名称
	    String name = "select comcode ,shortname from ldcom a "
	  		         +" where comcode like concat('?ManageCom?','%') "
			         +" and char_length(trim(comcode)) = ?comcodechar_length? ";
	    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	    sqlbv2.sql(name);
	    sqlbv2.put("ManageCom",tManageCom.trim());
	    sqlbv2.put("comcodechar_length",String.valueOf(Con));
	 logger.debug("name["+name+"]");
  //规模保费 当日进度
	/* "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
	 改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
    */
	   String tsql1="select subcom,sum(s1) from ( select substr(b.managecom,1,?Con?) " +
	   		"as subcom,(case when sum(b.paymoney) is not null then sum(b.paymoney) else 0 end) as s1 "
       + "from ljtempfee b where b.tempfeetype='1' "
       + "and b.makedate='"+"?tdate?"+"' "
       + "and b.managecom like concat('?ManageCom?','%') "
       + "and exists (select 1 from laagent c,labranchgroup d where b.agentcode=c.agentcode and c.branchtype='"+"?tBranchType?"+"' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
       +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=b.otherno and b.othernotype='0' and e.edortype ='WT' and d.makedate<='"+"?tdate?"+"' " +
       " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=b.otherno and b.othernotype='4' and e.edortype='WT' and d.makedate<='"+"?tdate?"+"' )" +
       " and not exists( select 1 from ljagettempfee where tempfeeno=b.tempfeeno and makedate<='"+"?tdate?"+"')" +
       "group by substr(b.managecom,1,?Con?),b.riskcode,b.PayYears,b.PayIntv) a group by subcom ";
	   
	   SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	    sqlbv3.sql(tsql1);
	    sqlbv3.put("ManageCom",this.tManageCom);
	    sqlbv3.put("Con",String.valueOf(Con));
	    sqlbv3.put("tdate", tdate);
	    sqlbv3.put("tBranchType", tBranchType);
	    
	   logger.debug("tsql1["+tsql1+"]");
      // 规模保费累计进度
	   
	   
	   logger.debug("tdate"+tdate+"");
	    
	     
	   String tdate1_sql="select b.startdate from lastatsegment a, lastatsegment b  where a.stattype=b.stattype and a.yearmonth=b.yearmonth and a.stattype='X'and '?tdate?'>=a.startdate and '?tdate?'<=a.enddate";
	 
	   SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
	    sqlbv4.sql(tdate1_sql);
	    sqlbv4.put("tdate", tdate);
	   ExeSQL tsql=new ExeSQL();
	   String tdate1= tsql.getOneValue(sqlbv4).substring (0,10);
	    
	 /*  "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
	   改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
*/
	    String tsql2="select subcom,sum(s1)from ( select substr(b.managecom,1,?Con?) as subcom," +
	  "(case when sum(b.paymoney) is not null then sum(b.paymoney) else 0 end) as s1 "
       + "from ljtempfee b where b.tempfeetype='1' "
       + "and b.makedate>='?tdate1?' and b.makedate<='?tdate?' "
       + "and b.managecom like concat('?tManageCom?','%') "
       + "and exists (select 1 from laagent c,labranchgroup d where b.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
       +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=b.otherno and b.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
       " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=b.otherno and b.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
       " and not exists( select 1 from ljagettempfee where tempfeeno=b.tempfeeno and makedate<='?tdate?')" 
       
       + "group by substr(b.managecom,1,?Con?),b.riskcode,b.PayYears,b.PayIntv) a group by subcom ";
	    SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
	    sqlbv5.sql(tsql2);
	    sqlbv5.put("Con", String.valueOf(Con));
	    sqlbv5.put("tBranchType", tBranchType);
	    sqlbv5.put("tManageCom", this.tManageCom);
	    sqlbv5.put("tdate", tdate);
	    sqlbv5.put("tdate1", tdate1);
     //业绩当日进度
	 
	 
	  
	  String  tsql3="select substr(managecom,1,?Con?) "
	  +",sum(round(a.paymoney*round(AgentCalDirectWage(otherno,otherno,riskcode,makedate,managecom,payyears,payintv,'1')/0.35 ,2),2))"
	  +"from ljtempfee a where tempfeetype='1'"
	  		
      +"and makedate = '?tdate?"
      +"'and managecom like concat('?tManageCom?','%')"
      + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
      +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
      " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
      " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
      
      + "group by substr(managecom,1,?Con?) ";
	  SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
	  sqlbv6.sql(tsql3);
	  sqlbv6.put("Con", String.valueOf(Con));
	  sqlbv6.put("tBranchType", tBranchType);
	  sqlbv6.put("tManageCom", this.tManageCom);
	  sqlbv6.put("tdate", tdate);
	  
	//2.  // 业绩累计进度
	  String tsql4="select substr(managecom,1,?Con?)"
		+",sum(round(a.paymoney*round(AgentCalDirectWage(otherno,otherno,riskcode,makedate,managecom,payyears,payintv,'1')/0.35,2),2))"
  		+"from ljtempfee a where tempfeetype='1'"
  		
        +"and makedate>='?tdate1?' and makedate<='?tdate?"
      
        +"'and managecom like concat('?tManageCom?','%') "
        + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
        +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
        
        + "group by substr(managecom,1,?Con?) ";
	  SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
	  sqlbv7.sql(tsql4);
	  sqlbv7.put("Con", String.valueOf(Con));
	  sqlbv7.put("tBranchType", tBranchType);
	  sqlbv7.put("tManageCom", this.tManageCom);
	  sqlbv7.put("tdate1", tdate1);
	  sqlbv7.put("tdate", tdate);
	  //准保当日进度
	    String tsql5="select substr(managecom,1,?Con?)"
	  	+",sum(a.paymoney*AgentCalStandPrem(riskcode,PayYears,PayIntv,'1') )"
	  	+"from ljtempfee a where tempfeetype='1'"
	  		
        +"and makedate = '?tdate?"
        +"'and managecom like concat('?tManageCom?','%') "
        + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
        +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
         
        + "group by substr(managecom,1,?Con?)       ";
	    SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
	    sqlbv8.sql(tsql5);
	    sqlbv8.put("Con", String.valueOf(Con));
	    sqlbv8.put("tBranchType", tBranchType);
	    sqlbv8.put("tManageCom", this.tManageCom);
	    sqlbv8.put("tdate1", tdate1);
	    sqlbv8.put("tdate", tdate);
	//3.    //准保累计进度
	    String tsql6="select substr(managecom,1,?Con?) "
		+",sum(a.paymoney*AgentCalStandPrem(riskcode,PayYears,PayIntv,'1')) "
  		+"from ljtempfee a where tempfeetype='1'"
  		
        +"and makedate>='?tdate1?' and makedate<='?tdate?"
        +"'and managecom like concat('?tManageCom?','%') "
        + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
        +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
        + "group by substr(managecom,1,?Con?)";
	    
	    SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
	    sqlbv9.sql(tsql6);
	    sqlbv9.put("Con", String.valueOf(Con));
	    sqlbv9.put("tBranchType", tBranchType);
	    sqlbv9.put("tManageCom", this.tManageCom);
	    sqlbv9.put("tdate1", tdate1);
	    sqlbv9.put("tdate", tdate);
	//4.  //标准件数
	   //union是内连接－－结果集中只包含两个表中都有的记录，相当于交运算
	    //union all是全接连－－结果集中包含两个表的所有的记录，相当于并运算
        
	  String tsql7="select distinct substr(managecom,1,?Con?) , "
        + "sum( case when b.paymoney>=0 then 1  "
        + "else -1 end) over(partition by substr(managecom,1,?Con?) ) "
        + "from ljtempfee b "
        // 标准件数修改 附加险不折算标准件数
        //and exists (select '1' from lcpol where polno=b.otherno and polno=mainpolno)
        //ljtempfee签单前otherno为印刷号 改用lmriskapp.subriskflag='M'来限制主险排除附加险
        + "where exists (select 1 from lmriskapp m where b.riskcode=m.riskcode and m.riskperiod='L' and m.subriskflag='M') "
        + "and b.tempfeetype='1' "
        
       
        + "and b.makedate>='?tdate1?' and b.makedate<='?tdate?' "
        + "and b.managecom like concat('?tManageCom?','%') "
        + "and exists (select 1 from laagent c,labranchgroup d where b.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
        +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=b.otherno and b.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=b.otherno and b.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
        " and not exists( select 1 from ljagettempfee where tempfeeno=b.tempfeeno and makedate<='?tdate?')" 
        + "union "
        + "select distinct substr(managecom,1,?Con?) ,"
        + "sum( case when b.paymoney>-500 and b.paymoney<500 then round(b.paymoney/500,2) "
        + "when b.paymoney>=500 then 1 "
        + "else -1 end) over(partition by substr(managecom,1,?Con?)) "
        + "from ljtempfee b "
        //add by jiaqiangli 2007-06-18 标准件数修改 附加险不折算标准件数
        //and exists (select '1' from lcpol where polno=b.otherno and polno=mainpolno)
        //ljtempfee签单前otherno为印刷号 改用lmriskapp.subriskflag='M'来限制主险排除附加险
        + "where exists (select 1 from lmriskapp m where b.riskcode=m.riskcode and m.riskperiod in ('M','S') and m.subriskflag='M') "
        + "and b.tempfeetype='1' "
       
      
        + "and b.makedate>='?tdate1?' and b.makedate<='?tdate?' "
        + "and b.managecom like concat('?tManageCom?','%') "
	    + "and exists (select 1 from laagent c,labranchgroup d where b.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null))"
	    +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=b.otherno and b.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=b.otherno and b.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
	       " and not exists( select 1 from ljagettempfee where tempfeeno=b.tempfeeno and makedate<='?tdate?')" ;
	  
	  
	  SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
	  sqlbv10.sql(tsql7);
	  sqlbv10.put("Con", String.valueOf(Con));
	  sqlbv10.put("tBranchType", tBranchType);
	  sqlbv10.put("tManageCom", this.tManageCom);
	  sqlbv10.put("tdate1", tdate1);
	  sqlbv10.put("tdate", tdate);
	  //5.  //新契约件数
	     String tsql8="select distinct substr(managecom,1,?Con?) , "
	    +" count(*) from ljtempfee a, lmriskapp b where a.riskcode=b.riskcode and b.subriskflag='M' and a.payyears>1  "
	    +"and a.tempfeetype='1' "
	    +"and a.makedate>='?tdate1?' and a.makedate<='?tdate?' "
	    + "and a.managecom like concat('?tManageCom?','%') "
	    + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
	    +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
	     
	    + "group by substr(managecom,1,?Con?)";
	   
	     SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
	     sqlbv11.sql(tsql8);
	     sqlbv11.put("Con", String.valueOf(Con));
	     sqlbv11.put("tBranchType", tBranchType);
	     sqlbv11.put("tManageCom", this.tManageCom);
	     sqlbv11.put("tdate1", tdate1);
	     sqlbv11.put("tdate", tdate);
	    /*   "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
	                改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
       */
	   String tsql9="select distinct substr(managecom,1,?Con?), "
	     +"sum(round((case when a.paymoney is not null then a.paymoney else 0 end) / 600, 2)) from ljtempfee a, lmriskapp b where a.riskcode=b.riskcode and b.subriskflag='M' and a.payyears=1 "
	     +"and a.tempfeetype='1' "
	     +"and a.makedate>='?tdate1?' and a.makedate<='?tdate?' "
	     + "and a.managecom like concat('?tManageCom?','%') "
	     + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
	     +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
	        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
	        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
	     + "group by substr(managecom,1,?Con?)";
	     SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
	     sqlbv12.sql(tsql9);
	     sqlbv12.put("Con", String.valueOf(Con));
	     sqlbv12.put("tBranchType", tBranchType);
	     sqlbv12.put("tManageCom", this.tManageCom);
	     sqlbv12.put("tdate1", tdate1);
	     sqlbv12.put("tdate", tdate);
	   
	   String tsql10="select distinct substr(managecom,1,?Con?) , "
	     +" count(*)from ljtempfee a, lmriskapp b where a.riskcode=b.riskcode   and b.subriskflag='M' "
	     +"and a.tempfeetype='1' "
	     +"and a.makedate>='?tdate1?' and a.makedate<='?tdate?' "
	     + "and a.managecom like  concat('?tManageCom?','%') "
	    
	     + "and exists (select 1 from laagent c,labranchgroup d where a.agentcode=c.agentcode and c.branchtype='?tBranchType?' and c.branchcode=d.agentgroup and (d.state <> '1' or d.state is null)) "
	     +" and not exists( select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.contno=a.otherno and a.othernotype='0' and e.edortype ='WT' and d.makedate<='?tdate?' " +
	        " union select 1 from lcpol c,lpedormain d,LPEdorItem e  where c.appflag='4' and d.edorno=e.edorno and c.contno=d.contno and c.prtno=a.otherno and a.othernotype='4' and e.edortype='WT' and d.makedate<='?tdate?' )" +
	        " and not exists( select 1 from ljagettempfee where tempfeeno=a.tempfeeno and makedate<='?tdate?')" 
	     + "group by substr(managecom,1,?Con?)";
	   SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
	   sqlbv13.sql(tsql10);
	   sqlbv13.put("Con", String.valueOf(Con));
	   sqlbv13.put("tBranchType", tBranchType);
	   sqlbv13.put("tManageCom", this.tManageCom);
	   sqlbv13.put("tdate1", tdate1);
	   sqlbv13.put("tdate", tdate);
	  
	   //开始调用ZHashReport
	      VData tVData = new VData();
	      tVData.add(sqlbv2);
	      tVData.add(sqlbv3);
	      tVData.add(sqlbv5);
	      tVData.add(sqlbv6);
	      tVData.add(sqlbv7);
	      tVData.add(sqlbv8);
	      tVData.add(sqlbv9);
	      tVData.add(sqlbv10);
	      tVData.add(sqlbv11);
	      tVData.add(sqlbv12);
	      tVData.add(sqlbv13);
	     
            ZHashReport tZHashReport = new ZHashReport(sqlbv1,tVData);
	     
            tZHashReport.setColumnType(1,tZHashReport.StringType);
            
	         tZHashReport.setDoformat(false);
	         
	        String[][] result = tZHashReport.calItem();
	        
	        
	        for (int jj=0;jj<result.length;jj++) 
	        {
	        	  for (int kk=0;kk<result[jj].length;kk++) 
	        	  {
	        	    logger.debug(result[jj][kk]+",");
	        	  }
	        	
	        }
	        
	        logger.debug(result.length);
	        
            String[][] newresult = new String[result.length+1][13];
            logger.debug("why");
            double sum[]=new double[13];
            double fee=0.0;
             for (int i=0;i<sum.length;i++)
             {sum[i]=0.0;}
             
           
    
             for (int i = 0; i < result.length; i++) {
              //logger.debug("result.length["+result.length+"]");
                 newresult[i][0] = result[i][1]; // 机构
                 newresult[i][1] = result[i][0];//结构代码
                 newresult[i][2] = result[i][2]; // 规模保费当日进度；
                 newresult[i][3] = result[i][3];//规模保费累计进度；
                 newresult[i][4] = result[i][4];//业绩当日进度；
                 newresult[i][5] = result[i][5];//业绩累计进度；
                 newresult[i][6] = result[i][6];//标准保费当日进度；
                 newresult[i][7] = result[i][7];//标准保费累计进度;
                 newresult[i][8] = result[i][8];//标准件数；
                
                
                 //新契约件数；
                 newresult[i][9] = String.valueOf (Double.parseDouble(result[i][9])+Double.parseDouble (result[i][10]));
                 if(result[i][11].equals("0.0")||result[i][8].equals("0.0") ||newresult[i][9].equals("0.0"))
                 {  fee  += Double.parseDouble(result[i][11]);
                 newresult[i][10]="-1";
                 newresult[i][11]="-1";
                 newresult[i][12]="-1";
                 
                	 
                   }
                 else{
                	 fee  += Double.parseDouble(result[i][11]); 
                 
                 newresult[i][10]=String.valueOf(Math.round((Double.parseDouble(result[i][3]) /Double.parseDouble (result[i][11]))*100)/100.0);//件均保费
                 newresult[i][11]=String.valueOf(Math.round((Double.parseDouble(result[i][7]) /Double.parseDouble(result[i][8]))*100)/100.0);//件均标保；
                newresult[i][12]= String.valueOf(Math.round((Double.parseDouble(result[i][5]) /Double.parseDouble( newresult[i][9]))*100)/100.0);//件均业绩；
                 }
             //全系统
           //规模保费当日进度总合
                
                 sum[2] += Double.parseDouble(newresult[i][2]) ;
              // 规模保费当日累计综合    
                 sum[3] += Double.parseDouble(newresult[i][3]) ;
               //业绩当日进度总合
                 sum[4] += Double.parseDouble(newresult[i][4]);
               //业绩累计综合
                 sum[5] += Double.parseDouble(newresult[i][5]);
                //标准保费当日累计综合
                 sum[6] += Double.parseDouble(newresult[i][6]);
                 //标准保费累计综合
                 sum[7] += Double.parseDouble(newresult[i][7]);
                //标准件数累计综合
                 sum[8]+= Double.parseDouble(newresult[i][8]);
                //新契约件数累计综合
                 sum[9] += Double.parseDouble(newresult[i][9]);
                 
                 
             
          } 
             newresult[result.length][0]="全系统";
             newresult[result.length][1]="  ";
             for (int i=2;i<10;i++)
            	 
             {newresult[result.length][i]= String.valueOf(sum[i]);}
            if(fee==0.0 || sum[8]==0.0 || sum[9]==0.0){
            	newresult[result.length][10] ="-1";
            	newresult[result.length][11] ="-1";
            	newresult[result.length][12] ="-1";
            	
            }
            else {
            newresult[result.length][10]=  String.valueOf(Math.round(sum[3]/fee*100)/100.0);//件均保费
            newresult[result.length][11]=String.valueOf(Math.round(sum[7]/sum[8]*100)/100.0);//件均标保；
            newresult[result.length][12]= String.valueOf(Math.round(sum[5] /sum[9]*100)/100.0);//件均业绩；
            	
            	
            	
            }
            	
            	
             
             
             for(int i=0;i<newresult.length;i++)
             {
            	 for(int j=0;j<newresult[0].length;j++)
            	 {
            		 logger.debug("newresult["+i+"]["+j+"]"+newresult[i][j]);
            	 }
             }
             
	           TransferData tempTransferData=new TransferData();
	            //输入表头等信息
	            tempTransferData.setNameAndValue("&tdate",tdate);
	            tempTransferData.setNameAndValue("&operator", Tman);
	              String BZ="";
	            if(tBranchType.equals("4"))
	            {BZ="续期系列";}
	            else
	            {BZ="收展系列";}
	            tempTransferData.setNameAndValue("&branchtype", BZ);
	         

	            HashReport tHashReport = new HashReport();
	            String tpath = "";
	         LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	          tLDSysVarDB.setSysVar("XSCreatListPath");
	            if (!tLDSysVarDB.getInfo()) {
	              return false;
	            }
	            tpath = tLDSysVarDB.getSysVarValue();
	             String tFileName = "AdvancePrem" + "_" + tManageCom+"_"+tComLevel+"_"+tdate+"_"+tBranchType;	
	                       
	        //tpath = "D:\\TEMP\\";
	            tHashReport.outputArrayToFile1(tpath + tFileName + ".xls",
	            		      templatepath + "AdvancePrem.xls",
	                                         newresult, tempTransferData);


	           logger.debug("adddddddddddddd"); 
             
	
	    return true;
	  }
	  }



