<%@page import="java.net.URLEncoder"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%
    loggerDebug("CloudObjectStorageQCloudSave","��ʼִ��CloudObjectStorageQCloudSave�ĳ���...");
    
    String Content = "��Ѷ�ƶ���洢���ñ���ʧ��";    //������
    String Flag="Fail";
    
    String AppIdValue = request.getParameter("AppId");
    String SecretIdValue = request.getParameter("SecretId");
    String SecretKeyValue = request.getParameter("SecretKey");
    String BucketNameValue = request.getParameter("BucketName");
    String AccessChannelValue = request.getParameter("AccessChannel");
    com.sinosoft.utility.TransferData transferData = new com.sinosoft.utility.TransferData();
    transferData.setNameAndValue("AppId", AppIdValue);
    transferData.setNameAndValue("SecretId", SecretIdValue);
    transferData.setNameAndValue("SecretKey", SecretKeyValue);
    transferData.setNameAndValue("BucketName", BucketNameValue);
    transferData.setNameAndValue("AccessChannel", AccessChannelValue);
    com.sinosoft.utility.VData vData = new com.sinosoft.utility.VData();
    vData.add(transferData);
    com.sinosoft.service.BusinessDelegate tBusinessDelegate = com.sinosoft.service.BusinessDelegate.getBusinessDelegate();
    boolean isSuccessful = tBusinessDelegate.submitData(vData, "UPDATE", "CloudObjectStorageQCloudUI");
    String result = "Succ";
    com.sinosoft.utility.VData invokeResult = tBusinessDelegate.getResult();
    if(invokeResult != null && !invokeResult.isEmpty()){
        result = invokeResult.get(0).toString();
    }
    if(result.equalsIgnoreCase("Fail") || !isSuccessful){
    	Flag = "Fail";
    	com.sinosoft.utility.CErrors cErrors = tBusinessDelegate.getCErrors();
    	if(cErrors == null)
    	    Content = "��Ѷ�ƶ���洢���ñ���ʧ��: δ֪����";
    	else
    		Content = "��Ѷ�ƶ���洢���ñ���ʧ��: " + cErrors.getErrContent();
    }else {
    	Flag = "Succ";
    	Content = "��Ѷ�ƶ���洢���ñ���ɹ�.";
    }
    Content = URLEncoder.encode(Content, "UTF-8");
%>
<html>
    <script type="text/javascript">
        var content = decodeURIComponent("<%=Content%>");
        parent.fraInterface.afterSubmit("<%=Flag%>", content, null);
    </script>
</html>