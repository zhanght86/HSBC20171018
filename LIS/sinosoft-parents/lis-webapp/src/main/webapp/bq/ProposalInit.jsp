<%
//�������ƣ�ProposalInput.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
  try {  
                                   
	  // ������Ϣ����
    document.all('ProposalNo').value = '';
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('SaleChnl').value = '';
    document.all('AgentCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('Handler').value = '';
    document.all('AgentCode1').value = '';
 

  //  document.all('ContNo').value = '';
    document.all('GrpPolNo').value = '';
    document.all('MainPolNo').value = '';
    document.all('FirstPayDate').value = '';
    document.all('Lang').value = '';
    document.all('Currency').value = '';
    document.all('DisputedFlag').value = '';
    document.all('AgentPayFlag').value = '';
    document.all('AgentGetFlag').value = '';
    document.all('Remark').value = '';
      

  	// ����Ͷ������Ϣ����   
    document.all( 'AppntCustomerNo' ).value          = "";
  	document.all( 'AppntName' ).value                = "";
  	document.all( 'AppntSex' ).value                 = "";
  	document.all( 'AppntBirthday' ).value            = "";
  	document.all( 'AppntAge' ).value                 = "";
  	document.all( 'AppntIDType' ).value              = "";
  	document.all( 'AppntIDNo' ).value                = "";
  	document.all( 'AppntNativePlace' ).value         = "";
  	document.all( 'AppntRgtAddress' ).value          = "";
  	document.all( 'AppntMarriage' ).value            = "";
  	document.all( 'AppntNationality' ).value         = "";
  	document.all( 'AppntDegree' ).value              = "";
  	document.all( 'AppntRelationToInsured' ).value   = "";
  	document.all( 'AppntPostalAddress' ).value       = "";
  	document.all( 'AppntZipCode' ).value             = "";
  	document.all( 'AppntPhone' ).value               = "";
  	document.all( 'AppntMobile' ).value              = "";
  	document.all( 'AppntEMail' ).value               = "";
  	document.all( 'AppntGrpName' ).value             = "";
  	document.all( 'AppntGrpPhone' ).value            = "";
  	document.all( 'AppntGrpAddress' ).value          = "";
  	document.all( 'AppntGrpZipCode' ).value          = "";
  	document.all( 'AppntWorkType' ).value            = "";
  	document.all( 'AppntPluralityType' ).value       = "";
  	document.all( 'AppntOccupationType' ).value      = "";
  	document.all( 'AppntOccupationCode' ).value      = "";
  	document.all( 'SmokeFlag' ).value                = "";

  	// ����Ͷ������Ϣ����    
  	document.all('GrpNo').value = '';
    document.all('GrpName').value = '';
    document.all('LinkMan').value = '';
    document.all('GrpRelation').value = '';
    document.all('GrpPhone').value = '';
    document.all('GrpFax').value = '';
    document.all('GrpEMail').value = '';
    document.all('GrpZipCode').value = '';
    document.all('GrpAddress').value = '';

	  // ��������Ϣ����  
  	document.all( 'CustomerNo' ).value          = "";
  	document.all( 'Name' ).value                = "";
  	document.all( 'Sex' ).value                 = "";
  	document.all( 'Birthday' ).value            = "";
  	document.all( 'Age' ).value                 = "";
  	document.all( 'IDType' ).value              = "";
  	document.all( 'IDNo' ).value                = "";
  	document.all( 'NativePlace' ).value         = "";
  	document.all( 'RgtAddress' ).value          = "";
  	document.all( 'Marriage' ).value            = "";
  	document.all( 'Nationality' ).value         = "";
  	document.all( 'Degree' ).value              = "";
  	document.all( 'SmokeFlag' ).value           = "";
  	document.all( 'PostalAddress' ).value       = "";
  	document.all( 'ZipCode' ).value             = "";
  	document.all( 'Phone' ).value               = "";
  	document.all( 'Mobile' ).value              = "";
  	document.all( 'EMail' ).value               = "";
  	document.all( 'GrpName' ).value             = "";
  	document.all( 'GrpPhone' ).value            = "";
  	document.all( 'GrpAddress' ).value          = "";
  	document.all( 'GrpZipCode' ).value          = "";	
  	document.all( 'WorkType' ).value            = "";
  	document.all( 'PluralityType' ).value       = "";
  	document.all( 'OccupationType' ).value      = "";
  	document.all( 'OccupationCode' ).value      = "";
  
    // ������Ϣ����
    document.all('RiskCode').value              = '';
    document.all('RiskVersion').value           = '';
    document.all('CValiDate').value             = '';
    document.all('HealthCheckFlag').value       = '';
    document.all('Years').value                 = '';
    document.all('PayYears').value              = '';
    document.all('PayIntv').value               = '';
    document.all('OutPayFlag').value            = '';
    document.all('PayLocation').value           = '';
    document.all('BankCode').value              = '';
    document.all('countName').value             = '';
    document.all('BankAccNo').value             = '';
    document.all('LiveGetMode').value           = '';
    document.all('getTerm').value               = '';
    document.all('getIntv').value               = '';
    document.all('BonusGetMode').value          = '';
    document.all('Mult').value                  = '';
    document.all('Prem').value                  = '';
    document.all('Amnt').value                  = '';
   
    document.all('PayEndYear').value = '';
    document.all('PayEndYearFlag').value = '';
    document.all('GetYear').value = '';
    document.all('GetYearFlag').value = '';
    document.all('GetStartType').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('AutoPayFlag').value = '';
    document.all('InterestDifFlag').value = '';
    document.all('SubFlag').value = '';
  } catch(ex) {
    //alert("��ProposalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��ProposalInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

// ��������Ϣ�б�ĳ�ʼ��
function initSubInsuredGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���������˿ͻ���";    	//����
      iArray[1][1]="180px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //�Ƿ�ʹ���Լ���д�ĺ��� 

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";         			//����
      iArray[3][1]="140px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="140px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�뱻���˹�ϵ";         		//����
      iArray[5][1]="160px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SubInsuredGrid.mulLineCount = 1;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ��������Ϣ�б�ĳ�ʼ��
function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[1]=new Array();
    iArray[1][0]="���������"; 		//����
    iArray[1][1]="60px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="BnfType";
    iArray[1][9]="���������|notnull&code:BnfType";
   
    iArray[2]=new Array();
    iArray[2][0]="����"; 	//����
    iArray[2][1]="30px";		//�п�
    iArray[2][2]=30;			//�����ֵ
    iArray[2][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][9]="����|len<=20"; //У��
  
    iArray[3]=new Array();
    iArray[3][0]="֤������"; 		//����
    iArray[3][1]="40px";		//�п�
    iArray[3][2]=40;			//�����ֵ
    iArray[3][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][4]="IDType";
    iArray[3][9]="֤������|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="120px";		//�п�
    iArray[4][2]=80;			//�����ֵ
    iArray[4][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][9]="֤������|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="�뱻���˹�ϵ"; 	//����
    iArray[5][1]="60px";		//�п�
    iArray[5][2]=60;			//�����ֵ
    iArray[5][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][4]="Relation";
    iArray[5][9]="�뱻���˹�ϵ|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="�������"; 		//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=40;			//�����ֵ
    iArray[6][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][9]="�������|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="����˳��"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[7][4]="OccupationType";
    iArray[7][9]="����˳��|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="סַ������ţ�"; 		//����
    iArray[8][1]="160px";		//�п�
    iArray[8][2]=100;			//�����ֵ
    iArray[8][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][9]="סַ|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="����"; 		//����
    iArray[9][1]="30px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][4]="customertype";
  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray); 
  
    //��Щ����������loadMulLine����
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="ImpartVer";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="��֪�ͻ�����";         		//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="CustomerType";
      iArray[5][9]="��֪�ͻ�����|len<=18";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// �ر�Լ����Ϣ�б�ĳ�ʼ��
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="0px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��Լ����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Լ����";         		//����
      iArray[3][1]="540px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="��Լ����|len<=255";
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 0;
      SpecGrid.hiddenPlus = 1;
      SpecGrid.hiddenSubtraction = 1;
      SpecGrid.loadMulLine(iArray);           
      //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      SpecGrid.setRowColData(0,1,"1");
      SpecGrid.setRowColData(0,2,"1");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����б�
function initDutyGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ڵ�λ";         			//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="��ȡ����";         			//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ڵ�λ";         			//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;             			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="��������";         			//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="���ڵ�λ";         			//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="���ѷ�ʽ";         			//����
      iArray[11][1]="40px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=1;
      
      iArray[12]=new Array();
      iArray[12][0]="����ѱ���";         			//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
  
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�������б�
function initPremGrid()
{
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѱ���";         			//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         			//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ʻ���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="�ʻ��������";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //��Щ���Ա�����loadMulLineǰ
	 	     PremGrid.mulLineCount = 0;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}

function emptyForm() {
	//emptyFormElements();     //���ҳ�������������COMMON��JS��ʵ��

	//initInpBox();
	//initSelBox();    
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	initPremGrid();
}

function initForm() {
	try	{  
	
		if (loadFlag == "2") {	//�����¸���Ͷ����¼��
			var tRiskCode = parent.VD.gVSwitch.getVar( "RiskCode" );
			getRiskInput( tRiskCode, loadFlag );
		}
		
		if (loadFlag == "3") {	//����Ͷ������ϸ��ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "4") {	//�����¸���Ͷ������ϸ��ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "5") {	//����Ͷ�������˲�ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		
		if (loadFlag == "6") {	//����Ͷ������ѯ
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		
		if (loadFlag == "10") {	//����Ͷ�������������޸�
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
				
		
		//����¼�룬MINIM����
		document.all('RiskCode').focus();
		setFocus(prtNo);
		
	} catch(ex) {
	}			
}

</script>