<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>
<% 
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<script>
 var tManageCom = "<%=tGI.ManageCom%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="CombinationConfig.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CombinationConfigInit.jsp"%>
<title>个险产品组合配置 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id=fm target="fraSubmit" action="CombinationConfigSave.jsp">
		<table>
			<tr class=common>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class= titleImg>已存在产品组合</td>
			</tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanCodeGrid" ></span>
				</td>
			</tr>
		</table></div>
		<table>
			<tr class=common>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
				<td class= titleImg>产品组合定制</td>
			</tr>
		</table>
        <Div  id= "peer" style= "display: ''" class="maxbox1">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>组合代码</TD>
				<TD  class= input>
					<Input class="wid" class=common name=ContPlanCode id=ContPlanCode clearDate="Static" maxlength="6" onchange="ChangePlan();">
				</TD>
				<TD  class= title>分类说明</TD>
				<TD  class= input>
					<input class="wid" class=common name=ContPlanName id=ContPlanName clearDate="Static">
				</TD>
				<TD  class= title>可保人数</TD>
				<TD  class= input>
					<Input class="wid" class=common name=Peoples3 id=Peoples3 clearDate="Static">
				</TD>
					<Input type=hidden name=PlanSql>
					<input type=hidden name=mOperate>
			</TR>
			<TR  class= common>
				<TD  class= title>保额分配比例</TD>
				<TD  class= input>
					<Input class="wid" class=common name=PlanKind1 id=PlanKind1 clearDate="Static">
				</TD>
				<!-- TD  class= title>组合层级2</TD>
				<TD  class= input -->
					<input class=common name=PlanKind2 type=hidden>
				<!-- /TD -->
				<!-- TD  class= title>组合层级3</TD>
				<TD  class= input -->
					<Input class=common name=PlanKind3 type=hidden>
				<!-- /TD -->
				<TD  class= title>险种代码</TD>
				<TD  class= input>
					<!--Input class=code name=RiskCode ondblclick="return showCodeList('RiskCode1',[this,RiskFlag],[0,3],null,'','',1,400);" onkeyup="return showCodeListKey('RiskCode1',[this,RiskFlag],[0,3],null,'','','',400);"-->
					<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>							
					<input type=hidden name=RiskFlag>
				</TD>
			</TR>			
			<TR>
				<TD id=divmainriskname style="display:none" class=title>主险编码</TD>
				<TD id=divmainrisk style="display:none" class=input>
					<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=code name=MainRiskCode id=MainRiskCode ondblclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onkeyup="return showCodeListKey('GrpMainRisk1',[this],[0],null,'','');">
				</TD>
				<!-- td CLASS="title">管理机构
    	    </td>
			    <td CLASS="input" -->
			      <Input name=ManageCom type=hidden class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true type=hidden>
			      <!--input NAME="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly"-->
    	    <!-- /td 				
			</TR>
			</table>
			<table  class= common align=center>
				<tr class=common>
					<td class=title>特别约定</td>
				</tr>
				<tr class=common>
					<td class=title>
						<textarea name="Remark" cols="110" rows="3" class="common"></textarea>
					</td>
				</tr -->
			</table>	
	<table>
		<tr>
			<td class="titleImg">
			<font color="red">
			提示：保额分配比例请按照"*/*/*"的格式录入，每两个小数间用"/"隔开，且录入小数的总和为1，系统会默认只保留小数点后三位小数。
			</font>
			</td>
		</tr>
	</table></div>
		<Input VALUE="险种责任查询" class=cssButton TYPE=button onclick="QueryDutyClick();">
        <br><br>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>
		<Input VALUE="定义产品组合要素" class=cssButton  TYPE=button onclick="AddContClick();"><br>
		<!--table class=common>
			<TR  class= common>
				<TD>
					<Input VALUE="添加险种到保险计划" class=cssButton  TYPE=button onclick="AddContClick();">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>保险计划类型</TD>
				<TD  class= input>
					<Input class="code" name=ContPlanType ondblclick="showCodeList('contplantype',[this]);" onkeyup="showCodeListKey('contplantype',[this]);">
				</TD>
			</TR>
		</table-->
		<table>
			<tr>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vbn);"></td>
				<td class= titleImg>产品组合详细信息</td>
			</tr>
		</table>
        <Div  id= "vbn" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanGrid" ></span>
				</td>
			</tr>
		</table></Div>
    	
		<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="产品组合查询" class="cssButton" style= "display: 'none'" TYPE=button onclick="">
		<INPUT VALUE="产品组合保存" class="cssButton"  TYPE=button onclick="submitForm();">
		<INPUT VALUE="产品组合修改" class="cssButton"  TYPE=button onclick="UptContClick();">
		<INPUT VALUE="产品组合删除" class="cssButton"  TYPE=button onclick="DelContClick();">
	    </Div><br>
		 <%
          String today=PubFun.getCurrentDate();
         %>
		 <input type=hidden id="" name="sysdate" value="<%=today%>">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
