<%
/***************************************************************
 * <p>ProName��LCCoinsuranceInit.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-03
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
		
		initCoinsuranceGrid();
		queryCoinsuranceInfo();
		
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
		fm.MasterSlaveFlag.value = "";
		fm.MasterSlaveName.value = "";
		fm.CoinComCode.value = "";
		fm.CoinComName.value = "";
		fm.AmntShareRate.value = "";
		fm.PremShareRate.value = "";
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if(tFlag=='0'){
			divButton.style.display = "";
		}else if(tFlag=='1'){
			divButton.style.display='none';
		}else if(tFlag=='3'){
			divButton.style.display='none';
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

/**
 * ��������
 */
function initCoinsuranceGrid() {
	
	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������/�ӷ���ʶ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������/�ӷ���ʶ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������˾����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������˾";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����̯����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CoinsuranceGrid = new MulLineEnter("fm", "CoinsuranceGrid");
		CoinsuranceGrid.mulLineCount = 0;
		CoinsuranceGrid.displayTitle = 1;
		CoinsuranceGrid.locked = 1;
		CoinsuranceGrid.canSel = 1;
		CoinsuranceGrid.canChk = 0;
		CoinsuranceGrid.hiddenPlus = 1;
		CoinsuranceGrid.hiddenSubtraction = 1;
		CoinsuranceGrid.selBoxEventFuncName = "showCoinInfo";
		CoinsuranceGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}

</script>
