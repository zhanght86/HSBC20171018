/***************************************************************
 * <p>ProName��LSQuotSelectPlanInput.js</p>
 * <p>Title��ѡ�񱨼۷���</p>
 * <p>Description��ѡ�񱨼۷���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ������ķ������
 */
function queryNoPlanCombi() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), NoPlanCombiGrid, 1, 1);
}

/**
 * ѯ�۷���
 */
function queryQuotPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	if (tQuotType == '01') {
		tSQLInfo.addSubPara('and a.sysplancode = b.sysplancode and a.plancode = b.plancode');	
	} else if (tQuotType == '00') {
		tSQLInfo.addSubPara('');	
	}
	
	//turnPage2.queryModal(tSQLInfo.getString(), QuotPlanGrid, 1, 1);
	
	if (!noDiv(turnPage2, QuotPlanGrid, tSQLInfo.getString())) {
		initQuotPlanGrid();
		return false;
	}
}

/**
 * ����ѡ�񷽰�
 */
function queryBJQuotPlan() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql3");
	tSQLInfo.addSubPara(tOfferListNo);
	
	if (tQuotType == '01') {
		tSQLInfo.addSubPara('and a.sysplancode = b.sysplancode and a.plancode = b.plancode');	
	} else if (tQuotType == '00') {
		tSQLInfo.addSubPara('');	
	}
	
	turnPage3.queryModal(tSQLInfo.getString(), BJQuotPlanGrid, 1, 1);
}

/**
 * ѡ��
 */
function selectClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//�Ѵ�ӡ
		alert("�ñ��۵��Ѵ�ӡ�����ɽ��иò�����");
		return false;
	}
	//�ж��Ƿ�ѡ���˷�������
	var chkNum=0;
	var tGrpNum=0;//�˻��������˻�����
	var tSingeNum=0;//�˻��ո����˻�����
	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {
	
		if( QuotPlanGrid.getChkNo(i)) {	
			chkNum++;
			if (tTranProdType == "02") {
				var tType = QuotPlanGrid.getRowColData(i,13);
				if (tType == "02") {
					tSingeNum++;
				} else if (tType == "03") {
					tGrpNum++;
				}
			}
		}
	}
	if(chkNum==0) {
		alert("��ѡ��ѯ�۷�����Ϣ��");
		return false;
	}
	
	//У�齨����ʱ��ֻ��ѡ��һ������
	if (tTranProdType=="01") {
		
		if (chkNum>1) {
			alert("������ֻ����ѡ��һ��������");
			return false;
		}
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql5");
		tSQLInfo.addSubPara(tOfferListNo);
		var tPlanNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tPlanNumArr!=null && tPlanNumArr[0][0]>0) {
			alert("������ֻ����ѡ��һ��������");
			return false;
		}
	}
	//�˻���ʱ��У���������ֻ��һ�������˻��������и����˻��������˻��ɶ����
	if (tTranProdType=="02") {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql6");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tGrpNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if (tGrpNumArr!=null && tGrpNumArr[0][0]==0) {
			if (tGrpNum==0 || tGrpNum>1) {
				alert("�˻��ձ�������ֻ��һ�������ķ�������Ϊ�����˻���");
				return false;
			}
		} else if (tGrpNumArr!=null && tGrpNumArr[0][0]>0 && tGrpNum>0){
			alert("�˻��ձ�������ֻ��һ�������ķ�������Ϊ�����˻���");
			return false;
		}
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
		tSQLInfo.setSqlId("LSQuotSelectPlanSql7");
		tSQLInfo.addSubPara(tOfferListNo);
		
		var tSingeNumArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tSingeNumArr != null && tSingeNumArr[0][0]==0) {
			if (tSingeNum==0) {
				alert("�˻��ձ���Ҫ�з����ķ�������Ϊ�����˻���");
				return false;
			}
		}
	}
	
	mOperate = "INSERT";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&QuotType="+ tQuotType +"&TranProdType="+ tTranProdType;
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//�Ѵ�ӡ
		alert("�ñ��۵��Ѵ�ӡ�����ɽ��иò�����");
		return false;
	}
	
	//�ж��Ƿ�ѡ���˷�������
	var chkFlag=false;
	for ( var i=0;i<BJQuotPlanGrid.mulLineCount; i++ ) {
	
		if( BJQuotPlanGrid.getChkNo(i)) {	
			chkFlag = true;
			break;
		}
	}
	if(!chkFlag) {
		alert("��ѡ�񱨼���ѡ������Ϣ��");
		return false;
	}
	
	mOperate = "DELETE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo+"&QuotType="+ tQuotType +"&TranProdType="+ tTranProdType;
	submitForm();
}

/**
 * ����ѡ��
 */
function bindSelectClick() {
	
	var tPrint = getPrintState();
	if (tPrint!=null && tPrint=="1") {//�Ѵ�ӡ
		alert("�ñ��۵��Ѵ�ӡ�����ɽ��иò�����");
		return false;
	}
	
	if (!checkBindSelect()) {
		return false;
	}
	
	mOperate = "BIND";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&OfferListNo="+ tOfferListNo +"&TranProdType="+ tTranProdType +"&QuotType="+ tQuotType;
	submitForm();

}

/**
 * У������ѡ��
 */
function checkBindSelect() {
	
	//��ѡ��������������ѯ�۷���
//	var chkNum=0;
//	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {
//
//		if( QuotPlanGrid.getChkNo(i)) {	
//			chkNum++;
//		}
//	}
//	if (Number(chkNum)<2) {
//		alert("�빴ѡ��������������ѯ�۷���!");
//		return false;
//	}
	
	//У�����������һ������Ϊ���ķ���
	var tPlanFlagArr = [];
//	var tOccupTypeFlagArr = [];//ְҵ���������
//	var tOccupTypeArr = [];//ְҵ�������
//	var tOccupRateArr = [];//ְҵ��������
	var tPlanCodeAdd="";//ƴ�ӷ��������ַ��������ڲ�ѯ��������
	var tNum = 0;
	for ( var i=0;i<QuotPlanGrid.mulLineCount; i++ ) {

		if( QuotPlanGrid.getChkNo(i)) {	
			var tPlanCode=QuotPlanGrid. getRowColData(i,2);//��������
			var tPlanType=QuotPlanGrid. getRowColData(i,13);//��������
			var tPlanFlag=QuotPlanGrid. getRowColData(i,14);//������ʶ
//			var tOccupTypeFlag=QuotPlanGrid. getRowColData(i,4);//ְҵ�����
//			var tOccupType=QuotPlanGrid. getRowColData(i,5);//ְҵ���
//			var tOccupRate=QuotPlanGrid. getRowColData(i,14);//ְҵ����
			
			if (tPlanType=="00") {
				
				//add by ZhangC 20140830 ְҵ��������Ȳ������� 
//modify by ZhangC	20141204	ְҵ��������������䶯������У��ŵ���̨����				
//				if (tNum>0) {
//					if (!in_Array(tOccupTypeFlag,tOccupTypeFlagArr)) {//ְҵ���ͱ�ǲ����
//						alert("ְҵ�������ʱ���ܽ�������!");
//						return false;
//					} else {//ְҵ���ͱ�����
//						if (tOccupTypeFlag=="2") {//��ְҵʱ��У�����ְҵ�����ȣ����ְҵ�����ȣ�ְҵ�������
//							
//							if (!in_Array(tOccupType,tOccupTypeArr)) {//У��ְҵ����Ƿ����
//								alert("��Ϊ��ְҵ���ʱ����ѡ�������ְҵ�����������ְҵ������ʱ�����ܽ�������!");
//								return false;
//							}
//							
//							if (!in_Array(tOccupRate,tOccupRateArr)) {//У��ְҵ�����Ƿ����
//								alert("��Ϊ��ְҵ���ʱ����ѡ����ְҵ���������ʱ���ܽ�������!");
//								return false;
//							}
//						}
//					}
//				}
//				
//				tOccupTypeFlagArr.push(tOccupTypeFlag);
//				tOccupTypeArr.push(tOccupType);
//				tOccupRateArr.push(tOccupRate);
//				tNum++;
				
				tPlanFlagArr.push(tPlanFlag);
				tPlanCodeAdd += "'"+tPlanCode + "',";
				
			} else {
				alert("��["+(i+1)+"]�й����������������");
				return false;
			}
		}
	}
	
	if (!in_Array("00",tPlanFlagArr)) {//���ķ���--00
		alert("������Ҫ��һ������Ϊ���ķ���!");
		return false;
	}

	//����ķ����в��ܺ�����ͬ����������
	tPlanCodeAdd = tPlanCodeAdd.substring(0,tPlanCodeAdd.length -1);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql4");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tPlanCodeAdd);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr != null) {
		alert("����ķ����в��ܺ�����ͬ����������!");
		return false;
	}
	
	var tBindPlanDesc = fm.BindPlanDesc.value;
	if (tBindPlanDesc==null || tBindPlanDesc=="") {
		alert("���󷽰���������Ϊ�գ�");
		return false;
	}
	return true;
}

/**
 * �ж�һ�����Ƿ���������
 */
function in_Array(search,array){
	
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}

/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
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
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//QuotPlanGrid.checkBoxAllNot();//������й�ѡ
		queryQuotPlan();
		queryBJQuotPlan();
		fm.BindPlanDesc.value = "";
		
		showFixedValue();
	}
}

/**
 * ��ȡ�ñ��۵���ӡ״̬
 */
function getPrintState() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql8");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null) {
		return tStateArr[0][0];
	} else {
		return null;
	}
}

/**
 * ��Ŀѯ�۽�����ʱ����������ۡ��������¼���չʾ
 */
function showFixedValue() {
	
	if (mOperate == "INSERT" || mOperate == "SAVE") {
		
		if (tQuotType =="01" && tTranProdType == "01") {
			showFixedValueSub();
		}
	} else {
		document.all("divFixedValue").style.display = "none";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
}

/**
 * ���ݱ��Ѽ��㷽ʽ���� ������ۡ��������¼���չʾ
 */
function showFixedValueSub() {
	
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//���Ѽ��㷽ʽ
	
	if (tPremCalType == "2") {//�����
		
		document.all("divFixedValue").style.display = "";
		document.all("tdFixed1").style.display = "";
		document.all("tdFixed2").style.display = "";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginArea.value = "";
	} else if (tPremCalType == "3") {//�����
		
		document.all("divFixedValue").style.display = "";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "";
		document.all("tdFixed4").style.display = "";
		fm.EnginCost.value = "";
	} else { //������
		
		document.all("divFixedValue").style.display = "none";
		document.all("tdFixed1").style.display = "none";
		document.all("tdFixed2").style.display = "none";
		document.all("tdFixed3").style.display = "none";
		document.all("tdFixed4").style.display = "none";
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
	if (mOperate == "SAVE") {
		fm.EnginCost.value = "";
		fm.EnginArea.value = "";
	}
}
/**
 * ˢ�½���ʱ����ʼ�� ��Ŀѯ�� ������ ����
 */
function initFixedValue() {
	
	if (tQuotType =="01" && tTranProdType == "01") {//��Ŀѯ�۽�����ʱ
		
		var tOfferCout = BJQuotPlanGrid.mulLineCount;
		if (Number(tOfferCout)==0) {//�ޱ��۷�����¼ʱ������¼���
			hiddenFixedValue();
		} else {
			
			var tPrintState = getPrintState();
			if (tPrintState!=null && tPrintState=="-1") {//δ��ӡ
				
				if (tQuotQuery=="Y") {//ѯ�۲�ѯ�������
					hiddenFixedValue();
				} else {
					showFixedValueSub();
				}
			} else {
				hiddenFixedValue();
			}
		}
		
	} else {//�������ȫ����
		hiddenFixedValue();
	}
}

/**
 * ���ع������ ������� ¼���
 */
function hiddenFixedValue() {
	
	document.all("divFixedValue").style.display = "none";
	document.all("tdFixed1").style.display = "none";
	document.all("tdFixed2").style.display = "none";
	document.all("tdFixed3").style.display = "none";
	document.all("tdFixed4").style.display = "none";
	fm.EnginCost.value = "";
	fm.EnginArea.value = "";
}

/**
 * ������Ŀѯ�۽�����ʱ��������ۡ������������ǰУ��
 */
function checkFixedValue() {
	
	var tOfferCout = BJQuotPlanGrid.mulLineCount;
	if (Number(tOfferCout)==0) {
		alert("����ѡ�񱨼۷�����");
		return false;
	}
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//���Ѽ��㷽ʽ
	
	if (tPremCalType == "2") {//�����
		
		//У�鹤�����
		var tEnginCost = fm.EnginCost.value;
		if (tEnginCost==null || tEnginCost=="") {
			alert("������۲���Ϊ�գ�");
			return false;
		} else {
			
			//�������,ӦΪ����0����Ч����
			if (!isNumeric(tEnginCost) || Number(tEnginCost)<0) {
				alert("�������ӦΪ����0����Ч���֣�");
				return false;
			}
			
			if (!checkDecimalFormat(tEnginCost, 18, 2)) {
				alert("�����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
				return false;
			}
		}
		
	}  else if (tPremCalType == "3") {//�����
		
		//У�鹤�����
		var tEnginArea = fm.EnginArea.value;
		if (tEnginArea==null || tEnginArea=="") {
			alert("�����������Ϊ�գ�");
			return false;
		} else {
			
			//�������,ӦΪ����0����Ч����
			if (!isNumeric(tEnginArea) || Number(tEnginArea)<0) {
				alert("�������ӦΪ����0����Ч���֣�");
				return false;
			}
			
			if (!checkDecimalFormat(tEnginArea, 18, 2)) {
				alert("�����������λ��Ӧ����18λ,С��λ��Ӧ����2λ��");
				return false;
			}
		}
	}
	
	//У�� �������/��������Ƿ���ѯ����͡����֮��
	var tSysPlanCode = BJQuotPlanGrid.getRowColData(0,2);//����ϵͳ��������
	var tPlanCode = BJQuotPlanGrid.getRowColData(0,3);//��������
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql9");
	tSQLInfo.addSubPara(tPremCalType);
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(tOfferListNo);
	tSQLInfo.addSubPara(tSysPlanCode);
	tSQLInfo.addSubPara(tPlanCode);
	
	var tMinValue = "";
	var tMaxValue = "";
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr == null) {
		alert("��ѯѯ�������Ϣʧ�ܣ�");
		return false;
	} else {
		tMinValue = tArr[0][0];
		tMaxValue = tArr[0][1];
	}
	
	if (tPremCalType == "2") {//�����
		
		var tValue = fm.EnginCost.value;
		if ((Number(tValue) < Number(tMinValue)) || (Number(tValue) > Number(tMaxValue)) ) {
			alert("�������Ӧ��"+tMinValue+"(Ԫ)��"+tMaxValue+"(Ԫ)֮�䣡");
			return false;
		}
	}
	if (tPremCalType == "3") {//�����
		
		var tValue = fm.EnginArea.value;
		if ((Number(tValue) < Number(tMinValue)) || (Number(tValue) > Number(tMaxValue)) ) {
			alert("�������Ӧ��"+tMinValue+"(ƽ����)��"+tMaxValue+"(ƽ����)֮�䣡");
			return false;
		}
	}
	return true;
}

/**
 * ������Ŀѯ�۽�����ʱ��������ۡ������������
 */
function saveFixedValue() {
	
	if (!checkFixedValue()) {
		return false;
	}
	var tOfferListNo=BJQuotPlanGrid.getRowColData(0,1);//���ۺ�
	var tSysPlanCode=BJQuotPlanGrid.getRowColData(0,2);//����ϵͳ��������
	var tPlanCode=BJQuotPlanGrid.getRowColData(0,3);//���۷�������
	var tPremCalType=BJQuotPlanGrid.getRowColData(0,10);//���Ѽ��㷽ʽ
	
	mOperate = "SAVE";
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotSelectPlanSave.jsp?OfferListNo="+tOfferListNo+"&SysPlanCode="+tSysPlanCode+"&PlanCode="+tPlanCode+"&PremCalType="+tPremCalType+"&TranProdType="+ tTranProdType +"&QuotType="+ tQuotType;
	submitForm();
}
