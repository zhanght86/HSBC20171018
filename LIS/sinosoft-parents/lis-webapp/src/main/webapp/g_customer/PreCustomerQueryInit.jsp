<%
/***************************************************************
 * <p>ProName��PreCustomerQueryInit.jsp</p>
 * <p>Title��׼�ͻ�ά����ѯ����</p>
 * <p>Description��׼�ͻ�ά����ѯ����</p>
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
		initPreCustomerGrid();
		
		if (tFlag=="1") {
			
			fm.AddButton.style.display = "";
			fm.QueryButton.style.display = "";
		} else if (tFlag=="2") {
			
			fm.AddButton.style.display = "none";
			fm.QueryButton.style.display = "";
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
function initPreCustomerGrid() {
	
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
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "׼�ͻ�����";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��λ����";
		iArray[i][1] = "55px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҵ����";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ԥ��Ͷ��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ԥ�Ʊ��ѹ�ģ(Ԫ)";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ�б�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PreCustomerGrid = new MulLineEnter("fm", "PreCustomerGrid");
		PreCustomerGrid.mulLineCount = 0;
		PreCustomerGrid.displayTitle = 1;
		PreCustomerGrid.locked = 1;
		PreCustomerGrid.canSel = 1;
		PreCustomerGrid.canChk = 0;
		PreCustomerGrid.hiddenPlus = 1;
		PreCustomerGrid.hiddenSubtraction = 1;
		PreCustomerGrid.selBoxEventFuncName = "GotoDetail";
		PreCustomerGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��PreCustomerGridʱ����"+ ex);
	}
}
</script>
