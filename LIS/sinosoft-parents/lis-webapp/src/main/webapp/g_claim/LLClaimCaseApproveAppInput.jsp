<%
/***************************************************************
 * <p>ProName��LLClaimCaseApproveAppInput.jsp</p>
 * <p>Title��������������ҳ��</p>
 * <p>Description��������������ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>������������ҳ��</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LLClaimCaseApproveAppInput.js"></script>
	<%@include file="./LLClaimCaseApproveAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCaseApproveAppSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMainQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divMainQuery" class=maxbox1 style="display: ''">
		<table class=common>
		<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid common" name=GrpRgtNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName></td>  
				<td class=title>�������</td>				 
				<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>				             
			</tr>
			<tr class=common>
				<td class=title>����ȷ������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptConfirmStart onClick="laydate({elem: '#AcceptConfirmStart'});" id="AcceptConfirmStart"><span class="icon"><a onClick="laydate({elem: '#AcceptConfirmStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ȷ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptConfirmEnd onClick="laydate({elem: '#AcceptConfirmEnd'});" id="AcceptConfirmEnd"><span class="icon"><a onClick="laydate({elem: '#AcceptConfirmEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ʱЧ����(>=)</td>
				<td class=input><input class="wid common" name=AcceptWorkdays></td>
			</tr>
		</table>
		<input class=cssButton value="��  ѯ" type=button onclick="queryMain(1);">
	</div>

	<br>
	<font color='#FF0000'><b><span id="AcceptCountSpan"></span></b></font>    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divMainCase);">
			</td>
			<td class=titleImg>������</td>
		</tr>
	</table>
	<div id="divMainCase" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanMainCaseGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage(turnPage1,MainCaseGrid);">
			<input class=cssButton91 type=button value="��һҳ" onclick="previousPage(turnPage1,MainCaseGrid);">
			<input class=cssButton92 type=button value="��һҳ" onclick="nextPage(turnPage1,MainCaseGrid);">
			<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage(turnPage1,MainCaseGrid);">
		</center>
	</div>
	<input class=cssButton name='Report' value="��  ��" type=button onclick="applay();">
	
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSelfQuery);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divSelfQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid common" name=GrpRgtNo1></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName1></td>  
				<td class=title>�������</td>				
				<td class=input><input class=codeno name=AcceptCom1 style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName1],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName1],[0,1],null,null,null,1);"><input class=codename name=AcceptComName1 readonly></td>
			</tr>
			<tr class=common>
				<td class=title>����ȷ������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptConfirmStart1 onClick="laydate({elem: '#AcceptConfirmStart1'});" id="AcceptConfirmStart1"><span class="icon"><a onClick="laydate({elem: '#AcceptConfirmStart1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ȷ��ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AcceptConfirmEnd1 onClick="laydate({elem: '#AcceptConfirmEnd1'});" id="AcceptConfirmEnd1"><span class="icon"><a onClick="laydate({elem: '#AcceptConfirmEnd1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ʱЧ����(>=)</td>
				<td class=input><input class="wid common" name=AcceptWorkdays1></td>
			</tr>
		</table>
		<input class=cssButton value="��  ѯ" type=button onclick="querySelf(1);">
	</div>	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divSelfCase);">
			</td>
			<td class=titleImg>���˳�</td>
		</tr>
	</table>
	<div id="divSelfCase" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanSelfCaseGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton91 type=button value="��һҳ" onclick="previousPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton92 type=button value="��һҳ" onclick="nextPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage(turnPage2,SelfCaseGrid);">
		</center>
	</div>
	
	<input class=cssButton name='Report' value="���밸��" type=button onclick="enterCase();">
	<input class=cssButton name='Report' value="�ͷŰ���" type=button onclick="releaseCase();">	
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
	<Input type=hidden  name=SelectGrpRgtNo>			<!--ѡ�е�����������-->
	<Br /><Br /><Br /><Br /><Br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
