<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptQueryInput.js</p>
 * <p>Title��δ����ͻ���ѯ</p>
 * <p>Description��δ����ͻ���ѯ</p>
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

		initNoAcceptListGrid();
		
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
function initNoAcceptListGrid() {
	
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
    iArray[i][0]="Ͷ���˱���";             
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
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�˵���"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;     
    
    iArray[i]=new Array();
    iArray[i][0]="δ����ԭ��"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ʱ��"; 
    iArray[i][1]="140px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                                         
    
    NoAcceptListGrid = new MulLineEnter( "fm" , "NoAcceptListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    NoAcceptListGrid.mulLineCount = 0;   
    NoAcceptListGrid.displayTitle = 1;
    NoAcceptListGrid.locked = 0;
    NoAcceptListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//NoAcceptListGrid.selBoxEventFuncName ="showSelectDetail"; //��Ӧ�ĺ���������������        
    NoAcceptListGrid.hiddenPlus=1;
    NoAcceptListGrid.hiddenSubtraction=1;
    NoAcceptListGrid.loadMulLine(iArray);
         
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->NoAcceptListGrid");
    }
}
</script>
