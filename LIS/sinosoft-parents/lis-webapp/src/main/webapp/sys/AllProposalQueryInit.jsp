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
    initPolGrid();
    initDisplayButton();
    initQuery();
    initManageCom(); //��ʼ��������������ȡ6λ
    
}

function initDisplayButton()
{
    tDisplay = <%=tDisplay%>;
    //alert(tDisplay);
    if(tDisplay == "0")
    {
        fm.Return.style.display='none';
        fm.ReturnBack.style.display='none';
    }
    else if(tDisplay=="1"||tDisplay=="2")
    {
        fm.Return.style.display='';
        fm.ReturnBack.style.display='';
    }
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
      document.all('IdCardNo').value = '';
      document.all('CustomerNo').value = '';
      document.all('ContNo').value = '';
      document.all('ManageCom').value = '';
      document.all('AgentCode').value = '';
      document.all('AgentGroup').value = '';
      document.all('GrpContNo').value = '';
      document.all('ProposalContNo').value = '';
      document.all('AppntName').value = '';
      document.all('InsuredNo').value = '';
      document.all('InsuredName').value = '';
      document.all('PayLocation').value = '';
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
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����";
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
        iArray[5][3]=8;

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

        CustomerGrid = new MulLineEnter("document", "CustomerGrid");
        //��Щ���Ա�����loadMulLineǰ
        CustomerGrid.mulLineCount = 5;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.locked = 1;
        //CustomerGrid.canSel = 1;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
        iArray[1][0]="��������";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="ӡˢ��";
        iArray[2][1]="90px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="Ͷ����";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="���屣����";
        iArray[4][1]="130px";
        iArray[4][2]=150;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="Ͷ������";
        iArray[5][1]="0px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="�����˺�";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="����������";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        PolGrid = new MulLineEnter("document", "PolGrid");
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 5;
        PolGrid.displayTitle = 1;
        PolGrid.locked = 1;
        PolGrid.canSel = 1;
        PolGrid.hiddenPlus = 1;
        PolGrid.hiddenSubtraction = 1;
        PolGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
