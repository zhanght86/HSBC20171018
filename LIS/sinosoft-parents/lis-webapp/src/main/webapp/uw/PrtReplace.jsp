
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
   loggerDebug("PrtReplace","�������-----"+tG.ComCode);
%>   

<script>
  var comCode = <%=tG.ComCode%>
  var tDisplay = <%=tDisplay%>
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="PrtReplace.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="PrtReplaceInit.jsp"%>
  
  <title>ӡˢ���滻 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./PrtReplaceSave.jsp">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></td>
			<td class= titleImg>��������˱�����ѯ������</td>
		</tr>
	</table>
    <Div  id= "air" style= "display: ''" class="maxbox">
    <table  class= common>
      	<TR  class= common>
      	  <TD  class= title>�������� </TD>
          <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo > </TD>
          <TD  class= title> ���屣������</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= "code" name=GrpContNo id=GrpContNo CodeData="0|^00000000000000000000|�Ǽ��嵥�µĸ��˵�" ondblclick="showCodeListEx('GrpPolNo',[this],[0]);" onclick="showCodeListEx('GrpPolNo',[this],[0]);" onkeyup="showCodeListKeyEx('GrpPolNo',[this],[0]);"> </TD><TD  class= title> ӡˢ����</TD>
          <TD  class= input> <Input class="wid" class= common name=OldPrtNo id=OldPrtNo ></TD></TR>
          <TR  class= common>
          
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=ManageCom id=ManageCom ondblclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onclick="return showCodeList('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');" onkeyup="return showCodeListKey('ComCode',[this],null,null,'1 and length(comcode)>=#4#','1');"> </TD>
          <TD  class= title> ���ֱ���</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);"></TD>
          <TD  class= title> �����˱���</TD>
          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=AgentCode id=AgentCode verify="�����˱���|notnull&code:AgentCode" ondblclick="return queryAgent(comCode);" onclick="return queryAgent(comCode);" onkeyup="return queryAgent(comCode);"></TD>
        </TR>
       
        
          
          <!-- 
          <TD  class= title> ��������� </TD>
          <TD  class= input> <Input class=common name=AgentGroup verify="���������|notnull&len<=12" > </TD>
           -->
       
        <TR  class= common>          
          <TD  class= title> Ͷ�������� </TD>
          <TD  class= input> <Input class="wid" class= common name=AppntName id=AppntName > </TD>
          <TD  class= title> �����˿ͻ��� </TD>
          <TD  class= input><Input class="wid" class= common name=InsuredNo id=InsuredNo ></TD>
          <TD  class= title> ���������� </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredName id=InsuredName > </TD></TR>
          	          
    </table> </Div>  
      <!--<INPUT VALUE=" ��  ѯ " class = cssButton TYPE=button onclick="easyQueryClick();">--> 
      <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a> <br><br>
      <div class="maxbox1"> 
    <table class= common>
    	<TR class= common>
        		<TD  class= title>��ӡˢ��</TD>
         		<TD  class= input><Input class="wid" class= common name=NewPrtNo id=NewPrtNo ></TD>
        	<TD  class= title></TD>
            <TD  class= input></TD>
            <TD  class= title></TD>
            <TD  class= input></TD>
        </TR>            
    </table></div>
    <!--<INPUT VALUE="ӡˢ���滻" class = cssButton TYPE=button onclick="PrtReplace();">--> 
    <a href="javascript:void(0);" class="button" onClick="PrtReplace();">ӡˢ���滻</a>
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
     <Div  id= "divPage" align=center >
      <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onclick="turnPage.lastPage();">
     </Div>
  	</div>
  	<Input class= common  type=hidden id="OldPrtNoHide" name=OldPrtNoHide value="">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
