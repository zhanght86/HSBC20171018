<%
//�������ƣ�WFEdorCancelInit.jsp
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>


<script language="JavaScript">

function initForm()
{
  try
  {
    initAllGrid();
    initSelfGrid();
    querySelfGrid();
  }
  catch(re)
  {
    alert("WFGrpEdorCancelInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initAllGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                  //�п�
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";
      iArray[1][1]="120px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���屣����";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="0px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����λ";
      iArray[4][1]="150px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="�������";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";
      iArray[7][1]="70px";
      iArray[7][2]=200;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="¼������";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=0;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="�����������";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="�������������";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="�������Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;

      AllGrid = new MulLineEnter( "document" , "AllGrid" );
      //��Щ���Ա�����loadMulLineǰ
      AllGrid.mulLineCount = 5;
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1;
      AllGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
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
      iArray[0][2]=10;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";
      iArray[1][1]="120px";
      iArray[1][2]=170;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���屣����";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="0px";
      iArray[3][2]=100;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����λ";
      iArray[4][1]="150px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���뷽ʽ";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="�������";
      iArray[6][1]="90px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      iArray[6][4]="Station";

      iArray[7]=new Array();
      iArray[7][0]="¼��Ա";
      iArray[7][1]="70px";
      iArray[7][2]=100;
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="¼������";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=0;
      iArray[8][21]=3;

      iArray[9]=new Array();
      iArray[9][0]="�����������";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="�������������";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;

      iArray[11]=new Array();
      iArray[11][0]="�������Id";
      iArray[11][1]="0px";
      iArray[11][2]=0;
      iArray[11][3]=3;

      SelfGrid = new MulLineEnter( "document" , "SelfGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;
      SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>