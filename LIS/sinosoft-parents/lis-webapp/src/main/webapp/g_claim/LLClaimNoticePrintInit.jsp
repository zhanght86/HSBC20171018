<%
/***************************************************************
 * <p>ProName��LLClaimNoticePrintInit.jsp</p>
 * <p>Title������֪ͨ���ӡ</p>
 * <p>Description������֪ͨ���ӡ</p>
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
		
		initClaimGrid();
		initCaseGrid();
   
		
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
function initClaimGrid() {
	
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
    iArray[i][0]="���κ�";
    iArray[i][1]="150px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ���˱���";
    iArray[i][1]="180px";
    iArray[i][2]=120;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;
    
   	iArray[i]=new Array();
    iArray[i][0]="��������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="δ��������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="δ�᰸����";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
  	iArray[i]=new Array();
    iArray[i][0]="�ѽ᰸����";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶���";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
    ClaimGrid = new MulLineEnter("fm","ClaimGrid");
    ClaimGrid.mulLineCount =0;
    ClaimGrid.displayTitle = 1;
    ClaimGrid.locked = 1;
    ClaimGrid.canSel =1;
    ClaimGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    ClaimGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    ClaimGrid. selBoxEventFuncName = "queryCustomer";
    ClaimGrid.loadMulLine(iArray);
  }catch(ex){
		alert("��ʼ����񱨴�!");
	}
	
}

//��ʾ��������Ϣ add by fengzg 
function initCaseGrid()
{
  turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
    iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="���κ�";
    iArray[i][1]="150px";
    iArray[i][2]=100;
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="������";
    iArray[i][1]="150px";
    iArray[i][2]=100;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���˿ͻ���";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=3;    
    
		iArray[i]=new Array();
    iArray[i][0]="Ͷ���˱���";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;

    
    iArray[i]=new Array();
    iArray[i][0]="������������";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="�Ա����";
    iArray[i][1]="70px";
    iArray[i][2]=80;
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա�";
    iArray[i][1]="50px";
    iArray[i][2]=80;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������";
    iArray[i][1]="80px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";
    iArray[i][1]="80px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";
    iArray[i][1]="150px";
    iArray[i][2]=120;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�⸶���";
    iArray[i][1]="60px";
    iArray[i][2]=60;
    iArray[i++][3]=0;
  
	iArray[i]=new Array();
    iArray[i][0]="�᰸����";
    iArray[i][1]="80px";
    iArray[i][2]=80;
    iArray[i++][3]=0;        
    
    CaseGrid = new MulLineEnter("fm","CaseGrid");
    CaseGrid.mulLineCount =0;
    CaseGrid.displayTitle = 1;
    CaseGrid.locked = 1;
    CaseGrid.canSel =0;
    CaseGrid.canChk =1;
    CaseGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    CaseGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    CaseGrid. selBoxEventFuncName = "onSelSelected";
    CaseGrid.loadMulLine(iArray);
	}catch(ex){
		alert("��ʼ����񱨴�!");
	}
	
}
</script>