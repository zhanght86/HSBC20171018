<%
/***************************************************************
 * <p>ProName：LSQuotCountRateInit.jsp</p>
 * <p>Title：费率测算</p>
 * <p>Description：费率测算</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-05-29
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
		initCustInfoGrid();
		
		if (tActivityID == "" || tActivityID==null) {
			
			fm2.SavePolInfo.style.display = "none";
			fm2.DelPolInfo.style.display = "none";
			fm2.AddButton.style.display = "none";
			fm2.ModButton.style.display = "none";
			fm2.DelButton.style.display = "none";
			
			divCustImp.style.display = "none";
		}
		
		queryPolInfo();
		queryCustInfo();
		
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
		fm2.RiskCode.value = "";
		fm2.RiskName.value = "";
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
		fm2.EffectivDate.value = "";
		fm2.CalDirection.value = "G";
		fm2.CalDirectionName.value = "保额算保费";
		fm2.Amnt.value = "";
		fm2.PayIntv.value = "";
		fm2.PayIntvName.value = "";
		fm2.InsuPeriod.value = "";
		fm2.InsuPeriodFlag.value = "Y";
		fm2.InsuPeriodFlagName.value = "年";
		fm2.ReceiveCode.value = "";
		fm2.ReceiveFlag.value = "";
		fm2.ReceiveName.value = "";
	
		fm2.InsuredName.value = "";
		fm2.Gender.value = "";
		fm2.GenderName.value = "";
		fm2.InsuredAge.value = "";
		fm2.Birthday.value = "";
		fm2.IDNo.value = "";
		
		document.getElementById('expTD1').style.display = "none";
		document.getElementById('expTD2').style.display = "none";
		document.getElementById('expTD3').style.display = "none";
		document.getElementById('expTD4').style.display = "none";
		document.getElementById('expTD5').style.display = "none";
		document.getElementById('expTD6').style.display = "none";
		document.getElementById('expTD7').style.display = "";
		document.getElementById('expTD8').style.display = "";
		document.getElementById('expTD9').style.display = "";
		document.getElementById('expTD10').style.display = "";
		
		fm2.ExpType.value = "";
		fm2.ExpTypeName.value = "";
		fm2.Age.value = "";
		fm2.YearStart.value = "";
		fm2.YearEnd.value = "";
			
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
	/**
		if (tActivityID=="0100100001" || tActivityID=="0100100002" || tActivityID=="0100100003") {
			
			fm2.AddButton.style.display = "";
			fm2.ModButton.style.display = "";
			fm2.DelButton.style.display = "";
		} else {
			
			fm2.AddButton.style.display = "none";
			fm2.ModButton.style.display = "none";
			fm2.DelButton.style.display = "none";
		}
	*/
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
 * 被保人信息
 */
function initCustInfoGrid() {
	
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
		iArray[i][0] = "流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "询价号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "批次号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "姓名";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "身份证号码";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "每期应交保费";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总保费";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保额";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "费率";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		CustInfoGrid = new MulLineEnter("fm2", "CustInfoGrid");
		CustInfoGrid.mulLineCount = 0;
		CustInfoGrid.displayTitle = 1;
		CustInfoGrid.locked = 0;
		CustInfoGrid.canSel = 1;
		CustInfoGrid.canChk = 0;
		CustInfoGrid.hiddenSubtraction = 1;
		CustInfoGrid.hiddenPlus = 1;
		CustInfoGrid.selBoxEventFuncName = "showCustInfo";
		CustInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化界面错误!");
	}
}
</script>
