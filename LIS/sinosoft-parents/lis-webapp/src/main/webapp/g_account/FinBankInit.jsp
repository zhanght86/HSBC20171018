<%
/***************************************************************
 * <p>ProName��FinBankInit.jsp</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2013-01-01
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
		
		initFinBankGrid();
	} catch(ex) {
		alert("��ʼ���������!");
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
 * ������Ϣ
 */
function initFinBankGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���б���";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����˺�";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���д������";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���д�������";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˻����ʱ���";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�˻�����";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="״̬����";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="״̬";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		FinBankGrid = new MulLineEnter("fm", "FinBankGrid");
		FinBankGrid.mulLineCount = 0;
		FinBankGrid.displayTitle = 1;
		FinBankGrid.locked = 0;
		FinBankGrid.canSel = 1;
		FinBankGrid.canChk = 0;
		FinBankGrid.hiddenSubtraction = 1;
		FinBankGrid.hiddenPlus = 1;
		FinBankGrid.selBoxEventFuncName = "showFinBankInfo";
		FinBankGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
