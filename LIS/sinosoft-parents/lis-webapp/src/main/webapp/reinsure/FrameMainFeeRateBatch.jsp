<%@include file="/i18n/language.jsp"%>


<%
//�������ƣ�
//�����ܣ���ʾ���ʱ����ζ���
//�������ڣ�2008-06-02
//������  ���ű�
//���¼�¼��
//�����ˣ�
//��������:
//����ԭ��/����:
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <% 
    String reContCode = "";
    reContCode = request.getParameter("ReContCode");
   %>
<html>
<head>
<title></title>
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script type="text/javascript">
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
	window.focus();
	
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
    var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    return easyExecSql(strSql);
  } 
function focusMe()
{
  window.focus();
}
</script>
</head>
<frameset name="fraMain" rows="0,0,0,0,0,*" frameborder="no" border="1" framespacing="0" cols="*">
<!--������״̬����-->
	<!--����ͻ��˱��������򣬸����������-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<!--ɨ���-->
    <frameset name="SMJSet" cols="100%,*" frameborder="no" border="0" framespacing="0" rows="*">
    <frame name="fraPic" src="about:blank">
    </frameset>
	<!--����ͻ��˱�����WebServerʵ�ֽ��������򣬸����������-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="0" framespacing="0" rows="*">
		<!--�˵�����-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--��������-->
		<%
			String szSrc = request.getParameter("Interface");
      		szSrc += "?FeeTableNo=" + request.getParameter("FeeTableNo");
		%>
<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= szSrc %>">
    	<!--��һ��ҳ������-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" >
	</body>
</noframes>
</html>
