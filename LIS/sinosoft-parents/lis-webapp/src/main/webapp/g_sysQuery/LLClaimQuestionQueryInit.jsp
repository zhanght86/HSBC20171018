<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptQueryInput.js</p>
 * <p>Title���������ѯ</p>
 * <p>Description���������ѯ</p>
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

		initQuestionListGrid();
		
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
	
	fm.GrpRgtNo.value = "";	
	fm.RgtNo.value = "";	
	fm.PageNo.value = "";	
	fm.GrpContNo.value = "";	
	fm.GrpName.value = "";	
	fm.CustomerName.value = "";
	fm.InputStartDate.value = "";	
	fm.InputEndDate.value = "";	
	fm.State.value = "";
	fm.StateName.value = "";
	fm.DealStartDate.value = "";	
	fm.DealEndDate.value = "";	
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";
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
// ��ʼ��������б�
function initQuestionListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";
    iArray[i][1]="30px";
    iArray[i][2]=10;
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�������κ�";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="������ת��";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;   
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;  
    
    iArray[i]=new Array();
    iArray[i][0]="��������ͱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        
    
    iArray[i]=new Array();
    iArray[i][0]="���������"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ʱЧ"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                                           
    
    QuestionListGrid = new MulLineEnter( "fm" , "QuestionListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    QuestionListGrid.mulLineCount = 0;   
    QuestionListGrid.displayTitle = 1;
    QuestionListGrid.locked = 0;
    QuestionListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//QuestionListGrid.selBoxEventFuncName ="showSelectDetail"; //��Ӧ�ĺ���������������        
    QuestionListGrid.hiddenPlus=1;
    QuestionListGrid.hiddenSubtraction=1;
    QuestionListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->QuestionListGrid");
    }
}
</script>
