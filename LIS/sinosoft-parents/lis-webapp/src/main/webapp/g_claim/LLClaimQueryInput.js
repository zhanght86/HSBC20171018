/***************************************************************
 * <p>ProName��LLClaimQueryCustInput.js</p>
 * <p>Title��ϵͳ�����˲�ѯ</p>
 * <p>Description��ϵͳ�����˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo;

/**
 * ��ѯ��������Ϣ
 */
function queryCustomerList() {
	
	//������ѯ
	if (mAppntNo==null || nullToEmpty(mAppntNo)=="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQuerySql");
		tSQLInfo.setSqlId("LLClaimQuerySql")		
	} else {
		
		tQueryResultFlag = false;
		tQueryFlag = true;
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimQuerySql");
		tSQLInfo.setSqlId("LLClaimQuerySql2");
		tSQLInfo.addSubPara(mAppntNo);		
	}

	tSQLInfo.addSubPara(nullToEmpty(mCustomerName));
	tSQLInfo.addSubPara(nullToEmpty(mBirthday));
	tSQLInfo.addSubPara(nullToEmpty(mEmpNo));
	tSQLInfo.addSubPara(nullToEmpty(mIDNo));		
	tSQLInfo.addSubPara(nullToEmpty(mGrpRgtNo));
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}
/**
 * ���ؿͻ���Ϣ
 */
function returnSelect() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = CustomerListGrid.getRowData(tSelNo);
		if (returnArr!=null) {

			if (returnArr[0]==null || returnArr[0]=="") {
				alert("���Ȳ�ѯ��");
				return false;
			} else {				
				top.opener.afterQueryCustomer(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("�����쳣��"+ ex);
	}
	top.close();
}