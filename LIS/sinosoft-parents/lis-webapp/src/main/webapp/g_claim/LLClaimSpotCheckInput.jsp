<%
/***************************************************************
 * <p>ProName��LLClaimSpotCheckInput.jsp</p>
 * <p>Title�����������ά��</p>
 * <p>Description�����������ά��</p>
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
	<script src="./LLClaimSpotCheckInput.js"></script>
	<%@include file="./LLClaimSpotCheckInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimSpotCheckSave.jsp" target=fraSubmit>
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
					<td class=title>�û���</td>
					<td class=input><input class="wid common" name=QueryUserCode></td>
					<td class=title>������</td>
					<td class=input><input class=codeno name=QueryManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,QueryManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=QueryManageComName readonly></td>
					<td class=title>������</td>      
					<td class=input><input class="wid common" name=QueryGrpContNo></td>
				</tr>
				<tr class=common>
					<td class=title>Ͷ��������</td>      
					<td class=input><input class="wid common" name=QueryGrpName></td>
					<td class=title>��������</td>
					<td class=input><input class="wid common" name=QueryRiskName></td>
					<td class=title>�⸶���(>=)</td>      
					<td class=input><input class="wid common" name=QueryRealPay></td>
				</tr>
				<tr class= common>
					<td class=title>������ʽ</td>
					<td class=input><input class=codeno readonly name=QueryGiveType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('casegivetype',[this,CheckGiveTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('casegivetype',[this,CheckGiveTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=QueryGiveTypeName readonly ></td>
					<td class=title>��Ч����</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryStartDate onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>��Чֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=QueryEndDate onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				</tr>
			</table>  
			<input value="��  ѯ" class=cssButton type=button name=querybutton onclick="queryClaimRuleInfo();">
		</div>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divRuleList);">
				</td>
				<td class=titleImg>�ⰸ�������б�</td>
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
				<td class=titleImg>��������Ϣ</td>
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
				<td class=title>�������</td>
				<td class=input><input class=codeno readonly name=CheckType verify="�������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('spotchecktype',[this,CheckTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('spotchecktype',[this,CheckTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=CheckTypeName readonly ></td>
				<td class=title>����û���</td>				     
				<td class=input><input class=codeno readonly name=CheckUserCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('claimusercode',[this,CheckUserName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('claimusercode',[this,CheckUserName],[0,1],null,null,null,'1',null);"><input class=codename name=CheckUserName readonly ></td>
				<td class=title>������</td>      
				<td class=input><input class=codeno name=CheckManageCom verify="������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);" onkeyup="showCodeListKey('conditioncomcode',[this,CheckManageComName],[0,1],null,'1 and comcode like #'+mManageCom+'%# and comgrade=#02#','1',1);"><input class=codename name=CheckManageComName elementtype=nacessary readonly></td>
			</tr>
			<tr class=common>
				<td class=title>������</td>      
				<td class=input><input class="wid common" name=CheckGrpContNo></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class=codeno name=CheckAppntNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showWarnInfo();"><input class=codename name=CheckAppntName onkeydown="QueryOnKeyDown(this);" maxlength='200'></td>				
				<td class=title>����</td>      
				<td class=input><input class=codeno name=CheckRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotrisk',[this,CheckRiskName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('quotrisk',[this,CheckRiskName],[0,1],null,null,null,'1',null);"><input class=codename name=CheckRiskName readonly ></td>
			</tr>
			<tr class=common>
        <td class=title>������</td>
				<td class=input><input class="wid common" name=CheckRate verify="������|notnull&num&value>0&value<=1" elementtype=nacessary></td>
				<td class=title>�⸶���(>=)</td>
				<td class=input><input class="wid common" name=CheckMoney verify="�⸶���|num"></td>
				<td class=title>������ʽ</td>      
				<td class=input><input class=codeno readonly name=CheckGiveType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('casegivetype',[this,CheckGiveTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('casegivetype',[this,CheckGiveTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=CheckGiveTypeName readonly ></td>
			</tr>
     	<tr class=common>
				<td class=title>�����������������Ƿ�һ��</td>
				<td class=input><input class=codeno readonly name=QueryBnfFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('trueflag',[this,QueryBnfFlagName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('trueflag',[this,QueryBnfFlagName],[0,1],null,null,null,'1',null);"><input class=codename name=QueryBnfFlagName readonly ></td>
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
