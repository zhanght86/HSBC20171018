/***************************************************************
 * <p>ProName：LSQuotUWRuleInput.js</p>
 * <p>Title：核保规则</p>
 * <p>Description：核保规则</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-04
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
		
		afterSubmitSelect();
	}
}

/**
 * 提交后界面展示处理
 */
function afterSubmitSelect(){
	
	if (mOperate == "SAVE") {
		queryUWRuleInfo();
	} else {
		showPage('0');
		initInpBox();
		queryEdorRuleInfo();
	}
}

/**
 * 下拉后动态展示
 */
function afterCodeSelect(o, p) {
	
	if(o=='edorruletype') {
		
		fm.EdorCode.value = "";
		fm.EdorCodeName.value = "";
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
		
		if(p.value=='1') {
			
			showPage('1');
		
		} else if (p.value=='2') {
			
			showPage('2');
		} else {
			
			showPage('0');
		}
	}
	if(o=='quotedorcode') {
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
	}
}

/**
 * 模拟下拉操作
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	if (value1=="edorrulecode") {
		
		if (!isEmpty(fm.RuleType)) {
			var tRuleType = fm.RuleType.value;
			var vCodeType = fm.RuleType.value;
			if(tRuleType=="2"){
				vCodeType = fm.EdorCode.value;
			}
			
			var tSql = "1 and codetype=#edorrulecode# and codeexp=#"+ vCodeType +"#"
			if (returnType=='0') {
				return showCodeList('queryexp',value2,value3,null,tSql,'1','1',180);
			} else {
				return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',180);
			}
		}
	}
}
/**
 * 查询核保规则
 */
function queryUWRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (tActivityID =="0800100004" || tActivityID =="0800100005") {
		tSQLInfo.addSubPara('1');
	} else {
		tSQLInfo.addSubPara('1');
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), UWRuleGrid, 2, 1);
}

/**
 * 查询保全规则
 */
function queryEdorRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), EdorRuleGrid, 2, 1);
}

/**
 * 展示保全规则
 */
function showEdorRuleInfo() {
	
	var tRow = EdorRuleGrid.getSelNo();
	var tRuleType = EdorRuleGrid.getRowColData(tRow-1,1);//保全规则类型编码
		
		showPage(tRuleType);
		
		fm.RuleType.value = EdorRuleGrid.getRowColData(tRow-1,1);
		fm.RuleTypeName.value = EdorRuleGrid.getRowColData(tRow-1,2);
		fm.EdorCode.value = EdorRuleGrid.getRowColData(tRow-1,3);
		fm.EdorCodeName.value = EdorRuleGrid.getRowColData(tRow-1,4);
		fm.CalCode.value = EdorRuleGrid.getRowColData(tRow-1,5);
		fm.CalCodeName.value = EdorRuleGrid.getRowColData(tRow-1,6);
	 
}


/**
 * 保存
 */
function saveClick() {
	
	mOperate = "SAVE";
	var tRow = UWRuleGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	
	fm.RiskCode.value = UWRuleGrid.getRowColData(tRow-1,3);//产品编码
	fm.Params.value = UWRuleGrid.getRowColData(tRow-1,6);//参数
	fm.InputValues.value = UWRuleGrid.getRowColData(tRow-1,8);//参数值
	fm.RuleCode.value = UWRuleGrid.getRowColData(tRow-1,9);//规则编码
	
	if (!checkSave()) {
		return false;
	}
	
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 保存前校验
 */
function checkSave() {
	
	var tInputValues = fm.InputValues.value;
	if (tInputValues==null || tInputValues=="") {
		alert("参数值不能为空！");
		return false;
	}
	return true;
	
}

/**
 * 增加
 */
function addClick() {
	
	mOperate = "INSERT";

	if (!checkSubmit()) {
		return false;
	}
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 修改
 */
function modClick() {
	
	mOperate = "UPDATE";
	var tRow = EdorRuleGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	var tSerialNo = EdorRuleGrid.getRowColData(tRow-1,7);//流水号
	fm.SerialNo.value = tSerialNo;
	if (!checkSubmit()) {
		return false;
	}
	
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 删除
 */
function delClick() {
	
	var tRow = EdorRuleGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	var tSerialNo = EdorRuleGrid.getRowColData(tRow-1,7);//流水号
	fm.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm.action = "./LSQuotUWRuleSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm);
}

/**
 * 提交前的校验
 */
function checkSubmit() {
	
	var tRuleType = fm.RuleType.value;
	if (tRuleType==null || tRuleType=="") {
		alert("保全规则类型不能为空！");
		return false;
	}
	if (tRuleType=="2") {
		if (fm.EdorCode.value==null || fm.EdorCode.value=="") {
			alert("保全项目不能为空！");
			return false;
		}
	}
	if (fm.CalCode.value==null || fm.CalCode.value=="") {
		alert("保全算法不能为空！");
		return false;
	}
	//校验录入的保全规则信息是否已存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotUWRuleSql");
	tSQLInfo.setSqlId("LSQuotUWRuleSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tRuleType);
	tSQLInfo.addSubPara(NullToEmpty(fm.EdorCode.value));
	if(mOperate == "INSERT"){
		tSQLInfo.addSubPara("");//modify JingDian 只能是一个规则保存一次
		tSQLInfo.addSubPara("");
	}else if(mOperate == "UPDATE"){
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(NullToEmpty(fm.SerialNo.value));
	}
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult[0][0]!="0") {
		alert("该保全规则已存在!");
		return false;
	}
	return true;
}

/**
 * 界面要素隐藏/展示
 */
function showPage(tObj) {
	
	if (tObj == "2") {
		document.all("tdRule5").style.display = '';
		document.all("tdRule6").style.display = '';
		document.all("tdRule7").style.display = '';
		document.all("tdRule8").style.display = '';
		document.all("tdRule9").style.display = 'none';
		document.all("tdRule10").style.display = 'none';
		document.all("tdRule11").style.display = 'none';
		document.all("tdRule12").style.display = 'none';
	} else if (tObj == "1") {
		document.all("tdRule5").style.display = 'none';
		document.all("tdRule6").style.display = 'none';
		document.all("tdRule7").style.display = '';
		document.all("tdRule8").style.display = '';
		document.all("tdRule9").style.display = '';
		document.all("tdRule10").style.display = '';
		document.all("tdRule11").style.display = 'none';
		document.all("tdRule12").style.display = 'none';
	} else {
		document.all("tdRule5").style.display = 'none';
		document.all("tdRule6").style.display = 'none';
		document.all("tdRule7").style.display = 'none';
		document.all("tdRule8").style.display = 'none';
		document.all("tdRule9").style.display = '';
		document.all("tdRule10").style.display = '';
		document.all("tdRule11").style.display = '';
		document.all("tdRule12").style.display = '';
	}
}

