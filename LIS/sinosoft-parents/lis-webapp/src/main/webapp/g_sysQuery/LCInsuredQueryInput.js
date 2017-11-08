/***************************************************************
 * <p>ProName��LCInsuredQueryInput.js</p>
 * <p>Title���������˲�ѯ</p>
 * <p>Description���������˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

/**
 * ��ѯ��ť
 */
function queryClick() {
	
	if ((fm.GrpContNo.value==null || fm.GrpContNo.value=="")
		 && (fm.CustomerName.value==null || fm.CustomerName.value=="")
		 	&& (fm.IDNo.value==null || fm.IDNo.value=="")) {
		alert("��ѯ������[������]��[����������]��[֤������]����ͬʱΪ�գ�");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql");
	
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);	
	
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);	
	tSQLInfo.addSubPara(mManageCom);
						
	turnPage1.queryModal(tSQLInfo.getString(),LCInsuredListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}

/**
 * չʾѡ�е��ⰸ�⸶��ϸ
 */
function showSelectDetail () {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ��һ������������Ϣ");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
	var tMainCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,17);
	var tMainFlag = LCInsuredListGrid.getRowColData(tSelNo,6);
	var tContNo = LCInsuredListGrid.getRowColData(tSelNo,5);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql1");	
	if (tMainFlag!=null && tMainFlag!="����") {
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tMainCustomerNo);			
	} else {
		tSQLInfo.addSubPara(tCustomerNo);
		tSQLInfo.addSubPara("");		
	}
	tSQLInfo.addSubPara(tContNo);
				
	turnPage2.queryModal(tSQLInfo.getString(),MainInsuredListGrid, 2);	
}

//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "Ͷ���˱���^|Ͷ��������^|Ͷ������^|������^|���˱�����^|�����������˹�ϵ^|"
							+"�����˿ͻ���^|����������^|�Ա�^|��������^|֤������^|֤������^|"
							+"��������^|����ֹ��^|����״̬^|�б�����";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_sysQuery.LCInsuredQuerySql");
	tSQLInfo.setSqlId("LCInsuredQuerySql");
	
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.GrpAppNo.value);
	tSQLInfo.addSubPara(fm.GrpContNo.value);	
	
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.IDNo.value);
	tSQLInfo.addSubPara(fm.BirthDay.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}

//��ѯ����
/**
 * ������ѯ
 */
function showInsuredLCPol() {	
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ��һ������������Ϣ");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
		
	var strUrl="../g_claim/LLClaimQueryPolicyMain.jsp?CustomerNo="+tCustomerNo+"&Mode=2";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"������ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��ȫ��ѯ
 */
function showInsuredLCEdor() {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ��һ������������Ϣ");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
		
	var strUrl="../g_claim/LLClaimQueryEdorMain.jsp?CustomerNo="+tCustomerNo+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"��ȫ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
/**
 * �����ⰸ��ѯ
 */
function showOldCase() {
	
	var tSelNo = LCInsuredListGrid.getSelNo() - 1;
	if (tSelNo<0) {
		
		alert("��ѡ��һ������������Ϣ");
		return false;
	}	
	var tCustomerNo = LCInsuredListGrid.getRowColData(tSelNo,7);
	
	var strUrl="../g_claim/LLClaimLastCaseMain.jsp?CustomerNo="+tCustomerNo+"&Mode=0";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
