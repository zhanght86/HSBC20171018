<%@include file="/i18n/language.jsp"%>
<html>
<%
	//�������ƣ����ʱ������ѯ
	//�����ܣ�
	//�������ڣ�2008-01-8
	//������  ��Zhang Bin
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="RIFeeRateQueryInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<%@include file="RIFeeRateQueryInit.jsp"%>
</head>

<body onload="initForm();">
	<form action="" method=post name=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
		<!-- ��ʾ������LLReport1����Ϣ -->
		<br>
		<table>
			<tr>
				<td class=common><IMG src="../common/images/butExpand.gif"
					style="cursor: hand;" OnClick="showPage(this,divLLReport1);"></td>
				<td class=titleImg>���ʱ������б�</td>
			</tr>
		</table>

		<Div id="divLLReport1" style="display: ''">

			<Table class=common>
				<TR class=common>
					<TD class=title5>���ʱ����</TD>
					<TD class=input5><Input class=common name=FeeTableNo>
					</TD>
					<td class="title5">���ʱ�����</td>
					<td class="input5"><input class=common name="FeeTableName">
					</td>
					<td class="title5"></td>
					<td class="input5"></td>
				</TR>
			</Table>
			<input class="cssButton" type=button value="��  ѯ"
				onclick="submitForm()"> <input class="cssButton" type=button
				value="��  ��" onclick="ReturnData()"> <input
				class="cssButton" type=button value="��  ��" onclick="ClosePage()">
			<table>
				<tr>
					<td class=common><IMG src="../common/images/butExpand.gif"
						style="cursor: hand;" OnClick="showPage(this,divLCPol1);"></td>
					<td class=titleImg>���ʱ������б�</td>
				</tr>
			</table>
			<Div id="divLLReport2" style="display: ''">
				<table class=common>
					<tr class=common>
						<td style="text-align:left;" colSpan=1><span id="spanFeeRateGrid">
						</span></td>
					</tr>
				</table>
			</div>
	</form>
	<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>