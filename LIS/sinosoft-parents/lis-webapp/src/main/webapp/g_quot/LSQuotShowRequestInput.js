/***************************************************************
 * <p>ProName��LSQuotShowRequestInput.jsp</p>
 * <p>Title��ҵ������</p>
 * <p>Description��ҵ������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-26
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
	tSQLInfo.setResourceName("g_quot.LSQuotRequestSql");
	tSQLInfo.setSqlId("LSQuotRequestSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), RequestGrid, 1, 1);
}

/**
 * չʾҵ��������ϸ
 */
function showDetail() {
	
	var tSelNo = RequestGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.RequestType.value = RequestGrid.getRowColData(tSelNo-1, 2);
	fm.RequestTypeName.value = RequestGrid.getRowColData(tSelNo-1, 3);
	fm.OtherTypeDesc.value = RequestGrid.getRowColData(tSelNo-1, 4);
	fm.RiskCode.value = RequestGrid.getRowColData(tSelNo-1, 5);
	fm.RiskName.value = RequestGrid.getRowColData(tSelNo-1, 6);
	fm.RequestDesc.value = RequestGrid.getRowColData(tSelNo-1, 7);
	fm.SubUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 8);
	fm.BranchUWOpinion.value = RequestGrid.getRowColData(tSelNo-1, 9);
	
	if (fm.RequestType.value == "00" || fm.RequestType.value == "01" ) {
		
		divOtherTypeDescTitle.style.display = "none";
		divOtherTypeDescInput.style.display = "none";
		divRiskTitle.style.display = "";
		divRiskInput.style.display = "";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	} else if (fm.RequestType.value == "09") {
		
		divOtherTypeDescTitle.style.display = "";
		divOtherTypeDescInput.style.display = "";
		divRiskTitle.style.display = "none";
		divRiskInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
		divTD3.style.display = "none";
		divTD4.style.display = "none";
	}
}
