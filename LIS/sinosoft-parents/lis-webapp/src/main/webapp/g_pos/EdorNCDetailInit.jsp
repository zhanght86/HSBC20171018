<%
/***************************************************************
 * <p>ProName��LCContPlanDetailInit.jsp</p>
 * <p>Title��������Ϣ¼��</p>
 * <p>Description��������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-05-14
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
		
		initPlanDetailInfoGrid();
		initPubAmntRelaPlanGrid();
		initPubAmntRelaDutyGrid();
		initPayFeeGrid();
		initTZFeeGrid();
		
		queryPlanDetail();
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		tContPlanType = getContPlanType(tPolicyNo);
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
		divInfoButton.style.display = "none";//�����޸�ɾ��
		divInfoButton2.style.display = "none";//�����������
		divInfoButton3.style.display = "none";//�ɷ�����Ϣ
		divInfoButton4.style.display = "none";//Ͷ���˻���Ϣ
		if(tQueryFlag=="2"){
			divInfoButton.style.display = "";
			divInfoButton2.style.display = "";
			divInfoButton3.style.display = "";
			divInfoButton4.style.display = "none";
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
 * ѯ�۷�����ϸ��Ϣ
 */
function initPlanDetailInfoGrid() {

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
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
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
		iArray[i][0] = "���Ѽ��㷽ʽ";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���Ѽ��㷽ʽ";
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
		iArray[i][0] = "ְҵ����Ǳ���";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ�����";//OccupTypeFlag
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������ͱ���";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�̶�����(Ԫ)";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��н����";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��߱���(Ԫ)";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��ͱ���";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��������(Ԫ)/��������/�����ۿ�";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�ο�����(Ԫ)/�ο�����";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "�ۿ���";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��ʼ����(Ԫ)";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		if (tContPlanType=="00" || tContPlanType=="03") {//��ͨ����,��������
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="01") {//������
			
			iArray[i][1] = "0px";
			iArray[i][2] = 300;
			iArray[i++][3] = 3;
		} else if (tContPlanType=="02") {//�˻���
			
			iArray[i][1] = "40px";
			iArray[i][2] = 300;
			iArray[i++][3] = 0;
		}
		
		iArray[i] = new Array();
		iArray[i][0] = "��ע";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		PlanDetailInfoGrid = new MulLineEnter("fm2", "PlanDetailInfoGrid");
		PlanDetailInfoGrid.mulLineCount = 0;
		PlanDetailInfoGrid.displayTitle = 1;
		PlanDetailInfoGrid.locked = 1;
		PlanDetailInfoGrid.canSel = 1;
		PlanDetailInfoGrid.canChk = 0;
		PlanDetailInfoGrid.hiddenSubtraction = 1;
		PlanDetailInfoGrid.hiddenPlus = 1;
		PlanDetailInfoGrid.selBoxEventFuncName = "showPlanDetailInfo";
		PlanDetailInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * ������������޶�
 */
function initPubAmntRelaPlanGrid() {

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
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "120px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ��ѱ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޶�(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;

		PubAmntRelaPlanGrid = new MulLineEnter("fm2", "PubAmntRelaPlanGrid");
		PubAmntRelaPlanGrid.mulLineCount = 0;
		PubAmntRelaPlanGrid.displayTitle = 1;
		PubAmntRelaPlanGrid.locked = 1;
		PubAmntRelaPlanGrid.canSel = 1;
		PubAmntRelaPlanGrid.canChk = 0;
		PubAmntRelaPlanGrid.hiddenSubtraction = 1;
		PubAmntRelaPlanGrid.hiddenPlus = 1;
		PubAmntRelaPlanGrid.selBoxEventFuncName = "showRelaDuty";
		PubAmntRelaPlanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}

/**
 * �������������޶�
 */
function initPubAmntRelaDutyGrid() {

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
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���α���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ�ʹ�ù����������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ�ʹ�ù�������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i][3] = 2;
		iArray[i][4] = "trueflag";
		iArray[i][5] = "6|5";
		iArray[i][6] = "1|0";
		iArray[i++][19] = "1";
		
		iArray[i] = new Array();
		iArray[i][0] = "�����޶�(Ԫ)";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;

		PubAmntRelaDutyGrid = new MulLineEnter("fm2", "PubAmntRelaDutyGrid");
		PubAmntRelaDutyGrid.mulLineCount = 0;
		PubAmntRelaDutyGrid.displayTitle = 1;
		PubAmntRelaDutyGrid.locked = 1;
		PubAmntRelaDutyGrid.canSel = 0;
		PubAmntRelaDutyGrid.canChk = 0;
		PubAmntRelaDutyGrid.hiddenSubtraction = 1;
		PubAmntRelaDutyGrid.hiddenPlus = 1;
		//PubAmntRelaDutyGrid.selBoxEventFuncName = "";
		PubAmntRelaDutyGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initPayFeeGrid() {
	
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
		iArray[i][0] = "���α���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷѱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�����";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "�ɷѽ�Ԫ��";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		PayFeeGrid = new MulLineEnter("fm2", "PayFeeGrid");
		PayFeeGrid.mulLineCount = 1;
		PayFeeGrid.displayTitle = 1;
		PayFeeGrid.locked = 0;
		PayFeeGrid.canSel = 1;
		PayFeeGrid.canChk = 0;
		PayFeeGrid.hiddenSubtraction = 1;
		PayFeeGrid.hiddenPlus = 1;
		PayFeeGrid.selBoxEventFuncName = "showTZInfo";
		//PayFeeGrid.addEventFuncName = "showTZInfo";
		PayFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initTZFeeGrid() {
	
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
		iArray[i][0] = "�ɷѱ���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����˻�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ���˻�����";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;

		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʽ�Ԫ��";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ�ʷ������";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "ϵͳ��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ֱ���";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;

		TZFeeGrid = new MulLineEnter("fm2", "TZFeeGrid");
		TZFeeGrid.mulLineCount = 2;
		TZFeeGrid.displayTitle = 1;
		TZFeeGrid.locked = 0;
		TZFeeGrid.canSel = 0;
		TZFeeGrid.canChk = 0;
		TZFeeGrid.hiddenSubtraction = 1;
		TZFeeGrid.hiddenPlus = 1;
		TZFeeGrid.addEventFuncName = "";
		TZFeeGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
