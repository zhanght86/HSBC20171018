<%
/***************************************************************
 * <p>ProName��LCInsuredCollectInit</p>
 * <p>Title��������Ϣ���ܲ�ѯ</p>
 * <p>Description��������Ϣ���ܲ�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-04
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initQueryScanGrid();
		showList();
	
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ���������
 */
function initOtherParam() {

	try {
	} catch (ex) {
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

function initQueryScanGrid() {
	
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
		iArray[i][0] = "���շ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����(Ԫ)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;

		iArray[i] = new Array();
		iArray[i][0] = "�ܱ���(Ԫ)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ܱ���(Ԫ)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		QueryScanGrid = new MulLineEnter("fm", "QueryScanGrid");
		QueryScanGrid.mulLineCount = 0;
		QueryScanGrid.displayTitle = 1;
		QueryScanGrid.locked = 1;
		QueryScanGrid.canSel = 0;
		QueryScanGrid.canChk = 0;
		QueryScanGrid.hiddenSubtraction = 1;
		QueryScanGrid.hiddenPlus = 1;
		QueryScanGrid.selBoxEventFuncName = ""; 
		QueryScanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
