<%
/***************************************************************
 * <p>ProName��LLClaimCalPortalSave.jsp</p>
 * <p>Title��ƥ������</p>
 * <p>Description��ƥ������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("GrpRgtNo", request.getParameter("GrpRgtNo"));
			tTransferData.setNameAndValue("RgtNo", request.getParameter("RegisterNo"));
			tTransferData.setNameAndValue("PayType", request.getParameter("PayType"));
			tTransferData.setNameAndValue("PublicFlag", request.getParameter("PublicFlag"));
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCalPortalUI")) {
				tContent = tBusinessDelegate.getCErrors().getLastError();
				tFlagStr = "Fail";						
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				if ("G".equals(tOperate)) {
					
					System.out.println(tBusinessDelegate.getCErrors().getErrorCount());
					TransferData sTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
					if (sTransferData!=null) {
						
						CErrors mErrors = (CErrors)sTransferData.getValueByName("Error");
						if (mErrors!=null && mErrors.getErrorCount()>=1) {
							
							tContent = "����ɹ���"+"�����ڴ�����Ϣ��"+"<br>";
							for (int i=1;i<=mErrors.getErrorCount();i++) {
								
								String tErrorMessage = mErrors.getError(i-1).errorMessage;
								tContent=tContent+tErrorMessage+"<br>";
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
