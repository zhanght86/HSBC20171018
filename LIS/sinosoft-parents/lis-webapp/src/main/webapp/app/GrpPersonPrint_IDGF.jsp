<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("GrpPersonPrint_IDGF"," ���������" + tGI.ManageCom);
  loggerDebug("GrpPersonPrint_IDGF"," ��½������" + tGI.ComCode);
%>
<script>
  var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
  var comcode = "<%=tGI.ComCode%>";     //��¼��½����
</script>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpPersonPrint_IDGF.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpPersonPrintInit_IDGF.jsp"%>
  
  <title>��ӡ���屣������ƾ֤ </title>
  
</head>

<body  onload="initForm();" >
  <form action="./GrpPersonPrintSave_IDGF.jsp" method=post name=fm id="fm" target="fraSubmit">
  
    <!-- ���屣����Ϣ���� -->
<TABLE>
    <TR>
        <TD class= common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
        </TD>
        <TD class = titleImg>
            �������ŵ���ѯ������
        </TD>
    </TR>
</TABLE>	
<div class="maxbox1">
<Div id= "divLCPolContion" style= "display: ''">		    
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>
            ���屣����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title5>
            ӡˢ����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PrtNo id="PrtNo">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ���˱�����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ContNo id="ContNo">
          </TD>
        </TR>
</table> 
</div>
</div>
<a href="javascript:void(0)" class=button onclick="queryGrpPol();">��ѯ�ŵ�</a>
<a href="javascript:void(0)" class=button onclick="queryGrpPerson();">��ѯ�ŵ��¸�����ϸ</a>
<!--     <INPUT VALUE="��ѯ�ŵ�" class= cssButton TYPE=button onclick="queryGrpPol();">
    <INPUT VALUE="��ѯ�ŵ��¸�����ϸ" class= cssButton TYPE=button onclick="queryGrpPerson();"> -->
    
    
   
    <Div  id= "divGrpPol" style= "display: none">
      <table  class= common>
        <tr>
          <td class= titleImg>���˱�����Ϣ</td>
    	</tr>
        <tr  class= common>
      	  <td text-align: left colSpan=1><span id="spanGrpPolGrid" ></span></td>
  	</tr>
      </table>
    
      <INPUT VALUE="��ӡ�ŵ�ȫ������ƾ֤" class= cssButton name='GrpPolButton' id="GrpPolButton" TYPE=button onclick="printGrpPol();">
    </Div>
    
    <Div  id= "divGrpPerson" style= "display: none">
      <table  class= common>
        <tr>
          <td class= titleImg>���˱�����Ϣ</td>
    	</tr>
        <tr  class= common>
      	  <td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
  	</tr>
      </table>
      
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();">
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();">
      <table  class= common>
        <tr>
          <td><INPUT VALUE="��ӡѡ���ŵ�����ƾ֤" class= cssButton name='GrpPersonButton' id="GrpPersonButton" TYPE=button onclick="printGrpPerson();"> </td>
    	</tr>
      </table>
      
    </Div>
    
    <input type=hidden id="fmtransact" name="fmtransact">

  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html> 
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
</script>
