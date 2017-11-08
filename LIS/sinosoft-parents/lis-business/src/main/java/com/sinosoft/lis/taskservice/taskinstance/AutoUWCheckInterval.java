/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 自动核保－后台自动处理入口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 * @CreateDate：2005-09-02
 */
public class AutoUWCheckInterval extends TaskThread {
private static Logger logger = Logger.getLogger(AutoUWCheckInterval.class);
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
    private VData mInputData;

	public AutoUWCheckInterval() {
	}
	
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC2000")) {
			return false;
		}
		return true;
	}
	
	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData=new VData();
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "SelAssignPlanBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
	

	public boolean dealMain() {
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
    	PubFun.logTrack(tG,tG.LogID[1],"开始新契约逾期通知书处理");
    	
		String tSql_Interval = "select sysvarvalue from ldsysvar where sysvar ='UWInterval'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql_Interval);
		ExeSQL tExeSQL =new ExeSQL();
		SSRS tSSRS = new SSRS();
		//String tInterval = "";
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();//时间在修改状态时会用
		int tInterval = Integer.valueOf(tExeSQL.getOneValue(sqlbv)).intValue();
		logger.debug("数据库中所描述的天数为："+tInterval);
		   //日志监控,过程监控        
    	PubFun.logTrack(tG,tG.LogID[1],"新契约通知书逾期天数为"+tInterval+"天");
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB =new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionSet tLWMissionSet1 = new LWMissionSet();
		LBMissionSet tLBMissionSet1 = new LBMissionSet();
		//add by lzf 
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		//String sql = "select * from lwmission where ActivityID='0000001100' and MissionProp18 in ('4','6') order by missionprop8 ";
		String sql = "select a.* from lwmission a,lccuwmaster b where a.ActivityID in (select activityid from lwactivity where functionid='10010028')"
		             +" and a.missionprop2 =b.contno and b.uwstate in ('4','6') order by a.missionprop8 ";//modify by lzf 
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
        //end by lzf
		//tLWMissionDB.setActivityID("0000001100");
		//tLWMissionDB.setMissionProp18("4");
		//tLWMissionSet =tLWMissionDB.query();
		tLWMissionSet =tLWMissionDB.executeQuery(sqlbv1);
		if(tLWMissionSet.size()>0){
			int mCount=0;
			try {			
				for(int i =1;i<=tLWMissionSet.size();i++)
				{
					String tUWDate = tLWMissionSet.get(i).getMissionProp8();
					logger.debug("合同号为："+tLWMissionSet.get(i).getMissionProp1()+"的保单的通知书下发日期为："+tUWDate);
					   //日志监控,过程监控        
			    	PubFun.logTrack(tG,tLWMissionSet.get(i).getMissionProp1(),"保单"+tLWMissionSet.get(i).getMissionProp1()+"的通知书下发日期为："+tUWDate);
					logger.debug("当前时间为："+tNowDate);
					//校验时间间隔
					int tDay =PubFun.calInterval(tUWDate, tNowDate, "D");
					if(tDay>=tInterval){
						mCount++; 
						//超过要求时间
						logger.debug("下发通知书时间已经超过了系统要求的"+tInterval+"天！此保单的UW_State将被置为‘7’");
						   //日志监控,过程监控        
				    	   PubFun.logTrack(tG,tLWMissionSet.get(i).getMissionProp1(),"保单"+tLWMissionSet.get(i).getMissionProp1()+"的通知书下发时间已经超过了"+tInterval+"天！");
						   if (!lockNo(tLWMissionSet.get(i).getMissionProp1())) {
							   logger.debug("锁定号码失败!");
							   this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
							   //mPubLock.unLock();
							   continue;
						   }// 锁定主附险投保单号以及暂收费号码)
						   else{
							    //修改状态和其他字段
								tLWMissionSchema = new LWMissionSchema();
								tLWMissionSchema = tLWMissionSet.get(i);
								//备份工作流 ln 2009-2-18 add	
								LBMissionSchema mLBMissionSchema = new LBMissionSchema();
								Reflections mReflections = new Reflections();
								String tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);
								mReflections.transFields(mLBMissionSchema, tLWMissionSchema);
								mLBMissionSchema.setSerialNo(tSerielNo);
								mLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
								mLBMissionSchema.setLastOperator("AUTO");
								mLBMissionSchema.setMakeDate(PubFun.getCurrentDate());
								mLBMissionSchema.setMakeTime(PubFun.getCurrentTime());
								tLBMissionSet1.add(mLBMissionSchema);
								
								tLWMissionSchema.setMissionProp18("7");
								tLWMissionSchema.setMissionProp8(tNowDate);
								tLWMissionSchema.setMissionProp9(tNowTime);
								tLWMissionSchema.setDefaultOperator(null);
								tLWMissionSchema.setMissionProp14("0000000000");
								tLWMissionSchema.setModifyDate(tNowDate);
								tLWMissionSchema.setModifyTime(tNowTime);
								tLWMissionSet1.add(tLWMissionSchema);
								//更新lccuwmaster，lccuwsub表中的uwstate字段 2013-04-07 lzf
								
								tLCCUWMasterDB.setContNo(tLWMissionSet.get(i).getMissionProp2());
								if(!tLCCUWMasterDB.getInfo()){
									CError.buildErr(this, "LCCUWMaster表取数失败!");
									return false;
								}
								tLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
								tLCCUWMasterSchema.setUWState("7");
								tLCCUWMasterSchema.setModifyDate(tNowDate);
								tLCCUWMasterSchema.setModifyTime(tNowTime);
								tLCCUWSubDB.setContNo(tLWMissionSet.get(i).getMissionProp2());
								tLCCUWSubDB.setUWNo(tLCCUWMasterSchema.getUWNo());
								if(!tLCCUWSubDB.getInfo()){
									CError.buildErr(this, "LCCUWSub表取数失败!");
									return false;
								}
								tLCCUWSubSchema = tLCCUWSubDB.getSchema();
								tLCCUWSubSchema.setUWState("7");
								tLCCUWSubSchema.setModifyDate(tNowDate);
								tLCCUWSubSchema.setModifyTime(tNowTime);
								//end by lzf
						   }
							// 日志监控,状态监控                 		
		         			PubFun.logState(tG,tLWMissionSet.get(i).getMissionProp1(), "保单"+tLWMissionSet.get(i).getMissionProp1()+"的逾期通知书处理成功","1");  
					}
					
				}
				map.put(tLWMissionSet1, "UPDATE");
				map.put(tLBMissionSet1, "INSERT");
				map.put(tLCCUWMasterSchema, "UPDATE");//add by lzf
				map.put(tLCCUWSubSchema, "UPDATE");//add by lzf
				if (!prepareOutputData()) {
					return false;
				}
                PubSubmit tPubSubmit = new PubSubmit();
				if (!tPubSubmit.submitData(mInputData, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "SelAutoAssignDutyBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				mPubLock.unLock();
			}
//			日志监控,结果监控
			PubFun.logResult (tG,tG.LogID[1],"本次共处理新契约逾期通知书"+mCount+"笔");
		}else{
			logger.debug("没有处在人工核保状态的保单！");
		}
		
		logger.debug("结束业务逻辑处理!--自动复核" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		/* end 业务处理逻辑 add by ck */
		   //日志监控,过程监控        
    	PubFun.logTrack(tG,tG.LogID[1],"新契约逾期通知书处理结束");
		return true;
	}

	public static void main(String[] args) {
		AutoUWCheckInterval tAutoUWCheckInterval = new AutoUWCheckInterval();
		tAutoUWCheckInterval.dealMain();
	}
}
