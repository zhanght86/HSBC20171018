<%
//程序名称：GrpEdorTypeNRSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Vivian
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bqgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//个人批改信息
	loggerDebug("GrpEdorTypeNRSubmit","---NR submit---");

	//后面要执行的动作：添加，修改
	String FlagStr = "";
	String Content = "";
	String[] tResult=new String[3];
	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
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

	GrpEdorNRDetailUI tGrpEdorNRDetailUI = new GrpEdorNRDetailUI();
	if (!tGrpEdorNRDetailUI.submitData(tVData, transact))
	{
		VData rVData = tGrpEdorNRDetailUI.getResult();
		loggerDebug("GrpEdorTypeNRSubmit","Submit Failed! " + tGrpEdorNRDetailUI.mErrors.getErrContent());
		Content = transact + "失败，原因是:" + tGrpEdorNRDetailUI.mErrors.getFirstError();
		FlagStr = "Fail";
	}
	else 
	{
		loggerDebug("GrpEdorTypeNRSubmit","Submit Succed!");
		Content = "保存成功";
		FlagStr = "Success";
	}
%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

