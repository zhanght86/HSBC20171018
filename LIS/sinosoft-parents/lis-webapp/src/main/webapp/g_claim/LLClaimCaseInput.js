/***************************************************************
 * <p>ProName：LLClaimCaseInput.js</p>
 * <p>Title：普通案件录入</p>
 * <p>Description：普通案件录入</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
/**
 * 申请交接流转号
 */
function applyPageNo() {
	
	var strUrl="./LLClaimHandAppMain.jsp";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"交接流转号查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 保存受理信息
 */
function saveAccept() {
	
	if(!checkAcceptInfo()) {
		return false;
	}
		
	fm.Operate.value="ACCEPTINSERT";
	submitForm();	
}
/**
 * 删除受理信息
 */
function deleteAccept() {
	
	if (fm.GrpRgtNo.value==null || fm.GrpRgtNo.value=="") {
		alert("请先保存受理信息！");
		return false;
	}
		
	fm.Operate.value="ACCEPTDELETE";
	submitForm();	
}
/**
 * 新增客户
 */
function addCustomer() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
		return false;
	}	
	fm.Operate.value="CUSTOMERINSERT";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * 修改客户
 */
function modifyCustomer() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRegisterNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询出险人信息！");
		return;
	}

	tPageNo = turnPage1.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;		
	
	fm.Operate.value="CUSTOMERUPDATE";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();
}
/**
 * 删除客户
 */
function deleteCustomer() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tRegisterNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询出险人信息！");
		return;
	}
	fm.RegisterNo.value = tRegisterNo;
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);
	fm.CustomerNo.value = tCustomerNo;
	if(confirm("是否确认删除选中的出险人信息？")) {
		fm.Operate.value="CUSTOMERDELETE";
		submitForm();		
	}

}
/**
 * 未受理客户信息
 */
function noAcceptInfo() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
		return false;
	}
	var strUrl="./LLClaimNoAcceptMain.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode="+mMode;
	window.open(strUrl,"未受理客户",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

/**
 * 删除所有受理客户信息
 */
function deleteAllAccept() {
	
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("请先保存理赔受理信息");
		return false;
	}	
	
	if (confirm("是否删除所有已受理客户？")) {
		fm.Operate.value="CUSTOMERDELETEALL";
		submitForm();		
	}	
}

/**
 * 新增事件
 */
function addCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}	
	fm.CaseSource.value = "01";
	fm.Operate.value="CASEINSERT";
	
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();
}

/**
 * 修改事件
 */
function modifyCase() {
	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件信息");
		return false;
	}
	var tRegisterNo = EventlistGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询事件信息！");
		return;
	}	
	
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo+1;	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value="CASEUPDATE";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();
}
/**
 * 删除事件
 */
function deleteCase() {
	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件信息");
		return false;
	}
	var tRegisterNo = EventlistGrid.getRowColData(tSelNo,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("请先查询事件信息！");
		return;
	}
	if (confirm("确定删除该条事件？")) {
		fm.Operate.value="CASEDELETE";
		submitForm();		
	}	
}
/**
 * 非医疗账单
 */
function easyBill() {

	var tSelNo = EventlistGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条事件信息");
		return false;
	}
	var strUrl="./LLClaimNoMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+fm.CaseNo.value+"&CustomerNo="+fm.CustomerNo.value+"&AccidentDate="+fm.AccDate.value+"&Mode="+mMode;

	var tWidth=window.screen.availWidth;
	var tHeight=500;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"非医疗账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 医疗账单
 */
function standBill() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人信息");
		return false;
	}
	var tCaeSelNo=EventlistGrid.getSelNo()-1;
	var CaseSource="";
	var tCaseNo= "";	
	if(tCaeSelNo<0){
		CaseSource="02";//账单生成
	}else{
		CaseSource=fm.CaseSource.value;
		tCaseNo= EventlistGrid.getRowColData(tCaeSelNo,2);	
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql26");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	var tArr = easyExecSql(tSQLInfo.getString());
	var busson = fm.RegisterNo.value;
	var tBussType = "G_CM";
	var tSubType = "23003";
 	
 	if(tArr== null || tArr == "") {
 		
 	}else{
	 	busson = tArr[0][0];
		tBussType = tArr[0][1];
		tSubType = tArr[0][2];
 	}
	
	var strUrl="./LLClaimMedicalMain.jsp?RgtNo="+fm.RegisterNo.value+"&CaseNo="+tCaseNo+"&CustomerNo="+fm.CustomerNo.value+"&CaseSource="+CaseSource+"&Mode=0"
	+"&BussNo="+busson+"&BussType="+tBussType+"&SubType=23003";
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //获得窗口的垂直位置;
	var tLeft=0;        //获得窗口的水平位置;	
	window.open(strUrl,"医疗账单",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
/**
 * 取消事件责任关联
 */
function offAssociate() {
	
	var rowNum = OnEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OnEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("请至少选择一条事件责任信息");
		return false;
	}

/*		
	var tSelNo = OnEventDutyListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条出险人事件已关联责任信息");
		return false;
	}
	fm.PolNo.value = OnEventDutyListGrid.getRowColData(tSelNo,2);
	fm.DutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,5);
	fm.GetDutyCode.value = OnEventDutyListGrid.getRowColData(tSelNo,7);
*/
	fm.Operate.value="DUTYOFF";
	submitForm();		
}

/**
 * 关联事件责任
 */
function onAssociate() {
	
	var rowNum = OffEventDutyListGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(OffEventDutyListGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("请至少选择一条事件责任信息");
		return false;
	}
		
	fm.Operate.value="DUTYON";
	submitForm();		
}

/**
 * 立案完毕
 */
function caseOver() {

	if(confirm("是否确认立案完毕？")) {
		
		if(!checkBeforeOver()) {
			return false;
		}		
		fm.Operate.value="RGTOVER";
		submitForm();
	}
}
/**
 * 返回
 */
function goBack() {
	
	//特殊案件返回至特殊立案申请页面，返回前校验
	if(mMode=="2" || mMode=="1") {
		
		//返回前校验
		//window.location.href="./LLClaimSpecialCaseInput.jsp?GrpRgtNo="+fm.GrpRgtNo.value;
		top.close();
	}else {
		window.location.href="./LLClaimCaseAppInput.jsp";
	}
}
/**
 * 客户信息批量导入
 */
function customerImport() {
	
	if (fm.GrpRgtNo.value==null || fm.GrpRgtNo.value=="") {
		alert("请先保存团体立案信息！");
		return false;
	}
	var strUrl="./LLClaimCustImportMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode=1";
	var tWidth=1200;
	var tHeight=800;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"客户信息批量导入",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, tGrpRegisterNo,tRegisterNo,tCaseNo) {
	
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
		 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		 var  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if (fm.Operate.value=="ACCEPTINSERT") {
			mGrpRegisterNo = tGrpRegisterNo;			
			window.location.href="LLClaimCaseInput.jsp?GrpRgtNo="+tGrpRegisterNo+"&Mode=0";
		} else if (fm.Operate.value=="ACCEPTDELETE") {
			window.location.href="LLClaimCaseInput.jsp?Mode=0";
		}
		if (fm.Operate.value=="RGTOVER") {
			goBack();
		} else {
			
			if ((fm.Operate.value).indexOf("CASE")>=0) {

				initCaseInfo();				
				queryCustomerCaseList();
				//修改后仍选择当前行
				if (fm.Operate.value=="CASEUPDATE") {
					
					for (var i=0;i<fm.PageIndex.value;i++) {
						turnPage2.nextPage();
					}
					EventlistGrid.radioSel(fm.SelNo.value);
					showSelectCase();
				} else {
					initCaseInfo();
					initEventlistGrid();
				}
			} else if ((fm.Operate.value).indexOf("CUSTOMER")>=0) {
				initCustomerInfo();
				queryCustomerList();
				if (fm.Operate.value=="CUSTOMERUPDATE") {
					setSelRow(CustomerListGrid,turnPage1);
					showSelectCustomer();					
				} else {
					initCaseInfo();
					initEventlistGrid();
				}
				
			} else if ((fm.Operate.value).indexOf("DUTY")>=0) {
				queryCaseDutyInfo();
			} else {
				initForm();
			}			
		}
	
	}	
}
/**
 * 返回交接流转号
 */
function afterAppPageNo(tSelectResult) {
	
	fm.all('PageNo').value = "";
	fm.all('PageNo').value = tSelectResult[0];
}

/**
 * 正则校验录入数字
 */
function checkInput(tObject) {
	
		tValue = tObject.value;
		if(((!/^[0-9]\d*$/.test(tValue)) && (!tValue==""))){
			
			alert("请输入正整数！") ;
			tObject.className = "warn" ;
			tObject.focus();
			tObject.value="";		
		}	
}
/**
 * 正则校验录入金额
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(tValue)) && (!tValue==""))){
		
		alert("请输入正确的金额！") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}
//展示提示信息
function showWarnInfo() {
		
	alert('请录入[投保人名称]后回车操作(支持模糊查询)！');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
//展示提示信息
function showWarnInfo1() {
		
	alert('请录入[主要诊断名称]后回车操作(支持模糊查询)！');
	fm.AccResult1Name.focus();
	fm.AccResult1Name.style.background='#ffb900';
	fm.AccResult1.value = "";
}