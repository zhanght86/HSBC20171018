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
    //Edorquery();
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
    alert("��PEdorTypeDBInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCustomerGrid();
    initPolGrid();
    getCustomerGrid();
  }
  catch(re)
  {
    alert("PEdorTypeDBInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ������0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="150px";
        iArray[2][2]=300;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�ʻ�����";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="�������";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][23]="0";

	
        iArray[7]=new Array();
        iArray[7][0]="������ȡ";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][9]="������|notnull&num&value>0";
        iArray[7][23]="1";
        
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        
        iArray[9]=new Array();
        iArray[9][0]="�ʻ�����";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;


        iArray[10]=new Array();
        iArray[10][0]="������Ϣ";
        iArray[10][1]="50px";
        iArray[10][2]=0;
        iArray[10][3]=7;
        iArray[10][23]="0";
        
        iArray[11]=new Array();
				iArray[11][0]="����";
				iArray[11][1]="60px";
				iArray[11][2]=100;
				iArray[11][3]=2;
				iArray[11][4]="currency";
        
      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      
        fm.action="./PEdorTypeDBCount.jsp";
        fm.submit();

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
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ������0��ʾ������

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
        iArray[5][3]=8;

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
</script>