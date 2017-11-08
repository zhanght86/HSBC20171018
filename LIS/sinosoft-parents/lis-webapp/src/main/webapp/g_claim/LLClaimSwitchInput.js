/***************************************************************
 * <p>ProName：LLClaimBenefitInput.jsp</p>
 * <p>Title：暂停或启动案件</p>
 * <p>Description：暂停或启动案件</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//系统使用
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询批次下所有个人案件
 */
function queryClaimList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSwitchSql");
	tSQLInfo.setSqlId("LLClaimSwitchSql");
	tSQLInfo.addSubPara(mRgtNo);
	tSQLInfo.addSubPara(mCustomerNo);
	
	turnPage1.queryModal(tSQLInfo.getString(),ClaimCaseGrid,2);	
}
/**
 * 查询选中的个人案件操作轨迹
 */
function showSelectTrace() {
	
	var i = ClaimCaseGrid.getSelNo()-1;		
	var tRgtNo = ClaimCaseGrid.getRowColData(i,1);
	fm.RgtNo.value = tRgtNo;
	var tCustomerNo = ClaimCaseGrid.getRowColData(i,2);
	fm.CustomerNo.value = tCustomerNo;
	var tRgtConClusion = ClaimCaseGrid.getRowColData(i,8);
	var tPauseReason = document.getElementById("Pause");
	var tDivInfo = document.getElementById("divRemarkInfo");
	var tRemark = document.getElementById("Remark");
	if (tRgtConClusion=="6") {//案件暂停
		
		tDivInfo.style.display = "";
		tPauseReason.style.display = "none";
		tRemark.style.display = "";
		fm.RgtPause.style.display = "none";
		fm.RgtOpen.style.display = "";		
	} else {
		
		tDivInfo.style.display = "";
		tPauseReason.style.display = "";
		tRemark.style.display = "";
		fm.RgtPause.style.display = "";
		fm.RgtOpen.style.display = "none";			
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSwitchSql");
	tSQLInfo.setSqlId("LLClaimSwitchSql1");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);	
	
	turnPage2.queryModal(tSQLInfo.getString(),ClaimCaseTraceGrid,2);	
		
}

//暂停案件
function pauseRgt(){
	
	if(!notNull(fm.PauseReason,"案件暂停原因")){return false;};
	if(!notNull(fm.ReasonDesc,"原因描述")){return false;};
	fm.Operate.value="PAUSE";
	submitForm();	
}

//启动案件
function openRgt(){
	
	if(!notNull(fm.ReasonDesc,"原因描述")){return false;};	
	fm.Operate.value="OPEN";
	submitForm();	
}
//关闭操作
function closeClick() {
	top.close();
	top.opener.afterSwitchCase();
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
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		initForm();
	}	
}
/**
 * 校验非空要素，并聚焦
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"不可为空，请录入！");
		if (tObject.className=="codeno") {
			tObject.className="warnno";
		}else {
			tObject.className="warn";
		}
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}