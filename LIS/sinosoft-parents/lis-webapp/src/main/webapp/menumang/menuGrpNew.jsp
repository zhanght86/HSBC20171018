<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
GlobalInput tG1 =new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
String Operator =tG1.Operator;
String ComCode = tG1.ComCode;
%>
<%
//程序名称：menuGrpNew.jsp
//程序功能：菜单组的输入
//创建日期：2002-10-10
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="menuGrpNew.js"></SCRIPT>
<script src="treeMenu.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="menuGrpInitNew.jsp"%>
</head>
<body onload="initForm();">
	<form action="./menuGrpManNew.jsp" method=post name=fm target="fraSubmit">
		<table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>请输入查询条件：</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
		<Table class="common">
			<TR class=common style="display:none">
				<TD class=title5>操作员</TD>
				<TD class=input5>
					<Input class="wid" class=common name=Operator value="<%=Operator%>">
				</TD>
				<TD class=title5>登陆机构</TD>
				<TD class=input5>
					<Input class="wid" class=common name=ComCode value="<%=ComCode%>">
			</TR>
			<TR  class=common>
				<TD class=title5>管理员代码</TD>
				<TD class=input5>
					<Input class="wid" class=common name=UserCode id=UserCode >
				</TD>
                <TD class=title5></TD>
                <TD class=input5></TD>
				 <!--td  class=title></td>
          <td  class=common ></td>
				</TD-->
			</TR>
		</Table></div>
		<!--<INPUT VALUE="查询菜单组" TYPE=button onclick="queryClick()" class="cssButton">-->
        <a href="javascript:void(0);" class="button" onClick="queryClick();">查询菜单组</a>
		<!--<Div id=divCmdButton style="display: ''">-->
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divQueryGrp);">
				</td>
				<td class=titleImg>菜单组信息</td>
			</tr>
		</table>			
		
		<Div  id="divQueryGrp" style="display: ''">
			<table class=common>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanQueryGrpGrid"></span>
					</td>
				</tr>
			</table>
		
    </div>
    
    <div class="maxbox1">
	<Table class="common">
	<TR class=common>
		
			<TD class=title5>菜单组编码</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpCode id=MenuGrpCode>
			</TD>
		
			<TD class=title5>菜单组名称</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpName id=MenuGrpName>
				</TD>
			</TR>
			<TR class =common>
				<TD class=title5>菜单组描述</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpDescription id=MenuGrpDescription>
				</TD>
				<TD class=title5>菜单标志</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuSign id=MenuSign>
				</TD>
			</TR>
			<tr>
					<TD class=input style="display: none">
						<Input class="code" name=Action>
					</TD>
			</tr>
		</Table>		
	</div>
		<!--<input type=button value="复制"  onclick="DataCopy()" class="cssButton" >-->
        <a href="javascript:void(0);" class="button" onClick="DataCopy();">复    制</a>
        <br><br><br><br>
	</form>
</body>
</html>
