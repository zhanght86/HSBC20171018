<%
/***************************************************************
 * <p>ProName��RenewalBackInit.jsp</p>
 * <p>Title���鵵����</p>
 * <p>Description���鵵����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
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
		initContInfoGrid();
		initRiskInfoGrid();
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
 * ��ʼ���ڽɱ����б�
 */
function initContInfoGrid() {
	
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
		iArray[i][0] = "�ɷѺ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�б�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "130px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "150px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�Ƶ��";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ǰ����";
		iArray[i][1] = "60px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������Ч����";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ֹ����";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ���ֹ����";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�߽�����";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ӧ������";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ӧ�����(Ԫ)";
		iArray[i][1] = "70px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		ContInfoGrid = new MulLineEnter("fm", "ContInfoGrid");
		ContInfoGrid.mulLineCount = 0;
		ContInfoGrid.displayTitle = 1;
		ContInfoGrid.locked = 1;
		ContInfoGrid.canSel = 1;
		ContInfoGrid.canChk = 0;
		ContInfoGrid.hiddenPlus = 1;
		ContInfoGrid.hiddenSubtraction = 1;
		ContInfoGrid.selBoxEventFuncName = "showRiskInfo";
		ContInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ContInfoGridʱ����"+ ex);
	}
}

/**
 * ����Ӧ��������Ϣ
 */
function initRiskInfoGrid() {
	
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
		iArray[i][0] = "������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ӧ�����(Ԫ)";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RiskInfoGrid = new MulLineEnter("fm", "RiskInfoGrid");
		RiskInfoGrid.mulLineCount = 0;
		RiskInfoGrid.displayTitle = 1;
		RiskInfoGrid.locked = 1;
		RiskInfoGrid.canSel = 0;
		RiskInfoGrid.canChk = 0;
		RiskInfoGrid.hiddenPlus = 1;
		RiskInfoGrid.hiddenSubtraction = 1;
		RiskInfoGrid.selBoxEventFuncName = "";
		RiskInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��RiskInfoGridʱ����"+ ex);
	}
}
</script>
