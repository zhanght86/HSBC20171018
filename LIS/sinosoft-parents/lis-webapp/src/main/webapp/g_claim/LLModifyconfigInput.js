/***************************************************************
 * <p>ProName：LLModifyconfigInput.js</p>
 * <p>Title：保项修改规则配置界面</p>
 * <p>Description：保项修改规则配置确认</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 王志豪
 * @version  : 8.0
 * @date     : 2014-06-01
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo1 = new SqlClass();
var tSQLInfo2 = new SqlClass();
var tSQLInfo3 = new SqlClass();

/**
 * 提交，保存按钮对应操作
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
	fm.submit(); //提交
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} 
	else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	/*
	queryClick();*/
	if (mOperate=="DELETE1") {
				queryClick1();
	}else if(mOperate=="DELETE2"){
				queryClick2();
	}else if(mOperate=="DELETE3"){
				queryClick3();
	}else if(mOperate=="INSERT1"){
				queryClick1();
	}else if(mOperate=="INSERT2"){
				queryClick2();
	}else if(mOperate=="INSERT3"){
				queryClick3();
	}
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}


 /*重置*/
 function reset(){
 
 	fm.RuleType.value = '';
 	fm.RuleTypeName.value = '';
 	fm.PolType.value = '';
 	fm.PolTypeName.value = '';
 	fm.TiaoZhengType.value = '';
 	fm.TiaoZhengName.value = '';
 
 }
 /**
 * 保项修改查询
 */
function queryClick() {

	if (!beforeSubmit()) {
		return false;
	}
	
	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo1.setSqlId("LLModifyconfigInputSql1");
	tSQLInfo1.addSubPara(fm.ReasonNo.value);
	turnPage1.queryModal(tSQLInfo1.getString(), RuleTypeGrid, 2);
	/*if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
	*/
	tSQLInfo2 = new SqlClass();
	tSQLInfo2.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo2.setSqlId("LLModifyconfigInputSql2");
	tSQLInfo2.addSubPara(fm.ReasonNo.value);
	turnPage2.queryModal(tSQLInfo2.getString(), PolTypeGrid, 2);
	/*if (!turnPage2.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
	}
	*/
	tSQLInfo3 = new SqlClass();
	tSQLInfo3.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo3.setSqlId("LLModifyconfigInputSql3");
	tSQLInfo3.addSubPara(fm.ReasonNo.value);
	turnPage3.queryModal(tSQLInfo3.getString(), TiaoZhengGrid, 2);
	/*if (!turnPage3.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
	*/
	if (!turnPage1.strQueryResult) {
		alert("未查询到规则类型列表的相关数据！");
	}else if (!turnPage2.strQueryResult) {
		alert("未查询到险种列表的相关数据！");
	}else if (!turnPage3.strQueryResult) {
		alert("未查询到调整方向列表的相关数据！");
		return false;
	}
	reset();
}
/*
新增规则类型
*/
function addClick(tFlag){
	if (!beforeSubmit()) {
		return false;
	}

	if(tFlag==1){
		if(trim(fm.RuleType.value)==""){
			alert("规则类型不能为空");
			return false;
		}
		mOperate = "INSERT1";
	}
	else if(tFlag==2){
		if(trim(fm.PolType.value)==""){
			alert("险种编码不能为空");
			return false;
		}
		mOperate = "INSERT2";
	}
	else if(tFlag==3){
		if(trim(fm.TiaoZhengType.value)==""){
			alert("调整方向不能为空");
			return false;
		}
		mOperate = "INSERT3";
	}
	submitForm();
} 


function deleteClick(tFlag){
	if (!beforeSubmit()) {
		return false;
	}
 	
   if(tFlag==1){
   		var tSelNo = RuleTypeGrid.getChkCount();
		if (tSelNo<1) {
			alert("请选中一条规则类型数据！");
			return false;
		}
   		mOperate = "DELETE1";
   }
   else if(tFlag==2){
   		var tSelNo = PolTypeGrid.getChkCount();
		if (tSelNo<1) {
			alert("请选中一条险种数据！");
			return false;
		}
   		mOperate = "DELETE2";
   }
   else{
   		var tSelNo = TiaoZhengGrid.getChkCount();
		if (tSelNo<1) {
			alert("请选中一条调整方向数据！");
			return false;
		}
   		mOperate = "DELETE3";
   }
   if(!confirm("确认要删除？")){
      return false;
   }
   submitForm();
}

function queryClick1(){

	tSQLInfo1 = new SqlClass();
	tSQLInfo1.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo1.setSqlId("LLModifyconfigInputSql1");
	tSQLInfo1.addSubPara(fm.ReasonNo.value);
	turnPage1.queryModal(tSQLInfo1.getString(), RuleTypeGrid, 2);
}

function queryClick2(){

	tSQLInfo2 = new SqlClass();
	tSQLInfo2.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo2.setSqlId("LLModifyconfigInputSql2");
	tSQLInfo2.addSubPara(fm.ReasonNo.value);
	turnPage2.queryModal(tSQLInfo2.getString(), PolTypeGrid, 2);
}

function queryClick3(){

	tSQLInfo3 = new SqlClass();
	tSQLInfo3.setResourceName("g_claim.LLModifyconfigInputSql");
	tSQLInfo3.setSqlId("LLModifyconfigInputSql3");
	tSQLInfo3.addSubPara(fm.ReasonNo.value);
	turnPage3.queryModal(tSQLInfo3.getString(), TiaoZhengGrid, 2);
}









