<%
/***************************************************************
 * <p>ProName��LLClaimQuerySocialInfoInit.jsp</p>
 * <p>Title���籣��ѯ</p>i
 * <p>Description���籣��ѯ</p>
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
		
		initQuesRecordGrid();
		queryQuestionInfo();
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

function initQuesRecordGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����˱���";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;

		iArray[i]=new Array();
		iArray[i][0]="����������";
		iArray[i][1]="40px";
		iArray[i][2]=120;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="�籣��ʶ";
		iArray[i][1]="30px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
				
		iArray[i]=new Array();
		iArray[i][0]="�籣���ڵ�";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������ڵ�";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		QuesRecordGrid = new MulLineEnter("fm","QuesRecordGrid");
		QuesRecordGrid.mulLineCount = 1;//Ĭ�ϳ�ʼ����ʾ����
		QuesRecordGrid.displayTitle = 1;
		QuesRecordGrid.locked = 0;
		QuesRecordGrid.canSel = 0;//��ѡ��ť��1��ʾ��0����
		QuesRecordGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		QuesRecordGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		QuesRecordGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ������������б�񱨴�!");
	}
	
}

</script>