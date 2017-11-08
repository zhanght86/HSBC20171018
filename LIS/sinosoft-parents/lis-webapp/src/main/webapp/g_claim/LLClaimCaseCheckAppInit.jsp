<%
/***************************************************************
 * <p>ProName��LLClaimCaseReviewAppInput.js</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
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

		initMainCaseGrid();
		initSelfCaseGrid();
		
		querySelf("INIT");
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
		initMainQueryInfo();
		initSelfQueryInfo();
	
		
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ����ѯ����
 */
function initMainQueryInfo() {
	
	fm.GrpRgtNo.value = "";
	fm.GrpName.value = "";
	fm.AcceptCom.value = "";
	fm.AcceptConfirmStart.value = "";
	fm.AcceptConfirmEnd.value = "";
	fm.AcceptWorkdays.value = "";
}
/**
 * ��ʼ�����˳ز�ѯ����
 */
function initSelfQueryInfo() {
	
	fm.GrpRgtNo1.value = "";
	fm.GrpName1.value = "";
	fm.AcceptCom1.value = "";
	fm.AcceptConfirmStart1.value = "";
	fm.AcceptConfirmEnd1.value = "";
	fm.AcceptWorkdays1.value = "";
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
 * ��ʼ���ؼ�
 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
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
// ��ʼ��������Ϣ�б�
function initMainCaseGrid() {
	
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
    iArray[i][1]="140px";                
    iArray[i][2]=200;                 
    iArray[i++][3]=0;
		
	iArray[i]=new Array();
    iArray[i][0]="��������";             
    iArray[i][1]="60px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0; 

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="δ��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="ʱЧ����(������)"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���ʱ��"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;  
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;   
    
    MainCaseGrid = new MulLineEnter( "fm" , "MainCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MainCaseGrid.mulLineCount = 0;   
    MainCaseGrid.displayTitle = 1;
    MainCaseGrid.locked = 0;
    MainCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    MainCaseGrid.hiddenPlus=1;
    MainCaseGrid.hiddenSubtraction=1;
    MainCaseGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}

// ��ʼ�����˳��°�����Ϣ�б�
function initSelfCaseGrid() {
	       
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
    iArray[i][1]="140px";                
    iArray[i][2]=200;
    iArray[i][3]=0;
    iArray[i++][7]="enterCase";

	iArray[i]=new Array();
    iArray[i][0]="��������";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0; 

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="δ��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="ʱЧ����(������)"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���ʱ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    SelfCaseGrid = new MulLineEnter( "fm" , "SelfCaseGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfCaseGrid.mulLineCount = 0;   
    SelfCaseGrid.displayTitle = 1;
    SelfCaseGrid.locked = 0;
    SelfCaseGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //SelfCaseGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    SelfCaseGrid.hiddenPlus=1;
    SelfCaseGrid.hiddenSubtraction=1;
    SelfCaseGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfCaseGrid");
    }
}
</script>
