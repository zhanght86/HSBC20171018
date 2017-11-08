/***************************************************************
 * <p>ProName：EdorAcceptDetailInput.js</p>
 * <p>Title：保全受理</p>
 * <p>Description：保全受理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 受理信息查询
 */
function queryEdorAppInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql1");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.PolicyNo.value = strQueryResult[0][0];
		fm.HidGrpContNo.value = strQueryResult[0][0];
		fm.AppntName.value = strQueryResult[0][1];
		
		if (strQueryResult[0][4]!=null && strQueryResult[0][4]!="") {
			
			fm.ConfButton.style.display = "none";
			divEdorAppDetailInfo.style.display = "";
			
			fm.AppDate.value = strQueryResult[0][2];
			fm.ReceiveDate.value = strQueryResult[0][3];
			fm.AppMode.value = strQueryResult[0][4];
			fm.AppModeName.value = strQueryResult[0][5];
		} else {
			
			fm.ConfButton.style.display = "";
			divEdorAppDetailInfo.style.display = "none";
			
			fm.AppDate.value = tCurrentDate;
			fm.ReceiveDate.value =tCurrentDate;
			fm.AppMode.value = "";
			fm.AppModeName.value = "";
		}
	}
}

/**
 * 保单信息查询
 */
function queryPolicyInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.ValDate.value = strQueryResult[0][0];
		fm.InitNumPeople.value = strQueryResult[0][1];
		fm.InitPrem.value = strQueryResult[0][2];
		fm.EndDate.value = strQueryResult[0][3];
		fm.NumPeople.value = strQueryResult[0][4];
		fm.Prem.value = strQueryResult[0][5];
		fm.ContState.value = strQueryResult[0][6];
		fm.EdorTimes.value = strQueryResult[0][7];
		fm.PayIntv.value = strQueryResult[0][8];
		fm.BanlanceFlag.value = strQueryResult[0][9];
		fm.AfterClmRule.value = strQueryResult[0][10];
		fm.InsuAccFlag.value = strQueryResult[0][11];
	}
}

/**
 * 保全项目查询
 */
function queryEdorTypeInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorTypeGrid, 1, 1);
}

/**
 * 关联保单号
 */
function confClick() {
	
	fm.Operate.value = "RELATE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * 添加保全项目
 */
function addEdorType() {
	
	fm.Operate.value = "ADDEDORTYPE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * 删除保全项目
 */
function delEdorType() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要删除的保全项目！");
		return false;
	}
	
	fm.DelEdorType.value = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	fm.Operate.value = "DELEDORTYPE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * 展示保全项目信息
 */
function showEdorType() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要删除的保全项目！");
		return false;
	}
	
	fm.EdorType.value = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	fm.EdorTypeName.value = EdorTypeGrid.getRowColData(tSelNo-1, 2);
	fm.EdorValDate.value = EdorTypeGrid.getRowColData(tSelNo-1, 4);
	
	var tEdorLevel = EdorTypeGrid.getRowColData(tSelNo-1, 5);
	var tValDateFlag = EdorTypeGrid.getRowColData(tSelNo-1, 7);
	
	fm.GetObj.value = EdorTypeGrid.getRowColData(tSelNo-1, 8);
	fm.GetObjName.value = EdorTypeGrid.getRowColData(tSelNo-1, 9);
	
	if (tEdorLevel=="1" || (tEdorLevel=="0" && tValDateFlag=="0")) {
		
		divEdorValDateTitle.style.display = "none";
		divEdorValDateInput.style.display = "none";
		fm.EdorValDate.value = "";
		
		//账户型产品减人展示支付方式
		if (fm.EdorType.value=="ZT" && fm.InsuAccFlag.value=="1") {
			
			divGetObjTitle.style.display = "";
			divGetObjInput.style.display = "";
			
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		} else {
			
			divGetObjTitle.style.display = "none";
			divGetObjInput.style.display = "none";
			fm.GetObj.value = "";
			fm.GetObjName.value = "";
			
			divTD1.style.display = "";
			divTD2.style.display = "";
		}
	} else {
		
		divEdorValDateTitle.style.display = "";
		divEdorValDateInput.style.display = "";
		
		divGetObjTitle.style.display = "none";
		divGetObjInput.style.display = "none";
		
		divTD1.style.display = "none";
		divTD2.style.display = "none";
	}
}

/**
 * 保全项目明细
 */
function detailClick() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一个保全项目！");
		return false;
	}
	
	var tGrpContNo = fm.HidGrpContNo.value;
	var tEdorType = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	var tEdorActivityID = EdorTypeGrid.getRowColData(tSelNo-1, 6);
	
	if (tActivityID!=tEdorActivityID) {
		alert("该保全项目不在保全受理进行录入！");
		return false;
	}
	
	window.open("./EdorTypeDetailMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&EdorType="+tEdorType,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * 受理完毕
 */
function acceptClick() {
	
	fm.Operate.value = "ACCEPT";
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
		
		initForm();
		
		if("ACCEPT"==fm.Operate.value){
			returnClick();
		}
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
	
	if (fm.Operate.value=="RELATE") {
		
//		if (fm.PolicyNo.value=="") {
//			alert("保单号不能为空！");
//			return false;
//		}
		
		if (fm.AppDate.value=="") {
			alert("客户申请日期不能为空！");
			return false;
		}
		
		if (fm.ReceiveDate.value=="") {
			alert("公司接收日期不能为空！");
			return false;
		}
		if (fm.AppDate.value>fm.ReceiveDate.value) {
			alert("公司接收日期不能早于客户申请日期！");
			return false;
		}
		if (fm.ReceiveDate.value>tCurrentDate) {
			alert("公司接收日期不能晚于当前日期！");
			return false;
		}
		
		if (fm.AppMode.value=="") {
			alert("申请方式不能为空！");
			return false;
		}
	} else if (fm.Operate.value=="ADDEDORTYPE") {
		
		if (fm.EdorType.value=="") {
			alert("请先选择保全项目！");
			return false;
		}
		
		if (divEdorValDateInput.style.display=="" && fm.EdorValDate.value=="") {
			alert("保全生效日期不能为空！");
			return false;
		}
	}
	
	return true;
}

/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=="edorcode") {
		
		if (fm.HidEdorLevel.value=="1" || (fm.HidEdorLevel.value=="0" && fm.HidValDateFlag.value=="0")) {
		
			divEdorValDateTitle.style.display = "none";
			divEdorValDateInput.style.display = "none";
			fm.EdorValDate.value = "";
			
			//账户型产品减人默认对公支付
			if (Field.value=="ZT" && fm.InsuAccFlag.value=="1") {
				
				divGetObjTitle.style.display = "";
				divGetObjInput.style.display = "";
				fm.GetObj.value = "00";
				fm.GetObjName.value = "对公";
				
				divTD1.style.display = "none";
				divTD2.style.display = "none";
			} else {
				
				divGetObjTitle.style.display = "none";
				divGetObjInput.style.display = "none";
				fm.GetObj.value = "";
				fm.GetObjName.value = "";
				
				divTD1.style.display = "";
				divTD2.style.display = "";
			}
		} else {
			
			divEdorValDateTitle.style.display = "";
			divEdorValDateInput.style.display = "";
			fm.EdorValDate.value = "";
			
			divGetObjTitle.style.display = "none";
			divGetObjInput.style.display = "none";
			fm.GetObj.value = "";
			fm.GetObjName.value = "";
			
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		}
	}
}