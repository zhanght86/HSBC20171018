<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var tManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<html>
<head>
	<title>?????????</title>
	<!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
    <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LDUnifyCodeTypeInput.js"></script>
	<%@include file="./LDUnifyCodeTypeInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form name=fm  id=fm target=fraSubmit method=post action="./LDUnifyCodeTypeSave.jsp" style="margin-bottom:0px;margin-top: 0px">
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCodeType);">
			</td>
			<td class=titleImg>系统编码类型信息</td>
		</tr>
	</table>
	<div id="divCodeType" style="display:''" class="maxbox1">
		<table class=common>
			<tr class=common>
				<td class=title>系统名称</td>
				<td class=input style="display:" id="divSysCode"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SysCode id=SysCode verify="系统名称|code:SysCode" ondblclick="return showCodeList('SysCode',[this,SysCodeName],[0,1],null,null,null,'1',null);" onclick="return showCodeList('SysCode',[this,SysCodeName],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('syscode',[this,SysCodeName],[0,1],null,null,null,'1',null);"><input class=codename name=SysCodeName id=SysCodeName elementtype=nacessary></td>
				<td class=input style="display:none" id="divSysCodeReadOnly">
					<input class=codeno name=SysCodeReadOnly readonly=true><input class=codename name=SysCodeReadOnlyName readonly=true elementtype=nacessary>
				</td>
				<td class=title>编码类型</td>
				<td class=input><input class="wid" class=common name=CodeType id=CodeType elementtype=nacessary verify="编码类型|len<=10"></td>
				<td class=title>编码类型名称</td>
				<td class=input><input class="wid" class=common name=CodeTypeName id=CodeTypeName elementtype=nacessary verify="编码类型名称|len<=33"></td>
			</tr>
			<tr class=common>
				<td class=title>维护标识</td>
				<td class=input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MaintainFlag id=MaintainFlag verify="维护标识|code:maintainflag" ondblclick="return showCodeList('maintainflag',[this,MaintainFlagName],[0,1],null,null,null,1);" onclick="return showCodeList('maintainflag',[this,MaintainFlagName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('maintainflag',[this,MaintainFlagName],[0,1],null,null,null,1);"><input class=codename name=MaintainFlagName id=MaintainFlagName elementtype=nacessary></td>
				<td class=title>编码类型描述</td>
				<td class=input><input class="wid" class=common name=CodeTypeDescription id=CodeTypeDescription verify="编码类型描述|len<=100"></td>
				<td class=title>状态&nbsp;<font style=" color:#FF0000; font-weight:normal; font-size:12px">[<!--?????-->仅查询]</font></td>
				<td class=input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State id=State verify="状态|code:ustate" ondblclick="return showCodeList('ustate',[this,StateName],[0,1],null,null,null,1);" onclick="return showCodeList('ustate',[this,StateName],[0,1],null,null,null,1);" onkuyup="return showCodeListKey('ustate',[this,StateName],[0,1],null,null,null,1);"><input class=codename name=StateName id=StateName ></td>
			</tr>
		</table></div>
		<table>
			<input class=cssButton name=QueryButton value="查  询" type=button onclick="queryClick()">
			<input class=cssButton name=AddButton value="新  增" type=button onclick="addClick()">
			<input class=cssButton name=UpdateButton value="修  改" type=button onclick="updateClick()">
			<input class=cssButton name=DeleteButton value="删  除" type=button onclick="deleteClick()">
			<input class=cssButton name=DeleteButton value="提  交" type=button onclick="submitClick()">
			<input class=cssButton name=DeleteButton value="重  置" type=button onclick="resetClick()">
		</table>
	
   	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCodeTypeInfo);">
				</td>
				<td class=titleImg>系统编码类型列表</td>
			</tr>
		</table>
		<div id="divCodeTypeInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1><span id="spanCodeTypeInfoGrid" ></span></td>
				</tr>
			</table>			
			<center>
				<input value="首页" class=cssButton90 type=button onclick="turnPage1.firstPage();">
				<input value="上一页" class=cssButton91 type=button onclick="turnPage1.previousPage();">
				<input value="下一页" class=cssButton92 type=button onclick="turnPage1.nextPage();">
				<input value="尾页" class=cssButton93 type=button onclick="turnPage1.lastPage();">
			</center>
		</div>
		
		
		<!--系统编码-->
		<table>
			<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divCode);">
			</td>
			<td class=titleImg>系统编码信息</td>
			</tr>
		</table>
	<div id="divCode" style="display: ''" class="maxbox1">
		<table class=common>
			<tr class=common>
				<td class=title>系统名称</td>
				<td class=input style="display:" id="divSysCode1"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SysCode1 id=SysCode1 verify="系统名称|code:SysCode" ondblclick="return showCodeList('SysCode',[this,SysCode1Name],[0,1],null,null,null,'1',null);" onclick="return showCodeList('SysCode',[this,SysCode1Name],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('syscode',[this,SysCode1Name],[0,1],null,null,null,'1',null);"><input class=codename name=SysCode1Name id=SysCode1Name elementtype=nacessary></td>
				<td class=input style="display:none" id="divSysCodeReadOnly1">
					<input class=codeno name=SysCodeReadOnly1 readonly=true><input class=codename name=SysCodeReadOnlyName1 id=SysCodeReadOnlyName1 readonly=true elementtype=nacessary>
				</td>
				
				<td class=title>编码类型</td>
				<td class=input style="display:" id="divCodeType1"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=CodeType1 id=CodeType1 verify="编码类型|code:CodeType" ondblclick="return showCodeList('CodeType',[this,CodeType1Name],[0,1],null,null,null,'1',null);" onclick="return showCodeList('CodeType',[this,CodeType1Name],[0,1],null,null,null,'1',null);" onkeyup="return showCodeListKey('syscode',[this,CodeType1Name],[0,1],null,null,null,'1',null);"><input class=codename name=CodeType1Name id=CodeType1Name elementtype=nacessary></td>
				<td class=input style="display:none" id="divCodeTypeReadOnly1">
					<input class=codeno name=CodeTypeReadOnly1 readonly=true><input class=codename name=CodeTypeReadOnlyName1 id=CodeTypeReadOnlyName1 readonly=true elementtype=nacessary>
				</td>
				
				<td class=title>状态&nbsp;<font style=" color:#FF0000; font-weight:normal; font-size:12px">[仅查询]</font></td>
				<td class=input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=State1 id=State1 verify="状态|code:ustate" ondblclick="return showCodeList('ustate',[this,State1Name],[0,1],null,null,null,1);" onclick="return showCodeList('ustate',[this,State1Name],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('ustate',[this,State1Name],[0,1],null,null,null,1);"><input class=codename name=State1Name id=State1Name ></td>
			</tr>
			<tr class=common>
				<td class=title>编码</td>
				<td class=input><input class="wid" class=common name=Code id=Code elementtype=nacessary verify="编码|len<=100"></td>
				<td class=title>编码名称</td>
				<td class=input><input class="wid" class=common name=CodeName id=CodeName elementtype=nacessary maxlength=30></td>   
				<td class=title>编码别名</td>
				<td class=input><input class="wid" class=common name=CodeAlias id=CodeAlias maxlength=100></td>   
			</tr>
		</table>   </div>
		<table>
			<input class=cssButton name=QueryButton value="查  询" type=button onclick="queryClick1()">
			<input class=cssButton name=AddButton value="新  增" type=button onclick="addClick1()">
			<input class=cssButton name=UpdateButton value="修  改" type=button onclick="updateClick1()">
			<input class=cssButton name=DeleteButton value="删  除" type=button onclick="deleteClick1()">
			<input class=cssButton name=DeleteButton value="提  交" type=button onclick="submitClick1()">
			<input class=cssButton name=DeleteButton value="重  置" type=button onclick="resetClick1()">
		</table>   
	 
	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divCodeInfo);">
				</td>
				<td class=titleImg><!--???????б?-->系统编码列表</td>
			</tr>
		</table>
		<div id="divCodeInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanCodeInfoGrid" ></span>
					</td>
				</tr>
			</table>			
			<center>
				<input value="首页" class=cssButton90 type=button onclick="turnPage2.firstPage();">
				<input value="上一页" class=cssButton91 type=button onclick="turnPage2.previousPage();">
				<input value="下一页" class=cssButton92 type=button onclick="turnPage2.nextPage();">
				<input value="尾页" class=cssButton93 type=button onclick="turnPage2.lastPage();">
			</center>
		</div>

		<input type=hidden name=fmtransact>
		
</form>
<span id="spanCode" style="display: 'none'; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
