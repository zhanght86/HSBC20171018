package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.db.LRTemplateTDB;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.lis.vschema.LRTemplateTSet;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;


public class RuleTask implements SyncTask {
private static Logger logger = Logger.getLogger(RuleTask.class);

	private List list = new ArrayList();
	
	private List tasks = new ArrayList();
	
	private int  taskCount =0;
	
	private int  tasksDone = 0;
	
	private boolean done = false;
	
	Map map = new HashMap();
	
	private List listBoms;
	
	//规则模块，根据这个参数来决定执行的业务规则模块
	private String Business;
	//tongmeng 2010-12-15 modify
	//如果外面传过来算法编码,查询时要按照算法编码查询
	private String CalCode;
	static
	{
		RulePool.getInstance().init(8,8,8);	
	}
	
	public RuleTask(List boms,String Business,String calCode){
		this.listBoms = boms;
		this.Business=Business;
		this.CalCode = calCode;
	}

		
	public Object execute() {
		
		//	从LRTemplate里面取出所有满足规则的记录，并且获得记录中的sqlstament, parameter,boms为解析后的BOM
		long startTime = System.currentTimeMillis();
		LRTemplateSet tLRTemplateSet = new LRTemplateSet();
		LRTemplateDB tLRTemplateDB = new LRTemplateDB();
		Map mapVersion = new HashMap();		
		//String templateSql = "select * from lrtemplate where business='"+this.Business+"' and valid='1' and state='7' and startdate<=sysdate and (enddate>=sysdate or enddate is null) ";
		String templateSql = "select * from lrtemplate where 1=1 and valid='1' and state='7' and startdate<=now() and (enddate>=now() or enddate is null) ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		//tongmeng 2011-05-25 modify 
		//此处可能不传入business
		if(this.Business!=null&&!this.Business.equals(""))
		{
			templateSql =  templateSql + " and business='?business?' ";
			sqlbv.put("business", this.Business);
		}
		
		if(this.CalCode!=null&&!this.CalCode.equals(""))
		{
			templateSql =  templateSql + " and rulename='?rulename?' ";
			sqlbv.put("rulename", this.CalCode);
		}
		
		templateSql = templateSql + " order by templatelevel desc";
		sqlbv.sql(templateSql);
		tLRTemplateSet = tLRTemplateDB.executeQuery(sqlbv);
		logger.debug(templateSql);
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		//	对于每一条记录，创建一个sqlTask类对象
		if(tLRTemplateSet.size()==0)
		{
			return new ArrayList();
		}
		for(int i=0;i<tLRTemplateSet.size();i++){ //每一条记录 
			// 创建SQLTask任务
			SQLTask sqlTask = new SQLTask( tLRTemplateSet.get(i+1).getSQLStatement(), tLRTemplateSet.get(i+1).getSQLParameter(), tLRTemplateSet.get(i+1).getBOMs(),
					tLRTemplateSet.get(i+1).getId(),this.listBoms,this,tLRTemplateSet.get(i+1).getRuleName());
			// 添加SQLTask任务
			this.tasks.add(sqlTask);
			mapVersion.put(tLRTemplateSet.get(i+1).getId(),new Integer(tLRTemplateSet.get(i+1).getVersion()));
		
			taskCount+=1;
		}
		// 线程池管理
		ThreadPoolManager manager=null;
		try {
			manager = RulePool.getInstance().getThreadPool();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//logger.debug("tasks.size():"+tasks.size());
		for(int i=0;i<tasks.size();i++){
			manager.addTask((Task)tasks.get(i));
		}
		logger.debug("taskCount = "+this.taskCount);
		manager.process();
		// 线程管理
		synchronized (this.list) {
			while(!done){
				try {
					this.list.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		RulePool.getInstance().putThreadPool(manager);
		logger.debug("workdone");
		//将查询的结果和相关的结果写入到LRResult表中 使用异步调用
		for(int i=0;i<list.size();i++){
			SQLTaskResult result = (SQLTaskResult)list.get(i);
			if(result.getTemplateId()!=null)
				result.setVersion(((Integer)mapVersion.get(result.getTemplateId())).intValue());
		}
		return this.list;
	}
	
	public Object execute(List sqlWithParaList) {
		
		//	从LRTemplate里面取出所有满足规则的记录，并且获得记录中的sqlstament, parameter,boms为解析后的BOM
		long startTime = System.currentTimeMillis();
		Map mapVersion = new HashMap();		
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		//	对于每一条记录，创建一个sqlTask类对象
	
		
		for(int i=0;i<sqlWithParaList.size();i++){
			String sqlWithPara = (String) sqlWithParaList.get(i);
			String[] sqlInfoArr = sqlWithPara.split("~");
			// 创建SQLTask任务
			SQLTask sqlTask = new SQLTask(sqlInfoArr[0],sqlInfoArr[1],sqlInfoArr[2],
					"001",this.listBoms,this,"测试批量规则");
			// 添加SQLTask任务
			this.tasks.add(sqlTask);
			
		
			taskCount+=1;
		}
		
		
		// 线程池管理
		ThreadPoolManager manager=null;
		try {
			manager = RulePool.getInstance().getThreadPool();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//logger.debug("tasks.size():"+tasks.size());
		for(int i=0;i<tasks.size();i++){
			manager.addTask((Task)tasks.get(i));
		}
		logger.debug("taskCount = "+this.taskCount);
		manager.process();
		// 线程管理
		synchronized (this.list) {
			while(!done){
				try {
					this.list.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		RulePool.getInstance().putThreadPool(manager);
		logger.debug("workdone");
		//将查询的结果和相关的结果写入到LRResult表中 使用异步调用
		for(int i=0;i<list.size();i++){
			SQLTaskResult result = (SQLTaskResult)list.get(i);
			if(result.getTemplateId()!=null)
				result.setVersion(((Integer)mapVersion.get(result.getTemplateId())).intValue());
		}
		return this.list;
	}

	public void workDone(TaskContext context) {
		// TODO Auto-generated method stub
		synchronized (this.list) {
			tasksDone++;
			SQLTask task = (SQLTask)context.getAttribute("task");
			logger.debug("taskDone = "+this.tasksDone);
			logger.debug("task = "+task.getSql());
			List result = (List)context.getAttribute("results");
			for(int i=0;i<result.size();i++)
				list.add(result.get(i));
			if(tasksDone==taskCount) //所有的任务都已经完成
			{
				done = true;
				this.list.notify();
			}
			
		}
		
	}
	
	public List getResult()
	{
		return list;
	}
	
	// 创建连接池实例
	public static void main(String[] args){
		Connection conn =  DBConnPool.getConnection();
		long startTime = System.currentTimeMillis();
		ExeSQL exeSql = new ExeSQL();
		SSRS ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		startTime = System.currentTimeMillis();
		 exeSql = new ExeSQL();
		 ssrs = exeSql.execSQL("select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc");
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
		
		
		startTime = System.currentTimeMillis();
		LRTemplateSet tLRTemplateSet = new LRTemplateSet();
		LRTemplateDB tLRTemplateDB = new LRTemplateDB();
		String templateSql = "select * from lrtemplate where business='1' and valid='1' and startdate< SYSDATE  and enddate>SYSDATE order by templatelevel desc";
		tLRTemplateSet = tLRTemplateDB.executeQuery(templateSql);
		logger.debug(templateSql);
		logger.debug("------ template sql task execute time is "+(System.currentTimeMillis()-startTime));
	}
}
