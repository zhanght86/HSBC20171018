<%
/***************************************************************
 * <p>ProName��LCContPlanListSave.jsp</p>
 * <p>Title��Ͷ������¼�������</p>
 * <p>Description��Ͷ������¼�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �龰
 * @version  : 8.0
 * @date     : 2014-04-14
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
		
		initContPlanListGrid();
		initSelfContPlanListGrid();
		
		querySelfPlanClick();
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
 * ��¼��ѯ�۲�ѯ
 */
function initContPlanListGrid() {
	
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
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ActivityID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ������";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ��ʱ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʱЧ����(������)";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		 
		ContPlanListGrid = new MulLineEnter("fm", "ContPlanListGrid");
		ContPlanListGrid.mulLineCount = 0;
		ContPlanListGrid.displayTitle = 1;
		ContPlanListGrid.locked = 1;
		ContPlanListGrid.canSel = 1;
		ContPlanListGrid.canChk = 0;
		ContPlanListGrid.hiddenSubtraction = 1;
		ContPlanListGrid.hiddenPlus = 1;
		ContPlanListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ���˳�
 */
function initSelfContPlanListGrid() {
	
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
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ActivityID";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ContPlanType";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ������";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ��ʱ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʱЧ����(������)";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���⴦��״̬";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		SelfContPlanListGrid = new MulLineEnter("fm", "SelfContPlanListGrid");
		SelfContPlanListGrid.mulLineCount = 0;
		SelfContPlanListGrid.displayTitle = 1;
		SelfContPlanListGrid.locked = 1;
		SelfContPlanListGrid.canSel = 1;
		SelfContPlanListGrid.canChk = 0;
		SelfContPlanListGrid.hiddenSubtraction = 1;
		SelfContPlanListGrid.hiddenPlus = 1;
		SelfContPlanListGrid.selBoxEventFuncName = "";
		SelfContPlanListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
