<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    /**
    * CloudObjectStorageInput.jsp
    * @author Wang Zhang
    *
    */
%>
<html>
    <%@page contentType="text/html;charset=gb2312" %>
    <%@include file="../common/jsp/Log4jUI.jsp"%>
    <%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
    <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="java.util.*"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
        <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
        <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
        <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
        <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
        <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
        <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
        
        <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
        <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
        <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
        <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
        <script src="./CloudObjectStorage.js"></script>
        <%@include file="CloudObjectStorageInit.jsp"%>
    </head>
    <body id="cloudObjectStorageInputBody">
        <form action="./CloudObjectStorageSave.jsp" method="post" name="formCosMain" id="formCosMain" target="fraSubmit">
            <table>
                <tr class=common>
                    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCloudObjectStorageMain);"></td>
                    <td class=titleImg>云服务启动设置</td>
                </tr>
            </table>
            <div id="divCloudObjectStorageMain" class="maxbox">
                    <table class=common>
                        <tbody>
                            <tr class=common>
                                <td class=title5>
                                    云服务
                                </td>
                                <td class=input5>
                                    <select id="CosCloudSwitch" class=codename name="CosCloudSwitch"  style="border:1px solid #d4d4d4;height:26px;line-height:26px;border-radius:6px;padding-left:3px;margin-left:2px;background:url('../images/inputbg_03.png')" >
                                        <option value="Enabled">启用</option>
                                        <option value="Disabled">禁用</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class=title5>选择云服务</td>
                                <td class=input5>
                                    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="CloudObjectStorage" id="CloudObjectStorage" ondblclick="showCodeList('cloudobjectstoragename',[this,CloudObjectStorageName],[0,1])" onclick="showCodeList('cloudobjectstoragename',[this,CloudObjectStorageName],[0,1])" onkeyup="showCodeListKey('cloudobjectstoragename',[this,CloudObjectStorageName],[0,1])" />
                                    <input class=codename name="CloudObjectStorageName" id="CloudObjectStorageName" />
                                </td>
                            </tr>
                    </tbody>
                </table>
                <div style="margin-top:5pt;padding-top:1pt;">
                    <a id="cloudObjectStorageInputMainSubmit" class="button" type="submit" href="javascript:void(0);" >保存启动设置</a>
                </div>
            </div>
        </form>
        <%@include file="CloudObjectStorageAdditionalInputs.jsp"%>
    </body>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <script type="text/javascript">
        var cloudObjectStorageInputBodyElement = document.getElementById("cloudObjectStorageInputBody");
        cloudObjectStorageInputBodyElement.onload = function(e){
            if(initForms)
            initForms();
        }
        var cloudObjectStorageInputMainSubmitElement = document.getElementById("cloudObjectStorageInputMainSubmit");
        cloudObjectStorageInputMainSubmitElement.onclick = function(e) {
        	var formElement = window.document.getElementById("formCosMain");
        	formElement.submit();
        }
    </script>
</html>