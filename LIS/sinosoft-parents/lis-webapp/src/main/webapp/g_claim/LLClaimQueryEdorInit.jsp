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

		initEdorListGrid();		
		
		//mMode:1-���Ᵽ����ѯ
		if (mMode=="1") {
			fm.CustomerNo.value = mCustomerNo;
			queryListInfo();			
		}		
		
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
function initEdorListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[i]=new Array();
    iArray[i][0]="�������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";             
    iArray[i][1]="160px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��ȫ�����";             
    iArray[i][1]="140px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="��ȫ��";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��ȫ����"; 
    iArray[i][1]="140px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;                    

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="ȷ������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="contno"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="insuredno"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=3;                    
    
    EdorListGrid = new MulLineEnter( "fm" , "EdorListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    EdorListGrid.mulLineCount = 0;   
    EdorListGrid.displayTitle = 1;
    EdorListGrid.locked = 0;
    EdorListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��		
    EdorListGrid.hiddenPlus=1;
    EdorListGrid.hiddenSubtraction=1;
    EdorListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}

</script>
