<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ͨ����ͳ��</title>

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
  <script src="../common/javascript/MultiCom.js"></script>

<script type="text/javascript" src="./ThPutSta.js"></script>
  <%@include file="ThPutStaInit.jsp"%>
  <style>
a.button{border:1;padding:5px 12px;CURSOR: hand;}
</style>
</head>
<body onload="initForm();initQueryGrpGrid();">
<form action="./ThPutStaSave.jsp" method=post name=fm id=fm target="fraSubmit">
<Table>
	<TR>
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,RuleBasicInfor);"></TD>
		<TD class=titleImg>������Ϣ¼��</TD>
	</TR>
</Table>
<div id="RuleBasicInfor" style="display:''" class="maxbox1">
<Table>
<TR class=common>
		 <td class=title5>����</td>
          <td class=input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ManageCom id=ManageCom verify="�������|code:comcode&NOTNULL"  
                   ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" 
                   onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,null,null,1);" 
                   onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,null,null,1);"><input name=ManageComName id=ManageComName class='codename' readonly=true elementtype=nacessary >
           </td>  
			
       <TD class=title5>ҵ��ģ��</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);"
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]);"><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
	</TR>
    <TR>
       <TD class=title5>
                          ��ʼ���ڣ�
        </TD>	  
		<TD class=input5>
           <!--<input class="multiDatePicker" dateFormat="short" name=RecordStartDate verify="��Ч����|NOTNULL&Date">-->
           <Input class="coolDatePicker" onClick="laydate({elem: '#RecordStartDate'});" verify="��Ч����|NOTNULL&Date" dateFormat="short" name=RecordStartDate id="RecordStartDate"><span class="icon"><a onClick="laydate({elem: '#RecordStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </input>
        </TD>
		<TD class=title5>
                         ��ֹ���ڣ�
        </TD>	  
		<TD class=input5>
           <!--<input class="multiDatePicker" dateFormat="short" name=RecordEndDate verify="ʧЧ����|NOTNULL&Date">-->
           <Input class="coolDatePicker" onClick="laydate({elem: '#RecordEndDate'});" verify="ʧЧ����|NOTNULL&Date" dateFormat="short" name=RecordEndDate id="RecordEndDate"><span class="icon"><a onClick="laydate({elem: '#RecordEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </input>
        </TD>
	</TR>
<TR>
</Table>
</div>
<!--<Div id= divCmdButton style="display: ''">-->
  <!-- <input class=cssButton type="button" value='��  ѯ' onclick="all_Query()" />-->
   <a href="javascript:void(0);" class="button" onClick="all_Query();">��    ѯ</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <!--input class=cssButton type="button" value='��ϸ��ѯ'onclick="detail_Query()" /-->
 <!-- </Div>-->
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
       <!-- <INPUT VALUE="��  ҳ" TYPE=button  class="cssButton" onclick="turnPage.firstPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton" onclick="turnPage.previousPage()">
        <INPUT VALUE="��һҳ" TYPE=button  class="cssButton" onclick="turnPage.nextPage()">
        <INPUT VALUE="β  ҳ" TYPE=button  class="cssButton" onclick="turnPage.lastPage()">-->
</Div>
<p>
<!--<INPUT VALUE="����EXCEL" TYPE=button  class="cssButton" onclick="analyse_data_pol()">-->
<a href="javascript:void(0);" class="button" onClick="analyse_data_pol();">����EXCEL</a>
</p>
<br><br><br><br>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
