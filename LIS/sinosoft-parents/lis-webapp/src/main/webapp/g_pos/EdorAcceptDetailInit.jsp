<%
/***************************************************************
 * <p>ProName��EdorAcceptDetailInit.jsp</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
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
		initEdorTypeGrid();
		
		queryEdorAppInfo();
		if (fm.AppMode.value!="") {
			
			queryPolicyInfo();
			queryEdorTypeInfo();
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
		
		fm.PolicyNo.value = "";
		fm.AppntName.value = "";
		fm.AppDate.value = "";
		fm.ReceiveDate.value = "";
		fm.AppMode.value = "";
		fm.AppModeName.value = "";
		
		fm.EdorType.value = "";
		fm.EdorTypeName.value = "";
		fm.EdorValDate.value = "";
		fm.GetObj.value = "";
		fm.GetObjName.value = "";
		
		divEdorValDateTitle.style.display = "none";
		divEdorValDateInput.style.display = "none";
		divGetObjTitle.style.display = "none";
		divGetObjInput.style.display = "none";
		divTD1.style.display = "";
		divTD2.style.display = "";
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
function initEdorTypeGrid() {
	
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
		iArray[i][0] = "��ȫ��Ŀ����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ�㷨";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ч����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ�����ڵ�";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ���Ҫ¼����Ч����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷�֧����ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˷�֧����ʽ";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		EdorTypeGrid = new MulLineEnter("fm", "EdorTypeGrid");
		EdorTypeGrid.mulLineCount = 0;
		EdorTypeGrid.displayTitle = 1;
		EdorTypeGrid.locked = 1;
		EdorTypeGrid.canSel = 1;
		EdorTypeGrid.canChk = 0;
		EdorTypeGrid.hiddenPlus = 1;
		EdorTypeGrid.hiddenSubtraction = 1;
		EdorTypeGrid.selBoxEventFuncName = "showEdorType";
		EdorTypeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��EdorTypeGridʱ����"+ ex);
	}
}
</script>
