<%@page contentType="text/html;charset=GBK" %> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
String tGrpContNo =  request.getParameter("GrpContNo");
String tRptNo = request.getParameter("RptNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT> 

<SCRIPT src="./LLGrpClaimEdorTypeCAInput.js"></SCRIPT>
<%@include file="LLGrpClaimEdorTypeCAInit.jsp"%>
</head>
<body  onload="initForm();" >
<form action="./LLGrpClaimEdorTypeCASave.jsp" method=post name=fm target="fraSubmit">	
<!----------    ҳͷ��Ϣ    ---------->
<br><br>
<!----------    ���ת��    ---------->
<DIV  id= "DivMoney" style= "display: ''">
<TABLE  class= common>
<TR class=common>
<TD class=title>�����˻����:&nbsp;&nbsp;
<INPUT  class=common type="text" name="AccTotal" readonly >
</TD>
</TR>
</TABLE>
</DIV>

<BR>
<br><br>
<TABLE class=common><TR class= common><TD class=titleImg>
<IMG OnClick= "showPage(this,DivLPAccMove);" src= "../common/images/butExpand.gif" style= "cursor:hand;">
ת���˻���Ϣ</TD></TR>
</TABLE> 
<DIV  id= "DivLPAccMove" style= "display: ''" align="center">
<TABLE  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanLPAccMoveGrid" ></span>
</td></tr><tr><td>
    <DIV  id= "DivMark3" style= "display: ''" >
    <INPUT VALUE="��  ҳ" class="cssButton" type="button" onclick="turnPage.firstPage();"> 
    <INPUT VALUE="��һҳ" class="cssButton" type="button" onclick="turnPage.previousPage();"> 					
    <INPUT VALUE="��һҳ" class="cssButton" type="button" onclick="turnPage.nextPage();"> 
    <INPUT VALUE="β  ҳ" class="cssButton" type="button" onclick="turnPage.lastPage();">  
    </DIV>
</td></tr>
</TABLE>
</DIV>
<br><br>
<table>
<tr>
	<td><INPUT class=cssButton type=Button value="����ȷ��" name="Move" onclick="MoveRecord();"> </td>
	<td></td>
	<td><INPUT class=cssButton type=Button value="��  ��" onclick="returnParent();"> </td>
</tr>
</table>
<Input type="hidden" class=common name=GrpContNo >
<Input type="hidden" class=common name=RptNo >
<Input type="hidden"  name=fmtransact >

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<script>
  window.focus();
</script>
</body>
</html>
