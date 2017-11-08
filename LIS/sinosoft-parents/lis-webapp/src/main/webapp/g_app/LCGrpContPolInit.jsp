<%
/***************************************************************
 * <p>ProName��LCContPolInit.jsp</p>
 * <p>Title���µ�¼��</p>
 * <p>Description���µ�¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-28
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
		getPolicyInfo();
		initAgentDetailGrid();
		initBusinessAreaGrid();
		initAgentComGrid();
		initSalerInfoGrid();
		initIDInfoGrid();
		initTooContectGrid();
		initRiskGrid();
		initEntry();
		initEntry1();
		initEntry2();
		initEntry3();
		initEntry4();
		initEntry5();
		initEntry6();
		initEntry7();
		initEntry8();
		//initEntry9();
		initEntry10();
		initEntry11();
		initEntry12();
		queryRisk();
		showInfoEngin();
		//showRelaInfo();
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
//		if(scantype==1||scantype==2) {//�ж��Ƿ��涯
//			setFocus();
//		}
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��¼��ؼ�
 */
function initInpBox() {
	try {
		fm.GrpPropNo.value = tGrpPropNo;
		fm.SaleDepart.value = "G";
		fm.SaleDepartName.value = "����";
		if(nullToEmpty(tContPlanType)==''){
			var tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_app.LCContSql");
			tSQLInfo.setSqlId("LCContSql20");
			tSQLInfo.addSubPara(tGrpPropNo);
			tSQLInfo.addSubPara(tGrpPropNo);
			var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			if(tPropEntry==null||tPropEntry==''){
				alert("�����жϱ�������!");
				return false;
			}else{
				tContPlanType=tPropEntry[0][0];
			}
		}
		if(tContPlanType=='00'){
			pricingmode.style.display='none';
			divBasicInfo.style.display='none';
			EngTable.style.display='none';
			divEnginFactor.style.display='none';
			InsuredTable.style.display='none';
			PremTable.style.display='none';
		}else if(tContPlanType=='01'){
			pricingmode.style.display='none';
		}else if(tContPlanType=='02'){
			EngTable.style.display='none';
			divBasicInfo.style.display='none';
			divEnginFactor.style.display='none';
			InsuredTable.style.display='none';
			PremTable.style.display='none';
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
		if(tFlag=='0'){
			fm.Approve.style.display='none';
			fm.GrpCont.style.display='';
			fm.SaveCont.style.display='';
			fm.Question.style.display='';
		}else if(tFlag=='1'){
			fm.Approve.style.display='';
			fm.GrpCont.style.display='none';
			fm.SaveCont.style.display='none';
			fm.Question.style.display='';
			fm.ClaimDuty.style.display='';
		}else if(tFlag=='3'){
			fm.Question.style.display='none';
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
//�ͻ�����
function initAgentDetailGrid() {
	
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
		iArray[i][0] = "�ͻ��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryManager";
		iArray[i++][9]="�ͻ��������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "�ͻ���������";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryManager";
		iArray[i++][9]="�ͻ���������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ӷ����";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="Ӷ�����|notnull&num&>0";
		
		AgentDetailGrid = new MulLineEnter("fm", "AgentDetailGrid");
		AgentDetailGrid.mulLineCount = 0;
		AgentDetailGrid.displayTitle = 1;
		AgentDetailGrid.locked = 0;
		AgentDetailGrid.canSel = 0;
		AgentDetailGrid.canChk = 0;
		AgentDetailGrid.hiddenSubtraction = 0;
		AgentDetailGrid.hiddenPlus = 0;
		AgentDetailGrid.selBoxEventFuncName = "";
		AgentDetailGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������(01)!");
	}
}
//�н����
function initAgentComGrid() {
	
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
		iArray[i][0] = "�н��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="�н��������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "�н��������";
		iArray[i][1] = "20px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryAgentCom";
		iArray[i++][9]="�н��������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "�н�����˴���";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "querySalerInfo";
		iArray[i++][9]="";
		
		iArray[i] = new Array();
		iArray[i][0] = "�н����������";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i++][7] = "querySalerInfo";
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		AgentComGrid = new MulLineEnter("fm", "AgentComGrid");
		AgentComGrid.mulLineCount = 0;
		AgentComGrid.displayTitle = 1;
		AgentComGrid.locked = 0;
		AgentComGrid.canSel = 0;
		AgentComGrid.canChk = 0;
		AgentComGrid.hiddenSubtraction = 0;
		AgentComGrid.hiddenPlus = 0;
		AgentComGrid.selBoxEventFuncName = "";
		AgentComGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������(02)!");
	}
}

//������
function initSalerInfoGrid() {
	
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
		iArray[i][0] = "�����˴���";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i++][7] = "querySaler";
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "0px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ӷ����";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="Ӷ�����|notnull&num&>0";
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "17px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		SalerInfoGrid = new MulLineEnter("fm", "SalerInfoGrid");
		SalerInfoGrid.mulLineCount = 0;
		SalerInfoGrid.displayTitle = 1;
		SalerInfoGrid.locked = 0;
		SalerInfoGrid.canSel = 0;
		SalerInfoGrid.canChk = 0;
		SalerInfoGrid.hiddenSubtraction = 0;
		SalerInfoGrid.hiddenPlus = 0;
		SalerInfoGrid.selBoxEventFuncName = "";
		SalerInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������(03)!");
	}
}
function initBusinessAreaGrid() {
	
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
		iArray[i][0] = "�����ش���";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][4]="conditioncomcode";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1"; 
		iArray[i][9]="�����ش���|notnull";
		iArray[i][15]="1";
		iArray[i++][16]="1 and comgrade=#03#";
		
		iArray[i] = new Array();
		iArray[i][0] = "����������";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][4]="conditioncomcode";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1"; 
		iArray[i][9]="�����ش���|notnull";
		iArray[i][15]="1";
		iArray[i++][16]="1 and comgrade=#03#";
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="��������|notnull&num";
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ؿͻ��������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i][3] = 0;
		iArray[i][7] = "queryMComManager";
		iArray[i++][9]="�����ؿͻ��������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "�����ؿͻ���������";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 2;
		iArray[i][7] = "queryMComManager";
		iArray[i++][9]="�����ؿͻ��������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "��Ӷ����";
		iArray[i][1] = "8px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="Ӷ�����|notnull&num";
		
		BusinessAreaGrid = new MulLineEnter("fm", "BusinessAreaGrid");
		BusinessAreaGrid.mulLineCount = 0;
		BusinessAreaGrid.displayTitle = 1;
		BusinessAreaGrid.locked = 0;
		BusinessAreaGrid.canSel = 0;
		BusinessAreaGrid.canChk = 0;
		BusinessAreaGrid.hiddenSubtraction = 0;
		BusinessAreaGrid.hiddenPlus = 0;
		BusinessAreaGrid.selBoxEventFuncName = "";
		BusinessAreaGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������(04)!");
	}
}
//��λ֤��
function initIDInfoGrid() {
	
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
		iArray[i][0] = "֤�����ͱ���";
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 0;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i++][9]="֤�����ͱ���|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����������";
		iArray[i][1] = "20px";
		iArray[i][2] = 10;
		iArray[i][3] = 2;
		iArray[i][4]="gidtype";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";  
		iArray[i++][9]="֤�����ͱ���|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤������";
		iArray[i][1] = "20px";
		iArray[i][2] = 25;
		iArray[i][3] = 1;
		iArray[i++][9]="֤������|notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Ч����";
		iArray[i][1] = "15px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Ч����|date";
		
		iArray[i] = new Array();
		iArray[i][0] = "֤����Чֹ��";
		iArray[i][1] = "15px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9]="֤����Чֹ��|date";
		
		IDInfoGrid = new MulLineEnter("fm", "IDInfoGrid");
		IDInfoGrid.mulLineCount = 0;
		IDInfoGrid.displayTitle = 1;
		IDInfoGrid.locked = 0;
		IDInfoGrid.canSel = 0;
		IDInfoGrid.canChk = 0;
		IDInfoGrid.hiddenSubtraction = 0;
		IDInfoGrid.hiddenPlus = 0;
		IDInfoGrid.selBoxEventFuncName = "";
		IDInfoGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
function initTooContectGrid() {

	turnPage6 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i] = new Array();
		iArray[i][0] = "���";
		iArray[i][1] = "30px";
		iArray[i][2] = 10;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ������";
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ������|notnull&len<20";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ�˵绰";
		iArray[i][1] = "15px";
		iArray[i][2] = 15;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ�˵绰|PHONE&notnull";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��EMAIL";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ��EMAIL|EMAIL";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ���ֻ���";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i][3] = 1;
		iArray[i++][9]="��ϵ���ֻ���|NUM&len<=11";
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ�˲���";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤������";
		iArray[i][1] = "10px";
		iArray[i][2] = 10;
		iArray[i][3] = 2;
		iArray[i][4]="idtype";
		iArray[i][5]="6";
		iArray[i++][6]="0";  
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤������";
		iArray[i][1] = "15px";
		iArray[i][2] = 20;
		iArray[i++][3] = 1;
		
		iArray[i] = new Array();
		iArray[i][0] = "��ϵ��֤����Чֹ��";
		iArray[i][1] = "13px";
		iArray[i][2] = 30;
		iArray[i++][3] = 1;
		
		TooContectGrid = new MulLineEnter("fm", "TooContectGrid");
		TooContectGrid.mulLineCount = 0;
		TooContectGrid.displayTitle = 1;
		TooContectGrid.locked = 0;
		TooContectGrid.canSel = 0;
		TooContectGrid.canChk = 0;
		TooContectGrid.hiddenSubtraction = 0;
		TooContectGrid.hiddenPlus = 0;
		TooContectGrid.selBoxEventFuncName = "";
		TooContectGrid.loadMulLine(iArray);
	} catch (ex) {
		alert("��ʼ���������!");
	}
}
//����
function initRiskGrid(){
	turnPage7 = new turnPageClass();
	
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
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "��������";
		iArray[i][1] = "30px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "�ɷ�����";
		iArray[i][1] = "15px";
		iArray[i][2] = 30;
		iArray[i++][3] = 3;
		
		iArray[i] = new Array();
		iArray[i][0] = "Ͷ������";
		iArray[i][1] = "10px";
		iArray[i][2] = 30;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "10px";
		iArray[i][2] = 200;
		iArray[i++][3] = 0;
		
		iArray[i] = new Array();
		iArray[i][0] = "����";
		iArray[i][1] = "10px";
		iArray[i][2] = 200;
		iArray[i++][3] = 0;
		
		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
		RiskGrid.mulLineCount = 0;   
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =0;
		RiskGrid.chkBoxEventFuncName = "showBonusFlag";
		RiskGrid. hiddenPlus=1;
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);  
	} catch (ex) {
		alert("��ʼ���������!");
	}
 }
</script>
