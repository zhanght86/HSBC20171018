<%
/***************************************************************
 * <p>ProName��LCInsuredInfoInit.jsp</p>
 * <p>Title����Ա�嵥ά��</p>
 * <p>Description����Ա�嵥ά��</p>
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
		initQueryScanGrid();
		//InsuredQuery();
	
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
		
		fm.GrpPropNo.value=tGrpPropNo;
		fm.MissionID.value=tMissionID;
		fm.SubMissionID.value=tSubMissionID;
		fm.ActivityID.value=tActivityID;
		fm.Flag.value=tFlag;
		tContPlanType = getContPlanType(tGrpPropNo);
		fm.ContPlanType.value=tContPlanType;
		fm.CurrentDate.value=tCurrentDate;
		
		divButton.style.display="";
		divUplond.style.display="";
		divButton2.style.display="none";
		divConfime.style.display="";
		
		// Ͷ������Ա�嵥ά��
		if(tFlag =='00'){ 
			divConfime.style.display="none";
			divConfimeC.style.display="";	
		}else if(tFlag =='01'){
			divButton.style.display="none";
			divUplond.style.display="none";
			divButton2.style.display="";
		}
	initContPlanQuery();
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

function initQueryScanGrid() {
	
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
		iArray[i][0] = "�������˿ͻ���";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������˸��˱�����";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "�����������˹�ϵ";
		iArray[i][1] = "50px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
				
		iArray[i] = new Array();
		iArray[i][0] = "������������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�Ա�";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "40px";
		iArray[i][2] = 300;
		iArray[i++][3] =0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����";
		iArray[i][1] = "150px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "ְҵ���";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���շ���";
		iArray[i][1] = "130px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���ѣ�Ԫ��";
		iArray[i][1] = "60px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�������˱���";
		iArray[i][1] = "80px";
		iArray[i][2] = 300;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "���������˱���";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "70px";
		iArray[i][2] = 300;
		iArray[i++][3] = 3;
			
		QueryScanGrid = new MulLineEnter("fm", "QueryScanGrid");
		QueryScanGrid.mulLineCount = 0;
		QueryScanGrid.displayTitle = 1;
		QueryScanGrid.locked = 1;
		QueryScanGrid.canSel = 1;
		if(fm.Flag.value=='01'){
			QueryScanGrid.canChk = 0;
		}else{
			QueryScanGrid.canChk = 1;
		}
		QueryScanGrid.hiddenSubtraction = 1;
		QueryScanGrid.hiddenPlus = 1;
		QueryScanGrid.selBoxEventFuncName = "goToInfo"; 
		QueryScanGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
</script>
