<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  loggerDebug("LogSubject","LogSubject.jsp");
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
	<SCRIPT src="LogSubject.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LogSubjectInit.jsp"%>

</head>
<body  onload="initForm();initElementtype();" >
  <form action="LogSubjectSave.jsp" method=post name=fm  id=fm target="fraSubmit">	
  
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivQuery);">
    		</TD>
    		<TD class= titleImg>
    			 ��־������Ϣ��
    		</TD>
    	</TR>
	</Table> 
	<Div id= "DivQuery" style= "display: ''" >
      <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>��־����</TD>
          <TD  class= input5><input class="common wid" name= LogSubjectID ></TD>
          <TD  class= title5>��������</TD>
          <TD  class= input5><input class="common wid" name= LogSubjectDes></TD>
          </TR>
        <TR  class= common>
          <TD  class= title5>��ز���</TD>
          <TD  class= input5 ><Input class="codeno" name=LogDept  id=LogDept 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('logdept',[this,LogDeptName],[0,1],null,null,null,0,208);"
          onDblClick="showCodeList('logdept',[this,LogDeptName],[0,1],null,null,null,0,208);"
          onkeyup="showCodeListKey('logdept',[this,LogDeptName],[0,1],null,null,null,0,208);"><input class=codename name=LogDeptName readonly=true></TD>
        
          <TD  class= title5>����ҵ��ģ��</TD>
          <TD  class= input5 ><Input class="codeno" name=LogServiceModule  id=LogServiceModule
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('logservicemodule',[this,LogServiceModuleName],[0,1],null,null,null,0,208);"
          onDblClick="showCodeList('logservicemodule',[this,LogServiceModuleName],[0,1],null,null,null,0,208);"
          onkeyup="showCodeListKey('logservicemodule',[this,LogServiceModuleName],[0,1],null,null,null,0,208);"><input class=codename name=LogServiceModuleName readonly=true></TD>
          </TR>
        <TR  class= common>
          <TD  class= title5>ҵ���ܱ���</TD>
          <TD  class= input5><input class=codeno name=TaskCode id=TaskCode  
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
          onclick="return showCodeList('taskcode',[this,TaskCodeName],[0,1],null,null,null,1,'208');"
          onDblClick="return showCodeList('taskcode',[this,TaskCodeName],[0,1],null,null,null,1,'208');"
           onKeyUp="return showCodeListKey('taskcode',[this,TaskCodeName],[0,1],null,null,null,1,'208');" ><input class=codename name=TaskCodeName readonly=true ></td>
          <TD  class= title5>��־����</TD>
          <TD  class= input5 ><Input class="codeno" name=LogType  id=LogType
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
          onclick="showCodeList('logtype',[this,LogTypeName],[0,1],null,null,null,0,208);"
          onDblClick="showCodeList('logtype',[this,LogTypeName],[0,1],null,null,null,0,208);"
          onkeyup="showCodeListKey('logtype',[this,LogTypeName],[0,1],null,null,null,0,208);"><input class=codename name=LogTypeName readonly=true></TD>
        </TR> 
        <TR  class= common>
          <TD  class= title5>��ؿ���</TD>
          <TD  class= input5><Input class=codeno name= Switch CodeData='0|^Y|��^N|��' value=6  id= Switch
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
          onclick="return showCodeListEx('Switch',[this,SwitchName],[0,1]);"
          onDblClick="return showCodeListEx('Switch',[this,SwitchName],[0,1]);"
           onKeyUp="return showCodeListKeyEx('Switch',[this,SwitchName],[0,1]);"><input class=codename name=SwitchName readonly=true ></TD>
         
         
        </TR> 
	</Table>
	<table>
      <tr>
        <td><input type="button" class=cssButton value="&nbsp;��  ѯ&nbsp;" onClick="LogQuery()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onClick="LogReset()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onClick="LogSubmit()"></td>
        <td><input type="button" class=cssButton value="&nbsp;ɾ  ��&nbsp;" onClick="LogDelete()"></td>
        <td><input type="button" class=cssButton value="&nbsp;��  ��&nbsp;" onClick="LogUpdate()"></td>
      </tr>
	</table>
    <br>
    <!-- <a href="javascript:void(0);" class="button"onClick="LogQuery()">��   ѯ</a>
    <a href="javascript:void(0);" class="button"onClick="LogReset()">��   ��</a>
    <a href="javascript:void(0);" class="button"onClick="LogSubmit()">��   ��</a>
    <a href="javascript:void(0);" class="button"onClick="LogDelete()">ɾ   ��</a>
    <a href="javascript:void(0);" class="button"onClick="LogUpdate()">��   ��</a> -->
	</div>
    </div>
 
	<Div  id= "divLogSubject" style= "display: ''">
	<Table>
   	<TR>
       	<TD class=common>
           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLogSubjectGrid);">
   		</TD>
   		<TD class= titleImg>��־����</TD>
   	</TR>
    </Table>
    <Div  id= "divLogSubjectGrid" style= "display: ''"  align=center>
	<Table  class= common >
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanLogSubjectGrid" ></span> 
		</TD>
      </TR>
	</Table>					
        <!--<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onClick="turnPage.firstPage();"> 
	 	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onClick="turnPage.lastPage();"> 	-->	
	</Div>
	</Div>
  
	
    <!--<input type="button" class=cssButton value="���Ƶ���Ϣ¼��" onClick="LogToItem()">-->
    <a href="javascript:void(0);" class="button"onClick="LogToItem()">���Ƶ���Ϣ¼��</a>
	<input type=hidden id="Flag" name="Flag">
	<input type=hidden id="Transact" name="Transact">
	<br><br><br><br>
</form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
