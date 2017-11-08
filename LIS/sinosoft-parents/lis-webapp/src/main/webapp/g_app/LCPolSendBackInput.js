/***************************************************************
 * <p>ProName：LCPolSendBackInput.js</p>
 * <p>Title：回执回销</p>
 * <p>Description：回执回销</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tSQLInfo = new SqlClass();
var mOperate = '';


//查询保单
function queryClick(){
	if(!beforeCheck()){
		return false;
	}
	var mManageCom=fm.ManageCom.value;
	var mGrpPropNo=fm.GrpPropNo.value;
	var mGrpContNo=fm.GrpContNo.value;
	var mGrpName=fm.GrpName.value;
	var mExpressStartDate=fm.ExpressStartDate.value;
	var mExpressEndDate=fm.ExpressEndDate.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPolSendBackSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LCPolSendBackSql1");
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(mExpressStartDate);
		tSQLInfo.addSubPara(mExpressEndDate);
   }else if(_DBT==_DBM)
   {
	    tSQLInfo.setSqlId("LCPolSendBackSql2");
	    tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(mExpressStartDate);
		tSQLInfo.addSubPara(mExpressEndDate);
   }
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
	if(!turnPage1.strQueryResult){
		alert("未查到符合条件的结果!");
		initContInfoGrid();
	}
}

/**
 * 保存录入结论信息
 */
function saveClick(){
	if(!beforeSub()){
		return false;
	}
	fm.action = "./LCPolSendBackSave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");	
}


function submitFunc()
{
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
		queryClick();
	}	
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //提交
}


function beforeSub(){
	var tSelNo = ContInfoGrid.getChkCount();
	if (tSelNo<1) {
		alert("请选中一条投保信息");
		return false;
	}
	for (var i=0; i<ContInfoGrid.mulLineCount; i++) {
		if (ContInfoGrid.getChkNo(i)) {
			var tDate = ContInfoGrid.getRowColData(i, 10);//回执日期
			if(!isDateFormat(tDate)) {
				alert("保单第"+ (i+1) +"行客户签收日期录入格式错误,参考[2000-08-08]！");
				return false;
			}
			if(ContInfoGrid.getRowColData(i, 9)>tDate){
				alert("客户签收日期应晚于客户交接日期!");
				return false;
			}
			if(tDate>tCurrentDate){
				alert("客户签收日期不能晚于当前日期!");
				return false;
			}
		}
	}
	return true;
}
function beforeCheck(){
	var tExpressStartDate=fm.ExpressStartDate.value;
	var tExpressEndDate=fm.ExpressEndDate.value;
	if(((tExpressStartDate!=null&&tExpressStartDate!='')&&(tExpressEndDate==null||tExpressEndDate==''))||((tExpressStartDate==null||tExpressStartDate=='')&&(tExpressEndDate!=null&&tExpressEndDate!=''))){
		alert("递送登记起期和递送登记止期必须同时为空或者同时录入!");
		return false;
	}
	if(tExpressStartDate>tExpressEndDate){
		alert("递送登记起期应早于递送登记止期!");
		return false;
	}
	return true;
}
