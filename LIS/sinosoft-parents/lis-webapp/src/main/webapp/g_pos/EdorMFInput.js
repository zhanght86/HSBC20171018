/***************************************************************
 * <p>ProName��EdorMFInput.js</p>
 * <p>Title�����շ��ñ��</p>
 * <p>Description�����շ��ñ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";
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
		fm2.Operate.value = "";
		fm2.SerialNo.value = "";
		fm3.Operate.value = "";
		fm3.SerialNo.value = "";
		initPageInfo();
		query("0");
		
	}
}

/**
 * ��ѯ��ά���������Ϣ�б�
 */
function queryManageFee() {
	
	initManageFeeGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorMFSql");
	tSQLInfo.setSqlId("EdorMFSql1");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	turnPage1.queryModal(tSQLInfo.getString(), ManageFeeGrid, 1, 1);

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
			clearContent();
		} else if(p.value=='1') {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = '';
			document.getElementById('allTR3').style.display = 'none';
			document.getElementById('allTR4').style.display = 'none';
			clearContent();
		} else {
			
			document.getElementById('allTR1').style.display = 'none';
			document.getElementById('allTR2').style.display = 'none';
			document.getElementById('allTR3').style.display = '';
			document.getElementById('allTR4').style.display = '';
			clearContent();
		}
	}
	if(o=="contplanrisk"){
		fm3.GetType2.value="";
		fm3.GetTypeName2.value="";
		fm3.ValPeriod.value="";
		fm3.ValPeriodName.value="";
		fm3.ValStartDate.value="";
		fm3.ValEndDate.value="";
		fm3.FeeValues.value="";
	} else if(o=="gettype"){
		fm3.ValPeriod.value="";
		fm3.ValPeriodName.value="";
		fm3.ValStartDate.value="";
		fm3.ValEndDate.value="";
		fm3.FeeValues.value="";
	} else if(o=="insuperiodflag"){
		fm3.ValStartDate.value="";
		fm3.ValEndDate.value="";
		fm3.FeeValues.value="";
	}
}
/**
* ���¼��ֵ
*/
function clearContent(){
	fm2.DeductType.value="";
	fm2.DeductTypeName.value="";
	fm2.FeeValue.value="";
	fm2.MonthFeeType.value="";
	fm2.MonthFeeTypeName.value="";
	fm2.MonthValue.value="";
	fm2.YearFeeType.value="";
	fm2.YearFeeTypeName.value="";
	fm2.YearStartValue.value="";
	fm2.YearEndValue.value="";
	fm2.YearValue.value="";
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
	if (fm2.FeeType.value!="2") {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorMFSql");
		tSQLInfo.setSqlId("EdorMFSql6");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(fm2.RiskCode.value);
		tSQLInfo.addSubPara(fm2.AccType.value);
		tSQLInfo.addSubPara(fm2.FeeType.value);
		///tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if(tRes!=null && tRes[0]=='1'){
			alert("�����ͷ����Ѿ�����!");
			return false;
		}
	} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorMFSql");
		tSQLInfo.setSqlId("EdorMFSql7");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(fm2.RiskCode.value);
		tSQLInfo.addSubPara(fm2.AccType.value);
		tSQLInfo.addSubPara(fm2.FeeType.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearStartValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		tSQLInfo.addSubPara(fm2.YearEndValue.value);
		//tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(tRes!=null && tRes[0]=='1'){
			alert("�����ͷ����Ѿ�����!");
			return false;
		}
	}
	mOperate = "INSERT";
	fm2.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&EdorNo="+ tEdorNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
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
	
	mOperate = "UPDATE";
	fm2.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&EdorNo="+ tEdorNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
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
	
	var tSerialNo = ManageFeeGrid.getRowColData(tRow-1,12);//��ˮ��
	fm2.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE";
	fm2.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&EdorNo="+ tEdorNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}
/**
 * �ύǰУ��
 */
function checkSubmit() {
	
	if(!verifyForm("fm2")) {
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
		if(!isNumeric(fm2.FeeValue.value)){
			alert("��ʼ�����(Ԫ)/����������Ч����!");
			return false;
		}
		if((fm2.DeductType.value=='001'||fm2.DeductType.value=='003')&&(fm2.FeeValue.value<0||fm2.FeeValue.value>1)){
			alert("��ʼ�����(Ԫ)/��������Ϊ����0С��1������!");
			return false;
		}else if(fm2.FeeValue.value<0){
			alert("��ʼ�����(Ԫ)/��������Ϊ����0������!");	
			return false;
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
		if(!isNumeric(fm2.MonthValue.value)){
			alert("�¶ȹ����(Ԫ)/����������Ч����!");
			return false;
		}
		if((fm2.MonthFeeType.value=='101')&&(fm2.MonthValue.value<0||fm2.MonthValue.value>1)){
			alert("�¶ȹ����(Ԫ)/��������Ϊ����0С��1������!");
			return false;
		}else if(fm2.MonthValue.value<0){
			alert("�¶ȹ����(Ԫ)/��������Ϊ����0������!");	
			return false;
		}
		
	} else if (fm2.FeeType.value=="2") {

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
		if (!isInteger(tYearEndValue)) {
			alert("�����ֵֹ������Ч��������");
			return false;
		}
		if (parseInt(tYearStartValue) >= parseInt(tYearEndValue)) {
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
		if((fm2.YearFeeType.value=='201')&&(tYearValue<0||tYearValue>1)){
			alert("��ȹ����(Ԫ)/��������Ϊ����0С��1������!");
			return false;
		}else if(tYearValue<0){
			alert("��ȹ����(Ԫ)/��������Ϊ����0������!");	
			return false;
		}
	}
	return true;
}

/**
 * ��ѯ��ȫ�㷨
 */
function selectAccType(Filed,FildName,RiskCodeFiled) {
	
	if (RiskCodeFiled.value==null || RiskCodeFiled.value=="") {
		alert("����ѡ������ !");
		return false;
	}
	var tRiskCode = RiskCodeFiled.value;
	var conditionAccType = "1 and a.riskcode= #"+tRiskCode+"#";
	showCodeList('risktoacc',[Filed,FildName],[0,1],null,conditionAccType,'1','1',null);
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
		
		var tSql = "deducttype"+tFeeType;

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',null);
		}
	}
}


function goToPordParamStep(o, p) {
	
	if (o=="0") {
		EdorMFFee.style.display='';
		EdorMFCal.style.display='none';
		tab1.style.display='';
		tab2.style.display='none';
	} else if (o=="1") {
		EdorMFFee.style.display='none';
		EdorMFCal.style.display='';
		tab1.style.display='none';
		tab2.style.display='';
	}
}

/**
 * ��ѯ�˷���Ϣ�б�
 */
function query(o) {
	clearRefundInfo();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorMFSql");
	tSQLInfo.setSqlId("EdorMFSql3");
	tSQLInfo.addSubPara(fm3.RiskCode1.value);
	tSQLInfo.addSubPara(fm3.GetType1.value);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tGrpContNo);
	turnPage2.queryModal(tSQLInfo.getString(), RefundListGrid, 1, 1);

	if (o=="1"&&!turnPage2.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
} 
/**
 * չʾ�˷���Ϣά����Ϣ
 */
function showRefundList() {
	
	var tRow = RefundListGrid.getSelNo();
	fm3.RiskCode2.value = RefundListGrid.getRowColData(tRow-1,1);
	fm3.RiskName2.value = RefundListGrid.getRowColData(tRow-1,2);
	fm3.GetType2.value = RefundListGrid.getRowColData(tRow-1,8);
	fm3.GetTypeName2.value = RefundListGrid.getRowColData(tRow-1,3);
	fm3.ValPeriod.value = RefundListGrid.getRowColData(tRow-1,9);
	fm3.ValPeriodName.value = RefundListGrid.getRowColData(tRow-1,6);
	fm3.ValStartDate.value = RefundListGrid.getRowColData(tRow-1,4);
	fm3.ValEndDate.value = RefundListGrid.getRowColData(tRow-1,5);
	fm3.FeeValues.value = RefundListGrid.getRowColData(tRow-1,7);
	
}

/**
 * ��ȫ�˷��㷨ά��--����
 */
function addsSubmit() {
	
	mOperate = "INSERT1";
	if(!verifyForm("fm3")) {
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	 if(!checkValPeriod()){
		return false;
	}
	showPage();
	fm3.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&EdorNo="+ tEdorNo +"&EdorAppNo="+ tEdorAppNo +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm3);
}

/**
 * ��ȫ�˷��㷨ά��--�޸�
 */
function modsSubmit() {
	
	mOperate = "UPDATE1";
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	if(!verifyForm("fm3")) {
		return false;
	}
	if(!beforeSub()){
		return false;
	}
	if(!checkValPeriod()){
		return false;
	}
	 
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
	fm3.SerialNo.value = tSerialNo;
	showPage();
	fm3.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&EdorNo="+ tEdorNo +"&EdorAppNo="+ tEdorAppNo +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm3);
}

/**
 * ��ȫ�˷��㷨ά��--ɾ��
 */
function delsSubmit() {
	
	var tRow = RefundListGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	
	var tSerialNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
	fm3.SerialNo.value = tSerialNo;
	
	mOperate = "DELETE1";
	fm3.action = "./EdorMFSave.jsp?Operate="+ mOperate +"&EdorNo="+ tEdorNo +"&EdorAppNo="+ tEdorAppNo +"&GrpContNo="+ tGrpContNo +"&EdorType="+ tEdorType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm3);
}

 
/**
 * ����˷���Ϣά����Ϣ
 */
function clearRefundInfo() {
	
	fm3.RiskCode2.value = "";
	fm3.RiskName2.value = "";
	fm3.GetType2.value = "";
	fm3.GetTypeName2.value = "";
	fm3.ValPeriod.value = "";
	fm3.ValPeriodName.value = "";
	fm3.ValStartDate.value = "";
	fm3.ValEndDate.value = "";
	fm3.FeeValues.value = "";
}

/**
 * �������޸ĺ����չʾ
 */
function showPage() {
	
	if (fm3.RiskCode1.value!=null && fm3.RiskCode1.value != "") {
		fm3.RiskCode1.value = fm3.RiskCode2.value;
		fm3.RiskName1.value = fm3.RiskName2.value;
	}
	if (fm3.GetType1.value!=null && fm3.GetType1.value != "") {
		fm3.GetType1.value = fm3.GetType2.value;
		fm3.GetTypeName1.value = fm3.GetTypeName2.value;
	}
}

/**
 * ��ѯϵͳĬ���˷���Ϣά��
 */
function querySysRefundInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorMFSql");
	tSQLInfo.setSqlId("EdorMFSql5");
	tSQLInfo.addSubPara(tGrpContNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), SysRefundInfoGrid, 1, 1);
	
}
function beforeSub(){
	
	if (parseInt(fm3.ValStartDate.value) >= parseInt(fm3.ValEndDate.value)) {
		alert("��ʼֵӦС����ֵֹ��");
		return false;
	}
	if(fm3.FeeValues.value>1||fm3.FeeValues.value<0){
		alert("���ñ���ӦΪ����0��С��1������!");
		return false;
	}
	return true;
}


/**
 * У�鵥λ��ͬһ�����£�����ѡͬһ�ֵ�λ
 */
function checkValPeriod() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorMFSql");
	tSQLInfo.setSqlId("EdorMFSql4");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(fm3.RiskCode2.value);
	
	var tNo = "";
	if (mOperate == "INSERT1") {
		tSQLInfo.addSubPara(tNo);
	} else if (mOperate == "UPDATE1") {
		var tRow = RefundListGrid.getSelNo();
		tNo = RefundListGrid.getRowColData(tRow-1,10);//��ˮ��
		tSQLInfo.addSubPara(tNo);
	}
	tSQLInfo.addSubPara(fm3.GetType2.value);
	var arrResult = easyExecSql(tSQLInfo.getString());
	
	if (arrResult !== null && arrResult[0][0] !== "") {
		var tValPeriod = arrResult[0][0];
		if (tValPeriod != fm3.ValPeriod.value) {
			alert("��ͬһ��Ʒ����ά��ʱ������ѡ��ͬһ�ֵ�λ!");
			return false;
		}
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorMFSql");
	tSQLInfo.setSqlId("EdorMFSql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm3.RiskCode2.value);
	tSQLInfo.addSubPara(fm3.GetType2.value);
	tSQLInfo.addSubPara(fm3.ValPeriod.value);
	tSQLInfo.addSubPara(fm3.ValStartDate.value);
	tSQLInfo.addSubPara(fm3.ValEndDate.value);
	tSQLInfo.addSubPara(fm3.ValStartDate.value);
	tSQLInfo.addSubPara(fm3.ValEndDate.value);
	tSQLInfo.addSubPara(fm3.ValStartDate.value);
	tSQLInfo.addSubPara(fm3.ValStartDate.value);
	tSQLInfo.addSubPara(fm3.ValEndDate.value);
	tSQLInfo.addSubPara(fm3.ValEndDate.value);
	tSQLInfo.addSubPara(tNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	var tRes = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tRes!=null && tRes[0]=='1'){
		alert("���˷���Ϣ�Ѿ����ڣ��������佻��!");
		return false;
	}
	return true;
}
