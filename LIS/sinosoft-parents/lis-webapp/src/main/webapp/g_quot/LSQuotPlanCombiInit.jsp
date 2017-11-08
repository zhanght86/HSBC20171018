<%
/***************************************************************
 * <p>ProName��LSQuotPlanCombiInit.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-02
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
		initNoPlanCombiGrid();
		initPlanListGrid();
		
		queryPlanList();//��ѯ�����б�
		queryNoPlanCombi();
		
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
		fm.CombiType.value = "";
		fm.CombiTypeName.value = "";
		fm.LimitType.value = "";
		fm.LimitTypeName.value = "";
		
	} catch (ex) {
		alert("��ʼ��¼��ؼ�����");
	}
}

/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if (tActivityID=="0800100002" || tActivityID=="0800100003") {
			
			fm.DelButton.style.display = "";
			fm.AddButton.style.display = "";
		} else {
			
			fm.DelButton.style.display = "none";
			fm.AddButton.style.display = "none";
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
 * ������ķ������
 */
function initNoPlanCombiGrid() {
	
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
		iArray[i][0] = "���1";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ʒ�Χ";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		NoPlanCombiGrid = new MulLineEnter("fm", "NoPlanCombiGrid");
		NoPlanCombiGrid.mulLineCount = 0;
		NoPlanCombiGrid.displayTitle = 1;
		NoPlanCombiGrid.locked = 1;
		NoPlanCombiGrid.canSel = 1;
		NoPlanCombiGrid.canChk = 0;
		NoPlanCombiGrid.hiddenPlus = 1;
		NoPlanCombiGrid.hiddenSubtraction = 1;
		//NoPlanCombiGrid.selBoxEventFuncName = "";
		NoPlanCombiGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initPlanListGrid() {
	
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
		iArray[i][0] = "��������";
		iArray[i][1] = "23px";
		iArray[i][2] = 300;
		iArray[i][3] = 0;
		iArray[i++][7] = "showPlanDetail";
		//iArray[i++][24] = "blue";
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;	
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;	
		
		PlanListGrid = new MulLineEnter("fm", "PlanListGrid");
		PlanListGrid.mulLineCount = 0;
		PlanListGrid.displayTitle = 1;
		PlanListGrid.locked = 1;
		PlanListGrid.canSel = 0;
		PlanListGrid.canChk = 1;
		PlanListGrid.hiddenPlus = 1;
		PlanListGrid.hiddenSubtraction = 1;
		//PlanListGrid.selBoxEventFuncName = "";
		PlanListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��ʱ����"+ ex);
	}
}
</script>
