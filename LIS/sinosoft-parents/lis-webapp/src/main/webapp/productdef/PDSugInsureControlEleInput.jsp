<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//�������ƣ�PDDutyGetAliveInput.jsp
		//�����ܣ����θ�������
		//�������ڣ�2009-3-16
		//������  ��
		//���¼�¼��  ������    ��������     ����ԭ��/����
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<SCRIPT src="PDSugInsureControlEle.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugInsureControlEleInit.jsp"%>
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("RiskCode")%>';
	 		var standbyflag1 = '<%=request.getParameter("StandbyFlag1")%>';
		</script>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
		<form action="./PDSugInsureControlEleSave.jsp" method=post name=fm target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg">
Ͷ������ؼ�����ֵ:
					</td>
				</tr>
			</table>
			<table class=common>

				<tr class=common>
					<td class=title5>
�ؼ�ID��
					</td>
					<td class=input5>
						<Input class=codeno name=CONTROLCODE verify="�ؼ�ID|NOTNULL" ondblclick="return showMyCodeList('controlid',[this,CONTROLCODEName],[0,1],riskcode);" onkeyup="return showMyCodeListKey('controlid',[this,CONTROLCODEName],[0,1],riskcode);"><input class=codename name="CONTROLCODEName" readonly="readonly" elementtype="nacessary" >
					</td>
					<td class=title5>
�ؼ�ֵ��
					</td>
					<td class=input5>
						<input class="common" name=CONTROLVALUE  verify="�ؼ�ֵ|NOTNULL" elementtype="nacessary" >
					</td>
				</tr>
				<tr class=common>
					<td class=title5>
�������ͣ�
					</td>
					<td class=input5>
						
						<Input class=codeno name=FUNCTIONTYPE verify="��������|NOTNULL"  onpropertychange="controlValueChange(this.value);"  ondblclick="return showCodeList('controltype',[this,FUNCTIONTYPEName],[0,1]);" onkeyup="return showCodeListKey('controltype',[this,FUNCTIONTYPEName],[0,1]);"><input class=codename name="FUNCTIONTYPEName" readonly="readonly" elementtype="nacessary">
					</td>
					<td class=title5>
Ӱ��ؼ�ID��
					</td>
					<td class=input5>
						<Input class=codeno name=RELACODE verify="Ӱ��ؼ�ID|NOTNULL" ondblclick="return showMyCodeList('controlid',[this,RELACODEName],[0,1],riskcode);" onkeyup="return showMyCodeListKey('controlid',[this,RELACODEName],[0,1],riskcode);"><input class=codename name="RELACODEName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common id='RELASHOWFLAGTr'>
					<td class=title5>
�Ƿ�������
					</td>
					<td class=input5>
						<Input class=codeno name=RELASHOWFLAG  ondblclick="return showCodeList('controlexporimp',[this,RELASHOWFLAGName],[0,1]);" onkeyup="return showCodeListKey('controlexporimp',[this,RELASHOWFLAGName],[0,1]);"><input class=codename name="RELASHOWFLAGName" readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common id='RELAVALUESQLTr'>
					<td class=title5>
�����
					</td>
					<td class=input5 colspan="3">
						<textarea rows="5" cols="75" name=RELAVALUESQL tip="�������ݿ��" elementtype="nacessary"></textarea>
					</td>
				</tr>
				<tr class=common id='RELASHOWVALUETr'>
					<td class=title5>
Ĭ��ֵ��
					</td>
					<td class=input5 colspan="3">
						<textarea rows="5" cols="75" name=RELASHOWVALUE tip="�������ݿ��" elementtype="nacessary"></textarea>
					</td>
				</tr>
			</table>
			<input value="��   ��" name="btnEdit" onClick="save()" class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>" >
			<input value="��   ��" name="btnSave" onClick="update()" class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<table>
				<tr>
					<td class="titleImg">
Ͷ������ؼ������б�:
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			<br>
			<div align=left>
				<input value="ɾ   ��" name="btnEdit" onClick="del()" class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="��   ��" name="btnEdit" onClick="returnParent()" class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskShowRela">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode"  value=<%=request.getParameter("RiskCode")%>  >
		</form>
			<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;">
	</body>
</html>

