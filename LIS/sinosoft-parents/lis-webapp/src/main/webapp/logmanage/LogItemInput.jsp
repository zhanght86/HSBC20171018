<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //String tSubjectID = request.getParameter("SubjectID");
  //loggerDebug("LogItemInput",tSubjectID);
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
	<SCRIPT src="LogItemInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LogItemInputInit.jsp"%>

</head>
<body  onload="initForm();initElementtype();" >
  <form action="LogSubjectSave.jsp" method=post name=fm id=fm target="fraSubmit">	

	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLogItem);">
    		</TD>
    		<TD class= titleImg>
    			 ��־���Ƶ���Ϣ��
    		</TD>
    	</TR>
	</Table> 
  	<Div id= "DivLogItem" style= "display: ''" >
     <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
      	  <TD  class= title>����ID </TD>
          <TD  class= input><input class=common name= SubIDForItem readonly></TD>
        </TR> 
	</Table>
	<!--table>
      <tr>
        <td><input type="button" class=cssButton value="&nbsp;��  ѯ&nbsp;" onclick="ItemQuery()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onclick="ItemReset()"></td>
        <td width="5%">&nbsp;&nbsp;</td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onclick="ItemSubmit()"></td>
        <td><input type="button" class=cssButton value="&nbsp;ɾ  ��&nbsp;" onclick="ItemDelete()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onclick="ItemUpdate()"></td>
      </tr>
	</table-->
	<br>
	</div>
    </div>
 
	<Div  id= "divLogItemMain" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogItemGrid);">
   		</TD>
   		<TD class= titleImg>��־���Ƶ�</TD>
   	</TR>
    </Table>
    <Div  id= "divLogItemGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogItemGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <!--<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	-->	
	</Div>
	</Div>
	
  
<!--table class=common>  
   <TR  class= common>
          <TD  class= title>�ؼ���������</TD>
          <TD  class= input ><Input class="codeno" name=KeyType ondblclick="showCodeList('logkeytype',[this,KeyTypeName],[0,1],null,null,null,0,208);"onkeyup="showCodeListKey('logkeytype',[this,KeyTypeName],[0,1],null,null,null,0,208);"><input class=codename name=KeyTypeName readonly=true></TD>
	        <TD  class= title>��ؿ���</TD>
          <TD  class= input><Input class=codeno name= ItemSwitch readonly CodeData='0|^Y|��^N|��' value=6 ondblclick="return showCodeListEx('ItemSwitch',[this,ItemSwitchName],[0,1]);" onkeyup="return showCodeListKeyEx('ItemSwitch',[this,ItemSwitchName],[0,1]);"><input class=codename name=ItemSwitchName readonly=true ></TD>

	 </TR>
</table-->
    <!--<input type="button" class=cssButton value="�� ��" onClick="AddItem()">-->
    <!--input type="button" class=cssButton value="ɾ�����Ƶ������" onclick="DelItem()"-->
   <!-- <input type="button" class=cssButton value="�� ��" onClick="returnParent()">-->
   <a href="javascript:void(0);" class="button"onClick="AddItem()">��   ��</a>
   <a href="javascript:void(0);" class="button" onClick="returnParent()">��   ��</a>
	<input type=hidden id="Flag" name="Flag">
	<input type=hidden id="Transact" name="Transact">
	
</form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
