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
<title>���ղ�Ʒ������� </title>
</head>
<body onload="initForm();">
	<form method=post name=fm id=fm target="fraSubmit" action="CombinationConfigSave.jsp">
		<table>
			<tr class=common>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class= titleImg>�Ѵ��ڲ�Ʒ���</td>
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
				<td class= titleImg>��Ʒ��϶���</td>
			</tr>
		</table>
        <Div  id= "peer" style= "display: ''" class="maxbox1">
		<table  class= common>
			<TR  class= common>
				<TD  class= title>��ϴ���</TD>
				<TD  class= input>
					<Input class="wid" class=common name=ContPlanCode id=ContPlanCode clearDate="Static" maxlength="6" onchange="ChangePlan();">
				</TD>
				<TD  class= title>����˵��</TD>
				<TD  class= input>
					<input class="wid" class=common name=ContPlanName id=ContPlanName clearDate="Static">
				</TD>
				<TD  class= title>�ɱ�����</TD>
				<TD  class= input>
					<Input class="wid" class=common name=Peoples3 id=Peoples3 clearDate="Static">
				</TD>
					<Input type=hidden name=PlanSql>
					<input type=hidden name=mOperate>
			</TR>
			<TR  class= common>
				<TD  class= title>����������</TD>
				<TD  class= input>
					<Input class="wid" class=common name=PlanKind1 id=PlanKind1 clearDate="Static">
				</TD>
				<!-- TD  class= title>��ϲ㼶2</TD>
				<TD  class= input -->
					<input class=common name=PlanKind2 type=hidden>
				<!-- /TD -->
				<!-- TD  class= title>��ϲ㼶3</TD>
				<TD  class= input -->
					<Input class=common name=PlanKind3 type=hidden>
				<!-- /TD -->
				<TD  class= title>���ִ���</TD>
				<TD  class= input>
					<!--Input class=code name=RiskCode ondblclick="return showCodeList('RiskCode1',[this,RiskFlag],[0,3],null,'','',1,400);" onkeyup="return showCodeListKey('RiskCode1',[this,RiskFlag],[0,3],null,'','','',400);"-->
					<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=RiskCode id=RiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id=RiskCodeName readonly=true elementtype=nacessary>							
					<input type=hidden name=RiskFlag>
				</TD>
			</TR>			
			<TR>
				<TD id=divmainriskname style="display:none" class=title>���ձ���</TD>
				<TD id=divmainrisk style="display:none" class=input>
					<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=code name=MainRiskCode id=MainRiskCode ondblclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onclick="return showCodeList('GrpMainRisk1',[this],[0],null,'','',1);" onkeyup="return showCodeListKey('GrpMainRisk1',[this],[0],null,'','');">
				</TD>
				<!-- td CLASS="title">�������
    	    </td>
			    <td CLASS="input" -->
			      <Input name=ManageCom type=hidden class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true type=hidden>
			      <!--input NAME="ManageCom"  verifyorder="1" VALUE MAXLENGTH="10" CLASS="codeno" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'1 and #1# = #1# and Length(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull"><input NAME="ManageComName" elementtype=nacessary CLASS="codename" readonly="readonly"-->
    	    <!-- /td 				
			</TR>
			</table>
			<table  class= common align=center>
				<tr class=common>
					<td class=title>�ر�Լ��</td>
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
			��ʾ�������������밴��"*/*/*"�ĸ�ʽ¼�룬ÿ����С������"/"��������¼��С�����ܺ�Ϊ1��ϵͳ��Ĭ��ֻ����С�������λС����
			</font>
			</td>
		</tr>
	</table></div>
		<Input VALUE="�������β�ѯ" class=cssButton TYPE=button onclick="QueryDutyClick();">
        <br><br>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>
		<Input VALUE="�����Ʒ���Ҫ��" class=cssButton  TYPE=button onclick="AddContClick();"><br>
		<!--table class=common>
			<TR  class= common>
				<TD>
					<Input VALUE="������ֵ����ռƻ�" class=cssButton  TYPE=button onclick="AddContClick();">
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>���ռƻ�����</TD>
				<TD  class= input>
					<Input class="code" name=ContPlanType ondblclick="showCodeList('contplantype',[this]);" onkeyup="showCodeListKey('contplantype',[this]);">
				</TD>
			</TR>
		</table-->
		<table>
			<tr>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vbn);"></td>
				<td class= titleImg>��Ʒ�����ϸ��Ϣ</td>
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
		<INPUT VALUE="��Ʒ��ϲ�ѯ" class="cssButton" style= "display: 'none'" TYPE=button onclick="">
		<INPUT VALUE="��Ʒ��ϱ���" class="cssButton"  TYPE=button onclick="submitForm();">
		<INPUT VALUE="��Ʒ����޸�" class="cssButton"  TYPE=button onclick="UptContClick();">
		<INPUT VALUE="��Ʒ���ɾ��" class="cssButton"  TYPE=button onclick="DelContClick();">
	    </Div><br>
		 <%
          String today=PubFun.getCurrentDate();
         %>
		 <input type=hidden id="" name="sysdate" value="<%=today%>">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
