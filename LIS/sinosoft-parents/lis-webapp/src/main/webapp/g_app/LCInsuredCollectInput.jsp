<%
/***************************************************************
 * <p>ProName��LCInsuredCollectInput.jsp</p>
 * <p>Title��������Ϣ���ܲ�ѯ</p>
 * <p>Description��������Ϣ���ܲ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tGrpPropNo = request.getParameter("GrpPropNo");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>";
	var tOperator = "<%=tGI.Operator%>";
	var tGrpPropNo = "<%=tGrpPropNo%>";

</script>
<html>
<head >
	<title>�������˻�����Ϣ</title>
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
	<script src="./LCInsuredCollectInput.js"></script>
	<%@include file="./LCInsuredCollectInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm method=post action="" target=fraSubmit>
		<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQueryInfo);">
			</td>
			<td class=titleImg>�������˻�����Ϣ</td>
		</tr>
	</table>
	
	<div id="divQueryInfo" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanQueryScanGrid" ></span>
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
	
	<div id=divInsuredInfo99 style="display: ''">
		<input class=cssButton type=button value="��  ��" onclick="window.close();">
	</div>
		
	<!--������Ϣ-->
	<input type=hidden name=Operate>
	<input type=hidden id="GrpPrtno" name="GrpPropNo" value="<%=tGrpPropNo%>"> 
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
