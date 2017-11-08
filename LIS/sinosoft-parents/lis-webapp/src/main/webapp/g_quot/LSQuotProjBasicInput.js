/***************************************************************
 * <p>ProName：LSQuotProjBasicInput.js</p>
 * <p>Title：项目询价基本信息录入</p>
 * <p>Description：项目询价基本信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mLinkProNo = "0";//是否勾选关联其他项目询价号 0-未勾选

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
		initForm();
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
	document.getElementById("divPayIntvDiv").innerHTML = showPayIntvDiv();
	document.getElementById("divSaleChnlDiv").innerHTML = showSaleChnlDiv();
	pubShowAgencyInfoCheck(fm2);
	
}

/**
 * 项目询价--初始化基础信息
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
		
		var tBasicInfo = new Array();
		var i = 0;
		tBasicInfo[i] = "";
		tBasicInfo[i++] = "ProjName";
		tBasicInfo[i++] = "TargetCust";
		tBasicInfo[i++] = "NumPeople";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "PreLossRatio";
		tBasicInfo[i++] = "Partner";
		tBasicInfo[i++] = "ExpiryDate";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			document.getElementById(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//普通险种
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = 'none';
			document.getElementById("td6").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = '';
			document.getElementById("td6").style.display = '';
		}
	}
	
	//关联适用机构
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage2, AppOrgCodeGrid, tSQLInfo.getString())) {
		initAppOrgCodeGrid();
		return false;
	}
	
	//关联其他项目询价号既往信息
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage4, LinkInquiryNoGrid, tSQLInfo.getString())) {
		initLinkInquiryNoGrid();
		return false;
	}
  
	if (LinkInquiryNoGrid.mulLineCount>0) {

		document.getElementById('LinkInquiryNo').checked = true;
		document.getElementById('divLinkInquiryNo').style.display = '';
	}
}

/**
 * 展示中介机构名称
 */
function showAgencyInfo() {
	
	//关联中介名称
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage3, AgencyNameGrid, tSQLInfo.getString())) {
		initAgencyNameGrid();
		return false;
	}
}

/**
 * 选中渠道后是否展示中介名称
 */
function showIfAgencyName() {
	
	pubShowAgencyInfoCheck(fm2);
}


/**
 * 关联其他项目询价号既往信息
 */
function showRelaQuot() {

	if (document.getElementById('LinkInquiryNo').checked) {
		
		//initLinkInquiryNoGrid();
		mLinkProNo = "1";//已勾选--关联其他项目询价号既往信息
		document.getElementById('divLinkInquiryNo').style.display = '';
	} else {
		
		//initLinkInquiryNoGrid();
		mLinkProNo = "0";////未勾选--关联其他项目询价号既往信息
		document.getElementById('divLinkInquiryNo').style.display = 'none';
	}
}

/**
 * 保存基本信息前校验
 */
function basicCheck() {

	//校验基本信息是否录入完整
	if (isEmpty(fm2.QuotNo)) {
		alert("询价号不能为空！");
		return false;
	}
	
	if (isEmpty(fm2.QuotBatNo)) {
		alert("批次号不能为空！");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (isEmpty(fm2.ProdType)) {
		
		alert("产品类型不能为空！");
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
	
	if (fm2.ProdType.value == '00') {
		
		if (isEmpty(fm2.ElasticFlag)) {
			
			alert("是否为弹性计划不能为空！");
			return false;
		}
	}
	
	if (fm2.ExpiryDate.value<tCurrentDate) {
		alert("有效止期不能早于当前日期！");
		return false;
	}
	
	//校验保障层级划分标准
	if (!checkElements()) {
		return false;
	}
	
	if (fm2.ProjDesc.value.length>1500) {
		alert("项目说明应小于1500字！");
		return false;
	}
	
	//校验缴费方式
	if (!checkPayIntv()) {
		return false;
	}
	
	//校验适用机构
	if (!checkAppCom()) {
		return false;
	}
	
	//校验销售渠道是否选中
	if (!checkSaleChnl()) {
		return false;
	}
	
	//关联其他项目询价号既往信息
	if (!checkLinkInquiryNo()) {
		return false;
	}
	
	return true;
}

/**
 * 校验--适用机构
 */
function checkAppCom() {
	
	AppOrgCodeGrid.delBlankLine();//删除空行
	if(AppOrgCodeGrid.mulLineCount<=0) {
 	
		alert("适用机构不能为空！");
		return false;
 	} else {
 		var vRow = AppOrgCodeGrid.mulLineCount;
		for (var i = 0; i < vRow; i++) {
			var mAppCom = AppOrgCodeGrid.getRowColData(i, 1);
			for (var j = 0; j < i; j++) {
				var nAppCom = AppOrgCodeGrid.getRowColData(j, 1);
				if (mAppCom == nAppCom) {
					alert("适用机构重复，请修改！");
					return false;
				}
			}
		}
 	}
	return true;
}


/**
 * 校验--关联其他项目询价号既往信息
 */
function checkLinkInquiryNo() {
	
	if (document.getElementById('LinkInquiryNo').checked) {
		LinkInquiryNoGrid.delBlankLine();//删除空行
		if(LinkInquiryNoGrid.mulLineCount<=0) {
	 	
			alert("关联询价号不能为空！");
			return false;
	 	} else {
	 		var vRow = LinkInquiryNoGrid.mulLineCount;
			for (var i = 0; i < vRow; i++) {
				var mLinkNo = LinkInquiryNoGrid.getRowColData(i, 1);
				for (var j = 0; j < i; j++) {
					var nLinkNo = LinkInquiryNoGrid.getRowColData(j, 1);
					if (mLinkNo == nLinkNo) {
						alert("关联询价号重复，请修改！");
						return false;
					}
				}
			}
	 	}
	}
	return true;
}

///**
// * 查询适用机构编码
// */
//
//function queryCom() {
//	
//	var tSelNo = AppOrgCodeGrid.lastFocusRowNo;//行号从0开始
//	var strUrl = "LSQuotQueryAppComMain.jsp?SelNo="+tSelNo;
//	window.open(strUrl,"适用机构查询",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
//}


/**
 * 基本信息保存
 */
function basicSubmit() {

	if (!basicCheck()) {
		return false;
	}
	
	showRelaQuot();//防止刷新进入没点击
	fm2.action = "./LSQuotProjBasicSave.jsp?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&LinkProNo="+ mLinkProNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
	submitForm(fm2);
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


function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='prodtype') {
		
		var tSql = "1 and codetype=#prodtype# and codeexp=#"+tQuotType+"#";

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
}

/**
 * 下拉后处理
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=='queryexp') {//产品类型
		
		if (Field.value=='00') {//普通险种
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = 'none';
			document.getElementById("td6").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = '';
			document.getElementById("td6").style.display = '';
		}
	}
}
