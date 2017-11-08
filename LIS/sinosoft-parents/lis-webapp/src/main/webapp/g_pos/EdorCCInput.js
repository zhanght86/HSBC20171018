/***************************************************************
 * <p>ProName：EdorCCInput.js</p>
 * <p>Title：建工险工程面积造价变更</p>
 * <p>Description：建工险工程面积造价变更</p>
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
var valType;

//初始化保单信息
function initPolicyInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCCSql");
	tSQLInfo.setSqlId("EdorCCSql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
//	alert("001");
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tResult==null) {
		return false;
	} else {
		fm.PremCalMode.value=tResult[0][0];
		fm.PremCalName.value=tResult[0][1];
		fm.EnginArea.value=tResult[0][2];
		fm.EnginCost.value=tResult[0][3];
		fm.EnginAreaN.value=tResult[0][2];
		fm.EnginCostN.value=tResult[0][3];
		if(fm.PremCalMode.value=="2"){
			divEnginCost.style.display="";
			divEnginCostN.style.display="";
		}
		if(fm.PremCalMode.value=="3"){
			divEnginArea.style.display="";
			divEnginAreaN.style.display="";
		}
	}
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_pos.EdorCCSql");
//	tSQLInfo.setSqlId("EdorCCSql1");
//	tSQLInfo.addSubPara(tGrpContNo);
//	tSQLInfo.addSubPara(tEdorNo);
//	tSQLInfo.addSubPara(tEdorType);
//	
//	var tResult2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tResult2==null) {
//		alert("002");
//		return false;
//	} else {
//		fm.EnginAreaN.value=tResult2[0][2];
//		fm.EnginCostN.value=tResult2[0][3];
//	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCCSql");
	tSQLInfo.setSqlId("EdorCCSql9");
	tSQLInfo.addSubPara(tEdorNo);
//	alert("EdorCCSql9");
	var tResult3 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	var tConfDate = tResult3[0][0];
	if (tResult3==null||tResult3=="") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorCCSql");
		tSQLInfo.setSqlId("EdorCCSql10");
		tSQLInfo.addSubPara(tEdorNo);
		var tResult4 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tResult4==null||tResult4==""){
			return false;
		}else{
			fm.EnginArea.value=tResult4[0][3];
			fm.EnginCost.value=tResult4[0][1];
			fm.EnginAreaN.value=tResult4[0][2];
			fm.EnginCostN.value=tResult4[0][0];
		}
	} else {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorCCSql");
		tSQLInfo.setSqlId("EdorCCSql11");
		tSQLInfo.addSubPara(tEdorNo);
		var tResult5 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tResult5==null||tResult5==""){
			return false;
		}else{
			fm.EnginAreaN.value=tResult5[0][1];
			fm.EnginCostN.value=tResult5[0][0];
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorCCSql");
		tSQLInfo.setSqlId("EdorCCSql12");
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tConfDate);
		var tResult6 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tResult6==null||tResult6==""){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorCCSql");
			tSQLInfo.setSqlId("EdorCCSql13");
			tSQLInfo.addSubPara(tEdorNo);
			var tResult7 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tResult7==""||tResult7==null){
				return false;
			}else{
				fm.EnginArea.value=tResult7[0][1];
				fm.EnginCost.value=tResult7[0][0];
			}
		}else{
			fm.EnginArea.value=tResult6[0][1];
			fm.EnginCost.value=tResult6[0][0];
		}
	}
	
}
//保存操作
function saveClick(){
	
	if("2"==valType){
		if(""==fm.EnginCostN.value){
			alert("请录入变更后总造价");
			return false;
		}
	}else if("3"==valType){
		if(""==fm.EnginAreaN.value){
			alert("请录入变更后总面积");
			return false;
		}
	}
	if(!verifyInput()){
		return false;
	}
	
	mOperate="SAVE";
	fm.action = "./EdorCCSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	tSQLInfo.setResourceName("g_pos.EdorCCSql");
	tSQLInfo.setSqlId("EdorCCSql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorType);
	
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
	tSQLInfo.setResourceName("g_pos.EdorCCSql");
	tSQLInfo.setSqlId("EdorCCSql4");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
		alert("查询新单保费错误！");
		return false;
	} else {
		fm.FirstPrem.value=tPremResult[0][0];
		fm.FirstEnginCost.value=tPremResult[0][1];
		fm.FirstEnginArea.value=tPremResult[0][2];
	}
}

//初始化保全信息
function initReason(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCCSql");
	tSQLInfo.setSqlId("EdorCCSql5");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tSAResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tSAResult==null) {
		return false;
	} else {
		fm.GetMoney.value=tSAResult[0][0];
		fm.ReasonDesc.value=tSAResult[0][3];
	}
}

//计算保费
function calInitPrem(){
	
	valType = fm.PremCalMode.value;
	var vInitPrem = fm.FirstPrem.value;
	var vInitEnginCost = fm.FirstEnginCost.value;
	var vInitEnginArea = fm.FirstEnginArea.value;
	
	if("2"==valType){
		var sEnginCost = fm.EnginCost.value;
		var eEnginCost = fm.EnginCostN.value;
		if(eEnginCost=="" || !isNumeric(eEnginCost)){
			alert("请录入变更后总造价");
			return false;
		}
		var endCost = Arithmetic(Number(eEnginCost),"-",Number(sEnginCost),2);
		fm.CalPrem.value= Arithmetic(Arithmetic(Number(vInitPrem),"*",Number(endCost),2),"/",Number(vInitEnginCost),2);
	}else if("3"==valType){
		var sEnginArea = fm.EnginArea.value;
		var eEnginArea = fm.EnginAreaN.value;
		if(eEnginArea=="" || !isNumeric(eEnginArea)){
			alert("请录入变更后总面积");
			return false;
		}
		var endArea = Arithmetic(Number(eEnginArea),"-",Number(sEnginArea),2);
		fm.CalPrem.value= Arithmetic(Arithmetic(Number(vInitPrem),"*",Number(endArea),2),"/",Number(vInitEnginArea),2);
	}
}
