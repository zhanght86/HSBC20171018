/***************************************************************
 * <p>ProName��LJTempFeeApplyInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryApplyTempFee(o) {
	
	initTempFeeInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql1");
	tSQLInfo.addSubPara(tOperator);//�����Ʋ�����Ϊ���˼���
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	if (o=="0") {
		fm.QueryTempFeeNo.value = "";
	}
	tSQLInfo.addSubPara(fm.QueryTempFeeNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), TempFeeInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ѡ��ʱ����
 */
function showTempFeeInfo() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	fm.PayType.value = TempFeeInfoGrid.getRowColData(tSelNo, 2);
	fm.PayTypeName.value = TempFeeInfoGrid.getRowColData(tSelNo, 3);
	fm.CustBankCode.value = TempFeeInfoGrid.getRowColData(tSelNo, 4);
	fm.CustBankName.value = TempFeeInfoGrid.getRowColData(tSelNo, 5);
	fm.CustBankAccNo.value = TempFeeInfoGrid.getRowColData(tSelNo, 6);
	fm.CustAccName.value = TempFeeInfoGrid.getRowColData(tSelNo, 7);
	fm.Money.value = TempFeeInfoGrid.getRowColData(tSelNo, 8);
	fm.GrpName.value = TempFeeInfoGrid.getRowColData(tSelNo, 10);
	fm.AgentName.value = TempFeeInfoGrid.getRowColData(tSelNo, 12);
	
	if (TempFeeInfoGrid.getRowColData(tSelNo, 2)=='01') {
			
			
			document.all("N1").style.display = 'none';
			document.all("N2").style.display = 'none';
		} else {
			
			document.all("N1").style.display = '';
			document.all("N2").style.display = '';
		}
}

/**
 * ����
 */
function addTempFee() {
	
	if (tComGrade!="03") {
		alert("������֧�����½��иò�����");
		return false;
	}

	if (!checkPageElements()) {
		return false;
	}
	
	tOperate = "ADDTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate;
	submitForm(fm);
}

/**
 * �޸�
 */
function modifyTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("����ѡ��Ҫ���в����ļ�¼��");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);
	if (!checkPageElements()) {
		return false;
	}
	
	tOperate = "MODIFYTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * ɾ��
 */
function deleteTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("����ѡ��Ҫ���в����ļ�¼��");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "DELETETEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * ��ӡ���ӵ�
 */
function printTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("����ѡ��Ҫ���в����ļ�¼��");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "PRINTTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * �ύ¼��
 */
function toInpTempFee() {

	var tSelNo = TempFeeInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		
		alert("����ѡ��Ҫ���в����ļ�¼��");
		return false;
	}
	
	var tTempFeeNo = TempFeeInfoGrid.getRowColData(tSelNo, 1);

	tOperate = "TOINPTEMPFEE";
	fm.action = "./LJTempFeeApplySave.jsp?Operate="+ tOperate +"&TempFeeNo="+ tTempFeeNo;
	submitForm(fm);
}

/**
 * ҳ��Ԫ��У��
 */
function checkPageElements() {
	
	if (isEmpty(fm.PayType)) {
		
		alert("���ѷ�ʽ����Ϊ�գ�");
		return false;
	}
	
	if (fm.PayType.value!='01') {  
		
		if (isEmpty(fm.CustBankCode)) {
			
			alert("�ͻ������в���Ϊ�գ�");
			return false;
		}
		
		if (isEmpty(fm.CustBankAccNo)) {
		
			alert("�ͻ������˺Ų���Ϊ�գ�");
			return false;
		}
		
		var tCustBankAccNo = fm.CustBankAccNo.value;
		if (tCustBankAccNo.length>30) {
		
			alert("�ͻ������˺ų���Ӧ������30λ��");
			return false;
		}
	}
	
	if (isEmpty(fm.CustAccName)) {
		
		alert("�ͻ��˻���/�������������Ϊ��!");
		return false;
	}
	
	if (isEmpty(fm.Money)) {
		
		alert("����Ϊ��!");
		return false;
	}
	var tMoney = fm.Money.value;
	if (!isNumeric(tMoney) || Number(tMoney)<=0) {
		alert("���ӦΪ����0����Ч���֣�");
		return false;
	}
	
	if (!checkDecimalFormat(tMoney, 12, 2)) {
		alert("�������λӦ������8λ��С��λӦ������2λ��");
		return false;
	}
	
	if (isEmpty(fm.GrpName)) {
		
		alert("Ͷ����λ���Ʋ���Ϊ��!");
		return false;
	}
	
	if (isEmpty(fm.AgentName)) {
		
		alert("�ͻ�������������Ϊ��!");
		return false;
	}
	
	return true;
}


/**
 * �ύ����
 */
function submitForm(obj) {
	
	fmPub.Operate.value = tOperate;
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content, filepath,tfileName,cTempFeeNo,tOperate) {
	
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	if(tOperate=='PRINTTEMPFEE'){
		var filepath1 =filepath+tfileName;
		window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;	
		
	}
	
	showInfo.focus();
	}
	
	dealAfterSubmit(cTempFeeNo, FlagStr);
}

/**
 * �ɹ�����
 */
function dealAfterSubmit(o, p) {
	
	if (p!="Fail") {
	
		initQueryInfo();
		initEnterInfo();
		tOperate = fmPub.Operate.value;
		fmPub.Operate.value = "";
	
		if (tOperate=="ADDTEMPFEE" || tOperate=="MODIFYTEMPFEE" || tOperate=="PRINTTEMPFEE") {
			
			fm.QueryTempFeeNo.value = o;
			queryApplyTempFee('1');
			TempFeeInfoGrid.radioSel(1);
			showTempFeeInfo();
			
			if (tOperate=="PRINTTEMPFEE") {  
				
				if (o==null || o=="") {
					return;
				}
				
				tSQLInfo = new SqlClass();
				tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
				tSQLInfo.setSqlId("LJTempFeeSql7");
				tSQLInfo.addSubPara(o);
				
				var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
				var tFileName = tArr[0][0];
				var tFilePath = tArr[0][1];
	
				window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
			}
		} else {
			
			initTempFeeInfoGrid();
		}
	}
}

/**
 * ��ʼ����ѯ��Ϣ
 */
function initQueryInfo() {

	fm.QueryPayType.value = "";
	fm.QueryPayTypeName.value = "";
	fm.QueryCustBankCode.value = "";
	fm.QueryCustBankName.value = "";
	fm.QueryCustBankAccNo.value = "";
	fm.QueryCustAccName.value = "";
	fm.QueryGrpName.value = "";
	fm.QueryAgentName.value = "";
	fm.QueryStartDate.value = "";
	fm.QueryEndDate.value = "";
	fm.QueryTempFeeNo.value = "";
}

/**
 * ��ʼ��¼����Ϣ
 */
function initEnterInfo() {
	
	fm.PayType.value = "";
	fm.PayTypeName.value = "";
	fm.CustBankCode.value = "";
	fm.CustBankName.value = "";
	fm.CustBankAccNo.value = "";
	fm.CustAccName.value = "";
	fm.Money.value = "";
	fm.GrpName.value = "";
	fm.AgentName.value = "";
}

/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "����������Ϣ�б�";
	
	var tTitle = "�ݽ��Ѻ�^|���ѷ�ʽ^|�ͻ�������^|�ͻ������˺�^|�ͻ��˻���^|���^|Ͷ����λ����^|�ͻ���������" +
			"^|��������^|�˻�����^|�˻�ԭ��";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJTempFeeSql");
	tSQLInfo.setSqlId("LJTempFeeSql9");
	tSQLInfo.addSubPara(tOperator);//�����Ʋ�����Ϊ���˼���
	tSQLInfo.addSubPara(fm.QueryPayType.value);
	tSQLInfo.addSubPara(fm.QueryCustBankCode.value);
	tSQLInfo.addSubPara(fm.QueryCustBankAccNo.value);
	tSQLInfo.addSubPara(fm.QueryCustAccName.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryAgentName.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}

function afterCodeSelect(cCodeType, cField) {
	
	
	if (cCodeType=='paymode' && cField.name=='PayType') {
		
		if (cField.value=='6') {
			
			
			document.all("N1").style.display = 'none';
			document.all("N2").style.display = 'none';
		} else {
			
			document.all("N1").style.display = '';
			document.all("N2").style.display = '';
		}
	}
}

//ģ����ѯ���б���
function fuzzyQueryHeadBank(Filed,FildName) {

	var objCodeName = FildName.value;
	if (objCodeName=="") {
		return false;
	}
	if (window.event.keyCode == "13") {
		window.event.keyCode = 0;
		if (objCodeName==null || trim(objCodeName)=="") {
		
			alert("����������!");
			return false;
		} else {
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_config.LDBankSql");
			tSQLInfo.setSqlId("LDBankSql2");
			tSQLInfo.addSubPara(objCodeName);   
			var arr = easyExecSql(tSQLInfo.getString(),1,0,1);
			if (arr == null) {
				alert("���в����ڣ�");
				return false;
			} else {
				if (arr.length == 1) {
					Filed.value = arr[0][0];
					FildName.value = arr[0][1];
					afterCodeSelect('headbank', Filed);
				}else {
					var queryCondition = "1 and headbankname like #%"+objCodeName+"%#";
					showCodeList('headbank', [Filed, FildName], [0,1], null,queryCondition, '1', 1, '300');
				}
			}
		}
	}
}
