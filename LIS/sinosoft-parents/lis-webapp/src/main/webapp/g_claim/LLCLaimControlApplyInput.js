/***************************************************************
 * <p>ProName��LLCLaimControlApplyInput.js</p>
 * <p>Title���������ο����������</p>
 * <p>Description���������ο����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
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
	tSQLInfo.setResourceName("g_claim.LLCLaimControlApplySql");
	tSQLInfo.setSqlId("LLCLaimControlApplySql1");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.PolicyNo.value);
	tSQLInfo.addSubPara(fm.StartDate.value);
	tSQLInfo.addSubPara(fm.EndDate.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), PolicyGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}

function applyClick() {
	
	var tSelNo = PolicyGrid.getSelNo();
	if (tSelNo==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;	
	}
	
	var tGrpContNo = PolicyGrid.getRowColData(tSelNo-1, 2);
	
	var strUrl="./LLCLaimControlMain.jsp?Flag=1&BussType=NB&BussNo="+ tGrpContNo;
	
	window.open(strUrl,"�������ο���",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
