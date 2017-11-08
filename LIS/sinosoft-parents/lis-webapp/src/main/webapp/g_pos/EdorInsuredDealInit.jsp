<%
/***************************************************************
 * <p>ProName��EdorInsuredDealInit.jsp</p>
 * <p>Title����ȫ��Ա�嵥����</p>
 * <p>Description����ȫ��Ա�嵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-16
 ****************************************************************/
%>
<script language="JavaScript">
var tContPlanType = "";//00-��ͨ������01-�����գ�02-�˻������֣�03-��������
var tUnFixedAmntFlag = false;//�ǹ̶������ǣ�false-��true-��
/**
 * ��ʼ������
 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
		tSQLInfo.setSqlId("EdorInsuredDealSql6");
		tSQLInfo.addSubPara(tEdorAppNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			
			tContPlanType = strQueryResult[0][0];
		}
		
		//��ͨ���֡��������жϱ��շ������Ƿ񺬷ǹ̶�����
		if (tContPlanType=="00" || tContPlanType=="01") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorInsuredDealSql");
			tSQLInfo.setSqlId("EdorInsuredDealSql7");
			tSQLInfo.addSubPara(tEdorAppNo);
			
			var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if (strQueryResult != null && strQueryResult[0][0]=="1") {
				
				tUnFixedAmntFlag = true;
				
				temp1.style.display = "none";
				temp2.style.display = "";
				temp3.style.display = "none";
			} else {
				
				temp1.style.display = "";
				temp2.style.display = "none";
				temp3.style.display = "none";
			}
		} else if (tContPlanType=="02") {
			
			temp1.style.display = "none";
			temp2.style.display = "none";
			temp3.style.display = "";
		}
		
		initInsuredListGrid();
		initBatchGrid();
		queryBatchInfo();
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
function initInsuredListGrid() {
	
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
		iArray[i][0] = "��ˮ��";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ŀ";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������";
		iArray[i][1] = "28px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ȫ��Ч����";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "У��״̬";
		iArray[i][1] = "25px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "У��������";
		iArray[i][1] = "100px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		InsuredListGrid = new MulLineEnter("fm", "InsuredListGrid");
		InsuredListGrid.mulLineCount = 0;
		InsuredListGrid.displayTitle = 1;
		InsuredListGrid.locked = 0;
		InsuredListGrid.canSel = 0;
		InsuredListGrid.canChk = 1;
		InsuredListGrid.hiddenPlus = 1;
		InsuredListGrid.hiddenSubtraction = 1;
		InsuredListGrid.selBoxEventFuncName = "";
		InsuredListGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��InsuredListGridʱ����"+ ex);
	}
}

/**
 * ��ʼ���б�
 */
function initBatchGrid() {
	
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
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ʱ��";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		BatchGrid = new MulLineEnter("fmupload", "BatchGrid");
		BatchGrid.mulLineCount = 0;
		BatchGrid.displayTitle = 1;
		BatchGrid.locked = 0;
		BatchGrid.canSel = 1;
		BatchGrid.canChk = 0;
		BatchGrid.hiddenPlus = 1;
		BatchGrid.hiddenSubtraction = 1;
		BatchGrid.selBoxEventFuncName = "";
		BatchGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��BatchGridʱ����"+ ex);
	}
}
</script>
