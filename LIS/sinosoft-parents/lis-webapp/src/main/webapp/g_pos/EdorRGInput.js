/***************************************************************
 * <p>ProName:EdorRGInput.js</p>
 * <p>Title:  满期领取</p>
 * <p>Description:满期领取</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-27
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

//原被保险人信息查询
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果!");
		return false;
	}
}

//修改过的被保险人信息查询
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql2");
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

//单位银行账户信息查询
function queryGrpBank(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRGSql");
	tSQLInfo.setSqlId("EdorRGSql3");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.GrpBankName.value = strQueryResult[0][0];
		fm.GrpBankAccName.value = strQueryResult[0][1];
		fm.GrpBankAccNo.value = strQueryResult[0][2];
	}
}

//满期领取
function getClick(){
	
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
	
	if (fm.ExpiryGetMode.value=="") {
		alert("领取方式不能为空！");
		return false;
	}
	
	if (fm.ExpiryGetMode.value=="0") {
	
		if (fm.ExpiryPayMode.value=="") {
			alert("支付方式不能为空！");
			return false;
		}
		
		if (fm.ExpiryPayMode.value=="0") {
			
			if (fm.GrpBankName.value=="" || fm.GrpBankAccName.value=="" || fm.GrpBankAccNo.value=="") {
				alert("单位银行账户信息不完整！");
				return false;
			}
		}
	} else if (fm.ExpiryGetMode.value=="1") {
		
		if (fm.TransMode.value=="") {
			alert("转换类型不能为空！");
			return false;
		}
		
		if (fm.TransMode.value=="1") {
			
			if (fm.TransAmount.value=="") {
				alert("转换金额不能为空！");
				return false;
			}
			
			if (!isNumeric(fm.TransAmount.value)) {
				alert("转换金额需要录入大于0的数字！");
				return false;
			}
			
			if (fm.TransAmount.value<=0) {
				alert("转换金额需要录入大于0的数字！");
				return false;
			}
			
			if (fm.ExpiryPayMode.value=="") {
				alert("支付方式不能为空！");
				return false;
			}
		
			if (fm.ExpiryPayMode.value=="0") {
				
				if (fm.GrpBankName.value=="" || fm.GrpBankAccName.value=="" || fm.GrpBankAccNo.value=="") {
					alert("单位银行账户信息不完整！");
					return false;
				}
			}
		}
	}
	
	for (var i=0;i < OldInsuredInfoGrid.mulLineCount;i++) {
		
		if (OldInsuredInfoGrid.getChkNo(i)) {
			
			var tExpiryAmount = OldInsuredInfoGrid.getRowColData(i,7);
			var tBankName = OldInsuredInfoGrid.getRowColData(i,8);
			var tBankAccName = OldInsuredInfoGrid.getRowColData(i,9);
			var tBankAccNo = OldInsuredInfoGrid.getRowColData(i,10);
			
			if (fm.ExpiryGetMode.value=="1" && fm.TransMode.value=="1") {
				
				if (parseFloat(fm.TransAmount.value)>parseFloat(tExpiryAmount)) {
					alert("第"+(i+1)+"行转换金额不能大于满期金额!");
					return false;
				}
			}
			
			if (fm.ExpiryPayMode.value=="1") {
				
				if (tBankName=="" || tBankAccName=="" || tBankAccNo=="") {
					alert("第"+(i+1)+"行个人银行账户信息不完整！");
					return false;
				}
			}
		}
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorRGSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	fm.action = "./EdorRGSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	
	initInpBox();
	queryOldClick();
	queryUpClick(2);
}

/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=="expirygetmode") {
		
		if (fm.ExpiryGetMode.value=="0") {
			
			TransModeTitle.style.display = "none";
			TransModeInput.style.display = "none";
			fm.TransMode.value = "";
			fm.TransModeName.value = "";
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "";
			td4.style.display = "";
			
			divPayMode.style.display = "";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		} else if (fm.ExpiryGetMode.value=="1") {
			
			TransModeTitle.style.display = "";
			TransModeInput.style.display = "";
			fm.TransMode.value = "";
			fm.TransModeName.value = "";
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "none";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		}
	} else if (cCodeType=="expirypaymode") {
		
		if (fm.ExpiryPayMode.value=="0") {
		
			divGrpBank.style.display = "";
		} else if (fm.ExpiryPayMode.value=="1") {
			
			divGrpBank.style.display = "none";
		}
	} else if (cCodeType=="transmode") {
		
		if (fm.TransMode.value=="0") {
			
			TransAmountTitle.style.display = "none";
			TransAmountInput.style.display = "none";
			fm.TransAmount.value = "";
			
			td1.style.display = "";
			td2.style.display = "";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "none";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		} else if (fm.TransMode.value=="1") {
			
			TransAmountTitle.style.display = "";
			TransAmountInput.style.display = "";
			fm.TransAmount.value = "";
			
			td1.style.display = "none";
			td2.style.display = "none";
			td3.style.display = "none";
			td4.style.display = "none";
			
			divPayMode.style.display = "";
			fm.ExpiryPayMode.value = "";
			fm.ExpiryPayModeName.value = "";
			
			divGrpBank.style.display = "none";
		}
	}
}
