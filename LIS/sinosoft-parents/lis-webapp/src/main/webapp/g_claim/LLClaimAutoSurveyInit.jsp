<%
/***************************************************************
 * <p>ProName��LLClaimSpotCheckInit.js</p>
 * <p>Title����ʼ��������</p>
 * <p>Description����ʼ��������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
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

		initLLClaimRuleListGrid();				
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
		
		initQueryInfo();
		initDetailInfo();
		 	
	} catch (re) {
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ����ѯ����
 */
function initQueryInfo() {
	
	fm.QueryManageCom.value = "";
	fm.QueryManageComName.value = "";
	fm.QueryRiskcode.value = "";
	fm.QueryRiskName.value = "";
	fm.QueryUWMoney.value = "";	
	fm.QueryPayMoney.value = "";
	fm.QueryStartDate.value = "";	
	fm.QueryEndDate.value = "";
}

/**
 * ��ʼ����ϸ
 */
function initDetailInfo() {
	
	fm.RuleNo.value = "";
	fm.CheckManageCom.value = "";
	fm.CheckManageComName.value = "";	
	fm.CheckRiskCode.value = "";
	fm.CheckRiskName.value = "";
	fm.CheckAppPay.value = "";
	fm.CheckClmPay.value = "";
	fm.CheckStartDate.value = "";
	fm.CheckEndDate.value = "";	
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
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
		tSQLInfo.setSqlId("LLClaimSpotCheckSql3");
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("����������ʧ�ܣ�");
			return false;
		} else {
			var tComGrade = tArr[0][0];
			if (tComGrade!=null && tComGrade=="03") {//������������ά������
				
				fm.querybutton.disabled = "true";
				fm.exportbutton.disabled = "true";
				fm.addButton.disabled = "true";
				fm.modifyButton.disabled = "true";
				fm.deleteButton.disabled = "true";
				fm.initButton.disabled = "true";
				alert("���ڷֹ�˾���ܹ�˾������ά���Զ��������");
			}
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
// ��ʼ���������б�
function initLLClaimRuleListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ������0��ʾ������    

    iArray[i]=new Array();
    iArray[i][0]="���������";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;                

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����������";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="���ֱ���"; 
    iArray[i][1]="100px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��˽��"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="�⸶���"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Чֹ��"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;              
    
    LLClaimRuleListGrid = new MulLineEnter( "fm" , "LLClaimRuleListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimRuleListGrid.mulLineCount = 0;   
    LLClaimRuleListGrid.displayTitle = 1;
    LLClaimRuleListGrid.locked = 0;
    LLClaimRuleListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimRuleListGrid.selBoxEventFuncName ="showClaimRuleDetail";
    LLClaimRuleListGrid.hiddenPlus=1;
    LLClaimRuleListGrid.hiddenSubtraction=1;
    LLClaimRuleListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->LLClaimCaseGrid");
    }
}
</script>