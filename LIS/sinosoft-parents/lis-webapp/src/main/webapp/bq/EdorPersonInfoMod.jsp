
<%@page import="com.sinosoft.service.BusinessDelegate"%><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//EdorApproveSave.jsp
	//程序功能：保全审批
	//创建日期：2005-05-08 15:59:52
	//创建人  ：sinosoft
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
<%
	//输出参数
	String FlagStr = "";
	String Content = "";


		String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
		String tPhone = request.getParameter("Mobile_Mod");
		String tPostalAddress = request.getParameter("PostalAddress_Mod");
		String tZipCode = request.getParameter("ZipCode_Mod");
		if(tPhone == null){
			tPhone = "";
		}
		if(tPostalAddress == null){
			tPostalAddress = "";
		}
		if(tZipCode == null){
			tZipCode = "";
		}
		String tSQL = "Update LPEdorApp set Phone = '"+tPhone+"',PostalAddress = '"+tPostalAddress+"',ZipCode = '"+tZipCode+"' where EdorAcceptNo = '"+tEdorAcceptNo+"'";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		TransferData tTransferData=new TransferData();
		VData tVData = new VData();
		tTransferData.setNameAndValue("SQL", tSQL);
		tVData.add(tTransferData);
		String tRet = "";
   		if(tBusinessDelegate.submitData(tVData, "execUpdateSQL", "ExeSQLUI")){
   			tRet = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
   		}
	

	if (tRet != null && tRet.equals("true")) {

		Content = "修改成功!";
		FlagStr = "Succ";
	} else {
		Content = "修改失败!";
		FlagStr = "Fail";
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
