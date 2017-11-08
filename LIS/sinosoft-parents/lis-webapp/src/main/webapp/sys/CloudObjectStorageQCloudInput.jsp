<%@include file="../common/jsp/UsrCheck.jsp"%>
        <form action="./CloudObjectStorageQCloudSave.jsp" method="post" name="formCosQcloud" id="formCosQcloud" target="fraSubmit">            
            <table>
                <tr class=common>
                    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCloudObjectStorageQCloud);"></td>
                    <td class=titleImg>腾讯云对象存储设置</td>
                </tr>
            </table>
            <div id="divCloudObjectStorageQCloud" class="maxbox">
                <table class=common>
                    <tbody>
                        <tr>                        
                            <td class=title5>
                                AppId
                            </td>
                            <td class=input5>
                                <input class=common id="QCloudAppIdInput" name="AppId" type="text" />
                            </td>
                            <td></td>
                            <td></td>                                       
                        </tr>
                        <tr>                            
                            <td class=title5>
                                SecretId
                            </td>
                            <td class=input5>
                                <input class=common id="QCloudSecretIdInput" name="SecretId" type="text" />
                            </td>
                            <td class=title5>
                                SecretKey
                            </td>
                            <td class=input5>
                                <input class=common id="QCloudSecretKeyInput" name="SecretKey" type="text" />
                            </td>                                        
                        </tr>
                        <tr>                            
                            <td class=title5>
                                BucketName
                            </td>
                            <td class=input5>
                                <input class=common id="QCloudBucketNameInput" name="BucketName" type="text" />
                            </td>
                            <td class=title5>访问通道</td>
                            <td class=input5>
                                <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="AccessChannel" id="QCloudAccessChannelInput" ondblclick="showCodeList('qcloudaccesschannel',[this,QCloudAccessChannelNameInput],[0,1])" onclick="showCodeList('qcloudaccesschannel',[this,QCloudAccessChannelNameInput],[0,1])" onkeyup="showCodeListKey('qcloudaccesschannel',[this,QCloudAccessChannelNameInput],[0,1])" />
                                <input class=codename name="AccessChannelName" id="QCloudAccessChannelNameInput" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div style="margin-top:5pt;padding-top:1pt;">
                    <a id="cloudObjectStorageInputQCloudSubmit" class= "button" type="submit" href="javascript:void(0);" >保存腾讯云设置</a>
                </div>    
            </div>
        </form>
<script>
    var cloudObjectStorageInputQCloudSubmitElement = document.getElementById("cloudObjectStorageInputQCloudSubmit");
    cloudObjectStorageInputQCloudSubmitElement.onclick = function(e) {
	    var formElement = window.document.getElementById("formCosQcloud");
	    formElement.submit();
    }
</script>