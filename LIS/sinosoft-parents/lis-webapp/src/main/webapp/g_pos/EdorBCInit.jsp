<%
/***************************************************************
 * <p>ProName:EdorBCInput.jsp</p>
 * <p>Title:  ������ά��</p>
 * <p>Description:������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>

<%@page pageEncoding="GBK" contentType="text/html;charset=GBK"%>
<script language="JavaScript">

/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
 		initButton();
		initInpBox();
		initParam();
		initOldInsuredInfoGrid();
		initUpdateInsuredInfoGrid();
		initBnfUpdateInfoGrid();
		queryUpdateClick(2);
		

	} catch (re) {
		alert("��ʼ���������!");
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
		
		if(tActivityID=='1800401001'){
			divShowButton.style.display='';
			divQueryOld.style.display='';
			fm.BatchNo.value = tEdorAppNo;
		}else {
			divShowButton.style.display='none';
			divQueryOld.style.display='none';
			fm.BatchNo.value = "";
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

function initOldInsuredInfoGrid() {
	
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
		iArray[i][0] = "���˱�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���շ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���շ�������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������Ч����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������˿ͻ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OldInsuredInfoGrid = new MulLineEnter("fm", "OldInsuredInfoGrid");
		OldInsuredInfoGrid.mulLineCount = 0;
		OldInsuredInfoGrid.displayTitle = 1;
		OldInsuredInfoGrid.locked = 0;
		OldInsuredInfoGrid.canSel = 1;
		OldInsuredInfoGrid.canChk = 0;
		OldInsuredInfoGrid.hiddenSubtraction = 1;
		OldInsuredInfoGrid.hiddenPlus = 1;
		OldInsuredInfoGrid.selBoxEventFuncName = "showOldBnfList";
		OldInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}


function initUpdateInsuredInfoGrid() {
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ч����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "У��״̬";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "У��������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		UpdateInsuredInfoGrid = new MulLineEnter("fm", "UpdateInsuredInfoGrid");
		UpdateInsuredInfoGrid.mulLineCount = 0;
		UpdateInsuredInfoGrid.displayTitle = 1;
		UpdateInsuredInfoGrid.locked = 1;
		UpdateInsuredInfoGrid.canSel = 1;
		UpdateInsuredInfoGrid.canChk = 0;
		UpdateInsuredInfoGrid.hiddenSubtraction = 1;
		UpdateInsuredInfoGrid.hiddenPlus = 1;
		UpdateInsuredInfoGrid.selBoxEventFuncName = "showUpdateInsuredList";
		UpdateInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
* �޸ĺ���������Ϣ
*/

function initBnfUpdateInfoGrid(){
	
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
		iArray[i][0] = "���������";
		iArray[i][1] = "25px";
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
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="bnforder";
		iArray[i][5] = "3|4";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "������˳�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="������˳��|code:bnforder";
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="����|len<=30";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�������Ա�";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="sex";
		iArray[i][5] = "6|7";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������Ա����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="�Ա�|code:sex";	

		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "25px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9] = "��������|DATE";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5] = "9|10";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="֤������|code:idtype";	
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|len<=20";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="relation";
		iArray[i][5] = "12|13";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i][3] = 3;
		iArray[i++][9]="�뱻�����˹�ϵ|code:relation";	
	
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="�������|len<=6&DECIMAL";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="headbank";
		iArray[i][5] = "15|16";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������б���";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="������|len<=25";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�˻�";
		iArray[i][1] = "35px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="�˻�|len<=25";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ʡ";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4]="province";
		iArray[i][5] = "19|20";
		iArray[i][6] = "1|0";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ʡ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "city";
		iArray[i][5] = "21|22";
		iArray[i][6] = "1|0";
		iArray[i][15] = "upplacename";
		iArray[i][17] = "20";
		iArray[i++][19] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "���������б���";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ֻ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i][3] = 1;
		iArray[i++][9]="�ֻ���|num&len<=11";//У��
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˸��˱�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˿ͻ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;

		BnfUpdateInfoGrid = new MulLineEnter("fm", "BnfUpdateInfoGrid");
		BnfUpdateInfoGrid.mulLineCount = 0;
		BnfUpdateInfoGrid.displayTitle = 1;
		BnfUpdateInfoGrid.locked = 0;
		BnfUpdateInfoGrid.canSel = 0;
		BnfUpdateInfoGrid.canChk = 0;
		BnfUpdateInfoGrid.hiddenSubtraction = 0;
		BnfUpdateInfoGrid.hiddenPlus = 0;
		BnfUpdateInfoGrid.selBoxEventFuncName = "";
		BnfUpdateInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
