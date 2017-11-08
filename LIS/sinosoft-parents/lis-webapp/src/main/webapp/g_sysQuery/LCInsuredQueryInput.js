/***************************************************************
 * <p>ProName：LCInsuredQueryInput.js</p>
 * <p>Title：被保险人查询</p>
 * <p>Description：被保险人查询</p>
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
 * 查询按钮
 */
function queryClick() {
	
	if ((fm.GrpContNo.value==null || fm.GrpContNo.value=="")
		 && (fm.CustomerName.value==null || fm.CustomerName.value=="")
		 	&& (fm.IDNo.value==null || fm.IDNo.value=="")) {
		alert("查询条件中[保单号]、[被保人姓名]与[证件号码]不可同时为空！");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql");
	
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);	
	
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);	
	tSQLInfo.addSubPara(mManageCom);
						
	turnPage1.queryModal(tSQLInfo.getString(),LCInsuredListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}

/**
 * 展示选中的赔案赔付明细
 */
function showSelectDetail () {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择一条被保险人信息");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
	var tMainCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,17);
	var tMainFlag = LCInsuredListGrid.getRowColData(tSelNo,6);
	var tContNo = LCInsuredListGrid.getRowColData(tSelNo,5);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql1");	
	if (tMainFlag!=null && tMainFlag!="本人") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tMainCustomerNo);			
	} else {
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara("");		
	}
	tSQLInfo.addSubPara(tContNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),MainInsuredListGrid, 2);	
}

//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "投保人编码^|投保人名称^|投保单号^|保单号^|个人保单号^|与主被保险人关系^|"
							+"被保人客户号^|被保人姓名^|性别^|出生日期^|证件类型^|证件号码^|"
							+"责任起期^|责任止期^|保单状态^|承保机构";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql");
	
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);	
	
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

//查询功能
/**
 * 保单查询
 */
function showInsuredLCPol() {	
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择一条被保险人信息");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
		
	var strUrl="../g_claim/LLClaimQueryPolicyMain.jsp?CustomerNo="+tCustomerNo+"&Mode=2";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"保单查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * 保全查询
 */
function showInsuredLCEdor() {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择一条被保险人信息");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
		
	var strUrl="../g_claim/LLClaimQueryEdorMain.jsp?CustomerNo="+tCustomerNo+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"保全查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * 既往赔案查询
 */
function showOldCase() {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("请选择一条被保险人信息");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
	
	var strUrl="../g_claim/LLClaimLastCaseMain.jsp?CustomerNo="+tCustomerNo+"&Mode=0";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
