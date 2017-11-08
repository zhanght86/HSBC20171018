<%
/***************************************************************
 * <p>ProName:EdorACInit.jsp</p>
 * <p>Title:  投保人资料变更</p>
 * <p>Description:投保人资料变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>

<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * 初始化界面
 */
function initForm() {
	
	try {
		initParam();
		initButton();
		initInpBox();
		if("02"==tContType){
			queryAppInfo();
		}else {
			initIDInfoGrid();
			initTooContectGrid();
			queryOldClick();
		}

	} catch (re) {
		alert("初始化界面错误!");
	}
}


/**
 * 初始化参数
 */
function initParam() {
	
	try {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorACSql");
		tSQLInfo.setSqlId("EdorACSql23");
		tSQLInfo.addSubPara(tGrpContNo);
		
		var tResContType = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tResContType!=null && tResContType[0][0]!=""){
			tContType = tResContType[0][0];
		}
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
		
		if(tActivityID=='1800401002'){
			if("02"==tContType){
				divGrpInfo.style.display='none';
				divAppInfo.style.display='';
				divButton01.style.display='none';
				divButton03.style.display='';
			} else {
				divGrpInfo.style.display='';
				divAppInfo.style.display='none';
				divButton01.style.display='';
				divButton03.style.display='none';
			}
			divButton02.style.display='none';
		} else {
			if("02"==tContType){
				divGrpInfo.style.display='none';
				divAppInfo.style.display='';
			} else {
				divGrpInfo.style.display='';
				divAppInfo.style.display='none';
			}
			divButton01.style.display='none';
			divButton02.style.display='';
			divButton03.style.display='none';
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
//单位证件
function initIDInfoGrid() {
	
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型编码";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i][9]="证件类型编码|notnull&code:gidtype";
		iArray[i++][19]=1;
		
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效起期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效起期|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效止期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效止期|date";
		
		IDInfoGrid = new MulLineEnter("fm", "IDInfoGrid");
		IDInfoGrid.mulLineCount = 0;
		IDInfoGrid.displayTitle = 1;
		IDInfoGrid.locked = 0;
		IDInfoGrid.canSel = 0;
		IDInfoGrid.canChk = 0;
		IDInfoGrid.hiddenSubtraction = 0;
		IDInfoGrid.hiddenPlus = 0;
		IDInfoGrid.selBoxEventFuncName = "";
		IDInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
function initTooContectGrid() {

	turnPage6 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人姓名|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人电话";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人电话|notnull&PHONE";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人EMAIL";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人EMAIL|EMAIL";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人手机号";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人手机号|num&len<=11";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人部门";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件类型";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5]="6";
		iArray[i][6]="0"; 
		iArray[i++][19]=1; 
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件号码";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|num&len<=18";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件有效止期";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效止期|date";
		
		TooContectGrid = new MulLineEnter("fm", "TooContectGrid");
		TooContectGrid.mulLineCount = 0;
		TooContectGrid.displayTitle = 1;
		TooContectGrid.locked = 0;
		TooContectGrid.canSel = 0;
		TooContectGrid.canChk = 0;
		TooContectGrid.hiddenSubtraction = 0;
		TooContectGrid.hiddenPlus = 0;
		TooContectGrid.selBoxEventFuncName = "";
		TooContectGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
