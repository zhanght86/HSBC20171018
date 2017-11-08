<%
//程序名称：PayRuleInput.jsp
//程序功能：
//创建日期：2004-08-30
//创建人  ：sunxy
//更新记录：  更新人mqhu    更新日期2005-04-01     更新原因/内容 添加已存在缴费规则
%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String LoadFlag = request.getParameter("LoadFlag");
String AskFlag = request.getParameter("AskFlag");
%>
<head>
<script>
GrpContNo="<%=GrpContNo%>";
var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
AskFlag = "<%=AskFlag%>";
if (AskFlag == "" || AskFlag == "null" || AskFlag == null)
    AskFlag = 0;
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="PayRuleInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PayRuleInit.jsp"%>
<title>团体险种缴费规则定制 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="PayRuleSave.jsp">		
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''">
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title5>集体合同号</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo">
					<Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
					<input type=hidden name=mOperate id="mOperate">
				</TD>
				<TD  class= title5>管理机构</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=ManageCom id="ManageCom">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title5>保单位客户号</TD>
				<TD  class= input5>
					<Input class="readonly wid" readonly name=AppntNo id="AppntNo">
				</TD>
				<TD  class= title5>保单位名称</TD>
				<TD  class= input5>
					<Input class="readonly wid"  readonly name=GrpName id="GrpName">
				</TD>
			</TR>
			</table>
		</div>
		</div>
			<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefineOld();">
		<input name="ff"-->

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
	    	</table>
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
       	</Div>
       		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefine();">
		<input name="ff"-->
		
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
	       <div class="maxbox1">
	       <table>
       			<TR  class= common>
					<TD  class= title>员工类别</TD>
					<TD  class= input>
						<Input class="common wid" name=PayRuleCode id="PayRuleCode" maxlength=2>
					</TD>
					<TD  class= title>分类说明</TD>
					<TD  class= input>
						<Input class="common wid" name=PayRuleName id="PayRuleName">
					</TD>
					<td class= title></td>
					<td class= input></td>
				</TR>
			</table>
			</div>
			<br>
	    	<table  class= common>
	        	<tr  class= common>
	    	  		<td text-align: left colSpan=1>
						<span id="spanPayRuleNewGrid" >
						</span> 
					</td>
				</tr>
			</table>
		</Div>
		<a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
		<!-- <INPUT VALUE="上一步" class =cssButton  TYPE=button onclick="returnparent();"> -->
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right>
			<a href="javascript:void(0)" class=button onclick="submitForm();">缴费规则保存</a> 
			<a href="javascript:void(0)" class=button onclick="updateClick();">缴费规则修改</a> 
			<a href="javascript:void(0)" class=button onclick="DelContClick();">缴费规则删除</a> 
			<!-- <INPUT VALUE="缴费规则保存" class =cssButton  TYPE=button onclick="submitForm();">
			<INPUT VALUE="缴费规则修改" class =cssButton  TYPE=button onclick="updateClick();">
			<INPUT VALUE="缴费规则删除" class =cssButton  TYPE=button onclick="DelContClick();"> -->
		</Div>
	
	    <INPUT TYPE=hidden name=PayRuleCodeOld id="PayRuleCodeOld">
		<INPUT TYPE=hidden name=PayRuleNameOld id="PayRuleNameOld">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
