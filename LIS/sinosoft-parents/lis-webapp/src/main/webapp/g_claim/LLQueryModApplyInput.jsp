<%
/***************************************************************
 * <p>ProName��LLQueryModApplyInput.jsp</p>
 * <p>Title�������޸������ѯ</p>
 * <p>Description�������޸������ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ����
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = tGI.ManageCom;
	String tOperator = tGI.Operator;
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
</script>
<html>
<head>
	<title>�������β�ѯ����</title>
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
	<script src="LLQueryModApplyInput.js"></script>
	<%@include file="LLQueryModApplyInit.jsp"%>

</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action=""  target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuery);">
			</td>
			<td class=titleImg>�����޸Ĳ�ѯ����</td>
		</tr>
	</table>
	<div id="divQuery" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('conditioncomcode',[this,ManageComName],[0,1],null,'1 and comgrade=#02# and comcode like #'+ tManageCom +'%%#','1',1);" onkeyup="showCodeListKey('managecom',[this,ManageComName],[0,1],null,'1 and comgrade=#02# and comcode like #'+ tManageCom +'%%#','1',1);"><input class=codename name=ManageComName></td>
                <td class=title>��������</td>
				<td class=input><input class=codeno name=QueryRuleType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmruletype',[this,QueryRuleTypeName],[0,1],null,null,null,1);"><input class=codename name=QueryRuleTypeName></td>
				<td class=title>����</td>
				<td class=input><input class=codeno name=QueryRiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('quotrisk',[this,QueryRiskCodeName],[0,1],null,null,null,1);"><input class=codename name=QueryRiskCodeName></td>
			</tr>
			<tr class=common>
				<td class=title>�����޸���Ч����</td>
				<td class=input><input class=coolDatePicker name=QueryStartDate dateFormat="short" onClick="laydate({elem: '#QueryStartDate'});" id="QueryStartDate"><span class="icon"><a onClick="laydate({elem: '#QueryStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�����޸���Чֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndDate dateFormat="short" onClick="laydate({elem: '#QueryEndDate'});" id="QueryEndDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>״̬</td>
				<td class=input><input class=codeno name=ClmmodifyState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmmodifystate',[this,clmmodifystateName1],[0,1],null,null,null,1);" onkeyup="showCodeListKey('clmmodifystate',[this,clmmodifystateName1],[0,1],null,null,null,1);"><input class=codename name=clmmodifystateName1></td>				
			</tr>
			<tr class=common>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=QueryGrpName>
                <td class=title>������</td>
				<td class=input><input class="wid common" name=QueryGrpContNo>
			    <td class=title></td>
				<td class=input></td>			
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker name=QueryApplyDate dateFormat="short" onClick="laydate({elem: '#QueryApplyDate'});" id="QueryApplyDate"><span class="icon"><a onClick="laydate({elem: '#QueryApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker name=QueryEndApplyDate dateFormat="short" onClick="laydate({elem: '#QueryEndApplyDate'});" id="QueryEndApplyDate"><span class="icon"><a onClick="laydate({elem: '#QueryEndApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			    <td class=title></td>
				<td class=input></td>			
			</tr>
		</table>
		<input class=cssButton type=button name=querybutton value="��  ѯ" onclick="queryClick();">		
	</div>
	


	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divModItemGrid);">
			</td>
			<td class=titleImg>�����޸��б�</td>
		</tr>
	</table>
	<div id=divModItemGrid style="display:''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1><span id="spanQueryModItemGrid"></span></td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>

	
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divModifiyInfo);">
			</td>
			<td class=titleImg>�����޸���Ϣ</td>
		</tr>
	</table>
	<div id="divModifiyInfo" class=maxbox1 style="display: ''">	

	       
	    <table class=common>
			<tr class=common>
			    <td class=title>�����޸�ԭ��</td>
				<td class=input><input class=codeno name=ReasonType readonly><input class=codename name=ReasonTypeName  readonly></td>			
                <td class=title>��������</td>
				<td class=input><input class=codeno name=RuleType readonly><input class=codename name=RuleTypeName  readonly></td>
				<td class=title>���ֱ���</td>
				<td class=input><input class=codeno name=RiskCode readonly><input class=codename name=RiskCodeName  readonly></td>
			</tr>
			<tr class=common>
			    <td class=title>��������</td>
				<td class=input><input class=codeno name=AdjustDirection readonly><input class=codename name=AdjustDirectionName  readonly></td>
			    <td class=title id="UpAdjustRuleTitle">���ϵ�������</td>
			    <td class=input id="UpAdjustRuleInput"><input class=codeno name=UpAdjustRule readonly><input class=codename name=UpAdjustRuleName readonly></td>
			    <td class=title id="UpAdjustRateTitle">���ϵ�������</td>
			    <td class=input id="UpAdjustRateInput"><input class="wid common" name=UpAdjustRate  readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�����޸���Ч����</td>
				<td class=input><input class=coolDatePicker name=StartDate dateFormat="short" readonly onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�����޸���Чֹ��</td>
				<td class=input><input class=coolDatePicker name=EndDate dateFormat="short" readonly onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>								
			</tr>
			<tr class=common>
			    <td class=title>�������ԭ��</td>
			    <td class=input colspan="5"><textarea class=common name=Remark id=Remark verify="�������ԭ��|NOTNULL" cols="50" rows="4" maxlength=600 readonly></textarea></td>
			</tr>
		</table>
	</div>
	
<div id=divGrpCont style="display:none">	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAcceptGrp);">
			</td>
			<td class=titleImg>��ѡ�񱣵���Ϣ�б�</td>
		</tr>
	</table>
	<div id="divAcceptGrp" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanAcceptGrpGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</center>
	</div>
</div>	

	
<div id=divGrpContplan style="display:none">		
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divAcceptGrpa);">
			</td>
			<td class=titleImg>��ѡ�񱣵�������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divAcceptGrpa" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanAcceptGrpaGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage3.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage3.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage3.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage3.lastPage();">
		</center>
	</div>
</div>	

<div id=CheckInfo style="display:none">	
 	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCehckConclusion);">
			</td>
			<td class=titleImg>�������</td>
		</tr>
	</table>
		<div id=divCehckConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>���˽���</td>
				<td class=input><input class=codeno name=CheckConclusion  readonly><input class=codename name=CheckConclusionName readonly></td>
				<td class=title></td>	   		
	   			<td class=input></td>
	   			<td class=title></td>
	   			<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input colspan="5"><textarea name=CheckIdea id=CheckIdea readonly cols="60" rows="3" class=common maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>
</div>
	
<div id=ApproveInfo style="display:none">	
	 	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCehckConclusion);">
			</td>
			<td class=titleImg>�������</td>
		</tr>
	  	</table>
		<div id=divCehckConclusion class=maxbox1 style="display:''">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=ApproveConclusion readonly><input class=codename name=ApproveConclusionName readonly></td>
				<td class=title></td>	   		
	   			<td class=input></td>
	   			<td class=title></td>
	   			<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>�������</td>
				<td class=input colspan="5"><textarea name=ApproveIdea id=ApproveIdea readonly cols="60" rows="3" class=common maxlength=2000></textarea></td>
			</tr>
		</table>
	</div>
</div>	
	
	<input type=hidden name=Operate>
	<input type=hidden id="ApplyNo" name=ApplyNo > 
	<input type=hidden id="ApplyBatchNo" name=ApplyBatchNo>
	<input type=hidden id="GrpContNo" name=GrpContNo >
	<input type=hidden id="ApplyState" name=ApplyState >
</form>
<Br /><Br /><Br /><Br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
