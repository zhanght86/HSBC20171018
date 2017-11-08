<%
/***************************************************************
 * <p>ProName��PreCustomerUnlockSave.jsp</p>
 * <p>Title��׼�ͻ���������</p>
 * <p>Description��׼�ͻ���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.schema.LDPreGrpSalerSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDPreGrpSalerSet"%>
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
			String tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tMainSalerCode = request.getParameter("SalerCode");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("PreCustomerNo", tPreCustomerNo);
			tTransferData.setNameAndValue("MainSalerCode", tMainSalerCode);
			
			LDPreGrpSalerSet tLDPreGrpSalerSet = new LDPreGrpSalerSet();
			String tGridNo[] = request.getParameterValues("SalerGridNo");
			if (tGridNo!=null && tGridNo.length>0) {
				
				String tSalerCode[] = request.getParameterValues("SalerGrid1");
				
				for (int i=0; i<tGridNo.length; i++) {
				
					if (tSalerCode[i]!=null && !"".equals(tSalerCode[i])) {
						
						LDPreGrpSalerSchema tLDPreGrpSalerSchema = new LDPreGrpSalerSchema();
						tLDPreGrpSalerSchema.setSalerCode(tSalerCode[i]);
						
						tLDPreGrpSalerSet.add(tLDPreGrpSalerSchema);
					}
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLDPreGrpSalerSet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "PreCustomerUnlockUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
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
