<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�CutBonus.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpCalBonus.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpCalBonusInit.jsp"%>
  <title>����ֺ����</title>
</head>
<body  onload="initForm();initElementtype();">
  <form method=post name=fm id=fm target="fraSubmit" action= "./GrpCalBonusChk.jsp">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,jie);"></td>
				<td class= titleImg>�������ѯ������</td>
			</tr>
		</table>
        <Div  id= "jie" style= "display: ''" class="maxbox1">
    <table  class= common>
      <TR  class= common>
				<TD  class= title5>������</TD> 
        <TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear elementtype=nacessary></TD>
        <TD  class= title5>�ŵ�����</TD>
        <TD  class= input5><Input class="wid" class=common name=GrpContNo id=GrpContNo elementtype=nacessary></TD></TR>
        <TR  class= common>
				<TD  class= title5>���ֱ���</TD>
        <TD  class= input5><Input class="wid" class=common name=RiskCode id=RiskCode elementtype=nacessary></TD> 
        <TD  class= title5>��Ч����</TD>
        <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>  
			</TR> 
      <TR  class= common>
        
	  		<TD  class= title5>��Чֹ��</TD>
      	<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
				<TD  class= title5>�ֺ�״̬</TD>
        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ComputeState id=ComputeState CodeData="0|^0|����������^3|�ŵ����ֺ�������^4|�ŵ����ֺ�������" ondblclick="return showCodeListEx('ComputeState',[this]);" onclick="return showCodeListEx('ComputeState',[this]);" onkeyup="return showCodeListKeyEx('ComputeState',[this]);"></TD>
			</TR> 		 		  
    </table></Div>

  <!--  <INPUT VALUE="��    ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 
		<INPUT VALUE="�鿴�������������־��" class=cssButton TYPE=button onclick="viewErrLog();"> --><br>
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
        <a href="javascript:void(0);" class="button" onClick="viewErrLog();">�鿴�������������־��</a>	
		<!--INPUT VALUE="�鿴����������־��" class=cssButton TYPE=button onclick="viewErrLog();"--> 	
    <table>
    	<tr>
      	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>�ֺ�����</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align="center">
    	<table  class= common>
     		<tr  class= common>
  	  		<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();">
  	</div>
  	
  	<p>
	   <!-- <INPUT VALUE="�� �� �� ��" class=cssButton TYPE=button name=btnSubmit onclick = "submitForm();"> 
	    <INPUT VALUE="�����ŵ��¸���" class=cssButton TYPE=button name=btnForward onclick = "calGrpPart();">-->
        <a href="javascript:void(0);" class="button" name=btnSubmit onclick = "submitForm();">��������</a>
        <a href="javascript:void(0);" class="button" name=btnForward onclick = "calGrpPart();">�����ŵ��¸���</a> 
	    <INPUT name=opertype TYPE=hidden > 
	    <INPUT name=GrpContNo1 TYPE=hidden > 
	  </p>
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
