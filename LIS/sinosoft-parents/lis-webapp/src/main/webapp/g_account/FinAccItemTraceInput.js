/***************************************************************
 * <p>ProName��FinAccItemTraceInput.js</p>
 * <p>Title����Ŀ��ϸ��ѯ</p>
 * <p>Description����֧��Ŀ��ϸ��ѯ�뵼��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬

var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
	tSQLInfo.setSqlId("FinAccItemTraceSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.OtherNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.TransStartDate.value);
	tSQLInfo.addSubPara(fm.TransEndDate.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(mManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(), FinAccItemTraceGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}


/**
 * ģ����ѯ��֧��Ŀ
 */
function fuzzyQueryAccItemCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode=="13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("�������֧��Ŀ!");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
			tSQLInfo.setSqlId("FinAccItemTraceSql3");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("�����ڸ÷�֧��Ŀ��");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and accitemname like #%"+objCodeName+"%#";
					showCodeList('accitem', [Field, FieldName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

/**
 * ģ����ѯ��ƿ�Ŀ
 */
function fuzzyQueryFinCode(Field, FieldName) {
	
	var objCodeName = FieldName.value;
	if (objCodeName=="") {
		return false;
	}
	
	if (window.event.keyCode=="13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
			alert("�������ƿ�Ŀ���ƣ�");
			return false;
		} else {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
			tSQLInfo.setSqlId("FinAccItemTraceSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (arr==null) {
				alert("�����ڸû�ƿ�Ŀ��");
				return false;
			} else {
				if (arr.length == 1) {
					Field.value = arr[0][0];
					FieldName.value = arr[0][1];
				}else {
					var queryCondition = "1 and finname like #%"+objCodeName+"%#";
					showCodeList('finaccount', [Field, FieldName], [0,1], null, queryCondition, '1', 1, '300');
				}
			}
		}
	}
}

/**
 * ��������������Ϣ
 */
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "ҵ������^|���κ�^|ҵ���^|Ͷ������^|������^|�����������^|oracle��������^|��������^|�ɱ�����^|�����־^|��Ŀ����^|��Ŀ����^|���^|��ϸ��^|����^|��Ʒ��^|oracle��Ʒ��^|��Ʒ����^|�ο���^|���ö�^|��Ʒ����1^|��Ʒ����2^|��Ʒ����3^|��������^|�����������";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_account.FinAccItemTraceSql");
	tSQLInfo.setSqlId("FinAccItemTraceSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.OtherNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(fm.TransStartDate.value);
	tSQLInfo.addSubPara(fm.TransEndDate.value);
	tSQLInfo.addSubPara(fm.FinCode.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
