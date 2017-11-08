<%
/***************************************************************
 * <p>ProName��LLClaimMedicalInit.jsp</p>
 * <p>Title����ҽ���˵���ʼ��ҳ��</p>
 * <p>Description����ҽ���˵���ʼ��ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>

<script language="JavaScript">

function initForm(){
	try{

		initClinicGrid();
		initClinicBillItemGrid();
	 	InitHospBillGrid();
		initHospBillItemGrid();	
		initSpecialClinicBillGrid();
		initSpecialClinicItemGrid();		
		initSpecialHospGrid();
		initSpecialHospItemGrid();
		initParam();
		initButton();
		initDrugFeeMaintainGrid();
		initDrugFeeMaintain1Grid();
		queryClinicBillInfo();
		queryInitHospBillInfo();		
		querySpecialClinicInfo();		
		querySpecialHospInfo();		
	}catch(ex){
		alert("LLMediacalFeeInpInit.jsp-->initForm�����з����쳣:��ʼ���������!");
	}
	
}
/**
 * ��ʼ������

 */
function initParam() {
	
	try {
		
		fm.tRgtNo.value=tRgtNo;
		fm.tCustomerNo.value=tCustomerNo;
		fm.tCaseNo.value=tCaseNo;
		fm.tCaseNo1.value=tCaseNo;
		fm.tCaseNo2.value=tCaseNo;
		fm.tCaseNo3.value=tCaseNo;
		fm.tCaseNo4.value=tCaseNo;
		
		fm.CaseSource.value=tCaseSource;
		queryEasyDeductItem(ClinicBillItemGrid);
		queryEasyDeductItem(HospBillItemGrid);
		getCaseDateInfo();
		
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
		
		if(tMode==0){
			document.getElementById("addClinicBill1").style.display="";
			document.getElementById("modifyClinicBill1").style.display="";
			document.getElementById("deleteClinicBill1").style.display="";
			document.getElementById("closePage1").style.display="";
 
			document.getElementById("addHospBill2").style.display="";
			document.getElementById("modifyHospBill2").style.display="";
			document.getElementById("deleteHospBill2").style.display="";
			document.getElementById("closePage2").style.display="";

			document.getElementById("addSpeicalClinic3").style.display="";
			document.getElementById("modifySpecialClinic3").style.display="";
			document.getElementById("deleteSpecialClinic3").style.display="";
			document.getElementById("closePage3").style.display="";

			document.getElementById("addSpecialHospBill4").style.display="";
			document.getElementById("modifySpecialHospBill4").style.display="";
			document.getElementById("deleteSpecialHospBill4").style.display="";
			document.getElementById("closePage4").style.display="";
			document.getElementById("closePage5").style.display="none";

		}else if(tMode==1){
			
			document.getElementById("addClinicBill1").style.display="none";
			document.getElementById("modifyClinicBill1").style.display="none";
			document.getElementById("deleteClinicBill1").style.display="none";
			document.getElementById("closePage1").style.display="none";
					 
			document.getElementById("addHospBill2").style.display="none";
			document.getElementById("modifyHospBill2").style.display="none";
			document.getElementById("deleteHospBill2").style.display="none";
			document.getElementById("closePage2").style.display="none";
			
			document.getElementById("addSpeicalClinic3").style.display="none";
			document.getElementById("modifySpecialClinic3").style.display="none";
			document.getElementById("deleteSpecialClinic3").style.display="none";
			document.getElementById("closePage3").style.display="none";
    	
			document.getElementById("addSpecialHospBill4").style.display="none";
			document.getElementById("modifySpecialHospBill4").style.display="none";
			document.getElementById("deleteSpecialHospBill4").style.display="none";
			document.getElementById("closePage4").style.display="none";
			document.getElementById("closePage5").style.display="";
			
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
/**=========================================================================
    �����˵�¼����Ϣ
   =========================================================================
*/
function initClinicGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�¼���";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʊ��";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����ҽԺ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="����ҽԺ";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˵��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ҫ��ϱ���";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ҫ���";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����������";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="Ӱ�������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
				
		iArray[i]=new Array();
		iArray[i][0]="����˵��";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="ҽ���˵�����";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		ClinicGrid = new MulLineEnter("fm","ClinicGrid");
		ClinicGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		ClinicGrid.displayTitle = 1;
		ClinicGrid.locked = 0;
		ClinicGrid.canChk = 0;//��ѡ��ť��1��ʾ��0����
		ClinicGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		ClinicGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		ClinicGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		ClinicGrid.selBoxEventFuncName = "getClinicInfo"; //��������
		ClinicGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ�����������ϸ��Ϣ����!--ClinicGrid");
	}
}
/**
	* ҩƷ���ò�ѯ
	*/
function initDrugFeeMaintainGrid(){
	
	turnPage6 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
		iArray[i][1]="30px";         			//�п�                                                     
		iArray[i][2]=10;          			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҩƷ��ˮ��";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";    			//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҩƷ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��Ʒ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="���";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�Ը�����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�۸�";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ�����ͱ���";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
     
     iArray[i]=new Array();                                                                                       
		iArray[i][0]="�ۼ����";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=1;   
     
		DrugsGrid = new MulLineEnter("fm", "DrugsGrid");
		DrugsGrid.mulLineCount = 0;
		DrugsGrid.displayTitle = 1;
		DrugsGrid.locked = 1;
		DrugsGrid.canSel = 1;
		DrugsGrid.canChk = 0;
		DrugsGrid.hiddenSubtraction = 1;
		DrugsGrid.hiddenPlus = 1;
		DrugsGrid.selBoxEventFuncName = "showPermissionInfo";
		DrugsGrid.loadMulLine(iArray);

		}catch(ex){
			alert("��ʼ����Ϣ����");
			return false;
		}
	}
/**=========================================================================
    �����˵�������ϸ��Ϣ
   =========================================================================
*/
function initClinicBillItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ŀ���ͱ���";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ŀ����";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="200px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="��ע";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=1;		
		
		ClinicBillItemGrid = new MulLineEnter("fm","ClinicBillItemGrid");
		ClinicBillItemGrid.mulLineCount = 1;//Ĭ�ϳ�ʼ����ʾ����
		ClinicBillItemGrid.displayTitle = 1;
		ClinicBillItemGrid.locked = 0;
		ClinicBillItemGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		ClinicBillItemGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		ClinicBillItemGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		//ClinicBillItemGrid.selBoxEventFuncName = ""; //��������
		ClinicBillItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ��������ϸ��Ϣ����!--BillItemGrid");
	}
}
/**=========================================================================
    סԺ�˵�¼����Ϣ
   =========================================================================
*/
function InitHospBillGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�¼���";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ʊ��";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����ҽԺ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="����ҽԺ";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ժ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ժ����";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="סԺ����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�˵��������";
		iArray[i][1]="80px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ҫ��ϱ���";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��Ҫ���";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="����������";
		iArray[i][1]="50px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�������";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="Ӱ�������";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
				
		iArray[i]=new Array();
		iArray[i][0]="����˵��";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;	
		
	
		
		iArray[i]=new Array();
		iArray[i][0]="ҽ���˵�����";
		iArray[i][1]="120px";
		iArray[i][2]=300;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="100px";
		iArray[i][2]=300;
		iArray[i++][3]=0;		
		
		HospBillGrid = new MulLineEnter("fm","HospBillGrid");
		HospBillGrid.mulLineCount = 0;//Ĭ�ϳ�ʼ����ʾ����
		HospBillGrid.displayTitle = 1;
		HospBillGrid.locked = 0;
		HospBillGrid.canChk = 0;//��ѡ��ť��1��ʾ��0����
		HospBillGrid.canSel = 1;
		HospBillGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		HospBillGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		HospBillGrid.selBoxEventFuncName = "getHospBillInfo"; //��������
		HospBillGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ��סԺ������ϸ��Ϣ����!--HospBillGrid");
	}
}
/**
	* ҩƷ���ò�ѯ
	*/
function initDrugFeeMaintain1Grid(){
	
	turnPage7 = new turnPageClass();
	
	var iArray = new Array();
	var i = 0;
	try {
		
		iArray[i]=new Array();
		iArray[i][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
		iArray[i][1]="30px";         			//�п�                                                     
		iArray[i][2]=10;          			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҩƷ��ˮ��";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";    			//����                                                     
		iArray[i][1]="40px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";    			//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҩƷ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��Ʒ����";         		//����                                                     
		iArray[i][1]="100px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="���";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�Ը�����";         		//����                                                     
		iArray[i][1]="80px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="�۸�";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="��������";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="ҽ�����ͱ���";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������  
     
     iArray[i]=new Array();                                                                                       
		iArray[i][0]="�ۼ����";         			//����                                                     
		iArray[i][1]="60px";            			//�п�                                                     
		iArray[i][2]=100;            			//�����ֵ                                                 
		iArray[i++][3]=1;   
     
		Drugs1Grid = new MulLineEnter("fm", "Drugs1Grid");
		Drugs1Grid.mulLineCount = 0;
		Drugs1Grid.displayTitle = 1;
		Drugs1Grid.locked = 1;
		Drugs1Grid.canSel = 1;
		Drugs1Grid.canChk = 0;
		Drugs1Grid.hiddenSubtraction = 1;
		Drugs1Grid.hiddenPlus = 1;
		Drugs1Grid.selBoxEventFuncName = "showPermissionInfo";
		Drugs1Grid.loadMulLine(iArray);

		}catch(ex){
			alert("��ʼ����Ϣ����");
			return false;
		}
	}
/**=========================================================================
    סԺ�˵�������ϸ��Ϣ
   =========================================================================
*/
function initHospBillItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";//�п�
		iArray[i][2]=10;//�����ֵ
		iArray[i++][3]=0;
		
	iArray[i]=new Array();
		iArray[i][0]="������Ŀ���ͱ���";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="������Ŀ����";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="60px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="200px";
		iArray[i][2]=300;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="��ע";
		iArray[i][1]="150px";
		iArray[i][2]=300;
		iArray[i++][3]=1;		
		
		HospBillItemGrid = new MulLineEnter("fm","HospBillItemGrid");
		HospBillItemGrid.mulLineCount = 1;//Ĭ�ϳ�ʼ����ʾ����
		HospBillItemGrid.displayTitle = 1;
		HospBillItemGrid.locked = 0;
		HospBillItemGrid.canSel = 1;//��ѡ��ť��1��ʾ��0����
		HospBillItemGrid.hiddenPlus = 1;//���ţ�1���أ�0��ʾ
		HospBillItemGrid.hiddenSubtraction = 1;//���ţ�1���أ�0��ʾ
		//HospBillItemGrid.selBoxEventFuncName = ""; //��������
		HospBillItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("��ʼ��������ϸ��Ϣ����!--HospBillItemGrid");
	}
}
/**=========================================================================
    �������ﵥ֤¼����Ϣ
   =========================================================================
*/
function initSpecialClinicBillGrid() {
	
    var iArray = new Array();
    var i=0;
    try{
			iArray[i]=new Array();
			iArray[i][0]="���";
			iArray[i][1]="30px";//�п�
			iArray[i][2]=10;//�����ֵ
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�¼���";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��Ʊ��";
			iArray[i][1]="80px";
			iArray[i][2]=120;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="����ҽԺ����";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="����ҽԺ";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��������";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�˵��������";
			iArray[i][1]="80px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�۳����";
			iArray[i][1]="60px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="������";
			iArray[i][1]="60px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="��Ҫ��ϱ���";
			iArray[i][1]="50px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="��Ҫ���";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="����������";
			iArray[i][1]="50px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="�������";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
			
			iArray[i]=new Array();
			iArray[i][0]="�۳�˵��";
			iArray[i][1]="150px";
			iArray[i][2]=300;
			iArray[i++][3]=0;
				
			iArray[i]=new Array();
			iArray[i][0]="Ӱ�������";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
					
			iArray[i]=new Array();
			iArray[i][0]="����˵��";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;

			iArray[i]=new Array();
			iArray[i][0]="ҽ���˵�����";
			iArray[i][1]="120px";
			iArray[i][2]=300;
			iArray[i++][3]=3;
			
			iArray[i]=new Array();
			iArray[i][0]="��������";
			iArray[i][1]="100px";
			iArray[i][2]=300;
			iArray[i++][3]=0;			

			SpecialClinicBillGrid = new MulLineEnter("fm","SpecialClinicBillGrid");
			SpecialClinicBillGrid.mulLineCount = 0;
			SpecialClinicBillGrid.displayTitle = 1;
			SpecialClinicBillGrid.locked = 0;
			SpecialClinicBillGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
			SpecialClinicBillGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
			SpecialClinicBillGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
			SpecialClinicBillGrid.selBoxEventFuncName = "getSpecialClinicBillInfo"; //��������
			SpecialClinicBillGrid.loadMulLine(iArray);
    }
    catch(ex) {
        alert(ex);
    }
}
/**=========================================================================
    ���������˵�������ϸ��Ϣ
   =========================================================================
*/
function initSpecialClinicItemGrid(){
	
	var iArray = new Array();
	var i = 0;
	try{
		
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
			
		iArray[i]=new Array();
		iArray[i][0]="�������ʹ���";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i][3]=2;
		iArray[i][4]="llfeeitemtyp2";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][19]=1;

		iArray[i]=new Array();
		iArray[i][0]="���ý��";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�����Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�������Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;        
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="200px";
		iArray[i][2]=120;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="��ע";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=1;
		
		SpecialClinicItemGrid = new MulLineEnter("fm","SpecialClinicItemGrid");
		SpecialClinicItemGrid.mulLineCount = 1;//Ĭ�ϳ�ʼ����ʾ����
		SpecialClinicItemGrid.displayTitle = 1;
		SpecialClinicItemGrid.locked = 0;
		SpecialClinicItemGrid.canSel = 0;//��ѡ��ť��1��ʾ��0����
		SpecialClinicItemGrid.hiddenPlus = 0;//���ţ�1���أ�0��ʾ
		SpecialClinicItemGrid.hiddenSubtraction = 0;//���ţ�1���أ�0��ʾ
		//SpecialClinicItemGrid.selBoxEventFuncName = ""; //��������
		SpecialClinicItemGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("SpecialClinicItemGrid��ʼ������!");
	}
}

/**=========================================================================
    ����סԺ¼����Ϣ
   =========================================================================
*/
function initSpecialHospGrid() {
	
    var iArray = new Array();
    var i=0;
    try {
    	
		    iArray[i]=new Array();
				iArray[i][0]="���";
				iArray[i][1]="30px";//�п�
				iArray[i][2]=10;//�����ֵ
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="�¼���";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="��Ʊ��";
				iArray[i][1]="80px";
				iArray[i][2]=120;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="����ҽԺ����";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="����ҽԺ";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
			
				iArray[i]=new Array();
				iArray[i][0]="��Ժ����";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="��Ժ����";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="סԺ����";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
			
				iArray[i]=new Array();
				iArray[i][0]="�˵��������";
				iArray[i][1]="80px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="�۳����";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="������";
				iArray[i][1]="60px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="��Ҫ��ϱ���";
				iArray[i][1]="50px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="��Ҫ���";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="����������";
				iArray[i][1]="50px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="�������";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
				
				iArray[i]=new Array();
				iArray[i][0]="�۳�˵��";
				iArray[i][1]="150px";
				iArray[i][2]=300;
				iArray[i++][3]=0;
					
				iArray[i]=new Array();
				iArray[i][0]="Ӱ�������";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
						
				iArray[i]=new Array();
				iArray[i][0]="����˵��";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="ҽ���˵�����";
				iArray[i][1]="120px";
				iArray[i][2]=300;
				iArray[i++][3]=3;
				
				iArray[i]=new Array();
				iArray[i][0]="��������";
				iArray[i][1]="100px";
				iArray[i][2]=300;
				iArray[i++][3]=0;				
				
				SpecialHospGrid = new MulLineEnter("fm","SpecialHospGrid");
				SpecialHospGrid.mulLineCount = 0;
				SpecialHospGrid.displayTitle = 1;
				SpecialHospGrid.locked = 0;
				SpecialHospGrid.canSel =1;              //��ѡ��ť��1��ʾ��0����
				SpecialHospGrid.hiddenPlus=1;           //���ţ�1���أ�0��ʾ
				SpecialHospGrid.hiddenSubtraction=1;    //���ţ�1���أ�0��ʾ
				SpecialHospGrid.selBoxEventFuncName = "getSpecialHospInfo"; //��������    
				SpecialHospGrid.loadMulLine(iArray);
    }
    catch(ex) {
			alert(ex);
    }
}
/**=========================================================================
    ����סԺ�˵�������ϸ��Ϣ
   =========================================================================
*/
function initSpecialHospItemGrid() {
	
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
		iArray[i][0]="�������ʹ���";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i++][3]=1;


		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="60px";
		iArray[i][2]=50;
		iArray[i][3]=2;
		iArray[i][4]="llfeeitemtyp2";
		iArray[i][5]="1|2";
		iArray[i][6]="0|1";
		iArray[i++][19]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="���ý��";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�����Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="�������Է�";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=1;        
		
		iArray[i]=new Array();
		iArray[i][0]="�۳����";
		iArray[i][1]="100px";
		iArray[i][2]=50;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="�۳�˵��";
		iArray[i][1]="200px";
		iArray[i][2]=120;
		iArray[i++][3]=1;
		
		iArray[i]=new Array();
		iArray[i][0]="��ע";
		iArray[i][1]="150px";
		iArray[i][2]=100;
		iArray[i++][3]=1;
      
		SpecialHospItemGrid = new MulLineEnter("fm","SpecialHospItemGrid");
		SpecialHospItemGrid.mulLineCount = 1;
		SpecialHospItemGrid.displayTitle = 1;
		SpecialHospItemGrid.locked = 0;
		//SpecialHospItemGrid.canChk =1;              //��ѡ��ť��1��ʾ��0����
		SpecialHospItemGrid.canSel =0;              //��ѡ��ť��1��ʾ��0����
		SpecialHospItemGrid.hiddenPlus=0;           //���ţ�1���أ�0��ʾ
		SpecialHospItemGrid.hiddenSubtraction=0;    //���ţ�1���أ�0��ʾ     
		SpecialHospItemGrid.loadMulLine(iArray);	
		
	} catch(ex) {
		
		alert(ex);
  }
}
</script>
