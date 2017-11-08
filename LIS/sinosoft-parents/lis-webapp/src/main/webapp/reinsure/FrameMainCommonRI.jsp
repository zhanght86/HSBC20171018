<%@include file="/i18n/language.jsp"%>
<%
//程序名称：
//程序功能：显示分保信息
//创建日期：
//创建人  ：
//更新记录：
//更新人：
//更新日期:
//更新原因/内容:
%>
<!--Root="../../" -->
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
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
  //图片的队列数组
  var pic_name = new Array();
  var pic_place = 0;
  var b_img	= 0;  //放大图片的次数
  var s_img = 0;	//缩小图片的次数
  
  window.onunload = afterInput;
  
  function afterInput() {
    try {
      top.opener.afterInput();
    }
    catch(e) {}
  }
  
  var mainPolNo = "";
  var mainRisk = "";
  //查询扫描图片的描述
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
<!--标题与状态区域-->
	<!--保存客户端变量的区域，该区域必须有-->
	<frame name="VD" src="../common/cvar/CVarData.jsp">
	<!--扫描件-->
    <frameset name="SMJSet" cols="100%,*" frameborder="no" border="0" framespacing="0" rows="*">
    <frame name="fraPic" src="about:blank">
    </frameset>
	<!--保存客户端变量和WebServer实现交户的区域，该区域必须有-->
	<frame name="EX" src="../common/cvar/CExec.jsp">

	<frame name="fraSubmit"  scrolling="yes" noresize src="about:blank" >
	<frame name="fraTitle"  scrolling="no" noresize src="about:blank" >
	<frameset name="fraSet" cols="0%,*,0%" frameborder="no" border="0" framespacing="0" rows="*">
		<!--菜单区域-->
		<frame name="fraMenu" scrolling="yes" noresize src="about:blank">
		<!--交互区域-->
		<%
			String szSrc = request.getParameter("Interface");
		    String ArithmeticDefID = request.getParameter("ArithmeticDefID");
		    String RISolType = request.getParameter("RISolType");		    
		    
  		    String ArithmeticDefName=(request.getParameter("ArithmeticDefName")+"").trim();
  		    String RISolTypeName = request.getParameter("RISolTypeName");
  		  
		    ArithmeticDefName=new String((ArithmeticDefName).getBytes("ISO-8859-1"),"UTF-8");
		    RISolTypeName=new String((RISolTypeName).getBytes("ISO-8859-1"),"UTF-8");
		    
		    String str=szSrc+"?ArithmeticDefID="+ArithmeticDefID+"&ArithmeticDefName="+ArithmeticDefName+"&RISolType="+RISolType+"&RISolTypeName="+RISolTypeName;
		%>
	<frame id="fraInterface" name="fraInterface" scrolling="auto" src="<%= str %>">
    	<!--下一步页面区域-->
    	<frame id="fraNext" name="fraNext" scrolling="auto" src="about:blank">
	</frameset>
</frameset>
<noframes>
	<body bgcolor="#ffffff" >
	</body>
</noframes>
</html>
