<%
/***************************************************************
 * <p>ProName��LLClaimCaseMainInput.jsp</p>
 * <p>Title��������ѯ</p>
 * <p>Description��������ѯ</p>
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
	String mCustomerNo = request.getParameter("CustomerNo");
	String mRgtNo = request.getParameter("RgtNo");
	String mMode = request.getParameter("Mode");
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mOperator = "<%=tGI.Operator%>";
	var mMode = "<%=mMode%>";
	var mCustomerNo = "<%=mCustomerNo%>";
	var mRgtNo = "<%=mRgtNo%>";
</script>
<html>
<head>
	<title>������ѯ</title>
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
	<script src="./LLClaimQueryPolicyInput.js"></script>
	<%@include file="./LLClaimQueryPolicyInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>    
  	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divPolicyList);">
			</td>
			<td class=titleImg>�����б���Ϣ</td>
		</tr>
	</table>
	<div id="divPolicyList" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPolicyListGrid"></span>
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
	
	<input class=cssButton name=CaseDetail value="�鿴������ϸ��Ϣ" type=button onclick="showDetailInfo();">
	
	<input class=cssButton name=BackButton value="��  ��" type=button onclick="goBack();">
	
	<Input type=hidden  name=Operate id=Operate> 	 	 <!--��������-->
	<Input type=hidden  name=CustomerNo id=CustomerNo> 	 	 <!--�ͻ�����-->
	<Input type=hidden  name=RgtNo id=RgtNo> 
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span>
</body>
</html>
