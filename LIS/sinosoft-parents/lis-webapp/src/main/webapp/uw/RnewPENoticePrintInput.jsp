<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<script>
var managecom ="<%=tGI.ManageCom%>"; //记录管理机构
var comcode ="<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="RnewPENoticePrintInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RnewPENoticePrintInit.jsp"%>
<title>打印保全体检通知书</title>
</head>
<body onLoad="initForm();">
	<form action="./BodyCheckPrintQuery.jsp" method=post name=fm  id="fm" target="fraSubmit">
		<!-- 投保单信息部分 -->
		<table class=common border=0 width=100%>
			<tr>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
				<td class=titleImg align=center>请输入查询条件：</td>
			</tr>
		</table>
         <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
		<table  class=common align=center>
			<TR class=common>
				<TD class=title5>保单号码</TD>
				<TD class=input5><Input class="common wid" name=ContNo id=ContNo></TD>
				<TD class=title5>印刷号</TD>
				<TD class=input5><Input class="common wid" name=PrtNo id=PrtNo></TD>
				</TR>
			<TR class=common>
                <TD class=title5> 管理机构</TD>
				<TD class=input5><Input class="common wid"name=ManageCom id=ManageCom
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
                 onclick="return showCodeList('station',[this]);" 
                onDblClick="return showCodeList('station',[this]);" 
                onKeyUp="return showCodeListKey('station',[this]);"></TD>
			
				<TD class=title5>代理人编码</TD>
				<TD class=input5><Input class="common wid" name=AgentCode id=AgentCode
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentCode',[this]);"
                 onDblClick="return showCodeList('AgentCode',[this]);"
                  onKeyUp="return showCodeListKey('AgentCode',[this]);"></TD>
				</TR>
			<TR class=common>
                <TD class=title5>代理人组别</TD>
				<TD class=input5><Input class="common wid" name=AgentGroup id=AgentGroup
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('AgentGroup',[this]);" 
                onDblClick="return showCodeList('AgentGroup',[this]);" 
                onKeyUp="return showCodeListKey('AgentGroup',[this]);"></TD>
				<TD class=title5>展业机构</TD>
				<TD class="input5" nowrap="true">
					<Input class="common wid" name="BranchGroup">
					<input name="btnQueryBranch" class="common"  type="hidden" value="?" onClick="queryBranch()" style="width:20">
				</TD>
			</TR>
		</table>
        </div></div>
		<INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();">
	</form>
	<form action="./RnewPENoticePrintSave.jsp" method=post name=fmSave id=fmSave target="fraSubmit">
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCCont1);">
				</td>
				<td class=titleImg>体检信息</td>
			</tr>
		</table>
		<Div id="divLCCont1" style="display: ''" align =center>
			<table class=common>
				<TR class=common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid"></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="getFirstPage();">
			<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="getPreviousPage();">
			<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="getNextPage();">
			<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="getLastPage();">
		</div>
		<p>
			<INPUT VALUE="打印体检通知书" class=cssButton TYPE=button onClick="printPol();">
		</p>
        <br><br><br><br>
		<input type=hidden id="fmtransact" name="fmtransact">
		<input type=hidden id="ContNo" name="ContNo">
		<input type=hidden id="PrtSeq" name="PrtSeq">
		<input type=hidden id="MissionID" name="MissionID">
		<input type=hidden id="SubMissionID" name="SubMissionID">
		<input type=hidden id="PrtNo" name="PrtNo">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
