var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();



//��ʼ����ѯ
function initQuery(){
	//��ѯ�����ⰸ�⸶�ܽ��
	queryPaidClaim();
	//��ѯ���⸶������������
	queryPaidDuty();
}
//��ѯ�����ⰸ�⸶�ܽ��
function queryPaidClaim(){
	
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimCheckInputSql");
	mySql.setSqlId("LLClaimCheckSql3");
	mySql.addSubPara(fm.RgtNo.value);
	var arr = easyExecSql(mySql.getString());
	if (arr)
    {	
		fm.ClaimMoney.value=arr[0][0];
    }
}

//��ѯ���⸶������������
function queryPaidDuty(){
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimCheckInputSql");
	mySql.setSqlId("LLClaimCheckSql1");
	mySql.addSubPara(fm.ContNo.value);
	//alert(fm.CustNo.value);
	mySql.addSubPara(fm.CustNo.value);
	mySql.addSubPara(fm.RgtNo.value);
	turnPage1.queryModal(mySql.getString(),ThisDutyGrid);
}

//ѡ��ThisDutyGrid����
function getThisDutyGrid(){
	var i = ThisDutyGrid.getSelNo();
    if (i != '0'){
        i = i - 1;
        var tGetDutyCode = ThisDutyGrid.getRowColData(i,13);
        
        mySql = new SqlClass();
        mySql.setResourceName("claim.LLClaimCheckInputSql");
        mySql.setSqlId("LLClaimCheckSql2");
        mySql.addSubPara(fm.RgtNo.value);
        mySql.addSubPara(tGetDutyCode);
        turnPage2.queryModal(mySql.getString(),ThisFeeGrid);
    }
}