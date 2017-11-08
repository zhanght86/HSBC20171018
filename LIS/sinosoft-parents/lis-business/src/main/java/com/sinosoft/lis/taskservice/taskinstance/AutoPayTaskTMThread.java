package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.BqNameFun;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
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

public class AutoPayTaskTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(ProIndSignMThread.class);
	public CErrors mErrors = new CErrors();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	int XCount=0;
	public AutoPayTaskTMThread() {
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
		PubFun.logPerformance (tG,tG.LogID[1],"自动垫交批处理开始","1");
		PubFun.logTrack(tG,tG.LogID[1],"自动垫交批处理启动");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='AutoPayTaskTMCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("自动垫交个单取的最大的数tConutMax:" + tConutMax);

		try {
			logger.debug("参数个数:" + mParameters.size());

			// 保单机构
			String tManageCom = (String) mParameters.get("ManageCom");
			logger.debug("The manageCom is :" + tManageCom);
			// 进行自垫批处理的时间
			String tCalDate = (String) mParameters.get("CalDate");
			logger.debug("The CalDate is :" + tCalDate);
			// 保单号
			String tContNo = (String) mParameters.get("ContNo");
			logger.debug("The ContNo is :" + tContNo);

			if (tManageCom == null || "".equals(tManageCom)) {
				tManageCom = "86";
			}

			if (tCalDate == null || "".equals(tCalDate)) {
				tCalDate = PubFun.getCurrentDate();
			}
		
            // 查询逻辑由保单，变为续期催收数据
			String tSql =" SELECT * " 
				+" FROM ljspay a "
				  +" WHERE 1 = 1 "
					+" and exists (select 'X' "
									+ " from LCPol b "
								    + " where b.ContNo = a.otherno "
									+ " and AppFlag = '1' "
									+ " and exists (select 'C' from lccont where contno = b.contno and AutoPayFlag = '1') and polno=mainpolno and "
									+" exists (select 'A' from LMRiskApp where RiskCode=b.RiskCode and AutoPayFlag = '1')) "
					+" and a.ManageCom like concat('"+"?tManageCom?"+"','%') "
					+ BqNameFun.getWherePart("a.otherno", BqNameFun.getParameterStr(tContNo, "?tContNo?"))
				 	+" and not exists "
				    +" (select 'X' from lcconthangupstate d where d.ContNo = a.otherno) "
					+ " and exists( select 'Y'"
					+ " from LJSPayPerson c"
					+ " where "
					+" c.GetNoticeNo=a.GetNoticeNo"
					+ " and c.PayType='ZC'"
					+ " )" //排除失效的记录
					+ " and not exists (select 'B' from LCContState where  contno=a.otherno and StateType='Available' and State='1' and EndDate is null)"
					+" and a.othernotype = '2' "
					+" and a.bankonthewayflag <> '1' "
					//宽末期当前不允许自垫
					+" and a.paydate < to_date('"+"?tCalDate?"+"', 'YYYY-MM-DD') "
					+" and not exists (select 'Z' "
									+" from LJTempFee "
								  +" where TempFeeNo = a.GetNoticeNo "
									+" and TempFeeType = '2') " ; 
			// 可以确定处于保全挂起的不要进行自垫
			logger.debug(tSql);
			LJSPaySet tLJSPaySet = new LJSPaySet();
			RSWrapper rsWrapper = new RSWrapper();
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("tManageCom", tManageCom);
			sqlbv2.put("tContNo",tContNo );
			sqlbv2.put("tCalDate", tCalDate);
			// 采取长连接的方式
			if (!rsWrapper.prepareData(tLJSPaySet, sqlbv2)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}
			do {
			rsWrapper.getData();
//			if (tLJSPaySet == null || tLJSPaySet.size() <= 0) {
//					return false;
//			}
			String LockNo="";
			countJS=tLJSPaySet.size();
			VData tVData = new VData();
			for (int i = 1; i <= tLJSPaySet.size(); i++) {
					XCount++;
					LockNo = "";
					LockNo= tLJSPaySet.get(i).getOtherNo();
					if (!mPubLock.lock(LockNo, "LI0001", tG.Operator)) 
					{
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						continue;
					}
										
					// 数据准备操作
					LCContSet tLCContSet = new LCContSet();
					LCContDB tLCContDB = new LCContDB();
					LCContSchema tLCContSchema = new LCContSchema();
					tLCContDB.setContNo(tLJSPaySet.get(i).getOtherNo());
					tLCContDB.setAppFlag("1");
					tLCContSet=tLCContDB.query();
					if(tLCContSet.size()<1 || tLCContSet==null)
					{
						continue;
					}else
					{
					   tLCContSchema = tLCContSet.get(1);
					}
					
	            	Map map = new HashMap();
					map.put("LCContSchema", tLCContSchema);
					map.put("CalDate", tCalDate);
					map.put("GlobalInput", tG);
					
					tVData.add(map);

			     	if(i%tConutMax==0)
					{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
					}
					
					if(i%tConutMax!=0&&i==tLJSPaySet.size())
					{
						mTaskWaitList.add(tVData);
						tVData = new VData();
					} 

			}
		
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='AutoPayTaskTMThread'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (countJS < tThreadCount) {
			tThreadCount = countJS;
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.autopay.AutoPayBLMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
		} while (tLJSPaySet != null && tLJSPaySet.size() > 0);
			rsWrapper.close();
	     }catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		logger.debug("结束业务逻辑处理!--自动垫交批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "自动垫交批处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "自动垫交批处理"+String.valueOf(XCount)+"笔,共花费"+tt+"秒");

		return true;
	}

	public static void main(String[] args) {
		AutoPayTaskTMThread tAutoPayTaskTMThread = new AutoPayTaskTMThread();
		tAutoPayTaskTMThread.dealMain();
	}
}
