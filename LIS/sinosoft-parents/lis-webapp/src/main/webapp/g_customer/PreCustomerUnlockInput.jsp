<%
/***************************************************************
 * <p>ProName��PreCustomerUnlockInput.jsp</p>
 * <p>Title��׼�ͻ���������</p>
 * <p>Description��׼�ͻ���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tTraceID = request.getParameter("TraceID");
	String tPreCustomerNo = request.getParameter("PreCustomerNo");
%>
<script>
	var tTraceID = "<%=tTraceID%>";
	var tPreCustomerNo = "<%=tPreCustomerNo%>";
</script>
<html>
<head >
	<title>׼�ͻ���Ϣ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./PreCustomerUnlockInput.js"></script>
	<%@include file="./PreCustomerUnlockInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./PreCustomerUnlockSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divPreCustomer);">
			</td>
			<td class=titleImg>׼�ͻ�������Ϣ</td>
		</tr>
	</table>
	<div id="divPreCustomer" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>׼�ͻ�����</td>
				<td class=input><input class="wid readonly" name=PreCustomerNo id=PreCustomerNo readonly></td>
				<td class=title>׼�ͻ�����</td>
				<td class=input colspan=3><input class="wid readonly" name=PreCustomerName id=PreCustomerName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>֤������</td>
				<td class=input><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
				<td class=title>֤������</td>
				<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
				<td class=title>��λ����</td>
				<td class=input><input class="wid readonly" name=GrpNatureName id=GrpNatureName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>��ҵ����</td>
				<td class=input><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
				<td class=title>��λ������</td>
				<td class=input><input class="wid readonly" name=SumNumPeople id=SumNumPeople readonly></td>
				<td class=title>Ԥ��Ͷ��������</td>
				<td class=input><input class="wid readonly" name=PreSumPeople id=PreSumPeople readonly></td>
			</tr>
			<tr class=common>
				<td class=title>Ԥ�Ʊ��ѹ�ģ(Ԫ)</td>
				<td class=input><input class="wid readonly" name=PreSumPrem id=PreSumPrem readonly></td>
				<td class=title>�����ϼ��ͻ�</td>
				<td class=input><input class="wid readonly" name=UpCustomerName id=UpCustomerName readonly></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=SaleChannelName id=SaleChannelName readonly></td>
			</tr>
			<tr class=common>
				<td class=title>��λ��ַ</td>
				<td class=input colspan=5><input class="wid readonly" name=DetailAddress id=DetailAddress readonly></td>
			</tr>
			<tr class=common>
				<td class=title>��˾���</td>
				<td class=input colspan=5><textarea cols=76 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLink);">
			</td>
			<td class=titleImg>��Ҫ��ϵ����Ϣ</td>
		</tr>
	</table>
	<div id="divLink" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid readonly" name=LinkMan id=LinkMan readonly></td>
				<td class=title>��ϵ���ֻ�</td>
				<td class=input><input class="wid readonly" name=Mobile id=Mobile readonly></td>
				<td class=title>�칫�绰</td>
				<td class=input><input class="wid readonly" name=Phone id=Phone readonly></td>
			</tr>
			<tr class=common>
				<td class=title>��ϵ�˲���</td>
				<td class=input><input class="wid readonly" name=Depart id=Depart readonly></td>
				<td class=title>��ϵ��ְ��</td>
				<td class=input><input class="wid readonly" name=Post id=Post readonly></td>
				<td class=title>��ϵ������</td>
				<td class=input><input class="wid readonly" name=Email id=Email readonly></td>
			</tr>
		</table>
	</div>
	
	<div id="divMainSaler" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMainSalerGrid);">
				</td>
				<td class=titleImg>���ͻ�������Ϣ</td>
			</tr>
		</table>
		
		<div id="divMainSalerGrid"  class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ͻ�����</td>
					<td class=input><input class=codeno name=SalerCode id=SalerCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="queryManagerInfo();" verify="�ͻ�����|notnull"><input class=codename name=SalerName id=SalerName readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="divSaler" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSalerGrid);">
				</td>
				<td class=titleImg>�����ͻ�������Ϣ<font color=red>����ͻ�����ʱ��¼�������ͻ�������Ϣ��</font></td>
			</tr>
		</table>
		<div id="divSalerGrid" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSalerGrid"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div>
		<input class=cssButton type=button value="׼�ͻ�����" name="UnlockButton" onclick="unlockClick();">
		<input class=cssButton type=button value="�ͻ�������Ϣ�޸�" name="ModifyButton" onclick="modifyClick();">
		<input class=cssButton type=button value="��  ��" onclick="returnBack();">
	</div>
	
	<input type=hidden name=Operate id=Operate>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
