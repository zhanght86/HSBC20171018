package com.sinosoft.lis.taskservice.taskinstance;

import java.util.Date;
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import org.apache.log4j.Logger;

public class UWAutoMThread extends TaskThread {
	private static Logger logger = Logger.getLogger(UWAutoMThread.class);

	private static int totalCount = 0;
	public CErrors mErrors = new CErrors();
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	public UWAutoMThread() {
	}

	public boolean dealMain() {
		try {
			if (!this.mPubLock.lock("0000001003", "LC3000")) {
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				return false;
			}
			/* 业务处理逻辑 */
			logger.debug("进入业务逻辑处理!--开始自动复核" + PubFun.getCurrentDate() + "**"
					+ PubFun.getCurrentTime());

			GlobalInput tG = new GlobalInput();
			tG.ComCode = "86";
			tG.Operator = "AUTO";
			tG.ManageCom = "86";
			
			 //日志监控,初始化           	
			tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
			tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
		
             //日志监控,过程监控			
    		PubFun.logTrack(tG, tG.LogID[1], "自动核保批处理业务处理开始");


        	//启动时间        	
        	Date  dNow =  new Date();         	
        	long initTime =dNow.getTime();    
        	
			
			//初始化抽检数 
			this.gettotalCount();
			mGlobalCheckSpot.initCheckNum(totalCount);

			
			// 每次最大取数量,默认是100条
			int tConutMax = 100;
			String tResult = "";
			String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='AutoUWMaxCount' ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Count);
			ExeSQL tExeSQL = new ExeSQL();
			tResult = tExeSQL.getOneValue(sqlbv1);
			// 如果有描述的话,取描述的数据
			if (tResult != null && !tResult.equals("")) {
				tConutMax = Integer.parseInt(tResult);
			}
			logger.debug("自动核保取的最大的数tConutMax:" + tConutMax);

			String tSQL = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp3,lwmission.MissionProp4, "
					+ " lwmission.MissionProp5,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID "
//					+ " from lwmission where '1198561475000'='1198561475000' and LWMission.ProcessID = '0000000003'  "
//					+ " and LWMission.ActivityID = '0000001003'  and MissionProp5 like '86%' ";
					+ " from lwmission where '1198561475000'='1198561475000'"
					+ " and LWMission.ActivityID in (select activityid from lwactivity  where functionid ='10010005') and MissionProp5 like '86%' ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			Vector mTaskWaitList = new Vector();
			// 暂时定为一次取1000条
			tSSRS = tExeSQL.execSQL(sqlbv2, 1, tConutMax);
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				
				System.out
						.println("--------------------------------------------------------------------------");
				String tContNo = tSSRS.GetText(i, 1);
				String tMissionID = tSSRS.GetText(i, 6);
				String tSubMissionID = tSSRS.GetText(i, 7);
				String tActivityID = tSSRS.GetText(i, 8);
				logger.debug("----自动核保--正在处理第[" + i + "]个合同，合同号为:" + tContNo);
				
//				gettotalCount();

				TransferData tTransferData = new TransferData();
				if (!tContNo.equals("")) {

					tTransferData.setNameAndValue("ContNo", tContNo);

					tTransferData.setNameAndValue("MissionID", tMissionID);
					tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
					tTransferData.setNameAndValue("ActivityID", tActivityID);
					tTransferData.setNameAndValue("ContNo", tContNo);
//					tTransferData.setNameAndValue("totalCount", totalCount);
					logger.debug("Missionid:" + tMissionID);
					logger.debug("SubMissionID:" + tSubMissionID);
					logger.debug("ActivityID:" + tActivityID);

					 // 准备传输数据 VData
					VData tVData = new VData();
					tVData.add(tTransferData);
					tVData.add(tG);
					mTaskWaitList.add(tVData);
				}
			}
			
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue  else '0' end) from ldsysvar where sysvar ='AutoUWThreadCount'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL_ThreadCount);
			String tAutoUWThreadCount = tExeSQL.getOneValue(sqlbv3);
			if (tAutoUWThreadCount != null && !"".equals(tAutoUWThreadCount)
					&& Integer.parseInt(tAutoUWThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tAutoUWThreadCount);
			}
			if(mTaskWaitList.size()<tThreadCount)
			{
				tThreadCount = mTaskWaitList.size();
			}

			ServiceA tService = new ServiceA("com.sinosoft.workflow.tb.TbWorkFlowMthread", mTaskWaitList, tThreadCount, 10);
			tService.start();

			logger.debug("结束业务逻辑处理!--自动复核" + PubFun.getCurrentDate() + "**"
					+ PubFun.getCurrentTime());			

    	     //日志监控,过程监控
    		PubFun.logTrack(tG, tG.LogID[1], "自动核保批处理业务处理完毕");
            //完成时间
        	Date  dend = new Date();         	
        	long  endTime = dend.getTime(); 
        	long tt=(endTime-initTime)/1000; 
        	//日志监控,处理结果      
        	PubFun.logResult(tG,tG.LogID[1], "本次共自动核保投保单"+tSSRS.getMaxRow()+"笔,共花费"+tt+"秒");
        	
			updateLDsysvar();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			this.mPubLock.unLock();
		}
	}

	/**
	 * 查询已抽检件数
	 * */
	private void gettotalCount(){
		try {
			String tSql = "select sysvarvalue,remark from ldsysvar where sysvar='UwSampling'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSql);			
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			String tDate = tSSRS.GetText(1, 2);
			String tCurrentDate = PubFun.getCurrentDate();
			if(0!=PubFun.calInterval(tDate, tCurrentDate, "D")){
				totalCount = 0;
			} else {
				int tCurrentCount = Integer.parseInt(tSSRS.GetText(1, 1));
//				tCurrentCount = tCurrentCount+1;
				totalCount = tCurrentCount;
			}
			String updateSql = "update ldsysvar set sysvarvalue='"+"?totalCount?"+"' , "
							+ " remark='"+"?tCurrentDate?"+"' where sysvar='UwSampling'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(updateSql);
			sqlbv5.put("totalCount", totalCount);
			sqlbv5.put("tCurrentDate", tCurrentDate);
			tExeSQL.execUpdateSQL(sqlbv5);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			CError.buildErr(this, "执行查询已抽检件数时出错！");
		}
	}
	
	/**
	 * 修改自核已抽检数
	 * */
	synchronized private void updateLDsysvar(){
		String tCurrentDate = PubFun.getCurrentDate();
		totalCount = mGlobalCheckSpot.getCurrentCheckNum();
		String updateSql = "update ldsysvar set sysvarvalue='"+"?totalCount?"+"', "
							+ " remark='"+"?tCurrentDate?"+"' where sysvar='UwSampling'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(updateSql);
		sqlbv6.put("totalCount", totalCount);
		sqlbv6.put("tCurrentDate", tCurrentDate);
		ExeSQL tExeSQL = new ExeSQL();
		tExeSQL.execUpdateSQL(sqlbv6);
		
	}
	
	public static void main(String[] args) {
		UWAutoMThread tUWAutoMThread = new UWAutoMThread();
		tUWAutoMThread.dealMain();
	}
}