var type = /^[0-9]*$/;
var type1 = /^.[A-Za-z0-9]{0,100}$/;
var re = new RegExp(type);
var re1 = new RegExp(type1);
var turnPageN = new turnPageClass();
var turnPage = new turnPageClass();
var arrDataSet;
var sqlresourcename = "productdef.RiskSaleNameInputSql";
var showInfo;	//function returnParent(){		top.opener.focus();		top.close();}



function submitFrom() {
	
	if (!beforeSubmit()) {
		return false;
	}
	if (!giftExist()) {
		return false;
	}

	var i = 0;
	var showStr = ""+"正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ showStr;
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('Transact').value = "INSERT";
	fm.action = "./RiskSaleNameSave.jsp?flag="+flag;
	fm.submit(); // 提交

}

function afterSubmit(FlagStr, content) {
	showInfo.close();
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ content;
	showModalDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	initForm();

}



//查詢
function easyQueryClick() {
	// 初始化表格
	initMullineRiskSaleNameGrid();

	// 查询出来的险种名称
	var mySql = new SqlClass();
	mySql.setResourceName("productdef.RiskSaleNameInputSql");
	if(flag=='1'){
		mySql.setSqlId("RiskSaleNameInputSql1");
	}else if(flag=='0'){
		mySql.setSqlId("RiskSaleNameInputSql3");
	}
	mySql.addSubPara(fm.RiskCode.value);
	mySql.addSubPara(fm.SaleChnl.value);
	mySql.addSubPara(fm.ManageCom.value);
	mySql.addSubPara(fm.RiskNameCn.value);
	mySql.addSubPara(fm.RiskNameEn.value);
	mySql.addSubPara(fm.RiskNameShort.value);
	mySql.addSubPara(fm.RiskNameTr.value);
	turnPageN.queryModal(mySql.getString(), MullineRiskSaleNameGrid);
	if (MullineRiskSaleNameGrid.mulLineCount <= 0) {
		myAlert(""+"没有符合条件的数据！"+"");
		return false;
	}
}
//行选中事件
function ShowGift() {
	
	  var tSel = MullineRiskSaleNameGrid.getSelNo(); // 返回被选中行的行号
	  var tGiftCode = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 1);
	  
		try {
			
			fm.all('RiskCode').value = tGiftCode;
			showOneCodeName('riskcode', 'RiskCode', 'RiskCodeName');
		} catch (ex) {
		}
		try {
			fm.all('SaleChnl').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 2);
			showOneCodeName('salechnl', 'SaleChnl', 'SaleChnlName');
		} catch (ex) {
		}
		try {
			fm.all('ManageCom').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 3);
			showOneCodeName('manageCom', 'ManageCom', 'ManageComName');
		} catch (ex) {
		}
		try {
			fm.all('RiskNameCn').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 4);
		
		} catch (ex) {
		}
		try {
			fm.all('RiskNameEn').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 5);
			
		} catch (ex) {
		}
		try {
			fm.all('RiskNameShort').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 6);
			
		} catch (ex) {
		}
		try {
			fm.all('RiskNameTr').value = MullineRiskSaleNameGrid.getRowColData(tSel - 1, 7);
			
		} catch (ex) {
		}
		if(fm.all('RiskCode').value==''||fm.all('RiskCode').value==null){
			myAlert(""+"请先查询"+"");
		}
		
//		showCodeName();
		
		
	
}



//代码汉化
function showCodeName() {
	showOneCodeName('manageCom', 'ManageCom', 'ManageComName');
	showOneCodeName('salechnl', 'SaleChanel', 'SaleChanelName');
	}

//删除
function deleteClick() {

	var tSel = MullineRiskSaleNameGrid.getSelNo();//内部循环判断所有行,返回被选中的行的行号。 
                                     //注意：行号是从1开始,和数组是不一样的。如果没有选中行，返回值是0。
	if (tSel == null || tSel == 0) {
		myAlert(""+"请先查询，然后选择一条记录"+"");
		return false;
	}

	var showStr = ""+"正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ showStr;
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('Transact').value = "DELETE";
	fm.action = "./RiskSaleNameSave.jsp?flag="+flag;
	fm.submit(); // 提交
}

function updateClick() {

	var tSel = MullineRiskSaleNameGrid.getSelNo();
	if (tSel == null || tSel == 0) {
		myAlert(""+"请先查询，然后选择一条记录"+"");
		return false;
	}

	if (!beforeSubmit()) {
		return false;
	}
	
	if (tSel == null || tSel == 0) {
		myAlert(""+"请先查询，然后选择一条记录"+"");
		return false;
	}

	var showStr = ""+"正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ showStr;
	showInfo = window.showModelessDialog(urlStr, window,
			"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

	fm.all('Transact').value = "UPDATE";
	fm.action = "./RiskSaleNameSave.jsp?flag="+flag;
	fm.submit(); // 提交

}

function beforeSubmit() {

	if (fm.all('RiskCode').value == '' || fm.all('RiskCode').value == null
			|| fm.all('RiskCode').value.match(re1) == null) {
		myAlert(""+""+"");
		return false;
	}
	
	if (fm.SaleChnl.value == "" || fm.SaleChnlName.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.ManageCom.value == "" || fm.ManageComName.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.RiskNameCn.value == "" || fm.RiskNameCn.value == null) {
		myAlert("险种名称中文不能为空，请输入");
		return false;
	}

	if (fm.RiskNameCn.value.getBytes() > 120) {
		myAlert(""+""+"");
		return false;
	}

	if (fm.RiskNameEn.value.getBytes() > 120) {
		myAlert(""+""+"");
		return false;
	}
	
	if (fm.RiskNameShort.value.getBytes() > 120) {
		myAlert(""+""+"");
		return false;
	}
	
	if (fm.RiskNameTr.value.getBytes() > 120) {
		myAlert(""+""+"");
		return false;
	}

	return true;
}
function giftExist() {

	var mySql10 = new SqlClass();
	mySql10.setResourceName("productdef.RiskSaleNameInputSql");
	mySql10.setSqlId("RiskSaleNameInputSql1");
	mySql10.addSubPara(fm.RiskCode.value);
	var strSQL10 = mySql10.getString();
	var arrResult10 = easyExecSql(strSQL10);

	if (arrResult10 > 0) {
		myAlert(""+""+"");
		return false;
	}
	return true;
}

String.prototype.getBytes = function() {    
    var cArr = this.match(/[^\x00-\xff]/ig);    
    return this.length + (cArr == null ? 0 : 2*cArr.length);    
}
