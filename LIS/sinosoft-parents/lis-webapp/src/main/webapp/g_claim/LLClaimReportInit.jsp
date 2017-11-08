<%
/***************************************************************
 * <p>ProName��LLClaimReportInit.jsp</p>
 * <p>Title������¼�����</p>
 * <p>Description������¼�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
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

		initCustomerListGrid();
		initAccidentListGrid();
		initGrpClaimDutyGrid();
		
		queryReportInfo();
		if (fm.RptNo.value!=null && fm.RptNo.value!="") {
			
			queryReportCustomerInfo();
			var tCountNum = CustomerListGrid.mulLineCount;
			if (tCountNum>=1) {
				
			}			
			//queryReportCaseInfo();
			
		}
		
	} catch (re) {
		alert("��ʼ���������");
	}
}

/**
 * ��ʼ������
 */
function initParam() {
	
	try {
				
		initReportInfo();
		initReportCustomerInfo();
		initCustomerCaseInfo();
	} catch (re) {
		alert("��ʼ����������");
	}
}
/**
 * ��ʼ��������Ϣ
 */
function initReportInfo() {
	
	if (mRptNo==null || mRptNo=="null" || mRptNo=="") {
		fm.RptNo.value="";
		fm.ReportSave.style.display = "";
	} else {
		fm.RptNo.value = mRptNo;
		fm.ReportSave.style.display = "none";
	}	
	fm.RgtClass.value = "2";
	fm.RgtClassName.value = "�ŵ�";
	fm.AppntNo.value = "";
	fm.AppntName.value = "";
	fm.RptName.value = "";
	fm.RptPhone.value = "";
	fm.RptMode.value = "";
	fm.RptModeName.value = "";
	fm.Relation.value = "";
	fm.RelationName.value = "";
	fm.RptDate.value = mCurrentDate;
	fm.RgtFlag.value = "0";	
	fm.RgtFlagName.value = "��";	
	fm.RptInputOperator.value = mOperator;
	fm.RptInputDate.value = mCurrentDate;
	fm.RptCom.value = mManageCom;
	fm.RptComName.value = "";
	fm.RptConfOperator.value = "";
	fm.RptConfDate.value = "";	
}

/**
 * ��ʼ�������������¼���Ϣ
 */
function initReportCustomerInfo() {
	
	fm.SubRptNo.value = "";
	fm.CustomerNo.value = "";
	fm.CustomerName.value = "";
	fm.Birthday.value = "";
	fm.EmpNo.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value = "";
	fm.GenderName.value = "";
	fm.CustomerRemarks.value = "";
	fm.SelfAppntNo.value = "";
	fm.SelfAppntName.value = "";
	validCustomerInfo();
	
}

/**
 * ��ʼ���ͻ�������
 */
function disableCustomerInfo() {
		
		fm.CustomerName.disabled = true;	
		fm.Birthday.disabled = true;
		fm.EmpNo.disabled = true;
		fm.IDNo.disabled = true;
		fm.IDType.disabled = true;
		fm.IDTypeName.disabled = true;
		fm.Gender.disabled = true;
		fm.GenderName.disabled = true;				
}
/**
 * ��ʼ���ͻ�����
 */
function validCustomerInfo() {
	
		fm.CustomerName.disabled = false;	
		fm.Birthday.disabled = false;
		fm.EmpNo.disabled = false;
		fm.IDNo.disabled = false;
		fm.IDType.disabled = false;
		fm.IDTypeName.disabled = false;
		fm.Gender.disabled = false;
		fm.GenderName.disabled = false;		
}

/**
 * ��ʼ�������������¼���Ϣ
 */
function initCustomerCaseInfo() {
	
	fm.AccReason.value = "";
	fm.AccReasonName.value = "";
	fm.AccDate.value = "";
	fm.HospitalCode.value = "";
	fm.HospitalName.value = "";
	fm.ClaimPay.value = "";
	fm.AccGrade.value = "0";
	fm.AccGradeName.value = "��";
	fm.AccSite.value = "";
	for (var i=0;i<fm.ClaimType.length;i++) {
		
		fm.ClaimType[i].checked = false;
	}	
	fm.AccDesc.value = "";
	fm.CaseRemark.value = "";
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
 * mType:3-����ά����4-�ش󰸼��ϱ�
 */
function initButton() {
	
	try {
		
		if (mType==3) {
			
			fm.ReportSave.disabled = true;
			fm.ReportDelete.disabled = true;
			fm.CustomerAdd.disabled = true;
			fm.CustomerModify.disabled = true;
			fm.CustomerDelete.disabled = true;
			fm.AddCase.disabled = true;
			fm.ModifyCase.disabled = true;
			fm.DeleteCase.disabled = true;
			fm.ReportConfirm.disabled = true;
			$("#divReportConfirmButton").css("display","none");
			
		} else if (mType==4) {
			
			fm.ReportSave.disabled = true;
			fm.ReportDelete.disabled = true;
			fm.CustomerAdd.disabled = true;
			fm.CustomerModify.disabled = true;
			fm.CustomerDelete.disabled = true;
			fm.AddCase.disabled = true;
			fm.ModifyCase.disabled = true;
			fm.DeleteCase.disabled = true;
			fm.ReportConfirm.disabled = true;
			fm.BigReport.disabled = true;
			$("#divReportConfirmButton").css("display","none");
			$("#divMajorInfo").css("display","");
			initMajorInfo();	
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
// �ͻ���Ϣ�б�
function initCustomerListGrid() {
		
		turnPage1 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ�����";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="����";             
    iArray[i][1]="200px";                
    iArray[i][2]=200;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="�Ա�"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="200px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="Ա����"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��ע"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0; 
    
    iArray[i]=new Array();
    iArray[i][0]="SubRptNo"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=3;    
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		CustomerListGrid.selBoxEventFuncName ="showSelectCustomerInfo"; //��Ӧ�ĺ���������������    
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}

// �¼��б���Ϣ
function initAccidentListGrid() {

		turnPage2 = new turnPageClass();  
    var iArray = new Array();
    var i=0;
    try{
    
		iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[i]=new Array();
    iArray[i][0]="�¼���";             
    iArray[i][1]="180px";                
    iArray[i][2]=180;                 
    iArray[i++][3]=0;
    
		iArray[i]=new Array();
    iArray[i][0]="�ͻ���";             
    iArray[i][1]="120px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    
    
		iArray[i]=new Array();
    iArray[i][0]="����ԭ��";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ҽԺ"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�Ƿ��ش󰸼�"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    AccidentListGrid = new MulLineEnter( "fm" , "AccidentListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    AccidentListGrid.mulLineCount = 0;   
    AccidentListGrid.displayTitle = 1;
    AccidentListGrid.locked = 0;
    AccidentListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    AccidentListGrid.selBoxEventFuncName ="showSelReportCaseInfo"; //��Ӧ�ĺ���������������
    AccidentListGrid.hiddenPlus=1;
    AccidentListGrid.hiddenSubtraction=1;
    AccidentListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->SelfLLClaimReportGrid");
    }
}
// �¼�������Ϣ�б�
function initGrpClaimDutyGrid() {

		turnPage3 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="120px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�ȴ���"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="���ⷽʽ"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������������"; 
    iArray[i][1]="100px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���޶�"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;    
    
    GrpClaimDutyGrid = new MulLineEnter( "fm" , "GrpClaimDutyGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    GrpClaimDutyGrid.mulLineCount = 0;   
    GrpClaimDutyGrid.displayTitle = 1;
    GrpClaimDutyGrid.locked = 0;
    GrpClaimDutyGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    GrpClaimDutyGrid.hiddenPlus=1;
    GrpClaimDutyGrid.hiddenSubtraction=1;
    GrpClaimDutyGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
