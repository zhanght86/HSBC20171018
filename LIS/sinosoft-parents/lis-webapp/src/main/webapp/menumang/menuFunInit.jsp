<%
//�������ƣ�menuFunInit.jsp
//�����ܣ�
//�������ڣ�2002-10-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����

%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
      document.all("NodeName").value = "";
      document.all("RunScript").value = "";
  }
  catch(ex)
  {
    alert("��menuFunInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��menuFunInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initQueryGrpGrid();
  }
  catch(re)
  {
    alert("menuFunInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initQueryGrpGrid()
{

    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="�˵�ID";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="40px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�Ӳ˵���";    	                //����
      iArray[2][1]="45px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="���˵�ID";    	                //����
      iArray[3][1]="45px";            		        //�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[4]=new Array();
      iArray[4][0]="�˵�����";    	                //����
      iArray[4][1]="120px";            		        //�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�


      iArray[5]=new Array();
      iArray[5][0]="�˵�ӳ���ļ�";    	                //����
      iArray[5][1]="240px";            		        //�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[6]=new Array();
      iArray[6][0]="�˵�˳���";    	                //����
      iArray[6][1]="50px";            		        //�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[7]=new Array();
      iArray[7][0]="�˵���־";    	                //����
      iArray[7][1]="50px";            		        //�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;            //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );

      //��Щ���Ա�����loadMulLineǰ
      QueryGrpGrid.mulLineCount = 5;
      QueryGrpGrid.displayTitle = 1;
      QueryGrpGrid.canChk =0;
      QueryGrpGrid.canSel =1;
      QueryGrpGrid.locked =1;            //�Ƿ�������1Ϊ���� 0Ϊ������
      QueryGrpGrid.hiddenPlus=1;        //�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
      QueryGrpGrid.hiddenSubtraction=1; //�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
      QueryGrpGrid.recordNo=0;         //���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������

      QueryGrpGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
</script>
