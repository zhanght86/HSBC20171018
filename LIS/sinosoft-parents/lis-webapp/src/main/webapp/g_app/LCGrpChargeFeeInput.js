/***************************************************************
 * <p>ProName：LCGrpChargeFeeInput.js</p>
 * <p>Title：中介手续费维护</p>
 * <p>Description：中介手续费维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();


function queryAgentInfo(){
	divZJInfo.style.display="";
	var tRow = FeeRateInfoGrid.getSelNo()-1;
	var tRiskCode=FeeRateInfoGrid.getRowColData(tRow,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpChargeFeeSql");
	tSQLInfo.setSqlId("LCGrpChargeFeeSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(tRiskCode);
	turnPage2.queryModal(tSQLInfo.getString(), ZJGrid, 1, 1,30);
}
function queryPropInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpChargeFeeSql");
	tSQLInfo.setSqlId("LCGrpChargeFeeSql1");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage1.queryModal(tSQLInfo.getString(), FeeRateInfoGrid, 1, 1,30);
}
function feeRateSubmit(){
	if(!beforeSub()){
		return false;
	}
	var tRow = FeeRateInfoGrid.getSelNo()-1;
	if(tRow==-1){
		alert("请选择一行!");
		return false;
	}
	if(!ZJGrid.checkValue("ZJGrid")){
		return false;
	}
	var tRickCode=FeeRateInfoGrid.getRowColData(tRow,1);
	fm.action = "./LCGrpChargeFeeSave.jsp?Operate=INSERT&RiskCode="+tRickCode;
	submitForm(fm,"INSERT");
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

	}	
	queryPropInfo();
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //提交
}
function beforeSub(){
	var rowNum = ZJGrid.mulLineCount;
	var index=0;
	for(var i=0;i < rowNum;i++){
		if(ZJGrid.getRowColData(i,3)>1||ZJGrid.getRowColData(i,3)<0){
			alert("中介手续费比例为大于0且小于1的数字!");
			return false;
		}
		var k = new Number(ZJGrid.getRowColData(i,3));
		index=index+k;
	}
	if(index>1){
		alert("中介手续费比例之和应小于等于1的数字!");
		return false;
	}
	return true;
}
