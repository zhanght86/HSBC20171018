<%
/***************************************************************
 * <p>ProName��LLCLaimModApplyInit.jsp</p>
 * <p>Title�������޸ķֹ�˾�����������</p>
 * <p>Description�������޸ķֹ�˾�����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ����
 * @version  : 8.0
 * @date     : 2015-03-11
 ****************************************************************/
%>
<script language="JavaScript">
 var conditionCode = "1";
/**
 * ��ʼ������
 */

function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();
		
		initQueryModItemGrid();
		initNotGrpGrid();
		initAcceptGrpGrid();
		initAcceptGrpPlanGrid();
		initPlanGrid();
		

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
		
		document.all("UpAdjustRuleTitle").style.display = "none";
		document.all("UpAdjustRuleInput").style.display = "none";
		document.all("UpAdjustRateTitle").style.display = "none";
		document.all("UpAdjustRateInput").style.display = "none";
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
//�����޸��б�
function initQueryModItemGrid() {

	turnPage1 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸�ԭ�����";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸�ԭ��";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������ͱ���";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���ֱ���";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="130px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ�����������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���ϵ�������";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸���Ч����";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�����޸���Чֹ��";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������ԭ��";
		iArray[i][1]="0px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������κ�";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���˽���";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
			
		QueryModItemGrid = new MulLineEnter("fm", "QueryModItemGrid");
		QueryModItemGrid.mulLineCount = 0;
		QueryModItemGrid.displayTitle = 1;
    	QueryModItemGrid.locked = 0;
		QueryModItemGrid.canSel = 1;
		QueryModItemGrid.hiddenPlus=1;
		QueryModItemGrid.hiddenSubtraction=1;
		QueryModItemGrid.selBoxEventFuncName = "showClmModApplyInfo";
		QueryModItemGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}
//δѡ�񱣵���Ϣ�б�
function initNotGrpGrid(){
    
	turnPage2 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ч����";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Чֹ��";
		iArray[i][1]="70px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		NotGrpGrid = new MulLineEnter("fm", "NotGrpGrid");
		NotGrpGrid.mulLineCount = 0;
		NotGrpGrid.displayTitle = 1;
		NotGrpGrid.locked = 0;
		NotGrpGrid.canChk = 1;		
		NotGrpGrid.hiddenPlus=1;
		NotGrpGrid.hiddenSubtraction=1;
		NotGrpGrid.selBoxEventFuncName = "";
		NotGrpGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}

//��ѡ�񱣵���Ϣ�б�
function initAcceptGrpGrid(){
    
	turnPage3 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ч����";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���κ�";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		

		
		AcceptGrpGrid = new MulLineEnter("fm", "AcceptGrpGrid");
		AcceptGrpGrid.mulLineCount = 0;
		AcceptGrpGrid.displayTitle = 1;
		AcceptGrpGrid.locked = 0;
		AcceptGrpGrid.canChk = 1;		
		AcceptGrpGrid.hiddenPlus=1;
		AcceptGrpGrid.hiddenSubtraction=1;
		AcceptGrpGrid.selBoxEventFuncName = "";
		AcceptGrpGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}

//��ѡ�񱣵���Ϣ�б�
function initAcceptGrpPlanGrid(){
    
	turnPage4 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�б�����";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ͷ��������";
		iArray[i][1]="200px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ч����";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="���κ�";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="50px";
		iArray[i][2]=200;
		iArray[i++][3]=3;
		

		
		AcceptGrpPlanGrid = new MulLineEnter("fm", "AcceptGrpPlanGrid");
		AcceptGrpPlanGrid.mulLineCount = 0;
		AcceptGrpPlanGrid.displayTitle = 1;
		AcceptGrpPlanGrid.locked = 0;
		AcceptGrpPlanGrid.canSel = 1;
		AcceptGrpPlanGrid.canChk = 1;
		AcceptGrpPlanGrid.hiddenPlus=1;		
		AcceptGrpPlanGrid.hiddenSubtraction=1;
		AcceptGrpPlanGrid.selBoxEventFuncName = "queryPlan";
		AcceptGrpPlanGrid.loadMulLine(iArray);
		
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}

//������Ϣ
function initPlanGrid(){
        
	turnPage5 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ϵͳ��������";
		iArray[i][1]="0px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ƿ񱣴�";
		iArray[i][1]="60px";
		iArray[i][2]=200;
		iArray[i++][3]=0;
				
		PlanGrid = new MulLineEnter("fm", "PlanGrid");
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.locked = 0;
		PlanGrid.canSel = 0;
		PlanGrid.canChk = 0;
		PlanGrid.hiddenPlus=1;
		PlanGrid.hiddenSubtraction=0;
		PlanGrid.selBoxEventFuncName = "";
		PlanGrid.loadMulLine(iArray);
	} catch(ex) {
		alert("��ʼ���������!");
	}  
}
</script>
