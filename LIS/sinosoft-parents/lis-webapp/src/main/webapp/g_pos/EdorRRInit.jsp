<%
/***************************************************************
 * <p>ProName��EdorRRInit.jsp</p>
 * <p>Title��������ʵ����</p>
 * <p>Description��������ʵ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-27
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		initButton();
		initEdorDetailGrid();
		initQueryInfoGrid();
		initBnfGrid();
		initAmntGrid();
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
		fm.Hidrelatomain.value="00";
		

	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		
		if(tActivityID=='1800401001'){
			divButton01.style.display='';
			divButton03.style.display='';	
			divButton02.style.display='none';	
			fm.BatchNo.value=tEdorAppNo;
		}else {
			divButton02.style.display='';	
			divButton01.style.display='none';
			divButton03.style.display='none';	
			fm.BatchNo.value="";
		}
		
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
		iArray[i][1]="30px";
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
		iArray[i++][3] = 0;
		
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
		iArray[i++][9]="������˳��|code:bnforder&NOTNULL";

		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] =1;
		iArray[i++][9]="����|notnull&len<=20";//У��
		
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
		iArray[i++][9]="֤������|code:idtype&NOTNULL";	

		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|NOTNULL&len<=20";//У��
		
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
		iArray[i++][9]="�뱻���˹�ϵ|code:relation&NOTNULL";
    
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
		QueryInfoGrid.mulLineCount = 1;
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
		iArray[i++][3] = 1;
		
		AmntGrid = new MulLineEnter("fm", "AmntGrid");
		AmntGrid.mulLineCount = 1;
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

</script>
