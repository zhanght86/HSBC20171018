<%
/***************************************************************
 * <p>ProName��LLClaimQuestionQueryInput.jsp</p>
 * <p>Title���������ѯ</p>
 * <p>Description���������ѯ</p>
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
	<title>�������ѯ</title>
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
	<script src="./LLClaimQuestionQueryInput.js"></script>
	<%@include file="./LLClaimQuestionQueryInit.jsp"%>
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
					<td class=title>�������κ�</td>
					<td class=input><input class="wid common" name=GrpRgtNo id=GrpRgtNo></td>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=RgtNo id=RgtNo></td>							
					<td class=title>������ת��</td>
					<td class=input><input class="wid common" name=PageNo id=PageNo></td> 
				</tr>
				<tr class=common>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid common" name=GrpName id=GrpName></td>  
					<td class=title>����������</td>
					<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>			
				</tr>
				<tr class=common>
					<td class=title>�������������</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=InputStartDate onClick="laydate({elem: '#InputStartDate'});" id="InputStartDate"><span class="icon"><a onClick="laydate({elem: '#InputStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>���������ֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=InputEndDate onClick="laydate({elem: '#InputEndDate'});" id="InputEndDate"><span class="icon"><a onClick="laydate({elem: '#InputEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>  
					<td class=title>����״̬</td>
					<td class=input><input class=codeno name=State style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('clmflag',[this,StateName],[0,1],null,'1','1',1);" onkeyup="showCodeListKey('clmflag',[this,StateName],[0,1],null,'1','1',1);"><input class=codename name=StateName readonly></td>			
				</tr>
				<tr class=common>
					<td class=title>�������������</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=DealStartDate onClick="laydate({elem: '#DealStartDate'});" id="DealStartDate"><span class="icon"><a onClick="laydate({elem: '#DealStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
					<td class=title>���������ֹ��</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=DealEndDate onClick="laydate({elem: '#DealEndDate'});" id="DealEndDate"><span class="icon"><a onClick="laydate({elem: '#DealEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>  
					<td class=title>��������</td>
					<td class=input><input class=codeno name=AcceptCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,AcceptComName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('managecom',[this,AcceptComName],[0,1],null,null,null,1);"><input class=codename name=AcceptComName readonly></td>			
				</tr>								
			</table>
			<input class=cssButton name=Query  value="��  ѯ" type=button onclick="queryClick();">
			<input class=cssButton name=InitClick value="��  ��" type=button onclick="initQueryInfo();">	
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divQuestiontList);">
			</td>
			<td class=titleImg>������б�</td>
		</tr>
	</table>
	<div id="divQuestiontList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuestionListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
			<input class=cssButton type=button value="��������" onclick="exportData();">			
		</center>
	</div>			
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
