<%
/***************************************************************
 * <p>ProName：ScanServo.jsp</p>
 * <p>Title：影像随动界面</p>
 * <p>Description：影像随动界面</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String SubType    = request.getParameter("SubType");
	String BussNo     = request.getParameter("BussNo");
	String BussType   = request.getParameter("BussType");
	String PageCode   = request.getParameter("PageCode");
%>
<html>
<head>
	<title>随动定制界面</title>
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script><!-- jQuery EasyUI是一款基于JQuery的UI快速搭建组件 风格与EXTJS有点相似-->
	<script src="../common/javascript/jquery.Jcrop.min.js"></script><!-- Jcrop一个Query实现图片裁剪的插件 -->
	<script src="../common/javascript/jquery.rotate.js"></script><!-- 实现图片旋转功能 -->
	<script src="../common/javascript/jquery.imageView.js"></script><!-- 可以拖动查看大图的jQuery插件-->
	<script src="EasyScanCommon.js"></script>
	<script>window.document.onkeydown = document_onkeydown;</script>
	
</head>
<!-- 加载展示图片 -->
<script>
	//当用户关闭一个页面时触发 onunload 事件。
  window.onunload = afterInput;
  function afterInput() {
    try {
    	$("body").remove();
    }
    catch(e) {}
  }
  
  	var viewMode = 1;//显示模式
  	//加载图片
	var arrPicName = new Array();
	var subType =  "<%=SubType%>"; 
	var bussNo =  "<%=BussNo%>"; 
	var bussType =  "<%=BussType%>";
	var pageCode =  "<%=PageCode%>";
	
	//理赔发票同时要展示病历信息
	if (subType=="23003") {
		subType = "23003,23004";
	}
	
	var params = {Operate:'1',SubType:subType,BussNo:bussNo,BussType:bussType,PageCode:pageCode,async : false}
  	$.post("EasyScanQuery.jsp?Sid="+getTimeForURL(),
  		params,function(data) {
			$.each(data, function(i, n) {
				 var picPath = data[i].picPath;
				 arrPicName.push(picPath);
		}); 
		initEasyScanServo();//加载随动按钮
  	},"json" 
	);
</script>

<body  class="easyui-layout">
<div id="PicView" region="center" title="影像件" split="false">
	<div id="PicTab" class="easyui-tabs" fit="true" border="false"><!--图片区域 -->
	</div>
</div>

	<form name=fm>
		<input type=hidden  id="x" name="x" />
		<input type=hidden  id="y" name="y" />
		<input type=hidden  id="x2" name="x2" />
		<input type=hidden  id="y2" name="y2" />
		<input type=hidden  id="w" name="w" value="0"/>
		<input type=hidden  id="h" name="h" value="0"/>
		<input type=hidden  id="DocCode" name="DocCode" value = "<%=BussNo%>"/>
	</form>
</body>
</html>
