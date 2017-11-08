<%
/***************************************************************
 * <p>ProName：LSQuotGrpSpecInit.jsp</p>
 * <p>Title：特别约定</p>
 * <p>Description：特别约定</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-01
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
		initGrpSpecInfoGrid();
		quryGrpSpec();//查询已保存特别约定
		queryGrpSpecInfo();//初始化查询标准特别约定
		
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
		if (tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") {
			
			fm.SaveButton.style.display = "";
			divStdGrpSpec.style.display = "";
		} else {
			
			fm.SaveButton.style.display = "none";
			divStdGrpSpec.style.display = "none";
		}
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
function initGrpSpecInfoGrid() {
	
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
		iArray[i][0] = "标准特别约定要素信息";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "标准特别约定要素信息编码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		GrpSpecInfoGrid = new MulLineEnter("fm", "GrpSpecInfoGrid");
		GrpSpecInfoGrid.mulLineCount = 0;
		GrpSpecInfoGrid.displayTitle = 1;
		GrpSpecInfoGrid.locked = 0;
		GrpSpecInfoGrid.canSel = 0;
		GrpSpecInfoGrid.canChk = 1;
		GrpSpecInfoGrid.hiddenPlus = 1;
		GrpSpecInfoGrid.hiddenSubtraction = 1;
		//GrpSpecInfoGrid.selBoxEventFuncName = "";
		GrpSpecInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化GrpSpecInfoGrid时出错："+ ex);
	}
}
</script>
