<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLSecondUWRiskChk.jsp
//程序功能：险种核保信息----数据提交
//创建日期：2005-01-19 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人  yuejw  更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
//  String Flag = request.getParameter("flag");
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	if(tGI == null)
	{
		System.out.println("session has expired");
		return;
	}
	LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
	tLLUWMasterSchema.setCaseNo(request.getParameter("CaseNo"));
	tLLUWMasterSchema.setBatNo(request.getParameter("BatNo"));
	tLLUWMasterSchema.setPolNo(request.getParameter("PolNo"));
//	tLLUWMasterSchema.setProposalNo(request.getParameter("PolNo"));
	tLLUWMasterSchema.setPassFlag(request.getParameter("UWState")); //险种核保结论 
	tLLUWMasterSchema.setUWIdea(request.getParameter("UWIdea")); //核保意见
	try
	{
		// 准备传输数据 VData
		VData tVData = new VData();
	//	FlagStr="";
		tVData.add(tGI);
		tVData.add(tLLUWMasterSchema);	
		LLSecondUWRiskUI tLLSecondUWRiskUI = new LLSecondUWRiskUI();
		 if(!tLLSecondUWRiskUI.submitData(tVData,""))
        {           
            Content = "提交工作流失败，原因是: " + tLLSecondUWRiskUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
		else 
		{
		    Content = " 保存成功! ";
		    FlagStr = "SUCC";
		}		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Content = Content.trim()+".提示：异常终止!";
	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
