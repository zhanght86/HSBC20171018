<%
/***************************************************************
 * <p>ProName��LCGrpApproveListSave.jsp</p>
 * <p>Title�����������</p>
 * <p>Description�����������</p>
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
		initGrpApproveListGrid();
		initSelfGrpApproveListGrid();
		
		querySelfClick();
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
 * ��¼�븴�˲�ѯ
 */
function initGrpApproveListGrid() {
	
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
		iArray[i][0] = "�б�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "60px";
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
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		 
		GrpApproveListGrid = new MulLineEnter("fm", "GrpApproveListGrid");
		GrpApproveListGrid.mulLineCount = 0;
		GrpApproveListGrid.displayTitle = 1;
		GrpApproveListGrid.locked = 1;
		GrpApproveListGrid.canSel = 1;
		GrpApproveListGrid.canChk = 0;
		GrpApproveListGrid.hiddenSubtraction = 1;
		GrpApproveListGrid.hiddenPlus = 1;
		GrpApproveListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ���˳�
 */
function initSelfGrpApproveListGrid() {
	
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
		iArray[i][0] = "�б�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "60px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ʱ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʱЧ����(������)";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���⴦��״̬";
		iArray[i][1] = "50px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		SelfGrpApproveListGrid = new MulLineEnter("fm", "SelfGrpApproveListGrid");
		SelfGrpApproveListGrid.mulLineCount = 0;
		SelfGrpApproveListGrid.displayTitle = 1;
		SelfGrpApproveListGrid.locked = 1;
		SelfGrpApproveListGrid.canSel = 1;
		SelfGrpApproveListGrid.canChk = 0;
		SelfGrpApproveListGrid.hiddenSubtraction = 1;
		SelfGrpApproveListGrid.hiddenPlus = 1;
		SelfGrpApproveListGrid.selBoxEventFuncName = "";
		SelfGrpApproveListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
