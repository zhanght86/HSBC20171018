/***************************************************************
 * <p>ProName��QueryOfferListInput.js</p>
 * <p>Title����ѯ���۵���</p>
 * <p>Description����ѯ���۵���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-06
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

function queryClick(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCContPlanTradSql");
	tSQLInfo.setSqlId("LCContPlanTradSql24");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara("");//����������½����
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage1.queryModal(tSQLInfo.getString(), OfferListGrid, 1, 1);
}

function selectOffer(){
	var tSelNo = OfferListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ�����۵�����Ϣ��");
		return false;
	}
	var tOfferListNo = OfferListGrid.getRowColData(tSelNo,1);
	
	top.opener.fmrela.OfferListNo.value=tOfferListNo;
	
	top.close();
}
