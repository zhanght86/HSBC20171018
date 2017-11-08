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
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  
<%
  loggerDebug("UWNotePadSave","\n\n---UWNotePadSave Start---");
  loggerDebug("UWNotePadSave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWNotePadSave","ContNo:" + request.getParameter("ContNo"));
  loggerDebug("UWNotePadSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LCNotePadSchema tLCNotePadSchema = new LCNotePadSchema();
  tLCNotePadSchema.setContNo(request.getParameter("ContNo"));
  tLCNotePadSchema.setPrtNo(request.getParameter("PrtNo"));
  tLCNotePadSchema.setOperatePos(request.getParameter("OperatePos"));
  tLCNotePadSchema.setNotePadCont(request.getParameter("Content"));
  LCNotePadSet inLCNotePadSet = new LCNotePadSet();
  inLCNotePadSet.add(tLCNotePadSchema);

  VData inVData = new VData();
  inVData.add(inLCNotePadSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  UWNotePadUI UWNotePadUI1 = new UWNotePadUI();

  if (!UWNotePadUI1.submitData(inVData, "INSERT")) {
    VData rVData = UWNotePadUI1.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("UWNotePadSave",Content + "\n" + FlagStr + "\n---UWNotePadSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.queryClick();
</script>
</html>
