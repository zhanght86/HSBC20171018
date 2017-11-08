.1/***************************************************************
 * <p>ProName：LCAgentToUserInput.js</p>
 * <p>Title：客户经理关联</p>
 * <p>Description：客户经理关联</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-07-29
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
	tSQLInfo.setSqlId("LCAgentToUserSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.AgentName.value);
	tSQLInfo.addSubPara(tManageCom);

	turnPage1.queryModal(tSQLInfo.getString(), AgentInfoGrid,1,1);
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的信息!");
		return false;
	}
}
/**
 * 关联
 */
function relaClick(){
	var tSelNo = UserInfoGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("请选择一条用户进行关联!");
		return false;
	}
	var nSelNo = AgentInfoGrid.getSelNo()-1;
	var tUserCode = UserInfoGrid.getRowColData(tSelNo,1);
	var tAgentCode = AgentInfoGrid.getRowColData(nSelNo,2);

	mOperate="INSERT";
	fm.action = "./LCAgentToUserSave.jsp?Operate="+mOperate+"&AgentCode="+tAgentCode+"&UserCode="+tUserCode;
	submitFunc();
	fm.submit();
}

/**
 * 撤销
 */
function cancClick(){
	var tSelNo = AgentInfoGrid.getSelNo()-1;
	var tAgentCode = AgentInfoGrid.getRowColData(tSelNo,2);
	var tUserCode = AgentInfoGrid.getRowColData(tSelNo,3);
	if(tUserCode==null||tUserCode==''){
		alert("未关联用户!");
		return false;
	}
	mOperate="DELETE";
	fm.action = "./LCAgentToUserSave.jsp?Operate="+mOperate+"&AgentCode="+tAgentCode+"&UserCode="+tUserCode;
	submitFunc();
	fm.submit();
}
/**
 * 提交
 */
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
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" +
		
		 content;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	} else {
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
	queryClick();
	hidden.style.display='none';
}

function showHidden(){
	hidden.style.display='';
	var tSelNo = AgentInfoGrid.getSelNo()-1;
	var tAgentCode = AgentInfoGrid.getRowColData(tSelNo,2);
	var tUserCode = AgentInfoGrid.getRowColData(tSelNo,3);
	var tAgentName = AgentInfoGrid.getRowColData(tSelNo,4);
	var tManageCom = AgentInfoGrid.getRowColData(tSelNo,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
	tSQLInfo.setSqlId("LCAgentToUserSql2");
	tSQLInfo.addSubPara(tAgentCode);
	
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tUserCode!=null&&tUserCode!=''){
		hidden.style.display='none';
		fm.rela.style.display='none';
	}else if (tPropEntry==null) {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
		tSQLInfo.setSqlId("LCAgentToUserSql3");
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tAgentName);
		turnPage2.queryModal(tSQLInfo.getString(), UserInfoGrid,1,1);
		fm.rela.style.display='';
	} 
}
