package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
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

public class callInvaliBLTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(callInvaliBLTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private int XCount=0;
	public callInvaliBLTMThread() {
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
		PubFun.logPerformance (tG,tG.LogID[1],"续期未交失效批处理启动","1");
		
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='callInvaliBLTMCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
	
		logger.debug("续期未交失效批处理个单取的最大的数tConutMax:" + tConutMax);
		try {
				String strSql = "select * from lcpol a where 1 = 1  "
			    //comment by jiaqiangli 2008-10-17 MS的失效处理支持到垫交日后失效
				+ " and not exists(select 'X' from lccontstate b where a.contno=b.contno and b.statetype in ('Available','PH') and b.state = '1' and  b.enddate is null and b.polno = a.polno ) "
				+ " and payintv <> 0 and payintv <> -1 "
			  //+ // 115险种不参与失效处理
//				" and exists(select 'X' from ljspayperson where polno = a.polno "
//				+ subSql
//				+ " ) "
				//add by jiaqiangli 2009-03-20 失效取数逻辑只取个单
				+ " and conttype = '1' and appflag = '1'  "
				+ " and polno = mainpolno  "
				//add by jiaqiang 2009-11-12 由于这里是非垫缴保单的应交未交失效，将垫缴的排除之外而不是通过批处理的先后执行顺序来控制
				//也就是说让主险垫缴(包含lcpol层与产品定义层)的走垫缴的那个自动运行批处理 否则lccontstate.statereason不对应
				//+ " and not exists (select 1 from lcpol b where autopayflag='1' and exists (select 1 from LMRiskApp where RiskCode=b.RiskCode and AutoPayFlag='1') and polno=a.polno) "
				/*"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
				改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "
*/
				+ " and (case (select autopayflag from LMRiskApp where RiskCode = a.RiskCode) when '1' then (case when (select autopayflag from lccont where contno = a.contno) is not null then (select autopayflag from lccont where contno = a.contno) else '0' end) else '0' end) <> '1' "
				
				+"and contno = '86110020160210001402'"
				
				//addd by jiaqiangli 2009-03-06 加上and paytodate<payenddate
				+ " and paytodate<payenddate and paytodate < '"
				+ "?mCurrentDate?"
				+ "' "
				//add by jiaqiangli 2009-03-06 上面的逻辑里以主险进行判断 长期性附加险失效，短期附加险终止
				
//				+ " union "
//				+ " select * from lcpol c where 1 = 1 "
//				+ " and polno <> mainpolno "
//				+ " and exists(select 'X' from lcpol where polno = mainpolno and payintv = '0' and appflag = '1' and polno = c.mainpolno "
//				+ subSql
//				+ " ) "
////				+ " and exists(select 'X' from ljspayperson where polno = c.polno "
////				+ subSql
////				+ " ) "
//				+ " and appflag = '1' "
//				//addd by jiaqiangli 2009-03-06 加上and paytodate<payenddate
//				+ " and paytodate<payenddate and paytodate < '"
//				+ mCurrentDate + "' " + subSql
				//+ "and contno = '86130020060210053846' and rownum <= 100 "
				;
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(strSql);
				sqlbv2.put("mCurrentDate", mCurrentDate);
		LCPolSet tLCPolSet = new LCPolSet();
		RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
		if (!rsWrapper.prepareData(tLCPolSet, sqlbv2)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
		rsWrapper.getData();
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			return false;
		}
		VData tVData = new VData();
		countJS=tLCPolSet.size();
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) 
			{
				// 逐条调用保单失效处理
				 XCount++;
            	Map map = new HashMap();
				map.put("LCPolSchema", tLCPolSet.get(i));
				map.put("InvalidationStateReason", BqCode.BQ_InvalidationStateReason_Nomal);
				map.put("GlobalInput", tG);
				
				tVData.add(map);
				
			    if(i%tConutMax==0)
				{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
				}
					
				if(i%tConutMax!=0&&i==tLCPolSet.size())
				{
						mTaskWaitList.add(tVData);
						tVData = new VData();
				} 
			}
		}
		String TaskCode = (String)this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='callInvaliBLTMThread'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_ThreadCount);
		sqlbv3.put("mCurrentDate", mCurrentDate);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
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
		mPubLock.unLock();
		 } while (tLCPolSet != null && tLCPolSet.size() > 0);
		rsWrapper.close();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 
		logger.debug("结束业务逻辑处理!--续期未交失效批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "续期未交失效批处理"+String.valueOf(countJS)+"笔,共花费"+tt+"秒");
		PubFun.logTrack(tG, tG.LogID[1], "续期未交失效批处理完毕");
		logger.debug("sss");
		return true;
	}

	public static void main(String[] args) {
		callInvaliBLTMThread tcallInvaliBLTMThread = new callInvaliBLTMThread();
		tcallInvaliBLTMThread.dealMain();
	}
}
