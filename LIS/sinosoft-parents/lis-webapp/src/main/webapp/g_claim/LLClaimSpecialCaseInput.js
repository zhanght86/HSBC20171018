/***************************************************************
 * <p>ProName��LLClaimSpecialCaseInput.js</p>
 * <p>Title�����ⰸ��¼��</p>
 * <p>Description�����ⰸ��¼��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tQueryFlag = false;
var tQueryResultFlag = false;
var tSQLInfo;

//=======================��ʼ��ҳ��========================
/**
 * ��ѯ������Ϣ
 */
function queryAcceptInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.GrpRgtNo.value = tArr[0][0];		
		fm.RgtClass.value = tArr[0][1];
		fm.RgtClassName.value = tArr[0][2];
		
		if (fm.RgtClass.value=="1") {
			$("#appntinfo").hide();
			$("#appntvalueinfo").hide();
//			$("#risklevel").hide();
//			$("#risklevelname").hide();
			fm.AppntNo.value = "";
			fm.AppntName.value = "";	
//			fm.RiskLevel.value = "";
		} else if (fm.RgtClass.value=="2") {
			$("#appntinfo").show();
			$("#appntvalueinfo").show();
//			$("#risklevel").show();
//			$("#risklevelname").show();
			fm.AppntNo.value = tArr[0][3];
			fm.AppntName.value = tArr[0][4];
//			if (tArr[0][13]==null || tArr[0][13]==""){
//				fm.RiskLevel.value = "";
//			}else{
//				fm.RiskLevel.value = tArr[0][13];
//			}
		}
		
		fm.AppDate.value = tArr[0][5];
		fm.AcceptDate.value = tArr[0][6];
		fm.PageNo.value = tArr[0][7];
		fm.AcceptOperator.value = tArr[0][8];
		fm.AcceptCom.value = tArr[0][9];
		fm.SelfAcceptCom.value = tArr[0][9];
		fm.AcceptComName.value = tArr[0][10];
		
		fm.CaseType.value = tArr[0][11];
		fm.CaseTypeName.value = tArr[0][12];
	} else {
		alert("�ⰸ������Ϣ��ѯʧ�ܣ�");
		return false;		
	}	
}
/**
 * ��ѯ��ά���Ŀͻ���Ϣ�б�
 * ��������һ��ֻ��������һ��������
 * ����ʷ������¼�룬���ѯ��ʷ�ⰸ��������Ϣ
 */
function queryCustomerInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql4");
	
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("��������Ϣ������!");
		return false;
	}else {
		//�����ԭ�е�����
		initCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		
		fm.ApplyRemarks.value = tArr[0][9];
		fm.RgtNo.value = tArr[0][10];
		
		if (fm.CaseType.value == "12") {
			//fm.ErrorStation.style.display="";
			//fm.ErrorStationName.style.display="";
			document.getElementById("ErrorStation").style.display="";
			document.getElementById("ErrorStationInfo").style.display="";
			fm.ErrorStation.value = tArr[0][11];
			fm.ErrorStationName.value = tArr[0][12];			
		} else {
			//fm.ErrorStation.style.display="none";
			//fm.ErrorStationName.style.display="none";
			document.getElementById("ErrorStation").style.display="none";
			document.getElementById("ErrorStationInfo").style.display="none";
			fm.ErrorStation.value = "";
			fm.ErrorStationName.value = "";						
		}		
		
		fm.HistoryRgtNo.value = tArr[0][13];
		
		if(fm.HistoryRgtNo.value==null || fm.HistoryRgtNo.value=="") {
			fm.ClaimFlag.value = "0";
			fm.ClaimFlagName.value = "��";
			$("#HistoryRgtNo").hide();
			$("#HistoryRgtNoInfo").hide();	
		} else {
			fm.ClaimFlag.value = "1";
			fm.ClaimFlagName.value = "��";			
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
		}
		fm.SelfAppntNo.value = tArr[0][14];
		fm.SelfAppntName.value = tArr[0][15];
		fm.TelPhone.value = tArr[0][16];
	}
}

//=======================����������Ϣ========================

//����
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
				
		var tAcceptCom = fm.AcceptCom.value;
		if (tAcceptCom==null || tAcceptCom=="") {
			alert("����ѡ�����������");
			fm.AcceptCom.className = "warnno";
			fm.AcceptCom.focus();
			return false;
		} else {
			
			if (!checkAcceptCom()) {
				return false;
			}
		}
		
		var tAppntName = tObjectValue;		
		
		if (tAppntName==null || tAppntName=="") {
			alert("��¼��Ͷ�������ƣ�");
			tObject.focus();
			return false;
		}		
		
		//����ʱ��ѯ����Ͷ���˱�
		if (fm.RgtClass.value=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
			tSQLInfo.setSqlId("LLClaimSpecialCaseSql6");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tAcceptCom);
		} else if (fm.RgtClass.value=="2") {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
			tSQLInfo.setSqlId("LLClaimSpecialCaseSql");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tAcceptCom);
						
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.AppntType.value = "";				
			fm.AppntNo.value = "";
			fm.AppntName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AppntType.value = tArr[0][0];				
				fm.AppntNo.value = tArr[0][1];
				fm.AppntName.value = tArr[0][2];
//				showRiskLevel();
			} else {
				showAppntList();			
			}
		}		
	}
if (tObjectName=="BankCodeName") {
		
		var tBankName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimOutCaseSql");
		tSQLInfo.setSqlId("LLClaimOutCaseSql10");
		tSQLInfo.addSubPara(tBankName);
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.BankCode.value = "";
			fm.BankCodeName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.BankCode.value = tArr[0][0];
				fm.BankCodeName.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and headbankname like #%"+ tBankName +"%# and managecom like #"+mManageCom+"%#";
				showCodeList('headbank', [fm.BankCode,fm.BankCodeName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}	
	
}
/**
 * �򿪲�ѯ��ϵͳ����Ͷ����ѡ��ҳ��
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.AppntName.value+"&ContType="+fm.RgtClass.value+"&AcceptCom="+fm.AcceptCom.value;
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
//	showRiskLevel();
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
/**
 * ��ѯͶ���������еĸ����ⰸ��Ϣ
 */
function queryPastCaseInfo() {
	
	if(fm.ClaimFlag.value=="0") {
		fm.HistoryRgtNo.value = "";
		return;
	}
	var tHistoryRgtNo = fm.HistoryRgtNo.value;
	if (tHistoryRgtNo==null || tHistoryRgtNo=="") {
		alert("����¼����ʷ�ⰸ��");
		return false;
	}
	if (fm.GrpRgtNo.value=="01") {
		var tAppntNo = fm.AppntNo.value;
		if (tAppntNo==null || tAppntNo=="") {
			alert("����¼��Ͷ������Ϣ");
			return false;
		}		
	}	
	/* ��ʱ��������ͷ�޸�
	var strUrl="./LLClaimQueryPastCaseMain.jsp?AppntNo="+fm.AppntNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"Ͷ������ʷ�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	
	*/
	//���ݻ�õ���ʷ�ⰸ�Ų�ѯ��������ϸ�������ҳ��
	if (!queryOldCustomerInfo()) {
		return false;
	}
}
/**
 * ���ݰ����Ų�ѯ��ʷ�ⰸ�ĳ�������Ϣ
 */
function queryOldCustomerInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql7");
	
	tSQLInfo.addSubPara(fm.HistoryRgtNo.value);	
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("����ʷ�ⰸ�³����˲����ڣ�����!");
		return false;
	}else {
		//�����ԭ�е�����
		initCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		fm.SelfAppntNo.value = tArr[0][9];
		fm.SelfAppntName.value = tArr[0][10];
	}
}
/**
 * ����
 */
function saveSpecialCase() {
	
	if(!checkSaveInfo()) {
		return false;
	}		
	fm.Operate.value="CASEINSERT";
	submitForm();	
}

function checkSaveInfo() {
	
	//У��������Ϣ
	if(!notNull(fm.RgtClass,"��������")){return false;};
	var tRgtClass = fm.RgtClass.value;
	//�ŵ���Ͷ������Ϣ��¼����������
	if (tRgtClass=="01") {
		if(!notNull(fm.AppntNo,"Ͷ�������Ʊ���")){return false;};
		if(!notNull(fm.AppntName,"Ͷ��������")){return false;};
	}
	if(!notNull(fm.AppDate,"������������")){return false;};
	if(!notNull(fm.AcceptCom,"�������")){return false;};
	if(!notNull(fm.PageNo,"������ת��")){return false;};
	
	//У���������
	if (!checkAcceptCom()) {
		return false;
	}	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql9");
	tSQLInfo.addSubPara(fm.PageNo.value);			
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length!=1) {
		alert("��¼��Ľ�����ת�Ų����ڣ�������¼�룡");
		return false;
	}	
	if(!notNull(fm.CaseType,"��������")){return false;};
	if(!notNull(fm.ClaimFlag,"�Ƿ�����������")){return false;};
	if (fm.ClaimFlag.vlaue=="1") {
		if(!notNull(fm.HistoryRgtNo,"ԭ������")){return false;};
	}	
	//У���������Ϣ
	var tRgtClass = fm.RgtClass.value;
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[����������]")){return false;};
		if(!notNull(fm.Birthday,"[����������]")){return false;};
		if(!notNull(fm.IDNo,"[֤������]")){return false;};
		if(!notNull(fm.IDType,"[֤������]")){return false;};
		if(!notNull(fm.Gender,"[�Ա�]")){return false;};
	}
	var tTelphone = fm.TelPhone.value;
	//alert(fm.TelPhone.value);
	queryCustomer();
	if(!tQueryResultFlag){
		alert("�����������ĳ����ˣ�����");
		return false;
	}
	fm.TelPhone.value = tTelphone;
	if (fm.CaseType.value == "12") {
		if(!notNull(fm.ErrorStation,"������θ�")){return false;};
	}
	if(!notNull(fm.ApplyRemarks,"����ԭ��")){return false;};
	
	//У�鱻����״̬�Ƿ���Ч���Ƿ������Ч����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql3");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("��ѡ��Ŀͻ���������Ч���������飡");
		return false;
	}
	if(fm.ClaimFlag.value=="1"){
		if(!notNull(fm.HistoryRgtNo,"ԭ������")){return false;};            
		}
//У��ó����˳б��ı����Ƿ��Ѿ��ڱ��γ�����---����ڻ������
	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
//	tSQLInfo.addSubPara(mGrpRegisterNo);
//	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
//	var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
//	if (tExistsArr1!=null && tExistsArr1.length>0 ) {
		
//		alert("��ѡ��Ŀͻ����а��ı����Ѿ������ڱ����ⰸ��,��ѡ�������ͻ���");
//		return false;
//	}	

/*
//У��������Ƿ����δȷ�ϵı�ȫ
	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
//	tSQLInfo.addSubPara(mGrpRegisterNo);
//	tSQLInfo.addSubPara(fm.CustomerNo.value);		
//  var tSQL = "select 1 from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edorstate!='0'";
//	var arr = easyExecSql(tSQL);
//	if (arr!=null && arr.length>0) {
		
//			alert("�ó�������δȷ�ϱ�ȫ���뱣ȫȷ�Ϻ������⣡");
//			return false;
//	}	
	
	//add by lixf----����У�飬���ڱ�ȫɨ��¼��������δ¼�����ȷ��ǰ������ҵ������������ݵģ����Ǳ�ȫ��ϸ����������
	//������У���޷�У����ñ��������Ƿ���ڱ�ȫ,��ֻ�б�ȫ�����Ż��������������ʴ�У������������У��֮��------------start	
  var tCheckSQL = "select 1 from lccont where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and state is null ";
	var tCheckArr = easyExecSql(tCheckSQL);
	if (tCheckArr!=null && tCheckArr.length>0) {
		
			alert("�ó�������δȷ�ϵ�ɨ�豣ȫ���뱣ȫȷ�Ϻ������⣡");
			return false;
	}	
	//---------------------------end
*/
	
/*	
	//2.������У���Ƿ�����
	var tWanSql = "select 1 from lcgrppol a ,lmriskapp b where a.riskcode=b.riskcode and b.risktype3='4' and a.grpcontno='"+ tGrpContNo +"'" ;
	var tIsWan = easyExecSql(tWanSql);
	if(tIsWan!=null && tIsWan.length!=0){
			
		var tDeadSql = " select 1 from lcinsured where insuredno='"+ tCustomerNo +"' and insuredstat='9' ";
		var tIsDead = easyExecSql(tDeadSql);
		if(tIsDead!=null && tIsDead.length!=0){
			alert("������Ϊ�����գ������˲���ѡ���Ѿ������ı�������");
			resetInfo();
			return false;
		}
	}
*/	
/*
	//3.У���Ƿ�����ˡ����˶�����
	var tIsLockSql = " select insuredstat from lcinsured where insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' ";
	var tIsLock = easyExecSql(tIsLockSql);
	if (tIsLock!=null && tIsLock[0][0]=="8") {//insuredstat=8������ְ����ʱ�Ż��ж��Ƿ�����
		
		var tIsExist = "select State from LLRegisterLock where GrpContNo='"+ tGrpContNo +"' and ContNo='"+ tContNo +"' and InsuredNo='"+ tCustomerNo +"' order by applydate, applytime desc";
		var tUnlock = easyExecSql(tIsExist);
		if (tUnlock==null || tUnlock.length<=0) {
			alert("�����������˴�����滻�����ѱ�������");
			resetInfo();
			return false;	
		}else if(tUnlock[0][0] == "1" || tUnlock[0][0] == "3") {
			alert("�����������˴�����滻�����ѱ�������");
			resetInfo();
	  	return false;
		}		
	}
*/
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
	if (fm.RgtClass.value=="2") {
		if (fm.AppntNo.value==null || fm.AppntNo.value=="") {
			alert("����ѡ��Ͷ����");
			fm.AppntName.focus();
			fm.AppntName.style.background = "#ffb900";
			return false;
		}		
	}	
	
	//У���������
	if (!checkAcceptCom()) {
		return false;
	}	
	
	var tCustomerName = fm.CustomerName.value;
	var tBirthday = fm.Birthday.value;
	var tEmployeNo = fm.EmployeNo.value;
	var tIDNo = fm.IDNo.value;
	if (tCustomerName!=null && tCustomerName!="") {

		if (tBirthday!=null && tBirthday!="") {
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

		if(confirm("ϵͳ�в����ڸó����ˣ��Ƿ����δ����ͻ�¼��?")) {
			
			noAcceptInfo();
		} else {
			return false;
		}
	}
}
function queryCustomer(tFlag) {	
	
	tQueryFlag = true;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql2");
	
	//����������������ò�ѯ
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
		tSQLInfo.setSqlId("LLClaimSpecialCaseSql8");
	}	
		
	if (tFlag==1) {

		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");
	} else if (tFlag==2) {
			
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara(fm.EmployeNo.value);
		tSQLInfo.addSubPara("");				
	} else if (tFlag==3) {
		
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara("");					
		tSQLInfo.addSubPara("");			
		tSQLInfo.addSubPara(fm.IDNo.value);		
	} else {
		
		tSQLInfo.addSubPara(fm.CustomerName.value);
		tSQLInfo.addSubPara(fm.Birthday.value);					
		tSQLInfo.addSubPara(fm.EmployeNo.value);			
		tSQLInfo.addSubPara(fm.IDNo.value);			
	}
	tSQLInfo.addSubPara(fm.AppntNo.value);
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfAcceptCom.value);
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		
		return false;
	} else {
		
		tQueryResultFlag = true;
		if (tArr.length==1) {
			
			fm.CustomerNo.value = tArr[0][0];
			fm.CustomerName.value = tArr[0][1];
			fm.Birthday.value = tArr[0][2];
			fm.EmployeNo.value = tArr[0][3];
			fm.IDNo.value = tArr[0][6];
			fm.IDType.value = tArr[0][4];
			fm.IDTypeName.value = tArr[0][5];			
			fm.Gender.value = tArr[0][7];
			fm.GenderName.value = tArr[0][8];
			fm.SelfAppntNo.value = tArr[0][9];
			fm.SelfAppntName.value = tArr[0][10];
			fm.TelPhone.value = tArr[0][11];
			return true;
		} else {
			var tOpenFlag = false;
			for (var i=0;i<tArr.length;i++) {
													
				if (tArr[i][0]!=tArr[0][0] || tArr[i][9]!=tArr[0][9]) {
					tOpenFlag = true;
					break;
				}
			}
			if (tOpenFlag) {
				showCustomerList();
			} else {
				
				fm.CustomerNo.value = tArr[0][0];
				fm.CustomerName.value = tArr[0][1];
				fm.Birthday.value = tArr[0][2];
				fm.EmployeNo.value = tArr[0][3];
				fm.IDNo.value = tArr[0][6];
				fm.IDType.value = tArr[0][4];
				fm.IDTypeName.value = tArr[0][5];			
				fm.Gender.value = tArr[0][7];
				fm.GenderName.value = tArr[0][8];
				fm.SelfAppntNo.value = tArr[0][9];
				fm.SelfAppntName.value = tArr[0][10];
				fm.TelPhone.value = tArr[0][11];
			}	
		}
	}	
}
//չʾ�ͻ��б�--����ͬһͶ������ͬ��ͬ���ջ�ͬ��ͬԱ���Ż�ͬһ֤������
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmployeNo.value+"&IDNo="+fm.IDNo.value;
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
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
	fm.EmployeNo.value = tQueryResult[7];
	fm.IDType.value = tQueryResult[8];
	fm.IDTypeName.value = tQueryResult[9];	
	fm.IDNo.value = tQueryResult[10];	
	fm.TelPhone.value = tQueryResult[14];

	self.focus();
}
/**
 * �����Ǽ�
 */
function caseAccept() {
	
	//mode :ҳ�����ͣ�0-�ɲ�����1-�鿴��2-��������
	
	var strUrl="./LLClaimSpecialCaseMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&Mode=2";
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�����Ǽ�",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}

/**
 * �������
 */
function caseOver() {
	
	if (fm.GrpRgtNo.value == null || fm.GrpRgtNo.value == "") {
		alert("���ȱ���������Ϣ��");
		return false;
	}
	//ͨ�ڡ����߰�����δ��������ı�����������Ǽ�
	var tCaseType = fm.CaseType.value;
	var tClaimFlag = fm.ClaimFlag.value;
	if (tCaseType!=null && (tCaseType=="13" || tCaseType=="11") && tClaimFlag!=null && tClaimFlag=="0") {
		
		if(!checkCaseOver()) {
			return false;
		}		
	}		
	fm.Operate.value="CASEOVER";
	submitForm();		
}

/**
 * �������ǰУ��
 */
function checkCaseOver() {
	
	//У���Ƿ�¼�����¼���Ϣ����У���Ƿ�����������Ǽ�
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql5");
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.RgtNo.value);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("���Ƚ��������Ǽǣ�");
		return false;
	}
	//У���Ƿ���ڳ�����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql18");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistsArr==null || tExistsArr.length<=0 ) {
		
		alert("��Ϊ����������������¼��һ������������ˣ�");
		return false;
	} else {
		
		for (var i=0;i<tExistsArr.length;i++) {
			
			var tRgtNo = tExistsArr[i][0];
			var tCustomerNo = tExistsArr[i][1];
			var tCustomerName = tExistsArr[i][2];
			//У����������������Ƿ�¼�����¼���Ϣ
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql19");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);
			var tCaseArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tCaseArr==null || tCaseArr.length<=0) {
				alert("������'"+tCustomerName+"'δ¼���¼���Ϣ�����飡");
				return false;				
			}
			
			//У������¼��Ƿ�����˵����ɵ���δ�޸��¼���
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql21");
			tSQLInfo.addSubPara(tRgtNo);
			tSQLInfo.addSubPara(tCustomerNo);
			var tCaseArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tCaseArr!=null && tCaseArr.length>0) {
				alert("������'"+tCustomerName+"'�����˵����ɵ��¼������޸ĸ��¼���Ϣ�Բ�ȫ�¼���Ϣ�����飡");
				return false;				
			}			
		}
	}
	return true;		
}

/**
 * ����
 */
function goBack() {
	
	if (mMode==1) {
		top.close();
	} else {
		window.location.href="./LLClaimSpecialAppInput.jsp";
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
function afterSubmit(FlagStr, content, tGrpRgtNo, tRgtNo) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
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

	showInfo.focus();
		if (fm.Operate.value=="CASEINSERT") {
			
			mGrpRegisterNo = tGrpRgtNo;
			window.location.href="LLClaimSpecialCaseInput.jsp?GrpRgtNo="+tGrpRgtNo;
		} else if (fm.Operate.value=="CASEOVER") {
			goBack();
		}		
	}	
}
//====================ϵͳ===================//
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
/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == 'casetype') {
		
		//��������ѡ��
		if(fm.CaseType.value == "11" || fm.CaseType.value=="12"){
			
			fm.ClaimFlag.value="1";
			fm.ClaimFlagName.value="��";
			
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
							
			document.getElementById("ClaimFlag").setAttribute('readOnly',true);
			document.getElementById("ClaimFlagName").setAttribute('readOnly',true);
			

			if (fm.CaseType.value == "12") {
				
				$("#ErrorStation").show();
				$("#ErrorStationInfo").show();
			} else {
				
				fm.ErrorStation.value="";
				fm.ErrorStationName.value = "";
				$("#ErrorStation").hide();
				$("#ErrorStationInfo").hide();
			}			
			document.getElementById("HistoryRgtNo").style.display="";
			document.getElementById("HistoryRgtNoInfo").style.display="";
		} else {
			fm.ClaimFlag.value="";
			fm.ClaimFlagName.value="";													
			
			$("#ErrorStation").hide();
			$("#ErrorStationInfo").hide();			
			document.getElementById("HistoryRgtNo").style.display="none";
			document.getElementById("HistoryRgtNoInfo").style.display="none";
		}
	} else if(cCodeName == 'trueflag') {
		
		//��������ѡ��
		if(fm.ClaimFlag.value == "0"){
			
			if (fm.CaseType.value=="12") {
				
				fm.ClaimFlag.value = "1";
				fm.ClaimFlagName.value = "��";
				alert("��������������ʷ�ⰸ��");
				return false;
			}
			$("#HistoryRgtNo").hide();
			$("#HistoryRgtNoInfo").hide();	
			//fm.HistoryRgtNo.value="";
		} else {
			$("#HistoryRgtNo").show();
			$("#HistoryRgtNoInfo").show();
		}
	} else if (cCodeName == "conttype"){
			
			var tRgtClass = fm.RgtClass.value;
			if (tRgtClass=="02") {
				
				fm.AppntNo.value = "";
				fm.AppntName.value = "";
//				fm.RiskLevel.value = "";
				$("#appntinfo").hide();
				$("#appntvalueinfo").hide();
//				$("#risklevel").hide();
//				$("#risklevelname").hide();
			} else {
				$("#appntinfo").show();
				$("#appntvalueinfo").show();	
//				$("#risklevel").show();
//				$("#risklevelname").show();
			}
		}	
				
}
//չʾ��ʾ��Ϣ
function showWarnInfo() {
		
	alert('��¼��[Ͷ��������]��س�����(֧��ģ����ѯ)��');
	fm.AppntName.focus();
	fm.AppntName.style.background = "#ffb900";
}
//У���������
function checkAcceptCom() {

	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpecialCaseSql");
	tSQLInfo.setSqlId("LLClaimSpecialCaseSql10");
	tSQLInfo.addSubPara(fm.AcceptCom.value);
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
		
		alert("��������������ܹ�˾��ֹ�˾��������ѡ��");
		fm.AcceptCom.focus();
		fm.AcceptCom.className = "warnno";
		return false;
	}
	return true;
}


/**
 * �ͻ����յȼ�չʾ
 */
//function showRiskLevel(){
//	
//	var tAppntNo = fm.AppntNo.value;		
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
//	tSQLInfo.setSqlId("LLClaimCommonQuerySql27");
//	tSQLInfo.addSubPara(tAppntNo);
//	var tResult = easyExecSql(tSQLInfo.getString());
//	if(tResult==null||tResult==""){
//		fm.RiskLevel.value = "";
//	}else {
//		fm.RiskLevel.value = tResult;
//	}
//}