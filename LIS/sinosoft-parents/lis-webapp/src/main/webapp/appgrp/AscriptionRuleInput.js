//该文件中包含客户端需要处理的函数和事件
var mDebug="0";
var mOperate="";
var showInfo;
var arrDataSet;
var turnPage = new turnPageClass();
var QueryResult="";
var QueryCount = 0;
var mulLineCount = 0;
var QueryWhere="";
window.onfocus=myonfocus;

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow){
	if (cShow=="true"){
		cDiv.style.display="";
	}
	else{
		cDiv.style.display="none";
	}
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug){
	if(cDebug=="1"){
		parent.fraMain.rows = "0,0,0,0,*";
	}
	else {
		parent.fraMain.rows = "0,0,0,82,*";
	}
	parent.fraMain.rows = "0,0,0,0,*";
}

//数据提交（保存）
function submitForm(){
	//alert("submitForm start");
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	//alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	//增加校验
	if(fm.AscriptionRuleCode.value=="")
	{
		alert("请输入归属规则名称！");
		return false;
	}
	if(fm.AscriptionRuleName.value=="")
	{
		alert("请输入说明！");
		return false;
	}
	document.all('mOperate').value = "INSERT||MAIN";
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
	fm.submit(); //提交
	//initAscriptionRuleGrid();
}

//返回上一步
function returnparent(){
	window.location.href = "GrpFeeInput.jsp?ProposalGrpContNo="+GrpContNo+"&LoadFlag="+LoadFlag;
}

//数据提交（删除）
function DelContClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
//	alert(AscriptionRuleNewGrid.mulLineCount);
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	document.all('mOperate').value = "DELETE||MAIN";
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
	fm.submit(); //提交
}

//数据校验
function beforeSubmit(){
	return true;
}

function GrpPerPolDefine(){
	
		//alert("ok");

	var tGrpContNo = fm.GrpContNo.value;
	//if(tGrpContNo.substring(0,2)=="28")
	//{
	//	alert("已经承保的单子下的归属规则只能做保全变更！");
	//	return;
	//}
	var strSQL = "select a.RiskCode,b.RiskName,a.GrpPolNo from LCGrpPol a,LMRisk b where a.GrpContNo='"+tGrpContNo+"' and a.RiskCode = b.RiskCode";
	var tImpAscriptionRuleCode  = easyQueryVer3(strSQL, 1, 0, 1);
	
    
	
	initAscriptionRuleNewGrid(tImpAscriptionRuleCode);
  queryrelate();    
	divAscriptionRule.style.display= "";
	

}


function GrpPerPolDefineOld(){
	// 初始化表格
	var str = "";
	var tGrpContNo = GrpContNo;
	var tImpAscriptionRuleOldCode = initAscriptionRuleOld(tGrpContNo);
	//alert("is me");
	initAscriptionRuleOldGrid(tImpAscriptionRuleOldCode);
	//alert("not here");

	divAscriptionRuleOld.style.display= "";
	var strSQL = "";


	strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+tGrpContNo+"'" 
	turnPage = new turnPageClass();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		turnPage.pageDisplayGrid = AscriptionRuleOldGrid;
		turnPage.strQuerySql     = strSQL;
		turnPage.pageIndex = 0;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
        return ;
}


function initAscriptionRuleNew(tGrpContNo){
	// 书写SQL语句
	
}
function initAscriptionRuleOld(tGrpContNo){
	// 书写SQL语句
	var k=0;
	var strSQL = "";

	strSQL = "select Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory where lcAscriptionrulefactory.GrpContNo='tGrpContNo'";
	var str  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert(str);
    return str;
}
//单选框点击触发事件
function ShowAscriptionRule(parm1,parm2){
		//当前行第1列的值设为：选中
		//alert("not me");
		var tGrpContNo= fm.GrpContNo.value;
		//alert(GrpContNo);
		var cAscriptionRuleCode = document.all(parm1).all('AscriptionRuleOldGrid1').value;	//计划编码
		document.all('AscriptionRuleCode').value = cAscriptionRuleCode;
		document.all('AscriptionRuleName').value = document.all(parm1).all('AscriptionRuleOldGrid2').value;
		//alert("cAscriptionRuleCode");
               var strSQL="";
        /*       
               strSQL="select AscriptionRulecode,AscriptionRulename,Calremark,params from lcAscriptionrulefactory where Ascriptionrulecode='"+cAscriptionRuleCode+"' and lcAscriptionrulefactory.GrpContNo='"+tGrpContNo+"'";
      
            //   document.write(strSQL);
               	turnPage = new turnPageClass();
	        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//alert("not me");

	//判断是否查询成功
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = AscriptionRuleGrid;
		//保存SQL语句
		turnPage.strQuerySql     = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	*/
	strSQL="select riskcode,ascripttype,factorytype,otherno,factorycode||to_char(factorysubcode),calremark,params,factoryname,trim(FactoryType)||trim(RiskCode),grppolno from lcascriptionrulefactory "
		+ "where grpcontno='"+document.all('GrpContNo').value+"' "
		//+ "and riskcode='"+cRiskCode+"' "
		+ "and ascriptionrulecode='"+cAscriptionRuleCode+"'";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (turnPage.strQueryResult) {
	         // alert("result is right");
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = AscriptionRuleNewGrid;
		//保存SQL语句
		turnPage.strQuerySql     = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		QueryCount = 1;
		tSearch = 1;
	}
}
function afterSubmit(FlagStr,content){
	showInfo.close();
	if( FlagStr == "Fail" ){
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else{
		content = "操作成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initForm();
		document.all('AscriptionRuleCode').value="";
    document.all('AscriptionRuleName').value="";
	}
}

function updateClick(){
	AscriptionRuleNewGrid.delBlankLine("AscriptionRuleGrid");
	if(AscriptionRuleNewGrid.mulLineCount==0){
		alert("没有数据信息！");
		return false;
	}

	if (!beforeSubmit()){
		return false;
	}
	document.all('mOperate').value = "UPDATE||MAIN";
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
	fm.submit(); //提交
	initAscriptionRuleGrid();
}


function queryrelate()
{
	var strSQL = "select distinct Ascriptionrulecode,Ascriptionrulename from lcAscriptionrulefactory a where a.GrpContNo='"+fm.GrpContNo.value+"'" 
	turnPage.queryModal(strSQL, AscriptionRuleOldGrid);
}


function ChangePlan(){
	initAscriptionRuleNewGrid();
}