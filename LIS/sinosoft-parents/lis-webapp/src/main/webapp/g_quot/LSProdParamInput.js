/***************************************************************
 * <p>ProName��LSProdParamInput.js</p>
 * <p>Title����Ʒ������Ϣά��--�����ά��</p>
 * <p>Description����Ʒ������Ϣά��--�����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

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
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	fm2.Operate.value = "";
	fm2.SerialNo.value = "";
	initPageInfo();
}

/**
 * ��ѯ��ά���������Ϣ�б�
 */
function queryManageFee() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSProdParamSql");
	tSQLInfo.setSqlId("LSProdParamSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), ManageFeeGrid, 0, 1);

}

/**
 * ����б��չʾ����Ҫ��
 */
function showManageFee() {
	
	initInpBox();
	var tRow = ManageFeeGrid.getSelNo();
	fm2.RiskCode.value = ManageFeeGrid.getRowColData(tRow-1,8);
	fm2.RiskName.value = ManageFeeGrid.getRowColData(tRow-1,1);
	fm2.AccType.value = ManageFeeGrid.getRowColData(tRow-1,9);
	fm2.AccTypeName.value = ManageFeeGrid.getRowColData(tRow-1,2);
	fm2.FeeType.value = ManageFeeGrid.getRowColData(tRow-1,10);
	fm2.FeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,3);
	
	if (fm2.FeeType.value=='0') {
		document.getElementById('allTR1').style.display = '';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.DeductType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.DeductTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.FeeValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
		
	} else if (fm2.FeeType.value=='1') {
		
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = '';
		document.getElementById('allTR3').style.display = 'none';
		document.getElementById('allTR4').style.display = 'none';
		fm2.MonthFeeType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.MonthFeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.MonthValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
		
	} else if (fm2.FeeType.value=='2') {
		
		document.getElementById('allTR1').style.display = 'none';
		document.getElementById('allTR2').style.display = 'none';
		document.getElementById('allTR3').style.display = '';
		document.getElementById('allTR4').style.display = '';
		fm2.YearFeeType.value = ManageFeeGrid.getRowColData(tRow-1,11);
		fm2.YearFeeTypeName.value = ManageFeeGrid.getRowColData(tRow-1,4);
		fm2.YearValue.value = ManageFeeGrid.getRowColData(tRow-1,5);
		fm2.YearStartValue.value = ManageFeeGrid.getRowColData(tRow-1,6);
		fm2.YearEndValue.value = ManageFeeGrid.getRowColData(tRow-1,7);
	}
}

/**
 * ѡ���������ͺ�չʾ����Ҫ��
 */
function afterCodeSelect(o, p) {
	
	if(o=='mafeetype') {
		
		if(p.value=='0') {
			
			document.getElementById('allTR1').style.display = '';
			document.getElementById('allTR2').style.display = 'none';
			document.getElementById('allTR3').style.display = 'none';
			document.getElementById('allTR4').style.display = 'none';
		} else if(p.value=='1') {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = '';
			document.getElementById('allTR3').style.display = 'none';
			document.getElementById('allTR4').style.display = 'none';
		} else {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = 'none';
			document.getElementById('allTR3').style.display = '';
			document.getElementById('allTR4').style.display = '';
		}
	}
}

/**
 * ��ʼ��������Ϣ
 */
function initPageInfo() {
	
	queryManageFee();
	
	fm2.RiskCode.value = "";
	fm2.RiskName.value = "";
	fm2.AccType.value = "";
	fm2.AccTypeName.value = "";
	fm2.FeeType.value = "";
	fm2.FeeTypeName.value = "";
	
	document.getElementById('allTR1').style.display = 'none';
	document.getElementById('allTR2').style.display = 'none';
	document.getElementById('allTR3').style.display = 'none';
	document.getElementById('allTR4').style.display = 'none';
}

/**
 * �����ά��--����
 */
function addSubmit() {
	
	if (!checkSubmit()) {
		return false;
	}
	
	mOperate = "INSERT";
	fm2.action = "./LSProdParamSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * �����ά��--�޸�
 */
function modSubmit() {
	
	var tRow = ManageFeeGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	
	if (!checkSubmit()) {
		return false;
	}
	
	var tRiskCode = ManageFeeGrid.getRowColData(tRow-1,8);
	var tAccType = ManageFeeGrid.getRowColData(tRow-1,9);
	var tFeeType = ManageFeeGrid.getRowColData(tRow-1,10);
	
	if (fm2.RiskCode.value != tRiskCode) {
		alert("���ֲ����޸ģ�");
		return false;
	}
	if (fm2.AccType.value != tAccType) {
		alert("�˻����Ͳ����޸ģ�");
		return false;
	}
	if (fm2.FeeType.value != tFeeType) {
		alert("��������Ͳ����޸ģ�");
		return false;
	}
	
	var tSerialNo = ManageFeeGrid.getRowColData(tRow-1,12);//��ˮ��
	fm2.SerialNo.value = tSerialNo;
	
	mOperate = "UPDATE";
	fm2.action = "./LSProdParamSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * �����ά��--ɾ��
 */
function delSubmit() {
	
	var tRow = ManageFeeGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	var tSerialNo = ManageFeeGrid.getRowColData(tRow-1,12);//��ˮ��
	fm2.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm2.action = "./LSProdParamSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&ObjType="+ tObjType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}


/**
 * �ύǰУ��
 */
function checkSubmit() {
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (fm2.FeeType.value=="0") {
		if (fm2.DeductType.value==null || fm2.DeductType.value =="") {
			alert("��ʼ����ѿ۳���ʽ����Ϊ�գ�");
			return false;
		}
		if (fm2.FeeValue.value==null || fm2.FeeValue.value =="") {
			alert("��ʼ�����(Ԫ)/��������Ϊ�գ�");
			return false;
		}
		if (!isNumeric(fm2.FeeValue.value)) {
			alert("��ʼ�����(Ԫ)/����������Ч�����֣�");
			return false;
		}
		if (Number(fm2.FeeValue.value)<0) {
			alert("��ʼ�����(Ԫ)/����Ӧ���ڵ���0��");
			return false;
		}
		
		if (fm2.DeductType.value=="001" || fm2.DeductType.value =="003") {
			if (Number(fm2.FeeValue.value)>1) {
				alert("��ʼ�����(Ԫ)/����ӦС�ڵ���1��");
				return false;
			}
		}
	} else if (fm2.FeeType.value=="1") {
		if (fm2.MonthFeeType.value==null || fm2.MonthFeeType.value =="") {
			alert("�¶ȹ�������Ͳ���Ϊ�գ�");
			return false;
		}
		if (fm2.MonthValue.value==null || fm2.MonthValue.value =="") {
			alert("�¶ȹ����(Ԫ)/��������Ϊ�գ�");
			return false;
		}
		if (!isNumeric(fm2.MonthValue.value)) {
			alert("�¶ȹ����(Ԫ)/����������Ч�����֣�");
			return false;
		}
		if (Number(fm2.MonthValue.value)<0) {
			alert("�¶ȹ����(Ԫ)/����Ӧ���ڵ���0��");
			return false;
		}
		
		if (fm2.MonthFeeType.value=="101") {
			if (Number(fm2.MonthValue.value)>1) {
				alert("�¶ȹ����(Ԫ)/����ӦС�ڵ���1��");
				return false;
			}
		}
	} else if (fm2.FeeType.value=="2") {
		
		var tYearFeeType = fm2.YearFeeType.value;
		var tYearStartValue = fm2.YearStartValue.value;
		var tYearEndValue = fm2.YearEndValue.value;
		var tYearValue = fm2.YearValue.value;
		if (fm2.YearFeeType.value==null || fm2.YearFeeType.value =="") {
			alert("��ȹ�������Ͳ���Ϊ�գ�");
			return false;
		}
		if (tYearStartValue==null || tYearStartValue =="") {
			alert("�����ʼֵ����Ϊ�գ�");
			return false;
		}
		if (tYearEndValue==null || tYearEndValue =="") {
			alert("�����ֵֹ����Ϊ�գ�");
			return false;
		}
		if (!isInteger(tYearStartValue)) {
			alert("�����ʼֵ������Ч��������");
			return false;
		}
		if (Number(tYearStartValue)<0) {
			alert("�����ʼֵӦ���ڵ���0��");
			return false;
		}
		if (!isInteger(tYearEndValue)) {
			alert("�����ֵֹ������Ч��������");
			return false;
		}
		if (Number(tYearEndValue)<0) {
			alert("�����ֵֹӦ���ڵ���0��");
			return false;
		}
		if (parseInt(tYearStartValue) > parseInt(tYearEndValue)) {
			alert("�����ʼֵӦС�������ֵֹ��");
			return false;
		}
		if (tYearValue==null || tYearValue =="") {
			alert("��ȹ����(Ԫ)/��������Ϊ�գ�");
			return false;
		}
		if (!isNumeric(tYearValue)) {
			alert("��ȹ����(Ԫ)/����������Ч�����֣�");
			return false;
		}
		if (Number(tYearValue)<0) {
			alert("��ȹ����(Ԫ)/����Ӧ���ڵ���0��");
			return false;
		}
		if (tYearFeeType == "201") {
			if (Number(tYearValue)>1) {
				alert("��ȹ����(Ԫ)/����ӦС�ڵ���1��");
				return false;
			}
		}
	}
	return true;
}

function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	var tFeeType = fm2.FeeType.value;
	if (value1=='deducttype') {
		
		var tSql = "1 and codetype=#deducttype# and codeexp=#"+tFeeType+"#";

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='risktoacc') {
		
		if (fm2.RiskCode.value==null || fm2.RiskCode.value=="") {
			alert("����ѡ������ !");
			return false;
		}
		var tRiskCode = fm2.RiskCode.value;
		var tSql = "1 and a.riskcode= #"+tRiskCode+"#";
		if (returnType=='0') {
			return showCodeList('risktoacc',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('risktoacc',value2,value3,null,tSql,'1','1',null);
		}
	}
}




