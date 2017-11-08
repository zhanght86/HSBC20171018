var type = /^[0-9]*$/;
var type1 = /^.[A-Za-z0-9]{0,100}$/;
var re = new RegExp(type);
var re1 = new RegExp(type1);
var turnPageN = new turnPageClass();
var turnPage = new turnPageClass();
var arrDataSet;
var sqlresourcename = "productdef.ConfigCvalidateInputSql";
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
	fm.action = "./PDSaleControlSave.jsp?flag="+flag;
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
	initConfigCvalidateGrid();

	// 查询出来的险种名称
	var mySql = new SqlClass();
	mySql.setResourceName("productdef.ConfigCvalidateInputSql");
	
	mySql.setSqlId("ConfigCvalidateInputSql1");
	
	mySql.addSubPara(fm.RiskCode.value);
	mySql.addSubPara(fm.SaleChnl.value);
	mySql.addSubPara(fm.ManageComGrp.value);
	mySql.addSubPara(fm.Currency.value);
	turnPageN.queryModal(mySql.getString(), ConfigCvalidateGrid);
	if (ConfigCvalidateGrid.mulLineCount <= 0) {
		myAlert(""+"没有符合条件的数据！"+"");
		return false;
	}
}
//行选中事件
function ShowSaleDate() {
	
	  var tSel = ConfigCvalidateGrid.getSelNo(); // 返回被选中行的行号
	  
		try {
			fm.all('ReRiskCode').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 1);
			showOneCodeName('riskcode', 'ReRiskCode', 'ReRiskCodeName');
		} catch (ex) {
		}
		try {
			fm.all('ReSaleChnl').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 2);
			showOneCodeName('salechnl', 'ReSaleChnl', 'ReSaleChnlName');
		} catch (ex) {
		}
		try {
			fm.all('ReManageComGrp').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 3);
			showOneCodeName('manageCom', 'ReManageComGrp', 'ReManageComGrpName');
		} catch (ex) {
		}
		try {
			fm.all('ReCurrency').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 4);
			showOneCodeName('currency', 'ReCurrency', 'ReCurrencyName');
		} catch (ex) {
		}
		try {
			fm.all('LISStartDate').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 5);
			
		} catch (ex) {
		}
		try {
			fm.all('LISEndDate').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 6);
			
		} catch (ex) {
		}
		try {
			fm.all('PPLStartDate').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 7);
			
		} catch (ex) {
		}
		try {
			fm.all('PPLEndDate').value = ConfigCvalidateGrid.getRowColData(tSel - 1, 8);
			
		} catch (ex) {
		}
		if(fm.all('ReRiskCode').value==''||fm.all('ReRiskCode').value==null){
			myAlert(""+"请先查询"+"");
		}
		
		
	
}



//代码汉化
function showCodeName() {
	
	}

//删除
function deleteClick() {

	var tSel = ConfigCvalidateGrid.getSelNo();//内部循环判断所有行,返回被选中的行的行号。 
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
	fm.action = "./PDSaleControlSave.jsp?flag="+flag;
	fm.submit(); // 提交
}

function updateClick() {

	var tSel = ConfigCvalidateGrid.getSelNo();
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
	fm.action = "./PDSaleControlSave.jsp?flag="+flag;
	fm.submit(); // 提交

}

function beforeSubmit() {

	if (fm.all('ReRiskCode').value == '' || fm.all('ReRiskCode').value == null
			|| fm.all('ReRiskCode').value.match(re1) == null) {
		myAlert(""+""+"");
		return false;
	}
	
	if (fm.ReSaleChnl.value == "" || fm.ReSaleChnl.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.ReManageComGrp.value == "" || fm.ReManageComGrp.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.ReCurrency.value == "" || fm.ReCurrency.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.PPLStartDate.value == "" || fm.PPLStartDate.value == null) {
		myAlert(""+""+"");
		return false;
	}
	if (fm.LISStartDate.value == "" || fm.LISStartDate.value == null) {
		myAlert(""+""+"");
		return false;
	}


	return true;
}
function giftExist() {

	var mySql10 = new SqlClass();
	mySql10.setResourceName("productdef.ConfigCvalidateInputSql");

	mySql10.setSqlId("ConfigCvalidateInputSql1");
	
	mySql10.addSubPara(fm.ReRiskCode.value);
	mySql10.addSubPara(fm.ReSaleChnl.value);
	mySql10.addSubPara(fm.ReManageComGrp.value);
	mySql10.addSubPara(fm.ReCurrency.value);
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
