<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CutBonusChk.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
  
<!--用户校验类-->
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  
  String busiName="cbcheckgrpBonusGrpPolParmSaveUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String strFlag = "Fail";
  String strContent = "";
  
try
{
  GlobalInput tG = (GlobalInput)session.getValue("GI");
	if(tG == null) 
	{
		out.println("session has expired");
		return;
	}
	
	String tFiscalYear = request.getParameter("FiscalYear");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tBDate = request.getParameter("BDate");
	String tEDate = request.getParameter("EDate");
	String tActuRate = request.getParameter("ActuRate");
	String tEnsuRateDefault = request.getParameter("EnsuRateDefault");
	String tAssignRate = request.getParameter("AssignRate");
	String tRiskCode = request.getParameter("RiskCode");
	
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("FiscalYear",tFiscalYear);
	tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
	tTransferData.setNameAndValue("RiskCode",tRiskCode);
	tTransferData.setNameAndValue("BDate",tBDate);
	tTransferData.setNameAndValue("EDate",tEDate);
	tTransferData.setNameAndValue("ActuRate",tActuRate);
	tTransferData.setNameAndValue("EnsuRateDefault",tEnsuRateDefault);
	tTransferData.setNameAndValue("AssignRate",tAssignRate);
	
	String action = request.getParameter("fmtransact");

	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( tG );
	tVData.add( tTransferData );

	// 数据传输
	
	if(tBusinessDelegate.submitData(tVData,action,busiName))
	{
  	strFlag = "Succ";
  	strContent = "红利参数设置成功! ";
	}
	else
		strFlag = "Fail";

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (strFlag == "Fail")
	{
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
		    	strContent = "红利参数设置成功! ";
		    	strFlag = "Succ";
	    }
	    else                                                                           
	    {
  			if (tError.getErrorCount() > 0)
  			{	
	          strContent = "红利参数设置异常 ： " + tError.getError(0).errorMessage.trim() ;
						loggerDebug("GrpCalBonusPrePareChk","Error: " + strContent);
				}
	    	strFlag = "Fail";
	    }
	}
}
catch(Exception e)
{
	e.printStackTrace();
	strContent = strContent.trim() +" 异常退出 : " + e.toString();
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=strFlag%>","<%=strContent%>");
</script>
</html>
