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
<SCRIPT src="LDPlanConf.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LDPlanConfInit.jsp"%>
<title>团体保险套餐配置 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id=fm target="fraSubmit" action="LDPlanConfSave.jsp">
		<table>
			<tr class=common>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class= titleImg>已存在保险套餐</td>
			</tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanCodeGrid" ></span>
				</td>
			</tr>
		</table></Div>
        
		<table>
			<tr class=common>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
				<td class= titleImg>保险套餐定制</td>
			</tr>
		</table>
        <Div  id= "peer" style= "display: ''" class="maxbox">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>套餐代码</TD>
				<TD  class= input>
					<Input class="wid" class=common name=ContPlanCode id=ContPlanCode maxlength="6" onchange="ChangePlan();">
				</TD>
				<TD  class= title>分类说明</TD>
				<TD  class= input>
					<input class="wid" class=common name=ContPlanName id=ContPlanName>
				</TD>
				<TD  class= title>可保人数</TD>
				<TD  class= input>
					<Input class="wid" class=common name=Peoples3 id=Peoples3>
				</TD>
					<Input type=hidden name=PlanSql>
					<input type=hidden name=mOperate>
			</TR>
			<TR  class= common>
				<TD  class= title>套餐层级1</TD>
				<TD  class= input>
					<Input class="wid" class=common name=PlanKind1 id=PlanKind1>
				</TD>
				<TD  class= title>套餐层级2</TD>
				<TD  class= input>
					<input class="wid" class=common name=PlanKind2 id=PlanKind2>
				</TD>
				<TD  class= title>套餐层级3</TD>
				<TD  class= input>
					<Input class="wid" class=common name=PlanKind3 id=PlanKind3>
				</TD>
			</TR>			
			<TR>
				<TD  class= title>险种代码</TD>
				<TD  class= input>
					<!--Input class=code name=RiskCode ondblclick="return showCodeList('RiskCode1',[this,RiskFlag],[0,3],null,'','',1,400);" onkeyup="return showCodeListKey('RiskCode1',[this,RiskFlag],[0,3],null,'','','',400);"-->
					<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>							
					<input type=hidden name=RiskFlag>
				</TD>
				<TD id=divmainriskname style="display:none" class=title>主险编码</TD>
				<TD id=divmainrisk style="display:none" class=input>
					<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=MainRiskCode id=MainRiskCode ondblclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onkeyup="return showCodeListKey('GrpMainRisk1',[this],[0],null,'','');">
				</TD>
				<td CLASS="title">管理机构
    	    </td>
			    <td CLASS="input">
			      <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=ManageCom class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName id=ManageComName class=codename readonly=true>
			      <!--input NAME="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="管理机构|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly"-->
    	    </td>				
			</TR>
			</table>
			<table  class= common>
				<tr class=common>
					<td colspan="4" class=title>特别约定</td>
				</tr>
				<tr class=common>
					<td colspan="4" class=title>
						<textarea name="Remark" cols="200" rows="4" class="common"></textarea>
					</td>
				</tr>
			</table>	</Div>
		<Input VALUE="险种责任查询" class=cssButton TYPE=button onclick="QueryDutyClick();">
        <!--<a href="javascript:void(0);" class="button" onClick="QueryDutyClick();">险种责任查询</a><br><br>-->
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>
		<Input VALUE="定义保险套餐要素" class=cssButton  TYPE=button onclick="AddContClick();"><!--<br>
        <a href="javascript:void(0);" class="button" onClick="AddContClick();">定义保险套餐要素</a>-->
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
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></td>
				<td class= titleImg>保险套餐详细信息</td>
			</tr>
		</table>
        <Div  id= "air" style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanGrid" ></span>
				</td>
			</tr>
		</table></Div>
    	
		<Div  id= "divRiskPlanSave" style= "display: ''"> 
		<INPUT VALUE="保险套餐查询" class="cssButton" style= "display: 'none'" TYPE=button onclick="QueryDutyClick();">
		<INPUT VALUE="保险套餐保存" class="cssButton"  TYPE=button onclick="submitForm();">
		<INPUT VALUE="保险套餐修改" class="cssButton"  TYPE=button onclick="UptContClick();">
		<INPUT VALUE="保险套餐删除" class="cssButton"  TYPE=button onclick="DelContClick();">
	    </Div>
        <!--<a href="javascript:void(0);" class="button" style= "display: 'none'" onClick="">保险套餐查询</a>
        <a href="javascript:void(0);" class="button" onClick="submitForm();">保险套餐保存</a>
        <a href="javascript:void(0);" class="button" onClick="UptContClick();">保险套餐修改</a>
        <a href="javascript:void(0);" class="button" onClick="DelContClick();">保险套餐删除</a>-->
		 <%
          String today=PubFun.getCurrentDate();
         %>
		 <input type=hidden id="" name="sysdate" value="<%=today%>">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
