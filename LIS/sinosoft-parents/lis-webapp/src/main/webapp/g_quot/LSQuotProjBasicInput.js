/***************************************************************
 * <p>ProName��LSQuotProjBasicInput.js</p>
 * <p>Title����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Description����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mLinkProNo = "0";//�Ƿ�ѡ����������Ŀѯ�ۺ� 0-δ��ѡ

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
		initForm();
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
	
	document.getElementById("divPlanDiv").innerHTML = showPlanDiv();
	document.getElementById("divPayIntvDiv").innerHTML = showPayIntvDiv();
	document.getElementById("divSaleChnlDiv").innerHTML = showSaleChnlDiv();
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
		tBasicInfo[i++] = "ElasticFlag";
		tBasicInfo[i++] = "ElasticFlagName";
		tBasicInfo[i++] = "ProjDesc";
		
		for(var t=0; t<i; t++) {
			document.getElementById(tBasicInfo[t]).value = tBasicArr[0][t];
		}
		var tProdType = fm2.ProdType.value;
		if (tProdType == "00") {//��ͨ����
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = 'none';
			document.getElementById("td6").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = '';
			document.getElementById("td6").style.display = '';
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

		document.getElementById('LinkInquiryNo').checked = true;
		document.getElementById('divLinkInquiryNo').style.display = '';
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
 * ѡ���������Ƿ�չʾ�н�����
 */
function showIfAgencyName() {
	
	pubShowAgencyInfoCheck(fm2);
}


/**
 * ����������Ŀѯ�ۺż�����Ϣ
 */
function showRelaQuot() {

	if (document.getElementById('LinkInquiryNo').checked) {
		
		//initLinkInquiryNoGrid();
		mLinkProNo = "1";//�ѹ�ѡ--����������Ŀѯ�ۺż�����Ϣ
		document.getElementById('divLinkInquiryNo').style.display = '';
	} else {
		
		//initLinkInquiryNoGrid();
		mLinkProNo = "0";////δ��ѡ--����������Ŀѯ�ۺż�����Ϣ
		document.getElementById('divLinkInquiryNo').style.display = 'none';
	}
}

/**
 * ���������ϢǰУ��
 */
function basicCheck() {

	//У�������Ϣ�Ƿ�¼������
	if (isEmpty(fm2.QuotNo)) {
		alert("ѯ�ۺŲ���Ϊ�գ�");
		return false;
	}
	
	if (isEmpty(fm2.QuotBatNo)) {
		alert("���κŲ���Ϊ�գ�");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (isEmpty(fm2.ProdType)) {
		
		alert("��Ʒ���Ͳ���Ϊ�գ�");
		return false;
	} else {
		
		//�Բ�Ʒ�������У��,��Ʒ���Ͳ�һ��ʱ,������պ���������ʾ
		var tProdType = getProdType(tQuotNo, tQuotBatNo);
		if (tProdType==null || tProdType=="") {
			//Ϊ��,��ʾû�в�ѯ��
		} else {
			
			if (tProdType!=fm2.ProdType.value) {
				
				//У���ѯ�������Ƿ��ύ��,�ύ���Ĳ��������
				var tMissionCount = getNotInputCount(tQuotNo, tQuotBatNo);
				if (tMissionCount=="0") {
				
				} else {
					alert("��ѯ���Ѿ��ύ���˱���ˣ������ٴα����Ʒ���ͣ�");
					return false;
				}

				if (!confirm("��Ʒ���ͷ����˸ı䣬ȷ�Ϻ���ճ�������Ϣ����������ݣ��Ƿ�ȷ�ϣ�")) {
					return false;
				}
			}
		}
	}
	
	if (fm2.ProdType.value == '00') {
		
		if (isEmpty(fm2.ElasticFlag)) {
			
			alert("�Ƿ�Ϊ���Լƻ�����Ϊ�գ�");
			return false;
		}
	}
	
	if (fm2.ExpiryDate.value<tCurrentDate) {
		alert("��Чֹ�ڲ������ڵ�ǰ���ڣ�");
		return false;
	}
	
	//У�鱣�ϲ㼶���ֱ�׼
	if (!checkElements()) {
		return false;
	}
	
	if (fm2.ProjDesc.value.length>1500) {
		alert("��Ŀ˵��ӦС��1500�֣�");
		return false;
	}
	
	//У��ɷѷ�ʽ
	if (!checkPayIntv()) {
		return false;
	}
	
	//У�����û���
	if (!checkAppCom()) {
		return false;
	}
	
	//У�����������Ƿ�ѡ��
	if (!checkSaleChnl()) {
		return false;
	}
	
	//����������Ŀѯ�ۺż�����Ϣ
	if (!checkLinkInquiryNo()) {
		return false;
	}
	
	return true;
}

/**
 * У��--���û���
 */
function checkAppCom() {
	
	AppOrgCodeGrid.delBlankLine();//ɾ������
	if(AppOrgCodeGrid.mulLineCount<=0) {
 	
		alert("���û�������Ϊ�գ�");
		return false;
 	} else {
 		var vRow = AppOrgCodeGrid.mulLineCount;
		for (var i = 0; i < vRow; i++) {
			var mAppCom = AppOrgCodeGrid.getRowColData(i, 1);
			for (var j = 0; j < i; j++) {
				var nAppCom = AppOrgCodeGrid.getRowColData(j, 1);
				if (mAppCom == nAppCom) {
					alert("���û����ظ������޸ģ�");
					return false;
				}
			}
		}
 	}
	return true;
}


/**
 * У��--����������Ŀѯ�ۺż�����Ϣ
 */
function checkLinkInquiryNo() {
	
	if (document.getElementById('LinkInquiryNo').checked) {
		LinkInquiryNoGrid.delBlankLine();//ɾ������
		if(LinkInquiryNoGrid.mulLineCount<=0) {
	 	
			alert("����ѯ�ۺŲ���Ϊ�գ�");
			return false;
	 	} else {
	 		var vRow = LinkInquiryNoGrid.mulLineCount;
			for (var i = 0; i < vRow; i++) {
				var mLinkNo = LinkInquiryNoGrid.getRowColData(i, 1);
				for (var j = 0; j < i; j++) {
					var nLinkNo = LinkInquiryNoGrid.getRowColData(j, 1);
					if (mLinkNo == nLinkNo) {
						alert("����ѯ�ۺ��ظ������޸ģ�");
						return false;
					}
				}
			}
	 	}
	}
	return true;
}

///**
// * ��ѯ���û�������
// */
//
//function queryCom() {
//	
//	var tSelNo = AppOrgCodeGrid.lastFocusRowNo;//�кŴ�0��ʼ
//	var strUrl = "LSQuotQueryAppComMain.jsp?SelNo="+tSelNo;
//	window.open(strUrl,"���û�����ѯ",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
//}


/**
 * ������Ϣ����
 */
function basicSubmit() {

	if (!basicCheck()) {
		return false;
	}
	
	showRelaQuot();//��ֹˢ�½���û���
	fm2.action = "./LSQuotProjBasicSave.jsp?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&LinkProNo="+ mLinkProNo +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	
	submitForm(fm2);
}


/**
 * ��һ��
 */
function nextStep() {
	
	goToStep(2);
	
}

/**
 * ��Ŀ¼
 */
function returnLR() {
	
	goToStep(0);
}


function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='prodtype') {
		
		var tSql = "1 and codetype=#prodtype# and codeexp=#"+tQuotType+"#";

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	}
}

/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=='queryexp') {//��Ʒ����
		
		if (Field.value=='00') {//��ͨ����
			document.getElementById("td1").style.display = '';			
			document.getElementById("td2").style.display = '';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = 'none';
			document.getElementById("td6").style.display = 'none';
		} else {
			document.getElementById("td1").style.display = 'none';			
			document.getElementById("td2").style.display = 'none';
			document.getElementById("td3").style.display = '';
			document.getElementById("td4").style.display = '';
			document.getElementById("td5").style.display = '';
			document.getElementById("td6").style.display = '';
		}
	}
}
