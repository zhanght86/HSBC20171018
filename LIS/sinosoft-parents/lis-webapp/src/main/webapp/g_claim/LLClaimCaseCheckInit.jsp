<%
/***************************************************************
 * <p>ProName��LLClaimCaseCheckInit.jsp</p>
 * <p>Title���ⰸ����¼���ʼ��</p>
 * <p>Description���ⰸ����¼���ʼ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
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

		//�ѹ����¼���������Ϣ
		initOnEventDutyListGrid();
		//�������¼���������Ϣ
		initOffEventDutyListGrid();
		//�¼��⸶��Ϣ�б�
		initCaseDutyPayGrid();
		//�ͻ���Ϣ�б�
		initCustomerListGrid();
		//�¼���Ϣ�б�
		initAccidentListGrid();
		//������ʷ�⸶��Ϣ
		initPayInfoListGrid();
		
		if (mGrpRegisterNo!=null && mGrpRegisterNo!="" && mGrpRegisterNo!="null") {
			
			queryAcceptInfo();		
			queryCustomerList();			
			//showSelectCustomer();
			
			if (fm.CaseType.value!="02") {
				
				fm.BPOCheckInfo.style.display = "none";
				fm.QueryAllBill.style.display = "none";
				fm.QuestionQuery.style.display = "none";
				
				if (fm.CaseType.value!="01") {
					conditionCode = "1 and (codealias=#"+fm.CaseType.value+"# or codealias is null) ";
				} else {
					conditionCode = "1 and (codealias=#00# or codealias is null)";
				}
				
			} else {

				conditionCode = "1 and (codealias=#00# or codealias is null)";
			}			
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
		
		if (mMode==1) {
			fm.CaseConfirm.style.display = "none";
			fm.BatchCaseConfirm.style.display = "none";
		}
		
		initAcceptInfo();		
		initCustomerInfo();
		initCaseInfo();
		initPayInfo();
		initCaseReviewInfo();
		initChkConClusion();
		
	} catch (re) {
		alert("��ʼ����������");
	}
}
/**
 * ��ʼ��������Ϣ
 */
function initAcceptInfo() {
		
		fm.GrpRgtNo.value = mGrpRegisterNo;
		
		fm.AppntName.value = "";
		fm.PageNo.value = "";
		fm.ConfirmDate.value = "";
		fm.ConfirmOperator.value = "";		
		fm.AcceptCom.value = "";
		fm.AcceptComName.value = "";
				
		fm.SumRealPay.value = "";
		fm.CaseType.value = "";
		fm.CaseTypeName.value = "";
		fm.PayType.value = "02";//01-�ŵ�������02-���˸���
		fm.PayType.checked = false;
}
/**
 * ��ʼ���ͻ���Ϣ
 */
function initCustomerInfo() {
	
	fm.BankName.value = "";		
	fm.BankProvince.value = "";	
	fm.BankCity.value = "";
	fm.AccNo.value = "";
	fm.AccName.value = "";
	fm.OldClmNo.value = "";

}
/**
 * ��ʼ���������¼���Ϣ
 */
function initCaseInfo() {

	mFlag = "";
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
	fm.TreatResult.value = "";
	fm.TreatResultName.value = "";
	fm.CaseSource.value = "";
	fm.CaseSourceName.value = "";
	fm.LRCaseNo.value = "";

	fm.AccidentPlace.value = "";
	fm.AccidentRemarks.value = "";
	fm.Remarks.value = "";
	
	for (var i=0;i<fm.ClaimType.length;i++) {
		
		fm.ClaimType[i].checked = false;
	}
	
	var tCloseInfo = document.getElementById("divCloseAccident");
	tCloseInfo.style.display="none";
	fm.CloseReasonDesc.value = "";
	fm.CloseRemarkDesc.value = "";
	
	//$("#divPayModify").css("display","none");	
}
/**
 * ��ʼ�������⸶��Ϣ
 */
function initPayInfo() {
	
	fm.GiveType.value = "";
	fm.GiveTypeName.value = "";
	fm.AdjReason.value = "";
	fm.AdjReasonName.value = "";
	fm.RealPay.value = "";
	fm.NoGiveReason.value = "";
	fm.NoGiveReasonName.value = "";
	fm.SpecialRemark.value = "";
	fm.AdjRemark.value = "";		
}
/**
 * ��ʼ�����˽�����Ϣ
 */
function initCaseReviewInfo() {
	
	fm.Conclusion.value = "";
	fm.ConclusionName.value = "";
	fm.ReviewAdvice.value = "";
	fm.AgainReviewAdvice.value = "";	
}
/**
 * ��ʼ�����˽���
 */
function initChkConClusion() {
	
	fm.ChkConclusion.value = "";
	fm.ChkConclusionName.value = "";
	fm.ChkAdvice.value = "";
	fm.ChkAdviceName.value = "";	
	fm.CheckAdvice.value = "";
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
		
		fm.BatchCaseConfirm.style.display = "none";
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
//�ͻ���Ϣ�б�
function initCustomerListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="28px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="120px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="CustomerNo";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="����";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Gender";
		iArray[i][1]="0px";
		iArray[i][2]=60;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�Ա�";
		iArray[i][1]="40px";
		iArray[i][2]=40;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="IDType";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="80px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="֤������";
		iArray[i][1]="120px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="Ա����";
		iArray[i][1]="70px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="50px";
		iArray[i][2]=60;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�⸶���";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʊ��";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�ش���Ʊ��";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ȫ��־";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="¼������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����״̬";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������ʶ";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		           
    
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
        alert("ex");
    }
}

//�ͻ������⸶��Ϣ
function initPayInfoListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="28px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��λ����";
		iArray[i][1]="200px";
		iArray[i][2]=140;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="200px";
		iArray[i][2]=140;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="�⸶���";
		iArray[i][1]="180px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�⸶����";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�⸶�¼���";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=0;	
    
		PayInfoListGrid = new MulLineEnter( "fm" , "PayInfoListGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    PayInfoListGrid.mulLineCount = 0;   
	    PayInfoListGrid.displayTitle = 1;
	    PayInfoListGrid.locked = 0;
	    PayInfoListGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��   
	    PayInfoListGrid.hiddenPlus=1;
	    PayInfoListGrid.hiddenSubtraction=1;
	    PayInfoListGrid.loadMulLine(iArray);     
    }
    catch(ex){
        alert("ex");
    }
}

// �¼���Ϣ�б�
function initAccidentListGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
			iArray[i]=new Array();
			iArray[i][0]="���";
			iArray[i][1]="30px";
			iArray[i][2]=30;
			iArray[i++][3]=0;
		
			iArray[i]=new Array();
			iArray[i][0]="�¼���";
			iArray[i][1]="80px";
			iArray[i][2]=120;
			iArray[i++][3]=0;
			      
			iArray[i]=new Array();
			iArray[i][0]="����ԭ��";
			iArray[i][1]="60px";
			iArray[i][2]=60;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��������";
			iArray[i][1]="80px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="ҽԺ�ȼ�";
			iArray[i][1]="60px";
			iArray[i][2]=60;
			iArray[i++][3]=0;
			
			
			iArray[i]=new Array();
			iArray[i][0]="���շ���";
			iArray[i][1]="60px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�����������˹�ϵ";
			iArray[i][1]="80px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��������������";
			iArray[i][1]="80px";
			iArray[i][2]=100;
			iArray[i++][3]=0;
		
			iArray[i]=new Array();
			iArray[i][0]="������";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��˽��";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�⸶���";
			iArray[i][1]="50px";	
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�����־";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
		  	iArray[i]=new Array();
			iArray[i][0]="casestate";
			iArray[i][1]="0px";
			iArray[i][2]=80;
			iArray[i++][3]=3;			
			
		  	iArray[i]=new Array();
			iArray[i][0]="�¼�״̬";
			iArray[i][1]="50px";
			iArray[i][2]=80;
			iArray[i++][3]=0;
			
			AccidentListGrid = new MulLineEnter("fm","AccidentListGrid");
			AccidentListGrid.mulLineCount = 0;
			AccidentListGrid.displayTitle = 1;
			AccidentListGrid.locked = 0;
			AccidentListGrid.canSel =1;
			AccidentListGrid.selBoxEventFuncName ="showSelectCase";
			AccidentListGrid.selBoxEventFuncParm ="1";
			AccidentListGrid.hiddenPlus=1;
			AccidentListGrid.hiddenSubtraction=1;
			AccidentListGrid.loadMulLine(iArray); 

    } catch(ex){
        alert("��ʼ��������Ϣ����->AccidentListGrid");
    }
}

// �¼��ѹ���������Ϣ�б�
function initOnEventDutyListGrid() {
	       
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
				
		OnEventDutyListGrid = new MulLineEnter("fm","OnEventDutyListGrid");
		OnEventDutyListGrid.mulLineCount = 0;
		OnEventDutyListGrid.displayTitle = 1;
		OnEventDutyListGrid.locked = 0;
		OnEventDutyListGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		OnEventDutyListGrid.canChk= 1;
		//OnEventDutyListGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������
		//OnEventDutyListGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
		OnEventDutyListGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		OnEventDutyListGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		OnEventDutyListGrid.loadMulLine(iArray);

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->CaseDutyInfoGrid");
    }
}

// �¼���������Ϣ�б�
function initOffEventDutyListGrid() {
	
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
			
		OffEventDutyListGrid = new MulLineEnter("fm","OffEventDutyListGrid");
		OffEventDutyListGrid.mulLineCount = 0;
		OffEventDutyListGrid.displayTitle = 1;
		OffEventDutyListGrid.locked = 0;
		OffEventDutyListGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		OffEventDutyListGrid.canChk= 1;
		//OffEventDutyListGrid.selBoxEventFuncName ="showCaseDetail"; //��Ӧ�ĺ���������������
		//OffEventDutyListGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
		OffEventDutyListGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		OffEventDutyListGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		OffEventDutyListGrid.loadMulLine(iArray); 
    }
    catch(ex){
			alert("��ʼ��������Ϣ����->NoCaseDutyInfoGrid");
    }
}

// �¼��⸶��Ϣ
function initCaseDutyPayGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=30;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="100px";
		iArray[i][2]=100;
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
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="DutyCode";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GettDutyCode";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GetDutyKind";
		iArray[i][1]="0px";
		iArray[i][2]=120;
		iArray[i++][3]=3;			
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=120;
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
		iArray[i][0]="��˽��";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�⸶���";
		iArray[i][1]="50px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ʹ�ù�������";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����󱣶�";
		iArray[i][1]="60px";
		iArray[i][2]=80;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="GiveType";
		iArray[i][1]="0px";
		iArray[i][2]=150;
		iArray[i++][3]=3;						
		     
		iArray[i]=new Array();
		iArray[i][0]="�⸶����";
		iArray[i][1]="50px";
		iArray[i][2]=150;
		iArray[i++][3]=0;			
		
		CaseDutyPayGrid = new MulLineEnter("fm","CaseDutyPayGrid");
		CaseDutyPayGrid.mulLineCount = 0;
		CaseDutyPayGrid.displayTitle = 1;
		CaseDutyPayGrid.locked = 0;
		CaseDutyPayGrid.canSel =1;
		CaseDutyPayGrid.hiddenPlus=1;
		CaseDutyPayGrid.selBoxEventFuncName ="showAjustInfo"; //��Ӧ�ĺ���������������			
		CaseDutyPayGrid.hiddenSubtraction=1;
		CaseDutyPayGrid.loadMulLine(iArray); 

    }
    catch(ex){
        alert("��ʼ��������Ϣ����->CaseDutyPayGrid");
    }
}
</script>
