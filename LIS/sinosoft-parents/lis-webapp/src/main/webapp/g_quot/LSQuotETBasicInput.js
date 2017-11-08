/***************************************************************
 * <p>ProName：LSQuotETBasicInput.js</p>
 * <p>Title：一般询价基本信息录入</p>
 * <p>Description：一般询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//中介机构 initAgencyListGrid
var turnPage2 = new turnPageClass();//其他准客户关联 initRelaCustListGrid
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var turnPage9 = new turnPageClass();
var mPreCustomerFlag = false;//准客户名称关联信息查询方式

/** 询价--第一步--开始*/
/**
 * 关联其他准客户
 */
function relaCustClick() {

	if (document.getElementById('RelaCustomerFlag').checked) {
		
		document.getElementById('divRelaCustInfo').style.display = '';
	} else {
		
		document.getElementById('divRelaCustInfo').style.display = 'none';
	}
}

/**
 * 初始化询价第一步信息
 */
function initQuotStep1() {
	
	fm2.QuotNo.value = tQuotNo;
	fm2.QuotBatNo.value = tQuotBatNo;
	
	//初始化基础信息
	initBasicInfo();
	
	document.getElementById("divPlanDiv").innerHTML = showPlanDiv();
}

/**
 * 初始化基础信息
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
	
		var tBasicInfo = new Array();
		var i = 0;
		//tBasicInfo[i] = "";
		tBasicInfo[i++] = "PreCustomerNo";
		tBasicInfo[i++] = "PreCustomerName";
		tBasicInfo[i++] = "IDType";
		tBasicInfo[i++] = "IDTypeName";
		tBasicInfo[i++] = "IDNo";
		tBasicInfo[i++] = "GrpNature";
		tBasicInfo[i++] = "GrpNatureName";
		tBasicInfo[i++] = "BusiCategory";
		tBasicInfo[i++] = "BusiCategoryName";
		tBasicInfo[i++] = "Address";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "PremMode";
		tBasicInfo[i++] = "PremModeName";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "RenewFlag";
		tBasicInfo[i++] = "RenewFlagName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "PayIntv";
		tBasicInfo[i++] = "PayIntvName";
		tBasicInfo[i++] = "InsuPeriod";
		tBasicInfo[i++] = "InsuPeriodFlag";
		tBasicInfo[i++] = "InsuPeriodFlagName";
		tBasicInfo[i++] = "Coinsurance";
		tBasicInfo[i++] = "CoinsuranceName";
		tBasicInfo[i++] = "ValDateType";
		tBasicInfo[i++] = "ValDateTypeName";
		tBasicInfo[i++] = "AppointValDate";
		tBasicInfo[i++] = "CustomerIntro";
		
		for (var t=0; t<i; t++) {
			document.getElementById(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		
		var tValDateType = fm2.ValDateType.value;
		if (tValDateType=="1") {
			
			document.getElementById("tdValDate1").style.display = '';			
			document.getElementById("tdValDate2").style.display = '';
			document.getElementById("tdValDate3").style.display = 'none';
			document.getElementById("tdValDate4").style.display = 'none';
		}
		
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//普通险种
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = 'none';
			document.getElementById("td4").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
		}
	}
	//销售渠道处理
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql12");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr1==null) {
		
		return false;
	} else {
		
		fm2.SaleChannel.value = tArr1[0][0];
		fm2.SaleChannelName.value = tArr1[0][1];
		
		if (tArr1[0][2].substring(0,1)=="1") {//需要中介
			
			//先查询中介信息
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql22");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			noDiv(turnPage2, AgencyListGrid, tSQLInfo.getString());
			
			document.getElementById("divAgencyInfo").style.display = "";
		}
	}

	//关联准客户处理
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql13");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);

	if (!noDiv(turnPage2, RelaCustListGrid, tSQLInfo.getString())) {
		initRelaCustListGrid();
		return false;
  }

  if (RelaCustListGrid.mulLineCount>0) {

		document.getElementById('RelaCustomerFlag').checked = true;
		document.getElementById('divRelaCustInfo').style.display = '';
  }
}

/**
 * 提交处理
 */
function basicSubmit() {

	if (!basicCheck()) {
		return false;
	}

	fm2.action = "./LSQuotETBasicSave.jsp?Operate=SAVEBASIC&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * 基础校验
 */
function basicCheck() {

	//校验基本信息是否录入完整
	if (isEmpty(fm2.PreCustomerNo) || isEmpty(fm2.PreCustomerName)) {
		alert("准客户名称不能为空！");
		return false;
	} else {
		var tPreCustomerNo = fm2.PreCustomerNo.value;
		var tPreCustomerName = fm2.PreCustomerName.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql43");
		tSQLInfo.addSubPara(tPreCustomerNo);
		tSQLInfo.addSubPara(tPreCustomerName);
		
		var tArrCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArrCount[0][0]=="0") {
			alert("准客户名称不存在！");
			return false;
		}
	}
	
	if (isEmpty(fm2.ProdType)) {
		
		alert("产品分类不能为空！");
		return false;
	} else {
		
		//对产品分类进行校验,产品类型不一致时,给出清空后续数据提示
		var tProdType = getProdType(tQuotNo, tQuotBatNo);
		if (tProdType==null || tProdType=="") {
			//为空,表示没有查询到
		} else {
			
			if (tProdType!=fm2.ProdType.value) {
				
				//校验该询价批次是否提交过,提交过的不允许更改
				var tMissionCount = getNotInputCount(tQuotNo, tQuotBatNo);
				if (tMissionCount=="0") {
				
				} else {
					alert("该询价已经提交过核保审核，不能再次变更产品类型！");
					return false;
				}

				if (!confirm("产品类型发生了改变，确认后将清空除基本信息外的所有数据，是否确认？")) {
					return false;
				}
			}
		}
	}
	
	if (isEmpty(fm2.SaleChannel)) {
		
		alert("渠道类型不能为空！");
		return false;
	}
	
	if (isEmpty(fm2.PremMode)) {
		
		alert("保费分摊方式不能为空！");
		return false;
	}
	
	var tPrePrem = fm2.PrePrem.value;
	if (isEmpty(fm2.PrePrem)) {//待添加校验?小数校验
		
		alert("预计保费规模不能为空！");
		return false;
	}
	
	if (!isNumeric(tPrePrem)) {
		alert("预计保费规模不是有效数字！");
		return false;
	}
	if (Number(tPrePrem)<0) {
		alert("预计保费规模应大于等于0！");
		return false;
	}
	
	if (!checkDecimalFormat(tPrePrem, 14, 2)) {
		alert("预计保费规模整数位不应超过14位,小数位不应超过2位！");
		return false;
	}
	if (isEmpty(fm2.RenewFlag)) {
		
		alert("是否为续保业务不能为空！");
		return false;
	}
	
	if (isEmpty(fm2.BlanketFlag)) {
		
		alert("是否为统括单不能为空！");
		return false;
	}
	
	if (fm2.ProdType.value == '00') {
		
		if (isEmpty(fm2.ElasticFlag)) {
			
			alert("是否为弹性计划不能为空！");
			return false;
		}
	}
	
	if (isEmpty(fm2.InsuPeriod)) {
		
		alert("保险期限不能为空！");
		return false;
	}
	
	var tInsuPeriod = fm2.InsuPeriod.value;
	if (isEmpty(fm2.InsuPeriod) || !isInteger(tInsuPeriod) || Number(tInsuPeriod)<0) {
		
		alert("保险期限不能为空，且应为大于0的整数！");
		return false;
	}
	
	if (isEmpty(fm2.ValDateType)) {
		
		alert("契约生效日类型不能为空！");
		return false;
	}
	
	var tValDateType = fm2.ValDateType.value;
	if (tValDateType=="1") {
		
		if (isEmpty(fm2.AppointValDate)) {
			
			alert("生效日期不能为空！");
			return false;
		}

	} else {
		fm2.AppointValDate.value = "";
	}
	
	if (isEmpty(fm2.PayIntv)) {
		
		alert("缴费方式不能为空！");
		return false;
	}
	
	if (isEmpty(fm2.Coinsurance)) {
		
		alert("是否共保不能为空！");
		return false;
	}
	
	//校验保障层次划分标准
	if (!checkElements()) {
		return false;
	}
	
	//校验中介信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql6");
	tSQLInfo.addSubPara(fm2.SaleChannel.value);

	var tAgencyFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tAgencyFlag[0][0]=="1") {//选择了需要含中介机构的渠道,不进行处理
		
	} else {//没有的话进行处理,清空中介录入域
		initAgencyListGrid();
	}
	
	//校验是否关联了其他准客户
	var tRelaCustomerFlag = fm2.RelaCustomerFlag.checked;
	if (tRelaCustomerFlag==true) {
		
		RelaCustListGrid.delBlankLine();
		if (RelaCustListGrid.mulLineCount==0) {
			alert("选择了关联其他准客户,必须录入其他准客户信息！");
			return false;
		} else {
			//校验关联准客户是否与询价主要准客户相同
			for (var i=0; i<RelaCustListGrid.mulLineCount; i++) {
			
				if (RelaCustListGrid.getRowColData(i, 1)==fm2.PreCustomerNo.value) {
					alert("关联准客户不能与询价主要准客户重复！");
					return false;
				}
			}
			//校验关联的其他准客户是否重复
			var vRow = RelaCustListGrid.mulLineCount;
			for (var i = 0; i < vRow; i++) {
				var mCustNo = RelaCustListGrid.getRowColData(i, 1);
				for (var j = 0; j < i; j++) {
					var nCustNo = RelaCustListGrid.getRowColData(j, 1);
					if (mCustNo == nCustNo) {
						alert("关联其他准客户重复，请修改！");
						return false;
					}
				}
			}
		}
	} else {
		
		initRelaCustListGrid();//重新初始化
	}
	
	return true;
}

/** 询价--第一步--结束*/

/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=='queryexp') {//产品类型
		
		if (Field.value=='00') {//普通险种
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = 'none';
			document.getElementById("td4").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
		}
	}
	
	if (cCodeType=='valdatetype') {//契约生效日类型
		
		if (Field.value=='1') {//约定日期

			document.getElementById("tdValDate1").style.display = '';			
			document.getElementById("tdValDate2").style.display = '';
			document.getElementById("tdValDate3").style.display = 'none';
			document.getElementById("tdValDate4").style.display = 'none';
		} else {//到账次日
			
			document.getElementById("tdValDate1").style.display = 'none';
			document.getElementById("tdValDate2").style.display = 'none';
			document.getElementById("tdValDate3").style.display = '';
			document.getElementById("tdValDate4").style.display = '';
		}
		
		return;
	}
	
	if (cCodeType=='salechannel') {//销售渠道
		
		if (Field.value!=null || Field.value=='') {//不为空时,查询该渠道是否含有中介
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql6");
			tSQLInfo.addSubPara(Field.value);
	
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				
				if (tArr[0]!='0') {//为1,表示含有中介机构
					
					document.getElementById("divAgencyInfo").style.display = '';
					return;
				}
			}
		}

		document.getElementById("divAgencyInfo").style.display = 'none';
		initAgencyListGrid();
		
		return;
	}
	
	if (cCodeType=='precustomerno'&& mPreCustomerFlag) {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql41");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm2.PreCustomerNo.value);
		tSQLInfo.addSubPara(tOperator);
		
		var tArrCust = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArrCust == null) {
			alert("查询客户信息失败！");
			return false;
		} else {
			fm2.IDType.value = tArrCust[0][2];
			fm2.IDTypeName.value = tArrCust[0][3];
			fm2.IDNo.value = tArrCust[0][4];
			fm2.GrpNature.value = tArrCust[0][5];
			fm2.GrpNatureName.value = tArrCust[0][6];
			fm2.BusiCategory.value = tArrCust[0][7];
			fm2.BusiCategoryName.value = tArrCust[0][8];
			fm2.Address.value = tArrCust[0][9];
			fm2.PrePrem.value = tArrCust[0][10];
			fm2.CustomerIntro.value = tArrCust[0][11];
			fm2.SaleChannel.value = tArrCust[0][12];
			fm2.SaleChannelName.value = tArrCust[0][13];
			
			//处理中介信息展示
			showDivAgencyInfo(fm2.SaleChannel.value);
		}
		mPreCustomerFlag = false;
	}
}

/**
 * 提交数据
 */
function submitForm(obj) {

	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
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
 * 下一步
 */
function nextStep() {
	
	goToStep(2);
}

/**
 * 回目录
 */
function returnLR() {
	
	goToStep(0);
}
/**
 * 查询准客户信息
 */
function queryCustomer(tMark) {
	
	if ((tMark == "Page" || tMark == "Enter" )&& tQuotBatNo !=1) {
		
		alert("再次询价时不可修改准客户名称！");
		return false;
	}
	var tPreCustomerNo = fm2.PreCustomerNo.value; 
	var tPreCustomerName = fm2.PreCustomerName.value;
	var tSelNo = RelaCustListGrid.lastFocusRowNo;//行号从0开始
	var strUrl = "LSQuotQueryCustomerMain.jsp?SelNo="+tSelNo+"&Mark="+tMark +"&PreCustomerNo="+tPreCustomerNo +"&PreCustomerName="+tPreCustomerName +"&QuotNo="+tQuotNo;
	window.open(strUrl,"准客户名称查询",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * 选择其他准客户时，校验主准客户是否改变
 */
function checkPerCustomerNoChange(cCustNo) {
	
	if (fm2.PreCustomerNo.value != cCustNo) {
		alert("主准客户已改变，请重新选择关联准客户!");
		initRelaCustListGrid();
		return false;
	}
}

/**
 * 根据销售渠道值，展示中介信息
 */
function showDivAgencyInfo(cField) {
	
	if (cField ==null || cField=='') {
		
		document.getElementById("divAgencyInfo").style.display = 'none';
	} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql6");
		tSQLInfo.addSubPara(cField);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr!=null) {
			if (tArr[0]!='0') {//为1,表示含有中介机构
				
				document.getElementById("divAgencyInfo").style.display = '';
			} else {
				document.getElementById("divAgencyInfo").style.display = 'none';
			}
		} 
	}
}
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='prodtype') {
		
		//var tSql = "prodtype|"+tQuotType;
		var tSql = "1 and codetype=#prodtype# and codeexp=#"+ tQuotType +"#";

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
		
	} else if (value1=='precustomerno') {
		
		var tPreCustomerName = fm2.PreCustomerName.value;
		if (tPreCustomerName==null || tPreCustomerName=="") {
			return false;
		}
		var conditionPreCustomerNo = "1 and a.makeoperator = #"+tOperator+"# and a.precustomername like #%"+tPreCustomerName+"%#";
		if (returnType=='0') {
			return showCodeList('precustomerno',value2,value3,null,conditionPreCustomerNo,'1','1',null);
		} else {
			return showCodeListKey('precustomerno',value2,value3,null,conditionPreCustomerNo,'1','1',null);
		}
		
	}
}

/**
 *  查询准客户
 */
function selectPreCustomer(Filed,FildName) {
	
	if (tQuotBatNo !=1) {
		
		alert("再次询价时不可修改准客户名称！");
		return false;
	}
	var objCodeName = FildName.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql41");
	tSQLInfo.addSubPara(objCodeName);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tOperator);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr == null) {
		alert("不存在该准客户！");
		return false;
	} else {
		if (tArr.length == 1) {
			Filed.value = tArr[0][0];
			FildName.value = tArr[0][1];
			fm2.IDType.value = tArr[0][2];
			fm2.IDTypeName.value = tArr[0][3];
			fm2.IDNo.value = tArr[0][4];
			fm2.GrpNature.value = tArr[0][5];
			fm2.GrpNatureName.value = tArr[0][6];
			fm2.BusiCategory.value = tArr[0][7];
			fm2.BusiCategoryName.value = tArr[0][8];
			fm2.Address.value = tArr[0][9];
			fm2.PrePrem.value = tArr[0][10];
			fm2.CustomerIntro.value = tArr[0][11];
			fm2.SaleChannel.value = tArr[0][12];
			fm2.SaleChannelName.value = tArr[0][13];
			
			showDivAgencyInfo(fm2.SaleChannel.value);
			
		} else if (tArr.length >1 && tArr.length <= 10) {
			
			var conditionPreCustomerNo = "1 and a.makeoperator = #"+tOperator+"# and a.precustomername like #%"+objCodeName+"%#";
			showCodeList('precustomerno', [Filed, FildName], [0,1], null,conditionPreCustomerNo, '1', '1',null);
			
			mPreCustomerFlag = true;
		} else if (tArr.length > 10){
			queryCustomer('Enter');
			showDivAgencyInfo(fm2.SaleChannel.value);
		}
	}
}


/**
 * 准客户名称模糊查询
 */
function fuzzyPreCustomerName(Filed,FildName) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		selectPreCustomer(Filed,FildName);
	}	
}

