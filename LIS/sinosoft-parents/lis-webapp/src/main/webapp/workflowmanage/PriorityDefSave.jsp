<%@page import="com.sinosoft.lis.schema.LWPrioritysqlSchema"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%><!--用户校验类-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.db.LWPriorityDB" %>
<%@page import="com.sinosoft.lis.db.LWPrioritysqlDB" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.LWMissionDB" %>
<%@page import="com.sinosoft.lis.schema.LWPrioritySchema"%>
<%@page import="com.sinosoft.lis.vdb.LWMissionDBSet" %>
<%@page import="com.sinosoft.lis.vschema.LWMissionSet"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LWPrioritysqlSchema"%>
<%@page import="java.net.URLDecoder"%>
<%
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
  
    //修改
    if ("UPDATE||Condation".equalsIgnoreCase(OperFlag))
    {   String ProcessID = request.getParameter("ProcessID");
        String ActivityID = request.getParameter("ActivityID");
        String PriorityID = request.getParameter("PriorityID");
        String Sqls1 = request.getParameter("Sqls");
        String version = request.getParameter("Version");
        String Sqls = URLDecoder.decode(Sqls1, "UTF-8");
        LWPrioritysqlSchema tLWPrioritysqlSchema = new LWPrioritysqlSchema();
        tLWPrioritysqlSchema.setProcessID(ProcessID);
        tLWPrioritysqlSchema.setactivityID(ActivityID);
        tLWPrioritysqlSchema.setPrioritySQL(Sqls);
        tLWPrioritysqlSchema.setPriorityID(PriorityID);
        tLWPrioritysqlSchema.setversion(version);
        LWPrioritysqlDB tLWPrioritysqlDB = tLWPrioritysqlSchema.getDB();
        boolean temp = tLWPrioritysqlDB.update();
        String FlagStr="";      //操作结果
        String Content ="";    //控制台信息
  	   	if(temp==true){	
  	  	Content = " 修改成功! ";
    	FlagStr = "Succ"; 	   	
  	   	}else
  	     {
  	   	Content = " 修改失败! ";
  	   	FlagStr = "Fail";
      }
  	   response.getWriter().println(Content);
    }
    if ("UPDATE||PRIORITY".equalsIgnoreCase(OperFlag))
    {   
    	
    	String Colorid = request.getParameter("colorid");
        String Range = request.getParameter("range");
        String PriorityID = request.getParameter("priorityid");
        String priorityname1 = request.getParameter("priorityname");
		String priorityname = URLDecoder.decode(priorityname1, "UTF-8");
        LWPrioritySchema tLWPrioritySchema = new LWPrioritySchema();
        tLWPrioritySchema.setPriorityid(PriorityID);
        tLWPrioritySchema.setPriorityName(priorityname);
        tLWPrioritySchema.setRange(Range);
        tLWPrioritySchema.setColorid(Colorid);
        LWPriorityDB tLWPriorityDB = tLWPrioritySchema.getDB();
        boolean temp = tLWPriorityDB.update();
        String FlagStr="";      //操作结果
        String Content ="";    //控制台信息
  	   	if(temp==true){	
  	  	Content = " 更新成功! ";
    	FlagStr = "Succ"; 	   	
  	   	}else
  	     {
  	   	Content = " 更新失败! ";
  	   	FlagStr = "Fail";
      }
  	   response.getWriter().println(Content);
    }
    //添加
    if ("INSERT||Condation".equalsIgnoreCase(OperFlag))
    {
        String ProcessID = request.getParameter("ProcessID");
        String ActivityID = request.getParameter("ActivityID");
        String PriorityID = request.getParameter("PriorityID");
        String Sqls1 = request.getParameter("Sqls");
        String Sqls = URLDecoder.decode(Sqls1, "UTF-8");
        String version = request.getParameter("Version");
        LWPrioritysqlSchema tLWPrioritysqlSchema = new LWPrioritysqlSchema();
        tLWPrioritysqlSchema.setProcessID(ProcessID);
        tLWPrioritysqlSchema.setactivityID(ActivityID);
        tLWPrioritysqlSchema.setPriorityID(PriorityID);
        tLWPrioritysqlSchema.setPrioritySQL(Sqls);
        tLWPrioritysqlSchema.setversion(version);
        LWPrioritysqlDB tLWPrioritysqlDB = tLWPrioritysqlSchema.getDB();
        boolean temp = tLWPrioritysqlDB.insert();
        String FlagStr="";      //操作结果
        String Content ="";    //控制台信息
  	   	if(temp==true){	
  	  	Content = " 添加成功! ";
    	FlagStr = "Succ"; 	   	
  	   	}else
  	     {
  	   	Content = " 添加失败! ";
  	   	FlagStr = "Fail";
      }
  	   response.getWriter().println(Content);
    }
    //删除
    if ("DELETE||Condation".equalsIgnoreCase(OperFlag))
    {   

        String ProcessID = request.getParameter("ProcessID");
        String ActivityID = request.getParameter("ActivityID");
        String PriorityID = request.getParameter("PriorityID");
        String Sqls = request.getParameter("Sqls");
        String version = request.getParameter("Version");
        LWPrioritysqlSchema tLWPrioritysqlSchema = new LWPrioritysqlSchema();
        tLWPrioritysqlSchema.setProcessID(ProcessID);
        tLWPrioritysqlSchema.setactivityID(ActivityID);
        tLWPrioritysqlSchema.setPrioritySQL(Sqls);
        tLWPrioritysqlSchema.setversion(version);
        LWPrioritysqlDB tLWPrioritysqlDB = tLWPrioritysqlSchema.getDB();
        boolean temp = tLWPrioritysqlDB.delete();
        String FlagStr="";      //操作结果
        String Content ="";    //控制台信息
  	   	if(temp==true){	
  	  	Content = " 删除成功! ";
    	FlagStr = "Succ"; 	   	
  	   	}else
  	     {
  	   	Content = " 删除失败! ";
  	   	FlagStr = "Fail";
      }
  	   response.getWriter().println(Content);
    }
%>
