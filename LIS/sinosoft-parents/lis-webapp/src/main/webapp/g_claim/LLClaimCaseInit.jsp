<%
/***************************************************************
 * <p>ProName��LLClaimCaseInit.js</p>
 * <p>Title�����������ʼ��</p>
 * <p>Description�����������ʼ��</p>
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
		
		initOffEventDutyListGrid();
		initOnEventDutyListGrid();
		
		initCustomerListGrid();
		initEventlistGrid();
		
		if (mGrpRegisterNo!=null && mGrpRegisterNo!="" && mGrpRegisterNo!="null") {
			
			queryAcceptInfo();
			queryCustomerList();
			//queryCustomerCaseList();
			//showSelectCustomer();				
		} else {
			fm.GrpRgtNo.value="";
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
		initAcceptInfo();
		initCustomerInfo();
		initCaseInfo();
		
	} catch (re) {		
		alert("��ʼ����������");
	}
}

/**
 * ��ʼ��������Ϣ
 */
function initAcceptInfo() {
		
		fm.GrpRgtNo.value = mGrpRegisterNo;

		fm.RgtClass.value = "2";
		fm.RgtClassName.value = "�ŵ�";
		fm.AppntNo.value = "";
		fm.AppntName.value = "";
		fm.AppDate.value = mCurrentDate;
		fm.AcceptDate.value=mCurrentDate;
		fm.PageNo.value = "";
		fm.AcceptOperator.value = mOperator;
		fm.AcceptOperatorName.value = mOperator;
		fm.AcceptCom.value = mManageCom;
		//fm.AcceptComName.value = mManageComName;
}
/**
 * ��ʼ���ͻ���Ϣ
 */
function initCustomerInfo() {
	
	validCustomerInfo();
	fm.RegisterNo.value = "";
	fm.CustomerNo.value = "";		
	fm.CustomerName.value = "";	
	fm.Birthday.value = "";
	fm.EmployeNo.value = "";
	fm.IDNo.value = "";
	fm.IDType.value = "";
	fm.IDTypeName.value = "";
	fm.Gender.value="";
	fm.GenderName.value = "";
	fm.AppAmnt.value = "";
	fm.BillCount.value = "";
	fm.ScanCount.value = "";
	fm.IsUrgent.value = "0";
	fm.IsUrgentName.value = "��";
	fm.IsOpenBillFlag.value = "0";
	fm.IsOpenBillFlagName.value = "��";
	fm.IsBackBill.value = "0";
	fm.IsBackBillName.value = "��";
	fm.BankCode.value = "";
	fm.BankCodeName.value = "";
	fm.Province.value = "";
	fm.ProvinceName.value = "";
	fm.City.value = "";	
	fm.CityName.value = "";	
	fm.BankAccNo.value = "";	
	fm.AccName.value = "";
	fm.CustomerAppDate.value = "";
	fm.Remark.value = "";
	fm.RegisterNo.value = "";
	fm.SelfAppntNo.value = "";
	fm.TelPhone.value = "";	
}
/**
 * ��ʼ���ͻ�������
 */
function disableCustomerInfo() {
		
		fm.CustomerName.disabled = true;	
		fm.Birthday.disabled = true;
		fm.EmployeNo.disabled = true;
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
		fm.EmployeNo.disabled = false;
		fm.IDNo.disabled = false;
		fm.IDType.disabled = false;
		fm.IDTypeName.disabled = false;
		fm.Gender.disabled = false;
		fm.GenderName.disabled = false;		
}
/**
 * ��ʼ���������¼���Ϣ
 */
function initCaseInfo() {
	
	fm.CaseNo.value = "";
	fm.AccReason.value = "";
	fm.AccReasonName.value = "";
	fm.AccDate.value="";
	fm.ClaimPay.value = "";
	fm.HospitalCode.value = "";
	fm.HospitalName.value = "";
	fm.AccResult1.value = "";
	fm.AccResult1Name.value = "";
	fm.AccResult2.value = "";
	fm.AccResult2Name.value = "";
	fm.DeformityDate.value = "";
	fm.DeathDate.value = "";
	fm.TreatResult.value = "01";
	fm.TreatResultName.value = "Ȭ��";	
	fm.CaseSource.value = "01";
	fm.CaseSourceName.value = "�ֹ�¼��";
	fm.LRCaseNo.value = "";
	
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
 */
function initButton() {
	
	try {
		fm.CustomerSave.style.display="none";
		//����ҳ����������ҳ�棬mode:0-�ɲ�����1-ֻ�鿴��2-��������
		if(mMode!=null && mMode=="1") {
			fm.CustomerSave.style.display="none";
			fm.PageNoApply.style.display="none";
			fm.AcceptSave.style.display="none";
			fm.Acceptdelete.style.display="none";
			fm.CustomerAdd.style.display="none";
			fm.CustomerModify.style.display="none";
			fm.CustomerDelete.style.display="none";
			fm.noAccept.style.display="none";
			fm.AcceptDelete.style.display="none";
			fm.CaseAdd.style.display="none";
			fm.CaseModify.style.display="none";
			fm.CaseDelete.style.display="none";
			fm.AssociateOff.style.display="none";
			fm.AssociateOn.style.display="none";
			fm.OverCase.style.display="none";
			fm.ImportCustomer.style.display="none";
		} else if (mMode!=null && mMode=="2") {
			
			 fm.AcceptSave.style.display="none";
			 fm.Acceptdelete.style.display="none";
			fm.CustomerAdd.style.display="none";
			fm.CustomerModify.style.display="none";
			fm.CustomerDelete.style.display="none";
			fm.noAccept.style.display="none";
			fm.AcceptDelete.style.display="none";
			 fm.CaseAdd.style.display="";
			fm.CaseModify.style.display="";
			 fm.CaseDelete.style.display="";
			fm.AssociateOff.style.display="";
			fm.AssociateOn.style.display="";
			 fm.OverCase.style.display="none";
			// fm.ImportCustomer.style.display="none"; 
			fm.CustomerSave.style.display="";
			//$("#CustomerListInfo").css("display","none"); 
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

// ��ʼ���ͻ���Ϣ�б�
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
    iArray[i][0]="���˰�����";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�ͻ�����";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="����";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա����";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="�Ա�";             
    iArray[i][1]="60px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="֤�����ͱ���"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="֤����������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="֤������"; 
    iArray[i][1]="120px";
    iArray[i][2]=140; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="Ա����"; 
    iArray[i][1]="80px";
    iArray[i][2]=200; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��Ʊ��"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    CustomerListGrid = new MulLineEnter( "fm" , "CustomerListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    CustomerListGrid.mulLineCount = 0;   
    CustomerListGrid.displayTitle = 1;
    CustomerListGrid.locked = 0;
    CustomerListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    CustomerListGrid.selBoxEventFuncName ="showSelectCustomer"; //��Ӧ�ĺ���������������    
    CustomerListGrid.hiddenPlus=1;
    CustomerListGrid.hiddenSubtraction=1;
    CustomerListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("��ʼ��������Ϣ����->CustomerListGrid");
    }
}

// ��ʼ���ͻ����¼���Ϣ�б�
function initEventlistGrid() {
	  
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
    iArray[i][0]="�ⰸ��";             
    iArray[i][1]="0px";                
    iArray[i][2]=200;                 
    iArray[i++][3]=3; 
    
    iArray[i]=new Array();
    iArray[i][0]="�¼���";             
    iArray[i][1]="80px";                
    iArray[i][2]=120;                 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="�ͻ���";             
    iArray[i][1]="0px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;      

    iArray[i]=new Array();
    iArray[i][0]="����ԭ��"; 
    iArray[i][1]="60px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="ҽԺ����"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;    
    
    /* iArray[i]=new Array();
    iArray[i][0]="ҽԺ�ȼ�"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="ҽԺ����"; 
    iArray[i][1]="60px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
 */
    iArray[i]=new Array();
    iArray[i][0]="������"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="��˽��"; 
    iArray[i][1]="50px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�˵���"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    EventlistGrid = new MulLineEnter( "fm" , "EventlistGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    EventlistGrid.mulLineCount = 0;   
    EventlistGrid.displayTitle = 1;
    EventlistGrid.locked = 0;
    EventlistGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    EventlistGrid.selBoxEventFuncName ="showSelectCase"; //��Ӧ�ĺ���������������
    EventlistGrid.hiddenPlus=1;
    EventlistGrid.hiddenSubtraction=1;
    EventlistGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->EventlistGrid");
    }
}

// ��ʼ���ͻ����¼��ѹ���������Ϣ�б�
function initOnEventDutyListGrid() {
	  
	  turnPage3 = new turnPageClass();    
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="150px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�ȴ���"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="0px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="���ⷽʽ"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶����"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���޶�"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    OnEventDutyListGrid = new MulLineEnter( "fm" , "OnEventDutyListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    OnEventDutyListGrid.mulLineCount = 0;   
    OnEventDutyListGrid.displayTitle = 1;
    OnEventDutyListGrid.locked = 0;
    OnEventDutyListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    OnEventDutyListGrid.canChk =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //OnEventDutyListGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    OnEventDutyListGrid.hiddenPlus=1;
    OnEventDutyListGrid.hiddenSubtraction=1;
    OnEventDutyListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->EventlistGrid");
    }
}

// ��ʼ���ͻ����¼�������������Ϣ�б�
function initOffEventDutyListGrid() {
	  
	  turnPage4 = new turnPageClass();   
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[i][1]="30px";              //�п�
    iArray[i][2]=10;                  //�����ֵ
    iArray[i++][3]=0;                 //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[i]=new Array();
    iArray[i][0]="������";             
    iArray[i][1]="100px";                
    iArray[i][2]=140;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PolNo"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="RiskCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="150px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="DutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    

    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="80px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="GetDutyCode"; 
    iArray[i][1]="0px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="����ֹ��"; 
    iArray[i][1]="60px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="�ȴ���"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="PeroidFlag"; 
    iArray[i][1]="0px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;    
    
    iArray[i]=new Array();
    iArray[i][0]="���ⷽʽ"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��������������"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="�⸶����"; 
    iArray[i][1]="40px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="��Ч����"; 
    iArray[i][1]="50px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="���޶�"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="����״̬"; 
    iArray[i][1]="40px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;                  
    
    OffEventDutyListGrid = new MulLineEnter( "fm" , "OffEventDutyListGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    OffEventDutyListGrid.mulLineCount = 0;   
    OffEventDutyListGrid.displayTitle = 1;
    OffEventDutyListGrid.locked = 0;
    OffEventDutyListGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    OffEventDutyListGrid.canChk =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    //OffEventDutyListGrid.selBoxEventFuncName ="selfLLReport"; //��Ӧ�ĺ���������������
    OffEventDutyListGrid.hiddenPlus=1;
    OffEventDutyListGrid.hiddenSubtraction=1;
    OffEventDutyListGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->EventlistGrid");
    }
}
</script>
