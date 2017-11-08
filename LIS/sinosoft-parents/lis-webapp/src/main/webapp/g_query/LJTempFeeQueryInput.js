/***************************************************************
 * <p>ProName��LJTempFeeCancelInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryApplyTempFee() {
	
	initTempFeeInfoGrid();
	initTempFeeDetailInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql5");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ѡ��ʱ����
 */
function showTempFeeInfo() {
	
	initTempFeeDetailInfoGrid();
	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql6");
	tSQLInfo.addSubPara(tTempFeeNo);
	
	turnPage2.queryModal(tSQLInfo.getString(), TempFeeDetailInfoGrid, 0, 1);
}

/**
 * ��ʼ��ѯ����
 */
function initQueryInfo() {

	fm.QueryPayType.value = "";
	fm.QueryPayTypeName.value = "";
	fm.QueryCustBankCode.value = "";
	fm.QueryCustBankName.value = "";
	fm.QueryCustBankAccNo.value = "";
	fm.QueryCustAccName.value = "";
	fm.QueryGrpName.value = "";
	fm.QueryAgentName.value = "";
	fm.QueryApplyStartDate.value = "";
	fm.QueryApplyEndDate.value = "";
	fm.QueryInputStartDate.value = "";
	fm.QueryInputEndDate.value = "";
	fm.QueryConfirmStartDate.value = "";
	fm.QueryConfirmEndDate.value = "";
	fm.State.value = "";
	fm.StateName.value = "";
	fm.QueryManageCom.value = "";
	fm.QueryManageComName.value = "";
}

/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "������Ϣ�б�";
	
	var tTitle = "�������^|���շѺ�^|����״̬^|���ѷ�ʽ^|�ͻ�������^|�ͻ������˺�^|�ͻ��˻���^|���" +
			"^|��ǰʹ�ý��^|�������^|Ͷ����λ����^|�ͻ�����^|������^|��������^|��������^|�տ�����^|�տ��˺�" +
			"^|���λ^|֧Ʊ��^|¼����^|¼������^|�����^|�������";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql8");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryApplyStartDate.value);
	tSQLInfo.addSubPara(fm.QueryApplyEndDate.value);
	tSQLInfo.addSubPara(fm.QueryInputStartDate.value);
	tSQLInfo.addSubPara(fm.QueryInputEndDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmStartDate.value);
	tSQLInfo.addSubPara(fm.QueryConfirmEndDate.value);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
