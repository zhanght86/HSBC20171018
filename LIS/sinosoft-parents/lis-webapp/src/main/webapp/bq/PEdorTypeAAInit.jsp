<%
//PEdorTypeAAInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //����ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
//����ʱ��ѯ

function initInpBox()
{

  try
  {
    Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeAAInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeAAInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
       initInpBox();
       initCustomerGrid();
       initPolGrid();
       initMainPolGrid();
       getMainPolInfo();
       queryCustomerInfo();
       getPolInfo();
  }
  catch(re)
  {
     alert("PEdorTypeAAInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//������Ϣ�б�
function initPolGrid()
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
      iArray[1][0]="���ִ���";                  //����1
      iArray[1][1]="50px";                  //�п�
      iArray[1][2]=30;                      //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";                  //����2
      iArray[2][1]="100px";                 //�п�
      iArray[2][2]=50;                      //�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="ԭ����/ԭ����";                 //����8
      iArray[3][1]="60px";                  //�п�
      iArray[3][2]=20;                      //�����ֵ
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="ԭ����";     //����6
      iArray[4][1]="50px";                  //�п�
      iArray[4][2]=20;                      //�����ֵ
      iArray[4][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���º󱣶�";     //����6
      iArray[5][1]="60px";                  //�п�
      iArray[5][2]=30;                      //�����ֵ
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="���º����";     //����6
      iArray[6][1]="50px";                  //�п�
      iArray[6][2]=30;                      //�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="�ӱ�����";     //����6
      iArray[7][1]="50px";                  //�п�
      iArray[7][2]=30;                      //�����ֵ
      iArray[7][3]=0;


      iArray[8]=new Array();
      iArray[8][0]="���ֺ�";     //����6
      iArray[8][1]="0px";                  //�п�
      iArray[8][2]=30;                     //�����ֵ
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="���۱��(������/����)";     //����6
      iArray[9][1]="100px";                  //�п�
      iArray[9][2]=30;                     //�����ֵ
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="���۱��(������/����)";     //����6
      iArray[10][1]="0px";                  //�п�
      iArray[10][2]=0;                     //�����ֵ
      iArray[10][3]=3;


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canSel=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);
      PolGrid.detailInfo="������ʾ��ϸ��Ϣ";
      }
      catch(ex)
      {

      }
}

//�ͻ�������Ϣ�б�
function initCustomerGrid()
{
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";                  //�п�
      iArray[1][2]=10;                      //�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ɫ";                  //����1
      iArray[2][1]="60px";                  //�п�
      iArray[2][2]=50;                      //�����ֵ
      iArray[2][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";                  //����2
      iArray[3][1]="60px";                  //�п�
      iArray[3][2]=60;                      //�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ա�";              //����8
      iArray[4][1]="60px";                  //�п�
      iArray[4][2]=30;                      //�����ֵ
      iArray[4][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";     //����6
      iArray[5][1]="60px";                  //�п�
      iArray[5][2]=50;                      //�����ֵ
      iArray[5][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";              //����8
      iArray[6][1]="100px";                 //�п�
      iArray[6][2]=30;                      //�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="֤������";              //����5
      iArray[7][1]="150px";                 //�п�
      iArray[7][2]=100;                     //�����ֵ
      iArray[7][3]=0;                       //�Ƿ���������,1��ʾ������0��ʾ������,2��ʾ��������



      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 0;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.selBoxEventFuncName ="";
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.detailInfo="������ʾ��ϸ��Ϣ";
      CustomerGrid.loadMulLine(iArray);
      }
      catch(ex)
      {

      }
}

//

function initMainPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ������0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�������˺���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���ִ���";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Ч����";
        iArray[7][1]="50px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="�����ڼ�";
        iArray[9][1]="40px";
        iArray[9][2]=100;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="40px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="��������";
        iArray[10][1]="50px";
        iArray[10][2]=100;
        iArray[10][3]=0;

      MainPolGrid = new MulLineEnter( "fm" , "MainPolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      MainPolGrid.mulLineCount = 0;
      MainPolGrid.displayTitle = 1;
      //MainPolGrid.canChk=1;
      MainPolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      MainPolGrid.hiddenSubtraction=1;
      MainPolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>