<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
     //添加页面控件的初始化。
     String CloudSwitchValue = "Disabled";
     String CloudCode = "QCd";
     String CloudName = "";
     try{
         com.sinosoft.utility.VData invokeResult = null;
         com.sinosoft.lis.schema.LDSysVarSchema ldSysVarSchema = null;
         com.sinosoft.service.BusinessDelegate tBusinessDelegate = com.sinosoft.service.BusinessDelegate.getBusinessDelegate();
             if(tBusinessDelegate.submitData(null, "SELECT", "CloudObjectStorageUI")){
                 Object tmp = null;
                 invokeResult = tBusinessDelegate.getResult();
                 if(invokeResult != null && !invokeResult.isEmpty())
                     tmp = invokeResult.get(0);
                 if(tmp != null && (tmp instanceof com.sinosoft.lis.schema.LDSysVarSchema)){
                     ldSysVarSchema = (com.sinosoft.lis.schema.LDSysVarSchema)tmp;
                 }
             }
         if(ldSysVarSchema != null){
             CloudSwitchValue = ldSysVarSchema.getSysVarValue();
             CloudCode = ldSysVarSchema.getSysVarType();
         }
     }catch(Exception e){
         
     }
     try{
    	 String sqlString = "";
 		if(CloudCode != null)
 		    sqlString = "SELECT CODENAME FROM LDCODE WHERE CODETYPE='cloudobjectstoragename' AND CODE=\'" + CloudCode + "\'";
 		else
 			sqlString = "SELECT CODENAME FROM LDCODE WHERE CODETYPE='cloudobjectstoragename' AND CODE IS NULL";
 		try{
 			com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
 			CloudName = esql.getOneValue(sqlString);
 		}catch(Exception e){
 			
 		}
     }catch(Exception e){}
%>
<script type="text/javascript">
    window.initFormsFunctions = [];
</script>
<%@include file="CloudObjectStorageAdditionalInits.jsp"%>
<script type="text/javascript">
    function initMainForm(){
    	var csValue = "<%=CloudSwitchValue%>";
    	var cCode = "<%=CloudCode%>";
    	var cName = "<%=CloudName%>";
    	var cosCloudSwitchElement = document.getElementById("CosCloudSwitch");
    	var cloudObjectStorageElement = document.getElementById("CloudObjectStorage");
    	var cloudObjectStorageNameElement = document.getElementById("CloudObjectStorageName");
    	if(cosCloudSwitchElement !== null && cosCloudSwitchElement !== undefined && cosCloudSwitchElement.options && cosCloudSwitchElement.options.length){
    	    for(var i = 0; i < cosCloudSwitchElement.options.length; i++) {
    	    	if(cosCloudSwitchElement.options[i].value === null || cosCloudSwitchElement.options[i].value === undefined)
    	    		continue;
    	    	if(cosCloudSwitchElement.options[i].value.toUpperCase && csValue.toUpperCase && cosCloudSwitchElement.options[i].value.toUpperCase() == csValue.toUpperCase()) {
    	    	    cosCloudSwitchElement.selectedIndex = i;
    	    	    break;
    	    	}
    	    }
    	}
    	if(cCode !== null && cCode !== undefined && cloudObjectStorageElement)
    		cloudObjectStorageElement.value = cCode;
    	if(cName !== null && cName !== undefined && cloudObjectStorageNameElement)
    		cloudObjectStorageNameElement.value = cName;
    }
    function initForms()
    {
        initMainForm();
        for(var i = 0; i < window.initFormsFunctions.length; i++){
            window.initFormsFunctions[i]();
        }
    }
</script>