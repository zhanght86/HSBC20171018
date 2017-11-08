<%@include file="../i18n/language.jsp"%>


<%@ page contentType="text/html;charset=GBK" %>
<html>

<head>
<title>顶部导航</title>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="DictionaryUtilities.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<script type="text/javascript">
function tabMoveTo(num){
	if(num=='1'){
		var tUrl =  './PDSugIncomeDataMain.jsp?riskcode=<%=request.getParameter("riskcode")%>';
		document.getElementById("IbrmsDetail").src = tUrl;
	}else if(num=='2'){
		var tUrl =  './PDSugIncomeTabHeadMain.jsp?riskcode=<%=request.getParameter("riskcode")%>';
		document.getElementById("IbrmsDetail").src = tUrl;
	}
	
}
</script>
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
<body >
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
		<table  class=common >
		    <iframe id= "IbrmsDetail" src="" width=100% height=500 SCROLLING='Auto' >
		 	</iframe> 
		</table>		
</body>
<script type="text/javascript">
tabMoveTo(1);
</script>
</html>

