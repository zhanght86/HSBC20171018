<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：
//程序功能：
//创建日期：
//创建人 ：CrtHtml程序创建
//更新记录： 更新人  更新日期   更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
GlobalInput tG11 =new GlobalInput();
tG11=(GlobalInput)session.getValue("GI");
String Operator =tG11.Operator;
String operatorComCode =tG11.ComCode;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=9.0"/> -->
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<script src="../common/laydate/laydate.js"></script>
<script src="../menumang/treeMenu.js"></script>
<SCRIPT src="UserAdd.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./UserAddInit.jsp"%>
<title>用户管理 </title>
</head>
<body onLoad="initForm();">
	<form action="./userAddMan.jsp" method=post name=fm id="fm" target="fraSubmit">
    <div class="maxbox">
		<table class="common1">
			<TR class=common>
				<TD class="title5">用户编码</TD>
				<TD class="input5" style="display: " id="tdUserCode">
					<Input class="common wid" name=UserCode id=UserCode onKeyUp="value=value.replace(/[^a-zA-Z0-9]/g,'')" onClick="value=value.replace(/[^a-zA-Z0-9]/g,'')" onMouseOver="value=value.replace(/[^a-zA-Z0-9]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z0-9]/g,''))" maxlength="20" ><font id="fontId" style="display: none" size=1 color='#ff0000'><b>*</b></font>
				</TD>
				<TD class="input5" style="display:none" id="tdUserCodeReadOnly">
					<Input class="common wid" name=UserCodeReadOnly id=UserCodeReadOnly readonly maxlength="20">
				</TD>
				<TD class=title5>用户姓名</TD>
				<TD class=input5 style="display: " id="tdUserName">
					<Input class="wid" class=common name=UserName id=UserName>
				</TD>
				<TD class="input5" style="display:none" id="tdUserNameReadOnly">
					<Input class="common wid" name=UserNameReadOnly id=UserNameReadOnly readonly maxlength="20">
				</TD>
			</TR>
			<TR class=common>
				<TD class=title5>机构编码</TD>
					<TD class="input5">
                        <input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ComCode id=ComCode onMouseDown="showCodeList('ComCode',[this,ComCodeName],[0,1]);" onDblClick=" showCodeList('ComCode',[this,ComCodeName],[0,1]);" onClick=" showCodeList('ComCode',[this,ComCodeName],[0,1]);" onKeyUp="return showCodeListKey('ComCode',[this,ComCodeName],[0,1]); " ><input class=codename id=ComCodeName name=ComCodeName id=ComCodeName readonly=true><font style="display: none" size=1 color='#ff0000'><b>*</b></font>
					</TD>
           <TD  class= title5>
            代理机构
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code wid" name=AgentCom id=AgentCom elementtype=nacessary  ondblclick="return showCodeList('AgentCom',[this],null,null, document.all('ComCode').value, 'ManageCom');" onclick="return showCodeList('AgentCom',[this],null,null, document.all('ComCode').value, 'ManageCom');" onMouseDown="return showCodeList('AgentCom',[this],null,null, document.all('ComCode').value, 'ManageCom');" onKeyUp="return showCodeListKey('AgentCom',[this],null,null, document.all('ComCode').value, 'ComCode');">
          </TD>
			</TR>
		</table>
		<div id="divHideInput" style="display:none">
			<table class="common">
				<TR class=common id=passwordTR style="display:''">
					<TD class=title5>密码</TD>
					<td class=input5>
						<input class="common wid" type="Password" name=Password id=Password maxlength="48"><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
					<TD class=title5>密码确认</TD>
					<TD class=input5>
						<Input class="common wid" type="Password" name=PasswordConfirm id=PasswordConfirm maxlength="48"><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
				<TR class=common>
					<TD class=title5>用户状态</TD>
					<TD class=input5>
						<Input class=codeno name=UserState id=UserState style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick=" showCodeList('UserState',[this,UserStateName],[0,1]);" onMouseDown=" showCodeList('UserState',[this,UserStateName],[0,1]);" onKeyUp="return showCodeListKey('UserState',[this,UserStateName],[0,1]);"><input class=codename name=UserStateName id=UserStateName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
				<tr>
					<td class=title5>用户描述</td>
					<td class=input5>
						<Input class="common wid" name=UserDescription id=UserDescription>
					</td>
					<TD class=title5>保全权限</TD>
					<TD class=input5>
						<Input class=codeno name=EdorPopedom id=EdorPopedom style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick=" showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1]);" onMouseDown="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1]);" onKeyUp="return showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1]);"><input class=codename name=EdorPopedomName id=EdorPopedomName readonly=true>
					</TD>
				</tr>
				<TR class=common>
					<TD class=title5>核保权限</TD>
					<TD class=input5>
						<Input class=codeno name=UWPopedom style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=UWPopedom onDblClick=" showCodeList('UWPopedom',[this,UWPopedomName],[0,1]);" onMouseDown="showCodeList('UWPopedom',[this,UWPopedomName],[0,1]);" onKeyUp="return showCodeListKey('UWPopedom',[this,UWPopedomName],[0,1]);"><input class=codename name=UWPopedomName id=UWPopedomName readonly=true>
					</TD> 
					<TD class=title5>核赔权限</TD>
					<TD class=input5>
						<Input class=codeno name=ClaimPopedom style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=ClaimPopedom onDblClick=" showCodeList('ClaimPopedom',[this,ClaimPopedomName],[0,1]);" onMouseDown="showCodeList('ClaimPopedom',[this,ClaimPopedomName],[0,1]);" onKeyUp="return showCodeListKey('ClaimPopedom',[this,ClaimPopedomName],[0,1]);"><input class=codename name=ClaimPopedomName id=ClaimPopedomName readonly=true>
					</TD>
				</TR>
				<TR class=common>
					<TD class=title5>其它权限</TD>
					<TD class=input5>
						<Input class=codeno name=OtherPopedom style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=OtherPopedom onDblClick=" showCodeList('OtherPopedom',[this,OtherPopedomName],[0,1]);" onMouseDown="showCodeList('OtherPopedom',[this,OtherPopedomName],[0,1]);" onKeyUp="return showCodeListKey('OtherPopedom',[this,OtherPopedomName],[0,1]);"><input class=codename name=OtherPopedomName id=OtherPopedomName readonly=true>
					</TD>
					<TD class=title5>首席核保标志</TD>
					<TD class=input5>
						<Input class="common wid" name="PopUWFlag" id="PopUWFlag">
					</TD>
				</TR>
				<TR class=common>
					<TD class=title5>超级权限标志</TD>
					<TD class=input5>
						<Input class=codeno name=SuperPopedomFlag id=SuperPopedomFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" onMouseDown="showCodeList('SuperPopedomFlag',[this,SuperPopedomFlagName],[0,1]);" onDblClick=" showCodeList('SuperPopedomFlag',[this,SuperPopedomFlagName],[0,1]);" onKeyUp="return showCodeListKey('SuperPopedomFlag',[this,SuperPopedomFlagName],[0,1]);"><input class=codename name=SuperPopedomFlagName id=SuperPopedomFlagName readonly=true>
					</TD>
					<TD class=title5>操作员</TD>
					<TD class=input5>
						<Input class="common wid" name=Operator id=Operator readonly>
					</TD>
					<TD class=input style="display:none">
						<Input class=common value=<%=operatorComCode%> name=OperatorComCode id=OperatorComCode style="display:none">
					</TD>
					<TD class=input style="display:none">
						<Input class=common value=<%=Operator%> name=OperatorCode id=OperatorCode style="display:none">
					</TD>
				</TR>
			</table>
		</div>
		<table class="common">
			<TR class=common style="display:none">
				<TD class=title>入机日期</TD>
				<TD class=input>
					<Input class="readonly" name=MakeDate id=MakeDate>
				</TD>
				<TD class=title>入机时间</TD>
				<TD class=input>
					<Input class="readonly" name=MakeTime id=MakeTime>
				</TD>
			</TR>
			<TR class=common id ="validTR">
				<TD class=title5>有效开始日期</TD>
				<TD class=input5>
					<!--<Input class="laydate-icon1"  onClick="laydate({elem: '#ValidStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ValidStartDate id=ValidStartDate><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ValidStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ValidStartDate id="ValidStartDate"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class=title5>有效结束日期</TD>
				<TD class=input5>
					<Input class="coolDatePicker"  onClick="laydate({elem: '#ValidEndDate'});" verify="有效结束日期|DATE" dateFormat="short" name=ValidEndDate id=ValidEndDate><span class="icon"><a onClick="laydate({elem: '#ValidEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class=input style="display: none">
					<Input class=common name=HideInitTag id=HideInitTag>
				</TD>
			</TR>
				</table></div>
		<div id="divCmdButton", style="display:''">
			<input value="用户查询" type=button onClick="queryClick()" class="cssButton">
			<INPUT VALUE="用户增加" TYPE=button onClick="insertClick()" class="cssButton">
			<INPUT VALUE="用户更新" TYPE=button onClick="updateClick()" class="cssButton">
			<INPUT VALUE="用户删除" TYPE=button onClick="deleteClick()" class="cssButton">
            <!-- <a href="javascript:void(0);" class="button" onClick="queryClick();">用户查询</a>
            <a href="javascript:void(0);" class="button" onClick="insertClick();">用户增加</a>
            <a href="javascript:void(0);" class="button" onClick="updateClick();">用户更新</a>
            <a href="javascript:void(0);" class="button" onClick="deleteClick();">用户删除</a> -->
		</div>
		<Div id="divUserGrid" style="display: ''">
			<table>
				<tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
					<td class=titleImg>用户表结果</td>
				</tr>
			</table>
            <Div  id= "divPayPlan1" style= "display: ''">
			<table class=common>
				<tr class=common>
					<td style=" text-align: left" colSpan=1>
						<span id="spanUserGrid"></span>
					</td>
				</tr>
			</table>
			<!--<INPUT VALUE="首  页" TYPE=button onclick="userFirstPage()" class="cssButton">
			<INPUT VALUE="上一页" TYPE=button onclick="userPageUp()" class="cssButton">
			<INPUT VALUE="下一页" TYPE=button onclick="userPageDown()" class="cssButton">
			<INPUT VALUE="尾  页" TYPE=button onclick="userLastPage()" class="cssButton">-->
            <div id="divCmdButton1" align="center">
             <input value="首  页"  	class="cssButton90" type="button" onclick="userFirstPage();"/>
		      <input value="上一页" 	class="cssButton91" type="button" onclick="userPageUp();"/>
		      <input value="下一页" 	class="cssButton92" type="button" onclick="userPageDown();"/>
		      <input value="尾  页"  	class="cssButton93" type="button" onclick="userLastPage();"/>
           </div>
		</div></Div>
		<div id="hide" style="display: none">
			<table class=common>
				<tr>
					<TD class=input>
						<Input class=common name=Action id=Action>
					</TD>
				</tr>
			</table>
		</div>
		<Div id="divMenuGrpGrid" style="display: none">
			<table class=common>
				<tr>
					<td class=titleImg>用户拥有的菜单组</td>
					<td class=titleImg>用户未拥有的菜单组</td>
				</tr>
			</table>
			<hr class="line">
			<table class =common1>
			<tr class =common>
			  <TD class=title5>菜单组名称</TD>
					<TD class=input5>
						<Input class="common wid" name=MenuName id="MenuName"/>
					</TD>
			  <TD class=title5>菜单组描述</TD>
					<TD class=input5>
						<Input class="common wid" name=MenuDetail id=MenuDetail>
					</TD>
			</tr>
			<tr>
			<TD class=button width="10%" align=right>
				<INPUT VALUE="用户未拥有的菜单组查询" TYPE=button onClick="unSelectMenu()" class="cssButton10">
			</TD>
			</tr>
			</table>
			<hr class="line">
			<input value="用户菜单组浏览" type=button onClick="showMenuGrp()" style="display:none" class="cssButton">
			<table class=common>
				<tr class=common>
					<td style=" text-align: left" colSpan=1>
						<span id="spanSelectMenuGrpGrid"></span>
					</td>
					<td style=" text-align: left" colSpan=1>
						<span id="spanUnselectMenuGrpGrid"></span>
					</td>
					<td style=" text-align: left" colSpan=1>
						<span id="spanHideMenuGrpGrid1" style="display: none"></span>
					</td>
				</tr>
			</table>
			<div align="center">
			<INPUT VALUE="首  页" TYPE=button onClick="selectFirstPage()" class="cssButton90">
			<INPUT VALUE="上一页" TYPE=button onClick="selectPageUp()" class="cssButton91">
			<INPUT VALUE="下一页" TYPE=button onClick="selectPageDown()" class="cssButton92">
			<INPUT VALUE="尾  页" TYPE=button onClick="selectLastPage()" class="cssButton93">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT VALUE=">" TYPE=button onClick="removeMenus()" class="cssButton">
			<INPUT VALUE="<" TYPE=button onClick="addMenus()" class="cssButton">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<INPUT VALUE="首  页" TYPE=button onClick="unselectFirstPage()" class="cssButton90">
			<INPUT VALUE="上一页" TYPE=button onClick="unselectPageUp()" class="cssButton91">
			<INPUT VALUE="下一页" TYPE=button onClick="unselectPageDown()" class="cssButton92">
			<INPUT VALUE="尾  页" TYPE=button onClick="unselectLastPage()" class="cssButton93">
			</div>
			<table class=common>
				<tr>
					<td class=titleImg>菜单组节点明细表</td>
				</tr>
				<tr>
					<td style=" text-align: left" colSpan=1>
						<span id="spanMenuTree"></span>
					</td>
				</tr>
			</table>
		</div>
		<div id="divSubCmdButton" style="display: none">
			<INPUT VALUE="确  定" TYPE=button onClick="okClick()" class="cssButton">
			<INPUT VALUE="退  出" TYPE=button onClick="cancelClick()" class="cssButton"> 
		</div>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
