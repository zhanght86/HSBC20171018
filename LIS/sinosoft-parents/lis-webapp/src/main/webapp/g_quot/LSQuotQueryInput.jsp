<%
/***************************************************************
 * <p>ProName��LSQuotQueryInput.jsp</p>
 * <p>Title��ѯ�۲�ѯ</p>
 * <p>Description��ѯ�۲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-09-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.utility.SQLwithBindVariables"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	String tCurrentDate= PubFun.getCurrentDate();
	String tOperator = tGI.Operator;
	String tManageCom = tGI.ManageCom;
	String str = "select a.comgrade from ldcom a where 1=1 and a.comcode='"+"?ManageCom?"+"'";
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	sqlbv1.sql(str);
	sqlbv1.put("ManageCom",tGI.ManageCom);
	ExeSQL	tExeSQL = new ExeSQL();
	String tComGrade = tExeSQL.getOneValue(sqlbv1);
%>
<script>
	var tOperator = "<%=tOperator%>";//��ǰ��¼��
	var tManageCom = "<%=tManageCom%>";//��ǰ��¼����
	var tCurrentDate = "<%=tCurrentDate%>";
	var tComGrade = "<%=tComGrade%>";
	var tQuotNo = "";
	var tQuotBatNo = "";
	var tQuotType = "";
</script>
<html>
<head>
	<title>ѯ�۲�ѯ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotQueryInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<%@include file="./LSQuotQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LSQuotCombiPrintSave.jsp" target=fraSubmit>
	<div style="display: none">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanVirtualGrid" ></span>
				</td>
			</tr>
		</table>
	</div>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo);">
			</td>
			<td class=titleImg>��ѯ����</td>
		</tr>
	</table>
	<div id="divInfo" class=maxbox style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����/��Ŀ����</td>
				<td class=input><input class="wid common" name=CustomerName id=CustomerName></td>
				<td class=title>ѯ�ۺ�</td>
				<td class=input><input class="wid common" name=QuotNo id=QuotNo></td>
				<td class=title>ѯ������</td>
				<td class=input><input class=codeno name=QuotType id=QuotType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', 180);" onkeyup="return showCodeListKey('quottype', [this,QuotTypeName], [0,1], null, null, null, '1', 180);" readonly><input class=codename name=QuotTypeName id=QuotTypeName></td>
			</tr>
			<tr class=common>
				<td class=title>ѯ��״̬</td>
				<td class=input><input class=codeno name=QuotState id=QuotState style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('quotstate', [this,QuotStateName], [0,1], null,'1', '1', '1', 180);" onkeyup="return showCodeListKey('quotstate', [this,QuotStateName], [0,1], null, '1', '1', '1', 180);" readonly><input class=codename name=QuotStateName id=QuotStateName></td>
				<td class=title>�ͻ�����</td>
				<td class=input><input class="wid common" name=Manager id=Manager></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=ApplyCom id=ApplyCom style="background:url(../co mmon/images/select--bg_03.png) no-repeat right center"ondblclick=" showCodeList('conditioncomcode',[this,ApplyComName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('conditioncomcode',[this,ApplyComName],[0,1],null,null,null,1);"><input class=codename name=ApplyComName id=ApplyComName readonly></td>	
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=ApplyStartDate onClick="laydate({elem: '#ApplyStartDate'});" id="ApplyStartDate"><span class="icon"><a onClick="laydate({elem: '#ApplyStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=ApplyEndDate onClick="laydate({elem: '#ApplyEndDate'});" id="ApplyEndDate"><span class="icon"><a onClick="laydate({elem: '#ApplyEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>����ͨ������</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=UWStartDate onClick="laydate({elem: '#UWStartDate'});" id="UWStartDate"><span class="icon"><a onClick="laydate({elem: '#UWStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title>����ͨ��ֹ��</td>
				<td class=input><input class="coolDatePicker" dateFormat="short" name=UWEndDate onClick="laydate({elem: '#UWEndDate'});" id="UWEndDate"><span class="icon"><a onClick="laydate({elem: '#UWEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
				<td class=title></td>
				<td class=input></td>
			</tr>			
		</table>
	</div>
	<input class=cssButton type=button value="��  ѯ" onclick="queryClick();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuotInfo);">
			</td>
			<td class=titleImg>ѯ����Ϣ�б�</td>
		</tr>
	</table>
	<div id="divQuotInfo"  style="display: '';text-align:center;">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQuotGrid"></span>
				</td>
			</tr>
		</table>
		<table align=center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
		</table>
	</div>
	<input class=cssButton type=button value="ѯ����ϸ" onclick="showQuotInfo();">
	<input class=cssButton type=button id=enterQuest name=enterQuest value="�������ѯ" onclick="goQuestion();" style="display: none">
	<input class=cssButton type=button id=UWDesc name=UWDesc value="�ܹ�˾�������" onclick="goUWDesc();" style="display: none">
	<input class=cssButton type=button id=printButton name=printButton value="��ӡѯ�۵�" onclick="printInquiry('pdf');" style="display: none">
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOfferInfo);">
			</td>
			<td class=titleImg>������Ϣ�б�</td>
		</tr>
	</table>
	<div id="divOfferInfo"   style="display: '';text-align:center;">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOfferGrid"></span>
				</td>
			</tr>
		</table>
		<table align=center>
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
		</table>
	</div>
	<input class=cssButton type=button value="������ϸ" onclick="showOfferInfo();">
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
