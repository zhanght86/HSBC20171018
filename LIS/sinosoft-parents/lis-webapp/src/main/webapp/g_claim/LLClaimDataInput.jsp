<%
/***************************************************************
 * <p>ProName��LLClaimDataInput.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
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
	String mType = request.getParameter("Mode");	
	String mRptNo = request.getParameter("RptNo");
	String mCustomerNo = request.getParameter("CustomerNo");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
	var mCurrentDate = "<%=mCurrentDate%>";
	var mRptNo = "<%=mRptNo%>";
	var mCustomerNo = "<%=mCustomerNo%>";
	var mType = "<%=mType%>";
</script>
<html>
<head>
	<title>��������</title>
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
	<script src="./LLClaimDataInput.js"></script>
	<%@include file="./LLClaimDataInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="./LLClaimDataSave.jsp" target=fraSubmit>

	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divDocumentList);">
			</td>
			<td class=titleImg>��֤��Ϣ�б�</td>
		</tr>
	</table>
	<div id="divDocumentList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanDocumentListGrid"></span>
				</td>
			</tr>
		</table>

	</div>
	<input class=cssButton name=DocumentSave value="���浥֤" type=button onclick="saveDocument();">
	<input class=cssButton name=DocumentDelete value="ɾ����֤" type=button onclick="deleteDocument();">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divAddDocument);">
			</td>
			<td class=titleImg>���ӵ�֤��Ϣ</td>
		</tr>
	</table>
	<div id="divAddDocument" class=maxbox1 style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>������֤</td>
				<td class=input><input class="common wid" name=AffixName elementtype=nacessary></td>
				<td class=title><input class=cssButton type=button name=creatAffix value="���ɵ�֤"  onclick="createDocument();"></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>			      
			</tr>
		</table>
		
		<input class=cssButton value="��ӡ��������" type=button onclick="printClaimData();">
		<input class=cssButton value="��  ��" type=button onclick="goBack();">
	</div>
	
	<Input type=hidden name=CustomerNo>		<!--�ͻ���-->	
	<Input type=hidden name=RptNo>				<!--������-->	
	<Input type=hidden name=Operate>			<!--��������-->
	<br /><br /><br /><br />
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
