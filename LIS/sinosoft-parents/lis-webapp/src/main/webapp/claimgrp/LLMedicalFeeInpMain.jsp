<!--Root="../../" -->
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<title>ҽ�Ƶ�֤¼��</title>
<script language="javascript">

function focusMe()
{
    window.focus();
}
	var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.resizeTo(intPageWidth,intPageHeight);
	window.moveTo(-1, -1);
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
  function queryScanType() {
    var strSql = "select code1, codename, codealias from ldcode1 where codetype='scaninput'";
    
    return easyExecSql(strSql);
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
	<frameset name="fraSet" cols="*" frameborder="yes" border="1" framespacing="0" rows="0,*,0">
    <!--�˵�����-->
        <frame name="fraMenu" scrolling="yes" noresize src="about:blank">
        <!--��������-->
        <!--frame id="fraPic" name="fraPic" scrolling="auto" noresize src="../sys/ScanClaimQuerySave1.jsp?claimNo=<%=request.getParameter("claimNo")%>&scanType=4004&custNo=<%=request.getParameter("custNo")%>&EXT=.htm"-->
        <frame id="fraInterface" name="fraInterface" scrolling="auto" src="./LLMedicalFeeInpInput.jsp?claimNo=<%=request.getParameter("claimNo")%>&caseNo=<%=request.getParameter("caseNo")%>&custNo=<%=request.getParameter("custNo")%>&accDate1=<%=request.getParameter("accDate1")%>&accDate2=<%=request.getParameter("accDate2")%>">
        <!--��һ��ҳ������-->
        <frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#ffffff" onblur="focusMe();>
    </body>
</noframes>
</html>
