<%
/***************************************************************
 * <p>ProName��UWGErrInit.jsp</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
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
		
		initInpBox();
		initUWGCErrGrid();
		initUWContErrGrid();
		initUWGErrGrid();
		initCCErrGrid();
		initCpolErrGrid();
		queryUWErr();

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
		
		divUWGErr1.style.display = 'none';
		divUWGErr2.style.display = 'none';
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.UWGErrSql");
		tSQLInfo.setSqlId("UWGErrSql9");
		tSQLInfo.addSubPara(tGrpPropNo);
		tSQLInfo.addSubPara(tGrpPropNo);
		
		var arrRrsult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if(arrRrsult != null){
			showReins.style.display = '';	
		}else {
			showReins.style.display = 'none';			
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
 * �����Զ��˱���Ϣ
 */
function initUWGCErrGrid() {
	
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
		iArray[i][0] = "�������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "�˱�˳���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����㼶";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����㼶����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�Զ��˱�����";
		iArray[i][1] = "260px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Զ��Ժ�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱����۱���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		UWGCErrGrid = new MulLineEnter("fm", "UWGCErrGrid");
		UWGCErrGrid.mulLineCount = 2;
		UWGCErrGrid.displayTitle = 1;
		UWGCErrGrid.locked = 1;
		UWGCErrGrid.canSel = 1;
		UWGCErrGrid.canChk = 0;
		UWGCErrGrid.hiddenSubtraction = 1;
		UWGCErrGrid.hiddenPlus = 1;
		UWGCErrGrid.selBoxEventFuncName = "showUWGErrGrid";
		UWGCErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ����������Ϣ
 */
function initUWContErrGrid() {
	
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
		iArray[i][0] = "����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		UWContErrGrid = new MulLineEnter("fm", "UWContErrGrid");
		UWContErrGrid.mulLineCount = 1;
		UWContErrGrid.displayTitle = 1;
		UWContErrGrid.locked = 1;
		UWContErrGrid.canSel = 0;
		UWContErrGrid.canChk = 0;
		UWContErrGrid.hiddenSubtraction = 1;
		UWContErrGrid.hiddenPlus = 1;
		UWContErrGrid.selBoxEventFuncName = "";
		UWContErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
	
		alert("��ʼ���������!");
	}
}

/**
 * ���������б���ʾ�б�������Ϣ
 */
function initUWGErrGrid() {
	
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
		iArray[i][0] = "�����˿ͻ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˸��˱�����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱�˳���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
	
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���˺˱����۱���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���˺˱�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���˺˱��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		UWGErrGrid = new MulLineEnter("fm", "UWGErrGrid");
		UWGErrGrid.mulLineCount = 0;
		UWGErrGrid.displayTitle = 1;
		UWGErrGrid.canSel=1;
		UWGErrGrid.canChk=0;
		UWGErrGrid.hiddenPlus=1;
		UWGErrGrid.hiddenSubtraction=1;
		UWGErrGrid.selBoxEventFuncName = "showInsInfo";
		UWGErrGrid.loadMulLine(iArray);
		
	} catch(ex) {
	
		alert("��ʼ���������!");
	}
}



/**
 * ���˺˱���Ϣ
 */

function initCCErrGrid() {
	
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
		iArray[i][0] = "�˱�������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "130px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Զ��˱�����";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�˱�Ȩ��";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Զ��˱�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CCErrGrid = new MulLineEnter("fm", "CCErrGrid");
		CCErrGrid.mulLineCount = 0;
		CCErrGrid.displayTitle = 1;
		CCErrGrid.canSel=0;
		CCErrGrid.canChk=0;
		CCErrGrid.hiddenPlus=1;
		CCErrGrid.hiddenSubtraction=1;
		CCErrGrid.loadMulLine(iArray);
	} catch(ex) {
	
		alert("��ʼ���������!");
	}
}


/**
 * ��������������Ϣ
 */

function initCpolErrGrid() {
	
	turnPage5 = new turnPageClass();
	var iArray = new Array();
	
	var i = 0;
	
	try {
	
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˸��˱�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���������ֺ�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ԭ����(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ԭ����(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����󱣷�(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����󱣶�(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˿ͻ���";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ٱ����۱���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		iArray[i] = new Array();
		iArray[i][0] = "�����ٱ�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		CpolErrGrid = new MulLineEnter("fm", "CpolErrGrid");
		CpolErrGrid.mulLineCount = 0;
		CpolErrGrid.displayTitle = 1;
		CpolErrGrid.canSel=0;
		CpolErrGrid.canChk=0;
		CpolErrGrid.hiddenPlus=1;
		CpolErrGrid.hiddenSubtraction=1;
		CpolErrGrid.loadMulLine(iArray);
	} catch(ex) {
	
		alert("��ʼ���������!");
	}
}
</script>
