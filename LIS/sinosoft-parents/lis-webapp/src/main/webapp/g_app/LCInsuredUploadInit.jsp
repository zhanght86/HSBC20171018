<%
/***************************************************************
 * <p>ProName��LCInsuredUploadInit.jsp</p>
 * <p>Title����Ա�嵥����</p>
 * <p>Description����Ա�嵥����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-05-27
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
		tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
		tSQLInfo.setSqlId("LCInsuredUploadSql6");
		tSQLInfo.addSubPara(tGrpPropNo);
		
		var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (strQueryResult != null) {
			
			tContPlanType = strQueryResult[0][0];
		}
		
		//��ͨ���֡��������жϱ��շ������Ƿ񺬷ǹ̶�����
		if (tContPlanType=="00" || tContPlanType=="01") {
			
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCInsuredUploadSql");
			tSQLInfo.setSqlId("LCInsuredUploadSql7");
			tSQLInfo.addSubPara(tGrpPropNo);
			
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
		
		initBatchGrid();
		initBatchDetailGrid();
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
function initBatchGrid() {
	
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
		iArray[i][0] = "����ID";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "���κ�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɹ�����";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ʧ������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����ʱ��";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		BatchGrid = new MulLineEnter("fm", "BatchGrid");
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

/**
 * ��ʼ���б�
 */
function initBatchDetailGrid() {
	
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
		iArray[i][0] = "���κ�";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "20px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "30px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "10px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ƿ���ɹ�";
		iArray[i][1] = "15px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���������Ϣ";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		BatchDetailGrid = new MulLineEnter("fm", "BatchDetailGrid");
		BatchDetailGrid.mulLineCount = 0;
		BatchDetailGrid.displayTitle = 1;
		BatchDetailGrid.locked = 0;
		BatchDetailGrid.canSel = 0;
		BatchDetailGrid.canChk = 0;
		BatchDetailGrid.hiddenPlus = 1;
		BatchDetailGrid.hiddenSubtraction = 1;
		BatchDetailGrid.selBoxEventFuncName = "";
		BatchDetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ��BatchDetailGridʱ����"+ ex);
	}
}
</script>
