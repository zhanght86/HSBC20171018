package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;
/**
  *
  * 续期续保抽档批处理 -入口
  *
**/
public class RnewPolAutoTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private static Logger logger = Logger.getLogger(RnewPolAutoTMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();
	public RnewPolAutoTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--续期续保抽档批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	  	PubFun.logPerformance (tG,tG.LogID[1],"续期续保抽档批处理开始","1");
//		日志监控，性能监控
    	PubFun.logTrack(tG,tG.LogID[1],"续期续保抽档批处理启动");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='RnewPolAutoCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
 		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("续期续保自动催收开始：RnewPolAuto...");

		String AheadDays = ""; // 催收提前天数--默认60天
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("aheaddays");
		if (tLDSysVarDB.getInfo() == false) {
			AheadDays = "60";
		} else {
			AheadDays = tLDSysVarDB.getSysVarValue();
		}

		LDSysVarDB xLDSysVarDB = new LDSysVarDB();
		xLDSysVarDB.setSysVar("ExtendLapseDates");
		String ExtendDays ="";
		if (xLDSysVarDB.getInfo() == false) 
		{
		  ExtendDays = "0";//默认为0天
		} 
		else 
		{
		  ExtendDays = xLDSysVarDB.getSysVarValue();
		}
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", PubFun.calDate(PubFun
				.getCurrentDate(), -(Integer.parseInt(AheadDays) + Integer.parseInt(ExtendDays)), "D",
				null));
		tTransferData.setNameAndValue("EndDate", PubFun.calDate(PubFun
				.getCurrentDate(), Integer.parseInt(AheadDays), "D", null));
		logger.debug("StartDate"
				+ tTransferData.getValueByName("StartDate"));
		logger.debug("EndDate" + tTransferData.getValueByName("EndDate"));

//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.Operator = "001";
//		tGlobalInput.ComCode = "86";
//		tGlobalInput.ManageCom = "86";
    	tVData.add(tG);
//		VData tVData = new VData();
		tVData.add(tTransferData);
		
//		 查询所有符合抽档条件的所有保单
		IndiDueFeePartQuery tIndiDueFeePartQuery = new IndiDueFeePartQuery();
		//LCContSet tLCContSet = new LCContSet();		
		String[][] xResult ;
		if (!tIndiDueFeePartQuery.submitData(tVData, "ZC")) {
			this.mErrors.copyAllErrors(tIndiDueFeePartQuery.mErrors);
			return false;
		} else {
			VData hVData = tIndiDueFeePartQuery.getResult();
			xResult = (String[][]) hVData.get(0);
		}
		int ErrCount = 0;
		int xLength = xResult.length;
		if (xLength<=0)
        {
        	return false;
        }
			// 加锁contno，LR0001：续期催收
		String LockNo = "";
		try {
				// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
				VData mVData = new VData();
				for (int i = 1; i <= xLength; i++) 
				{
		
//					先加锁
					logger.debug(i);
					logger.debug("当前处理contno:"+xResult[i-1][0]);
					LockNo = "";
					LockNo= xResult[i-1][0];
					// if (!mPubLock.lock(LockNo, "LR0001", tGI.Operator))
					// {
					// CError tError = new CError(mPubLock.mErrors.getLastError());
					// this.mErrors.addOneError(tError);
					// continue;
					// }
					MMap tMap = new MMap();
					RnDealBL tRnDealBL = new RnDealBL();
					LCContSchema tLCContSchema = new LCContSchema();
					LCContDB xLCContDB = new LCContDB();
					xLCContDB.setContNo(xResult[i-1][0]);
					if(!xLCContDB.getInfo())
					{
						this.mErrors.addOneError("未找到保单"+xResult[i-1][0]+"的信息");
//						日志监控,状态监控          
		  		    	PubFun.logState(tG,xResult[i-1][0],"未找到保单"+xResult[i-1][0]+"的信息","0");	  		    	
						ErrCount++;
						continue;
					}
					tLCContSchema=xLCContDB.getSchema();
					

	            	Map map = new HashMap();
					map.put("LCContSchema", tLCContSchema);
					map.put("StartDate",(String)tTransferData.getValueByName("StartDate"));
					map.put("EndDate",(String)tTransferData.getValueByName("EndDate"));
					map.put("TransferData", tTransferData);
					map.put("GlobalInput", tG);
					
					mVData.add(map);
					
			     	if(i%tConutMax==0)
					{
	            		mTaskWaitList.add(mVData);
	            		mVData = new VData();
					}
					
					if(i%tConutMax!=0&&i==xLength)
					{
						mTaskWaitList.add(mVData);
						mVData = new VData();
					} 
				}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} finally {
			mPubLock.unLock();// 解锁
		}
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='RnewPolAutoThread'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
 		sqlbv2.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv2);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (xLength < tThreadCount) {
			tThreadCount =xLength;
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.operfee.RnDealBLMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();


        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期续保抽档批处理"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
	  	
		return true;
	}
	
    /**
     * 获取各种保全逾期终止批处理的逾期天数
     */
    private String getEdorTypeTimeoutDays(String sEdorType)
    {
        String sResultTimeoutDays = new String("");

        //查询逾期天数计算代码
        String QuerySQL = "select * from LMEdorCal where CalType = '" + "?sEdorType?" + "'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
 		sqlbv3.sql(QuerySQL);
 		sqlbv3.put("sEdorType", sEdorType);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
        try
        {
            tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv3);
        }
        catch (Exception ex)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 执行 SQL 查询出错");
            logger.debug("\t   错误原因 : " + tLMEdorCalDB.mErrors.getFirstError());
            return null;
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 未知的保全类型！ 查询逾期天数失败！");
            return null;
        }
        else
        {
            String sTempCalCode = tLMEdorCalSet.get(1).getCalCode();
            if (sTempCalCode == null || sTempCalCode.trim().equals(""))
            {
                logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 查询逾期天数计算代码失败！");
                return null;
            }
            else
            {
                //根据计算代码计算逾期天数
                Calculator tCalculator = new Calculator();
                tCalculator.setCalCode(sTempCalCode);
                sResultTimeoutDays = tCalculator.calculate();
                if (sResultTimeoutDays == null || sResultTimeoutDays.trim().equals(""))
                {
                    logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 根据计算代码计算逾期天数失败！");
                    return null;
                }
                tCalculator = null;
            }
        }
        tLMEdorCalDB = null;
        tLMEdorCalSet = null;

        return sResultTimeoutDays;
    } //function getEdorTypeTimeoutDays end

    //==========================================================================

	public static void main(String[] args) {
		RnewPolAutoTMThread tRnewPolAutoTMThread = new RnewPolAutoTMThread();
		tRnewPolAutoTMThread.dealMain();
	}
}
