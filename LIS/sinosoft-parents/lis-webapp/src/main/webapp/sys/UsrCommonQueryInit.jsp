<%
//�������ƣ�UsrCommonQueryInit.jsp
//�����ܣ�ϵͳ�û���Ϣ��ѯ
//�������ڣ�2005-11-30
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initUsrGrid();
    initManageCom(); //���������������Ϊ�գ���������������ֻ�ܲ�˻��������¼�����
  }
  catch(re)
  {
    alert("UsrCommonQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
    document.all('UsrCode').value = '';
    document.all('UsrName').value = '';
    if(managecom != null && managecom !="")
    {
      document.all('ManageCom').value = managecom;
      showOneCodeName("Station", "ManageCom", "ManageComName");
    }
    if(usercode != null && usercode !="" && usercode != 'null' && usercode != '')
    {
      document.all('UsrCode').value = usercode;
    }

  }
  catch(ex)
  {
    alert("��UsrCommonQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

/************************************************************
 *
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��UsrGrid
 ************************************************************
 */
function initUsrGrid()
  {
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�û�����";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�û�����";
        iArray[2][1]="140px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�������";
        iArray[3][1]="140px";
        iArray[3][2]=200;
        iArray[3][3] = 0;

        iArray[4]=new Array();
        iArray[4][0]="������ȫȨ�޼���";
        iArray[4][1]="140px";
        iArray[4][2]=200;
        iArray[4][3]=2;
        iArray[4][4] = "EdorPopedom";
        iArray[4][18] = 236;
        
        iArray[5]=new Array();
        iArray[5][0]="�ŵ���ȫȨ�޼���";
        iArray[5][1]="140px";
        iArray[5][2]=200;
        iArray[5][3]=2;
        iArray[5][4] = "EdorPopedom";
        iArray[5][18] = 236;
        
        UsrGrid = new MulLineEnter("document", "UsrGrid");
        //��Щ���Ա�����loadMulLineǰ
        UsrGrid.mulLineCount = 5;
        UsrGrid.displayTitle = 1;
        UsrGrid.canSel=1;
        //UsrGrid.canChk=0;
        UsrGrid.locked=1;
        UsrGrid.hiddenPlus=1;
        UsrGrid.hiddenSubtraction=1;
        UsrGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert("��ʼ��UsrGridʱ����"+ ex);
      }
    }

</script>
