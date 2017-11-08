<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%
//程序名称：PEdorTypeICSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Date"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.*" %> 

<%
	//接收信息，并作校验处理。
	//输入参数
	//个人批改信息

	LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//	EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	CErrors tError = null;
	//后面要执行的动作：添加，修改

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result = "";
	GlobalInput tG = new GlobalInput();
	try {
		transact = request.getParameter("fmtransact");

		tG = (GlobalInput) session.getValue("GI");

		//个人保单批改信息
		tLPEdorItemSchema.setEdorAcceptNo(request
		.getParameter("EdorAcceptNo"));
		tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
		tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
		tLPEdorItemSchema.setPolNo("000000");
		//存放此保单在做CM项目时的批单号
		tLPEdorItemSchema.setStandbyFlag1(request.getParameter("CMEdorNo"));
	  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("CustomerNo"));

				String tLastName = request.getParameter("LastNameBak");
				String tFirstName =  request.getParameter("FirstNameBak");
				
		tLPInsuredSchema.setEdorType(request
				.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request
				.getParameter("CustomerNo"));
		tLPInsuredSchema.setContNo(request
				.getParameter("ContNo"));
		tLPInsuredSchema
				.setName(tLastName+tFirstName);
		tLPInsuredSchema.setSex(request.getParameter("SexBak"));
		tLPInsuredSchema.setBirthday(request
				.getParameter("BirthdayBak"));
		tLPInsuredSchema.setIDType(request
				.getParameter("IDTypeBak"));
		tLPInsuredSchema
				.setIDNo(request.getParameter("IDNoBak"));
	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	try {
		// 准备传输数据 VData

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPInsuredSchema);
//		if (!tEdorDetailUI.submitData(tVData, transact)) {
		if (!tBusinessDelegate.submitData(tVData, transact ,busiName)) {
//			tError = tEdorDetailUI.mErrors;
			tError = tBusinessDelegate.getCErrors(); 
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		} else {

			Content = " 保存成功！";
			FlagStr = "Success";

		}

	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	//添加各种预处理
%>

<html>
    <script language="JavaScript">
      parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

    </script>
</html>
