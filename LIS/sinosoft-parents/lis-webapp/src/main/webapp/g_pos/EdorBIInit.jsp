<%
/***************************************************************
 * <p>ProName��GEdorBIDetail.jsp</p>
 * <p>Title��������Ϣ���</p>
 * <p>Description��������Ϣ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-06-17
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
		initOldInsuredInfoGrid();
		initUpdateInsuredInfoGrid();
		initFSGrid();
		queryUpClick(2);
		
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
		
		if(tActivityID=='1800401001'){
			divButton02.style.display='';
			divQueryOld.style.display='';
			fm.BatchNo.value = tEdorAppNo;
		}else {
			divButton02.style.display='none';
			divQueryOld.style.display='none';
			fm.BatchNo.value ="";
		}
		
	} catch (ex) {
		alert("��ʼ����ť����");
	}
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
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���б���";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˺�";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������ʡ";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������ʡ����";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������������";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ֻ���";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		OldInsuredInfoGrid = new MulLineEnter("fm", "OldInsuredInfoGrid");
		OldInsuredInfoGrid.mulLineCount = 0;
		OldInsuredInfoGrid.displayTitle = 1;
		OldInsuredInfoGrid.locked = 1;
		OldInsuredInfoGrid.canSel = 1;
		OldInsuredInfoGrid.canChk = 0;
		OldInsuredInfoGrid.hiddenSubtraction = 1;
		OldInsuredInfoGrid.hiddenPlus = 1;
		OldInsuredInfoGrid.selBoxEventFuncName = "showBankInfo";
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
		UpdateInsuredInfoGrid.locked = 0;
		UpdateInsuredInfoGrid.canSel = 1;
		UpdateInsuredInfoGrid.canChk = 0;
		UpdateInsuredInfoGrid.hiddenSubtraction = 1;
		UpdateInsuredInfoGrid.hiddenPlus = 1;
		UpdateInsuredInfoGrid.selBoxEventFuncName = "showUpInfo";
		UpdateInsuredInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

function initFSGrid() {
	
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
		iArray[i][0] = "�뱻�����˹�ϵ";
		iArray[i][1] = "00px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�뱻�����˹�ϵ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "1px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������˿ͻ���";
		iArray[i][1] = "4px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		FSGrid = new MulLineEnter("fm", "FSGrid");
		FSGrid.mulLineCount = 0;
		FSGrid.displayTitle = 1;
		FSGrid.locked = 1;
		FSGrid.canSel = 0;
		FSGrid.canChk = 1;
		FSGrid.hiddenSubtraction = 1;
		FSGrid.hiddenPlus = 1;
		FSGrid.selBoxEventFuncName = "";
		FSGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
