<%
/***************************************************************
 * <p>ProName��LLClaimCasePrintInit.jsp</p>
 * <p>Title��������ӡ</p>
 * <p>Description��������ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
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

		initBatchListGrid();
		initCustomerListGrid();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
		initQueryGrpInfo();
		initQueryRgtInfo();
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ���������β�ѯ����
 */
function initQueryGrpInfo() {
	
	fm.GrpRgtNo.value = "";
	fm.GrpName.value = "";
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";
	fm.StartDate.value = "";
	fm.EndDate.value = "";
	fm.PrintState.value = "";
	fm.PrintStateName.value = "";	
	fm.ConfirmStartDate.value = "";
	fm.ConfirmEndDate.value = "";
	
}

/**
 * ��ʼ�����˰�����ѯ����
 */
function initQueryRgtInfo() {
	
	fm.SingleGrpName.value = "";
	fm.SingleCustomerName.value = "";
	fm.SingleIdNo.value = "";
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
// �����б���Ϣ
function initBatchListGrid() {
	
		turnPage1 = new turnPageClass(); 	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������    

    iArray[i]=new Array();
    iArray[i][0]="���κ�";             
    iArray[i][1]="180px";                
    iArray[i][2]=180;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="δ��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ȷ������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��ӡ״̬"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    BatchListGrid = new MulLineEnter( "fm" , "BatchListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BatchListGrid.mulLineCount = 0;   
    BatchListGrid.displayTitle = 1;
    BatchListGrid.locked = 0;
    BatchListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    BatchListGrid.hiddenPlus=1;
    BatchListGrid.hiddenSubtraction=1;
    BatchListGrid.selBoxEventFuncName ="showSelectPrintInfo"; //��Ӧ�ĺ���������������
    BatchListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// �ͻ���Ϣ�б�
function initCustomerListGrid() {

		turnPage2 = new turnPageClass(); 
    var iArray = new Array();
    var i=0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[i]=new Array();
    iArray[i][0]="���κ�";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="���˰�����";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="��ӡ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��ӡ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��ӡ״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    CustomerListGrid.canChk =1
    CustomerListGrid.selBoxEventFuncName ="showSelectInfo"; //��Ӧ�ĺ���������������
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfLLClaimReportGrid");
    }
}
</script>
