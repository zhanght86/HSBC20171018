<%
/***************************************************************
 * <p>ProName��BalanceQueryInit.jsp</p>
 * <p>Title�����㵥��ѯ</p>
 * <p>Description�����㵥��ѯ</p>
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
		initPosInfoGrid();
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
 * ��ʼ����������б�
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
		iArray[i][0] = "�б�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "80px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���㵥����";
		iArray[i][1] = "80px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ӧ��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʵ�ʽ�������";
		iArray[i][1] = "50px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������(Ԫ)";
		iArray[i][1] = "40px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ȷ������";
		iArray[i][1] = "50px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���㵥״̬";
		iArray[i][1] = "40px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ӡ״̬";
		iArray[i][1] = "40px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ӡ����";
		iArray[i][1] = "40px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "70px";
		iArray[i][2] = 150;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���㱸ע";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ContInfoGrid = new MulLineEnter("fm", "ContInfoGrid");
		ContInfoGrid.mulLineCount = 0;
		ContInfoGrid.displayTitle = 1;
		ContInfoGrid.locked = 1;
		ContInfoGrid.canSel = 1;
		ContInfoGrid.canChk = 0;
		ContInfoGrid.hiddenPlus = 1;
		ContInfoGrid.hiddenSubtraction = 1;
		ContInfoGrid.selBoxEventFuncName = "showPosInfo";
		ContInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PublicGridʱ����"+ ex);
	}
}

/**
 * ��ȫ��Ϣ�б�
 */
function initPosInfoGrid() {
	
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
		iArray[i][0] = "�б�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���㵥����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫǩ������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ܱ�����(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ӧ��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PosInfoGrid = new MulLineEnter("fm", "PosInfoGrid");
		PosInfoGrid.mulLineCount = 0;
		PosInfoGrid.displayTitle = 1;
		PosInfoGrid.locked = 1;
		PosInfoGrid.canSel = 0;
		PosInfoGrid.canChk = 0;
		PosInfoGrid.hiddenPlus = 1;
		PosInfoGrid.hiddenSubtraction = 1;
		PosInfoGrid.selBoxEventFuncName = "";
		PosInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PersonalGridʱ����"+ ex);
	}
}
</script>
