<%
/***************************************************************
 * <p>ProName��LLClaimSurveyAppinput.jsp</p>
 * <p>Title������¼������</p>
 * <p>Description������¼������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom =tGI.ManageCom;
	String tOperator = tGI.Operator;
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //��¼��½����
	var tOperator = "<%=tOperator%>";  //��¼������
</script>
<html>
<head>
	<title>����¼������</title>
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
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimSurveyAppInput.js"></script>
	<%@include file="./LLClaimSurveyAppInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
	<form name=fm id=fm method=post action="./LLClaimSurveyAppSave.jsp" target=fraSubmit>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divLCQues);">
				</td>
				<td class=titleImg>�����������б�</td>
			</tr>
		</table>
		<div id="divLCQues" style="display:''">
  		<table class=common>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanLLSurveyTaskGrid"></span>
					</td>
				</tr>
			</table> 
			<center>
			<Input class=cssButton90 value="��  ҳ" type=button onclick="turnPage1.firstPage();">
			<Input class=cssButton91 value="��һҳ" type=button onclick="turnPage1.previousPage();">
			<Input class=cssButton92 value="��һҳ" type=button onclick="turnPage1.nextPage();">
			<Input class=cssButton93 value="β  ҳ" type=button onclick="turnPage1.lastPage();">       
   		</center>
		</div>
		    <!--������-->
		<Input type=hidden  name=Operate> 	 	 <!--��������-->
		<Input type=hidden  name=InqNo> 	 	 
		<Input type=hidden  name=RgtNo> 	 	 
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>