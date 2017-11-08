<%@include file="../common/jsp/UsrCheck.jsp"%>
<script type="text/javascript">
    window.regCosInitFormFunction = function(func){
        var regFunc = func;
        if(window.initFormsFunctions !== null && window.initFormsFunctions !== undefined && window.initFormsFunctions.length !== undefined && window.initFormsFunctions.length !== null){
            window.initFormsFunctions.push(regFunc);
        }
    }
</script>
<%@include file="CloudObjectStorageQCloudInit.jsp"%>