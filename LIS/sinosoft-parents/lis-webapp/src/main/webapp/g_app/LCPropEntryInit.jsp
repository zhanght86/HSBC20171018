<%
/***************************************************************
 * <p>ProName��LCPropEntryInit.jsp</p>
 * <p>Title��Ͷ������Ϣ¼��</p>
 * <p>Description��Ͷ������Ϣ¼��</p>
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
		
		initIDInfoGrid();
		initZJGrid();
		initEntry();
		showInfo();
		initState();
		getElasticflag();
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
		fm.GrpPropNo.value = tGrpPropNo;
		fm.Flag.value = tFlag;
		fm.QuotType.value = getQuotType(tGrpPropNo);
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
function initIDInfoGrid() {
	
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
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][9]="֤�����ͱ���|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][9]="֤�����ͱ���|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Ч����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Ч����|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Чֹ��";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Чֹ��|date";
		
		IDInfoGrid = new MulLineEnter("fm", "IDInfoGrid");
		IDInfoGrid.mulLineCount = 0;
		IDInfoGrid.displayTitle = 1;
		IDInfoGrid.locked = 0;
		IDInfoGrid.canSel = 0;
		IDInfoGrid.canChk = 0;
		IDInfoGrid.hiddenSubtraction = 0;
		IDInfoGrid.hiddenPlus = 0;
		IDInfoGrid.selBoxEventFuncName = "";
		IDInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initZJGrid() {
	
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
		iArray[i][0] = "�н��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="�н��������|notnull";
				
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="�н��������|notnull";
				
		ZJGrid = new MulLineEnter("fm", "ZJGrid");
		ZJGrid.mulLineCount = 0;
		ZJGrid.displayTitle = 1;
		ZJGrid.locked = 0;
		ZJGrid.canSel = 0;
		ZJGrid.canChk = 0;
		ZJGrid.hiddenSubtraction = 0;
		ZJGrid.hiddenPlus = 0;
		ZJGrid.selBoxEventFuncName = "shouZJ";
		ZJGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
