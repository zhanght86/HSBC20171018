<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<script>
  Operator = "<%=tGlobalInput.Operator%>";
  ComCode = "<%=tGlobalInput.ComCode%>";
  var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="BQMasterCenter.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQMasterCenterInit.jsp"%>
  <title>管理中心 </title>
 </head>

<body  onload="initForm();" >
<form action="../bq/MissionApply.jsp" method=post name=fm id=fm target="fraSubmit">
	<!-- ########################保单信息部分 输入查询条件########################  -->
	<Table class= common>
		<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
			<td class= titleImg>请输入查询条件：</td>
		</tr>
	</Table>
	<Div  id= "divSearch" style= "display: ''" class="maxbox1">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> 合同号 </TD>
				<TD  class= input5>					
					<Input class="wid common"  name=ContNo id="ContNo"  >
				</TD>
				<TD  class= title5> 管理机构</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom onDblClick="showCodeList('station',[this,ManageComName],[0,1]);" onClick="showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true>
				</TD>  
				<!--
				<TD  class= title >录单日期</TD>
				<TD  class= input><Input class="coolDatePicker" dateFormat="short" name=MakeDate ></TD>
			</TR>
			<TR  class= common>         
				<TD  class= title>业务员编码</TD>
				<TD class="input">
					  <input NAME="AgentCode"  class="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" ondblclick="return queryAgent();" >
				</TD>
				-->
			<!--
				<TD class="title">业务员姓名</td>
				<TD class="input">
				<input name="AgentName"  class="common" elementtype=nacessary  verifyorder="1" >
				</TD>
				<TD  class= title>营业部、营业组</TD>
				<TD  class= input>
				<input class="codeno" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" name=AgentGroupName readonly=true>
				</TD>
			-->
			<!--
				<TD  class=title style = "display:none"> 销售渠道</TD>
				<TD  class=input>
				<input class="codeno"  type = hidden name=SaleChnl ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true type = hidden>
				</TD>  	
				-->
			</TR>
		</Table>
	</DIV>
	
	<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">	
	<!-- ########################保单信息部分 共享工作池########################  -->
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
		</td>
		<td class= titleImg>共享工作池</td>
		</tr>
	</Table>
	
	<Div  id= "divLCPol1" style= "display: ''">
		<Table  class= common >
			<tr  class= common align = left>
				<td text-align: left colSpan=1>
					<span id="spanPolGrid" ></span> 
			  	</td>
			</tr>
		</Table>
        <center>
		<INPUT class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();HighlightAllRow();"> 
		<INPUT class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();HighlightAllRow();"> 					
		<INPUT class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();HighlightAllRow();"> 
		<INPUT class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();HighlightAllRow();">	</center>	
	</Div>
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();">
	<br>
	<br>

	<!-- ########################保单信息部分 个人待复核保单队列########################  -->
	<Div  id= "divSearch1" style= "display: ''" class="maxbox1">
		<Table  class= common>
			<TR  class= common>
				<TD  class= title5> 合同号 </TD>
				<TD  class= input5>
					<Input class="wid common"  name=ContNo1 id=ContNo1  >
				</TD>
				<TD  class= title5> 管理机构</TD>
				<TD  class= input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom1 id=ManageCom1 onDblClick="showCodeList('station',[this,ManageComName1],[0,1]);" onClick="showCodeList('station',[this,ManageComName1],[0,1]);" onKeyUp="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 id=ManageComName1 readonly=true>
				</TD>  
			</TR>
		</Table></DIV>
		<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickSelf();">
	
	<Table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
		</td>
		<td class= titleImg>待处理保单</td>
		</tr>  	
	</Table>
	<Div  id= "divSelfPolGrid" style= "display: ''">
		<Table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
				<span id="spanSelfPolGrid" ></span> 
		  	</td>
		</tr>
		</Table><center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();HighlightSelfRow();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();HighlightSelfRow();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();HighlightSelfRow();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();HighlightSelfRow();">    </center> 
	</Div>

	<!--#########################  隐藏表单区域   ##############################-->
	<Input type=hidden name="Operator" id="Operator" >
  <Input type=hidden name="ComCode" id="ComCode" >
	<Input type=hidden id="MissionID" name="MissionID" >
	<Input type=hidden id="SubMissionID" name="SubMissionID" >
	<Input type=hidden id="ActivityID" name="ActivityID" > 
	<Input type=hidden id="hiddenContNo" name="hiddenContNo" > 
	<Input class= common id="EdorNo"  name=EdorNo type="hidden">
	<Input class= common id="EdorType" name=EdorType type="hidden">  	 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>


