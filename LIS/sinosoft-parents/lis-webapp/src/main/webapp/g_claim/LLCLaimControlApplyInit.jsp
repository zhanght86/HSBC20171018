<%
/***************************************************************
 * <p>ProName��LLCLaimControlApplyInit.jsp</p>
 * <p>Title���������ο����������</p>
 * <p>Description���������ο����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
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
		
		initPolicyGrid();
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

function initPolicyGrid() {     

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
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ�����";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ч����";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ǩ������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		PolicyGrid = new MulLineEnter("fm", "PolicyGrid");
		PolicyGrid.mulLineCount = 0;
		PolicyGrid.displayTitle = 1;
		PolicyGrid.locked = 0;
		PolicyGrid.canSel = 1;
		PolicyGrid.hiddenPlus=1;
		PolicyGrid.hiddenSubtraction=1;
		PolicyGrid.selBoxEventFuncName = "";
		PolicyGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}
</script>
