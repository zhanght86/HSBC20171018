<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpCalPartBonus.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<%	
    String cGrpContNo = request.getParameter("GrpContNo");
    String cFiscalYear = request.getParameter("FiscalYear");
    String cRiskCode = request.getParameter("RiskCode");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./GrpCalPartBonus.js"></SCRIPT>
	<%@include file="./GrpCalPartBonusInit.jsp"%>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ѯ����</title>
</head>

<body onload="initForm()">
  <form method=post name=fm id=fm target="fraSubmit" action= "./GrpCalPartBonusSave.jsp">
  <div class="maxbox1">
    <table  class= common>
			<TR  class= common>
				<TD  class= title>������</TD> 
        <TD  class= input><Input class="wid" class=readonly readonly name=FiscalYear id=FiscalYear value=<%=cFiscalYear%>></TD>
        <TD  class= title>�ŵ�����</TD>
        <TD  class= input><Input class="wid" class=readonly readonly name=GrpContNo id=GrpContNo value=<%=cGrpContNo%>></TD>
				<TD  class= title>���˱�����</TD> 
        <TD  class= input><Input class="wid" class=common name=ContNo id=ContNo></TD>
			</TR>
			<TR  class= common>
        <TD  class= title>��Ч����</TD>
        <TD  class= input><!--<Input class="coolDatePicker" dateFormat="short" name=BDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>  
	  		<TD  class= title>��Чֹ��</TD>
      	<TD  class= input><!--<Input class="coolDatePicker" dateFormat="short" name=EDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
				<TD  class= title>�ֺ�״̬</TD>
        <TD  class= input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ComputeState id=ComputeState CodeData="0|^0|����������^3|�ŵ����ֺ�������^4|�ŵ����ֺ�������" ondblclick="return showCodeListEx('ComputeState',[this]);" onclick="return showCodeListEx('ComputeState',[this]);" onkeyup="return showCodeListKeyEx('ComputeState',[this]);"></TD>
			</TR>
    </table></div>
  <!--<p>
   <INPUT VALUE="��    ѯ" TYPE=button class=cssButton onclick="easyQueryClick();"> 	
  </p>-->
 <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a><br><br>
	<Div  id= "divBonusGrid" style= "display: ''">
  	<table  class= common>
   		<tr  class= common>
	  		<td text-align: left colSpan=1><span id="spanBonusGrid" ></span> </td>
			</tr>
  	</table>
    <center>
    <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
    <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
    <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
    <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>				
	</div>  
  <p>
  <!--<INPUT VALUE="�������" class=cssButton TYPE=button name=btnSubmit onclick = "submitForm();">-->
  <a href="javascript:void(0);" name=btnSubmit class="button" onClick="submitForm();">�������</a> 
	<INPUT type=hidden id="RiskCode" name="RiskCode" value=<%=cRiskCode%>>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
