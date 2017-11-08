<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：DirectDelRiskSave.jsp
//程序功能：直销险种删除提交信息界面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容   
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

	TransferData tTransferData = new TransferData(); 
	//ContInsuredUI tContInsuredUI   = new ContInsuredUI();
	String busiName="tbContInsuredUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//输出参数
	String FlagStr = "";
	String Content = "";
	
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");   
	loggerDebug("DirectDelRiskSave","tGI"+tGI);
	if(tGI==null)
	{
		loggerDebug("DirectDelRiskSave","页面失效,请重新登陆");   
		FlagStr = "Fail";        
		Content = "页面失效,请重新登陆";  
	}
	else  
	{
		CErrors tError = null;
		String tBmCert = "";
		String fmAction=request.getParameter("fmAction");
	
		tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); 
		tTransferData.setNameAndValue("PolNo",request.getParameter("polno"));             
		try
		{
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tGI);
			loggerDebug("DirectDelRiskSave","提交动作==="+fmAction);  
			loggerDebug("DirectDelRiskSave","InsuredNo=="+request.getParameter("InsuredNo"));
			loggerDebug("DirectDelRiskSave","polno=="+request.getParameter("polno")); 
			if ( tBusinessDelegate.submitData(tVData,fmAction,busiName))
			{
				Content ="险种删除成功！";
				FlagStr = "Succ";
			}
		}
		catch(Exception ex)
		{
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr=="")
		{
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError())
			{                          
			Content ="险种删除成功！";
			FlagStr = "Succ";
			}
			else                                                                           
			{
			Content = "删除失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
			}
		}
		loggerDebug("DirectDelRiskSave","FlagStr:"+FlagStr+"Content:"+Content);
	
	} 
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>

