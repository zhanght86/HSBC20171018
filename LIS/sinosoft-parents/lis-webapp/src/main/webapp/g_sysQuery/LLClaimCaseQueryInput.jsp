<%
/***************************************************************
 * <p>ProName��LLClaimCaseQueryInput.jsp</p>
 * <p>Title���ⰸ��ѯ</p>
 * <p>Description���ⰸ��ѯ</p>
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
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>�ⰸ��ѯ</title>
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
	<script src="./LLClaimCaseQueryInput.js"></script>
	<%@include file="./LLClaimCaseQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>
  
  <div id=queryInfo style="display:''">
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
					<td class=title>������</td>
					<td class=input><input class="wid common" name=RgtNo id=RgtNo></td>						
					<td class=title>������</td>
					<td class=input><input class="wid common" dateFormat=short name=GrpContNo id=GrpContNo></td>	
				</tr>
				<tr class=common>								
					<td class=title>Ͷ��������</td>
					<td class=input><input class="wid common" name=GrpName id=GrpName></td>  
					<td class=title>����</td>
					<td class=input><input class=codeno name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotrisk',[this,RiskName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('quotrisk',[this,RiskName],[0,1],null,null,null,'1',null);"><input class=codename name=RiskName readonly ></td>
					<td class=title>����״̬</td>
					<td class=input><input class=codeno name=ClmState id=ClmState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmstate',[this,ClmStateName],[0,1],null,'1 ','1',1);" onkeyup="showCodeListKey('clmstate',[this,ClmStateName],[0,1],null,'1','1',1);"><input class=codename name=ClmStateName readonly></td>				       					
				</tr>
				<tr class=common>
					<td class=title>����������</td>
					<td class=input><input class="wid common" name=CustomerName></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDNo></td>  
					<td class=title>�������</td>
					<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>			       
				</tr>
				<tr class=common>
					<td class=title>������ˮ��</td>
					<td class=input><input class="wid common" name=PageNo></td>						
					<td class=title>��������</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=AccStartDate onClick="laydate({elem: '#AccStartDate'});" id="AccStartDate"><span class="icon"><a onClick="laydate({elem: '#AccStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>����ֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=AccEndDate onClick="laydate({elem: '#AccEndDate'});" id="AccEndDate"><span class="icon"><a onClick="laydate({elem: '#AccEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>			
				</tr>									
				<tr class=common>
					<td class=title>�᰸����</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>�᰸ֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title></td>
					<td class=input></td>				
				</tr>				
			</table>
			<input class=cssButton name='queryCase'  value="��  ѯ" type=button onclick="queryClick();">
			<input class=cssButton name=initClick value="��  ��" type=button onclick="initQueryInfo();">
			<input class=cssButton name='Report' value="������ϸ" type=button onclick="exportClick();">						
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseList);">
			</td>
			<td class=titleImg>�ⰸ�б�</td>
		</tr>
	</table>
	<div id="divLastCaseList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLastCaseListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</center>
	</div>
	<input class=cssButton name=CaseDetail value="�鿴������ϸ��Ϣ" type=button onclick="showBatchDetailInfo();">
	<input class=cssButton name=CaseDetail value="�鿴�ⰸ��ϸ��Ϣ" type=button onclick="showCaseDetailInfo();">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divLastCaseDetail);">
			</td>
			<td class=titleImg>�ⰸ��Ϣ</td>
		</tr>
	</table>
	<div id="divLastCaseDetail" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLastCaseDetailGrid"></span>
				</td>
			</tr>
		</table>
	</div>
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
	<input type=hidden name=SheetName>		
	<input type=hidden name=SheetTitle>	
	<input type=hidden name=SheetSql>	
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
