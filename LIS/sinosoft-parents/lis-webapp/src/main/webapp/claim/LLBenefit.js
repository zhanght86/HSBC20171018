var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//��ʼ����ѯ
function initQuery(){
	//��ѯ���⸶������������
	queryPaidDuty();
}
//��ѯ���⸶������������
function queryPaidDuty(){
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLBenefitInputSql");
	mySql.setSqlId("LLBenefitSql1");
	//mySql.addSubPara(fm.ContNo.value);
	mySql.addSubPara(fm.CustNo.value);
	//mySql.addSubPara(fm.ContNo.value);
	mySql.addSubPara(fm.CustNo.value);
	turnPage1.queryModal(mySql.getString(),PaidDutyGrid);
	
	
	
	
}
//��ѯ���⸶�˵�����
function getPaidDutyGrid(){
	//alert(1);
	var i = PaidDutyGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tDutycode = PaidDutyGrid.getRowColData(i,10);
        var tGetDutyCode = PaidDutyGrid.getRowColData(i,11);
        fm.ContNo.value = PaidDutyGrid.getRowColData(i,1);
        mySql = new SqlClass();
        mySql.setResourceName("claim.LLBenefitInputSql");
        mySql.setSqlId("LLBenefitSql2");
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.CustNo.value);
        mySql.addSubPara(tDutycode);
        mySql.addSubPara(tGetDutyCode);
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(tGetDutyCode);
        turnPage2.queryModal(mySql.getString(),PaidFeeGrid);
    }
}




