<!-- 登陆页面 -->
<%@page contentType='text/html;charset=gb2312' %>
<html>
<head>
<title>系统登录</title>
<script src="../common/javascript/Common.js"></script>
<script src="../common/javascript/MulLine.js"></script>
<script src="../common/javascript/encrypt/Encrypt.min.js"></script>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<script src="../common/javascript/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../common/javascript/encrypt/jQuery.dPassword.min.js"></script>
<!-- 页面样式  -->
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<script language=javascript>
$(document).ready( function() {
			$('input:password').dPassword();
});

$(function(){

    $('#kaptchaImage').click(function () { 
    	$(this).fadeOut('slow',function(){
    		$(this).fadeIn('slow'); 
    		$(this).attr('src', '../kaptcha.jpg?' + Math.floor(Math.random()*100));
    	});
    }).hover(
    	function(){
    		$(this).addClass("cursor_hand");
    	},
    	function(){
    		$(this).removeClass("cursor_hand");
    	}
    );

});
	
function submitForm(){
    if (!achieveInit()) return false;

    if(fm.UserCode.value.length == 0){
    	document.all("UserCode").focus();
       alert('用户名不能为空');
        return false;
    }
    if(fm.PWD.value.length == 0){
    	document.all("PWD").focus();
         alert('密码不能为空');
        return false;
    }
    if (fm.StationCode.value.length == 0){
    	document.all("StationCode").focus();
        alert('机构编码不能为空');
        return false;
    }
    /*
    if (fm.NumCode.value.length == 0){
        alert('验证码不能为空');
        return false;
    }
    */
	tValue =  encrypt(fm.PWD.value);
	fm.PWD.value = tValue;
	//prompt('2',fm.PWD.value);
    fm.ClientURL.value = document.location;
    
    fm.submit();
    return true;
}

document.onkeypress = function(e) {
    e = e ? e : window.event;
    var keyCode = e.which ? e.which : e.keyCode;
    if(keyCode == 13) {
    	//fm.UserCode.focus();
	 	submitForm();
    }
}
/**
function exprint(){
	var ev = window.event || event;
	var keycode = ev.which ? ev.which : ev.keyCode;
	if (keycode == "13"){
		fm.UserCode.focus();
	 	submitForm();
 	}
}
**/
function achieveInit()
{
    try
    {
        var tVD = top.achieveVD;
        var tEX = top.achieveEX;

        if (!(tVD && tEX) || typeof(mCs) == "undefined")
        {
            //alert("tVD:" + tVD + "\ntEX:" + tEX + "\nmCs:" + typeof(mCs));
            alert('页面初始化未完成。请稍候!');
            top.window.location = "../indexlis.jsp";
            return false;
        }
    }
    catch (ex)
    {
    	 alert('页面初始化错误!');
        top.window.location = "../indexlis.jsp";
        return false;
    }
    return true;
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}

MM_reloadPage(true);
</script>
<style type="text/css">
.cursor_hand {
    cursor:hand;
 }
    body
{
font: tahoma,verdana,arial,helvetica,sans-serif;
font-size: 8px; margin-top:0px;
SCROLLBAR-FACE-COLOR: #97CBFF; SCROLLBAR-HIGHLIGHT-COLOR:#fff; 

SCROLLBAR-SHADOW-COLOR:#97CBFF; SCROLLBAR-DARKSHADOW-COLOR:#819FD3; 

SCROLLBAR-3DLIGHT-COLOR:#819FD3; SCROLLBAR-ARROW-COLOR:#3F65AD;

SCROLLBAR-TRACK-COLOR: #E4E4E4;
}
table {border-collapse:separate}/*2011-07-27 newupdate add*/
td {padding:1.5px}/*2011-07-27 newupdate add*/
<!--    
td {FONT-FAMILY: 宋体;font-size:9pt}
input {FONT-FAMILY: 宋体;font-size:9pt}
-->
	td {font-size:9pt}
	input {font-size:9pt}
.style4 {font-size: 9px}
.lag{ padding-bottom:10px;}
.inp{padding-bottom:12px;}
.nop{padding-bottom:12px;}
@-moz-document url-prefix() { .lang{ padding-top:44px;}}
@-moz-document url-prefix() { #lagger{ padding-bottom:8px;}}
@-moz-document url-prefix() { #inpper{ padding-bottom:60px;}}
@-moz-document url-prefix() { #nopper{ padding-bottom:60px;}}

@media screen and (-webkit-min-device-pixel-ratio:0) { .lang{ padding-top:36px;padding-left:100px;} }
@media screen and (-webkit-min-device-pixel-ratio:0) { .lan,.pass,.valv,.lag{padding-left:100px;} }
@media screen and (-webkit-min-device-pixel-ratio:0) { .inp{padding-left:150px;} }

.lang{ padding-bottom:2px\9\0;}
.intt{ width:182px\9\0; height:16px\9\0;}
.lang{ padding-left:18px\9\0; padding-top:6px\9\0;}
.lan,.pass,.valv,.lag{ padding-left:18px\9\0;}
.btn{ width:60px\9\0; height:24px\9\0;}
</style>
</head>
<body leftmargin="0" topmargin="0" >
<table id="mainTable" width="930" height="570" border="0" align="center" cellspacing="0" style="background: url(../common/images/Login_I.gif) no-repeat center;">
  <tr width="930" height="80">
    <td></td>
  </tr>
  <tr width="930" height="1">
    <td >
         <form name="fm" action="./LogonSubmit.jsp" method="post">
               <table cellspacing="-10px" width="650" border=0 height="0">
               	<tr width="930" height="0">
                   <td width="20">&nbsp;</td>
                    <td width="10" >
                       <table width="50" >
               						 <tr>
                            <td width="50" >
                            	<div class="lang" align="right">
                              	<input class="intt" name=language type="text" style="solid #701700;BACKGROUND-COLOR: #A00000;height:22;width:190"  size="14" maxlength="14" id="language" value='中文'  >
                      				</div>
                      			</td>
                      			<td width="50"></td>
                        	</tr>
                      	</table>
                </tr>
                <tr width="930" height="0">
                   <td width="90">&nbsp;</td>
                    <td width="150">
                       <table width="180">
                         <tr>
                             <td width="60">
                            <div class="lan" align="right">
                              <input class="intt" name=UserCode  type="text" style="height:22;width:190" id="UserCode2" size="14" maxlength="14" value="001">
                      </div>
                      </td>
                      <td width="50"></td>
                        </tr>
                      </table>
                    </tr>
                    <tr width="830" height="0">
                   <td width="90">&nbsp;</td>
                    <td width="150">
                       <table width="180">
                         <tr>
                            <td width="60">
                                  <div class="pass" align="right">
                                  <input class="intt" name=PWD  type="Password" style="height:22;width:190" id="PWD2" size="14" maxlength="14" value="001" autocomplete="off">
                                  </div>
                      </td>
                      <td width="50"></td>
                        </tr>
                      </table>
                     </tr>
                      <tr>
                  
                </tr>
                    <tr width="830" height="0">
                   <td width="90">&nbsp;</td>
                    <td width="150">
                       <table width="180" border=0>
                         <tr>
                             <td width="50">
                                   <div class="valv" align="right">
                                      <input class="intt" name=StationCode  type="text" style="BACKGROUND-COLOR: #A00000;height:22;width:190" id="StationCode" value=86 onDblClick="if (achieveInit()) showCodeList('comcode',[this]);"  onKeyUp="return showCodeListKey('comcode',[this]);" size="14"maxlength="14">
                                   </div>
                      </td>
                      <td width="50"></td>
                        </tr>
                      </table>
                    </td>
                </tr>
                
                <tr width="930" height="0">
                   <td width="90">&nbsp;</td>
                    <td width="200">
                    	
                       <table  style="word-break:keep-all;" width="180" border=0>
                         <tr>
                             <td  nowrap="nowrap"><div class="lag" id="lagger"><input class="btn" name=NumCode type="text" id="NumCode" style="height:30;width:80;FONT-SIZE:28;" maxlength="4" onfocus="closeCodeList();"><img id="kaptchaImage" align="AbsBottom" src="../kaptcha.jpg"/></div></td>
                      
                        </tr>
                      </table>
                    </td>
                </tr>
                
               
                <tr width="830" height="40" align="left" valign="middle">
                     <td width="90">&nbsp;</td>
                  <td height="20"><table width="200" height="29" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                          <!--<td width="60" align="left"><input type="image" src="../common/images/login_Yes.gif" name="submit2" onClick="return submitForm();"></td>
                        <td width="60" align="left"><input type="image" src="../common/images/login_No.gif" name="reset2" onClick="fm.reset();return false;"></td>
                      -->
                      <td width="200"></td>
                       <td class="inp" id="inpper" width="60" align="left"><input  class=commson type="button" value="登    录" name="submit2" onClick="return submitForm();"></td>
                       <td class="nop" id="nopper" style="padding-left:20px" width="60" align="left"><input  class=commson type="button" value="取    消" name="reset2" onClick="fm.reset();return false;"></td>
                        
                      </tr>
                  </table>
                  </td>
                </tr>
               
        <!--添加层-->
        <div align="right"></div>
        <div align="justify"></div>
         <input TYPE="hidden" name="ClientURL" value="">
        <span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;"> </span>
        </form>
    </td>
  </tr>
</table>
</body>
</html>
