/***************************************************************
 * <p>ProName��LSQuotPastInput.js</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var mPerUWOperate = "";
var tSQLInfo = new SqlClass();

/**
 * ������Ϣ��ѯ
 */
function queryTotal() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPastSql");
	tSQLInfo.setSqlId("LSQuotPastSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), PastTotalGrid, 2,1);
}

/**
 * ������Ϣ����
 */
function addPastTotal() {
	
	if (fm.InsuYear.value==null || fm.InsuYear.value=="") {
		alert("������Ȳ���Ϊ�գ�");
		return false;
	}
	
	mOperate = "TotalINSERT";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ������Ϣ�޸�
 */
function modifyPastTotal() {
	
	var tSelNo = PastTotalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�޸ĵĻ�����Ϣ��");
		return false;
	}
	
	fm.TotalSerialNo.value = PastTotalGrid.getRowColData(tSelNo-1, 1);
	
	if (fm.InsuYear.value==null || fm.InsuYear.value=="") {
		alert("������Ȳ���Ϊ�գ�");
		return false;
	}
	
	mOperate = "TotalUPDATE";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ������Ϣɾ��
 */
function deletePastTotal() {
	
	var tSelNo = PastTotalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ���Ļ�����Ϣ��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.TotalSerialNo.value = PastTotalGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "TotalDELETE";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ������Ϣչʾ
 */
function showPastTotal() {
	
	var tSelNo = PastTotalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.InsuYear.value = PastTotalGrid.getRowColData(tSelNo-1, 3);
	fm.InsuranceCom.value = PastTotalGrid.getRowColData(tSelNo-1, 4);
	fm.InsuranceComName.value = PastTotalGrid.getRowColData(tSelNo-1, 5);
	fm.SumPrem.value = PastTotalGrid.getRowColData(tSelNo-1, 6);
	fm.SumLoss.value = PastTotalGrid.getRowColData(tSelNo-1, 7);
	fm.SumClaimPeople.value = PastTotalGrid.getRowColData(tSelNo-1, 8);
	fm.MaleRate.value = PastTotalGrid.getRowColData(tSelNo-1, 9);
	fm.FemaleRate.value = PastTotalGrid.getRowColData(tSelNo-1, 10);
	
	divPastDetailTitle.style.display = "";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPastSql");
	tSQLInfo.setSqlId("LSQuotPastSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	tSQLInfo.addSubPara(fm.InsuYear.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), PastDetailGrid,2,1);
	
	fm.DetailInsuYear.value = PastTotalGrid.getRowColData(tSelNo-1, 3);
	fm.DetailInsuranceComName.value = PastTotalGrid.getRowColData(tSelNo-1, 5);
}

/**
 * У����ϸ��Ϣ
 */
function checkPastDetail() {
	
	var tGrpContNo = fm.GrpContNo.value;
	var tValDate = fm.ValDate.value;
	var tEndDate = fm.EndDate.value;
	var tNonMedSumPrem = fm.NonMedSumPrem.value;
	var tNonMedSumLoss = fm.NonMedSumLoss.value;
	var tNonMedPeople = fm.NonMedPeople.value;
	var tNonMedClmPeople = fm.NonMedClmPeople.value;
	var tMedSumPrem = fm.MedSumPrem.value;
	var tMedSumLoss = fm.MedSumLoss.value;
	var tMedPeople = fm.MedPeople.value;
	var tMedClmPeople = fm.MedClmPeople.value;
	
	if ( verifyEmpty(tGrpContNo)
	  && verifyEmpty(tValDate)
	  && verifyEmpty(tEndDate)
	  && verifyEmpty(tNonMedSumPrem)
	  && verifyEmpty(tNonMedSumLoss)
	  && verifyEmpty(tNonMedPeople)
	  && verifyEmpty(tNonMedClmPeople)
	  && verifyEmpty(tMedSumPrem)
	  && verifyEmpty(tMedSumLoss)
	  && verifyEmpty(tMedPeople)
	  && verifyEmpty(tMedClmPeople)
	) {
		alert("����¼��һ��Ҫ����Ϣ��");
		return false;
	}
	
	if (tValDate!=null && tValDate!="" && tEndDate!=null && tEndDate!="") {
		if (tValDate>tEndDate) {
			alert("������Ч���ڲ������ڱ�����ֹ���ڣ�");
			return false;
		}
	}
	return true;
}

/**
 * ��ϸ��Ϣ����
 */
function addPastDetail() {
	
	var tSelNo = PastTotalGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	if (!checkPastDetail()) {
		return false;
	}
	
	mOperate = "DetailINSERT";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ��ϸ��Ϣ�޸�
 */
function modifyPastDetail() {
	
	var tTotalSelNo = PastTotalGrid.getSelNo();
	if (tTotalSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tSelNo = PastDetailGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�޸ĵ���ϸ��Ϣ��");
		return false;
	}
	if (!checkPastDetail()) {
		return false;
	}
	fm.DetailSerialNo.value = PastDetailGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DetailUPDATE";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ��ϸ��Ϣɾ��
 */
function deletePastDetail() {
	
	var tTotalSelNo = PastTotalGrid.getSelNo();
	if (tTotalSelNo==0) {
		alert("��ѡ��һ��������Ϣ��");
		return false;
	}
	
	var tSelNo = PastDetailGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ������ϸ��Ϣ��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.DetailSerialNo.value = PastDetailGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "DetailDELETE";
	mPerUWOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * ��ϸ��Ϣչʾ
 */
function showPastDetail() {
	
	var tSelNo = PastDetailGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.GrpContNo.value = PastDetailGrid.getRowColData(tSelNo-1, 2);
	fm.ValDate.value = PastDetailGrid.getRowColData(tSelNo-1, 3);
	fm.EndDate.value = PastDetailGrid.getRowColData(tSelNo-1, 4);
	fm.NonMedSumPrem.value = PastDetailGrid.getRowColData(tSelNo-1, 5);
	fm.NonMedSumLoss.value = PastDetailGrid.getRowColData(tSelNo-1, 6);
	fm.NonMedPeople.value = PastDetailGrid.getRowColData(tSelNo-1, 7);
	fm.NonMedClmPeople.value = PastDetailGrid.getRowColData(tSelNo-1, 8);
	fm.MedSumPrem.value = PastDetailGrid.getRowColData(tSelNo-1, 9);
	fm.MedSumLoss.value = PastDetailGrid.getRowColData(tSelNo-1, 10);
	fm.MedPeople.value = PastDetailGrid.getRowColData(tSelNo-1, 11);
	fm.MedClmPeople.value = PastDetailGrid.getRowColData(tSelNo-1, 12);
}

/**
 * �ر���ϸ��Ϣ
 */
function closePastDetail() {
	
	initForm();
}

/**
 * ������Ϣ�ύ
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
	fm.Operate.value = mOperate;
	fm.action = "./LSQuotPastSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
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
		
		if (mOperate=="TotalINSERT") {
			
			initDetailInp();
			initPastTotalGrid();
			initPastDetailGrid();
			divPastDetailTitle.style.display = "none";
			queryTotal();
			clearPastTotal();
		} else if (mOperate=="TotalUPDATE" || mOperate=="TotalDELETE") {
			
			initTotalInp();
			initDetailInp();
			initPastTotalGrid();
			initPastDetailGrid();
			divPastDetailTitle.style.display = "none";
			queryTotal();
		} else if (mOperate=="DetailINSERT") {
			
			initPastDetailGrid();
			showPastTotal();
		} else if (mOperate=="DetailUPDATE" || mOperate=="DetailDELETE") {
			
			initDetailInp();
			initPastDetailGrid();
			showPastTotal();
		} else if (mPerUWOperate=="PerUWINSERT" || mPerUWOperate=="PerUWUPDATE" || mPerUWOperate=="PerUWDELETE" || mPerUWOperate=="PerUWSAVE" || mPerUWOperate=="PerUWUPLOAD") {
			
			initPersonUWInp();
			initPersonUWGrid();
			queryPersonUW();
			divRemark.style.display = "none";
			fmupload.all('UploadPath').outerHTML = fmupload.all('UploadPath').outerHTML;
		}
		
	}
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	return true;
}

/**
 * ���˺˱���Ϣ��ѯ
 */
function queryPersonUW() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPersonUWSql");
	
	//¼�뻷��ֻ��ѯ�ֶ�¼�������,modify by songsz 20140705 ��������
	tSQLInfo.setSqlId("LSQuotPersonUWSql2");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	//modify by ZhangC 20150116 
	//���ActivityIDΪ�գ���ֻ��չʾʱ�ã�ֻ��չʾʱ��ҳ���б��ޡ��Ƿ�˱������У����д��show������sql��decode����
	//���ActivityID��Ϊ�գ�����ActivityID�͸�������˽��ۣ��ж��Ƿ��Ѻ˱�
	if (tActivityID=="" || tActivityID==null) {
		tSQLInfo.addSubPara('show');
	} else {
		tSQLInfo.addSubPara(tActivityID);
	}
	
	turnPage3.queryModal(tSQLInfo.getString(), PersonUWGrid,1,1);
}

/**
 * ���˺˱���Ϣ����
 */
function addPerUW() {
	
	if (fm2.GrpName.value==null || fm2.GrpName.value=="") {
		alert("��λ���Ʋ���Ϊ�գ�");
		return false;
	}
	
	if (fm2.InsuredName.value==null || fm2.InsuredName.value=="") {
		alert("��������Ϊ�գ�");
		return false;
	}
	
	if (fm2.Age.value==null || fm2.Age.value=="") {
		alert("���䲻��Ϊ�գ�");
		return false;
	} else {
		if (fm2.Birthday.value!="") {
			
			var tAgeTemp = calAge(fm2.Birthday.value);
			if (fm2.Age.value!=tAgeTemp) {
				alert("����������¼�����䲻����");
				return false;
			}
		}
	}
	
	if (fm2.RiskName.value==null || fm2.RiskName.value=="") {
		alert("�⸶���ֲ���Ϊ�գ�");
		return false;
	}
	
	if (fm2.LossAmount.value==null || fm2.LossAmount.value=="") {
		alert("�⸶����Ϊ�գ�");
		return false;
	}
	
	if (fm2.Reason.value==null || fm2.Reason.value=="") {
		alert("��עԭ����Ϊ�գ�");
		return false;
	}
	
	mPerUWOperate = "PerUWINSERT";
	mOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	perUWSubmitForm();
}

/**
 * ���˺˱���Ϣ�޸�
 */
function modifyPerUW() {
	
	var tSelNo = PersonUWGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�޸ĵĸ��˺˱���Ϣ��");
		return false;
	}
	
	fm2.PerUWSerialNo.value = PersonUWGrid.getRowColData(tSelNo-1, 1);
	
	if (fm2.GrpName.value==null || fm2.GrpName.value=="") {
		alert("��λ���Ʋ���Ϊ�գ�");
		return false;
	}
	
	if (fm2.InsuredName.value==null || fm2.InsuredName.value=="") {
		alert("��������Ϊ�գ�");
		return false;
	}
	
	if (fm2.Age.value==null || fm2.Age.value=="") {
		alert("���䲻��Ϊ�գ�");
		return false;
	} else {
		if (fm2.Birthday.value!="") {
			
			var tAgeTemp = calAge(fm2.Birthday.value);
			if (fm2.Age.value!=tAgeTemp) {
				alert("����������¼�����䲻����");
				return false;
			}
		}
	}
	
	if (fm2.RiskName.value==null || fm2.RiskName.value=="") {
		alert("�⸶���ֲ���Ϊ�գ�");
		return false;
	}
	
	if (fm2.LossAmount.value==null || fm2.LossAmount.value=="") {
		alert("�⸶����Ϊ�գ�");
		return false;
	}
	
	if (fm2.Reason.value==null || fm2.Reason.value=="") {
		alert("��עԭ����Ϊ�գ�");
		return false;
	}
	
	mPerUWOperate = "PerUWUPDATE";
	mOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	perUWSubmitForm();
}

/**
 * ���˺˱���Ϣɾ��
 */
function deletePerUW() {
	
	var tSelNo = PersonUWGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ���ĸ��˺˱���Ϣ��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm2.PerUWSerialNo.value = PersonUWGrid.getRowColData(tSelNo-1, 1);
	
	mPerUWOperate = "PerUWDELETE";
	mOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	perUWSubmitForm();
}

/**
 * ���˺˱��������
 */
function savePerUW() {
	
	var tSelNo = PersonUWGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ�޸ĵĸ��˺˱���Ϣ��");
		return false;
	}
	
	fm2.PerUWSerialNo.value = PersonUWGrid.getRowColData(tSelNo-1, 1);
	
	if (fm2.UWOpinion.value==null || fm2.UWOpinion.value=="") {
		alert("�˱��������Ϊ�գ�");
		return false;
	}
	
	if (fm2.UWDesc.value==null || fm2.UWDesc.value=="") {
		alert("�˱���������Ϊ�գ�");
		return false;
	}
	
	var tEnterNode = PersonUWGrid.getRowColData(tSelNo-1, 19);
	if (tActivityID=="0800100002") {
		
		if (tEnterNode=="0800100003" || tEnterNode=="0800100004") {
			alert("�ü�¼�ѱ��ֺ˻��ܺ��´��˺˱����ۣ�");
			return false;
		}
	}
	if (tActivityID=="0800100003") {
		
		if (tEnterNode=="0800100004") {
			alert("�ü�¼�ѱ��ܺ��´��˺˱����ۣ�");
			return false;
		}
	}
	
	mPerUWOperate = "PerUWSAVE";
	mOperate = "";
	if (!beforeSubmit()) {
		return false;
	}
	perUWSubmitForm();
}

/**
 * ���˺˱���Ϣչʾ
 */
function showPersonUWDetail() {
	
	var tSelNo = PersonUWGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	var tDataSource = PersonUWGrid.getRowColData(tSelNo-1, 2);
	
	fm2.ContNo.value = PersonUWGrid.getRowColData(tSelNo-1, 4);
	fm2.GrpName.value = PersonUWGrid.getRowColData(tSelNo-1, 5);
	fm2.InsuredName.value = PersonUWGrid.getRowColData(tSelNo-1, 6);
	fm2.IDType.value = PersonUWGrid.getRowColData(tSelNo-1, 7);
	fm2.IDTypeName.value = PersonUWGrid.getRowColData(tSelNo-1, 8);
	fm2.IDNo.value = PersonUWGrid.getRowColData(tSelNo-1, 9);
	fm2.Gender.value = PersonUWGrid.getRowColData(tSelNo-1, 10);
	fm2.GenderName.value = PersonUWGrid.getRowColData(tSelNo-1, 11);
	fm2.Birthday.value = PersonUWGrid.getRowColData(tSelNo-1, 12);
	fm2.Age.value = PersonUWGrid.getRowColData(tSelNo-1, 13);
	fm2.RiskName.value = PersonUWGrid.getRowColData(tSelNo-1, 14);
	fm2.LossAmount.value = PersonUWGrid.getRowColData(tSelNo-1, 15);
	fm2.Reason.value = PersonUWGrid.getRowColData(tSelNo-1, 16);
	fm2.Remark.value = PersonUWGrid.getRowColData(tSelNo-1, 17);
	
	//modify by ZhangC 20150116 ��ӷֹ�˾�˱����ۡ��ܹ�˾�˱�����
	var tSegment1 = PersonUWGrid.getRowColData(tSelNo-1, 19);
	if (tSegment1=="" || tSegment1=="0800100001") {
		
		fm2.UWOpinion.value = "";
		fm2.UWOpinionName.value = "";
		fm2.UWDesc.value = "";
	} else if (tSegment1=="0800100002") {
	
		fm2.UWOpinion.value = PersonUWGrid.getRowColData(tSelNo-1, 21);
		fm2.UWOpinionName.value = PersonUWGrid.getRowColData(tSelNo-1, 22);
		fm2.UWDesc.value = PersonUWGrid.getRowColData(tSelNo-1, 23);
	} else if (tSegment1=="0800100003") {
		
		fm2.UWOpinion.value = PersonUWGrid.getRowColData(tSelNo-1, 24);
		fm2.UWOpinionName.value = PersonUWGrid.getRowColData(tSelNo-1, 25);
		fm2.UWDesc.value = PersonUWGrid.getRowColData(tSelNo-1, 26);
	} else {
		fm2.UWOpinion.value = PersonUWGrid.getRowColData(tSelNo-1, 27);
		fm2.UWOpinionName.value = PersonUWGrid.getRowColData(tSelNo-1, 28);
		fm2.UWDesc.value = PersonUWGrid.getRowColData(tSelNo-1, 29);
	}
	
	if (tDataSource=="0") {
		divRemark.style.display = "none";
		
		fm2.ContNo.readOnly = true;
		fm2.GrpName.readOnly = true;
		fm2.InsuredName.readOnly = true;
		fm2.IDNo.readOnly = true;
		fm2.Birthday.readOnly = true;
		fm2.Age.readOnly = true;
		fm2.RiskName.readOnly = true;
		fm2.LossAmount.readOnly = true;
		fm2.Reason.readOnly = true;
	} else {
		divRemark.style.display = "none";
		
		fm2.ContNo.readOnly = false;
		fm2.GrpName.readOnly = false;
		fm2.InsuredName.readOnly = false;
		fm2.IDNo.readOnly = false;
		fm2.Birthday.readOnly = false;
		fm2.Age.readOnly = false;
		fm2.RiskName.readOnly = false;
		fm2.LossAmount.readOnly = false;
		fm2.Reason.readOnly = false;
	}
}

/**
 * ���˺˱���Ϣ�ύ
 */
function perUWSubmitForm() {
	
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
	fm2.PerUWOperate.value = mPerUWOperate;
	fm2.action = "./LSQuotPersonUWSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	fm2.submit();
}

/**
 * �ϴ�����
 */
function upLoadFile() {
	
	var tSelNo = PersonUWGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ�����˺˱���Ϣ��");
		return false;
	}
	
	var tPerUWSerialNo = PersonUWGrid.getRowColData(tSelNo-1, 1);
	
	if (fmupload.UploadPath.value==null || fmupload.UploadPath.value=="") {
		alert("�����ļ�·������Ϊ�գ�");
		return false;
	}
	
	var tFileName = fmupload.all('UploadPath').value;
	var tExtenName = "";
	
	if (tFileName.indexOf("\\")>0) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("\\")+1);
	}
	if (tFileName.indexOf("/")>0 ) {
		tFileName = tFileName.substring(tFileName.lastIndexOf("/")+1);
	}
	
	if (tFileName.indexOf(".")>0 ) {
		tExtenName = tFileName.substring(tFileName.lastIndexOf(".")+1);
	}
	
	if (tExtenName!="jpg" && tExtenName!="gif") {
		alert("��֧��jpg,gif��ʽ���ļ����أ�");
		return false;
	}
	
	mPerUWOperate = "PerUWUPLOAD";
	mOperate = "";
	
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
	fmupload.action = "./LSQuotPersonUWUploadSave.jsp?OtherNoType=QUOT&OtherNo="+tQuotNo+"&SubOtherNo="+tQuotBatNo+"&UploadNode="+tActivityID+"&Operate="+mPerUWOperate+"&AttachType=01&PerUWSerialNo="+tPerUWSerialNo;
	fmupload.submit();
}

/**
 * ��������
 */
function downFile(parm1,parm2) {
	
	var tAttachID =document.all('PersonUWGrid30').value;
	if (tAttachID==null || tAttachID=="") {
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPersonUWSql");
	tSQLInfo.setSqlId("LSQuotPersonUWSql3");
	tSQLInfo.addSubPara(tAttachID);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		var tFilePath = strQueryResult[0][0];
		var tFileName = strQueryResult[0][1];
		
		window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	}
}

/**
 * ���ܼ����ѯ
 */
function queryAllCal() {
	
	window.open("./LSQuotQueryAllCalMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&ActivityID="+ tActivityID,"���ܼ����ѯ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��ս���
 */
function clearPastTotal() {
	
	fm.InsuYear.value = "";
	fm.InsuranceCom.value = "";
	fm.InsuranceComName.value = "";
	fm.SumPrem.value = "";
	fm.SumLoss.value = "";
	fm.SumClaimPeople.value = "";
	fm.MaleRate.value = "";
	fm.FemaleRate.value = "";
}
