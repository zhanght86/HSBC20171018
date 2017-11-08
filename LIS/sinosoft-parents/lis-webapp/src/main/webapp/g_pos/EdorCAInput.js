/***************************************************************
 * <p>ProName:EdorCAInput.js</p>
 * <p>Title:  账户金额转移</p>
 * <p>Description:账户金额转移</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-25
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

//原被保险人信息查询
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
	}
}

//修改过的被保险人信息查询
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
	
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("未查询到符合条件的查询结果!");
			return false;
		}
	}
}

//团体专项医疗账户信息查询
function queryGroupAcc(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorCASql");
	tSQLInfo.setSqlId("EdorCASql3");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage3.queryModal(tSQLInfo.getString(), GroupAccGrid, 1, 1);
}

//账户金额转移
function moveClick(){
	
	var rowNum = OldInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (OldInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("请至少选择一条记录!");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	var tInAmountFlag = false;
	var tOutAmountFlag = false;
	var tSumInAmount = 0;
	for (var i=0;i < OldInsuredInfoGrid.mulLineCount;i++) {
		
		if (OldInsuredInfoGrid.getChkNo(i)) {
			
			var tInsuredType = OldInsuredInfoGrid.getRowColData(i,1);
			var tInsuAccBala = OldInsuredInfoGrid.getRowColData(i,11);
			var tInAmount = OldInsuredInfoGrid.getRowColData(i,12);
			var tOutAmount = OldInsuredInfoGrid.getRowColData(i,13);
			
			if ((tInAmount==null || tInAmount=="") && (tOutAmount==null || tOutAmount=="")) {
				alert("第"+(i+1)+"行转入金额与转出金额不能同时为空！");
				return false;
			}
			
			if ((tInAmount!=null && tInAmount!="") && (tOutAmount!=null && tOutAmount!="")) {
				alert("第"+(i+1)+"行转入金额与转出金额不能同时录入！");
				return false;
			}
			
			if (tInAmount!=null && tInAmount!="") {
				
				tInAmountFlag = true;
				
				if (!isNumeric(tInAmount)) {
					alert("第"+(i+1)+"行转入金额需要录入大于等于0的数字！");
					return false;
				}
				
				tSumInAmount = tSumInAmount + parseFloat(tInAmount);
			}
			
			if (tOutAmount!=null && tOutAmount!="") {
				
				tOutAmountFlag = true;
				
				if (!isNumeric(tOutAmount)) {
					alert("第"+(i+1)+"行转出金额需要录入大于等于0的数字！");
					return false;
				}
				
				if (parseFloat(tOutAmount)>parseFloat(tInsuAccBala)) {
					alert("第"+(i+1)+"行转出金额不能大于账户本息和！");
					return false;
				}
			}
			
			if (tInAmountFlag && tOutAmountFlag) {
				alert("转入金额与转出金额不能同时录入！");
				return false;
			}
		}
	}
	
	if (GroupAccGrid.mulLineCount>0) {
		
		var tGroupName = GroupAccGrid.getRowColData(0,2);
		var tGroupAcc = GroupAccGrid.getRowColData(0,5);
		
		if (parseFloat(tSumInAmount)>parseFloat(tGroupAcc)) {
			alert("转入金额之和大于"+tGroupName+"本息和!");
			return false;
		}
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorCASave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//撤销操作
function deleteOperate(){
	
	var rowNum = UpdateInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (UpdateInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("请至少选择一条记录!");
		return false;
	}
	
	mOperate="DELETE";
	fm.action = "./EdorCASave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	}
	
	queryOldClick();
	queryUpClick(2);
}
