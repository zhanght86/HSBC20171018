<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
String tGrpContNo=request.getParameter("GrpContNo");
String tPrtNo=request.getParameter("PrtNo");
%>
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpPersonAgeInit.jsp"%>
  <SCRIPT src="GrpPersonAgeInput.js"></SCRIPT>
</head>

<body  onload="initForm();" >
  <form action="./GroupPolAgeSave.jsp" method=post name=fm target="fraSubmit">
<DIV id=DivLCImpart STYLE="display:''">
<!-- ��֪��Ϣ���֣��б� -->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
				</td>
				<td class= titleImg>
					����ֲ���Ϣ
				</td>
			</tr>
		</table>
		<div  id= "divLCImpart1" style= "display: ''">
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanPersonAgeGrid" >
						</span>
					</td>
				</tr>
			</table>
		</div>
</DIV>
    
	  <Div  id= "divButton" style= "display: ''" align= left >
	 	<!--INPUT class=cssButton VALUE="�� ѯ"  TYPE=button onclick="queryClick()"> 
	 	<INPUT class=cssButton VALUE="�� ��"  TYPE=button onclick="updateClick()"> 
	 	<INPUT class=cssButton VALUE="ɾ ��"  TYPE=button onclick="deleteClick()"--> 
	 	<INPUT class=cssButton VALUE="�� ��"  TYPE=button onclick="submitForm();"> 
	 	<input type=hidden id="fmtransact" name="fmtransact">
	 	<input type=hidden id="GrpContNo" name="GrpContNo" value="<%=tGrpContNo%>">
	 	<input type=hidden id="PrtNo" name="PrtNo" value="<%=tPrtNo%>">
	  </DIV>    
	</form>
	</body>
</html>
