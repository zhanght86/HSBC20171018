<%
//�������ƣ�LmRiskAppQueryInit.jsp
//�����ܣ�
//�������ڣ�2005-10-31
//������  �������


%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">



// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��LmRiskAppGridInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
//������Ϣ��ѯ��ʼ��
function initLmRiskAppForm()
{
  try
  {
    initSelBox();
		initLmRiskAppGrid();
  }
  catch(re)
  {
    alert("initLmRiskAppForm�����з����쳣:��ʼ���������!");
  }
}
//�������ʲ�ѯ��ʼ��
function initLdBankRateForm(){
  try
  {
    initSelBox();
		initLdBankRateGrid();
  }
  catch(re)
  {
    alert("initLdBankRateForm�����з����쳣:��ʼ���������!");
  }
}

var LmRiskAppGrid;
// ������Ϣ�б�ĳ�ʼ��
function initLmRiskAppGrid()
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
    iArray[1][0]="���ֱ���";             //����
    iArray[1][1]="100px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��������";             //����
    iArray[2][1]="180px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="���ְ汾";             //����
    iArray[3][1]="80px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="Ԥ������";             //����
    iArray[4][1]="80px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��������";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="����ʱ��";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=8;                    //�Ƿ���������,1��ʾ����0��ʾ������

    LmRiskAppGrid = new MulLineEnter( "fm" , "LmRiskAppGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LmRiskAppGrid.mulLineCount = 0;   
    LmRiskAppGrid.displayTitle = 1;
    LmRiskAppGrid.locked = 1;
    LmRiskAppGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LmRiskAppGrid.hiddenPlus=1;
    LmRiskAppGrid.hiddenSubtraction=1;
    LmRiskAppGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

var LdBankRateGrid;
//����������Ϣ�б�ĳ�ʼ��
function initLdBankRateGrid()
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
    iArray[1][0]="�ڼ���";             //����
    iArray[1][1]="100px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��������";             //����
    iArray[2][1]="180px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��/��";             //����
    iArray[3][1]="80px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��������";             //����
    iArray[4][1]="80px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="ֹ��";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 


    iArray[6]=new Array();
    iArray[6][0]="����";             //����
    iArray[6][1]="100px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    LdBankRateGrid = new MulLineEnter( "fm" , "LdBankRateGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LdBankRateGrid.mulLineCount = 0;   
    LdBankRateGrid.displayTitle = 1;
    LdBankRateGrid.locked = 1;
    LdBankRateGrid.canSel = 0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LdBankRateGrid.hiddenPlus=1;
    LdBankRateGrid.hiddenSubtraction=1;
    LdBankRateGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
