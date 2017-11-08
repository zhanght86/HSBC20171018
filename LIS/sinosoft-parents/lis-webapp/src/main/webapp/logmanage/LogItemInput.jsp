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
    			 日志控制点信息：
    		</TD>
    	</TR>
	</Table> 
  	<Div id= "DivLogItem" style= "display: ''" >
     <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
      	  <TD  class= title>主题ID </TD>
          <TD  class= input><input class=common name= SubIDForItem readonly></TD>
        </TR> 
	</Table>
	<!--table>
      <tr>
        <td><input type="button" class=cssButton value="&nbsp;查  询&nbsp;" onclick="ItemQuery()"></td>
        <td><input type="button" class=cssButton value="&nbsp;重  置&nbsp;" onclick="ItemReset()"></td>
        <td width="5%">&nbsp;&nbsp;</td>
        <td><input type="button" class=cssButton value="&nbsp;增  加&nbsp;" onclick="ItemSubmit()"></td>
        <td><input type="button" class=cssButton value="&nbsp;删  除&nbsp;" onclick="ItemDelete()"></td>
        <td><input type="button" class=cssButton value="&nbsp;修  改&nbsp;" onclick="ItemUpdate()"></td>
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
   		<TD class= titleImg>日志控制点</TD>
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
        <!--<INPUT VALUE="首  页" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="上一页" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	-->	
	</Div>
	</Div>
	
  
<!--table class=common>  
   <TR  class= common>
          <TD  class= title>关键号码类型</TD>
          <TD  class= input ><Input class="codeno" name=KeyType ondblclick="showCodeList('logkeytype',[this,KeyTypeName],[0,1],null,null,null,0,208);"onkeyup="showCodeListKey('logkeytype',[this,KeyTypeName],[0,1],null,null,null,0,208);"><input class=codename name=KeyTypeName readonly=true></TD>
	        <TD  class= title>监控开关</TD>
          <TD  class= input><Input class=codeno name= ItemSwitch readonly CodeData='0|^Y|开^N|关' value=6 ondblclick="return showCodeListEx('ItemSwitch',[this,ItemSwitchName],[0,1]);" onkeyup="return showCodeListKeyEx('ItemSwitch',[this,ItemSwitchName],[0,1]);"><input class=codename name=ItemSwitchName readonly=true ></TD>

	 </TR>
</table-->
    <!--<input type="button" class=cssButton value="保 存" onClick="AddItem()">-->
    <!--input type="button" class=cssButton value="删除控制点从主题" onclick="DelItem()"-->
   <!-- <input type="button" class=cssButton value="返 回" onClick="returnParent()">-->
   <a href="javascript:void(0);" class="button"onClick="AddItem()">保   存</a>
   <a href="javascript:void(0);" class="button" onClick="returnParent()">返   回</a>
	<input type=hidden id="Flag" name="Flag">
	<input type=hidden id="Transact" name="Transact">
	
</form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
