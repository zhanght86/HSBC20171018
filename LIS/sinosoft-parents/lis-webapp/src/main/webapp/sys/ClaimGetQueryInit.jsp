<%
//�������ƣ�ClaimGetQueryInit.jsp
//�����ܣ�
//�������ڣ�2003-4-2
//������  ��lh

//�޸��ˣ�������
//�޸����ڣ�2004-2-17
//���¼�¼��  ������    ��������     ����ԭ��/����

//�޸��ˣ������
//�޸����ڣ�2005-10-23
//���¼�¼��  ���Ĳ�ѯ�����ʾ��¼�б��ֶ�

%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
  try
  {
    document.all('PolNo').value = tPolNo;
  }
  catch(ex)
  {
    alert("��ClaimGetQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��ClaimGetQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
		initPolGrid();
  }
  catch(re)
  {
    alert("ClaimGetQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�����˱���";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�Ա�";             //����
    iArray[5][1]="100px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    PolGrid.mulLineCount = 0;   
    PolGrid.displayTitle = 1;
    PolGrid.locked = 0;
    PolGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    PolGrid.selBoxEventFuncName ="PolGridClick"; //��Ӧ�ĺ���������������
    PolGrid.hiddenPlus=1;
    PolGrid.hiddenSubtraction=1;
    PolGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
