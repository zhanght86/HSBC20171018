<%
/***************************************************************
 * <p>ProName��LCGrpPrintSave.jsp</p>
 * <p>Title�����屣����ӡ</p>
 * <p>Description�����屣����ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tFilePath = "";
	String tFileName = "";
	String strVFFileName = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tManageCom = request.getParameter("ManageCom");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tGrpPropNo = request.getParameter("GrpPropNo");
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			tTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			tTransferData.setNameAndValue("ManageCom",tManageCom);
					
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGrpPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				 tVData = tBusinessDelegate.getResult();
				 tFilePath=tVData.get(1).toString();
				//long longst= Long.parseLong(tVData.get(0).toString())+1;
				 
				 
				 tFileName=tVData.get(0).toString();
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">
	<%-- parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>"); --%>
 parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tFilePath%>", "<%=tFileName%>");
</script>
</html>
