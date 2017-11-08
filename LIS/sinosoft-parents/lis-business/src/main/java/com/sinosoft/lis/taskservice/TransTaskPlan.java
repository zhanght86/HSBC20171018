/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskParamDB;
import com.sinosoft.lis.db.LDTaskPlanTransDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.schema.LDTaskPlanTransBakSchema;
import com.sinosoft.lis.schema.LDTaskPlanTransSchema;
import com.sinosoft.lis.schema.LDTaskServerSchema;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanTransBakSet;
import com.sinosoft.lis.vschema.LDTaskPlanTransSet;
import com.sinosoft.lis.vschema.LDTaskServerParamSet;
import com.sinosoft.lis.vschema.LDTaskServerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 批处理引擎参数配置
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>

 */
public class TransTaskPlan {
private static Logger logger = Logger.getLogger(TransTaskPlan.class);
	public CErrors mErrors = new CErrors();
	private String mOperate = "";
	private String mOperator = "";
	private LDTaskParamSet mLDTaskParamSet = new LDTaskParamSet();
	private LDTaskServerParamSet mLDTaskServerParamSet = new LDTaskServerParamSet();
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	private Reflections mReflections = new Reflections();
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	public TransTaskPlan() {
	}

	/**
	 * 
	 * @param aInputData
	 * @param aOperate trans:将任务转交备份节点执行  release:将任务交还原节点
	 * @return
	 */
	public boolean submitData(VData aInputData, String aOperate) {
		mOperate = aOperate;
		
		//先判断当前节点是否是备份节点
		String tBackNode = MultiTaskServer.getBakNode();
		if(tBackNode.toLowerCase().equals("n"))
		{
			//非备份节点直接返回
			return true;
		}
		else
		{
			if(mOperate.toLowerCase().equals("trans"))
			{
				//转移任务
				transTaskPlan();
			}
			else 
			{
				//交回任务
				releaseTaskPlan();
				
			}
		}
		mResult.add(mMap);

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, "")) {
			mErrors.addOneError(new CError("任务数据提交失败！"));

			return false;
		}

		return true;
	}

	//转移任务
	private boolean transTaskPlan()
	{
		//分配任务
		AllocationTaskPlan();
		//各自处理各自的任务，修改ldtaskparam中的IP和端口,便于任务扫描到
		transTask();
		return true;
	}

	//转移相应的任务
	private void transTask()
	{
		String tServerIP = MultiTaskServer.getServerIP();
		String tServerPort = MultiTaskServer.getServerPort();
		String tSQL_Current = "select * from LDTaskPlanTrans where state='0' and newserverip='"+"?tServerIP?"+"' and newserverport='"+"?tServerPort?"+"' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Current);
		sqlbv1.put("tServerIP", tServerIP);
		sqlbv1.put("tServerPort", tServerPort);
		LDTaskPlanTransSet tLDTaskPlanTransSet = new LDTaskPlanTransSet();
		LDTaskPlanTransDB tLDTaskPlanTransDB = new LDTaskPlanTransDB();
		tLDTaskPlanTransSet = tLDTaskPlanTransDB.executeQuery(sqlbv1);
		
		
		LDTaskPlanTransSet tFinalLDTaskPlanTransSet = new LDTaskPlanTransSet();
		LDTaskParamSet tFinalLDTaskParamSet = new LDTaskParamSet();
		for(int i=1;i<=tLDTaskPlanTransSet.size();i++)
		{
			LDTaskPlanTransSchema tLDTaskPlanTransSchema = new LDTaskPlanTransSchema();
			tLDTaskPlanTransSchema = tLDTaskPlanTransSet.get(i);
			tLDTaskPlanTransSchema.setState("1");
			
			LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
			LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
			String tOldIP =  tLDTaskPlanTransSchema.getOldServerIP();
			String tOldPort = tLDTaskPlanTransSchema.getOldServerPort();
			
			String tSQL_Param = "select * from ldtaskparam where taskplancode = '"+"?taskplancode?"+"' "
			                  + " and paramname='serverip:port' and paramvalue=concat(concat('"+"?tOldIP?"+"',':'),'"+"?tOldPort?"+"') ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL_Param);
			sqlbv2.put("taskplancode", tLDTaskPlanTransSchema.getTaskPlanCode());
			sqlbv2.put("tOldIP", tOldIP);
			sqlbv2.put("tOldPort", tOldPort);
			
			tLDTaskParamSet = tLDTaskParamDB.executeQuery(sqlbv2);
			String tValue = tOldIP+":"+tOldPort;
			String tNewValue = tLDTaskPlanTransSchema.getNewServerIP()+":" + tLDTaskPlanTransSchema.getNewServerPort();
			for(int n=1;n<=tLDTaskParamSet.size();n++)
			{
				tLDTaskParamSet.get(n).setParamValue(tNewValue);
			}
			
			if(tLDTaskParamSet.size()>0)
			{
				tFinalLDTaskParamSet.add(tLDTaskParamSet);
			}
			tFinalLDTaskPlanTransSet.add(tLDTaskPlanTransSchema);
		}
		
		 MMap tMap = new MMap();
		 VData tResult = new VData();
		 if(tFinalLDTaskPlanTransSet.size()>0)
		 {
			  tMap.put(tFinalLDTaskPlanTransSet,"DELETE&INSERT");
		 }
		 if(tFinalLDTaskParamSet.size()>0)
		 {
			  tMap.put(tFinalLDTaskParamSet,"DELETE&INSERT");
		 }
		 
		 tResult.add(tMap);
		 PubSubmit tPubSubmit = new PubSubmit();

		 if (!tPubSubmit.submitData(tResult, "")) {
				mErrors.addOneError(new CError("任务数据提交失败！"));
		 }
	}
	
	
	/**
	 * 分配失效的任务
	 */
	private void AllocationTaskPlan() {
		//扫描失效节点
		//只能有一个进程处理失效的任务,其他都跳过
		try {
			if(this.mPubLock.lock("transTaskPlan","LD0003"))
			{
				String tCurrentDate = PubFun.getCurrentDate();
				String tCurrentTime = PubFun.getCurrentTime();
				//获取失效的节点
				LDTaskServerSet tLDTaskServerSet = TaskServerManager.getInvalidTaskServerSet();
				//获取备份节点
				LDTaskServerSet tBakNodeLDTaskServerSet = TaskServerManager.getValidBakNodeTaskServerSet();
				if(tLDTaskServerSet.size()>0&&tBakNodeLDTaskServerSet.size()>0)
				{
					//有失效的节点并且有备份节点再做处理
					//获取所有待处理的任务
					LDTaskParamSet tAllLDTaskParamSet = new LDTaskParamSet(); 
					
					for(int i=1;i<=tLDTaskServerSet.size();i++)
					{
						String tServerIP = tLDTaskServerSet.get(i).getServerIP();
						String tServerPort = tLDTaskServerSet.get(i).getServerPort();
						LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
						LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
						String tSQL_Param = "select * from ldtaskparam a where paramname='serverip:port' "
							              + " and paramvalue=concat(concat('"+"?tServerIP?"+"',':'),'"+"?tServerPort?"+"') "
							              + " and exists (select 1 from ldtaskplan where taskcode=a.taskcode "
							              + " and taskplancode=a.taskplancode and runflag='1')"
							              + " and not exists (select 1 from LDTaskPlanTrans where taskplancode=a.taskplancode)";
						MultiTaskServer.outPrint("tSQL_Param:"+tSQL_Param);
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSQL_Param);
						sqlbv3.put("tServerIP", tServerIP);
						sqlbv3.put("tServerPort", tServerPort);
						tLDTaskParamSet = tLDTaskParamDB.executeQuery(sqlbv3);
						if(tLDTaskParamSet.size()>0)
						{
							tAllLDTaskParamSet.add(tLDTaskParamSet);
						}	
					}
					
					MultiTaskServer.outPrint("tAllLDTaskParamSet.size():"+tAllLDTaskParamSet.size());
					MultiTaskServer.outPrint("tBakNodeLDTaskServerSet.size():"+tBakNodeLDTaskServerSet.size());
					int tTaskSize = tAllLDTaskParamSet.size();
					int tBakNodeSize = tBakNodeLDTaskServerSet.size();
					//分配次数
					int tTimes = 0;
					if(tTaskSize%tBakNodeSize==0)
					{
						//正好
						tTimes = tTaskSize/tBakNodeSize ;
					}
					else
					{
						tTimes = tTaskSize/tBakNodeSize + 1;
					}
					
					int tUnit = 0;
					tUnit = tTaskSize/tBakNodeSize;
					
					MultiTaskServer.outPrint("tTimes:"+tTimes);
					LDTaskPlanTransSet tLDTaskPlanTransSet = new LDTaskPlanTransSet();
					//任务分配
					for(int i=1;i<=tBakNodeSize;i++)
					{
						LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
						tLDTaskServerSchema = tBakNodeLDTaskServerSet.get(i);
						if(i!=tBakNodeSize)
						{
							for(int n=(i-1)*tUnit+1 ;n<=i*tUnit;n++)
							{
								LDTaskParamSchema tLDTaskParamSchema  = new LDTaskParamSchema();
								tLDTaskParamSchema = tAllLDTaskParamSet.get(n);
								LDTaskPlanTransSchema tLDTaskPlanTransSchema = new LDTaskPlanTransSchema();
								String[] tIPS = tLDTaskParamSchema.getParamValue().split(":");
								tLDTaskPlanTransSchema.setOldServerIP(tIPS[0]);
								tLDTaskPlanTransSchema.setOldServerPort(tIPS[1]);
								tLDTaskPlanTransSchema.setNewServerIP(tLDTaskServerSchema.getServerIP());
								tLDTaskPlanTransSchema.setNewServerPort(tLDTaskServerSchema.getServerPort());
								tLDTaskPlanTransSchema.setTaskPlanCode(tLDTaskParamSchema.getTaskPlanCode());
								
								tLDTaskPlanTransSchema.setState("0");
								
								tLDTaskPlanTransSchema.setMakeDate(tCurrentDate);
								tLDTaskPlanTransSchema.setMakeTime(tCurrentTime);
								tLDTaskPlanTransSchema.setModifyDate(tCurrentDate);
								tLDTaskPlanTransSchema.setModifyTime(tCurrentTime);
							
								tLDTaskPlanTransSet.add(tLDTaskPlanTransSchema);
							}
						}
						else if(i==tBakNodeSize)
						{
							//最后一个再把剩下的都拿走
							int tReadySize = tLDTaskPlanTransSet.size();
							for(int n=tReadySize+1;n<=tAllLDTaskParamSet.size();n++)
							{
								LDTaskParamSchema tLDTaskParamSchema  = new LDTaskParamSchema();
								tLDTaskParamSchema = tAllLDTaskParamSet.get(n);
								LDTaskPlanTransSchema tLDTaskPlanTransSchema = new LDTaskPlanTransSchema();
								String[] tIPS = tLDTaskParamSchema.getParamValue().split(":");
								tLDTaskPlanTransSchema.setOldServerIP(tIPS[0]);
								tLDTaskPlanTransSchema.setOldServerPort(tIPS[1]);
								tLDTaskPlanTransSchema.setNewServerIP(tLDTaskServerSchema.getServerIP());
								tLDTaskPlanTransSchema.setNewServerPort(tLDTaskServerSchema.getServerPort());
								tLDTaskPlanTransSchema.setTaskPlanCode(tLDTaskParamSchema.getTaskPlanCode());

								tLDTaskPlanTransSchema.setState("0");
								tLDTaskPlanTransSchema.setMakeDate(tCurrentDate);
								tLDTaskPlanTransSchema.setMakeTime(tCurrentTime);
								tLDTaskPlanTransSchema.setModifyDate(tCurrentDate);
								tLDTaskPlanTransSchema.setModifyTime(tCurrentTime);
							
								tLDTaskPlanTransSet.add(tLDTaskPlanTransSchema);
							}
						}
					}
					
				   MMap tMap = new MMap();
				   VData tResult = new VData();
				   if(tLDTaskPlanTransSet.size()>0)
				   {
					    tMap.put(tLDTaskPlanTransSet,"INSERT");
					    tResult.add(tMap);

						PubSubmit tPubSubmit = new PubSubmit();

						if (!tPubSubmit.submitData(tResult, "")) {
							mErrors.addOneError(new CError("任务数据提交失败！"));
						}
				   }
				}
				
//				LDTaskServerSet tBackLDTaskServerSet = 
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.mPubLock.unLock();
		}
		
	}
	
	//释放任务
	private boolean releaseTaskPlan()
	{
		LDTaskPlanTransDB tLDTaskPlanTransDB = new LDTaskPlanTransDB();
		String tSQL_Trans = "select * from LDTaskPlanTrans where 1=1 "
		                  + " and state='2' and newserverip='"+"?newserverip?"+"' "
		                  + " and newserverport='"+"?newserverport?"+"'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSQL_Trans);
		sqlbv4.put("newserverip", MultiTaskServer.getServerIP());
		sqlbv4.put("newserverport", MultiTaskServer.getServerPort());
		LDTaskPlanTransSet tLDTaskPlanTransSet = new LDTaskPlanTransSet();
		tLDTaskPlanTransSet = tLDTaskPlanTransDB.executeQuery(sqlbv4);
		
		LDTaskPlanTransSet tDeleteLDTaskPlanTransSet = new LDTaskPlanTransSet();
		LDTaskPlanTransBakSet tInsertLDTaskPlanTransBakSet = new LDTaskPlanTransBakSet();
		LDTaskParamSet tUpdateLDTaskParamSet = new LDTaskParamSet();
		for(int i=1;i<=tLDTaskPlanTransSet.size();i++)
		{
			LDTaskPlanTransSchema tLDTaskPlanTransSchema = new LDTaskPlanTransSchema();
			tLDTaskPlanTransSchema = tLDTaskPlanTransSet.get(i);
			LDTaskPlanTransBakSchema tLDTaskPlanTransBakSchema = new LDTaskPlanTransBakSchema();
			tLDTaskPlanTransBakSchema = this.copyLDTaskPlanTrans(tLDTaskPlanTransSchema);
			
			String tOldIP = tLDTaskPlanTransSchema.getOldServerIP();
			String tOldPort = tLDTaskPlanTransSchema.getOldServerPort();
			String tNewIP = tLDTaskPlanTransSchema.getNewServerIP();
			String tNewPort = tLDTaskPlanTransSchema.getNewServerPort();
			
			String tValue = tOldIP + ":" + tOldPort;
			LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
			LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
			String tSQL = "select * from ldtaskparam a where paramname='serverip:port' "
						+ " and paramvalue=concat(concat('"+"?tNewIP?"+"',':'),'"+"?tNewPort?"+"') "
						+ " and taskplancode='"+"?taskplancode?"+"'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("tNewIP", tNewIP);
			sqlbv5.put("tNewPort", tNewPort);
			sqlbv5.put("taskplancode", tLDTaskPlanTransSchema.getTaskPlanCode());
			tLDTaskParamSet =  tLDTaskParamDB.executeQuery(sqlbv5);
			if(tLDTaskParamSet.size()<=0)
			{
				continue;
			}
			for(int n=1;n<=tLDTaskParamSet.size();n++)
			{
				
				tLDTaskParamSet.get(n).setParamValue(tValue);
			}
			tDeleteLDTaskPlanTransSet.add(tLDTaskPlanTransSchema);
			tInsertLDTaskPlanTransBakSet.add(tLDTaskPlanTransBakSchema);
			tUpdateLDTaskParamSet.add(tLDTaskParamSet);
			
		}
		if(mOperate.toLowerCase().equals("releaseall"))
		{
			//全部释放
			 tLDTaskPlanTransSet = new LDTaskPlanTransSet();
			 tLDTaskPlanTransDB = new LDTaskPlanTransDB();
			 tSQL_Trans = "select * from LDTaskPlanTrans where 1=1 "
				                  + " and state in('0','1') and newserverip='"+"?newserverip?"+"' "
				                  + " and newserverport='"+"?newserverport?"+"'";
			 SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			 sqlbv6.sql(tSQL_Trans);
			 sqlbv6.put("newserverip", MultiTaskServer.getServerIP());
			 sqlbv6.put("newserverport", MultiTaskServer.getServerPort());
			 tLDTaskPlanTransSet = tLDTaskPlanTransDB.executeQuery(sqlbv6);
			 for(int i=1;i<=tLDTaskPlanTransSet.size();i++)
			 {
				 LDTaskPlanTransSchema tLDTaskPlanTransSchema = new LDTaskPlanTransSchema();
				 tLDTaskPlanTransSchema = tLDTaskPlanTransSet.get(i);
				 LDTaskPlanTransBakSchema tLDTaskPlanTransBakSchema = new LDTaskPlanTransBakSchema();
				 tLDTaskPlanTransBakSchema = this.copyLDTaskPlanTrans(tLDTaskPlanTransSchema);
					
				 String tOldIP = tLDTaskPlanTransSchema.getOldServerIP();
			     String tOldPort = tLDTaskPlanTransSchema.getOldServerPort();
				 String tNewIP = tLDTaskPlanTransSchema.getNewServerIP();
				 String tNewPort = tLDTaskPlanTransSchema.getNewServerPort();
				
				 String tValue = tOldIP + ":" + tOldPort;
				 if(tLDTaskPlanTransSchema.getState().equals("1"))
				 {
					 LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
					 LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
					 String tSQL = "select * from ldtaskparam a where paramname='serverip:port' "
						 		 + " and paramvalue=concat(concat('"+"?tNewIP?"+"',':'),'"+"?tNewPort?"+"') "
							     + " and taskplancode='"+"?taskplancode?"+"'";
					 SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					 sqlbv7.sql(tSQL);
					 sqlbv7.put("tNewIP", tNewIP);
					 sqlbv7.put("tNewPort", tNewPort);
					 sqlbv7.put("taskplancode", tLDTaskPlanTransSchema.getTaskPlanCode());
				     tLDTaskParamSet =  tLDTaskParamDB.executeQuery(sqlbv7);
				     for(int n=1;n<=tLDTaskParamSet.size();n++)
				     {
						
					    tLDTaskParamSet.get(n).setParamValue(tValue);
				     }
				     tUpdateLDTaskParamSet.add(tLDTaskParamSet);
				 }
				 tDeleteLDTaskPlanTransSet.add(tLDTaskPlanTransSchema);
				 tInsertLDTaskPlanTransBakSet.add(tLDTaskPlanTransBakSchema);
				 
			 }
			 
		}
		
		MMap tMap = new MMap();
		VData tResult = new VData();
		if(tUpdateLDTaskParamSet.size()>0||tDeleteLDTaskPlanTransSet.size()>0)
		{

			
			tMap.put(tDeleteLDTaskPlanTransSet,"DELETE");
			tMap.put(tInsertLDTaskPlanTransBakSet,"DELETE&INSERT");
			tMap.put(tUpdateLDTaskParamSet,"DELETE&INSERT");
			tResult.add(tMap);

			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(tResult, "")) {
		
			}
		 
		}
		
		
		
		
		
		return true;
	}
	
	private LDTaskPlanTransBakSchema copyLDTaskPlanTrans(LDTaskPlanTransSchema tLDTaskPlanTransSchema)
	{
		LDTaskPlanTransBakSchema tLDTaskPlanTransBakSchema = new LDTaskPlanTransBakSchema();
		
		this.mReflections.transFields(tLDTaskPlanTransBakSchema, tLDTaskPlanTransSchema);
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",20);
		tLDTaskPlanTransBakSchema.setSerialNo(tSerialNo);
		return tLDTaskPlanTransBakSchema;
	}

	//完成任务
	public static void completeTaskPlan(String tTaskPlanCode,String tGroupFlag)
	{
		String tSQL_Task = "select taskcode from ldtaskplan where taskplancode='"+"?tTaskPlanCode?"+"' ";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSQL_Task);
		sqlbv8.put("tTaskPlanCode", tTaskPlanCode);
		ExeSQL tExeSQL = new ExeSQL();
		String tFlag = tExeSQL.getOneValue(sqlbv8);
		if(tFlag!=null&&tFlag.indexOf("G")!=-1)
		{
			tFlag = "G";
		}
		else
		{
			tFlag = "T";
		}
		if(tFlag.equals(tGroupFlag))
		{
			//只有标记一样时,才做更新转移任务
			LDTaskPlanTransDB tLDTaskPlanTransDB = new LDTaskPlanTransDB();
			String tSQL_Trans = "select * from LDTaskPlanTrans where taskplancode='"+"?tTaskPlanCode?"+"' "
			                  + " and state='1' and newserverip='"+"?newserverip?"+"' "
			                  + " and newserverport='"+"?newserverport?"+"'";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL_Trans);
			sqlbv9.put("tTaskPlanCode", tTaskPlanCode);
			sqlbv9.put("newserverip", MultiTaskServer.getServerIP());
			sqlbv9.put("newserverport", MultiTaskServer.getServerPort());
			LDTaskPlanTransSet tLDTaskPlanTransSet = new LDTaskPlanTransSet();
			tLDTaskPlanTransSet = tLDTaskPlanTransDB.executeQuery(sqlbv9);
			if(tLDTaskPlanTransSet.size()>0)
			{
				for(int i=1;i<=tLDTaskPlanTransSet.size();i++)
				{
					tLDTaskPlanTransSet.get(i).setState("2");//已执行完
					tLDTaskPlanTransSet.get(i).setModifyDate(PubFun.getCurrentDate());
					tLDTaskPlanTransSet.get(i).setModifyTime(PubFun.getCurrentTime());
				}
				
				 MMap tMap = new MMap();
				 VData tResult = new VData();
				 tMap.put(tLDTaskPlanTransSet,"DELETE&INSERT");
				 tResult.add(tMap);

				 PubSubmit tPubSubmit = new PubSubmit();

				 if (!tPubSubmit.submitData(tResult, "")) {
				
				 }
			}
		}
	}
	
	
	public static void main(String[] args) {
		TransTaskPlan tTransTaskPlan  = new TransTaskPlan();
		tTransTaskPlan.submitData(new VData(), "trans");
	//	tTransTaskPlan.submitData(new VData(), "release");
	
	}
}
