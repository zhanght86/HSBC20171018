<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptInput.jsp</p>
 * <p>Title��δ����ͻ���ѯ</p>
 * <p>Description��δ����ͻ���ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String mCurrentDate = PubFun.getCurrentDate();
	String mGrpRgtNo = request.getParameter("GrpRgtNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var mOperator = "<%=tGI.Operator%>";
	
	var mCurrentDate = "<%=mCurrentDate%>";
	var mGrpRgtNo = "<%=mGrpRgtNo%>";	
	var mMode = "<%=mMode%>";	
</script>
<html>
<head>
	<title>����Ǽ�¼�����</title>
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
	<script src="./LLClaimNoAcceptInput.js"></script>
	<%@include file="./LLClaimNoAcceptInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimNoAcceptSave.jsp" target=fraSubmit>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divNoAcceptList);">
			</td>
			<td class=titleImg>δ����ͻ���Ϣ�б�</td>
		</tr>
	</table>
	<div id="divNoAcceptList" style="display: ''">
		<table class=commontop>
			<tr class=commontop>
				<td text-align: left colSpan=1>
					<span id="spanNoAcceptListGrid"></span>
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
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divNoAcceptInfo);">
			</td>
			<td class=titleImg>δ����ͻ���Ϣ</td>
		</tr>
	</table>
	<div id="divNoAcceptInfo" class=maxbox1 style="display: ''">
		<input class=cssButton name=addClickButton value="��  ��" type=button onclick="addClick();">
		<input class=cssButton name=modifyClickButton value="��  ��" type=button onclick="modifyClick();">
		<input class=cssButton name=deleteClickButton value="ɾ  ��" type=button onclick="deleteClick();">
		<input class=cssButton name=deleteAllButton value="ɾ��ȫ��δ����ͻ�" type=button onclick="deleteAll();">		
		<table class=common> 
			<tr class=common>
				<td class=title>����</td>
				<td class=input><input class="wid common" name=CustomerName verify="����|NOTNULL" elementtype=nacessary maxlength=50></td>
				<td class=title>�Ա�</td>
				<td class=input><input class=codeno name=Gender verify="�Ա�|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,GenderName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('sex',[this,GenderName],[0,1],null,null,null,'1',null);" maxlength=1 readonly><input class=codename name=GenderName  elementtype=nacessary readonly></td>  
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=Birthday verify="��������|NOTNULL" elementtype=nacessary onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
			</tr>
			<tr class=common>
				<td class=title>֤������</td>
				<td class=input><input class=codeno name=IDType verify="֤������|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1],null,null,null,'1',null);" maxlength=50 readonly><input class=codename name=IDTypeName maxlength=2  elementtype=nacessary readonly></td>				
				<td class=title>֤������</td>
				<td class=input><Input class="wid common" name=IDNo verify="֤������|NOTNULL" onblur="analysisIDNo(this);" elementtype=nacessary maxlength=20>
				<td class=title></td>
				<td class=input></td>  
			</tr>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid common" name=AppAmnt elementtype=nacessary verify="������|notnull&num&value>0"></td>
				<td class=title>��Ʊ��</td>
				<td class=input><input class="wid common" name=BillCount elementtype=nacessary verify="��Ʊ��|notnull&INT&value>=1&value<=10000"></td>
				<td class=title>Ӱ�����</td>
				<td class=input><input class="wid common" name=ScanCount verify="Ӱ�������|INT&value>=0&value<=10000"></td>
			</tr>
			<tr class=common>
				<td class=title>δ����ԭ��</td>				
				<td class=input colspan="5"><textarea class=common name=NoAcceptReasonName verify="δ����ԭ��|NOTNULL"  ondblclick="return showCodeList('noaceeptreason',[this,NoAcceptReason],[1,0],null,null,null,'1',300);" cols="50" rows="3" maxlength=200  style = {background:lightblue}></textarea><input type=hidden name=NoAcceptReason></td>
			</tr>			
		</table>
	</div>
	<input class=cssButton name='Report' value="��  ��" type=button onclick="goBack();">	
	<Input type=hidden  name=Operate>				<!--��������-->
	<input type=hidden name=GrpRgtNo>				<!--�������κ�-->
	<input type=hidden name=CustomerNo>			<!--�������κ�-->	
	<input type=hidden name=RgtNo>			<!--�������κ�-->	
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>

