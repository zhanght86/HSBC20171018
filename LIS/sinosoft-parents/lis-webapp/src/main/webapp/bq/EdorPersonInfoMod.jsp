
<%@page import="com.sinosoft.service.BusinessDelegate"%><%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//EdorApproveSave.jsp
	//�����ܣ���ȫ����
	//�������ڣ�2005-05-08 15:59:52
	//������  ��sinosoft
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
<%
	//�������
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

		Content = "�޸ĳɹ�!";
		FlagStr = "Succ";
	} else {
		Content = "�޸�ʧ��!";
		FlagStr = "Fail";
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
