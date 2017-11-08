/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskServerDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.schema.LDTaskServerParamSchema;
import com.sinosoft.lis.schema.LDTaskServerSchema;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskServerParamSet;
import com.sinosoft.lis.vschema.LDTaskServerSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 批处理引擎服务结点管理
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
 * 
 * @version 1.0
 */
public class TaskServerManager 
{
private static Logger logger = Logger.getLogger(TaskServerManager.class);

	
	public TaskServerManager() {
		
	}
	 
	/**
	 * 初始化服务结点信息
	 * 随着任务引擎的启动而调用
	 * @return
	 */
	public static boolean initCurrentServerInfo()
	{
		try {
			LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
			LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
			String tServerIP = MultiTaskServer.getServerIP();
			String tSetverPort = MultiTaskServer.getServerPort();
			
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			//查询是否已有记录
			tLDTaskServerDB.setServerIP(tServerIP);
			tLDTaskServerDB.setServerPort(tSetverPort);
			if(tLDTaskServerDB.getInfo())
			{
				tLDTaskServerSchema.setSchema(tLDTaskServerDB.getSchema());
			}
			else
			{
				tLDTaskServerSchema.setServerIP(tServerIP);
				tLDTaskServerSchema.setServerPort(tSetverPort);
				tLDTaskServerSchema.setOperator("Auto");
				tLDTaskServerSchema.setMakeDate(tCurrentDate);
				tLDTaskServerSchema.setMakeTime(tCurrentTime);
				tLDTaskServerSchema.setValidFlag("1");
				//如果是新服务结点,在启动时默认设置为0
				tLDTaskServerSchema.setPlanNum("0");
			}
			//初始化默认设置有效标记为1
			tLDTaskServerSchema.setValidFlag("1");
			tLDTaskServerSchema.setModifyDate(tCurrentDate);
			tLDTaskServerSchema.setModifyTime(tCurrentTime);
			
			
			//查询是否未设置参数
			String tSQL_Param = "select code,codename,codealias  from ldcode where codetype='taskserverparam' "
				              + " and  code not in (select paramname from LDTaskServerParam where serverip='"+"?tServerIP?"+"' and serverport='"+"?tSetverPort?"+"')"; 
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Param);
			sqlbv1.put("tServerIP", tServerIP);
			sqlbv1.put("tSetverPort", tSetverPort);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv1);
			LDTaskServerParamSet tLDTaskServerParamSet = new LDTaskServerParamSet();
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				String tParamName = tSSRS.GetText(i, 1);
				//String tParamValue = tSSRS.GetText(i, 2);
				String tDefaultValue = tSSRS.GetText(i, 3);
				
				LDTaskServerParamSchema tLDTaskServerParamSchema = new LDTaskServerParamSchema();
				tLDTaskServerParamSchema.setServerIP(tServerIP);
				tLDTaskServerParamSchema.setServerPort(tSetverPort);
				tLDTaskServerParamSchema.setParamName(tParamName);
				tLDTaskServerParamSchema.setParamValue(tDefaultValue);
				
				tLDTaskServerParamSet.add(tLDTaskServerParamSchema);
			}
				
			MMap mMap = new MMap();
			VData mData = new VData();
			mMap.put(tLDTaskServerSchema, "DELETE&INSERT");
			if(tLDTaskServerParamSet.size()>0)
			{
				mMap.put(tLDTaskServerParamSet, "DELETE&INSERT");
			}
			mData.add(mMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if(!tPubSubmit.submitData(mData, ""))
			{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static void refreshServerInfo()
	{
		try {
			LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			
			LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
			String tServerIP = MultiTaskServer.getServerIP();
			String tSetverPort = MultiTaskServer.getServerPort();
			tLDTaskServerDB.setServerIP(tServerIP);
			tLDTaskServerDB.setServerPort(tSetverPort);
			if(tLDTaskServerDB.getInfo())
			{
				tLDTaskServerSchema.setSchema(tLDTaskServerDB.getSchema());
				tLDTaskServerSchema.setModifyDate(tCurrentDate);
				tLDTaskServerSchema.setModifyTime(tCurrentTime);
				//只要能刷新到,就把有效标记设置为1
				tLDTaskServerSchema.setValidFlag("1");
				
				String tPlanMum = " select count(distinct taskplancode) from ldtaskparam  "
					            + " where paramname='serverip:port' and paramvalue=concat(concat('"+"?tServerIP?"+"',':'),'"+"?tSetverPort?"+"')";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tPlanMum);
				sqlbv2.put("tServerIP", tServerIP);
				sqlbv2.put("tSetverPort", tSetverPort);
				tLDTaskServerSchema.setPlanNum((new ExeSQL().getOneValue(sqlbv2)));
				MMap mMap = new MMap();
				VData mData = new VData();
				mMap.put(tLDTaskServerSchema, "DELETE&INSERT");
				mData.add(mMap);
				PubSubmit tPubSubmit = new PubSubmit();
				tPubSubmit.submitData(mData, "");
				
			}
			
			//增加刷新失效服务器的处理
			updateInvalidServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void invalidServerInfo()
	{
		try {
			LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			
			LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
			String tServerIP = MultiTaskServer.getServerIP();
			String tSetverPort = MultiTaskServer.getServerPort();
			tLDTaskServerDB.setServerIP(tServerIP);
			tLDTaskServerDB.setServerPort(tSetverPort);
			if(tLDTaskServerDB.getInfo())
			{
				tLDTaskServerSchema.setSchema(tLDTaskServerDB.getSchema());
				tLDTaskServerSchema.setModifyDate(tCurrentDate);
				tLDTaskServerSchema.setModifyTime(tCurrentTime);
				tLDTaskServerSchema.setValidFlag("0");
				MMap mMap = new MMap();
				VData mData = new VData();
				mMap.put(tLDTaskServerSchema, "DELETE&INSERT");
				mData.add(mMap);
				PubSubmit tPubSubmit = new PubSubmit();
				tPubSubmit.submitData(mData, "");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是当前服务器的任务
	 * @param tLDTaskParamSet
	 * @param tRunType 0,单节点运行,不判断,1-分结点运行,判断
	 * @return
	 */
	public static boolean isCurrentServerPlan(LDTaskParamSet tLDTaskParamSet,String tRunType)
	{
		if(tRunType!=null&&tRunType.equals("0"))
		{
			return true;
		}
		String tServerInfo = MultiTaskServer.getServerIP() + ":" + MultiTaskServer.getServerPort();
		if(tLDTaskParamSet==null||tLDTaskParamSet.size()<=0)
		{
			return false;
		}
		for(int i=1;i<=tLDTaskParamSet.size();i++)
		{
			LDTaskParamSchema tLDTaskParamSchema = new LDTaskParamSchema();
			tLDTaskParamSchema.setSchema(tLDTaskParamSet.get(i));
			String tParamName = tLDTaskParamSchema.getParamName();
			String tParamValue = tLDTaskParamSchema.getParamValue();
			if(tParamName!=null&&tParamName.toLowerCase().equals("serverip:port"))
			{
				if(tParamValue!=null&&tParamValue.equals(tServerInfo))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取失效的服务节点的数据
	 * @return
	 */
	public static LDTaskServerSet getInvalidTaskServerSet()
	{
		LDTaskServerSet tInvalidLDTaskServerSet = new LDTaskServerSet(); 
		
		//获取系统设置的失效时间
		String tInvalidTime = MultiTaskServer.getInvalidTime();
		int invalidTime = -1;
		try {
			invalidTime = Integer.parseInt(tInvalidTime);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			invalidTime = -1;
		}
		//不校验,直接返回
		if(invalidTime==-1)
		{
			return tInvalidLDTaskServerSet;
		}
		
		SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		LDTaskServerSet tLDTaskServerSet = new LDTaskServerSet();
		LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
		//获取所有服务节点的数据,排除备份节点
		String tSQL_Server = "select * from  ldtaskserver a where not exists ( "
			               + " select 1 from ldtaskserverparam where serverip=a.serverip and serverport=a.serverport " 
			               + " and lower(paramname)='backnode' and paramvalue='Y') " 
			               + " order by serverip,serverport ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL_Server);
		tLDTaskServerSet = tLDTaskServerDB.executeQuery(sqlbv3);
		for(int i=1;i<=tLDTaskServerSet.size();i++)
		{
			LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
			tLDTaskServerSchema.setSchema(tLDTaskServerSet.get(i));
			
			//String tCurrentDate = PubFun.getCurrentDate();
			//String tCurrentTime = PubFun.getCurrentTime();
			Date tCurrentTime = new Date();
			long currentTime = tCurrentTime.getTime();
			
			String tModifyDate = tLDTaskServerSchema.getModifyDate();
			String tModifyTime = tLDTaskServerSchema.getModifyTime();
			long validTime = 0;
			try {
				Date tValidDate = tSDF.parse(tModifyDate+" "+tModifyTime);
				validTime = tValidDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			long diff = (currentTime - validTime)/(1000 * 60 ) ;
			
			//MultiTaskServer.outPrint("CurrenDate:"+PubFun.getCurrentDate()+":CurrentTime:"+PubFun.getCurrentTime());
			//MultiTaskServer.outPrint("currentTime:"+currentTime+":validTime:"+validTime+":diff:"+diff);
			if(invalidTime<diff)
			{
				//失效了
				MultiTaskServer.outPrint("Invalid ServerIP:"+tLDTaskServerSchema.getServerIP()+":ServerPort:"+tLDTaskServerSchema.getServerPort()+":ModifyDate:"+tLDTaskServerSchema.getModifyDate()+":ModifyTime:"+tLDTaskServerSchema.getModifyTime());
				tInvalidLDTaskServerSet.add(tLDTaskServerSchema);
			}
		}
		
		return tInvalidLDTaskServerSet;
	}
	
	/**
	 * 判断是否失效的服务器
	 * @param tLDTaskServerSchema
	 * @return
	 */
	public static boolean isInvalidTaskServer(LDTaskServerSchema tLDTaskServerSchema)
	{
		//获取系统设置的失效时间
		String tInvalidTime = MultiTaskServer.getInvalidTime();
		int invalidTime = -1;
		try {
			invalidTime = Integer.parseInt(tInvalidTime);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			invalidTime = -1;
		}
		//不校验,直接返回
		if(invalidTime==-1)
		{
			return false;
		}
		SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date tCurrentTime = new Date();
		long currentTime = tCurrentTime.getTime();
		
		String tModifyDate = tLDTaskServerSchema.getModifyDate();
		String tModifyTime = tLDTaskServerSchema.getModifyTime();
		long validTime = 0;
		try {
			Date tValidDate = tSDF.parse(tModifyDate+" "+tModifyTime);
			validTime = tValidDate.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		long diff = (currentTime - validTime)/(1000 * 60 ) ;
		if(invalidTime<diff)
		{
			//失效
			MultiTaskServer.outPrint("isInvalidTaskServer Invalid ServerIP:"+tLDTaskServerSchema.getServerIP()+":ServerPort:"+tLDTaskServerSchema.getServerPort()+":ModifyDate:"+tLDTaskServerSchema.getModifyDate()+":ModifyTime:"+tLDTaskServerSchema.getModifyTime());
			return true;
		}
		return false;
	}
	
	/**
	 * 定时将无效的节点更新为失效状态
	 */
	public static void updateInvalidServer()
	{
		PubConcurrencyLock mPubLock = new PubConcurrencyLock();
		try {
			//加锁,保证同一时间只有一个程序处理失效的服务节点
			if(mPubLock.lock("updateInvalidServer", "LD0004"))
			{
				String tSQL_TaskServer = "select * from ldtaskserver where validflag='1'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL_TaskServer);
				LDTaskServerSet tLDTaskServerSet = new LDTaskServerSet();
				LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
				LDTaskServerSet finalLDTaskServerSet = new LDTaskServerSet();
				tLDTaskServerSet = tLDTaskServerDB.executeQuery(sqlbv4);
				for(int i=1;i<=tLDTaskServerSet.size();i++)
				{
					LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
					tLDTaskServerSchema=tLDTaskServerSet.get(i);
					if(isInvalidTaskServer(tLDTaskServerSchema))
					{
						tLDTaskServerSchema.setValidFlag("0");
						finalLDTaskServerSet.add(tLDTaskServerSchema);
					}
				}
				
				if(finalLDTaskServerSet.size()>0)
				{
					MMap mMap = new MMap();
					VData mData = new VData();
					mMap.put(finalLDTaskServerSet, "DELETE&INSERT");
					mData.add(mMap);
					PubSubmit tPubSubmit = new PubSubmit();
					tPubSubmit.submitData(mData, "");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			mPubLock.unLock();
		}
		
	}
	
	public static LDTaskServerSet getValidBakNodeTaskServerSet()
	{
		LDTaskServerSet tValidBakLDTaskServerSet = new LDTaskServerSet(); 
		
		//获取系统设置的失效时间
		String tInvalidTime = MultiTaskServer.getInvalidTime();
		int invalidTime = -1;
		try {
			invalidTime = Integer.parseInt(tInvalidTime);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			invalidTime = -1;
		}
		//-1时,不做任务转移,不返还备份节点的信息
		if(invalidTime==-1)
		{
			return tValidBakLDTaskServerSet;
		}
		
		SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		LDTaskServerSet tLDTaskServerSet = new LDTaskServerSet();
		LDTaskServerDB tLDTaskServerDB = new LDTaskServerDB();
		//获取所有服务节点的数据,排除备份节点
		String tSQL_Server = "select * from  ldtaskserver a where exists ( "
			               + " select 1 from ldtaskserverparam where serverip=a.serverip and serverport=a.serverport " 
			               + " and lower(paramname)='baknode' and paramvalue='Y') " 
			               + " and validflag='1' "
			               + " order by serverip,serverport ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL_Server);
		tLDTaskServerSet = tLDTaskServerDB.executeQuery(sqlbv5);
		for(int i=1;i<=tLDTaskServerSet.size();i++)
		{
			LDTaskServerSchema tLDTaskServerSchema = new LDTaskServerSchema();
			tLDTaskServerSchema.setSchema(tLDTaskServerSet.get(i));
			
			//String tCurrentDate = PubFun.getCurrentDate();
			//String tCurrentTime = PubFun.getCurrentTime();
			Date tCurrentTime = new Date();
			long currentTime = tCurrentTime.getTime();
			
			String tModifyDate = tLDTaskServerSchema.getModifyDate();
			String tModifyTime = tLDTaskServerSchema.getModifyTime();
			long validTime = 0;
			try {
				Date tValidDate = tSDF.parse(tModifyDate+" "+tModifyTime);
				validTime = tValidDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			
			long diff = (currentTime - validTime)/(1000 * 60 ) ;
			
			//MultiTaskServer.outPrint("CurrenDate:"+PubFun.getCurrentDate()+":CurrentTime:"+PubFun.getCurrentTime());
			//MultiTaskServer.outPrint("currentTime:"+currentTime+":validTime:"+validTime+":diff:"+diff);
			if(invalidTime>diff)
			{
				//有效的备份节点
				MultiTaskServer.outPrint("ValidBakNode:ServerIP:"+tLDTaskServerSchema.getServerIP()+":ServerPort:"+tLDTaskServerSchema.getServerPort()+":ModifyDate:"+tLDTaskServerSchema.getModifyDate()+":ModifyTime:"+tLDTaskServerSchema.getModifyTime());
				tValidBakLDTaskServerSet.add(tLDTaskServerSchema);
			}
		}
		
		return tValidBakLDTaskServerSet;
	}
	
	//为了统一各个服务器的时间,使用数据库的时间.
	
	
	public static void main(String[] args) {
//		Vector tVector = new Vector();
//		for(int i=1;i<=5;i++)
//		{
//			tVector.add(i);
//		}
//		logger.debug(tVector.size());
//		
//		for(int i=0;i<tVector.size();i++)
//		{
//			tVector.remove(i);
//			for(int n=0;n<tVector.size();n++)
//			{
//				logger.debug("%%!@!%:"+(Integer)tVector.get(n));
//			}
//			logger.debug("%%%%:"+tVector.size());
//		}
		LDTaskServerSet t = TaskServerManager.getInvalidTaskServerSet();
		for(int i=1;i<=t.size();i++)
		{
			logger.debug(t.get(i).getServerIP()+":"+t.get(i).getServerPort());
		}
		
		 t = TaskServerManager.getValidBakNodeTaskServerSet();
		for(int i=1;i<=t.size();i++)
		{
			logger.debug(t.get(i).getServerIP()+":"+t.get(i).getServerPort());
		}
	}
}
