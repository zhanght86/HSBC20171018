<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sinosoft
 * @version  : 1.00
 * @date     : 2006-01-22
 * @direction: ������ʧ�ܽ���
 ******************************************************************************/
%>


<html>
<head>
    <title>������ʧ����</title>
</head>
<frameset name="fraMain" framespacing="0" frameborder="no" rows="0,0,0,0,*" cols="*" border="1">
	<!-- ����ͻ��˱��������� -->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<!-- ʵ�ַ��������������� -->
	<frame name="EX" src="../common/cvar/CExec.jsp">
    <!-- �ύ�ͻ��˲�ѯ������ -->
	<frame name="fraSubmit" src="about:blank" scrolling="yes" noresize>
	<!-- ������ʾ������������ -->
	<frame name="fraTitle" src="about:blank" scrolling="no" noresize>
	<!-- ��ʾ�˵��ͽ��������� -->
	<frameset name="fraSet" framespacing="0" frameborder="no" rows="*" cols="0%,*,0%" border="1">
		<!-- ��ʾ�˵������� -->
		<frame name="fraMenu" src="about:blank" scrolling="yes" noresize>
		<!-- ʵ�ֽ��������� -->
		<frame name="fraInterface" src="PEdorTypePLInput.jsp" scrolling="auto">
        <!-- ��һ���������� -->
        <frame name="fraNext" src="about:blank" scrolling="auto">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#FFFFFF">
	    <br><br><br><br><br>
	    <center>
	        <font size="2">�Բ��������������֧�ֿ����ҳ��</font>
	        <br><br>
	        <font size="2">��ʹ�� IE 5.0 �������������ϵͳ��</font>
	    </center>
	</body>
</noframes>
</html>
