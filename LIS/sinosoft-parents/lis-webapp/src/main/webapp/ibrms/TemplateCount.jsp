
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CommandMain.jsp
//�����ܣ�ģ���ѯ
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
<body  onload="initForm('1');initElementtype();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>�����ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>������</TD>
		<TD class=input5><Input class="wid" class=common name=RuleName id=RuleName ></TD>
			
		<TD class=title5>����</TD>
		<TD class=input5><Input class="wid" class=common name=description id=description></TD>	</TR>
        
		<TR class=common>
       <TD class=title5>ҵ��ģ��</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);"
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
        <TD class=title5>
                          ��Ч���ڣ�
        </TD>	  
      
         <TD  class= input5>
          <!--<Input name=RuleStartDate class='multiDatePicker'  verify="��ʼ����|Date" >-->
           <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
	</TR>
	<TR class=common>
	
		<TD class=title5>
                         ʧЧ���ڣ�
        </TD>	  
         <TD  class= input5>
          <!--<Input name=RuleEndDate class='multiDatePicker'  verify="��ʼ����|Date" >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
         <TD class=title5>
                         ״̬��
        </TD>	  
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IbrmsState id=IbrmsState class="codeno" 
	          ondblclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);" 
              onclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsstate',[this,ibrmsstateName],[0,1]); " ><input name=ibrmsstateName id=ibrmsstateName  class='codename' onchange="return Clean();" >
	        </TD> 
	</TR>
<TR>
</Table>
</Div>
  <p>
  <Div id= DivSelect style="display: ''">
     <!--<INPUT VALUE="��    ѯ" TYPE=button class="cssButton" onclick="TemplateQuery()">
     <input class=cssButton type=button  value='�鿴��ϸ' onclick="RuleDetails()">  -->  <br>
     <a href="javascript:void(0);" class="button" onClick="TemplateQuery();">��    ѯ</a>
     <a href="javascript:void(0);" class="button" onClick="RuleDetails();">�鿴��ϸ</a> <br>
  </Div>

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	�����б�
      </td>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	        <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">						
		</TD>
	</tr>

     </table></Div>
    <!-- <center>
        <INPUT VALUE="��  ҳ" TYPE=button  class="cssButton90" onclick="turnPage.firstPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton91" onclick="turnPage.previousPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton92" onclick="turnPage.nextPage()">
        <INPUT VALUE="β  ҳ" TYPE=button  class="cssButton93" onclick="turnPage.lastPage()"></center> -->
     <!--<input class=cssButton type="submit" name='ToExcel' value='����EXCEL' onclick="analyse_data_pol()"> --><br>
     <a href="javascript:void(0);" name='ToExcel' class="button" onClick="analyse_data_pol();">����EXCEL</a>
</div>
<input type="hidden" name="flag" value="11">
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
