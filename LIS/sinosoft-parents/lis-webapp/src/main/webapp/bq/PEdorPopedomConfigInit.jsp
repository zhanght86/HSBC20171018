<%
//�������ƣ�PEdorPopedomConfigInit.jsp
//�����ܣ���ȫȨ������
//�������ڣ�2006-01-09 15:13:22
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initSelfGrid();
    //popedomQuery();  //��ʼ��Ȩ���б�
  }
  catch(re)
  {
    alert("PEdorPopedomConfigInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{

  try
  {
     fm.ManageCom.value = "86"; //�ݲ��ṩ�ֹ����������Ȩ�޹��ܣ�Ĭ��Ϊ86
  }
  catch(ex)
  {
    alert("PEdorPopedomConfigInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelfGrid()
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
      iArray[1][0]="��ȫ��Ŀ����";
      iArray[1][1]="120px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="��ȫ��Ŀ����";
      iArray[2][1]="200px";
      iArray[2][2]=200;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="����Ȩ��";
      iArray[3][1]="120px";
      iArray[3][2]=200;
      iArray[3][3]=0;
      iArray[3][9]="����Ȩ��|NOTNULL";
      iArray[3][10]="ApplyFlagList";
      iArray[3][11]="0|^0|��^1|��";
      iArray[3][18]=200;

      iArray[4]=new Array();
      iArray[4][0]="����Ȩ��";
      iArray[4][1]="120px";
      iArray[4][2]=200;
      iArray[4][3]=0;
      iArray[4][9]="����Ȩ��|NOTNULL";
      iArray[4][10]="ApproveFlagList";
      iArray[4][11]="0|^0|��^1|��";
      iArray[4][18]=200;
      
      iArray[5]=new Array();
      iArray[5][0]="��ȫ��Ŀ���";
      iArray[5][1]="0px";
      iArray[5][2]=0;
      iArray[5][3]=3;
      
      iArray[6]=new Array();
      iArray[6][0]="���֧�����";
      iArray[6][1]="120px";
      iArray[6][2]=200;
      iArray[6][3]=0;
      iArray[6][9]="���֧�����|NOTNULL";
      iArray[6][10]="LimitGetMoney";
      iArray[6][11]="0|^0|0^2000|1^5000|2^20000|3^50000|4^100000|5^200000|6";
      iArray[6][18]=200;
      //iArray[6][14]="0";

      SelfGrid = new MulLineEnter("fm", "SelfGrid");
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 3;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
