<%
/***************************************************************
 * <p>ProName��LSQuotUWDetailInit.jsp</p>
 * <p>Title��һ��ѯ�ۺ˱���ϸ</p>
 * <p>Description��һ��ѯ�ۺ˱���ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/
%>
<script language="JavaScript">
var tSubUWFlag = 0;//�Ƿ񾭹���֧��˾�˱���ǣ�0-��1-��
var tTranProdType = "";

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotUWSql");
		tSQLInfo.setSqlId("LSQuotUWSql20");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tSubUWFlag = 1;
			divSubUW.style.display = "";
		} else {
			divSubUW.style.display = "none";
		}
					
		queryQuotBasic();
		querySaleChannel();
		queryPlanDiv();
		
		initUWMainPointGrid();
		initOtherOpinionGrid();
		initAgencyListGrid();
		initRelaCustListGrid();
		initPlanInfoGrid();
		
		queryQuotAgency();
		queryRelaCust();
		initQuotStep2();
		queryUWMainPoint();
		queryOtherOpinion();
		queryProcess();
		judgeShowQuest(tQuotNo, tQuotBatNo, tActivityID);
		
		if (tActivityID=="0800100002") {
			
			//�Ȱ���ʽ������
			document.getElementById("tdUWRule1").className = "commontop";
			document.getElementById("tdUWRule2").className = "titleImgtop";
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "none";
			divSubUW.style.display = "";
			
			fm3.UWAnalySaveButton.style.display = "";
			fmEngin.EnginSaveButton.style.display = "";
			fmPlan.AddPlanButton.style.display = "";
			fmPlan.ModifyPlanButton.style.display = "";
			fmPlan.DelPlanButton.style.display = "";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "";
			
			divUW.style.display = "none";
			
		} else if (tActivityID=="0800100003") {
			
			document.getElementById("tdUWRule1").className = "commontop";
			document.getElementById("tdUWRule2").className = "titleImgtop";
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "";
			fmEngin.EnginSaveButton.style.display = "";
			fmPlan.AddPlanButton.style.display = "";
			fmPlan.ModifyPlanButton.style.display = "";
			fmPlan.DelPlanButton.style.display = "";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			divUW.style.display = "none";
			
		} else if (tActivityID=="0800100004") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			
			divUWManagerButton.style.display = "none";
			if (isEmpty(fm1.UWManagerConclu)) {
				divUWManager.style.display = "none";
			} else {
				divUWManager.style.display = "";
			}
			
			divOtherOpinion.style.display = "";
			divUW.style.display = "";
			divOtherOpinion.style.display = "";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			showReinsInfo();
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
		} else if (tActivityID=="0800100005") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "none";
			divUWManager.style.display = "";
			divOtherOpinion.style.display = "";
			divUW.style.display = "";
			divOtherOpinion.style.display = "";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "";
			
			showReinsInfo();
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			
		} else if (tActivityID=="0800100006") {
			
			divSubFinal.style.display = "none";
			divBranchFinal.style.display = "";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "";
			divOtherOpinion.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tSubUWFlagNew = querySubUWFlag();
			if (tSubUWFlagNew=="0") {
				divSubUW.style.display = "none";
			} else {
				divSubUW.style.display = "";
			}
			var tTotalFlag = queryTotalFlag();
			if (tTotalFlag=="0") {
				divUW.style.display = "none";
			} else {
				divUW.style.display = "";
			}
		} else if (tActivityID=="0800100007") {
			
			divSubFinal.style.display = "";
			divBranchFinal.style.display = "";
			divUWManager.style.display = "none";
			divOtherOpinion.style.display = "none";
			divUW.style.display = "none";
			divUWRule.style.display = "";
			divUWMainPoint.style.display = "";
			divUWMainPointDetail.style.display = "none";
			divBranchUW.style.display = "";
			
			divSubUWOpinionButton.style.display = "none";
			divSubUWSubmitButton.style.display = "none";
			divBranchUWOpinionButton.style.display = "none";
			divBranchUWSubmitButton.style.display = "none";
			divOtherOpinionButton.style.display = "none";
			divUWOpinionButton.style.display = "none";
			divUWSubmitButton.style.display = "none";
			divBranchFinalButton.style.display = "none";
			
			fm3.UWAnalySaveButton.style.display = "none";
			fmEngin.EnginSaveButton.style.display = "none";
			fmPlan.AddPlanButton.style.display = "none";
			fmPlan.ModifyPlanButton.style.display = "none";
			fmPlan.DelPlanButton.style.display = "none";
			fmPlan.PlanDetailButton.style.display = "";
			fm1.ShowCoinButton.style.display = "none";
			
			var tTotalFlag = queryTotalFlag();
			if (tTotalFlag=="0") {
				divUW.style.display = "none";
			} else {
				divUW.style.display = "";
			}
		}
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {

		if (tActivityID!="0800100002" && tActivityID!="0800100003" && tActivityID!="0800100004") {
			return;
		}
		
		var b1 = "0";//ҵ������
		var b2 = "0";//������Ϣ
		var b3 = "0";//������Ϣ
		var b4 = "0";//�ʾ����
		var b5 = "0";//��������
		var b6 = "0";//�ر�Լ��
		var b7 = "0";//��������
		
		if (tActivityID=="0800100002") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql36");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		} else if (tActivityID=="0800100003") {
		
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql37");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		} else if (tActivityID=="0800100004") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql38");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
		
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				b1 =  tArr[0][0];
				b2 =  tArr[0][1];
				b3 =  tArr[0][2];
				b4 =  tArr[0][3];
				b5 =  tArr[0][4];
				b6 =  tArr[0][5];
				b7 =  tArr[0][6];
			}
		}

		if (b1=="0") {
			fm1.all("RequestButton").className = "cssButton2";
		}
		
		if (b2=="0") {
			fm1.all("FeeButton").className = "cssButton2";
		}
		
		if (b3=="0") {
			fm1.all("PastButton").className = "cssButton2";
		}
		
		if (b4=="0") {
			fm1.all("QuesButton").className = "cssButton2";
		}
		
		if (b5=="0") {
			fm1.all("AttachButton").className = "cssButton2";
		}

		if (b6=="0") {
			fm1.all("SpecButton").className = "cssButton2";
		}
		
		if (tQuotType=="00") {//ֻ��һ��ѯ�۲��й�������
		
			if (b7=="0") {
				fm1.all("ShowCoinButton").className = "cssButton2";
			}
		}
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

/**
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}

/**
 * ��ʼ���б�
 */
function initUWMainPointGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ʒ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ʒ����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱�Ҫ�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ҫ������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		if (tActivityID=="0800100002") {
			
			iArray[i] = new Array();
			iArray[i][0] = "��֧��˾Ҫ�����";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾Ҫ�����";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			if (tSubUWFlag==1) {
			
				iArray[i] = new Array();
				iArray[i][0] = "��֧��˾Ҫ�����";
				iArray[i][1] = "90px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			} else {
				
				iArray[i] = new Array();
				iArray[i][0] = "��֧��˾Ҫ�����";
				iArray[i][1] = "90px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "�ֹ�˾Ҫ�����";
			iArray[i][1] = "90px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		UWMainPointGrid = new MulLineEnter("fm3", "UWMainPointGrid");
		UWMainPointGrid.mulLineCount = 0;
		UWMainPointGrid.displayTitle = 1;
		UWMainPointGrid.locked = 1;
		UWMainPointGrid.canSel = 1;
		UWMainPointGrid.canChk = 0;
		UWMainPointGrid.hiddenSubtraction = 1;
		UWMainPointGrid.hiddenPlus = 1;
		UWMainPointGrid.selBoxEventFuncName = "showUWMainPoint";
		UWMainPointGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��UWMainPointGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initOtherOpinionGrid() {

	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������������ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����·��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����(˫������)";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "downFile";
		
		OtherOpinionGrid = new MulLineEnter("fm2", "OtherOpinionGrid");
		OtherOpinionGrid.mulLineCount = 0;
		OtherOpinionGrid.displayTitle = 1;
		OtherOpinionGrid.locked = 1;
		OtherOpinionGrid.canSel = 1;
		OtherOpinionGrid.canChk = 0;
		OtherOpinionGrid.hiddenSubtraction = 1;
		OtherOpinionGrid.hiddenPlus = 1;
		OtherOpinionGrid.selBoxEventFuncName = "showOtherOpinion";
		OtherOpinionGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��OtherOpinionGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initAgencyListGrid() {

	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AgencyListGrid = new MulLineEnter("fm3", "AgencyListGrid");
		AgencyListGrid.mulLineCount = 0;
		AgencyListGrid.displayTitle = 1;
		AgencyListGrid.locked = 1;
		AgencyListGrid.canSel = 0;
		AgencyListGrid.canChk = 0;
		AgencyListGrid.hiddenSubtraction = 1;
		AgencyListGrid.hiddenPlus = 1;
		AgencyListGrid.selBoxEventFuncName = "";
		AgencyListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��AgencyListGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initRelaCustListGrid() {
	
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RelaCustListGrid = new MulLineEnter("fm3", "RelaCustListGrid");
		RelaCustListGrid.mulLineCount = 0;
		RelaCustListGrid.displayTitle = 1;
		RelaCustListGrid.locked = 1;
		RelaCustListGrid.canSel = 0;
		RelaCustListGrid.canChk = 0;
		RelaCustListGrid.hiddenSubtraction = 1;
		RelaCustListGrid.hiddenPlus = 1;
		RelaCustListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��RelaCustListGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initPlanInfoGrid() {

	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "0px";
		} else {
			iArray[i][1] = "40px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 3;
		} else {
			iArray[i++][3] = 0;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlagName
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ������";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ������(��)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�μ��籣ռ��";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҵ����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ����н(Ԫ)";
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "40px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='02') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����˵��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PlanInfoGrid = new MulLineEnter("fmPlan", "PlanInfoGrid");
		PlanInfoGrid.mulLineCount = 0;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.selBoxEventFuncName = "showPlanInfo";
		PlanInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
