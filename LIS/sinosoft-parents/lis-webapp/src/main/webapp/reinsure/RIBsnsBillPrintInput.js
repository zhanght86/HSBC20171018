//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug = "0";
var DealWithNam;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillPrintInputSql";
// ������˵���ѯ
function queryBillPrint() {
			
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillPrintInputSql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(fm.StartDate.value);// ָ������Ĳ������������˳�����
	mySql.addSubPara(fm.EndDate.value);// ָ������Ĳ������������˳�����
	var strSQL = mySql.getString();			
	turnPage.queryModal(strSQL, BillPrintListGrid);
}

// �˵���ӡ
function billPrint() {
	try {
		if (verifyInput() == true) {
			if (verifyInput2() == true) {
	
				fm.target = "importCessData";
		
				fm.action = "LPrtPrintBillSave.jsp";
				fm.submit();
			}
		}
	} catch (ex) {
		// showInfo.close( );
		myAlert(ex);
	}
}



function verifyInput1() {
	var mStaTerm = fm.StartDate.value;
	var year = mStaTerm.substr(0, 4);
	var season = mStaTerm.substr(5);
	if (!isInteger(year) || mStaTerm.length > 6 || parseFloat(season) > 4) {
		myAlert(""+"ͳ���ڼ�ʱ���ִ���!"+"");
		return false;
	}
	return true;
}


// �� ��
function ResetForm() {
	fm.StartDate.value = '';
	fm.EndDate.value = '';
}

function afterCodeSelect(CodeName, Field) {
	
//	if(CodeName=="riprinttype"){
//		
//		fm.CalYear.value='';
//		fm.seaType.value='';
//		fm.seaName.value='';
//		fm.monType.value='';
//		fm.monName.value='';
//		
//	}
	
	


}
