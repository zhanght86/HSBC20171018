<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDContDefiEntryInput.jsp
 //�����ܣ���Լ��Ϣ�������
 //�������ڣ�2009-3-13
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<%
	//=============================================================BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String tPdFlag = request.getParameter("pdflag"); //��ʾ���0ֻ��1���޸�

	//=============================================================END
%>

  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <SCRIPT src="SpeedProgressConvert.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDContDefiEntry.js"></SCRIPT>

 <%@include file="PDContDefiEntryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ��</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<input value="���ֻ�����Ϣ�鿴" type=button  onclick="button117( )" class="cssButton" type="button" >
<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ��</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<div id = 'riskPropId2' style=''> 
<table>
  <tr>
    <td class="titleImg" >���ϼƻ�Ҫ��</td>
  </tr>
</table>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanLMDutyParamGrid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<!--  <div id = 'riskProp2' style=''>  -->
<div id = 'riskPropId'> 
<table>
  <tr>
    <td class="titleImg" >���β㼶����</td>
  </tr>
</table>
<!--
<input value="���ʱ���" type=button  onclick="button100( )" class="cssButton" type="button" >

<input value="�������νɷ��㷨" type=button onclick="button101( )" class="cssButton" type="button" >
<input value="�ɷ��ֶο��ƶ���" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="�����ֶο��ƶ���" type=button id="btnGetFieldCtrl" onclick="button103( )" class="cssButton" type="button" >
<input value="�������μӷѶ���" type=button  onclick="button104( )" class="cssButton" type="button" >
  -->

 <input value="���ϼƻ�Լ��Ҫ��" type=button  onclick="button105( )" class="cssButton" type="button" >  

</div>
<table>
  <tr>
    <td class="titleImg" >���ֲ㼶����</td>
  </tr>
</table>

<input value="¼����Ϣ���ƶ���" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="���ָ���ɫ����" type=button  onclick="button106( )" class="cssButton" type="button" >

<input value="���ֽ��涨��" type=button id="btnRiskFace" onclick="buttonRiskFace( )" class="cssButton" type="button" ><!--  -->
<!-- <input value="���ֽɷ����Զ���" type=button  onclick="button108( )" class="cssButton" type="button" >   -->
<!--  <input value="���ֽɷѼ������" type=button  onclick="button109( )" class="cssButton" type="button" >    -->
<!-- input value="�˻������ֶ���" type=button id="btnRiskAcc" onclick="button116( )" class="cssButton" type="hidden"> -->

<!--<input value="�˻������ֶ���" type=button id="btnRiskAcc" onclick="button116( )" class="cssButton">-->
<input value="���ַ��ඨ��" type=button  onclick="button82( )" class="cssButton" type="button" >
<input value="�������ۻ�������" type=button  onclick="button114( )" class="cssButton" type="button" >
<input value="�������μӷѶ���" type=button  onclick="button104( )" class="cssButton" type="button" >
<!--<input value="����Ͷ������" type=button  onclick="button110( )" class="cssButton" type="button" >-->
<input value="���ֺ˱�����" type=button  onclick="button111( )" class="cssButton" type="button" >
<input value="���ֹ����˱�����" type=button  onclick="pubUWRule( )" class="cssButton" type="button" >
<input value="Ӷ���������" type=button  onclick="commission()" class="cssButton" type="button" >

<input value="Ա���ۿ۱�������" type=button  onclick="staffrateDefi()" class="cssButton" type="button" >

<input value="�ֶβ�������" type=button  onclick="button127( )" class="cssButton" type="button"  id=definedparams >
<!-- modify by pangyingjie 2015/07/03 -->
<!--  <input value="������������" type=button  onclick="button128()" class="cssButton" type="button"  id=risknameconfiguration >-->
<!-- modify by baos 2015/07/03 -->
<input type = "hidden" value="" type=button  onclick="button130( )" class="cssButton" type="button">

<div id='sugDefButtons'>
<table>
  <tr>
    <td class="titleImg" >������������ݶ���</td>
  </tr>
</table>

<input value="���ֽ��涨��" type=button  onclick="buttonSugFace( )" class="cssButton" type="button" >
<input value="�������ݶ���" type=button  onclick="buttonIncome( )" class="cssButton" type="button" >
<!-- input value="���־�̬�ļ�����" type=button  onclick="buttonStatic( )" class="cssButton" type="button" -->
<input value="�������ʲ�������" type=button  onclick="buttonRate( )" class="cssButton" type="button" >
<div>
<!--<input value="��������϶���" type=button  onclick="button115( )" class="cssButton" type="button" >--> 
<!--input value="���������ӡ����" type=button  onclick="button107( )" class="cssButton" type="button" -->
<!--input value="����ҳ��¼������" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" -->

<hr>
<input value="������ظ�/��ѯ" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input id=savabutton3 value="�����¼��" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<input value="���±���Ϣ" onclick="IssueQuery( )" class="cssButton" type="hidden" >
<hr>
<!--  
<input value="��һ��" type=button  onclick="button122( )" class="cssButton" type="button" >-->
<input id=savabutton1 value="��Լ��Ϣ¼�����" type=button  onclick="button119( )" class="cssButton" type="button" >
<input id=savabutton2 value="���ײ�Ʒ¼�����" type=button  onclick="button121( )" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="IsDealWorkflow">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name="SimpleContPara">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=IsReadOnly>
<input type=hidden name=ContOpt>
<input type=hidden name=PdFlag>
<input type=hidden name=PageNo value=101>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
