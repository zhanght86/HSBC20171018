<?xml version="1.0" encoding="GB2312"?>
<!--Root="../../" -->
<html>
<head>
<title id=indexTitle>NCL</title>
</head>
<!-- ��������������,���ڴ���ѡ���ܲ������������������� -->
<script language="javascript">
var achieveEX = false;          //�����ж�ҳ���ʼ�����
var achieveVD = false;          //�����ж�ҳ���ʼ�����

var intPageWidth=screen.availWidth;
var intPageHeight=screen.availHeight;
//����Ķ������Զ��Ŵ�
//window.resizeTo(intPageWidth,intPageHeight);
window.focus();

window.name = "Lis";
</script>
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="./common/cvar/CVarData.jsp" noresize />

	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="./common/cvar/CExec.jsp" noresize />

	<frame name="fraSubmit"  scrolling="yes"  src="about:blank" noresize />
	<frame name="fraTitle"  scrolling="no"  src="./logon/Title.jsp" noresize />
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="1" framespacing="0">
		<!--�˵�����-->
		<frameset name="fraMenuMain" rows="25,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraMenuTop" name="fraQuick" scrolling="no" src="./logon/menutop.jsp" noresize />
			<frame id="fraMenu" name="fraMenu" scrolling="auto" src="about:blank" noresize />
		</frameset>

		<!--��������-->
		<frameset name="fraTalk" rows="0,*" frameborder="no" border="1" framespacing="0">
			<frame id="fraQuick" name="fraQuick" scrolling="no" src="./logon/quick.jsp" noresize />
			<frameset name="fraTalkCol" frameborder="no" border="1" framespacing="0" cols="0, *">
			  <frame id="fraPic" name="fraPic" scrolling="auto" src="about:blank" noresize />
			  <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./logon/main1.jsp" noresize />
			</frameset>
		</frameset>

    <!--��һ��ҳ������-->
    <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank" noresize />
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
