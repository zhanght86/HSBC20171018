/***************************************************************
 * <p>ProName��LSQuotShowUWDescInit.js</p>
 * <p>Title��ѯ�۲�ѯ--�ܹ�˾�������</p>
 * <p>Description��ѯ�۲�ѯ--�ܹ�˾�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2015-01-14
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();


/**
 * ��ѯ�ѱ����ر�Լ��
 */
function quryGrpSpec() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	var arrResult = easyExecSql(tSQLInfo.getString());
	if (arrResult !== null && arrResult !== "") {
		fm.UWConclu.value = arrResult[0][0];
		fm.UWConcluName.value = arrResult[0][1];
		fm.UWOpinion.value = arrResult[0][2];
	}
}

