<%
/***************************************************************
 * <p>ProName��FinAccountInit.jsp</p>
 * <p>Title����ƿ�Ŀά������</p>
 * <p>Description��ά���������漰�Ļ�ƿ�Ŀ</p>
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
		
		initFinAccountGrid();
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

function initFinAccountGrid() {
	
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
		iArray[i][0]="��ƿ�Ŀ����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ƿ�Ŀ����";
		iArray[i][1]="180px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ƿ�Ŀ���ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��ƿ�Ŀ����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ע";
		iArray[i][1]="100px";
		iArray[i][2]=400;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="¼����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="¼������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����޸Ĳ�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����޸�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		FinAccountGrid = new MulLineEnter("fm", "FinAccountGrid");
		FinAccountGrid.mulLineCount = 0;
		FinAccountGrid.displayTitle = 1;
		FinAccountGrid.locked = 0;
		FinAccountGrid.canSel = 1;
		FinAccountGrid.canChk = 0;
		FinAccountGrid.hiddenSubtraction = 1;
		FinAccountGrid.hiddenPlus = 1;
		FinAccountGrid.selBoxEventFuncName = "showFinAccountInfo";
		FinAccountGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
