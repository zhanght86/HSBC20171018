<%
//�������ƣ�PEdorTypePLInit.jsp
//�����ܣ�
//�������ڣ�2005-5-24 11:12����
//������  ��Lihs
//���¼�¼��  ������    ��������     ����ԭ��/����
//             liurx    2005-06-28
%>
<%
   String mCurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initPolGrid();
    getPolInfo();
    initCustomerGrid();
    chkPol();
    ShowCustomerInfo();
    querySignDate();
    var tDate;
    if(fm.Flag.value == "0")
    {
      tDate = '<%=mCurrentDate%>';
    }
    else
    {
    	//modify by liuxiaosong ��ȫ���˵�ʱ����ֶε��´��� 2007-1-15
      	tDate = document.all('EdorItemAppDate').value;      	    
    }
    //alert(tDate);
    ReportByLoseFormV(tDate); //�ж��ǹ�ʧ���ǽ��
  }
  catch(re)
  {
    alert("PEdorTypePLInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initInpBox()
{
  Edorquery();
  try
  {
     document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
     document.all('ContNo').value = top.opener.document.all('ContNo').value;
     document.all('EdorType').value = top.opener.document.all('EdorType').value;
     document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
     document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
     document.all('PolNo').value = top.opener.document.all('PolNo').value;
     document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
     document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
     showOneCodeName('PEdorType','EdorType','EdorTypeName');
  }
  catch(ex)
  {
     alert("��PEdorTypePLInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypePLInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��ɫ";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=8;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="120px";
        iArray[7][2]=150;
        iArray[7][3]=0;

      CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 1;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canChk=0;
      CustomerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
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
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�����˺�";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="����";
        iArray[2][1]="70px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="���ִ���";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="200px";
        iArray[4][2]=300;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="���ѱ�׼";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="��Ч����";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=8;

        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="���屣������";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
				iArray[10][0]="����";
				iArray[10][1]="60px";
				iArray[10][2]=100;
				iArray[10][3]=2;
				iArray[10][4]="currency";

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.canChk=0;
      PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>