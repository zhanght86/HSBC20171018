/***************************************************************
 * <p>ProName��LSQuotProjDetailViewInput.js</p>
 * <p>Title����ϸ�鿴(��Ŀѯ��)</p>
 * <p>Description����ϸ�鿴(��Ŀѯ��)</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var tTranPremMode;//���ѷ�̯��ʽ

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
	}	
}

/**
 * ѡ�񱨼۷���
 */
function selectQuotPlan() {
	
	window.open("./LSQuotSelectPlanMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo + "&OfferListNo="+ tOfferListNo +"&TranProdType="+ tTranProdType+"&QuotQuery="+ tQuotQuery,"ѡ�񱨼۷���",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ���۷�����ϸ
 */
function offerPalnDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql7");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tBJPlanNum = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBJPlanNum[0][0]==0) {
		alert("����ѡ�񱨼۷���!");
		return false;
	}
	window.open("./LSQuotBJPlanDetailMain.jsp?QuotType="+ tQuotType +"&QuotNo="+ tQuotNo + "&QuotBatNo="+ tQuotBatNo +"&TranProdType="+ tTranProdType +"&OfferListNo="+ tOfferListNo+"&QuotQuery="+ tQuotQuery,"���۷�����ϸ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ����
 */
function returnPrint() {
	if(tReturnFlag=="1"){
		top.close();
	}else{
		location.href = "./LSQuotPrintInput.jsp";
	}
}


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
		tBasicInfo[i++] = "NumPeopleA";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "PreLossRatio";
		tBasicInfo[i++] = "Partner";
		tBasicInfo[i++] = "ExpiryDate";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			fm2.all(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//��ͨ����
			fm2.all("tdElasticFlag1").style.display = '';			
			fm2.all("tdElasticFlag2").style.display = '';
			fm2.all("tdElasticFlag3").style.display = '';
			fm2.all("tdElasticFlag4").style.display = '';
			fm2.all("tdElasticFlag5").style.display = 'none';
			fm2.all("tdElasticFlag6").style.display = 'none';
		} else {
			fm2.all("tdElasticFlag1").style.display = 'none';			
			fm2.all("tdElasticFlag2").style.display = 'none';
			fm2.all("tdElasticFlag3").style.display = '';
			fm2.all("tdElasticFlag4").style.display = '';
			fm2.all("tdElasticFlag5").style.display = '';
			fm2.all("tdElasticFlag6").style.display = '';
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
 * ��ʼ��������Ϣ
 */
function initQuotStep2() {
	
	queryPlanInfo();
	pubInitQuotStep2(fm2, tQuotType, tTranProdType, tTranPremMode, '');
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
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 2, 1);
}
/**
 * ѡ�񷽰���¼����
 */
function showPlanDetailInfo() {
	
	fm2.all('divInfo2').style.display = '';
	pubShowPlanInfo(fm2, tQuotType, tTranProdType);
	fm2.SexRate.value = fm2.MaleRate.value + " : " + fm2.FemaleRate.value;
}


/**
 * �رշ�����ϸ
 */
function closePlanDetail() {
	
	fm2.all('divInfo2').style.display = 'none';
}

/**
 * ��ѯ�˱�������Ϣ
 */
function queryFinalConclu() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotPrintSql");
	tSQLInfo.setSqlId("LSQuotPrintSql6");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArrUW = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if (tArrUW != null){
		if (tArrUW[0][2]==null || tArrUW[0][2] == "") {
			
			fm2.FinalOpinion.value = tArrUW[0][3];
			fm2.FinalConclu.value = tArrUW[0][4];
			fm2.FinalConcluName.value = tArrUW[0][5];
			
		} else {
			fm2.FinalOpinion.value = tArrUW[0][0];
			fm2.FinalConclu.value = tArrUW[0][1];
			fm2.FinalConcluName.value = tArrUW[0][2];
		}
	}
}

