package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;
import org.json.XML;

public class QueryForDTData {
private static Logger logger = Logger.getLogger(QueryForDTData.class);
	private SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static ArrayList columnType=new ArrayList();
	
	public QueryForDTData()
	{
		
	}
	
	
	 public ArrayList queryDataBase(String sql)
	{
		 ArrayList rArrayList=new ArrayList();
		 Connection conn=DBConnPool.getConnection();
		 
		 Statement stmt=null;
		 ResultSet rs=null;
		 
			try {
				stmt=conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				stmt=null;
			}
		
		
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs=null;
		}
		
		rArrayList=translateReusltSet(rs);
		
		try {
			rs.close();
			logger.debug("ResultSet has been closed!");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			stmt.close();
			logger.debug("Statement has been closed!");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			conn.close();
			logger.debug("Connection has been closed!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rArrayList;
	}
	 
	public String queryDTData(String tTableName,String tTemplateID) 
	{
		String tResJSON = "";
		
		if(!tTableName.equals("RuleData"))
		{
			 String sql="select * from "+tTableName;
			   
			   logger.debug("查询后台的SQL语句是："+sql);
			   ArrayList result=this.queryDataBase(sql);
//			   logger.debug("查询后台的结果是："+result.toString());
			   
			   JSONArray array=new JSONArray();
			   Map map=new HashMap();
			   
			   
			   for(int i=0;i<result.size();i++)
			   {
				   map=(Map)result.get(i);
				   
				   array.put(map);
			   }
			   
			   JSONObject obj;
			try {
				obj = new JSONObject();
				   obj.put("totalProperty",result.size());
				   obj.put("rows",array);
				   tResJSON=obj.toString();
				   
				   
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
			   
		}
		else
		{
			
		    try {
				Element elem = RuleInfoPrepare.getRuleData(tTemplateID);
				List nodes = elem.getChildren();	
				JSONArray array=new JSONArray();
				for (int i = 0; i < nodes.size(); i++) {
					Element node = (Element) nodes.get(i);
					List childNodes = node.getChildren();
					Map rowData=new HashMap();
						for (int j = 0; j < childNodes.size(); j++) {
							Element childNode = (Element) childNodes.get(j);
							rowData.put(childNode.getName(), childNode.getText());
						}
						
					array.put(rowData);
				}
		     // tVData = XMLToVData(tOnePolData);
		      
		      
		      JSONObject obj;
				try {
					obj = new JSONObject();
					   obj.put("totalProperty",nodes.size());
					   obj.put("rows",array);
					   tResJSON=obj.toString();
					   //logger.debug("tResJSON@@:"+obj.toString());
					//tResJSON = "{\"totalProperty\":" +nodes.size()+",\"rows\":"+tSetString+"}";
					   
					   
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }
		    catch (Exception ex) {
		    	ex.printStackTrace();
		    }
		}
		
//		logger.debug("tResJSON:"+tResJSON);
		return tResJSON;
	}
	

	
	private ArrayList translateReusltSet(ResultSet rs)
	{
		ArrayList rsArrayList=new ArrayList();
		
		ResultSetMetaData rsmd=null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("@translateReusltSet()::rs.getMetaData()出错！");
		}
		
		int columnCount=0;
		try {
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("@translateReusltSet()::rsmd.getColumnCount();出错！");
		}
		int i=0;
		
		try {
			/**准备rsmd中的属性数据，有columnName,dataType,dataScale,dataPrecision
			 * 减少对rsmd的读取，大幅提速
			 */
			String[][] columnAttr = new String[columnCount][4];
			for(i=0;i<columnCount;i++){
				columnAttr[i][0] = rsmd.getColumnName(i+1);
				columnAttr[i][1] = String.valueOf(rsmd.getColumnType(i+1));
				columnAttr[i][2] = String.valueOf(rsmd.getScale(i+1));
				columnAttr[i][3] = String.valueOf(rsmd.getPrecision(i+1));
			}
			while(rs.next())
			{
				Map rowData=new HashMap();
				
				String columnValue="";
				
				for(i=0;i<columnCount;i++)
				{
					columnType.add(i,columnAttr[i][0]);
					columnValue=getDataValue(columnAttr[i],rs,i+1);
					rowData.put(columnAttr[i][0], columnValue);
				}
				rsArrayList.add(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("@translateReusltSet()::rs.next()出错！");
		}
		return rsArrayList;
	}
	
	public String getDataValue(String[] columnAttr, ResultSet rs, int i) {
		String strValue = "";

		try {
			int dataType = Integer.parseInt(columnAttr[1]);
			int dataScale = Integer.parseInt(columnAttr[2]);
			int dataPrecision = Integer.parseInt(columnAttr[3]);
			// 数据类型为字符
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR)) {
				// 由于存入数据库的数据是GBK模式，因此没有必要做一次unicodeToGBK
				// strValue = StrTool.unicodeToGBK(rs.getString(i));
				strValue = rs.getString(i);
			}
			// 数据类型为日期、时间
			else if ((dataType == Types.TIMESTAMP) || (dataType == Types.DATE)) {
				//rs.getDate(columnName)
				Date date=rs.getTimestamp(i);
				if(date==null)
					strValue="";
				else 
				    strValue = dateFormat.format(date);
			}
			// 数据类型为浮点
			else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT)) {
				// strValue = String.valueOf(rs.getFloat(i));
				// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
				strValue = String.valueOf(rs.getBigDecimal(i));
				// 去零处理
				strValue = PubFun.getInt(strValue);
			}
			// 数据类型为整型
			else if ((dataType == Types.INTEGER)
					|| (dataType == Types.SMALLINT)) {
				strValue = String.valueOf(rs.getInt(i));
				strValue = PubFun.getInt(strValue);
			}
			// 数据类型为浮点
			else if (dataType == Types.NUMERIC) {
				if (dataScale == 0) {
					if (dataPrecision == 0) {
						// strValue = String.valueOf(rs.getDouble(i));
						// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
						strValue = String.valueOf(rs.getBigDecimal(i));
					} else {
						strValue = String.valueOf(rs.getLong(i));
					}
				} else {
					// strValue = String.valueOf(rs.getDouble(i));
					// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
					strValue = String.valueOf(rs.getBigDecimal(i));
				}
				strValue = PubFun.getInt(strValue);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return StrTool.cTrim(strValue);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        QueryForDTData queryDTData=new QueryForDTData();
        
        String sql="select * from DTT0113";
        
        ArrayList al=queryDTData.queryDataBase(sql);
        
        Map map=null;
        String rowData="";
        for(int i=0;i<al.size();i++)
        {
        	map=(Map)al.get(i);
        	rowData="";
        	for(int j=0;j<map.size();j++)
        	{
        		String key=(String)columnType.get(j);
        		rowData+=" "+map.get(key);
        	
        	}
        	logger.debug("ROW"+i+"::"+rowData);
        }
	}

}
