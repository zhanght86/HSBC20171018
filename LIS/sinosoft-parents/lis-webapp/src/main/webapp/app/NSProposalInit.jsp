<%
//�������ƣ�ProposalInput.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//only use for bq
%>

<script language="JavaScript">

var InsuredGrid;

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
    //document.all('MainPolNo').value = '';
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
    document.all('PolApplyDate').value          = '';
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

  //�����ֶ�
  try{
  document.all('StandbyFlag1').value = '';
  document.all('StandbyFlag2').value = '';
  document.all('StandbyFlag3').value = '';

  }
  catch(ex){}
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
   if(checktype=="1")
   {
     param="13";
     document.all("pagename").value="13";
   }
   if(checktype=="2")
   {
     param="23";
     document.all("pagename").value="23";
   }
  }
  catch(ex)
  {
    alert("��ProposalInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

// ��������Ϣ�б��ĳ�ʼ��
function initSubInsuredGrid()
  {
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���������˿ͻ���";      //����
      iArray[1][1]="180px";                 //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //�Ƿ�ʹ���Լ���д�ĺ���

      iArray[2]=new Array();
      iArray[2][0]="����";                  //����
      iArray[2][1]="160px";                 //�п�
      iArray[2][2]=100;                     //�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";                  //����
      iArray[3][1]="140px";                 //�п�
      iArray[3][2]=60;                      //�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";              //����
      iArray[4][1]="140px";                 //�п�
      iArray[4][2]=100;                     //�����ֵ
      iArray[4][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�뱻���˹�ϵ";              //����
      iArray[5][1]="160px";                 //�п�
      iArray[5][2]=100;                     //�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SubInsuredGrid.mulLineCount = 0;
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert("��ProposalInit.jsp-->initSubInsuredGrid�����з����쳣:��ʼ���������!");
      }
}

// ��������Ϣ�б��ĳ�ʼ��
function initBnfGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���";            //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";        //�п�
    iArray[0][2]=10;            //�����ֵ
    iArray[0][3]=0;         //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���������";      //����
    iArray[1][1]="60px";        //�п�
    iArray[1][2]=40;            //�����ֵ
    iArray[1][3]=2;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[1][4]="BnfType";
    iArray[1][9]="���������|notnull&code:BnfType";

    iArray[2]=new Array();
    iArray[2][0]="����";    //����
    iArray[2][1]="30px";        //�п�
    iArray[2][2]=30;            //�����ֵ
    iArray[2][3]=1;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[2][9]="����|len<=20"; //У��

    iArray[3]=new Array();
    iArray[3][0]="�Ա�";    //����
    iArray[3][1]="30px";        //�п�
    iArray[3][2]=30;            //�����ֵ
    iArray[3][3]=2;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[3][4]="Sex";
    //iArray[3][9]="�Ա�|code:Sex";

    iArray[4]=new Array();
    iArray[4][0]="֤������";        //����
    iArray[4][1]="40px";        //�п�
    iArray[4][2]=40;            //�����ֵ
    iArray[4][3]=2;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[4][4]="IDType";
    iArray[4][9]="֤������|code:IDType";

    iArray[5]=new Array();
    iArray[5][0]="֤������";        //����
    iArray[5][1]="120px";       //�п�
    iArray[5][2]=80;            //�����ֵ
    iArray[5][3]=1;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[5][9]="֤������|len<=20";

    iArray[6]=new Array();
    iArray[6][0]="�뱻���˹�ϵ";    //����
    iArray[6][1]="60px";        //�п�
    iArray[6][2]=60;            //�����ֵ
    iArray[6][3]=2;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[6][4]="Relation";
    iArray[6][9]="�뱻���˹�ϵ|code:Relation";


    iArray[7]=new Array();
    iArray[7][0]="����˳��";        //����
    iArray[7][1]="40px";        //�п�
    iArray[7][2]=40;            //�����ֵ
    iArray[7][3]=2;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[7][4]="bnforder";
    iArray[7][9]="����˳��|code:OccupationType";

    iArray[8]=new Array();
    iArray[8][0]="����ݶ�";        //����
    iArray[8][1]="40px";        //�п�
    iArray[8][2]=40;            //�����ֵ
    iArray[8][3]=1;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[8][9]="�������|num&len<=10";

    iArray[9]=new Array();
    iArray[9][0]="סַ������ţ�";      //����
    iArray[9][1]="0px";     //�п�
    iArray[9][2]=100;           //�����ֵ
    iArray[9][3]=3;         //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[9][9]="סַ|len<=80";

    iArray[10]=new Array();
    iArray[10][0]="����";       //����
    iArray[10][1]="30px";       //�п�
    iArray[10][2]=30;           //�����ֵ
    iArray[10][3]=2;            //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[10][4]="customertype";

    iArray[11]=new Array();
    iArray[11][0]="����������";         //����
    iArray[11][1]="80px";       //�п�
    iArray[11][2]=30;           //�����ֵ
    iArray[11][3]=2;            //�Ƿ���������,1��ʾ������0��ʾ������
    iArray[11][4]="insuredpeople";
    //iArray[11][5]="1|2|3";
    //iArray[11][6]="11|12|13";
    iArray[11][15]="PrtNo";
    iArray[11][16]="#"+prtNo+"#";
    iArray[11][17]="1";

    iArray[12]=new Array();
    iArray[12][0]="�����������ڲ��ͻ���";       //����
    iArray[12][1]="0px";        //�п�
    iArray[12][2]=30;           //�����ֵ
    iArray[12][3]=3;            //�Ƿ���������,1��ʾ������0��ʾ������

    iArray[13]=new Array();
    iArray[13][0]="���������˿ͻ���";       //����
    iArray[13][1]="0px";        //�п�
    iArray[13][2]=30;           //�����ֵ
    iArray[13][3]=3;            //�Ƿ���������,1��ʾ������0��ʾ������



    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" );
    //��Щ���Ա�����loadMulLineǰ
    BnfGrid.mulLineCount = 1;
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray);

    //��Щ����������loadMulLine����
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert("��ProposalInit.jsp-->initBnfGrid�����з����쳣:��ʼ���������!");
  }
}

// ��֪��Ϣ�б��ĳ�ʼ��
function initImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";              //����
      iArray[1][1]="60px";                  //�п�
      iArray[1][2]=60;                      //�����ֵ
      iArray[1][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[1][4]="ImpartVer";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";              //����
      iArray[2][1]="60px";                  //�п�
      iArray[2][2]=60;                      //�����ֵ
      iArray[2][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";              //����
      iArray[3][1]="250px";                 //�п�
      iArray[3][2]=200;                     //�����ֵ
      iArray[3][3]=1;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";              //����
      iArray[4][1]="150px";                 //�п�
      iArray[4][2]=150;                     //�����ֵ
      iArray[4][3]=1;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[4][9]="��д����|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="��֪�ͻ�����";              //����
      iArray[5][1]="90px";                  //�п�
      iArray[5][2]=90;                      //�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[5][4]="CustomerType";
      iArray[5][9]="��֪�ͻ�����|len<=18";

      iArray[6]=new Array();
      iArray[6][0]="��֪�ͻ�����";              //����
      iArray[6][1]="90px";                  //�п�
      iArray[6][2]=90;                      //�����ֵ
      iArray[6][3]=2;                       //�Ƿ���������,1��ʾ������0��ʾ������
      iArray[6][4]="CustomerNo";
      iArray[6][9]="��֪�ͻ�����";

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
      alert("��ProposalInit.jsp-->initImpartGrid�����з����쳣:��ʼ���������!");
    }
}

// �ر�Լ����Ϣ�б��ĳ�ʼ��
function initSpecGrid() {
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="0px";                   //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";              //����
      iArray[1][1]="0px";                   //�п�
      iArray[1][2]=100;                     //�����ֵ
      iArray[1][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��Լ����";              //����
      iArray[2][1]="0px";                   //�п�
      iArray[2][2]=100;                     //�����ֵ
      iArray[2][3]=3;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Լ����";              //����
      iArray[3][1]="540px";                 //�п�
      iArray[3][2]=200;                     //�����ֵ
      iArray[3][3]=1;                       //�Ƿ���������,1��ʾ������0��ʾ������
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
        alert("��ProposalInit.jsp-->initSpecGrid�����з����쳣:��ʼ���������!");
      }
}

// ��������Ϣ�б��ĳ�ʼ��
function initInsuredGrid()
  {
   var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";
      iArray[0][1]="30px";
      //iArray[0][2]=1;
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";
      iArray[1][1]="80px";
      //iArray[1][2]=20;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="����";
      iArray[2][1]="60px";
      //iArray[2][2]=20;
      iArray[2][3]=0;


      iArray[3]=new Array();
      iArray[3][0]="�Ա�";
      iArray[3][1]="40px";
      //iArray[3][2]=20;
      iArray[3][3]=2;
      iArray[3][4]="Sex";

      iArray[4]=new Array();
      iArray[4][0]="��������";
      iArray[4][1]="80px";
      //iArray[4][2]=20;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���һ�������˹�ϵ";
      iArray[5][1]="100px";
      //iArray[5][2]=20;
      iArray[5][3]=2;
      iArray[5][4]="Relation";
      iArray[5][9]="�����������˹�ϵ|code:Relation&NOTNULL";
      //iArray[5][18]=300;

      iArray[6]=new Array();
      iArray[6][0]="�ͻ��ڲ���";
      iArray[6][1]="80px";
      //iArray[6][2]=20;
      iArray[6][3]=2;
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";

      InsuredGrid = new MulLineEnter("fm", "InsuredGrid");
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;

      //InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid.hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      }

      catch(ex)
      {
        alert("��ProposalInit.jsp-->initInsuredGrid�����з����쳣:��ʼ���������!");
      }
      //alert(parent.VD.gVSwitch.getVar( "ContNo" ));
      //alert(parent.VD.gVSwitch.getVar("InsuredNo"));
      var turnPage = new turnPageClass();
      turnPage.queryModal("select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+parent.VD.gVSwitch.getVar( "ContNo" )+"' and insuredno!='"+parent.VD.gVSwitch.getVar("InsuredNo")+"'", InsuredGrid)
}

function emptyForm() {
    //emptyFormElements();     //���ҳ�������������COMMON��JS��ʵ��
    //initInpBox();
    initSelBox();
    initSubInsuredGrid();

    //alert(getRisk()); //�������е����ֱ��뼰���ֵ�ƴ��

    initBnfGrid();
    initImpartGrid();
    initSpecGrid();
    initDutyGrid();
    initPremGrid();
    //add by yaory for relatedinsured

    //�����������,��Ϊ�ǵ��þ�������ֽ���,���ܽ��滹û��չ�ֳ����͵��� initInsuredGrid(),�����쳣
    //XinYQ added on 2007-03-09
    var oInsuredGrid;
    try
    {
        oInsuredGrid = document.all("spanInsuredGrid");
    }
    catch (ex) {}
    if (oInsuredGrid != null && typeof(oInsuredGrid) != "undefined")
    {
        initInsuredGrid();
    }
}

function initForm() {
    try {
      //document.all("RiskCode").CodeData=getRisk();  //delete by yaory
        try
        {
          document.all('SelPolNo').value=parent.VD.gVSwitch.getVar("SelPolNo");
            if (document.all('SelPolNo').value=='false')
            {
              document.all('SelPolNo').value='';
            }
        }
        catch(ex){

        }
      //alert("LoadFlag="+LoadFlag);
      //alert("ok:"+document.all('SelPolNo').value);

    //���ղ�ͬ��LoadFlag���в�ͬ�Ĵ���
    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
        if (LoadFlag == "1"&& document.all('SelPolNo').value!="") {
          //alert(document.all('SelPolNo').value);
            var tPolNo = document.all('SelPolNo').value;
            queryPolDetail( tPolNo );
            divButton.style.display="none";
            divInputContButton.style.display = "none";
            divApproveContButton.style.display = "none";
            divApproveModifyContButton.style.display = "none";
        //alert("bbb");
            //��������ؿؼ���ֵ
            document.all('MissionID').value=tMissionID;
            document.all('SubMissionID').value=tSubMissionID;
            if(ScanFlag=="1"){
              document.all('WorkFlowFlag').value="0000001099";
          }
          else{
              document.all('WorkFlowFlag').value="0000001098";
          }




        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          //alert("ccc");
      setFocus();
          //alert("ddd");

          return;

        }


        if (LoadFlag == "2") {  //�����¸���Ͷ����¼��
           //alert(LoadFlag);
            var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
            var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
            //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
            if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {

                document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
                }
            else
            {
                    document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
                }
            if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //���ǰһ������ѡ������
            {
                var tPolNo = document.all('SelPolNo').value;
                    queryPolDetail( tPolNo );
                    inputButton.style.display = "none";
                    modifyButton.style.display = "";
            }
            else
            {
                    getRiskInput( tRiskCode, LoadFlag );
                }
            //����¼�룬MINIM����
            document.all('RiskCode').focus();
        setFocus();
        return;

        }

        if (LoadFlag == "3") {  //����Ͷ������ϸ��ѯ
            var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
            //alert("PolNo="+tPolNo);
            if(tPolNo!=false){
        queryPolDetail( tPolNo );
            }
            //alert("abc");
            divButton.style.display="none";
            divInputContButton.style.display = "none";
            divApproveContButton.style.display = "none";
            divApproveModifyContButton.style.display = "none";
        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          setFocus();
      return;

        }

        if (LoadFlag == "4") {  //�����¸���Ͷ������ϸ���
            var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
            queryPolDetail( tPolNo );
        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          setFocus();
      return;
        }

        if (LoadFlag == "5") {  //����Ͷ�������˲�ѯ
            var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
            queryPolDetail( tPolNo );
        divButton.style.display="none";
            divInputContButton.style.display = "none";
            divApproveModifyContButton.style.display = "none";
      divApproveContButton.style.display = "";
        inputButton.style.display = "";
        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          setFocus();
      return;
        }

        if (LoadFlag == "6") {  //����Ͷ������ѯ
            //<!--document.all("divHideButton").style.display="none";-->
            var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
            queryPolDetail( tPolNo );
            return;
        }

        if (LoadFlag == "7") {  //��ȫ�±�����
            var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
            var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
            //alert(tContPlanCode);
            if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
            {
                document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
            }
            else
            {
                document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
            }
            if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //���ǰһ������ѡ������
            {
                var tPolNo = document.all('SelPolNo').value;
                queryPolDetail( tPolNo );
                inputButton.style.display = "none";
                modifyButton.style.display = "";
            }
            else
            {
                getRiskInput( tRiskCode, LoadFlag );
            }

          //����¼�룬MINIM����
          document.all('RiskCode').focus();
          setFocus();
          return;
        }

        if (LoadFlag == "8")
        {
            //alert(document.all('SelPolNo').value);
            if (document.all('SelPolNo').value!="")
            {
            	  //alert(parent.VD.gVSwitch.getVar( "PolNo" ));
                var tPolNo = document.all('SelPolNo').value;
                aContNo=document.all('ContNo').value;
                //alert("aaaaa" + tPolNo);
                queryPolDetail( tPolNo );
                divButton.style.display="none";
                divInputContButton.style.display = "none";
                divApproveContButton.style.display = "none";
                divApproveModifyContButton.style.display = "none";
                //alert("bbb");
                //��������ؿؼ���ֵ
                document.all('MissionID').value=tMissionID;
                document.all('SubMissionID').value=tSubMissionID;
                if (ScanFlag=="1")
                {
                    document.all('WorkFlowFlag').value="0000001099";
                }
                else
                {
                  document.all('WorkFlowFlag').value="0000001098";
                }

                //����¼�룬MINIM����
                //document.all('RiskCode').focus();
                //alert("ccc");
                //setFocus();
             //alert(EdorType);
               if (EdorType == "NS")
               {
            	   //alert(1);
                 document.all('BQContNo').value = top.opener.document.all('ContNo').value;
                 var aContNo = document.all('BQContNo').value;
                 document.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
                 //alert(document.all("RiskCode").CodeData);
               }
                //alert("ddd");
                return;
            }
              if (EdorType == "NS")
               {
            	   //alert(1);
                 document.all('BQContNo').value = top.opener.document.all('ContNo').value;
                 var aContNo = document.all('BQContNo').value;
                 document.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
               }

        }

        if (LoadFlag == "10") { //����Ͷ�������������޸�
            var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
            queryPolDetail( tPolNo );
        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          setFocus();
          return;
        }

      if (LoadFlag == "13"||LoadFlag == "14"||LoadFlag == "23")
      { //����Ͷ�������������޸�
      var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
          var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
          var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );

          //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
          if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
          {
            document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
          }
          else
          {
              document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
            }
          if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //���ǰһ������ѡ������
          {
            //alert(tPolNo);
            var tPolNo = document.all('SelPolNo').value;
              queryPolDetail( tPolNo );
              inputButton.style.display = "none";
              modifyButton.style.display = "";
          }
          else
          {
              getRiskInput( tRiskCode, LoadFlag );
            }
            //����¼�룬MINIM����
        document.all('RiskCode').focus();
        setFocus();
      return;
        }

      if (LoadFlag == "16") {   //�����¸���Ͷ������ϸ��ѯ
          var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
            queryPolDetail( tPolNo );
            //����¼�룬MINIM����
        document.all('RiskCode').focus();
        setFocus();
        return;

        }

      //�˹��˱��޸�Ͷ����,�����ǲ鿴��������
        if (LoadFlag =="25"&&document.all('SelPolNo').value!="") {
            var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );

            queryPolDetail( tPolNo );

//      inputButton.style.display = "";

        //getRiskInput( tRiskCode, LoadFlag );

        //����¼�룬MINIM����
        document.all('RiskCode').focus();
          setFocus();
      return;
        }

      if (LoadFlag == "99") {   //�����涯����
      document.all("RiskCode").CodeData=getRiskByGrpAll();
      inputButton.style.display = "none";
            //����¼�룬MINIM����
        document.all('RiskCode').focus();
        setFocus();
        return;
        }

    }
    catch(ex){
    }
}

//ȡ�ü���Ͷ��������Ϣ
function getGrpPolInfo()
{
    //alert("getGrpPolInfo"+parent.VD.gVSwitch.getVar("GrpContNo")+"|"+document.all('GrpContNo').value);
   try{document.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    //alert("getGrpPolInfo"+document.all('GrpContNo').value);
    try{document.all('ProposalGrpContNo').value=parent.VD.gVSwitch.getVar("ProposalGrpContNo");}catch(ex){};
    try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{document.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{document.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{document.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{document.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{document.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{document.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('Password2').value=parent.VD.gVSwitch.getVar("Password2");}catch(ex){};
    try{document.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{document.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{document.all('Peoples2').value=parent.VD.gVSwitch.getVar("Peoples2");}catch(ex){};
    try{document.all('GrpName').value=parent.VD.gVSwitch.getVar("GrpName");}catch(ex){};
    try{document.all('BusinessType').value=parent.VD.gVSwitch.getVar("BusinessType");}catch(ex){};
    try{document.all('GrpNature').value=parent.VD.gVSwitch.getVar("GrpNature");}catch(ex){};
    try{document.all('RgtMoney').value=parent.VD.gVSwitch.getVar("RgtMoney");}catch(ex){};
    try{document.all('Asset').value=parent.VD.gVSwitch.getVar("Asset");}catch(ex){};
    try{document.all('NetProfitRate').value=parent.VD.gVSwitch.getVar("NetProfitRate");}catch(ex){};
    try{document.all('MainBussiness').value=parent.VD.gVSwitch.getVar("MainBussiness");}catch(ex){};
    try{document.all('Corporation').value=parent.VD.gVSwitch.getVar("Corporation");}catch(ex){};
    try{document.all('ComAera').value=parent.VD.gVSwitch.getVar("ComAera");}catch(ex){};
    try{document.all('Fax').value=parent.VD.gVSwitch.getVar("Fax");}catch(ex){};
    try{document.all('Phone').value=parent.VD.gVSwitch.getVar("Phone");}catch(ex){};
    try{document.all('GetFlag').value=parent.VD.gVSwitch.getVar("GetFlag");}catch(ex){};
    try{document.all('Satrap').value=parent.VD.gVSwitch.getVar("Satrap");}catch(ex){};
    try{document.all('EMail').value=parent.VD.gVSwitch.getVar("EMail");}catch(ex){};
    try{document.all('FoundDate').value=parent.VD.gVSwitch.getVar("FoundDate");}catch(ex){};
    try{document.all('GrpGroupNo').value=parent.VD.gVSwitch.getVar("GrpGroupNo");}catch(ex){};
    try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };

   
//    try { document.all( 'DisputedFlag' ).value = parent.VD.gVSwitch.getVar( "DisputedFlag"); } catch(ex) { };
//    try { document.all( 'OutPayFlag' ).value = parent.VD.gVSwitch.getVar( "OutPayFlag"); } catch(ex) { };
//    try { document.all( 'GetPolMode' ).value = parent.VD.gVSwitch.getVar( "GetPolMode"); } catch(ex) { };
//    try { document.all( 'Lang' ).value = parent.VD.gVSwitch.getVar( "Lang"); } catch(ex) { };
//    try { document.all( 'Currency' ).value = parent.VD.gVSwitch.getVar( "Currency"); } catch(ex) { };
//    try { document.all( 'LostTimes' ).value = parent.VD.gVSwitch.getVar( "LostTimes"); } catch(ex) { };
//    try { document.all( 'PrintCount' ).value = parent.VD.gVSwitch.getVar( "PrintCount"); } catch(ex) { };
//    try { document.all( 'RegetDate' ).value = parent.VD.gVSwitch.getVar( "RegetDate"); } catch(ex) { };
//    try { document.all( 'LastEdorDate' ).value = parent.VD.gVSwitch.getVar( "LastEdorDate"); } catch(ex) { };
//    try { document.all( 'LastGetDate' ).value = parent.VD.gVSwitch.getVar( "LastGetDate"); } catch(ex) { };
//    try { document.all( 'LastLoanDate' ).value = parent.VD.gVSwitch.getVar( "LastLoanDate"); } catch(ex) { };
//    try { document.all( 'SpecFlag' ).value = parent.VD.gVSwitch.getVar( "SpecFlag"); } catch(ex) { };
//    try { document.all( 'GrpSpec' ).value = parent.VD.gVSwitch.getVar( "GrpSpec"); } catch(ex) { };
//    try { document.all( 'PayMode' ).value = parent.VD.gVSwitch.getVar( "PayMode"); } catch(ex) { };
//    try { document.all( 'SignCom' ).value = parent.VD.gVSwitch.getVar( "SignCom"); } catch(ex) { };
//    try { document.all( 'SignDate' ).value = parent.VD.gVSwitch.getVar( "SignDate"); } catch(ex) { };
//    try { document.all( 'SignTime' ).value = parent.VD.gVSwitch.getVar( "SignTime"); } catch(ex) { };
//    try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };
//    try { document.all( 'PayIntv' ).value = parent.VD.gVSwitch.getVar( "PayIntv"); } catch(ex) { };
//    try { document.all( 'ManageFeeRate' ).value = parent.VD.gVSwitch.getVar( "ManageFeeRate"); } catch(ex) { };
//    try { document.all( 'ExpPeoples' ).value = parent.VD.gVSwitch.getVar( "ExpPeoples"); } catch(ex) { };
//    try { document.all( 'ExpPremium' ).value = parent.VD.gVSwitch.getVar( "ExpPremium"); } catch(ex) { };
//    try { document.all( 'ExpAmnt' ).value = parent.VD.gVSwitch.getVar( "ExpAmnt"); } catch(ex) { };
//    try { document.all( 'Peoples' ).value = parent.VD.gVSwitch.getVar( "Peoples"); } catch(ex) { };
//    try { document.all( 'Mult' ).value = parent.VD.gVSwitch.getVar( "Mult"); } catch(ex) { };
//    try { document.all( 'Prem' ).value = parent.VD.gVSwitch.getVar( "Prem"); } catch(ex) { };
//    try { document.all( 'Amnt' ).value = parent.VD.gVSwitch.getVar( "Amnt"); } catch(ex) { };
//    try { document.all( 'SumPrem' ).value = parent.VD.gVSwitch.getVar( "SumPrem"); } catch(ex) { };
//    try { document.all( 'SumPay' ).value = parent.VD.gVSwitch.getVar( "SumPay"); } catch(ex) { };
//    try { document.all( 'Dif' ).value = parent.VD.gVSwitch.getVar( "Dif"); } catch(ex) { };
//    try { document.all( 'Remark' ).value = parent.VD.gVSwitch.getVar( "Remark"); } catch(ex) { };
//    try { document.all( 'StandbyFlag1' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag1"); } catch(ex) { };
//    try { document.all( 'StandbyFlag2' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag2"); } catch(ex) { };
//    try { document.all( 'StandbyFlag3' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag3"); } catch(ex) { };
//    try { document.all( 'InputOperator' ).value = parent.VD.gVSwitch.getVar( "InputOperator"); } catch(ex) { };
//    try { document.all( 'InputDate' ).value = parent.VD.gVSwitch.getVar( "InputDate"); } catch(ex) { };
//    try { document.all( 'InputTime' ).value = parent.VD.gVSwitch.getVar( "InputTime"); } catch(ex) { };
//    try { document.all( 'ApproveFlag' ).value = parent.VD.gVSwitch.getVar( "ApproveFlag"); } catch(ex) { };
//    try { document.all( 'ApproveCode' ).value = parent.VD.gVSwitch.getVar( "ApproveCode"); } catch(ex) { };
//    try { document.all( 'ApproveDate' ).value = parent.VD.gVSwitch.getVar( "ApproveDate"); } catch(ex) { };
//    try { document.all( 'ApproveTime' ).value = parent.VD.gVSwitch.getVar( "ApproveTime"); } catch(ex) { };
//    try { document.all( 'UWOperator' ).value = parent.VD.gVSwitch.getVar( "UWOperator"); } catch(ex) { };
//    try { document.all( 'UWFlag' ).value = parent.VD.gVSwitch.getVar( "UWFlag"); } catch(ex) { };
//    try { document.all( 'UWDate' ).value = parent.VD.gVSwitch.getVar( "UWDate"); } catch(ex) { };
//    try { document.all( 'UWTime' ).value = parent.VD.gVSwitch.getVar( "UWTime"); } catch(ex) { };
//    try { document.all( 'AppFlag' ).value = parent.VD.gVSwitch.getVar( "AppFlag"); } catch(ex) { };
//    try { document.all( 'PolApplyDate' ).value = parent.VD.gVSwitch.getVar( "PolApplyDate"); } catch(ex) { };
//    try { document.all( 'CustomGetPolDate' ).value = parent.VD.gVSwitch.getVar( "CustomGetPolDate"); } catch(ex) { };
//    try { document.all( 'GetPolDate' ).value = parent.VD.gVSwitch.getVar( "GetPolDate"); } catch(ex) { };
//    try { document.all( 'GetPolTime' ).value = parent.VD.gVSwitch.getVar( "GetPolTime"); } catch(ex) { };
//    try { document.all( 'State' ).value = parent.VD.gVSwitch.getVar( "State"); } catch(ex) { };

    //����Ͷ������Ϣ
    //alert("GrpNo"+parent.VD.gVSwitch.getVar( "GrpNo"))

    try { document.all( 'AppGrpNo' ).value = parent.VD.gVSwitch.getVar( "GrpNo"); } catch(ex) { };

    try { document.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo"); } catch(ex) { };




}
//��ú�ͬ,Ͷ����,����������Ϣ
function getContInput()
{
    //alert("getContInput:"+parent.VD.gVSwitch.getVar("GrpContNo"))  ;
    //alert("here!");
    //try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    //try{document.all('ProposalNo').value=parent.VD.gVSwitch.getVar("ProposalContNo");}catch(ex){};
    //alert("fm.ProposalContNo.value=="+fm.ProposalContNo.value);
    try{document.all('ProposalContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    try{document.all('ContType').value=parent.VD.gVSwitch.getVar("ContType");}catch(ex){};
    try{document.all('FamilyType').value=parent.VD.gVSwitch.getVar("FamilyType");}catch(ex){};
    try{document.all('PolType').value=parent.VD.gVSwitch.getVar("PolType");}catch(ex){};
    try{document.all('CardFlag').value=parent.VD.gVSwitch.getVar("CardFlag");}catch(ex){};
    try{document.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{document.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{document.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{document.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{document.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{document.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{document.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
//    try{document.all('Handler').value=parent.VD.gVSwitch.getVar("Handler");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{document.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{document.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{document.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{document.all('AppntIDType').value=parent.VD.gVSwitch.getVar("AppntIDType");}catch(ex){};
    try{document.all('AppntIDNo').value=parent.VD.gVSwitch.getVar("AppntIDNo");}catch(ex){};
    try{document.all('CustomerNo').value=parent.VD.gVSwitch.getVar("InsuredNo");}catch(ex){};
    try{document.all('Name').value=parent.VD.gVSwitch.getVar("Name");}catch(ex){};
    try{document.all('Sex').value=parent.VD.gVSwitch.getVar("Sex");}catch(ex){};
    try{document.all('Birthday').value=parent.VD.gVSwitch.getVar("Birthday");}catch(ex){};
    try{document.all('IDType').value=parent.VD.gVSwitch.getVar("IDType");}catch(ex){};
    try{document.all('IDNo').value=parent.VD.gVSwitch.getVar("IDNo");}catch(ex){};
    try{document.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};

    try{document.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
//   try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
//   try{document.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
//   try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{document.all('AppntGrade').value=parent.VD.gVSwitch.getVar("AppntGrade");}catch(ex){};
    try{document.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{document.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{document.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('State').value=parent.VD.gVSwitch.getVar("State");}catch(ex){};
    try{document.all('AppntType').value=parent.VD.gVSwitch.getVar("AppntType");}catch(ex){};
    try{document.all('Operator').value=parent.VD.gVSwitch.getVar("Operator");}catch(ex){};
    try{document.all('MakeDate').value=parent.VD.gVSwitch.getVar("MakeDate");}catch(ex){};
    try{document.all('MakeTime').value=parent.VD.gVSwitch.getVar("MakeTime");}catch(ex){};
    try{document.all('ModifyDate').value=parent.VD.gVSwitch.getVar("ModifyDate");}catch(ex){};
    try{document.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
    try{document.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    //alert("document.all('CValiDate').value=="+document.all('CValiDate').value);
//alert("AppntNo"+parent.VD.gVSwitch.getVar("AppntNo"));
//alert("ManageCom"+parent.VD.gVSwitch.getVar("ManageCom"));


}
</script>