package com.sinosoft.lis.tb;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDAssignPlanDB;
import com.sinosoft.lis.db.LDAutoAssignDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDAssignPlanSchema;
import com.sinosoft.lis.schema.LDAutoAssignDetailSchema;
import com.sinosoft.lis.schema.LDAutoAssignSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LDAssignPlanSet;
import com.sinosoft.lis.vschema.LDAutoAssignSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:  
 * </p>
 * <p>
 * Description: 自动分配保单服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */
public class SelAssignServiceBL extends TimerTask {
private static Logger logger = Logger.getLogger(SelAssignServiceBL.class);

	//操作符
	private String mOpearte = "";
	//活动节点编码
	private String mActivityid = "";
	public CErrors mErrors = new CErrors();
	private Hashtable mActivityHashtable = new Hashtable();
	//并发控制锁.自动分配锁~
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	//单个单子锁~
	private PubConcurrencyLock mPubLockPrtNo = new PubConcurrencyLock();
	//是否运行标记,用来控制每次只能有一个进程运行分配.防止出错.
	private String mRunFlag = "";
	
	//每个个人工作池最大分配数量
	private int mPoolMaxPol = 15; 
	//本次最大查询数量
	private int mMaxAssignPol = 0;
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	//LDAutoAssignSet tDealLDAutoAssignSet = new LDAutoAssignSet();
	
	private int mMaxLoopTime = 50;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "A";
		String b = "B";
		String m = "Z";
		int c = a.compareTo(b);
		int d = m.compareTo(b);
		logger.debug("C".compareTo("F"));
		logger.debug(d);
		SelAssignServiceBL tSelAssignServiceBL = new SelAssignServiceBL();
//		LDAutoAssignSet tempLDAutoAssignSet = new LDAutoAssignSet();
//		LDAutoAssignDB tLDAutoAssignDB =new LDAutoAssignDB();
//	//	tempLDAutoAssignSet = tLDAutoAssignDB.executeQuery("select * from ldautoassign where taskno = '000053'");
//	//	tSelAssignServiceBL.sortLDAutoAssign(tempLDAutoAssignSet);
//		tSelAssignServiceBL.run();
		TransferData mTransferData = new TransferData();
		//自动分配标记
		mTransferData.setNameAndValue("AutoAssignFlag","Auto");
		//处理节点标记
		mTransferData.setNameAndValue("AutoAssignActivity","ALL");
		//操作员
		mTransferData.setNameAndValue("Operator","AutoAssign");
		VData tVData = new VData();
		tVData.add(mTransferData);
		if(!tSelAssignServiceBL.submitData(tVData,"AUTO"))
		{
			;
		}

	}
	
	/**
	 * 构造函数
	 */
	public SelAssignServiceBL()
	{
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		//获取需要处理的所有节点缓存
		String tSQL = "select * from ldcode where codetype ='selfservice' ";
		sqlbv1.sql(tSQL);
		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeSet = tLDCodeDB.executeQuery(sqlbv1);
		for(int i=1;i<=tLDCodeSet.size();i++)
		{
			this.mActivityHashtable.put(tLDCodeSet.get(i).getCode(), tLDCodeSet.get(i).getCode());
		}
	}
	
	/**
	 * 
	 * @param tActivityId
	 */
	private boolean dealData(String tActivityId)
	{
		try {
			GlobalInput tG = new GlobalInput();
			tG.Operator = "AUTO";
			tG.ManageCom = "86";
			tG.ComCode = "86";
			//提交数据的VData
			VData submitData = new VData();
			PubSubmit tPubSubmit = new PubSubmit();
			
			mMaxAssignPol = 0;
			/*  
			 *  先查询所有任务计划,以便删除过期任务;至于任务是否启动,程序会在锁定任务号时判断,
			 *  只有state='0'即启动了的任务才会进行分配
			 */
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String tSQL_Plan = "select * from LDAssignPlan where activityid='"+"?tActivityId?"+"' ";
			sqlbv2.sql(tSQL_Plan);
			sqlbv2.put("tActivityId", tActivityId);
			LDAssignPlanDB tLDAssignPlanDB = new LDAssignPlanDB();
			LDAssignPlanSet tLDAssignPlanSet = new LDAssignPlanSet();
			tLDAssignPlanSet = tLDAssignPlanDB.executeQuery(sqlbv2);
			if(tLDAssignPlanSet.size()>0)
			{
				
				LDAssignPlanSet tempLDAPSet =new LDAssignPlanSet();
				tempLDAPSet.add(tLDAssignPlanSet);
				for(int i=1;i<=tLDAssignPlanSet.size();i++)
				{
					// 校验计划的有效性
					if (checkDate(tLDAssignPlanSet.get(i).getTaskEndTime())) {
						LDAssignPlanSet tLDAPSet = new LDAssignPlanSet();
						logger.debug("表中的计划时间已经过期，计划将被删除");
						tLDAPSet.add(tLDAssignPlanSet.get(i));
						SelAssignPlanBL tSelAssignPlanBL = new SelAssignPlanBL();
						VData tVData = new VData();
						tVData.add(tG);
						tVData.add(tLDAPSet);
						if (!tSelAssignPlanBL.submitData(tVData, "DELETEPLAN")) {
							logger.debug("没有能够成功删除过期计划！");
						}
						tempLDAPSet.remove(tempLDAPSet.get(i));
					}
				}
				tLDAssignPlanSet.clear();
				tLDAssignPlanSet.add(tempLDAPSet);
			}
			//校验计划是否完成
			//查询是否有planamount=actuallyamount的记录，有则将其计划终止
			ExeSQL sExeSQL =new ExeSQL();
			SSRS tSSRS = new SSRS();
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("select a.activityid,a.assignno ,a.taskstarttime,a.taskendtime,a.planamount,a.servicename from LDAssignPlan a ,LDAutoAssign b where a.taskno=b.taskno and a.activityid=b.activityid and a.assignno=b.assignno and  a.planamount<=b.actuallyamount and a.state='0'");
			   tSSRS = sExeSQL.execSQL(sqlbv3);
			   if(tSSRS.MaxRow>0)
			   {
				   LDAssignPlanSet tLDAssignPlanSet1 = new LDAssignPlanSet();
				   SelAssignPlanBL tSelAssignPlanBL =new SelAssignPlanBL();
				   for(int c=1;c<=tSSRS.MaxRow;c++)
				   {
					   logger.debug("分配计划的保单数已完成！业务员["+tSSRS.GetText(c, 2)+"]在["+tSSRS.GetText(c, 6)+"]的计划将被终止！");
					   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
					   //tldSchema.settaskno(tSSRS.GetText(c, cCol));
					   tldSchema.setTaskStartTime(tSSRS.GetText(c, 3));
					   tldSchema.setTaskEndTime(tSSRS.GetText(c, 4));
					   tldSchema.setServiceName(tSSRS.GetText(c, 6));
					   tldSchema.setPlanAmount(tSSRS.GetText(c, 5));
					   tldSchema.setActivityid(tSSRS.GetText(c, 1));
					   tldSchema.setAssignNo(tSSRS.GetText(c, 2));
					   tldSchema.setState("1");
					   tLDAssignPlanSet1.add(tldSchema);
				   }

				   VData tVData = new VData();
				   tVData.add(tG);
				   tVData.add(tLDAssignPlanSet1);
				   if(!tSelAssignPlanBL.submitData(tVData, "UPDATE"))
				   {
					   logger.debug("没能停止已分配完的计划！");
				   }
			   }
			//先将所有需要分配的计划都锁定.在分配过程中,不允许其他分配调用.
			//锁定成功的才做分配
			Hashtable tLockHashtable = new Hashtable();
			for(int n=1;n<=tLDAssignPlanSet.size();n++)
			{
				//tLDAssignPlanSet里面存放的是所有的计划,只有启动了的计划才会进行锁定
				if("0".equals(tLDAssignPlanSet.get(n).getState())){
					String tLockNo = tLDAssignPlanSet.get(n).getTaskNo()+"#"+tLDAssignPlanSet.get(n).getActivityid()+"#"+tLDAssignPlanSet.get(n).getAssignNo();
					if(tLockHashtable.containsKey(tLockNo))
					{
						continue;
					}
					else
					{
						if(!this.lockNo(tLockNo))
						{
							continue;
						}
						tLockHashtable.put(tLockNo,  tLDAssignPlanSet.get(n));
					}
				}
			}
			
			//当前已经处理分配的集合.
			LDAutoAssignSet tDealLDAutoAssignSet = new LDAutoAssignSet();
			//查询LDAutoAssign
			Enumeration eKey=tLockHashtable.keys(); 
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement(); 
				String[] tTaskNo = key.split("#");
				LDAutoAssignDB tLDAutoAssignDB = new LDAutoAssignDB();
				LDAutoAssignSet tempLDAutoAssignSet = new LDAutoAssignSet();
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				String tSQL = "select * from LDAutoAssign where taskno='"+"?taskno?"+"' and activityid='"+"?activityid?"+"' "
				            + " and assignno='"+"?assignno?"+"'";
				sqlbv4.sql(tSQL);
				sqlbv4.put("taskno", tTaskNo[0]);
				sqlbv4.put("activityid", tTaskNo[1]);
				sqlbv4.put("assignno", tTaskNo[2]);
				tempLDAutoAssignSet = tLDAutoAssignDB.executeQuery(sqlbv4);
				LDAssignPlanSchema tempLDAssignPlanSchema = new LDAssignPlanSchema();
				tempLDAssignPlanSchema = (LDAssignPlanSchema)tLockHashtable.get(key);
				
				if(tempLDAutoAssignSet.size()<=0)
				{
					//没有的话,需要重新生成新数据
					LDAutoAssignSchema tempLDAutoAssignSchema = new LDAutoAssignSchema();
					tempLDAutoAssignSchema.setTaskNo(tempLDAssignPlanSchema.getTaskNo());
					tempLDAutoAssignSchema.setActivityid(tempLDAssignPlanSchema.getActivityid());
					tempLDAutoAssignSchema.setAssignNo(tempLDAssignPlanSchema.getAssignNo());
					tempLDAutoAssignSchema.setServiceName(tempLDAssignPlanSchema.getServiceName());
					tempLDAutoAssignSchema.setTaskStartTime(tempLDAssignPlanSchema.getTaskStartTime());
					tempLDAutoAssignSchema.setTaskEndTime(tempLDAssignPlanSchema.getTaskEndTime());
					tempLDAutoAssignSchema.setPlanAmount(tempLDAssignPlanSchema.getPlanAmount());
					tempLDAutoAssignSchema.setActuallyAmount("0");
					tempLDAutoAssignSchema.setUWPopedom(tempLDAssignPlanSchema.getUWPopedom());
					tempLDAutoAssignSchema.setOperator(tempLDAssignPlanSchema.getOperator());
					tempLDAutoAssignSchema.setMakeDate(this.mCurrentDate);
					tempLDAutoAssignSchema.setMakeTime(this.mCurrentTime);
					tempLDAutoAssignSchema.setModifyDate(this.mCurrentDate);
					tempLDAutoAssignSchema.setModifyTime(this.mCurrentTime);
					tDealLDAutoAssignSet.add(tempLDAutoAssignSchema);
				}
				else
				{
					LDAutoAssignSchema tempLDAutoAssignSchema = new LDAutoAssignSchema();
					tempLDAutoAssignSchema = tempLDAutoAssignSet.get(1);
					tempLDAutoAssignSchema.setPlanAmount(tempLDAssignPlanSchema.getPlanAmount());
					tDealLDAutoAssignSet.add(tempLDAutoAssignSet);
				}
				
			} 
			//查询分配计划.
//			for(int i=1;i<=tLDAssignPlanSet.size();i++)
//			{
//				LDAssignPlanSchema tempLDAssignPlanSchema = new LDAssignPlanSchema();
//				tempLDAssignPlanSchema = tLDAssignPlanSet.get(i);
//				String tSQL = "select * from LDAutoAssign where taskno='"+tempLDAssignPlanSchema.getTaskNo()+"' "
//				            + " and activityid='"+tempLDAssignPlanSchema.getActivityid()+"' "
//				            + " and assignno='"+tempLDAssignPlanSchema.getAssignNo()+"' ";
//				LDAutoAssignDB tLDAutoAssignDB = new LDAutoAssignDB();
//				LDAutoAssignSet tempLDAutoAssignSet = new LDAutoAssignSet();
//				tempLDAutoAssignSet = tLDAutoAssignDB.executeQuery(tSQL);
//				if(tempLDAutoAssignSet.size()<=0)
//				{
//					//没有的话,需要重新生成新数据
//					LDAutoAssignSchema tempLDAutoAssignSchema = new LDAutoAssignSchema();
//					tempLDAutoAssignSchema.setTaskNo(tempLDAssignPlanSchema.getTaskNo());
//					tempLDAutoAssignSchema.setActivityid(tempLDAssignPlanSchema.getActivityid());
//					tempLDAutoAssignSchema.setAssignNo(tempLDAssignPlanSchema.getAssignNo());
//					tempLDAutoAssignSchema.setServiceName(tempLDAssignPlanSchema.getServiceName());
//					tempLDAutoAssignSchema.setTaskStartTime(tempLDAssignPlanSchema.getTaskStartTime());
//					tempLDAutoAssignSchema.setTaskEndTime(tempLDAssignPlanSchema.getTaskEndTime());
//					tempLDAutoAssignSchema.setPlanAmount(tempLDAssignPlanSchema.getPlanAmount());
//					tempLDAutoAssignSchema.setActuallyAmount("0");
//					tempLDAutoAssignSchema.setOperator(tempLDAssignPlanSchema.getOperator());
//					tempLDAutoAssignSchema.setMakeDate(this.mCurrentDate);
//					tempLDAutoAssignSchema.setMakeTime(this.mCurrentTime);
//					tempLDAutoAssignSchema.setModifyDate(this.mCurrentDate);
//					tempLDAutoAssignSchema.setModifyTime(this.mCurrentTime);
//					tDealLDAutoAssignSet.add(tempLDAutoAssignSchema);
//				}
//				else
//				{
//					LDAutoAssignSchema tempLDAutoAssignSchema = new LDAutoAssignSchema();
//					tempLDAutoAssignSchema = tempLDAutoAssignSet.get(1);
//					tempLDAutoAssignSchema.setPlanAmount(tempLDAssignPlanSchema.getPlanAmount());
//					tDealLDAutoAssignSet.add(tempLDAutoAssignSet);
//				}
//			}
			if(tDealLDAutoAssignSet.size()<=0)
			{
				return true;
			}
			//对LDAutoAssign进行排序
			sortLDAutoAssign(tDealLDAutoAssignSet);
			//所有需要分配的人和单子信息
			

			//为了减少并发,需要一张一张单子分配并提交到数据库中.
			//查询共享工作池中的单子总数.
			LWMissionSet tCurrLWMissionSet = new LWMissionSet();
			tCurrLWMissionSet = this.getAllNeedAssignMission(tActivityId);
			//计算每个人尚需分配的单子数.
			logger.debug("当前需要分配的保单数量:"+tCurrLWMissionSet.size());
			if(tCurrLWMissionSet.size()==0)
			{
				return true;
			}
			//开始分配保单.
			//用来缓存分配的保单数.
			Hashtable tUserHashtable = null;
			int tMaxLoopTime = 0;
			for(int i=1;i<=tCurrLWMissionSet.size();i++)
			{
				MMap submitMMap = new MMap();
				try {
					LWMissionSchema tCurrLWMissionSchema = tCurrLWMissionSet.get(i);
					//开始分单啦~
					//分配一张锁定一张
					String[] tOperatedNo = new String[2];
					tOperatedNo[0]=tCurrLWMissionSchema.getMissionProp1();
					//mMissionID+mActivityID 和任务申请并发控制
					tOperatedNo[1]=tCurrLWMissionSchema.getMissionID()+tCurrLWMissionSchema.getActivityID();
					if(!mPubLockPrtNo.lock(tOperatedNo, "LC2000",tG.Operator))
					{
						continue;
					}
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					//tongmeng 2009-06-19 add
					//校验一下是否已经被申请了
					String tSQL = "select count(*) from lwmission where activityid='"+"?tActivityId?"+"' "
					            + " and defaultoperator is null and missionid='"+"?missionid?"+"' ";
					sqlbv5.sql(tSQL);
					sqlbv5.put("tActivityId", tActivityId);
					sqlbv5.put("missionid", tCurrLWMissionSchema.getMissionID());
					ExeSQL tempExeSQL = new ExeSQL();
					String tValue = tempExeSQL.getOneValue(sqlbv5);
					if(tValue==null||tValue.equals("")||Integer.parseInt(tValue)<=0)
					{
						logger.debug("tCurrLWMissionSchema:"+tCurrLWMissionSchema.getMissionID()+"已经被申请");
						continue;
					}
						
					LDAutoAssignSchema tCurrLDAutoAssignSchema = new LDAutoAssignSchema();
					
					for(int n=1;n<=tDealLDAutoAssignSet.size();n++)
					{
						tCurrLDAutoAssignSchema = tDealLDAutoAssignSet.get(n);
						int tActuallyAmount = tCurrLDAutoAssignSchema.getActuallyAmount();
//						LWMissionDB tLWMissionDB =new LWMissionDB();
//						LWMissionSet tLWMissionSet = new LWMissionSet();
//						tLWMissionDB.setDefaultOperator(tDealLDAutoAssignSet.get(n).getAssignNo());
//						tLWMissionDB.setActivityID(tDealLDAutoAssignSet.get(n).getActivityid());
//						if("0000001100".equals(tCurrLDAutoAssignSchema.getActivityid())){
//							tLWMissionDB.setMissionProp18("1");
//						}
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						String tSQL_Count = "select count(*) from lwmission where defaultoperator is not null "
							              + " and defaultoperator='"+"?defaultoperator?"+"' "
							              + " and activityid='"+"?activityid?"+"' ";
//						if("0000001100".equals(tCurrLDAutoAssignSchema.getActivityid())){
//							tSQL_Count = tSQL_Count + " and missionprop18='1' ";
//						}
						if("0000001110".equals(tCurrLDAutoAssignSchema.getActivityid())){
							tSQL_Count = tSQL_Count + " and exists(select 1 from lccuwmaster where contno=lwmission.missionprop2 and uwstate='1') ";
						}
						sqlbv6.sql(tSQL_Count);
						sqlbv6.put("defaultoperator", tDealLDAutoAssignSet.get(n).getAssignNo());
						sqlbv6.put("activityid", tCurrLDAutoAssignSchema.getActivityid());
						//modify by lzf 2013-04-19
						ExeSQL tCountExeSQL = new ExeSQL();
						String tValueCount = tCountExeSQL.getOneValue(sqlbv6);
						if(tValueCount==null||tValueCount.equals(""))
						{
							continue;
						}
						int tCount = 0;
						try {
							tCount = Integer.parseInt(tValueCount);
						} catch (RuntimeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue;
						}
						//tLWMissionSet =tLWMissionDB.query();
						//判断个人工作池中保单数是否大于15
						if (tCount < 15) {
							if (tActuallyAmount==tCurrLDAutoAssignSchema.getPlanAmount()) {
								continue;
							}

							if (tUserHashtable == null) {
								// 为空,说明第一次分配
								tUserHashtable = new Hashtable();

								//tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(), tCurrLDAutoAssignSchema.getAssignNo());

								//tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
								//tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
								//tongmeng 2009-06-17 modify
								//比较核保级别 
								if(tActivityId.equals("0000001100"))
								{
									String tUWPopedom = tCurrLDAutoAssignSchema.getUWPopedom();//核保师核保级别
									String tUWGrade = tCurrLWMissionSchema.getMissionProp12();//保单核保级别
									logger.debug("核保师核保级别: "+tUWPopedom);
									logger.debug("保单核保级别: "+tUWGrade);
									//如果是核保师的核保等级是X级需做特殊处理
									if("X".equals(tUWPopedom)){
										tUWPopedom="2";
									}
									//还需要处理并单数据,如果有并单的话,需要一并申请
									//待添加.........
									if(tUWPopedom.compareTo(tUWGrade)>=0)
									{
										//判断是保单是否为员工单，如果是，则需要判断当前核保师是否有核保权限
										ExeSQL ttExeSQL =new ExeSQL();
										SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
										String tAgentCode_sql ="select agentcode from lcpol where prtno ='"+"?prtno?"+"'";
										sqlbv7.sql(tAgentCode_sql);
										sqlbv7.put("prtno", tCurrLWMissionSchema.getMissionProp1());
										String tAgentCode ="";
										tAgentCode =ttExeSQL.getOneValue(sqlbv7);
										if(tAgentCode.subSequence(4, 10).equals("999999")){
											SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
											String tSQL_CheckUWFlag ="select uwoperatorflag from lduwuser where uwtype ='1' and usercode ='"+"?usercode?"+"'";
											sqlbv8.sql(tSQL_CheckUWFlag);
											sqlbv8.put("usercode", tCurrLDAutoAssignSchema.getAssignNo());
											SSRS tempSSRS =new SSRS();
											ExeSQL tExeSQL =new ExeSQL();
											tempSSRS =tExeSQL.execSQL(sqlbv8);
											if(tempSSRS.getMaxRow()>0&&tempSSRS.GetText(1, 1).equals("Y")){
												tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
												tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
												tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
												break;
											}
										}else{
											tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
											tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
											tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
											break;
										}
									}
									//tongmeng 2009-08-07 add
									//如果是员工单,需要校验该核保师是否有核保员工单的权限.
									
									//待添加...........
									
								}else{
									tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
									tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
									tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
									break;
								}
								break;
							} else {
								if (tUserHashtable.containsKey(tCurrLDAutoAssignSchema.getAssignNo())) {
									// 如果已经分配过了.
									if (n == tDealLDAutoAssignSet.size()) {
										// 如果已经到最后一条了.本次发牌结束.
										//清空tUserHashtable,重新发牌.重新获取当前的保单.
										tUserHashtable = null;
										//tongmeng 2009-08-28 modify
										//防止核保分配时一张单子因为一直没有匹配到核保师而造成死循环
										tMaxLoopTime = tMaxLoopTime + 1;
										logger.debug("tMaxLoopTime:"+tMaxLoopTime);
										if(tMaxLoopTime<=this.mMaxLoopTime)
										{
											i = i - 1;
										}
										else
										{
											//如果超过最大循环次数,就不将循环次数减1了 i = i - 1;
											logger.debug("MaxLoop:"+tMaxLoopTime);
											tMaxLoopTime = 0;
										}
									} else {
										continue;
									}
								} else {
									// 没有分配
									if(tActivityId.equals("0000001100"))
									{

										//还需要处理并单数据,如果有并单的话,需要一并申请
										//待添加.........
										
										String tUWPopedom = tCurrLDAutoAssignSchema.getUWPopedom();//核保师核保级别
										String tUWGrade = tCurrLWMissionSchema.getMissionProp12();//保单核保级别
										logger.debug("核保师核保级别: "+tUWPopedom);
										logger.debug("保单核保级别: "+tUWGrade);
										if(tUWPopedom.compareTo(tUWGrade)>=0)
										{
//											判断是保单是否为员工单，如果是，则需要判断当前核保师是否有核保权限
											ExeSQL ttExeSQL =new ExeSQL();
											SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
											String tAgentCode_sql ="select agentcode from lcpol where prtno ='"+"?prtno?"+"'";
											sqlbv9.sql(tAgentCode_sql);
											sqlbv9.put("prtno", tCurrLWMissionSchema.getMissionProp1());
											String tAgentCode ="";
											tAgentCode =ttExeSQL.getOneValue(sqlbv9);
											if(tAgentCode.subSequence(4, 10).equals("999999")){
												SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
												String tSQL_CheckUWFlag ="select uwoperatorflag from lduwuser where uwtype ='1' and usercode ='"+"?usercode?"+"'";
												sqlbv10.sql(tSQL_CheckUWFlag);
												sqlbv10.put("usercode", tCurrLDAutoAssignSchema.getAssignNo());
												SSRS tempSSRS =new SSRS();
												ExeSQL tExeSQL =new ExeSQL();
												tempSSRS =tExeSQL.execSQL(sqlbv10);
												if(tempSSRS.getMaxRow()>0&&tempSSRS.GetText(1, 1).equals("Y")){
													tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
													tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
													tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
													break;
												}
											}
											else{
												tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
												tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
												tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
												break;
											}
										}
										//tongmeng 2009-08-07 add
										//如果是员工单,需要校验该核保师是否有核保员工单的权限.
										
										//待添加...........
										
									}else{
										tUserHashtable.put(tCurrLDAutoAssignSchema.getAssignNo(),tCurrLDAutoAssignSchema.getAssignNo());
										tCurrLDAutoAssignSchema.setActuallyAmount(tActuallyAmount + 1);
										tCurrLWMissionSchema.setDefaultOperator(tCurrLDAutoAssignSchema.getAssignNo());
										break;
										
									}
								}
							}
						}
					}
					//记录分配轨迹
					LDAutoAssignDetailSchema tLDAutoAssignDetailSchema = new LDAutoAssignDetailSchema();
					if(tCurrLWMissionSchema.getDefaultOperator()==null||tCurrLWMissionSchema.getDefaultOperator().equals(""))
					{
						continue;
					}
					tLDAutoAssignDetailSchema = this.makeLDAutoAssignDetail(tCurrLWMissionSchema, tCurrLDAutoAssignSchema);
					
					//分配一张提交一张
					submitMMap.put(tCurrLWMissionSchema, "UPDATE"); 
					submitMMap.put(tLDAutoAssignDetailSchema, "INSERT"); 
					submitData.clear();
					submitData.add(submitMMap);
					//提交数据
					if(!tPubSubmit.submitData(submitData))
					{
						this.mErrors.copyAllErrors(tPubSubmit.mErrors);
						CError.buildErr(this,"提交数据失败!");
						//已经分配的数量减少一张
						tCurrLDAutoAssignSchema.setActuallyAmount(tCurrLDAutoAssignSchema.getActuallyAmount()-1);
						//return false;
						continue;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					mPubLockPrtNo.unLock();
				}
			}
			
			for(int i=1;i<=tDealLDAutoAssignSet.size();i++)
			{
				tDealLDAutoAssignSet.get(i).setModifyDate(PubFun.getCurrentDate());
				tDealLDAutoAssignSet.get(i).setModifyTime(PubFun.getCurrentTime());
			}
			MMap sMMap = new MMap();
			LDAutoAssignSet sLDAutoAssignSet = new LDAutoAssignSet();
			sLDAutoAssignSet.add(tDealLDAutoAssignSet);
			sMMap.put(sLDAutoAssignSet, "DELETE"); 
			sMMap.put(tDealLDAutoAssignSet, "INSERT"); 
			submitData.clear();
			submitData.add(sMMap);
			if(!tPubSubmit.submitData(submitData))
			{
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError.buildErr(this,"提交数据失败!");
				//已经分配的数量减少一张
				//return false;
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.mPubLock.unLock();
		}
		
		return true;
	}
	
	private LDAutoAssignDetailSchema makeLDAutoAssignDetail(LWMissionSchema tLWMissionSchema,LDAutoAssignSchema tLDAutoAssignSchema)
	{
		LDAutoAssignDetailSchema tLDAutoAssignDetailSchema = new LDAutoAssignDetailSchema();
		String tSerialNo = PubFun.CreateSeq("SERIALNO_SEQ");
		tLDAutoAssignDetailSchema.setSerialno(tSerialNo);
		tLDAutoAssignDetailSchema.setActivityid(tLDAutoAssignSchema.getActivityid());
		tLDAutoAssignDetailSchema.setAssignNo(tLDAutoAssignSchema.getAssignNo());
		tLDAutoAssignDetailSchema.setTaskNo(tLDAutoAssignSchema.getTaskNo());
		tLDAutoAssignDetailSchema.setMissionID(tLWMissionSchema.getMissionID());
		tLDAutoAssignDetailSchema.setSubMissionID(tLWMissionSchema.getSubMissionID());
		tLDAutoAssignDetailSchema.setPrtNo(tLWMissionSchema.getMissionProp1());
		tLDAutoAssignDetailSchema.setOperator("AUTO");
		tLDAutoAssignDetailSchema.setMakeDate(PubFun.getCurrentDate());
		tLDAutoAssignDetailSchema.setMakeTime(PubFun.getCurrentTime());
		tLDAutoAssignDetailSchema.setModifyDate(PubFun.getCurrentDate());
		tLDAutoAssignDetailSchema.setModifyTime(PubFun.getCurrentTime());
		return tLDAutoAssignDetailSchema;
	}
	/**
	 * 查询待分配的任务.
	 * @param tActivityid
	 * @return
	 */
	private LWMissionSet getAllNeedAssignMission(String tActivityId)
	{
		LWMissionSet sLWMissionSet = new LWMissionSet();
		LWMissionDB sLWMissionDB = new LWMissionDB();
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		String tSQL="";
		if(tActivityId.equals("0000001110"))//由‘0000001100’修改为‘0000001110’ modify by lzf 
		{
			 tSQL =" select a.* from lwmission a,es_doc_main b where a.missionprop1=b.doccode and b.subtype='UA001' "
//				  +" and activityid='"+tActivityId+"' and defaultoperator is null and missionprop18 ='1' "
                  +" and activityid='"+"?tActivityId?"+"' and defaultoperator is null and exists(select 1 from lccuwmaster where contno=a.missionprop2 and uwstate='1') "
                  + " and missionprop19 is null "
				  +" order by missionprop12 desc,b.makedate,b.maketime ";
		}else if(tActivityId.equals("0000001002")){
			 tSQL =" select a.* from lwmission a,es_doc_main b where a.missionprop1=b.doccode and b.subtype='UA001' "
				  +" and activityid='"+"?tActivityId?"+"' and defaultoperator is null  and missionprop9 ='1' order by b.makedate,b.maketime ";
		}else{
			tSQL =" select a.* from lwmission a,es_doc_main b where a.missionprop1=b.doccode and b.subtype='UA001' "
				+" and activityid='"+"?tActivityId?"+"' and defaultoperator is null order by b.makedate,b.maketime ";
		}
		sqlbv11.sql(tSQL);
		sqlbv11.put("tActivityId", tActivityId);
		sLWMissionSet = sLWMissionDB.executeQuery(sqlbv11);
		return sLWMissionSet;
	}
	
	/**
	 * 排序,保证对每个人的分配都平均
	 * @param tLDAutoAssignSet
	 */
	private void sortLDAutoAssign(LDAutoAssignSet tLDAutoAssignSet)
	{
		//LDAutoAssignSet tempLDAutoAssignSet = new LDAutoAssignSet();
		//tempLDAutoAssignSet.add(tLDAutoAssignSet);
		LDAutoAssignSchema tLDAutoSchema =new LDAutoAssignSchema();
		for (int i=2;i<=tLDAutoAssignSet.size();i++)
		{
			for(int j=tLDAutoAssignSet.size();j>=i;j--)
			{
				if(tLDAutoAssignSet.get(j-1).getActuallyAmount()>tLDAutoAssignSet.get(j).getActuallyAmount())
				{
					tLDAutoSchema.setSchema(tLDAutoAssignSet.get(j));
					tLDAutoAssignSet.get(j).setSchema(tLDAutoAssignSet.get(j-1));
					tLDAutoAssignSet.get(j-1).setSchema(tLDAutoSchema);
				}
			}
		}
		//tLDAutoAssignSet.clear();
		//tLDAutoAssignSet.add(tempLDAutoAssignSet);
		//排序,并确定最大可分配数量.
	}
	/**
	 * 入口函数
	 * @param tInputData
	 * @return
	 */
	public boolean submitData(VData tInputData,String tOperate)
	{
		
		this.mOpearte = tOperate;
		if(!this.getInputData(tInputData))
		{
			return false;
		}
		//获取所有需要处理的节点号
		Enumeration e=this.mActivityHashtable.keys(); 
		while(e.hasMoreElements()) 
		{ 
			String key=(String)e.nextElement(); 
			
			String tValue = mActivityHashtable.get(key).toString();
			//逐一处理相关节点的数据分配
			if(!dealData(tValue))
			{
				continue;
			}
		} 
		return true;
	}
	
	//自动扫描入口程序
	public void run() {
		logger.debug("begin deal AssignService...."+mRunFlag);
		try {
			if (this.mRunFlag.equals("1")) {
				//正在运行中...本次不做操作.
				logger.debug("上次分配未完成,本次不做操作...");
			} else {
				//设置为运行.开始自动分配.
				this.mRunFlag = "1";
				
				TransferData mTransferData = new TransferData();
				//自动分配标记
				mTransferData.setNameAndValue("AutoAssignFlag","Auto");
				//处理节点标记
				mTransferData.setNameAndValue("AutoAssignActivity","ALL");
				//操作员
				mTransferData.setNameAndValue("Operator","AutoAssign");
				VData tVData = new VData();
				tVData.add(mTransferData);
				if(!this.submitData(tVData,"AUTO"))
				{
					//
					this.mRunFlag = "0";
				}
				this.mRunFlag = "0";
			}
			logger.debug("end deal AssignService...."+mRunFlag);
		} catch (Exception e) {
			// TODO: handle exception
			//出错了....设置为不分配...
			e.printStackTrace();
			this.mRunFlag = "0";
		}
		finally
		{
			//this.mRunFlag
		}
	}

	/**
	 * 锁定业务号
	 * @param tPrtNo
	 * @return
	 */
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC2000")) {
			return false;
		}
		return true;
	}
	
	//private boolean 
	/*
	 * 获取数据
	 */
	private boolean getInputData(VData tInputData)
	{
		return true;
	}
	public boolean checkDate(String endline)   throws java.text.ParseException
    {
        boolean flag = false;
        Date endDate = null;
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endDate = formatter.parse(endline);
        if(endDate.getTime() - nowDate.getTime() <= 0)
            flag = true;
        
        return flag;
   }
}
