<%
/***************************************************************
 * <p>ProName��LLClaimLockAppInit.jsp</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
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

		initLockGrid();
		initAppUnLockGrid();
		
		queryAppLock();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.GrpContNo.value = "";
		fm.AppntName.value = "";
		fm.SignCom.value = "";
		fm.SignComName.value = "";
		fm.CustomerName.value = "";
		fm.IdNo.value = "";
		fm.BirthDay.value = "";
		fm.CustomerNo.value = "";	
		
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
function initLockGrid(){
	
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
		iArray[i][0]="�������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="���˱�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�ͻ���";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա�";
		iArray[i][1]="40px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="140px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������״̬";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		LockGrid = new MulLineEnter("fm","LockGrid");
		LockGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		LockGrid.displayTitle = 1;
		LockGrid.locked = 0;
		LockGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		LockGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		LockGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		//FlowStateGrid.selBoxEventFuncName = "getReportMissGrid"; //��������
		LockGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ��������Ϣ��񱨴�!");
	}
	
}

function initAppUnLockGrid(){
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������˽���";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����������";
		iArray[i][1]="220px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		AppUnLockGrid = new MulLineEnter("fm","AppUnLockGrid");
		AppUnLockGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		AppUnLockGrid.displayTitle = 1;
		AppUnLockGrid.locked = 0;
		AppUnLockGrid.canSel = 0;//��ѡ��ť��1��ʾ��0����
		AppUnLockGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		AppUnLockGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		//UnLockGrid.selBoxEventFuncName = "getReportMissGrid"; //��������
		AppUnLockGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ��������Ϣ��񱨴�!");
	}
	
}
</script>
