/***************************************************************
 * <p>ProName��LCPolicyQueryInput.js</p>
 * <p>Title��������ѯ</p>
 * <p>Description��������ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-08-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick(o) {
	if(!verifyInput2()){
		return false;
	}
	if(fm.GrpPropNo.value=="" && fm.GrpContNo.value==""
	 && fm.OfferListNo.value=="" && fm.AgentCode.value=="" && fm.GrpName.value==""
	  && fm.SaleChnl.value=="" && fm.SignStartDate.value=="" && fm.SignEndDate.value==""
	   && fm.ServiceCom.value=="" && fm.RiskCode.value==""&&fm.QuotNo.value==""){
		alert("������¼��һ�����б�������Ĳ�ѯ��Ϣ���в�ѯ��");
		return false;
	}
	
	if(fm.SignStartDate.value !="" && fm.SignEndDate.value !="" ){
		 if(fm.SignStartDate.value > fm.SignEndDate.value){
		 	alert("ǩ��������С��ǩ��ֹ�ڣ�" );
		 	return false;
		 }
	}

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.PolicyQuerySql");
	tSQLInfo.setSqlId("PolicyQuerySql1");
	
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	tSQLInfo.addSubPara(fm.SignStartDate.value);
	tSQLInfo.addSubPara(fm.SignEndDate.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.OfferListNo.value);
	tSQLInfo.addSubPara(fm.AgentCode.value);
	tSQLInfo.addSubPara(fm.ServiceCom.value);
	tSQLInfo.addSubPara(fm.RiskCode.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	
	turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 2, 1);
	
	if(o=='1'){
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ������ϸ
 */
function policyDetClick() {
	
	var tSelNo = QueryResultGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��Ͷ����Ϣ");
		return false;
	}
	
	var tGrpContNo = QueryResultGrid.getRowColData(tSelNo,3);
	var tGrpPropNo = QueryResultGrid.getRowColData(tSelNo,4);

	strUrl="../g_app/LCGrpContPolInput.jsp?GrpPropNo="+ tGrpContNo+"&GrpContNo="+tGrpPropNo+"&Flag=3" ;		
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
