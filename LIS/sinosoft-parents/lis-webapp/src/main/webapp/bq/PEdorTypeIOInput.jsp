<html>
<%
	//�������ƣ�
	//�����ܣ�ְҵ�����
	//�������ڣ�2005-5-17 11:48����
	//������  ��Lihs
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/Occupation.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="./PEdor.js"></SCRIPT>
<script src="../common/javascript/jquery.js"></script>
<SCRIPT src="./PEdorTypeIO.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Occupation.css" rel=stylesheet type=text/css>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="PEdorTypeIOInit.jsp"%>
<title>ְҵ�����</title>
</head>

<body onload="initForm()">
<form action="./PEdorTypeIOSubmit.jsp" method=post name=fm id=fm	target="fraSubmit">
<div class=maxbox1>
<table class="common">
	<tr class="common">
		<td class="title">��ȫ�����</td>
		<td class="input"><input type="text" class="readonly wid"
			name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
		<td class="title">��������</td>
		<td class="input"><input type="text" class="codeno"
			name="EdorType" id=EdorType readonly><input type="text" class="codename"
			name="EdorTypeName" id=EdorTypeName readonly></td>
		<td class="title">������</td>
		<td class="input"><input type="text" class="readonly wid"
			name="ContNo" id=ContNo readonly></td>
	</tr>
	<tr class="common">
		<td class="title">������������</td>
		<td class="input"><input type="text" class="multiDatePicker wid"
			name="EdorItemAppDate" id=EdorItemAppDate readonly></td>
			<!--  comment by jiaqiangli 2009-02-16
		<td class="title">��Ч����</td>
		<td class="input"><input type="text" class="readonly wid"
			name="EdorValiDate" readonly></td>
			 -->
		<td class="title">&nbsp;</td>
		<td class="input">&nbsp;</td>
	</tr>
</table>
</div>
<div id="divPerson" style="display:none">
<table>
	<tr>
		<td class="common normal" ><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divAGInfo);"></td>
		<td class=titleImg>�ͻ�������Ϣ</td>
	</tr>
</table>
<Div id="divAGInfo" class=maxbox1 style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>Ͷ��������</TD>
		<TD class=input><input class="readonly wid" readonly name=AppntName id=AppntName>
		</TD>
		<TD class=title>֤������</TD>
		<TD class=input><Input class=codeno "readonly" readonly
			name=AppntIDType id=AppntIDType><input class=codename name=AppntIDTypeName id=AppntIDTypeName
			readonly></TD>
		<TD class=title>֤������</TD>
		<TD class=input><input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo>
		</TD>
	</TR>
	<TR class=common>
		<TD class=title>����������</TD>
		<TD class=input><input class="readonly wid" readonly
			name=InsuredName id=InsuredName></TD>
		<TD class=title>֤������</TD>
		<TD class=input><Input class=codeno "readonly" readonly
			name=InsuredIDType id=InsuredIDType><input class=codename
			name=InsuredIDTypeName id=InsuredIDTypeName readonly></TD>
		<TD class=title>֤������</TD>
		<TD class=input><input class="readonly wid" readonly
			name=InsuredIDNo id=InsuredIDNo></TD>
	</TR>
</table>
</Div>
</div>

<Div id="divLPInsuredDetail" style="display:''">
<table>
	<tr>
		<td class=common> <IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divDetail);"></td>
		<td class=titleImg>������ְҵ��Ϣ</td>
	</tr>
</table>
</Div>
<Div id="divDetail" class=maxbox1 style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>ְҵ���뼰����</TD>
		<TD class=input><Input class=codeno readonly
			name=OccupationCode_S id=OccupationCode_S><input class=codename
			name=OccupationCode_SName id=OccupationCode_SName readonly></TD>
		<TD class=title>ְҵ���</TD>
		<TD class=input><Input class=codeno "readonly" readonly
			name=OccupationType_S id=OccupationType_S><input class=codename
			name=OccupationType_SName id=OccupationType_SName readonly></TD>
		<TD class=title>ְ ҵ</TD>
		<TD class=input><input class="wid common" id=Occupation_S name=Occupation_S readonly>
		</TD>
	</TR>
</table>
</Div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divPolOldGrid);"></td>
		<td class=titleImg>����������Ϣ</td>
	</tr>
</table>
<Div id="divPolOldGrid" style="display: ''">
<table class=common>
	<tr class=common>
		<td><span id="spanPolOldGrid"></span></td>
	</tr>
</table>
<div id="divTurnPagePolOldGrid" align="center" style="display:none">
	<input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePolOldGrid.firstPage()"> 
	<input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePolOldGrid.previousPage()"> 
	<input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePolOldGrid.nextPage()"> 
	<input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePolOldGrid.lastPage()">
</div>
</Div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divDetail1);"></td>
		<td class=titleImg>���������ְҵ��Ϣ</td>
	</tr>
</table>
<Div id="divDetail1" class=maxbox1 style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>ְҵ���뼰����</TD>
		<TD class=input><Input class=codeno readonly name=OccupationCode id=OccupationCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240');" 
			onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240');"><input
			class=codename name=OccupationCodeName id=OccupationCodeName readonly></TD>
		<TD class=title>ְҵ���</TD>
		<TD class=input><Input class=codeno readonly name=OccupationType id=OccupationType><input
			class=codename name=OccupationTypeName id=OccupationTypeName readonly></TD>
		<TD class=title>ְ ҵ</TD>
		<TD class=input><Input class="wid common" id="Occupation"
			name="Occupation"></TD>
	</TR>
</table>
</Div>

  <!-- add by jiaqiangli 2009-02-16 ���ӱ����˸�֪¼�� -->
  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        �����˽�����֪��Ϣ
      </td>
   </tr>
   </table>

  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
    <br> 
    <!-- add by jiaqiangli 2009-02-16 ���ӱ����˸�֪¼�� -->
    
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divDetail3);"></td>
		<td class=titleImg>����󱣷���Ϣ</td>
	</tr>
</table>
<Div id="divDetail3" style="display: ''">
<table class=common>
	<tr class=common>
		<td><span id="spanPolNewGrid"></span></td>
	</tr>
</table>
<div id="divTurnPagePolNewGrid" align="center" style="display:none">
	<input type="button" class="cssButton90" value="��  ҳ" onclick="turnPagePolNewGrid.firstPage()"> 
	<input type="button" class="cssButton91" value="��һҳ" onclick="turnPagePolNewGrid.previousPage()"> 
	<input type="button" class="cssButton92" value="��һҳ" onclick="turnPagePolNewGrid.nextPage()"> 
	<input type="button" class="cssButton93" value="β  ҳ" onclick="turnPagePolNewGrid.lastPage()">
</div>
</Div>
<br>

<Div id="divEdorquery" style="display: ''">
<Input class=cssButton type=Button value=" �� �� " onclick="saveEdorTypeIO()"> 
<Input class=cssButton type=Button value=" �� �� " onclick="resetForm()">
<Input class=cssButton type=Button value=" �� �� " onclick="returnParent()"> 
<Input class=cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
</Div>

<input type=hidden id="fmtransact" name="fmtransact"> 
<input type=hidden id="InsuredNo" name="InsuredNo"> 
<input type=hidden id="AppObj" name="AppObj"> 
<input type=hidden id="EdorNo" name="EdorNo"> 
<br>
<%@ include file="../bq/PEdorFeeDetail.jsp"%> 
<br>
<br><br><br><br>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
