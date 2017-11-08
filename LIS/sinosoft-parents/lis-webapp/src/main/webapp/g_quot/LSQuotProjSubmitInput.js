/***************************************************************
 * <p>ProName��LSQuotProjSubmitInput.js</p>
 * <p>Title��ѯ���ύ</p>
 * <p>Description��ѯ���ύ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-25
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//�н���� initAgencyListGrid
var turnPage2 = new turnPageClass();//����׼�ͻ����� initRelaCustListGrid
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var turnPage9 = new turnPageClass();

/**
 * ��ʼ��ѯ�۵�һ����Ϣ
 */
function initQuotStep1() {
	
	fm2.QuotNo.value = tQuotNo;
	fm2.QuotBatNo.value = tQuotBatNo;
	
	//��ʼ��������Ϣ
	initBasicInfo();
	
	fm2.all("divPlanDiv").innerHTML = showPlanDiv("1");
	fm2.all("divPayIntvDiv").innerHTML = showPayIntvDiv("1");
	fm2.all("divSaleChnlDiv").innerHTML = showSaleChnlDiv("1");
	pubShowAgencyInfoCheck(fm2);
}

/**
 * ��Ŀѯ��--��ʼ��������Ϣ
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql1");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
		
		var tBasicInfo = new Array();
		var i = 0;
		tBasicInfo[i] = "";
		tBasicInfo[i++] = "ProjName";
		tBasicInfo[i++] = "TargetCust";
		tBasicInfo[i++] = "NumPeople";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "PreLossRatio";
		tBasicInfo[i++] = "Partner";
		tBasicInfo[i++] = "ExpiryDate";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			fm2.all(tBasicInfo[t]).value = tBasicArr[0][t];
		}
	}
	
	//�������û���
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage2, AppOrgCodeGrid, tSQLInfo.getString())) {
		initAppOrgCodeGrid();
		return false;
	}
	
	//����������Ŀѯ�ۺż�����Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage4, LinkInquiryNoGrid, tSQLInfo.getString())) {
		initLinkInquiryNoGrid();
		return false;
	}
  
	if (LinkInquiryNoGrid.mulLineCount>0) {

		fm2.all('LinkInquiryNo').checked = true;
		fm2.all('divLinkInquiryNo').style.display = '';
	}
}

/**
 * չʾ�н��������
 */
function showAgencyInfo() {
	
	//�����н�����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotProjBasicSql");
	tSQLInfo.setSqlId("LSQuotProjBasicSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	if (!noDiv(turnPage3, AgencyNameGrid, tSQLInfo.getString())) {
		initAgencyNameGrid();
		return false;
	}
}


/**
 * ��ʼ��������Ϣ��ѯ
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql25");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid,1,1);//����λ��ʾʹ�ô�������
}
/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		location.href = "./LSQuotProjQueryInput.jsp";
	}	
}

/**
 * ��һ��
 */
function previousStep() {
	
	goToStep(2);
}

/**
 * ��Ŀ¼
 */
function returnLR() {
	
	goToStep(0);
}

/**
 * ѯ����Ϣ�ύ
 */
function quotInfoSubmit() {
	
	fmOther.action = "./LSQuotEnterFinalSave.jsp?Operate=ENTERSUBMIT&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType;
	submitForm(fmOther);
}

function showAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	cObj.PageInfo.value = "��"+OnPage+"/"+PageNum+"ҳ";
	document.all("divShowAllPlan").innerHTML = pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID);
}

/**
 * չʾָ��ҳ
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showAllPlanDetail(fm2, strQueryResult,tStartNum, tQuotType, tTranProdType, tActivityID);
}
