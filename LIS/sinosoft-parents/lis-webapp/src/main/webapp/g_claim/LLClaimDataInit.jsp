<%
/***************************************************************
 * <p>ProName��LLClaimDataInit.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
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

		initDocumentListGrid();
		
		initAffix();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.RptNo.value = mRptNo;
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
		
		if (mType=="3" || mType=="4") {
			fm.creatAffix.disabled = true;
			fm.DocumentSave.disabled = true;
			fm.DocumentDelete.disabled = true;
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
//��֤��Ϣ�б�
function initDocumentListGrid() {
		
		turnPage1 = new turnPageClass();
		turnPage1.pageLineNum = 200;
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="��֤����";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="��֤����";             
    iArray[i][1]="380px";                
    iArray[i][2]=380;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��֤���ͱ���"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="��֤����"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

		iArray[i] = new Array();
		iArray[i][0] = "�ύ��ʽ";
		iArray[i][1] = "100px";
		iArray[i][2] = 100;
		iArray[i][3] = 2;
		iArray[i][4] = "property";
		iArray[i][5]="5|6"; 
		iArray[i++][6] = "1|0";
		
		iArray[i] = new Array();
		iArray[i][0] = "Property";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;		
    
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ѱ���"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��֤���"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    DocumentListGrid = new MulLineEnter( "fm" , "DocumentListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    DocumentListGrid.mulLineCount = 0;   
    DocumentListGrid.displayTitle = 1;
    DocumentListGrid.locked = 0;
    DocumentListGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    DocumentListGrid.canChk = 1;
    DocumentListGrid.hiddenPlus=1;
    DocumentListGrid.hiddenSubtraction=1;
    DocumentListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
