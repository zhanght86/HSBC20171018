<%
/***************************************************************
 * <p>ProName��LSQuotGrpSpecInit.jsp</p>
 * <p>Title���ر�Լ��</p>
 * <p>Description���ر�Լ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-01
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
		initGrpSpecInfoGrid();
		quryGrpSpec();//��ѯ�ѱ����ر�Լ��
		queryGrpSpecInfo();//��ʼ����ѯ��׼�ر�Լ��
		
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
		if (tActivityID=="0800100002" || tActivityID=="0800100003" || tActivityID=="0800100004") {
			
			fm.SaveButton.style.display = "";
			divStdGrpSpec.style.display = "";
		} else {
			
			fm.SaveButton.style.display = "none";
			divStdGrpSpec.style.display = "none";
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
 * ��ʼ���б�
 */
function initGrpSpecInfoGrid() {
	
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
		iArray[i][0] = "��׼�ر�Լ��Ҫ����Ϣ";
		iArray[i][1] = "200px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��׼�ر�Լ��Ҫ����Ϣ����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		GrpSpecInfoGrid = new MulLineEnter("fm", "GrpSpecInfoGrid");
		GrpSpecInfoGrid.mulLineCount = 0;
		GrpSpecInfoGrid.displayTitle = 1;
		GrpSpecInfoGrid.locked = 0;
		GrpSpecInfoGrid.canSel = 0;
		GrpSpecInfoGrid.canChk = 1;
		GrpSpecInfoGrid.hiddenPlus = 1;
		GrpSpecInfoGrid.hiddenSubtraction = 1;
		//GrpSpecInfoGrid.selBoxEventFuncName = "";
		GrpSpecInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��GrpSpecInfoGridʱ����"+ ex);
	}
}
</script>
