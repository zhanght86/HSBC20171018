<%
/***************************************************************
 * <p>ProName��LlmodifyconfigInit.jsp</p>
 * <p>Title�������޸Ĺ������ý���</p>
 * <p>Description�������޸Ĺ�������ȷ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��־��
 * @version  : 8.0
 * @date     : 2014-06-01
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
        initRuleTypeGrid();
        initPolTypeGrid();
        initTiaoZhengGrid();
        
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
 * ���������б�
 */
function initRuleTypeGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		RuleTypeGrid = new MulLineEnter("fm", "RuleTypeGrid");
		RuleTypeGrid.mulLineCount = 0;
		RuleTypeGrid.displayTitle = 1;
		RuleTypeGrid.canSel = 0;
		RuleTypeGrid.canChk = 1;
		RuleTypeGrid.hiddenPlus = 1;
		RuleTypeGrid.hiddenSubtraction = 1;
		RuleTypeGrid.selBoxEventFuncName = "";
		RuleTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��RuleTypeGridʱ����"+ ex);
	}
}

function initPolTypeGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PolTypeGrid = new MulLineEnter("fm", "PolTypeGrid");
		PolTypeGrid.mulLineCount = 0;
		PolTypeGrid.displayTitle = 1;
		PolTypeGrid.canSel = 0;
		PolTypeGrid.canChk = 1;
		PolTypeGrid.hiddenPlus = 1;
		PolTypeGrid.hiddenSubtraction = 1;
		PolTypeGrid.selBoxEventFuncName = "";
		PolTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PolTypeGridʱ����"+ ex);
	}
}

function initTiaoZhengGrid() {
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޸�ԭ������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		TiaoZhengGrid = new MulLineEnter("fm", "TiaoZhengGrid");
		TiaoZhengGrid.mulLineCount = 0;
		TiaoZhengGrid.displayTitle = 1;
		TiaoZhengGrid.canSel = 0;
		TiaoZhengGrid.canChk = 1;
		TiaoZhengGrid.hiddenPlus = 1;
		TiaoZhengGrid.hiddenSubtraction = 1;
		TiaoZhengGrid.selBoxEventFuncName = "";
		TiaoZhengGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��TiaoZhengGridʱ����"+ ex);
	}
}


</script>
