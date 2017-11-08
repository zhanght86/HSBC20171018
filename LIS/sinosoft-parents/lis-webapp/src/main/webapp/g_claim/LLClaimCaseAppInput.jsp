<%
/***************************************************************
 * <p>ProName��LLClaimCaseMainInput.jsp</p>
 * <p>Title����ͨ��������</p>
 * <p>Description����ͨ��������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mManageCom = tGI.ManageCom;
	String mOperator = tGI.Operator;
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>��ͨ�����������</title>
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
	<script src="./LLClaimCaseAppInput.js"></script>
	<%@include file="./LLClaimCaseAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimCaseAppSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBank);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divMissFind" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid common" name=GrpRgtNo id=GrpRgtNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>  
				<td class=title>�������</td>
				<td class=input><input class=codeno name=AcceptCom id=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);">
				<input class=codename name=AcceptComName id=AcceptComName readonly></td>				       
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>ʱЧ����(>=)</td>
				<td class=input><input class="wid common"  name=AcceptWorkdays id=AcceptWorkdays></td>				
			</tr>
		</table>
		<input class=cssButton value="��  ѯ" type=button onclick="queryCommonClick(1);">	
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
	<input class=cssButton name='Report' value="��  ��" type=button onclick="applyClick();">
	
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
		<center style="display:none">
			<input class=cssButton90 type=button value="��  ҳ" onclick="firstPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton91 type=button value="��һҳ" onclick="previousPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton92 type=button value="��һҳ" onclick="nextPage(turnPage2,SelfCaseGrid);">
			<input class=cssButton93 type=button value="β  ҳ" onclick="lastPage(turnPage2,SelfCaseGrid);">
		</center>
	</div>
	
	<input class=cssButton name=CaseEnter value="���밸��" type=button onclick="enterCase();">
	<input class=cssButton name=CaseRelease value="�ͷŰ���" type=button onclick="releaseCase();">
	<input class=cssButton name=AddCase value="��������" type=button onclick="newCase();">
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
	<Input type=hidden  name=SelectGrpRgtNo> 	 	 <!--ѡ�е����κ�-->
	
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
