<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�RiskFeeInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������ ��CrtHtml���򴴽�
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
String AskFlag = request.getParameter("AskFlag");
//GrpContNo = "220110000000071";
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
LoadFlag="<%=LoadFlag%>";
AskFlag = "<%=AskFlag%>";
if (AskFlag == "" || AskFlag == "null" || AskFlag == null)
    AskFlag = 0;
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="RiskFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RiskFeeInit.jsp"%>
</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="RiskFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>���屣����Ϣ</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''">
		<table class=common align=center>
			<TR class=common>
				<TD class=title>
					��ͬ��
				</TD>
				<TD class=input>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo" value="<%=GrpContNo%>">
					<input type=hidden name="mOperate" id="mOperate">
				</TD>
				<TD class=title>
					���ֱ���
				</TD>
				<TD class=input>
					<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class=code name=RiskCode id="RiskCode" onclick="return showCodeList('GrpRisk',[this,GrpPolNo],[0,2],null,fm.GrpContNo.value,'b.GrpContNo');" ondblclick="return showCodeList('GrpRisk',[this,GrpPolNo],[0,2],null,fm.GrpContNo.value,'b.GrpContNo');" onkeyup="return showCodeListKey('GrpRisk',[this,GrpPolNo],[0,2],null,fm.GrpContNo.value,'b.GrpContNo');">
				</TD>
				<TD class=title>
					�ֱ��յ���
				</TD>
				<TD class=input>
					<input class="readonly wid" readonly name="GrpPolNo" id="GrpPolNo">
				</tD>
			</TR>
		</table>
	</div>
	</div>
	<a href="javascript:void(0)" class=button onclick="easyQueryClick();">����Ѳ�ѯ</a>
		<!-- <input type=button value="����Ѳ�ѯ" class="cssButton" onclick="easyQueryClick();"> -->
		<br>
		<table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divRiskFee);">
				</td>
				<td class=titleImg>
					���ֹ������ϸ
				</td>
			</tr>
		</table>
		<Div id="divRiskFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFeeGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<br>
		<div id="divRiskFeeParam" style="display:'none'">
			<table>
				<tr>
					<td class=common>
						<IMG src="../common/images/butExpand.gif" style="cursor:hand;">
					</td>
					<td class=titleImg>
						�������ϸ����
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanRiskFeeParamGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
		<!-- <input type=button class="cssButton" value="��һ��" onclick="returnparent();"> -->
	    <Div  id= "divRiskFeeSave" style= "display: ''" align= left> 
	    	<a href="javascript:void(0)" class=button onclick="submitForm();">��  ��</a>
	    	<a href="javascript:void(0)" class=button onclick="deleteClick();">ɾ  ��</a>
		<!-- <input type=button class="cssButton" value="��  ��" onclick="submitForm();">
		<input type=button class="cssButton" value="ɾ  ��" onclick="deleteClick();"> -->
		</Div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
