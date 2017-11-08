package com.sinosoft.ibrms;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.bom.AbstractBOM;
import com.sinosoft.lis.db.LRCommandDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LRCommandSchema;
import com.sinosoft.lis.vschema.LRCommandSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// 解析、执行
public class Rule {
private static Logger logger = Logger.getLogger(Rule.class);
	private String mRuleName = "";
	private RuleEngineCacheService ruleEngineCacheService = new RuleEngineCacheService();
	
	//批量处理多个SQL
	public List execRule(List listBoms,List sqlWithParaList,String tCalCode){
		RuleTask tRuleTask = new RuleTask(listBoms,"00",tCalCode);
		List resultList = (List) tRuleTask.execute(sqlWithParaList);
		
		
		return resultList;
	}

	
	public List execRule(List listBoms,String sqlWithPara,String ruleName){
		List resultList ;
		List paraList = new ArrayList();
		StringTokenizer st = new StringTokenizer(sqlWithPara,"~");
		while(st.hasMoreTokens()){
			
			paraList.add((String)st.nextToken()) ;
		}
		String sql = (String)paraList.get(0);
		String parameter = (String)paraList.get(1);
		String boms = (String)paraList.get(2);
		logger.debug("In Rule.java execRule(List listBoms,String sqlWithPara)方法 获取参数sql："+sql);
		logger.debug("In Rule.java execRule(List listBoms,String sqlWithPara)方法 获取参数parameter："+parameter);
		logger.debug("In Rule.java execRule(List listBoms,String sqlWithPara)方法 获取参数boms："+boms);
		
		resultList = this.execRule(listBoms, sql, parameter, boms,ruleName);
		return resultList;
		
	}
	
	public List execRule(List listBoms,String sql,String parameter,String boms,String ruleName){
		
		this.mRuleName = ruleName;
		long startTime = System.currentTimeMillis();
		Map mapBoms = new HashMap();
		for(int i=0;i<listBoms.size();i++){
			Object obj = listBoms.get(i);
			String bomName = getBomName(obj);
			mapBoms.put(bomName,obj);
		}
		//tongmeng 2011-05-06 modify 
		//规则引擎决策表替代方案处理
		//logger.debug("解析与执行");
		StringTokenizer st = new StringTokenizer(boms,",");
		Map mapBomStruct = new HashMap();
		List listBomHeader = new ArrayList(); //最顶层的类结构的列表
		while(st.hasMoreTokens()){
			String bom = (String)st.nextToken();
			Object objBom = mapBoms.get(bom);
			if(objBom==null){ //没有传递相应的值，如何处理
				
			}else{
				if(objBom.getClass().isArray() && mapBomStruct.get(bom)==null){ //如果是数组，需要对类建立层次关系，以便循环
					if(mapBomStruct.size()==0)  //第一次建立
					{
						BOMStruct bomStruct  = new BOMStruct(bom,objBom);
						mapBomStruct.put(bom,bomStruct);
						listBomHeader.add(bomStruct);
					}else{
						buildBOMStruct(bom,objBom,mapBoms,mapBomStruct,listBomHeader);
					}
						
				}
			}
			
		}
		st = new StringTokenizer(parameter,",");
		Map mapUsedBoms = new HashMap();
		
		Map mapUsedParamValues = new HashMap();
		
		while(st.hasMoreTokens()){
			String bomField = (String)st.nextToken();
			int index = bomField.indexOf(".");
			String bomName = bomField.substring(0,index);
			String fieldName = bomField.substring(index+1);
			Object obj = mapBoms.get(bomName);
			if(obj==null){ //为空，出错，抛出异常
				
			}else{
				if(!obj.getClass().isArray()){
				   //非层次性的先做替换
				   sql = replaceSqlParameter(sql,(AbstractBOM)obj,bomName,fieldName,mapUsedParamValues);
				   mapUsedBoms.put(bomName, obj);
				 
				}
			}
			
		}
		
		
		
		List listSql = new ArrayList(); //存放要执行sql语句的list
		List listUsedBoms = new ArrayList(); //存放解析该条 sql语句时所需要的bom对象。
		                                     //是一个map对象。如果没有用到某个bom对象而传递过
		
		List listSqlParams = new ArrayList();
	   // if(list)	
	/*	while(true){
			if(listBomHeader.size()<=1)
				break;
			
		}*/
		
		if(listBomHeader.size()==0){
			listSql.add(sql);
			listUsedBoms.add(mapUsedBoms);
			listSqlParams.add(mapUsedParamValues);
		}else 
		{
			List listTemp = new ArrayList();
			List listSql1 = new ArrayList();
			List listTempBom = new ArrayList();
			List listUsedBoms1 = new ArrayList();
			
			//记录每一个SQL对应的变量内容
			List listTempParams  =  new ArrayList();
			List listSqlParams1  =  new ArrayList();
			
			listTemp.add(sql);
			listTempBom.add(mapUsedBoms);
			
			listTempParams.add(mapUsedParamValues);
			for(int j=0;j<listBomHeader.size();j++){
			    BOMStruct bomStruct = (BOMStruct)listBomHeader.get(j);
		    	Object objBom = bomStruct.getObjBom();
		    	
		    	listSql1.clear();
		    	listUsedBoms1.clear();
		    	listSqlParams1.clear();
		    	
		    	for(int k=0;k<listTemp.size();k++){
			       String sqlTemp = (String)listTemp.get(k);
			       mapUsedBoms = (Map)listTempBom.get(k);
			       
			       mapUsedParamValues = (Map)listTempParams.get(k);
			       
			       listSql.clear();
			       listUsedBoms.clear();
			       listSqlParams.clear();
			       
			       if(objBom.getClass().isArray()){
				      for(int i=0;i<Array.getLength(objBom);i++){
					    AbstractBOM bom = (AbstractBOM)Array.get(objBom, i);
					    if(bom==null)
						    continue;
					    
					    Map tempMapUsedParamValues = new HashMap();
					    tempMapUsedParamValues.putAll(mapUsedParamValues);
					    
					     buildSqlFromBom(bom, bomStruct, sqlTemp, boms, parameter, listSql,mapBoms,mapUsedBoms,listUsedBoms,listSqlParams,tempMapUsedParamValues);
				      
					  }
				      for(int x=0;x<listSql.size();x++)
			        	  listSql1.add(listSql.get(x));
				      for(int x=0;x<listUsedBoms.size();x++)
				    	  listUsedBoms1.add(listUsedBoms.get(x));
				      
				      for(int x=0;x<listSqlParams.size();x++)
				    	  listSqlParams1.add(listSqlParams.get(x));
				  }
			    
				  
			   }
		    	   listTemp.clear();
		    	   
			       for(int x=0;x<listSql1.size();x++)
			        	  listTemp.add(listSql1.get(x)); 
				   listTempBom.clear();
			       for(int x=0;x<listUsedBoms1.size();x++)
			        	  listTempBom.add(listUsedBoms1.get(x));
			       
			       listTempParams.clear();
			       for(int x=0;x<listSqlParams1.size();x++)
			    	   listTempParams.add(listSqlParams1.get(x));
			       
			}
			listSql.clear();
			listSql = listSql1;
			listUsedBoms.clear();
			listUsedBoms=listUsedBoms1;
			
			listTempParams.clear();
			listTempParams = listSqlParams1;
		}
		
		List results = new ArrayList();
		logger.debug("------ parse sql task execute time is "+(System.currentTimeMillis()-startTime));
		startTime = System.currentTimeMillis();
		Connection conn =  DBConnPool.getConnection();
		ExeSQL   exeSql  = new ExeSQL(conn);
		String altSession="alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(altSession);
		exeSql.execUpdateSQL(sbv);

		for(int i=0;i<listSql.size();i++){
			sql = (String)listSql.get(i);
			 mapUsedBoms = (Map)listUsedBoms.get(i); 
			 mapUsedParamValues = (Map)listSqlParams.get(i); 
			 
			logger.debug("i:"+i+":sql:"+sql);
			 getResult(exeSql,sql,mapUsedBoms,results,mapUsedParamValues); //返回的值不全面，需要添加ruleid
			
		//	if(result!=null)
		//		results.add(result);
		}
		
		logger.debug("------ total sql task execute time is "+(System.currentTimeMillis()-startTime));
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("alter session set nls_date_format='yyyy-mm-dd'");
		exeSql.execUpdateSQL(sbv1);

		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return results;
	}
	
	protected void getResult(ExeSQL exeSql,String sql,Map mapUsedBoms,List results,Map mapSqlParams){
		// 就是简单第执行sql语句，并返回结果 ，结果应该是两列 ruleid,reuslt
		//tongmeng 2011-04-25 add
		//此处增加解析JAVA方法
		String tFinalSQL  = filterSQL(sql);
		//修改成支持绑定变量的模式
		//需要对SQL做转换
		//////////////////////////////////////////////////////////////////
		//修改成绑定变量执行的方式
		//使用正则表达式进行替换
		String tSourceSQL = tFinalSQL;
		String tCachedSQL = tSourceSQL;
		//构建需要缓存的SQL
		
		Iterator it=mapSqlParams.keySet().iterator();    
		while(it.hasNext()){    
		     String key;    
		     String value;    
		     key=it.next().toString();    
		     value=(String) mapSqlParams.get(key);    
		     tCachedSQL = tCachedSQL.replace("?"+key+"?", value);
		}   
		
		System.out.println("###################缓存的SQL："+tSourceSQL);
		System.out.println("###################替换后缓存的SQL："+tCachedSQL);
		boolean hasCached = false;
		
		SSRS ssrs = new SSRS();
		ssrs = ruleEngineCacheService.getResultBySQL(tCachedSQL);
		if(ssrs!=null){
			hasCached = true;
		}
		
		if (!hasCached) {
			//SQL未缓存
			Pattern pattern = Pattern.compile("(\\'*\\?.+?\\?\\'*)");
			Matcher matcher = pattern.matcher(tSourceSQL);
			int n = 0;
			TransferData tParams = new TransferData();
			while (matcher.find()) {
				// 当前查到的内容
				String tCurrentGroup = matcher.group();
				logger.debug("tCurrentGroup:" + tCurrentGroup);
				//
				String tKeyCode = " & ";
				String tKeyIdx = tCurrentGroup.replaceAll("([\\'\\?])", "");
				String tReplaceStr = "";
				String tReplaceCode = "";

				String tParamType = "";
				String tParamValue = "";
				tParamValue = (String) mapSqlParams.get(tKeyIdx);
				if (tCurrentGroup.indexOf("'") != -1
						|| tParamValue.indexOf("'") != -1) {
					tParamType = "String";
					tParamValue = tParamValue.replaceAll("'", "");
				} else {
					try {
						Integer.parseInt(tParamValue);
						tParamType = "int";
					} catch (Exception e) {
						tParamType = "double";
					}

				}

				tReplaceCode = tKeyCode;
				tReplaceStr = matcher.replaceFirst(tReplaceCode);

				tParams.setNameAndValue(String.valueOf(n), tParamType + ":"
						+ tParamValue);

				// String test = matcher.replaceFirst("test");
				// logger.debug("tReplaceStr:" + tReplaceStr);
				matcher = pattern.matcher(tReplaceStr);
				tFinalSQL = tReplaceStr;
				n++;
			}
			tFinalSQL = tFinalSQL.replaceAll("&", "?");
			logger.debug("tFinalSQL:" + tFinalSQL);

			VData tParseResult = new VData();
			tParseResult.add(tFinalSQL);
			tParseResult.add(tParams);

			// ///////////////////////////////////////////////////////////////
			// SSRS ssrs = exeSql.execSQL(tFinalSQL);
			ssrs = exeSql.execSQL(tParseResult);
			//执行完后，放入缓存 
			ruleEngineCacheService.putSQLResult(tCachedSQL, ssrs);
		}
		
		if(ssrs.getMaxRow()>0){
			//tongmeng 2010-12-15 modify
			//支持计算型规整设置
			if(ssrs.getMaxCol()==2)
			{
				for(int i=0;i<ssrs.getMaxRow();i++){
					 String ruleid = ssrs.GetText(i+1, 2);
					 String UWLevel=ssrs.GetText(i+1,2);
					 String result = ssrs.GetText(i+1, 1);
					 SQLTaskResult  taskResult= new SQLTaskResult(mapUsedBoms,result,ruleid);
					 taskResult.setUWLevel(UWLevel);
					 taskResult.setResultMessage("zh", result);
					 //tongmeng 2011-06-02 add
					 //增加返回信息
					 //String tSQL = "select language,msg from ldmsginfo_msg where keyid='"+this.mRuleName+ruleid+"'";
					 SSRS tMsgSSRS = new SSRS();
					 //修改成支持绑定变量的模式
					 String tSQL = "select language,msg from ldmsginfo_msg where keyid=?";
					 TransferData tparam = new TransferData();
					 tparam.setNameAndValue("0","string:"+this.mRuleName+ruleid);
					 VData tBVData = new VData();
					 tBVData.add(tSQL);
					 tBVData.add(tparam);
					 tMsgSSRS = exeSql.execSQL(tBVData);
					 
//					 String tSQL = "select language,msg from ldmsginfo_msg where keyid='?keyid?'";
//					 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
//					 sqlbv1.sql(tSQL);
//					 sqlbv1.put("keyid",this.mRuleName+ruleid);
//					 tMsgSSRS = exeSql.execSQL(sqlbv1);
					 for(int m=1;m<=tMsgSSRS.getMaxRow();m++)
					 {
						 taskResult.setResultMessage(tMsgSSRS.GetText(m, 1), tMsgSSRS.GetText(m, 2));
					 }
					 
					 results.add(taskResult);
		    	    // ssrs.getMaxRow();
				}
			}
			else
			{
				for(int i=0;i<ssrs.getMaxRow();i++){
					String ruleid = ssrs.GetText(i+1, 1);
					String UWLevel=ssrs.GetText(i+1,2);
					String result = ssrs.GetText(i+1, 3);
					//tongmeng 2011-06-08 modify
					//校验型规则返回值也设成1,以支持calculator的调用
					
					SQLTaskResult  taskResult= new SQLTaskResult(mapUsedBoms,result,ruleid);
					taskResult.setUWLevel(UWLevel);
					taskResult.setResultMessage("zh", result);
					taskResult.setResult("1");
					 //tongmeng 2011-06-02 add
					 //增加返回信息
					// String tSQL = "select language,msg from ldmsginfo_msg where keyid='"+this.mRuleName+ruleid+"'";
					 SSRS tMsgSSRS = new SSRS();
					 //修改成支持绑定变量的模式
					 String tSQL = "select language,msg from ldmsginfo_msg where keyid='?keyid?'";
					 TransferData tparam = new TransferData();
					 tparam.setNameAndValue("0","string:"+this.mRuleName+ruleid);
					 VData tBVData = new VData();
					 tBVData.add(tSQL);
					 tBVData.add(tparam);
//					 SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
//					 sqlbv2.sql(tSQL);
//					 sqlbv2.put("keyid",this.mRuleName+ruleid);
//					 tMsgSSRS = exeSql.execSQL(sqlbv2);
					
					 for(int m=1;m<=tMsgSSRS.getMaxRow();m++)
					 {
						 taskResult.setResultMessage(tMsgSSRS.GetText(m, 1), tMsgSSRS.GetText(m, 2));
					 }
					 
					results.add(taskResult);
	    	    // ssrs.getMaxRow();
				}
			}
		}else{
			
			if(exeSql.mErrors.getErrorCount()>0)
			{
				SQLTaskResult  taskResult = new SQLTaskResult(mapUsedBoms,"0","-1");
				for(int i=0;i<exeSql.mErrors.getErrorCount();i++){
					CError error = exeSql.mErrors.getError(i);
					taskResult.addError(error);
				}
				results.add(taskResult);
			}
			else
			{
				SQLTaskResult  taskResult = new SQLTaskResult(mapUsedBoms,"0","-1");
				results.add(taskResult);
			}
			
		}
		
		exeSql.mErrors.clearErrors();
	}
	
	protected void buildSqlFromBom(AbstractBOM bom,BOMStruct bomStruct,String sql,String boms,String parameter,List listSql,Map mapBoms,Map mapUsedBoms,List listUsedBoms,List listSqlParams,Map mapUsedParamValues){
		String bomName = getBomName(bom);
	
		if(isBomParameter(bomStruct.getBomName(),boms)){ //该bom对象是否参与了
			StringTokenizer st = new StringTokenizer(parameter,",");
			while(st.hasMoreTokens()){
				String bomField = (String)st.nextToken();
				int index = bomField.indexOf(".");
				bomName = bomField.substring(0,index);
				String fieldName = bomField.substring(index+1);
				if(bomName.equals(bomStruct.getBomName())){
			    
					   sql = replaceSqlParameter(sql,bom,bomName,fieldName,mapUsedParamValues);
					   if(mapUsedBoms.containsKey(bomName))
							mapUsedBoms.remove(bomName);
				       mapUsedBoms.put(bomName, bom);
				}
				
			}
		}
		if(bomStruct.getListChildren().size()==0)  //最低端的，如果该层次的祖先级之下还有别的同级的需要参加替换，则进行递归调用
			                                       //否则不需要再参与循环，整个递归结束
		{
			BOMStruct nextBomStruct = getNextSibling(bomStruct);
			if(nextBomStruct==null){
			   Map map = buildUsedBomsMap(mapBoms,mapUsedBoms);
			   listUsedBoms.add(map);
			   listSql.add(sql);
			   listSqlParams.add(mapUsedParamValues);
			   return ;
			}else{
				AbstractBOM fatherBom = getNextSiblingFatherBom(bomStruct,nextBomStruct,bom);
				buildSqlFromBomChildren(fatherBom,nextBomStruct,sql,boms,parameter,listSql,mapBoms,mapUsedBoms,listUsedBoms,listSqlParams,mapUsedParamValues);
				return;
			}
				
		}
		
		BOMStruct childBomStruct =(BOMStruct) bomStruct.getListChildren().get(0);
				buildSqlFromBomChildren(bom,childBomStruct,sql,boms,parameter,listSql,mapBoms,mapUsedBoms,listUsedBoms,listSqlParams,mapUsedParamValues);

	}
	
	//处理该bom的子层结构
	protected void buildSqlFromBomChildren(AbstractBOM bom,BOMStruct childBomStruct,String sql,String boms,String parameter,List listSql,Map mapBoms,Map mapUsedBoms,List listUsedBoms,List listSqlParams,Map mapUsedParamValues)
	{
		
		Object objBom = childBomStruct.getObjBom();
		
		if(!objBom.getClass().isArray()){ //不是数组
			buildSqlFromBom((AbstractBOM)objBom,childBomStruct,sql,boms,parameter,listSql,mapBoms,mapUsedBoms,listUsedBoms,listSqlParams,mapUsedParamValues);
		}else{
			for(int i=0;i<Array.getLength(objBom);i++){
				AbstractBOM childBom = (AbstractBOM)Array.get(objBom, i);
				if(childBom!=null && bom.equals(childBom.getFatherBOM())) //是该bom的子类对象
				{
					buildSqlFromBom(childBom,childBomStruct,sql,boms,parameter,listSql,mapBoms,mapUsedBoms,listUsedBoms,listSqlParams,mapUsedParamValues);
				}
			}
		}
	}
	/**
	 * 
	 * @param bomStruct
	 * @param bom 这个是用来获得bom的父类对象的,并且返回到调用方
	 * @return
	 */
	protected BOMStruct getNextSibling(BOMStruct bomStruct){
		BOMStruct fatherBomStruct = bomStruct.getFatherBOMStruct();
	
		while(fatherBomStruct!=null){
			BOMStruct nextBomStruct = getNextSibling(fatherBomStruct,bomStruct);
			if(nextBomStruct != null){
				
				return nextBomStruct;
			}
			bomStruct = fatherBomStruct;
			fatherBomStruct = fatherBomStruct.getFatherBOMStruct();
		}
		return null;
		
	}
	
	protected BOMStruct getNextSibling(BOMStruct fatherBomStruct,BOMStruct childBomStruct){
		List list =fatherBomStruct.getListChildren();
		int index = list.indexOf(childBomStruct);
		if(index<0 || index>=list.size()-1) //最后一个，或者没有找到
			 return null;
		return (BOMStruct)list.get(index+1); //取下一个
	}
	
	//获得在当前的情况下使用的祖先类
	protected AbstractBOM getNextSiblingFatherBom(BOMStruct bomStruct,BOMStruct nextBomStruct,AbstractBOM bom){
		BOMStruct nextFatherBomStruct = nextBomStruct.getFatherBOMStruct();
		AbstractBOM fatherBom = bom.getFatherBOM();
		BOMStruct fatherBomStruct = bomStruct.getFatherBOMStruct();
		while(fatherBomStruct!= nextFatherBomStruct && fatherBomStruct!=null){
			fatherBomStruct = fatherBomStruct.getFatherBOMStruct();
			fatherBom = fatherBom.getFatherBOM();
		}
		return fatherBom;
	}
	
	
	protected Map buildUsedBomsMap(Map mapBoms,Map mapUsedBoms){
		Map map = new HashMap();
		Set keys =mapUsedBoms.keySet();
		for(Iterator iter = keys.iterator();iter.hasNext();){
		    String key = (String)iter.next();
		    map.put(key, mapUsedBoms.get(key));
		}
		
	 /*    keys =mapBoms.keySet();
		for(Iterator iter = keys.iterator();iter.hasNext();){
		    String key = (String)iter.next();
		    if(!map.containsKey(key))
		        map.put(key, mapBoms.get(key));
		}*/
		return map;
		
	}
	
	protected boolean isBomParameter(String bomName,String boms){
		StringTokenizer st = new StringTokenizer(boms,",");
		while(st.hasMoreTokens()){
			String bom = st.nextToken();
			if(bom.equals(bomName))
				return true;
		}
		return false;
	}
	
	
	
	//建立类的层次关系.
	//mapBomStruct中存储的是已经建立好层次关系的类。需要将新的类加入进来。
	//新的类有可能是已有类的子类或者子孙类，或者是已有类的祖先类。中间可能会有一些新的中间的类对象加入进来，形成一个链表		
	
	protected void buildBOMStruct(String bomName,Object objBom,Map mapBoms,Map mapBomStruct,List listBomHeader){
		if(Array.getLength(objBom)==0) //如果数组中没有对象，那么就不可能建立，直接返回
		    return;
		int index = 0;
		boolean found = false;
		List list = new ArrayList(); //中间经过的类
		Set setBoms = mapBomStruct.entrySet();
		String ancestorBomName = null;
		for(Iterator iter = setBoms.iterator();iter.hasNext();){
			Map.Entry entry = (Map.Entry)iter.next();
			list.clear();
			 ancestorBomName = ((BOMStruct)entry.getValue()).getBomName();
			if(isAncestor(objBom,ancestorBomName,list)){
				found = true;
				break;
			}
		//	logger.debug(entry.getValue());
		}
		
		
		if(found){ //如果有
			BOMStruct ancestorBomStruct = (BOMStruct)mapBomStruct.get(ancestorBomName);
			BOMStruct fatherBomStruct = ancestorBomStruct;
			for(int i=list.size()-1;i>=0;i--){
				Object obj = mapBoms.get((String)list.get(i));
				fatherBomStruct = new BOMStruct((String)list.get(i),obj);
				fatherBomStruct.setFatherBOMStruct(ancestorBomStruct);
				ancestorBomStruct.addChild(fatherBomStruct);
				ancestorBomStruct = fatherBomStruct;
				mapBomStruct.put((String)list.get(i),fatherBomStruct);
			}
			BOMStruct bomStruct  = new BOMStruct(bomName,objBom);
			bomStruct.setFatherBOMStruct(fatherBomStruct);
			fatherBomStruct.addChild(bomStruct);
			mapBomStruct.put(bomName,bomStruct);
			return;
		}
		
		list.clear();
		
		setBoms = mapBomStruct.entrySet();
		ancestorBomName = bomName;
		String descendBomName = null;
		for(Iterator iter = setBoms.iterator();iter.hasNext();){ //看已有的类中是否有祖先类为bomName
			
			Map.Entry entry = (Map.Entry)iter.next();
			list.clear();
			BOMStruct bomStruct = (BOMStruct)entry.getValue();
			if(bomStruct.getFatherBOMStruct()!=null)
				continue;
			Object  descendObjBom=bomStruct.getObjBom();
			descendBomName = bomStruct.getBomName();
			if(isAncestor(descendObjBom,ancestorBomName,list)){
				found = true;
				break;
			}
		//	logger.debug(entry.getValue());
		}	
		
		if(found){
			BOMStruct descendBomStruct = (BOMStruct)mapBomStruct.get(descendBomName);
			BOMStruct fatherBomStruct = descendBomStruct;
			for(int i=0;i<list.size();i++){
				Object obj = mapBoms.get((String)list.get(i));
				fatherBomStruct = new BOMStruct((String)list.get(i),obj);
				descendBomStruct.setFatherBOMStruct(fatherBomStruct);
				fatherBomStruct.addChild(descendBomStruct);
				descendBomStruct = fatherBomStruct;
				mapBomStruct.put((String)list.get(i),fatherBomStruct);
			
			}
			BOMStruct bomStruct  = new BOMStruct(bomName,objBom);
			fatherBomStruct.setFatherBOMStruct(bomStruct);
			bomStruct.addChild(fatherBomStruct);
			mapBomStruct.put(bomName,bomStruct);
			listBomHeader.clear();
			listBomHeader.add(bomStruct);
			
		}else{
			BOMStruct bomStruct  = new BOMStruct(bomName,objBom);
			mapBomStruct.put(bomName,bomStruct);
			listBomHeader.add(bomStruct);
		}
		
		
	}
	
	protected boolean isAncestor(Object objBom,String ancestorBomName,List list){
	    if(Array.getLength(objBom)==0)
	    	return false;
	    int index = 0;	
	    
		while(true){
			AbstractBOM bom = (AbstractBOM)Array.get(objBom, index);
			AbstractBOM fatherBom = bom.getFatherBOM();
			while(fatherBom!=null){
				String bomName = getBomName(fatherBom);
				if(ancestorBomName.equals(bomName))
					return true;
				list.add(bomName);
				fatherBom = fatherBom.getFatherBOM();
			}
			index++;
			if(index>= Array.getLength(objBom))
				break;
			
			list.clear();
		}
		return false;
	}
	
	protected String replaceSqlParameter(String sql,AbstractBOM bom,String bomName,String fieldName,Map mapUsedParamValues){
		String fieldValue = getBomField(bom, bomName,fieldName); //bom.getV(fieldName);
		
		
		String regex = "\\?"+bomName+"."+fieldName+"\\?";
		//sql = sql.replaceAll(regex, fieldValue);
		mapUsedParamValues.put(bomName+"."+fieldName, fieldValue);
		logger.debug("bomName.fieldName:"+bomName+"."+fieldName+":fieldValue:"+fieldValue);
		
		return sql;
	}
	
	
	protected String getBomClassName(Object objBom){
		String className ;
		if(objBom.getClass().isArray())
			className = objBom.getClass().getComponentType().getName();
		else
			className = objBom.getClass().getName();
		return className;

	}
	
	protected String getBomName(Object objBom){
		String className = getBomClassName(objBom);
		String bomName;
		int index = className.lastIndexOf(".");
		if(index>0)
			className = className.substring(index+1);
		bomName = className;
	//	if(className.startsWith("BOM"))
		//	bomName = className.substring(3);
	    return bomName;		
	}
	
	public boolean isObjectBom(Object objBom, String bom){
		String className = getBomClassName(objBom);

		String bomClass = "com.sinosoft.ibrms.bom.BOM"+bom;
		return bomClass.equals(className);
	}
	
	public String getBomField(AbstractBOM objBom,String bomName,String fieldName){
		Object obj = null;
		try {
			Method method = objBom.getClass().getMethod("get"+fieldName,null);
			if(method!=null)
				obj = method.invoke(objBom, null);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String fieldValue  ;// =  objBom.getV(fieldName);
	    String type = BOMInfo.getInstance().getBomItemType(bomName, fieldName, objBom);
		if(type==BOMInfo.STRING){
			if(obj== null)
				fieldValue = "null";
			else
				fieldValue = "'"+obj+"'";
		}else if(type==BOMInfo.DATE){
			if(obj== null)
				fieldValue = "null";
			else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				fieldValue = "'"+sdf.format((Date)obj)+"'";
			}
				
			
		}else if(type==BOMInfo.NUMBER){
			if(obj== null)
				fieldValue = "null";
			else
				fieldValue = obj.toString();
		}else{
	      if(obj==null ){ //如果没有传递这个参数的值，如何处理？？
			  fieldValue = "";
			  
	      }else
	    	  fieldValue = obj.toString();
		}
		return fieldValue;
	}
	

	protected String getMap(Map map){
		map.put("key1", "1111");
		return "";
	}
	
	//tongmeng 2011-04-25 add
	//处理JAVA函数
	protected String filterSQL(String tSQL)
	{
		String tResSQL = "";
		tResSQL = tSQL;
		
		String tStr = "", tStr1 = "";
		int count=0;
		
		int tQuestionMarkCount = 0;
		tQuestionMarkCount = tResSQL.length() - tResSQL.replaceAll("\\#","").length();
		if (tQuestionMarkCount % 2 != 0) {
			logger.debug("SQL语句的#问号个数不匹配，请核查:"+tResSQL+":tQuestionMarkCount:"+tQuestionMarkCount);
			return tResSQL;
		}
		
		
		try {
			//tongmeng 2011-04-28 modify
			//采用新的算法支持多函数嵌套调用的处理
			StringTokenizer st=null;
			Hashtable tHashtable =  new Hashtable();
			tHashtable.put("SQL", tSQL);
			//StringBuffer tSB = new StringBuffer(tTestRule);
			String t = this.tCalJavaMethod(tHashtable,st,tSQL, "FUNC");
			logger.debug((String)tHashtable.get("SQL"));
			tSQL = (String)tHashtable.get("SQL");
			return tSQL;
			/*
			while (true) {
				tStr = PubFun.getStr(tResSQL, 2, "#");
				if (tStr.equals("")) {
					break;
				}
				String tJAVAName =  tStr.substring(0,tStr.indexOf("("));
				logger.debug("current tStr:"+tStr+"JavaName:"+tJAVAName);
				
				//获取LRCommand数据
				String tSQL_LRCommand = "select * from lrcommand where implenmation='"+tJAVAName+"' and commtype='2' ";
				LRCommandSchema tLRCommandSchema = new LRCommandSchema();
				LRCommandSet tLRCommandSet = new LRCommandSet();
				LRCommandDB tLRCommandDB = new LRCommandDB();
				tLRCommandSet =  tLRCommandDB.executeQuery(tSQL_LRCommand);
				if(tLRCommandSet.size()>0)
				{
					tLRCommandSchema = tLRCommandSet.get(1);
				}
				else
				{
					//只要找不到,就不解析了.认为有问题,直接返回原SQL
					return tSQL;
				}
				tStr1 = "#" + tStr + "#";
				// 替换变量
				Object tValueObject = executeJavaMethod(tStr,tLRCommandSchema);
				String tValue = "";
				if(tLRCommandSchema.getResultType().equals("Number"))
				{
					tValue = String.valueOf((Double)executeJavaMethod(tStr,tLRCommandSchema));
				}
				else if(tLRCommandSchema.getResultType().equals("String"))
				{
					tValue = (String)executeJavaMethod(tStr,tLRCommandSchema);
				}
				else if(tLRCommandSchema.getResultType().equals("Date"))
				{
					tValue = String.valueOf((Date)executeJavaMethod(tStr,tLRCommandSchema));
				}
				else if(tLRCommandSchema.getResultType().equals("Boolean"))
				{
					tValue = String.valueOf((Boolean)executeJavaMethod(tStr,tLRCommandSchema));
				}
				
				//String tValue = (String)executeJavaMethod(tStr,tLRCommandSchema);
				logger.debug("tValue:"+tValue);
				tValue = "'"+tValue+"'";
				tResSQL = StrTool.replaceEx(tResSQL, tStr1,tValue);  
				//防止死循环
				if(count++>1000){
					logger.debug("Error SQL:"+tResSQL);
					break;
				}
			}
			*/
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
		}
		logger.debug(tResSQL);
		
		return tResSQL;
	}
	
	/**
	 * 执行JAVA方法
	 * @param tJavaMethod
	 * @param tLRCommandSchema
	 * @return
	 */
	protected Object executeJavaMethod(String tJavaMethod,LRCommandSchema tLRCommandSchema)
	{
		//Object 
		String tJavaName = tJavaMethod.substring(0,tJavaMethod.lastIndexOf("."));
		Class mClass;
		Object resultObject;
		 try
	        {
	            mClass = Class.forName(tJavaName);
	        
	        Object[] para = prepareMethordParamValue(tJavaMethod,tLRCommandSchema);
            
	        Method addMethod = mClass.getMethod(tJavaMethod.substring(tJavaMethod.lastIndexOf(".")+1,tJavaMethod.indexOf("(")), prepareMethordParam(tLRCommandSchema));
            resultObject = addMethod.invoke((Object) mClass.newInstance(),para);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            //this.buildError("getCalClass", "获取算法计算类出错！");
	            return false;
	        }
            return resultObject;
	}
	
	/**
	 * 准备参数对象
	 * @param tLRCommandSchema
	 * @return
	 */
	private Class[] prepareMethordParam(LRCommandSchema tLRCommandSchema)
	{
		String tParamDetail = tLRCommandSchema.getCommDetail();
		String[] tParams = tParamDetail.split(",");
		Class[] para = new Class[tParams.length];
		for(int i=0;i<tParams.length;i++)
		{
			if(tParams[i].equals("String"))
			{
				para[i] = String.class;
			}
			else if(tParams[i].equals("Number"))
			{
				para[i] = Double.class;
			}
			else if(tParams[i].equals("DATE"))
			{
				para[i] = Date.class;
			}	
			else if(tParams[i].equals("Boolean"))
			{
				para[i] = Boolean.class;
			}
		}
		return para;
	}
	
	/**
	 * 准备参数
	 * @param tJavaMethod
	 * @param tLRCommandSchema
	 * @return
	 */
	private Object[] prepareMethordParamValue(String tJavaMethod,LRCommandSchema tLRCommandSchema)
	{
		String tParamDetail = tLRCommandSchema.getCommDetail();
		String[] tParams = tParamDetail.split(",");
		Object[] para = new Object[tParams.length];
		
		String tParamStr = tJavaMethod.substring(tJavaMethod.indexOf("(")+1,tJavaMethod.lastIndexOf(")"));
		//logger.debug("tParamStr:"+tParamStr);
		String[] tParaValue = tParamStr.split(",");
		for(int i=0;i<tParams.length;i++)
		{
			//logger.debug("tParaValue[i]:"+tParaValue[i]);

			if(tParaValue[i].indexOf("'")!=-1)
			{
				para[i] =  PubFun.getStr(tParaValue[i], 2, "'");
			}
			else
			{
				para[i] = tParaValue[i];
			}
				
		}
		return para;
	}
	

	
	/**
	 * 	tongmeng 2011-04-27 add
	 *  解析JAVA Method,并将最终结果放到tHashtable中
	 */
	public String tCalJavaMethod(Hashtable tHashtable,StringTokenizer st,String tExplainStr,String tFindStr)
	{
		String tResult = "";
		
		String tFilter = "";
		
		String tCurrExplainStr = tExplainStr;
		
		
		boolean firstflag = false;
		if(tFindStr.equals("FUNC"))
		{
			//查找函数
			String tFuncString = "";
			tFilter = "#(";
			if(st==null)
			{
				st = new StringTokenizer(tCurrExplainStr,tFilter,true);
				tResult = tCurrExplainStr;
				//firstflag = true;
			}
			String tLastStr = "";
			while(st.hasMoreTokens()){
				String tCurrentStr = st.nextToken(tFilter);
				//logger.debug("FUNC Curr:"+tCurrentStr);
				//logger.debug("FUNC Last:"+tLastStr);
				tFuncString = tFuncString + tCurrentStr;
				//logger.debug("tFuncString:"+tFuncString);
				//判断是在lmcommand中定义的函数
				if(tLastStr.indexOf("#")!=-1)
				{
					LRCommandSchema tLRCommandSchema = new LRCommandSchema();
					tLRCommandSchema = this.checkMethod(tCurrentStr);
					if(tLRCommandSchema!=null&&tLRCommandSchema.getCommType().equals("2"))
					{
						//开始找函数的参数 
						//logger.debug("发现函数名:"+tCurrentStr+"开始找参数");
						String tFinal  = tCalJavaMethod(tHashtable,st,tCurrentStr, "PARAM");
						logger.debug("找到函数名:"+tCurrentStr+"的参数是:"+tFinal);
						
						String tStr = tCurrentStr + tFinal;
						//找到后,执行
						// 替换变量
						Object tValueObject = executeJavaMethod(tStr,tLRCommandSchema);
						String tValue = "";
						if(tLRCommandSchema.getResultType().equals("Number"))
						{
							tValue = String.valueOf((Double)executeJavaMethod(tStr,tLRCommandSchema));
						}
						else if(tLRCommandSchema.getResultType().equals("String"))
						{
							tValue = (String)executeJavaMethod(tStr,tLRCommandSchema);
						}
						else if(tLRCommandSchema.getResultType().equals("Date"))
						{
							tValue = String.valueOf((Date)executeJavaMethod(tStr,tLRCommandSchema));
						}
						else if(tLRCommandSchema.getResultType().equals("Boolean"))
						{
							tValue = String.valueOf((Boolean)executeJavaMethod(tStr,tLRCommandSchema));
						}
						
						//String tValue = (String)executeJavaMethod(tStr,tLRCommandSchema);
						//logger.debug("tValue:"+tValue);
						tValue = "'"+tValue+"'";
						//替换变量
						String tStr1 = "#"+tStr+"#";
						
						String tReplaceSQL = (String)tHashtable.get("SQL");
						//logger.debug("before replace:"+tReplaceSQL);
						tReplaceSQL = StrTool.replaceEx(tReplaceSQL, tStr1,tValue);  
						//logger.debug("after replace:"+tReplaceSQL);
						tHashtable.put("SQL", tReplaceSQL);
						
						//return  tValue;
					}
				}
				//else if(tLastStr.indexOf("#")!=-1)
					
				tLastStr = tCurrentStr;
				
			}
		}
		else if(tFindStr.equals("PARAM"))
		{
			Hashtable  tParamHash = new Hashtable();
			ArrayList tArrayList = new ArrayList();
			//logger.debug("当前处理的方法JAVA名:"+tExplainStr);
			//String tParamsString = "";
			tFilter = "#(),";
			String tLastStr = "";
			
			boolean leftflag = false;
			
			boolean rightflag = false;
			while(st.hasMoreTokens()){
				String tCurrentStr = st.nextToken(tFilter);
			    logger.debug("PARAM Curr:"+tCurrentStr);
				if(tCurrentStr.equals("("))
				{
					int tLeftNum= 1;
					if(tParamHash.containsKey("LEFT"))
					{
						tLeftNum = (Integer)tParamHash.get("LEFT") +1 ;
					}
					
					logger.debug("tLeftNum:"+tLeftNum);
					if(tLeftNum==1&&!leftflag)
					{
						leftflag = true;
					}
					tParamHash.put("LEFT", tLeftNum);
				}
				
				else if(tCurrentStr.equals(")"))
				{
					int tRightNum= 1;
					if(tParamHash.containsKey("RIGHT"))
					{
						tRightNum = (Integer)tParamHash.get("RIGHT") +1 ;
					}
					
					//logger.debug("tRightNum:"+tRightNum);
					tParamHash.put("RIGHT", tRightNum);
					if((Integer)tParamHash.get("LEFT")==(Integer)tParamHash.get("RIGHT"))
					{
						//logger.debug("find");
						rightflag = true;
						
						if(!tLastStr.equals("#"))
						{
							//如果上一个是函数的话,最后一个#不放到tArrayList中
							tArrayList.add(tCurrentStr);
						}
						else
						{
							tArrayList.remove(tArrayList.size()-1);
							tArrayList.add(tCurrentStr);
						}
						
						//tParamsString = tParamsString + tCurrentStr;
						break;
					}
				}
				else if(tCurrentStr.equals(","))
				{
					if(tLastStr.equals("#"))
					{
						tArrayList.remove(tArrayList.size()-1);
						//tArrayList.add(tCurrentStr);
					}
				}
				
				if(leftflag&&!rightflag)
				{
					tArrayList.add(tCurrentStr);
					//tParamsString = tParamsString + tCurrentStr;
				}
				LRCommandSchema tLRCommandSchema = new LRCommandSchema();
				tLRCommandSchema = this.checkMethod(tCurrentStr);
				if(tLastStr.indexOf("#")!=-1)
				{
					
					if(tLRCommandSchema!=null&&(tLRCommandSchema.getCommType().equals("2")))
					{
						//logger.debug("解析参数时发现函数名为:"+tCurrentStr+"开始找参数");
						//开始找函数的参数 
						String tFinal  = tCalJavaMethod(tHashtable,st,tCurrentStr, "PARAM");
						//logger.debug("解析参数时发现函数名为:"+tCurrentStr+"的参数是:"+tFinal);
						String tStr = tCurrentStr + tFinal;
						//找到后,执行
						// 替换变量
						String tValue = "";

						if(tLRCommandSchema.getCommType().equals("2"))
						{
							Object tValueObject = executeJavaMethod(tStr,tLRCommandSchema);

							if(tLRCommandSchema.getResultType().equals("Number"))
							{
								tValue = String.valueOf((Double)executeJavaMethod(tStr,tLRCommandSchema));
							}
							else if(tLRCommandSchema.getResultType().equals("String"))
							{
								tValue = (String)executeJavaMethod(tStr,tLRCommandSchema);
							}
							else if(tLRCommandSchema.getResultType().equals("Date"))
							{
								tValue = String.valueOf((Date)executeJavaMethod(tStr,tLRCommandSchema));
							}
							else if(tLRCommandSchema.getResultType().equals("Boolean"))
							{
								tValue = String.valueOf((Boolean)executeJavaMethod(tStr,tLRCommandSchema));
							}
						}
						else
						{
							String tSQL_Oracle = "select "+tCurrentStr+tFinal + "from dual ";
							SQLwithBindVariables sqlbv=new SQLwithBindVariables();
							sqlbv.sql(tSQL_Oracle);
							 tValue = new ExeSQL().getOneValue(sqlbv);
						}
						tValue = "'"+tValue+"'";
						
						//替换变量
						String tStr1 = "#"+tStr+"#";
						
						String tReplaceSQL = (String)tHashtable.get("SQL");
						//logger.debug("before replace1:"+tReplaceSQL);
						tReplaceSQL = StrTool.replaceEx(tReplaceSQL, tStr1,tValue);  
						//logger.debug("after replace1:"+tReplaceSQL);
						tHashtable.put("SQL", tReplaceSQL);
						
						//重新处理下信息
						String tempString = "";
						for(int i=0;i<tArrayList.size();i++)
						{
							//logger.debug("show current stack:"+tArrayList.get(i).toString());
							tempString = tempString + tArrayList.get(i).toString();	
						}
						tempString = StrTool.replaceEx(tempString, "#"+tCurrentStr+"",tValue);  
						tArrayList.clear();
						
						tArrayList.add(tempString);
					}
				}
				tLastStr = tCurrentStr;
				
			}
			
			if(leftflag&&rightflag)
			{
				//找到参数,返回
				String tParamValue = "";
				logger.debug("---------------------------");	
				for(int i=0;i<tArrayList.size();i++)
				{
					logger.debug(""+tArrayList.get(i).toString());
					tParamValue = tParamValue + tArrayList.get(i).toString();
				}
				logger.debug(" ");
				logger.debug("---------------------------");
				
				tResult = tParamValue;
				return tResult;
			}
		}
		return tResult;
	}
	
	private LRCommandSchema checkMethod(String tMethodName)
	{
		String tSQL_LRCommand = "select * from lrcommand where implenmation='?tMethodName?' and commtype in ('1','2') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_LRCommand);
		sqlbv.put("tMethodName", tMethodName);
		LRCommandSchema tLRCommandSchema = new LRCommandSchema();
		LRCommandSet tLRCommandSet = new LRCommandSet();
		LRCommandDB tLRCommandDB = new LRCommandDB();
		tLRCommandSet =  tLRCommandDB.executeQuery(sqlbv);
		//logger.debug("tLRCommandSet.size():"+tLRCommandSet.size());
		if(tLRCommandSet.size()>0)
		{
			tLRCommandSchema = tLRCommandSet.get(1);
			return tLRCommandSchema;
		}
		return null;
	}
	
	
	
	public static void main(String[] args){
	
		
		//语法分析,找出JAVA方法并执行
		//
		//String[] funcArr = new String[3];
		
		
		//String tTestRule = "select    #com.sinosoft.lis.pubfun.PubFun.isNumeric( ?BOMCont.AccName?)# , '0001'  from  ldsysvar  where sysvar='onerow'  ";
		String tTestRule = "select   #com.sinosoft.lis.pubfun.PubFun.isNumeric('100'+'200')# , '0001'  from  ldsysvar  where sysvar='onerow'  ";
	tTestRule = "select   #com.sinosoft.lis.pubfun.PubFun.isNumeric(#com.sinosoft.lis.pubfun.PubFun1.CreateMaxNo('TEST','2')#)# , '0001'  from  ldsysvar  where sysvar='onerow' ";
		
	tTestRule = "select   #com.sinosoft.lis.pubfun.PubFun.isNumeric(#com.sinosoft.lis.pubfun.PubFun1.CreateMaxNo(#com.sinosoft.lis.pubfun.PubFun1.CreateMaxNo('TEST','23')#,'33')#,#com.sinosoft.lis.pubfun.PubFun1.CreateMaxNo('TEST','32')#)# , '0001'  from  ldsysvar  where sysvar='onerow' ";
	
	//tTestRule = "select   #com.sinosoft.lis.pubfun.PubFun.isNumeric(least('1','3'),'33')#,#com.sinosoft.lis.pubfun.PubFun1.CreateMaxNo('TEST','32')#)# , '0001'  from  ldsysvar  where sysvar='onerow' ";

	//String tTestRule = "select #func1('param1',#func2(orfunc1(para2,para3),(para4))#,#func3(orfunc2(para12,para13))#)#";
		logger.debug("tTestRule:"+tTestRule);
		Rule tRule = new Rule();
		StringTokenizer st=null;
		Hashtable tHashtable =  new Hashtable();
		tHashtable.put("SQL", tTestRule);
		//StringBuffer tSB = new StringBuffer(tTestRule);
		String t = tRule.tCalJavaMethod(tHashtable,st,tTestRule, "FUNC");
		logger.debug("Result:"+tHashtable.get("SQL").toString());
		
	}

}
