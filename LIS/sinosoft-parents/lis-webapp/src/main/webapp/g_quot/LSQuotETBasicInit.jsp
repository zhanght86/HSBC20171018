<%
/***************************************************************
 * <p>ProName��LSQuotETBasicInit.jsp</p>
 * <p>Title��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Description��һ��ѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
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
		
		initAgencyListGrid();	
		initRelaCustListGrid();
		initQuotStep1();//��ʼ����һ��ѯ����Ϣ
		judgeShowQuest(tQuotNo, tQuotBatNo, tActivityID);
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
 * �н����¼��
 */
function initAgencyListGrid() {

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
		iArray[i][0] = "�н��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		AgencyListGrid = new MulLineEnter("fm2", "AgencyListGrid");
		AgencyListGrid.mulLineCount = 0;
		AgencyListGrid.displayTitle = 1;
		AgencyListGrid.locked = 0;
		AgencyListGrid.canSel = 0;
		AgencyListGrid.canChk = 0;
		AgencyListGrid.hiddenSubtraction = 0;
		AgencyListGrid.hiddenPlus = 0;
		//AgencyListGrid.selBoxEventFuncName = "";
		AgencyListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ����׼�ͻ��б�
 */
function initRelaCustListGrid() {
	
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
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryCustomer";
		
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RelaCustListGrid = new MulLineEnter("fm2", "RelaCustListGrid");
		RelaCustListGrid.mulLineCount = 0;
		RelaCustListGrid.displayTitle = 1;
		RelaCustListGrid.locked = 0;
		RelaCustListGrid.canSel = 0;
		RelaCustListGrid.canChk = 0;
		RelaCustListGrid.hiddenSubtraction = 0;
		RelaCustListGrid.hiddenPlus = 0;
		RelaCustListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
