//更新记录： 
//更新人:  
//更新日期:  2008-9-17 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.CommandMainSql';
// 查询并列出所有运算符记录
function queryClick() {
	//BOM高级查询
		var sqlid1="CommandMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("CommandName").value);//指定传入的参数
		mySql1.addSubPara(document.all("CommandType").value);//指定传入的参数
		mySql1.addSubPara(document.all("CommType").value);//指定传入的参数
		mySql1.addSubPara(document.all("Valid").value);
	  var strSql=mySql1.getString();	
	  
	/*
	
	var sqlStr = "select name,display,Replace(implenmation,'''','\"') implenmation,valid,commandtype,resulttype,paratype,paranum,description,commtype from LRCOMMAND where 1=1";
	if (document.all("CommandName").value.trim()!=null&&document.all("CommandName").value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("CommandName").value + "%'";
	}	
	if (document.all("CommandType").value.length > 0) {
		sqlStr += " and commandtype='" + document.all("CommandType").value + "'";
	}	
	if (document.all("CommType").value.length > 0) {
		sqlStr += " and CommType='" + document.all("CommType").value + "'";
	}	
	
	if (document.all("Valid").value.length > 0) {
		sqlStr += " and valid='" + document.all("Valid").value + "'";
	}
	sqlStr += " order by name";*/
	//prompt("",sqlStr);
	turnPage.queryModal(strSql, QueryGrpGrid);
	
}

function hiddenButton(){
	var BranchTyp = document.all('BranchTyp').value;
	if(BranchTyp=='1'){
		document.all('ic').style.display='none';
		document.all('uc').style.display='none';
		document.all('dc').style.display='none';		
	}
}

//添加运算符
function insertClick() {
	document.all("CommandName").value = "";
	document.all("CommandType").value = "";	
	document.all("Valid").value = "";	
	document.location.href = "CommandAddInput.jsp";
}

//更新
function updateClick(){
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	var CommandName = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	}else{
		if (count == 0) {
			alert("您还没有选择需要修改的运算符");
			return;
		}
	}		
	document.location.href = "CommandUpdate.jsp?CommandName="+CommandName;
}

//提交
function submitForm() {	
	var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); // 提交
}


//删除
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("您还没有选择需要删除的运算符");
		return;
	}
	if (!confirm("您确实要删除这个运算符吗？"))
		return;
	document.all("Transact").value = "DELETE";
	submitForm();	
	queryClick();	
}

function InfoClose(){
	try{
		showInfo.close();
	}catch(ex){
		
	}
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	alert(FlagStr);
	InfoClose();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
}