<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="ContPlanNext.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ContPlanNextInit.jsp"%>
<title>团体保险计划要素定制 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id=fm target="fraSubmit" action="ContPlanNextSave.jsp">		
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
		</table>
		<div class=maxbox1>
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title5>集体合同号</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=GrpContNo id=GrpContNo>
					<Input type=hidden name=ProposalGrpContNo id=ProposalGrpContNo>
					<input type=hidden name=mOperate id=mOperate>
					<!--Input class=code name=ContPlanCode ondblclick="return showCodeList('ContPlanCode',[this]);" onkeyup="return showCodeListKey('ContPlanCode',[this]);"-->
				</TD>
				<TD  class= title5>管理机构</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=ManageCom id=ManageCom>
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title5>保单位客户号</TD>
				<TD  class= input5>
					<Input class= "readonly wid" readonly name=AppntNo id=AppntNo>
				</TD>
				<TD  class= title5>保单位名称</TD>
				<TD  class= input5>
					<Input class= "readonly wid" readonly name=GrpName id=GrpName>
				</TD>
			</TR>
		</table>
		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefine();">
		<input name="ff"-->
		<Div  id= "divLCImpart1" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 保险计划要约信息
    		</td>
    	</tr>
       </table>
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
		<INPUT VALUE="上一步" class =cssButton  TYPE=button onclick="returnparent();">
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="保险计划要约保存" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="保险计划要约删除" class =cssButton  TYPE=button onclick="DelContClick();">
	        </Div>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
