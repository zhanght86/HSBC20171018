<%
/***************************************************************
 * <p>ProName��FinAccItemInit.jsp</p>
 * <p>Title����֧��Ŀ�������</p>
 * <p>Description�������ƿ�Ŀ�µķ�֧��Ŀ</p>
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
		
		initFinAccItemGrid();
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

function initFinAccItemGrid() {
	
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
		iArray[i][0]="��֧��Ŀ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����";
		iArray[i][1]="180px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����1";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����1����";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����2";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����2����";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����3";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��֧��Ŀ����3����";
		iArray[i][1]="0px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
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
		
		FinAccItemGrid = new MulLineEnter("fm", "FinAccItemGrid");
		FinAccItemGrid.mulLineCount = 0;
		FinAccItemGrid.displayTitle = 1;
		FinAccItemGrid.locked = 0;
		FinAccItemGrid.canSel = 1;
		FinAccItemGrid.canChk = 0;
		FinAccItemGrid.hiddenSubtraction = 1;
		FinAccItemGrid.hiddenPlus = 1;
		FinAccItemGrid.selBoxEventFuncName = "showFinAccItemInfo";
		FinAccItemGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
