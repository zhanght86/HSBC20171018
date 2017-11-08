package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDAssignPlanDB;
import com.sinosoft.lis.db.LDAutoAssignDB;
import com.sinosoft.lis.db.LDAutoAssignTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDAssignPlanSchema;
import com.sinosoft.lis.schema.LDAssignPlanTraceSchema;
import com.sinosoft.lis.schema.LDAutoAssignSchema;
import com.sinosoft.lis.schema.LDAutoAssignTraceSchema;
import com.sinosoft.lis.vschema.LDAssignPlanSet;
import com.sinosoft.lis.vschema.LDAssignPlanTraceSet;
import com.sinosoft.lis.vschema.LDAutoAssignSet;
import com.sinosoft.lis.vschema.LDAutoAssignTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 工作流任务申请
 * </p>
 * <p>
 * Description: 每个操作员可以每次申请1个任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ [upd zhangtao]
 * @version 1.0
 */

public class SelAssignPlanBL {
private static Logger logger = Logger.getLogger(SelAssignPlanBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private LDAssignPlanSchema mLDAssignPlanSchem =new LDAssignPlanSchema();
	private LDAssignPlanTraceSchema mLDAssignPlanTraceSchem =new LDAssignPlanTraceSchema();
	private LDAssignPlanSet mLDAssignPlanSet =new LDAssignPlanSet();
	private LDAssignPlanTraceSet mLDAssignPlanTraceSet =new LDAssignPlanTraceSet();
	private LDAssignPlanDB mLDAssignPlanDB =new LDAssignPlanDB();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mType;

	public SelAssignPlanBL() {
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "WageDetailListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	/**
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LW0003")) {
			return false;
		}
		return true;
	}
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData, mOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}
		
		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "SelAssignPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tTaskNo = "";
		String tSelTaskNo = "";
		String sql="";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		if(mType.equals("UW")){
//			sql ="select * from LDAssignPlan where activityid in ('0000001100')";
//			tExeSQL.execSQL("select distinct taskno from ldassignplan where activityid in ('0000001100')");
			sql ="select * from LDAssignPlan where activityid in (select activityid from lwactivity  where functionid ='10010028')";
			sqlbv.sql(sql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("select distinct taskno from ldassignplan where activityid in (select activityid from lwactivity  where functionid ='10010028')");
			tExeSQL.execSQL(sqlbv1);
			tSelTaskNo =tExeSQL.getOneValue(sqlbv);
		}else if (mType.equals("QAC")){
			sql ="select * from LDAssignPlan where activityid in ('0000001090','0000001002','0000001404')";
			sqlbv.sql(sql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select distinct taskno from ldassignplan where activityid in ('0000001090','0000001002','0000001404')");
			tExeSQL.execSQL(sqlbv2);
			tSelTaskNo =tExeSQL.getOneValue(sqlbv);
		}
		LDAssignPlanSet tSet = new LDAssignPlanSet();
		LDAssignPlanSet tLDAssignPlanSet =new LDAssignPlanSet();
		LDAssignPlanTraceSchema tldtraceSchema = new LDAssignPlanTraceSchema();
		LDAssignPlanTraceSet tldtraceSet =new LDAssignPlanTraceSet();
		LDAssignPlanSchema tLDAssignPlanSchema =new LDAssignPlanSchema ();
		tSet=mLDAssignPlanDB.executeQuery(sqlbv);
		String tNowdate = PubFun.getCurrentDate();
		String tNowtime = PubFun.getCurrentTime();
		String tMdate ="";
		String tMtime ="";
		if(mOperate.equals("INSERT")||mOperate.equals("UPDATE"))
		{
			if(this.mOperate.equals("INSERT"))
			{
				if(tSet.size()>0)
				{
					CError.buildErr(this, "表中已经存在计划，不能再分配！请尝试[修改]提交");
					return false;
				}
				tTaskNo = PubFun1.CreateMaxNo("tTaskNo", 6);
				tMdate =tNowdate;
				tMtime =tNowtime;
				logger.debug("生成流水号，即任务号为： "+tTaskNo);
				
				//tLDAssignPlanSchema.settaskno(tTaskNo);
			}else if(this.mOperate.equals("UPDATE"))
			{
				tSet = mLDAssignPlanDB.executeQuery(sqlbv);
				String tMakedate="";
				String tMaketime="";
				//如果停止任务的同时，任务过期，计划将会被删除 get(1) 会空指针
				if(tSet.size()<=0){
					logger.debug("本次"+mType+"(UW-核保、QAC-随机任务)自动分配进行修改时没有查询到原来的计划，可能由于在修改的同时，任务过期被删除！");
					return true;
				}else{
					tTaskNo = tSet.get(1).getTaskNo();
					tMakedate = tSet.get(1).getMakeDate();
					tMaketime = tSet.get(1).getMakeTime();
				}
				tMdate =tMakedate;
				tMtime =tMaketime;
				//tLDAssignPlanSchema.settaskno(tTaskNo);
			}
			//插入和修改的时侯需要将页面的数据
			for(int i=1;i<=mLDAssignPlanSet.size();i++)
			{
				tLDAssignPlanSchema = mLDAssignPlanSet.get(i);
				if("".equals(mLDAssignPlanSet.get(i).getState())||mLDAssignPlanSet.get(i).getState()==""){
				 tLDAssignPlanSchema.setState("0");
				}else
				{
					tLDAssignPlanSchema.setState(mLDAssignPlanSet.get(i).getState());
				}
				tLDAssignPlanSchema.setMakeDate(tMdate);
				tLDAssignPlanSchema.setMakeTime(tMtime);
				tLDAssignPlanSchema.setModifyDate(tNowdate);
				tLDAssignPlanSchema.setModifyTime(tNowtime);
				tLDAssignPlanSchema.setOperator(this.mOperator);
			    tLDAssignPlanSchema.setTaskNo(tTaskNo);
			    tLDAssignPlanSet.add(tLDAssignPlanSchema);
			}
			logger.debug("操作命令是"+this.mOperate+"得到的taskno为： "+tTaskNo);
		}
		
		    LDAssignPlanSet tldassSet1 = new LDAssignPlanSet();//但更新时有不同的更行情况 新增节点计划
		    LDAssignPlanSet tldassSet2 = new LDAssignPlanSet();//但更新时有不同的更行情况 修改节点计划
			if(this.mOperate.equals("INSERT"))
			{
				map.put(tLDAssignPlanSet, "INSERT");
			}else if(this.mOperate.equals("UPDATE"))
			{
				LDAssignPlanSet tldassSet = new LDAssignPlanSet();
//				LDAssignPlanSet tldassSet2 = new LDAssignPlanSet();
//				tldassSet2.set(tLDAssignPlanSet);
				int tsize = tLDAssignPlanSet.size();
				for(int k=1;k<=tsize;k++)
				{
					//如果只是对原有记录的变化则执行更新
					LDAssignPlanSchema tldassU =new LDAssignPlanSchema();
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					String upinsersql = "select * from LDAssignPlan where activityid = '"+"?activityid?"+"' and assignno = '"+"?assignno?"+"'";
					sqlbv5.sql(upinsersql);
					sqlbv5.put("activityid", tLDAssignPlanSet.get(k).getActivityid());
					sqlbv5.put("assignno", tLDAssignPlanSet.get(k).getAssignNo());
					tldassSet = mLDAssignPlanDB.executeQuery(sqlbv5);
					tldassU = tLDAssignPlanSet.get(k);
					if(tldassSet.size()<=0)
					{
//						tLDAssignPlanSet.removeRange(1, 1);
//						tLDAssignPlanSet.remove(tldassS);
						tldassU.setMakeDate(tNowdate);
						tldassU.setMakeTime(tNowtime); 
						tldassSet1.add(tldassU);//获取新增的节点计划数据
					}else
					{
						if(tldassU.getAssignNo().equals(tldassSet.get(1).getAssignNo())&&
								tldassU.getPlanAmount()==tldassSet.get(1).getPlanAmount()&&
								tldassU.getState().equals(tldassSet.get(1).getState())&&
								tldassU.getTaskStartTime().equals(tldassSet.get(1).getTaskStartTime())&&
								tldassU.getTaskEndTime().equals(tldassSet.get(1).getTaskEndTime()))
//							if(tLDAssignPlanSet.get(1).getAssignNo().equals(tldassSet.get(1).getAssignNo())&&
//									tLDAssignPlanSet.get(1).getPlanAmount()==tldassSet.get(1).getPlanAmount()&&
//									tLDAssignPlanSet.get(1).getState().equals(tldassSet.get(1).getState())&&
//									tLDAssignPlanSet.get(1).getTaskStartTime().equals(tldassSet.get(1).getTaskStartTime())&&
//									tLDAssignPlanSet.get(1).getTaskEndTime().equals(tldassSet.get(1).getTaskEndTime()))
								{
							//记录没有改动
							//tLDAssignPlanSet.removeRange(1, 1);
								}else{
									tldassSet2.add(tldassU);//获取修改的节点计划数据
								}
					}
						//tldassSet1.add(tldassS);
						//tldassS.setmakedate(amakedate)
				}
				/*
				 * 程序有些地方太乱太复杂 可以选择更简便的方法 有待简化、修改
				 * */
				tLDAssignPlanSet.clear();
				tLDAssignPlanSet.set(tldassSet2);
				
				 map.put(tldassSet1, "INSERT");//此种更新是为操作员在一个新的节点下分配工作量
				
				map.put(tLDAssignPlanSet, "UPDATE");
			}
			else if(this.mOperate.equals("STARTALL"))
			{
				if(mType.equals("QAC"))
				{
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					//异常件、问题件、人工合并
					sqlbv6.sql("update LDAssignPlan set state='0',modifydate='"+"?tNowdate?"+"',modifytime='"+"?tNowtime?"+"' ,operator = '"+"?operator?"+"' where activityid in ('0000001090','0000001404','0000001002')");
					sqlbv6.put("tNowdate", tNowdate);
					sqlbv6.put("tNowtime", tNowtime);
					sqlbv6.put("operator", this.mGlobalInput.Operator);
				    map.put(sqlbv6,"UPDATE");
				}else{
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					//当前还有核保、也许以后还会增加其他...
					sqlbv7.sql("update LDAssignPlan set state='0',modifydate='"+"?tNowdate?"+"',modifytime='"+"?tNowtime?"+"' ,operator = '"+"?operator?"+"' where activityid in (select activityid from lwactivity  where functionid ='10010028')");
					sqlbv7.put("tNowdate", tNowdate);
					sqlbv7.put("tNowtime", tNowtime);
					sqlbv7.put("operator", this.mGlobalInput.Operator);
//					map.put("update LDAssignPlan set state='0',modifydate='"+tNowdate+"',modifytime='"+tNowtime+"' ,operator = '"+this.mGlobalInput.Operator+"' where activityid in ('0000001100')","UPDATE");
					map.put(sqlbv7,"UPDATE");
					
				}
			}
			else if (this.mOperate.equals("TERMINATEALL"))
			{
				if(mType.equals("QAC"))
				{
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					//异常件、问题件、人工合并
					sqlbv8.sql("update LDAssignPlan set state='1',modifydate='"+"?tNowdate?"+"',modifytime='"+"?tNowtime?"+"' ,operator = '"+"?operator?"+"' where activityid in ('0000001090','0000001404','0000001002')");
					sqlbv8.put("tNowdate", tNowdate);
					sqlbv8.put("tNowtime", tNowtime);
					sqlbv8.put("operator", this.mGlobalInput.Operator);
					map.put(sqlbv8,"UPDATE");
				}else
				{
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					//当前还有核保、也许以后还会增加其他...mType = "UW"
					sqlbv9.sql("update LDAssignPlan set state='1',modifydate='"+"?tNowdate?"+"',modifytime='"+"?tNowtime?"+"' ,operator = '"+"?operator?"+"' where activityid in (select activityid from lwactivity  where functionid ='10010028')");
					sqlbv9.put("tNowdate", tNowdate);
					sqlbv9.put("tNowtime", tNowtime);
					sqlbv9.put("operator", this.mGlobalInput.Operator);
//					map.put("update LDAssignPlan set state='1',modifydate='"+tNowdate+"',modifytime='"+tNowtime+"' ,operator = '"+this.mGlobalInput.Operator+"' where activityid in ('0000001100')","UPDATE");
					map.put(sqlbv9,"UPDATE");
					
				}
			}else if (this.mOperate.equals("STARTSEL"))
			{
				for (int i=1;i<=mLDAssignPlanSet.size();i++)
				{
					logger.debug(""+mLDAssignPlanSet.get(i).getAssignNo());
					mLDAssignPlanDB.setAssignNo(mLDAssignPlanSet.get(i).getAssignNo());
					mLDAssignPlanDB.setTaskNo(tSelTaskNo);
					tLDAssignPlanSet = mLDAssignPlanDB.query();
					for(int j=1;j<=tLDAssignPlanSet.size();j++){
						if("0".equals(tLDAssignPlanSet.get(j).getState())||tLDAssignPlanSet.get(j).getState()=="0")
						{
							CError.buildErr(this, "业务员["+tLDAssignPlanSet.get(j).getAssignNo()+"]在"+tLDAssignPlanSet.get(j).getActivityid()+"节点的分配计划已经启动！");
							continue;
						}else{
							if(mType.equals("QAC")){
								SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
								sqlbv.sql("update LDAssignPlan set state='0',modifydate='"+"?modifydate?"+"'," 
										+" modifytime='"+"?modifytime?"+"' where assignno='" 
										+""+"?assignno?"+"'" 
										+" and activityid in ('0000001090','0000001404','0000001002')" 
										+" ");
								sqlbv10.put("modifydate", PubFun.getCurrentDate());
								sqlbv10.put("modifytime", PubFun.getCurrentTime());
								sqlbv10.put("assignno", mLDAssignPlanSet.get(i).getAssignNo());
								map.put(sqlbv10, "UPDATE");
							}else{
								SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
								sqlbv.sql("update LDAssignPlan set state='0',modifydate='"+"?modifydate?"+"'," 
										+" modifytime='"+"?modifytime?"+"' where assignno='" 
										+""+"?assignno?"+"'" 
//										+" and activityid in ('0000001100')" 
										+" and activityid in (select activityid from lwactivity  where functionid ='10010028')" 
										+" ");
								sqlbv11.put("modifydate", PubFun.getCurrentDate());
								sqlbv11.put("modifytime", PubFun.getCurrentTime());
								sqlbv11.put("assignno", mLDAssignPlanSet.get(i).getAssignNo());
								map.put(sqlbv11, "UPDATE");
								
							}
						}
					}
					
				}
				int tEC= this.mErrors.getErrorCount();
//				if(tEC!=0)
//					return false;
			}else if(this.mOperate.equals("TERMINATESEL"))
			{
				for (int i=1;i<=mLDAssignPlanSet.size();i++)
				{
					logger.debug(""+mLDAssignPlanSet.get(i).getAssignNo());
					mLDAssignPlanDB.setAssignNo(mLDAssignPlanSet.get(i).getAssignNo());
					mLDAssignPlanDB.setTaskNo(tSelTaskNo);
					tLDAssignPlanSet = mLDAssignPlanDB.query();
					for(int j=1;j<=tLDAssignPlanSet.size();j++){
						if("1".equals(tLDAssignPlanSet.get(j).getState())||tLDAssignPlanSet.get(j).getState()=="1")
						{
							CError.buildErr(this, "业务员["+tLDAssignPlanSet.get(j).getAssignNo()+"]在"+tLDAssignPlanSet.get(j).getActivityid()+"节点的分配计划已经终止！");
							continue;
						}else{
							if(mType.equals("QAC")){
								SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
								sqlbv12.sql("update LDAssignPlan set state='1',modifydate='"+"?modifydate?"+"'," 
										+" modifytime='"+"?modifytime?"+"' where assignno='" 
										+""+"?assignno?"+"'" 
										+" and activityid in ('0000001090','0000001404','0000001002')" 
										+" ");
								sqlbv12.put("modifydate", PubFun.getCurrentDate());
								sqlbv12.put("modifytime", PubFun.getCurrentTime());
								sqlbv12.put("assignno", mLDAssignPlanSet.get(i).getAssignNo());
								map.put(sqlbv12, "UPDATE");
							}else{
								SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
								sqlbv13.sql("update LDAssignPlan set state='1',modifydate='"+"?modifydate?"+"'," 
										+" modifytime='"+"?modifytime?"+"' where assignno='" 
										+""+"?assignno?"+"'" 
//										+" and activityid in ('0000001100')" 
										+" and activityid in (select activityid from lwactivity  where functionid ='10010028')" 
										+" ");
								sqlbv13.put("modifydate", PubFun.getCurrentDate());
								sqlbv13.put("modifytime", PubFun.getCurrentTime());
								sqlbv13.put("assignno", mLDAssignPlanSet.get(i).getAssignNo());
								map.put(sqlbv13, "UPDATE");
								
							}
						}
					}
				}
				int tEC= this.mErrors.getErrorCount();
//				if(tEC!=0)
//					{
//					return false;
//					}
//			}else {
//				ExeSQL sExeSQL = new ExeSQL();
//				logger.debug("应该是：INITDATA 实际为："+mOperate);
//				String Sql = " select assignno,planamount from LDAssignPlan ";
//				SSRS tSSRS = new SSRS();
//				tSSRS = sExeSQL.execSQL(Sql);
//				String[] mStrArr=new String[2];
//				String[][] mStr = new String[tSSRS.getMaxRow()][2];
//				for(int i=1; i <= tSSRS.getMaxRow(); i++)
//				{
//				  for( int j=1 ;j <= tSSRS.getMaxCol(); j++)
//				  {
//				    mStrArr[j-1] = tSSRS.GetText(i,j);
//				  } 
//				  mStr[i-1] = (String[])mStrArr.clone();
//			          mStrArr=new String[2];
//				}
//				mResult.add(0,mStr);
			}else
			{
				//mOperate="DELETEPLAN" 删除计划表中的计划
				LDAutoAssignSet tLDAutoAssignSet = new LDAutoAssignSet();
				LDAssignPlanSet tLDAPlanSet = new LDAssignPlanSet();
				LDAutoAssignSet tLDAASet = new LDAutoAssignSet();
				LDAutoAssignDB tLDAutoAssignDB = new LDAutoAssignDB();
				LDAutoAssignSchema tLDAutoAssignSchema =new LDAutoAssignSchema();
				LDAutoAssignTraceSet tLDAutoAssignTraceSet = new LDAutoAssignTraceSet();
				LDAutoAssignTraceDB tLDAutoAssignTraceDGB = new LDAutoAssignTraceDB();
				LDAutoAssignTraceSchema tLDAutoAssignTraceSchema = new LDAutoAssignTraceSchema();
				for(int i=1;i<=mLDAssignPlanSet.size();i++)
				{
					mLDAssignPlanSchem.setTaskNo(mLDAssignPlanSet.get(i).getTaskNo());
					mLDAssignPlanSchem.setActivityid(mLDAssignPlanSet.get(i).getActivityid());
					mLDAssignPlanSchem.setAssignNo(mLDAssignPlanSet.get(i).getAssignNo());
					tLDAPlanSet.add(mLDAssignPlanSchem);
//					map.put("delete from ldassignplan where taskno = '"+mLDAssignPlanSet.get(i).getTaskNo()+"' " +
//							" and activityid='"+mLDAssignPlanSet.get(i).getActivityid()+"' " +
//							" and assignno = '"+mLDAssignPlanSet.get(i).getAssignNo()+"' ", "DELETE");
//					tLDAutoAssignDB.setTaskNo(mLDAssignPlanSet.get(i).getTaskNo());
//					tLDAutoAssignDB.setActivityid(mLDAssignPlanSet.get(i).getActivityid());
//					tLDAutoAssignDB.setAssignNo(mLDAssignPlanSet.get(i).getAssignNo());
//					tLDAutoAssignSet = tLDAutoAssignDB.query();
//					map.put("delete from ldautoassign where taskno = '"+mLDAssignPlanSet.get(i).getTaskNo()+"' " +
//							" and activityid ='"+mLDAssignPlanSet.get(i).getActivityid()+"'" +
//							" and assignno = '"+mLDAssignPlanSet.get(i).getAssignNo()+"' ", "DELETE");
					//同时删除LDAutoAssign表中的记录
					if(tLDAutoAssignSet.size()>0)
					{
						tLDAutoAssignSchema.setTaskNo(mLDAssignPlanSet.get(i).getTaskNo());
						tLDAutoAssignSchema.setActivityid(mLDAssignPlanSet.get(i).getActivityid());
						tLDAutoAssignSchema.setAssignNo(mLDAssignPlanSet.get(i).getAssignNo());
						tLDAASet.add(tLDAutoAssignSchema);
					//再向LDAutoAssignTrace表中添加记录
					
					String tSerialNo = PubFun1.CreateMaxNo("tLDAutoTrace", 10);
					tLDAutoAssignTraceSchema.setSerialno(tSerialNo);
					tLDAutoAssignTraceSchema.setTaskNo(tLDAutoAssignSet.get(i).getTaskNo());
					tLDAutoAssignTraceSchema.setActivityid(tLDAutoAssignSet.get(i).getActivityid());
					tLDAutoAssignTraceSchema.setAssignNo(tLDAutoAssignSet.get(i).getAssignNo());
					tLDAutoAssignTraceSchema.setPlanAmount(tLDAutoAssignSet.get(i).getPlanAmount());
					tLDAutoAssignTraceSchema.setActuallyAmount(tLDAutoAssignSet.get(i).getActuallyAmount());
					tLDAutoAssignTraceSchema.setOperator(tLDAutoAssignSet.get(i).getOperator());
					tLDAutoAssignTraceSchema.setServiceName(tLDAutoAssignSet.get(i).getServiceName());
					tLDAutoAssignTraceSchema.setTaskStartTime(tLDAutoAssignSet.get(i).getTaskStartTime());
					tLDAutoAssignTraceSchema.setTaskEndTime(tLDAutoAssignSet.get(i).getTaskEndTime());
					tLDAutoAssignTraceSchema.setMakeDate(tNowdate);
					tLDAutoAssignTraceSchema.setMakeTime(tNowtime);
					tLDAutoAssignTraceSet.add(tLDAutoAssignTraceSchema);
					
					}
				}
				map.put(tLDAPlanSet, "DELETE");
				map.put(tLDAASet, "DELETE");
				map.put(tLDAutoAssignTraceSet, "INSERT"); 
				//map.put("delete from LDAssignPlan", "DELETE");
				
			}
			//向轨迹表中添加数据
			String tSerialNo="";
			LDAssignPlanSet tSet1 =new LDAssignPlanSet();
	    if(!this.mOperate.equals("INSERT"))
	    {
	    	//第一次插入数据是轨迹表中不用赋值
	    	
	    	if(this.mOperate.equals("STARTSEL")||this.mOperate.equals("TERMINATESEL"))
	    	{
	    		
	    		//修改计划 复制当前计划到轨迹表中
	    		if(tLDAssignPlanSet.size()>0)
	    		{
	    			for(int i =1;i<=tLDAssignPlanSet.size();i++)
	    			{
	    				mLDAssignPlanDB.setAssignNo(tLDAssignPlanSet.get(i).getAssignNo());
	    				mLDAssignPlanDB.setActivityid(tLDAssignPlanSet.get(i).getActivityid());
	    				tSet1 = mLDAssignPlanDB.query();
	    				if(tSet1.size()>0)
	    				{
	    				logger.debug("LDAssignPlan表中可通过assignno和activityid确定为唯一一条记录，实际查询出来的有"+tSet.size()+"条记录！");
	    				tldtraceSchema = new LDAssignPlanTraceSchema();
	    				tSerialNo = PubFun1.CreateMaxNo("tAssignTrace", 10);
	    				tldtraceSchema.setSerialno(tSerialNo);
	    				tldtraceSchema.setTaskNo(tSet1.get(1).getTaskNo());
	    				tldtraceSchema.setActivityid(tSet1.get(1).getActivityid());
	    				tldtraceSchema.setServiceName(tSet1.get(1).getServiceName());
	    				tldtraceSchema.setTaskStartTime(tSet1.get(1).getTaskStartTime());
	    				tldtraceSchema.setTaskEndTime(tSet1.get(1).getTaskEndTime());
	    				tldtraceSchema.setAssignNo(tSet1.get(1).getAssignNo());
	    				tldtraceSchema.setPlanAmount(tSet1.get(1).getPlanAmount());
	    				tldtraceSchema.setOperator(tSet1.get(1).getOperator());
	    				tldtraceSchema.setState(tSet1.get(1).getState());
	    				tldtraceSchema.setMakeDate(tLDAssignPlanSet.get(1).getModifyDate());
	    				tldtraceSchema.setMakeTime(tLDAssignPlanSet.get(1).getModifyTime());
	    				tldtraceSet.add(tldtraceSchema);
	    				}
	    			}
	    		}
	    	}else if(this.mOperate.equals("UPDATE")){
	    		if(tLDAssignPlanSet.size()>0)
	    		{
	    			for(int j=1;j<=tLDAssignPlanSet.size();j++)
		    		{
		    			mLDAssignPlanDB.setAssignNo(tLDAssignPlanSet.get(j).getAssignNo());
		    			mLDAssignPlanDB.setActivityid(tLDAssignPlanSet.get(j).getActivityid());
		    			tSet1 = mLDAssignPlanDB.query();
		    			
		    				if(tSet1.size()>0)
		    				{
		    					if(!tLDAssignPlanSet.get(j).getAssignNo().trim().equals(tSet1.get(1).getAssignNo().trim())
				    					||tLDAssignPlanSet.get(j).getPlanAmount()!=tSet1.get(1).getPlanAmount()
				    					||!tLDAssignPlanSet.get(j).getState().equals(tSet1.get(1).getState()))
				    			{
		    				       tldtraceSchema = new LDAssignPlanTraceSchema();
		    				       tSerialNo = PubFun1.CreateMaxNo("tAssignTrace", 10);
		    				       tldtraceSchema.setSerialno(tSerialNo);
		    				       tldtraceSchema.setTaskNo(tSet1.get(1).getTaskNo());
		    				       tldtraceSchema.setActivityid(tSet1.get(1).getActivityid());
		    				       tldtraceSchema.setServiceName(tSet1.get(1).getServiceName());
		    				       tldtraceSchema.setTaskStartTime(tSet1.get(1).getTaskStartTime());
		    				       tldtraceSchema.setTaskEndTime(tSet1.get(1).getTaskEndTime());
		    				       tldtraceSchema.setAssignNo(tSet1.get(1).getAssignNo());
		    				       tldtraceSchema.setPlanAmount(tSet1.get(1).getPlanAmount());
		    				       tldtraceSchema.setOperator(tSet1.get(1).getOperator());
		    				       tldtraceSchema.setState(tSet1.get(1).getState());
		    				       tldtraceSchema.setMakeDate(tLDAssignPlanSet.get(1).getModifyDate());
		    				       tldtraceSchema.setMakeTime(tLDAssignPlanSet.get(1).getModifyTime());
		    				       tldtraceSet.add(tldtraceSchema);
		    				}
		    			}
		    		}
	    		}
	    	}else if(this.mOperate.equals("DELETEPLAN"))
	    	{
	    		//超时后自动删除计划，同修改，只是makedate，maketime要置为当前时间
	    		for(int i =1;i<=tSet.size();i++)
	    		{
	    			tldtraceSchema = new LDAssignPlanTraceSchema();
	    			tSerialNo = PubFun1.CreateMaxNo("tAssignTrace", 10);
	    			tldtraceSchema.setSerialno(tSerialNo);
	    			tldtraceSchema.setTaskNo(tSet.get(i).getTaskNo());
	    			tldtraceSchema.setActivityid(tSet.get(i).getActivityid());
	    			tldtraceSchema.setServiceName(tSet.get(i).getServiceName());
	    			tldtraceSchema.setTaskStartTime(tSet.get(i).getTaskStartTime());
	    			tldtraceSchema.setTaskEndTime(tSet.get(i).getTaskEndTime());
	    			tldtraceSchema.setAssignNo(tSet.get(i).getAssignNo());
	    			tldtraceSchema.setPlanAmount(tSet.get(i).getPlanAmount());
	    			tldtraceSchema.setOperator(tSet.get(i).getOperator());
	    			tldtraceSchema.setState(tSet.get(i).getState());
	    			tldtraceSchema.setMakeDate(tNowdate);
	    			tldtraceSchema.setMakeTime(tNowtime);
	    			tldtraceSet.add(tldtraceSchema);
	    			
	    		}
	    	}else 
	    	{
	    		for(int i =1;i<=tSet.size();i++)
	    		{
    				tldtraceSchema = new LDAssignPlanTraceSchema();
    				tSerialNo = PubFun1.CreateMaxNo("tAssignTrace", 10);
    				tldtraceSchema.setSerialno(tSerialNo);
    				tldtraceSchema.setTaskNo(tSet.get(i).getTaskNo());
    				tldtraceSchema.setActivityid(tSet.get(i).getActivityid());
    				tldtraceSchema.setServiceName(tSet.get(i).getServiceName());
    				tldtraceSchema.setTaskStartTime(tSet.get(i).getTaskStartTime());
    				tldtraceSchema.setTaskEndTime(tSet.get(i).getTaskEndTime());
    				tldtraceSchema.setAssignNo(tSet.get(i).getAssignNo());
    				tldtraceSchema.setPlanAmount(tSet.get(i).getPlanAmount());
    				tldtraceSchema.setOperator(tSet.get(i).getOperator());
    				tldtraceSchema.setState(tSet.get(i).getState());
    				tldtraceSchema.setMakeDate(mLDAssignPlanSet.get(1).getModifyDate());
    				tldtraceSchema.setMakeTime(mLDAssignPlanSet.get(1).getModifyTime());
    				tldtraceSet.add(tldtraceSchema);
	    		}
	    	}
	    }
			map.put(tldtraceSet,"INSERT");
			logger.debug("处理完毕！");
//		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		tLWMissionDB.setActivityID(mActivityID);
//		tLWMissionDB.setMissionID(mMissionID);
//		tLWMissionDB.setSubMissionID(mSubMissionID);
//		if (!tLWMissionDB.getInfo()) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
//			CError tError = new CError();
//			tError.moduleName = "UWApplyBL";
//			tError.functionName = "dealData";
//			tError.errorMessage = "工作流任务查询失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		tLWMissionSchema.setSchema(tLWMissionDB.getSchema());
//
//		tLWMissionSchema.setLastOperator(mOperator);
//		tLWMissionSchema.setDefaultOperator(mOperator);
//		tLWMissionSchema.setMissionProp14(mOperator);
//		tLWMissionSchema.setInDate(PubFun.getCurrentDate());
//		tLWMissionSchema.setInTime(PubFun.getCurrentTime());
//		tLWMissionSchema.setModifyDate(tLWMissionSchema.getInDate());
//		tLWMissionSchema.setModifyTime(tLWMissionSchema.getInTime());
//		map.put(tLWMissionSchema, "UPDATE");
//		finally{
//			mPubLock.unLock();
//		}
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}
	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public boolean deleteplan(){
		mOperate="DELETEPLAN";
		dealData();
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "SelAssignPlanBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
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

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mLDAssignPlanSet=(LDAssignPlanSet) cInputData.getObjectByObjectName("LDAssignPlanSet", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据Operate失败");
			return false;
		}

		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据mManageCom失败");
			return false;
		}
		try{
			mType = (String)mTransferData.getValueByName("AssignType");
		}catch(Exception e){
			mType = "AUTO";
		}


		return true;
	}
	
	public static void main(String[] args) {
		SelAssignPlanBL tplan = new SelAssignPlanBL();
		LDAssignPlanSet tLDAssignPlanSet =new LDAssignPlanSet();
		LDAssignPlanDB mldDB = new LDAssignPlanDB();
		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.Operator = "001";
//		tGlobalInput.ComCode = "86";
//		tGlobalInput.ManageCom = "8611";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String  sql="select * from LDAssignPlan ";
		sqlbv.sql(sql);
		tLDAssignPlanSet=mldDB.executeQuery(sqlbv); 
		
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLDAssignPlanSet);
		if(tplan.submitData(tVData, "DELETEPLAN")==true)
			logger.debug("已经成功删除，请查询数据库！");
		
	}

}

