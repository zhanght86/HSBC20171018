<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  
  LCContSchema tLCContSchema = new LCContSchema();
	
	String tContNo = request.getParameter("ContNo");  
	String tCvalidate = request.getParameter("Cvalidate");
	loggerDebug("UWChangeCvalidateChk","tCvalidate:"+tCvalidate);
  tLCContSchema.setContNo(tContNo);
  tLCContSchema.setCValiDate(tCvalidate);
	
	
	boolean flag = true;


	loggerDebug("UWChangeCvalidateChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
	   VData tVData = new VData();        
     tVData.add(tGlobalInput);
	   tVData.add(tLCContSchema);	 
	   String busiName="cbcheckgrpChangeCvalidateUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   //ChangeCvalidateUI tChangeCvalidateUI = new ChangeCvalidateUI();
	   
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
		 
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 修改保单生效日期失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {    
		    loggerDebug("UWChangeCvalidateChk","chenggong");                   
		    	Content = " 修改保单生效日期成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		   
		    	Content = " 修改保单生效日期失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
