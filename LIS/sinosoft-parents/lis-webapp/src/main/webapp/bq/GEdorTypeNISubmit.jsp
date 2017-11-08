
<%
//程序名称：GEdorTypeNISubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
 String busiName="bqgrpGrpEdorNIDetailUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//后面要执行的动作：添加，修改
String FlagStr = "";
String Content = "";
  //个人批改信息
  //loggerDebug("GEdorTypeNISubmit","=====This is GEdorTypeNISubmit.jsp=====\n");

GlobalInput tGlobalInput = new GlobalInput();
tGlobalInput = (GlobalInput)session.getValue("GI");
LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

String transact = request.getParameter("fmtransact");
String EdorNo = request.getParameter("EdorNo");
String EdorAcceptNo = request.getParameter("EdorAcceptNo");
String EdorType = request.getParameter("EdorType");
String GrpContNo = request.getParameter("GrpContNo");

tLPGrpEdorItemSchema.setEdorNo(EdorNo);
tLPGrpEdorItemSchema.setEdorType(EdorType);
tLPGrpEdorItemSchema.setGrpContNo(GrpContNo);
tLPGrpEdorItemSchema.setEdorAcceptNo(EdorAcceptNo);

VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPGrpEdorItemSchema);	
  	
  	if (!tBusinessDelegate.submitData(tVData, transact,busiName))
  	{
  	  VData rVData = tBusinessDelegate.getResult();
  	  //loggerDebug("GEdorTypeNISubmit","Submit Failed! " + tBusinessDelegate.getCErrors().getErrContent());
  	  Content = transact + "失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
  	  FlagStr = "Fail";
  	}
  	else 
  	{
  	  //loggerDebug("GEdorTypeNISubmit","Submit Succed!");
  	  Content = "保存成功！";
  	  FlagStr = "Success";  	
  	}
	
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

