<%
/***************************************************************
 * <p>ProName��LLClaimCustomerInit.jsp</p>
 * <p>Title��ϵͳ����Ͷ���˲�ѯ</p>
 * <p>Description��ϵͳ����Ͷ���˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-04-17
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
//��ʼ���������б�
function initCustomerListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
		iArray[i][1]="30px";         			//�п�                                                     
		iArray[i][2]=10;          			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                               
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ����λ����";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ����λ";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ���";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;            
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";                                                
		iArray[i][1]="120px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="Gender";                                                
		iArray[i][1]="180px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�Ա�";                                                
		iArray[i][1]="60px";                                             
		iArray[i][2]=180;
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;      
		             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="Ա����";                                                
		iArray[i][1]="80px";                                             
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="IDType";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ч����";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Чֹ��";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ֻ���";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;		
		      
		CustomerListGrid = new MulLineEnter("fm", "CustomerListGrid");
		CustomerListGrid.mulLineCount = 0;
		CustomerListGrid.displayTitle = 1;
		CustomerListGrid.locked = 1;
		CustomerListGrid.canSel = 1;
		CustomerListGrid.canChk = 0;
		CustomerListGrid.hiddenSubtraction = 1;
		CustomerListGrid.hiddenPlus = 1;
		CustomerListGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}	
	
</script>
