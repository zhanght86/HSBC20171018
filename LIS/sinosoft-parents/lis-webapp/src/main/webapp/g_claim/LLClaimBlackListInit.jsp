<%
/***************************************************************
 * <p>ProName��LLClaimBlackListInit.jsp</p>
 * <p>Title������������</p>
 * <p>Description������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
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
		
		initCustomerStateListGrid();
		initCustomerListGrid();
		
		
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
		
		fm.AdjustReason.value = "";
		fm.AdjustReasonName.value = "";
		fm.AdjustRemark.value = "";
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
			document.getElementById("blackConform").style.display="";	
			document.getElementById("blackRelase").style.display="";	
		}else if(mMode=="1"){
			document.getElementById("blackConform").style.display="none";	
			document.getElementById("blackRelase").style.display="none";	
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

function initCustomerListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="RgtNo";
		iArray[i][1]="60px";
		iArray[i][2]=60;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="60px";
		iArray[i][2]=60;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա�";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="90px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="State";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ���ǰ״̬";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		CustomerListGrid = new MulLineEnter("fm","CustomerListGrid");
		CustomerListGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		CustomerListGrid.displayTitle = 1;
		CustomerListGrid.locked = 0;
		CustomerListGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		CustomerListGrid.selBoxEventFuncName ="showCustomerTrace"; //��Ӧ�ĺ���������������    		
		CustomerListGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		CustomerListGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		CustomerListGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ������������б�񱨴�!");
	}
}

function initCustomerStateListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����ԭ��";
		iArray[i][1]="120px";
		iArray[i][2]=180;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ԭ������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ�����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����ʱ��";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		CustomerStateListGrid = new MulLineEnter("fm","CustomerStateListGrid");
		CustomerStateListGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		CustomerStateListGrid.displayTitle = 1;
		CustomerStateListGrid.locked = 0;
		CustomerStateListGrid.canSel = 0;//��ѡ��ť��1��ʾ��0����
		CustomerStateListGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		CustomerStateListGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		CustomerStateListGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ������������б�񱨴�!");
	}
}

</script>
