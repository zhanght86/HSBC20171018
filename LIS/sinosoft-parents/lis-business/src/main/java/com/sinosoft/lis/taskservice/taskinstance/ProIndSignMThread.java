package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ProIndSignMThread extends TaskThread {
private static Logger logger = Logger.getLogger(ProIndSignMThread.class);
	public CErrors mErrors = new CErrors();

	public ProIndSignMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--开始自动签发个单" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
        //日志监控,过程监控
		PubFun.logTrack(tG, tG.LogID[1], "自动签单批处理业务处理开始");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='AutoSignMaxCount' ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		logger.debug("自动签发个单取的最大的数tConutMax:" + tConutMax);

		String tSQL = "select lwmission.MissionProp1,"
				+ " lwmission.MissionProp2, "
				+ " lwmission.MissionProp5, "
				+ " lwmission.MissionProp6, "
				+ " lwmission.MissionProp7, "
				+ " LWMission.MissionID, "
				+ " LWMission.SubMissionID, "
				+ " LWMission.missionprop3,LWMission.activityid "
				+ " from lwmission "
//				+ " where '1198561475000'='1198561475000' and LWMission.ProcessID = '0000000003'  "
//				+ " and LWMission.ActivityID = '0000001150'  and MissionProp7 like '86%' ";
				+ " where '1198561475000'='1198561475000' "
				+ " and LWMission.ActivityID in (select activityid from lwactivity  where functionid ='10010042') and MissionProp7 like '86%' ";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	 		sqlbv2.sql(tSQL);
		tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		// 暂时定为一次取1000条
		logger.debug("查询签发个单SQL:" + tSQL);
		// tongmeng 2008-01-16 modify
		// 暂时不做数量限制
		// tSSRS = tExeSQL.execSQL(tSQL, 1, tConutMax);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		logger.debug("本次需要签发的保单的数量:" + tSSRS.getMaxRow());
		Vector mTaskWaitList = new Vector();
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			boolean flag = true;
			LCContSet tLCContSet = new LCContSet();
			String Content = "";
			logger.debug("--------------------------------------------------------------------------");
			String tContNo = tSSRS.GetText(i, 1);
			String tPrtNo = tSSRS.GetText(i, 2);
			String tMissionID = tSSRS.GetText(i, 6);
			String tSubMissionID = tSSRS.GetText(i, 7);
			String tActivityID = tSSRS.GetText(i, 9);
			logger.debug("----自动签发个单--正在处理第[" + i + "]个合同，合同号为:"
					+ tContNo);

			TransferData tTransferData = new TransferData();
			if (!tContNo.equals("")) {

				LCContSchema tLCContSchema = new LCContSchema();
				logger.debug("select contno:" + tContNo);
				tLCContSchema.setContNo(tContNo);
				tLCContSchema.setPrtNo(tPrtNo);
				tLCContSet.clear();
				tLCContSet.add(tLCContSchema);
				tTransferData.setNameAndValue("MissionID", tMissionID);
				tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
				tTransferData.setNameAndValue("ContNo", tContNo);

				tTransferData.setNameAndValue("ActivityID", tActivityID);

				logger.debug("Missionid:" + tMissionID);
				logger.debug("SubMissionID:" + tSubMissionID);
				logger.debug("ActivityID:" + tActivityID);

				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add(tG);
				tVData.add(tLCContSet);
				tVData.add(tTransferData);
				mTaskWaitList.add(tVData);
			}
		}
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='SignMThreadCount'";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	 		sqlbv3.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (mTaskWaitList.size() < tThreadCount) {
			tThreadCount = mTaskWaitList.size();
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.workflow.tb.TbWorkFlowMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();

		logger.debug("结束业务逻辑处理!--自动签发个单" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "自动签单批处理业务处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "本次共自动签单"+tSSRS.getMaxRow()+"笔,共花费"+tt+"秒");

		return true;
	}

	public static void main(String[] args) {
		ProIndSignMThread tProIndSignMThread = new ProIndSignMThread();
		tProIndSignMThread.dealMain();
	}
}
