<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("LogComponentQuery","LogComponentQuery.jsp");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="LogComponentQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LogComponentQueryInit.jsp"%>

</head>
<body  onload="initForm();initElementtype();" >
  <form action="" method=post name=fm  id=fm target="fraSubmit">	
  
	<Div id= "DivLogSubject" style= "display: ''" >
	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivQuery);">
    		</TD>
    		<TD class= titleImg>
    			 ��־��ѯ��
    		</TD>
    	</TR>
	</Table> 
  <div id="DivQuery">
       <div class="maxbox1" >  
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>��־����</TD>
          <TD  class= input5><input class=common name= SubjectID></TD>
          <TD  class= title5>���Ƶ�</TD>
          
          <TD  class= input5><input class=common name= ItemID></TD>
          </TR>
          <TR  class= common>
          <TD  class= title5>�ؼ�����</TD>
          <TD  class= input5><input class=common name= KeyNO></TD>
        </TR>
	</Table>
	
    </div>
    </div>
   <!-- <table>
      <tr>
        <td><input type="button" class=cssButton value="&nbsp;��  ѯ&nbsp;" onClick="ClickQuery()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onClick="DateReset()"></td>
      </tr>
	</table>-->
    <a href="javascript:void(0);" class="button"onClick="ClickQuery()">��   ѯ</a>
    <a href="javascript:void(0);" class="button"onClick="DateReset()">��   ��</a>
	<br>
	</div>
 
	<Div  id= "divState" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogStateGrid);">
   		</TD>
   		<TD class= titleImg>״̬��־��Ϣ</TD>
   	</TR>
    </Table>
    <Div  id= "divLogStateGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogStateGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onClick="turnPage.lastPage();"> 		
	</Div>
	</Div>  
  <Div  id= "divTrack" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogTrackGrid);">
   		</TD>
   		<TD class= titleImg>�켣��־��Ϣ</TD>
   	</TR>
    </Table>
    <Div  id= "divLogTrackGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogTrackGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onClick="turnPage1.firstPage();"> 
	 	<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage1.previousPage();"> 					
		<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage1.nextPage();"> 
		<INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onClick="turnPage1.lastPage();"> 		
	</Div>
	</Div>
	<br><br><br><br>
</form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
