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
<SCRIPT src="GrpContInsuredInput.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{
    try
    {
        fm.all('ContType').value=ContType;
        fm.all( 'BQFlag' ).value = BQFlag;
        fm.all( 'EdorType' ).value = EdorType;
        fm.all( 'EdorTypeCal' ).value = EdorTypeCal;
        fm.all( 'EdorValiDate' ).value = EdorValiDate;  
        fm.SelPolNo.value='';
        fm.all('OldContNo').value = "";
        try{mSwitch.deleteVar("PolNo");}catch(ex){};
    }
    catch(ex)
    {
        alert("��GrpInsuredInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
        alert("��GrpInsuredInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    
        initInpBox();
        initSelBox();
        initInsuredGrid();
        initImpartGrid();
        initMainInsuredGrid();
        initInsuredRelaGrid();
        initContGrid();
        
        queryPol();
        initGrpInfo();

        //����֤�����ͳ�ʼ����ֵ��9
        //fm.IDType.value="0";
        
        if (scantype== "scan")
        {
            setFocus();
        }
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
        }
        //alert("c");
        initContInfo();
        //initPolInfo();
        //alert("d");

        DivGrpNoname.style.display="none";
        //alert("d1");
        if(fm.all('FamilyType').value==''||fm.all('FamilyType').value==null||fm.all('FamilyType').value=='0'||fm.all('FamilyType').value=='false'){
            fm.all('FamilyType').value='0';
            getAge(); 
            //alert(NameType);
            if(NameType=="1")
            {
                DivLCBasicInfo.style.display="none";     
                //fm.all('PolTypeFlagName').value="������"; 
                fm.all('Sex').value="0"; 
                fm.all('InsuredPeoples').readOnly=false;
                        fm.all('InsuredAppAge').readOnly=false; 
                fm.all('Name').value="������"; 
                //fm.all('PolTypeFlag').value="1";
            }
            else if(NameType=="2")
            {
                DivLCBasicInfo.style.display="none"; 
                //fm.all('PolTypeFlag').value="2";
                fm.all('InsuredPeoples').readOnly=false;
                fm.all('InsuredAppAge').readOnly=false;     
                //fm.all('PolTypeFlagName').value="�����ʻ�"; 
                fm.all('Sex').value="0"; 
                fm.all('Name').value="�����ʻ�"; 
            }else{
            
                //fm.all('PolTypeFlag').value="0";
                //fm.all('PolTypeFlagName').value="���˵�";
                fm.all('InsuredPeoples').value="";
                fm.all('InsuredPeoples').value="1";
            }
        }

        try
        {
    }
    catch(re)
    {
        alert("GrpInsuredInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
    if (ContType=="2")
    {
        initContPlanCode();
        //initExecuteCom();
    }

    if(LoadFlag=="18"){ //��������¼
        fm.InsuredSequencename.value="������������";
        divaddPerButton.style.display="";
        fm.butBack.style.display="";
        divSamePerson.style.display="none";
        DivRelation.style.display="none";
        //DivAddress.style.display="";
        DivClaim.style.display="none";
        divContPlan.style.display="none";
        divAnotherAddDelButton.style.display="";
        if(checkISFILL()) //�Ƿ���Ҫ�����ж�
        {
          checkGrpContType(); //�жϱ������ͣ�ȷ����ѯ���
        }
    }
    
    //�����ĳ�ʼ��
    //if(fm.PolTypeFlag.value=="0")
    //{
    //    fm.Name.focus();
    //}
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
//      iArray[1][4]="ImpartVer";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][10]="AppntImpart";
      iArray[1][11]="0|^01|����������֪^02|������֪";
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


function initContInfo()
{
    try { fm.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(fm.all( 'ContNo' ).value=="false"){fm.all( 'ContNo' ).value="";} } catch(ex) { };
    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
    try { fm.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalGrpContNo" ); } catch(ex) { };
    try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { fm.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    //try { fm.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(fm.all( 'PolTypeFlag' ).value=="false"){fm.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    try { fm.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(fm.all( 'InsuredPeoples' ).value=="false"){fm.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
}

function initContPlanCode()
{
	 fm.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));
}

function initExecuteCom()
{
	 //fm.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));
}  

function initGrpInfo()
{
	fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	fm.ManageCom.value=mSwitch.getVar('ManageCom');
	fm.AgentCode.value=mSwitch.getVar('AgentCode');
	fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
	fm.GrpName.value=mSwitch.getVar('GrpName');
	fm.CValiDate.value=mSwitch.getVar('CValiDate');
	fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}

//����������Ϣ
function initMainInsuredGrid() {
    var iArray = new Array();

    try {
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

        iArray[7]=new Array();
        iArray[7][0]="��������";      	   		//����
        iArray[7][1]="80px";            			//�п�
        iArray[7][2]=20;            			//�����ֵ
        iArray[7][3]=3;

        MainInsuredGrid = new MulLineEnter( "fm" , "MainInsuredGrid" );
        //��Щ���Ա�����loadMulLineǰ
        MainInsuredGrid.mulLineCount = 0;
        MainInsuredGrid.displayTitle = 1;
        MainInsuredGrid.canSel =1;
        MainInsuredGrid. selBoxEventFuncName ="getInsuredRela" ;     //���RadioBoxʱ��Ӧ��JS����
        MainInsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        MainInsuredGrid. hiddenSubtraction=1;
        MainInsuredGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

//������Ϣ
function initContGrid() {
    var iArray = new Array();

    try {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="40px"; 	           		//�п�
        iArray[0][2]=1;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ͬ��";          		//����
        iArray[1][1]="120px";      	      		//�п�
        iArray[1][2]=20;            			//�����ֵ
        iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

        iArray[2]=new Array();
        iArray[2][0]="���ֺ�";         			//����
        iArray[2][1]="60px";            			//�п�
        iArray[2][2]=20;            			//�����ֵ
        iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


        iArray[3]=new Array();
        iArray[3][0]="��������";      	   		//����
        iArray[3][1]="120px";            			//�п�
        iArray[3][2]=20;            			//�����ֵ
        iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="��¼������������";      	   		//����
        iArray[4][1]="80px";            			//�п�
        iArray[4][2]=20;            			//�����ֵ
        iArray[4][3]=0;

        //iArray[5]=new Array();
        //iArray[5][0]="���һ�������˹�ϵ";      	   		//����
        //iArray[5][1]="100px";            			//�п�
        //iArray[5][2]=20;            			//�����ֵ
        //iArray[5][3]=3;
        //iArray[5][4]="Relation";              	        //�Ƿ����ô���:null||""Ϊ������
        //iArray[5][9]="�����������˹�ϵ|code:Relation&NOTNULL";
        //iArray[5][18]=300;

        //iArray[6]=new Array();
        //iArray[6][0]="�ͻ��ڲ���";      	   		//����
        //iArray[6][1]="80px";            			//�п�
        //iArray[6][2]=20;            			//�����ֵ
        //iArray[6][3]=3;
        //iArray[6][10]="MutiSequenceNo";
        //iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";

        iArray[5]=new Array();
        iArray[5][0]="������";      	   		//����
        iArray[5][1]="80px";            			//�п�
        iArray[5][2]=20;            			//�����ֵ
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="�Ƿ�У�������������˱�־";      	   		//����
        iArray[6][1]="20px";            			//�п�
        iArray[6][2]=20;            			//�����ֵ
        iArray[6][3]=3;

        ContGrid = new MulLineEnter( "fm" , "ContGrid" );
        //��Щ���Ա�����loadMulLineǰ
        ContGrid.mulLineCount = 0;
        ContGrid.displayTitle = 1;
        ContGrid.canSel =1;
        ContGrid. selBoxEventFuncName ="getInsuredInfo" ;     //���RadioBoxʱ��Ӧ��JS����
        ContGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ContGrid. hiddenSubtraction=1;
        ContGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}


//������������Ϣ
function initInsuredRelaGrid() {
    var iArray = new Array();

    try {
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
        iArray[5][0]="�����������˹�ϵ";      	   		//����
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
        iArray[6][3]=3;
        iArray[6][10]="MutiSequenceNo";
        iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";

        iArray[7]=new Array();
        iArray[7][0]="��������";      	   		//����
        iArray[7][1]="80px";            			//�п�
        iArray[7][2]=20;            			//�����ֵ
        iArray[7][3]=3;

        InsuredRelaGrid = new MulLineEnter( "fm" , "InsuredRelaGrid" );
        //��Щ���Ա�����loadMulLineǰ
        InsuredRelaGrid.mulLineCount = 0;
        InsuredRelaGrid.displayTitle = 1;
        InsuredRelaGrid.canSel =0;
        //InsuredRelaGrid. selBoxEventFuncName ="getInsuredRela" ;     //���RadioBoxʱ��Ӧ��JS����
        InsuredRelaGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        InsuredRelaGrid. hiddenSubtraction=1;
        InsuredRelaGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
