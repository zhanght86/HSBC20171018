<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：AscriptionRuleInput.jsp
//程序功能：
//创建日期：2006-05-18
//创建人  ：
//更新记录：  更新人    更新日期    更新原因/内容 
%>

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
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="AscriptionRule.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AscriptionRuleInit.jsp"%>  
<title>团体险种归属规则定制 </title>
</head>
<body onload="initForm();">
<form method=post name=fm id="fm" target="fraSubmit" action="AscriptionRuleSave.jsp">		
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
			<TD  class= input5><Input class="readonly wid" readonly name=GrpContNo id="GrpContNo"></TD>
			<TD  class= title5>管理机构</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=ManageCom id="ManageCom"></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>保单位客户号</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=AppntNo id="AppntNo"></TD>
			<TD  class= title5>保单位名称</TD>
			<TD  class= input5><Input class="readonly wid" readonly name=GrpName id="GrpName"></TD>
		</TR>
	</table>
</div>
</div>
		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefineOld();">
	<input name="ff"-->

	<Div  id= "divAscriptionRuleOld" style= "display: ''">
	 	<table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
        		<td class= titleImg>已添加的归属规则</td>
        	</tr>
        </table>
	    <table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1><span id="spanAscriptionRuleOldGrid" ></span> </td>
			</tr>
	    </table>
	    <div id="divAscriptionRuleGrid" style="display:none">
			<table>
    			<tr>
    				<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
    				<td class= titleImg>归属规则详细信息</td>
    			</tr>
		    </table>
    		<table  class= common>
    			<tr  class= common>
    				<td text-align: left colSpan=1><span id="spanAscriptionRuleGrid" ></span></td>
    			</tr>
    		</table>
	    </div>
    </Div>
   		<!--input type=button class="cssButton" value="kick me" onclick="GrpPerPolDefine();">
	<input name="ff"-->
		
    <Div  id= "divAscriptionRule" style= "display: ''">
	 	<table>
        	<tr>
            	<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
        		<td class= titleImg>定制归属规则</td>
        	</tr>
        </table>
        <div class="maxbox1">
        <table>
	   		<TR  class= common>
				<TD  class= title5>员工类别</TD>
				<TD  class= input5><Input class="common wid" name=AscriptionRuleCode id="AscriptionRuleCode" maxlength=2></TD>
				<TD  class= title5>分类说明</TD>
				<TD  class= input5><Input class="common wid" name=AscriptionRuleName id="AscriptionRuleName"></TD>
			</TR>
		</table>
		</div>
		<br>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1><span id="spanAscriptionRuleNewGrid" ></span> </td>
			</tr>
		</table>
    </Div>
    <a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
	<!-- <INPUT VALUE="上一步" class =cssButton  TYPE=button onclick="returnparent();"> -->
	<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<a href="javascript:void(0)" class=button onclick="submitForm();">归属规则保存</a>
		<a href="javascript:void(0)" class=button onclick="updateClick();">归属规则修改</a>
		<a href="javascript:void(0)" class=button onclick="DelContClick();">归属规则删除</a>
		<!-- <INPUT VALUE="归属规则保存" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="归属规则修改" class =cssButton  TYPE=button onclick="updateClick();">
		<INPUT VALUE="归属规则删除" class =cssButton  TYPE=button onclick="DelContClick();"> -->
	</Div>
	<Input type=hidden name=ProposalGrpContNo id="ProposalGrpContNo">
	<input type=hidden name=mOperate id="mOperate">
	<input type=hidden name=AscriptionRuleCodeOld id="AscriptionRuleCodeOld">
	<input type=hidden name=AscriptionRuleNameOld id="AscriptionRuleNameOld">
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
