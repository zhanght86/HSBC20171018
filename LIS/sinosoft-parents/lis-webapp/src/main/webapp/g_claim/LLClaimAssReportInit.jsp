<%
/***************************************************************
 * <p>ProName��LLClaimAssReportInit.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-01-01
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
		
		initAssociatedReportGrid();
		initLLClaimReportGrid();
		
		queryOnReportInfo();
		queryOffReportInfo();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.RgtNo.value = mRgtNo;
		fm.CustomerNo.value = mCustomerNo;
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
		if(mMode=="0"){
			document.getElementById("tRela").style.display="";	
			document.getElementById("removeRela").style.display="";	
		}else if(mMode=="1"){
			document.getElementById("tRela").style.display="none";	
			document.getElementById("removeRela").style.display="none";	
		}
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
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
          
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="160px";
    iArray[i][2]=160; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ�����"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
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
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;        
        
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ش󰸼�"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    LLClaimReportGrid = new MulLineEnter( "fm" , "LLClaimReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimReportGrid.mulLineCount = 0;   
    LLClaimReportGrid.displayTitle = 1;
    LLClaimReportGrid.locked = 0;
    LLClaimReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimReportGrid.hiddenPlus=1;
    LLClaimReportGrid.hiddenSubtraction=1;
    LLClaimReportGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// �ѹ���������Ϣ
function initAssociatedReportGrid() {
	       
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
    iArray[i++][3]=0;
          
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������"; 
    iArray[i][1]="160px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="�����Ǽ�����"; 
    iArray[i][1]="100px";
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
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ش󰸼�"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�ѹ���������"; 
    iArray[i][1]="160px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;    
    
    AssociatedReportGrid = new MulLineEnter( "fm" , "AssociatedReportGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    AssociatedReportGrid.mulLineCount = 0;   
    AssociatedReportGrid.displayTitle = 1;
    AssociatedReportGrid.locked = 0;
    AssociatedReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //AssociatedReportGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    AssociatedReportGrid.hiddenPlus=1;
    AssociatedReportGrid.hiddenSubtraction=1;
    AssociatedReportGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfLLClaimReportGrid");
    }
}
</script>
