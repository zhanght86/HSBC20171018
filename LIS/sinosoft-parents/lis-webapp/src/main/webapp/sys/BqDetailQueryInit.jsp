<%
//�������ƣ�
//�����ܣ���ȫ��ѯ��ϸ��Ϣ
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script language="JavaScript">

//ҳ���ʼ��
function initForm()
{
  try
  {
    initInputBox();
    initEdorItemGrid();
    initDiv();

  }
  catch(re)
  {
    alert("BqDetailQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

        /**
         * �����ĳ�ʼ��
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
            }
            catch (ex)
            {
                alert("�� BqDetailQueryInit.jsp --> initInputBox �����з����쳣: ��ʼ���������");
            }
        }

function initEdorItemGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;

        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="����������ʾ����";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="�����ͬ����";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="130px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�ͻ�����";
        iArray[7][1]="120px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="�������ֺ���";
        iArray[8][1]="120px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="������������";
        iArray[9][1]="140px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="������������";
        iArray[10][1]="90px";
        iArray[10][2]=100;
        iArray[10][3]=8;

        iArray[11]=new Array();
        iArray[11][0]="��Ч����";
        iArray[11][1]="90px";
        iArray[11][2]=100;
        iArray[11][3]=8;

        iArray[12]=new Array();
        iArray[12][0]="����ԭ��";
        iArray[12][1]="90px";
        iArray[12][2]=100;
        iArray[12][3]=0;

        iArray[13]=new Array();
        iArray[13][0]="����ԭ�����";
        iArray[13][1]="80px";
        iArray[13][2]=100;
        iArray[13][3]=3;

        iArray[14]=new Array();
        iArray[14][0]="���˷ѽ��";
        iArray[14][1]="100px";
        iArray[14][2]=100;
        iArray[14][3]=3;

        iArray[15]=new Array();
        iArray[15][0]="����ʱ��";
        iArray[15][1]="80px";
        iArray[15][2]=100;
        iArray[15][3]=3;

        iArray[16]=new Array();
        iArray[16][0]="���ɾ���ʱ��";
        iArray[16][1]="0px";
        iArray[16][2]=100;
        iArray[16][3]=3;

        iArray[17]=new Array();
        iArray[17][0]="��󱣴�ʱ��";
        iArray[17][1]="80px";
        iArray[17][2]=100;
        iArray[17][3]=3;

        iArray[18]=new Array();
        iArray[18][0]="�������";
        iArray[18][1]="80px";
        iArray[18][2]=100;
        iArray[18][3]=3;

        iArray[19]=new Array();
        iArray[19][0]="����״̬";
        iArray[19][1]="100px";
        iArray[19][2]=100;
        iArray[19][3]=0;

        iArray[20]=new Array();
        iArray[20][0]="����״̬����";
        iArray[20][1]="0px";
        iArray[20][2]=100;
        iArray[20][3]=3;

        iArray[21]=new Array();
        iArray[21][0]="��ȫ��������";
        iArray[21][1]="0px";
        iArray[21][2]=100;
        iArray[21][3]=3;

      EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" );
      //��Щ���Ա�����loadMulLineǰ
      EdorItemGrid.mulLineCount = 1;
      EdorItemGrid.displayTitle = 1;
      EdorItemGrid.canSel =1;
      EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      EdorItemGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      EdorItemGrid.hiddenSubtraction=1;
      EdorItemGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initRiskGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="160px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����������";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="����";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="���ѱ�׼";
        iArray[6][1]="90px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="�����ӷ�";
        iArray[7][1]="90px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="ְҵ�ӷ�";
        iArray[8][1]="90px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="���ֺ���";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="���Ѷ�Ӧ��";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=0;

      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
      //��Щ���Ա�����loadMulLineǰ
      RiskGrid.mulLineCount = 0;
      RiskGrid.displayTitle = 1;
      RiskGrid.canChk=0;
      RiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      RiskGrid.hiddenSubtraction=1;
      RiskGrid.loadMulLine(iArray);
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
