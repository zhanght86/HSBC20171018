<%@include file="../i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
 //�������ƣ�PDEdorDefiEntryInput.jsp
 //�����ܣ���ȫ��Ϣ����
 //�������ڣ�2009-3-16
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
   GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 String mRiskcode=request.getParameter("riskcode");
 String tPdFlag=request.getParameter("pdflag");
%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<link rel="stylesheet" type="text/css"
	href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<script src="PDCommonJS.js"></script>
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDEdorDefiEntry.js"></SCRIPT>
<%@include file="PDEdorDefiEntryInit.jsp"%>
<script>
	
	var mOperator = "<%=tGI.Operator%>";   //��¼����Ա
</script>
</head>
<body onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm
	target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>���ֱ���</td>
		<td class=input5><input class="common" name="RiskCode" readonly="readonly" id=RiskCode>
		</td>
		<td class=title5>��������</td>
		<td class=input5><input class="common" name="RiskName" readonly="readonly">
		</td>
		<td class=title5></td>
		<td class=input5></td>
	</tr>
</table>
<input type="button" name="SearchDetail" value="���ֻ�����Ϣ��ѯ"
	class="cssButton" onclick="searchDetail()">


<table>
	<tr>
		<td class="titleImg">��ȫ��Ŀ��Ϣ</td>
	</tr>
</table>
<table class=common id="edoritemTab">
</table>
<input id=savabutton1 value="����/�޸�" type=button onclick="saveEdorItem()"
	class="cssButton" type="button">
<table>
	<tr>
		<td class="titleImg">����ӵı�ȫ��</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline10Grid">
		</span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
	onclick="Mulline10GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline10GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline10GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ" TYPE=button
	onclick="Mulline10GridTurnPage.lastPage();">
<hr>
<table>
	<tr>
		<td class="titleImg" id='allDefEdorRule'>������</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<input value="��ȫ���������" type=button onclick="button110( )"
			class="cssButton" type="button">
		<input value="��ȫ�˱�������" type=button onclick="button111( )"
			class="cssButton" type="button">
	</tr>
</table>
<hr>
<table>
	<tr>
		<td class="titleImg" id='allDefEdorCal'>�Ѷ����㷨</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline9Grid">
		</span></td>
	</tr>
</table>
<table>
	<tr>
		<td class="titleImg">�㷨��ϸ</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<TD class=title5>��ȫ��Ŀ����</TD>
		<TD class=input5><input class="common" name="EdorType"></TD>
		<TD class=title5>���δ���</TD>
		<TD class=input5><Input class=codeno name=DutyCode
			verify="���δ���|NOTNUlL" readonly="readonly"
			ondblclick="return showCodeList('pd_dutycode',[this,DutyCodeName],[0,1],null,'<%=mRiskcode%>','RiskCode',1);"
			onkeyup="return showCodeListKey('pd_dutycode',[this,DutyCodeName],[0,1],null,'<%=mRiskcode%>','RiskCode',1);"><input
			class=codename name="DutyCodeName" readonly="readonly"><font size=1
			color='#ff0000'><b>*</b></font></TD>

		<TD class=title5>�㷨����</TD>
		<TD class=input5><input class="common" name="CalCode"
			verify="�㷨����|LEN>=6"></TD>
		<TD class=title5></TD>
		<TD class=common></TD>
	</tr>
	<tr class=common>
	
		<TD class=title5>��ȫ�㷨����</TD>
		<TD class=input5><Input class=codeno name=EdorCalType
			verify="��ȫ�㷨����|NOTNUlL&CODE:edorcaltype" readonly="readonly"
			ondblclick="return showCodeList('edorcaltype',[this,EdorCalTypeName],[0,1]);"
			onFocus="displayCalDescibe(this.value);"
			onkeyup="return showCodeListKey('edorcaltype',[this,EdorCalTypeName],[0,1]);"><input
			class=codename name="EdorCalTypeName" readonly="readonly"><font
			size=1 color='#ff0000'><b>*</b></font></TD>
		<TD class=title5>
		<div style="display:none;" id='CalDescibeDivA'>�ֽ��ֵ���</div>
		</TD>
		<TD class=input5>
		<div style="display:none;" id='CalDescibeDivB'><Input
			class=codeno name=CalDescibe readonly="readonly"
			ondblclick="return showCodeList('pd_caldescibe',[this,CalDescibeName],[0,1]);"
			onkeyup="return showCodeListKey('pd_caldescibe',[this,CalDescibeName],[0,1]);"><input
			class=codename display='none' name="CalDescibeName" readonly="readonly">
		</div>
		</TD>
	</tr>
</table>


<jsp:include page="CalCodeDefMain.jsp" />
<table>
	<tr class=common  style = "display: none" id = OthersCTID >
		<td class="titleImg">�����˱���Ϣ</td>
	</tr>
</table>
<table class=common>
	<div id = 'CycPayIntvTypeDiv' >
	<tr class=common  style = "display: none" id = CycPayIntvTypeSelID>
	
	<td class=title5>���ֱ���</td>
		<td class=input5><input class="common" name="RiskCode1" readonly="readonly" id=RiskCode1>
		</td>
	<td class=title5>�˱����α���</td>
		<td class=input5><input class="common" name="DutyCode1" readonly="readonly" id=DutyCode1>
		</td>
	
	</tr>
	
	<tr class=common  style = "display: none" id = CycPayIntvTypeSelID1>
	
	<TD class=title5>�ڽ�ʱ����</TD>
		<TD class=input5><Input class=codeno name=CycPayIntvType
	 	readonly="readonly"  VALUE=12 
		ondblclick="return showCodeList('payintv',[this,CycPayIntvTypeName],[0,1]);"
		onFocus="displayCalDescibe(this.value);"
		onkeyup="return showCodeListKey('payintv',[this,CycPayIntvTypeName],[0,1]);"><input
		class=codename name="CycPayIntvTypeName" readonly="readonly"><font
		size=1 color='#ff0000'><b>*</b></font></TD>
	
	</tr>
	</div>
	
</table>


<div id=savabuttonid><input value="��  ��" type=button
	onclick="initEdorDetail('1')" class="cssButton" type="button">
<input value="����/�޸�" type=button onclick="saveCAL()" class="cssButton"
	type="button"> <input value="ɾ  ��" type=button
	onclick="delCAL()" class="cssButton" type="button"></div>
<div align=left id=checkFunc>
<input value="�鿴�㷨����" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>
<hr>
<div style="display: none">
<table>
	<tr>
		<td class="titleImg">����ӵ��㷨</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align:left;" colSpan=1><span id="spanMulline11Grid">
		</span></td>
	</tr>
</table>


<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
	onclick="Mulline11GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline11GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ" TYPE=button
	onclick="Mulline11GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ" TYPE=button
	onclick="Mulline11GridTurnPage.lastPage();"></div>
<!--table  class= common> <tr  class= common> <td style="text-align:left;" colSpan=1> <span> </span>  </td> </tr></table-->
<div style="display:none;" id='defineDetailDivZT'><input
	type="button" name="DefineDetailZT" value="�����˱���������" class="cssButton"
	onclick="defineDetail()"></div>
<div style="display: none" id='defineDetailDivCF'><input
	type="button" name="DefineDetailCF" value="�侲����ȡ��������������"
	class="cssButton" onclick="defineDetail()"></div>
<div style="display: none" id='defineDetailDivPL'><input
	type="button" name="DefineDetailPL" value="�������" class="cssButton"
	onclick="defineDetail()"></div>
<!--�㷨��������ҳ--> <!--
			<table class=common>
				<tr>
					<td class="titleImg">
						��ȫ����������
					</td>
				</tr>
				<tr>
					<td class="common">
						<input value="�������������" type=button onclick="button207( )"
							class="cssButton" id="BonusFlagId">
					</td>
				</tr>
			</table>
			--> <!-- <input value="���ֱ��������" type=button  onclick="button208( )" class="cssButton" type="button" >
<input value="���������" type=button  onclick="button209( )" class="cssButton" type="button" >
<br><br>--> <!-- input value="���±�" type=button  onclick="button203( )" class="cssButton" type="button" -->
<input value="������ظ�/��ѯ" type=button onclick="IssueQuery( )"
	class="cssButton" type="button"> <input id=savabutton2
	value="�����¼��" type=button onclick="IssueInput( )" class="cssButton"
	type="button"> <br>
<br>
<input id=savabutton3 value="��ȫ��Ϣ¼�����" type=button
	onclick="btnEdorDone( )" class="cssButton" type="button"> <br>
<br>
<input value="��  ��" type=button onclick="returnParent( )"
	class="cssButton" type="button"> <br>
<br>

<input type=hidden name=hideEdorType> <input type=hidden
	name=hideCalType> <input type=hidden name=hideCalCode>
<input type=hidden name="operator"> <input type=hidden
	name="tableName" value="PD_LMRiskEdorItem"> <input type=hidden
	name="RequDate"> <input type=hidden name=MissionID ID=MissionID> <input type=hidden name=SubMissionID ID=SubMissionID>
<input type=hidden name=ActivityID ID=ActivityID> <input type=hidden
	name=IsReadOnly><input type=hidden
	name=PdFlag id=PdFlag></form>

<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
