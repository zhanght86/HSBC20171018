<%
/***************************************************************
 * <p>ProName��EdorCancelInit.jsp</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-07-14
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
		initEdorAppGrid();
		initEdorGrid();
		initEdorItemGrid();
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
function initEdorAppGrid() {
	
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
		iArray[i][0] = "�������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ�����";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ������";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ɨ����Ա";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������Ա";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ܱ������(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ԭ��";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorAppGrid = new MulLineEnter("fm", "EdorAppGrid");
		EdorAppGrid.mulLineCount = 0;
		EdorAppGrid.displayTitle = 1;
		EdorAppGrid.locked = 1;
		EdorAppGrid.canSel = 1;
		EdorAppGrid.canChk = 0;
		EdorAppGrid.hiddenPlus = 1;
		EdorAppGrid.hiddenSubtraction = 1;
		EdorAppGrid.selBoxEventFuncName = "showEdorInfo";
		EdorAppGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��EdorAppGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initEdorGrid() {
	
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
		iArray[i][0] = "��ȫ������";
		iArray[i][1] = "75px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������Ա";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����״̬";
		iArray[i][1] = "45px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ԭ��";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorGrid = new MulLineEnter("fm", "EdorGrid");
		EdorGrid.mulLineCount = 0;
		EdorGrid.displayTitle = 1;
		EdorGrid.locked = 1;
		EdorGrid.canSel = 1;
		EdorGrid.canChk = 0;
		EdorGrid.hiddenPlus = 1;
		EdorGrid.hiddenSubtraction = 1;
		EdorGrid.selBoxEventFuncName = "showEdorItemInfo";
		EdorGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��EdorGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initEdorItemGrid() {
	
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
		iArray[i][0] = "��ȫ��Ŀ����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ԭ��";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
		EdorItemGrid.mulLineCount = 0;
		EdorItemGrid.displayTitle = 1;
		EdorItemGrid.locked = 1;
		EdorItemGrid.canSel = 1;
		EdorItemGrid.canChk = 0;
		EdorItemGrid.hiddenPlus = 1;
		EdorItemGrid.hiddenSubtraction = 1;
		EdorItemGrid.selBoxEventFuncName = "";
		EdorItemGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��EdorItemGridʱ����"+ ex);
	}
}
</script>
