<%
/***************************************************************
 * <p>ProName��LLClaimReportMainInit.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
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

		initLLClaimReportGrid();
		initSelfLLClaimReprotGrid();
		
		querySelfClick();
		
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
		
//		if (mManageCom!=null && mManageCom.length!=6) {
			
//			fm.reportEnter.disabled=true;
//			fm.reportRelease.disabled=true;
//			fm.reportNew.disabled=true;
//		}
		
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
// ������Ϣ�б�ĳ�ʼ��
function initLLClaimReportGrid() {
	
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
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ���";
    iArray[i][1]="100px";
    iArray[i][2]=100;
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ���"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ȷ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ȷ������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;        
        
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ش󰸼�"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
        
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    /* iArray[i]=new Array();
    iArray[i][0]="����״̬����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3; */  
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;  
    
    
    
    
    LLClaimReportGrid = new MulLineEnter( "fm" , "LLClaimReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimReportGrid.mulLineCount = 0;   
    LLClaimReportGrid.displayTitle = 1;
    LLClaimReportGrid.locked = 0;
    LLClaimReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimReportGrid.hiddenPlus=1;
    LLClaimReportGrid.hiddenSubtraction=1;
    LLClaimReportGrid.selBoxEventFuncName = "selectReportButton";
    LLClaimReportGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// ������Ϣ�б�ĳ�ʼ������
function initSelfLLClaimReprotGrid() {
	      
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
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i][3]=0;    
    iArray[i++][7]="enterReport";    
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ���";
    iArray[i][1]="100px";
    iArray[i][2]=100;
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ���"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ�����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ȷ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ȷ������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;        
        
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ش󰸼�"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
        
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    /* iArray[i]=new Array();
    iArray[i][0]="����״̬����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;  */ 
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;    
    
    SelfLLClaimReportGrid = new MulLineEnter( "fm" , "SelfLLClaimReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    SelfLLClaimReportGrid.mulLineCount = 0;   
    SelfLLClaimReportGrid.displayTitle = 1;
    SelfLLClaimReportGrid.locked = 0;
    SelfLLClaimReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //SelfLLClaimReportGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    SelfLLClaimReportGrid.selBoxEventFuncName = "selectSelfReportButton"
    SelfLLClaimReportGrid.hiddenPlus=1;
    SelfLLClaimReportGrid.hiddenSubtraction=1;
    SelfLLClaimReportGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfLLClaimReportGrid");
    }
}
</script>
