<%
/***************************************************************
 * <p>ProName��LSQuotCountProfitInit.jsp</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-29
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
		initProfitInfoGrid();
		
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
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
	/**
		if (tActivityID=="0100100001" || tActivityID=="0100100002" || tActivityID=="0100100003") {
			
			fm2.AddButton.style.display = "";
			fm2.ModButton.style.display = "";
			fm2.DelButton.style.display = "";
		} else {
			
			fm2.AddButton.style.display = "none";
			fm2.ModButton.style.display = "none";
			fm2.DelButton.style.display = "none";
		}
	*/
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
 * ��������Ϣ
 */
function initProfitInfoGrid() {
	
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
		iArray[i][0] = "��N��ĩ";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
				
		iArray[i] = new Array();
		iArray[i][0] = "�˻���ֵ";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		ProfitInfoGrid = new MulLineEnter("fm2", "ProfitInfoGrid");
		ProfitInfoGrid.mulLineCount = 0;
		ProfitInfoGrid.displayTitle = 1;
		ProfitInfoGrid.locked = 0;
		ProfitInfoGrid.canSel = 0;
		ProfitInfoGrid.canChk = 0;
		ProfitInfoGrid.hiddenSubtraction = 1;
		ProfitInfoGrid.hiddenPlus = 1;
		ProfitInfoGrid.selBoxEventFuncName = "showProfitInfo";
		ProfitInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
