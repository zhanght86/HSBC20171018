/***************************************************************
 * <p>ProName��LJTempFeeOutQueryInput.jsp</p>
 * <p>Title: �����˷Ѳ�ѯ</p>
 * <p>Description�������˷Ѳ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-08-31
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
	
	initQueryInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	
	
	  if(_DBT==_DBO){
		  tSQLInfo.setSqlId("LJOutPayOutSql4");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LJOutPayOutSql7");
	   }
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}
/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "��ѯ����б�";
	
	var tTitle = "�������^|�������κ�^|������^|Ͷ��������^|�н��������^|��ɽ��(Ԫ)^|������^|����������ʡ^|������������^|�����˺�^|�˻���^|����״̬^|������^|��������^|�����^|�������^|��˽���";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJOutPayOutSql");
	  if(_DBT==_DBO){
		  tSQLInfo.setSqlId("LJOutPayOutSql5");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LJOutPayOutSql8");
	   }
	
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AgencyName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate1.value);
	tSQLInfo.addSubPara(fm.QueryEndDate1.value);
	tSQLInfo.addSubPara(fm.QueryStartDate2.value);
	tSQLInfo.addSubPara(fm.QueryEndDate2.value);
	
	var tQueryPayState = fm.QueryPayState.value;
	if (tQueryPayState=="0") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm.QueryPayState.value);
	} else if (tQueryPayState=="1") {
		tSQLInfo.addSubPara(fm.QueryPayState.value);
		tSQLInfo.addSubPara("");
	} else {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	}
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
