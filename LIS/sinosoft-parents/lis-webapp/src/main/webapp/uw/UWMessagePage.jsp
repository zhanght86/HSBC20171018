<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：MessagePage.jsp
//程序功能：信息显示页面
//创建日期：2002-05-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//             欧阳晟   2002-05-10    修改
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<html>
<head>
<title>查询结果</title>
<link rel="stylesheet" type="text/css" href="../common/css/Project.css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<%
String SUCCESS = "S";	//成功
String FAILURE = "F";	//失败
String COMMON = "C";	//一般信息
String Picture = request.getParameter("picture");
String Content = StrTool.unicodeToGBK(request.getParameter("content"));

/*********************************************************************/

%>
</head>
<body class="interface">
<h1><center></center></h1>
<br>

	 <table class="common">
    	<TR  class= common>
          <TD  class= title>
            承保计划变更结论及加费原因
          </TD>
      </tr>
      <tr>

      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="UWIdea" id=UWIdea readonly cols="100%" rows="5" witdh=100% class="common" ><%=Content%></textarea></TD>
      </TR>
     </table>
	<center>
		<input type=button class="cssButton" style="float: left" id=butSubmit value="确 定" onclick="window.close();" tabIndex=0>
	</center>

<script language=JavaScript>
ini = new Date().getTime();
var pc = 0;

function load()
{
	pc += 1;
	lpc.style.width = pc + "%";
	time = setTimeout("load()",30);
	if (pc > 100)
	{
		pc=0;
	}
}

function loaded()
{
	fim = new Date().getTime();
	dif = fim - ini;
	ld.style.display = 'none';
	//body.style.backgroundColor = 'silver';
	q.innerHTML = dif/1000;
	page.style.display = '';
}

function Show()
{
	if (txt.style.display == "none")
	{
		txt.style.display = "";
	}
	else
	{
		txt.style.display = "none";
	}
}

try
{
	window.butSubmit.focus();
}
catch(e)
{}
</script>
</body>
</html>
