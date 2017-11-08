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
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.LWConditionSchema" %>
<%@page import="com.sinosoft.lis.vschema.LWConditionSet" %>
<%@page import="java.sql.*" %>
<%@page import="java.net.URLDecoder"%>

<%
    TransferData tTransferData = new TransferData();
    GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    String operator = tG.Operator;
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
    if ("INSERT||Condition".equalsIgnoreCase(OperFlag) || "MODIFY||Condition".equalsIgnoreCase(OperFlag) || "DELETE||Condition".equalsIgnoreCase(OperFlag))
    {

    String ProcessId = request.getParameter("ProcessId");
    String Version = request.getParameter("Version");
    String tStartActivityID = request.getParameter("StartActivityID");
    String tEndActivityID = request.getParameter("EndActivityID");
    String tTransitionCondT = request.getParameter("TransitionCondT");//转移条件类型
    String tTransitionCond1 = request.getParameter("TransitionCond");//转移条件
    String tTransitionCondDesc = request.getParameter("CondDesc");//转移条件
    String tTransitionCond = URLDecoder.decode(tTransitionCond1, "UTF-8");
    String transitionid = PubFun1.CreateMaxNo("TRANSITIONID", 10);
    //增加busytype
    String BusiType= request.getParameter("BusiType");
    
    LWConditionSchema tLWConditionSchema = new LWConditionSchema();
    tLWConditionSchema.setBusiType(BusiType);
    tLWConditionSchema.setProcessID(ProcessId);
    tLWConditionSchema.setVersion(Version);
    tLWConditionSchema.setTransitionStart(tStartActivityID);
	tLWConditionSchema.setTransitionEnd(tEndActivityID);
	tLWConditionSchema.setTransitionCond(tTransitionCond);
	tLWConditionSchema.setTransitionCondT(tTransitionCondT);
	tLWConditionSchema.setCondDesc(tTransitionCondDesc);
	
	LWConditionSet tLWConditionSet = new LWConditionSet();
	tLWConditionSet.add(tLWConditionSchema);
	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(tLWConditionSet);
	 String busiName="LWConditionUI";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
     String FlagStr="";      //操作结果
     String Content ="";    //控制台信息
	 if (!tBusinessDelegate.submitData(tVData, "save",busiName))
   {  //操作失败时
    		Content = "设置失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
     		FlagStr = "Fail";

   }
	 else
	 {
		 	 Content = " 设置成功! ";
	     FlagStr = "Succ"; 	   	
	 }
	
	 response.getWriter().println(Content);
    /*
    LWProcessTransSchema tLWProcessTransSchema = new LWProcessTransSchema();
//  查询工作流过程实例表
            LWProcessTransDB tempLWProcessTransDB = new LWProcessTransDB();
            ExeSQL tExeSQL = new ExeSQL();
			String Pid = tExeSQL.getOneValue( "SELECT transitionid FROM LWProcessTrans WHERE  ProcessId = '"+ProcessId+"' and TransitionStart = '"+tStartActivityID+"' and TransitionEnd = '"+tEndActivityID+"'");           
            //String Pid = tempLWProcessTransDB.getID(ProcessId,tStartActivityID,tEndActivityID);
            boolean res = true; 
			if(Pid==null||Pid.equals("")){
				tLWProcessTransSchema.setTransitionID(transitionid);
				tLWProcessTransSchema.setProcessID(ProcessId);
				tLWProcessTransSchema.setVersion(Version);
				tLWProcessTransSchema.setTransitionStart(tStartActivityID);
				tLWProcessTransSchema.setTransitionEnd(tEndActivityID);
				tLWProcessTransSchema.setTransitionCond(tTransitionCond);
				tLWProcessTransSchema.setTransitionCondT(tTransitionCondT);
				tLWProcessTransSchema.setTransitionModel("");//转移时方式
				tLWProcessTransSchema.setStartType("2");
			    LWProcessTransDB tLWProcessTransDB = tLWProcessTransSchema.getDB();
				res = tLWProcessTransDB.insert();
			}else{
				tLWProcessTransSchema.setTransitionID(Pid);
				tLWProcessTransSchema.setProcessID(ProcessId);
				tLWProcessTransSchema.setVersion(Version);
				tLWProcessTransSchema.setTransitionStart(tStartActivityID);
				tLWProcessTransSchema.setTransitionEnd(tEndActivityID);
				tLWProcessTransSchema.setTransitionCond(tTransitionCond);
				tLWProcessTransSchema.setTransitionCondT(tTransitionCondT);
				tLWProcessTransSchema.setTransitionModel("");//转移时方式
				tLWProcessTransSchema.setStartType("2");
			    LWProcessTransDB tLWProcessTransDB = tLWProcessTransSchema.getDB();
				 res = tLWProcessTransDB.update();
			}
			  String FlagStr="";      //操作结果
		        String Content ="";    //控制台信息
		  	   	if(res==true){	
		  	  	Content = " 设置成功! ";
		    	FlagStr = "Succ"; 	   	
		  	   	}else
		  	     {
		  	   	Content = " 设置失败! ";
		  	   	FlagStr = "Fail";
		      }
		  	   response.getWriter().println(Content);
		  	   
		  	   */
		    }
   
    if ("CREATE||Condition".equalsIgnoreCase(OperFlag))
    {         
    	 String mCalCodeType = request.getParameter("mCalCodeType");
    	 String calcode = PubFun1.CreateRuleCalCode("WF",mCalCodeType);
		 response.getWriter().println(calcode);
		    }
		
%>
<html>
<script language="javascript">
  
</script>
</html>
