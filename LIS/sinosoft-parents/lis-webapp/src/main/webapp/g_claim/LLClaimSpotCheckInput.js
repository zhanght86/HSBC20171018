/***************************************************************
 * <p>ProName：LLClaimSpotCheckInput.js</p>
 * <p>Title：理赔抽检</p>
 * <p>Description：理赔抽检</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

/**
 * 查询理赔规则
 */
function queryClaimRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql");
	
	tSQLInfo.addSubPara(fm.QueryUserCode.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryRealPay.value);
	tSQLInfo.addSubPara(fm.QueryGiveType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimRuleListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}
}
/**
 * 展示规则明细
 */
function showClaimRuleDetail() {
	
	var i = LLClaimRuleListGrid.getSelNo()-1;
	var tRuleNo = LLClaimRuleListGrid.getRowColData(i,1);
	if (tRuleNo==null || tRuleNo=="") {
		alert("请先查询！");
		return;
	}
	
	fm.RuleNo.value = tRuleNo;
	fm.CheckType.value = LLClaimRuleListGrid.getRowColData(i,2);
	fm.CheckTypeName.value = LLClaimRuleListGrid.getRowColData(i,3);
	fm.CheckUserCode.value = LLClaimRuleListGrid.getRowColData(i,6);
	fm.CheckUserName.value = LLClaimRuleListGrid.getRowColData(i,7);
	fm.CheckManageCom.value = LLClaimRuleListGrid.getRowColData(i,4);
	fm.CheckManageComName.value = LLClaimRuleListGrid.getRowColData(i,5);	
	fm.CheckGrpContNo.value = LLClaimRuleListGrid.getRowColData(i,8);
	fm.CheckAppntNo.value = LLClaimRuleListGrid.getRowColData(i,9);
	fm.CheckAppntName.value = LLClaimRuleListGrid.getRowColData(i,10);
	fm.CheckRiskCode.value = LLClaimRuleListGrid.getRowColData(i,11);
	fm.CheckRiskName.value = LLClaimRuleListGrid.getRowColData(i,12);
	fm.CheckRate.value = LLClaimRuleListGrid.getRowColData(i,13);
	fm.CheckMoney.value = LLClaimRuleListGrid.getRowColData(i,14);
	fm.CheckGiveType.value = LLClaimRuleListGrid.getRowColData(i,15);
	fm.CheckGiveTypeName.value = LLClaimRuleListGrid.getRowColData(i,16);
	fm.CheckStartDate.value = LLClaimRuleListGrid.getRowColData(i,17);
	fm.CheckEndDate.value = LLClaimRuleListGrid.getRowColData(i,18);
	
	fm.QueryBnfFlag.value = LLClaimRuleListGrid.getRowColData(i,19);;	
	fm.QueryBnfFlagName.value = LLClaimRuleListGrid.getRowColData(i,20);	
	
}

/**
 * 新增规则
 */
function addRule() {
	
	//系统的校验方法
	if (!verifyInput2()) {
		return false;
	}
	
	//校验抽检规则是否存在
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql2");
	tSQLInfo.addSubPara(fm.CheckManageCom.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length>0) {
		alert("该分公司下，抽检规则已维护，只能修改！");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();		
}
/**
 * 修改规则
 */
function modifyRule() {
	
	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条抽检规则！");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="UPDATE";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();	
//	fm.action="./LLClaimSpotCheckSave.jsp";
}
/**
 * 删除规则
 */
function delteRule() {

	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请先选择一条抽检规则！");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="DELETE";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();
}
/**
 * 重置规则
 */
function initRule() {
	initDetailInfo();
}

//展示提示信息
function showWarnInfo() {
		
	alert('请录入[投保人名称]后回车操作(支持模糊查询)！');
	fm.CheckAppntName.focus();
	fm.CheckAppntName.style.background = "#ffb900";
}

//查询投保人信息，支持左右模糊查询
function QueryOnKeyDown(tObject) {
	
	var keycode = event.keyCode;
	//回车的ascii码是13	
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	if (tObjectName=="CheckAppntName") {
		
		var tAppntName = tObjectValue;	
			
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
		tSQLInfo.setSqlId("LLClaimSpotCheckSql1");
		tSQLInfo.addSubPara(tAppntName);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(tAppntName);
		tSQLInfo.addSubPara(mManageCom);				
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("未查询到符合条件的查询结果！");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.CheckAppntNo.value = tArr[0][1];
				fm.CheckAppntName.value = tArr[0][2];
			} else {
				showAppntList();			
			}
		}		
	}
}
/**
 * 打开查询的系统团体投保人选择页面
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.CheckAppntName.value+"&ContType=01";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"用户信息查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * 获得查询的系统投保人
 */
function afterQueryAppnt(tQueryResult) {
	
	fm.all('CheckAppntNo').value = tQueryResult[1];
	fm.all('CheckAppntName').value = tQueryResult[2];
	self.focus();
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "抽检规则序号^|抽检类型编码^|抽检类型^|抽检机构^|抽检机构名称^|抽检用户^|保单号^|抽检用户名^|"
							+"投保人编码^|投保人名称^|险种编码^|险种名称^|抽检比例^|赔付金额^|给付类型编码^|给付方式^|抽检起期^|抽检止期^|是否受益人编码^|是否与受益人一致";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql");
	
	tSQLInfo.addSubPara(fm.QueryUserCode.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryRealPay.value);
	tSQLInfo.addSubPara(fm.QueryGiveType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
