 <%
//�������ƣ�TempFeeInit.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="ContInsuredInput.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>
<script language="JavaScript">
function initInpBox()
{
  try
  {
    document.all('ContType').value=ContType;
    document.all( 'BQFlag' ).value = BQFlag;
    document.all( 'EdorType' ).value = EdorType;
    document.all( 'EdorTypeCal' ).value = EdorTypeCal;
    document.all( 'EdorValiDate' ).value = EdorValiDate;  
  	fm.SelPolNo.value='';
  	try{mSwitch.deleteVar("PolNo");}catch(ex){};
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
   if(checktype=="1")
   {
     param="121";
     fm.pagename.value="121";
   }
   if(checktype=="2")
   {
     param="22";
     fm.pagename.value="22";
   }
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
  	//alert("@@"+top.opener.parent.VD.gVSwitch.getVar( "ContCValiDate" ));
    initInpBox();
    initSelBox();
    initInsuredGrid();
    initImpartGrid();
    //initImpartDetailGrid();

    initPolGrid();
    initGrpInfo();
    
	//����֤�����ͳ�ʼ����ֵ��9
	fm.IDType.value="9";
    if (scantype== "scan")
    {

      setFocus();
    }
	//alert(Auditing);
    if(Auditing=="1")
    {
    	var tmSwitch = top.opener.parent.VD.gVSwitch;
			mSwitch.deleteVar( "ContNo" );
  		mSwitch.addVar( "ContNo", "", tmSwitch.getVar("ContNo") );
  		mSwitch.updateVar("ContNo", "", tmSwitch.getVar("ContNo"));
  		mSwitch.deleteVar( "ProposalContNo" );
  		mSwitch.addVar( "ProposalContNo", "", tmSwitch.getVar("ContNo") );
  		mSwitch.updateVar("ProposalContNo", "", tmSwitch.getVar("ContNo"));
  		mSwitch.deleteVar( "PrtNo" );
  		mSwitch.addVar( "PrtNo", "", tmSwitch.getVar("PrtNo") );
  		mSwitch.updateVar("PrtNo", "", tmSwitch.getVar("PrtNo"));
  		mSwitch.deleteVar( "GrpContNo" );
  		mSwitch.addVar( "GrpContNo", "", tmSwitch.getVar("GrpContNo") );
  		mSwitch.updateVar("GrpContNo", "", tmSwitch.getVar("GrpContNo"));

  		//tongmeng 2009-03-20 add
  		//������Ч��
 // 		mSwitch.deleteVar( "ContCValidate" );
 // 		mSwitch.addVar( "ContCValidate", "", tmSwitch.getVar("ContCValidate") );
 // 		mSwitch.updateVar("ContCValidate", "", tmSwitch.getVar("ContCValidate"));
  		
    }
    getContInfo();
    getInsuredInfo();
	getoccupationtype();
    DivGrpNoname.style.display="";
    //alert(document.all('FamilyType').value);
    if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false'){
      document.all('FamilyType').value='0';
      divTempFeeInput.style.display="none";
      getProposalInsuredInfo();          //��ø��˵���Ϣ����д�����˱�
      getoccupationtype();
      getAge(); 
      //alert(NameType);
    if(NameType=="1")
      {
      DivLCBasicInfo.style.display="none";     
        document.all('PolTypeFlagName').value="������"; 
        document.all('Sex').value="0"; 
        document.all('InsuredPeoples').readOnly=false;
        document.all('InsuredAppAge').readOnly=false; 
        document.all('Name').value="������"; 
        document.all('PolTypeFlag').value="1";
      }
    else if(NameType=="2")
       {
        DivLCBasicInfo.style.display="none"; 
        document.all('PolTypeFlag').value="2";
        document.all('InsuredPeoples').readOnly=true;
        document.all('InsuredAppAge').readOnly=false;     
        document.all('PolTypeFlagName').value="�����ʻ�"; 
        document.all('Sex').value="0"; 
        document.all('Name').value="�����ʻ�"; 
        document.all('InsuredPeoples').value="0";
        divContPlan.style.display = "none";    	  
            	  
                
       } else if(NameType=="3")
       {
        DivLCBasicInfo.style.display="none"; 
        document.all('PolTypeFlag').value="3";
        document.all('InsuredPeoples').readOnly=true;
        document.all('InsuredAppAge').readOnly=false;     
        document.all('PolTypeFlagName').value="�����ʻ�"; 
        document.all('Sex').value="0"; 
        document.all('Name').value="�����ʻ�"; 
        document.all('InsuredPeoples').value="0";
        divContPlan.style.display = "none";     	  
         
                
       }
       else{
       
        document.all('PolTypeFlag').value="0";
        document.all('PolTypeFlagName').value="���˵�������˻�";
        document.all('InsuredPeoples').value="";
        document.all('InsuredPeoples').value="1";
       }
    }
    

  }
  catch(re)
  {
    alert("ContInsuredInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
  if (ContType=="2")
  {
  	initContPlanCode();
	  initExecuteCom();


  }
  if(LoadFlag=="1"){
       divLCInsuredPerson.style.display="none";
       //divFamilyType.style.display="";
       divPrtNo.style.display="";
       divSamePerson.style.display="";
       DivGrpNoname.style.display="none";
       fm.PolTypeFlag.value='0';
  }
  if(LoadFlag=="2"){
   fm.InsuredSequencename.value="������������";
   //divFamilyType.style.display="";
   divSamePerson.style.display="none";
   divPrtNo.style.display="none";
   divTempFeeInput.style.display="none";
   divInputContButton.style.display="none";
   divGrpInputContButton.style.display="";
   divLCInsuredPerson.style.display="none";
   DivRelation.style.display="none";
   //DivAddress.style.display="none";
   DivClaim.style.display="none";
  }
  if(LoadFlag=="3"){
    divTempFeeInput.style.display="";
    getInsuredInfo();
  }
  if(LoadFlag=="4"){
    fm.InsuredSequencename.value="������������";
    //divFamilyType.style.display="none";
    divPrtNo.style.display="none";
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divTempFeeInput.style.display="none";
    divAddDelButton.style.display="none";
    divSamePerson.style.display="none";
    divLCInsuredPerson.style.display="none";
    divCheckInsuredButton.style.display="none";
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";
    getInsuredInfo();
    //���ӿؼ�Ϊֻ��

    document.all('Name').readOnly=true;
    document.all('Sex').readOnly=true;
    document.all('IDType').readOnly=true;
    document.all('IDNo').readOnly=true;
    document.all('OccupationCode').readOnly=true;
    document.all('Mobile').readOnly=true;
    document.all('OccupationType').readOnly=true;
    document.all('WorkNo').readOnly=true;
    document.all('Birthday').readOnly=true;
    document.all('EMail').readOnly=true;
    document.all('JoinCompanyDate').readOnly=true;
    document.all('Salary').readOnly=true;
    document.all('ExecuteCom').readOnly=true;
    document.all('ContPlanCode').readOnly=true;
  }
  if(LoadFlag=="5"||LoadFlag=="25"){
    divTempFeeInput.style.display="";
    getInsuredInfo();
    //divFamilyType.style.display="";
    divAddDelButton.style.display="";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divLCInsuredPerson.style.display="none";


  }


  if(LoadFlag=="6"){
    divTempFeeInput.style.display="";
    divPrtNo.style.display="";
    divInputContButton.style.display="none";
    divQueryButton.style.display="";
    //divFamilyType.style.display="";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";
  }
  if(LoadFlag=="7"){
   divFamilyType.style.display="none";
   divSamePerson.style.display="none";
   divPrtNo.style.display="none";
   divTempFeeInput.style.display="none";
   divInputContButton.style.display="none";
   divGrpInputContButton.style.display="";
   DivRelation.style.display="none";
   divLCInsuredPerson.style.display="none";
   fm.InsuredSequencename.value="������������";
   //alert("aa");
   //fm.ContCValiDate.value=EdorValiDate;
   //if(fm.ContCValiDate.value == null || fm.ContCValiDate.value == "null" ){
   	//	EdorValiDate = mSwitch.getVar('CValiDate');
   	//	fm.ContCValiDate.value=EdorValiDate;
   	//}
   //alert("bb");
  }
  if(LoadFlag=="8"){
   divAddDelButton.style.display="none";
   divFamilyType.style.display="none";
   divSamePerson.style.display="none";
   divPrtNo.style.display="none";
   divTempFeeInput.style.display="none";
   divInputContButton.style.display="none";
   divGrpInputContButton.style.display="";
   DivRelation.style.display="none";
   divLCInsuredPerson.style.display="none";
   fm.InsuredSequencename.value="������������";
  }
    if(LoadFlag=="16"){
    fm.InsuredSequencename.value="������������";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divQueryButton.style.display="";
    divFamilyType.style.display="none";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";
    divLCInsuredPerson.style.display="none";
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";
    DivAddress.style.display="";
    
    //���ӿؼ�Ϊֻ��

    document.all('Name').readOnly=true;
    document.all('Sex').readOnly=true;
    document.all('IDType').readOnly=true;
    document.all('IDNo').readOnly=true;
    document.all('OccupationCode').readOnly=true;
    document.all('Mobile').readOnly=true;
    document.all('OccupationType').readOnly=true;
    document.all('WorkNo').readOnly=true;
    document.all('Birthday').readOnly=true;
    document.all('EMail').readOnly=true;
    document.all('JoinCompanyDate').readOnly=true;
    document.all('Salary').readOnly=true;
    document.all('ExecuteCom').readOnly=true;
    document.all('ContPlanCode').readOnly=true;
  }
  if(LoadFlag=="18"){//��������¼
    fm.InsuredSequencename.value="������������";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divQueryButton.style.display="none";
    divaddPerButton.style.display="";
    divFamilyType.style.display="none";
    divAddDelButton.style.display="";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";
    divLCInsuredPerson.style.display="none";
    DivRelation.style.display="none";
    DivAddress.style.display="";
    DivClaim.style.display="none";
    divContPlan.style.display="none";
    //
    divAddDelButton.style.display="none";
    divAnotherAddDelButton.style.display="";
    DivLCPol.style.display="none";
    //��ʼ��ְҵ���
    InitOccupationType();
    //alert(vContNo);
  }
  if(LoadFlag=="13"){
   divFamilyType.style.display="none";
   divSamePerson.style.display="none";
   divPrtNo.style.display="none";
   divTempFeeInput.style.display="none";
   divInputContButton.style.display="none";
   divLCInsuredPerson.style.display="none";
   divApproveModifyContButton.style.display="";
   DivRelation.style.display="none";
   getInsuredInfo();
  }
  if(this.LoadFlag == "99"&&checktype=="1"){
       divAddDelButton.style.display="none";
       divInputContButton.style.display="none";
       autoMoveButton.style.display="";
 }
   if(this.LoadFlag == "99"&&checktype=="2"){
       divAddDelButton.style.display="none";
       divInputContButton.style.display="none";
       autoMoveButton.style.display="";
 }
 if(this.display == "0")
   {
     divFamilyType.style.display="none";

     //���޸Ŀͻ���Ϣʱ���ͻ��Ų��ɱ༭
     fm.InsuredNo.readOnly=true;
   } 
   //�����ĳ�ʼ��
   //alert(fm.PolTypeFlag.value);
   if(fm.PolTypeFlag.value=="0")
   {
   fm.Name.focus();
   }
   //
   if(document.all('PolTypeFlag').value=="2"||document.all('PolTypeFlag').value=="3")
   {
     document.all('InsuredPeoples').readOnly=true;
   }
   
}


// ���շ���Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {
    var iArray = new Array();
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
      iArray[5][9]="�����������˹�ϵ|code:Relation";
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
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
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
      iArray[1][4]="GrpImpartVer";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^01|����������֪^02|������֪";
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

//      iArray[5]=new Array();
//      iArray[5][0]="��֪�ͻ�����";         		//����
//      iArray[5][1]="90px";            		//�п�
//      iArray[5][2]=90;            			//�����ֵ
//      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="��֪�ͻ�����|len<=18";
//
//      iArray[6]=new Array();
//      iArray[6][0]="��֪�ͻ�����";         		//����
//      iArray[6][1]="90px";            		//�п�
//      iArray[6][2]=90;            			//�����ֵ
//      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="��֪�ͻ�����";
//      iArray[6][15]="Cont";

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
// ��֪��ϸ��Ϣ�б�ĳ�ʼ��
function initImpartDetailGrid() {
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
      iArray[1][4]="GrpImpartVer";
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
      //iArray[2][7]="getImpartCode";
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
      iArray[4][0]="��ʼʱ��";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��ʼʱ��|date";

      iArray[5]=new Array();
      iArray[5][0]="����ʱ��";         		//����
      iArray[5][1]="90px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="����ʱ��|date";

      iArray[6]=new Array();
      iArray[6][0]="֤����";         		//����
      iArray[6][1]="90px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="Ŀǰ���";         		//����
      iArray[7][1]="90px";            		//�п�
      iArray[7][2]=90;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[8]=new Array();
      iArray[8][0]="�ܷ�֤��";         		//����
      iArray[8][1]="90px";            		//�п�
      iArray[8][2]=90;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][4]="yesno";


      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ImpartDetailGrid.mulLineCount = 1;
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);

    }
    catch(ex) {
      alert(ex);
    }
}
//������������Ϣ�б��ʼ��
function initPolGrid(){
    var iArray = new Array();


    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���յ����ֺ���";         		//����
      iArray[1][1]="60px";            		        //�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]=" ���ֱ���";         		//����
      iArray[2][1]="60px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]=" ����(Ԫ)";         		//����
      iArray[3][1]="60px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����(Ԫ)";         		//����
      iArray[4][1]="60px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����״̬";         		//����
      iArray[5][1]="60px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������



      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}


function getContInfo()

{
    try { document.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(document.all( 'ContNo' ).value=="false"){document.all( 'ContNo' ).value="";} } catch(ex) { };
    try { document.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
   // try { document.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalContNo" ); } catch(ex) { };
    try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { document.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    try { document.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(document.all( 'PolTypeFlag' ).value=="false"){document.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    //yaory
    try { document.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(document.all( 'InsuredPeoples' ).value=="false"){document.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
	//tongmeng 2009-03-20 add
	//������Ч��
//	alert(mSwitch.getVar( "ContCValiDate" ));
 //   try { document.all( 'ContCValiDate' ).value = mSwitch.getVar( "ContCValiDate" );if(document.all( 'ContCValiDate' ).value=="false"){document.all( 'ContCValiDate' ).value="";} } catch(ex) {};

}
function initContPlanCode()
{
	 document.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));
}
function initExecuteCom()
{
	 document.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));
}  

function initGrpInfo()
{
	fm.PrtNo.value=mSwitch.getVar('PrtNo');
	fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	fm.ManageCom.value=mSwitch.getVar('ManageCom');
	fm.AgentCode.value=mSwitch.getVar('AgentCode');
	fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
	fm.GrpName.value=mSwitch.getVar('GrpName');
	fm.CValiDate.value=mSwitch.getVar('CValiDate');
	fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}
</script>
