package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class callLoanInvaliBLTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(callLoanInvaliBLTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public callLoanInvaliBLTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoPayPrem Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
    	//日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"保单借款失效批处理开始","3");
		PubFun.logTrack(tG,tG.LogID[1],"保单借款失效批处理启动");
		
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='callLoanInvaliBLTMCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("保单借款失效批处理取的最大的数tConutMax:" + tConutMax);
        SSRS tSSRS = null;
        String aPolNo = "";
        String aContNo = "";
        String mEndDate = "";
        String strsql = "";
		try {
			  //*********************************************//
	        //逐日判断“次日保单的现金价值是否大于贷款、自垫本息和”,这里不用筛选时间
	        strsql = "Select ContNo,PolNo from LCContState a"
	         //+ " where StateType='Loan' and State='1' and EndDate is null and contno = '86210020080219001125'"
	         + " where StateType='Loan' and State='1' and EndDate is null "
	         //add by jiaqiangli 2009-03-06 万能险借款不再走失效而是终止批处理 万能险已做批处理 此处排除万能险险种
	         + " and exists (select 1 from lcpol b where b.contno=a.contno and exists(select 1 from lmedorztduty c where b.riskcode=c.riskcode and c.paybyacc<>'1'))"
	         + " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = a.contno) "
	         //add by jiaqiangli 2009-11-05 对于批处理日期大于等于责任止期的不予借款失效处理 lis-9823
	         //应交未交失效与垫缴失效应该不会存在这样的问题，因为他们一定会处于“缴费期”内
	         + " and exists(select 'X' from lcpol where appflag = '1' and polno=mainpolno and '" + "?mCurrentDate?" + "' < EndDate and contno = a.contno) "
	         //add by jiaqiangli 2009-03-06 万能险借款不再走失效而是终止批处理 万能险已做批处理 此处排除万能险险种
	         + " and not exists ( select 'X' from LCContState where "
	         //+ " PolNo=a.PolNo and "
	         + " ContNo=a.ContNo "
	         + " and StateType='Available' "
	         + " and State='1' and EndDate is null)"
	         //+ " and rownum <= 100 "
	         ;  //不失效
	        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(strsql);
			sqlbv2.put("mCurrentDate", mCurrentDate);
	        logger.debug("strsql" + strsql);
	        try
	        {
	            tSSRS = new SSRS();
	            tExeSQL = new ExeSQL();
	            tSSRS = tExeSQL.execSQL(sqlbv2);
	        }
	        catch (Exception e)
	        {
	            // @@错误处理
	            CError.buildErr(this, "查询贷款的保单状态信息时产生错误!");
	            return false;
	        }
//			if (tSSRS == null ||tSSRS.MaxRow <= 0) {
//				return false;
//			}
	        VData tVData = new VData();
	        countJS=tSSRS.MaxRow;
	        if (tSSRS != null && tSSRS.MaxRow > 0)
	        {
	        	//有效止期为昨天，从今天开始失效
	        	mEndDate = PubFun.calDate(this.mCurrentDate, -1, "D", null);
	        	
	            for (int i = 1; i <= tSSRS.MaxRow; i++)
	            {
	              aContNo = tSSRS.GetText(i, 1);
	              aPolNo = tSSRS.GetText(i, 2);
//	           		 修改成公共的调用失效批处理
					LCPolSchema tLCPolSchema = new LCPolSchema();
	                LCPolDB tLCPolDB = new LCPolDB();
	                String tMainPolSQL = "select * from lcpol where contno='"+"?aContNo?"+"' and polno=mainpolno and appflag='1' ";
	                SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	    			sqlbv3.sql(tMainPolSQL);
	    			sqlbv3.put("aContNo", aContNo);
	                LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
	                if (tLCPolSet.size()>0) {
	                    tLCPolSchema.setSchema(tLCPolSet.get(1));
	                  
	                }

	                Map map = new HashMap();
					map.put("LCPolSchema", tLCPolSchema);
					map.put("InvalidationStateReason", BqCode.BQ_InvalidationStateReason_InGracePayPrem);
					map.put("GlobalInput", tG);
					tVData.add(map); 
	              if(i%tConutMax==0)
	              {
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
	              }
					
	              if(i%tConutMax!=0&&i==tSSRS.MaxRow)
	              {
						mTaskWaitList.add(tVData);
						tVData = new VData();
	              } 
	            }
	        }
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='callLoanInvaliBLTMThread'";
		 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_ThreadCount);
			sqlbv4.put("aContNo", aContNo);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv4);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (countJS < tThreadCount) {
			tThreadCount = countJS;
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.bq.ContInvaliBLTMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();

		logger.debug("结束业务逻辑处理!--保单借款失效批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	

        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "保单借款失效批处理完毕"+String.valueOf(countJS)+"笔,共花费"+tt+"秒");
		PubFun.logTrack(tG, tG.LogID[1], "保单借款失效批处理完毕");
	}catch (Exception ex) {
			ex.printStackTrace();
	} finally {
			mPubLock.unLock();				
	}
		return true;
	}

	public static void main(String[] args) {
		callLoanInvaliBLTMThread tcallLoanInvaliBLTMThread = new callLoanInvaliBLTMThread();
		tcallLoanInvaliBLTMThread.dealMain();
	}
}
