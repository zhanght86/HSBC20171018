<%
/***************************************************************
 * <p>ProName��LSQuotQueryAppComInit.jsp</p>
 * <p>Title����ѯ���û���</p>
 * <p>Description����ѯ���û���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-29
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
		
		initAppComGrid();
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
 * ��ѯ���û���
 */
function initAppComGrid() {
	
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
		iArray[i][0] = "���û�������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���û�������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AppComGrid = new MulLineEnter("fm", "AppComGrid");
		AppComGrid.mulLineCount = 0;
		AppComGrid.displayTitle = 1;
		AppComGrid.locked = 1;
		AppComGrid.canSel = 1;
		AppComGrid.canChk = 0;
		AppComGrid.hiddenSubtraction = 1;
		AppComGrid.hiddenPlus = 1;
		AppComGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

</script>
