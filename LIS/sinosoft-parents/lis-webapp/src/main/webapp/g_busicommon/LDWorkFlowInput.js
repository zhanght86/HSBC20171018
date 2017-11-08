/***************************************************************
 * <p>ProName：LDWorkFlowInput.js</p>
 * <p>Title：工作流驱动</p>
 * <p>Description：工作流驱动</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2015-11-09
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var tSQLInfo = new SqlClass();

/**
 * 工作流驱动
 */
function workFlowClick() {
	
	mOperate = "INSERT";
	
	//系统的校验方法
	if (!verifyInput()) {
		return false;
	}
	
	if (fm.SubType.value == "21001" || fm.SubType.value == "21002" || fm.SubType.value == "21003") {
		
		 if (fm.PropType.value == "") {
		 	alert("投保书类型不能为空！");
			return false;
		 }
		 
		 if (fm.PropType.value == "1" && fm.DocCode.value == "") {
		 	alert("业务号不能为空！");
			return false;
		 }
	} else if (fm.SubType.value == "22001") {
		
		if (fm.DocCode.value == "") {
			alert("业务号不能为空！");
			return false;
		}
	}
	
	submitForm();
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
	fm.action = "./LDWorkFlowSave.jsp?Operate="+mOperate;
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
}

/**
 * 根据前台页面进行下拉控制
 **/
function returnShowCodeList (value1, value2, value3) {

	if (value1=='subtypedetail') {
		
		if (isEmpty(fm.BussType)) {
			alert("请先选择业务类型！");
			return false;
		}
		
		return showCodeList(value1,value2,value3,null,fm.BussType.value,'busstype','1',180);
	}
}

/**
 * 下拉框选择后处理
 */
function afterCodeSelect(tSelectValue, tObj) {
	
	if (tSelectValue=='esbusstype') {
			
		DivDocCode.style.display="none";
		id1.style.display="none";
		id2.style.display="none";
		id3.style.display="none";
		id4.style.display="none";
		id5.style.display="none";
		id6.style.display="none";
		id7.style.display="none";
		id8.style.display="none";
		
		fm.SubType.value = "";
		fm.SubTypeName.value = "";
		fm.PropType.value = "";
		fm.PropTypeName.value = "";
		fm.DocCode.value = "";
	} else if (tSelectValue=='subtypedetail') {
		
		if (tObj.value == "21001" || tObj.value == "21002" || tObj.value == "21003") {
			
			DivDocCode.style.display="";
			id1.style.display="";
			id2.style.display="";
			id3.style.display="none";
			id4.style.display="none";
			id5.style.display="none";
			id6.style.display="none";
			id7.style.display="none";
			id8.style.display="none";
			
			fm.PropType.value = "";
			fm.PropTypeName.value = "";
			fm.DocCode.value = "";
		} else if (tObj.value == "22001") {
			
			DivDocCode.style.display="";
			id1.style.display="none";
			id2.style.display="none";
			id3.style.display="";
			id4.style.display="";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="";
			id8.style.display="";
			
			fm.PropType.value = "";
			fm.PropTypeName.value = "";
			fm.DocCode.value = "";
		}
	} else if (tSelectValue=='proptype') {
		
		if (tObj.value == "1") {//打印
			
			id3.style.display="";
			id4.style.display="";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="none";
			id8.style.display="none";
			
			fm.DocCode.value = "";
		} else if (tObj.value == "2") {//预印
			
			id3.style.display="none";
			id4.style.display="none";
			id5.style.display="";
			id6.style.display="";
			id7.style.display="";
			id8.style.display="";
			
			fm.DocCode.value = "";
		}
	}
}