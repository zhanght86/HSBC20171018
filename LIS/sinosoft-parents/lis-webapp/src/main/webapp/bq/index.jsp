<?xml version="1.0" encoding="GB2312"?>
<!--Root="../../" -->
<html>
<head>
<title id=indexTitle>NCL</title>
</head>
<!-- 设置主窗口名字,用于代码选择功能查找主窗口来缓存数据 -->
<script language="javascript">
var achieveEX = false;          //用于判断页面初始化完成
var achieveVD = false;          //用于判断页面初始化完成

var intPageWidth=screen.availWidth;
var intPageHeight=screen.availHeight;
//讨厌的东西，自动放大
//window.resizeTo(intPageWidth,intPageHeight);
window.focus();

window.name = "Lis";
</script>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0">
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="./common/cvar/CVarData.jsp" noresize />

	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="./common/cvar/CExec.jsp" noresize />

	<frame name="fraSubmit"  scrolling="yes"  src="about:blank" noresize />
	<frame name="fraTitle"  scrolling="no"  src="./logon/Title.jsp" noresize />
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0">
		<!--菜单区域-->
		<frameset name="fraMenuMain" rows="25,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraMenuTop" name="fraQuick" scrolling="no" src="./logon/menutop.jsp" noresize />
			<frame id="fraMenu" name="fraMenu" scrolling="auto" src="about:blank" noresize />
		</frameset>

		<!--交互区域-->
		<frameset name="fraTalk" rows="0,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraQuick" name="fraQuick" scrolling="no" src="./logon/quick.jsp" noresize />
			<frameset name="fraTalkCol" frameborder="no" border="1" framespacing="0" cols="0, *">
			  <frame id="fraPic" name="fraPic" scrolling="auto" src="about:blank" noresize />
			  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./logon/main1.jsp" noresize />
			</frameset>
		</frameset>

    <!--下一步页面区域-->
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank" noresize />
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
