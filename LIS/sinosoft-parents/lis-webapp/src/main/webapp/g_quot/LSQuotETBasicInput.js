/***************************************************************
 * <p>ProName��LSQuotETBasicInput.js</p>
 * <p>Title��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Description��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
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
var mPreCustomerFlag = false;//׼�ͻ����ƹ�����Ϣ��ѯ��ʽ

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
	
	document.getElementById("divPlanDiv").innerHTML = showPlanDiv();
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

/**
 * �ύ����
 */
function basicSubmit() {

	if (!basicCheck()) {
		return false;
	}

	fm2.action = "./LSQuotETBasicSave.jsp?Operate=SAVEBASIC&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID;
	submitForm(fm2);
}

/**
 * ����У��
 */
function basicCheck() {

	//У�������Ϣ�Ƿ�¼������
	if (isEmpty(fm2.PreCustomerNo) || isEmpty(fm2.PreCustomerName)) {
		alert("׼�ͻ����Ʋ���Ϊ�գ�");
		return false;
	} else {
		var tPreCustomerNo = fm2.PreCustomerNo.value;
		var tPreCustomerName = fm2.PreCustomerName.value;
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql43");
		tSQLInfo.addSubPara(tPreCustomerNo);
		tSQLInfo.addSubPara(tPreCustomerName);
		
		var tArrCount = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArrCount[0][0]=="0") {
			alert("׼�ͻ����Ʋ����ڣ�");
			return false;
		}
	}
	
	if (isEmpty(fm2.ProdType)) {
		
		alert("��Ʒ���಻��Ϊ�գ�");
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
	
	if (isEmpty(fm2.SaleChannel)) {
		
		alert("�������Ͳ���Ϊ�գ�");
		return false;
	}
	
	if (isEmpty(fm2.PremMode)) {
		
		alert("���ѷ�̯��ʽ����Ϊ�գ�");
		return false;
	}
	
	var tPrePrem = fm2.PrePrem.value;
	if (isEmpty(fm2.PrePrem)) {//�����У��?С��У��
		
		alert("Ԥ�Ʊ��ѹ�ģ����Ϊ�գ�");
		return false;
	}
	
	if (!isNumeric(tPrePrem)) {
		alert("Ԥ�Ʊ��ѹ�ģ������Ч���֣�");
		return false;
	}
	if (Number(tPrePrem)<0) {
		alert("Ԥ�Ʊ��ѹ�ģӦ���ڵ���0��");
		return false;
	}
	
	if (!checkDecimalFormat(tPrePrem, 14, 2)) {
		alert("Ԥ�Ʊ��ѹ�ģ����λ��Ӧ����14λ,С��λ��Ӧ����2λ��");
		return false;
	}
	if (isEmpty(fm2.RenewFlag)) {
		
		alert("�Ƿ�Ϊ����ҵ����Ϊ�գ�");
		return false;
	}
	
	if (isEmpty(fm2.BlanketFlag)) {
		
		alert("�Ƿ�Ϊͳ��������Ϊ�գ�");
		return false;
	}
	
	if (fm2.ProdType.value == '00') {
		
		if (isEmpty(fm2.ElasticFlag)) {
			
			alert("�Ƿ�Ϊ���Լƻ�����Ϊ�գ�");
			return false;
		}
	}
	
	if (isEmpty(fm2.InsuPeriod)) {
		
		alert("�������޲���Ϊ�գ�");
		return false;
	}
	
	var tInsuPeriod = fm2.InsuPeriod.value;
	if (isEmpty(fm2.InsuPeriod) || !isInteger(tInsuPeriod) || Number(tInsuPeriod)<0) {
		
		alert("�������޲���Ϊ�գ���ӦΪ����0��������");
		return false;
	}
	
	if (isEmpty(fm2.ValDateType)) {
		
		alert("��Լ��Ч�����Ͳ���Ϊ�գ�");
		return false;
	}
	
	var tValDateType = fm2.ValDateType.value;
	if (tValDateType=="1") {
		
		if (isEmpty(fm2.AppointValDate)) {
			
			alert("��Ч���ڲ���Ϊ�գ�");
			return false;
		}

	} else {
		fm2.AppointValDate.value = "";
	}
	
	if (isEmpty(fm2.PayIntv)) {
		
		alert("�ɷѷ�ʽ����Ϊ�գ�");
		return false;
	}
	
	if (isEmpty(fm2.Coinsurance)) {
		
		alert("�Ƿ񹲱�����Ϊ�գ�");
		return false;
	}
	
	//У�鱣�ϲ�λ��ֱ�׼
	if (!checkElements()) {
		return false;
	}
	
	//У���н���Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql6");
	tSQLInfo.addSubPara(fm2.SaleChannel.value);

	var tAgencyFlag = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tAgencyFlag[0][0]=="1") {//ѡ������Ҫ���н����������,�����д���
		
	} else {//û�еĻ����д���,����н�¼����
		initAgencyListGrid();
	}
	
	//У���Ƿ����������׼�ͻ�
	var tRelaCustomerFlag = fm2.RelaCustomerFlag.checked;
	if (tRelaCustomerFlag==true) {
		
		RelaCustListGrid.delBlankLine();
		if (RelaCustListGrid.mulLineCount==0) {
			alert("ѡ���˹�������׼�ͻ�,����¼������׼�ͻ���Ϣ��");
			return false;
		} else {
			//У�����׼�ͻ��Ƿ���ѯ����Ҫ׼�ͻ���ͬ
			for (var i=0; i<RelaCustListGrid.mulLineCount; i++) {
			
				if (RelaCustListGrid.getRowColData(i, 1)==fm2.PreCustomerNo.value) {
					alert("����׼�ͻ�������ѯ����Ҫ׼�ͻ��ظ���");
					return false;
				}
			}
			//У�����������׼�ͻ��Ƿ��ظ�
			var vRow = RelaCustListGrid.mulLineCount;
			for (var i = 0; i < vRow; i++) {
				var mCustNo = RelaCustListGrid.getRowColData(i, 1);
				for (var j = 0; j < i; j++) {
					var nCustNo = RelaCustListGrid.getRowColData(j, 1);
					if (mCustNo == nCustNo) {
						alert("��������׼�ͻ��ظ������޸ģ�");
						return false;
					}
				}
			}
		}
	} else {
		
		initRelaCustListGrid();//���³�ʼ��
	}
	
	return true;
}

/** ѯ��--��һ��--����*/

/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=='queryexp') {//��Ʒ����
		
		if (Field.value=='00') {//��ͨ����
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
	
	if (cCodeType=='valdatetype') {//��Լ��Ч������
		
		if (Field.value=='1') {//Լ������

			document.getElementById("tdValDate1").style.display = '';			
			document.getElementById("tdValDate2").style.display = '';
			document.getElementById("tdValDate3").style.display = 'none';
			document.getElementById("tdValDate4").style.display = 'none';
		} else {//���˴���
			
			document.getElementById("tdValDate1").style.display = 'none';
			document.getElementById("tdValDate2").style.display = 'none';
			document.getElementById("tdValDate3").style.display = '';
			document.getElementById("tdValDate4").style.display = '';
		}
		
		return;
	}
	
	if (cCodeType=='salechannel') {//��������
		
		if (Field.value!=null || Field.value=='') {//��Ϊ��ʱ,��ѯ�������Ƿ����н�
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_quot.LSQuotSql");
			tSQLInfo.setSqlId("LSQuotSql6");
			tSQLInfo.addSubPara(Field.value);
	
			var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (tArr==null) {
			
			} else {
				
				if (tArr[0]!='0') {//Ϊ1,��ʾ�����н����
					
					document.getElementById("divAgencyInfo").style.display = '';
					return;
				}
			}
		}

		document.getElementById("divAgencyInfo").style.display = 'none';
		initAgencyListGrid();
		
		return;
	}
	
	if (cCodeType=='precustomerno'&& mPreCustomerFlag) {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql41");
		tSQLInfo.addSubPara("");
		tSQLInfo.addSubPara(fm2.PreCustomerNo.value);
		tSQLInfo.addSubPara(tOperator);
		
		var tArrCust = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArrCust == null) {
			alert("��ѯ�ͻ���Ϣʧ�ܣ�");
			return false;
		} else {
			fm2.IDType.value = tArrCust[0][2];
			fm2.IDTypeName.value = tArrCust[0][3];
			fm2.IDNo.value = tArrCust[0][4];
			fm2.GrpNature.value = tArrCust[0][5];
			fm2.GrpNatureName.value = tArrCust[0][6];
			fm2.BusiCategory.value = tArrCust[0][7];
			fm2.BusiCategoryName.value = tArrCust[0][8];
			fm2.Address.value = tArrCust[0][9];
			fm2.PrePrem.value = tArrCust[0][10];
			fm2.CustomerIntro.value = tArrCust[0][11];
			fm2.SaleChannel.value = tArrCust[0][12];
			fm2.SaleChannelName.value = tArrCust[0][13];
			
			//�����н���Ϣչʾ
			showDivAgencyInfo(fm2.SaleChannel.value);
		}
		mPreCustomerFlag = false;
	}
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
	}	
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
/**
 * ��ѯ׼�ͻ���Ϣ
 */
function queryCustomer(tMark) {
	
	if ((tMark == "Page" || tMark == "Enter" )&& tQuotBatNo !=1) {
		
		alert("�ٴ�ѯ��ʱ�����޸�׼�ͻ����ƣ�");
		return false;
	}
	var tPreCustomerNo = fm2.PreCustomerNo.value; 
	var tPreCustomerName = fm2.PreCustomerName.value;
	var tSelNo = RelaCustListGrid.lastFocusRowNo;//�кŴ�0��ʼ
	var strUrl = "LSQuotQueryCustomerMain.jsp?SelNo="+tSelNo+"&Mark="+tMark +"&PreCustomerNo="+tPreCustomerNo +"&PreCustomerName="+tPreCustomerName +"&QuotNo="+tQuotNo;
	window.open(strUrl,"׼�ͻ����Ʋ�ѯ",'height=600,width=600,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}

/**
 * ѡ������׼�ͻ�ʱ��У����׼�ͻ��Ƿ�ı�
 */
function checkPerCustomerNoChange(cCustNo) {
	
	if (fm2.PreCustomerNo.value != cCustNo) {
		alert("��׼�ͻ��Ѹı䣬������ѡ�����׼�ͻ�!");
		initRelaCustListGrid();
		return false;
	}
}

/**
 * ������������ֵ��չʾ�н���Ϣ
 */
function showDivAgencyInfo(cField) {
	
	if (cField ==null || cField=='') {
		
		document.getElementById("divAgencyInfo").style.display = 'none';
	} else {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotSql");
		tSQLInfo.setSqlId("LSQuotSql6");
		tSQLInfo.addSubPara(cField);

		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr!=null) {
			if (tArr[0]!='0') {//Ϊ1,��ʾ�����н����
				
				document.getElementById("divAgencyInfo").style.display = '';
			} else {
				document.getElementById("divAgencyInfo").style.display = 'none';
			}
		} 
	}
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
		
		//var tSql = "prodtype|"+tQuotType;
		var tSql = "1 and codetype=#prodtype# and codeexp=#"+ tQuotType +"#";

		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
		
	} else if (value1=='precustomerno') {
		
		var tPreCustomerName = fm2.PreCustomerName.value;
		if (tPreCustomerName==null || tPreCustomerName=="") {
			return false;
		}
		var conditionPreCustomerNo = "1 and a.makeoperator = #"+tOperator+"# and a.precustomername like #%"+tPreCustomerName+"%#";
		if (returnType=='0') {
			return showCodeList('precustomerno',value2,value3,null,conditionPreCustomerNo,'1','1',null);
		} else {
			return showCodeListKey('precustomerno',value2,value3,null,conditionPreCustomerNo,'1','1',null);
		}
		
	}
}

/**
 *  ��ѯ׼�ͻ�
 */
function selectPreCustomer(Filed,FildName) {
	
	if (tQuotBatNo !=1) {
		
		alert("�ٴ�ѯ��ʱ�����޸�׼�ͻ����ƣ�");
		return false;
	}
	var objCodeName = FildName.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql41");
	tSQLInfo.addSubPara(objCodeName);
	tSQLInfo.addSubPara("");
	tSQLInfo.addSubPara(tOperator);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr == null) {
		alert("�����ڸ�׼�ͻ���");
		return false;
	} else {
		if (tArr.length == 1) {
			Filed.value = tArr[0][0];
			FildName.value = tArr[0][1];
			fm2.IDType.value = tArr[0][2];
			fm2.IDTypeName.value = tArr[0][3];
			fm2.IDNo.value = tArr[0][4];
			fm2.GrpNature.value = tArr[0][5];
			fm2.GrpNatureName.value = tArr[0][6];
			fm2.BusiCategory.value = tArr[0][7];
			fm2.BusiCategoryName.value = tArr[0][8];
			fm2.Address.value = tArr[0][9];
			fm2.PrePrem.value = tArr[0][10];
			fm2.CustomerIntro.value = tArr[0][11];
			fm2.SaleChannel.value = tArr[0][12];
			fm2.SaleChannelName.value = tArr[0][13];
			
			showDivAgencyInfo(fm2.SaleChannel.value);
			
		} else if (tArr.length >1 && tArr.length <= 10) {
			
			var conditionPreCustomerNo = "1 and a.makeoperator = #"+tOperator+"# and a.precustomername like #%"+objCodeName+"%#";
			showCodeList('precustomerno', [Filed, FildName], [0,1], null,conditionPreCustomerNo, '1', '1',null);
			
			mPreCustomerFlag = true;
		} else if (tArr.length > 10){
			queryCustomer('Enter');
			showDivAgencyInfo(fm2.SaleChannel.value);
		}
	}
}


/**
 * ׼�ͻ�����ģ����ѯ
 */
function fuzzyPreCustomerName(Filed,FildName) {
	
	if (window.event.keyCode == "13") {
		
		window.event.keyCode = 0;
		
		var objCodeName = FildName.value;
		if (objCodeName=="") {
			return false;
		}
		selectPreCustomer(Filed,FildName);
	}	
}

