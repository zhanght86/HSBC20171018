/***************************************************************
 * <p>ProName��LLClaimCustomerInput.jsp</p>
 * <p>Title�������Ǽ�ͨ��</p>
 * <p>Description�������Ǽ�ͨ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/

var tResultFlag=false;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tPageNo = 1;//��¼��ѯҳ��
var tSelNo = 1;//��¼��ѯ���
var tQueryFlag = false;
var tQueryResultFlag = false;

//У��������Ϣ
function checkAcceptInfo() {
	
	if(!notNull(fm.RgtClass,"��������")){return false;};
	var tRgtClass = fm.RgtClass.value;
	//�ŵ���Ͷ������Ϣ��¼����������
	if (tRgtClass=="01") {
		if (fm.AppntNo.value==null || fm.AppntNo.value=="") {
			alert("δѡ��Ͷ���ˣ������롾Ͷ�������ơ�����ģ����ѯ");
			fm.AppntName.focus();
			fm.AppntName.style.background="#ffb900";
			return false;
		}
	}
	if(!notNull(fm.AppntName,"Ͷ��������")){return false;};
	if(!notNull(fm.AppDate,"������������")){return false;};
	var tAppDate = fm.AppDate.value;
	if (dateDiff(tAppDate,mCurrentDate,'D')<0) {	
		
		alert("�����������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}	
	if(!notNull(fm.PageNo,"������ת��")){return false;};
	
	//У���������
	if (!checkAcceptCom()) {
		return false;
	}	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql20");
	tSQLInfo.addSubPara(fm.PageNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length!=1) {
		alert("��¼��Ľ�����ת�Ų����ڣ�������¼�룡");
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
		
		//������ѯ
		if (tContType!=null && tContType=="1") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql17");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(mManageCom);
			tSQLInfo.addSubPara(tAcceptCom);
		} else if(tContType=="2") {//�ŵ���ѯ
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
			tSQLInfo.setSqlId("LLClaimCommonQuerySql");
			tSQLInfo.addSubPara(tAppntName);
			tSQLInfo.addSubPara(tAcceptCom);
//			tSQLInfo.addSubPara(tAppntName);
//			tSQLInfo.addSubPara(mManageCom);			
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
			fm.HospitalCode.value = "";
			fm.HospitalName.value = "";			
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.HospitalCode.value = tArr[0][0];
				fm.HospitalName.value = tArr[0][1];
				
				
				fm.AccSite.value = tArr[0][2];
			} else {
				var tCodeCondition = "1 and hospitalname like #%"+ tHospitalName +"%# and hosstate=#1# ";
				showCodeList('hospital', [fm.HospitalCode,fm.HospitalName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult1Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql11");
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("1");
		tSQLInfo.addSubPara("");
		
		if (fm.AccResult1Name.value==null || fm.AccResult1Name.value=="") {
			alert("��Ҫ��ϲ���Ϊ��");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			fm.AccResult2.value = "";
			fm.AccResult2Name.value = "";
			return false;
		} else {
			fm.AccResult2.value="";
			fm.AccResult2Name.value="";
			if (tArr.length==1) {
				fm.AccResult1.value = tArr[0][0];
				fm.AccResult1Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and icdlevel=#1#  ";
				showCodeList('diseasecode', [fm.AccResult1,fm.AccResult1Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}
	if (tObjectName=="AccResult2Name") {
		var tAccResultName = tObjectValue;		
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql11");		
		tSQLInfo.addSubPara(tAccResultName);
		tSQLInfo.addSubPara("2");
		tSQLInfo.addSubPara(fm.AccResult1.value);
		
		if (fm.AccResult1.value==null || fm.AccResult1.value=="") {
			alert("δѡ����Ҫ��ϣ������롾��Ҫ������ơ�����ģ����ѯ");
			fm.AccResult1Name.focus();
			fm.AccResult1Name.style.background="#ffb900";
			return false;
		}
		if (fm.AccResult2Name.value==null || fm.AccResult2Name.value=="") {
			alert("������鲻��Ϊ��");
			return false;
		}
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.AccResult2.value = tArr[0][0];
				fm.AccResult2Name.value = tArr[0][1];
			} else {
				var tCodeCondition = "1 and icdname like #%"+ tAccResultName +"%# and upicdcode=#"+ fm.AccResult1.value +"# and icdlevel=#2#";
				showCodeList('diseasecode', [fm.AccResult2,fm.AccResult2Name], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	} else if (tObjectName=="BankCodeName") {
		
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
				var tCodeCondition = "1 and headbankname like #%"+ tBankName +"%# ";
				showCodeList('headbank', [fm.BankCode,fm.BankCodeName], [0,1], null,tCodeCondition, "1", 1, '400');
			}
		}
	}	
	
}

/**
 * �ͻ����յȼ�չʾ
 *//*
function showRiskLevel(){
	
	var tAppntNo = fm.AppntNo.value;		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql27");
	tSQLInfo.addSubPara(tAppntNo);
	var tResult = easyExecSql(tSQLInfo.getString());
	if(tResult==null||tResult==""){
		fm.RiskLevel.value = "";
	}else {
		fm.RiskLevel.value = tResult;
	}
}*/
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

}
function queryAcceptInfo() {
		
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql1");
	tSQLInfo.addSubPara(mGrpRegisterNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length==1) {
					
		fm.GrpRgtNo.value = tArr[0][0];		
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
		fm.AppDate.value = tArr[0][5];
		fm.AcceptDate.value = tArr[0][6];
		fm.PageNo.value = tArr[0][7];
		fm.AcceptOperator.value = tArr[0][8];
		fm.AcceptCom.value = tArr[0][9];
		fm.SelfAcceptCom.value = tArr[0][9];
		fm.AcceptComName.value = tArr[0][10];
		fm.AcceptOperatorName.value = tArr[0][11];
		fm.AppntType.value = tArr[0][12];
		
	}
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
	var tGrpRgtNo = fm.GrpRgtNo.value;
	if (tGrpRgtNo==null || tGrpRgtNo=="" || tGrpRgtNo=="null") {
		alert("���ȱ�������������Ϣ");
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
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql2");
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	
	//����������������ò�ѯ
	var tContType = fm.RgtClass.value;
	if (tContType!=null && tContType=="02") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql16");
		tSQLInfo.addSubPara(fm.AppntNo.value);
		
	}	
	
	var tEmployeNo = fm.EmployeNo.value ;
	if(tEmployeNo!=null && tEmployeNo!="" && tEmployeNo.indexOf("where",0)>=0){
		fm.EmployeNo.value = "";
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
	if (fm.CustomerNo.value !=null && fm.CustomerNo.value!="") {
		tSQLInfo.addSubPara(fm.CustomerNo.value);
	} else {
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.SelfAcceptCom.value);		
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null || tArr.length==0) {
		tQueryResultFlag = false;		
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
	return true;
}
//չʾ�ͻ��б�--����ͬһͶ������ͬ��ͬ���ջ�ͬ��ͬԱ���Ż�ͬһ֤������
function showCustomerList() {
	
	var strUrl="./LLClaimQueryMain.jsp?GrpRgtNo="+fm.GrpRgtNo.value+"&AppntNo="+fm.AppntNo.value+"&CustomerName="+fm.CustomerName.value+"&Birthday="+fm.Birthday.value+"&EmpNo="+fm.EmployeNo.value+"&IDNo="+fm.IDNo.value;
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
 * ��ò�ѯ���ı����������˻���Ϣ
 */
function afterQueryAccInfo(tQueryResult) {
	
	fm.BankCode.value = tQueryResult[7];
	fm.BankCodeName.value = tQueryResult[8];
	fm.Province.value = tQueryResult[3];
	fm.ProvinceName.value = tQueryResult[4];		
	fm.City.value = tQueryResult[5];	
	fm.CityName.value = tQueryResult[6];	
	fm.AccName.value = tQueryResult[9];
	fm.BankAccNo.value = tQueryResult[10];

	self.focus();
}

/**
 * ��ѯ��ά���Ŀͻ���Ϣ�б�
 */
function queryCustomerList() {
	if(mMode=="2"){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql22");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);
		
	}else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql5");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		
		turnPage1.queryModal(tSQLInfo.getString(),CustomerListGrid,2);	
	}
}
/**
 * չʾѡ�еĿͻ���Ϣ
 */
function showSelectCustomer() {
	var i = CustomerListGrid.getSelNo()-1;
	var tRegisterNo = CustomerListGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(i,2);
	
	fm.CustomerNo.value = tCustomerNo;
	fm.RegisterNo.value = tRegisterNo;
	fm.SelNo.value = CustomerListGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage1.pageIndex;//add 2011-11-29
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql6");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(mGrpRegisterNo);
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("��������Ϣ������!");
		return false;
	}else {
		//�����ԭ�е�����
		initCustomerInfo();
		disableCustomerInfo();
		fm.CustomerNo.value = tArr[0][0];
		fm.CustomerName.value = tArr[0][1];	
		fm.Birthday.value = tArr[0][2];
		fm.EmployeNo.value = tArr[0][3];
		fm.IDNo.value = tArr[0][4];
		fm.IDType.value = tArr[0][5];
		fm.IDTypeName.value = tArr[0][6];
		fm.Gender.value = tArr[0][7];
		fm.GenderName.value = tArr[0][8];
		fm.AppAmnt.value = tArr[0][9];
		fm.BillCount.value = tArr[0][10];
		fm.ScanCount.value = tArr[0][11];
		fm.IsUrgent.value = tArr[0][12];
		fm.IsUrgentName.value = tArr[0][13];
		fm.IsOpenBillFlag.value = tArr[0][14];
		fm.IsOpenBillFlagName.value = tArr[0][15];
		fm.IsBackBill.value = tArr[0][16];
		fm.IsBackBillName.value = tArr[0][17];
		fm.BankCode.value = tArr[0][18];
		fm.BankCodeName.value = tArr[0][19];
		fm.Province.value = tArr[0][20];
		fm.ProvinceName.value = tArr[0][21];
		fm.City.value = tArr[0][22];	
		fm.CityName.value = tArr[0][23];	
		fm.BankAccNo.value = tArr[0][24];	
		fm.AccName.value = tArr[0][25];	
		fm.CustomerAppDate.value = tArr[0][26];
		fm.Remark.value = tArr[0][27];
		fm.RegisterNo.value = tArr[0][28];
		fm.SelfAppntNo.value = tArr[0][29];
		fm.SelfAppntName.value = tArr[0][30];
		fm.TelPhone.value = tArr[0][31];
	}
	queryCustomerCaseList();
}
/**
 * У��ͻ���Ϣ--�Ƿ������������
 */
function checkCustomerInfo() {			
	
	if (fm.CustomerNo.value==null || fm.CustomerNo.value=="") {
		
		if(!notNull(fm.CustomerName,"[����������]")){return false;};
		if(!notNull(fm.Birthday,"[����������]")){return false;};
		if(!notNull(fm.IDNo,"[֤������]")){return false;};
		if(!notNull(fm.IDType,"[֤������]")){return false;};
		if(!notNull(fm.Gender,"[�Ա�]")){return false;};
	}
	if( fm.TelPhone.value!=null ||  fm.TelPhone.value!=""){
		var tTelPhone = fm.TelPhone.value;
	}
	var tRgtClass = fm.RgtClass.value;
	//�ŵ���Ͷ������Ϣ��¼����������
	
	queryCustomer();
	if(!tQueryResultFlag){
		alert("�����������ĳ����ˣ�����");
		return false;
	}		
	fm.TelPhone.value = tTelPhone;
	if(!notNull(fm.AppAmnt,"[������]")){return false;};
	if(!notNull(fm.BillCount,"[��Ʊ��]")){return false;};
	if(!notNull(fm.IsUrgent,"[�Ƿ�Ӽ�]")){return false;};
	if(!notNull(fm.IsOpenBillFlag,"[�Ƿ񿪾ݷָ]")){return false;};
	if(!notNull(fm.IsBackBill,"[�Ƿ��˻ط�Ʊ]")){return false;};
	
	if (fm.IsOpenBillFlag.value == "1" && fm.IsBackBill.value == "1") {
		alert("[�Ƿ񿪾ݷָ]��[�Ƿ��˻ط�Ʊ]����ͬʱΪ'��'��");
		return false;
	}
	
	//У��ó������Ƿ��Ѿ��ڱ��γ�����--�޸Ĳ���ʱ����
	if (fm.Operate.value=="CUSTOMERINSERT") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql3");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		tSQLInfo.addSubPara(fm.CustomerNo.value);	
		
		var tExistsArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tExistsArr!=null && tExistsArr.length>0 ) {
			
			alert("��ѡ��Ŀͻ��Ѿ������ڱ����ⰸ��,��ѡ�������ͻ���");
			return false;
		}		
	}
	//У�鱻����״̬�Ƿ���Ч���Ƿ������Ч����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql4");
	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length==0 ) {
		
		alert("��ѡ��Ŀͻ���������Ч���������飡");
		return false;
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
	
	if(fm.BankCode.value!="" || fm.Province.value!=""
			|| fm.City.value!="" || fm.BankAccNo.value!=""
				|| fm.AccName.value!=""){
	
		if(!notNull(fm.BankCode,"�˻���Ϣ���ɲ���¼�룬[����������ʡ]")){return false;};
		if(!notNull(fm.Province,"�˻���Ϣ���ɲ���¼�룬[����������ʡ]")){return false;};
		if(!notNull(fm.City,"�˻���Ϣ���ɲ���¼�룬[������������]")){return false;};
		if(!notNull(fm.BankAccNo,"�˻���Ϣ���ɲ���¼�룬[�˺�]")){return false;};
		if(!notNull(fm.AccName,"�˻���Ϣ���ɲ���¼�룬[�˻���]")){return false;};			
	}
	
	var tCustomerAppDate = fm.CustomerAppDate.value;	
	if (tCustomerAppDate!=null && tCustomerAppDate!="") {
		
		if (dateDiff(tCustomerAppDate,mCurrentDate,'D')<0) {	
			
			alert("[�ͻ���������]�������ڵ�ǰ���ڣ�");
			return false;
		}		
	}
	
	return true;
}
/**
 * �������ǰУ��
 */
function checkBeforeOver() {
	
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
 * Ĭ��ѡ�иղ����ļ�¼
 */
function setSelRow(ObjGrid,tTurnPage){			
	
 	var tPageIndex = fm.PageIndex.value;
 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	var tSelNo =fm.SelNo.value;
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}

/**
 * ѡ�����ǰ������ѡ��ʡ��
 */
function checkProvince() {
	
	if(fm.Province.value == "") {
		
		alert("����ѡ��ʡ��");
		fm.City.value = "";
		fm.CityName.value = "";
	}
}
//====================�¼�===================//
/**
 * У���¼���Ϣ
 */
function checkCaseInfo() {
	
	if(!notNull(fm.AccReason,"����ԭ��")){return false;};
	if(!notNull(fm.AccDate,"��������")){return false;};
	if(!notNull(fm.ClaimPay,"������")){return false;};
	if(!notNull(fm.HospitalCode,"����ҽԺ")){return false;};
	if(!notNull(fm.HospitalName,"ҽԺ����")){return false;};
	if(!notNull(fm.AccResult1,"��Ҫ���")){return false;};
	if(!notNull(fm.TreatResult,"���ƽ��")){return false;};
	if(!notNull(fm.CaseSource,"�¼���Դ")){return false;};
	/*
	if(!notNull(fm.AccProvinceName,"���յص�[ʡ]")){return false;};
	if(!notNull(fm.AccCityName,"���յص�[��]")){return false;};
	if(!notNull(fm.AccCountyName,"���յص�[��/��]")){return false;};*/
	if(!notNull(fm.AccSite,"���յص�")){return false;};	
	
	var tGrpRegisterNo = fm.GrpRgtNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	var tRegisterNo = fm.RegisterNo.value;
	var tAccDate = fm.AccDate.value;
	var tAppntNo = fm.AppntNo.value;
	var tHospitalCode = fm.HospitalCode.value;
	var tHospitalName = fm.HospitalName.value;
	var tAccidentType = fm.AccReason.value;
	
	//��������ϢУ��
	if (tCustomerNo==null || tCustomerNo=="") {
		
		alert("����ѡ��һ���ͻ���Ϣ��");
		return false;
	}
	//У��������Ƿ����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql7");
	tSQLInfo.addSubPara(tCustomerNo);
	tSQLInfo.addSubPara(tRegisterNo);
	tSQLInfo.addSubPara(tGrpRegisterNo);
	
	var tExistArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tExistArr==null || tExistArr.length<=0) {
		
		alert("�ó����˲��ڴ˴������£��������!");
		return false;
	}
	
/*	//���������б���������������������죩 ����������
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql8");
	tSQLInfo.addSubPara(tCustomerNo);
	
	var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tValidArr==null || tValidArr.length<=0) {
		
		alert("�ó������Ѿ��������������������������!");
		return false;
	}*/
/*
	//�˱������ղ��������⣬�˱���ͨ���ǿ��������
	var tPGSQL = "select count(1) from LPEdorItem where grpcontno='"+ tGrpContNo +"' and insuredno='"+ tCustomerNo +"' and contno='"+ tContNo +"' and edortype='CT'";
	var tEdorState = easyExecSql(tPGSQL);
	
	var tCSQL = "select count(*) from lmriskapp where "
	         + "riskcode in (select riskcode From lcpol where "
	         + "grpcontno = '"+ tGrpContNo +"' and insuredno = '"+ tCustomerNo +"' and contno='"+ tContNo +"') "
	         + "and RiskPeriod = 'L'";
	var tCount = easyExecSql(tCSQL);
	
	if (tEdorState>0 && tCount>0 ) {
		alert("�ó�������δȷ�ϵı�ȫ���Ѿ��˱�����ȷ�Ϻ�����������");
		return false;
	}	
*/					

/*
		var tSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+ tAccidentDate +"'";
				tSQLBQ = tSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+ tCustomerNo +"' and a.contno='"+ tContNo +"'";
				tSQLBQ = tSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')��and EdorState='0'";
		if (tAppntNo!=null) {
			
				tSQLBQ = tSQLBQ+" and b.AppntNo='"+ tAppntNo +"'";
		}
		if (tGrpContNo!=null) {
				tSQLBQ = tSQLBQ+" and b.GrpContNo='"+ tGrpContNo +"'";
		}
		var tArrBQ = easyExecSql(tSQLBQ);
		if ( tArrBQ != null ) {

				alert("���ؾ��棺�ñ��������������ܸ��ı���ı�ȫ��");   
		}
*/
	//��������У��
	if (dateDiff(tAccDate,mCurrentDate,'D')<0) {	
		
		alert("�������ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	if (fm.Operate.value=="CASEUPDATE") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql23");
		tSQLInfo.addSubPara(tRegisterNo);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		var Result = easyExecSql(tSQLInfo.getString());
		if(Result != null && Result != ""){
		
		var len = Result.length;
		
			for(var i=0;i<len;i++){
				var tStateDate=Result[i][0];
				if(dateDiff(tAccDate,tStateDate,'D')< 0 ){
						alert("�������ڲ��������˵����ڣ�");
						return false;
				}
			}
		}		
	}

	//�������У��
	var tDeathDate = fm.DeathDate.value;
	if (tDeathDate!=null && tDeathDate!="") {
		
		if (dateDiff(tDeathDate,mCurrentDate,'D')<0) {
			
			alert("������ڲ������ڵ�ǰ���ڣ�");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";			
			return false;
		}
		if (dateDiff(tDeathDate,tAccDate,'D')>0) {		
			
			alert("������ڲ������ڳ������ڣ�");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDeathDate,'D')>180) {
				
				if (confirm("���������������ڵļ������180���Ƿ������")) {
									
				} else {
					
					return false;
				}
			}
		}
	}
	//�˲�/ȫ������У��
	var tDianoseDate = fm.DeformityDate.value;
	if (tDianoseDate!=null && tDianoseDate!="") {
		
		if (dateDiff(tDianoseDate,mCurrentDate,'D')<0) {		
			
			alert("�˲�/ȫ�����ڲ������ڵ�ǰ���ڣ�");
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";							
			return false;
		}
		if (dateDiff(tDianoseDate,tAccDate,'D')>0) {
			
			alert("�˲�/ȫ�����ڲ������ڳ������ڣ�");
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;			
		}
		if (fm.AccReason.value=="1") {
			
			if (dateDiff(tAccDate,tDianoseDate,'D')>180) {
				
				if (confirm("�˲�������������ڵļ������180���Ƿ������")) {
									
				} else {
					
					return false;
				}
			}
		}				
		var tDeadCheck = fm.ClaimType[0].checked;//У���������Ϊ��ʵĶ�ѡ���Ƿ�ѡ
		if (!tDeadCheck && tDeathDate!="") {
			
			alert("��¼���ˡ�������ڡ����빴ѡ��������--����ʡ���");
			return false;
		}
		var tMaim = fm.ClaimType[2].checked;//У���������Ϊ�˲еĶ�ѡ���Ƿ�ѡ
		if (!tMaim && tDianoseDate!="") {
			
			alert("��¼���ˡ��˲�/ȫ�����ڡ����빴ѡ��������--���˲С���");
			return false;
		}		

	}	
	var tAllGetClaimType = "";
	//У���������
	
	var tClaimType = new Array;	
	var tDeadFalg = fm.ClaimType[0].checked;//У���Ƿ�ѡ��ʶ�ѡ��
	var tMedicalFlag = fm.ClaimType[3].checked;//�Ƿ�ѡ��������--ҽ��
	var tMaimFalg = fm.ClaimType[2].checked;//�Ƿ�ѡ��������--�˲�
		
	//��������
	for (var j=0;j<fm.ClaimType.length;j++) {
  	
		if (fm.ClaimType[j].checked == true) {
      	
			tClaimType[j] = fm.ClaimType[j].value;
			tAllGetClaimType = tAllGetClaimType + "'" + tAccidentType + "" + tClaimType[j] + "',";
		}
	}
	if (tClaimType==null || tClaimType=="") {

		alert("��ѡ��������ͣ�");
		return false;
	}
	tAllGetClaimType = tAllGetClaimType.substr(0,tAllGetClaimType.length-1);
//  var tRowNum = RiskDutyGrid.mulLineCount;
  /*
	for (var i=0;i<tRowNum;i++) {
	
		var tRiskCode = RiskDutyGrid.getRowColData(i,2);
		var tDutyCode = RiskDutyGrid.getRowColData(i,4);
		var tDutyName = RiskDutyGrid.getRowColData(i,5);
		
		var tGetDutyKindSQL = "select 1 from lmdutygetrela a, lmdutygetclm b where a.getdutycode = b.getdutycode  and a.dutycode = '"+ tDutyCode +"'  and b.getdutykind in ("+ tAllGetClaimType +")";
		var tGetDutyKindResult = easyExecSql(tGetDutyKindSQL);
		if(tGetDutyKindResult==null){
			
			alert("��¼��ġ�"+ tDutyName +"��,�������������Ӧ�ĳ���ԭ����������ͣ�");
			return false;
		}	
	}
	*/
	if (tDeadFalg) {
		
		if (tDeathDate==null || tDeathDate=="") {
			
			alert("��������Ϊ��ʣ���¼�롾������ڡ���");
			fm.DeathDate.focus();
			fm.DeathDate.style.background="#ffb900";				
			return false;
		}
	}
	if (tMaimFalg) {
		
		if (tDianoseDate==null || tDianoseDate=="") {
			
			alert("��������Ϊ�˲У���¼�롾�˲�/ȫ�����ڡ���");			
			fm.DeformityDate.focus();
			fm.DeformityDate.style.background="#ffb900";				
			return false;
		}
	}	
	
	//У������ҽԺ��ҽԺ�����Ƿ�ƥ��
		
	if (tHospitalCode!=null && tHospitalCode!="") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql9");
		tSQLInfo.addSubPara(tHospitalCode);
		tSQLInfo.addSubPara(tHospitalName);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tValidArr==null || tValidArr.length<=0) {
			
			alert("ҽԺ������ҽԺ���벻��Ӧ�����飡");
			return false;
		}
	}
	
	if (fm.Operate.value=="CASEUPDATE") {
		
		//У���������Ƿ���ڸ��¼������������˵���ԭʼ���֮��
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql25");
		tSQLInfo.addSubPara(fm.RegisterNo.value);
		tSQLInfo.addSubPara(fm.CustomerNo.value);
		tSQLInfo.addSubPara(fm.CaseNo.value);
		
		var tValidArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tOriaSum = 0;
		if (tValidArr==null || tValidArr.length<=0) {
			
			tOriaSum=0;		
		} else {
			tOriaSum = tValidArr[0][0];
		}
		var tAppMoney = fm.ClaimPay.value;
		if (tAppMoney-tOriaSum<0) {
			alert("�������С�ڸ��¼����˵�ԭʼ���֮��");		
			return false;
		}		
	}
	
	return true;	
}
/**
 * ��ѯ��ά���Ŀͻ����¼���Ϣ�б�
 */
function queryCustomerCaseList() {
	
	initCaseInfo();
	initOnEventDutyListGrid();
	initOffEventDutyListGrid();
	
	if (fm.RegisterNo.value=="" || fm.RegisterNo.value==null || fm.CustomerNo.value=="" || fm.CustomerNo.value==null) {
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql12");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(),EventlistGrid,2);	
}
/**
 * ��ѯѡ�еĿͻ����¼���ϸ��Ϣ
 */
function showSelectCase() {

	var i = EventlistGrid.getSelNo()-1;
	var tRegisterNo = EventlistGrid.getRowColData(i,1);
	if (tRegisterNo==null || tRegisterNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	var tCaseNo = EventlistGrid.getRowColData(i,2);
	var tCustomerNo = EventlistGrid.getRowColData(i,3);
	
	fm.CustomerNo.value = tCustomerNo;
	fm.RegisterNo.value = tRegisterNo;
	fm.CaseNo.value = tCaseNo;
	fm.SelNo.value = EventlistGrid.getSelNo();//add 2011-11-29
	fm.PageIndex.value = turnPage2.pageIndex;//add 2011-11-29	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql13");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	
	var tArr = easyExecSql(tSQLInfo.getString());
	
	if (tArr == null || tArr.length==0) {
		alert("�������¼���Ϣ������!");
		return false;
	}else {
		//�����ԭ�е�����
		initCaseInfo();

		fm.AccReason.value = tArr[0][0];
		fm.AccReasonName.value = tArr[0][1];
		fm.AccDate.value = tArr[0][2];
		fm.ClaimPay.value = tArr[0][3];
		fm.HospitalCode.value = tArr[0][4];
		fm.HospitalName.value = tArr[0][5];
		fm.AccResult1.value = tArr[0][6];
		fm.AccResult1Name.value = tArr[0][7];
		fm.AccResult2.value = tArr[0][8];
		fm.AccResult2Name.value = tArr[0][9];
		fm.DeformityDate.value = tArr[0][10];
		fm.DeathDate.value = tArr[0][11];
		fm.TreatResult.value = tArr[0][12];
		fm.TreatResultName.value = tArr[0][13];
		fm.CaseSource.value = tArr[0][14];
		fm.CaseSourceName.value = tArr[0][15];
		fm.LRCaseNo.value = tArr[0][16];
		/*fm.AccProvinceName.value = tArr[0][17];
		fm.AccProvince.value = tArr[0][18];	
		fm.AccCityName.value = tArr[0][19];	
		fm.AccCity.value = tArr[0][20];	
		fm.AccCountyName.value = tArr[0][21];
		fm.AccCounty.value = tArr[0][22];*/
		fm.AccSite.value = tArr[0][17];
		fm.AccDesc.value = tArr[0][18];
		fm.CaseRemark.value = tArr[0][19];
		fm.CaseNo.value = tArr[0][20]; 			
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql14");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);	
	
	var tClaimType = easyExecSql(tSQLInfo.getString());
	
	if (tClaimType == null || tClaimType.length==0) {
		alert("����������������Ϣ�����ڣ�����ó������Ƿ���Ч!");
		return false;
	} else {
		
		for (var i=0;i<fm.ClaimType.length;i++) {
		  	
			fm.ClaimType[i].checked = false;
		}
		if (tClaimType!= '' && tClaimType !=null) {
		  	
			for (var j=0;j<fm.ClaimType.length;j++){
		     	
				for (var l=0;l<tClaimType.length;l++){
								
					var tClaim = tClaimType[l].toString();
					tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//ȡ�������ͺ���λ
					if(fm.ClaimType[j].value == tClaim){				        		
						fm.ClaimType[j].checked = true;
					}
				}
			}
		}
	}
	queryCaseDutyInfo();
}
/**
 * ��ѯ�¼�������Ϣ
 */
function queryCaseDutyInfo() {
	
	initOnEventDutyListGrid();
		
	turnPage3.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql15");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("1");
	turnPage3.queryModal(tSQLInfo.getString(),OnEventDutyListGrid,2,1);	
	
	initOffEventDutyListGrid();
	
	turnPage4.pageLineNum = 100;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql15");
	tSQLInfo.addSubPara(fm.RegisterNo.value);
	tSQLInfo.addSubPara(fm.CaseNo.value);
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara("0");
	turnPage4.queryModal(tSQLInfo.getString(),OffEventDutyListGrid,2,1);		
	
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
 * ��¼����ҳ�������
 */
function initSelNo() {
	
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
			
//			fm.AccCity.value = "";
//			fm.AccCityName.value = "";
//			fm.AccCountyName.value = "";
//			fm.AccCounty.value = "";
			fm.AccSite.value = "";						
		} else if (CodeName == "city") {
			
//			fm.AccCountyName.value = "";
//			fm.AccCounty.value = "";
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
				
				/*	fm.AccProvince.value = "";
				fm.AccProvinceName.value = "";
				fm.AccCity.value = "";
				fm.AccCityName.value = "";
				fm.AccCounty.value = "";
				fm.AccCountyName.value = "";*/
				fm.AccSite.value = "";							
				return false;
			} else {
				
				if (tArr.length==1) {
					fm.HospitalCode.value = tArr[0][0];
					fm.HospitalName.value = tArr[0][1];
					
				/*	fm.AccProvince.value = tArr[0][2];
					fm.AccProvinceName.value = tArr[0][3];
					fm.AccCity.value = tArr[0][4];
					fm.AccCityName.value = tArr[0][5];
					fm.AccCounty.value = tArr[0][6];
					fm.AccCountyName.value = tArr[0][7];*/
					fm.AccSite.value = tArr[0][8];
				}		
			}
		}
	} catch (ex){
		
		alert(ex);
	}
}

function initAreaData() {
	
	//fm.City.value = "";
	//fm.CityName.value = "";
}

function initSelectCustomerInfo() {
	
		initCustomerInfo();
		initCaseInfo();
		initEventlistGrid();
		initOnEventDutyListGrid();
		initOffEventDutyListGrid();
		queryCustomerList();			
}

function initSelectCaseInfo() {
	
	initCaseInfo();	
	initOnEventDutyListGrid();
	initOffEventDutyListGrid();
	queryCustomerCaseList();
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "���˰�����^|�ͻ�����^|����^|�Ա����^|�Ա�^|��������^|"
							+"֤�����ͱ���^|֤����������^|֤������^|Ա����^|������^|��Ʊ��";
	 
	tSQLInfo = new SqlClass();
	if(mMode=="2"){
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql22");
		tSQLInfo.addSubPara(mGrpRegisterNo);
		
	}else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
		tSQLInfo.setSqlId("LLClaimCommonQuerySql5");
		tSQLInfo.addSubPara(mGrpRegisterNo);	
	}
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
//У���������
function checkAcceptCom() {

	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCommonQuerySql");
	tSQLInfo.setSqlId("LLClaimCommonQuerySql24");
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

//��ѯ����
/**
 * ������ѯ
 */
function showInsuredLCPol() {	
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ��������");
		return false;
	}	
	var strUrl="./LLClaimQueryPolicyMain.jsp?CustomerNo="+fm.CustomerNo.value+"&Mode=1&RgtNo="+fm.RegisterNo.value;
	var tWidth=1000;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	window.open(strUrl,"�ⰸ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
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
 * Ӱ�����ѯ
 */
function queryEasyscan() {
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo =CustomerListGrid.getRowColData(tSelNo,1);
	window.open("../g_easyscan/ScanPagesQueryMainInput.jsp?BussType=G_CM&BussNo="+tRgtNo,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * BPOУ������ѯ
 */
function BPOCheck() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	var strUrl="./LLClaimBPOcheckMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=1200;
	var tHeight=500;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�����",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
 * �籣����Ϣ��ѯ
 */
function querySecurity() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	
	var strUrl="./LLClaimQuerySocialInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�����",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * �˻���ѯ
 */
function queryAccInfo() {
	
	var tGrpRgtNo=fm.GrpRgtNo.value;
	var tRgtNo=fm.RegisterNo.value;
	
	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}	
	var strUrl="./LLClaimQueryAccInfoMain.jsp?GrpRgtNo="+tGrpRgtNo+"&RgtNo="+tRgtNo+"&Mode=0" ;
	var tWidth=window.screen.availWidth;
	var tHeight=window.screen.availHeight-200;
	var tTop=0;       //��ô��ڵĴ�ֱλ��;
	var tLeft=0;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�����",'width=1000,height=480,top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
 * ��������Ϣ
 */
function benefitInfo() {

	var tSelNo = CustomerListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ����������Ϣ");
		return false;
	}
	var tRgtNo = CustomerListGrid.getRowColData(tSelNo,1);
	if (tRgtNo==null || tRgtNo=="") {
		alert("���Ȳ�ѯ��������Ϣ��");
		return;
	}
	var tCustomerNo = CustomerListGrid.getRowColData(tSelNo,2);

	var strUrl="./LLClaimBenefitMain.jsp?GrpRgtNo="+mGrpRegisterNo+"&RgtNo="+tRgtNo+"&CustomerNo="+tCustomerNo;
	
	window.open(strUrl,"������",'width=1000,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}