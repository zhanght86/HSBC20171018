<%
/***************************************************************
 * <p>ProName��LCAgentToUserInit.jsp</p>
 * <p>Title���ͻ��������</p>
 * <p>Description���ͻ��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : caiyc
 * @version  : 8.0
 * @date     : 2014-07-29
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
		initAgentInfoGrid();
		initUserInfoGrid();
		
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
 * ��ʼ���б�
 */
function initAgentInfoGrid() {
	
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
		iArray[i][0] = "�������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ�û�����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ���������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "40px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա����";
		iArray[i][1] = "0px";
		iArray[i][2] = 10;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AgentInfoGrid = new MulLineEnter("fm", "AgentInfoGrid");
		AgentInfoGrid.mulLineCount = 0;
		AgentInfoGrid.displayTitle = 1;
		AgentInfoGrid.locked = 0;
		AgentInfoGrid.canSel = 1;
		AgentInfoGrid.canChk = 0;
		AgentInfoGrid.hiddenPlus = 1;
		AgentInfoGrid.hiddenSubtraction = 1;
		AgentInfoGrid.selBoxEventFuncName = "showHidden";
		AgentInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��AttachmentGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initUserInfoGrid() {
	
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 100;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�û�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�û�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		UserInfoGrid = new MulLineEnter("fm", "UserInfoGrid");
		UserInfoGrid.mulLineCount = 1;
		UserInfoGrid.displayTitle = 1;
		UserInfoGrid.locked = 0;
		UserInfoGrid.canSel = 1;
		UserInfoGrid.canChk = 0;
		UserInfoGrid.hiddenPlus = 1;
		UserInfoGrid.hiddenSubtraction = 1;
		UserInfoGrid.selBoxEventFuncName = "";
		UserInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��AttachmentGridʱ����"+ ex);
	}
}
</script>
