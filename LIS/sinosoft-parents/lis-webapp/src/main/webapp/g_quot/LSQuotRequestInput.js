/***************************************************************
 * <p>ProName：LSQuotRequestInput.js</p>
 * <p>Title：业务需求</p>
 * <p>Description：业务需求</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotRequestSql");
	
	if (tActivityID == "0800100002") {//中支
		tSQLInfo.setSqlId("LSQuotRequestSql1");
	} else {
		tSQLInfo.setSqlId("LSQuotRequestSql2");
	}
	
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), RequestGrid, 0, 1);
}

/**
 * 新增
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 修改
 */
function modifyClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	if(!confirm('确定要删除选中信息吗?')){
		return false;
	}
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 保存
 */
function saveClick() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.SerialNo.value = RequestGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "SAVE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * 展示业务需求明细
 */
function showDetail() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的信息！");
		return false;
	}
	
	fm.RequestType.value = RequestGrid.getRowColData(tSelNo-1, 2);
	fm.RequestTypeName.value = RequestGrid.getRowColData(tSelNo-1, 3);
	fm.OtherTypeDesc.value = RequestGrid.getRowColData(tSelNo-1, 4);
	fm.RiskCode.value = RequestGrid.getRowColData(tSelNo-1, 5);
	fm.RiskName.value = RequestGrid.getRowColData(tSelNo-1, 6);
	fm.RequestDesc.value = RequestGrid.getRowColData(tSelNo-1, 7);
	fm.SubUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 8);
	fm.BranchUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 9);
	
	if (fm.RequestType.value == "00" || fm.RequestType.value == "01" ) {
		
		divOtherTypeDescTitle.style.display = "none";
		divOtherTypeDescInput.style.display = "none";
		divRiskTitle.style.display = "";
		divRiskInput.style.display = "";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	} else if (fm.RequestType.value == "09") {
		
		divOtherTypeDescTitle.style.display = "";
		divOtherTypeDescInput.style.display = "";
		divRiskTitle.style.display = "none";
		divRiskInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	}
	
	var tSysPlanCode = RequestGrid.getRowColData(tSelNo-1, 10);
	var tPlanCode = RequestGrid.getRowColData(tSelNo-1, 11);
	
	if (tActivityID == "0800100001") {
		if ((tSysPlanCode==null || tSysPlanCode == "" )&&(tPlanCode==null || tPlanCode == "")) {
			
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
		} else {
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
		}
	}
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotRequestSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
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
	}
}

/**
 * 查询准客户详细信息
 */
function queryDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql2");
	tSQLInfo.addSubPara(tPreCustomerNo);
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	if (mOperate=="INSERT" || mOperate=="UPDATE") {
		
		if (fm.RequestType.value=="00" || fm.RequestType.value=="01") {
			
			if (fm.RiskCode.value=="") {
				
				alert("险种不能为空！");
				return false;
			}
		}
		
		if (fm.RequestType.value=="09") {
			
			if (fm.OtherTypeDesc.value=="") {
				
				alert("其他分类描述不能为空！");
				return false;
			}
		}
		
		if (fm.RequestDesc.value=="") {
			
			alert("业务需求内容不能为空！");
			return false;
		}
		
		if (fm.RequestDesc.value.length>1500) {
			
			alert("业务需求内容过长！");
			return false;
		}
	}
	
	if (mOperate=="SAVE") {
		
		if (tActivityID == "0800100002") {
			
			if (fm.SubUWOpinion.value=="") {
				
				alert("中支公司核保意见不能为空！");
				return false;
			}
			
			if (fm.SubUWOpinion.value.length>1500) {
				
				alert("中支公司核保意见过长！");
				return false;
			}
		} else if (tActivityID == "0800100003") {
			
			if (fm.BranchUWOpinion.value=="") {
				
				alert("分公司核保意见不能为空！");
				return false;
			}
			
			if (fm.BranchUWOpinion.value.length>1500) {
				
				alert("分公司核保意见过长！");
				return false;
			}
		}
	}
	
	return true;
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {

	if (tSelectValue=='requesttype') {
		
		if (tObj.value == "00" || tObj.value == "01" ) {
			
			divOtherTypeDescTitle.style.display = "none";
			divOtherTypeDescInput.style.display = "none";
			fm.OtherTypeDesc.value = "";
			divRiskTitle.style.display = "";
			divRiskInput.style.display = "";
			fm.RiskCode.value = "";
			fm.RiskName.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
			divTD3.style.display = "none";
			divTD4.style.display = "none";
		} else if (tObj.value == "09") {
			
			divOtherTypeDescTitle.style.display = "";
			divOtherTypeDescInput.style.display = "";
			fm.OtherTypeDesc.value = "";
			divRiskTitle.style.display = "none";
			divRiskInput.style.display = "none";
			fm.RiskCode.value = "";
			fm.RiskName.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
			divTD3.style.display = "none";
			divTD4.style.display = "none";
		}
	}
}
