<%
/***************************************************************
 * <p>ProName��FinExtractInit.jsp</p>
 * <p>Title����Ʒ�¼��ȡ</p>
 * <p>Description����Ʒ�¼��ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2012-01-01
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
		
		initFinExtractGrid();
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


function initFinExtractGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ҵ���";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ŀ����";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�跽���";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ŀ����";
		iArray[i][1]="80px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��˾����";
		iArray[i][1]="50px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������ϸ";
		iArray[i][1]="70px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���ִ���";
		iArray[i][1]="40px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ɷ���";
		iArray[i][1]="30px";
		iArray[i][2]=1;
		iArray[i++][3]=0;
		
		FinExtractGrid = new MulLineEnter("fm", "FinExtractGrid");
		FinExtractGrid.mulLineCount = 0;
		FinExtractGrid.displayTitle = 1;
		FinExtractGrid.canSel = 0;
		FinExtractGrid.canChk = 0;
		FinExtractGrid.hiddenPlus = 1;
		FinExtractGrid.hiddenSubtraction = 1;
		FinExtractGrid.selBoxEventFuncName = "";
		FinExtractGrid.loadMulLine(iArray);
		
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
