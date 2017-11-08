package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.get.PersonPaytoAccBL;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
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
  * 个单催付处理类 -入口
  *
**/
public class PersonPayPlanTMThread extends TaskThread {
	/** 公共锁定号码类 */
   private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
   private static Logger logger = Logger.getLogger(PersonPayPlanTMThread.class);
   public CErrors mErrors = new CErrors();
   private GlobalInput tG = new GlobalInput();
   private String mCurrentDate = PubFun.getCurrentDate();
   private String tParamCalDate;
   private String tCalDate = PubFun.getCurrentDate();
   private LCPolSchema inLCPolSchema;
   private LCGetSchema inLCGetSchema;
   /** 参数描述 */
   private String mTimeStart;
   private String tCurrentdate = PubFun.getCurrentDate();
   public PersonPayPlanTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--个单催付处理类" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";
		int tThreadCount=0;
		String TaskCode;
		inLCPolSchema = new LCPolSchema();
		inLCGetSchema = new LCGetSchema();
		FDate tFDate = new FDate();
		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
        //日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"个单催付处理类开始","1");
		tParamCalDate = (String) mParameters.get("CalDate");
		if (tParamCalDate == null || tParamCalDate.equals("")) {
			//add by jiaqiangli 2009-03-26 提前45天催付
			tParamCalDate = PubFun.calDate(tCalDate, 45, "D", null);
		}
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='PersonPayPlanTMCount' ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("个单催付处理类批处理开始：RnewPolAuto...");

		LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
		LJSGetSet tLJSGetSet = new LJSGetSet();

		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		// 初始化催付查询条件
		String condSql = initCondGet(sqlbv6)
				+ " order by ContNo,PolNo,getdutycode,dutycode";
		sqlbv6.sql(condSql);
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet mLCGetSet=new LCGetSet();
		mLCGetSet = tLCGetDB.executeQuery(sqlbv6);
		logger.debug("有" + mLCGetSet.size() + "条符合条件的给付数据!");


//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.Operator = "001";
//		tGlobalInput.ComCode = "86";
//		tGlobalInput.ManageCom = "86";

		int ErrCount = 0;
		int xLength = mLCGetSet.size();
//        if (mLCGetSet == null || mLCGetSet.size() <= 0)
//        {
//        	return false;
//        }
			// 加锁contno，LR0001：续期催收
		String LockNo = "";
		try {
				// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
				VData mVData = new VData();
				for (int i = 1; i <= xLength; i++) 
				{
		
//					先加锁
					logger.debug(i);
					LockNo = "";
					MMap tMap = new MMap();
					LCGetSchema tLCGetSchema=mLCGetSet.get(i);
	            	Map map = new HashMap();
					map.put("LCGetSchema", tLCGetSchema);
					map.put("ParamCalDate", tParamCalDate);
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
		
		TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='PersonPayPlanTMThread'";
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
				"com.sinosoft.lis.get.PersonPayPlanBLTMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 
		//add by jiaqiangli 2009-03-24 进行生存金入账户批处理
		logger.debug("come to PersonPaytoAccBL");
		String ljsget_sql = "select * from ljsget a where managecom like concat('"
				+ "?ManageCom?"
				//add by jiaqiangli 2009-03-31 排除团险
				+ "','%') and exists(select getnoticeno from ljsgetdraw b where b.grpcontno='00000000000000000000' and a.getnoticeno=b.getnoticeno) order by getdate ";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	 		sqlbv3.sql(ljsget_sql);
	 		sqlbv3.put("ManageCom", tG.ManageCom);
		LJSGetSet temp_LJSGetSet = new LJSGetSet();
		LJSGetDB tLJSGetDB = new LJSGetDB();
    	int tSuc = 0, tFail = 0;	
		try{
		temp_LJSGetSet = tLJSGetDB.executeQuery(sqlbv3);
//		日志监控，性能监控
		PubFun.logPerformance (tG,tG.LogID[1],"生存金入账户批处理开始","2");

    	TransferData tOtherTransferData = new TransferData();
    	VData tOtherVData = new VData();
    	VData mVData = new VData();
    	mTaskWaitList=new Vector();
		TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
			+ (String) this.mParameters.get("TaskCode") + ":"
			+ (String) this.mParameters.get("TaskPlanCode");
    	if (temp_LJSGetSet == null || temp_LJSGetSet.size() <= 0)
    	{
    			return false;
    	}
    	LJSGetSet hLJSGetSet=new LJSGetSet(); 
    	
    	for (int x= 1; x <= temp_LJSGetSet.size(); x++) {
    	if (tFDate.getDate(temp_LJSGetSet.get(x).getGetDate()).compareTo(tFDate.getDate(tCurrentdate)) <= 0) 
    	{
    		hLJSGetSet.add(temp_LJSGetSet.get(x));
    	}
		else {
			logger.debug(temp_LJSGetSet.get(x).getGetNoticeNo() + "尚未到应领日期，不入帐户！");
//				 日志监控,状态监控                 		
     		PubFun.logState(tG,temp_LJSGetSet.get(x).getGetNoticeNo(), "生存金(通知书号：" +temp_LJSGetSet.get(x).getGetNoticeNo()+ ")尚未到应领日期，不入帐户","0");  
			tFail++;
			continue;
		}
    	}
    	 xLength = hLJSGetSet.size();
		for (int j = 1; j <= hLJSGetSet.size(); j++) {
			LJSGetSchema tLJSGetSchema = hLJSGetSet.get(j);
				logger.debug("tLJSGetSchema.getGetNoticeNo()"+tLJSGetSchema.getGetNoticeNo()+"into lcinsureacc");
				tOtherTransferData.removeByName("GetNoticeNo");
				tOtherTransferData.setNameAndValue("GetNoticeNo", tLJSGetSchema.getGetNoticeNo());
				Map map = new HashMap();
				map.put("TransferData", tOtherTransferData);
				map.put("GlobalInput", tG);
				tOtherVData.add(map);
				
		     	if(j%tConutMax==0)
				{
            		mTaskWaitList.add(tOtherVData);
            		tOtherVData = new VData();
				}
				
				if(j%tConutMax!=0&&j==xLength)
				{
					mTaskWaitList.add(tOtherVData);
					tOtherVData = new VData();
				} 
				
//				PersonPaytoAccBL tPersonPaytoAccBL = new PersonPaytoAccBL();
//				if (!tPersonPaytoAccBL.submitData(tOtherVData, "")) {
//					CError.buildErr(this, "生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
//					logger.debug("生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
//					continue;
//				}
				tSuc++;
//				 日志监控,状态监控                 		
     			PubFun.logState(tG,tLJSGetSchema.getGetNoticeNo(), "生存金(通知书号：" +tLJSGetSchema.getGetNoticeNo()+ ")转入领取帐户中","1");  
				//add by jiaqiang 2009-03-30
//				//add by jiaqiang 2009-03-30 进行银行转帐授权的自动转账处理
//				LCContDB tLCContDB = new LCContDB();
//				tLCContDB.setContNo(tLJSGetSchema.getOtherNo());//保单号
//				tLCContDB.getInfo();
//				tOtherTransferData.removeByName("BalaDate");
//				tOtherTransferData.setNameAndValue("BalaDate", tCurrentdate);
//				tOtherVData.clear();
//				tOtherVData.add(tGI);
//				tOtherVData.add(tLCContDB.getSchema());
//				tOtherVData.add(tOtherTransferData);
//				AliveAccAssignBL tAliveAccAssignBL = new AliveAccAssignBL();
//				//add by jiaqiangli 非银行转帐的continue
//				tAliveAccAssignBL.submitData(tOtherVData, "");
			
			
		}
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.get.PersonPaytoAccBLTMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
	     }catch (Exception ex) {
	 		ex.printStackTrace();
	 	} 
		logger.debug("PersonPayPlanTask end deal" + tG.ManageCom);
//		日志监控,结果监控
		PubFun.logResult (tG,tG.LogID[1],"共有"+tSuc+"笔生存金转入领取帐户中");
		PubFun.logResult (tG,tG.LogID[1],"共有"+tFail+"笔生存金尚未到应领日期，不入帐户");	

        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "个单催付处理类批处理"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
	  	
		return true;
	}
	/**
	 * 初始化查询条件
	 * 
	 * @param Schema
	 * @return
	 */
	private String initCondGet(SQLwithBindVariables sqlbv6) {
		String aSql = "select * from LCGet where 1 = 1";
		  
	 		
		logger.debug("inLCGetSchema.getManageCom:"+inLCGetSchema.getManageCom() +"    mManageCom:"+tG.ManageCom);

		if ((inLCGetSchema.getManageCom() == null)
				|| inLCGetSchema.getManageCom().trim().equals("")) {
			//aSql = aSql + " and ManageCom like '" + mManageCom + "%'";
			//modify by jiaqiangli 2009-06-08
			aSql = aSql + " and ManageCom like concat('" + "?ManageCom?" + "','%')";	
			sqlbv6.put("ManageCom", tG.ManageCom);
		} else {
			aSql = aSql + " and ManageCom like concat('"
					+ "?inLCGetSchema?" + "','%')";
			sqlbv6.put("inLCGetSchema", inLCGetSchema.getManageCom());
		}

		if ((inLCGetSchema.getContNo() != null)
				&& !inLCGetSchema.getContNo().trim().equals("")) {
			aSql = aSql + " and ContNo= '" + "?inLCGetSchema?" + "'";
			sqlbv6.put("inLCGetSchema", inLCGetSchema.getManageCom());
		}

		if ((inLCGetSchema.getPolNo() != null)
				&& !inLCGetSchema.getPolNo().trim().equals("")) {
			aSql = aSql + " and PolNo= '" + "?inLCGetSchema?" + "'";
			sqlbv6.put("inLCGetSchema", inLCGetSchema.getPolNo());
		}

		// if ((inLCGetSchema.getAppntNo() != null)
		// && !inLCGetSchema.getAppntNo().trim().equals("")) {
		// aSql = aSql + " and AppntNo= '" + inLCGetSchema.getAppntNo() + "'";
		// }

		if ((inLCGetSchema.getInsuredNo() != null)
				&& !inLCGetSchema.getInsuredNo().trim().equals("")) {
			aSql = aSql + " and InsuredNo= '" + "?inLCGetSchema?"
					+ "'";
			sqlbv6.put("inLCGetSchema", inLCGetSchema.getInsuredNo());
		}
		// 如果没传入催付开始日期，则默认为MS开业日
		if ((mTimeStart == null) || mTimeStart.equals("")) {
			mTimeStart = "2003-01-01";
		}

		if (!((tParamCalDate == null) && tParamCalDate.trim().equals(""))) {
			aSql = aSql + " and gettodate<='" + "?tParamCalDate?" + "' and gettodate>='"
					+ "?mTimeStart?" + "' and getenddate>='" + "?mTimeStart?" + "'";
			sqlbv6.put("tParamCalDate", tParamCalDate);
			sqlbv6.put("mTimeStart", mTimeStart);
		}

		// LiveGetType=0（生存给付）,UrgeGetFlag Y--发催付 
		// 无垫款，无借款，不能失效，满期终止后的保单允许催付
		// 由于MS生存金也做清算，所以有垫款、借款和失效的都可以完成催付，但是不能领取
		// add by jiaqiangli 2009-03-31 排除团险
		aSql += " and grpcontno = '00000000000000000000' and LiveGetType='0' and UrgeGetFlag = 'Y' "
			+ " and ((getintv =0 and summoney=0) or getintv>0) "//add by jiaqiangli 趸领的只允许催付一次，即便催付的金额为0
			//+ " and not exists(select 'C' from LCContState where ContNo=LCGet.ContNo and StateType='Loan' and State='1' and EndDate is null)"
			//+ " and not exists(select 'V' from LCContState where PolNo=LCGet.PolNo and StateType='Available' and State='1' and EndDate is null)"
			//+ " and not exists(select 'B' from LCContState where PolNo=LCGet.PolNo and StateType='PayPrem' and State='1' and EndDate is null)"
			// add by jiaqiangli 2009-04-08 保全挂起不允许催付 其他如理赔挂起可以催付
			+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and hanguptype = '2' and contno = LCGet.contno) "
			+ " and exists(select 'X' from LCPol where conttype='1' and PolNo=LCGet.PolNo and (AppFlag='1' or (AppFlag='4' and exists(select 'T' from LCContState where PolNo=LCPol.PolNo and StateType='Terminate' and State='1' and StartDate <= '"
			+"?tParamCalDate?" 
			+"' and EndDate is null and StateReason in ('01')) or UrgeGetFlag = 'L')))"
			// add by jiaqiangli 2009-04-09 use as testcase
			//+ " and rownum <= 1 "
			;
		
		sqlbv6.put("tParamCalDate", tParamCalDate);
		logger.debug("==> 查询LCGet表SQL：" + aSql);

		return aSql;
	}

	
	 /**
     * 保全未使用无扫描申请数据清理批处理
     */
    private void EdorUnuseNoScanApply()
    {
        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理启动");

        //if (!(mCurrentTime.compareTo("23:00:00") >= 0 && mCurrentTime.compareTo("23:59:59") <= 0))
        //{
        //    return;    //在晚上 11 点以后运行
        //}
        //if (!(mCurrentTime.compareTo("00:00:00") >= 0 && mCurrentTime.compareTo("05:59:59") <= 0))
        //{
        //    return;    //在凌晨 06 点以前运行
        //}

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("WFOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理, 系统未设置逾期终止天数");
            return;
        }

        //选取需要清理的数据
        String DeleteSQL = new String("");
        DeleteSQL = "delete from LWMission "
                  +  "where ActivityID = '0000000002' "
                  +    "and MissionProp3 is null "
                  +    "and MakeDate <= (to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') - " + "?sTimeoutDays?" + ")";
        //logger.debug(DeleteSQL);
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
 		sqlbv4.sql(DeleteSQL);
 		sqlbv4.put("mCurrentDate", mCurrentDate);
 		sqlbv4.put("sTimeoutDays", sTimeoutDays);
        MMap tMap = new MMap();
        tMap.put(sqlbv4, "DELETE");
        VData tVData = new VData();
        tVData.add(tMap);
        tMap = null;

        //PubSubmit
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 删除数据失败！");
            return;
        }
        tPubSubmit = null;
        tVData = null;

        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理完毕");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理完毕");
    }  //function EdorUnuseNoScanApply end

    /**
     * 获取各种保全逾期终止批处理的逾期天数
     */
    private String getEdorTypeTimeoutDays(String sEdorType)
    {
        String sResultTimeoutDays = new String("");

        //查询逾期天数计算代码
        String QuerySQL = "select * from LMEdorCal where CalType = '" + "?sEdorType?" + "'";
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
 		sqlbv5.sql(QuerySQL);
 		sqlbv5.put("sEdorType", sEdorType);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
        try
        {
            tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv5);
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
		PersonPayPlanTMThread tPersonPayPlanTMThread = new PersonPayPlanTMThread();
		tPersonPayPlanTMThread.dealMain();
	}
}
