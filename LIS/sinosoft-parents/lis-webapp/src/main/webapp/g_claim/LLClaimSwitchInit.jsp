<%
/***************************************************************
 * <p>ProName��LLClaimBenefitInput.jsp</p>
 * <p>Title����ͣ����������</p>
 * <p>Description����ͣ����������</p>
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
		
		initClaimCaseGrid();
		initClaimCaseTraceGrid();		
		
		queryClaimList();//��ѯ���������а���
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.RgtNo.value = mRgtNo;
		fm.CustomerNo.value = mCustomerNo;
		fm.PauseReason.value = "";
		fm.PauseReasonName.value = "";	
		fm.ReasonDesc.value = "";		
		
		
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


/**=========================================================================
    ����������
   =========================================================================
*/
function initClaimCaseGrid(){
	
	turnPage = new turnPageClass();
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
		iArray[i][1]="160px";
		iArray[i][2]=160;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա�";
		iArray[i][1]="100px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="ClmState";
		iArray[i][1]="60px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
			
		ClaimCaseGrid = new MulLineEnter("fm","ClaimCaseGrid");
		ClaimCaseGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		ClaimCaseGrid.displayTitle = 1;
		ClaimCaseGrid.locked = 0;
		ClaimCaseGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		ClaimCaseGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		ClaimCaseGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		ClaimCaseGrid.selBoxEventFuncName ="showSelectTrace"; //��Ӧ�ĺ���������������				
		ClaimCaseGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ������������б�񱨴�!");
	}
	
}

function initClaimCaseTraceGrid(){
	
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
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="140px";
		iArray[i][2]=140;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ͣ������";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="��ͣ����";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ͣԭ��";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ԭ������";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ظ�������";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ظ�����";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ظ�����";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;					
		
		ClaimCaseTraceGrid = new MulLineEnter("fm","ClaimCaseTraceGrid");
		ClaimCaseTraceGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		ClaimCaseTraceGrid.displayTitle = 1;
		ClaimCaseTraceGrid.locked = 0;
		ClaimCaseTraceGrid.canSel = 0;//��ѡ��ť��1��ʾ��0����
		ClaimCaseTraceGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		ClaimCaseTraceGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ		
		ClaimCaseTraceGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ�������켣�б���!");
	}
	
}

</script>
