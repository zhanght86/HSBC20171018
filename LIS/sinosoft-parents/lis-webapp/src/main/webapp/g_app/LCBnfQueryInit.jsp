<%
/***************************************************************
 * <p>ProName��LCBnfQueryInit.jsp</p>
 * <p>Title����������Ϣά��</p>
 * <p>Description����������Ϣά��</p>
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
		initButton();
		initBnfGrid();
		getBnfInfo();
	
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
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if("01"==tFlag){
			div1.style.display="none";
			div2.style.display="";
		}
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}

function initBnfGrid() {
	
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
		iArray[i][0] = "������˳��";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "1|2";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		

		iArray[i] = new Array();
		iArray[i][0] = "������˳�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="������˳��|code:bnforder&NOTNULL";

		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] =1;
		iArray[i++][9]="����|notnull&len<=20";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "4|5";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="�Ա�|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "��������|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "7|8";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 3;
		iArray[i++][9]="֤������|code:idtype&NOTNULL";	

		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|NOTNULL&len<=20";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[i][4]="relation";
		iArray[i][5] = "10|11";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;

		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3]=3;		
		iArray[i++][9]="�뱻���˹�ϵ|code:relation&NOTNULL";
    
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9] = "�������|FLOAT&NOTNULL&value>0";
				
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

</script>
