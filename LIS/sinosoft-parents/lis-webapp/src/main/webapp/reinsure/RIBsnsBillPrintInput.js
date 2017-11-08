//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug = "0";
var DealWithNam;
var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIBsnsBillPrintInputSql";
// 待审核账单查询
function queryBillPrint() {
			
	var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIBsnsBillPrintInputSql1");//指定使用的Sql的id
	mySql.addSubPara(fm.StartDate.value);// 指定传入的参数，多个参数顺序添加
	mySql.addSubPara(fm.EndDate.value);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();			
	turnPage.queryModal(strSQL, BillPrintListGrid);
}

// 账单打印
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
		myAlert(""+"统计期间时出现错误!"+"");
		return false;
	}
	return true;
}


// 重 置
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
