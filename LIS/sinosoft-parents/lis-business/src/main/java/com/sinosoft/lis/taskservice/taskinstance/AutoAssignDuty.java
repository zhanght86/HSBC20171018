package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.sinosoft.lis.db.LDAssignPlanDB;
import com.sinosoft.lis.db.LDAutoAssignDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDAssignPlanSchema;
import com.sinosoft.lis.schema.LDAutoAssignSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.SelAssignPlanBL;
import com.sinosoft.lis.vschema.LDAssignPlanSet;
import com.sinosoft.lis.vschema.LDAutoAssignSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个单自动签单－后台自动处理入口</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author:ck
 * @version 1.0
 * @CreateDate：2005-09-07
 */
public class AutoAssignDuty extends TaskThread
{
private static Logger logger = Logger.getLogger(AutoAssignDuty.class);

    public CErrors mErrors = new CErrors();
    protected HashMap mParameters = new HashMap();
    private MMap map = new MMap();
    int mcount =1;
    private VData mInputData;

    public AutoAssignDuty()
    {
    }

    
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC2000")) {
			return false;
		}
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

    public boolean dealMain()
    {
      /*业务处理逻辑*/
      logger.debug("进入业务逻辑处理!");
      
      
      try {
		GlobalInput tG = new GlobalInput();
		  tG.Operator = "AUTO";
		  tG.ManageCom = "86";
		  tG.ComCode = "86";
		  
		  ExeSQL sExeSQL = new ExeSQL();
		  SSRS tSSRS = new SSRS();
		  SSRS tSSRS1 = new SSRS();
		  
		  LDAssignPlanSet tLDAssignPlanSet =new LDAssignPlanSet();
		  LDAssignPlanDB tLDAssignPlanDB =new LDAssignPlanDB();
		  
		  LDAutoAssignSchema tLDAutoAssignSchema = null;
		  LDAutoAssignDB tLDAutoAssignDB =new LDAutoAssignDB();
		  
		  //查询已经启动的自动分配计划
		  String sql = "select * from LDAssignPlan where state='0' ";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		  sqlbv1.sql(sql);
		  tLDAssignPlanSet = tLDAssignPlanDB.executeQuery(sqlbv1);
		  if(tLDAssignPlanSet.size()>0)
		  {
		  String ttaskendtime = tLDAssignPlanSet.get(1).getTaskEndTime();
		  //如果计划时间超时 删除计划表中的计划
		  try {
			   if(checkDate(ttaskendtime))
			     {
				   logger.debug("表中的计划时间已经过期，计划将被删除");
				   SelAssignPlanBL tSelAssignPlanBL =new SelAssignPlanBL();
				   VData tVData = new VData();
				   tVData.add(tG);
				   tVData.add(tLDAssignPlanSet);
				   if(!tSelAssignPlanBL.submitData(tVData, "DELETEPLAN"))
				   {
				    	logger.debug("没有能够成功删除过期计划！");
				   }
			     }
		   } catch (ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   //查询是否有planamount=actuallyamount的记录，有则将其计划终止
		  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		  sqlbv2.sql("select a.activityid,a.assignno ,a.taskstarttime,a.taskendtime,a.planamount,a.servicename from LDAssignPlan a ,LDAutoAssign b where a.taskno=b.taskno and a.activityid=b.activityid and a.assignno=b.assignno and  a.planamount<=b.actuallyamount and a.state='0'");
		   tSSRS = sExeSQL.execSQL(sqlbv2);
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
		   
		  
		   
/*
 *以上部分是校验计划的有效性
 *下面进行分配
  */
			   String tdate = PubFun.getCurrentDate();
			   String ttime = PubFun.getCurrentTime();
			   LDCodeSet tLDCodeSet =new LDCodeSet();
			   LDCodeDB tLDCodeDB =new LDCodeDB();
			   //查询所有可分配的节点
			   String selfsql = "select * from ldcode where codetype ='selfservice' ";
			   SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			   sqlbv3.sql(selfsql);
			   tLDCodeSet = tLDCodeDB.executeQuery(sqlbv3);
			   for (int i=1;i<=tLDCodeSet.size();i++)
			   {
				   logger.debug("第"+i+"个节点是"+tLDCodeSet.get(i).getCode());
				   LDAutoAssignSet tLDAutoAssignSet2 = new LDAutoAssignSet();
				   SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				   sqlbv4.sql("select * from LDAssignPlan where state ='0' and activityid='"+"?tLDCodeSet?"+"' ");
				   sqlbv4.put("tLDCodeSet", tLDCodeSet.get(i).getCode());
				   tLDAssignPlanSet = tLDAssignPlanDB.executeQuery(sqlbv4);
				   if(tLDAssignPlanSet.size()<=0)
				   {
					   logger.debug("计划表中"+tLDCodeSet.get(i).getCode()+"节点的计划没有启动！");
					   
				   }else
				   {
				   //如果计划已经分配过一次或多次，则从LDAutoAssign表中却出assignno和本次最多分配保单数
					   SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
					   sqlbv5.sql("select * from LDAutoAssign where activityid='"+"?activityid?"+"'");
					   sqlbv5.put("activityid", tLDCodeSet.get(i).getCode());
				   tLDAutoAssignSet2 = tLDAutoAssignDB.executeQuery(sqlbv5);
//			   ExeSQL sExeSQL = new ExeSQL();
//			   tSSRS = new SSRS();
				   tSSRS.Clear();
				   if(tLDAutoAssignSet2.size()>0)
				   {
					   SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
					   sqlbv6.sql("  select b.assignno, (b.planamount - a.actuallyamount),b.taskno,b.activityid,b.servicename," +
						   		"b.planamount,b.taskstarttime,b.taskendtime," +
						   		"b.makedate,b.maketime from LDAutoAssign a, LDAssignPlan b where a.activityid =" +
						   		" '"+"?activityid?"+"' and a.taskno = b.taskno and a.activityid = b.activityid and a.assignno" +
						   				" = b.assignno and b.state = '0' order by actuallyamount");
					   sqlbv6.put("activityid", tLDCodeSet.get(i).getCode());
					   tSSRS = sExeSQL.execSQL(sqlbv6);
				   }else{
					   SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					   sqlbv7.sql("  select b.assignno, b.planamount,b.taskno,b.activityid," +
						   		"b.servicename,b.planamount,b.taskstarttime,b.taskendtime,b.makedate," +
						   		"b.maketime from LDAssignPlan b  where b.activityid = '"+"?activityid?"+"' and b.state = '0'");
					   sqlbv7.put("activityid", tLDCodeSet.get(i).getCode());
				   tSSRS = sExeSQL.execSQL(sqlbv7);}
      
				   //查询工作流节点的保单
//			   LWMissionSchema tLWMissionSchema =new LWMissionSchema();
//			   LWMissionDB tLWMissionDB = new LWMissionDB();
//			   LWMissionSet tLWMissionSet = new LWMissionSet();
//			   String lwmissionsql = "select * from lwmission where activityid='"+tLDCodeSet.get(i).getCode()+"' and defaultoperator is null ";
////			   tLWMissionDB.setActivityID(tLDCodeSet.get(i).getCode());
////			   tLWMissionDB.setDefaultOperator("");
//			   tLWMissionSet = tLWMissionDB.executeQuery(lwmissionsql);
				   //tongmeng 2008-10-17 modify
				   //自动分配,如果是核保任务的话,只取1状态的单子
				   SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				   sqlbv8.sql(" select distinct(a.missionprop1),a.activityid,(select min(makedate) from es_doc_main where doccode =a.missionprop1) x from lwmission a where a.activityid" +
					   		" = '"+"?activityid?"+"' and a.defaultoperator is null order by x");
				   sqlbv8.put("activityid", tLDCodeSet.get(i).getCode());
				   tSSRS1 = sExeSQL.execSQL(sqlbv8);
				   if(tSSRS1.MaxRow>0&&tSSRS.MaxRow>0)
				   {
					   int l=1;
					   //将所有公共池中的保单全部加锁，分配完后再解锁
					   for(int k=1;k<=tSSRS1.MaxRow;k++)
					   {
						   boolean tLockFlag = true;
						   if (!lockNo(tSSRS1.GetText(k, 1))) {
							   logger.debug("锁定号码失败!");
							   this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
							   tLockFlag = false;
							   //mPubLock.unLock();
							   return false;
						   }// 锁定主附险投保单号以及暂收费号码)
					   }
					   //int k=1;
				lwmission:
				   for(int j=1;j<=tSSRS1.MaxRow;j++)
				   {
					   if(l>tSSRS.MaxRow)
					   {
						   l=1;
					   }
					   for(int k =l;k<=tSSRS.MaxRow;k++)
					   {
						   //if(tSSRS.GetText(k, 1))
						   String tcount =(String)mParameters.get(tSSRS.GetText(k, 1));
						   if(!(tcount == null)&&!tcount.equals("15"))
						   {
							   int count = Integer.parseInt(tcount);
							   count++;
							   if (!(count>Integer.parseInt(tSSRS.GetText(k, 2))))
							   {
								    tcount = Integer.toString(count);
									mParameters.put(tSSRS.GetText(k, 1), tcount);
//									if (!lockNo(tSSRS1.GetText(j, 1))) {
//										logger.debug("锁定号码失败!");
//										this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
//										tLockFlag = false;
//										//mPubLock.unLock();
//										return false;
//									}// 锁定主附险投保单号以及暂收费号码)
									String sqlUpdate1 = "update lwmission set defaultoperator ='"+"?defaultoperator?"+"' ,modifydate ='"+"?tdate?"+"',modifytime = '"+"?ttime?"+"' where activityid ='"+"?activityid?"+"' and missionprop1 ='"+"?missionprop1?"+"'";
									SQLwithBindVariables sqlbve = new SQLwithBindVariables();
									sqlbve.sql(sqlUpdate1);
									sqlbve.put("defaultoperator",tSSRS.GetText(k, 1));
									sqlbve.put("tdate",tdate);
									sqlbve.put("ttime",ttime);
									sqlbve.put("activityid",tSSRS1.GetText(j, 2));
									sqlbve.put("missionprop1",tSSRS1.GetText(j, 1));
									map.put(sqlbve, "UPDATE");
									l++;
									continue lwmission;
									
							   }
//						   else{
//							   //mParameters.put(tSSRS.GetText(k, 1), "0");
//							   continue;
//						   }
						   }else{
							   if(tcount==null)
							   {
								   mParameters.put(tSSRS.GetText(k, 1), "1");
//								   if (!lockNo(tSSRS1.GetText(j, 1))) {
//										logger.debug("锁定号码失败!");
//										this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
//										tLockFlag = false;
//										//mPubLock.unLock();
//										return false;
//									}// 锁定主附险投保单号以及暂收费号码)
								   map.put("update lwmission set defaultoperator ='"+tSSRS.GetText(k, 1)+"' ,modifydate ='"+tdate+"',modifytime = '"+ttime+"' where activityid ='"+tSSRS1.GetText(j, 2)+"' and missionprop1 ='"+tSSRS1.GetText(j, 1)+"'", "UPDATE");
								   l++;
								   continue lwmission;
							   }
						   }
							  
					   }

				   }
				   LDAutoAssignSet tldautoSet = new LDAutoAssignSet();
				   
				   LDAutoAssignSet tLDAutoAssignSet_i =new LDAutoAssignSet();
				   LDAutoAssignSet tLDAutoAssignSet1_i =new LDAutoAssignSet();
				   //String[] operate =new String[tLDAssignPlanSet.size()];
				     for(int n=1;n<=tSSRS.MaxRow;n++)
				     {
				    	 tLDAutoAssignSchema =new LDAutoAssignSchema();
				    	 tLDAutoAssignSchema.setTaskNo(tSSRS.GetText(n, 3));
				    	 tLDAutoAssignSchema.setActivityid(tSSRS.GetText(n, 4));
				    	 tLDAutoAssignSchema.setServiceName(tSSRS.GetText(n, 5));
				    	 tLDAutoAssignSchema.setAssignNo(tSSRS.GetText(n, 1));
				    	 tLDAutoAssignSchema.setPlanAmount(tSSRS.GetText(n, 6));
				    	 //mParameters.remove(tLDAssignPlanSet.get(n).getassignno());
				    	 tLDAutoAssignSchema.setOperator(tG.Operator);
				    	 tLDAutoAssignSchema.setTaskStartTime(tSSRS.GetText(n, 7));
				    	 tLDAutoAssignSchema.setTaskEndTime(tSSRS.GetText(n, 8));
				    	 //判断是否为继续分配
				    	 tLDAutoAssignDB.setActivityid(tLDCodeSet.get(i).getCode());
				    	 tLDAutoAssignDB.setAssignNo(tSSRS.GetText(n, 1));
				    	 tldautoSet = tLDAutoAssignDB.query();
				    	 String actamount = (String)mParameters.get(tSSRS.GetText(n, 1));//如果第一次没有给该节点该业务员分配保单，则将其置为“0”，此种情况只可能出现在第一次插入时
				    		if("".equals(actamount)||actamount==null)
				    			{
				    			   actamount = "0";
				    			}
				    	if(tldautoSet.size()<=0)
				    	{
				    		//operate[n-1]="INSERT";//因为tldautoSet只能够查处一条记录...
				    		 //任务第一次分配
//			    		String actamount = (String)mParameters.get(tSSRS.GetText(n, 1));//如果第一次没有给该节点该业务员分配保单，则将其置为“0”，此种情况只可能出现在第一次插入时
//			    		if("".equals(actamount)||actamount==null)
//			    			{
//			    			   actamount = "0";
//			    			}
				    	 tLDAutoAssignSchema.setActuallyAmount(actamount);
				    	 mParameters.remove(tSSRS.GetText(n, 1));
					     tLDAutoAssignSchema.setMakeDate(tdate);
					     tLDAutoAssignSchema.setMakeTime(ttime);
					     tLDAutoAssignSchema.setModifyDate(tdate);
					     tLDAutoAssignSchema.setModifyTime(ttime);
					     tLDAutoAssignSet_i.add(tLDAutoAssignSchema);
				        }else
				        {
				        	//operate[n-1]="UDPATE";
				         //将本次分配和上次分配相加
				         int lastactually = tldautoSet.get(1).getActuallyAmount();
				         int actually = Integer.parseInt(actamount);
				         mParameters.remove(tSSRS.GetText(n, 1));
				         String actuallyamount = Integer.toString(lastactually+actually);
				         tLDAutoAssignSchema.setActuallyAmount(actuallyamount);
				         tLDAutoAssignSchema.setMakeDate(tSSRS.GetText(n, 9));
						 tLDAutoAssignSchema.setMakeTime(tSSRS.GetText(n, 10));
						 tLDAutoAssignSchema.setModifyDate(tdate);
						 tLDAutoAssignSchema.setModifyTime(ttime);
						 tLDAutoAssignSet1_i.add(tLDAutoAssignSchema);
//					 String autosql = "select * from LDAutoAssign where activityid='"+tLDAssignPlanSet.get(n).getactivityid()+"'";
//					 tldautoSet = tLDAutoAssignDB.executeQuery(autosql);
						 //String tSerialNo ="";
				        }
				     }
				     map.put(tLDAutoAssignSet_i, "INSERT");
				     map.put(tLDAutoAssignSet1_i, "UPDATE");
				     //如果LDAutoAssign表中没有此条数据则执行“INSERT” 否则“UPDATE”
//			     for(int j=0;j<operate.length;j++)
//			     {
//			    	 if(operate[j].equals("INSERT")){
//			    		 
//			    		 map.put(tLDAutoAssignSet_i, "INSERT");
//			    	 }
//			    	 else{
//			    		 map.put(tLDAutoAssignSet_i, "UPDATE");
//			    	 }
//			     }
//			     if(tldautoSet.size()<=0)
//			    	{
//			    	 map.put(tLDAutoAssignSet_i, "INSERT");
//			    	}else
//			    	{
//			    	 //map.put(tLDAutoAssigntraceSet, "INSERT");//重复传入......
//			         map.put(tLDAutoAssignSet_i, "UPDATE");
//			    	}
				    }
				   
			   }  
				   //
			   }
			   
			   
		   //}
		   
		  }
		   else
		  {
			  logger.debug("计划表中没有任何计划！");
		  }
		  
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
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally
	{
		    //liuqh 2008-09-17 modify
			// 使用新的加锁逻辑
			mPubLock.unLock();
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

    public static void main(String[] args)
    {
    	AutoAssignDuty tAutoAssignDuty = new AutoAssignDuty();
    	tAutoAssignDuty.dealMain();
    	logger.debug("测试完毕！");
    }
}
