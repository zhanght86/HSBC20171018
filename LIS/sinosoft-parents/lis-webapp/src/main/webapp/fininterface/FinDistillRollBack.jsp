<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
 String CurrentDate = PubFun.getCurrentDate();
%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>    
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FinDistillRollBack.js"></SCRIPT>
  <%@include file="FinDistillRollBackInit.jsp"%>
  <title>����ӿ���ȡ����</title>
</head>
<body onload="initForm();initElementtype();">
  <form action= "./FinRollBack.jsp" method=post name=fm id=fm target="fraSubmit"> 
   	
      <table class= common border=0 width=100%>
          <tr>
          		<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSe);"></td>
                <td class= titleImg align= center>���������ڣ�</td>
          </tr>
      </table>
      <Div id="divSe" style="display: ''"><div class="maxbox1">
   <table  class= common align=center>
      	
      	<TR  class= common>
          <TD  class= title5>
            ��ʼ����
          </TD>
          <TD  class= input5>
            <Input class="wid" onClick="laydate({elem: '#Bdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" elementtype = nacessary name=Bdate id="Bdate"><span class="icon"><a onClick="laydate({elem: '#Bdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            ��ֹ����
          </TD>
          <TD  class= input5>
            <Input class="wid" onClick="laydate({elem: '#Edate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Edate id="Edate"><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>

    
    </table>
    </div>
    </Div>
    <br />
   <!-- &nbsp;&nbsp;<INPUT  class=cssButton VALUE="��ѯ��������" TYPE=Button onclick="easyQueryClick();">
    &nbsp;&nbsp;<INPUT  class=cssButton VALUE="�������ݻ���" TYPE=Button onclick="rollBack();">-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��ѯ��������</a>
    <a href="javascript:void(0);" class="button" onClick="rollBack();">�������ݻ���</a><br><br>
    <Div  id= "divLCGrp1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanRBResultGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    <p></p>
      <center><INPUT VALUE="��  ҳ"
        class =  cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ"  class =  cssButton93 TYPE=button onclick="getLastPage();"> 	</center>				
  	</div>
   </table> 
  	
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>


