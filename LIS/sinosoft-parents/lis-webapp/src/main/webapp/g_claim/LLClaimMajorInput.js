/***************************************************************
 * <p>ProName：LLClaimMajorInput.js</p>
 * <p>Title：重大案件上报/录入</p>
 * <p>Description：重大案件上报/录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 初始化页面展示信息
 */
function initPageInfo() {
	
		var tArr;
		
		//查询是否存在已录入的上报信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());

		//判断是否查询成功
		if (tArr==null || tArr.length==0) {
			fm.reportMajor.style.display = "";			
			/*document.getElementById("InputRemarks").setAttribute('readOnly',false);		*/
			document.getElementById("InputRemarks").readOnly=false;		
			return;
		} else {
			fm.InputOperator.value = tArr[0][0];			
			fm.InputDate.value = tArr[0][1];
			fm.InputCom.value = tArr[0][2];
			fm.InputComName.value = tArr[0][3];
			fm.InputRemarks.value = tArr[0][4];
		/*	document.getElementById("InputRemarks").setAttribute('readOnly',true);*/
			document.getElementById("InputRemarks").readOnly=true;		
			fm.reportMajor.style.display = "none";			
		}
		//查询是否存在已录入的分公司回复信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql1");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {
			document.getElementById("divReturnButton").style.display="";
			document.getElementById("divInputRepInfo").style.display="none";			
			return;
		} else {
			document.getElementById("divReturnButton").style.display="";
			document.getElementById("divInputRepInfo").style.display="";			
			document.getElementById("divBranchInfo").style.display="";
			fm.InputRepOperator.value = tArr[0][0];
			fm.InputRepDate.value = tArr[0][1];
			fm.InputRepCom.value = tArr[0][2];
			fm.InputRepComName.value = tArr[0][3];
			fm.InputRepRemarks.value = tArr[0][4];			
		}
		//查询是否存在已录入的总公司回复信息
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimMajorSql");
		tSQLInfo.setSqlId("LLClaimMajorSql2");
		tSQLInfo.addSubPara(mRptNo);
		tSQLInfo.addSubPara(mCustomerNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//判断是否查询成功
		if (tArr==null || tArr.length==0) {

			document.getElementById("divBranchInfo1").style.display="none";
			return;
		} else {

			document.getElementById("divBranchInfo1").style.display="";
			fm.InputRepOperator1.value = tArr[0][0];
			fm.InputRepDate1.value = tArr[0][1];
			fm.InputRepCom1.value = tArr[0][2];
			fm.InputRepComName1.value = tArr[0][3];
			fm.InputRepRemarks1.value = tArr[0][4];			
		}	
}

/**
 * 重大案件上报
 */
function majorReport() {
	
	var tRemark = fm.InputRemarks.value;
	if (tRemark==null || tRemark.length==0) {
		alert("机构意见不可为空！");
		fm.InputRepRemarks.focus();
		return false;
	}
	fm.Operate.value = "INSERT";
	submitForm();
}

/**
 * 打印重大案件
 */
function casePrint() {
			
	
	fm.Operate.value="PRINT";
	submitForm();	
}

/**
 * 重大案件审批确认
 */
function majorApprove() {
	
}

/**
 * 提交数据
 */
function submitForm() {
	
	var showStr = "正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * 提交数据后返回操作
 */
function afterSubmit(FlagStr, content, filepath, tfileName) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
				
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	
		if (fm.Operate.value == "PRINT") {
			window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
		}	else {
			initForm();
		}
		
	}	
}

/**
 * 下拉框选择以后调用
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
