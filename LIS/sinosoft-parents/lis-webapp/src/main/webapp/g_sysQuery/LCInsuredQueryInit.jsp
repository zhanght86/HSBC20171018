<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptQueryInput.js</p>
 * <p>Title���������˲�ѯ</p>
 * <p>Description���������˲�ѯ</p>
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

		initLCInsuredListGrid();
		initMainInsuredListGrid();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
		initQueryInfo();
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ����ѯ����
 */
function initQueryInfo() {
	
	fm.GrpAppNo.value = "";	
	fm.GrpContNo.value = "";	
	fm.AppntName.value = "";	
	fm.CustomerName.value = "";	
	fm.IDNo.value = "";	
	fm.BirthDay.value = "";	
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
// ��ʼ��δ����ͻ��б�
function initLCInsuredListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ���˱���"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���˱�����";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;        

    iArray[i]=new Array();
    iArray[i][0]="�����������˹�ϵ";             
    iArray[i][1]="60px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����˿ͻ���";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա�";             
    iArray[i][1]="40px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="��������";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�б�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="�������˿ͻ���"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;                                               
    
    LCInsuredListGrid = new MulLineEnter( "fm" , "LCInsuredListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsuredListGrid.mulLineCount = 0;   
    LCInsuredListGrid.displayTitle = 1;
    LCInsuredListGrid.locked = 0;
    LCInsuredListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		LCInsuredListGrid.selBoxEventFuncName ="showSelectDetail"; //��Ӧ�ĺ���������������        
    LCInsuredListGrid.hiddenPlus=1;
    LCInsuredListGrid.hiddenSubtraction=1;
    LCInsuredListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LCInsuredListGrid");
    }
}

// ��ʼ��δ����ͻ��б�
function initMainInsuredListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ���˱���"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���˱�����";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;        

    iArray[i]=new Array();
    iArray[i][0]="�����������˹�ϵ";             
    iArray[i][1]="60px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����˿ͻ���";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա�";             
    iArray[i][1]="40px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="��������";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�б�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                                
    
    MainInsuredListGrid = new MulLineEnter( "fm" , "MainInsuredListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    MainInsuredListGrid.mulLineCount = 0;   
    MainInsuredListGrid.displayTitle = 1;
    MainInsuredListGrid.locked = 0;
    MainInsuredListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��      
    MainInsuredListGrid.hiddenPlus=1;
    MainInsuredListGrid.hiddenSubtraction=1;
    MainInsuredListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LCInsuredListGrid");
    }
}
</script>
