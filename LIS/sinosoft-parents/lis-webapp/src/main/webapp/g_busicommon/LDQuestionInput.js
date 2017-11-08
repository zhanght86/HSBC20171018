/***************************************************************
 * <p>ProName：LDQuestionInput.js</p>
 * <p>Title：问题件管理</p>
 * <p>Description：问题件管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var mQuesID = "";
var tSQLInfo = new SqlClass();
var tNBPOSFlag = "";

/**
 * 查询
 */
function queryClick(tMark) {
	
	var tLimitSendNode = "";
	var tLimitReplyNode = "";
	if (tOtherNoType=="QUOT") {//modify by songsz 20140522 增加询价节点限制
		
		if (tActivityID=="0800100001") {//询价录入
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
			tLimitReplyNode = " and a.replynode='0800100001' ";
		} else if (tActivityID=="0800100002") {//中核
			
			tLimitSendNode = " and a.sendnode in('0800100002','0800100003') ";
		} 
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_busicommon.LDQuestiontSql");
	if(_DBT == _DBO ){
		tSQLInfo.setSqlId("LDQuestiontSql1");
	}else if( _DBT == _DBM){
	    tSQLInfo.setSqlId("LDQuestiontSql11");
    }
	tSQLInfo.addSubPara(tOtherNoType);
	tSQLInfo.addSubPara(tOtherNo);
	if (tSubOtherNo==null || tSubOtherNo=="" || tSubOtherNo=="null") {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(tSubOtherNo);
	}
	tSQLInfo.addSubPara(fm.SendStartDate.value);
	tSQLInfo.addSubPara(fm.SendEndDate.value);
	tSQLInfo.addSubPara(fm.QuesState.value);
	tSQLInfo.addSubPara(fm.ReplyStartDate.value);
	tSQLInfo.addSubPara(fm.ReplyEndDate.value);
	tSQLInfo.addSubPara(tLimitSendNode);
	tSQLInfo.addSubPara(tLimitReplyNode);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuestionGrid);
	if (tMark=="1") {
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

/**
 * 新增问题件
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	submitForm();
}

/**
 * 修改问题件
 */
function modifyClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "MODIFY";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 删除问题件
 */
function deleteClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 回复问题件
 */
function replyClick() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	if (fm.ReplyContent.value ==null || fm.ReplyContent.value == "") {
		alert("回复内容不能为空！");
		return false;
	}
	
	mQuesID = QuestionGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "REPLY";
	submitForm();
}

/**
 * 展示问题件信息
 */
function showDetail() {
	
	var tSelNo = QuestionGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.QuesType.value = QuestionGrid.getRowColData(tSelNo-1, 2);
	fm.QuesTypeName.value = QuestionGrid.getRowColData(tSelNo-1, 3);
	fm.SendContent.value = QuestionGrid.getRowColData(tSelNo-1, 4);
	fm.ReplyContent.value = QuestionGrid.getRowColData(tSelNo-1, 5);
	
	var tState = QuestionGrid.getRowColData(tSelNo-1, 6);
	var tSelSendNode = QuestionGrid.getRowColData(tSelNo-1, 8);
	var tSelReplyNode = QuestionGrid.getRowColData(tSelNo-1, 11);
	var tMaketake = QuestionGrid.getRowColData(tSelNo-1, 14);
	//add by dianj 增加影像单证细类
	fm.SubType.value = QuestionGrid.getRowColData(tSelNo-1, 17);
	fm.SubTypeName.value = QuestionGrid.getRowColData(tSelNo-1, 18);
	
	if (tSelSendNode==tActivityID) {
		
		if (tState=="0") {//未回复,可以修改及删除
			
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "none";
		} else {//其余情况不展示相关按钮
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	} else if (tSelReplyNode==tActivityID) {
		
		if (tState=="0" || tState=="1") {//未回复,可以修改及删除
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "";
			divReplyContent.style.display = "";
		} else {//其余情况不展示相关按钮
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	} else if("NB"==tOtherNoType || "POS"==tOtherNoType){alert("4");
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
		divReplyContent.style.display = "";
	}else {
		
		if (tState=="0" || tState=="1") {//不为已回复已提交状态,即可以持续回复
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "";
			divReplyContent.style.display = "";
		} else if (tState=="2") {
			
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ReplyButton.style.display = "none";
			divReplyContent.style.display = "";
		}
	}
	if("1"==tMaketake){
		fm.Mistake.checked = true;
		fm.Mistake.value="1";
	}else {
		fm.Mistake.checked = false;
		fm.Mistake.value="0";
	}
	///add by dianj 增加影像问题件单证类型
	if(fm.QuesType.value == "00"){
		divSubTypeName.style.display="";
		divSubTypeCode.style.display="";
	} else {
		divSubTypeName.style.display="none";
		divSubTypeCode.style.display="none";
	}
	//add by ZhangC 20141216 询价查询时，处理问题件按钮展示
	//当 tShowFlag = 1 时，只展示“对话展示”按钮
	if (tShowFlag=="1") {
		
		fm.AddButton.style.display = "none";
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
		fm.ShowButton.style.display = "";
	}
}

/**
 * 问题件对话展示
 */
function showClick() {
	
	window.open("../g_busicommon/LDQuestionShowMain.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&ActivityID="+tActivityID+"&ShowStyle="+tShowStyle,"问题件对话展示",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 提交
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./LDQuestionSave.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&ActivityID="+tActivityID+"&Operate="+mOperate+"&QuesID="+mQuesID;
	fm.submit();
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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	initForm();
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="MODIFY") {
		
		//add by dianj 增加影像问题件单证细类是必须录入的项
		if(fm.QuesType.value=="00" && fm.SubType.value ==""){
			alert("影像问题件必须选择单证类型！");
			return false;
		}
		if (tShowStyle=="2") {
			
			if (fm.QuesType.value=="") {
				
				alert("问题件类型不能为空！");
				return false;
			}
		}
		if (fm.SendContent.value=="") {
			
			alert("问题内容不能为空！");
			return false;
		}
		
		if (fm.SendContent.value.length>1500) {
			
			alert("问题内容过长！");
			return false;
		}
	}
	
	if (mOperate=="REPLY") {
		
		if (fm.ReplyContent.value=="") {
			
			alert("回复内容不能为空！");
			return false;
		}
		
		if (fm.ReplyContent.value.length>1500) {
			
			alert("回复内容过长！");
			return false;
		}
	}
	
	return true;
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

	if (value1=="questype") {
		
		var tSql = "1 and codetype=#"+value1+"# and codeexp=#"+tActivityID+"# and othersign=#"+tOtherNoType+"# and code in (select code from ldcode where codetype=#questype#  and (othersign is null or othersign in (select activityid from lbmission b where missionprop1=#"+tOtherNo+"#))) ";
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
	if (value1=="subtype") {
		
		var tSql = "1 and b.doccode = substr(#"+tOtherNo+"#,1,18) ";
		
		if (returnType=='0') {
			return showCodeList('subtype',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('subtype',value2,value3,null,tSql,'1','1',null);
		}
	}
}
/**
** 是否记入差错
**/
function checkClick(){
	if(fm.Mistake.checked){
		fm.Mistake.value="1";
	}else {
		fm.Mistake.value="0";
	}
}
//判断问题件是影像问题件增加类型
function afterCodeSelect(o, p){
	 
	if(o=='queryexp'){
		if(p.name=="QuesType" && p.value=="00" ){
			divSubTypeName.style.display="";
			divSubTypeCode.style.display="";
		} else {
			divSubTypeName.style.display="none";
			divSubTypeCode.style.display="none";
		}
	}
}
