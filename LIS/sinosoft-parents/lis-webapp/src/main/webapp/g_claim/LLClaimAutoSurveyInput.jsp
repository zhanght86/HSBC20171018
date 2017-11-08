<%
/***************************************************************
 * <p>ProName��LLClaimAutoSurveyInput.jsp</p>
 * <p>Title�������Զ�����ά��</p>
 * <p>Description�������Զ�����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String mManageCom = tGI.ManageCom;
	String mOperator = tGI.Operator;
%>
<script>
	var mManageCom = "<%=mManageCom%>"; //��¼��½����
	var mOperator = "<%=mOperator%>";  //��¼������
</script>
<html>
<head>
	<title>���������ά��</title>
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
	<script src="./LLClaimAutoSurveyInput.js"></script>
	<%@include file="./LLClaimAutoSurveyInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimAutoSurveySave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,QueryHospital);">
				</td>
				<td class=titleImg>��ѯ����</td>
			</tr>
		</table> 
		<div id=divQueryInfo class=maxbox1 style="display:''">
			<table class=common>
				<tr class=common>  
					<td class=title>�������</td>
					<td class=input><input class=codeno name=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=QueryManageComName readonly></td>
					<td class=title>����</td>
					<td class=input><input class=codeno name=QueryRiskcode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('quotrisk',[this,QueryRiskName],[0,1],null,'1','1',1);" onkeyup="showCodeListKey('quotrisk',[this,QueryRiskName],[0,1],null,'1','1',1);"><input class=codename name=QueryRiskName readonly></td>
					<td class=title>��˽��(>=)</td>      
					<td class=input><input class="wid common" name=QueryUWMoney></td>
				</tr>
				<tr class=common>
					<td class=title>�⸶���(>=)</td>      
					<td class=input><input class="wid common" name=QueryPayMoney></td>
					<td class=title>��Ч����</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryStartDate onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>��Чֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryEndDate onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>  
			<input value="��  ѯ" class=cssButton name=querybutton type=button onclick="queryAutoRule();">
			<input class=cssButton name="initButton" value="��  ��" type=button onclick="initQueryInfo();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divRuleList);">
				</td>
				<td class=titleImg>�Զ���������б�</td>
			</tr>
		</table>  
		<div id="divRuleList" style= "display:''">    
			<table  class= common>
				<tr>
					<td text-align: left colSpan=1><span id="spanLLClaimRuleListGrid"></span></td>
				</tr>
			</table>
			<center>
				<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
				<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
				<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
				<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
				<input class=cssButton type=button name=exportbutton value="��������" onclick="exportData();">
			</center>
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divClaimRuleInfo);">
				</td>
				<td class=titleImg>�Զ����������Ϣ</td>
			</tr>
		</table>  
		<div id="divClaimRuleInfo" class=maxbox1 style= "display:''">       
		<div>
			<input class=cssButton name="addButton" value="��  ��" type=button onclick="addRule();">
			<input class=cssButton name="modifyButton" value="��  ��" type=button onclick="modifyRule();">
			<input class=cssButton name="deleteButton" value="ɾ  ��" type=button onclick="delteRule();">
			<input class=cssButton name="initButton" value="��  ��" type=button onclick="initRule();">
		</div>    
		<table class=common>
			<tr class=common>   
				<td class=title>������</td>
				<td class=input><input class=codeno name=CheckManageCom verify="������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=CheckManageComName elementtype=nacessary readonly></td>
				<td class=title>����</td>      
				<td class=input><input class=codeno name=CheckRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotrisk',[this,CheckRiskName],[0,1],null,'1','1','1',null);" onkeyup="return showCodeListKey('quotrisk',[this,CheckRiskName],[0,1],null,'1','1','1',null);"><input class=codename name=CheckRiskName readonly ></td>
				<td class=title>��˽��(>=)</td>				     
				<td class=input><input class="wid common" name=CheckAppPay verify="�⸶���|num" ></td>
			</tr>
			<tr class=common>
        <td class=title>�⸶���(>=)</td>
				<td class=input><input class="wid common" name=CheckClmPay verify="�⸶���|num" ></td>
				<td class=title>��Ч����</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckStartDate verify="��Ч����|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#CheckStartDate'});" id="CheckStartDate"><span class="icon"><a onClick="laydate({elem: '#CheckStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��Чֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=CheckEndDate verify="��Чֹ��|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#CheckEndDate'});" id="CheckEndDate"><span class="icon"><a onClick="laydate({elem: '#CheckEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>				
			</tr>
		</table>
	</div>

	<input type=hidden id="Operate" name=Operate > 
	<input type=hidden id="RuleNo" name=RuleNo > 
	<br /><br /><br /><br />
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
