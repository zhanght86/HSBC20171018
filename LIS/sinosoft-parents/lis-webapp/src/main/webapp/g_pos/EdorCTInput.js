/***************************************************************
 * <p>ProName：EdorCTInput.js</p>
 * <p>Title：整单退保</p>
 * <p>Description：整单退保</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

//整单退保被保险人查询
function queryClick(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCTSql");
	tSQLInfo.setSqlId("EdorCTSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.InsuredNo.value);

	turnPage1.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
	}
}

//保存信息
function saveClick(){
	
	if(!verifyInput()){
		return false;
	}
	if(fm.BackType.value=='22' && fm.BackCal.value=="" ){
		alert("条件退保，请选择退保算法！");
		return false;
	}
	
	mOperate="SAVE";
	fm.action = "./EdorCTSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorNo="+tEdorNo;
	submitFunc();
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
	fm.submit();
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
		QueryReson();
	}	
}
function afterCodeSelect(o, p) {
	
	if(o=='queryexp') {
	
		if(p.value=='21') {
			divCalName.style.display = "none";
			divCalCode.style.display = "none";
		} else if(p.value=='22'){
			
			divCalName.style.display = "";
			divCalCode.style.display = "";
		}	
	}
}

/** 
* 查询退保原因
**/

function QueryReson(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCTSql");
	tSQLInfo.setSqlId("EdorCTSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tGrpContNo);

	var arrResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
	if (arrResult==null) {
	
	}else{
		fm.ReasonDesc.value = arrResult[0][0];
		fm.BackType.value = arrResult[0][1];
		fm.BackTypeName.value = arrResult[0][2];
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCTSql");
	tSQLInfo.setSqlId("EdorCTSql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);

	var arrResult2 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
	if (arrResult2==null) {
	
	}else{
		fm.BackCal.value = arrResult2[0][0];
		fm.BackCalName.value = arrResult2[0][1];
	}
}
