<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDCheckFieldInput.jsp
 //�����ܣ�Ͷ������
 //�������ڣ�2009-3-14
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%
	String tType = request.getParameter("Type");
	String tEdorType = request.getParameter("EdorType");
	System.out.println("tType:"+tType+":tEdorType:"+tEdorType);
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">


<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
 <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDCheckField.js"></SCRIPT>
 <%@include file="PDCheckFieldInit.jsp"%>
<script>
	var mType = '<%=tType%>';
	var mEdorType = '<%=tEdorType%>';
</script>	

<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDCheckFieldSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >����Ͷ������</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR><br>
<!--input value="�����㷨����" type=button  onclick="button192( )" class="cssButton" type="button" -->

</BR>

<table>
  <tr>
    <td class="titleImg" >Ͷ��������ϸ��</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
    	 <!--TD  class= title>���ֱ���</TD>
        <TD  class= input>
            <Input class="codeno" name=RiskCode readonly="readonly" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName readonly="readonly">
        </TD--> 
        <TD  class= title>�����ֶ�����</TD>
        <TD  class= input>
						<Input class=codeno name=CheckField readonly="readonly"  verify="�����ֶ�����|NOTNUlL" ondblclick="return showCodeList('pd_lc_checkfield',[this,CheckFieldName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);" onkeyup="return showCodeListKey('pd_lc_checkfield',[this,CheckFieldName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1);"><input class=codename name="CheckFieldName" readonly="readonly" elementtype="nacessary">
        </TD> 
        <TD  class= title>
���
        </TD>
        <TD  class= input>
          <input class="common"  name="Serialno"  verify="���|NOTNUlL&NUM" elementtype="nacessary">
        </TD>
        <TD  class= title>
�㷨����
        </TD>
        <TD  class= input>
          <input class="common"   name="CalCode" verify="�㷨����|LEN>=6&LEN<=10" elementtype="nacessary">
        </TD>
        <TD  class= title></TD>
        <TD  class= common></TD>
      </tr>
      <tr class=common>
    	 <TD  class= title>��ʾ��Ϣ
        </TD>
        <TD  class= input>
        	<input class="common"   name="Msg" verify="��ʾ��Ϣ|NOTNUlL"  elementtype="nacessary">
        </TD>
        <TD  class= title>����ϵͳ</TD>
        <TD  class= input>
						<Input class=codeno name=STANDBYFLAG1 readonly="readonly"  verify="����ϵͳ|NOTNUlL" ondblclick="return showCodeList('pd_systype',[this,STANDBYFLAG1Name],[0,1]);" onkeyup="return showCodeListKey('pd_systype',[this,STANDBYFLAG1Name],[0,1]);"><input class=codename name="STANDBYFLAG1Name" readonly="readonly"  elementtype="nacessary">
        </TD> 
    	</tr>
    	
</table>
<!--�㷨��������ҳ-->
<jsp:include page="CalCodeDefMain.jsp?RuleType=0"/>
	


<!--table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table-->
<br>
<div align=left id=savabuttonid>
<input value="��  ��" type=button  onclick="initForm()" class="cssButton" type="button" >	
<input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br><br>
<div align=left id=checkFunc>
<input value="�鿴��������" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMCheckField">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
