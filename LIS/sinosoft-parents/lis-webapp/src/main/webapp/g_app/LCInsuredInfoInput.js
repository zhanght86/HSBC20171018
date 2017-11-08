/***************************************************************
 * <p>ProName：LCInsuredInfoInput.js</p>
 * <p>Title：人员清单维护</p>
 * <p>Description：人员清单维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();
var tQuerFlag;

/**
 * 提交数据后返回操作

 */
function afterSubmit(tFlagStr, tContent) {
	
	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {
	
		showInfo.close();
	}
	
	if (tFlagStr == "Fail") {	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		if(mOperate =="CREATE" ){//初始化，不做任何反应
			return;
		}
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		if(mOperate=="CONFDATA"){
			turnBack();
		}
		if(mOperate =="CHOSEDEL" || mOperate =="CONDDEL" || mOperate =="ALLDEL" ){
			insuredQuery();
		}
	}	
}

/**
 * 提交
 */
function submitForm() {
	
	var i = 0;
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
}

/**
 * 调用保险方案的方法
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	var sql="1 and (GrpContNo=#"+fm.GrpPropNo.value+"# or GrpContNo like #"+fm.GrpPropNo.value+"%#) ";
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,sql,'1',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	var sql="1 and (GrpContNo=#"+fm.GrpPropNo.value+"# or GrpContNo like #"+fm.GrpPropNo.value+"%#) ";
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,sql,'1',1,null);
}


/**
* 查询被保人信息列表
*/

function insuredQuery(){
	
	tQuerFlag ='Q';	
	var querySql = "";	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredInfoSql");
	tSQLInfo.setSqlId("LCInsuredInfo1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.Customerno.value);
	tSQLInfo.addSubPara(fm.CustomName.value);
	tSQLInfo.addSubPara(fm.IDNO.value);
	tSQLInfo.addSubPara(fm.CustomerID.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.SysPlanCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}	
}

/**
* 询价方案查询
*/

function showPlanQuery(){
	
	window.open("./LCContPlanQueryMain.jsp?ContPlanType="+tContPlanType+"&PolicyNo="+tGrpPropNo ,"方案查询",'height='+screen.availHeight+',width='+screen.availWidth+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
* 添加被保人信息
*/

function goIntoInsuredList(){
	
	var tContNo ="";
	var tInsuredno =""
	var tGrpPropNo=fm.GrpPropNo.value;
	var tContPlanType = fm.ContPlanType.value;
	var tFlag = fm.Flag.value;
	
	window.open("./LCinsuredAddMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredno+"&ContNo="+tContNo+"&ContPlanType="+tContPlanType+"&Flag="+tFlag,"添加被保人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* 导入被保人清单
*/
function showlmpInsuredList(){
	
	window.open("./LCInsuredUploadMain.jsp?ContPlanType="+tContPlanType+"&GrpPropNo="+tGrpPropNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"人员清单导入",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* 被保险人汇总信息
*/

function showQueryInsredList(){
	
	var tGrpPropNo=fm.GrpPropNo.value;
	window.open("./LCInsuredCollectInput.jsp?GrpPropNo="+tGrpPropNo,"导入被保人清单",'width=950,height=820,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* 选择删除
*/
function choseDelet(){
	
	var hasCheck = false ;	
	for (var i = 0; i < QueryScanGrid.mulLineCount; i++) {
		if (QueryScanGrid.getChkNo(i)) {
			hasCheck = true;
			break;
		}
	}
	if (!hasCheck) {
		alert("请至少选择一行数据进行处理操作！");
		return false;
	}
	
	mOperate = "CHOSEDEL";
	submitForm();
	
}

/**
* 条件删除
*/
function condDelete(){
	
	if(fm.Customerno.value =="" && fm.CustomName.value=="" && fm.IDNO.value=="" && fm.CustomerID.value=="" && fm.ContPlanCode.value==""){
		alert("请至少录入一个查询条件进行条件删除操作！");
		return false;
	}

	if(tQuerFlag !='Q'){
		alert("请先点击【查询】按钮查询数据！");
		return false;
	}
	var tCount =  QueryScanGrid.mulLineCount;
	if(tCount =='0'){		
		alert("没有需要进行【条件删除】的数据！");
		return false;	
	}
	
	mOperate = "CONDDEL";
	submitForm();
	
}

/**
* 全部删除
*/
function allDelete(){
	
	if(!confirm("是否确定执行全部删除操作？")){
		return false;
	}
	
	mOperate = "ALLDEL";
	submitForm();
}

/**
* 进入明细页面
*/
function goToInfo(){
	
	var tSelNo = QueryScanGrid.getSelNo();
	if(tSelNo!='0'){	
		var tInsuredno = QueryScanGrid.getRowColData(tSelNo-1,1);
		var tContNo = QueryScanGrid.getRowColData(tSelNo-1,2);
		var tContPlanType = fm.ContPlanType.value
	}
	var tGrpPropNo=fm.GrpPropNo.value;
	var tFlag = fm.Flag.value;
	
	window.open("./LCinsuredAddMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredno+"&ContNo="+tContNo+"&ContPlanType="+tContPlanType+"&Flag="+tFlag,"添加被保人信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* 人员清单确认
*/
function saveClick(){
	mOperate = "CONFDATA";
	submitForm();
}

//返回
function turnBack(){
	location.href = "./LCInsuredListInput.jsp";
}

/**
** 弹性福利计划
**/
function initContPlanQuery(){
	if("1"==tPolicyType){
		divContPlanQuery1.style.display="none";
		divContPlanQuery2.style.display="";
	}
}

function showAllPlan() {
	tAllDetailOpen = window.open("./LCContPlanAllMain.jsp?PolicyNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType, "1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
