<%
/***************************************************************
 * <p>ProName��LLClaimSpecialCaseInput.jsp</p>
 * <p>Title����������¼��</p>
 * <p>Description����������¼��</p>
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
	String mCurrentDate = PubFun.getCurrentDate();
	String mGrpRegisterNo = request.getParameter("GrpRgtNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mGrpRegisterNo = "<%=mGrpRegisterNo%>";
	var mMode = "<%=mMode%>";
</script>
<html>
<head>
	<title>��������¼��ҳ��</title>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimSpecialCaseInput.js"></script>
	<%@include file="./LLClaimSpecialCaseInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimSpecialCaseSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAcceptInfo);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divAcceptInfo" class=maxbox1 style="display: ''">
		<table class=common> 
			<tr class=common>
				<td class=title>���κ�</td>
				<td class=input><input class="wid readOnly" name=GrpRgtNo readOnly></td>
				<td class=title>��������</td>				
				<td class=input><input class=codeno name=RgtClass maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conttype',[this,RgtClassName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conttype',[this,RgtClassName],[0,1],null,null,null,1);" readonly><input class=codename name=RgtClassName readonly elementtype=nacessary readonly></td>
				<td class=title id='appntinfo'>Ͷ��������</td>
				<td class=input id='appntvalueinfo'><input class=codeno name=AppntNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showWarnInfo();" readonly><input class=codename name=AppntName onkeydown="QueryOnKeyDown(this);" elementtype=nacessary></td>
			</tr>
			<tr class=common>
				<td class=title>������������</td>
				<td class=input><input class=coolDatePicker name=AppDate dateFormat=short elementtype=nacessary onClick="laydate({elem: '#AppDate'});" id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readOnly" name=AcceptDate maxlength='20' dateFormat=short readonly></td>
				<!-- <td class=title id='risklevel'>�ͻ����յȼ�</td>
				<td class=input id='risklevelname'><input class=readOnly name=RiskLevel readOnly></td> -->
			</tr>
			<tr class=common>
				<td class=title>����Ǽ���</td>
				<td class=input><input class="wid readOnly" name=AcceptOperator maxlength='30' readonly></td>
				<td class=title>�������</td>				
				<td class=input><input class=codeno name=AcceptCom verify="code:managecom" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,AcceptComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#03#','1',1);" maxlength='20' readonly><input class=codename name=AcceptComName readonly elementtype=nacessary></td>
				<td class=title>������ת��</td>
				<td class=input><input class=common name=PageNo maxlength='20' elementtype=nacessary><input class=cssButton type=button name=PageNoApply value="�� ��" onclick="applyPageNo();"></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=CaseType maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('casetypes',[this,CaseTypeName],[0,1],null,'1 and code in (#11#,#12#,#13#)','1',1);" onkeyup="return showCodeListKey('casetypes',[this,CaseTypeName],[0,1],null,'1 and code in (#11#,#12#,#13#)','1',1);"><input class=codename name=CaseTypeName readonly elementtype=nacessary></td>
				<td class=title>�Ƿ�����������</td>
				<td class=input><input class=codeno name=ClaimFlag maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('trueflag',[this,ClaimFlagName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('trueflag',[this,ClaimFlagName],[0,1],null,null,null,1);"><input class=codename name=ClaimFlagName readonly elementtype=nacessary></td>
				<td class=title id=HistoryRgtNo>ԭ������</td>
				<td class=input id=HistoryRgtNoInfo><input class=code name=HistoryRgtNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="queryPastCaseInfo();" elementtype=nacessary></td>
			</tr>
		</table>
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCustomerInfo);">
			</td>
			<td class=titleImg>�ͻ���Ϣ</td>
		</tr>
	</table>
	<div id= "divCustomerInfo" class=maxbox1 style= "display: ''">	
		
	<table class=common> 
		<tr class=common>
			<td class=title>����</td>
			<td class=input><input class="wid common" name=CustomerName onkeydown="selectUser(this);" elementtype=nacessary maxlength=50></td>
			<td class=title>��������</td>
			<td class=input><input class=coolDatePicker dateFormat=short name=Birthday onkeydown="selectUser(this);" elementtype=nacessary onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			<td class=title>Ա����</td>
			<td class=input><input class="wid common" name=EmployeNo onkeydown="selectUser(this);" maxlength='30'></td>
		</tr>
		<tr class=common>
			<td class=title>֤������</td>
			<td class=input><Input class="wid common" name=IDNo onkeydown="selectUser(this);" elementtype=nacessary maxlength=30>
			<td class=title>֤������</td>
			<td class=input><input class=codeno name=IDType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1],null,null,null,'1',null);" maxlength=6><input class=codename name=IDTypeName maxlength=50 elementtype=nacessary readonly></td>
			<td class=title>�Ա�</td>
			<td class=input><input class=codeno name=Gender readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('',[this,GenderName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('',[this,GenderName],[0,1],null,null,null,'1',null);" maxlength=2><input class=codename name=GenderName  elementtype=nacessary readonly></td>  
		</tr>
		<tr class=common>
			<td class=title>������</td>
			<td class=input><input class="wid readonly" name=RgtNo readonly></td>
			<td class=title id=ErrorStation>������θ�</td>			
			<td class=input id=ErrorStationInfo><input class=codeno name=ErrorStation maxlength='6' style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="showCodeList('errorstation',[this,ErrorStationName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('errorstation',[this,ErrorStationName],[0,1],null,null,null,1);"><input class=codename name=ErrorStationName readonly elementtype=nacessary></td>
			<td class=title>�ֻ���</td>
			<td class=input><input class="wid common" name=TelPhone maxlength="11"></td>
		</tr>
		<tr class=common>
			<td class=title elementtype=nacessary>����ԭ��</td>
			<td class=input colspan="5"><textarea class=common name=ApplyRemarks id=ApplyRemarks cols="50" rows="2" maxlength=600 elementtype=nacessary></textarea>
		</tr>
	</table>	
	</div>	
	<input class=cssButton name='Report' value="��  ��" type=button onclick="saveSpecialCase();">
	<input class=cssButton name='AcceptCase' value="�����Ǽ�" type=button onclick="caseAccept();">

	<hr class=line>
	<table>
		<tr>
			<td>
				<input class=cssButton type=button name=RecordOver value="�������" onclick="caseOver();">
				<input class=cssButton value="��  ��" type=button onclick="goBack()">
			</td>
  	</tr>
  </table> 
		<input type=hidden name=CustomerNo>			<!--�ͻ���-->		
		<input type=hidden name=Operate>			<!--��������-->
		<input type=hidden name=AppntType>		<!--Ͷ��������-->	
		<input type=hidden name=SelfAcceptCom>		<!--Ĭ���������-->										
		<input type=hidden name=SelfAppntNo>		<!--Ͷ���˱���-->
		<input type=hidden name=SelfAppntName>		<!--Ͷ��������-->
		<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
