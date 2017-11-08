<%
/***************************************************************
 * <p>ProName��LLClaimMajorInput.jsp</p>
 * <p>Title���ش󰸼��ϱ�/¼��</p>
 * <p>Description���ش󰸼��ϱ�/¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
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
	String mRptNo = request.getParameter("RptNo");
	String mCustomerNo = request.getParameter("CustomerNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mRptNo = "<%=mRptNo%>";
	var mCustomerNo = "<%=mCustomerNo%>";
	var mMode = "<%=mMode%>";
</script>
<html>
<head>
	<title>�ش󰸼��ϱ�/¼��</title>
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
	<script src="./LLClaimMajorInput.js"></script>
	<%@include file="./LLClaimMajorInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimMajorSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divMajorInfo);">
			</td>
			<td class=titleImg>�ʱ���Ϣ</td>
		</tr>
	</table>
	<div id="divInputInfo" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������</td>
				<td class=input><input class="wid readonly" name=InputOperator readonly></td>
				<td class=title>��������</td>
				<td class=Input ><Input class="wid readonly" dateFormat="short" name=InputDate verify="��������|NOTNULL&DATE&LEN=10" maxlength="10" readonly></td>
				<td class=title>�������</td>
				<td class=input><input class=codeno name=InputCom readonly><input class=codename name=InputComName elementtype=nacessary readonly></td>
			</tr>
			<tr class=common>
				<td class=title>�������(������ƻ�)<font color=red>*</font></td>
				<td class=input colspan=5>
					<textarea class=common id=InputRemarks name=InputRemarks cols="60" rows="4" maxlength=200 readonly></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="divInputRepInfo" style="display: none">
		
		<table>
			<tr>
				<td class=common> <IMG  src= "../common/images/butCollapse.gif" style= "cursor:hand;" OnClick= "showPage(this,divReturnSubmit);"></ td>
				<td class=titleImg> �ظ���Ϣ </ td>
			</tr>
		</table>
		<div id="divReturnSubmit" class=maxbox1 style="display: ''">
			<div id="divBranchInfo" style="display: none">
				<table class=common>
					<tr class=common>
						<td class=title>������</td>
						<td class=input><input class="wid readonly" name=InputRepOperator readonly></td>
						<td class=title>��������</td>
						<td class=Input ><Input class="wid readonly" dateFormat="short" name=InputRepDate maxlength="10" readonly></td>
						<td class=title>�������</td>
						<td class=input><input class=codeno name=InputRepCom readonly><input class=codename name=InputRepComName readonly></td>
					</tr>
					<tr class=common>
						<td class=title>�ֹ�˾���<font color=red>*</font></td>
						<td class=input colspan=5>
							<textarea class=common name=InputRepRemarks id=InputRepRemarks cols="60" rows="4" maxlength=2000 readonly></textarea>
						</td>
					</tr>
				</table>
			</div>
			<div id="divBranchInfo1" style="display: none">
				<table class=common>
					<tr class=common>
						<td class=title>������</td>
						<td class=input><input class="wid common" name=InputRepOperator1 readonly></td>
						<td class=title>��������</td>
						<td class=Input ><Input class=coolDatePicker dateFormat="short" name=InputRepDate1 maxlength="10" readonly onClick="laydate({elem: '#InputRepDate1'});" id="InputRepDate1"><span class="icon"><a onClick="laydate({elem: '#InputRepDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
						<td class=title>�������</td>
						<td class=input><input class=codeno name=InputRepCom1 readonly><input class=codename name=InputRepComName1 readonly></td>
					</tr>
					<tr class=common>
						<td class=title>�ܹ�˾���<font color=red>*</font></td>
						<td class=input colspan=5>
							<textarea class=common name=InputRepRemarks1 id=InputRepRemarks1 cols="60" rows="4" maxlength=2000 readonly></textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>	
    	
	<div id="divReturnButton" style="display: ''">
		<input class=cssButton name=reportMajor value="��  ��" type=button onclick="majorReport();">
		<input class=cssButton name=printCase value="��ӡ�ش󰸼�" type=button onclick="casePrint();">
		<input class=cssButton value="��  ��" type=button onclick="top.close();">
	</div>
	
	<div id="divReplyButtonB" style="display: none">
		<input class=cssButton value="����ȷ��" type=button onclick="majorApprove();">
		<input class=cssButton value="��  ��" type=button onclick="top.close();">
	</div>
	
	<Input type=hidden  name=RptNo>					<!--������-->
	<Input type=hidden  name=CustomerNo>		<!--�ͻ���-->
	<Input type=hidden  name=Operate>				<!--��������-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
