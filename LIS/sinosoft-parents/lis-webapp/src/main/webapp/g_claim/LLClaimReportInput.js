/***************************************************************
 * <p>ProName��LLClaimReportInput.js</p>
 * <p>Title������¼�����</p>
 * <p>Description������¼�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();

var tQueryFlag = false;
var tQueryResultFlag = false;
var tMajorFlag;//��ʶ��2-�ֹ�˾�ظ�����3-�ܹ�˾�ظ�

var tPageNo = 1;//��¼��ѯҳ��
var tIndexNo = 1;//��¼��ѯ���

var submit = 0;//�ύ����

/**
 * ��ʼ��ҳ��չʾ��Ϣ
 */
function initMajorInfo() {
		
		fm.RptNo.value = mRptNo;
		fm.InputOperator.value = "";
		fm.InputDate.value = "";
		fm.InputCom.value = "";
		fm.InputComName.value = "";
		fm.InputRemarks.value = "";
		
		var tArr;
		
		//��ѯ�Ƿ������¼����ϱ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql9");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {
			alert("�ñ���δ�����ش󰸼��ϱ�����ȷ�ϣ�");
			return;
		} else {
			fm.InputOperator.value = tArr[0][0];
			fm.InputDate.value = tArr[0][1];
			fm.InputCom.value = tArr[0][2];
			fm.InputComName.value = tArr[0][3];
			fm.InputRemarks.value = tArr[0][4];					
		}
		
		//У��¼���������
		var tComGrade;
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql16");
		tSQLInfo.addSubPara(mManageCom);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ����¼�����Ļ�������!");
			return false;
		} else {
			tComGrade = tArr[0][0];
		}		
		if (tComGrade!=null && tComGrade!="01" && tComGrade!="02") {
			return false;
		}
		
		//��ѯ�Ƿ������¼��ķֹ�˾�ظ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql10");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {

			if (tComGrade=="02") {
				$("#divReplyInfo").css("display","");
				$("#divReplyInfo1").css("display","none");
				fm.RepInputOperator.value = mOperator;
				fm.RepInputDate.value = mCurrentDate;
				fm.RepInputCom.value = mManageCom;
				fm.RepInputComName.value = "";
				fm.RepInputRemarks.value = "";
				tMajorFlag=2;				
			}
		} else {
			
			$("#divReplyInfo").css("display","");
			fm.RepInputOperator.value = tArr[0][0];
			fm.RepInputDate.value = tArr[0][1];
			fm.RepInputCom.value = tArr[0][2];
			fm.RepInputComName.value = tArr[0][3];
			fm.RepInputRemarks.value = tArr[0][4];
			tMajorFlag=3;		
		}
		
		//��ѯ�Ƿ������¼����ܹ�˾�ظ���Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql11");
		tSQLInfo.addSubPara(mRptNo);
		tArr = null;
		tArr = easyExecSql(tSQLInfo.getString());
		//�ж��Ƿ��ѯ�ɹ�
		if (tArr==null || tArr.length==0) {
			
			if (tComGrade=="01") {
				
				$("#divReplyInfo1").css("display","");
				fm.RepInputOperator1.value = mOperator;
				fm.RepInputDate1.value = mCurrentDate;
				fm.RepInputCom1.value = mManageCom;
				fm.RepInputComName1.value = "";
				fm.RepInputRemarks1.value = "";	
				tMajorFlag=3;					
			}
		} else {
						
			$("#divReplyInfo1").css("display","");						
			fm.RepInputOperator1.value = tArr[0][0];
			fm.RepInputDate1.value = tArr[0][1];
			fm.RepInputCom1.value = tArr[0][2];
			fm.RepInputComName1.value = tArr[0][3];
			fm.RepInputRemarks1.value = tArr[0][4];
			tMajorFlag=3;		
		}			
}

/**
 * ��������
 */
function saveReport() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	var tRgtClass = fm.RgtClass.value;
	if (tRgtClass=="01") {
		if(!notNull(fm.AppntNo,"Ͷ��������")){return false;};
	}
	//У�鱨������
	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql16");
	tSQLInfo.addSubPara(fm.RptCom.value);
	tArr = null;
	tArr = easyExecSql(tSQLInfo.getString());
	//�ж��Ƿ��ѯ�ɹ�
	if (tArr==null || tArr.length==0) {
		alert("δ��ѯ����¼�����Ļ�������!");
		return false;
	} else {
		tComGrade = tArr[0][0];
	}
	if (tComGrade==null || tComGrade=="01" || tComGrade=="02") {
		
		alert("���������������ܹ�˾��ֹ�˾��������ѡ��");
		fm.RptCom.focus();
		fm.RptCom.className = "warnno";
		return false;
	}	
	
	
	var tRptDate = fm.RptDate.value;
	if (dateDiff(tRptDate,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	fm.Operate.value="REPORTINSERT";
	submitForm();
}

/**
 * ����ɾ��
 */
function deleteReport() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		alert("���ȱ���!");
		return false;
	}
	if (confirm("ȷ��ɾ���ñ�����")) {
		
		fm.Operate.value="REPORTDELETE";
		submitForm();		
	}	
}


/**
 * ��ѯ������Ϣ
 */
function queryReportInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql");
	tSQLInfo.addSubPara(mRptNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.RptNo.value = tArr[0][0];
		fm.RgtClass.value = tArr[0][1];
		fm.RgtClassName.value = tArr[0][2];
		if (fm.RgtClass.value=="1") {
			$("#appntinfo").hide();
			$("#appntvalueinfo").hide();
			fm.AppntNo.value = "";
			fm.AppntName.value = "";						
		} else if (fm.RgtClass.value=="2") {
			$("#appntinfo").show();
			$("#appntvalueinfo").show();
			fm.AppntNo.value = tArr[0][3];
			fm.AppntName.value = tArr[0][4];					
		}	
		fm.RptName.value = tArr[0][5];
		fm.RptPhone.value = tArr[0][6];
		fm.RptMode.value = tArr[0][7];
		fm.RptModeName.value = tArr[0][8];
		fm.Relation.value = tArr[0][9];
		fm.RelationName.value = tArr[0][10];
		fm.RptDate.value = tArr[0][11];
		fm.RgtFlag.value = tArr[0][12];
		fm.RgtFlagName.value = tArr[0][13];
		fm.RptInputOperator.value = tArr[0][14];
		fm.RptInputDate.value = tArr[0][15];
		fm.RptCom.value = tArr[0][16];
		fm.SelfRptCom.value = tArr[0][16];
		fm.RptComName.value = tArr[0][17];
		fm.RptConfOperator.value = tArr[0][18];
		fm.RptConfDate.value = tArr[0][19];
	} else {
		return false;		
	}
}
/**
 * ��ѯ������������Ϣ
 */
function queryReportCustomerInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql1");
	tSQLInfo.addSubPara(fm.RptNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2,1);
	

}
/**
 * ��ѯѡ�еĳ�������Ϣ������Ĭ��ѡ�е�һ��
 * ����ֻ�������һ��������
 */
function showSelectCustomerInfo() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		return false;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	fm.CustomerNo.value = tCustomerNo;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql2");
	tSQLInfo.addSubPara(mRptNo);
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
		
		initReportCustomerInfo();
		disableCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];		
		fm.Birthday.value = tArr[0][2];
		fm.EmpNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];		
		fm.CustomerRemarks.value = tArr[0][9];
		fm.SelfAppntNo.value = tArr[0][10];
		fm.SelfAppntName.value = tArr[0][11];
		fm.SubRptNo.value = tArr[0][12];
		queryReportCaseInfo();
			
	} else {
		return false;		
	}	
}
/*=================================�ͻ�����===========================*/
/**
 * �����ͻ�
 */
function addCustomer() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("���ȱ��汨����Ϣ");
		return false;;
	}	
	
	fm.Operate.value="CUSTOMERINSERT";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * У��ͻ���Ϣ--�Ƿ������������
 */
function checkCustomerInfo() {			
	
	//У�鱨����ֻ����1��������
	if (fm.Operate.value=="CUSTOMERINSERT") {
						
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql3");
		tSQLInfo.addSubPara(fm.RptNo.value);
		
		var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistsArr!=null && tExistsArr[0][0]==1 ) {
			
			alert("һ�α�����ֻ����1�������ˣ�");
			return false;
		}		
	}
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[����������]")){return false;};
		if(!notNull(fm.Birthday,"[����������]")){return false;};
		if(!notNull(fm.IDNo,"[֤������]")){return false;};
		if(!notNull(fm.IDType,"[֤������]")){return false;};
		if(!notNull(fm.Gender,"[�Ա�]")){return false;};
	}
	
	//У�����¼��ĳ�������Ϣ�Ƿ��ܹ���ñ�����
	queryCustomer();
	if (!tQueryResultFlag) {

		alert("ϵͳ�в����ڸó����ˣ�������¼�룡");
		return false;
	}
	
	return true;
}

/**
 * ����¼����Ϣѡ��ϵͳ��������Ϣ�������ҳ��
 * �������˼�������������+�������ڡ�����+Ա���š�֤�����룬����������ѡһ��
 */
function selectUser(tType) {
	
	var keycode = event.keyCode;
	//�س���ascii����13
	if(keycode!="13" && keycode!="9") {
		return;
	} else {
		tQueryFlag = false;
	}
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("���ȱ��汨����Ϣ");
		return false;;
	}
		
	var tCustomerName = fm.CustomerName.value;
	var tBirthday = fm.Birthday.value;
	var tEmployeNo = fm.EmpNo.value;
	var tIDNo = fm.IDNo.value;
	if (tCustomerName!=null && tCustomerName!="") {

		if (tBirthday!=null && tBirthday!="") {
			
			if (dateDiff(tBirthday,mCurrentDate,'D')<0) {	
				
				alert("�������ڲ������ڵ�ǰ���ڣ�");
				return false;
			}
			//queryCustomer(1);
		} else {
			
			if (tEmployeNo!=null && tEmployeNo!="") {
				//queryCustomer(2);
			} else {
				
				if (tIDNo!=null && tIDNo!="") {
						//queryCustomer(3);
				} else {
					return;
				}				
			}
		}	
	} else {
			if (tIDNo!=null && tIDNo!="") {
					//queryCustomer(3);
			} else {
				alert("�������˼�������������+�������ڡ�����+Ա���š�֤�����룬����������ѡ��������һ��!");
				return false;
			}		
	}
	queryCustomer();
	if (!tQueryResultFlag) {

		alert("ϵͳ�в����ڸó����ˣ�������¼�룡");
		return false;
	}
}
/**
 * ����¼����Ϣѡ��ϵͳ��������Ϣ�������ҳ��
 * �������˼�������������+�������ڡ�����+Ա���š�֤�����룬����������ѡһ��
 * tFlag �����ѯ���ȼ�����ʱ����--�ȴ�ȷ��
 */
function queryCustomer(tFlag) {
		
	tQueryResultFlag = false;
	tQueryFlag = true;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql4");
	tSQLInfo.addSubPara(fm.RptNo.value);
	
	//����������������ò�ѯ
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql15");
		tSQLInfo.addSubPara(fm.AppntNo.value);
	}	
				
	if (tFlag==1) {

		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	} else if (tFlag==2) {
			
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara(fm.EmpNo.value);
		tSQLInfo.addSubPara("");				
	} else if (tFlag==3) {
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara("");			
		tSQLInfo.addSubPara(fm.IDNo.value);		
	} else {
		
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);					
		tSQLInfo.addSubPara(fm.EmpNo.value);			
		tSQLInfo.addSubPara(fm.IDNo.value);			
	}
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfRptCom.value);	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		
		return false;
	} else {
		
		tQueryResultFlag = true;
		if (tArr.length==1) {
			
			fm.CustomerNo.value = tArr[0][0];
			fm.CustomerName.value = tArr[0][1];
			fm.Birthday.value = tArr[0][2];
			fm.EmpNo.value = tArr[0][3];
			fm.IDNo.value = tArr[0][6];
			fm.IDType.value = tArr[0][4];
			fm.IDTypeName.value = tArr[0][5];			
			fm.Gender.value = tArr[0][7];
			fm.GenderName.value = tArr[0][8];
			fm.SelfAppntNo.value = tArr[0][9];
			fm.SelfAppntName.value = tArr[0][10];
		} else {
			alert("����¼����Ϣ�ɲ�ѯ����������ˣ����ֶ�ѡ��!");
			showCustomerList();			
		}
	}	
}
//չʾ�ͻ��б�--����ͬһͶ������ͬ��ͬ���ջ�ͬ��ͬԱ���Ż�ͬһ֤������
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmpNo.value+"&IDNo="+fm.IDNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}
/**
 * �޸Ŀͻ�
 */
function modifyCustomer() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("���ȱ��汨����Ϣ");
		return false;
	}
		
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���ó�������Ϣ");
		return false;
	}
	var tCustomerNoSel = CustomerListGrid.getRowColData(tSelNo,1);
	var tCustomerNo = fm.CustomerNo.value;
	if (tCustomerNo!=null && tCustomerNo!="") {
		if (tCustomerNoSel!=tCustomerNo) {
			alert("���޸ġ����������滻������");
			return false;
		}
	}
	fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,1);		
	fm.Operate.value="CUSTOMERUPDATE";
	if(!checkCustomerInfo()) {
		return false;
	}
	submitForm();	
}

/**
 * delete�ͻ�
 */
function deleteCustomer() {
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="" || tRptNo=="null") {
		alert("���ȱ��汨����Ϣ");
		return false;;
	}
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���ó�������Ϣ");
		return false;
	}
	
  if (confirm("������ɾ����������ɾ���ó������µ�������ص���Ϣ��ȷ��ɾ����?")) {  
		
		fm.CustomerNo.value = CustomerListGrid.getRowColData(tSelNo,1);
		
		fm.Operate.value = "CUSTOMERDELETE";
		submitForm();	
	}	
}
/*======================�¼�����========================*/
/**
 * ��ѯ�����������¼���Ϣ
 */
function queryReportCaseInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql5");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),AccidentListGrid,2);

}
/**
 * չʾѡ�еı����������¼���Ϣ
 */
function showSelReportCaseInfo() {
	
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ����������Ϣ");
		return false;
	}
	var tAccNo = AccidentListGrid.getRowColData(tSelNo,1);
	if (tAccNo==null || tAccNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	var tCustomerNo = AccidentListGrid.getRowColData(tSelNo,2);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	
	fm.AccNo.value = tAccNo;
	fm.CustomerNo.value = tCustomerNo;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql6");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tAccNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
		
		initCustomerCaseInfo();		
		fm.AccNo.value = tArr[0][0];
		fm.AccReason.value = tArr[0][1];
		fm.AccReasonName.value = tArr[0][2];
		fm.AccDate.value = tArr[0][3];
		fm.HospitalCode.value = tArr[0][4];
		fm.HospitalName.value = tArr[0][5];
		fm.ClaimPay.value = tArr[0][6];
		fm.AccGrade.value = tArr[0][7];
		fm.AccGradeName.value = tArr[0][8];
		fm.AccSite.value = tArr[0][9];
		fm.AccDesc.value = tArr[0][10];
		fm.CaseRemark.value = tArr[0][11];
		
		var tClaimType = tArr[0][12];
		var tClaimTypeArr = tClaimType.split(",");
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimTypeArr!= '' && tClaimTypeArr !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimTypeArr.length;l++){
								
					var tClaim = tClaimTypeArr[l].toString();
					if(fm.ClaimType[j].value == tClaim.substr(1,3)){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		} else {
			
			for (var i=0;i<fm.ClaimType.length;i++) {
			  
			  if (fm.ClaimType[i].value==tClaimType.substr(1,3)) {
			  	fm.ClaimType[i].checked = true;
			  }				
			}			
		}
	} else {
		return false;		
	}
	queryCaseDutyInfo();
}
/**
 * ��ѯ�¼�������Ϣ
 */
function queryCaseDutyInfo() {
	
	turnPage3.pageLineNum = 100;	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql12");
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.AccNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	turnPage3.queryModal(tSQLInfo.getString(),GrpClaimDutyGrid,2,1);		
	
}
/**
 * �����¼�
 */
function newCase() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ����������Ϣ");
		return false;
	}
	
	fm.Operate.value="CASEINSERT";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}
/**
 * У���¼�
 */
function checkCaseInfo() {
	
	var tCustomerNo = fm.CustomerNo.value;
	//��������ϢУ��
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("����ѡ��һ���ͻ���Ϣ��");
		return false;
	}	
	
	if(!notNull(fm.AccReason,"����ԭ��")){return false;};
	if(!notNull(fm.AccDate,"��������")){return false;};	
	if(!notNull(fm.HospitalCode,"����ҽԺ")){return false;};
	if(!notNull(fm.HospitalName,"ҽԺ����")){return false;};
	if(!notNull(fm.AccSite,"���յص�")){return false;};
	if(!notNull(fm.AccDesc,"�¹�����")){return false;};
	
	var tRptNo = fm.RptNo.value;
	var tAccDate = fm.AccDate.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	
	//У��������Ƿ����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql7");
	tSQLInfo.addSubPara(tRptNo);
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("�ó����˲��ڴ˴������£��������!");
		return false;
	}	
	//��������У��
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	//У���������
	var tClaimType = new Array;
		
	//��������
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;			
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("��ѡ��������ͣ�");
		return false;
	}

	
	//У������ҽԺ��ҽԺ�����Ƿ�ƥ��
		
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
		tSQLInfo.setSqlId("LLClaimReportSql8");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("ҽԺ������ҽԺ���벻��Ӧ�����飡");
			return false;
		}
	}
	return true;
}

/**
 * �޸��¼�
 */
function modifyCase() {
	
	var tSelNo = AccidentListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���ó����˵��¼���Ϣ");
		return false;
	}
	tPageNo = turnPage2.pageIndex;
	tIndexNo = tSelNo+1;
	
	fm.SelNo.value = tIndexNo;
	fm.PageIndex.value = tPageNo;	
	
	fm.Operate.value="CASEUPDATE";
	if(!checkCaseInfo()) {
		return false;
	}
	submitForm();	
}

/**
 * ɾ���¼�
 */
function deleteCase() {
	
		var tSelNo = AccidentListGrid.getSelNo()-1;
		if (tSelNo<0) {
			alert("��ѡ��һ���¼���Ϣ");
			return false;
		}	
	
  if (confirm("�¼�ɾ����������ɾ���ó����˸��¼��µ�������ص���Ϣ��ȷ��ɾ����?")) {  
					
		fm.Operate.value = "CASEDELETE";
		submitForm();	
	}	
}

/**
 * ������ѯ
 */
function showInsuredLCPol() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=2";
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
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimQueryEdorMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
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
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimLastCaseMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1";
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * ��������
 */
function ClaimData() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("���ȱ��汨����Ϣ��");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("���ȱ����������Ϣ");
		return false;
	}	
	
	var strUrl="./LLClaimDataMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * ǰ�õ���
 */
function ShowPresurveyData() {
		var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tGrpRgtNo="";
	var tRgtNo =fm.RptNo.value;
	var tInsuredNo = CustomerListGrid.getRowColData(tSelNo,1);
	var tVistFlag = "02";//02 ��ʾ ǰ�õ���
	var tManageCom =fm.RptCom.value;//�������
	var tAppntNo=fm.AppntNo.value;//Ͷ���˱���	
   if(tInsuredNo == "" || tInsuredNo == null) {
    
    alert("�����˱���Ϊ�գ���˲���Ϣ��");
		return false;
  	}
		
		var strUrl="LLClaimPreinvestInputMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&custNo="+tInsuredNo+"&surveyType=02&initPhase=01&AppntNo="+tAppntNo+"&ManageCom="+tManageCom+"&Mode=0";

		window.open(strUrl,"�������",'width=1100,height=680,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �����ط�
 */
function ReturnVisit() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("���ȱ��汨����Ϣ��");
			return false;		
	}
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("���ȱ����������Ϣ");
		return false;
	}	
		
	var strUrl="./LLClaimVisitMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}

/**
 * �ش󰸼��ϱ�
 */
function ShowReportBig() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("���ȱ��汨����Ϣ��");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("���ȱ����������Ϣ");
		return false;
	}
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ��");
		return false;
	}
	
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return false;
	}
	
	fm.CustomerNo.value = tCustomerNo;
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
	tSQLInfo.setSqlId("LLClaimReportSql17");
	tSQLInfo.addSubPara(tRptNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0 || tValidArr[0][0]=="0") {
		
		alert("�ñ��������ش󰸼���");
		return false;
	}		
	
	var strUrl="./LLClaimMajorMain.jsp?RptNo="+fm.RptNo.value+"&CustomerNo="+fm.CustomerNo.value+"&Mode="+mType;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ش󰸼��ϱ�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
	
}
/**
 * ��ճ�������Ϣ
 */
function initInfo() {
	
	initReportCustomerInfo();
	initCustomerCaseInfo();
	initAccidentListGrid();
	initGrpClaimDutyGrid();
	validCustomerInfo();
	queryReportCustomerInfo();
		
}
/**
 * ����ȷ��
 */
function confirmReport() {
	
	var tRptNo = fm.RptNo.value;
	if (tRptNo==null || tRptNo=="") {
		
			alert("���ȱ��汨����Ϣ��");
			return false;		
	}	
	var tCount = CustomerListGrid.mulLineCount;
	if (tCount<=0) {
		alert("���ȱ����������Ϣ");
		return false;
	}	
	
	//У���Ƿ�¼�����¼�
	var tCaseCount = AccidentListGrid.mulLineCount;
	if (tCaseCount<=0) {
		alert("�������δ¼���¼���Ϣ");
		return false;
	}	
	
  if (confirm("�Ƿ񱨰�ȷ��?")) {  
		
		var tRptNo = fm.RptNo.value;
		if (tRptNo==null || tRptNo=="") {
			alert("δ��ñ�����!");
			return false;
		}
		fm.Operate.value = "REPORTCONFIRM";
		submitForm();	
	}		
	
}

/**
 * ����
 */
function goToBack1() {
	window.location.href="./LLClaimMajorAppInput.jsp";
}

/**
 * ����
 */
function goToBack() {
	window.location.href="./LLClaimReportAppInput.jsp";
}

/**
 * �ش󰸼��ظ�ȷ��
 */
function majorApprove() {
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ��");
		return false;
	}
	
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tCustomerNo==null || tCustomerNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return false;
	}
	
	fm.CustomerNo.value = tCustomerNo;
	
  if (confirm("�ش󰸼��ظ��Ƿ�����ȷ��?")) {  
				
		fm.MajorFlag.value = tMajorFlag;
		if (tMajorFlag==2) {
			if(!notNull(fm.RepInputRemarks,"�ֹ�˾�������")){return false;};
		} else if (tMajorFlag==3) {
			if(!notNull(fm.RepInputRemarks1,"�ܹ�˾�������")){return false;};
		}
		fm.Operate.value = "MAJORCONFIRM";
		submitForm();	
	}	
}
/**
 * �ύ����
 */
function submitForm() {
		
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, tRptNo) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
//		alert("111");
////		alert(content);
//		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
//		showDialogWindow(urlStr, 1);
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		if (fm.Operate.value=="REPORTCONFIRM") {
			window.location.href="./LLClaimReportAppInput.jsp";
		} else if (fm.Operate.value=="MAJORCONFIRM") {
			window.location.href="./LLClaimMajorAppInput.jsp";
		} else {

			if ((fm.Operate.value).indexOf("CASE")>=0) {
				initCustomerCaseInfo();				
				queryReportCaseInfo();
				//�޸ĺ���ѡ��ǰ��
				if (fm.Operate.value=="CASEUPDATE") {
					
					for (var i=0;i<fm.PageIndex.value;i++) {
						turnPage2.nextPage();
					}
					AccidentListGrid.radioSel(fm.SelNo.value);
					showSelReportCaseInfo();
				}
				initGrpClaimDutyGrid();
				queryCaseDutyInfo();
				
			} else {
				if (fm.Operate.value=="REPORTINSERT") {
					
					if (tRptNo!=null && tRptNo!="") {
						mRptNo = tRptNo;
						window.location.href="LLClaimReportInput.jsp?Type=2&RptNo="+ tRptNo;
					}										
				}
				initForm();
			}
		}		
	}
	submit = 0;
}
/**
 * У��ǿ�Ҫ�أ����۽�
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"����Ϊ�գ���¼�룡");

		tObject.focus();
		tObject.style.background="#ffb900";
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}
//��ѯͶ������Ϣ��֧������ģ����ѯ
function QueryOnKeyDown(tObject) {

	var keycode = event.keyCode;
	//�س���ascii����13
	if(keycode!="13" && keycode!="9") {
		return;
	}	
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	if (tObjectName=="AppntName") {
		
		var tContType = fm.RgtClass.value;
		if (tContType==null || tContType=="") {
			alert("����ѡ�񱣵����ͣ�");
			fm.RgtClass.className = "warnno";
			fm.RgtClass.focus();
			return false;
		}		
		
		var tRptCom = fm.RptCom.value;
		if (tRptCom==null || tRptCom=="") {
			alert("����ѡ�񱨰�������");
			fm.RptCom.className = "warnno";
			fm.RptCom.focus();
			return false;
		} else {
			
			//У�鱨������
			var tComGrade;
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql16");
			tSQLInfo.addSubPara(fm.RptCom.value);
			tArr = null;
			tArr = easyExecSql(tSQLInfo.getString());
			//�ж��Ƿ��ѯ�ɹ�
			if (tArr==null || tArr.length==0) {
				alert("δ��ѯ����¼�����Ļ�������!");
				return false;
			} else {
				tComGrade = tArr[0][0];
			}
			if (tComGrade==null || tComGrade=="01" || tComGrade=="02") {
				
				alert("���������������ܹ�˾��ֹ�˾��������ѡ��");
				fm.RptCom.focus();
				fm.RptCom.className = "warnno";
				return false;
			}
		}
		
		var tAppntName = tObjectValue;
		
		if (tAppntName==null || tAppntName=="") {
			alert("��¼��Ͷ�������ƣ�");
			tObject.focus();
			return false;
		}
		
		
		//������ѯ
		if (tContType!=null && tContType=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql14");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tRptCom);
		} else if(tContType=="2") {//�ŵ���ѯ
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimReportSql");
			tSQLInfo.setSqlId("LLClaimReportSql13");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tRptCom);			
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AppntType.value = tArr[0][0];
				fm.AppntNo.value = tArr[0][1];
				fm.AppntName.value = tArr[0][2];
			} else {
				showAppntList();			
			}
		}		
	}else if (tObjectName=="HospitalName") {
		
		var tHospitalName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql10");
		tSQLInfo.addSubPara(tHospitalName);
		tSQLInfo.addSubPara("");		
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.AccResult1.value = "";
			fm.AccResult1Name.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.HospitalCode.value = tArr[0][0];
				fm.HospitalName.value = tArr[0][1];
				
				fm.AccSite.value = tArr[0][2];				
			} else {
				var tCodeCondition = "1 and hospitalname like #%"+ tHospitalName +"%# ";
				showCodeList('hospital', [fm.HospitalCode,fm.HospitalName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	
}
/**
 * �򿪲�ѯ��ϵͳ����Ͷ����ѡ��ҳ��
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.AppntName.value+"&ContType="+fm.RgtClass.value+"&AcceptCom="+fm.RptCom.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * ��ò�ѯ��ϵͳͶ����
 */
function afterQueryAppnt(tQueryResult) {
	
	fm.all('AppntType').value = tQueryResult[0];
	fm.all('AppntNo').value = tQueryResult[1];
	fm.all('AppntName').value = tQueryResult[2];
	self.focus();
}
/**
 * ��ò�ѯ��ϵͳ������
 */
function afterQueryCustomer(tQueryResult) {
	
	fm.all('SelfAppntNo').value = tQueryResult[0];
	fm.all('SelfAppntName').value = tQueryResult[1];
	fm.CustomerNo.value = tQueryResult[2];
	fm.CustomerName.value = tQueryResult[3];
	fm.Gender.value = tQueryResult[4];
	fm.GenderName.value = tQueryResult[5];		
	fm.Birthday.value = tQueryResult[6];	
	fm.EmpNo.value = tQueryResult[7];
	fm.IDType.value = tQueryResult[8];
	fm.IDTypeName.value = tQueryResult[9];	
	fm.IDNo.value = tQueryResult[10];			

	self.focus();
}
/**
 * ���뽻����ת��
 */
function applyPageNo() {
	
	var strUrl="./LLClaimHandAppMain.jsp";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"������ת�Ų�ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ���ؽ�����ת��
 */
function afterAppPageNo(tSelectResult) {
	
	fm.all('PageNo').value = "";
	fm.all('PageNo').value = tSelectResult[0];
}
//ѡ������������
function afterCodeSelect (CodeName) {
	
	try{
		if(CodeName == "conttype"){
			
			var tRgtClass = fm.RgtClass.value;
			if (tRgtClass=="02") {
				
				fm.AppntNo.value = "";
				fm.AppntName.value = "";
				$("#appntinfo").hide();
				$("#appntvalueinfo").hide();
			} else {
				$("#appntinfo").show();
				$("#appntvalueinfo").show();				
			}
		} else if (CodeName == "province") {
			
			fm.AccCity.value = "";
			fm.AccCityName.value = "";
			fm.AccCountyName.value = "";
			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "city") {
			
			fm.AccCountyName.value = "";
			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "hospital") {
			
			var tHospitalCode = fm.HospitalCode.value;
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql10");
			tSQLInfo.addSubPara("");
			tSQLInfo.addSubPara(tHospitalCode);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null || tArr.length==0) {
				
				fm.HospitalCode.value = "";
				fm.HospitalName.value = "";
				
				fm.AccProvince.value = "";
				fm.AccProvinceName.value = "";
				fm.AccCity.value = "";
				fm.AccCityName.value = "";
				fm.AccCounty.value = "";
				fm.AccCountyName.value = "";
				fm.AccSite.value = "";							
				return false;
			} else {
				
				if (tArr.length==1) {
					fm.HospitalCode.value = tArr[0][0];
					fm.HospitalName.value = tArr[0][1];
					
					fm.AccProvince.value = tArr[0][2];
					fm.AccProvinceName.value = tArr[0][3];
					fm.AccCity.value = tArr[0][4];
					fm.AccCityName.value = tArr[0][5];
					fm.AccCounty.value = tArr[0][6];
					fm.AccCountyName.value = tArr[0][7];
					fm.AccSite.value = tArr[0][8];
				}		
			}
		}
	} catch (ex){
		
		alert(ex);
	}
}
function initAreaData(tFlag) {
	
	if (tFlag==1) {
		fm.AccCity.value = "";
		fm.AccCityName.value = "";
		fm.AccCountyName.value = "";
		fm.AccCounty.value = "";		
	} else if (tFlag==2) {
		fm.AccCountyName.value = "";
		fm.AccCounty.value = "";		
	}
}
//չʾ��ʾ��Ϣ
function showWarnInfo() {
		
	alert('��¼��[Ͷ��������]����ģ����ѯ��');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
/**
 * ����У��¼����
 */
function checkMoney(tObject) {

	var tValue = tObject.value;
	if(((!/^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(tValue)) && (!tValue==""))){
		
		alert("��������ȷ�Ľ�") ;
		tObject.className = "warn" ;
		tObject.focus();
		tObject.value="";		
	}
}