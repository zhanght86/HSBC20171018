
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
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�¼���";             //����
      iArray[1][1]="120px";                //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�¹ʷ�������";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�¹�����";             //����
      iArray[3][1]="80px";                //�п�
      iArray[3][2]=200;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�¼�����";             //����
      iArray[4][1]="120px";                //�п�
      iArray[4][2]=500;                  //�����ֵ
      iArray[4][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�¹�����";             //����
      iArray[5][1]="60px";                //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="�¹ʵص�";             //����
      iArray[6][1]="150px";                //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�ش��¼���־";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����Ա";             //����
      iArray[8][1]="100px";                //�п�
      iArray[8][2]=200;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��Ͻ����";             //����
      iArray[9][1]="100px";                //�п�
      iArray[9][2]=200;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

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
