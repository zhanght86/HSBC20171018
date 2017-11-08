<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

	<%
		//程序名称：PDSugIncomeDataInput.jsp
		//程序功能：收益数据定义
		//创建日期：2011-10-19
		//创建人  ：qihe
		//更新记录：  更新人    更新日期     更新原因/内容
	%>

	<head>
		<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
		<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
		<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
		<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
		<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
		<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		<link rel="stylesheet" type="text/css"
			href="../common/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="../common/themes/icon.css">
		<script type="text/javascript">
	 	var riskcode = '<%=request.getParameter("riskcode")%>';
		var contOpt = '<%=request.getParameter("contopt")%>';
	 	function tabMoveTo(num){
			if(num=='1'){
				window.location.href= './PDSugIncomeDataInput.jsp?riskcode='+riskcode + '&contopt=' + contOpt;
			}else if(num=='2'){
				window.location.href= './PDSugIncomeTabHeadInput.jsp?riskcode='+riskcode + '&contopt=' + contOpt;
			}
			
		}
		</script>
		<SCRIPT src="PDSugIncomeData.js"></SCRIPT>
		<script src="../common/javascript/jquery.js"></script>
		<script src="../common/javascript/jquery.easyui.min.js"></script>
		<%@include file="PDSugIncomeDataInit.jsp"%>
		<STYLE>
#changeRiskPlan a {
					float: left;
			background: url("../common/images/tableft1.gif") no-repeat left top;
			margin: 0;
			padding: 0 0 0 4px;
			text-decoration: none;
		}
		
#changeRiskPlan a span {
					float: left;
			display: block;
			background: url("../common/images/tabright1.gif") no-repeat right top;
			padding: 5px 15px 4px 6px;
			color:#627EB7;
 		}
		
		/* Commented Backslash Hack hides rule from IE5-Mac \*/
#changeRiskPlan a span {
					float: none;
		}
		
		/* End IE5-Mac hack */
#changeRiskPlan a:hover span {
					color:#627EB7;
 		}
		
#changeRiskPlan a:hover {
					background-position: 0% -42px;
		}
		
#changeRiskPlan a:hover span {
					background-position: 100% -42px;
		}
		
#changeRiskPlan #current a {
					background-position: 0% -42px;
		}
		
#changeRiskPlan #current a span {
					background-position: 100% -42px;
		}
		</STYLE>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
	<body onload="initForm();initElementtype();">
			<Div id="changeRiskPlan" style="display: ''">
				<Div id="changeRiskPlanTitle" style="display: ''">
					<table class=common>
						<tr>
							<td class="common">
								<a href="#" onclick="tabMoveTo(1);"> <span>收益算法定义</span>
								</a>
								<a href="#" onclick="tabMoveTo(2);"> <span>收益表头配置</span>
								</a>
							</td>
						</tr>
					</table>
				</Div>
			</Div>	
			<form action="./PDSugIncomeDataAlgSave.jsp" method=post name=fm
			target="fraSubmit">
			<table>
				<tr>
					<td class="titleImg"><%=request.getParameter("riskcode")%>的收益算法列表:
					</td>
				</tr>
			</table>
			<table class=common>
				<tr class=common>
					<td style="text-align:left;" colSpan=1>
						<span id="spanMulline10Grid"> </span>
					</td>
				</tr>
			</table>
			<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button
				onclick="Mulline10GridTurnPage.firstPage();">
			<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button
				onclick="Mulline10GridTurnPage.previousPage();">
			<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button
				onclick="Mulline10GridTurnPage.nextPage();">
			<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button
				onclick="Mulline10GridTurnPage.lastPage();">
			<br>
			<input value="新  增" name="btnDelete" onClick="newIncome()"
				class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<input value="修  改" name="btnDelete" onClick="update()"
				class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<input value="删  除" name="btnDelete" onClick="del()"
				class="cssButton" type="button" style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
			<input value="返  回" name="btnDelete" onClick="returnParent()"
				class="cssButton" type="button">
			<input type=hidden name="operator">
			<input type=hidden name="tableName" value="PD_ProceedsExpress">
			<input type=hidden name=IsReadOnly>
			<input type=hidden name="RiskCode" value=<%=request.getParameter("riskcode")%>>
			<input type=hidden name=PROCEEDSCODE>
			<input type=hidden name=TERMS>
	
		</form>
	</body>
</html>

