/***************************************************************
 * <p>ProName：FinAccItemInput.js</p>
 * <p>Title：分支科目定义界面</p>
 * <p>Description：定义会计科目下的分支科目</p>
 * <p>Copyright：Copyright (c) 2013</p>
 * <p>Company：Sinosoft</p>
 * @author   : 石全彬
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态

var tSQLInfo = new SqlClass();

var conditionCode2 = "1";//查询分支编码2条件
var conditionCode3 = "1";//查询分支编码3条件

/**
 * 提交数据
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
	fm.action = "./FinAccItemSave.jsp";
	fm.submit(); //提交
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		if (mOperate!="DELETE") {
			queryClick();
			clearInfo();
		} else {
			resetClick();
		}
	}
}

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemSql");
	tSQLInfo.setSqlId("FinAccItemSql1");
	tSQLInfo.addSubPara(fm.AccItemCode1.value);
	tSQLInfo.addSubPara(fm.AccItemCode2.value);
	tSQLInfo.addSubPara(fm.AccItemCode3.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccItemGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}

/**
 * 显示分支科目信息
 */
function showFinAccItemInfo() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要操作的列表信息！");
		return false;
	}
	
	fm.AccItemCode1.disabled = true;
	fm.AccItemName1.disabled = true;
	fm.AccItemCode2.disabled = true;
	fm.AccItemName2.disabled = true;
	fm.AccItemCode3.disabled = true;
	fm.AccItemName3.disabled = true;
	
	fm.AccItemName.value = FinAccItemGrid.getRowColData(tSelNo-1, 2);
	fm.AccItemCode1.value = FinAccItemGrid.getRowColData(tSelNo-1, 3);
	fm.AccItemName1.value = FinAccItemGrid.getRowColData(tSelNo-1, 4);
	fm.AccItemCode2.value = FinAccItemGrid.getRowColData(tSelNo-1, 5);
	fm.AccItemName2.value = FinAccItemGrid.getRowColData(tSelNo-1, 6);
	fm.AccItemCode3.value = FinAccItemGrid.getRowColData(tSelNo-1, 7);
	fm.AccItemName3.value = FinAccItemGrid.getRowColData(tSelNo-1, 8);
	fm.FinCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 9);
	fm.FinName.value = FinAccItemGrid.getRowColData(tSelNo-1, 10);
	fm.Remark.value = FinAccItemGrid.getRowColData(tSelNo-1, 11);
	
	conditionCode2 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"#";
	conditionCode3 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"# and Code2=#"+fm.AccItemCode2.value+"#";
}

/**
 * 新增
 */
function insertClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.HiddenAccItemName.value = fm.AccItemName.value;
	fm.AccItemCode.value = fm.AccItemCode1.value + fm.AccItemCode2.value + fm.AccItemCode3.value;
	
	mOperate = "INSERT";
	submitForm();
}

/**
 * 修改
 */
function updateClick() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条分支科目信息！");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	fm.AccItemCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 1);
	fm.HiddenAccItemName.value = fm.AccItemName.value;
	mOperate = "UPDATE";
	submitForm();
}

/**
 * 删除
 */
function deleteClick() {
	
	var tSelNo = FinAccItemGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择一条分支科目信息！");
		return false;
	}
	
	if (!confirm("确认要删除该分支科目？")) {
		return false
	}
	
	fm.AccItemCode.value = FinAccItemGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DELETE";
	submitForm();
}

/**
 * 重置
 */
function resetClick() {
	
	clearInfo();
	initFinAccItemGrid();
}

/**
 * 清除表单数据
 */
function clearInfo() {
	
	fm.AccItemCode1.disabled = false;
	fm.AccItemName1.disabled = false;
	fm.AccItemCode2.disabled = false;
	fm.AccItemName2.disabled = false;
	fm.AccItemCode3.disabled = false;
	fm.AccItemName3.disabled = false;
	
	fm.AccItemCode.value = "";
	fm.AccItemName.value = "";
	fm.AccItemCode1.value = "";
	fm.AccItemName1.value = "";
	fm.AccItemCode2.value = "";
	fm.AccItemName2.value = "";
	fm.AccItemCode3.value = "";
	fm.AccItemName3.value = "";
	fm.FinCode.value = "";
	fm.FinName.value = "";
	fm.Remark.value = "";
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {
	
	if (Field.name=="AccItemCode1") {
		
		fm.AccItemCode2.value = "";
		fm.AccItemName2.value = "";
		fm.AccItemCode3.value = "";
		fm.AccItemName3.value = "";
		conditionCode2 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"#";
		fm.AccItemName.value = fm.AccItemName1.value;
	}
	
	if (Field.name=="AccItemCode2") {
		
		fm.AccItemCode3.value = "";
		fm.AccItemName3.value = "";
		conditionCode3 = "1 and codetype=#accitem# and Code1=#"+fm.AccItemCode1.value+"# and Code2=#"+fm.AccItemCode2.value+"#";
		
		if (Field.value!="0000") {
			fm.AccItemName.value = fm.AccItemName1.value + "-" + fm.AccItemName2.value;
		}
	}
	
	if (Field.name=="AccItemCode3") {
	
		if (Field.value!="0000") {
			fm.AccItemName.value = fm.AccItemName1.value + "-" + fm.AccItemName2.value + "-" + fm.AccItemName3.value;
		}
	}
}

/**
 * 查询分支科目编码之前的校验
 */
function beforQueryCode(obj, Field) {
	
	var tAccItemCode = Field.value;
	if (tAccItemCode==null || tAccItemCode=="") {
		
		alert("请先选择上级分支科目编码！");
		field.focus();
		return false;
	}
	
	return true;
}

/**
 * 模糊查询会计科目
 */
function fuzzyQueryFinCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
			alert("请输入会计科目名称！");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemSql");
			tSQLInfo.setSqlId("FinAccItemSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("不存在该会计科目！");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and finname like #%"+objCodeName+"%#";
					showCodeList('finaccount', [Field, FieldName], [0,1], null, queryCondition, '1', 1, '300');
				}
			}
		}
	}
}


/**
 * 导出数据
 */
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "分支科目编码^|分支科目名称^|分支科目编码1^|分支科目名称1^|分支科目编码2^|分支科目名称2^|分支科目编码3^|分支科目名称3^|会计科目编码^|会计科目名称^|备注^|录入人^|录入日期^|最后修改操作人^|最后修改日期";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemSql");
	tSQLInfo.setSqlId("FinAccItemSql1");
	tSQLInfo.addSubPara(fm.AccItemCode1.value);
	tSQLInfo.addSubPara(fm.AccItemCode2.value);
	tSQLInfo.addSubPara(fm.AccItemCode3.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
