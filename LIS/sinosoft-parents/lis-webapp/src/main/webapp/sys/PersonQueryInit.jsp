<%
//�������ƣ�ProposalQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

function initForm()
{
    initInpBox();
    initCustomerGrid();
    initAddressGrid();
    initAccountGrid();
    initDisplayButton();
    //initQuery();
    //initManageCom();//��ʼ��������������ȡ6λ
}


function initQuery()
{
    try
    {
        //alert("asdfsdaf"+top.opener.document.all('ContNo'));
        var tContNo = top.opener.document.all('ContNo').value;
        //alert(tContNo);
        if (tContNo!=""&&tContNo!=null)
        {
            document.all('ContNo').value = tContNo;
            easyQueryClick();
        }
     }
     catch(ex)
     {
     }
}
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
    // ������ѯ����
    
      document.all('Name').value = '';
      document.all('Sex').value = '';
      document.all('BIRTHDAY').value = '';
      document.all('IDNo').value = '';
  }
  catch(ex)
  {
      alert("�� AllProposalQueryInit.jsp --> InitInpBox �����з����쳣:��ʼ���������!");
  }
}

// �ͻ���Ϣ�б�ĳ�ʼ��
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�ͻ���";
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��λ����";
        iArray[2][1]="50px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="30px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="֤������";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="֤������";
        iArray[7][1]="130px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerGrid.mulLineCount = 5;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.locked = 1;
        CustomerGrid.canSel = 1;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.loadMulLine(iArray);
        
        CustomerGrid.selBoxEventFuncName = "QueryClick2";
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initAddressGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ַ����";
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="ͨѶ��ַ";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="ͨѶ�绰";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��ͥ��ַ";
        iArray[4][1]="120px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��ͥ�绰";
        iArray[5][1]="80px";
        iArray[5][2]=150;
        iArray[5][3]=0;


		iArray[6]=new Array();
        iArray[6][0]="�ֻ�����";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
		
        AddressGrid = new MulLineEnter("fm", "AddressGrid");
        //��Щ���Ա�����loadMulLineǰ
        AddressGrid.mulLineCount = 5;
        AddressGrid.displayTitle = 1;
        AddressGrid.locked = 1;
        AddressGrid.canSel = 0;
        AddressGrid.hiddenPlus = 1;
        AddressGrid.hiddenSubtraction = 1;
        AddressGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


// ������Ϣ�б�ĳ�ʼ��
function initAccountGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="�Ƿ�Ĭ���˻�";
        iArray[1][1]="100px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���б���";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�����ʺ�";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�����ʻ���";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;


        AccountGrid = new MulLineEnter("fm", "AccountGrid");
        //��Щ���Ա�����loadMulLineǰ
        AccountGrid.mulLineCount = 5;
        AccountGrid.displayTitle = 1;
        AccountGrid.locked = 1;
        AccountGrid.canSel = 0;
        AccountGrid.hiddenPlus = 1;
        AccountGrid.hiddenSubtraction = 1;
        AccountGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
