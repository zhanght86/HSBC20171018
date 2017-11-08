//-------------------------------------------------
//程序名称：LDUnifyCodeTypeInput.js
//程序功能：系统统一编码维护
//创建日期：2012-01-01
//创建人  ：刘锦祥
//修改人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//-------------------------------------------------
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//操作状态
var mySql = new SqlClass();
/**
 * 提交，保存按钮对应操作
 */
function submitForm() {
	var i = 0;
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
	fm.fmtransact.value=mOperate;
	fm.submit(); //提交
}

/**
 * 提交后操作,服务器数据返回后执行的操作
 */
function afterSubmit(FlagStr, content) {
	if(typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}else {
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		if(mOperate=="UPDATE&TYPE"||mOperate=="INSERT&TYPE"||mOperate=="SUBMIT&TYPE"){
			fm.State.value="";
			fm.StateName.value="";
			queryClick();
			resetClick1();
		}else if(mOperate=="DELETE&TYPE") {
			resetClick();
			resetClick1();
		}else if(mOperate=="UPDATE"||mOperate=="SUBMIT") {
			queryClick1();
		}else if(mOperate=="INSERT"){
			queryClick1();
		}else if(mOperate=="DELETE") {
			initCodeGrid();
			//resetClick1();
			//showCodeTypeInfo();
			//清空代码信息
			fm.State1.value="";
			fm.State1Name.value="";
			fm.Code.value="";
			fm.CodeName.value="";
			fm.CodeAlias.value="";
			fm.Code.readOnly = false;	
		}
	}
}

/**
 * 提交前的校验、计算
 */
function beforeCodeTypeSubmit() {
	
	//系统的校验方法
	if(!verifyInput2()) {
		return false;
	}
	//不为空的校验
	
	var tSysCode = fm.SysCode.value.trim();
	var tCodeType = fm.CodeType.value.trim();
	var tCodeTypeName = fm.CodeTypeName.value.trim();
	var tMaintainFlag = fm.MaintainFlag.value.trim();
	
	if(tSysCode==null||tSysCode=="") {
		alert("系统名称不能为空，请重新输入！");
		fm.SysCode.focus();
		return false;
	}
	
	if(tCodeType==null||tCodeType=="") {
		alert("编码类型不能为空，请重新输入！");
		fm.CodeType.value="";
		fm.CodeType.focus();
		return false;
	}
	
	if(tCodeTypeName==null||tCodeTypeName=="") {
		alert("编码类型名称不能为空，请重新输入！");
		fm.CodeTypeName.value = "";
		fm.CodeTypeName.focus();
		return false;
	}
	
	if(tMaintainFlag==null||tMaintainFlag=="") {
		alert("维护标识不能为空，请重新输入！");
		fm.MaintainFlag.focus();
		return false;
	}
	//新增时编码类型不能重复
	if(mOperate=="INSERT&TYPE") {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql1");
		mySql.addSubPara(trim(tSysCode));   
		mySql.addSubPara(trim(tCodeType));   
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (strQueryResult) {  	
			alert('该编码类型已存在,请重新输入！');
			fm.CodeType.focus();
			return false;
		}
	}
	
	return true;
}

/**
 * Click事件，当点击增加图片时触发该函数
 */
function addClick() {
	
	mOperate = "INSERT&TYPE";
	if(!beforeCodeTypeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * Click事件，当点击“修改”图片时触发该函数
 */
function updateClick() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码类型信息");
		return false;
	}
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码类型已经生效，不能执行该操作");
		return false;
		}
	mOperate = "UPDATE&TYPE";
	if(!beforeCodeTypeSubmit()) {
		return false;
	}
	submitForm();
}
/**
 * Click事件，当点击“提交”图片时触发该函数

 */
function submitClick() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码类型信息");
		return false;
	}
	
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码类型已经生效，不能执行该操作");
		return false;
	}
			mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql4");
		mySql.addSubPara(trim(fm.SysCode.value));   
		mySql.addSubPara(trim(fm.CodeType.value));   
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (!strQueryResult) {  	
			alert('该代码类型下未维护编码不能提交,请在下面的编码信息中维护！');
			return false;
		}
	
	mOperate = "SUBMIT&TYPE";
	submitForm();
}

/**
 * Click事件，当点击“查询”图片时触发该函数
 */
function queryClick() {
	 
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeTypeSql2");
	mySql.addSubPara(trim(fm.SysCode.value));
	mySql.addSubPara(trim(fm.CodeType.value));
	mySql.addSubPara(trim(fm.CodeTypeName.value));
	mySql.addSubPara(trim(fm.MaintainFlag.value));
	mySql.addSubPara(trim(fm.State.value));
	mySql.addSubPara(trim(fm.CodeTypeDescription.value));
	
	turnPage1.queryModal(mySql.getString(), CodeTypeInfoGrid);	
	fm.CodeType.readOnly = false;
	if(!turnPage1.strQueryResult){
		alert("未查询到符合条件的查询结果！");
	}
}

/**
 * Click事件，当点击“删除”图片时触发该函数
 */
function deleteClick() {
	
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码类型信息");
		return false;
	}
	
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码类型已经生效，不能执行该操作");
		return false;
	}
	if (!confirm("确认要删除编码类型信息？")) {
		return false;
	}
	mOperate = "DELETE&TYPE";
	submitForm();
}

/**
 * Click事件，当点击“重置”图片时触发该函数
 */
function resetClick() {
	initCodeTypeInfo();
	initCodeTypeGrid();
}

/**
 * 展示代码类型信息
 */
function showCodeTypeInfo() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	fm.SysCode.value= CodeTypeInfoGrid.getRowColData(tSelNo,9);
	
	fm.SysCodeName.value=CodeTypeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType.value=CodeTypeInfoGrid.getRowColData(tSelNo,2);
	fm.CodeTypeName.value=CodeTypeInfoGrid.getRowColData(tSelNo,3);
	fm.MaintainFlag.value=CodeTypeInfoGrid.getRowColData(tSelNo,10);
	fm.MaintainFlagName.value=CodeTypeInfoGrid.getRowColData(tSelNo,4);
	fm.State.value=CodeTypeInfoGrid.getRowColData(tSelNo,11);
	fm.StateName.value=CodeTypeInfoGrid.getRowColData(tSelNo,5);
	fm.CodeTypeDescription.value=CodeTypeInfoGrid.getRowColData(tSelNo,6);
	fm.SysCodeReadOnly.value = CodeTypeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCodeReadOnlyName.value = CodeTypeInfoGrid.getRowColData(tSelNo,1);
	divSysCodeReadOnly.style.display = "";
	divSysCode.style.display="none";
	fm.CodeType.readOnly = true;
	//对应操作代码查询
	divSysCodeReadOnly1.style.display = "none";
	divCodeTypeReadOnly1.style.display = "none";
	divSysCode1.style.display = "";
	divCodeType1.style.display = "";
	fm.SysCode1.value= CodeTypeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCode1Name.value= CodeTypeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType1.value= CodeTypeInfoGrid.getRowColData(tSelNo,2);
	fm.CodeType1Name.value= CodeTypeInfoGrid.getRowColData(tSelNo,3);
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";
	//查询编码
	initCodeQueryInfoByCodeType();
	fm.Code.readOnly = false;

}

/**
 * 初始化查询编码类型
 */
function initCodeTypeQueryInfo() {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql3");
		mySql.addSubPara("");
		turnPage1.queryModal(mySql.getString(), CodeTypeInfoGrid);	
	}

/**
 * -----------------------编码信息维护js------------------------------------------------------------
 *--------------------------------------------------------------------------------------------------------
 */




/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if(!verifyInput2()) {
		return false;
	}
	
	//不为空的校验
	var tSysCode = fm.SysCode1.value;
	var tCodeType = fm.CodeType1.value;
	var tCode = fm.Code.value;
	var tCodeName = fm.CodeName.value;
	
	if(tSysCode==null||tSysCode=="") {
		alert("系统名称不能为空，请重新输入！");
		fm.SysCode1.focus();
		return false;
	}
	
	if(tCodeType==null||tCodeType=="") {
		alert("编码类型不能为空，请重新输入！");
		fm.CodeType1.focus();
		return false;
	}
	
	if(tCode==null||tCode=="") {
		alert("编码不能为空，请重新输入！");
		fm.Code.focus();
		return false;
	}
	
	if(tCodeName==null||tCodeName=="") {
		alert("编码名称不能为空，请重新输入！");
		fm.CodeName.focus();
		return false;
	}
	//新增编码不能重复
	if(mOperate=="INSERT") {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeSql1");
		mySql.addSubPara(trim(tSysCode));   
		mySql.addSubPara(trim(tCodeType));   
		mySql.addSubPara(trim(tCode)); 
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (strQueryResult) {  	
			alert('该编码信息已存在,请重新输入！');
			fm.Code.focus();
			return false;
		}
	}
	
	return true;
}

/**
 * Click事件，当点击增加图片时触发该函数
 */
function addClick1() {
	
	mOperate = "INSERT";
	if(!beforeSubmit()) {
		return false;
	}
	//判断是否存在组合的编码类型
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql4");
	mySql.addSubPara(trim(fm.SysCode1.value));   
	mySql.addSubPara(trim(fm.CodeType1.value));   
	
	var arr = easyExecSql(mySql.getString());
	if (arr == null) {
		alert('不存在对应的编码类型,请到编码类型中维护该编码类型！');
		return false;
	}
	var tState = arr[0][0];
	var tMaintainflag = arr[0][1];
	if(tState=="1"&&tMaintainflag=="0") {
			alert("选择的编码类型，已生效并且不能维护，不能新增编码信息");
			return false;
		}
	submitForm();
	cleartDate();//清空数据
}

/**
 * Click事件，当点击“修改”图片时触发该函数
 */
function updateClick1() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码信息");
		return false;
	}
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码已经生效，不能进行执行该操作");
		return false;
		}
	mOperate = "UPDATE";
	if(!beforeSubmit()) {
		return false;
	}
	fm.Code.readOnly = false;
	fm.State1.value="";
	fm.State1Name.value="";
	submitForm();
	cleartDate();//清空数据
}
/**
 * Click事件，当点击“提交”图片时触发该函数

 */
function submitClick1() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码信息");
		return false;
	}
	
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码已经生效，不能执行该操作");
		return false;
	}
		//判断编码类型是否已生效
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeTypeSql1");
	mySql.addSubPara(trim(fm.SysCode1.value));   
	mySql.addSubPara(trim(fm.CodeType1.value));   
	
	var arr = easyExecSql(mySql.getString());
	var tState = arr[0][0];
	if(tState=="0") {
			alter("选择的编码类型未生效，不能单独提交编码，请提交编码类型");
			return false;
		}
	mOperate = "SUBMIT";
	submitForm();
}

/**
 * Click事件，当点击“查询”图片时触发该函数
 */
function queryClick1() {
	 
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql3");
	mySql.addSubPara(trim(fm.SysCode1.value));
	mySql.addSubPara(trim(fm.CodeType1.value));
	mySql.addSubPara(trim(fm.State1.value));
	mySql.addSubPara(trim(fm.Code.value));
	mySql.addSubPara(trim(fm.CodeName.value));
	mySql.addSubPara(trim(fm.CodeAlias.value));
	turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
	fm.Code.readOnly = false;
	if(!turnPage2.strQueryResult)
	{
		alert("未查询到符合条件的编码信息！");
	}
}

/**
 * Click事件，当点击“删除”图片时触发该函数
 */
function deleteClick1() {
	
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("请选中一条编码信息");
		return false;
	}
	
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("编码已经生效，不能执行该操作");
		return false;
	}
	if (!confirm("确认要删除编码信息？")) {
		return false;
	}
	mOperate = "DELETE";
	submitForm();
	cleartDate();//清空数据
}

/**
 * Click事件，当点击“重置”图片时触发该函数
 */
function resetClick1() {
	initCodeInfo();
	initCodeGrid();
	fm.Code.readOnly = false;
}
//清空数据
function cleartDate(){
	fm.SysCodeReadOnly1.value="";
	fm.CodeTypeReadOnly1.value="";
	fm.SysCode1.value="";
	fm.CodeType1.value="";
	fm.SysCodeReadOnlyName1.value="";
	fm.SysCodeReadOnlyName1.value="";
	fm.CodeType1.value="";
	fm.CodeType1Name.value="";
	fm.CodeTypeReadOnlyName1.value="";
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";
}
/**
 * 展示代码类型信息
 */
function showCodeInfo() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	fm.SysCode1.value= CodeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCode1Name.value= CodeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType1.value= CodeInfoGrid.getRowColData(tSelNo,10);
	fm.CodeType1Name.value= CodeInfoGrid.getRowColData(tSelNo,2);
	fm.State1.value= CodeInfoGrid.getRowColData(tSelNo,11);
	fm.State1Name.value= CodeInfoGrid.getRowColData(tSelNo,6);
	fm.Code.value= CodeInfoGrid.getRowColData(tSelNo,3);
	fm.CodeName.value= CodeInfoGrid.getRowColData(tSelNo,4);
	fm.CodeAlias.value= CodeInfoGrid.getRowColData(tSelNo,5);
	//隐藏信息
	fm.SysCodeReadOnly1.value =  CodeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCodeReadOnlyName1.value = CodeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeTypeReadOnly1.value = CodeInfoGrid.getRowColData(tSelNo,10);
	fm.CodeTypeReadOnlyName1.value = CodeInfoGrid.getRowColData(tSelNo,2);
	divSysCodeReadOnly1.style.display = "";
	divCodeTypeReadOnly1.style.display = "";
	divSysCode1.style.display = "none";
	divCodeType1.style.display = "none";
	fm.Code.readOnly = true;
}

/**
 * 初始化查询编码类型
 */
function initCodeQueryInfo() {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeSql2");
		mySql.addSubPara("");
		turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
	}
	
/**
 * 根据编码类型查询编码
 */	
function initCodeQueryInfoByCodeType() {
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql3");
	mySql.addSubPara(trim(fm.SysCode1.value));
	mySql.addSubPara(trim(fm.CodeType1.value));
	mySql.addSubPara("");
	mySql.addSubPara("");
	mySql.addSubPara("");
	mySql.addSubPara("");
	turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
}