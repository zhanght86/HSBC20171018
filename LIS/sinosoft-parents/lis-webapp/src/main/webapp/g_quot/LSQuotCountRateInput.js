/***************************************************************
 * <p>ProName��LSQuotCountRateInput.js</p>
 * <p>Title�����ʲ���</p>
 * <p>Description�����ʲ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ����
 */
function submitForm(obj) {

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
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		if (mOperate == "EXPRATE" || mOperate == "EXPCASHVALUE") {
			
			var tFileName = content.substring(content.lastIndexOf("/")+1);
			
			window.location = "../common/jsp/download.jsp?FilePath="+content+"&FileName="+tFileName;
			
		} else {
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
		initForm();
	}
	if (mOperate=="UPLOAD") {
		
		fm2.all('UploadPath').outerHTML = fm2.all('UploadPath').outerHTML;
	}
}

/**
* ����ѡ���չʾ����Ҫ��
*/
function afterCodeSelect(o, p) {
	
	if(o=='quotprodrisk') {
		
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
	}
	if(o=='cashvalueexptype') {
		
		if(p.value=='0') {
			
			fm2.all('expTD1').style.display = '';
			fm2.all('expTD2').style.display = '';
			fm2.all('expTD3').style.display = 'none';
			fm2.all('expTD4').style.display = 'none';
			fm2.all('expTD5').style.display = 'none';
			fm2.all('expTD6').style.display = 'none';
			fm2.all('expTD7').style.display = 'none';
			fm2.all('expTD8').style.display = 'none';
			fm2.all('expTD9').style.display = '';
			fm2.all('expTD10').style.display = '';
			
		} else if(p.value=='1') {
			
			fm2.all('expTD1').style.display = 'none';
			fm2.all('expTD2').style.display = 'none';
			fm2.all('expTD3').style.display = '';
			fm2.all('expTD4').style.display = '';
			fm2.all('expTD5').style.display = '';
			fm2.all('expTD6').style.display = '';
			fm2.all('expTD7').style.display = 'none';
			fm2.all('expTD8').style.display = 'none';
			fm2.all('expTD9').style.display = 'none';
			fm2.all('expTD10').style.display = 'none';
		} else {
			
			fm2.all('expTD1').style.display = 'none';
			fm2.all('expTD2').style.display = 'none';
			fm2.all('expTD3').style.display = 'none';
			fm2.all('expTD4').style.display = 'none';
			fm2.all('expTD5').style.display = 'none';
			fm2.all('expTD6').style.display = 'none';
			fm2.all('expTD7').style.display = '';
			fm2.all('expTD8').style.display = '';
			fm2.all('expTD9').style.display = '';
			fm2.all('expTD10').style.display = '';
		}
	}
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	if (value1=='quotprodrisk') {
		
		var tSql = "1 and a.quotno = #"+ tQuotNo +"# and a.quotbatno = #"+ tQuotBatNo +"# and b.riskperiod=#L#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='quotduty') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("����ѡ�����֣�");
			return false;
		}
		
		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	}
	
	if (value1=='payintv') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("����ѡ�����֣�");
			return false;
		}
		
		var tSql = "1 and b.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList('payintvbyrisk',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('payintvbyrisk',value2,value3,null,tSql,'1','1',null);
		}	
	}
}

/**
 * ������Ϣ����
 */
function savePolInfo() {
	
	mOperate = "SAVEPOL";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}

/**
 * ������Ϣɾ��
 */
function delPolInfo() {
	
	mOperate = "DELETEPOL";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}
/**
 * ����
 */
function addSubmit() {
	
	mOperate = "INSERT";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
}

/**
* �޸�
*/
function modSubmit() {
	
	mOperate = "UPDATE";
	
	var tRow = CustInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	var tSerialNo = CustInfoGrid.getRowColData(tRow-1,1);//��ˮ��
	
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&SerialNo="+ tSerialNo;
	submitForm(fm2);
}

/**
 * ɾ��
 */
function delSubmit() {
	
	var tRow = CustInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	var tSerialNo = CustInfoGrid.getRowColData(tRow-1,1);//��ˮ��
	
	mOperate = "DELETE";
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&SerialNo="+ tSerialNo;
	submitForm(fm2);
}

/**
 * ���ʵ���
 */
function expRate() {
	
	mOperate = "EXPRATE";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateExpSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm2);
}

/**
 * �����ֽ��ֵ
 */
function expCashValue() {
	
	mOperate = "EXPCASHVALUE";
	
	if (!checkSubmit()) {
		return false;
	}
	fm2.encoding = "application/x-www-form-urlencoded";
	fm2.action = "./LSQuotCountRateExpSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo;
	submitForm(fm2);
}

/**
* �ύǰУ��
*/
function checkSubmit() {
	
	//������Ϣ����
	if (mOperate == "SAVEPOL") {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("���ֲ���Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm2.DutyCode)) {
			alert("���β���Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm2.EffectivDate)) {
			alert("Ԥ����Ч���ڲ���Ϊ�գ�");
			return false;
		} else {
			var tEffectivDate = fm2.EffectivDate.value;
			if (tCurrentDate > tEffectivDate) {
				alert("Ԥ����Ч���ڲ������ڵ�ǰ���ڣ�");
				return false;
			}
		}
		
		if (isEmpty(fm2.CalDirection)) {
			alert("���㷽����Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm2.Amnt)) {
			alert("����/���Ѳ���Ϊ�գ�");
			return false;
		} else {
			if (!isNumeric(fm2.Amnt.value)) {
				alert("����/���Ѳ�����Ч���֣�");
				return false;
			}
			if (Number(fm2.Amnt.value)<0) {
				alert("����/����Ӧ���ڵ���0��");
				return false;
			}
		}
		
		if (isEmpty(fm2.PayIntv)) {
			alert("�ɷѷ�ʽ����Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm2.InsuPeriod)) {
			alert("�ɷ��ڼ䲻��Ϊ�գ�");
			return false;
		} else {
			if (!isInteger(fm2.InsuPeriod.value) || Number(fm2.InsuPeriod.value)<0) {
				alert("�ɷ��ڼ�ӦΪ����0��������");
				return false;
			}
		}
		
		if (isEmpty(fm2.InsuPeriodFlag)) {
			alert("�ɷ��ڼ䵥λ����Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm2.ReceiveCode)) {
			alert("��ȡ��ʽ����Ϊ�գ�");
			return false;
		} else {
			if (!isInteger(fm2.ReceiveCode.value) || Number(fm2.ReceiveCode.value)<0) {
				alert("��ȡ��ʽӦΪ����0��������");
				return false;
			}
		}
		
		if (isEmpty(fm2.ReceiveFlag)) {
			alert("�ɷ��ڼ䵥λ����Ϊ�գ�");
			return false;
		}
		
		//У���Ƿ�¼�뱣����Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tPolArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		var tSaveFlag = false;
		if (tPolArr != null) {
			if (fm2.RiskCode.value == tPolArr[0][0] && fm2.DutyCode.value == tPolArr[0][2]
				&& fm2.EffectivDate.value == tPolArr[0][4] && fm2.CalDirection.value == tPolArr[0][5] 
				&& fm2.Amnt.value == tPolArr[0][7] && fm2.PayIntv.value == tPolArr[0][9] 
				&& fm2.InsuPeriod.value == tPolArr[0][11] && fm2.InsuPeriodFlag.value == tPolArr[0][12]
				&& fm2.ReceiveCode.value == tPolArr[0][14] && fm2.ReceiveFlag.value == tPolArr[0][15]
			) {
				alert("������Ϣδ�����䶯��");
				return false;
			} else {
				tSaveFlag = true;
			}
		}
		
		//У���Ƿ�¼�뱻������Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tCustCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tCustCount[0][0] != "0" && tSaveFlag) {
			if(!confirm('������Ϣ�����䶯����ɾ�����б�������Ϣ���Ƿ����?')){
				return false;
			}
		}
	} 
	
	//������Ϣɾ��
	if (mOperate == "DELETEPOL") {
		
		var tRiskCode = fm2.RiskCode.value;
		if (tRiskCode == "" || tRiskCode == null) {
			return false;
		} else {
			if(!confirm('��ͬʱɾ����������Ϣ���Ƿ����?')){
				return false;
			}
		}
	}
	
	//����  �޸�
	if (mOperate == "INSERT" || mOperate == "UPDATE") {
		
		if (isEmpty(fm2.InsuredName)) {
			alert("��������Ϊ�գ�");
			return false;
		}
		if (isEmpty(fm2.Gender)) {
			alert("�Ա���Ϊ�գ�");
			return false;
		}
		if (isEmpty(fm2.InsuredAge) && isEmpty(fm2.Birthday) && isEmpty(fm2.IDNo)) {
			alert("���䡢�������ڡ����֤��������¼��һ�");
			return false;
		}
		if (!isEmpty(fm2.InsuredAge)) {
			
			if (!isInteger(fm2.InsuredAge.value)) {
				alert("���䲻����Ч��������");
				return false;
			}
			if (Number(fm2.InsuredAge.value)<0) {
				alert("����ӦΪ��С��0��������");
				return false;
			}
		}
		
		//��¼���˳������ڣ���������
		if (!isEmpty(fm2.Birthday)&&isEmpty(fm2.InsuredAge)) {
			
			var tInsuredAge = calAge(fm2.Birthday.value);
			if (tInsuredAge=="-1") {
				fm2.InsuredAge.value = "0";
			} else {
				fm2.InsuredAge.value = tInsuredAge;
			}
		}
		//��¼���� ���֤�ţ��������䡢��������
		if (!isEmpty(fm2.IDNo)&&isEmpty(fm2.InsuredAge)&&isEmpty(fm2.Birthday)) {
			
			fm2.Birthday.value = getBirthdatByIdNo(fm2.IDNo.value);
			
			var tInsuredAge = calAge(fm2.Birthday.value);
			if (tInsuredAge=="-1") {
				fm2.InsuredAge.value = "0";
			} else {
				fm2.InsuredAge.value = tInsuredAge;
			}
		}
		
		//���¼�����֤�ţ�У�����֤����ȷ��
		if (!isEmpty(fm2.IDNo)) {
			
			if (!checkIdCard(fm2.IDNo.value)) {
				return false;
			}
		}
		
		if (!isEmpty(fm2.Gender) && !isEmpty(fm2.IDNo)) {
			
			var tGender = fm2.Gender.value;
			var tTempGender = getSex(fm2.IDNo.value);
			if (tGender != tTempGender ) {
				alert("�Ա������֤���벻��Ӧ��");
				return false;
			}
		}
		
		if (!isEmpty(fm2.InsuredAge) && !isEmpty(fm2.Birthday)) {
			
			var tAge = fm2.InsuredAge.value;
			var tTempAge = calAge(fm2.Birthday.value);
			if (tAge != tTempAge ) {
				alert("������������ڲ���Ӧ��");
				return false;
			}
		}
		
		if (!isEmpty(fm2.Birthday) && !isEmpty(fm2.IDNo)) {
			
			var tBirthday = fm2.Birthday.value;
			var tTempBirthday = getBirthdatByIdNo(fm2.IDNo.value);
			if (tBirthday != tTempBirthday ) {
				alert("�������������֤���벻��Ӧ��");
				return false;
			}
		}
		
		if (!isEmpty(fm2.InsuredAge) && !isEmpty(fm2.IDNo)) {
			
			var tInsuredAge = fm2.InsuredAge.value;
			var tTempBirthday = getBirthdatByIdNo(fm2.IDNo.value);
			var tTempAge = calAge(tTempBirthday);
			if (tInsuredAge != tTempAge ) {
				alert("���������֤���벻��Ӧ��");
				return false;
			}
		}
	}
	
	//���ʵ���
	if (mOperate == "EXPRATE") {
		
		if (tQuotNo=="" || tQuotNo == null) {
			alert("��ȡѯ�ۺ�ʧ�ܣ�");
			return false;
		}
		if (tQuotBatNo=="" || tQuotBatNo == null) {
			alert("��ȡ���κ�ʧ�ܣ�");
			return false;
		}
		
		//У���Ƿ�¼�뱣����Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql3");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tPolCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tPolCount[0][0]=="0") {
			alert("����ά��������Ϣ��");
			return false;
		}
		
		//У���Ƿ�¼�뱻������Ϣ
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
		tSQLInfo.setSqlId("LSQuotCountRateSql4");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var tCustCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tCustCount[0][0]=="0") {
			alert("����ά����������Ϣ��");
			return false;
		}
	}
	
	//�����ֽ��ֵ
	if (mOperate == "EXPCASHVALUE") {
		
		if (isEmpty(fm2.ExpType)) {
			alert("������ʽ����Ϊ�գ�");
			return false;
		} else {
			if (fm2.ExpType.value == "0") {//������ʽ������
				
				if (isEmpty(fm2.Age)) {
					alert("���䲻��Ϊ�գ�");
					return false;
				} else {
					if (!isInteger(fm2.Age.value)) {
						alert("���䲻����Ч��������");
						return false;
					}
					if (Number(fm2.Age.value)<0) {
						alert("����ӦΪ��С��0��������");
						return false;
					}
				}
			} else if (fm2.ExpType.value == "1") {//������ʽ����������
				if (isEmpty(fm2.YearStart)) {
					alert("��������Ϊ�գ�");
					return false;
				} else {
					if (!isInteger(fm2.YearStart.value)) {
						alert("����������Ч��������");
						return false;
					}
					if (Number(fm2.YearStart.value)<=0) {
						alert("������ӦΪ����0��������");
						return false;
					}
				}
				if (isEmpty(fm2.YearEnd)) {
					alert("����ֹ����Ϊ�գ�");
					return false;
				} else {
					if (!isInteger(fm2.YearEnd.value)) {
						alert("����ֹ������Ч��������");
						return false;
					}
					if (Number(fm2.YearEnd.value)<0) {
						alert("����ֹӦΪ��С��0��������");
						return false;
					}
					if (Number(fm2.YearEnd.value)>105) {
						alert("����ֹӦΪС��105��������");
						return false;
					}
				}
				
				if (Number(fm2.YearStart.value) > Number(fm2.YearEnd.value)) {
					alert("�������ܴ�������ֹ��");
					return false;
				}
			}
		}
	}
	
	return true;
}

/**
 * ��ʼ����ѯ������Ϣ
 */
function queryPolInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tPolArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tPolArr != null) {
		
		fm2.RiskCode.value = tPolArr[0][0];
		fm2.RiskName.value = tPolArr[0][1];
		fm2.DutyCode.value = tPolArr[0][2];
		fm2.DutyName.value = tPolArr[0][3];
		fm2.EffectivDate.value = tPolArr[0][4];
		fm2.CalDirection.value = tPolArr[0][5];
		fm2.CalDirectionName.value = tPolArr[0][6];
		fm2.Amnt.value = tPolArr[0][7];
		fm2.PayIntv.value = tPolArr[0][9];
		fm2.PayIntvName.value = tPolArr[0][10];
		fm2.InsuPeriod.value = tPolArr[0][11];
		fm2.InsuPeriodFlag.value = tPolArr[0][12];
		fm2.InsuPeriodFlagName.value = tPolArr[0][13];
		fm2.ReceiveCode.value = tPolArr[0][14];
		fm2.ReceiveFlag.value = tPolArr[0][15];
		fm2.ReceiveName.value = tPolArr[0][16];
	}
}

/**
 * ��ʼ����ѯ������Ϣ
 */
function queryCustInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initCustInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), CustInfoGrid, 1, 1);//����λ��ʾʹ�ô�������
}

/**
 * ����ͻ��б��չʾ��ϸ�ͻ���Ϣ
 */
function showCustInfo() {
	
	var tSelNo = CustInfoGrid.getSelNo()-1;
	fm2.InsuredName.value = CustInfoGrid.getRowColData(tSelNo, 4);
	fm2.Gender.value = CustInfoGrid.getRowColData(tSelNo, 5);
	fm2.GenderName.value = CustInfoGrid.getRowColData(tSelNo, 6);
	fm2.InsuredAge.value = CustInfoGrid.getRowColData(tSelNo, 7);
	fm2.Birthday.value = CustInfoGrid.getRowColData(tSelNo, 8);
	fm2.IDNo.value = CustInfoGrid.getRowColData(tSelNo, 9);
	
}

/**
 * ��������Ϣ����
 */
function impSubmit() {
	
	if (tQuotNo == null || tQuotNo == "") {
		alert("��ȡѯ�ۺ�ʧ�ܣ�");
		return false;
	}
	if (tQuotBatNo == null || tQuotBatNo == "") {
		alert("��ȡ���κ�ʧ�ܣ�");
		return false;
	}
	
	//У���Ƿ�ά��������Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotCountRateSql");
	tSQLInfo.setSqlId("LSQuotCountRateSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tCountArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tCountArr[0][0]=="0") {
		alert("����ά��������Ϣ!");
		return false;
	}
	
	var tFilePath = fm2.UploadPath.value;
	if(tFilePath == null || tFilePath == ""){
		alert("��ѡ�����ļ�·����");
		return false;
	}
	
	if (tFilePath!=null && tFilePath !="") {
		
		var tFileName = tFilePath.substring(tFilePath.lastIndexOf("\\")+1);
		var pattern2 = /^[^\s\+\&]+$/;
		if (!pattern2.test(tFileName)) {
			alert("�ϴ����ļ����ܺ��пո񡢡�+������&����");
			return false;
		}
		var tFileSuffix = tFilePath.substring(tFilePath.lastIndexOf("."));
		if (tFileSuffix==".xls" || tFileSuffix==".XLS" ) {
			
		} else {
			alert("��֧�ִ��ļ������ϴ���");
			return false;
		}
		
	}
	mOperate = "UPLOAD";
	fm2.encoding = "multipart/form-data";
	fm2.action="./LSQuotCustInfoImpSave.jsp?Operate=UPLOAD&QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo;
	submitForm(fm2);
	
}

