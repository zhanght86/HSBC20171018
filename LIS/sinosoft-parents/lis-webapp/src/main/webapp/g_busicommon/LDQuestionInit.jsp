<%
/***************************************************************
 * <p>ProName：LDQuestionInit.jsp</p>
 * <p>Title：问题件管理</p>
 * <p>Description：问题件管理</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-04
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
		initQuestionGrid();
		queryClick("2");
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
		
		if (tShowStyle=="1") {
			
			divQuesType.style.display = "none";
		} else if (tShowStyle=="2"){
			
			divQuesType.style.display = "";
		}
		
		fm.QuesType.value = "";
		fm.QuesTypeName.value = "";
		fm.SendContent.value = "";
		fm.ReplyContent.value = "";
		divReplyContent.style.display = "none";
		//add by JingDian 新单保全，没有问题件，则不展示增加
		if("NB"==tOtherNoType || "POS"==tOtherNoType){
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_busicommon.LDQuestiontSql");
			tSQLInfo.setSqlId("LDQuestiontSql3");
			tSQLInfo.addSubPara(tActivityID);
			var tResArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tResArr==null || tResArr[0][0]=="") {
				tNBPOSFlag = "1";
			}
			divMistake.style.display = "";
			fm.Mistake.value = "0";
			fm.Mistake.checked = false;
		}else {
			fm.Mistake.value = "";
			fm.Mistake.checked = false;
		}
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		if (tShowStyle=="1") {
			
			fm.AddButton.style.display = "none";
			fm.ShowButton.style.display = "";
		} else if (tShowStyle=="2"){
			
			fm.AddButton.style.display = "";
			fm.ShowButton.style.display = "none";
		} else if (tShowStyle=="3"){
			
			fm.AddButton.style.display = "";
			fm.ShowButton.style.display = "none";
		}
		//add by JingDian 新单保全，没有问题件，则不展示增加
		if(tNBPOSFlag=="1"){
			fm.AddButton.style.display = "none";
		}
		
		//add by ZhangC 20141216 处理询价查询时，问题件模块按钮的展示
		if (tShowFlag =="1") {
			
			fm.AddButton.style.display = "none";
			fm.ShowButton.style.display = "";
		} 
		
		fm.ModifyButton.style.display = "none";
		fm.DeleteButton.style.display = "none";
		fm.ReplyButton.style.display = "none";
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
function initQuestionGrid() {
	
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
		iArray[i][0] = "问题件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "问题件类型编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tShowStyle=="1") {
			
			iArray[i] = new Array();
			iArray[i][0] = "问题件类型";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tShowStyle=="2"){
			
			iArray[i] = new Array();
			iArray[i][0] = "问题件类型";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "问题内容";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "回复内容";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "问题件状态编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "问题件状态";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "发送节点编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "发送节点";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "发送日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "回复节点编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "回复节点";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "回复日期";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "是否记入差错";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if("NB"==tOtherNoType || "POS"==tOtherNoType){
			iArray[i] = new Array();
			iArray[i][0] = "发送人";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "回复人";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else {
			iArray[i] = new Array();
			iArray[i][0] = "发送人";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "回复人";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		iArray[i] = new Array();
		iArray[i][0] = "单证细类编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "单证细类";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		QuestionGrid = new MulLineEnter("fm", "QuestionGrid");
		QuestionGrid.mulLineCount = 0;
		QuestionGrid.displayTitle = 1;
		QuestionGrid.locked = 0;
		QuestionGrid.canSel = 1;
		QuestionGrid.canChk = 0;
		QuestionGrid.hiddenPlus = 1;
		QuestionGrid.hiddenSubtraction = 1;
		QuestionGrid.selBoxEventFuncName = "showDetail";
		QuestionGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化QuestionGrid时出错："+ ex);
	}
}
</script>
