<%
/***************************************************************
 * <p>ProName：QueryDataExport.jsp</p>
 * <p>Title：查询大数据导出</p>
 * <p>Description：查询大数据导出，主要针对把大数据导出在一个sheet中的情况</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-03
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
 	
	String tPath = "";
 	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String[] tSheetName = (String[])request.getParameterValues("SheetName");
			String[] tSheetTitle = (String[])request.getParameterValues("SheetTitle");
			String[] tSheetSql = (String[])request.getParameterValues("SheetSql");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SheetName", tSheetName);
			tTransferData.setNameAndValue("SheetTitle", tSheetTitle);
			tTransferData.setNameAndValue("SheetSql", tSheetSql);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, "", "QueryLargeDataExportUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				tContent = "数据导出成功！";
				tFlagStr = "Succ";
				VData tResult = tBusinessDelegate.getResult();
				tPath = (String)tResult.get(0);
			}
		} catch(Exception ex) {
			tContent = tFlagStr + "处理异常，请联系系统运维人员！";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	
	var tFlagStr = "<%=tFlagStr%>";
	alert("<%=tContent%>");
	
	if (tFlagStr=="Succ") {
		var tPath = "<%=tPath%>";
		var tFileName = tPath.substring(tPath.lastIndexOf("/")+1);
		window.open("./download.jsp?FilePath="+ tPath +"&FileName="+ tFileName);
	}
</script>
</html>
