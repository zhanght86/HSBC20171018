<!-- 登陆页面 -->
<%@page contentType='text/html;charset=gb2312' %>
<html>
<head>
<title>系统登录</title>
<script>
var a="输入密码";
</script>

<script src="../common/javascript/Common.js"></script>
<script src="../common/javascript/MulLine.js"></script>
<script src="../common/javascript/encrypt/Encrypt.min.js"></script>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<script src="../common/javascript/jquery-1.5.1.js"></script>
<script type="text/javascript" src="../common/javascript/encrypt/jQuery.dPassword.min.js">

</script>
<script>
var current;
function setColor(input) {
	if (current) {
		removeColor(current);
	}
	current = input; 
	input.parentNode.style.borderColor='#32a0de';
}
function removeColor(input){input.parentNode.style.borderColor=''}

</script>

<script>
        $(document).ready(function() {
            $("#input-box").click(function(){
                if($(".dropdown-menu").css("display") == "none"){
                    $(".dropdown-menu").show();
                    $("#input-box").mouseleave(function(){
                        $(".dropdown-menu").hide();
                    });
                }else{
                    $(".dropdown-menu").hide();
                }
            });
            $(".dropdown-menu ul li a").click(function(){
                var content = $(this).html();
                $("#selected").html(content);
            });
        });
    </script>

<script src="cloud.js" type="text/javascript"></script>
<!-- 页面样式  -->
<!--<link rel='stylesheet' type='text/css' href='../common/css/other.css'>-->
<!--<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>-->
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<style>
    .input-box {
    padding-left: 8px;
    width: 130px;
    height: 20px;
    font-size: 13px;
    color: #000;
    background: #f8f7f7;
    border: 1px solid #d2d2d2;
    border-radius: 4px;
    display: inline-block;
    position: absolute;
    right:80px;
    top:12px;
}
    #input-box {
    text-decoration: none;
    float: left;
    margin-right: 10px;
}
    .dropdown-menu{
    padding: 0;
    position: absolute;
    top: 19px;
    left: -2px;
    z-index: 1000;
    display: none;
    float: left;
    width: 140px;
    margin: 2px 0 0;
    font-size: 10pt;
    text-align: left;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    border-radius: 4px;
    -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
    background-clip: padding-box;
    overflow-x: hidden;
    height: 40px;
}
.dropdown-menu ul {
    height: 40px;
    overflow-y: scrool;
}
.dropdown-menu ul li{
    width: 100%;
    padding: 0 10px;
    height: 20px;
    line-height: 20px;
}
.dropdown-menu ul li:hover{
    background: #0077bb;
}
.dropdown-menu ul li a{
    display: block;
    text-decoration: none;
}
.dropdown-menu ul li a:hover{
    color: white;
}
.selected {
    font-size: 13px;
    font-weight: normal;
    color: #767676;
    height: 20px;
    line-height: 20px;
    overflow-x: hidden;
    overflow-y: hidden;
}
</style>
<script language=javascript>
$(document).ready( function() {
			$('input:password').dPassword();
			var password = document.getElementById('password_PWD2');
			password.onfocus = function(){setColor(password)};
			password.onblur = function(){removeColor(password)};
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
       alert('用户名不能为空');
        return false;
    }
    if(fm.PWD.value.length == 0){
         alert('密码不能为空');
        return false;
    }
    if (fm.StationCode.value.length == 0){
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

function exprint(e){
/* 	var keycode = event.keyCode;
	if (keycode == "13"){
		fm.UserCode.focus();
	 	submitForm();
 	} */
 	 e = e ? e : window.event;
    var keyCode = e.which ? e.which : e.keyCode;
    if(keyCode == 13) {
    	//fm.UserCode.focus();
	 	submitForm();
    }
}

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



<!--下拉框-->





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
	td {font-size:9pt;}
	input {font-size:9pt;}
.style4 {font-size: 9px;}
#login_header a,#login_header span{ color:#b2c8e2; font-size:14px; font-family:"宋体"; padding-bottom:4px;}
#login_header #textbox{ position:absolute; right:6%; top:8%;}
.newsubmitbtn{ width:110px; height:40px;}
.newsubmitbtn input{width:110px; height:40px; background-color:#4098ca; color:#fff; border-radius:3px; font-family:"黑体"; font-size:18px;}
#UserCode2,#StationCode,#NumCode{ background-color:#ecf5fa}
/*.login_table .immg{position:absolute; left:40%; top:10%;}*/
/*#yidong{ position:absolute; top:21%; left:24%;}*/
.login_table .immg{ margin-bottom:20px;}
#yidong{ margin-bottom:28px;}
#mainBody {width:100%;height:100%;position:absolute;z-index:-1;}
.cloud {position:absolute;top:0px;left:0px;width:100%;height:100%;background:url(../common/images/cloud_03.png) no-repeat;z-index:1;opacity:0.5;}
#cloud2 {z-index:2;}


ul{margin:0;padding:0;list-style:none;}
.dropDownList{position:absolute;left:100px;top:100px;}
/* .dropDownList div.dropdown select{display:none;}*/
.dropDownList div.dropdown{float:left;margin-right:120px;width:146px;}
.dropDownList span{display:block;width:146px;height:26px;background:url() left top no-repeat;line-height:26px;text-indent:12px;border:solid 1px #83BBD9;cursor:default;}
.dropDownList span.over{background-position:left bottom;border-color:#B4C91A;}
 .dropDownList ul{background:#eee;width:200px;display:none;}
  .dropDownList ul li{height:20px;width:100%;padding:3px 0;text-indent:12px;cursor:default;}
  .dropDownList ul li.over{background:#052a57;}
 .dropDownList ul.show{display:block;}
/*#login_main .ipt:hover{ border:1px solid #32a0de;}*/
body{ min-width:1000px; min-height:600px;}










</style>



</head>
<!--<body leftmargin="0" topmargin="0" onkeypress = "exprint(event);">
<form name="fm" action="./LogonSubmit.jsp" method="post">
<div id="login_header">
    <img src="../common/images/03_03.png">
    <select name="language" id="language">
        <option value="1">中文</option>  
        <option value="2">英文</option> 
    </select>
</div>
<div id="login_main">
	<img src="../common/images/07.png" class="system">
    <div class="login_table">
    	<div class="login_input">
        	<div class="user_name ipt">
            	<img class="" src="../common/images/10.png">
                <input name="UserCode" type="text" id="UserCode2" placeholder="输入登录名">
            </div>
            <div class="password ipt">
            	<img class="" src="../common/images/13.png">
                <input name="PWD" type="password" id="PWD2" placeholder="输入密码">
            </div>
            <div class="station_code ipt" style="position:relative">
            	<img class="" src="../common/images/19.png">
                <input name="StationCode" type="text" id="StationCode" value="86" onDblClick="if (achieveInit()) showCodeList('comcode',[this]);"  onKeyUp="return showCodeListKey('comcode',[this]);" placeholder="输入机构">
                <span id="spanCode"  style="display: none; position:absolute; slategray; left: 0; top: 30px; width: 271px; height: 44px;z-index:100;"> </span>
            </div>
            <div>
                <div class="num_code ipt">
                	<img class="" src="../common/images/18.png">
                    <input name="NumCode" type="text" id="NumCode" onFocus="closeCodeList();" placeholder="输入验证码">
                </div>
                <img id="kaptchaImage" class="code_img" src="../kaptcha.jpg"/>
            </div>
            <div class="submit_btn"><input type="button" value="" name="submit2" onClick="return submitForm();"></div>
        </div>
    </div>
</div>

<div id="login_footer">Copyright China Life Insurance Company Ltd. All Rights Reserved. 京ICP备12006044号</div>
<input TYPE="hidden" name="ClientURL" value="">
<!--span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;"> </span-->
<!--</form>


</body>-->


<body leftmargin="0" topmargin="0" onkeypress = "exprint(event);" style="background:url(../common/images/lastbbgg_01.png) no-repeat; background-color:#51b1e1; position:relative;">

<div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>
    
    

<form name="fm" action="./LogonSubmit.jsp" method="post">
<div id="login_header">
    <img src="../common/images/logotext_03.png">
    <!-- <select name="language" id="language">
        <option value="1">中文</option>  
        <option value="2">英文</option> 
    </select> -->
    <div class="input-box" title="请选择!" id="input-box">
        <div id="selected" class="selected">简体中文</div>
        <div class="dropdown-menu">
            <ul class="dropdown-ul" role="menu" id="selectable">
                <li><a href="javascript:void(0);">English</a></li>
                <li><a href="javascript:void(0);">简体中文</a></li>
            </ul>
        </div>
    </div>
  


</div>
<div style="width:696px; height:408px; position:absolute; top:50%; right:50%; margin-top:-204px; margin-right:-348px;">
	<img id="yidong" src="../common/images/yidonghexin_03.png">
    <div id="login_main" style=" width:696px; height:348px; background-color:#fff; overflow:hidden;">
        <img src="../common/images/lefttree-bg_03.png" style="float:left">
        <div class="login_table" style="float:right; margin-top:34px; margin-right:49px;">
            <img class="immg" src="../common/images/yonghudenglu_03.png">
            <div class="login_input">
                <div class="user_name ipt">
                    <img class="" src="../common/images/btn01_03.png">
                    <input name="UserCode" type="text" id="UserCode2" placeholder="输入登录名" value = 'lis001'onfocus="setColor(this)" onblur="removeColor(this)">
                </div>
                <div class="password ipt">
                    <img class="" src="../common/images/btn02_03.png">
                    <input style="background-color:#ecf5fa" name="PWD" type="password" id="PWD2" value = 'lis001' placeholder="输入密码" onfocus="setColor(this)" onblur="removeColor(this)">
                </div>
                <div class="station_code ipt" style="position:relative">
                    <img class="" src="../common/images/btn03_03.png">
                    <input name="StationCode" type="text" id="StationCode" value="86" onDblClick="if (achieveInit()) showCodeList('comcode',[this]);" onClick="if (achieveInit()) showCodeList('comcode',[this]);"  onKeyUp="return showCodeListKey('comcode',[this]);" placeholder="输入机构" onfocus="setColor(this)" onblur="removeColor(this)">
                    <span id="spanCode"  style="display: none; position:absolute; slategray; left: 0; top: 30px; width: 271px; height: 44px;z-index:100;"> </span>
                </div>
                <div>
                    <div class="num_code ipt">
                        <img class="" src="../common/images/btn04_03.png">
                        <input name="NumCode" type="text" id="NumCode" onFocus="closeCodeList();setColor(this);" placeholder="输入验证码" onblur="removeColor(this)">
                    </div>
                    <img id="kaptchaImage" class="code_img" src="../kaptcha.jpg"/>
                </div>
                <div class="newsubmitbtn"><input type="button" value="登&nbsp;&nbsp;录" name="submit2" onClick="return submitForm();"></div>
            </div>
        </div>
    </div>
</div>


<div id="login_footer">Copyright Sinosoft Co, Ltd. All Rights Reserved.</div>
<input TYPE="hidden" name="ClientURL" value="">
<!--span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;"> </span-->
</form>


</body>
</html>
