<%                                                   //????
//�������ƣ�ViewErrLogBonusAssignInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->                                              
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

function initForm()
{
  try
  {
		initErrLogBonusGrid();
  }
  catch(re)
  {
    alert("ViewErrLogBonusAssignInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var ErrLogBonusGrid;
// ������Ϣ�б�ĳ�ʼ��
function initErrLogBonusGrid()
{
    var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";         	//����
      iArray[1][1]="40px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0; 

      iArray[2]=new Array();
      iArray[2][0]="���屣������";      //����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ֺ�״̬";         	//����
      iArray[3][1]="40px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      iArray[4]=new Array();
      iArray[4][0]="����ԭ��";         	//����
      iArray[4][1]="300px";            	//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  
      iArray[5]=new Array();
      iArray[5][0]="���ʱ��";         	//����
      iArray[5][1]="80px";            	//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ErrLogBonusGrid = new MulLineEnter( "document" , "ErrLogBonusGrid" );
      //��Щ���Ա�����loadMulLineǰ
      ErrLogBonusGrid.mulLineCount = 5;
      ErrLogBonusGrid.displayTitle = 1;
      ErrLogBonusGrid.hiddenPlus=1;
      ErrLogBonusGrid.hiddenSubtraction=1;
      ErrLogBonusGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
