<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ImageQueryMain.jsp
//�����ܣ�Ӱ�����ϲ�ѯ
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>���������</title>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script language="javascript">
	//parent.document.all.ifrm8.height = screen.availHeight;	//add by liuyuxiao 2011-05-23
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	//window.focus();
	
	var initWidth = 0;
  //ͼƬ�Ķ�������
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //�Ŵ�ͼƬ�Ĵ���
  var s_img = 0;	//��СͼƬ�Ĵ���
  
  window.onunload = afterInput;
  
  function afterInput() {
    try {
      top.opener.afterInput();
    }
    catch(e) {}
  }
  
  var mainPolNo = "";
  var mainRisk = "";
  //��ѯɨ��ͼƬ������
  function queryScanType() {
//     var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
	   var mySql=new SqlClass();
		 mySql.setResourceName("uw.ImageQuerySql"); //ָ��ʹ�õ�properties�ļ���
		 mySql.setSqlId("ImageQuerySql1");//ָ��ʹ�õ�Sql��id
		 var strSql = mySql.getString();
    var arrResult=easyExecSql(strSql);
    //alert(arrResult);
    return arrResult;
  }  
</script>
</head>
<!--<frameset rows="0,0,0,65,*" frameborder="no" border="1" framespacing="0" cols="*"> -->
<frameset name="fraMain" rows="0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp"> 
	
	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,100%,*,0">	
		<!--�˵�����--> 
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../common/EasyScanQuery/EasyScanQuery.jsp?prtNo=<%=request.getParameter("ContNo")%>&SubType=TB1017&QueryType=1"> 
		<frame id="fraInterface" name="fraInterface" scrolling="auto" src="./ImageQuery.jsp?ContNo=<%=request.getParameter("ContNo")%>">
    	<!--��һ��ҳ������-->     
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset> 
</frameset>
<noframes>
	<body bgcolor="#ffffff">
	</body>
</noframes>
</html>
 
