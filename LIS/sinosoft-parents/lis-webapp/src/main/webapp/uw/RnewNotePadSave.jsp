<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：UWNotePadSave.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人 zhangrong   更新日期 2004.11.20    更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  
<%
  loggerDebug("RnewNotePadSave","\n\n---UWNotePadSave Start---");
  loggerDebug("RnewNotePadSave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("RnewNotePadSave","ContNo:" + request.getParameter("ContNo"));
  loggerDebug("RnewNotePadSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  RnewNotePadSchema tRnewNotePadSchema = new RnewNotePadSchema();
  tRnewNotePadSchema.setContNo(request.getParameter("ContNo"));
  tRnewNotePadSchema.setPrtNo(request.getParameter("PrtNo"));
  tRnewNotePadSchema.setOperatePos(request.getParameter("OperatePos"));
  tRnewNotePadSchema.setNotePadCont(request.getParameter("Content"));
  RnewNotePadSet inRnewNotePadSet = new RnewNotePadSet();
  inRnewNotePadSet.add(tRnewNotePadSchema);

  VData inVData = new VData();
  inVData.add(inRnewNotePadSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  RnewNotePadUI RnewNotePadUI1 = new RnewNotePadUI();

  if (!RnewNotePadUI1.submitData(inVData, "INSERT")) {
    VData rVData = RnewNotePadUI1.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("RnewNotePadSave",Content + "\n" + FlagStr + "\n---RnewNotePadSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.queryClick();
</script>
</html>
