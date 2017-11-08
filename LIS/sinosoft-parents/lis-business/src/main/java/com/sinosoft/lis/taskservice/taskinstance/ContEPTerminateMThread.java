package com.sinosoft.lis.taskservice.taskinstance;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.ContInvaliBLMultThreads;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ContEPTerminateMThread extends TaskThread {
private static Logger logger = Logger.getLogger(ContEPTerminateMThread.class);
//全局变量
private GlobalInput mGlobalInput = new GlobalInput();
	public CErrors mErrors = new CErrors();

	public ContEPTerminateMThread() {
	}
	
	public boolean dealMain(VData aVData) {		
		mGlobalInput=(GlobalInput) aVData.getObjectByObjectName(
				"GlobalInput", 0);	  
		logger.debug("start to terminate");		
		//ContInvaliBLMultThreads.main(null);		
		String tComSQL = "select comcode from ldcom where char_length(comcode) = 8 order by comcode";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tComSQL);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = tExeSQL.execSQL(sqlbv);
		Vector mTaskWaitList = new Vector();
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ManageCom", tssrs.GetText(i, 1));		
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(mGlobalInput);
			mTaskWaitList.add(tVData);
		}
		logger.debug(mTaskWaitList.size());
		ServiceA tService = new ServiceA("com.sinosoft.lis.bq.ContInvaliBLMultThreads", mTaskWaitList, 50, 10);
		tService.start();		
		logger.debug("end to terminate");
		return true;
	}


	public boolean dealMain() {
		
		logger.debug("start to terminate");
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";
		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 		
		//ContInvaliBLMultThreads.main(null);		
		String tComSQL = "select comcode from ldcom where char_length(comcode) = 8 order by comcode";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tComSQL);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = tExeSQL.execSQL(sqlbv);
		Vector mTaskWaitList = new Vector();
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ManageCom", tssrs.GetText(i, 1));		
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);
			mTaskWaitList.add(tVData);
		}
		logger.debug(mTaskWaitList.size());
		ServiceA tService = new ServiceA("com.sinosoft.lis.bq.ContInvaliBLMultThreads", mTaskWaitList, 50, 10);
		tService.start();		
		logger.debug("end to terminate");
		return true;
	}

	public static void main(String[] args) {
	}
}
