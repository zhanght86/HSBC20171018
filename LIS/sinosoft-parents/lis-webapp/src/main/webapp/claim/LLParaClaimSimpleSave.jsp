<%
//程序名称：LLParaClaimSimpleSave.jsp
//程序功能：医院信息维护
//创建日期：2005-9-19
//创建人  ：wuhao
//更新记录：  更新人 wuhao    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>


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
        loggerDebug("LLParaClaimSimpleSave","登录信息没有获取!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLParaClaimSimpleSave","没有获取操作符!!!");
        return;   
    }
    
    //准备数据容器信息
    TransferData tTransferData = new TransferData();    
    
    LLParaClaimSimpleSchema tLLParaClaimSimpleSchema = new LLParaClaimSimpleSchema();    
    //简单案例标准信息
    if (tOperate.equals("Simple||INSERT")||tOperate.equals("Simple||DELETE")||tOperate.equals("Simple||UPDATE"))
    {
				//准备后台数据
	    	tLLParaClaimSimpleSchema.setComCode(request.getParameter("ComCode"));
	    	tLLParaClaimSimpleSchema.setComCodeName(request.getParameter("ComCodeName"));
	    	tLLParaClaimSimpleSchema.setUpComCode(request.getParameter("UpComCode"));
	    	tLLParaClaimSimpleSchema.setBaseMin(request.getParameter("BaseMin"));
	    	tLLParaClaimSimpleSchema.setBaseMax(request.getParameter("BaseMax"));
	    	tLLParaClaimSimpleSchema.setStartDate(request.getParameter("StartDate"));
	    	tLLParaClaimSimpleSchema.setEndDate(request.getParameter("EndDate"));
    }
    
    try
    {    
        // 准备传输数据 VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLParaClaimSimpleSchema );
				
        LLParaClaimSimpleUI tLLParaClaimSimpleUI = new LLParaClaimSimpleUI();
       if (tLLParaClaimSimpleUI.submitData(tVData,tOperate) == false)
        {
          Content = Content + "案例标准保存失败，原因是: " + tLLParaClaimSimpleUI.mErrors.getError(0).errorMessage;
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
    
//    loggerDebug("LLParaClaimSimpleSave","LLParaClaimSimpleSave测试--"+FlagStr);
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterSimpleSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
