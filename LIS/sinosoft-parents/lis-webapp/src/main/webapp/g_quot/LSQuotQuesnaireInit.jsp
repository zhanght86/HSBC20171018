<%
/***************************************************************
 * <p>ProName：LSQuotQuesnaireInit.jsp</p>
 * <p>Title：问卷调查</p>
 * <p>Description：问卷调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-24
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		initQuesnaireGrid();
		queryClick();
		
		if (tActivityID == "0800100001") {
			
			divQuesnaireUpload.style.display = "";
			
			fmupload.UploadButton.style.display = "";
			fmupload.DownloadButton.style.display = "";
			fmupload.DeleteButton.style.display = "";
		} else {
			
			divQuesnaireUpload.style.display = "none";
			divPersonButton.style.display = "none";
			fmupload.UploadButton.style.display = "none";
			fmupload.DownloadButton.style.display = "";
			fmupload.DeleteButton.style.display = "none";
			
		}
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

/**
 * 把null的字符串转为空
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}

/**
 * 初始化列表
 */
function initQuesnaireGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "问卷类型";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "问卷名称";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件系统名称";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "问卷路径";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "上传位置";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "上传人员";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "上传日期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		QuesnaireGrid = new MulLineEnter("fm", "QuesnaireGrid");
		QuesnaireGrid.mulLineCount = 0;
		QuesnaireGrid.displayTitle = 1;
		QuesnaireGrid.locked = 0;
		QuesnaireGrid.canSel = 1;
		QuesnaireGrid.canChk = 0;
		QuesnaireGrid.hiddenPlus = 1;
		QuesnaireGrid.hiddenSubtraction = 1;
		QuesnaireGrid.selBoxEventFuncName = "showPages";
		QuesnaireGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化QuesnaireGrid时出错："+ ex);
	}
}
</script>
