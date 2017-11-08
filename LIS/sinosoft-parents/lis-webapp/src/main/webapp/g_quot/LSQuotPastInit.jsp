<%
/***************************************************************
 * <p>ProName：LSQuotPastInit.jsp</p>
 * <p>Title：既往信息</p>
 * <p>Description：既往信息</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
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
		initPastTotalGrid();
		initPastDetailGrid();
		initPersonUWGrid();
		divPastDetailTitle.style.display = "none";
		queryTotal();
		queryPersonUW();
		divRemark.style.display = "none";
		
		if (tFlag=="Quesnaire") {
			
			var menu=document.getElementById("tab1").getElementsByTagName("li");
		
			for(i=0;i<2;i++)
			{
			   menu[i].className=i==1?"now":""; 
				if (i==1) {
					document.getElementById("tablistdiv"+i).style.display = "block";
				} else {
					document.getElementById("tablistdiv"+i).style.display = "none";
				}
			}
		}
		
		if (tActivityID == "0800100001") {
			
			fm.PastTotalAddButton.style.display = "";
			fm.PastTotalModifyButton.style.display = "";
			fm.PastTotalDeleteButton.style.display = "";
			fm.PastDetailAddButton.style.display = "";
			fm.PastDetailModifyButton.style.display = "";
			fm.PastDetailDeleteButton.style.display = "";
			
			fm2.PerUWAddButton.style.display = "";
			fm2.PerUWModifyButton.style.display = "";
			fm2.PerUWDeleteButton.style.display = "";
			fm2.PerUWSaveButton.style.display = "none";
			
			divUWOpinion.style.display = "none";
			divUWDesc.style.display = "none";
			divAttachmentUpload.style.display = "";
			
		} else if (tActivityID == "0800100002" || tActivityID == "0800100003" || tActivityID == "0800100004") {
			
			fm.PastTotalAddButton.style.display = "none";
			fm.PastTotalModifyButton.style.display = "none";
			fm.PastTotalDeleteButton.style.display = "none";
			fm.PastDetailAddButton.style.display = "none";
			fm.PastDetailModifyButton.style.display = "none";
			fm.PastDetailDeleteButton.style.display = "none";
			
			fm2.PerUWAddButton.style.display = "none";
			fm2.PerUWModifyButton.style.display = "none";
			fm2.PerUWDeleteButton.style.display = "none";
			fm2.PerUWSaveButton.style.display = "";
			
			divUWOpinion.style.display = "";
			divUWDesc.style.display = "";
			divAttachmentUpload.style.display = "none";
		} else {
			
			fm.PastTotalAddButton.style.display = "none";
			fm.PastTotalModifyButton.style.display = "none";
			fm.PastTotalDeleteButton.style.display = "none";
			fm.PastDetailAddButton.style.display = "none";
			fm.PastDetailModifyButton.style.display = "none";
			fm.PastDetailDeleteButton.style.display = "none";
			
			fm2.PerUWAddButton.style.display = "none";
			fm2.PerUWModifyButton.style.display = "none";
			fm2.PerUWDeleteButton.style.display = "none";
			fm2.PerUWSaveButton.style.display = "none";
			
			divUWOpinion.style.display = "none";
			divUWDesc.style.display = "none";
			divAttachmentUpload.style.display = "none";
		}
		
		if (tActivityID == "0800100002" || tActivityID == "0800100003" || tActivityID == "0800100004" || tActivityID == "0800100005") {
			fm.QueryAllCal.style.display = "";
		} else {
			fm.QueryAllCal.style.display = "none";
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
		
		initTotalInp();
		initDetailInp();
		initPersonUWInp();
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化汇总信息
 */
function initTotalInp() {
	
	try {
		
		fm.InsuYear.value = "";
		fm.InsuranceCom.value = "";
		fm.InsuranceComName.value = "";
		fm.SumPrem.value = "";
		fm.SumLoss.value = "";
		fm.SumClaimPeople.value = "";
		fm.MaleRate.value = "";
		fm.FemaleRate.value = "";
	} catch (ex) {
		alert("初始化汇总信息录入控件错误！");
	}
}

/**
 * 初始化明细信息
 */
function initDetailInp() {
	
	try {
		
		fm.DetailInsuYear.value = "";
		fm.DetailInsuranceComName.value = "";
		fm.GrpContNo.value = "";
		fm.ValDate.value = "";
		fm.EndDate.value = "";
		fm.NonMedSumPrem.value = "";
		fm.NonMedSumLoss.value = "";
		fm.NonMedPeople.value = "";
		fm.NonMedClmPeople.value = "";
		fm.MedSumPrem.value = "";
		fm.MedSumLoss.value = "";
		fm.MedPeople.value = "";
		fm.MedClmPeople.value = "";
	} catch (ex) {
		alert("初始化明细信息录入控件错误！");
	}
}

/**
 * 初始化个人核保信息
 */
function initPersonUWInp() {
	
	try {
		
		fm2.ContNo.value = "";
		fm2.GrpName.value = "";
		fm2.InsuredName.value = "";
		fm2.IDType.value = "";
		fm2.IDTypeName.value = "";
		fm2.IDNo.value = "";
		fm2.Gender.value = "";
		fm2.GenderName.value = "";
		fm2.Birthday.value = "";
		fm2.Age.value = "";
		fm2.RiskName.value = "";
		fm2.LossAmount.value = "";
		fm2.Reason.value = "";
		fm2.Remark.value = "";
		fm2.UWOpinion.value = "";
		fm2.UWOpinionName.value = "";
		fm2.UWDesc.value = "";
	} catch (ex) {
		alert("初始化个人核保信息录入控件错误！");
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
function initPastTotalGrid() {
	
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
		iArray[i][0] = "汇总信息流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务来源";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单年度";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险公司代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保险公司名称";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总保费";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总赔付";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "总出险人数";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "男性比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "女性比例";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "男女比例";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PastTotalGrid = new MulLineEnter("fm", "PastTotalGrid");
		PastTotalGrid.mulLineCount = 0;
		PastTotalGrid.displayTitle = 1;
		PastTotalGrid.locked = 0;
		PastTotalGrid.canSel = 1;
		PastTotalGrid.canChk = 0;
		PastTotalGrid.hiddenPlus = 1;
		PastTotalGrid.hiddenSubtraction = 1;
		PastTotalGrid.selBoxEventFuncName = "showPastTotal";
		PastTotalGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化PastTotalGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initPastDetailGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "明细信息流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号码";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单生效日期";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单终止日期";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "非医疗险总保费(元)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "非医疗险总赔付(元)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "非医疗险被保险人数";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "非医疗险出险人数";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "医疗险总保费(元)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "医疗险总赔付(元)";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "医疗险被保险人数";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "医疗险出险人数";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PastDetailGrid = new MulLineEnter("fm", "PastDetailGrid");
		PastDetailGrid.mulLineCount = 0;
		PastDetailGrid.displayTitle = 1;
		PastDetailGrid.locked = 0;
		PastDetailGrid.canSel = 1;
		PastDetailGrid.canChk = 0;
		PastDetailGrid.hiddenPlus = 1;
		PastDetailGrid.hiddenSubtraction = 1;
		PastDetailGrid.selBoxEventFuncName = "showPastDetail";
		PastDetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化PastDetailGrid时出错："+ ex);
	}
}

/**
 * 初始化列表
 */
function initPersonUWGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "序号";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "个人核保信息流水号";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务来源代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "业务来源";
		iArray[i][1] = "12px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "保单号";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "单位名称";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "姓名";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件类型";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "证件号码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "性别";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "出生日期";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "年龄";
		iArray[i][1] = "8px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "赔付险种";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "赔付金额(元)";
		iArray[i][1] = "13px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "关注原因";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "备注";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tActivityID == "0800100001") {
			
			iArray[i] = new Array();
			iArray[i][0] = "是否已核保";
			iArray[i][1] = "13px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			iArray[i] = new Array();
			iArray[i][0] = "是否已核保";
			iArray[i][1] = "13px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "核保节点编码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "核保节点";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		//modify by ZhangC 20150113 
		iArray[i] = new Array();
		iArray[i][0] = "中支公司核保意见代码";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "中支公司核保意见";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "中支公司核保描述";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		if (tActivityID == "0800100003" || tActivityID == "0800100004") {
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保意见代码";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保意见";
			iArray[i][1] = "17px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保描述";
			iArray[i][1] = "20px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保意见代码";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保意见";
			iArray[i][1] = "17px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保描述";
			iArray[i][1] = "20px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
		} else {
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保意见代码";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保意见";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "分公司核保描述";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保意见代码";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保意见";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "总公司核保描述";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "附件ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "附件(双击下载)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "downFile";
		
		
		PersonUWGrid = new MulLineEnter("fm2", "PersonUWGrid");
		PersonUWGrid.mulLineCount = 0;
		PersonUWGrid.displayTitle = 1;
		PersonUWGrid.locked = 0;
		PersonUWGrid.canSel = 1;
		PersonUWGrid.canChk = 0;
		PersonUWGrid.hiddenPlus = 1;
		PersonUWGrid.hiddenSubtraction = 1;
		PersonUWGrid.selBoxEventFuncName = "showPersonUWDetail";
		PersonUWGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("初始化PersonUWGrid时出错："+ ex);
	}
}
</script>
