<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Supl
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->

<%

%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    Edorquery();
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeCTInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    //initInusredGrid();
    initCustomerGrid();
    initPolGrid();
    getCustomerGrid();
    getPolGrid(document.all('ContNo').value);
    chkPol();  //�ϴα��˱������ִ�
    getZTMoney();
    getContZTInfo();
    //initCTFeePolGrid();
    //getCTFeePolGrid();
    //initCTFeeDetailGrid();
    //getCTFeeDetailGrid();
    getAgentToAppntRelation("CT");
    queryLRInfo();
    initCustomerActBnfGrid();
    getCustomerActBnfGrid();
  }
  catch(re)
  {
    alert("PEdorTypeCTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//function initInusredGrid()
//
//{
//    var iArray = new Array();
//
//      try
//      {
//        iArray[0]=new Array();
//        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
//        iArray[0][1]="30px";                  //�п�
//        iArray[0][2]=30;                      //�����ֵ
//        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������
//
//        iArray[1]=new Array();
//        iArray[1][0]="�ͻ�����";
//        iArray[1][1]="80px";
//        iArray[1][2]=100;
//        iArray[1][3]=0;
//
//
//        iArray[2]=new Array();
//        iArray[2][0]="����";
//        iArray[2][1]="100px";
//        iArray[2][2]=100;
//        iArray[2][3]=0;
//
//        iArray[3]=new Array();
//        iArray[3][0]="�Ա�";
//        iArray[3][1]="80px";
//        iArray[3][2]=100;
//        iArray[3][3]=0;
//
//        iArray[4]=new Array();
//        iArray[4][0]="��������";
//        iArray[4][1]="80px";
//        iArray[4][2]=100;
//        iArray[4][3]=0;
//
//
//        iArray[5]=new Array();
//        iArray[5][0]="��������";
//        iArray[5][1]="0px";
//        iArray[5][2]=0;
//        iArray[5][3]=3;
//
//        iArray[6]=new Array();
//        iArray[6][0]="���屣������";
//        iArray[6][1]="0px";
//        iArray[6][2]=0;
//        iArray[6][3]=3;
//
//        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
//        //��Щ���Ա�����loadMulLineǰ
//        InsuredGrid.mulLineCount = 0;
//        InsuredGrid.displayTitle = 1;
//        //alert(fm.DisplayType.value);
//        InsuredGrid.canChk=0;
//        InsuredGrid.canSel =1;
//        //InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
//        InsuredGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
//        InsuredGrid.hiddenSubtraction=1;
//        InsuredGrid.loadMulLine(iArray);
//
//      //��Щ����������loadMulLine����
//      }
//      catch(ex)
//      {
//        alert(ex);
//      }
//}

function initPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="200px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����������";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�����ӷ�";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="ְҵ�ӷ�";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="��Ч����";
        iArray[9][1]="80px";
        iArray[9][2]=100;
        iArray[9][3]=0;
        
        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
        iArray[11]=new Array();
        iArray[11][0]="���պ���";
        iArray[11][1]="0px";
        iArray[11][2]=0;
        iArray[11][3]=3;


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      PolGrid.chkBoxEventFuncName="checkMainPol";
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initCustomerGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="93px";
        iArray[1][2]=150;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="92px";
        iArray[2][2]=150;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="98px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="77px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="77px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="115px";
        iArray[6][2]=150;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="115px";
        iArray[7][2]=150;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="Ͷ�����뱻���˹�ϵ";
        iArray[8][1]="115px";
        iArray[8][2]=150;
        iArray[8][3]=0;

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 3;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      //PolGrid.canChk=1;
      CustomerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ;
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCustomerActBnfGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="����";
        iArray[1][1]="93px";
        iArray[1][2]=150;
        iArray[1][3]=1;
        iArray[1][9]="����|NotNull&Len<=60";

         iArray[2]=new Array();
        iArray[2][0]="�Ա�";
        iArray[2][1]="30px";
        iArray[2][2]=150;
        iArray[2][3]=2;
        iArray[2][4]="sex";

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="80px";
        iArray[3][2]=150;
        iArray[3][3]=1;
        iArray[3][9] = "��������|Date";

        iArray[4]=new Array();
        iArray[4][0]="֤������";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3] = 2;
        iArray[4][4] = "IDType";
        iArray[4][9] = "֤������|NotNull&Code:IDType";

        iArray[5]=new Array();
        iArray[5][0]="֤������";
        iArray[5][1]="77px";
        iArray[5][2]=100;
        iArray[5][3] = 1;
        iArray[5][9] = "֤������|NotNull&Len<=20";


//        iArray[6]=new Array();
//        iArray[6][0]="�뱻���˹�ϵ";
//        iArray[6][1]="60px";
//        iArray[6][2]=150;
//        iArray[6][3]=2;
//        iArray[6][4] = "Relation";
//        iArray[6][9] = "�뱻���˹�ϵ|NotNull&Code:Relation"; 
      

        iArray[6] = new Array();
        iArray[6][0] = "��ȡ����";
        iArray[6][1] = "80px";
        iArray[6][2] = 100;
        iArray[6][3] = 1;
        iArray[6][9] = "��ȡ����|NotNull&Num&Value>=0&Value<=1";

        iArray[7]=new Array();
        iArray[7][0]="��ȡ��ʽ";
        iArray[7][1]="70px";
        iArray[7][2]=150;
        iArray[7][3]=2;
        iArray[7][9] = "��ȡ��ʽ|NotNull";
        iArray[7][10]="LGGetMode"; //���������Ψһ��
        iArray[7][11]= "0|^1|�ֽ�^4|����ת��^9|��������" ; 

		//modify by jiaqiangli 2009-06-23 ����ת�����������������б��벻��ͬ
        iArray[8] = new Array();
        iArray[8][0] = "���б���";
        iArray[8][1] = "80px";
        iArray[8][2] = 100;
        iArray[8][3] = 2;
        //iArray[8][4] = "bank";
        iArray[8][7] = "GetBankCode";
        iArray[8][9] = "���б���";
                        
        iArray[9] = new Array();
        iArray[9][0] = "�����ʺ�";
        iArray[9][1] = "120px";
        iArray[9][2] = 100;
        iArray[9][3] = 1;
        iArray[9][22]="confirmSecondInput1"
                        
        iArray[10] = new Array();
        iArray[10][0] = "�����ʻ���";
        iArray[10][1] = "80px";
        iArray[10][2] = 100;
        iArray[10][3] = 1;
 

        CustomerActBnfGrid = new MulLineEnter( "fm" , "CustomerActBnfGrid" );
      //��Щ���Ա�����loadMulLineǰ
      CustomerActBnfGrid.mulLineCount = 0;
      CustomerActBnfGrid.displayTitle = 1;
      CustomerActBnfGrid.canSel=0;
      CustomerActBnfGrid.canAdd=1;
      CustomerActBnfGrid.hiddenPlus=0;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CustomerActBnfGrid.hiddenSubtraction=0;
      CustomerActBnfGrid.loadMulLine(iArray);
      CustomerActBnfGrid.selBoxEventFuncName ="" ;
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
