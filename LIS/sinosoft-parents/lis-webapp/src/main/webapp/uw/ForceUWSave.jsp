<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupPolInput.jsp
//程序功能：
//创建日期：2002-08-15 11:48:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//输出参数
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	//输入参数
	LCContSchema tLCContSchema   = new LCContSchema();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  loggerDebug("ForceUWSave","ContNo=="+request.getParameter("ContNo"));
  loggerDebug("ForceUWSave","ForceUWOpt=="+request.getParameter("ForceUWOpt"));
  loggerDebug("ForceUWSave","ForceUWOptRemark=="+request.getParameter("ForceUWRemark"));


  tLCContSchema.setContNo(request.getParameter("ContNo"));
  tLCContSchema.setForceUWFlag(request.getParameter("ForceUWOpt"));
  if(request.getParameter("ForceUWOpt").equals("1")){
    tLCContSchema.setForceUWReason(request.getParameter("ForceUWRemark"));
  }
  else{
    tLCContSchema.setForceUWReason("");
  }

	VData tVData = new VData();
  tVData.add(tLCContSchema);
  tVData.add(tG);
  String busiName1="tbForceUWUI";
  BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
  //ForceUWUI tForceUWUI = new ForceUWUI();

        
  if(!tBusinessDelegate1.submitData(tVData,"UPDATE",busiName1)){
		Content = " 保存失败，原因是: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " 保存成功! ";
		FlagStr = "Succ";
	}
  loggerDebug("ForceUWSave","Content:"+Content);	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
    
