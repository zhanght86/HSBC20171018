<%
/***************************************************************
 * <p>ProName��LLClaimBlackQueryInput.js</p>
 * <p>Title����������ѯ</p>
 * <p>Description����������ѯ</p>
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

		initBlackListGrid();
		
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
	
	fm.GrpContNo.value = "";	
	fm.GrpName.value = "";	
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
// ��ʼ��������Ϣ�б�
function initBlackListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������˱���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա�";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;          

    iArray[i]=new Array();
    iArray[i][0]="��������";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ�״̬";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="ҵ������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="ҵ�����"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������ԭ��"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="���������ñ�ע"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                          
    
    BlackListGrid = new MulLineEnter( "fm" , "BlackListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BlackListGrid.mulLineCount = 0;   
    BlackListGrid.displayTitle = 1;
    BlackListGrid.locked = 0;
    BlackListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//BlackListGrid.selBoxEventFuncName ="showSelectDetail"; //��Ӧ�ĺ���������������        
    BlackListGrid.hiddenPlus=1;
    BlackListGrid.hiddenSubtraction=1;
    BlackListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}
</script>
