<%
/***************************************************************
 * <p>ProName��LSQuotProjDetailViewInit.jsp</p>
 * <p>Title����ϸ�鿴(��Ŀѯ��)</p>
 * <p>Description����ϸ�鿴(��Ŀѯ��)</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-22
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
		
		tTranProdType = getProdType(tQuotNo, tQuotBatNo);
		
		initAppOrgCodeGrid();//���û���
		initAgencyNameGrid();//�н�����
		initLinkInquiryNoGrid();//����ѯ�ۺ�
		initPlanInfoGrid();	
		initQuotStep1();//��ʼ����һ��ѯ����Ϣ
		initQuotStep2();//��ʼ���ڶ���ѯ����Ϣ
		queryFinalConclu();
		
	} catch (re) {
		alert("��ʼ���������1��");
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
 * ���û�������
 */
function initAppOrgCodeGrid() {
	
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
		iArray[i][0] = "���û�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���û�������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AppOrgCodeGrid = new MulLineEnter("fm2", "AppOrgCodeGrid");
		AppOrgCodeGrid.mulLineCount = 0;
		AppOrgCodeGrid.displayTitle = 1;
		AppOrgCodeGrid.locked = 0;
		AppOrgCodeGrid.canSel = 0;
		AppOrgCodeGrid.canChk = 0;
		AppOrgCodeGrid.hiddenSubtraction = 1;
		AppOrgCodeGrid.hiddenPlus = 1;
		AppOrgCodeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������2!");
	}
}

/**
 * �н��������
 */
function initAgencyNameGrid() {
	
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
		iArray[i][0] = "�н����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�н�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		AgencyNameGrid = new MulLineEnter("fm2", "AgencyNameGrid");
		AgencyNameGrid.mulLineCount = 0;
		AgencyNameGrid.displayTitle = 1;
		AgencyNameGrid.locked = 0;
		AgencyNameGrid.canSel = 0;
		AgencyNameGrid.canChk = 0;
		AgencyNameGrid.hiddenSubtraction = 1;
		AgencyNameGrid.hiddenPlus = 1;
		AgencyNameGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������3!");
	}
}

/**
 * ����ѯ�ۺ�
 */
function initLinkInquiryNoGrid() {
	
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
		iArray[i][0] = "����ѯ�ۺ�";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		LinkInquiryNoGrid = new MulLineEnter("fm2", "LinkInquiryNoGrid");
		LinkInquiryNoGrid.mulLineCount = 0;
		LinkInquiryNoGrid.displayTitle = 1;
		LinkInquiryNoGrid.locked = 0;
		LinkInquiryNoGrid.canSel = 0;
		LinkInquiryNoGrid.canChk = 0;
		LinkInquiryNoGrid.hiddenSubtraction = 1;
		LinkInquiryNoGrid.hiddenPlus = 1;
		LinkInquiryNoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������4!");
	}
}

/**
 * ѯ�۷�����Ϣ
 */
function initPlanInfoGrid() {

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
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������ʶ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "0px";
		} else {
			iArray[i][1] = "10px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 3;
		} else {
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ڼ�";
		iArray[i][1] = "5px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ڼ䵥λ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ڼ䵥λ";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlagName
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ������";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�з���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������(��)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ������(��)";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}

		iArray[i] = new Array();
		iArray[i][0] = "����";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��˻�������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��˻�������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�μ��籣ռ��";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ů����";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѷ�̯��ʽ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ҵ����ռ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����н(Ԫ)";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ƽ����н(Ԫ)";
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i][1] = "10px";
		} else {
			iArray[i][1] = "0px";
		}
		iArray[i][2] = 300;
		if (tTranProdType=='00' || tTranProdType=='03') {//��ͨ���ֻ��������չʾ
			iArray[i++][3] = 0;
		} else {
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����˵��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ����";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		PlanInfoGrid = new MulLineEnter("fm2", "PlanInfoGrid");
		PlanInfoGrid.mulLineCount = 0;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.selBoxEventFuncName = "showPlanDetailInfo";
		PlanInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������5!");
	}
}
</script>
