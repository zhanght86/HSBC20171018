<%
/***************************************************************
 * <p>ProName��LLClaimQueryInput.jsp</p>
 * <p>Title��ϵͳ����Ͷ���˲�ѯ</p>
 * <p>Description��ϵͳ����Ͷ���˲�ѯ/p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import = "com.sinosoft.utility.*"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	StrTool tStrTool = new StrTool();
	String mGrpRgtNo = tStrTool.unicodeToGBK(request.getParameter("GrpRgtNo"));
	String mAppntNo = tStrTool.unicodeToGBK(request.getParameter("AppntNo"));
	String mCustomerName = tStrTool.unicodeToGBK(request.getParameter("CustomerName"));
	String mBirthday = tStrTool.unicodeToGBK(request.getParameter("Birthday"));
	String mEmpNo = tStrTool.unicodeToGBK(request.getParameter("EmpNo"));
	String mIDNo = tStrTool.unicodeToGBK(request.getParameter("IDNo"));
%>
<script>
	var mManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var mGrpRgtNo = "<%=mGrpRgtNo%>";		
	var mAppntNo = "<%=mAppntNo%>";	
	var mCustomerName = "<%=mCustomerName%>";
	var mBirthday = "<%=mBirthday%>";
	var mEmpNo = "<%=mEmpNo%>";
	var mIDNo = "<%=mIDNo%>";
</script>
<html>
<head>
	<title>ϵͳ�����˲�ѯ</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LLClaimQueryInput.js"></script>
	<%@include file="./LLClaimQueryInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm id=fm method=post action="" target=fraSubmit>

	<table>
		<tr>
    	<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divGrpAppntList);">
			</td>
			<td class=titleImg>��������Ϣ�б�</td>
		</tr>
	</table> 
	<div id="divGrpAppntList" style= "display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanCustomerListGrid"></span> 
				</td>
			</tr>
		</table>
		<center>
			<input class=cssbutton90 value="��  ҳ" type=button onclick="turnPage1.firstPage();">
			<input class=cssbutton91 value="��һҳ" type=button onclick="turnPage1.previousPage();">
			<input class=cssbutton92 value="��һҳ" type=button onclick="turnPage1.nextPage();">
			<input class=cssbutton93 value="β  ҳ" type=button onclick="turnPage1.lastPage();">
		</center>
	</div>	
	
	<input class=cssbutton value="ѡ  ��" type=button onclick="returnSelect();">
	<input class=cssbutton value="��  ��" type=button onclick="top.close();"> 
	<input type=hidden name=Operate id=Operate> 	 	 <!--��������-->
 </form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
