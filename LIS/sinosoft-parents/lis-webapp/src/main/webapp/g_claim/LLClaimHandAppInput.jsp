<%
/***************************************************************
 * <p>ProName��LLClaimHandAppInput.jsp</p>
 * <p>Title��������ת������ҳ��</p>
 * <p>Description��������ת������ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-17
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
	<title>������ת������ҳ��</title>
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
	<script src="./LLClaimHandAppInput.js"></script>
	<%@include file="./LLClaimHandAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimHandAppSave.jsp" target=fraSubmit>
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
				<td class=title>������ת��</td>
				<td class=input><input class="wid common" name=QueryPageNo></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="showCodeList('managecom',[this,ManageComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('managecom',[this,ManageComName],[0,1],null,null,null,1);"><input class=codename name=ManageComName readonly></td>
				<td class=title></td>
				<td class=input><input class="wid common" type=hidden name=AppOperator  readonly></td>  
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AppStartDate onClick="laydate({elem: '#AppStartDate'});" id="AppStartDate"><span class="icon"><a onClick="laydate({elem: '#AppStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class=coolDatePicker dateFormat=short name=AppEndDate onClick="laydate({elem: '#AppEndDate'});" id="AppEndDate"><span class="icon"><a onClick="laydate({elem: '#AppEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
				<td class=title></td>
				<td class=input></td>     
			</tr>			
		</table>
		
		<input class=cssButton value="��  ѯ" type=button onclick="queryClick();">
		<input class=cssButton value="ѡ  ��" type=button onclick="returnSelect();">
	</div>
    	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divHandNoList);">
			</td>
			<td class=titleImg>������ת����Ϣ�б�</td>
		</tr>
	</table>
	<div id="divHandNoList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanHandNoListGrid"></span>
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
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divHandInfo);">
			</td>
			<td class=titleImg>������ת����Ϣ</td>
		</tr>
	</table>
	<div id="divHandInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title5>������ת��</td>
				<td class=input5><input class="wid readonly" name=PageNo readonly></td>
				<td class=title5>�ܹ�������</td>
				<td class=input5><input class="wid common" name=SumNum  verify="�ܹ�������|int"  elementtype=nacessary></td>    
			</tr>
			<tr class=common>
				<td class=title5>����˵��</td>
				<td class=input5 colspan="3"><textarea class=common name=Remark cols="50" rows="2" maxlength=200></textarea>
			</tr>
		</table>
		
		<input class=cssButton value="��  ��" type=button onclick="addPageNoClick();">
		<input class=cssButton value="��  ��" type=button onclick="modifyClick();">
		<input class=cssButton value="ɾ  ��" type=button onclick="deleteClick();">
		<input class=cssButton value="��  ��" type=button onclick="initPageNoInfo();">
		<input class=cssButton value="��  ��" type=button onclick="goBack();">		
	</div>	
	<br /><br /><br /><br /><br />
	<Input type=hidden  name=Operate> 	 	 <!--��������-->
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
