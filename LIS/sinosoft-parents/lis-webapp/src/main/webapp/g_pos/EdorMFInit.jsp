<%
/***************************************************************
 * <p>ProName��EdorMFInit.jsp</p>
 * <p>Title�����շ��ñ��</p>
 * <p>Description�����շ��ñ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		initManageFeeGrid();
		queryManageFee();
		initRefundListGrid();
		initSysRefundInfoGrid();
		querySysRefundInfo();
		
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
		fm2.RiskCode.value = "";
		fm2.RiskName.value = "";
		fm2.AccType.value = "";
		fm2.AccTypeName.value = "";
		fm2.FeeType.value = "";
		fm2.FeeTypeName.value = "";
		fm2.DeductType.value = "";
		fm2.DeductTypeName.value = "";
		fm2.FeeValue.value = "";
		fm2.MonthFeeType.value = "";
		fm2.MonthFeeTypeName.value = "";
		fm2.MonthValue.value = "";
		fm2.YearFeeType.value = "";
		fm2.YearFeeTypeName.value = "";
		fm2.YearStartValue.value = "";
		fm2.YearEndValue.value = "";
		fm2.YearValue.value = "";
		fm3.RiskCode1.value = "";
		fm3.RiskName1.value = "";
		fm3.GetType1.value = "";
		fm3.GetTypeName1.value = "";
		fm3.RiskCode2.value = "";
		fm3.RiskName2.value = "";
		fm3.GetType2.value = "";
		fm3.GetTypeName2.value = "";
		fm3.ValPeriod.value = "";
		fm3.ValPeriodName.value = "";
		fm3.ValStartDate.value = "";
		fm3.ValEndDate.value = "";
		fm3.FeeValues.value = "";
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if(tActivityID=='1800401002'){
			divInfoButton1.style.display='';
			divInfoButton2.style.display='';
		} else {
			divInfoButton1.style.display='none';
			divInfoButton2.style.display='none';
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
 * ��ά���������Ϣ�б�
 */
function initManageFeeGrid() {
	
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
		iArray[i][0] = "����";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˻�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "���������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ѿ۳���ʽ/����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ѽ��(Ԫ)/����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�����ʼֵ(��)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�����ֵֹ(<)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˻����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ѿ۳���ʽ/���ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		ManageFeeGrid = new MulLineEnter("fm2", "ManageFeeGrid");
		ManageFeeGrid.mulLineCount = 0;
		ManageFeeGrid.displayTitle = 1;
		ManageFeeGrid.locked = 0;
		ManageFeeGrid.canSel = 1;
		ManageFeeGrid.canChk = 0;
		ManageFeeGrid.hiddenSubtraction = 1;
		ManageFeeGrid.hiddenPlus = 1;
		ManageFeeGrid.selBoxEventFuncName = "showManageFee";
		ManageFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
/**
 * �˷���Ϣ�б�
 */
function initRefundListGrid() {
	
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
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "��ʼֵ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ֵֹ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ñ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		RefundListGrid = new MulLineEnter("fm3", "RefundListGrid");
		RefundListGrid.mulLineCount = 0;
		RefundListGrid.displayTitle = 1;
		RefundListGrid.locked = 0;
		RefundListGrid.canSel = 1;
		RefundListGrid.canChk = 0;
		RefundListGrid.hiddenSubtraction = 1;
		RefundListGrid.hiddenPlus = 1;
		RefundListGrid.selBoxEventFuncName = "showRefundList";
		RefundListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ϵͳĬ���˷���Ϣ�б�
 */
function initSysRefundInfoGrid() {
	
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
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "��ʼֵ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ֵֹ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ñ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		SysRefundInfoGrid = new MulLineEnter("fm3", "SysRefundInfoGrid");
		SysRefundInfoGrid.mulLineCount = 0;
		SysRefundInfoGrid.displayTitle = 1;
		SysRefundInfoGrid.locked = 0;
		SysRefundInfoGrid.canSel = 0;
		SysRefundInfoGrid.canChk = 0;
		SysRefundInfoGrid.hiddenSubtraction = 1;
		SysRefundInfoGrid.hiddenPlus = 1;
		SysRefundInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
