<%
/***************************************************************
 * <p>ProName��QueryOfferListInit.jsp</p>
 * <p>Title����ѯ���۵���</p>
 * <p>Description����ѯ���۵���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-06
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
		initOfferListGrid();
		
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

function initOfferListGrid() {
	
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
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ѯ�ۺ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ѯ������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		
		OfferListGrid = new MulLineEnter("fm", "OfferListGrid");
		OfferListGrid.mulLineCount = 0;
		OfferListGrid.displayTitle = 1;
		OfferListGrid.locked = 0;
		OfferListGrid.canSel = 1;
		OfferListGrid.canChk = 0;
		OfferListGrid.hiddenSubtraction = 1;
		OfferListGrid.hiddenPlus = 1;
		//OfferListGrid.selBoxEventFuncName = "queryAgentInfo";
		OfferListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��init����!");
	}
}
</script>
 
