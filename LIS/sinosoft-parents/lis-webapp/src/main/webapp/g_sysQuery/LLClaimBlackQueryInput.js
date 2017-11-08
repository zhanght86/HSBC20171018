/***************************************************************
 * <p>ProName：LLClaimLastCaseInput.js</p>
 * <p>Title：赔案查询</p>
 * <p>Description：赔案查询</p>
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
turnPage2.pageLineNum = 100;

/**
 * 查询赔案
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackQuerySql");
	tSQLInfo.setSqlId("LLClaimBlackQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
						
	turnPage1.queryModal(tSQLInfo.getString(),BlackListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("未查询到符合条件的查询结果！");
		return false;
	}	
}
/**
 * 返回
 */
function goBack() {
	top.close();
}
/**
 * 既往赔案查询
 */
function showOldCase() {
	
	var tSelNo = BlackListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选择一条记录");
		return false;
	}
	var tCustomerNo =BlackListGrid.getRowColData(tSelNo,3);
	var strUrl="../g_claim/LLClaimLastCaseMain.jsp?CustomerNo="+tCustomerNo+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //获得窗口的垂直位置;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //获得窗口的水平位置;	
	window.open(strUrl,"赔案查询",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
//数据导出
function exportData() {
	
	if (!confirm("确认要导出数据？")) {
		return false;
	}
	
	//报表标题
	var tTitle = "保单号^|投保人名称^|被保险人编码^|被保险人姓名^|"
							+"性别^|出生日期^|证件类型^|证件号码^|客户状态^|业务类型^|"
							+"业务号码^|黑名单操作人^|黑名单设置原因^|黑名单设置备注^|设置日期^|";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBlackQuerySql");
	tSQLInfo.setSqlId("LLClaimBlackQuerySql");
	
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}