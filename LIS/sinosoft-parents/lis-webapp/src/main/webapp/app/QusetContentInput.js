 //程序名称：SelAssignDuty.js
//程序功能：
//创建日期：2008-09-26
//创建人  :  liuqh
//更新记录：  更新人    更新日期     更新原因/内容

//var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var k = 0;
var turnPage = new turnPageClass();  
var turnPage1 = new turnPageClass();  
var strOperate ="like"; 
/*********************************************************************
 *  提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function afterSubmit(FlagStr,content){
	showInfo.close();  
	fm.QuestCode.disabled = false;
	fm.BackObj.disabled = false;
	initForm();
	//fm.QuestCode.value="";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
}

//tongmeng 2007-11-13 modify
//按照MS的逻辑修改
function afterCodeSelect( cCodeName, Field ){
	//alert(cCodeName);
	if(cCodeName=="backobj"){
		
	}
}

//查询
function QueryData(){
	window.open("./QuestContentQueryMain.jsp");
//	var BackObj = fm.BackObj.value;
//	var QuestCode = fm.QuestCode.value;
//	fm.tCode.value = trim(BackObj+QuestCode);
//	var tQuerySql="select a.code,a.cont,substr(a.code,0,1),a.recordquest,a.operator,a.modifydate "
//				  +"from ldcodemod a where 1=1 and codetype = 'Question' "
//	              +getWherePart('code','tCode',strOperate)+" "
//	              +getWherePart('RecordQuest','RecordQuest',strOperate);
//	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
//	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
//	}else{
//		alert("未查到该问题件编码所对应的问题件");
//		return false;
//	}
//	turnPage.queryModal(tQuerySql, QuestGrid);
}

//新增
function SaveQuest(){
	if(fm.BackObj.value==null||fm.BackObj.value==""){
		alert("请选择发送对象！");
		return false;
	}
	
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
//	var tQuerySql = "select * from ldcodemod where 1=1 and codetype = 'Question' and code='"+BackObj+QuestCode+"'";
	var tQuerySql="";
	var sqlid1="QuestContentInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.QuestContentInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(BackObj+QuestCode);//指定传入的参数
	tQuerySql = mySql1.getString();
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr!=null){
		alert("问题件编码已存在，不允许新增！");
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "INSERT";
	document.all('fmAction').value = mAction;//alert(73);
	alert(document.all('fmAction').value);
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

//删除
function deleteQuest(){
	alert("禁止删除！");
	return false;
	if(fm.QuestCode.value==null||fm.QuestCode.value==""){
		alert("请先查询出需要删除的问题件！");
		return false;
	}
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
//	var tQuerySql = "select code,cont from ldcodemod where 1=1 and codetype = 'Question' and code='"+BackObj+QuestCode+"'";
	var tQuerySql="";
	var sqlid2="QuestContentInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.QuestContentInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(BackObj+QuestCode);//指定传入的参数
	tQuerySql = mySql2.getString();
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr==null){
		alert("问题件编码有误，未查询到该编码下的问题件！");
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "DELETE";
	document.all('mAction').value = mAction;
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

//修改
function updateQuest(){
	fm.QuestCode.disabled = false;
	fm.BackObj.disabled = false;
	if(fm.QuestCode.value==null||fm.QuestCode.value==""){
		alert("请先查询出需要修改的问题件！");
		return false;
	}
	if(fm.BackObj.value==null||fm.BackObj.value==""){
		alert("请选择发送对象！");
		return false;
	}
	
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
	var tQuerySql = "select * from ldcodemod where 1=1 and codetype = 'Question' and code='"+QuestCode+"'";
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr==null){
		alert("问题件编码有误，未查询到该编码下的问题件！");
		fm.QuestCode.disabled = true;
		return false;
	}
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "UPDATE";
	document.all('fmAction').value = mAction;//alert(111);
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

function displayQuest(){
	//alert("ok");
	var tBackObj="";//发送对像
	var tQuestCode="";//问题件编码
	var checkFlag = QuestGrid.getSelNo() - 1;
	
	var tCode=QuestGrid.getRowColData(checkFlag, 1);
	//alert("1tCode:"+tCode.substring(1,3));
	//alert("2tCode:"+tCode.substring(2,4));
	fm.RecordQuest.value=QuestGrid.getRowColData(checkFlag, 4);
	fm.BackObj.value=QuestGrid.getRowColData(checkFlag, 3);
	fm.QuestCode.value=tCode.substring(1,4)
	fm.Content.value=QuestGrid.getRowColData(checkFlag, 2);
}

//接受返回来的数据并显示
function afterQuery(RecordQuest,BackObj,QuestCode,Content){
	
}