
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html> 
<%
  
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="CountPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CountPrintInit.jsp"%>
  <title>��ӡ���� </title>
</head>
<body  onload="initForm();" >
  <form  action = './CountPrintSave.jsp' method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�����뱣����ѯ������</td>
		  </tr>
	</table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>    ��������  </TD>
          <TD  class= input5> 	<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=SaleChnl verify="��������|code:SaleChnl" onclick="return showCodeList('SaleChnl',[this]);" ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);"> </TD>
          <TD  class= title5>  ��ͬ��   </TD>
          <TD  class= input5>  <Input class= "common wid" name=ContNo > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  ӡˢ���� </TD>
          <TD  class= input5>  <Input class= "common wid" name=PrtNo >  </TD>
          <TD  class= title5>   ���ֱ���     </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=RiskCode onClick="return showCodeList('RiskCode',[this]);" onDblClick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">  </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> �������  </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"><font size=1 color="#ff0000"><b>*</b></font>  </TD>
          <TD  class= title5> �����˱���  </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode onclick="return queryAgent(ComCode);" ondblclick="return queryAgent(ComCode);" onkeyup="return queryAgent(ComCode);"></TD> 
        </TR>
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��ѯ����</a>
  <a href="javascript:void(0)" class=button onclick="printPol();">��ӡ����</a>
          <!-- <INPUT VALUE="��ѯ����" class= cssButton TYPE=button onclick="easyQueryClick();"> 
      	  <INPUT VALUE="��ӡ����" class= cssButton TYPE=button onclick="printPol();"> --> 
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
      <INPUT VALUE="��ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
	<input type=hidden name="EasyPrintFlag" value="1">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
