<%
/***************************************************************
 * <p>ProName��LCinsuredQueryInit.jsp</p>
 * <p>Title�������˲�ѯ</p>
 * <p>Description�������˲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initCustomerGrid();
		initInpBox();
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ���������
 */
function initOtherParam() {

	try {
	} catch (ex) {
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
		
		var mFlag = fm.Flag.value;
		var mGrpPropNo = fm.GrpPropNo.value;
		var mInsuredName = fm.InsuredName.value;
		if (mInsuredName!=null &&mInsuredName!='') {
			if(mFlag =='01'){
				queryCustomer();
			}else if(mFlag =='02'){
				queryMainCustomer();
			}
			
		}

		
		
		
		
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
 * ��ʼ���ͻ����б�
 */
function initCustomerGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array()
		iArray[i][0]="�Ա�";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array()
		iArray[i][0]="�Ա����";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="֤�����ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�����˿ͻ���";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�����˱�����";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
		CustomerGrid.mulLineCount = 0;
		CustomerGrid.displayTitle = 1;
		CustomerGrid.canSel=1;
		CustomerGrid.canChk=0;
		CustomerGrid.hiddenPlus = 1;
		CustomerGrid.hiddenSubtraction = 1;
		CustomerGrid.selBoxEventFuncName = "";
		CustomerGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��CustomerGridʱ����"+ ex);
	}
}
 
</script>
