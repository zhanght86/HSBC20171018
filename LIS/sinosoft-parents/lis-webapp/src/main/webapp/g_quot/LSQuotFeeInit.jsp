<%
/***************************************************************
 * <p>ProName��LSQuotFeeInit.jsp</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<script language="JavaScript">
var tAgencyFlag = 0;//�Ƿ��н������ǣ�0-��1-��
var tAgencyInputFlag = 1;
/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_quot.LSQuotFeeSql");
		tSQLInfo.setSqlId("LSQuotFeeSql1");
		tSQLInfo.addSubPara(tQuotNo);
		tSQLInfo.addSubPara(tQuotBatNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			tAgencyFlag = 1;
		}
		
		initRiskFeeGrid();
		//initChargeFeeGrid();
		initOtherFeeGrid();
		
		if (tActivityID == "0800100001") {
			
			if (tAgencyFlag==0) {
				divFee.style.display = "none";
			} else {
				divFee.style.display = "";
			}
			divWeightFee.style.display = "none";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
		} else if (tActivityID == "0800100002") {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "none";
		} else if (tActivityID == "0800100003") {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "none";
		} else {
			
			divFee.style.display = "";
			divWeightFee.style.display = "";
			
			fm.SaveButton.style.display = "none";
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
		}
		
		//չʾ��������Ϣʱ�Ŵ�����ѯ
		if (divFee.style.display=="") {
			queryRiskFee();
		}
		//չʾ��Ȩ��������Ϣʱ�Ŵ�����ѯ
		if (divWeightFee.style.display=="") {
			queryWeightFee();
		}
		queryOtherFee();
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
function initRiskFeeGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ο�Ӷ�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����Ӷ�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		//���н����ʱչʾ�����ѣ�����չʾ
		if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			if (tAgencyFlag==1) {
				
				iArray[i] = new Array();
				iArray[i][0] = "�ο������ѱ���";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "���������ѱ���";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
			} else {
			
				iArray[i] = new Array();
				iArray[i][0] = "�ο������ѱ���";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
				
				iArray[i] = new Array();
				iArray[i][0] = "���������ѱ���";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
		} else {
		
			if (tAgencyFlag==1) {
				
				iArray[i] = new Array();
				iArray[i][0] = "�ο������ѱ���";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "���������ѱ���";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			} else {
			
				iArray[i] = new Array();
				iArray[i][0] = "�ο������ѱ���";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
				
				iArray[i] = new Array();
				iArray[i][0] = "���������ѱ���";
				iArray[i][1] = "0px";
				iArray[i][2] = 300;
				iArray[i++][3] = 3;
			}
		}
		
		//¼�뻷�ڲ�չʾ���������ʡ�������̯�ȱ�������������չʾ
		if (tActivityID=="0800100001") {
			
			iArray[i] = new Array();
			iArray[i][0] = "�ο�ҵ�������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "����ҵ�������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		
			iArray[i] = new Array();
			iArray[i][0] = "Ԥ��������";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "������̯";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "˰��";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "ֱ�ӹ����";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ο������ʺϼ�";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "���������ʺϼ�";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
			
			iArray[i] = new Array();
			iArray[i][0] = "���ֱ��Ѻϼ�";
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else {
			
			iArray[i] = new Array();
			iArray[i][0] = "�ο�ҵ�������";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
				iArray[i] = new Array();
				iArray[i][0] = "����ҵ�������";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
				
				iArray[i] = new Array();
				iArray[i][0] = "Ԥ��������";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 1;
			} else {
				
				iArray[i] = new Array();
				iArray[i][0] = "����ҵ�������";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
				
				iArray[i] = new Array();
				iArray[i][0] = "Ԥ��������";
				iArray[i][1] = "30px";
				iArray[i][2] = 300;
				iArray[i++][3] = 0;
			}
			
			iArray[i] = new Array();
			iArray[i][0] = "������̯";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "˰��";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "ֱ�ӹ����";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "�ο������ʺϼ�";
			iArray[i][1] = "30px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "���������ʺϼ�";
			iArray[i][1] = "45px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
			
			iArray[i] = new Array();
			iArray[i][0] = "���ֱ��Ѻϼ�";
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		RiskFeeGrid = new MulLineEnter("fm", "RiskFeeGrid");
		RiskFeeGrid.mulLineCount = 0;
		RiskFeeGrid.displayTitle = 1;
		RiskFeeGrid.locked = 0;
		RiskFeeGrid.canSel = 1;
		RiskFeeGrid.canChk = 0;
		RiskFeeGrid.hiddenPlus = 1;
		RiskFeeGrid.hiddenSubtraction = 1;
		//RiskFeeGrid.selBoxEventFuncName = "showRiskFeeDetail";
		RiskFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��RiskFeeGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initChargeFeeGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н����������ѱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		ChargeFeeGrid = new MulLineEnter("fm", "ChargeFeeGrid");
		ChargeFeeGrid.mulLineCount = 0;
		ChargeFeeGrid.displayTitle = 1;
		ChargeFeeGrid.locked = 0;
		ChargeFeeGrid.canSel = 0;
		ChargeFeeGrid.canChk = 0;
		ChargeFeeGrid.hiddenPlus = 1;
		ChargeFeeGrid.hiddenSubtraction = 1;
		ChargeFeeGrid.selBoxEventFuncName = "";
		ChargeFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ChargeFeeGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initOtherFeeGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ñ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ֵ";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ע";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OtherFeeGrid = new MulLineEnter("fm", "OtherFeeGrid");
		OtherFeeGrid.mulLineCount = 0;
		OtherFeeGrid.displayTitle = 1;
		OtherFeeGrid.locked = 0;
		OtherFeeGrid.canSel = 1;
		OtherFeeGrid.canChk = 0;
		OtherFeeGrid.hiddenPlus = 1;
		OtherFeeGrid.hiddenSubtraction = 1;
		OtherFeeGrid.selBoxEventFuncName = "showOtherFeeDetail";
		OtherFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��OtherFeeGridʱ����"+ ex);
	}
}
</script>
