

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CommandMain.jsp
//�����ܣ�Υ����������
//�������ڣ�2008-9-17
//������  ��
//���¼�¼��  
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="CountQuery.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <%@include file="CountQueryInit.jsp"%>

</head>
<body  onload="initForm('2');initElementtype();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact id="Transact">
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>Υ��������ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		 <td class=title5>����</td>
          <td class=input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ManageCom id=ManageCom verify="�������|code:comcode"  
                   ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);"
                   onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" 
                   onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input name=ManageComName id=ManageComName class='codename' readonly=true >
           </td>  
			
       <TD class=title5>ҵ��ģ��</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);"
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onChange="return Clean();" readonly=true>
	    </TD> 
	</TR>
	<TR class=common>
	<TD class=title5>
                          ��ʼ���ڣ�
        </TD>	  
      
         <TD  class= input5>
          <!--<Input name=RuleStartDate class='multiDatePicker'  verify="��ʼ����|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
		<TD class=title5>
                         �������ڣ�
        </TD>	  
         <TD  class= input5>
          <!--<Input name=RuleEndDate class='multiDatePicker'  verify="��ʼ����|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
	</TR>
<TR>
</Table>
</Div>
  <p>
  <Div id= DivSelect style="display: ''">
     <!--<INPUT VALUE="��    ѯ" TYPE=button class="cssButton" onclick="BreakQuery()">
     <input class=cssButton type="submit"  value='�鿴��ϸ' onclick="BreakDetailQuery()"> -->  
     <br>
     <a href="javascript:void(0);" class="button" onClick="BreakQuery();">��    ѯ</a>
     <a href="javascript:void(0);" class="button" onClick="BreakDetailQuery();">�鿴��ϸ</a> <br>  
  </Div>

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	Υ�������б�
      </td>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	        <span id="spanBreakCountGrid" ></span>
		</td>
		<TD class=input style="display: none">						
		</TD>
	</tr>

     </table></Div>
       <!-- <INPUT VALUE="��  ҳ" TYPE=button  class="cssButton" onclick="turnPage.firstPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton" onclick="turnPage.previousPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton" onclick="turnPage.nextPage()">
        <INPUT VALUE="β  ҳ" TYPE=button  class="cssButton" onclick="turnPage.lastPage()">-->
   
     <!--<input class=cssButton type="submit" name='ToExcel' value='����EXCEL' onclick="Break_To_Excel()"> -->
     <br>
     <a href="javascript:void(0);" name='ToExcel' class="button" onClick="Break_To_Excel();">����EXCEL</a>
`	 
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
