<%
//程序名称：PEdorTypeLRSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容

//更新记录：  更新人 梁聪    更新日期 2006-4-14     更新原因/内容 团体保全复用
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%

CErrors tError = null;
String tRela  = "";
String FlagStr = "";
String Content = "";
String transact = "";

GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");

transact = request.getParameter("fmtransact");
String edorNo = request.getParameter("EdorNo");
String edorType = request.getParameter("EdorType");
String contNo = request.getParameter("ContNo");

String edoracceptNo = request.getParameter("EdorAcceptNo");



// 准备传输数据 VData
LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
tLPEdorItemSchema.setEdorNo(edorNo);
tLPEdorItemSchema.setContNo(contNo);
tLPEdorItemSchema.setPolNo("000000");
tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
tLPEdorItemSchema.setEdorAcceptNo(edoracceptNo);
tLPEdorItemSchema.setEdorType(edorType);
tLPEdorItemSchema.setStandbyFlag1(request.getParameter("RemindMode")); //变更后的交费提醒方式;


VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPEdorItemSchema);


//    EdorDetailUI tEdorDetailUI = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
    System.out.println("--==  PEdorTypeLRSubmit  ==--");
//    if (!tEdorDetailUI.submitData(tVData, transact))
    if (!tBusinessDelegate.submitData(tVData, transact ,busiName))
    {
//        VData rVData = tEdorDetailUI.getResult();
        VData rVData = tBusinessDelegate.getResult();
//        Content = "保存失败，原因是:" + tEdorDetailUI.mErrors.getFirstError();
        Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
        FlagStr = "Fail";
    }
    else
    {
        Content = "保存成功";
        FlagStr = "Success";
    }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

