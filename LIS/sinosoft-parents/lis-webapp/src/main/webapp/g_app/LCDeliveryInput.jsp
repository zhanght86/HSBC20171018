<%
/***************************************************************
 * <p>ProName��LCDeliveryInput.jsp</p>
 * <p>Title�����͵Ǽ�</p>
 * <p>Description�����͵Ǽ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>

<script>
	var tManageCom = "<%=tGI.ManageCom%>";//��¼��½����
	var tOperator = "<%=tGI.Operator%>";
</script>
<html>
<head>
	<title>���͵Ǽ�</title>
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
	<script src="./LCDeliveryInput.js"></script>
	<%@include file="./LCDeliveryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">

<form name=fm id=fm method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�б�����</td>
				<td class=input><input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);" onkeyup="return showCodeListKey('comcodeall',[this,EscanComName],[0,1],null,'1 and comgrade=#03#',1,1);"><input class=codename name=EscanComName readonly=true></td>
				<td class=title>Ͷ������</td>
				<td class=input><input class="wid common" name=GrpPropNo id=GrpPropNo></td>
				<td class=title>���屣����</td>
				<td class=input><input class="wid common" name=GrpContNo id=GrpContNo></td>
			</tr>
			<tr class=common>
				<td class=title>��ӡ����</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PrintStartDate verify="��ӡ����|date" onClick="laydate({elem: '#PrintStartDate'});" id="PrintStartDate"><span class="icon"><a onClick="laydate({elem: '#PrintStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>��ӡֹ��</td>
				<td class=input><Input class="coolDatePicker" dateFormat="short"  name=PrintEndDate verify="��ӡֹ��|date" onClick="laydate({elem: '#PrintEndDate'});" id="PrintEndDate"><span class="icon"><a onClick="laydate({elem: '#PrintEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>Ͷ��������</td>
				<td class=input><input class="wid common" name=GrpName id=GrpName></td>
			</tr>
			<tr class=common>
				<td class=title>�Ǽ�״̬</td>
				<td class=input><input class=codeno name=ContState id=ContState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick=" showCodeList('registerflag',[this,ContStateName],[0,1],null,null,null,1,null);" onkeyup="return showCodeListKey('registerflag',[this,ContStateName],[0,1],null,null,null,1,null);"><input class=codename name=ContStateName readonly=true></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td> 
			</tr>
		</table>
		<input class=cssButton  type=button value="��  ѯ" onclick="queryClick();">
	</div>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>��ѯ���</td>
		</tr>
	</table>
	<div id="divQueryInfo" style="display: ''">	
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanContInfoGrid"></span>
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
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSaveInfo);">
			</td>
			<td class=titleImg>������Ϣ</td>
		</tr>
	</table>
	<div id="divSaveInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>�Ǽǽ���</td>
				<td class=input><input class=codeno name=RegisterPassFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=RegisterPassFlag verify="�Ǽǽ���|notnull" ondblclick="return showCodeList('registerclu',[this,RegisterPassFlagName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('registerclu',[this,RegisterPassFlagName],[0,1],null,null,null,'1',null);"><input class=codename name=RegisterPassFlagName  elementtype=nacessary></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
		</table>   
	</div>	
	<div id="divQuery1Info" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=TransferType id=TransferType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('sendtype',[this,TransferTypeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('sendtype',[this,TransferTypeName],[0,1],null,null,null,'1',null);"><input class=codename name=TransferTypeName></td>
				<td class=title>��ݹ�˾</td>
				<td class=input><input class="wid common" name=ExpressCorpName id=ExpressCorpName verify="��ݹ�˾|len<40"></td>
				<td class=title>��ݵ���</td>
				<td class=input><input class="wid common" name=ExpressNo id=ExpressNo verify="��ݵ���|len<25"></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short"  name=TransferDate verify="��������|date" onClick="laydate({elem: '#TransferDate'});" id="TransferDate"><span class="icon"><a onClick="laydate({elem: '#TransferDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>�Ǽ���</td>
				<td class=input><input class="wid readonly" name=Register value=<%=tGI.Operator%>></td>
				<td class=title>��ݵǼ�����</td>
				<td class=input><input class="wid readonly" name=ExpressDate value=<%=PubFun.getCurrentDate()%>></td>
			</tr>
		</table>   
	</div>
	<div id="divQuery2Info" style="display: none">
		<table class=common>
			<tr class=common>
				<td class=title colspan=6>���ϸ�ԭ��</td>
			</tr>
			<tr class=common>
				<td class=input colspan=6><textarea cols=80 rows=3 name=Reason id=Reason></textarea><span style='color: red'>*</span></td>
			</tr>
		</table>   
	</div>
	<input class=cssButton  type=button value="��  ��" onclick="saveClick();">
	<input type=hidden name=Operate>
			
</form>
<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
