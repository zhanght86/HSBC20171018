<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//��½��Ϣ
	GlobalInput tGI = new GlobalInput();
	if (session.getValue("GI")==null)
	{
		loggerDebug("RiskAddFeeQuery","null");
	}
	else
	{
		tGI = (GlobalInput)session.getValue("GI");
	}
	String tContNo = request.getParameter("ContNo");
	String tInsuredNo = request.getParameter("InsuredNo");
	
%>
<script>
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
	var tContNo = "<%=tContNo%>";
	var tInsuredNo = "<%=tInsuredNo%>";
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RiskAddFeeQueryInit.jsp"%>
	<title>�ӷѲ�ѯ</title>
</head>

<body  onload="initForm(tContNo,tInsuredNo);" >
<form action="" method=post name=fm id=fm target="fraSubmit">
	<!--#################### ������Ϣ��ѯ�������� ##############################-->
	<Table class= common border=0 width=100%>
		<TR>
		    <td class=common>
  			<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
  			</td>
			<td class=titleImg align= center>�ӷ���Ϣ��</td>
		</TR>
	</Table>
	
	
	<Div  id= "divBalPol" style= "display:  " align = center>
		<Table  class= common>
			<TR  class= common>
				<td style="text-align: left" colSpan=1>
					<span id="spanAddFeeGrid" ></span> 
				</td>
			</TR>
		</Table>
		<INPUT CLASS=cssButton90 VALUE="��  ҳ" class= cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT CLASS=cssButton92 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="β  ҳ" class= cssButton TYPE=button onclick="turnPage.lastPage();">
	</Div>
	<br>
<Div  id= "divBalPol2" style= "display: none" align = center>
	<table class="common">
    	<TR  class=common>
          <TD  class= title>
            �ӷ�ԭ��
          </TD>
          <tr class=common>
			<TD  class= input> <textarea name="AddReason" id=AddReason cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		  </tr>
      </TR> 
</table>
</Div>
	<INPUT type="hidden" name="BalNo" id=BalNo value="">
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
