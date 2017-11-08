/***************************************************************
 * <p>ProName：LLClaimNoAcceptInput.js</p>
 * <p>Title：受益人客户查询</p>
 * <p>Description：受益人客户查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();
/**
 * 查询受益人信息
 */
function queryBenefitInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimBenefitSql");
	tSQLInfo.setSqlId("LLClaimBenefitSql1");
	tSQLInfo.addSubPara(mRgtNo);
	tSQLInfo.addSubPara(mCustomerNo);		
	
	turnPage1.pageLineNum = 100;
	turnPage1.queryModal(tSQLInfo.getString(),BenefitListGrid,2);	
}

/**
 * 查询选中的受益人信息
 */
function showDetail() {
	
	var i = BenefitListGrid.getSelNo()-1;
	var tSerialNo = BenefitListGrid.getRowColData(i, 1);
	
	fm.BnfType.value = BenefitListGrid.getRowColData(i, 3);
	fm.BnfTypeName.value = BenefitListGrid.getRowColData(i, 4);
	fm.BnfName.value = BenefitListGrid.getRowColData(i, 7);
	fm.BnfGender.value = BenefitListGrid.getRowColData(i, 8);
	fm.BnfGenderName.value = BenefitListGrid.getRowColData(i, 9);
	fm.BnfIDType.value = BenefitListGrid.getRowColData(i, 10);
	fm.BnfIDTypeName.value = BenefitListGrid.getRowColData(i, 11);
	fm.BnfIDNo.value = BenefitListGrid.getRowColData(i, 12);	
	fm.BnfBirthday.value = BenefitListGrid.getRowColData(i, 13);	
	fm.BnfRelation.value = BenefitListGrid.getRowColData(i, 14);
	fm.BnfRelationName.value = BenefitListGrid.getRowColData(i, 15);
	fm.BnfLot.value = BenefitListGrid.getRowColData(i, 16);
	fm.BnfMoney.value = BenefitListGrid.getRowColData(i, 17);
	fm.BankCode.value = BenefitListGrid.getRowColData(i, 18);	
	fm.BankCodeName.value = BenefitListGrid.getRowColData(i, 19);
	fm.Province.value = BenefitListGrid.getRowColData(i, 20);
	fm.ProvinceName.value = BenefitListGrid.getRowColData(i, 21);
	fm.City.value = BenefitListGrid.getRowColData(i, 22);
	fm.CityName.value = BenefitListGrid.getRowColData(i, 23);
	fm.AccNo.value = BenefitListGrid.getRowColData(i, 24);
	fm.AccName.value = BenefitListGrid.getRowColData(i, 25);	
	fm.PayType.value = "03";
	fm.PayTypeName.value = "银行转账";
}