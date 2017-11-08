<%
/***************************************************************
 * <p>ProName：LCContPolInit.jsp</p>
 * <p>Title：新单录入</p>
 * <p>Description：新单录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-28
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
		getPolicyInfo();
		initAgentDetailGrid();
		initBusinessAreaGrid();
		initAgentComGrid();
		initSalerInfoGrid();
		initIDInfoGrid();
		initTooContectGrid();
		initRiskGrid();
		initEntry();
		initEntry1();
		initEntry2();
		initEntry3();
		initEntry4();
		initEntry5();
		initEntry6();
		initEntry7();
		initEntry8();
		//initEntry9();
		initEntry10();
		initEntry11();
		initEntry12();
		queryRisk();
		showInfoEngin();
		//showRelaInfo();
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
//		if(scantype==1||scantype==2) {//判断是否随动
//			setFocus();
//		}
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件
 */
function initInpBox() {
	try {
		fm.GrpPropNo.value = tGrpPropNo;
		fm.SaleDepart.value = "G";
		fm.SaleDepartName.value = "团险";
		if(nullToEmpty(tContPlanType)==''){
			var tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContSql");
			tSQLInfo.setSqlId("LCContSql20");
			tSQLInfo.addSubPara(tGrpPropNo);
			tSQLInfo.addSubPara(tGrpPropNo);
			var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tPropEntry==null||tPropEntry==''){
				alert("不能判断保单类型!");
				return false;
			}else{
				tContPlanType=tPropEntry[0][0];
			}
		}
		if(tContPlanType=='00'){
			pricingmode.style.display='none';
			divBasicInfo.style.display='none';
			EngTable.style.display='none';
			divEnginFactor.style.display='none';
			InsuredTable.style.display='none';
			PremTable.style.display='none';
		}else if(tContPlanType=='01'){
			pricingmode.style.display='none';
		}else if(tContPlanType=='02'){
			EngTable.style.display='none';
			divBasicInfo.style.display='none';
			divEnginFactor.style.display='none';
			InsuredTable.style.display='none';
			PremTable.style.display='none';
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
		if(tFlag=='0'){
			fm.Approve.style.display='none';
			fm.GrpCont.style.display='';
			fm.SaveCont.style.display='';
			fm.Question.style.display='';
		}else if(tFlag=='1'){
			fm.Approve.style.display='';
			fm.GrpCont.style.display='none';
			fm.SaveCont.style.display='none';
			fm.Question.style.display='';
			fm.ClaimDuty.style.display='';
		}else if(tFlag=='3'){
			fm.Question.style.display='none';
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
//客户经理
function initAgentDetailGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "客户经理代码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryManager";
		iArray[i++][9]="客户经理代码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "客户经理姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryManager";
		iArray[i++][9]="客户经理姓名|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "所属机构";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "分佣比例";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="佣金比例|notnull&num&>0";
		
		AgentDetailGrid = new MulLineEnter("fm", "AgentDetailGrid");
		AgentDetailGrid.mulLineCount = 0;
		AgentDetailGrid.displayTitle = 1;
		AgentDetailGrid.locked = 0;
		AgentDetailGrid.canSel = 0;
		AgentDetailGrid.canChk = 0;
		AgentDetailGrid.hiddenSubtraction = 0;
		AgentDetailGrid.hiddenPlus = 0;
		AgentDetailGrid.selBoxEventFuncName = "";
		AgentDetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误(01)!");
	}
}
//中介机构
function initAgentComGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构编码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="中介机构编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "中介机构名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="中介机构编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "中介代理人代码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "querySalerInfo";
		iArray[i++][9]="";
		
		iArray[i] = new Array();
		iArray[i][0] = "中介代理人姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i++][7] = "querySalerInfo";
		
		iArray[i] = new Array();
		iArray[i][0] = "所属区域";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		AgentComGrid = new MulLineEnter("fm", "AgentComGrid");
		AgentComGrid.mulLineCount = 0;
		AgentComGrid.displayTitle = 1;
		AgentComGrid.locked = 0;
		AgentComGrid.canSel = 0;
		AgentComGrid.canChk = 0;
		AgentComGrid.hiddenSubtraction = 0;
		AgentComGrid.hiddenPlus = 0;
		AgentComGrid.selBoxEventFuncName = "";
		AgentComGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误(02)!");
	}
}

//代理人
function initSalerInfoGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "代理人代码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i++][7] = "querySaler";
		
		iArray[i] = new Array();
		iArray[i][0] = "代理人姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "所属区域";
		iArray[i][1] = "0px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "分佣比例";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="佣金比例|notnull&num&>0";
		
		iArray[i] = new Array();
		iArray[i][0] = "所属区域";
		iArray[i][1] = "17px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		SalerInfoGrid = new MulLineEnter("fm", "SalerInfoGrid");
		SalerInfoGrid.mulLineCount = 0;
		SalerInfoGrid.displayTitle = 1;
		SalerInfoGrid.locked = 0;
		SalerInfoGrid.canSel = 0;
		SalerInfoGrid.canChk = 0;
		SalerInfoGrid.hiddenSubtraction = 0;
		SalerInfoGrid.hiddenPlus = 0;
		SalerInfoGrid.selBoxEventFuncName = "";
		SalerInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误(03)!");
	}
}
function initBusinessAreaGrid() {
	
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "归属地代码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][4]="conditioncomcode";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1"; 
		iArray[i][9]="归属地代码|notnull";
		iArray[i][15]="1";
		iArray[i++][16]="1 and comgrade=#03#";
		
		iArray[i] = new Array();
		iArray[i][0] = "归属地名称";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][4]="conditioncomcode";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1"; 
		iArray[i][9]="归属地代码|notnull";
		iArray[i][15]="1";
		iArray[i++][16]="1 and comgrade=#03#";
		
		iArray[i] = new Array();
		iArray[i][0] = "归属比例";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="归属比例|notnull&num";
		
		iArray[i] = new Array();
		iArray[i][0] = "归属地客户经理代码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryMComManager";
		iArray[i++][9]="归属地客户经理代码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "归属地客户经理姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryMComManager";
		iArray[i++][9]="归属地客户经理代码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "分佣比例";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="佣金比例|notnull&num";
		
		BusinessAreaGrid = new MulLineEnter("fm", "BusinessAreaGrid");
		BusinessAreaGrid.mulLineCount = 0;
		BusinessAreaGrid.displayTitle = 1;
		BusinessAreaGrid.locked = 0;
		BusinessAreaGrid.canSel = 0;
		BusinessAreaGrid.canChk = 0;
		BusinessAreaGrid.hiddenSubtraction = 0;
		BusinessAreaGrid.hiddenPlus = 0;
		BusinessAreaGrid.selBoxEventFuncName = "";
		BusinessAreaGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误(04)!");
	}
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
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 0;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i++][9]="证件类型编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 10;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i++][9]="证件类型编码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "20px";
		iArray[i][2] = 25;
		iArray[i][3] = 1;
		iArray[i++][9]="证件号码|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效起期";
		iArray[i][1] = "15px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9]="证件有效起期|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "证件有效止期";
		iArray[i][1] = "15px";
		iArray[i][2] = 10;
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
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人姓名|notnull&len<20";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人电话";
		iArray[i][1] = "15px";
		iArray[i][2] = 15;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人电话|PHONE&notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人EMAIL";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人EMAIL|EMAIL";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人手机号";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="联系人手机号|NUM&len<=11";
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人部门";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件类型";
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5]="6";
		iArray[i++][6]="0";  
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件号码";
		iArray[i][1] = "15px";
		iArray[i][2] = 20;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "联系人证件有效止期";
		iArray[i][1] = "13px";
		iArray[i][2] = 30;
		iArray[i++][3] = 1;
		
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
//险种
function initRiskGrid(){
	turnPage7 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种编码";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "险种名称";
		iArray[i][1] = "30px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "缴费周期";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "投保人数";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保费";
		iArray[i][1] = "10px";
		iArray[i][2] = 200;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额";
		iArray[i][1] = "10px";
		iArray[i][2] = 200;
		iArray[i++][3] = 0;
		
		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
		RiskGrid.mulLineCount = 0;   
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =0;
		RiskGrid.chkBoxEventFuncName = "showBonusFlag";
		RiskGrid. hiddenPlus=1;
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);  
	} catch (ex) {
		alert("初始化界面错误!");
	}
 }
</script>
