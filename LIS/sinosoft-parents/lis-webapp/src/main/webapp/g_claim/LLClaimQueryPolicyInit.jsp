<%
/***************************************************************
 * <p>ProName��LLClaimCaseInput.js</p>
 * <p>Title�������ⰸ��ѯ</p>
 * <p>Description�������ⰸ��ѯ</p>
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

		initPolicyListGrid();		
		
		//mMode:1-���Ᵽ����ѯ;2-����������ѯ
		fm.CustomerNo.value = mCustomerNo;
		fm.RgtNo.value=mRgtNo;
		queryListInfo(); 	
		
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
// ��ʼ��������Ϣ�б�
function initPolicyListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="�б�����";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="Ͷ�����";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";             
    iArray[i][1]="160px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="ǩ������";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;            

    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��ֹ����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="�ܱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
    
    PolicyListGrid = new MulLineEnter( "fm" , "PolicyListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    PolicyListGrid.mulLineCount = 0;   
    PolicyListGrid.displayTitle = 1;
    PolicyListGrid.locked = 0;
    PolicyListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��		
    PolicyListGrid.hiddenPlus=1;
    PolicyListGrid.hiddenSubtraction=1;
    PolicyListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}

</script>
