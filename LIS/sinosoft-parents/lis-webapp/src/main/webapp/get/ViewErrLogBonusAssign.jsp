<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ViewErrLogBonusAssign.jsp
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

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ViewErrLogBonusAssign.js"></SCRIPT>
  	<%@include file="ViewErrLogBonusAssignInit.jsp"%>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ѯ����</title>
</head>
<body onload="initErrLogBonusGrid()">
  <form method=post name=fm id=fm target="fraSubmit" action= "">
    <table class=common>
		<td class=common><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img></td>
		<td class=titleImg>�������ѯ������</td>
	</table>
	<div class=maxbox1>
    <table  class= common align=center>
        <TR  class= common>
            <TD  class= title5>������</TD>
            <TD  class= input5><Input class="common wid" name=ContNo id=ContNo></TD>
			<TD  class= title5>��ȡ��ʽ	</TD>
	        <TD  class= input5><Input class="common wid" name=GetMode id=GetMode ></TD>                    
        </TR>
        <TR  class= common>
            <TD  class= title5>���ʱ��</TD>
            <TD  class= input5><Input class="common wid" name=MakeDate id=MakeDate></TD>   
			<TD  class= title5></TD>
			<TD  class= input5></TD>
        </TR>
    </table>
	</div>
  <p>
   <INPUT VALUE="��    ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 	
  </p>
 
  	<Div  id= "divErrLogBonus" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanErrLogBonusGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ"  class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ"  class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ"  class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>  
  
 </form>
 
 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
