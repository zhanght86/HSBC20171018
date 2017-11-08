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
import com.sinosoft.lis.schema.LJTempFeeSchema;
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
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;

public class RnewAutoVerifyTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(RnewAutoVerifyTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mManageCom = "";
	private String tContNo ="";
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public RnewAutoVerifyTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  RnewAutoVerifyTMThread Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
    	//日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"续期续保核销自动运行程序开始","1");
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='RnewAutoVerifyTMCount' ";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("续期核销自动运行程序个单取的最大的数tConutMax:" + tConutMax);
		try {
			logger.debug("current thread"+this+"hashcode");
			logger.debug("start to mManageCom"+mManageCom+"-"+PubFun.getCurrentDate()+":"+PubFun.getCurrentTime());
			logger.debug("callTerminate 续期核销自动运行程序程序运行开始。。。。");
			 //实现查询出需要核销的保单号集合
            String Data_sql="";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            Data_sql = "select  /*+index(ljspay ,IDX_LJSPAY_5)*/ otherno from ljspay where StartPayDate <= now() "
	          +" and othernotype = '2'  and sumduepaymoney > 0 "
	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
	          +" and exists (select 1 from ljtempfee where tempfeeno = ljspay.getnoticeno and tempfeetype = '2' "
	          +"  and othernotype = '0' and (case when ConfFlag is not null then ConfFlag else '0' end) = '0'  and EnterAccDate is not null and makedate <= '"+"?PubFun?"+"') "
	          //+"  and not exists "
	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
	          +"  and not exists "
	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
	          +" union "
	          + "select  /*+index(ljspay ,IDX_LJSPAY_5)*/ otherno from ljspay where StartPayDate <= now() "
	          +" and othernotype = '2'  and sumduepaymoney = 0 "
	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
	          //+"  and not exists "
	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
	          +"  and not exists "
	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
	          ;
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	 Data_sql = "select otherno from ljspay where StartPayDate <= now() "
           	          +" and othernotype = '2'  and sumduepaymoney > 0 "
           	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
           	          +" and exists (select 1 from ljtempfee where tempfeeno = ljspay.getnoticeno and tempfeetype = '2' "
           	          +"  and othernotype = '0' and (case when ConfFlag is not null then ConfFlag else '0' end) = '0'  and EnterAccDate is not null and makedate <= '"+"?PubFun?"+"') "
           	          //+"  and not exists "
           	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
           	          +"  and not exists "
           	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
           	          +" union "
           	          + "select otherno from ljspay where StartPayDate <= now() "
           	          +" and othernotype = '2'  and sumduepaymoney = 0 "
           	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
           	          //+"  and not exists "
           	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
           	          +"  and not exists "
           	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
           	          ;	
            }
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	 		sqlbv2.sql(Data_sql);
	 		sqlbv2.put("PubFun", PubFun.getCurrentDate());
            SSRS data_ssrs = new SSRS();
            data_ssrs = tExeSQL.execSQL(sqlbv2);
            int suc=0,fail=0;
//            if (data_ssrs == null ||data_ssrs.MaxRow <= 0) {
//				return false;
//			}
        	VData tVData = new VData();
        	countJS=data_ssrs.MaxRow;
            for(int i=1;i<=data_ssrs.MaxRow;i++)
            {
            	String Content = "";
            	tContNo = "";
            	tContNo = data_ssrs.GetText(i, 1);
            	
            	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
            	tLJTempFeeSchema.setOtherNo(tContNo);
                
               	Map map = new HashMap();
				map.put("LJTempFeeSchema", tLJTempFeeSchema);
				map.put("GlobalInput", tG);
				tVData.add(map);
            	
			    if(i%tConutMax==0)
				{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
				}
					
				if(i%tConutMax!=0&&i==data_ssrs.MaxRow)
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
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='RnewAutoVerifyTMThread'";
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
				"com.sinosoft.lis.operfee.IndiFinUrgeVerifyUITMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();

		logger.debug("结束业务逻辑处理!--续期续保核销自动运行程序" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "续期续保核销自动运行程序完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "续期续保核销自动运行处理"+String.valueOf(countJS)+"笔,共花费"+tt+"秒");
	}catch (Exception ex) {
			ex.printStackTrace();
	} finally {
			mPubLock.unLock();				
	}
		return true;
	}

	public static void main(String[] args) {
		RnewAutoVerifyTMThread tRnewAutoVerifyTMThread = new RnewAutoVerifyTMThread();
		tRnewAutoVerifyTMThread.dealMain();
	}
}
