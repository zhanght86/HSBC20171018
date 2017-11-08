/***************************************************************
 * <p>ProName：LSQuotQuestionInput.js</p>
 * <p>Title：问卷调查</p>
 * <p>Description：问卷调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var mOperate = "";//操作状态
var mAttachID = "";
var mSubAttachType = "";
var tSQLInfo = new SqlClass();
var s_img =0;
var b_img = 0;
var w  = 0;

/**
 * 查询
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuesnaireSql");
	tSQLInfo.setSqlId("LSQuotQuesnaireSql1");
	tSQLInfo.addSubPara("QUOT");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), QuesnaireGrid,0,1);
}

/**
 * 上传附件
 */
function upLoadClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	
	mSubAttachType = fmupload.all('QuesnaireType').value;
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
	
	if (tExtenName!="gif") {
		alert("仅支持gif格式的文件上载！");
		return false;
	}
	
	submitForm();
}

/**
 * 下载附件
 */
function downLoadClick() {
	
	var tSelNo = QuesnaireGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要下载的问卷！");
		return false;
	}
	
	var tFilePath = QuesnaireGrid.getRowColData(tSelNo-1, 5);
	var tFileName = QuesnaireGrid.getRowColData(tSelNo-1, 3);
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * 删除附件
 */
function deleteClick() {
	
	var tSelNo = QuesnaireGrid.getSelNo();
	if (tSelNo==0) {
		alert("请选择需要删除的问卷！");
		return false;
	}
	
	mAttachID = QuesnaireGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
	submitForm();
}

/**
 * 上传个人问卷
 */
function PersonClick() {
	
	//进入个人核保界面
	window.open("./LSQuotPastMain.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID+"&Flag=Quesnaire","既往信息",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	fmupload.action = "../g_busicommon/LDAttachmentSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mOperate+"&AttachID="+mAttachID+"&AttachType=00&SubAttachType="+mSubAttachType;
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
		
		queryClick();
		fmupload.QuesnaireType.value = "";
		fmupload.QuesnaireTypeName.value = "";
		fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
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
	
	return true;
}

/**
 * 查询问卷图片
 */
function showPages() {

	var tSelNo = QuesnaireGrid.getSelNo();
	if(tSelNo==0 || tSelNo==null) {
		alert("请选择一条问卷信息！");
	}
	var tQuesnairePath = QuesnaireGrid.getRowColData(tSelNo-1, 5);
	var tFileType = QuesnaireGrid.getRowColData(tSelNo-1, 9);
	//	window.open("./ShowPictureMain.jsp?FileFullPath="+tQuesnairePath,"问卷浏览",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	if(tQuesnairePath!=null&&tQuesnairePath!="") {
		
		fm3.service.width = 1200;
		if(w!=0){
			fm3.service.width = w;
		}
		
		fm3.service.src = "../common/jsp/ShowPicture.jsp?FileFullPath="+ tQuesnairePath +"&FileType="+ tFileType;
		fm3.all('divPages').filters.item(0).rotation = "0";
		divPages.style.display = '';
		w = fm3.service.width;
		s_img =0;
		b_img = 0;
	} else {
		fm3.service.src = "";
		fm3.service.width= 0;
		divPages.style.display = 'none';
		alert("没有影像件");
	}
}
