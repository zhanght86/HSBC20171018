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

<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./GrpCalBonusView.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./GrpCalBonusViewInit.jsp"%>
  <title>����ֺ��ѯ</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" >
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,pair);"></td>
				<td class= titleImg>�������ѯ������</td>
			</tr>
		</table>
        <Div  id= "pair" style= "display: ''" class="maxbox">
    <table  class= common>
      <TR  class= common>
				<TD  class= title5>������</TD> 
	      <TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear></TD>
      	<TD  class= title5>�ŵ�����</TD>
        <TD  class= input5><Input class="wid" class= common name=GrpContNo id=GrpContNo></TD></TR>
        <TR  class= common>
				<TD  class= title5>���ֱ���</TD>
        <TD  class= input5><Input class="wid" class=common name=RiskCode id=RiskCode></TD> 
        <TD  class= title5>��Ч����</TD>
	        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>  
      </TR>
      <TR  class= common>
          
		  		<TD  class= title5>��Чֹ��</TD>
	      	<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD  class= title5>�ֺ�״̬</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=State id=State CodeData="0|^0|������^1|�ŵ������������^2|�ŵ������������^3|���ָ����Ѽ���^4|���ָ����ѷ���" 
	        		ondblClick="showCodeListEx('State',[this],[0,1]);" onclick="showCodeListEx('State',[this],[0,1]);" onkeyup="showCodeListKeyEx('State',[this],[0,1]);"></TD>     		
			</TR>
    </table>
    </Div>
    <!--<INPUT VALUE="��     ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 
    <INPUT VALUE="��     ӡ" class=cssButton TYPE=button onclick="easyPrint();"> -->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="easyPrint();">��    ӡ</a>
    <br>
    <table>
    	<tr>
      	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>�ŵ��ֺ���Ϣ</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
    	<table  class= common>
     		<tr  class= common>
    	  	<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
				</tr>
  		</table>
        <center>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> </center>					
  	</div>
  	<p>
    <!--<INPUT VALUE="��ѯ�ŵ��¸���" class=cssButton TYPE=button onclick="queryGrpDetail();"> -->
    <a href="javascript:void(0);" class="button" onClick="queryGrpDetail();">��ѯ�ŵ��¸���</a>
  	<!--
    <table class= common align=center>
      <TR class= common>
        <TD  class= title>����ϼƷֺ��� </TD>
        <TD  class= input><Input class= common name=sumBonus></TD>
        <TD  class= input><INPUT VALUE="����" class=common TYPE=button onclick="calSum();"> </TD>
 			</TR> 
  	</table>
  	-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
