<%
/***************************************************************
 * <p>ProName��LSQuotProjBasicInit.jsp</p>
 * <p>Title����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Description����Ŀѯ�ۻ�����Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-03-26
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
		
		initAppOrgCodeGrid();//���û���
		initAgencyNameGrid();//�н�����
		initLinkInquiryNoGrid();//����ѯ�ۺ�
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
 * ���û�������
 */
function initAppOrgCodeGrid() {
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		/**
		iArray[i] = new Array();
		iArray[i][0] = "���û�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryCom";
		**/
		
		iArray[i] = new Array();
		iArray[i][0] = "���û�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "conditioncomcode";
		iArray[i][5] = "1|2";
		iArray[i][6] = "0|1";
		iArray[i][15] = "comgrade";
		iArray[i][16] = " #01# or comgrade=#02# or comgrade=#03# ";
		iArray[i++][19] = "1";
		
		iArray[i] = new Array();
		iArray[i][0] = "���û�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AppOrgCodeGrid = new MulLineEnter("fm2", "AppOrgCodeGrid");
		AppOrgCodeGrid.mulLineCount = 1;
		AppOrgCodeGrid.displayTitle = 1;
		AppOrgCodeGrid.locked = 0;
		AppOrgCodeGrid.canSel = 0;
		AppOrgCodeGrid.canChk = 0;
		AppOrgCodeGrid.hiddenSubtraction = 0;
		AppOrgCodeGrid.hiddenPlus = 0;
		AppOrgCodeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * �н��������
 */
function initAgencyNameGrid() {
	
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
		iArray[i][0] = "�н����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		AgencyNameGrid = new MulLineEnter("fm2", "AgencyNameGrid");
		AgencyNameGrid.mulLineCount = 1;
		AgencyNameGrid.displayTitle = 1;
		AgencyNameGrid.locked = 0;
		AgencyNameGrid.canSel = 0;
		AgencyNameGrid.canChk = 0;
		AgencyNameGrid.hiddenSubtraction = 0;
		AgencyNameGrid.hiddenPlus = 0;
		AgencyNameGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ����ѯ�ۺ�
 */
function initLinkInquiryNoGrid() {
	
	turnPage4 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ѯ�ۺ�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		LinkInquiryNoGrid = new MulLineEnter("fm2", "LinkInquiryNoGrid");
		LinkInquiryNoGrid.mulLineCount = 1;
		LinkInquiryNoGrid.displayTitle = 1;
		LinkInquiryNoGrid.locked = 0;
		LinkInquiryNoGrid.canSel = 0;
		LinkInquiryNoGrid.canChk = 0;
		LinkInquiryNoGrid.hiddenSubtraction = 0;
		LinkInquiryNoGrid.hiddenPlus = 0;
		LinkInquiryNoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
