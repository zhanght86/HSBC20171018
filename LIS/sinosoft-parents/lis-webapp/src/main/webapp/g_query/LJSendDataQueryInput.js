/***************************************************************
 * <p>ProName��LJSendDataQueryInput.jsp</p>
 * <p>Title���������ݲ�ѯ</p>
 * <p>Description���������ݲ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-11-18
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryInfo() {
	
	if (isEmpty(fm.QueryStartDate1) || isEmpty(fm.QueryEndDate1)) {
		alert("ҵ��ȷ����ֹ�ڱ�¼��");
		return false;
	}
	
	initDataInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LJSendDataQuerySql");
	tSQLInfo.setSqlId("LJSendDataQuerySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryFinBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryGetMode.value);
	tSQLInfo.addSubPara(fm.QueryFinState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryGetDealType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
		tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), DataInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='conditioncomcode') {
	
		var tSql = " 1 and comgrade in (#01#,#02#) and comcode like #"+ tManageCom +"%%#";
		
		if (returnType=='0') {
			return showCodeList('conditioncomcode',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('conditioncomcode',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='finbusstype') {
		
		var tSql = "1 and codetype=#finbusstype# and codeexp=#get#";
			
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
}

//���ݵ���
function exportData() {
	
	if (isEmpty(fm.QueryStartDate1) || isEmpty(fm.QueryEndDate1)) {
		alert("ҵ��ȷ����ֹ�ڱ�¼��");
		return false;
	}
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "����ʽ^|�����������^|�������^|���屣����^|����Ͷ��������^|ҵ������^|"
							+"ҵ�����^|���ѽ��^|֧����ʽ^|״̬^|ҵ��ȷ������^|���̿���ȡ����^|"
							+"ת������^|�ͻ�����^|�ͻ������˺�^|�ͻ��˻���^|��ȡ��^|��ȡ��֤������";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LJSendDataQuerySql");
	tSQLInfo.setSqlId("LJSendDataQuerySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryFinBussType.value);
	tSQLInfo.addSubPara(fm.QueryBussNo.value);
	tSQLInfo.addSubPara(fm.QueryGetMode.value);
	tSQLInfo.addSubPara(fm.QueryFinState.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	tSQLInfo.addSubPara(fm.QueryStartDate3.value);
	tSQLInfo.addSubPara(fm.QueryEndDate3.value);
	tSQLInfo.addSubPara(fm.QueryGetDealType.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
		tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
