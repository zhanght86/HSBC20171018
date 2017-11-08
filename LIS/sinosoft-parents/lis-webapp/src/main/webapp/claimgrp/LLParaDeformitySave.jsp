<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLParaDeformitySave.jsp
//程序功能：伤残等级参数表信息保存
//创建日期：2005-6-24 13:38
//创建人  ：赵雁
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
   
    if(tG == null) 
    {
        loggerDebug("LLParaDeformitySave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLParaDeformitySave","没有获取操作符!!!");
        return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData();    
    
    LLParaDeformitySchema tLLParaDeformitySchema = new LLParaDeformitySchema();        
    
    
    //理赔权限信息
    if (tOperate.equals("Deformity||INSERT")||tOperate.equals("Deformity||DELETE")||tOperate.equals("Deformity||UPDATE"))
    {
				//准备后台数据
	    	tLLParaDeformitySchema.setDefoType(request.getParameter("DefoType"));
	    	tLLParaDeformitySchema.setDefoGrade(request.getParameter("DefoGrade"));
	    	tLLParaDeformitySchema.setDefoGradeName(request.getParameter("DefoGradeName"));
	    	tLLParaDeformitySchema.setDefoCode(request.getParameter("DefoCode"));
	    	tLLParaDeformitySchema.setDefoName(request.getParameter("DefoName"));
	    	tLLParaDeformitySchema.setDefoRate(request.getParameter("DefoRate"));
    }
    //loggerDebug("LLParaDeformitySave","LLParaDeformitySchemaSave.jsp测试--"+tOperate);
  
    
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLParaDeformitySchema );
				
        LLParaDeformityUI tLLParaDeformityUI = new LLParaDeformityUI();
       if (tLLParaDeformityUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "伤残等级参数信息保存失败，原因是: " + tLLParaDeformityUI.mErrors.getError(0).errorMessage;
          FlagStr = "FAIL";
         
        } else {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }
    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterDeformitySubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
