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

		initLastCaseListGrid();
		initLastCaseDetailGrid();
		
		//mMode:0-�ⰸ��ѯ(�����ⰸ������;����);1-�����ⰸ��ѯ
		if (mMode=="1") {
			fm.CustomerNo.value = mCustomerNo;
			queryClick();			
		} else if (mMode=="0") {
			fm.CustomerNo.value = mCustomerNo;
			queryClick1();	
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
		
		initQueryInfo();
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ����ѯ����
 */
function initParam() {
	
	fm.GrpRgtNo.value = "";	
	fm.GrpName.value = "";	
	fm.AcceptCom.value = "";
	fm.AcceptComName.value = "";	
	fm.CustomerName.value = "";	
	fm.IDNo.value = "";	
	fm.RgtNo.value = "";	
	fm.StartDate.value = "";	
	fm.EndDate.value = "";	
	fm.PageNo.value = "";
	if (mCustomerNo!=null && mCustomerNo!="" && mCustomerNo!="null") {
		fm.CustomerNo.value = mCustomerNo;
	} else {
		fm.CustomerNo.value = "";
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
		
		//mMode:0-�ⰸ��ѯ;1-�����ⰸ��ѯ
		if (mMode=="1") {
			document.getElementById("queryInfo").style.display = "";
			fm.CaseQuery.style.display = "";
			fm.AllCaseDetail.style.display = "none";
			fm.CaseDetail.style.display = "";
		} else {
			document.getElementById("queryInfo").style.display = "";
			fm.CaseQuery.style.display = "";
			fm.AllCaseDetail.style.display = "none";
			fm.CaseDetail.style.display = "";
			fm.BackButton.style.display = "";
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
// ��ʼ��������Ϣ�б�
function initLastCaseListGrid() {
	
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
    iArray[i][2]=120;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="Ͷ��������";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="�������";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="140px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ���";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;            

    iArray[i]=new Array();
    iArray[i][0]="����������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="�᰸����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������ˮ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����״̬����"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    iArray[i]=new Array();
    
    iArray[i][0]="�������ͱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    LastCaseListGrid = new MulLineEnter( "fm" , "LastCaseListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LastCaseListGrid.mulLineCount = 0;   
    LastCaseListGrid.displayTitle = 1;
    LastCaseListGrid.locked = 0;
    LastCaseListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		LastCaseListGrid.selBoxEventFuncName ="showSelectDetail"; //��Ӧ�ĺ���������������        
    LastCaseListGrid.hiddenPlus=1;
    LastCaseListGrid.hiddenSubtraction=1;
    LastCaseListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}

// ��ʼ�������⸶��ϸ
function initLastCaseDetailGrid() {
	
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
    iArray[i][0]="�⸶����";             
    iArray[i][1]="100px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶����";             
    iArray[i][1]="100px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶������";             
    iArray[i][1]="100px";                
    iArray[i][2]=160;                 
    iArray[i++][3]=0;        

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��˽��"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="ʹ�ù�������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�۲���"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="���ⷽʽ"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="���޶�"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶����"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;                       
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶���"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    LastCaseDetailGrid = new MulLineEnter( "fm" , "LastCaseDetailGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LastCaseDetailGrid.mulLineCount = 0;   
    LastCaseDetailGrid.displayTitle = 1;
    LastCaseDetailGrid.locked = 0;
    LastCaseDetailGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LastCaseDetailGrid.hiddenPlus=1;
    LastCaseDetailGrid.hiddenSubtraction=1;
    LastCaseDetailGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}
</script>
