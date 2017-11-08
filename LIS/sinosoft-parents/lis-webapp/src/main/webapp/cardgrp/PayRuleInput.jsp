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
<SCRIPT src="PayRuleInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PayRuleInit.jsp"%>
<title>团体险种缴费规则定制 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="PayRuleSave.jsp">		
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
		</table>
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title>集体合同号</TD>
				<TD  class= input>
					<Input class= common  name=GrpContNo onblur="GrpPerPolDefine(); GrpPerPolDefineOld();queryrelate();">
					
					<input type=hidden name=mOperate>
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					&nbsp;
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					&nbsp;
				</TD>
			</TR>
			
			</table>
			

		<Div  id= "divPayRuleOld" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 已添加的缴费规则
    		</td>
    	</tr>
    	    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPayRuleOldGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<div id="divPayRuleGrid" style="display:none">
				<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>缴费规则详细信息</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanPayRuleGrid" ></span>
				</td>
			</tr>
		</table>
		</div>
       </table>
       
       </Div>
       		
		
		<Div  id= "divPayRule" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 定制缴费规则
    		</td>
    	</tr>
       </table>
       			<TR  class= common>
				<TD  class= title>员工类别</TD>
				<TD  class= input>
					<Input class=common name=PayRuleCode maxlength=2>
				</TD>
				<TD  class= title>分类说明</TD>
				<TD  class= input>
					<Input class=common name=PayRuleName>
				</TD>
			</TR>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPayRuleNewGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
		<!--INPUT VALUE="上一步" class =cssButton  TYPE=button onclick="returnparent();"-->
	<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="缴费规则保存" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="缴费规则修改" class =cssButton  TYPE=button onclick="updateClick();">
		<INPUT VALUE="缴费规则删除" class =cssButton  TYPE=button onclick="DelContClick();">
	</Div>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
