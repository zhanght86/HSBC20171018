<%
/***************************************************************
 * <p>ProName��LSEdorRefundCalInit.jsp</p>
 * <p>Title����ȫ�˷��㷨ά��</p>
 * <p>Description����ȫ�˷��㷨ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-08
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
		fm2.RiskCode1.value = "";
		fm2.RiskName1.value = "";
		fm2.GetType1.value = "";
		fm2.GetTypeName1.value = "";
		fm2.RiskCode2.value = "";
		fm2.RiskName2.value = "";
		fm2.GetType2.value = "";
		fm2.GetTypeName2.value = "";
		fm2.ValPeriod.value = "";
		fm2.ValPeriodName.value = "";
		fm2.ValStartDate.value = "";
		fm2.ValEndDate.value = "";
		fm2.FeeValue.value = "";
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if (tActivityID=="0800100001" || tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm2.AddButton.style.display = "";
			fm2.ModButton.style.display = "";
			fm2.DelButton.style.display = "";
		} else {
			
			fm2.AddButton.style.display = "none";
			fm2.ModButton.style.display = "none";
			fm2.DelButton.style.display = "none";
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
 * �˷���Ϣ�б�
 */
function initRefundListGrid() {
	
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

		RefundListGrid = new MulLineEnter("fm2", "RefundListGrid");
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

		SysRefundInfoGrid = new MulLineEnter("fm2", "SysRefundInfoGrid");
		SysRefundInfoGrid.mulLineCount = 0;
		SysRefundInfoGrid.displayTitle = 1;
		SysRefundInfoGrid.locked = 0;
		SysRefundInfoGrid.canSel = 0;
		SysRefundInfoGrid.canChk = 0;
		SysRefundInfoGrid.hiddenSubtraction = 1;
		SysRefundInfoGrid.hiddenPlus = 1;
		//SysRefundInfoGrid.selBoxEventFuncName = "";
		SysRefundInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
