<%
/***************************************************************
 * <p>ProName��LSQuotUWRuleInit.jsp</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-04
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
		initUWRuleGrid();
		initEdorRuleGrid();
		queryUWRuleInfo();
		queryEdorRuleInfo();
		showPage('0');
		
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
		fm.RuleType.value = "";
		fm.RuleTypeName.value = "";
		fm.EdorCode.value = "";
		fm.EdorCodeName.value = "";
		fm.CalCode.value = "";
		fm.CalCodeName.value = "";
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
		if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm.SaveButton.style.display = "";
			fm.AddButton.style.display = "";
			fm.ModButton.style.display = "";
			fm.DelButton.style.display = "";
		} else {
			
			fm.SaveButton.style.display = "none";
			fm.AddButton.style.display = "none";
			fm.ModButton.style.display = "none";
			fm.DelButton.style.display = "none";
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
 * �˱�����
 */
function initUWRuleGrid() {
	
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
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ʒ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ʒ����";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ο�����ֵ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ֵ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		UWRuleGrid = new MulLineEnter("fm", "UWRuleGrid");
		UWRuleGrid.mulLineCount = 1;
		UWRuleGrid.displayTitle = 1;
		UWRuleGrid.locked = 0;
		UWRuleGrid.canSel = 1;
		UWRuleGrid.canChk = 0;
		UWRuleGrid.hiddenPlus = 1;
		UWRuleGrid.hiddenSubtraction = 1;
		//UWRuleGrid.selBoxEventFuncName = "";
		UWRuleGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}

/**
 * ��ȫ�����б�
 */
function initEdorRuleGrid() {
	
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
		iArray[i][0] = "��ȫ�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ/������������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ�㷨����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�㷨����";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		EdorRuleGrid = new MulLineEnter("fm", "EdorRuleGrid");
		EdorRuleGrid.mulLineCount = 0;
		EdorRuleGrid.displayTitle = 1;
		EdorRuleGrid.locked = 0;
		EdorRuleGrid.canSel = 1;
		EdorRuleGrid.canChk = 0;
		EdorRuleGrid.hiddenPlus = 1;
		EdorRuleGrid.hiddenSubtraction = 1;
		EdorRuleGrid.selBoxEventFuncName = "showEdorRuleInfo";
		EdorRuleGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}
</script>
