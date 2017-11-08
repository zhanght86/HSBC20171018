<%
/***************************************************************
 * <p>ProName：LJPremMatchPrintSave.jsp</p>
 * <p>Title: 匹配单打印</p>
 * <p>Description：匹配单打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-08-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	String tFilePath = "";
	String tFileName = "";
	String strVFFileName = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tMatchSerialNo = request.getParameter("MatchSerialNo");
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MatchSerialNo", tMatchSerialNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJPremMatchPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
				
				 tVData = tBusinessDelegate.getResult();
				 tFilePath=tVData.get(1).toString();
				long longst= Long.parseLong(tVData.get(0).toString())+1;
				 
				 
				 tFileName="000000"+longst+".pdf";
				 //strVFFileName=	tVData.get(1).toString()+tVData.get(0).toString()+".pdf";
				/*  TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");	 */
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	<%--  parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");  --%>
 	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tFilePath%>", "<%=tFileName%>"); 

<%-- parent.fraInterface.afterSubmit('<%=tFlagStr%>', '<%=tContent%>','<%=strVFFileName%>'); --%>

</script>
</html>
