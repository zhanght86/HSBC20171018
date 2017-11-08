package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.operfee.IndiFinUrgeVerifyUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


import com.sinosoft.lis.reagent.*;

import java.sql.Connection;

/**
 * <p>Title: </p>
 * <p>Description: 核销自动运行程序</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author xiongzh 08-1-30
 * @version 1.0
 */

public class BQProgressTask extends TaskThread 
{
private static Logger logger = Logger.getLogger(BQProgressTask.class);



        public BQProgressTask() {
        }

        public CErrors mErrors = new CErrors();
        private ExeSQL mExeSQL = new ExeSQL();



        public boolean dealMain()
        {
            /*业务处理逻辑*/            
      
            String today = PubFun.getCurrentDate();
           /* Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
                                                改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription */
            //查询当前系统日期－柜面受理日期≥5个工作日 的保全项目
            String  tSql = " select a.EdorAcceptNo,b.ContNo,b.EdorState, b.EdorNo,b.Operator, a.ManageCom,b.EdorAppDate,a.EdorAppName, "
            			 + " (select edorname from lmedoritem where edorcode = b.edortype and appobj = 'I'),(case when (select codename from ldcode "
            			 + " where code = (select activityid from lwmission where missionprop2 = b.contno and activityid in ('0000000007', '0000000003', '0000000009', "
            			 + " '0000000001', '0000000005', '0000000006', '0000000100') and rownum = 1) and codetype = 'bqactivityname') is not null then (select codename from ldcode "
            			 + " where code = (select activityid from lwmission where missionprop2 = b.contno and activityid in ('0000000007', '0000000003', '0000000009', "
            			 + " '0000000001', '0000000005', '0000000006', '0000000100') and rownum = 1) and codetype = 'bqactivityname') else "
            			 + " (case (select count(*) from lwmission where missionprop1 = a.edoracceptno  and activityid = '0000000007') when 0 then "
            			 + " (case (select count(*) from lwmission  where missionprop1 = a.edoracceptno and activityid = '0000000005') when 0 then (select CodeName from LDCode "
            			 + " where codetype = 'edorstate'  and trim(code) = (b.edorstate)) else '人工核保中' end) else '审批中' end) end) "
                		 + " from LPEdorApp a, LPEdorItem b where a.EdorAcceptNo = b.EdorAcceptNo and b.EdorState in ('1', '2', '3', '5', 'a') and a.OtherNoType in ('1','3') "
                		 + " and (select count(*) from LDCalendar where CommonDate > b.makedate and commondate <= '"+"?today?"+"' and workdateflag = 'Y') >= 5 " 
                		 + " and not exists (select 1 from loprtmanager where StandbyFlag3 = b.EdorNo and code = 'BQ72' and otherno = b.ContNo )";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	tSql = " select a.EdorAcceptNo,b.ContNo,b.EdorState, b.EdorNo,b.Operator, a.ManageCom,b.EdorAppDate,a.EdorAppName, "
           			 + " (select edorname from lmedoritem where edorcode = b.edortype and appobj = 'I'),(case when (select codename from ldcode "
           			 + " where code = (select activityid from lwmission where missionprop2 = b.contno and activityid in ('0000000007', '0000000003', '0000000009', "
           			 + " '0000000001', '0000000005', '0000000006', '0000000100') limit 0,1) and codetype = 'bqactivityname') is not null then (select codename from ldcode "
           			 + " where code = (select activityid from lwmission where missionprop2 = b.contno and activityid in ('0000000007', '0000000003', '0000000009', "
           			 + " '0000000001', '0000000005', '0000000006', '0000000100') limit 0,1) and codetype = 'bqactivityname') else "
           			 + " (case (select count(*) from lwmission where missionprop1 = a.edoracceptno  and activityid = '0000000007') when 0 then "
           			 + " (case (select count(*) from lwmission  where missionprop1 = a.edoracceptno and activityid = '0000000005') when 0 then (select CodeName from LDCode "
           			 + " where codetype = 'edorstate'  and trim(code) = (b.edorstate)) else '人工核保中' end) else '审批中' end) end) "
               		 + " from LPEdorApp a, LPEdorItem b where a.EdorAcceptNo = b.EdorAcceptNo and b.EdorState in ('1', '2', '3', '5', 'a') and a.OtherNoType in ('1','3') "
               		 + " and (select count(*) from LDCalendar where CommonDate > b.makedate and commondate <= '"+"?today?"+"' and workdateflag = 'Y') >= 5 " 
               		 + " and not exists (select 1 from loprtmanager where StandbyFlag3 = b.EdorNo and code = 'BQ72' and otherno = b.ContNo )";
            }
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(tSql);
            sqlbv1.put("today", today);
            SSRS tSSRS = new SSRS();
            tSSRS = mExeSQL.execSQL(sqlbv1);
            for(int i=1;i<=tSSRS.MaxRow;i++)
            {
            	String tEdorAcceptNo = tSSRS.GetText(i, 1);
            	String tContNo = tSSRS.GetText(i, 2);
            	String tEdorState = tSSRS.GetText(i, 3);
            	String tEdorNo = tSSRS.GetText(i, 4);
            	String tOperator = tSSRS.GetText(i, 5);
            	String tManageCom = tSSRS.GetText(i, 6);
            	String tEdorAppDate = tSSRS.GetText(i, 7);
            	String tEdorAppName = tSSRS.GetText(i, 8);
            	String tEdorTypeName =  tSSRS.GetText(i, 9);
            	String tEdorStateName =  tSSRS.GetText(i, 10);
            	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
            	tLOPRTManagerSchema = new LOPRTManagerSchema();
    			String strNoLimit = PubFun.getNoLimit(tManageCom);
    			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
    			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
    			tLOPRTManagerSchema.setOtherNo(tContNo);
    			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
    			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorRemind); // 打印类型
    			tLOPRTManagerSchema.setManageCom(tManageCom); // 管理机构
    			tLOPRTManagerSchema.setAgentCode(""); // 代理人编码
    			tLOPRTManagerSchema.setReqCom(tManageCom);
    			tLOPRTManagerSchema.setReqOperator(tOperator);
    			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
    			tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
    			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
    			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
    			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
    			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
    			tLOPRTManagerSchema.setStandbyFlag1(tEdorAcceptNo);//受理号
    			tLOPRTManagerSchema.setStandbyFlag2(tEdorState);//保全状态
    			tLOPRTManagerSchema.setStandbyFlag3(tEdorNo);//批改号
    			tLOPRTManagerSchema.setStandbyFlag4(tEdorAppName);
    			tLOPRTManagerSchema.setStandbyFlag5(tEdorTypeName);
    			tLOPRTManagerSchema.setStandbyFlag6(tEdorStateName);
    			tLOPRTManagerSchema.setStandbyFlag7(tEdorAppDate);//受理时间
    			tLOPRTManagerSchema.setRemark("");
    			
    			VData tVData = new VData();
    			MMap tMap = new MMap();
    			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
    			tVData.add(tMap);
    			PubSubmit tSubmit = new PubSubmit();
    			if (!tSubmit.submitData(tVData, "")) {

    			}
            }    
            return true;
        }
        
        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
        	BQProgressTask tBQProgressTask = new BQProgressTask();
        	tBQProgressTask.dealMain();
        }
        

}
