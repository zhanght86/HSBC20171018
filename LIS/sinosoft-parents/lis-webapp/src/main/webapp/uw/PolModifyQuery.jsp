
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%> 

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
   loggerDebug("PolModifyQuery","�������-----"+tG.ComCode);
%>   

<script>
  var comCode = <%=tG.ComCode%>
  var RiskSql = "1 and subriskFlag =#M# ";
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="PolModifyQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="PolModifyQueryInit.jsp"%>
  
  <title>������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>��������˱�����ѯ������</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title>�������� </TD>
          <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo > </TD>
          <TD  class= title> ���屣������</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= "code" name=GrpContNo id=GrpContNo CodeData="0|^00000000000000000000|�Ǽ��嵥�µĸ��˵�" ondblclick="showCodeListEx('GrpPolNo',[this],[0]);" onclick="showCodeListEx('GrpPolNo',[this],[0]);" onkeyup="showCodeListKeyEx('GrpPolNo',[this],[0]);"> </TD><TD  class= title> ӡˢ����</TD>
          <TD  class= input> <Input class="wid" class= common name=PrtNo id=PrtNo ></TD></TR>
          <TR  class= common>
          
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ManageCom id=ManageCom ondblclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onkeyup="return showCodeListKey('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');"> </TD>
           <td  class= title> ���ֱ��� </td>
          <td  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);"></td>
          <TD  class= title> �����˱���</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=AgentCode id=AgentCode verify="�����˱���|notnull&code:AgentCode" ondblclick="return queryAgent(comCode);" onclick="return queryAgent(comCode);" onkeyup="return queryAgent(comCode);"></TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> Ͷ�������� </TD>
          <TD  class= input> <Input class="wid" class= common name=AppntName id=AppntName > </TD>
          <TD  class= title> �����˿ͻ��� </TD>
          <TD  class= input><Input class="wid" class= common name=InsuredNo id=InsuredNo ></TD><TD  class= title> ���������� </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredName id=InsuredName > </TD></TR>
         
        <!-- <TR  class= common>
          <TD  class= title> �����˳������� </TD>
          <TD  class= input><Input class= common name=InsuredBirthday ></TD>
          <TD  class= title> ������Ч��:��ʼ </TD>
          <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=StartCValiDate > </TD>
          <TD  class= title> ������Ч��:��ֹ</TD>
          <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=EndCValiDate > </TD>
        </TR> -->
    </table></Div>
          <INPUT VALUE=" ��  ѯ " class = cssButton TYPE=button onclick="easyQueryClick();">
          <INPUT VALUE=" ���뱣���޸� " class = cssButton TYPE=button onclick="PolClick();">
          <INPUT VALUE=" ��  �� " name=Return class = cssButton TYPE=button STYLE="display:none" onclick="returnParent();">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
<center>
      <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
