<%
/***************************************************************
 * <p>ProName��LCInsuredQueryInput.jsp</p>
 * <p>Title���������˲�ѯ</p>
 * <p>Description���������˲�ѯ</p>
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
	<title>�������˲�ѯ��ѯ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LCInsuredQueryInput.js"></script>
	<%@include file="./LCInsuredQueryInit.jsp"%>
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
					<td class=title>Ͷ��������</td>
					<td class=input><input class="wid common" name=AppntName id=AppntName></td> 
					<td class=title>Ͷ������</td>
					<td class=input><input class="wid common" name=GrpAppNo id=GrpAppNo></td>
					<td class=title>������</td>
					<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>						
				</tr>
				<tr class=common>
					<td class=title>����������</td>
					<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid common" name=IDNo id=IDNo></td>  
					<td class=title>��������</td>
					<td class=input><input class=coolDatePicker dateFormat=short name=BirthDay onClick="laydate({elem: '#BirthDay'});" id="BirthDay"><span class="icon"><a onClick="laydate({elem: '#BirthDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>			
				</tr>
			</table>
			<input class=cssButton name=Query  value="��  ѯ" type=button onclick="queryClick();">
			<input class=cssButton name=InitClick value="��  ��" type=button onclick="initQueryInfo();">	
		</div>
	</div>  
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInsuredList);">
			</td>
			<td class=titleImg>���������б�</td>
		</tr>
	</table>
	<div id="divInsuredList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanLCInsuredListGrid"></span>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divMainInsuredList);">
			</td>
			<td class=titleImg>��/�����������б�</td>
		</tr>
	</table>
	<div id="divMainInsuredList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanMainInsuredListGrid"></span>
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
	<input class=cssButton name=PolicyQuery value="������ѯ" type=button onclick="showInsuredLCPol();">
	<input class=cssButton name=EdorQuery value="��ȫ��ѯ" type=button onclick="showInsuredLCEdor();">
	<input class=cssButton name=ClaimQuery value="�ⰸ��ѯ" type=button onclick="showOldCase();">			
	
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
</form>
<br /><br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
