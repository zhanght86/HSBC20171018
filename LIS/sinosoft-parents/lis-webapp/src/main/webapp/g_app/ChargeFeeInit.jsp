<%
/***************************************************************
 * <p>ProName��ChargeFeeInit.jsp</p>
 * <p>Title���н�������ά��</p>
 * <p>Description���н�������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
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
		
		initParam();
		initInpBox();
		initButton();
		initFeeRateInfoGrid();
		initZJGrid();
		queryPropInfo();
		buttonShowState();
		
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
		fm.GrpPropNo.value=tGrpPropNo;
		if(tState=='1'){
			fm.FeeRateSaveButton.style.display='none';
		}else{
			fm.FeeRateSaveButton.style.display='';
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
 * ��null���ַ���תΪ��
 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}

function initFeeRateInfoGrid() {
	
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
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ο������ѱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������������ѱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		FeeRateInfoGrid = new MulLineEnter("fm", "FeeRateInfoGrid");
		FeeRateInfoGrid.mulLineCount = 0;
		FeeRateInfoGrid.displayTitle = 1;
		FeeRateInfoGrid.locked = 0;
		FeeRateInfoGrid.canSel = 1;
		FeeRateInfoGrid.canChk = 0;
		FeeRateInfoGrid.hiddenSubtraction = 1;
		FeeRateInfoGrid.hiddenPlus = 1;
		FeeRateInfoGrid.selBoxEventFuncName = "queryAgentInfo";
		FeeRateInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initZJGrid() {
	
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
		iArray[i][0] = "�н��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ο������ѱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н����������ѱ���";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][9] = "�н����������ѱ���|NUM";
		
		ZJGrid = new MulLineEnter("fm", "ZJGrid");
		ZJGrid.mulLineCount = 0;
		ZJGrid.displayTitle = 1;
		ZJGrid.locked = 0;
		ZJGrid.canSel = 0;
		ZJGrid.canChk = 0;
		ZJGrid.hiddenSubtraction = 1;
		ZJGrid.hiddenPlus = 1;
		ZJGrid.selBoxEventFuncName = "";
		ZJGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
 
