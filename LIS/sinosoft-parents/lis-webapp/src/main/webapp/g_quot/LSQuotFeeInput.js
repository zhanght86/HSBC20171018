/***************************************************************
 * <p>ProName��LSQuotFeeInput.js</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��������Ϣ��ѯ
 */
function queryRiskFee() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql3");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), RiskFeeGrid, 0, 1);
}

/**
 * ��Ȩ��������Ϣ��ѯ
 */
function queryWeightFee() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql8");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		var tSumPrem = strQueryResult[0][0];//���Ѻϼ�
		var tSum1 = strQueryResult[0][1];//������
		var tSum2 = strQueryResult[0][2];//ҵ�������
		var tSum3 = strQueryResult[0][3];//������
		var tSum4 = strQueryResult[0][4];//������̯
		var tSum5 = strQueryResult[0][5];//˰��
		var tSum6 = strQueryResult[0][6];//������
		var tSumNonMedPrem = strQueryResult[0][7];//�����ձ���
		
		if (tSumPrem==0) {
			
			fm.WeightChargeRate.value = "";
			fm.WeightBusiFeeRate.value = "";
			fm.WeightLossRatio.value = "";
			fm.WeightPoolRate.value = "";
			fm.WeightTaxFeeRate.value = "";
			fm.WeightSumRate.value = "";
			fm.NonMedPremRate.value = "";
		} else {
			
			fm.WeightChargeRate.value = mathRound(tSum1/tSumPrem);
			fm.WeightBusiFeeRate.value = mathRound(tSum2/tSumPrem);
			fm.WeightLossRatio.value = mathRound(tSum3/tSumPrem);
			fm.WeightPoolRate.value = mathRound(tSum4/tSumPrem);
			fm.WeightTaxFeeRate.value = mathRound(tSum5/tSumPrem);
			fm.WeightSumRate.value = mathRound(tSum6/tSumPrem);
			fm.NonMedPremRate.value = mathRound(tSumNonMedPrem/tSumPrem);
		}
	}
}

/**
 * ѯ���н���������Ѳ�ѯ
 */
//function showRiskFeeDetail() {
//	
//	var tSelNo = RiskFeeGrid.getSelNo();
//	var tRiskCode = RiskFeeGrid.getRowColData(tSelNo-1, 1);
//	
//	tSQLInfo = new SqlClass();
//	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
//	tSQLInfo.setSqlId("LSQuotFeeSql5");
//	tSQLInfo.addSubPara(tQuotNo);
//	tSQLInfo.addSubPara(tQuotBatNo);
//	tSQLInfo.addSubPara(tRiskCode);
//	
//	initChargeFeeGrid();
//	noDiv(turnPage2, ChargeFeeGrid, tSQLInfo.getString());
//	if (ChargeFeeGrid.mulLineCount==0) {
//		divChargeFee.style.display = "none";
//	} else {
//		divChargeFee.style.display = "";
//	}
//}

/**
 * ����������Ϣ��ѯ
 */
function queryOtherFee() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
	tSQLInfo.setSqlId("LSQuotFeeSql7");
	tSQLInfo.addSubPara(tQuotNo);
	tSQLInfo.addSubPara(tQuotBatNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), OtherFeeGrid, 0, 1);
}

/**
 * ����
 */
function saveClick() {
	
	var tSelNo = RiskFeeGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
		
	var tExpectChargeRate = RiskFeeGrid.getRowColData(tSelNo,6);
	var tExpectBusiFeeRate = RiskFeeGrid.getRowColData(tSelNo,8);
	var tPreLossRatio = RiskFeeGrid.getRowColData(tSelNo,9);
	var tPoolRate = RiskFeeGrid.getRowColData(tSelNo,10);
	var tTaxFeeRate = RiskFeeGrid.getRowColData(tSelNo,11);
	var tManageFeeRate = RiskFeeGrid.getRowColData(tSelNo,12);
	
	if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {//¼�뻷��У��������
		
		if (tAgencyFlag==1) {//����У��,����0-1֮��
			
			if (tExpectChargeRate==null || tExpectChargeRate=="") {
				alert("��"+(tSelNo+1)+"�����������ѱ�������Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(tExpectChargeRate)) {
				alert("��"+(tSelNo+1)+"�����������ѱ���������Ч�����֣�");
				return false;
			}
			
			if (Number(tExpectChargeRate)<0 || Number(tExpectChargeRate)>1) {
				alert("��"+(tSelNo+1)+"�����������ѱ���ӦΪ��С��0�Ҳ�����1����Ч���֣�");
				return false;
			}

			if (!checkDecimalFormat(tExpectChargeRate, 1, 6)) {
				alert("��"+(tSelNo+1)+"���������ѱ�������λ��Ӧ����1λ,С��λ��Ӧ����6λ��");
				return false;
			}
			
//			var tSumAgencyRate = 0;
//			if (ChargeFeeGrid.mulLineCount==0) {
//				
//			} else {
//				
//				for(var i=0;i<ChargeFeeGrid.mulLineCount;i++){
//					
//					var tAgencyRate = ChargeFeeGrid.getRowColData(i,2);
//					if (tAgencyRate==null || tAgencyRate=="") {
//						alert("��"+(i+1)+"���н����������ѱ�������Ϊ�գ�");
//						return false;
//					}
//					
//					if (!isNumeric(tAgencyRate)) {
//						alert("��"+(i+1)+"���н����������ѱ���������Ч�����֣�");
//						return false;
//					}
//					
//					if (Number(tAgencyRate)<0 || Number(tAgencyRate)>1) {
//						alert("��"+(i+1)+"���н����������ѱ���ӦΪ��С��0�Ҳ�����1����Ч���֣�");
//						return false;
//					}
//					
//					if (!checkDecimalFormat(tAgencyRate, 1, 2)) {
//						alert("��"+(i+1)+"�н����������ѱ�������λ��Ӧ����1λ,С��λ��Ӧ����2λ��");
//						return false;
//					}
//					
//					tSumAgencyRate += tAgencyRate*1000000;
//				}
//			}
//			
//			if (ChargeFeeGrid.mulLineCount==0) {
//			} else {
//				
//				if (tExpectChargeRate*1000000!=tSumAgencyRate) {
//					alert("��ѡ���������������ʱ�����ڸ��н����������ѱ���֮�ͣ�");
//					return false;
//				}
//			}
		}
	}

	if (tActivityID=="0800100002" || tActivityID=="0800100003") {
		
		if (tExpectBusiFeeRate==null || tExpectBusiFeeRate=="") {
			alert("��"+(tSelNo+1)+"������ҵ������ʲ���Ϊ�գ�");
			return false;
		}
		
		if (!isNumeric(tExpectBusiFeeRate)) {
			alert("��"+(tSelNo+1)+"������ҵ������ʲ�����Ч�����֣�");
			return false;
		}

		if (tPreLossRatio==null || tPreLossRatio=="") {
			alert("��"+(tSelNo+1)+"��Ԥ�������ʲ���Ϊ�գ�");
			return false;
		}
		
		if (!isNumeric(tPreLossRatio)) {
			alert("��"+(tSelNo+1)+"��Ԥ�������ʲ�����Ч�����֣�");
			return false;
		}
	}

	mOperate = "SAVE";
	fm.action = "./LSQuotFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * ����
 */
function addClick() {
	
	mOperate = "INSERT";
	if (!beforeSubmit()) {
		return false;
	}
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * �޸�
 */
function modifyClick() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.SerialNo.value = OtherFeeGrid.getRowColData(tSelNo-1, 1);
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * ɾ��
 */
function deleteClick() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	if(!confirm('ȷ��Ҫɾ��ѡ����Ϣ��?')){
		return false;
	}
	fm.SerialNo.value = OtherFeeGrid.getRowColData(tSelNo-1, 1);
	mOperate = "DELETE";
	fm.action = "./LSQuotOtherFeeSave.jsp?QuotType="+tQuotType+"&QuotNo="+tQuotNo+"&QuotBatNo="+tQuotBatNo+"&ActivityID="+tActivityID;
	submitForm();
}

/**
 * չʾ����������ϸ
 */
function showOtherFeeDetail() {
	
	var tSelNo = OtherFeeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫ��������Ϣ��");
		return false;
	}
	
	fm.FeeType.value = OtherFeeGrid.getRowColData(tSelNo-1, 2);
	fm.FeeTypeName.value = OtherFeeGrid.getRowColData(tSelNo-1, 3);
	fm.OtherFeeDesc.value = OtherFeeGrid.getRowColData(tSelNo-1, 4);
	fm.FeeValue.value = OtherFeeGrid.getRowColData(tSelNo-1, 5);
	fm.Remark.value = OtherFeeGrid.getRowColData(tSelNo-1, 6);
	
	if (fm.FeeType.value == "09") {
		
		divOtherFeeDescTitle.style.display = "";
		divOtherFeeDescInput.style.display = "";
		divTD1.style.display = "none";
		divTD2.style.display = "none";
	} else {
		
		divOtherFeeDescTitle.style.display = "none";
		divOtherFeeDescInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
	}
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
	fm.Operate.value = mOperate;
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
		
		initForm();
		if (mOperate == "INSERT" || mOperate == "UPDATE" || mOperate == "DELETE") {
			fm.FeeType.value = "";
			fm.FeeTypeName.value = "";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			fm.Remark.value = "";
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
	
	if (mOperate=="INSERT" || mOperate=="UPDATE") {
		
		if (fm.FeeType.value=="09") {
			
			if (fm.OtherFeeDesc.value=="") {
				
				alert("�����������Ʋ���Ϊ�գ�");
				return false;
			}
		}
		if (fm.FeeValue.value=="") {
			
			alert("����ֵ����Ϊ�գ�");
			return false;
		}
		if (fm.Remark.value.length>500) {
			
			alert("ҵ���������ݹ�����");
			return false;
		}
	}
	
	return true;
}

/**
 * ������ѡ�����
 */
function afterCodeSelect(tSelectValue, tObj) {

	if (tSelectValue=='otherfeetype') {
		
		if (tObj.value == "09") {
			
			divOtherFeeDescTitle.style.display = "";
			divOtherFeeDescInput.style.display = "";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		} else {
			
			divOtherFeeDescTitle.style.display = "none";
			divOtherFeeDescInput.style.display = "none";
			fm.OtherFeeDesc.value = "";
			fm.FeeValue.value = "";
			divTD1.style.display = "";
			divTD2.style.display = "";
		}
	}
}
