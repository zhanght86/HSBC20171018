<%
/***************************************************************
 * <p>ProName��LCPropPrintInit.jsp</p>
 * <p>Title��Ͷ�����ӡ</p>
 * <p>Description��Ͷ�����ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-22
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
		
		initQuotInfoGrid();
		initPropInfoGrid();
		//queryPropInfo();
		
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
 * ѯ�۲�ѯ�б�
 */
function initQuotInfoGrid() {
	
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
		iArray[i][0] = "���۵���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ѯ�ۺ�";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "ѯ�����ͱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ѯ������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		QuotInfoGrid = new MulLineEnter("fm", "QuotInfoGrid");
		QuotInfoGrid.mulLineCount = 0;
		QuotInfoGrid.displayTitle = 1;
		QuotInfoGrid.locked = 1;
		QuotInfoGrid.canSel = 1;
		QuotInfoGrid.canChk = 0;
		QuotInfoGrid.hiddenSubtraction = 1;
		QuotInfoGrid.hiddenPlus = 1;
		QuotInfoGrid.selBoxEventFuncName = "";
		QuotInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initPropInfoGrid() {
	
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
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���۵���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ����";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ����״̬����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ����״̬";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][7] = "downloadAppnt";
		iArray[i++][21] = "2";
		//iArray[i++][24] = "blue";
		
		iArray[i] = new Array();
		iArray[i][0] = "�ļ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ַ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		PropInfoGrid = new MulLineEnter("fm", "PropInfoGrid");
		PropInfoGrid.mulLineCount = 0;
		PropInfoGrid.displayTitle = 1;
		PropInfoGrid.locked = 1;
		PropInfoGrid.canSel = 1;
		PropInfoGrid.canChk = 0;
		PropInfoGrid.hiddenSubtraction = 1;
		PropInfoGrid.hiddenPlus = 1;
		PropInfoGrid.selBoxEventFuncName = "";
		PropInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
