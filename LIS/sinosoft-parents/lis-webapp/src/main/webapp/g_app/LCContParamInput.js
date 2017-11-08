/***************************************************************
 * <p>ProName：LCContParamInput.js</p>
 * <p>Title：产品参数信息维护--管理费维护</p>
 * <p>Description：产品参数信息维护--管理费维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";
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
	var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	fm2.Operate.value = "";
	fm2.SerialNo.value = "";
	initPageInfo();
}

/**
 * 查询已维护管理费信息列表
 */
function queryManageFee() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContParamSql");
	tSQLInfo.setSqlId("LCContParamSql1");
	tSQLInfo.addSubPara(tBussNo);
	turnPage1.queryModal(tSQLInfo.getString(), ManageFeeGrid, 1, 1);

}

/**
 * 点击列表后展示界面要素
 */
function showManageFee() {
	
	initInpBox();
	var tRow = ManageFeeGrid.getSelNo();
	fm2.RiskCode.value = ManageFeeGrid.getRowColData(tRow-1,8);
	fm2.RiskName.value = ManageFeeGrid.getRowColData(tRow-1,1);
	fm2.AccType.value = ManageFeeGrid.getRowColData(tRow-1,9);
	fm2.AccTypeName.value = ManageFeeGrid.getRowColData(tRow-1,2);
	fm2.FeeType.value = ManageFeeGrid.getRowColData(tRow-1,10);
	fm2.FeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,3);
	
	if (fm2.FeeType.value=='0') {
		document.getElementById('allTR1').style.display = '';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.DeductType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.DeductTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.FeeValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
	} else if (fm2.FeeType.value=='1') {
		
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = '';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.MonthFeeType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.MonthFeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.MonthValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
	} else if (fm2.FeeType.value=='2') {
		
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = '';
		document.getElementById('allTR4').style.display = '';
		fm2.YearFeeType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.YearFeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.YearValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
		fm2.YearStartValue.value = ManageFeeGrid.getRowColData(tRow-1,6);
		fm2.YearEndValue.value = ManageFeeGrid.getRowColData(tRow-1,7);
	}
		var tRow = ManageFeeGrid.getSelNo();
		tSQLInfo = new SqlClass();
		
		tSQLInfo.setResourceName("g_app.LCContParamSql");
		tSQLInfo.setSqlId("LCContParamSql4");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm2.RiskCode.value);
		tSQLInfo.addSubPara(fm2.AccType.value);

		var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);

		if(tResult!=null && tResult[0]=='1'){
			fm2.ModButton.disabled=true; 
			fm2.DelButton.disabled=true; 
		}else{
			fm2.ModButton.disabled=false; 
			fm2.DelButton.disabled=false; 
		}
	
}

/**
 * 选择管理费类型后展示界面要素
 */
function afterCodeSelect(o, p) {
	
	if(o=="contplanrisk"){
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.AccType.value="";
		fm2.AccTypeName.value="";
		fm2.FeeType.value="";
		fm2.FeeTypeName.value="";
		clearContent();
	} else if(o=="risktoacc"){
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.FeeType.value="";
		fm2.FeeTypeName.value="";
		clearContent();
	} else if(o=='mafeetype') {
		
		if(p.value=='0') {
			
			document.getElementById('allTR1').style.display = '';
			document.getElementById('allTR2').style.display = 'none';
			document.getElementById('allTR3').style.display = 'none';
			document.getElementById('allTR4').style.display = 'none';
			clearContent();
		} else if(p.value=='1') {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = '';
			document.getElementById('allTR3').style.display = 'none';
			document.getElementById('allTR4').style.display = 'none';
			clearContent();
		} else {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = 'none';
			document.getElementById('allTR3').style.display = '';
			document.getElementById('allTR4').style.display = '';
			clearContent();
		}
	} else if(o=="deducttype"){
		
		fm2.MonthValue.value="";
		fm2.MonthValue.value="";
		fm2.YearStartValue.value="";
		fm2.YearEndValue.value="";
	}
}

/**
 * 初始化界面信息
 */
function initPageInfo() {
	
	queryManageFee();
	
	fm2.RiskCode.value = "";
	fm2.RiskName.value = "";
	fm2.AccType.value = "";
	fm2.AccTypeName.value = "";
	fm2.FeeType.value = "";
	fm2.FeeTypeName.value = "";
	
	document.getElementById('allTR1').style.display = 'none';
	document.getElementById('allTR2').style.display = 'none';
	document.getElementById('allTR3').style.display = 'none';
	document.getElementById('allTR4').style.display = 'none';
}

/**
 * 管理费维护--增加
 */
function addSubmit() {
	
	if (!checkSubmit()) {
		return false;
	}
	if (fm2.FeeType.value!="2") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContParamSql");
		tSQLInfo.setSqlId("LCContParamSql2");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm2.RiskCode.value);
		tSQLInfo.addSubPara(fm2.AccType.value);
		tSQLInfo.addSubPara(fm2.FeeType.value);
		var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if(tRes!=null && tRes[0]=='1'){
			alert("该类型费用已经存在!");
			return false;
		}
	} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCContParamSql");
		tSQLInfo.setSqlId("LCContParamSql3");
		tSQLInfo.addSubPara(tBussNo);
		tSQLInfo.addSubPara(fm2.RiskCode.value);
		tSQLInfo.addSubPara(fm2.AccType.value);
		tSQLInfo.addSubPara(fm2.FeeType.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tRes!=null && tRes[0]=='1'){
			alert("该类型费用已经存在!");
			return false;
		}
	}
	
	mOperate = "INSERT";
	fm2.action = "./LCContParamSave.jsp?Operate="+ mOperate +"&BussNo="+ tBussNo +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 管理费维护--修改
 */
function modSubmit() {
	
	var tRow = ManageFeeGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	mOperate = "UPDATE";
	fm2.action = "./LCContParamSave.jsp?Operate="+ mOperate +"&BussNo="+ tBussNo +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 管理费维护--删除
 */
function delSubmit() {
	
	var tRow = ManageFeeGrid.getSelNo();
	if (tRow==0) {
		alert("请先选择一条信息！");
		return false;	
	}
	
	mOperate = "DELETE";
	fm2.action = "./LCContParamSave.jsp?Operate="+ mOperate +"&BussNo="+ tBussNo +"&BussType="+ tBussType +"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}
/**
 * 提交前校验
 */
function checkSubmit() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (fm2.FeeType.value=="0") {
		if (fm2.DeductType.value==null || fm2.DeductType.value =="") {
			alert("初始管理费扣除方式不能为空！");
			return false;
		}
		if (fm2.FeeValue.value==null || fm2.FeeValue.value =="") {
			alert("初始管理费(元)/比例不能为空！");
			return false;
		}
		if(!isNumeric(fm2.FeeValue.value)){
			alert("初始管理费(元)/比例不是有效数字!");
			return false;
		}
		if((fm2.DeductType.value=='001'||fm2.DeductType.value=='003')&&(fm2.FeeValue.value<0||fm2.FeeValue.value>1)){
			alert("初始管理费(元)/比例必须为大于0小于1的数字!");
			return false;
		}else if(fm2.FeeValue.value<0){
			alert("初始管理费(元)/比例必须为大于0的数字!");	
			return false;
		}
		
	} else if (fm2.FeeType.value=="1") {
		if (fm2.MonthFeeType.value==null || fm2.MonthFeeType.value =="") {
			alert("月度管理费类型不能为空！");
			return false;
		}
		if (fm2.MonthValue.value==null || fm2.MonthValue.value =="") {
			alert("月度管理费(元)/比例不能为空！");
			return false;
		}
		if(!isNumeric(fm2.MonthValue.value)){
			alert("月度管理费(元)/比例不是有效数字!");
			return false;
		}
		if((fm2.MonthFeeType.value=='101')&&(fm2.MonthValue.value<0||fm2.MonthValue.value>1)){
			alert("月度管理费(元)/比例必须为大于0小于1的数字!");
			return false;
		}else if(fm2.MonthValue.value<0){
			alert("月度管理费(元)/比例必须为大于0的数字!");	
			return false;
		}
		
	} else if (fm2.FeeType.value=="2") {

		var tYearStartValue = fm2.YearStartValue.value;
		var tYearEndValue = fm2.YearEndValue.value;
		var tYearValue = fm2.YearValue.value;
		if (fm2.YearFeeType.value==null || fm2.YearFeeType.value =="") {
			alert("年度管理费类型不能为空！");
			return false;
		}
		if (tYearStartValue==null || tYearStartValue =="") {
			alert("年度起始值不能为空！");
			return false;
		}
		if (tYearEndValue==null || tYearEndValue =="") {
			alert("年度终止值不能为空！");
			return false;
		}
		if (!isInteger(tYearStartValue)) {
			alert("年度起始值不是有效的整数！");
			return false;
		}
		if (!isInteger(tYearEndValue)) {
			alert("年度终止值不是有效的整数！");
			return false;
		}
		if (parseInt(tYearStartValue) >= parseInt(tYearEndValue)) {
			alert("年度起始值应小于年度终止值！");
			return false;
		}
		if (tYearValue==null || tYearValue =="") {
			alert("年度管理费(元)/比例不能为空！");
			return false;
		}
		if (!isNumeric(tYearValue)) {
			alert("年度管理费(元)/比例不是有效的数字！");
			return false;
		}
		if((fm2.YearFeeType.value=='201')&&(tYearValue<0||tYearValue>1)){
			alert("年度管理费(元)/比例必须为大于0小于1的数字!");
			return false;
		}else if(tYearValue<0){
			alert("年度管理费(元)/比例必须为大于0的数字!");	
			return false;
		}
	}
	return true;
}

/**
 * 查询保全算法
 */
function selectAccType(Filed,FildName,RiskCodeFiled) {
	
	if (RiskCodeFiled.value==null || RiskCodeFiled.value=="") {
		alert("请先选择保险种 !");
		return false;
	}
	var tRiskCode = RiskCodeFiled.value;
	var conditionAccType = "1 and a.riskcode= #"+tRiskCode+"#";
	showCodeList('risktoacc',[Filed,FildName],[0,1],null,conditionAccType,'1','1',null);
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	var tFeeType = fm2.FeeType.value;
	if (value1=='deducttype') {
		
		var tSql = "deducttype"+tFeeType;

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}
/**
* 清空录入值
*/
function clearContent(){
	fm2.DeductType.value="";
	fm2.DeductTypeName.value="";
	fm2.FeeValue.value="";
	fm2.MonthFeeType.value="";
	fm2.MonthFeeTypeName.value="";
	fm2.MonthValue.value="";
	fm2.YearFeeType.value="";
	fm2.YearFeeTypeName.value="";
	fm2.YearStartValue.value="";
	fm2.YearEndValue.value="";
	fm2.YearValue.value="";
}

