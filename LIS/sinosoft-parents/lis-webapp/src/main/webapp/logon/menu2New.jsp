<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%

    String sIPAddress = request.getParameter("Ip");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="MzTreeView Author" content="meizz: http://www.meizz.com">
    <title>系统菜单</title>
    <!-- 私有使用样式2015716 -->
    <style type=text/css>
        body
        {
		font: tahoma,verdana,arial,helvetica,sans-serif;
            font-size: 12px;
            SCROLLBAR-FACE-COLOR: #fff; SCROLLBAR-HIGHLIGHT-COLOR:#fff; 

SCROLLBAR-SHADOW-COLOR:#fff; SCROLLBAR-DARKSHADOW-COLOR:#fff; 

SCROLLBAR-3DLIGHT-COLOR:#fff; SCROLLBAR-ARROW-COLOR:#fff;

SCROLLBAR-TRACK-COLOR: #fff;
            
            padding:0; /*margin:0;*/ margin-top:25px; margin-left: 0; margin-right: 0px;/*2015-9-1*/
        }/*2011-07-28 newupdate*/ 
        a:link, a:visited, a:active
        {
            color: #2c85d1;font-family:"宋体"; font-weight:bold;
			padding-left:15px;
            /*padding-left: 2px;*/
            text-decoration: none;
        }
        a:hover
        {
            color: #fff;
			padding-left:15px;
           /* padding-left: 2px;*/
            text-decoration: none;
			color:#2c85d1; font-family:"宋体"; font-weight:bold;
        }
        div{padding:0; margin:0;}
<!--        	DIV.MzTreeView DIV{background:url(../common/TreeView/images/menutitloop.gif) no-repeat right;width:100%;display:block; vertical-align:middle;height:24px; padding-left:3px; line-height:24px;}/*2011-07-28 newupdate*/  -->
				DIV.MzTreeView DIV{background:url(../common/TreeView/images/menutitloop.gif) no-repeat right;width:120%;display:block; vertical-align:middle;height:40px; padding-left:15px; line-height:24px;}/*2015-06-18 ###*/ 
    
    
    /*菜单弹出层*/
    </style>
    <link href="../common/css/pointMenu2Jsp.css" rel="stylesheet" type="text/css" />
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="../common/TreeView/MzTreeView.js"></script>
	<script src="../common/javascript/jquery-1.7.2.js"></script>
    <script src="../common/javascript/pointMenu2Jsp.js"></script>
    <!-- 私有使用脚本 -->
    <script type="text/javascript">
            
		  
		  
        //检查访问地址
        if (top.location == self.location)
        {
            top.location = "../indexlis.jsp";
        }

        //显示状态栏信息
        defaultStatus = "欢迎使用本系统";
        top.window.status = defaultStatus;
	
        //显示用户位置
        function showStation(sNodeCode)
        {
        	c();
            var sLinkURL;
            if (sNodeCode == null || sNodeCode == "")
            {
                sLinkURL = "stationNew.jsp";
            }
            else
            {
                sLinkURL = "stationNew.jsp?nodecode=" + sNodeCode + "&Ip=<%=sIPAddress%>";
            }
            try
            {
                parent.fraQuick.window.location = sLinkURL;
            }
            catch (ex) {}
        }
		var destroys = true;
		function destroy(){
			destroys =false;
		}
        //退出业务系统
        function destroySession()
        {
        	if(destroys) {
            try
            {
                //window.showModelessDialog("close.jsp", window, "status=0; help=0; close=0; dialogWidth=160px; dialogHeight=100px");
            	 window.open ("close.jsp","close", "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height=1,width=1,innerHeight=1,innerWidth=1,left=-1,top=-1",false);
            }
            catch (ex) {}
        	}
        } 
        
        function clickLeftMenu(a)
        {
            try
            {
            	var headFrame = window.parent.frames["head"];
            	if(typeof(window.parent.frames["head"].contentWindow) != 'undefined')
            		headFrame = window.parent.frames["head"].contentWindow;
            	headFrame.clickLeftMenu(a);
            }
            catch (ex) {
            	alert(ex);
            }
        }  
        
    </script>
</head>
<body topmargin="2" onbeforeunload="destroySession()"  ondragstart="return false" style="margin-top:0px;background:url(../common/TreeView/images/menubgloop.gif) repeat-y right #fff;">
<div style="  width: 100%;height: 100%;overflow: scroll;">
    <div id="divMenuTree" style="float:left;margin-top: 25px;"></div>
    <div id="divMenuTreeCopy" style="margin-top: 25px;float:left;display: none;width: 168px;height: 600px;">
    	<div class="left">
            <ul>
                <p class="diyi"><b>业务系统首页</b></p>
                <li>Test</li>
            </ul>
		</div>
    </div>
	<div id="middle-self" class="middle" style="float:left;margin-top: 25px;">
		<div class="huadong1" id="huadong1">
			<dl>
				<dt class=""><b>Test</b></dt>
			</dl>
		</div>
	</div>
    <div  onclick="fivo()" class="" style="position: absolute;right: 2px;top: 250px;"><img src="../common/images/images/hide_03.png"></div>
    <div style="position: absolute;right: 0px;border: 1px solid whitesmoke;height: 100%;"></div>
    <!-- 缓存后续图像文件 -->
    <div style="display:none">
        <img src="../common/TreeView/images/folder_blue.png">
    </div>
</div>
</body>
</html>
<script language="JavaScript">fivo2();</script>
