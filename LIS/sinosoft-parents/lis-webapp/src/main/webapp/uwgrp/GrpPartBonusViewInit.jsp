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
		initBonusGrid();
		
		easyQueryClick();
  }
  catch(re)
  {
    alert("ViewErrLogBonusAssignInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var BonusGrid;
// ������Ϣ�б�ĳ�ʼ��
function initBonusGrid()
{
    var iArray = new Array();

    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="50px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ŵ���";         		//����
      iArray[1][1]="160px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Ч����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ֺ����";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�ֺ�״̬";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=30;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10] = "Status";
      iArray[5][11] = "0|^0|������^3|���ָ����Ѽ���^4|���ָ����ѷ���";
      iArray[5][12] = "5";
      iArray[5][19] = "0";

      BonusGrid = new MulLineEnter( "document" , "BonusGrid" );
      //��Щ���Ա�����loadMulLineǰ
      BonusGrid.mulLineCount = 5;
      BonusGrid.displayTitle = 1;
      //BonusGrid.locked = 1;
      BonusGrid.canSel=1;
      BonusGrid.hiddenPlus=1;
      BonusGrid.hiddenSubtraction=1;
      BonusGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
