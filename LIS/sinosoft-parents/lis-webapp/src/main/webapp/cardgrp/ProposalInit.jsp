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
	try{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|"; //yaory
	strsql = "select insuredno,name from lcinsured where grpcontno='"+tt+"'" ;
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		}}catch(ex)
		{}

// �����ĳ�ʼ��������¼���֣�
function initInpBox() {
  try {

	  // ������Ϣ����
    fm.all('ProposalNo').value = '';
    fm.all('PrtNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('SaleChnl').value = '';
    fm.all('AgentCom').value = '';
    fm.all('AgentCode').value = '';
    fm.all('AgentGroup').value = '';
    fm.all('Handler').value = '';
    fm.all('AgentCode1').value = '';


  //  fm.all('ContNo').value = '';
    fm.all('GrpPolNo').value = '';
    //fm.all('MainPolNo').value = '';
    fm.all('FirstPayDate').value = '';
    fm.all('Lang').value = '';
    fm.all('Currency').value = '';
    fm.all('DisputedFlag').value = '';
    fm.all('AgentPayFlag').value = '';
    fm.all('AgentGetFlag').value = '';
    fm.all('Remark').value = '';


  	// ����Ͷ������Ϣ����
    fm.all( 'AppntCustomerNo' ).value          = "";
  	fm.all( 'AppntName' ).value                = "";
  	fm.all( 'AppntSex' ).value                 = "";
  	fm.all( 'AppntBirthday' ).value            = "";
  	fm.all( 'AppntAge' ).value                 = "";
  	fm.all( 'AppntIDType' ).value              = "";
  	fm.all( 'AppntIDNo' ).value                = "";
  	fm.all( 'AppntNativePlace' ).value         = "";
  	fm.all( 'AppntRgtAddress' ).value          = "";
  	fm.all( 'AppntMarriage' ).value            = "";
  	fm.all( 'AppntNationality' ).value         = "";
  	fm.all( 'AppntDegree' ).value              = "";
  	fm.all( 'AppntRelationToInsured' ).value   = "";
  	fm.all( 'AppntPostalAddress' ).value       = "";
  	fm.all( 'AppntZipCode' ).value             = "";
  	fm.all( 'AppntPhone' ).value               = "";
  	fm.all( 'AppntMobile' ).value              = "";
  	fm.all( 'AppntEMail' ).value               = "";
  	fm.all( 'AppntGrpName' ).value             = "";
  	fm.all( 'AppntGrpPhone' ).value            = "";
  	fm.all( 'AppntGrpAddress' ).value          = "";
  	fm.all( 'AppntGrpZipCode' ).value          = "";
  	fm.all( 'AppntWorkType' ).value            = "";
  	fm.all( 'AppntPluralityType' ).value       = "";
  	fm.all( 'AppntOccupationType' ).value      = "";
  	fm.all( 'AppntOccupationCode' ).value      = "";
  	fm.all( 'SmokeFlag' ).value                = "";

  	// ����Ͷ������Ϣ����
  	fm.all('GrpNo').value = '';
    fm.all('GrpName').value = '';
    fm.all('LinkMan').value = '';
    fm.all('GrpRelation').value = '';
    fm.all('GrpPhone').value = '';
    fm.all('GrpFax').value = '';
    fm.all('GrpEMail').value = '';
    fm.all('GrpZipCode').value = '';
    fm.all('GrpAddress').value = '';

	  // ��������Ϣ����
  	fm.all( 'CustomerNo' ).value          = "";
  	fm.all( 'Name' ).value                = "";
  	fm.all( 'Sex' ).value                 = "";
  	fm.all( 'Birthday' ).value            = "";
  	fm.all( 'Age' ).value                 = "";
  	fm.all( 'IDType' ).value              = "";
  	fm.all( 'IDNo' ).value                = "";
  	fm.all( 'NativePlace' ).value         = "";
  	fm.all( 'RgtAddress' ).value          = "";
  	fm.all( 'Marriage' ).value            = "";
  	fm.all( 'Nationality' ).value         = "";
  	fm.all( 'Degree' ).value              = "";
  	fm.all( 'SmokeFlag' ).value           = "";
  	fm.all( 'PostalAddress' ).value       = "";
  	fm.all( 'ZipCode' ).value             = "";
  	fm.all( 'Phone' ).value               = "";
  	fm.all( 'Mobile' ).value              = "";
  	fm.all( 'EMail' ).value               = "";
  	fm.all( 'GrpName' ).value             = "";
  	fm.all( 'GrpPhone' ).value            = "";
  	fm.all( 'GrpAddress' ).value          = "";
  	fm.all( 'GrpZipCode' ).value          = "";
  	fm.all( 'WorkType' ).value            = "";
  	fm.all( 'PluralityType' ).value       = "";
  	fm.all( 'OccupationType' ).value      = "";
  	fm.all( 'OccupationCode' ).value      = "";

    // ������Ϣ����
    fm.all('RiskCode').value              = '';
    fm.all('RiskVersion').value           = '';
    fm.all('CValiDate').value             = '';
    fm.all('PolApplyDate').value          = '';
    fm.all('HealthCheckFlag').value       = '';
    fm.all('Years').value                 = '';
    fm.all('PayYears').value              = '';
    fm.all('PayIntv').value               = '';
    fm.all('OutPayFlag').value            = '';
    fm.all('PayLocation').value           = '';
    fm.all('BankCode').value              = '';
    fm.all('countName').value             = '';
    fm.all('BankAccNo').value             = '';
    fm.all('LiveGetMode').value           = '';
    fm.all('getTerm').value               = '';
    fm.all('getIntv').value               = '';
    fm.all('BonusGetMode').value          = '';
    fm.all('Mult').value                  = '';
    fm.all('Prem').value                  = '';
    fm.all('Amnt').value                  = '';

    fm.all('PayEndYear').value = '';
    fm.all('PayEndYearFlag').value = '';
    fm.all('GetYear').value = '';
    fm.all('GetYearFlag').value = '';
    fm.all('GetStartType').value = '';
    fm.all('InsuYear').value = '';
    fm.all('InsuYearFlag').value = '';
    fm.all('AutoPayFlag').value = '';
    fm.all('InterestDifFlag').value = '';
    fm.all('SubFlag').value = '';


  } catch(ex) {
    //alert("��ProposalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }

  //�����ֶ�
  try{
  fm.all('StandbyFlag1').value = '';
  fm.all('StandbyFlag2').value = '';
  fm.all('StandbyFlag3').value = '';

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
     fm.pagename.value="13";
   }
   if(checktype=="2")
   {
     param="23";
     fm.pagename.value="23";
   }
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
    iArray[1][1]="50px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="BnfType";
    iArray[1][9]="���������|notnull&code:BnfType";

    iArray[2]=new Array();
    iArray[2][0]="����"; 	//����
    iArray[2][1]="50px";		//�п�
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
    iArray[6][0]="����˳��"; 		//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=40;			//�����ֵ
    iArray[6][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][4]="bnforder";
    iArray[6][9]="����˳��|code:bnforder";

    iArray[7]=new Array();
    iArray[7][0]="����ݶ�"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[7][9]="�������|num&len<=10";

    iArray[8]=new Array();
    iArray[8][0]="סַ������ţ�"; 		//����
    iArray[8][1]="0px";		//�п�
    iArray[8][2]=100;			//�����ֵ
    iArray[8][3]=3;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][9]="סַ|len<=80";

    iArray[9]=new Array();
    iArray[9][0]="����"; 		//����
    iArray[9][1]="30px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][4]="customertype";

    iArray[10]=new Array();
    iArray[10][0]="����������"; 		//����
    iArray[10][1]="80px";		//�п�
    iArray[10][2]=30;			//�����ֵ
    iArray[10][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    //iArray[10][7]=1;
    //iArray[10][4]="insuredpeople";
    
    iArray[10][10]="insured1";
    iArray[10][11]=tCodeData;
    iArray[10][19]=1;
    //iArray[10][5]="1|2|3";
    //iArray[10][5]="10|11|12";
    //iArray[10][6]="10|11|12";
    //iArray[10][15]="ContNo";
    //iArray[10][16]="#"+tt+"#";

    iArray[11]=new Array();
    iArray[11][0]="�����������ڲ��ͻ���"; 		//����
    iArray[11][1]="0px";		//�п�
    iArray[11][2]=30;			//�����ֵ
    iArray[11][3]=3;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[12]=new Array();
    iArray[12][0]="���������˿ͻ���"; 		//����
    iArray[12][1]="0px";		//�п�
    iArray[12][2]=30;			//�����ֵ
    iArray[12][3]=3;			//�Ƿ���������,1��ʾ����0��ʾ������



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

      iArray[6]=new Array();
      iArray[6][0]="��֪�ͻ�����";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
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

// �ر�Լ����Ϣ�б�ĳ�ʼ��
function initSpecGrid() {
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="0px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��Լ����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

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
        alert("��ProposalInit.jsp-->initSpecGrid�����з����쳣:��ʼ���������!");
      }
}


// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {
   var iArray = new Array();
   var turnPage = new turnPageClass();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�Ա�";      	   		//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="Sex";


      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���һ�������˹�ϵ";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2;
      iArray[5][4]="Relation";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][9]="�����������˹�ϵ|code:Relation&NOTNULL";
      //iArray[5][18]=300;

      iArray[6]=new Array();
      iArray[6][0]="�ͻ��ڲ���";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2;
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
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

  //initInsuredGrid();


}

function initForm() {
	try	{
	  //fm.all("RiskCode").CodeData=getRisk();	//delete by yaory
	  //alert(PolTypeFlag);
		try
		{
		  fm.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
			if (fm.all('SelPolNo').value=='false')
			{
			  fm.all('SelPolNo').value='';
			}
		}
		catch(ex){

		}
		//alert("LoadFlag="+LoadFlag);
	  //alert("ok:"+fm.all('SelPolNo').value);
	  //alert(scantype);
	  //alert(1);
    if(scantype=="scan"){
    	fm.all('pagename').value='11';	  
	  	//����¼�룬MINIM����
	  	//alert("aa");
	  	fm.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");
		  
		}
	//	alert(1);
	
			


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
     *LoadFlag=18-- ����������������
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/
		if (LoadFlag == "1"&& fm.all('SelPolNo').value!="") {
		  //alert(fm.all('SelPolNo').value);
			var tPolNo = fm.all('SelPolNo').value;
			queryPolDetail( tPolNo );
			divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
		//alert("bbb");
			//��������ؿؼ���ֵ
			fm.all('MissionID').value=tMissionID;
			fm.all('SubMissionID').value=tSubMissionID;
			if(ScanFlag=="1"){
			  fm.all('WorkFlowFlag').value="0000001099";
			  
		  }
		  else{
			  fm.all('WorkFlowFlag').value="0000001098";
		  }




	  	//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");

		  return;

		}
		


		if (LoadFlag == "2"||LoadFlag == "18") {	//�����¸���Ͷ����¼��
		   //alert(LoadFlag);
		    var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {

		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    	}
		    else
		    {
					fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
				}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //���ǰһ������ѡ������
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
					queryPolDetail( tPolNo );
					inputButton.style.display = "none";
					modifyButton.style.display = "";
		    }
		    else
		    {
		    //alert("1");
					getRiskInput( tRiskCode, LoadFlag );
				}
    		//����¼�룬MINIM����
	    	fm.all('RiskCode').focus();
  	  	setFocus();
  	  	return;

		}

		if (LoadFlag == "3") {	//����Ͷ������ϸ��ѯ
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
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;

		}

		if (LoadFlag == "4") {	//�����¸���Ͷ������ϸ���
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

		if (LoadFlag == "5") {	//����Ͷ�������˲�ѯ
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
    	divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
      divApproveContButton.style.display = "";
    	inputButton.style.display = "";
  		//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

		if (LoadFlag == "6") {	//����Ͷ������ѯ
			//<!--fm.all("divHideButton").style.display="none";-->
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
			return;
		}

		if (LoadFlag == "7") {	//��ȫ�±�����
		    var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert(tContPlanCode);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {
		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    }
		    else
		    {
				fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //���ǰһ������ѡ������
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
				queryPolDetail( tPolNo );
				inputButton.style.display = "none";
				modifyButton.style.display = "";
		    }
		    else
		    {
				getRiskInput( tRiskCode, LoadFlag );
			}

		  //����¼�룬MINIM����
		  fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}
		
		if(LoadFlag == "8"){			 
			 	  //fm.all('BQContNo').value = top.opener.fm.all('ContNo').value;
			 	  //var aContNo = fm.all('BQContNo').value;
			 	  //fm.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
			 	var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert(tContPlanCode);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {
		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    }
		    else
		    {
				fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //���ǰһ������ѡ������
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
				queryPolDetail( tPolNo );
				inputButton.style.display = "none";
				modifyButton.style.display = "";
		    }
		    else
		    {
				getRiskInput( tRiskCode, LoadFlag );
			}

		  //����¼�룬MINIM����
		  fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}

		if (LoadFlag == "10") {	//����Ͷ�������������޸�
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}

	  if (LoadFlag == "13"||LoadFlag == "14"||LoadFlag == "23")
	  {	//����Ͷ�������������޸�
      var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
		  var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		  var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );

		  //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		  if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		  {
		    fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		  }
		  else
		  {
			  fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		  if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //���ǰһ������ѡ������
		  {
		    //alert(tPolNo);
		    var tPolNo = fm.all('SelPolNo').value;
			  queryPolDetail( tPolNo );
			  inputButton.style.display = "none";
			  modifyButton.style.display = "";
		  }
		  else
		  {
			  getRiskInput( tRiskCode, LoadFlag );
			}
			//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
  		setFocus();
      return;
		}

	  if (LoadFlag == "16") {	//�����¸���Ͷ������ϸ��ѯ
		  var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
			//����¼�룬MINIM����
  		fm.all('RiskCode').focus();
	  	setFocus();
	  	return;

		}

	  //�˹��˱��޸�Ͷ����,�����ǲ鿴��������
		if (LoadFlag =="25"&&fm.all('SelPolNo').value!="") {
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );

			queryPolDetail( tPolNo );

//    	inputButton.style.display = "";

  		//getRiskInput( tRiskCode, LoadFlag );

  		//����¼�룬MINIM����
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

	  if (LoadFlag == "99") {	//�����涯����
      fm.all("RiskCode").CodeData=getRiskByGrpAll();
      inputButton.style.display = "none";
			//����¼�룬MINIM����
  		fm.all('RiskCode').focus();
	  	setFocus();
	  	autoMoveButton.style.display="";
	  	return;
		}

	}
	catch(ex){
	}
}

//ȡ�ü���Ͷ��������Ϣ
function getGrpPolInfo()
{
    //alert("getGrpPolInfo"+parent.VD.gVSwitch.getVar("GrpContNo")+"|"+fm.all('GrpContNo').value);
   try{fm.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    //alert("getGrpPolInfo"+fm.all('GrpContNo').value);
    try{fm.all('ProposalGrpContNo').value=parent.VD.gVSwitch.getVar("ProposalGrpContNo");}catch(ex){};
    try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{fm.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{fm.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{fm.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{fm.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{fm.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{fm.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('Password2').value=parent.VD.gVSwitch.getVar("Password2");}catch(ex){};
    try{fm.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{fm.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{fm.all('Peoples2').value=parent.VD.gVSwitch.getVar("Peoples2");}catch(ex){};
    try{fm.all('GrpName').value=parent.VD.gVSwitch.getVar("GrpName");}catch(ex){};
    try{fm.all('BusinessType').value=parent.VD.gVSwitch.getVar("BusinessType");}catch(ex){};
    try{fm.all('GrpNature').value=parent.VD.gVSwitch.getVar("GrpNature");}catch(ex){};
    try{fm.all('RgtMoney').value=parent.VD.gVSwitch.getVar("RgtMoney");}catch(ex){};
    try{fm.all('Asset').value=parent.VD.gVSwitch.getVar("Asset");}catch(ex){};
    try{fm.all('NetProfitRate').value=parent.VD.gVSwitch.getVar("NetProfitRate");}catch(ex){};
    try{fm.all('MainBussiness').value=parent.VD.gVSwitch.getVar("MainBussiness");}catch(ex){};
    try{fm.all('Corporation').value=parent.VD.gVSwitch.getVar("Corporation");}catch(ex){};
    try{fm.all('ComAera').value=parent.VD.gVSwitch.getVar("ComAera");}catch(ex){};
    try{fm.all('Fax').value=parent.VD.gVSwitch.getVar("Fax");}catch(ex){};
    try{fm.all('Phone').value=parent.VD.gVSwitch.getVar("Phone");}catch(ex){};
    try{fm.all('GetFlag').value=parent.VD.gVSwitch.getVar("GetFlag");}catch(ex){};
    try{fm.all('Satrap').value=parent.VD.gVSwitch.getVar("Satrap");}catch(ex){};
    try{fm.all('EMail').value=parent.VD.gVSwitch.getVar("EMail");}catch(ex){};
    try{fm.all('FoundDate').value=parent.VD.gVSwitch.getVar("FoundDate");}catch(ex){};
    try{fm.all('GrpGroupNo').value=parent.VD.gVSwitch.getVar("GrpGroupNo");}catch(ex){};
    try { fm.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };

//    try { fm.all( 'DisputedFlag' ).value = parent.VD.gVSwitch.getVar( "DisputedFlag"); } catch(ex) { };
//    try { fm.all( 'OutPayFlag' ).value = parent.VD.gVSwitch.getVar( "OutPayFlag"); } catch(ex) { };
//    try { fm.all( 'GetPolMode' ).value = parent.VD.gVSwitch.getVar( "GetPolMode"); } catch(ex) { };
//    try { fm.all( 'Lang' ).value = parent.VD.gVSwitch.getVar( "Lang"); } catch(ex) { };
//    try { fm.all( 'Currency' ).value = parent.VD.gVSwitch.getVar( "Currency"); } catch(ex) { };
//    try { fm.all( 'LostTimes' ).value = parent.VD.gVSwitch.getVar( "LostTimes"); } catch(ex) { };
//    try { fm.all( 'PrintCount' ).value = parent.VD.gVSwitch.getVar( "PrintCount"); } catch(ex) { };
//    try { fm.all( 'RegetDate' ).value = parent.VD.gVSwitch.getVar( "RegetDate"); } catch(ex) { };
//    try { fm.all( 'LastEdorDate' ).value = parent.VD.gVSwitch.getVar( "LastEdorDate"); } catch(ex) { };
//    try { fm.all( 'LastGetDate' ).value = parent.VD.gVSwitch.getVar( "LastGetDate"); } catch(ex) { };
//    try { fm.all( 'LastLoanDate' ).value = parent.VD.gVSwitch.getVar( "LastLoanDate"); } catch(ex) { };
//    try { fm.all( 'SpecFlag' ).value = parent.VD.gVSwitch.getVar( "SpecFlag"); } catch(ex) { };
//    try { fm.all( 'GrpSpec' ).value = parent.VD.gVSwitch.getVar( "GrpSpec"); } catch(ex) { };
//    try { fm.all( 'PayMode' ).value = parent.VD.gVSwitch.getVar( "PayMode"); } catch(ex) { };
//    try { fm.all( 'SignCom' ).value = parent.VD.gVSwitch.getVar( "SignCom"); } catch(ex) { };
//    try { fm.all( 'SignDate' ).value = parent.VD.gVSwitch.getVar( "SignDate"); } catch(ex) { };
//    try { fm.all( 'SignTime' ).value = parent.VD.gVSwitch.getVar( "SignTime"); } catch(ex) { };
//    try { fm.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };
//    try { fm.all( 'PayIntv' ).value = parent.VD.gVSwitch.getVar( "PayIntv"); } catch(ex) { };
//    try { fm.all( 'ManageFeeRate' ).value = parent.VD.gVSwitch.getVar( "ManageFeeRate"); } catch(ex) { };
//    try { fm.all( 'ExpPeoples' ).value = parent.VD.gVSwitch.getVar( "ExpPeoples"); } catch(ex) { };
//    try { fm.all( 'ExpPremium' ).value = parent.VD.gVSwitch.getVar( "ExpPremium"); } catch(ex) { };
//    try { fm.all( 'ExpAmnt' ).value = parent.VD.gVSwitch.getVar( "ExpAmnt"); } catch(ex) { };
//    try { fm.all( 'Peoples' ).value = parent.VD.gVSwitch.getVar( "Peoples"); } catch(ex) { };
//    try { fm.all( 'Mult' ).value = parent.VD.gVSwitch.getVar( "Mult"); } catch(ex) { };
//    try { fm.all( 'Prem' ).value = parent.VD.gVSwitch.getVar( "Prem"); } catch(ex) { };
//    try { fm.all( 'Amnt' ).value = parent.VD.gVSwitch.getVar( "Amnt"); } catch(ex) { };
//    try { fm.all( 'SumPrem' ).value = parent.VD.gVSwitch.getVar( "SumPrem"); } catch(ex) { };
//    try { fm.all( 'SumPay' ).value = parent.VD.gVSwitch.getVar( "SumPay"); } catch(ex) { };
//    try { fm.all( 'Dif' ).value = parent.VD.gVSwitch.getVar( "Dif"); } catch(ex) { };
//    try { fm.all( 'Remark' ).value = parent.VD.gVSwitch.getVar( "Remark"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag1' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag1"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag2' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag2"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag3' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag3"); } catch(ex) { };
//    try { fm.all( 'InputOperator' ).value = parent.VD.gVSwitch.getVar( "InputOperator"); } catch(ex) { };
//    try { fm.all( 'InputDate' ).value = parent.VD.gVSwitch.getVar( "InputDate"); } catch(ex) { };
//    try { fm.all( 'InputTime' ).value = parent.VD.gVSwitch.getVar( "InputTime"); } catch(ex) { };
//    try { fm.all( 'ApproveFlag' ).value = parent.VD.gVSwitch.getVar( "ApproveFlag"); } catch(ex) { };
//    try { fm.all( 'ApproveCode' ).value = parent.VD.gVSwitch.getVar( "ApproveCode"); } catch(ex) { };
//    try { fm.all( 'ApproveDate' ).value = parent.VD.gVSwitch.getVar( "ApproveDate"); } catch(ex) { };
//    try { fm.all( 'ApproveTime' ).value = parent.VD.gVSwitch.getVar( "ApproveTime"); } catch(ex) { };
//    try { fm.all( 'UWOperator' ).value = parent.VD.gVSwitch.getVar( "UWOperator"); } catch(ex) { };
//    try { fm.all( 'UWFlag' ).value = parent.VD.gVSwitch.getVar( "UWFlag"); } catch(ex) { };
//    try { fm.all( 'UWDate' ).value = parent.VD.gVSwitch.getVar( "UWDate"); } catch(ex) { };
//    try { fm.all( 'UWTime' ).value = parent.VD.gVSwitch.getVar( "UWTime"); } catch(ex) { };
//    try { fm.all( 'AppFlag' ).value = parent.VD.gVSwitch.getVar( "AppFlag"); } catch(ex) { };
//    try { fm.all( 'PolApplyDate' ).value = parent.VD.gVSwitch.getVar( "PolApplyDate"); } catch(ex) { };
//    try { fm.all( 'CustomGetPolDate' ).value = parent.VD.gVSwitch.getVar( "CustomGetPolDate"); } catch(ex) { };
//    try { fm.all( 'GetPolDate' ).value = parent.VD.gVSwitch.getVar( "GetPolDate"); } catch(ex) { };
//    try { fm.all( 'GetPolTime' ).value = parent.VD.gVSwitch.getVar( "GetPolTime"); } catch(ex) { };
//    try { fm.all( 'State' ).value = parent.VD.gVSwitch.getVar( "State"); } catch(ex) { };

    //����Ͷ������Ϣ
    //alert("GrpNo"+parent.VD.gVSwitch.getVar( "GrpNo"))

    try { fm.all( 'AppGrpNo' ).value = parent.VD.gVSwitch.getVar( "GrpNo"); } catch(ex) { };

    try { fm.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo"); } catch(ex) { };




}
//��ú�ͬ,Ͷ����,����������Ϣ
function getContInput()
{
    //alert("getContInput:"+parent.VD.gVSwitch.getVar("GrpContNo"))  ;
    //alert("here!");
    //try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    //try{fm.all('ProposalNo').value=parent.VD.gVSwitch.getVar("ProposalContNo");}catch(ex){};
    //alert("fm.ProposalContNo.value=="+fm.ProposalContNo.value);
    try{fm.all('ProposalContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    try{fm.all('ContType').value=parent.VD.gVSwitch.getVar("ContType");}catch(ex){};
    try{fm.all('FamilyType').value=parent.VD.gVSwitch.getVar("FamilyType");}catch(ex){};
    try{fm.all('PolType').value=parent.VD.gVSwitch.getVar("PolType");}catch(ex){};
    try{fm.all('CardFlag').value=parent.VD.gVSwitch.getVar("CardFlag");}catch(ex){};
    try{fm.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{fm.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{fm.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{fm.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{fm.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{fm.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
//    try{fm.all('Handler').value=parent.VD.gVSwitch.getVar("Handler");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{fm.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{fm.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{fm.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{fm.all('AppntIDType').value=parent.VD.gVSwitch.getVar("AppntIDType");}catch(ex){};
    try{fm.all('AppntIDNo').value=parent.VD.gVSwitch.getVar("AppntIDNo");}catch(ex){};
    try{fm.all('CustomerNo').value=parent.VD.gVSwitch.getVar("InsuredNo");}catch(ex){};
    try{fm.all('Name').value=parent.VD.gVSwitch.getVar("Name");}catch(ex){};
    try{fm.all('Sex').value=parent.VD.gVSwitch.getVar("Sex");}catch(ex){};
    try{fm.all('Birthday').value=parent.VD.gVSwitch.getVar("Birthday");}catch(ex){};
    try{fm.all('IDType').value=parent.VD.gVSwitch.getVar("IDType");}catch(ex){};
    try{fm.all('IDNo').value=parent.VD.gVSwitch.getVar("IDNo");}catch(ex){};
    try{fm.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    //try{fm.all('InsuredAppAge').value=parent.VD.gVSwitch.getVar("InsuredAppAge");}catch(ex){};
    try{fm.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
//   try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
//   try{fm.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
//   try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{fm.all('AppntGrade').value=parent.VD.gVSwitch.getVar("AppntGrade");}catch(ex){};
    try{fm.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{fm.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{fm.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('State').value=parent.VD.gVSwitch.getVar("State");}catch(ex){};
    try{fm.all('AppntType').value=parent.VD.gVSwitch.getVar("AppntType");}catch(ex){};
    try{fm.all('Operator').value=parent.VD.gVSwitch.getVar("Operator");}catch(ex){};
    try{fm.all('MakeDate').value=parent.VD.gVSwitch.getVar("MakeDate");}catch(ex){};
    try{fm.all('MakeTime').value=parent.VD.gVSwitch.getVar("MakeTime");}catch(ex){};
    try{fm.all('ModifyDate').value=parent.VD.gVSwitch.getVar("ModifyDate");}catch(ex){};
    try{fm.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
    
    //tongmeng 2009-03-23 Modify
    //֧���ŵ��¸�����Ч�յ�ָ��.
    /*var tCValidate = parent.VD.gVSwitch.getVar("CValiDate");
    var tContCvaliDate = parent.VD.gVSwitch.getVar("ContCValiDate");
    
    alert("tContCvaliDate:"+tContCvaliDate+":tCValidate"+tCValidate)
    if(tContCvaliDate!=null&&tContCvaliDate!='')
    {
    	try{fm.all('CValiDate').value=tContCvaliDate;}catch(ex){};
    }
    else
    {
    	try{fm.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    }*/
    try{fm.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{fm.all('InsuredPeoples').value=parent.VD.gVSwitch.getVar("InsuredPeoples");}catch(ex){};
    //alert("fm.all('CValiDate').value=="+fm.all('InsuredPeoples').value);
//alert("AppntNo"+parent.VD.gVSwitch.getVar("AppntNo"));
//alert("ManageCom"+parent.VD.gVSwitch.getVar("ManageCom"));


}
</script>
