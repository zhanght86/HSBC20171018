<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//�������ƣ�PDSugIncomeDataInput.jsp
		//�����ܣ��������ݶ���
		//�������ڣ�2011-10-19
		//������  ��qihe
		//���¼�¼��  ������    ��������     ����ԭ��/����
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
		<script type="text/javascript">
	 		var riskcode = '<%=request.getParameter("riskcode")%>';
			var contOpt = '<%=request.getParameter("contopt")%>';
	 		function tabMoveTo(num){
				if(num=='1'){
					window.location.href =  './PDSugIncomeDataInput.jsp?riskcode='+riskcode + '&contopt=' + contOpt;
				}else if(num=='2'){
					window.location.href =   './PDSugIncomeTabHeadInput.jsp?riskcode='+riskcode + '&contopt=' + contOpt;
				}
			}
		</script>
		<SCRIPT src="PDSugIncomeTabHead.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugIncomeTabHeadInit.jsp"%>
		<STYLE>
#changeRiskPlan a {
					float: left;
			background: url("../common/images/tableft1.gif") no-repeat left top;
			margin: 0;
			padding: 0 0 0 4px;
			text-decoration: none;
		}
		
#changeRiskPlan a span {
					float: left;
			display: block;
			background: url("../common/images/tabright1.gif") no-repeat right top;
			padding: 5px 15px 4px 6px;
			color:#627EB7;
 		}
		
		/* Commented Backslash Hack hides rule from IE5-Mac \*/
#changeRiskPlan a span {
					float: none;
		}
		
		/* End IE5-Mac hack */
#changeRiskPlan a:hover span {
					color:#627EB7;
 		}
		
#changeRiskPlan a:hover {
					background-position: 0% -42px;
		}
		
#changeRiskPlan a:hover span {
					background-position: 100% -42px;
		}
		
#changeRiskPlan #current a {
					background-position: 0% -42px;
		}
		
#changeRiskPlan #current a span {
					background-position: 100% -42px;
		}
		</STYLE>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm(),initElementtype();">
			<Div id="changeRiskPlan" style="display: ''">
				<Div id="changeRiskPlanTitle" style="display: ''">
					<table class=common>
						<tr>
							<td class="common">
								<a href="#" onclick="tabMoveTo(1);"> <span>�����㷨����</span>
								</a>
								<a href="#" onclick="tabMoveTo(2);"> <span>�����ͷ����</span>
								</a>
							</td>
						</tr>
					</table>
				</Div>
			</Div>	
		<form action="./PDSugIncomeTabHeadSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg"><%=request.getParameter("riskcode")%>�������ͷ�б�:
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
			<input value="��ͷ��������" name="btnEdit" onClick="tabHeadConf()"
					class="cssButton" type="button">
			<table>
			  <tr>
			    <td class="titleImg" >��ͷ������:</td>
			  </tr>
			</table>
			<table  class=common>
				<tr class=common>
					<td class=title5>��ͷID:</td>
					<td class=input5>
						<input class="common8" name=ID  verify="��ͷID|NOTNUlL&LEN<=10" elementtype="nacessary">
					</td>
					<td class=title5>��ͷ��������룺</td>
					<td class=input5>
						<Input class=codeno style='width:120px' name=PRIFITCODE readonly="readonly"  ondblclick="return showMyCodeList('prifitcode',[this,PRIFITCODEName],[0,1],riskcode);" onkeyup="return showMyCodeListKey('prifitcode',[this,PRIFITCODEName],[0,1],riskcode);"><input class=codename name="PRIFITCODEName" style='display:none;' readonly="readonly">
					</td>
			    </tr>
			    <tr class=common>
			        <td class=title5>��ͷ���ƣ�</td>
					<td class=input5>
						<input class="common8" name=NAME verify="��ͷ����|NOTNUlL" elementtype="nacessary">
					</td>
					<td class=title5>�ϲ��ͷID��</td>
					<td class=input5>
					<Input class=codeno style='width:120px' name=PARENTID readonly="readonly"  verify="�ϲ��ͷID|NOTNUlL&LEN<=10" ondblclick="return showMyCodeList('parentid',[this,PARENTIDName],[0,1],riskcode);" onkeyup="return showMyCodeListKey('parentid',[this,PARENTIDName],[0,1],riskcode);"><input class=codename name="PARENTIDName" style='display:none;' readonly="readonly" elementtype="nacessary">
					</td>
				</tr>
				<tr class=common>
					<td class=title5>��ͷչʾ˳��</td>
					<td class=input5>
						<input class="common" name=SHOWORDER verify="��ͷչʾ˳��|NOTNUlL&NUM&LEN<=2" elementtype="nacessary">
					</td>
				</tr>
			</table>
			<div align=left>
				<input value="������������" name="btnEdit" onClick="multiLanguage()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="��  ��" name="btnEdit" onClick="save()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="��  ��" name="btnSave" onClick="update()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="ɾ ��" name="btnEdit" onClick="del()"
					class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
				<input value="��   ��" name="btnEdit" onClick="returnParent()"
					class="cssButton" type="button">
			</div>
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_LMRiskProfitHead">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%>>
		</form>
	</body>
	<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;"></span>
</html>

