/***************************************************************
 * <p>ProName：LSQuotQueryCustomerInput.js</p>
 * <p>Title：查询准客户名称</p>
 * <p>Description：查询准客户名称</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * 查询准客户名
 */
function queryCustomer() {
	if(_DBT==_DBM){
		//关联其他准客户时，查询询价申请机构
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotQueryCustomerSql");
		tSQLInfo.setSqlId("LSQuotQueryCustomer2");
		tSQLInfo.addSubPara(tQuotNo);
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("查询询价申请机构失败！");
			return false;
		} else {
			var tManageComTemp = tArr[0][0];
		}
		//查询准客户名称
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotQueryCustomerSql");
		tSQLInfo.setSqlId("LSQuotQueryCustomer3");
		tSQLInfo.addSubPara(tOperator);// =
		if (tMark == 'Page' || tMark == 'Enter') {
			tSQLInfo.addSubPara(tManageCom);
		}  else {
			tSQLInfo.addSubPara(tManageComTemp);
		}
		tSQLInfo.addSubPara(tOperator);// !=
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
		if (tMark == 'Page' || tMark == 'Enter') {
			tSQLInfo.addSubPara(2);
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(1);
			tSQLInfo.addSubPara(tPreCustomerNo1);
			tSQLInfo.addSubPara(tPreCustomerNo1);
		}
		var customerno=fm.PreCustomerNo.value;
		var precustomerno=queryUpCustomer(customerno);
		if(precustomerno=="" || precustomerno==null){
		}else{
			tSQLInfo.addSubPara(precustomerno);
		}
		initRelaCustGrid();
		turnPage1.queryModal(tSQLInfo.getString(), RelaCustGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
		
	}else if(_DBT==_DBO){
		//关联其他准客户时，查询询价申请机构
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotQueryCustomerSql");
		tSQLInfo.setSqlId("LSQuotQueryCustomer2");
		tSQLInfo.addSubPara(tQuotNo);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("查询询价申请机构失败！");
			return false;
		} else {
			var tManageComTemp = tArr[0][0];
		}
		
		//查询准客户名称
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotQueryCustomerSql");
		tSQLInfo.setSqlId("LSQuotQueryCustomer1");
		tSQLInfo.addSubPara(tOperator);// =
		
		if (tMark == 'Page' || tMark == 'Enter') {
			tSQLInfo.addSubPara(tManageCom);
		}  else {
			tSQLInfo.addSubPara(tManageComTemp);
		}
		
		tSQLInfo.addSubPara(tOperator);// !=
		tSQLInfo.addSubPara(fm.PreCustomerNo.value);
		tSQLInfo.addSubPara(fm.PreCustomerName.value);
		
		if (tMark == 'Page' || tMark == 'Enter') {
			tSQLInfo.addSubPara(2);
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara("");
		} else {
			tSQLInfo.addSubPara(1);
			tSQLInfo.addSubPara(tPreCustomerNo1);
			tSQLInfo.addSubPara(tPreCustomerNo1);
		}
		
	
		initRelaCustGrid();
		turnPage1.queryModal(tSQLInfo.getString(), RelaCustGrid, 1, 1);
		
		if (!turnPage1.strQueryResult) {
			alert("未查询到符合条件的查询结果！");
		}
	}
}

function queryUpCustomer(customerno){
	tPreSQLInfo = new SqlClass();
	tPreSQLInfo.setResourceName("g_quot.LSQuotQueryCustomerSql");
	tPreSQLInfo.setSqlId("LSQuotQueryCustomer4");
	tPreSQLInfo.addSubPara(customerno); 
 
	var tmBasicArr = easyExecSql(tPreSQLInfo.getString(), 1, 0, 1);
	var mprecustomerno =null;
	if(tmBasicArr == null){
		return ""; 
	}else{
		if ( tmBasicArr[0][0] ==null || tmBasicArr[0][0] =="" ) {
			   return ""; 
			}else{
				mprecustomerno =tmBasicArr[0][0];
			}
	}
	
	return mprecustomerno+","+ queryUpCustomer(mprecustomerno);
}
	  



/***
 * 返回准客户名
 */
function returnCustomer() {
	
	var tSelNo = RelaCustGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("请选中一条准客户信息记录！");
		return false;
	}
	var tPreCustomerNo = RelaCustGrid.getRowColData(tSelNo,1);
	var tPreCustomerName = RelaCustGrid.getRowColData(tSelNo,2);
	
	if (tMark == "Page" || tMark == "Enter") {
		
		var tIDType = RelaCustGrid.getRowColData(tSelNo,4);
		var tIDTypeName = RelaCustGrid.getRowColData(tSelNo,5);
		var tIDNo = RelaCustGrid.getRowColData(tSelNo,6);
		var tGrpNature = RelaCustGrid.getRowColData(tSelNo,7);
		var tGrpNatureName = RelaCustGrid.getRowColData(tSelNo,8);
		var tBusiCategory = RelaCustGrid.getRowColData(tSelNo,9);
		var tBusiCategoryName = RelaCustGrid.getRowColData(tSelNo,10);
		var tAddress = RelaCustGrid.getRowColData(tSelNo,11);
		var tPreSumPrem = RelaCustGrid.getRowColData(tSelNo,12);
		var tCustomerIntro = RelaCustGrid.getRowColData(tSelNo,13);
		var tSaleChannel = RelaCustGrid.getRowColData(tSelNo,14);
		var tSaleChannelName = RelaCustGrid.getRowColData(tSelNo,15);
		
		top.opener.fm2.PreCustomerNo.value = tPreCustomerNo;
		top.opener.fm2.PreCustomerName.value = tPreCustomerName;
		top.opener.fm2.IDType.value = tIDType;
		top.opener.fm2.IDTypeName.value = tIDTypeName;
		top.opener.fm2.IDNo.value = tIDNo;
		top.opener.fm2.GrpNature.value = tGrpNature;
		top.opener.fm2.GrpNatureName.value = tGrpNatureName;
		top.opener.fm2.BusiCategory.value = tBusiCategory;
		top.opener.fm2.BusiCategoryName.value = tBusiCategoryName;
		top.opener.fm2.Address.value = tAddress;
		top.opener.fm2.PrePrem.value = tPreSumPrem;
		top.opener.fm2.CustomerIntro.value = tCustomerIntro;
		top.opener.fm2.SaleChannel.value = tSaleChannel;
		top.opener.fm2.SaleChannelName.value = tSaleChannelName;
		
		top.opener.showDivAgencyInfo(tSaleChannel);
		
	} else {
		top.opener.RelaCustListGrid.setRowColData(tMulLineNo,1,tPreCustomerNo);
		top.opener.RelaCustListGrid.setRowColData(tMulLineNo,2,tPreCustomerName);
		top.opener.checkPerCustomerNoChange(tPreCustomerNo1);
	}
	top.close();
}

