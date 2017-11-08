<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<!--*******************************************************
* 程序名称：station.jsp
* 程序功能：系统标题页面
* 最近更新人：朱向峰
* 最近更新日期：2005-3-21 14:45
*******************************************************-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
			BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
	String nodecode = request.getParameter("nodecode");
	String Ip = request.getParameter("Ip");
	tTransferData.setNameAndValue("nodecode", nodecode);
	tTransferData.setNameAndValue("Ip", Ip);

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	tVData.add(tG);
	tVData.add(tTransferData);

	tBusinessDelegate.submitData(tVData, "", "Station");
	tVData = null;
	tVData = tBusinessDelegate.getResult();
	tTransferData = (TransferData) tVData.getObjectByObjectName(
			"TransferData", 0);

	String ls = (String) tTransferData.getValueByName("ls");
	//显示登录区站及用户姓名
	String LogonStr = (String) tTransferData.getValueByName("logonStr");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../common/css/Project.css">
<script src="../common/javascript/Common.js" type="text/javascript"></script>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<script type="text/javascript">
function showmenu()
{
	if(parent.fraSet.cols=="0%,*,0%,0%")
	{
		document.all("menushow").style.display = "none";
		parent.fraSet.cols = "180,*,0%,0%";
	}
}
var mMenuTree = null;//以字符串保存菜单
var gInputContent = "";
function showMenuQuick(inputObj)
{	
	//document.body.onclick=function (){resizeFrame();}	
	initMenuTree();
	//查找menu2中的Menutree，返回查找结果
	var regContent = showMenu.value;
	if(!checkRegContent(regContent)){
		//校检输入字符
		return null;
	}
	var regStr = eval("/text:[^;]*"+regContent+"[^;]*/g");
	var temp = mMenuTree;
	var reg = mMenuTree.match(regStr);
	var txtVarData = "";
	var regLength = 0;
	if(reg){
		regLength=reg.length;
	}
	var tempArray = new Array();
	tempArray[0] = "0|"+regLength;
	for(i=0;i<regLength;i++){
		j = i+1;
		tempArray[j] = "^"+i+"|"+reg[i].split(":")[1]+"|||";
	}
	txtVarData = tempArray.join();
	parent.EX.fm.all("txtVarData").value=txtVarData;
	mVs.deleteVar("QuickMenu");
	mVs.deleteVar("QuickMenuSelect");
	parent.document.frames('fraQuick').initializeCode('QuickMenu', '' , '','0');
	showCodeList('QuickMenu',[inputObj,inputObj],[0,1],parent.fraQuick,null,null,null,null,0);
	if(reg){
		afterShow();
	}
}
var flagFocusOnShowMenu = false;
var flagFocusOnSpanCode = false;


function initMenuTree(){
	//保存菜单信息到字符串
	if(mMenuTree==null){
		var temp = parent.fraMenu.MenuTree;
		var tempStr = new Array();
		var i = 0;
		for(var id in temp.node){
			tempStr[i] = "node:"+id+";"+temp.node[id];
			i++;
		}
		for(var id in temp.nodes){
			tempStr[i] = "nodes:"+id+";"+temp.nodes[id];
			i++;
		}
		mMenuTree=tempStr.join("|||");
	}
}
function checkRegContent(inputContent)
{
	//校检
	if(gInputContent == inputContent){
		return false;
	}else{
		gInputContent = inputContent;
	}
	return true;
}
function changeshowMenu(){
	//选中后触发事件，定位到指定的菜单
	var regContent = showMenu.value;
	var regStr = eval("/node[^;]*;text:"+regContent+";/g");
	var reg = mMenuTree.match(regStr);
	if(reg==null){
		regStr = eval("/node[^;]*;index[^;]*;text:"+regContent+";/g");
		reg = mMenuTree.match(regStr);
	}
	var temp=reg[0].split(";")[0].split(":")[1].split("_")[1];
	parent.fraMenu.MenuTree.focus(temp,true);
	afterSelect();
}
function afterSelect(){
	setTimeout(function(){changeFrameRows();},100);
}
function afterShow(){
	setTimeout(function(){changeFrameRows();},100);
}
function resizeFrame()
{
	setTimeout(function(){changeFrameRows();},100);
}
function changeFrameRows(){
	//根据内容调整窗口大小
	if(!flagFocusOnShowMenu&&!flagFocusOnSpanCode){parent.fraQuick.spanCode.style.display="none";}
	var spanDisplay = parent.fraQuick.spanCode.style.display;	
	if(spanDisplay=="none"){
		parent.fraTalk.rows = "30,67%";
	}else{
		parent.fraTalk.rows = "140,67%";
	}
}
function initWaterMark(){
	  $('#showMenu').watermark("菜单快速搜索");
}

</script>
<link rel='stylesheet' type='text/css' href='../common/css/other.css'>
<link rel="stylesheet" type="text/css" href="../common/css/watermark.css">
<script src="../common/javascript/jquery-1.7.2.js"></script>
<script src="../common/javascript/jquery.form.js"></script>
<script src="../common/javascript/jquery.easyui.js"></script>
<script src="../common/javascript/jquery.watermark.min.js"></script>
<script src="../common/cvar/xqkchinese.core.js"></script>
<script type="text/javascript">  
	$(document).ready(function() {
		initWaterMark();	
	});
	$("#showMenu").live("blur",function(){
		flagFocusOnShowMenu=false;resizeFrame();
	}).live("focus",function(){
		flagFocusOnShowMenu=true;
	}); 
	$("#spanCode").live("blur",function(){
		flagFocusOnSpanCode=false;resizeFrame();
	}).live("focus",function(){
		flagFocusOnSpanCode=true;
	}); 
</script> 
	
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0"
	oncontextmenu="self.event.returnValue=false">
<table width="100%" height="25" cellspacing="0">
	<tr>
		<th align="left" id="menushow" style="display:none" name="menushow">
		<a href="#" onclick="showmenu();"> <img
			src="../common/images/t_open.gif" border="0" title="显示菜单栏" alt="">
		</a></th>
		<th align="center"><font style="font-size:9pt" color="#FFFFFF"><%=LogonStr%></font></th>
		<th align="center"><img src="../common/themes/icons/search.png"></img><input id="showMenu" name="showMenu" type="text"
			onkeyup="showMenuQuick(this);"  height="9" /></th>
		<!--<th align=right><font style="font-size:9pt" color="#FFFFFF"><%//ls%></font></th>-->
	</tr>
</table>
<span id="spanCode"  style="display: none; position:absolute; slategray; left: 736px; top: 264px; width: 229px; height: 44px;"> </span>
</body>
</html>
