<%
/***************************************************************
 * <p>ProName��EdorPrintInput.jsp</p>
 * <p>Title��������ӡ</p>
 * <p>Description��������ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-09-20
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
		initQueryResultGrid();
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
// ��ѯ�����ʼ��
function initQueryResultGrid() {
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������"; 
		iArray[i][1]="20px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ȫ�����";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ���˿ͻ���"; 
		iArray[i][1]="40px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������"; 
		iArray[i][1]="80px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��/���ѽ��"; 
		iArray[i][1]="35px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������"; 
		iArray[i][1]="30px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		QueryResultGrid = new MulLineEnter( "fm" , "QueryResultGrid");
		QueryResultGrid.mulLineCount = 0;
		QueryResultGrid.displayTitle = 1;
		QueryResultGrid.locked = 0;
		QueryResultGrid.canSel =1;
		QueryResultGrid.hiddenPlus=1;
		QueryResultGrid.hiddenSubtraction=1;
		QueryResultGrid.selBoxEventFuncName = "";
		QueryResultGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}
</script>
