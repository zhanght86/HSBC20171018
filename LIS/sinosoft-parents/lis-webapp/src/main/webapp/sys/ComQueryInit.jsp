<%
//�������ƣ�ComQuery.js
//�����ܣ�
//�������ڣ�2002-08-16 17:44:45
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
 
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{

  try
  {
  //$initfield$
  }
  catch(ex)
  {
    alert("��ComQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
//    setOption("t_sex","0=��&1=Ů&2=����");
//    setOption("sex","0=��&1=Ů&2=����");
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");
  }
  catch(ex)
  {
    alert("��ComQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initComGrid();
  }
  catch(re)
  {
    alert("ComQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��ComGrid
 ************************************************************
 */
 var ComGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initComGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";         //�п�
		iArray[0][2]=100;            //�����ֵ
		iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[1]=new Array();
		iArray[1][0]="��������";         //����
		iArray[1][1]="100px";         //���
		iArray[1][2]=100;         //��󳤶�
		iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[2]=new Array();
		iArray[2][0]="��������";         //����
		iArray[2][1]="150px";         //���
		iArray[2][2]=100;         //��󳤶�
		iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[3]=new Array();
		iArray[3][0]="������ַ";         //����
		iArray[3][1]="200px";         //���
		iArray[3][2]=100;         //��󳤶�
		iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[4]=new Array();
		iArray[4][0]="�����绰";         //����
		iArray[4][1]="100px";         //���
		iArray[4][2]=100;         //��󳤶�
		iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[5]=new Array();
		iArray[5][0]="����������";         //����
		iArray[5][1]="100px";         //���
		iArray[5][2]=100;         //��󳤶�
		iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

		iArray[6]=new Array();
		iArray[6][0]="�������������";         //����
		iArray[6][1]="100px";         //���
		iArray[6][2]=100;         //��󳤶�
		iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		iArray[7]=new Array();
		iArray[7][0]="��Ч����";         //����
		iArray[7][1]="80px";         //���
		iArray[7][2]=100;         //��󳤶�
		iArray[7][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		iArray[8]=new Array();
		iArray[8][0]="ͣЧ����";         //����
		iArray[8][1]="80px";         //���
		iArray[8][2]=100;         //��󳤶�
		iArray[8][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		iArray[9]=new Array();
		iArray[9][0]="��Ч״̬";         //����
		iArray[9][1]="60px";         //���
		iArray[9][2]=100;         //��󳤶�
		iArray[9][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����						

		ComGrid = new MulLineEnter( "document" , "ComGrid" );

		//��Щ���Ա�����loadMulLineǰ
		ComGrid.mulLineCount = 5;
		ComGrid.displayTitle = 1;
		ComGrid.canSel=1;
		ComGrid.hiddenPlus = 1;
		ComGrid.hiddenSubtraction = 1;
		ComGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert("��ʼ��ComGridʱ����"+ ex);
	}
}
</script>
