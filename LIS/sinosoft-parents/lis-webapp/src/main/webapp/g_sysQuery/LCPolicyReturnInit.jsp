<%
/***************************************************************
 * <p>ProName��LCPolicyReturnInit.jsp</p>
 * <p>Title����ִ��ѯ</p>
 * <p>Description����ִ��ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-08-04
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
		fm.ManageCom.value = tManageCom;  
		
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
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���屣����"; 
		iArray[i][1]="130px";
		iArray[i][2]=120; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������"; 
		iArray[i][1]="150px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ��������"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ�����"; 
		iArray[i][1]="70px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ǩ������"; 
		iArray[i][1]="80px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ǩ������"; 
		iArray[i][1]="80px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������"; 
		iArray[i][1]="80px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="״̬"; 
		iArray[i][1]="60px";
		iArray[i][2]=60; 
		iArray[i++][3]=0;

		QueryResultGrid = new MulLineEnter( "fm" , "QueryResultGrid");
		//��Щ���Ա�����loadMulLineǰ
		QueryResultGrid.mulLineCount = 0;
		QueryResultGrid.displayTitle = 1;
		QueryResultGrid.locked = 0;
		QueryResultGrid.canSel =0;
		QueryResultGrid.hiddenPlus=1;
		QueryResultGrid.hiddenSubtraction=1;
		QueryResultGrid.loadMulLine(iArray);
	} catch(ex){
	  alert("ex");
	}
}
</script>
