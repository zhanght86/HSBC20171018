<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName��EdorPrintInput.jsp</p>
 * <p>Title��������ӡ</p>
 * <p>Description��������ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-09-20
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
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tOperate = request.getParameter("Operate");
			TransferData tTransferData = new TransferData();
		
			tTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			tTransferData.setNameAndValue("EdorNo",tEdorNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LPEdorPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				if("PRINT".equals(tOperate)){
					VData tReturn = new VData();
					tReturn = tBusinessDelegate.getResult();
					tFilePath1 = (String) tReturn.get(0);
					tFileName = (String) tReturn.get(1);
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
