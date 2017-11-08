<%
/***************************************************************
 * <p>ProName��QueryDataExport.jsp</p>
 * <p>Title����ѯ���ݵ���</p>
 * <p>Description����ѯ���ݵ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�

 * @version  : 8.0
 * @date     : 2012-01-01
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
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			//String tTitle = StrTool.unicodeToUTF8(request.getParameter("Title"));
			//String tQuerySQL = StrTool.unicodeToUTF8(request.getParameter("QuerySQL"));
			
			String tTitle = StrTool.unicodeToGBK(request.getParameter("Title"));
			String tQuerySQL = StrTool.unicodeToGBK(request.getParameter("QuerySQL"));
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Title", tTitle);
			tTransferData.setNameAndValue("QuerySQL", tQuerySQL);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, "", "QueryDataExportUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				tContent = "���ݵ����ɹ���";
				tFlagStr = "Succ";
				VData tResult = tBusinessDelegate.getResult();
				tPath = (String)tResult.get(0);
			}
		} catch(Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
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
