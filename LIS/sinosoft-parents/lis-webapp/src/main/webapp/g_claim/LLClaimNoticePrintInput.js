/***************************************************************
 * <p>ProName：LLClaimNoticePrintInput.js</p>
 * <p>Title：理赔通知书打印</p>
 * <p>Description：理赔通知书打印</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

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
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
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
	
		window.location = "../common/jsp/download.jsp?FilePath="+filepath+"&FileName="+tfileName;
	}	
}

/*
 *批次查询
 */
function easyQueryClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoticePrintSql");
	tSQLInfo.setSqlId("LLClaimNoticePrintSql");
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.MngCom.value);
	tSQLInfo.addSubPara(fm.RgtdateStart.value);
	tSQLInfo.addSubPara(fm.RgtdateEnd.value);
	turnPage1.queryModal(tSQLInfo.getString(), ClaimGrid,"2");
	
}
function QueryRgtClick( grpRgtNo){
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimNoticePrintSql");
	tSQLInfo.setSqlId("LLClaimNoticePrintSql1");
	if(grpRgtNo==null){
		
	tSQLInfo.addSubPara("");
	}else {
		
	tSQLInfo.addSubPara(grpRgtNo);
	}
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.GrpName1.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.MngCom1.value);
	tSQLInfo.addSubPara(fm.RgtNo1.value);
	tSQLInfo.addSubPara(fm.EndDateStart.value);
	tSQLInfo.addSubPara(fm.EndDateEnd.value);
	turnPage2.queryModal(tSQLInfo.getString(), CaseGrid,"2");
	
}
function queryCustomer(){
	
	var i = ClaimGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
	i = ClaimGrid.getSelNo()-1;
	
	fm.GrpRgtNo.value = ClaimGrid.getRowColData(i,1);
	
	QueryRgtClick(fm.GrpRgtNo.value);
	
}
function onSelSelected(){
	
	var i = CaseGrid.getSelNo();
   if(i < 1)
    {
        alert("请选中一行记录！");
        return false;
     }
	i = CaseGrid.getSelNo()-1;
	fm.RgtNo.value=CaseGrid.getRowColData(i,2);
	
}
/**
	*理赔批次结案通知书打印
	*/
function GrpGetPrint(){
	
	var i = ClaimGrid.getSelNo();
	if(i < 1) {
			
		alert("请选中一行批次信息！");
		return false;
     }
    i = ClaimGrid.getSelNo()-1;
    fm.GrpRgtNo.value=ClaimGrid.getRowColData(i,1);    
    fm.Operate.value="BATCHENDPRINT";
    submitForm();
}

/**
	*个人给付批单打印
	*/
function PerGetPrint(){
		
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	var tRowNum = 0;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
			tRowNum++;
			
		}
	}
	if (!flag) {
		alert("请至少选择一条个人赔案信息");
		return false;
	} else if (tRowNum>1) {
		alert("只能选择一个赔案进行【个人结案通知书打印】");
		return false;		
	}

	fm.Operate.value="PERENDPRINT";
	submitForm();
}
/**
	*个人给付批单批量打印
	*/
function PerGetPrint1(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;			
		}
	}
	if (!flag) {
		
		alert("请至少选择一条事件责任信息");
		return false;
	}

	fm.Operate.value="PERENDPRINT";
	submitForm();
}
/**
	*个人密封函打印
	*/
function PerSealPrint(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	var tRowNum = 0;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
			tRowNum++;
			
		}
	}
	if (!flag) {
		alert("请至少选择一条事件责任信息");
		return false;
	} else if (tRowNum>1) {
		alert("只能选择一个赔案进行【个人密封函打印】");
		return false;		
	}
	fm.Operate.value="PERSEALPRINT";
	submitForm();	
}
/**
	*个人密封函批量打印
	*/
function AllSealPrint(){
	
	var rowNum = CaseGrid.mulLineCount;
	var flag = false;
	
	for(var i=0;i<rowNum;i++) {
		
		if(CaseGrid.getChkNo(i)) {
			flag = true;
		}
	}
	if (!flag) {
		alert("请至少选择一条事件责任信息");
		return false;
	}
	fm.Operate.value="PERSEALPRINT";
	submitForm();
	
}