/***************************************************************
 * <p>ProName：EdorSAInput.js</p>
 * <p>Title：建工险保单延期</p>
 * <p>Description：建工险保单延期</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

//初始化保单信息
function initPolicyInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql1");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tResult==null) {
		return false;
	} else {
		fm.PremCalMode.value=tResult[0][0];
		fm.PremCalName.value=tResult[0][1];
		fm.EnginArea.value=tResult[0][2];
		fm.EnginCost.value=tResult[0][3];
		if(fm.PremCalMode.value=="2"){
			divEnginCost.style.display="";
		}
		if(fm.PremCalMode.value=="3"){
			divEnginArea.style.display="";
		}
	}
}

//保存操作
function saveClick(){
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="SAVE";
	fm.action = "./EdorSASave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}
function submitFunc(){
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initReason();
	}	
}

//初始化保存信息查询
function initSACount(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tSAResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tSAResult==null) {
		return false;
	} else {
		fm.Mtime.value=tSAResult[0][0];
	}
}

//初始化保存信息查询
function initPrem(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql4");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
		return false;
	} else {
		fm.FirstPrem.value=tPremResult[0][0];
		//fm.EndDate.value=tPremResult[0][1];
	}
}
//计算终止日期
function getStopDate(){
	
	var startDate = fm.EndDate.value;
	var sDeferDays = fm.DeferDays.value;
	if(NullToEmpty(startDate)==""){
		alert("获取保单满期日期错误");
		return false;
	}
	if (sDeferDays!="" && !isInteger(sDeferDays)) {
		alert("不是有效的整数！");
		return false;
	}
	if(parseFloat(sDeferDays)<1){
		alert("请录入大于0的整数");
		return false;
	}
	fm.StopDate.value= addDate(4,sDeferDays,startDate);
	getDeferDays();
}

//计算间隔
function getDeferDays(){
	var startDate = fm.EndDate.value;
	if(NullToEmpty(startDate)==""){
		return false;
	}
	var stopDate = fm.StopDate.value;
	if(NullToEmpty(stopDate)==""){
		alert("请录入保单延期日期错误!");
		return false;
	}
	if(compareDate(stopDate,startDate)!=1){
		alert("请录入大于保单满期的延期日期!");
		return false;
	}
	fm.DeferDays.value = dateDiff(startDate,stopDate,"D");
	calInitPrem();
}

//初始化保全信息
function initReason(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql5");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tSAResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tSAResult==null) {
		return false;
	} else {
		fm.GetMoney.value=tSAResult[0][0];
		fm.Reason.value=tSAResult[0][1];
		fm.ReasonName.value=tSAResult[0][2];
		fm.ReasonDesc.value=tSAResult[0][3];
	}
}

//计算保费
function calInitPrem(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql6");
	tSQLInfo.addSubPara(fm.DeferDays.value);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
		alert("计算保费错误！");
		return false;
	} else {
		fm.InitPrem.value=tPremResult[0][0];
	}
}


//保全延期日期
function queryStopDate(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql7");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
	} else {
		fm.StopDate.value=tPremResult[0][0];
		
		fm.DeferDays.value = dateDiff(fm.EndDate.value,fm.StopDate.value,"D");
		calInitPrem();
	}
}

//保全延期日期
function queryEndDate(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var tEndResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tEndResult==null) {
	} else {
		fm.EndDate.value=tEndResult[0][0];
	}
}
