<%
/***************************************************************
 * <p>ProName��FinAccItemTraceInit.jsp</p>
 * <p>Title����Ŀ��ϸ��ѯ</p>
 * <p>Description����֧��Ŀ��ϸ��ѯ�뵼��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ʯȫ��
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
		
		initFinAccItemTraceGrid();
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

function initFinAccItemTraceGrid() {
	
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
		iArray[i][0]="ҵ������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���κ�";
		iArray[i][1]="90px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҵ���";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="70px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Oracle��������";
		iArray[i][1]="70px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ɱ�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����־";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ŀ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ŀ����";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ϸ��";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʒ��";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Oracle��Ʒ��";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʒ����";
		iArray[i][1]="180px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ο���";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���ö�";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʒ����1";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʒ����2";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʒ����3";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		FinAccItemTraceGrid = new MulLineEnter("fm", "FinAccItemTraceGrid");
		FinAccItemTraceGrid.mulLineCount = 0;
		FinAccItemTraceGrid.displayTitle = 1;
		FinAccItemTraceGrid.locked = 0;
		FinAccItemTraceGrid.canSel = 0;
		FinAccItemTraceGrid.canChk = 0;
		FinAccItemTraceGrid.hiddenSubtraction = 1;
		FinAccItemTraceGrid.hiddenPlus = 1;
		FinAccItemTraceGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
