<%
/***************************************************************
 * <p>ProName��PreCustomerManageInit.jsp</p>
 * <p>Title��׼�ͻ�ά������</p>
 * <p>Description��׼�ͻ�ά������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-14
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
		initSalerGrid();
		
		if (tFlag=="1") {
			
			fm.AddButton.style.display = "";
			fm.ModifyButton.style.display = "none";
			fm.DeleteButton.style.display = "none";
			fm.ModifyTraceButton.style.display = "none";
			divAppQuot.style.display = "none";
		} else if (tFlag=="2") {
			
			queryDetail();
			
			fm.AddButton.style.display = "none";
			fm.ModifyButton.style.display = "";
			fm.DeleteButton.style.display = "";
			fm.ModifyTraceButton.style.display = "";
			divAppQuot.style.display = "";
		}
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
 * ��ʼ����������б�
 */
function initSalerGrid() {
	
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
		iArray[i][0] = "�ͻ��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i++][7] = "queryManager";
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ���������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��֧����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		SalerGrid = new MulLineEnter("fm", "SalerGrid");
		SalerGrid.mulLineCount = 0;
		SalerGrid.displayTitle = 1;
		SalerGrid.locked = 0;
		SalerGrid.canSel = 0;
		SalerGrid.canChk = 0;
		SalerGrid.hiddenPlus = 0;
		SalerGrid.hiddenSubtraction = 0;
		//SalerGrid.selBoxEventFuncName = "";
		SalerGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��SalerGridʱ����"+ ex);
	}
}
</script>
