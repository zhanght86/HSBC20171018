/***************************************************************
 * <p>ProName��LSQuotETSubmitInput.js</p>
 * <p>Title��ѯ���ύ</p>
 * <p>Description��ѯ���ύ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-24
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

/** ѯ��--��һ��--��ʼ*/
/**
 * ��������׼�ͻ�
 */
function relaCustClick() {

	if (document.getElementById('RelaCustomerFlag').checked) {
		
		document.getElementById('divRelaCustInfo').style.display = '';
	} else {
		
		document.getElementById('divRelaCustInfo').style.display = 'none';
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
	
	document.getElementById("divPlanDiv").innerHTML = showPlanDiv("1");
}

/**
 * ��ʼ��������Ϣ
 */
function initBasicInfo() {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql5");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tBasicArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tBasicArr==null) {
		return false;
	} else {
	
		var tBasicInfo = new Array();
		var i = 0;
		//tBasicInfo[i] = "";
		tBasicInfo[i++] = "PreCustomerNo";
		tBasicInfo[i++] = "PreCustomerName";
		tBasicInfo[i++] = "IDType";
		tBasicInfo[i++] = "IDTypeName";
		tBasicInfo[i++] = "IDNo";
		tBasicInfo[i++] = "GrpNature";
		tBasicInfo[i++] = "GrpNatureName";
		tBasicInfo[i++] = "BusiCategory";
		tBasicInfo[i++] = "BusiCategoryName";
		tBasicInfo[i++] = "Address";
		tBasicInfo[i++] = "ProdType";
		tBasicInfo[i++] = "ProdTypeName";
		tBasicInfo[i++] = "PremMode";
		tBasicInfo[i++] = "PremModeName";
		tBasicInfo[i++] = "PrePrem";
		tBasicInfo[i++] = "RenewFlag";
		tBasicInfo[i++] = "RenewFlagName";
		tBasicInfo[i++] = "BlanketFlag";
		tBasicInfo[i++] = "BlanketFlagName";
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "PayIntv";
		tBasicInfo[i++] = "PayIntvName";
		tBasicInfo[i++] = "InsuPeriod";
		tBasicInfo[i++] = "InsuPeriodFlag";
		tBasicInfo[i++] = "InsuPeriodFlagName";
		tBasicInfo[i++] = "Coinsurance";
		tBasicInfo[i++] = "CoinsuranceName";
		tBasicInfo[i++] = "ValDateType";
		tBasicInfo[i++] = "ValDateTypeName";
		tBasicInfo[i++] = "AppointValDate";
		tBasicInfo[i++] = "CustomerIntro";
		
		for (var t=0; t<i; t++) {
			document.getElementById(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		
		var tValDateType = fm2.ValDateType.value;
		if (tValDateType=="1") {
			
			document.getElementById("tdValDate1").style.display = '';			
			document.getElementById("tdValDate2").style.display = '';
			document.getElementById("tdValDate3").style.display = 'none';
			document.getElementById("tdValDate4").style.display = 'none';
		}
		
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//��ͨ����
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = 'none';
			document.getElementById("td4").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
		}
	}
	//������������
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql12");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr1 = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr1==null) {
		
		return false;
	} else {
		
		fm2.SaleChannel.value = tArr1[0][0];
		fm2.SaleChannelName.value = tArr1[0][1];
		
		if (tArr1[0][2].substring(0,1)=="1") {//��Ҫ�н�
			
			//�Ȳ�ѯ�н���Ϣ
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql22");
			tSQLInfo.addSubPara(tQuotNo);
			tSQLInfo.addSubPara(tQuotBatNo);
			noDiv(turnPage2, AgencyListGrid, tSQLInfo.getString());
			
			document.getElementById("divAgencyInfo").style.display = "";
		}
	}

	//����׼�ͻ�����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql13");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);

	if (!noDiv(turnPage2, RelaCustListGrid, tSQLInfo.getString())) {
		initRelaCustListGrid();
		return false;
  }

  if (RelaCustListGrid.mulLineCount>0) {

		document.getElementById('RelaCustomerFlag').checked = true;
		document.getElementById('divRelaCustInfo').style.display = '';
  }
}

/** ѯ��--��һ��--����*/

/**
 * չʾ������Ϣ
 */
function showEnginInfo() {
	
	if (tTranProdType=="00") {
		document.getElementById('divEngin').style.display = 'none';
	} else if (tTranProdType=="02") {
		document.getElementById('divEngin').style.display = 'none';
	} else {
		document.getElementById('divEngin').style.display = '';
		queryEnginInfo(fm2);
		document.getElementById("divEnginFactor").innerHTML = showEnginFactorDiv(tQuotNo, tQuotBatNo, '0000000000', '000', '1');
		pubShowConditionCheck(fm2);
	}
}

/**
 * ��ѯ������Ϣ
 */
function queryEnginInfo(cObj) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql23");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		//��������
	} else {
		
		var tEnginInfo = new Array();
		var i = 0;
		tEnginInfo[i++] = "EnginName";
		tEnginInfo[i++] = "EnginType";
		tEnginInfo[i++] = "EnginTypeName";
		tEnginInfo[i++] = "EnginArea";
		tEnginInfo[i++] = "EnginCost";
		tEnginInfo[i++] = "EnginPlace";
		tEnginInfo[i++] = "EnginStartDate";
		tEnginInfo[i++] = "EnginEndDate";
		tEnginInfo[i++] = "EnginDesc";
		tEnginInfo[i++] = "EnginCondition";
		tEnginInfo[i++] = "Remark";
		tEnginInfo[i++] = "InsurerName";
		tEnginInfo[i++] = "InsurerType";
		tEnginInfo[i++] = "InsurerTypeName";
		tEnginInfo[i++] = "ContractorName";
		tEnginInfo[i++] = "ContractorType";
		tEnginInfo[i++] = "ContractorTypeName";
		
		for (var t=0; t<i; t++) {
			
			document.getElementById(tEnginInfo[t]).value = tArr[0][t];
		}
	}
}

/**
 * ��ʼ��������Ϣ��ѯ
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql14");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 1, 1);//����λ��ʾʹ�ô�������
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
		
		location.href = "./LSQuotETQueryInput.jsp";
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
	
	var tCoinsurance = fm2.Coinsurance.value;
	fmOther.action = "./LSQuotEnterFinalSave.jsp?Operate=ENTERSUBMIT&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID +"&TranProdType="+ tTranProdType  +"&Coinsurance="+ tCoinsurance;
	submitForm(fmOther);
}

function showAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID) {
	
	cObj.PageInfo.value = "��"+OnPage+"/"+PageNum+"ҳ";
	document.getElementById("divShowAllPlan").innerHTML = pubShowAllPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cActivityID);
}

/**
 * չʾָ��ҳ
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showAllPlanDetail(fm2, strQueryResult,tStartNum, tQuotType, tTranProdType, tActivityID);
}
