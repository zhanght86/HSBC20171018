package com.sinosoft.lis.taskservice.taskinstance;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.audit.CheckSQL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;

public class AutoCheckSQL extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoCheckSQL.class);


	/**
	 * @param args
	 */
	public boolean dealMain()
	{
		/* 业务处理逻辑 */
		logger.debug("开始进行数据校验...");
//		CheckSQL tMsCheckSQL = new CheckSQL();
//		tMsCheckSQL.main();		

		// TODO Auto-generated method stub
		GlobalInput tG = new GlobalInput();  
		tG.Operator = "AUTO";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 
	    //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"开始进行数据校验");
	  	
		ExeSQL tExeSQL = new ExeSQL();
		 SSRS mSSRS = new SSRS();
		 String tCalCode = "";
		    String tCalSql = "";
	            String str ="select * from StatSqlConfig where ValiFlag = '1' order by calcode";
	            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	            sqlbv.sql(str);
	            mSSRS = tExeSQL.execSQL(sqlbv);
	            int trowsize = mSSRS.getMaxRow();
		    if (trowsize < 1) {
		        logger.debug("StatSqlConfig表中没有需要执行的检查语句！");
			    //日志监控,过程监控        
			  	PubFun.logTrack(tG,tG.LogID[1],"StatSqlConfig表中没有需要执行的检查语句");
	               return false;
		    }
		    
		Vector vectorlists = new Vector();
		for(int i=0;i<trowsize;i++)
		{
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("CalCode", mSSRS.GetText(i+1, 1));
			tTransferData.setNameAndValue("CalSql", mSSRS.GetText(i+1, 6));
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);
			vectorlists.add(tVData);
			
//			String[] temp = {mSSRS.GetText(i+1, 1),mSSRS.GetText(i+1, 6)};	
//			VData tVData = new VData();	
//			tVData.add(tG);
//			//vectorlists.add(tVData);
//			vectorlists.add(temp);
		}
		ServiceA tService = new ServiceA(
				"com.sinosoft.lis.audit.CheckSQL",
				vectorlists, 5,5);
		tService.start();
		
		 String iSQL ="insert into StatSqlConfigLog select calcode,ErrCount,remark,substr(now(),1,10),substr(now(),12,10) from StatSqlConfig ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
         sqlbv1.sql(iSQL);
		 if (!tExeSQL.execUpdateSQL(sqlbv1)) {
             logger.debug("插入日志表失败");
         }		
 		logger.debug("结束数据校验...");
 		  //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"结束数据校验");	  	
		return true;
	}	
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		CheckSQL tMsCheckSQL = new CheckSQL();
		logger.debug("before OtoFTask!");
		tMsCheckSQL.main();

	}

}
