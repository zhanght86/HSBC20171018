/***************************************************************
 * <p>ProName：LDAttachmentInput.js</p>
 * <p>Title：附件管理</p>
 * <p>Description：附件管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var mAttachID = "";
var mAttachType = "";
var tSQLInfo = new SqlClass();

/**
 * 查询
 */
function queryClick() {
	
	
	var tQuotUpLoadNode = "";
	if (tOtherNoType=="QUOT") {// 增加询价节点限制
		
		if (tUploadNode=="0800100001") {//询价录入
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001') ";
		} else if (tUploadNode=="0800100002") {//中核
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001','0800100002') ";
		} else if (tUploadNode=="0800100003") {//分核
			
			tQuotUpLoadNode = " and a.uploadnode in('0800100001','0800100002','0800100003') ";
		} 
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_busicommon.LDAttachmentSql");
	tSQLInfo.setSqlId("LDAttachmentSql1");
	tSQLInfo.addSubPara(tOtherNoType);
	tSQLInfo.addSubPara(tOtherNo);
	if (tSubOtherNo==null || tSubOtherNo=="" || tSubOtherNo=="null") {
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara(tSubOtherNo);
	}
	tSQLInfo.addSubPara(tQuotUpLoadNode);
	
	turnPage1.queryModal(tSQLInfo.getString(), AttachmentGrid,1,1);
}

/**
 * 上传附件
 */
function upLoadClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	mAttachType = fmupload.all('AttachType').value;
	var tFileName = fmupload.all('UploadPath').value;
	var tExtenName = "";
	
	if (tFileName.indexOf("\\")>0) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("\\")+1);
	}
	if (tFileName.indexOf("/")>0 ) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("/")+1);
	}
	
	if (tFileName.indexOf(".")>0 ) {
		tExtenName = tFileName.substring(tFileName.lastIndexOf(".")+1);
	}
	submitForm();
}

/**
 * 下载附件
 */
function downLoadClick() {
	
	var tSelNo = AttachmentGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要下载的附件！");
		return false;
	}
	
	var tFilePath = AttachmentGrid.getRowColData(tSelNo-1, 5);
	var tFileName = AttachmentGrid.getRowColData(tSelNo-1, 3);
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * 删除附件
 */
function deleteClick() {
	
	var tSelNo = AttachmentGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要删除的附件！");
		return false;
	}
	
	mAttachID = AttachmentGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fmupload.action = "./LDAttachmentSave.jsp?OtherNoType="+tOtherNoType+"&OtherNo="+tOtherNo+"&SubOtherNo="+tSubOtherNo+"&UploadNode="+tUploadNode+"&Operate="+mOperate+"&AttachID="+mAttachID+"&AttachType="+mAttachType;
	fmupload.submit();
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
	}
	queryClick();
	fmupload.AttachType.value = "";
	fmupload.AttachTypeName.value = "";
	fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
}

/**
 * 提交前的校验、计算
 */
function beforeSubmit() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}
