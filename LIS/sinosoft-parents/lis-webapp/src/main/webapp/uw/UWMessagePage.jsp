<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�MessagePage.jsp
//�����ܣ���Ϣ��ʾҳ��
//�������ڣ�2002-05-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//             ŷ����   2002-05-10    �޸�
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<html>
<head>
<title>��ѯ���</title>
<link rel="stylesheet" type="text/css" href="../common/css/Project.css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<%
String SUCCESS = "S";	//�ɹ�
String FAILURE = "F";	//ʧ��
String COMMON = "C";	//һ����Ϣ
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
            �б��ƻ�������ۼ��ӷ�ԭ��
          </TD>
      </tr>
      <tr>

      <TD  class= input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="UWIdea" id=UWIdea readonly cols="100%" rows="5" witdh=100% class="common" ><%=Content%></textarea></TD>
      </TR>
     </table>
	<center>
		<input type=button class="cssButton" style="float: left" id=butSubmit value="ȷ ��" onclick="window.close();" tabIndex=0>
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
