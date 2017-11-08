package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.util.LinkedList;

import com.sinosoft.lis.db.LogBusinessStateDB;
import com.sinosoft.lis.db.LogDomainToItemDB;
import com.sinosoft.lis.db.LogSubjectDB;
import com.sinosoft.lis.schema.LogBusinessStateSchema;
import com.sinosoft.lis.schema.LogTrackResultSchema;
import com.sinosoft.lis.vschema.LogBusinessStateSet;
import com.sinosoft.lis.vschema.LogTrackResultSet;
import com.sinosoft.utility.VData;

public class LogProcessor {
private static Logger logger = Logger.getLogger(LogProcessor.class);
	
	public LogProcessor(){
	}
	
	/**
	 * 日志记录
	 * **/
	public static void log(String LogType,String SubjectID,String SerialNo,String KeyNo,String LogDes,String State)
	{
		addLogItem(LogType,SubjectID,SerialNo,KeyNo,LogDes,State);
	}
	/**
	 * 往日志队列中添加日志记录
	 * **/
	public static void addLogItem (String LogType,String SubjectID,String SerialNo,String KeyNo,String LogDes,String State)
	{
//		String tLimit = PubFun.getNoLimit("86");
//	    String LogNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
//	    LogQueue lq = new LogQueue();
		//01-业务状态 LogBusinessState
		//02-业务过程 LogTrackResult
		//03-处理结果 LogTrackResult
		//04-业务性能 LogTrackResult
		logger.debug("LogType:"+LogType);
		if(LogType!=null&&!LogType.equals(""))
		{
			if(LogType.equals("01"))
			{
				LogBusinessStateSchema _LogBusinessStateSchema = new LogBusinessStateSchema();
				_LogBusinessStateSchema.setSubjectID(SubjectID);
				_LogBusinessStateSchema.setItemID(LogType);
				_LogBusinessStateSchema.setKeyNo(KeyNo);
				_LogBusinessStateSchema.setSerialNo(SerialNo);
				_LogBusinessStateSchema.setState(State);
				_LogBusinessStateSchema.setItemDes(LogDes);
				_LogBusinessStateSchema.setMakeDate(PubFun.getCurrentDate());
				_LogBusinessStateSchema.setMakeTime(PubFun.getCurrentTime());

				LogQueue.putQueue(_LogBusinessStateSchema);
			}
			else if(LogType.equals("02")||LogType.equals("04")||LogType.equals("03"))
			{
				LogTrackResultSchema _LogTrackResultSchema = new LogTrackResultSchema();
//				_LogTrackResultSchema.setLogNo(LogNo);
				_LogTrackResultSchema.setSubjectID(SubjectID);
				_LogTrackResultSchema.setItemID(LogType);
				_LogTrackResultSchema.setSerialNo(SerialNo);
				_LogTrackResultSchema.setKeyNo(KeyNo);
				_LogTrackResultSchema.setItemDes(LogDes);
				_LogTrackResultSchema.setMakeDate(PubFun.getCurrentDate());
				_LogTrackResultSchema.setMakeTime(PubFun.getCurrentTime());
				if(LogType.equals("04"))
				{
					_LogTrackResultSchema.setRemark(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
				}
				LogQueue.putQueue(_LogTrackResultSchema);
			}
		}
		
		/*
		if(State == null || "null".equals(State)){
			LogTrackResultSchema _LogTrackResultSchema = new LogTrackResultSchema();
//			_LogTrackResultSchema.setLogNo(LogNo);
			_LogTrackResultSchema.setSubjectID(SubjectID);
			_LogTrackResultSchema.setItemID(LogType);
			_LogTrackResultSchema.setSerialNo(SerialNo);
			_LogTrackResultSchema.setKeyNo(KeyNo);
			_LogTrackResultSchema.setItemDes(LogDes);
			_LogTrackResultSchema.setMakeDate(PubFun.getCurrentDate());
			_LogTrackResultSchema.setMakeTime(PubFun.getCurrentTime());
			LogQueue.putQueue(_LogTrackResultSchema);
		}else{
			LogBusinessStateSchema _LogBusinessStateSchema = new LogBusinessStateSchema();
			_LogBusinessStateSchema.setSubjectID(SubjectID);
			_LogBusinessStateSchema.setItemID(LogType);
			_LogBusinessStateSchema.setKeyNo(KeyNo);
			_LogBusinessStateSchema.setSerialNo(SerialNo);
			_LogBusinessStateSchema.setState(State);
			_LogBusinessStateSchema.setItemDes(LogDes);
			_LogBusinessStateSchema.setMakeDate(PubFun.getCurrentDate());
			_LogBusinessStateSchema.setMakeTime(PubFun.getCurrentTime());

			LogQueue.putQueue(_LogBusinessStateSchema);
		}*/
	}
	/**
	 * 日志开关设置
	 * **/
	public static boolean getSwitch(String SubjectID,String ItemID)
	{
		if(SubjectID==null||ItemID==null||SubjectID.equals("")||ItemID.equals(""))
		{
			return false;
		}
		LogSubjectDB _LogSubjectDB=new LogSubjectDB();
		_LogSubjectDB.setSubjectID(SubjectID);
		if(_LogSubjectDB.getInfo())
		{
			if(_LogSubjectDB.getSwitch()!=null&&_LogSubjectDB.getSwitch().equals("Y"))
			{
				LogDomainToItemDB _LogDomainToItemDB=new LogDomainToItemDB();
				_LogDomainToItemDB.setSubjectID(SubjectID);
				_LogDomainToItemDB.setItemID(ItemID);
				if(_LogDomainToItemDB.getInfo())
				{
					if(_LogDomainToItemDB.getSwitch()!=null&&_LogDomainToItemDB.getSwitch().equals("Y"))
					{
						return true;
					}else
					{
						return false;
					}
				}else
				{
					return false;
				}
			}else
			{
				return false;
			}
			
		}
		return true;
		
	}
	/**
	 * 日志队列异步处理
	 * **/
	public synchronized static void dealQueue() {
		LinkedList LogList = LogQueue.getList();
		
		logger.debug("LogList.size():"+LogList.size());
        if(LogList.size()>0)
        {
        	MMap map = new MMap();
        	LogTrackResultSet _LogTrackResultSet = new LogTrackResultSet();
        	LogBusinessStateSet _LogBusinessStateSet = new LogBusinessStateSet();
        	for(int i =1; i<=LogList.size();i++){
        		Object o = LogList.get(i-1);        		
        		if(o instanceof LogTrackResultSchema) {
        			LogTrackResultSchema mLogTrackResultSchema = (LogTrackResultSchema)o;
        			if(!getSwitch(mLogTrackResultSchema.getSubjectID(),mLogTrackResultSchema.getItemID()))
        			{
        				continue;
        			}
            		String tLimit = PubFun.getNoLimit("86");
            	    String LogNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        			mLogTrackResultSchema.setLogNo(LogNo);
        			_LogTrackResultSet.add(mLogTrackResultSchema);
        			
				}else if(o instanceof LogBusinessStateSchema){
					LogBusinessStateSchema mLogBusinessStateSchema = (LogBusinessStateSchema)o;
        			if(!getSwitch(mLogBusinessStateSchema.getSubjectID(),mLogBusinessStateSchema.getItemID()))
        			{
        				continue;
        			}
					
        			LogBusinessStateDB tLogBusinessStateDB = new LogBusinessStateDB();
					tLogBusinessStateDB.setSubjectID(mLogBusinessStateSchema.getSubjectID());
					tLogBusinessStateDB.setItemID(mLogBusinessStateSchema.getItemID());
					tLogBusinessStateDB.setKeyNo(mLogBusinessStateSchema.getKeyNo());
					tLogBusinessStateDB.setSerialNo(mLogBusinessStateSchema.getSerialNo());
					
					LogBusinessStateSet tLogBusinessStateSet=new LogBusinessStateSet();
					tLogBusinessStateSet=tLogBusinessStateDB.query();
					if(tLogBusinessStateSet.size()>0)
					{
						String State = mLogBusinessStateSchema.getState();
						String ItemDes=mLogBusinessStateSchema.getItemDes();
						int SerialNo=mLogBusinessStateSchema.getSerialNo();
						mLogBusinessStateSchema = tLogBusinessStateSet.get(1).getSchema();
						
						if(mLogBusinessStateSchema.getState().equals(State))
						{
							continue;
						}
						mLogBusinessStateSchema.setSerialNo(SerialNo);
						mLogBusinessStateSchema.setState(State);	
						mLogBusinessStateSchema.setItemDes(ItemDes);
						mLogBusinessStateSchema.setModifyDate(PubFun.getCurrentDate());
						mLogBusinessStateSchema.setModifyTime(PubFun.getCurrentTime());
					}else
					{		
		        		String tLimit = PubFun.getNoLimit("86");
		        	    String LogNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
						mLogBusinessStateSchema.setLogNo(LogNo);
					}
					
//					String tSQL = "Select * From LogBusinessState Where keytype = '"+mLogBusinessStateSchema.getKeyType()+"' And keyno = '"+mLogBusinessStateSchema.getKeyNo()+"'";
//					LogBusinessStateSet tLogBusinessStateSet = tLogBusinessStateDB.executeQuery(tSQL);
//					if(tLogBusinessStateSet != null && tLogBusinessStateSet.size() > 0){
//						String State = mLogBusinessStateSchema.getState();
//						mLogBusinessStateSchema = tLogBusinessStateSet.get(1);
//						mLogBusinessStateSchema.setState(State);
//					}else{
//						mLogBusinessStateSchema.setLogNo(LogNo);
//					}

					_LogBusinessStateSet.add(mLogBusinessStateSchema);
					map.put(tLogBusinessStateSet, "DELETE");
        		}
        	}
        	
        	
        	map.put(_LogTrackResultSet, "DELETE&INSERT");
        	map.put(_LogBusinessStateSet, "DELETE&INSERT");
        	VData temp = new VData();
    		temp.add(map);
    		PubSubmit t = new PubSubmit();
    		t.submitData(temp, "");
    		LogQueue.getList().clear();
        }
        
    }
	
}
