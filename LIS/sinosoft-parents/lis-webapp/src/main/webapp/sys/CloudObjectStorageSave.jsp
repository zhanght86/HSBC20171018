<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%
    loggerDebug("CloudObjectStorageSave","��ʼִ��CloudObjectStorageSave�ĳ���...");
    String Content = "�Ʒ����������ñ���ʧ��";    //������
    String Flag="Fail";
    //��Ҫ�����̨��������󼯺ϣ�
    String CloudSwitchValue = request.getParameter("CosCloudSwitch");
    String CloudCode = request.getParameter("CloudObjectStorage");
    com.sinosoft.utility.TransferData transferData = new com.sinosoft.utility.TransferData();
    transferData.setNameAndValue("CosCloudSwitch", CloudSwitchValue);
    transferData.setNameAndValue("CloudObjectStorage", CloudCode);
    com.sinosoft.utility.VData vData = new com.sinosoft.utility.VData();
    vData.add(transferData);
    com.sinosoft.service.BusinessDelegate tBusinessDelegate = com.sinosoft.service.BusinessDelegate.getBusinessDelegate();
	boolean isSuccessful = tBusinessDelegate.submitData(vData, "UPDATE", "CloudObjectStorageUI");
    String result = "Succ";
    com.sinosoft.utility.VData invokeResult = tBusinessDelegate.getResult();
    if(invokeResult != null && !invokeResult.isEmpty()){
        result = invokeResult.get(0).toString();
    }
    if(result.equalsIgnoreCase("Fail") || !isSuccessful){
    	Flag = "Fail";
    	com.sinosoft.utility.CErrors cErrors = tBusinessDelegate.getCErrors();
    	if(cErrors == null)
    	    Content = "�Ʒ����������ñ���ʧ��: δ֪����";
    	else
    		Content = "�Ʒ����������ñ���ʧ��: " + cErrors.getErrContent();
    }else {
    	Flag = "Succ";
    	Content = "�Ʒ����������ñ���ɹ�.";
    }
    Content = java.net.URLEncoder.encode(Content, "UTF-8");
    
%>
<html>
    <script type="text/javascript">
        var content = decodeURIComponent("<%=Content%>");
        parent.fraInterface.afterSubmit("<%=Flag%>", content, null);
    </script>
</html>