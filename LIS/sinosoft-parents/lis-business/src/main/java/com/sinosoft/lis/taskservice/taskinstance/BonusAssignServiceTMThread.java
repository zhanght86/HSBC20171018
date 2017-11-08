package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.get.BonusAssignManuBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
public class BonusAssignServiceTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private static Logger logger = Logger.getLogger(BonusAssignServiceTMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private  int tSuc = 0, tFail = 0;
    private String mCurrentDate = PubFun.getCurrentDate();
	/**红利分配组号*/
	private String tGroupID;
	private String tCurMakeDate = PubFun.getCurrentDate();
	private String 	tCom;
	private int XCount=0;
	public BonusAssignServiceTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--红利分配批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
		PubFun.logPerformance (tG,tG.LogID[1],"红利分配批处理开始","1");
//		日志监控，性能监控
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='BonusAssignServiceTMCount' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("红利分配批处理开始：RnewPolAuto...");

//		处理的机构
//        tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");

        //分红年度
        String tFiscalYear = (String)mParameters.get("FiscalYear");
        tFiscalYear="2012";
        logger.debug("The CalDate is :"+tFiscalYear);
        
		// 红利分配保单号
		String tContNo = (String) mParameters.get("ContNo");
		logger.debug("The ContNo is :" + tContNo);
		
        //如果会计年度录入直接将其返回
        if(tFiscalYear==null || "".equals(tFiscalYear))
        {        	
        	return false;
        }
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FiscalYear", tFiscalYear);
		tTransferData.setNameAndValue("ContNo", tContNo);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
        String tComSQL = "";
		if (!"".equals(tContNo) && tContNo != null) {
			BonusAssignManuBL tBonusAssignManuBL = new BonusAssignManuBL();
			tCom = tContNo.substring(0, 4);
			if (!tBonusAssignManuBL.submitData(tVData, "")) {			
				   //完成时间
		    	Date  dend = new Date();         	
		    	long  endTime = dend.getTime(); 
		    	long tt=(endTime-initTime)/1000; 
		    	//日志监控,过程监控         	
		    	PubFun.logTrack(tG,tG.LogID[1], "本次红利派发批处理共花费"+tt+"秒");
				 //日志监控,过程监控
				PubFun.logTrack(tG, tG.LogID[1], "红利派发批处理业务处理完毕");	
				return false;
			}		
		} else{

			tComSQL = "select distinct substr(comcode,1,4) com from ldcom  where comcode!='86' "
					+ " order by com";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tComSQL);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
			int xLength=0;
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError.buildErr(this, "查询分支机构失败!");
				return false;
			} else {
				for (int k = 1; k <= tSSRS.MaxRow; k++) {
					// 数据操作业务处理
					tCom = tSSRS.GetText(k, 1);
					// 红利计算组,如果为空则将其置为 ”1“

					if (tGroupID == null || "".equals(tGroupID)) {
						tGroupID = "1";
					}
					try {

						String tContSql = "select * from lccont a where exists (select 'X' from LOBonusPol b"
							+ " where a.contno=b.contno and FiscalYear='"
							+ "?tFiscalYear?"
							+ "' and BonusFlag='0'  and groupid='"
							+ "?tGroupID?"
							+ "'"
							+ " and SGetDate<='"
							+ "?tCurMakeDate?"
							+ "' and AGetDate is null"
							+ " and substr(ContNo,1,4)='"
							+ "?tCom?"
							+ "'"
							// modify by jiaqiangli 2009-09-04 不能这么判断 只需要应分日期<=交至日期即可 类似于生存金的处理
							//+ " and exists (select 'B' from LCContState  where PolNo=b.PolNo and StateType='Available' and State='0' AND ((STARTDATE<=b.sgetdate and  EndDate >=b.SGetDate) or (EndDate is null))))"
							+ " and exists (select 'B' from lcpol  where PolNo=b.PolNo and b.sgetdate<=paytodate)) "
							+ ReportPubFun.getWherePart("ContNo", ReportPubFun.getParameterStr(tContNo, "?tContNo?"))
						   + " and (a.AppFlag='1' or (a.AppFlag='4' and  exists (select 'T' from LCContState where contno=a.contno and StateType='Terminate' and State='1' "
					       +" and EndDate is null and StateReason in ('01'))) or (a.AppFlag='4' and  exists (select 'T' from LCContState where contno=a.contno and StateType='Terminate' and State='1' "
					       +" and EndDate is null and StateReason in ('07')))) " 			       			       
					       //满期终止，失效终止的也要进行红利计算
							+ " and not exists (select  'X' from lcconthangupstate d where d.ContNo=a.ContNo and d.hanguptype='2')";// ??
					// 可以确定处于保全挂起的不要进行分红。其他的应该不受影响
//						    logger.debug(tContSql);
						    LCContSet hLCContSet=new LCContSet();
						    RSWrapper rsWrapper = new RSWrapper();
							// 采取长连接的方式
						    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
							sqlbv2.sql(tContSql);
							sqlbv2.put("tFiscalYear", tFiscalYear);
							sqlbv2.put("tGroupID", tGroupID);
							sqlbv2.put("tCurMakeDate", tCurMakeDate);
							sqlbv2.put("tCom", tCom);
							sqlbv2.put("tContNo", tContNo);
							if (!rsWrapper.prepareData(hLCContSet,sqlbv2)) {
								this.mErrors.copyAllErrors(rsWrapper.mErrors);
								logger.debug(rsWrapper.mErrors.getFirstError());
								return false;
							}
							do{
							rsWrapper.getData();
//							if (hLCContSet == null || hLCContSet.size() <= 0) {
//								continue;
//							}
							String LockNo= "";
							xLength=hLCContSet.size();
							VData hVData = new VData();
							for (int i = 1; i <= hLCContSet.size(); i++) {
								LCContSchema tLCContSchema = hLCContSet.get(k);
								XCount++;
								 // 日志监控,过程监控
								PubFun.logTrack (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发开始");
								
								LockNo = "";
								LockNo= tLCContSchema.getContNo();
								if (!mPubLock.lock(LockNo, "LB0002", tG.Operator)) 
								{
									CError tError = new CError(mPubLock.mErrors.getLastError());
									this.mErrors.addOneError(tError);
									 // 日志监控,状态监控				
									PubFun.logState(tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
									 // 日志监控,过程监控
									PubFun.logTrack (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
									tFail++;
									continue;
								}
								
								LOBonusPolSet tLOBonusPolSet = new LOBonusPolSet();
								LOBonusPolDB tLOBonusPolDB = new LOBonusPolDB();
								String tSql2 = "select * from LOBonusPol b"
									+ " where FiscalYear='"
									+ "?tFiscalYear?"
									+ "' and BonusFlag='0'  and groupid='"
									+ "?tGroupID?"
									+ "'"
									+ " and SGetDate<='"
									+ "?tCurMakeDate?"
									+ "' and AGetDate is null"
									+ " and substr(ContNo,1,4)='"
									+ tCom
									+ "'"
									+ " and b.ContNo='"
									+ "?ContNo?"
									+ "'";
//									+ " and (a.AppFlag='1' or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
//									+ " and EndDate is null and StateReason in ('01'))) or (a.AppFlag='4' and  exists (select 'T' from LCContState where PolNo=a.PolNo and StateType='Terminate' and State='1' "
//					                +" and EndDate is null and StateReason in ('07')))) " //满期终止的也要进行红利计算
//									+ " and exists (select 'B' from LCContState  where PolNo=b.PolNo and StateType='Available' and State='0' AND ((STARTDATE<=b.sgetdate and  EndDate >=b.SGetDate) or ( EndDate is null)))";
							     logger.debug(tSql2);
								    SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
									sqlbv3.sql(tSql2);
									sqlbv3.put("tFiscalYear", tFiscalYear);
									sqlbv3.put("tCurMakeDate", tCurMakeDate);
									sqlbv3.put("ContNo", tLCContSchema.getContNo());
								tLOBonusPolSet = tLOBonusPolDB.executeQuery(sqlbv3);
								if (tLOBonusPolSet == null || tLOBonusPolSet.size() < 1) {
									 // 日志监控,状态监控
									PubFun.logState(tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
									 // 日志监控,过程监控
									PubFun.logTrack (tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
									continue;
								}
								/** 分配红利金额，以LOBonusPolSet 为处理单位 */
								TransferData hTransferData = new TransferData();
								hTransferData.setNameAndValue("FiscalYear", tFiscalYear);
								
								VData mResult = new VData();
								
				            	Map map = new HashMap();
								map.put("LOBonusPolSet", tLOBonusPolSet);
								map.put("LCContSchema", tLCContSchema);
								map.put("TransferData", tTransferData);
								map.put("GlobalInput", tG);
								hVData.add(map);
								// 业务逻辑处理
								if(i%tConutMax==0)
								{
				            		mTaskWaitList.add(hVData);
				            		hVData = new VData();
								}
								
								if(i%tConutMax!=0&&i==hLCContSet.size())
								{
									mTaskWaitList.add(hVData);
									hVData = new VData();
								} 
							}
					mPubLock.unLock();
					
					String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BonusAssignServiceTMThread'";
			 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}

			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.get.BonusAssignBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
			} while (hLCContSet != null && hLCContSet.size() > 0);
							rsWrapper.close();
			}catch (Exception ex) {
							ex.printStackTrace();
			} 

	        //完成时间
	    	Date  dend = new Date();         	
	    	long  endTime = dend.getTime(); 
	    	long tt=(endTime-initTime)/1000; 
	    	//日志监控,结果监控 
	    	
	    	PubFun.logResult(tG,tG.LogID[1], "红利分配批处理"+String.valueOf(XCount)+"笔,共花费"+tt+"秒");
			return true;
				}
			}
		}
		return true;
	}

   
    //==========================================================================

	public static void main(String[] args) {
		BonusAssignServiceTMThread tBonusAssignServiceTMThread = new BonusAssignServiceTMThread();
		tBonusAssignServiceTMThread.dealMain();
	}
}
