<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    //添加页面控件的初始化。
    String AppIdValue = "";
    String SecretIdValue = "";
    String SecretKeyValue = "";
    String BucketNameValue = "";
    String AccessChannelValue = null;
    String AccessChannelNameValue = "";
    try{
        com.sinosoft.utility.VData invokeResult = null;
        java.util.Map<?, ?> qCloudVariables = null;
        com.sinosoft.service.BusinessDelegate tBusinessDelegate = com.sinosoft.service.BusinessDelegate.getBusinessDelegate();
            if(tBusinessDelegate.submitData(null, "SELECT", "CloudObjectStorageQCloudUI")){
                Object tmp = null;
                invokeResult = tBusinessDelegate.getResult();
                if(invokeResult != null && !invokeResult.isEmpty())
                    tmp = invokeResult.get(0);
                if(tmp != null && (tmp instanceof java.util.Map)){
                	qCloudVariables = (java.util.Map<?, ?>)tmp;
                }
            }
        if(qCloudVariables != null){
            Object tmpAppId = qCloudVariables.get("AppId");
            Object tmpSecretId = qCloudVariables.get("SecretId");
            Object tmpSecretKey = qCloudVariables.get("SecretKey");
            Object tmpBucketName = qCloudVariables.get("BucketName");
            Object tmpAccessChannel = qCloudVariables.get("AccessChannel");
            if(tmpAppId != null)
            	AppIdValue = tmpAppId.toString();
            if(tmpSecretId != null)
            	SecretIdValue = tmpSecretId.toString();
            if(tmpSecretKey != null)
            	SecretKeyValue = tmpSecretKey.toString();
            if(tmpBucketName != null)
            	BucketNameValue = tmpBucketName.toString();
            if(tmpAccessChannel != null)
            	AccessChannelValue = tmpAccessChannel.toString();
        }
    }catch(Exception e){
        
    }
    try{
   	 String sqlString = "";
		if(AccessChannelValue != null)
		    sqlString = "SELECT CODENAME FROM LDCODE WHERE CODETYPE='qcloudaccesschannel' AND CODE=\'" + AccessChannelValue + "\'";
		else
			sqlString = "SELECT CODENAME FROM LDCODE WHERE CODETYPE='qcloudaccesschannel' AND CODE IS NULL";
		try{
			com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
			AccessChannelNameValue = esql.getOneValue(sqlString);
		}catch(Exception e){
			
		}
    }catch(Exception e){}
%>
<script type="text/javascript">
    var initQCloudForm = function(){
        var qAppId = "<%=AppIdValue%>";
        var qSecretId = "<%=SecretIdValue%>";
        var qSecretKey = "<%=SecretKeyValue%>";
        var qBucketName = "<%=BucketNameValue%>";
        var qAccessChannel = "<%=AccessChannelValue%>";
        var qAccessChannelName = "<%=AccessChannelNameValue%>";
        var qCloudAppIdInputElement = document.getElementById("QCloudAppIdInput");
        var qCloudSecretIdInputElement = document.getElementById("QCloudSecretIdInput");
        var qCloudSecretKeyInputElement = document.getElementById("QCloudSecretKeyInput");
        var qCloudBucketNameInputElement = document.getElementById("QCloudBucketNameInput");
        var qCloudAccessChannelInputElement = document.getElementById("QCloudAccessChannelInput");
        var qCloudAccessChannelNameInputElement = document.getElementById("QCloudAccessChannelNameInput");
        if(qCloudAppIdInputElement) {
        	qCloudAppIdInputElement.value = qAppId;
        }
        if(qCloudSecretIdInputElement) {
        	qCloudSecretIdInputElement.value = qSecretId;
        }
        if(qCloudSecretKeyInputElement) {
        	qCloudSecretKeyInputElement.value = qSecretKey;
        }
        if(qCloudBucketNameInputElement) {
        	qCloudBucketNameInputElement.value = qBucketName;
        }
        if(qCloudAccessChannelInputElement) {
        	qCloudAccessChannelInputElement.value = qAccessChannel;
        }
        if(qCloudAccessChannelNameInputElement) {
        	qCloudAccessChannelNameInputElement.value = qAccessChannelName;
        }
    }
</script>
<script type="text/javascript">
    if(window.regCosInitFormFunction){
        window.regCosInitFormFunction(initQCloudForm);
    }
</script>