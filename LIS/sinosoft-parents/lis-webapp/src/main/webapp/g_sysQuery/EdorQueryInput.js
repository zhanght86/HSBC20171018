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
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick(o) {
	if(o=='1'){
		if(!verifyInput2()){
			return false;
		}
		
		if(fm.EdorAppNo.value =="" && fm.GrpContNo.value=="" && fm.AppntNo.value=="" && fm.AcceptDATE.value=="" && fm.GrpName.value=="" && fm.SaleChnl.value==""){
			alert("������¼��һ�����б�������Ĳ�ѯ������");
			return false;
		}
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.PolicyQuerySql");
	tSQLInfo.setSqlId("PolicyQuerySql2");
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.AppntNo.value);
	tSQLInfo.addSubPara(fm.AcceptDATE.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.SaleChnl.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	
	turnPage1.queryModal(tSQLInfo.getString(),QueryResultGrid, 1, 1);
	
	if(o=='1'){
		if (!turnPage1.strQueryResult) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
		}
	}
}

/**
 * ��ȫ��ϸ
 */
function policyDetClick() {
	
	var tSelNo = QueryDetailGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����ȫ������Ϣ");
		return false;
	}
	var tEdorAppno = QueryDetailGrid.getRowColData(tSelNo,1);
	var tEdorNo = QueryDetailGrid.getRowColData(tSelNo,2);	
	
	strUrl="../g_pos/EdorCheckDetailInput.jsp?EdorAppNo="+tEdorAppno+"&EdorNo="+tEdorNo ;
	window.open(strUrl,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		
}

/**
 * չʾ��ȫ��ϸ
 */
function showEdorInfo(){
	
	var tSelNo = QueryResultGrid.getSelNo()-1;
	var tEdorAppNo = QueryResultGrid.getRowColData(tSelNo,1);
	var tGrpContNo = QueryResultGrid.getRowColData(tSelNo,2) 
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.PolicyQuerySql");
	tSQLInfo.setSqlId("PolicyQuerySql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var arrResult = easyExecSql(tSQLInfo.getString());
	
	if(arrResult !=null ){
		divQueryEdorDetail.style.display="";
		turnPage2.queryModal(tSQLInfo.getString(),QueryDetailGrid, 1, 1);
	}else {
		divQueryEdorDetail.style.display="none";
	}
}


function showCheck(){	
	if(tFlag=="Old"){
		divShowQueryInfo.style.display="none";
		fm.CloseButton.style.display="";		
		fm.GrpContNo.value=tGrpContNo;
		queryClick(2);	
	}
}

