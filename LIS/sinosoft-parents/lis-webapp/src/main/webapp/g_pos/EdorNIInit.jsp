<%
/***************************************************************
 * <p>ProName��EdorNIInit.jsp</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-11
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initEdorDetailGrid();
		initQueryInfoGrid();
		initBnfGrid();
		initAmntGrid();
		initPayPlanGrid();
		initInvestGrid();
		initInpBox();
		queryInsured(2);
		
	
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
		
		clearPage();
		if(tActivityID=='1800401001'){
			divButton01.style.display="";
			divInsuredInfo2.style.display="";
			fm.BatchNo.value = tEdorAppNo;
		}else{
			divButton01.style.display="none";
			divButton02.style.display="";
			divInsuredInfo2.style.display="none";
			fm.BatchNo.value = "";
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

function initEdorDetailGrid(){
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������˿ͻ���";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���˱�����";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա����";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա�";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤�����ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���շ�������";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���շ���";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="����(Ԫ)";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		if(tActivityID=="1800401001"){
			iArray[i++][3]=3;
		}else{
			iArray[i++][3]=0;
		}
		
		iArray[i]=new Array();
		iArray[i][0]="��ȫ��Ч����";
		iArray[i][1]="40px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������κ�";
		iArray[i][1] = "80px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ϵ";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 100;
		iArray[i++][3] = 3;
		
		EdorDetailGrid = new MulLineEnter("fm", "EdorDetailGrid");
		EdorDetailGrid.mulLineCount = 0;
		EdorDetailGrid.displayTitle = 1;
		EdorDetailGrid.locked = 1;
		EdorDetailGrid.canSel = 1;
		EdorDetailGrid.canChk = 1;
		EdorDetailGrid.hiddenSubtraction = 1;
		EdorDetailGrid.hiddenPlus = 1;
		EdorDetailGrid.selBoxEventFuncName = "getCustomerInfo";
		EdorDetailGrid.loadMulLine(iArray);
		
		} catch (ex) {
			alert("��ʼ���������!");
		}
	}
	

function initBnfGrid(){
	
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "���������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnftype";
		iArray[i][5] = "1|2";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="���������|code:bnftype&NOTNULL";
		
		iArray[i] = new Array();
		iArray[i][0] = "������˳��";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "3|4";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "������˳�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="������˳��|code:bnforder";

		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] =1;
		iArray[i++][9]="����|len<=20";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "6|7";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="�Ա�|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "��������|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "9|10";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="֤������|code:idtype";	

		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|len<=20";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[i][4]="relation";
		iArray[i][5] = "12|13";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i][3]=3;		
		iArray[i++][9]="�뱻���˹�ϵ|code:relation";
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "�������|FLOAT&NOTNULL";
				
		BnfGrid = new MulLineEnter("fm", "BnfGrid");
		BnfGrid.mulLineCount = 0;
		BnfGrid.displayTitle = 1;
		BnfGrid.locked = 0;
		BnfGrid.canSel = 0;
		BnfGrid.canChk = 0;
		BnfGrid.hiddenSubtraction = 0;
		BnfGrid.hiddenPlus = 0;
		BnfGrid.selBoxEventFuncName = ""; 
		BnfGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
	
}

function initQueryInfoGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������˹�ϵ";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "����������������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���շ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		QueryInfoGrid = new MulLineEnter("fm", "QueryInfoGrid");
		QueryInfoGrid.mulLineCount = 0;
		QueryInfoGrid.displayTitle = 1;
		QueryInfoGrid.locked = 1;
		QueryInfoGrid.canSel = 0;
		QueryInfoGrid.canChk = 0;
		QueryInfoGrid.hiddenSubtraction = 1;
		QueryInfoGrid.hiddenPlus = 1;
		
		QueryInfoGrid.selBoxEventFuncName = "";
		QueryInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

// �ǹ̶�����
function initAmntGrid() {

	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��߱���(Ԫ)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ͱ���(Ԫ)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����(Ԫ)";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="����|notnull&num";
		
		AmntGrid = new MulLineEnter("fm", "AmntGrid");
		AmntGrid.mulLineCount = 0;
		AmntGrid.displayTitle = 1;
		AmntGrid.locked = 1;
		AmntGrid.canSel = 0;
		AmntGrid.canChk = 0;
		AmntGrid.hiddenSubtraction = 1;
		AmntGrid.hiddenPlus = 1;
		
		AmntGrid.selBoxEventFuncName = "";
		AmntGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initPayPlanGrid() {
	
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���νɷ�ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷѱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�ɷѽ�Ԫ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		PayPlanGrid = new MulLineEnter("fm", "PayPlanGrid");
		PayPlanGrid.mulLineCount = 0;
		PayPlanGrid.displayTitle = 1;
		PayPlanGrid.locked = 1;
		PayPlanGrid.canSel = 1;
		PayPlanGrid.canChk = 0;
		PayPlanGrid.hiddenSubtraction = 1;
		PayPlanGrid.hiddenPlus = 1;
		PayPlanGrid.selBoxEventFuncName = "queryInvest";
		PayPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initInvestGrid() {
	
	turnPage6 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���νɷ�ID";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ���˻�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ���˻�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʽ�Ԫ��";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʷ������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		InvestGrid = new MulLineEnter("fm", "InvestGrid");
		InvestGrid.mulLineCount = 0;
		InvestGrid.displayTitle = 1;
		InvestGrid.locked = 1;
		InvestGrid.canSel = 0;
		InvestGrid.canChk = 0;
		InvestGrid.hiddenSubtraction = 1;
		InvestGrid.hiddenPlus = 1;
		InvestGrid.selBoxEventFuncName = ""; 
		InvestGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
