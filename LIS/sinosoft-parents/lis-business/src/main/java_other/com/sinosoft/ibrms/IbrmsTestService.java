package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.sinosoft.ibrms.bom.AbstractBOM;
import com.sinosoft.lis.db.LRBOMDB;
import com.sinosoft.lis.db.LRBOMItemDB;
import com.sinosoft.lis.db.LRRuleDataDB;
import com.sinosoft.lis.db.LRTemplateTDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LRBOMItemSchema;
import com.sinosoft.lis.schema.LRBOMSchema;
import com.sinosoft.lis.schema.LRTemplateTSchema;
import com.sinosoft.lis.vschema.LRBOMItemSet;
import com.sinosoft.lis.vschema.LRBOMSet;
import com.sinosoft.lis.vschema.LRTemplateTSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

public class IbrmsTestService {
private static Logger logger = Logger.getLogger(IbrmsTestService.class);
	
	private List paramTypes = new ArrayList();
	LRBOMSet bomSet = new LRBOMSet();
	private List listNames = new ArrayList();
	private  Map mapBomItems = new HashMap();   
//	LRTemplateTSchema templateSchema ;
	Map mapTypes = new HashMap();
	List  listBoms = new ArrayList();
	List  listItems = new ArrayList();
	List  listTemplate = new ArrayList();
	LRTemplateTSet templateSet;
	private RuleMapCached mRuleMapCached = RuleMapCached.getInstance();
	
	private String mLanguage = "";
	
	public IbrmsTestService()
	{
		
	}
	
	public void setLanguage(String tLanguage)
	{
		this.mLanguage = tLanguage;
	}
	
	public void getRuleInfo(HttpServletRequest request){
		String javaClass = request.getParameter("javaClass");
		String methodName = request.getParameter("method");
		String templateId = request.getParameter("template");
		
		try {
			Class clazz = Class.forName(javaClass);
			Method[] methods = clazz.getDeclaredMethods();
			for(int i=0;i<methods.length;i++){
				Method method = methods[i];
				if(method.getName().equals(methodName))
				{
					Class[] classes = method.getParameterTypes();
					for(int j=0;j<classes.length;j++){
						Class paramType = classes[j];
						paramTypes.add(paramType);
						if (paramType.isArray()) {
						
							String className = paramType.getComponentType()
									.getName();
							String bomName = getBomName(className);
							mapTypes.put(bomName, paramType);
							listNames.add(bomName);
						} else {
							//logger.debug("class " + paramType.getName());
							String className = paramType.getName();
							String bomName =getBomName(className);
							listNames.add(bomName);
							mapTypes.put(bomName, paramType);
						}
						
						
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//查出用到的几个bom对象
		
		StringBuffer sqlBuf =new StringBuffer( "select * from lrbom where name in ( ");
		StringBuffer sqlItemBuf = new StringBuffer("select * from lrbomitem where valid=1 and bomname in ( ");
		
		String className;
		for(int i=0;i<listNames.size();i++){
			String bomName = (String)listNames.get(i);
			
			sqlBuf.append("'"+bomName+"'");
			sqlItemBuf.append("'"+bomName+"'");
			if(i<paramTypes.size()-1){
				sqlBuf.append(",");
				sqlItemBuf.append(",");
			}
		}
		sqlBuf.append(")");
		sqlItemBuf.append(")");
		
		sqlBuf.append(" and name<>'BOMDTTable' ");
		
		sqlItemBuf.append(" and bomname<>'BOMDTTable' ");
		LRBOMDB bomDb = new LRBOMDB();
		
		bomSet = bomDb.executeQuery(sqlBuf.toString());
		
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i+1);
			String name = bomSchema.getName();
    	}
		
		LRBOMItemDB  itemDb = new LRBOMItemDB();
		LRBOMItemSet itemSet = new LRBOMItemSet();
		
		itemSet = itemDb.executeQuery(sqlItemBuf.toString());
		for(int i=0;i<itemSet.size();i++){
			LRBOMItemSchema itemSchema = itemSet.get(i+1);
			String bomName = itemSchema.getBOMName();
			List list = (List)mapBomItems.get(bomName);
			if(list==null){
				list = new ArrayList();
				mapBomItems.put(bomName, list);
			}
			list.add(itemSchema);
		}
		
		templateSet = new LRTemplateTSet();
		LRTemplateTDB templateDb = new LRTemplateTDB();
		
		sqlBuf =new StringBuffer( "select * from lrtemplatet where  id in ( ");
		String[] arrTemplates = templateId.split(",");
		
		for(int i=0;i<arrTemplates.length;i++){
			String id  = (String)arrTemplates[i];
			
			sqlBuf.append("'"+id+"'");
			
			if(i<arrTemplates.length-1){
				sqlBuf.append(",");
				
			}
		}
		sqlBuf.append(")");
		
		//String sql = "select * from lrtemplatet where  id='"+templateId+"'";
		templateSet = templateDb.executeQuery(sqlBuf.toString());
		if(templateSet.size()>0){
		    for(int i=1;i<=templateSet.size();i++){
		    	LRTemplateTSchema templateSchema = templateSet.get(i);
		    	String boms = templateSchema.getBOMs();
				String sqlParams = templateSchema.getSQLParameter();
				String[] arrBoms = boms.split(",");
				String[] arrSqlParams = sqlParams.split(",");
				
				for(int j=0;j<arrBoms.length;j++){
					if(!listBoms.contains(arrBoms[j]))
						listBoms.add(arrBoms[j]);
				}
				
				for(int j=0;j<arrSqlParams.length;j++){
					if(!listItems.contains(arrSqlParams[j]))
						listItems.add(arrSqlParams[j]);
				}
		    }
		}
		else{
			//templateSchema = null;
		}
		
	}
	
	
	
	/**
	 * 输出左边菜单栏中的步骤
	 * 步骤一： 测试信息
	 * 最后一个步骤 测试结果
	 * @param request
	 * @return
	 */
	public String writeStep(HttpServletRequest request){
		List listSteps = getBomsForStep();
		StringBuffer buf = new StringBuffer(1024);
		buf.append("<div id='step1'> <a href='#'  onclick='javascript:gotoStep(1);'>步骤1:  规则信息</a></div>\r\n");
		
		for(int i=0;i<listSteps.size();i++){
			String bomName = (String)listSteps.get(i);
			
			LRBOMSchema bomSchema = getBOMSchemaByName(bomName); //bomSet.get(i+1);
			if(bomSchema==null)
				continue;
			buf.append("<div id='step"+(i+2)+"'> <a href='#'  onclick='javascript:gotoStep("+(i+2)+");'>步骤"+
					    (i+2)+"：  "+getBomCnName(bomSchema.getName())+"</a></div>\r\n");
		}
		
		buf.append("<div id='step"+(listSteps.size()+2)+"'> <a href='#'  onclick='javascript:gotoStep("+
				(listSteps.size()+2)+");'>步骤"+((listSteps.size()+2))+":  测试结果</a></div>\r\n");
		
		return buf.toString();
	}
	
	protected LRBOMSchema getBOMSchemaByName(String bomName){
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i+1); //bomSet.get(i+1);
			if(bomSchema.getName().equals(bomName))
				return bomSchema;
			
		}
		return null;
		
	}
	/**
	 * 输出项目的概述
	 * @param request
	 * @return
	 */
	public String writeRuleInfo(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		if(templateSet.size()==0)
			return buf.toString();
		
	//	String boms = templateSchema.getBOMs();
	//	String sqlParams = templateSchema.getSQLParameter();
		buf.append("<div>当前测试的规则有 ： " + "</div>\r\n");
		for(int i=1;i<=templateSet.size();i++){
			LRTemplateTSchema templateSchema =templateSet.get(i);
			if(templateSchema.getRuleCalCh()!=null&&!templateSchema.getRuleCalCh().equals(""))
			{
				buf.append("<div>&nbsp;&nbsp;&nbsp<a target='_blank' href='RuleMakeNew.jsp?flag=0&LRTemplate_ID="
						+templateSchema.getId()+"&LRTemplateName=LRTemplatet'>  " + templateSchema.getRuleName()
						+ "</a></div>\r\n");
			}
			else
			{
				buf.append("<div>&nbsp;&nbsp;&nbsp<a target='_blank' href='RuleMake.jsp?flag=0&LRTemplate_ID="
						+templateSchema.getId()+"&LRTemplateName=LRTemplatet'>  " + templateSchema.getRuleName()
						+ "</a></div>\r\n");
			}
		}
		buf.append("<br>");
		buf.append("<br>");
		
	//		String[] arrBoms = boms.split(",");
		buf.append("<div>当前规则使用的bom对象有 ： " + "</div>\r\n");
		for (int i = 0; i < listBoms.size(); i++) {
			String bom = (String)listBoms.get(i);
			buf.append("<div>&nbsp;&nbsp;&nbsp<a target='_blank' href='"+"bomUpdate.jsp?bbName="+bom+"&flag=view'>" + getBomCnName(bom)
					+ "</a></div>\r\n");
		}
		buf.append("<br>");
		buf.append("<br>");

		buf.append("<div>当前规则使用的bom对象词条有 ： " + "</div>");
		//String[] arrSqlParams = sqlParams.split(",");
		for (int i = 0; i < listItems.size(); i++) {
			String bomField =(String)listItems.get(i);
			if(bomField.equals("NULL"))
			{
				continue;
			}
			int index = bomField.indexOf(".");
			String bomName = bomField.substring(0, index);
			String fieldName = bomField.substring(index + 1);
			
			//tongmeng 2010-12-27 modify
			if(bomName.equals("BOMSubCal")||bomName.equals("BOMDTTable")||bomName.equals("NULL"))
			{
				continue;
			}
			buf.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ getBomCnName(bomName) + this.mRuleMapCached.getMsg("LINK_OF", mLanguage,"的")+" <a target='_blank' href='itemUpdate.jsp?itemName="
					+fieldName+"&bomName="+bomName+"&flag=view'>"
					+ getItemCnName(bomName, fieldName) + "</a></div>\r\n");

		}
		
		buf.append("<br>");
		buf.append("<br>");
		buf.append("<div>当前规则使用的子算法有 ： " + "</div>");
		
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "select id from lrtemplate where 1=1 ";
		String tResultID = "";
		for (int i = 0; i < listItems.size(); i++) {
			String bomField =(String)listItems.get(i);
			if(bomField.equals("NULL"))
			{
				continue;
			}
			int index = bomField.indexOf(".");
			String bomName = bomField.substring(0, index);
			String fieldName = bomField.substring(index + 1);
			
			//tongmeng 2010-12-27 modify
			if(!bomName.equals("BOMSubCal"))
			{
				continue;
			}
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSQL+" and rulename='?fieldName?'");
			sqlbv3.put("fieldName", fieldName);
			tResultID = tExeSQL.getOneValue(sqlbv3);
			buf.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "算法编码" + ": <a target='_blank' href='RuleMakeNew.jsp?flag=0&LRTemplate_ID="+tResultID+"&LRTemplateName=LRTemplate'>"
					+ fieldName + "</a></div>\r\n");

		}

		return buf.toString();
	}
	/**
	 * 创建弹出窗口的脚本，只要是参数中为数组的，都需要一个单独的窗口来输入数据
	 * 如果该bom对象还有子对象，那么还需要创建tab页，每个tab页为一个ext grid
	 */
	public String writeWinInfo(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		for(int i=0;i<paramTypes.size();i++){
			Class paramType = (Class)paramTypes.get(i);
			if(!paramType.isArray())
				continue;
			buf.append(getWinInfo(paramType));
			buf.append("\r\n");
			buf.append("\r\n");
		}
		
		return buf.toString();
	}
	
	protected String getWinInfo(Class paramType){
		StringBuffer buf = new StringBuffer(1024);
		
		String className = paramType.getComponentType()
		.getName();
        String bomName = getBomName(className);
        LRBOMSchema bomSchema = getBOMSchemaByName(bomName); //bomSet.get(i+1);
		if(bomSchema==null)
			return buf.toString();
		
		String childBoms = getChildBoms(bomName);
		buf.append("<div id='win_"+bomName+"' class='x-hidden' bomName='"+bomName+"' childBoms='"+childBoms+"'>\r\n");
		buf.append("    <div class='x-window-header'>"+bomSchema.getCNName()+"详细信息</div>\r\n");
		buf.append("    <div id='tabs_"+bomName+"'>\r\n");
		buf.append("        <div class='x-tab' id='tab_"+bomName+"' bomName='"+bomName+"' title='"+bomSchema.getCNName()+"'>\r\n");
		    buf.append(writeEditPage4Step(bomName));
		buf.append("        </div>\r\n");
		
		/**
		 * 以下是子bom对象的html代码
		 */
		String[] arrBoms = childBoms.split(",");
		for(int i=0;i<arrBoms.length;i++){
			String childBom = arrBoms[i];
			LRBOMSchema bomChildSchema = getBOMSchemaByName(childBom);
			if(bomChildSchema==null)
				continue;
			buf.append("        <div class='x-tab' id='tab_grid_"+childBom+"' bomName='"+childBom+"' title='"+bomChildSchema.getCNName()+"'>\r\n");
			//展示editGrid的container
			buf.append("              <div id='grid_"+childBom+"'></div>\r\n");
			buf.append("        </div>\r\n");
		}
		
		buf.append("    </div>\r\n");
		buf.append("</div>\r\n");
		return buf.toString();
	}
	
	protected List getBomsForStep(){
		List list = new ArrayList();
		for(int i=0;i<listNames.size();i++){
			String bomName =(String) listNames.get(i);
			LRBOMSchema bomSchema =getBOMSchemaByName(bomName) ;
			if(bomSchema==null)
				continue;
			
			String fatherBom = bomSchema.getFBOM();
			if(fatherBom== null) //没有父Bom对象的可以放在步骤中
			{
				list.add(bomName);
				continue;
			}
			while(true){ //父bom包括祖先bom不是数组的，也可以放在步骤中
				LRBOMSchema bomSchema1 =getBOMSchemaByName(fatherBom) ;
				if(bomSchema1==null){
					list.add(bomName);
					break;
				}
				Class paramType = (Class)mapTypes.get(bomName);
				if(paramType.isArray()) //如果是数组的
				    break;	
				fatherBom = bomSchema1.getFBOM();
				if(fatherBom==null)
				{
					list.add(bomName);
					break;
	    		}
				
			}
		}
		return list;
		
	}
	
	protected String getChildBoms(String bomName){
		StringBuffer buf = new StringBuffer();
		int count=0;
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i+1);
			String fatherBom = bomSchema.getFBOM();
			if(bomName.equals(fatherBom))
			{
				if(count>0)
					buf.append(",");
				buf.append(bomSchema.getName());
				count++;
				continue;
			}
    	}
		return buf.toString();
	}
	/**
	 * boms的中文名称
	 * @param bomName
	 * @return
	 */
	protected String getChildCnBoms(String bomName){
		StringBuffer buf = new StringBuffer();
		int count=0;
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i+1);
			String fatherBom = bomSchema.getFBOM();
			if(bomName.equals(fatherBom))
			{
				if(count>0)
					buf.append(",");
				buf.append(this.mRuleMapCached.getMsg(bomSchema.getName(), mLanguage,bomSchema.getCNName()));
				count++;
				continue;
			}
    	}
		return buf.toString();
	}
	public String writeFormStepInfo(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		List listSteps = getBomsForStep();
		
		
		Map mapUsedItem = new HashMap();
		for(int i=0;i<listSteps.size();i++){
			String bomName =(String) listSteps.get(i);
			LRBOMSchema bomSchema =getBOMSchemaByName(bomName) ;
			if(bomSchema==null)
				continue;
			
			String fatherBom = bomSchema.getFBOM();
			buf.append("<div id='formStep"+(i+2)+"' bomName='"+bomSchema.getName()+"'");
			if(fatherBom!=null && fatherBom.length()>0)
				buf.append(" fatherBom='"+fatherBom+"'");
			Class paramType = (Class)mapTypes.get(bomName);
			if(paramType.isArray())
				buf.append(" isArray=true");
			String childBoms = getChildBoms(bomName);
			buf.append(" childBoms='"+childBoms+"'");
			buf.append(" style='display:none;overflow-y:scroll'>\r\n");
			
			if(!paramType.isArray()) //不是数组的编辑页面
			{
				buf.append(writeEditPage4Step(bomName));
			}else{
				//展示editGrid的container
				buf.append("<div id='grid_"+bomName+"'></div>\r\n");
			}
			buf.append("</div>\r\n");
		}
		
		//写最后一个页面，即测试页面
		buf.append("<div id='formStep"+(listSteps.size()+2)+"' style='display:none'>");
		buf.append("<div>测  试  结   果</div>\r\n");
		buf.append("<div id='testSummary'></div>\r\n");
		buf.append("<br/><br/>\r\n");
		buf.append("<div id='testResult'></div>\r\n");
		buf.append("</div>\r\n");
		
		
		
		return buf.toString();
	}
	
	/**
	 * 单条数据的编辑页面
	 * @param bomName
	 * @return
	 */
	protected String writeEditPage4Step(String bomName){
		Map mapUsedItem = new HashMap();
		StringBuffer buf = new StringBuffer(1024);
		List list = (List)mapBomItems.get(bomName);
		if(list!=null) //
		{
			for(int j=0;j<list.size();j++){ //如果是基础词条，则生成一个隐藏的input,主要是为了统一生成json
				LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(j);
				
				if("SQL".equals(itemSchema.getSourceType()))
					buf.append("<input type='hidden' id='"+bomName+"."+itemSchema.getName()+"' itemName='"+
							//getItemCnName(itemSchema.getBOMName(),itemSchema.getName())+"' bomItem=true value=''></input>\r\n");
							itemSchema.getName()+"' bomItem=true value=''></input>\r\n");
			}
		}
		buf.append("   <table>\r\n");
		//先构造测试数据需要的词条，这样可以方便用户输入
	
		int colCount = 0;
		
 		//if(templateSchema!=null){
		for (int i = 0; i < listItems.size(); i++) {
			// String boms = templateSchema.getBOMs();
			// String sqlParams = templateSchema.getSQLParameter();
			// String[] arrSqlParams = sqlParams.split(",");
			// for(int j=0;j<arrSqlParams.length;j++){
			String bomField = (String) listItems.get(i); // arrSqlParams[j];
			if(bomField.equals("NULL"))
			{
				continue;
			}
			int index = bomField.indexOf(".");
			String bomName1 = bomField.substring(0, index);
			String fieldName = bomField.substring(index + 1);
			if (bomName.equals(bomName1)) {
				mapUsedItem.put(fieldName, new Boolean(true));
				LRBOMItemSchema itemSchema = getBomItemSchema(bomName,
						fieldName);
				if (itemSchema != null) {
					if (colCount == 0)
						buf.append("    <tr>\r\n");
					buf.append("<td class=title style='color:blue'>"
							+ getItemCnName(itemSchema.getBOMName(),itemSchema.getName()) + "</td>");
					buf.append("<td class=input style='color:blue' id='col."
							+ bomField + "'></td>");
					colCount += 2;
					if (colCount == 4) // 到了行末
					{
						colCount = 0;
						buf.append("    </tr>\r\n");
					}

				}
			}
		}
		
		
		list = (List)mapBomItems.get(bomName);
		if(list!=null){
			for(int j=0;j<list.size();j++){
				LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(j);
				if(mapUsedItem.get(itemSchema.getName())!=null) //已经用过了
					continue;
				if(colCount==0)
				     buf.append("    <tr>\r\n");
				buf.append("<td class=title>"+getItemCnName(itemSchema.getBOMName(),itemSchema.getName())+"</td>");
				buf.append("<td class=input id='col."+bomName+"."+itemSchema.getName()+"'></td>");
				
				colCount += 2;
				if(colCount==4) //到了行末
				{
					colCount = 0;
					buf.append("    </tr>\r\n");
				}
			}
		}
		
		boolean bAddRow = false;
		if(colCount>0 )
			bAddRow = true;
		for(;colCount<3;colCount++){ //补齐缺失的td
			buf.append("<td class=title></td>");
		}
		if(bAddRow)
			buf.append("    </tr>\r\n");
		
		buf.append("  </table>\r\n");
		
		
		return buf.toString();

	}
	
	/**
	 * 自动生成grid的javaScript语句，主要包括fields的定义，以及columns的定义
	 * @param bomName
	 * @return
	 */
	protected String writeGridScript4Step(String bomName,String bomCnName,String gridContainer){
		
		Map mapUsedItem = new HashMap();
		StringBuffer buf = new StringBuffer(1024);
		List list = (List)mapBomItems.get(bomName);
		if(list==null)
			return "";
		
		buf.append("var fields = ["); 
		for(int j=0;j<list.size();j++){ 
			LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(j);
			if(j>0)
				buf.append(",\r\n");
			buf.append("{name: '"+itemSchema.getName()+"',type: '");
			String type=itemSchema.getCommandType();
			if("Number".equals(type))
				type="float";
			else if("Date".equals(type))
				type="date";
			else
				type="string";
			buf.append(type+"'}");
				
	
		}
		buf.append("];\r\n");
		buf.append("\r\n");
		
		buf.append("var columns=[");

	
		//先构造测试数据需要的词条，这样可以方便用户输入
	
		int count = 0;
		
 		
		for (int j = 0; j < listItems.size(); j++) {
			String bomField = (String)listItems.get(j);
			if(bomField.equals("NULL"))
			{
				continue;
			}
			int index = bomField.indexOf(".");
			String bomName1 = bomField.substring(0, index);
			String fieldName = bomField.substring(index + 1);
			if (bomName.equals(bomName1)) {
				mapUsedItem.put(fieldName, new Boolean(true));
				LRBOMItemSchema itemSchema = getBomItemSchema(bomName,
						fieldName);
				if (itemSchema != null) {
					if (count > 0)
						buf.append(",");
					buf.append("{\r\n");
					buf.append("  id: '" + itemSchema.getName() + "',\r\n");
					buf.append("    header: '" + this.mRuleMapCached.getMsg(itemSchema.getBOMName()+"_"+itemSchema.getName(), mLanguage,itemSchema.getCNName())
							+ "',\r\n");
					buf.append("    dataIndex: '" + itemSchema.getName()
							+ "',\r\n");
					if ("SQL".equals(itemSchema.getSourceType()))
						buf.append("   renderer   : renderComboDisplayText,");
					buf.append("    width: 100,\r\n");
					buf.append(getGridEditorScript(itemSchema, bomName, count));
					buf.append("}");
					count++;

				}
			}
		}
	
		
    	for(int j=0;j<list.size();j++){
				LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(j);
				if(mapUsedItem.get(itemSchema.getName())!=null) //已经用过了
					continue;
				if(count>0)
					buf.append(",");
				buf.append("{\r\n");
				buf.append("  id: '"+itemSchema.getName()+"',\r\n");
				buf.append("    header: '"+this.mRuleMapCached.getMsg(itemSchema.getBOMName()+"_"+itemSchema.getName(), mLanguage,itemSchema.getCNName())+"',\r\n");
				buf.append("    dataIndex: '"+itemSchema.getName()+"',\r\n");
				if("SQL".equals(itemSchema.getSourceType()))
					buf.append("   renderer   : renderComboDisplayText,");
				buf.append("    width: 100,\r\n");
                buf.append(getGridEditorScript(itemSchema,bomName,count));
                buf.append("}");
                count++;
			
		}
    	buf.append("];\r\n");
    	
    	String bomCnNames = getChildCnBoms(bomName);
    	String title = bomCnName;
    	if(bomCnNames.length()>0)
    		title = title+" ( 其子BOM对象为"+bomCnNames+")";
		
    	buf.append("  makeGrid(fields,columns,'grid_"+bomName+"','"+title+"','"+gridContainer+"','"+bomName+"');\r\n");
    	
    	

		
		return buf.toString();

	}
	
	protected String getGridEditorScript(LRBOMItemSchema itemSchema,String bomName,int index){
		StringBuffer buf= new StringBuffer(256);
		String type = itemSchema.getCommandType();
		String itemName = itemSchema.getName();
		if ("String".equals(type)) // 字符类型
		{
			if ("SQL".equals(itemSchema.getSourceType())) {
				buf.append("   editor: makeGridComboField(\"" + itemSchema.getSource() + "\",'"+bomName+"','"+itemName+"',"+index+")");
					
			} else {
				buf.append(" editor: new Ext.form.TextField({ })\r\n");
			}
		} else if ("Number".equals(type)) { // 数字类型
			buf.append(" editor: new Ext.form.NumberField({ })\r\n");
		} else if ("Date".equals(type)) { // 日期类型
			buf.append("   renderer: formatDate,\r\n" 
					+ "    editor : new Ext.form.DateField({format: 'Y-m-d H:i:s'})\r\n");
		}
		return buf.toString();
	}
	
	protected String writeEditScript4Step(String bomName){
	    StringBuffer buf = new StringBuffer(1024);
		List list = (List) mapBomItems.get(bomName);
		if (list != null) //
		{
			for (int j = 0; j < list.size(); j++) { // 如果是基础词条，则生成一个隐藏的input,
													// 主要是为了统一生成json
				LRBOMItemSchema itemSchema = (LRBOMItemSchema) list.get(j);
				String type = itemSchema.getCommandType();
				if ("String".equals(type)) // 字符类型
				{
					if ("SQL".equals(itemSchema.getSourceType())) {
						buf.append("makeFormComboField('" + bomName + "."
								+ itemSchema.getName() + "','"
								+ itemSchema.getName() + "',\""
								+ itemSchema.getSource() + "\");\r\n");
					} else {
						buf.append("makeFormTextField('" + bomName + "."
								+ itemSchema.getName() + "','"
								+ itemSchema.getName() + "');\r\n");
					}
				} else if ("Number".equals(type)) { // 数字类型
					buf.append("makeFormNumberField('" + bomName + "."
							+ itemSchema.getName() + "','"
							+ itemSchema.getName() + "');\r\n");
				} else if ("Date".equals(type)) { // 日期类型
					buf.append("makeFormDateField('" + bomName + "."
							+ itemSchema.getName() + "','"
							+ itemSchema.getName() + "');\r\n");
				}
			}
		}

		return buf.toString();
    }

	
	
	public String writeTestScript(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		List listSteps = getBomsForStep();
		int maxSteps = listSteps.size()+2;
		
		buf.append("    maxSteps="+maxSteps+";\r\n");
		
	//	buf.append("    function initBomComponent(bomName){\r\n");
		for(int i=0;i<listSteps.size();i++){
			String bomName = (String)listSteps.get(i);
			LRBOMSchema bomSchema =getBOMSchemaByName(bomName) ;
			if(bomSchema==null)
				continue;
			Class paramType = (Class)mapTypes.get(bomName);
			if(!paramType.isArray())
			{
		//		buf.append("   if(bomName=='"+bomName+"'){\r\n");
				buf.append(writeEditScript4Step(bomName));
		//		buf.append("}\r\n");
			}else{
		//		buf.append("   if(bomName=='grid_"+bomName+"'){\r\n");
				buf.append(writeGridScript4Step(bomName, this.getBomCnName(bomName),"formStep"+(i+2)));			
	     //       buf.append("}\r\n");		
			}
		}
		
		buf.append(writeWinScript(request));
		
	//	buf.append("}\r\n");
		
		return buf.toString();

	}
	
	/**
	 * 对于生成的win进行
	 * @param request
	 * @return
	 */
	public String writeWinScript(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		List listSteps = getBomsForStep();
		
		for(int i=0;i<listNames.size();i++){
			String bomName = (String)listNames.get(i);
			LRBOMSchema bomSchema =getBOMSchemaByName(bomName) ;
			if(bomSchema==null)
				continue;
			Class paramType = (Class)mapTypes.get(bomName);
			if(listSteps.contains(bomName) && !paramType.isArray() )
				continue;
			
			//buf.append(" if(bomName=='tab_"+bomName+"')\r\n{");
			buf.append(writeEditScript4Step(bomName));
			//buf.append("}\r\n");
			
			if(paramType.isArray()&& !listSteps.contains(bomName))	
			{
				//buf.append(" if(bomName=='grid_"+bomName+"')\r\n{");
				buf.append(writeGridScript4Step(bomName, bomSchema.getCNName(),"tab_grid_"+bomName));			
			    //buf.append("}\r\n");
		       
			}
		}
		return buf.toString();

	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public String writeFormScript(HttpServletRequest request){
		StringBuffer buf = new StringBuffer(1024);
		return buf.toString();
	}
	
	
     protected  String getBomName(String className){
		
		String bomName;
		int index = className.lastIndexOf(".");
		if(index>0)
			className = className.substring(index+1);
		bomName = className;
	//	if(className.startsWith("BOM"))
		//	bomName = className.substring(3);
	    return bomName;		
	}
	
	protected String getBomCnName(String bomName){
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i+1);
			if(bomSchema.getName().equals(bomName))
				return this.mRuleMapCached.getMsg(bomSchema.getName(), mLanguage,bomSchema.getCNName()) ;
				//bomSchema.getCNName();
		}
		return "";
	}
	
	protected String getItemCnName(String bomName,String itemName){
		List list = (List)mapBomItems.get(bomName);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(i);
				if(itemSchema.getName().equals(itemName))
					return this.mRuleMapCached.getMsg(itemSchema.getBOMName()+"_"+itemSchema.getName(), mLanguage,itemSchema.getCNName()); 
			}
			
		}
		return "";
	}
	
	protected LRBOMItemSchema getBomItemSchema(String bomName,String itemName){
		List list = (List)mapBomItems.get(bomName);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				LRBOMItemSchema itemSchema = (LRBOMItemSchema)list.get(i);
				if(itemSchema.getName().equals(itemName))
					return itemSchema;
			}
		}
		return null;
	}
	
	
	public String test(HttpServletRequest request){
		String javaClass = request.getParameter("javaClass");
		String methodName = request.getParameter("method");
		String templateId = request.getParameter("template");
		String json = request.getParameter("json");
		try {
			json =  java.net.URLDecoder.decode(json, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		LRTemplateTSet templateSet = new LRTemplateTSet();
		LRTemplateTDB templateDb = new LRTemplateTDB();
		
		StringBuffer sqlBuf =new StringBuffer( "select * from lrtemplatet where  id in ('?id?'");
		String[] arrTemplates = templateId.split(",");
		ArrayList<String> idArr=new ArrayList<String>();
		for(int i=0;i<arrTemplates.length;i++){
			String id  = (String)arrTemplates[i];
			idArr.add(id);
//			sqlBuf.append("'"+id+"'");
//			
//			if(i<arrTemplates.length-1){
//				sqlBuf.append(",");
//				
//			}
		}
		sqlBuf.append(")");
		
		//String sql = "select * from lrtemplatet where  id='"+templateId+"'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlBuf.toString());
		sqlbv4.put("id", idArr);
		templateSet = templateDb.executeQuery(sqlbv4);
		
		String sql = "select * from lrtemplatet where  id='?templateId?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("templateId", templateId);
		templateSet = templateDb.executeQuery(sqlbv5);
/*		if(templateSet.size()>0)
			 templateSchema = templateSet.get(1);
		else
			templateSchema = null;
		
		if(templateSchema==null)
				return "";*/
		List listBomsObj = makeBoms(json,javaClass,methodName);
		long start = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = df.format(cal.getTime());
		
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		Connection conn=null;
		try{
			conn =  DBConnPool.getConnection();
			ExeSQL   exeSql  = new ExeSQL(conn);
			String altSession="alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(altSession);
			exeSql.execUpdateSQL(sqlbv);
			int resultCount = 0;
			String CalFlag = "0";
			for(int j=1;j<=templateSet.size();j++){
				LRTemplateTSchema templateSchema = templateSet.get(j);
		    	String tableName = templateSchema.getTableName();

		    	//tongmeng 2010-12-27 modify
		    	
		    	if(templateSchema.getRuleCalCh()!=null&&
		    			!templateSchema.getRuleCalCh().equals(""))
		    	{
		    		CalFlag = "1";
		    	}

		    	
				SQLTask sqlTask = new SQLTask( templateSchema.getSQLStatement(), templateSchema.getSQLParameter(),templateSchema.getBOMs(),
						templateSchema.getId(),listBomsObj,(new RuleTask(listBomsObj,templateSchema.getBusiness(),templateSchema.getRuleName())),templateSchema.getRuleName());

		    	
				List results = sqlTask.executeForTest();
				String viewParameter = templateSchema.getViewParameter();
			    resultCount+= results.size();
				for(int i=0;i<results.size();i++){
	     			SQLTaskResult result = (SQLTaskResult)results.get(i);
	     			//tongmeng 2010-12-29 modify
	     			if(result.getRuleid().equals("-1"))
	     			{
	     				resultCount = 0;
	     				continue;
	     			}
	     			TestResult testResult = null;
	     			//tongmeng 2011-05-31 modify
	     			//多语言方案
	     			String tRuleCh = this.replaceRule(templateSchema.getRuleCh());
	     			String tCalRuleCh = this.replaceRule(templateSchema.getRuleCalCh());
	     			testResult = MakeTestResult(result,tRuleCh,exeSql,tableName,templateSchema.getSQLParameter(),templateSchema.getId(),templateSchema.getRuleCalCh());
	     		
	     			Map map = new HashMap();
	     			map.put("bomsDesc", testResult.getBoms());
	     			map.put("rule", testResult.getRule());
	     			map.put("result", testResult.getResult());
	     			map.put("calrule", tCalRuleCh);
	     			array.put(map);
	     		}
				
		    	/*
			    Rule rule = new Rule();
			    List results = rule.execRule(listBomsObj, templateSchema.getSQLStatement(), templateSchema.getSQLParameter(), templateSchema.getBOMs());
			    String viewParameter = templateSchema.getViewParameter();
			    resultCount+= results.size();
				for(int i=0;i<results.size();i++){
	     			SQLTaskResult result = (SQLTaskResult)results.get(i);
	     			TestResult testResult = MakeTestResult(result,templateSchema.getRuleCh(),exeSql,tableName,templateSchema.getSQLParameter());
	     			Map map = new HashMap();
	     			map.put("bomsDesc", testResult.getBoms());
	     			map.put("rule", testResult.getRule());
	     			map.put("result", testResult.getResult());
	     			array.put(map);
	     		}
	     		*/
	     		
			}
			long end = System.currentTimeMillis();
			String endTime = df.format(new Date());
			
			try {
				obj.put("startTime",startTime);
				obj.put("endTime",endTime);
				obj.put("duration",end-start);
				obj.put("results", resultCount);
				//tongmeng 2010-12-29 add
				obj.put("CalFlag", CalFlag);
				//obj.put("result",array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		  	//	List ruleElements = getRuleElements(viewParameter);
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("alter session set nls_date_format='yyyy-mm-dd'");
			exeSql.execUpdateSQL(sqlbv1);
			
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}catch(Exception e)
		{
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}

		

		try {
			obj.put("result", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//List ruleElements = 
		if (obj!=null)
		return obj.toString();
		
		else return null;
		
	}
	private String replaceRule(String tString)
	{
		String tRes = "";
		String tStr = tString;
		tRes = tString;
		String tStr1 = "";
		while (true) {
			tStr = PubFun.getStr(tRes, 2, "?");
			if (tStr.equals("")) {
				break;
			}
			
			tStr1 = "?" + tStr + "?";
			// 替换变量
			String tValue =  this.mRuleMapCached.getMsg(tStr, this.mLanguage);
			if(!tValue.equals(""))
				
			{
				String[] tReplace = PubFun.split(tValue, ":");
				if(tReplace[0].toUpperCase().equals("BOMITEM"))
				{
					String tLink = this.mRuleMapCached.getMsg("LINK_OF", mLanguage);
					tLink = tLink.split(":")[1];
					tValue = tLink + tReplace[1];
				}
				else
				{
					tValue = tReplace[1];
				}
			}
			logger.debug("tValue:"+tValue);
			tValue = " "+tValue+" ";
			tRes = StrTool.replaceEx(tRes, tStr1,tValue);  
			//防止死循环
//			if(count++>1000){
//				logger.debug("Error SQL:"+tResSQL);
//				break;
//			}
		}
		return tRes;
	}
	
	
	class TestResult{
		String result;
		String rule ; //规则
		String boms;
		public String getRule() {
			return rule;
		}
		public void setRule(String rule) {
			this.rule = rule;
		}
		public String getBoms() {
			return boms;
		}
		public void setBoms(String boms) {
			this.boms = boms;
		}
		
		
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public TestResult(String boms, String rule,String result) {
			super();
			this.boms = boms;
			this.rule = rule;
			this.result = result;
		} //使用的具体的bom对象值
		
		
	}
	
	protected TestResult MakeTestResult(SQLTaskResult result,String ruleCh,ExeSQL   exeSql,String dtTable,String items,String tTempID,String  ruleCalCh){
		
		String ruleid = result.getRuleid();
		String calFlag = "0";
		if(ruleCalCh!=null&&!ruleCalCh.equals(""))
		{
			calFlag = "1";
		}
		TestResult testResult = null;
		if (result.getErrors().getErrorCount() > 0) {
			String bomsDesc = makeBomsDesc(result, items);
			CError error = result.getErrors().getError(0);
			String errorMsg = error.errorMessage;
			testResult = new TestResult(bomsDesc, errorMsg, "获取决策表中的数据出错！");

		} else {
			
			String bomsDesc = makeBomsDesc(result, items);
			if(dtTable!=null&&!dtTable.toUpperCase().equals("LDSYSVAR"))
			{
			
				String sql = "";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			if(!dtTable.toUpperCase().equals("RULEDATA"))
			{
				sql = "select * from " + dtTable + " where ruleid = '?ruleid?'";
				sqlbv6.put("ruleid", ruleid);
				//测试下该table是否有ruleid的列,如果没有,直接返回1
				String tCheckSQL = "";
				if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
					tCheckSQL = " select 1 from user_tab_cols where table_name = upper('"+"?dtTable?"+"') "
			                 + " and column_name = upper('ruleid') ";
				} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
					tCheckSQL = " select 1 from information_schema.COLUMNS where table_name = upper('"+"?dtTable?"+"') "
			                 + " and column_name = upper('ruleid') ";
				}
				SQLwithBindVariables sqlbvq = new SQLwithBindVariables();
				sqlbvq.sql(tCheckSQL);
				sqlbvq.put("dtTable",dtTable);
				String tCheckValue = exeSql.getOneValue(sqlbvq);
				if(tCheckValue==null||tCheckValue.equals(""))
				{
					sql = "select 1 from ldsysvar where  sysvar='onerow'" ;
				}
				
			}
			else
			{
				LRRuleDataDB tLRRuleDataDB = new LRRuleDataDB();
				tLRRuleDataDB.setId(tTempID);
				if(!tLRRuleDataDB.getInfo())
				{
					return null;
				}
				sql = "select * from " + tLRRuleDataDB.getRuleDataSQL() + " where ruleid = '?ruleid?'";
				sqlbv6.put("ruleid", ruleid);
			}
			sqlbv6.sql(sql);
			//if(tCalFlag.equals("0"))
			//{
			SSRS ssrs = exeSql.execSQL(sqlbv6);
			StringBuffer ruleBuf = new StringBuffer(256);
			if (ssrs != null) {
				// ruleBuf.append("如果");
				int colIndex = 1;
				if (ruleCh != null && ruleCh.length() > 0) {
					String ruleTemp = ruleCh;
					while (true) {

						int index = ruleTemp.indexOf("#");
						if (index >= 0 && colIndex <= ssrs.getMaxCol()) {
							ruleBuf.append(ruleTemp.substring(0, index));
							ruleBuf.append(ssrs.GetText(1, colIndex));
							ruleTemp = ruleTemp.substring(index + 1);
							colIndex++;
						} else
							break;
					}

				}

				String ruleResult = ssrs.GetText(1, colIndex - 1);
				if(calFlag.equals("1"))
				{
					ruleResult =result.getResult();
				}
				
				testResult = new TestResult(bomsDesc, ruleBuf.toString(),ruleResult);
				}
			}
			else
			{
				testResult = new TestResult(bomsDesc, ruleCh,result.getResult());
			}
			exeSql.mErrors.clearErrors();
		}

		return testResult;
		
	}
	
	protected String makeBomsDesc(SQLTaskResult result,String items){
		String[] arrItems = items.split(",");
		StringBuffer bomsBuffer = new StringBuffer(256);
		for(int i = 0;i<arrItems.length;i++){
			
				
			String bomField = arrItems[i];
			//String bomField = (String)st.nextToken();
			int index = bomField.indexOf(".");
			String bomName = bomField.substring(0,index);
			String fieldName = bomField.substring(index+1);
			Object obj = result.getMapUsedBoms().get(bomName);
			if(obj==null)
				continue;
			//if(i>0)
			//	bomsBuffer.append("   ");
			bomsBuffer.append("<div>");
			String bomsDesc = getBomItemCnDesc(bomName,fieldName);
			bomsBuffer.append(bomsDesc+"：");
			
			if(obj.getClass().isArray()){
				AbstractBOM objBom =(AbstractBOM) Array.get(obj, 0);
				bomsBuffer.append(objBom.getV(fieldName));
			}else{
				AbstractBOM objBom = (AbstractBOM)obj;
				bomsBuffer.append(objBom.getV(fieldName));
			}
			bomsBuffer.append("</div>");
		}
		return bomsBuffer.toString();
				
	}
	//获得词条的中文描述
	protected String getBomItemCnDesc(String bomName,String itemName){
		StringBuffer bomsBuf= new StringBuffer("");
		String sqlBom ="select * from lrbom where name ='?bomName?'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sqlBom);
		sqlbv8.put("bomName", bomName);
		String sqlItem= "select * from lrbomitem where valid=1 and bomname='?bomName?' and name='?itemName?'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sqlItem);
		sqlbv9.put("bomName", bomName);
		sqlbv9.put("itemName", itemName);
		LRBOMDB bomDb = new LRBOMDB();
		
		LRBOMSet bomSet = bomDb.executeQuery(sqlbv8);
		
		if(bomSet.size()>0)
		{
			LRBOMSchema bomSchema = bomSet.get(1);
			String name = this.mRuleMapCached.getMsg(bomSchema.getName(), mLanguage,bomSchema.getCNName());
			bomsBuf.append(name+" "+this.mRuleMapCached.getMsg("LINK_OF", mLanguage,"的"));
    	}
		
		LRBOMItemDB  itemDb = new LRBOMItemDB();
		LRBOMItemSet itemSet = new LRBOMItemSet();
		
		itemSet = itemDb.executeQuery(sqlbv9);
		
		
		if(itemSet.size()>0){
			LRBOMItemSchema itemSchema = itemSet.get(1);
			bomsBuf.append(this.mRuleMapCached.getMsg(itemSchema.getBOMName()+"_"+itemSchema.getName(), mLanguage,itemSchema.getCNName()));
			
		}
		return bomsBuf.toString();
	}
	
	
	protected List makeBoms(String json,String javaClass,String methodName){
	  	List list = new ArrayList();
        JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(json);
			String xml = XML.toString(jsonObj, "boms");
			// Array.
			Map mapTypes = new HashMap();
			Class clazz = Class.forName(javaClass);
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().equals(methodName)) {
					Class[] classes = method.getParameterTypes();
					for (int j = 0; j < classes.length; j++) {
						Class paramType = classes[j];
						if (paramType.isArray()) {
							logger.debug("array "
									+ paramType.getComponentType().getName());
							String className = paramType.getComponentType()
									.getName();
							String bomName = getBomName(className);
							mapTypes.put(bomName, paramType);
						} else {
							//logger.debug("class " + paramType.getName());
							String className = paramType.getName();
							String bomName = getBomName(className);
							mapTypes.put(bomName, paramType);
						}

					}
					break;
				}
			}

			Map mapBoms = new HashMap();
			
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(new StringReader(xml));
				Element elem = doc.getRootElement();
				List nodes = elem.getChildren();
				for (int i = 0; i < nodes.size(); i++) {
					
					Element node = (Element) nodes.get(i);
				//	logger.debug(node.getName());
					MakeBomByElement(node,mapTypes,mapBoms,null);
					
	
				}
				Set set = mapBoms.entrySet();
				for(Iterator iter = set.iterator();iter.hasNext();){
					Map.Entry entry = (Map.Entry)iter.next();
					Object obj = entry.getValue();
					if(obj instanceof List){
						List listObj = (List)obj;
						Object arrObj = Array.newInstance(listObj.get(0).getClass(), listObj.size());
						for(int i=0;i<listObj.size();i++){
							Array.set(arrObj, i, listObj.get(i));
						}
						list.add(arrObj);
					}else
						list.add(obj);
				}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;

	}
	
	protected void MakeBomByElement(Element node,Map mapTypes,Map mapBoms,Object fatherBom){
		Class paramType = (Class) mapTypes.get(node.getName());
		if (paramType != null) {
			Object objBom = null;
			if (paramType.isArray()) {
				//Array.newInstance(paramType., arg1);
				List objArr = (List)mapBoms.get(node.getName());
				if(objArr==null){
					objArr = new ArrayList();
					mapBoms.put(node.getName(), objArr);
				}
				try {
					 objBom = paramType.getComponentType().newInstance();
					 objArr.add(objBom);
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					 objBom = paramType.newInstance();
					 mapBoms.put(node.getName(), objBom);
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			if(objBom!=null){
				AbstractBOM bom = (AbstractBOM)objBom;
				if(fatherBom!=null ){
					//paramType.getMethod("setFat", parameterTypes)
					setFatherBom(bom.getClass(),bom,fatherBom);
				}
					
				List list = node.getChildren();
				for(int i=0;i<list.size();i++){
					Element childNode =(Element)list.get(i);
					Class paramType1 = (Class) mapTypes.get(childNode.getName());
					if(paramType1!=null)
					{
						MakeBomByElement(childNode,mapTypes,mapBoms,bom);
					}else
					{
						String nodeName = childNode.getName();
						String text     = childNode.getText();
						setBomItemValue(bom,nodeName,text);
						//bom.setV(nodeName, text);
					}
					
				}
			}
		}else{
			
		}

	}
	
	protected void setBomItemValue(Object objBom,String itemName,String value){
		if(value==null )
			return;
		String methodName = "set"+itemName;
		Method[] methods = objBom.getClass().getMethods();
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals(methodName)){
				Method method = methods[i];
				Class[] paramTypes = method.getParameterTypes();
				Class paramType = paramTypes[0];
				
				try {
					if(paramType.equals(Double.class))
					    method.invoke(objBom, new Object[]{Double.valueOf(value)});
					else if(paramType.equals(String.class))
						method.invoke(objBom, new Object[]{value});
					else if(paramType.equals(Date.class)){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date date = sdf.parse(value);
						method.invoke(objBom, new Object[]{date});
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
	}
	
	protected void setFatherBom(Class clazz,AbstractBOM bom,Object fatherBom){
		Method[] methods = clazz.getMethods();
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals("setFatherBOM")){
				Method method = methods[i];
				try {
					method.invoke(bom, new Object[]{fatherBom});
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
				break;
			}
		}
		
		
	}
	
	protected List getRuleElements(String xml){
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		List ruleElements = new ArrayList();
		try {
			doc = builder.build(new StringReader(xml));
			Element elem = doc.getRootElement();
			List nodes = elem.getChildren();
			StringBuffer valueBuf = new StringBuffer(1024);
			for (int i = 0; i < nodes.size(); i++) {
				
				Element node = (Element) nodes.get(i);
				if("Input".equals(node.getAttributeValue("id"))){
					RuleElement element = new RuleElement("text",valueBuf.toString());
					ruleElements.add(element);
					valueBuf = new StringBuffer("");
					element = new RuleElement("input",null);
					ruleElements.add(element);
				}else
					valueBuf.append(node.getAttributeValue("ChName"));
	

			}
			if(valueBuf.length()>0){
				RuleElement element = new RuleElement("text",valueBuf.toString());
				ruleElements.add(element);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruleElements;
	
	}
	

	
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		logger.debug(sdf.format(new Date()));
	/*	Class clazz = RuleUI.class;
		Method[] methods = clazz.getDeclaredMethods();
		for(int i=0;i<methods.length;i++){
			Method method = methods[i];
			if(method.getName().equals("AutoUWIndUI"))
			{
				Class[] classes = method.getParameterTypes();
				for(int j=0;j<classes.length;j++){
					Class paramType = classes[j];
					if(paramType.isArray()){
						logger.debug("array "+paramType.getComponentType().getName());
						
					}
					else
						logger.debug("class "+paramType.getName());
					
					
				}
				break;
			}
		}
		
	//	Connection conn =  DBConnPool.getConnection();
		LRBOMSet bomSet = new LRBOMSet();
		LRBOMDB bomDb = new LRBOMDB();
		String templateSql = "select * from lrbom where business='01' and valid='1' order by bomlevel";
		bomSet = bomDb.executeQuery(templateSql);
		
		for(int i=0;i<bomSet.size();i++){
			LRBOMSchema bomSchema = bomSet.get(i);
			
		}*/
	/*	IbrmsTestService service = new IbrmsTestService();
		String xml = "<Rule><ChildNode id='BOM' EnName='BOMCont' ChName='个人保单'/><ChildNode id='BOMItem' EnName='Amnt' ChName='的保额' MatchType='Number' Source=''/><ChildNode id='Operator' EnName='&gt;' ChName='大于' MatchType='Number' ResultType='Boolean' ParaType='Number' IsNextOperator='2'/><ChildNode id='Input' EnName='300000' ChName='请输入一个值'/></Rule>";
		List list = service.getRuleElements(xml);
		for(int i=0;i<list.size();i++){
			RuleElement ruleElement = (RuleElement)list.get(i);
			logger.debug(ruleElement.getValue());
		}*/
		
		
		//logger.debug(df.format(cal.getTime()));
		
	}
	
	class RuleElement{
		/*
		 * text 或者是input
		 * 如果是test，则直接从value中读取规则，否则从dt表中依次获取值来进行填充
		 * 
		 */
		String type ;
		String value;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public RuleElement(String type, String value) {
			super();
			this.type = type;
			this.value = value;
		}
		
		
	}

}
