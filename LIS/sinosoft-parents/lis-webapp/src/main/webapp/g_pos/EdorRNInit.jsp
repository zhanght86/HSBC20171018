<%
/***************************************************************
 * <p>ProName:EdorRNInput.jsp</p>
 * <p>Title:  �����ڽɷ�</p>
 * <p>Description:�����ڽɷ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-26
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
		initPayPlanGrid();
		initInvestGrid();
		queryUpClick(2);
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
		
		if (tActivityID=='1800401001') {
			
			divModifyButton.style.display='';
			divCancelButton.style.display='';
			divPayPlanSaveButton.style.display="";
			divQueryOld.style.display='';
			fm.BatchNo.value = tEdorAppNo;
		} else {
			
			divModifyButton.style.display='none';
			divCancelButton.style.display='none';
			divPayPlanSaveButton.style.display="none";
			divQueryOld.style.display='none';
			fm.BatchNo.value ="";
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
		iArray[i][0] = "�˻����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		OldInsuredInfoGrid = new MulLineEnter("fm", "OldInsuredInfoGrid");
		OldInsuredInfoGrid.mulLineCount = 0;
		OldInsuredInfoGrid.displayTitle = 1;
		OldInsuredInfoGrid.locked = 0;
		OldInsuredInfoGrid.canSel = 0;
		OldInsuredInfoGrid.canChk = 1;
		OldInsuredInfoGrid.hiddenSubtraction = 1;
		OldInsuredInfoGrid.hiddenPlus = 1;
		OldInsuredInfoGrid.selBoxEventFuncName = "";
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
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������ID";
		iArray[i][1] = "0px";
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
		iArray[i][0] = "���νɷѽ��(Ԫ)";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "У��״̬";
		iArray[i][1] = "30px";
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
		UpdateInsuredInfoGrid.locked = 0;
		UpdateInsuredInfoGrid.canSel = 1;
		UpdateInsuredInfoGrid.canChk = 0;
		UpdateInsuredInfoGrid.hiddenSubtraction = 1;
		UpdateInsuredInfoGrid.hiddenPlus = 1;
		UpdateInsuredInfoGrid.selBoxEventFuncName = "queryPayPlan";
		UpdateInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initPayPlanGrid() {
	
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
		iArray[i][0] = "�������ֺ�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷѱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�ɷѽ�Ԫ��";
		iArray[i][1] = "30px";
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
		iArray[i][0] = "�������ֺ�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷѱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ���˻�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ���˻�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʽ�Ԫ��";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʷ������";
		iArray[i][1] = "30px";
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
