<%
/***************************************************************
 * <p>ProName��LJMoidfyBankConfirmInit.jsp</p>
 * <p>Title: �ͻ�������Ϣ�޸����</p>
 * <p>Description���ͻ�������Ϣ�޸����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-08-02
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
		
		initVirtualGrid();
		initModifyBankInfoGrid();
		initHisBankInfoGrid();
	} catch(ex) {
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

function initVirtualGrid() {
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ֶ�";
		iArray[i][1] = (window.document.body.clientWidth-30)+"px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		VirtualGrid = new MulLineEnter("fm", "VirtualGrid");
		VirtualGrid.mulLineCount = 0;
		VirtualGrid.displayTitle = 1;
		VirtualGrid.locked = 0;
		VirtualGrid.canSel = 1;
		VirtualGrid.canChk = 0;
		VirtualGrid.hiddenSubtraction = 1;
		VirtualGrid.hiddenPlus = 1;
		//TempFeeInfoGrid.selBoxEventFuncName = "";
		VirtualGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initModifyBankInfoGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ��������";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "ҵ������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ���";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������˿ͻ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȡ������";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȡ��֤����";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿����б���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿���������";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿�����ʡ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿�����ʡ";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿������б���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿�������";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿������˺�";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��¿����л���";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ҵ������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�޸�ԭ��";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʧ��ԭ��";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ModifyBankInfoGrid = new MulLineEnter("fm", "ModifyBankInfoGrid");
		ModifyBankInfoGrid.mulLineCount = 0;//��ʼ���������Ժ��޸�Ϊ0
		ModifyBankInfoGrid.displayTitle = 1;
		ModifyBankInfoGrid.locked = 0;
		ModifyBankInfoGrid.canSel = 1;
		ModifyBankInfoGrid.canChk = 0;
		ModifyBankInfoGrid.hiddenSubtraction = 1;
		ModifyBankInfoGrid.hiddenPlus = 1;
		ModifyBankInfoGrid.selBoxEventFuncName = "showInfo";
		ModifyBankInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initHisBankInfoGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ�������";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ�����������ʡ";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ�������������";
		iArray[i][1] = "105px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ������˺�";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��˻���";
		iArray[i][1] = "90px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		HisBankInfoGrid = new MulLineEnter("fm", "HisBankInfoGrid");
		HisBankInfoGrid.mulLineCount = 0;//��ʼ���������Ժ��޸�Ϊ0
		HisBankInfoGrid.displayTitle = 1;
		HisBankInfoGrid.locked = 0;
		HisBankInfoGrid.canSel = 0;
		HisBankInfoGrid.canChk = 0;
		HisBankInfoGrid.hiddenSubtraction = 1;
		HisBankInfoGrid.hiddenPlus = 1;
		//HisBankInfoGrid.selBoxEventFuncName = "showInfo";
		HisBankInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
