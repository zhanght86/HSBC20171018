<%
//�������ƣ�LLAccidentQueryInit.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

var tAccDate = "<%=tAccDate%>";
var tCustomerNo = "<%=tCustomerNo%>";

function initInpBox()
{
    try
    {


    }
    catch(ex)
    {
        alert("��LLAccidentQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initSelBox()
{
    try
    {

    }
    catch(ex)
    {
       alert("��LLAccidentQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initSelBox();
        initLLAccidentGrid();
        easyQueryClick();
    }
    catch(re)
    {
        alert("LLAccidentQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
// ������Ϣ�б�ĳ�ʼ��
function initLLAccidentGrid()
  {
      var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                //�п�
      iArray[0][2]=30;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�¼���";             //����
      iArray[1][1]="70px";                //�п�
      iArray[1][2]=70;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�¹�����";             //����
      iArray[2][1]="70px";                //�п�
      iArray[2][2]=70;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�¹�����";             //����
      iArray[3][1]="160px";                //�п�
      iArray[3][2]=160;                  //�����ֵ
      iArray[3][3]=0;


      iArray[4]=new Array();
      iArray[4][0]="�¹ʵص�";             //����
      iArray[4][1]="60px";                //�п�
      iArray[4][2]=60;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������      
            
      iArray[5]=new Array();
      iArray[5][0]="����ԭ�����";             //���������¹����ʹ���
      iArray[5][1]="0px";                //�п�
      iArray[5][2]=0;                  //�����ֵ
      iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

//*******************modify by niuzj,2005-11-03,��������ʾ�¹���������***************

      iArray[6]=new Array();
      iArray[6][0]="����ԭ��";         //����
      iArray[6][1]="60px";                //�п�
      iArray[6][2]=60;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
//************************�����¹����ʹ�����������ش���*****************************


      iArray[7]=new Array();
      iArray[7][0]="����ϸ�ڱ���";             //����
      iArray[7][1]="0px";                //�п�
      iArray[7][2]=0;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����ϸ��";             //����
      iArray[8][1]="160px";                //�п�
      iArray[8][2]=160;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="���ս��1����";             //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=0;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="���ս��1";             //����
      iArray[10][1]="120px";                //�п�
      iArray[10][2]=120;                  //�����ֵ
      iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="���ս��2����";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=0;                  //�����ֵ
      iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="���ս��2";             //����
      iArray[12][1]="120px";                //�п�
      iArray[12][2]=120;                  //�����ֵ
      iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="����Ա";             //����
      iArray[13][1]="60px";                //�п�
      iArray[13][2]=60;                  //�����ֵ
      iArray[13][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�������";             //����
      iArray[14][1]="60px";                //�п�
      iArray[14][2]=60;                  //�����ֵ
      iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[15]=new Array();
      iArray[15][0]="��ע";             //����
      iArray[15][1]="0px";                //�п�
      iArray[15][2]=0;                  //�����ֵ
      iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      

      LLAccidentGrid = new MulLineEnter( "fm" , "LLAccidentGrid" );
      //��Щ���Ա�����loadMulLineǰ
      LLAccidentGrid.mulLineCount = 10;
      LLAccidentGrid.displayTitle = 1;
      LLAccidentGrid.locked = 0;
//      LLAccidentGrid.canChk = 1;
      LLAccidentGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLAccidentGrid.hiddenPlus=1;
      LLAccidentGrid.hiddenSubtraction=1;
      LLAccidentGrid.loadMulLine(iArray);

    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
