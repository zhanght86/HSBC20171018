package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.schema.LRRuleDataSchema;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * tongmeng 2011-05-05 add
 * 规则引擎决策表替代方案
 * @author Administrator
 *
 */
public class RuleInfoPrepare extends XML{
private static Logger logger = Logger.getLogger(RuleInfoPrepare.class);
	
	public static final int rowNumLimit = 50;
	//public static final int rowNumLimit = 1;
	//private static final ArrayList ArrayList() = null; 
	
	
	private RuleInfoPrepare(){};
	
	
	public  static String[][] getDtColumn(String tTemplateID)
	{
		String[][] tResultSSRS = null;
		String tTableName = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_Table = "select lower(tablename) from lrtemplatet where id='?tTemplateID?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Table);
		sqlbv.put("tTemplateID", tTemplateID);
		tTableName = tExeSQL.getOneValue(sqlbv);
		if(tTableName.equals("ldsysvar"))
		{
			return null;
		}
		else if(tTableName.equals("ruledata"))
		{
			String tSQL_Column = "select columnnames from lrruledata where id='?tTemplateID?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Column);
			sqlbv1.put("tTemplateID", tTemplateID);
			String tColNames[] = tExeSQL.getOneValue(sqlbv1).split(";");
			
			tResultSSRS = new String[tColNames.length][2];
			for(int i=0;i<tResultSSRS.length;i++)
			{
				tResultSSRS[i][0] = tColNames[i];
				tResultSSRS[i][1] = "RuleData";
			}
			
		}
		else
		{
			String queryColNames ="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				queryColNames =  "select column_name,table_name from user_tab_columns where lower(table_name) "
		                + " = ( "
		                + " select lower(tablename) from lrtemplatet where id='?tTemplateID?') "
		                + " order by column_id ";   
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				queryColNames= "select column_name,table_name from INFORMATION_SCHEMA.COLUMNS where lower(table_name) "
		                + " = ( "
		                + " select lower(tablename) from lrtemplatet where id='?tTemplateID?') "
			            + " order by ORDINAL_POSITION ";  
			}

			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(queryColNames);
			sqlbv2.put("tTemplateID", tTemplateID);
			tResultSSRS = tExeSQL.execSQL(sqlbv2).getAllData();
		}
		
		
		return tResultSSRS;
	}
	
	
	public  static TransferData getColumnNameAndTypes(String tTemplateID)
	{
		TransferData tTransferData = new TransferData();
		String tTableName = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL_Table = "select lower(tablename) from lrtemplatet where id='?tTemplateID?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_Table);
		sqlbv3.put("tTemplateID", tTemplateID);
		tTableName = tExeSQL.getOneValue(sqlbv3);
		if(tTableName.equals("ldsysvar"))
		{
			return null;
		}
		else if(tTableName.equals("ruledata"))
		{
			String tSQL_Column = "select columnnames,columntypes from lrruledata where id='?tTemplateID?'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_Column);
			sqlbv4.put("tTemplateID", tTemplateID);
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if(tSSRS.getMaxRow()>0)
			{
				String tTypes = tSSRS.GetText(1, 2);
				tTypes = StrTool.replace(tTypes, "Button", "String");
				tTransferData.setNameAndValue("columnnames", tSSRS.GetText(1, 1));
				tTransferData.setNameAndValue("columntypes", tTypes);
			}
		}
		else
		{
			String queryColNames ="";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				queryColNames = "select  column_name,data_type from user_tab_columns where lower(table_name) "
		                + " = ( "
		                + " select lower(tablename) from lrtemplatet where id='?tTemplateID?') "
		                + " order by column_id ";  
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				queryColNames= "select  column_name,data_type from INFORMATION_SCHEMA.COLUMNS where lower(table_name) "
			                + " = ( "
			                + " select lower(tablename) from lrtemplatet where id='?tTemplateID?') "
			                + " order by ORDINAL_POSITION ";  
			}
		
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(queryColNames);
			sqlbv5.put("tTemplateID", tTemplateID);
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv5);
			String tColumnNames = "";
			String tColumnTypes = "";
			//if(tSSRS.getMaxRow()>0)
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				String tType = tSSRS.GetText(i, 2);
				if(tType.toLowerCase().equals("varchar2"))
				{
					tType = "String";
				}
				else if(tType.toLowerCase().equals("button"))
					{
						tType = "String";
					}
				if(i==1)
				{
					tColumnNames = tSSRS.GetText(i, 1);
					tColumnTypes = tType;
				}
				else
				{
					tColumnNames = tColumnNames + ";" + tSSRS.GetText(i, 1);
					tColumnTypes = tColumnTypes + ";" + tType;
				}
				//tTransferData.setNameAndValue("columnnames", tSSRS.GetText(1, 1));
				//tTransferData.setNameAndValue("columntypes", tSSRS.GetText(1, 2));
			}
			
			tTransferData.setNameAndValue("columnnames", tColumnNames);
			tTransferData.setNameAndValue("columntypes", tColumnTypes);
		}
		
		
		return tTransferData;
	}
	
	
	public static Element getRuleData(String tTemplateID)
	{
		Element elem = null;
	    Connection conn = DBConnPool.getConnection();
	    CMySQLBlob tCMySQLBlob = new CMySQLBlob();
	    COracleBlob blob = new COracleBlob();
	    DOMBuilder domBuilder = new DOMBuilder();
	    String tSQL = " and id='"+tTemplateID+"' ";
	    Blob tBlob = null;
	    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  tBlob = blob.SelectBlob("LRRuleData", "RuleData", tSQL, conn);
	    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	    	 tBlob = tCMySQLBlob.SelectBlob("LRRuleData", "RuleData", tSQL, conn);	
	    }
	    try {
	    	
	    	SAXBuilder builder = new SAXBuilder();
	    	//
	    	//InputStreamReader strInStream = new InputStreamReader(tBlob.getBinaryStream(), "GBK");
	    	InputStreamReader strInStream = new InputStreamReader(tBlob.getBinaryStream(), "UTF-8");
			Document doc = builder.build(strInStream);
			elem = doc.getRootElement();
			conn.close();
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	      try
	         {
	           conn.close();
	        } catch(Exception ex1){}
	    }
	    
	    return elem;
	}
	
	public static Element getRuleDataByJSONString(String tJSONString)
	{
		Element elem = null;
	    
	    try {
	    	
	    	SAXBuilder builder = new SAXBuilder();
	    	
	    	String content = new String(tJSONString);
	    	InputStream is;
			try {
				is = new ByteArrayInputStream(content.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				is = null;
			}
			
			//Document doc = builder.build(new StringReader(tJSONString));
			Document doc = builder.build(is);
			elem = doc.getRootElement();
	    }
	    catch (Exception ex) {
	    	ex.printStackTrace();
	      try
	         {
	        } catch(Exception ex1){}
	    }
	    
	    return elem;
	}
	public static String replaceRuleDataSQL(String tOldSQL,String tTemplateID)
	{
		String replaceRuleDataSQL = "";
		LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
		tLRRuleDataDB.setId(tTemplateID);
		if(!tLRRuleDataDB.getInfo())
		{
			return tOldSQL;
		}
		tOldSQL = StrTool.replaceEx(tOldSQL, " $RuleData$ ",tLRRuleDataDB.getRuleDataSQL());  
		tOldSQL = StrTool.replaceEx(tOldSQL, "$RuleData$.","");  
		replaceRuleDataSQL = tOldSQL;
		return replaceRuleDataSQL;
	}

	public static String prepartResultSet(String tDTData,String tColumnName,String tColumnType)
	{	
		//准备结果集
		Element elem = getRuleDataByJSONString(tDTData);
		//拼接SQL
		String tResultSet = "";
		String[] DTRowDataArray;
		
		int dtRownum = 0;
	
			String[] tColumnNames = tColumnName.split(";");
			String[] tColumnTypes = tColumnType.split(";");
			if(tColumnNames.length!=tColumnTypes.length)
			{
				//如果列和类型不等的话,不做替换
				return tResultSet;
			}
			
			HashMap tDTColumnTypeMap = new HashMap();
			
			for(int i=0;i<tColumnNames.length;i++)
			{
				tDTColumnTypeMap.put(tColumnNames[i], tColumnTypes[i]);
			}
			
			List nodes = elem.getChildren();
			
			//记录DT表的行数
			dtRownum = nodes.size();
			DTRowDataArray = new String[dtRownum];
			for (int i = 0; i < nodes.size(); i++) {
				Element node = (Element) nodes.get(i);
				List childNodes = node.getChildren();
				//Map rowData=new HashMap();
				
				String tempDTRowData = "";
				try {
					for (int j = 0; j < childNodes.size(); j++) {
						

						Element childNode = (Element) childNodes.get(j);
						String dataType = (String) tDTColumnTypeMap.get(childNode.getName());
						
						if (dataType.equalsIgnoreCase("String")) {
							tempDTRowData = tempDTRowData + ", '" + childNode.getText() + "' " + childNode.getName();
							
						} else if (dataType.equalsIgnoreCase("Number")) {
							tempDTRowData = tempDTRowData + ", " + String.valueOf(Double.parseDouble(childNode.getText())) + " " + childNode.getName();
	
						} else if (dataType.equalsIgnoreCase("Date")) {
							tempDTRowData = tempDTRowData + ", " + "to_date('" + childNode.getText()+ "','yyyy-mm-dd hh24:mi:ss')," + " " + childNode.getName();
						}
						else if (dataType.equalsIgnoreCase("INT")) {
							tempDTRowData = tempDTRowData + ", " + String.valueOf(Integer.parseInt(childNode.getText())) + " " + childNode.getName();
						}
						else if (dataType.equalsIgnoreCase("BUTTON")) {
							tempDTRowData = tempDTRowData + ", '" + childNode.getText() + "' " + childNode.getName();
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					continue;
				}
				DTRowDataArray[i]=tempDTRowData.substring(tempDTRowData.indexOf(",")+1);	
			}
			
			
			for(int i=0;i<DTRowDataArray.length;i++)
			{
				if(i==0)
				{
					tResultSet = " ( " ;
					tResultSet = tResultSet + " select " + DTRowDataArray[i] + " from ldsysvar where sysvar='onerow' ";
				}
				else
				{
					tResultSet = tResultSet + " union " + " select " + DTRowDataArray[i] + " from ldsysvar where sysvar='onerow' ";
				}
				
				if(i==DTRowDataArray.length-1)
				{
					tResultSet = tResultSet + " ) ";
				}
			}
			logger.debug("tResultSet:"+tResultSet);
			//tResultSet = StrTool.replaceEx(tResultSet, "'","''");
			//tResultSet = "'"+tResultSet+"'";
			
		return tResultSet;
	}
	

	/**
	 * 处理SQL和数据的准备
	 * @param tCalFlag 是否是计算型规则的标记 True:当前规则为计算型规则 False:当前规则为校验型规则
	 * @param preTransferData 包含所有传递的信息
	 * String OldSQL 原始SQL
	 * String TableName 决策表名
	 * String DTData 界面传递过来的DT表的数据
	 * Map DTColumnTypeMap 界面传递过来的DT表的各列的类型
	 * 
	 * 
	 * @return
	 * 
	 * TransferData tResTransferData
	 */
	public static TransferData prepareSQLAndData(boolean tCalFlag,TransferData preTransferData)
	{
		TransferData tResTransferData = new TransferData();
		String tOldSQL = (String)preTransferData.getValueByName("OldSQL");
		String tDTData = (String)preTransferData.getValueByName("DTData");
		Map tDTColumnTypeMap = (Map)preTransferData.getValueByName("DTColumnTypeMap");
		String TableName = (String)preTransferData.getValueByName("TableName");
		String LRTemplate_ID = (String)preTransferData.getValueByName("LRTemplate_ID");
		String Version = (String)preTransferData.getValueByName("Version");
		//是否采用新方案标记,0-原方案(建决策表) ,1-新方案(伪结果集方式)
		String tConvertFlag = "0";
		//LRRuleDataSet tLRRuleDataSet = new LRRuleDataSet();
		LRRuleDataSchema tLRRuleDataSchema =new LRRuleDataSchema();
		int dtRownum = 0;
		
		String ColumnNames = "";
		String ColumnTypes = "";
		//解析DT表的数据.
		 
		ArrayList SQLArray = new ArrayList();
		//记录决策表的
		String[] tDTColName = null;
		String[] DTRowDataArray = null;
		//tongmeng 2011-05-23 modify
		ArrayList tDTRowDataArray = new ArrayList();
		String xml = "";
		
		//tongmeng 2011-08-01 modify
		//采用新方法获取数组
		VData tVData = transJsonData(tDTData);
		ArrayList tNameList = (ArrayList)tVData.get(0);
		ArrayList tDataList = (ArrayList)tVData.get(1);
		tDTColName  =  (String[])tNameList.get(0);
		xml = "";
		xml = "<records>";
		for(int i=0;i<tDataList.size();i++)
		{
			String[] oneRowData = (String[])tDataList.get(i);
			StringBuffer tempDTRowData = new StringBuffer();
			StringBuffer columnBuffer = new StringBuffer(256);
			StringBuffer valuesBuffer = new StringBuffer(256);
			
			for(int j=0;j<tDTColName.length;j++)
			{
				String tName = tDTColName[j];
				String tType = (String) tDTColumnTypeMap.get(tName);
				String tData = oneRowData[j];
				
				
				
				
				
	
				if(j==0)
				{
							xml = xml + "<rows>";
				}
						
				xml = xml +  "<"+tName+">"+tData+"</"+tName+">";
						
				if(j==tDTColName.length-1)
				{		
					xml = xml + "</rows>";
				}
				if(i==0)
				{
					if(j==0)
					{
						ColumnNames = tName;
						ColumnTypes = tType;
					}
					else
					{
						ColumnNames =  ColumnNames + ";" + tName;
						ColumnTypes =  ColumnTypes + ";" + tType;
					}
				}
						

				if (tType.equalsIgnoreCase("String")) {
					valuesBuffer.append("''").append( StrTool.cTrim(tData)).append("''");	
					tempDTRowData.append(", ''").append(StrTool.cTrim(tData)).append("'' ").append(tName);	
				} 
				else if (tType.equalsIgnoreCase("Button")) {
					valuesBuffer.append("''").append( tData ).append("''");
					tempDTRowData.append(", ''").append( tData).append( "'' ").append(  tName);	
				} 		
				else if (tType.equalsIgnoreCase("Number")) {
					if(tData == null || tData.equals("")){
						tData = "0";
					}
					valuesBuffer.append(Double.parseDouble(tData)+ ",");
					tempDTRowData.append(", ").append(String.valueOf(Double.parseDouble(tData))).append(" ").append(tName);
				} else if (tType.equalsIgnoreCase("Date")) {			
					valuesBuffer.append("to_date(''" + tData	+ "'',''yyyy-mm-dd hh24:mi:ss''),");
					tempDTRowData.append( ", " ).append( "to_date(''" ).append(tData).append("'',''yyyy-mm-dd hh24:mi:ss''),").append(" ").append( tName);
				}
				else if (tType.equalsIgnoreCase("INT")) {
					valuesBuffer.append(Integer.parseInt(tData)+ "");
					tempDTRowData.append(", ").append(String.valueOf(Integer.parseInt(tData))).append(  " " ).append(tName);
				}
				
				if(j!=tDTColName.length-1)
				{
					columnBuffer.append(tName + ",");		
					valuesBuffer.append(",");
				}
				else
				{
					columnBuffer.append(tName+")");		
					valuesBuffer.append(")");
				}
				
			}
				if(valuesBuffer!=null&&!valuesBuffer.toString().equals(""))
				{
					//columnBuffer.append("RuleId" + ")");
					//valuesBuffer.append("'" + RuleId + "')");

					StringBuffer sqlBuf = new StringBuffer("insert into ").append(
							 TableName).append(" (").append( columnBuffer.toString()).append(" values( ").append( valuesBuffer.toString());
					logger.debug("INSERT 语句::" + sqlBuf.toString());

					SQLArray.add(sqlBuf.toString());
					
					tDTRowDataArray.add(tempDTRowData.substring(tempDTRowData.indexOf(",")+1));
				}
			
		}
		
		
		xml = xml + "</records>";

	//	if(tCalFlag)
		{
			//计算型
			//如果dt表的数据小于最大限制数.则不采用建立决策表的方式.
			//if(dtRownum<=rowNumLimit)
			if(SQLArray.size()>0&&SQLArray.size()<=rowNumLimit)
			{
				//进行SQL替换
				logger.debug("before replace :"+tOldSQL);
				tConvertFlag= "1";
				//select ?BOMCont.AccoBodyCheck? ,RuleID from DTT1019 where 1=1   and ?BOMCont.Amnt? > Column0   and  ?BOMInsured.Avoirdupois? > Column1
				//输出决策表的列名
				for(int i=0;i<tDTColName.length;i++)
				{
					logger.debug("tDTColName["+i+"]:"+tDTColName[i]);
				}
				
				//进行伪结果集的拼写
				String tResultSet = "";
				DTRowDataArray = new String[tDTRowDataArray.size()];
				for(int i=0;i<tDTRowDataArray.size();i++)
				{
					DTRowDataArray[i] = (String)tDTRowDataArray.get(i);
				}
				for(int i=0;i<DTRowDataArray.length;i++)
				{
					//tongmeng 
					if(i==0)
					{
						tResultSet = " ( " ;
						tResultSet = tResultSet + " select " + DTRowDataArray[i] + " from ldsysvar where sysvar=''onerow'' ";
					}
					else
					{
						tResultSet = tResultSet + " union " + " select " + DTRowDataArray[i] + " from ldsysvar where sysvar=''onerow'' ";
					}
					
					if(i==DTRowDataArray.length-1)
					{
						tResultSet = tResultSet + " ) ";
					}
				}
				logger.debug("tResultSet:"+tResultSet);
				//tResultSet = StrTool.replaceEx(tResultSet, "'","''");
				//tongmeng 2011-05-06 modify
				//为防止拼接出来的SQL过长,先不进行结果集的替换 
				tOldSQL = StrTool.replaceEx(tOldSQL, " "+TableName+" "," $RuleData$ ");  
				tOldSQL = StrTool.replaceEx(tOldSQL, ""+TableName+".","$RuleData$.");  
				logger.debug("after replace :"+tOldSQL);
				
				//tongmeng 2011-05-09 add
				//drop old table 
				//稍后添加
				
				
				TableName = "RuleData";
				
				
				//准备LRRuleData表的数据
				tLRRuleDataSchema.setId(LRTemplate_ID);
				tLRRuleDataSchema.setVersion(Integer.parseInt(Version));
				//tLRRuleDataSchema.setRuleDataSQL("'"+tResultSet+"'");
				tLRRuleDataSchema.setRuleDataSQL(tResultSet);
				//new StringReader(xml)
				String content = new String(xml);
				logger.debug("ColumnNames："+ColumnNames);
				tLRRuleDataSchema.setColumnNames(ColumnNames);
				tLRRuleDataSchema.setColumnTypes(ColumnTypes);
				InputStream is;
				try {
					is = new ByteArrayInputStream(content.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					is = null;
				}
				tLRRuleDataSchema.setRuleData(is);
				
			}
			
		}
//		else
//		{
//			//非计算型规则,返回的xml数据不设值
//			xml = "";
//		}
		
		//转换标记
		tResTransferData.setNameAndValue("ConvertFlag", tConvertFlag);
		//结果集数据
		tResTransferData.setNameAndValue("RuleData", xml);
		//最终的SQL
		tResTransferData.setNameAndValue("FinalSQL", tOldSQL);
		//把决策表名修改为 RuleDate
		tResTransferData.setNameAndValue("TableName", TableName);
		tResTransferData.setNameAndValue("LRRuleDataSchema", tLRRuleDataSchema);
		tResTransferData.setNameAndValue("SQLArray", SQLArray);
		return tResTransferData;
	
	}
	
	//tongmeng 2011-08-01 add
	//先 转换和过滤jsondata
	private static VData transJsonData(String tDTData)
	{
		VData tVData = new VData();
		ArrayList tColNameList = new ArrayList();
		ArrayList tDataList = new ArrayList();
	
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(tDTData);
			//jsonObj.getJSONArray("Row");
			//为排序,修改了原来的toString方法
			String xml = toStringSorted(jsonObj,"records");
			logger.debug("xml:= " + xml);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new StringReader(xml));
			Element elem = doc.getRootElement();
			List nodes = elem.getChildren();
			
			//记录DT表的行数
			int dtRownum = nodes.size();
			//DTRowDataArray = new String[dtRownum];
			//JSONArray array=new JSONArray();
			for (int i = 0; i < nodes.size(); i++) {
				Element node = (Element) nodes.get(i);
				List childNodes = node.getChildren();
				//Map rowData=new HashMap();
				
				String tempDTRowData = "";
				String[] oneRowNames = new String[childNodes.size()];
				String[] oneRowData = new String[childNodes.size()];
				try {
					for (int j = 0; j < childNodes.size(); j++) {
					
						Element childNode = (Element) childNodes.get(j);
						
						String tColumnName = childNode.getName();
						String tColumnData = childNode.getText();
						if(tColumnData==null)
						{
							tColumnData = "";
						}
						
						if(i==0)
						{
							oneRowNames[j] = tColumnName;
						}
						oneRowData[j]= tColumnData;
						
					}
					
					if(i==0)
					{
						tColNameList.add(oneRowNames);
					}
					tDataList.add(oneRowData);
				}
				catch(Exception ex)
				{
					
				}
			}
			
			
			//开始做过滤
			ArrayList tFinalList = filterData(tColNameList,tDataList);
			 tVData.add(0,tColNameList);
			 tVData.add(1,tFinalList);
		}
		catch(Exception e)
		{
				
		}
				
		
		return tVData;
	}
	
	private static ArrayList filterData(ArrayList tColumnDataList,ArrayList tDataList)
	{
		ArrayList tFinalList = new ArrayList();
		String[] tColumnData = (String[])tColumnDataList.get(0);
		for(int i=0;i<tDataList.size();i++)
		{
			String[] tOneRowData = (String[])tDataList.get(i);
			boolean hasData = false;
			for(int j=0;j<tColumnData.length;j++)
			{
				String tCurrentData = tOneRowData[j];
				String tCuttrntName = tColumnData[j];
				if(tCuttrntName.toUpperCase().equals("RULEID")
					||tCuttrntName.toUpperCase().equals("UWLEVEL"))
				{
					continue;
				}
				if(!tCurrentData.equals(""))
				{
					hasData = true;
				}
			}
			
			if(hasData)
			{
				tFinalList.add(tOneRowData);
			}
		}
		logger.debug("tFinalList:"+tFinalList.size());
		return tFinalList;
		//tDataList = (ArrayList)tFinalList.clone();
	}
	
	
	
	public static String toStringSorted(Object o, String tagName)
    throws JSONException
  {
    StringBuffer b = new StringBuffer();

    if (o instanceof JSONObject)
    {
      if (tagName != null) {
        b.append('<');
        b.append(tagName);
        b.append('>');
      }

      JSONObject jo = (JSONObject)o;
      Iterator keys = jo.sortedKeys();
      //tongmeng 2011-06-13 modify
      //手工调整顺序
      Vector tKeyVector = new Vector();
      while (keys.hasNext()) {
    	  String k = keys.next().toString();
    	  logger.debug("$$$$k:"+k);
    	  tKeyVector.add(k);
      }
     // for(int i=0;i<tKeyVector.size();i++)
      //{
      if(tKeyVector.size()>1)
      {
    	  if(((String)tKeyVector.get(tKeyVector.size()-1)).equals("UWLevel")
    			  &&((String)tKeyVector.get(tKeyVector.size()-2)).equals("RuleId"))
    	  {
    		  tKeyVector.set(tKeyVector.size()-1, "RuleId");
    		  tKeyVector.set(tKeyVector.size()-2, "UWLevel");
    	  }
      }
      for(int i=0;i<tKeyVector.size();i++)
      {
    	  logger.debug("%%%%%%%%%%%%:"+(String)tKeyVector.get(i));
      }
     // }
     // while (keys.hasNext()) {
      for(int KeyVectori=0;KeyVectori<tKeyVector.size();KeyVectori++)
      {
        //String k = keys.next().toString();
    	  String k = (String)tKeyVector.get(KeyVectori);
      
        Object v = jo.opt(k);
        if (v == null)
          v = "";
        String s;
        
        if (v instanceof String)
          s = (String)v;
        else {
          s = null;
        }

        if (k.equals("content")) {
          if (v instanceof JSONArray) {
            JSONArray ja = (JSONArray)v;
            int len = ja.length();
            for (int i = 0; i < len; ++i) {
              if (i > 0) {
                b.append('\n');
              }
              b.append(escape(ja.get(i).toString()));
            }
          } else {
            b.append(escape(v.toString()));
          }

        }
        else if (v instanceof JSONArray) {
          JSONArray ja = (JSONArray)v;
          int len = ja.length();
          for (int i = 0; i < len; ++i)
            b.append(toStringSorted(ja.get(i), k));
        }
        else if (v.equals("")) {
          b.append('<');
          b.append(k);
          b.append("/>");
        }
        else
        {
          b.append(toStringSorted(v, k));
        }
      }
      if (tagName != null)
      {
        b.append("</");
        b.append(tagName);
        b.append('>');
      }
      return b.toString();
    }

    if (o instanceof JSONArray) {
      JSONArray ja = (JSONArray)o;
      int len = ja.length();
      for (int i = 0; i < len; ++i) {
        b.append(toString(
          ja.opt(i), (tagName == null) ? "array" : tagName));
      }
      return b.toString();
    }
    String s = (o == null) ? "null" : escape(o.toString());
    return 
      "<" + tagName + ">" + s + "</" + tagName + ">";
  }
	
	public static ArrayList getRuleDataArray(String tTemplateID,String tTableName)
	{
		ArrayList tArrayList = new ArrayList();
		if(tTableName.toLowerCase().equals("ruledata"))
		{
			Element elem = RuleInfoPrepare.getRuleData(tTemplateID);
			List nodes = elem.getChildren();
			for (int i = 0; i < nodes.size(); i++) {
				Element node = (Element) nodes.get(i);
				List childNodes = node.getChildren();
				String[][] tempStr = new String[childNodes.size()][2];
				for (int j = 0; j < childNodes.size(); j++) {
					Element childNode = (Element) childNodes.get(j);
					// String[] tempStr = new String[2];
					tempStr[j][0] = childNode.getName();
					tempStr[j][1] = childNode.getText();

				}
				tArrayList.add(tempStr);
			}
		}
		else
		{
			 String sql="select * from "+tTableName;
			 SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			 ExeSQL tExeSQL = new ExeSQL();
			 SSRS tSSRS = new SSRS();
			 tSSRS = tExeSQL.execSQL(sqlbv6);
			 String[][] tDTColums = getDtColumn(tTemplateID);
			 for(int i=1;i<=tSSRS.getMaxRow();i++)
			 {
				 String[][] tempStr = new String[tDTColums.length][2];
				 for(int j=0;j<tDTColums.length;j++)
				 {
						tempStr[j][0] = tDTColums[j][0];
						tempStr[j][1] = tSSRS.GetText(i, j+1);
				 }
				 tArrayList.add(tempStr);
			 }
			 
			
		}
		
		return tArrayList;
	}
}
