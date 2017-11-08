<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：GEdorTypeRiskDetailSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	loggerDebug("GEdorTypeRiskDetailSubmit","=====This is GEdorTypeRiskDetailSubmit.jsp=====\n");

	//接收信息，并作校验处理
	String FlagStr  = "";
	String Content  = "";
	String transact = "";
	String Result   = "";
  String EdorType = request.getParameter("EdorType");
	GlobalInput tG  = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");  
	TransferData tTransferData = new TransferData();
	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	
	//团体项目批改信息
	tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	
	//个人主表批改信息
	LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
	tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSet.add(tLPEdorItemSchema);   
	tTransferData.setNameAndValue("ContType", "2");
	transact = request.getParameter("Transact");      // 应该加上吧？？
	GEdorRiskDetailBL tGEdorRiskDetailBL = new GEdorRiskDetailBL();
	try
	{
		// 准备传输数据 VData
		VData tVData = new VData();  
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSet);
		tVData.addElement(tLPGrpEdorItemSchema);
		tVData.addElement(tTransferData);

		//保存个人保单信息(保全)	
		tGEdorRiskDetailBL.submitData(tVData, transact);
	}
	catch(Exception ex)
	{
		Content = transact + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}			
	
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "")
	{
		CErrors tError = new CErrors(); 
		tError = tGEdorRiskDetailBL.mErrors;
		if (!tError.needDealError())
		{
		  Content = " 保存成功";
			FlagStr = "Success";
			
			if (transact.equals("QUERY||MAIN") || transact.equals("QUERY||DETAIL"))
			{
				if (tGEdorRiskDetailBL.getResult() != null && 
				 													tGEdorRiskDetailBL.getResult().size() > 0)
				{
					Result = (String)tGEdorRiskDetailBL.getResult().get(0);					
					if (Result == null || Result.equals(""))	
					{
						FlagStr = "Fail";
						Content = "提交失败!!";
					}
				}
			}
		}
		else
		{
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

