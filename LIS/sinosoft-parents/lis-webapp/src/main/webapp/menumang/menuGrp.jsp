<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
GlobalInput tG1 =new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
String Operator =tG1.Operator;;
%>
<%
//程序名称：menuGrp.jsp
//程序功能：菜单组的输入
//创建日期：2002-10-10
//创建人  ：
//更新记录： ml    2006-03-08     无法显示所有的查询信息/对MulLine的查询显示进行重写
%>
<html>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="menuGrp.js"></SCRIPT>
<script src="treeMenu.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="menuGrpInit.jsp"%>
<style type=text/css>
#spanUnselectMenuGrpTree ul ul{
	margin-left:20px;
}
#spanSelectMenuGrpTree ul ul{
	margin-left:20px;
}
</style>
</head>
<body onload="initForm();">
	<form action="./menuGrpMan.jsp" method=post name=fm id=fm target="fraSubmit">
    <div class="maxbox">
		<Table class="common">
			<TR class=common style="display:none">
				<TD class=title5>操作员</TD>
				<TD class=input5>
					<Input class="wid" class=common name=Operator id=Operator value="<%=Operator%>">
				</TD>
			</TR>
			<TR  class=common>
				<TD class=title5>菜单组编码</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpCode id=MenuGrpCode maxlength="10">
				</TD>
				<TD class=title5>菜单组名称</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpName id=MenuGrpName>
				</TD></TR>
                <TR  class=common>
				<TD class=title5>菜单组描述</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpDescription id=MenuGrpDescription>
				</TD>
                <TD class=title5>菜单标志</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuSign id=MenuSign>
				</TD>
			</TR>
			<TR class =common id="trUsercode" style="display: ">				
				
				<TD class=title5>操作员</TD>
				<TD class=Input5>
					<Input class="wid" class=common name=Usercode id=Usercode>
				</TD>
			</TR>
		</Table></div>
		<Div id=divCmdButton style="display: ">
			<INPUT VALUE="查询菜单组" TYPE=button onclick="queryClick()" class="cssButton">
			<INPUT VALUE="增加菜单组" TYPE=button onclick="insertClick()" class="cssButton">
			<INPUT VALUE="更新菜单组" TYPE=button onclick="updateClick()" class="cssButton">
			<INPUT VALUE="删除菜单组" TYPE=button onclick="deleteClick()" class="cssButton">
		</Div>
		<Div  id="divQueryGrp" style="display:  ">
			<table>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class=titleImg>菜单组表</td>
			</table>
            <Div  id= "divPayPlan1" style= "display:  ">
			<table class=common>
				<tr>
					<td style="text-align: left" colSpan=1>
						<span id="spanQueryGrpGrid"></span>
					</td>
				</tr>
			</table>
            </div>
		</div>
		<Div id=divSubCmdButton style="display: none">
			<INPUT VALUE="确  定" TYPE=button  onclick="okClick()" class="cssButton">
			<INPUT VALUE="退  出" TYPE=button  onclick="cancelClick()" class="cssButton">
		</Div>
		<Div id="divmenuGrid2" style="display: none">
			<table class=common>
				<tr>
					<td class=titleImg>菜单组已有菜单表</td>
					<td class=titleImg>菜单组未有菜单表</td>
				</tr>
			</table>
			<div class="maxbox1">
			<table class=common>
				<tr>
					<span id="spanSelectMenuGrpTree" style="display:  ; position:absolute; slategray"></span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input VALUE="<-" TYPE=button onclick="addMenus()" class="cssButton">
					<input value="->" type=button onclick="removeMenus()" class="cssButton">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="spanUnselectMenuGrpTree" style="display:  ; position:absolute; slategray"></span>
					<td style="text-align: left" colSpan=1">
						<span id="spanHideMenuGrpGrid1" style="display: none"></span>
						<span id="spanHideMenuGrpGrid2" style="display: none"></span>
						<Input class=common name=hideString style="display:none">
						<Input class=common name=hideRemoveString style="display:none">
					</td>
				</tr>
				<tr>
					<TD class=input style="display: none">
						<Input class="code" name=hide1>
						<Input class="code" name=hide2>
						<Input class="code" name=Action>
					</TD>
				</tr>
			</table>
			</div>
		</Div>
		<br /><br /><br /><br />
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
