<%
//�������ƣ�AllFeeQueryPDetailInit.jsp
//�����ܣ�
//�������ڣ�2002-12-16
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<script src="../common/javascript/Common.js" type=""></SCRIPT>

<script language="JavaScript" type="">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
    // ������ѯ����
    document.all('PayNo').value = tPayNo;
    document.all('SumActuPayMoney').value = tSumActuPayMoney;
  }
  catch(ex)
  {
    alert("��AllFeeQueryPDetailInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();
    ValidPremCal();
    CurrentPremCal();
    easyQueryClick();
  }
  catch(re)
  {
    alert("AllFeeQueryPDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            		//�п�
    iArray[0][2]=10;            			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    //      iArray[7]=new Array();
    //      iArray[7][0]="��������";         		//����
    //      iArray[7][1]="0px";            		//�п�
    //      iArray[7][2]=100;            			//�����ֵ
    //      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //      iArray[8]=new Array();
    //      iArray[8][0]="���屣������";         		//����
    //      iArray[8][1]="0px";            		//�п�
    //      iArray[8][2]=100;            			//�����ֵ
    //      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //
    //      iArray[9]=new Array();
    //      iArray[9][0]="�ܵ�/��ͬ����";         		//����
    //      iArray[9][1]="0px";            		//�п�
    //      iArray[9][2]=200;            			//�����ֵ
    //      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�����վݺ���";         		//����
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=200;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����Ŀ�ķ���";         		//����
    iArray[2][1]="100px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    //  iArray[2][10] = "PayAimClass";
    //  iArray[2][11] = "0|^1|���˽���^2|���彻��";
    //  iArray[2][12] = "11";
    //  iArray[2][19] = "0";

    iArray[3]=new Array();
    iArray[3][0]="��ʵ�����";         		//����
    iArray[3][1]="100px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[3][23]="0";
		
    iArray[4]=new Array();
    iArray[4][0]="���Ѽ��";         		//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="���Ѻ�������";         		//����
    iArray[5][1]="100px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=8; 									//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="��������";         		//����
    iArray[6][1]="100px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    // iArray[6][10] = "PayType";
    // iArray[6][11] = "0|^ZC|����^YET|�������^YEL|ʹ���ϴ����";
    // iArray[6][12] = "11";
    // iArray[6][19] = "0";


    iArray[7]=new Array();
    iArray[7][0]="�ֽ�������";         		//����
    iArray[7][1]="100px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=8; 									//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="���Ѵ���";         		//����
    iArray[8][1]="60px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0; 									//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[9]=new Array();
		iArray[9][0]="����";
		iArray[9][1]="60px";
		iArray[9][2]=100;
		iArray[9][3]=2;
		iArray[9][4]="currency";
		
    PolGrid = new MulLineEnter( "fm" , "PolGrid" );
    //��Щ���Ա�����loadMulLineǰ
    PolGrid.mulLineCount = 5;
    PolGrid.displayTitle = 1;
    PolGrid.locked = 1;
    PolGrid.canSel = 1;
    PolGrid.hiddenPlus = 1;
    PolGrid.hiddenSubtraction = 1;
    PolGrid.loadMulLine(iArray);

    //��Щ����������loadMulLine����
  }
  catch(ex)
  {
    alert(ex);
  }
}

</script>
