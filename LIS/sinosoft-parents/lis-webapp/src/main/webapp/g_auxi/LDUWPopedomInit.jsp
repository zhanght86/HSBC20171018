<%
/***************************************************************
 * <p>ProName��LDUWPopedomInit.jsp</p>
 * <p>Title���˱�Ȩ�޹���</p>
 * <p>Description���˱�Ȩ�޹���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-06-26
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
		initUWPopedomGrid();
		
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
		fm.PopedomLevel1.value = "";
		fm.PopedomName1.value = "";
		fm.PopedomLevel.value = "";
		fm.PopedomName.value = "";
		fm.PerLifeAmnt.value = "";
		fm.PerAcciAmnt.value = "";
		fm.PerIllAmnt.value = "";
		fm.PerMedAmnt.value = "";
		fm.PremScale.value = "";
		fm.MainPremRateFloat.value = "";
		fm.MedPremRateFloat.value = "";
		fm.ValDate.value = "";
		fm.EndDate.value = "";
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
 * �˷���Ϣ�б�
 */
function initUWPopedomGrid() {
	
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
		iArray[i][0] = "Ȩ�޼���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ȩ������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ձ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���������ձ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ؼ�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 30;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ҽ���ձ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѹ�ģ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���շ��ʸ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҽ���շ��ʸ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ч����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ֹ����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		UWPopedomGrid = new MulLineEnter("fm", "UWPopedomGrid");
		UWPopedomGrid.mulLineCount = 0;
		UWPopedomGrid.displayTitle = 1;
		UWPopedomGrid.locked = 0;
		UWPopedomGrid.canSel = 1;
		UWPopedomGrid.canChk = 0;
		UWPopedomGrid.hiddenSubtraction = 1;
		UWPopedomGrid.hiddenPlus = 1;
		UWPopedomGrid.selBoxEventFuncName = "showUWPopedomInfo";
		UWPopedomGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
