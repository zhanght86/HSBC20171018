<%
/***************************************************************
 * <p>ProName��LLClaimNoAcceptInit.jsp</p>
 * <p>Title��δ����ͻ���ѯ</p>
 * <p>Description��δ����ͻ���ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-04-23
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
		
		queryCustomerList();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}
/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
		initCustomerInfo();//��ʼ��δ����ͻ���Ϣ
		
	} catch (re) {
		alert("��ʼ����������");
	}
}
/**
 * ��ʼ������
 */
function initCustomerInfo() {
	
	fm.GrpRgtNo.value = mGrpRgtNo;
	fm.CustomerNo.value = "";
	fm.RgtNo.value = "";		
	fm.CustomerName.value = "";	
	fm.Birthday.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value="";
	fm.GenderName.value = "";	
	fm.AppAmnt.value = "";	
	fm.BillCount.value = "";
	fm.ScanCount.value = "";	
	fm.NoAcceptReasonName.value = "";
	fm.NoAcceptReason.value = "";
	getInfo();
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
		
		
		if (mMode=="1") {
			fm.addClickButton.style.display="none";
			fm.modifyClickButton.style.display="none";
			fm.deleteClickButton.style.display="none";
			fm.deleteAllButton.style.display="none";
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
// δ����ͻ���Ϣ�б�
function initNoAcceptListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="RgtNo";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="CustomerNo";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="����";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�Ա�"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��Ʊ��"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ӱ�����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="δ����ԭ��"; 
    iArray[i][1]="200px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;
    
    NoAcceptListGrid = new MulLineEnter( "fm" , "NoAcceptListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    NoAcceptListGrid.mulLineCount = 0;   
    NoAcceptListGrid.displayTitle = 1;
    NoAcceptListGrid.locked = 0;
    NoAcceptListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    NoAcceptListGrid.hiddenPlus=1;
		NoAcceptListGrid.selBoxEventFuncName ="showSelectCustomerInfo"; //��Ӧ�ĺ���������������
    NoAcceptListGrid.hiddenSubtraction=1;
    NoAcceptListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
