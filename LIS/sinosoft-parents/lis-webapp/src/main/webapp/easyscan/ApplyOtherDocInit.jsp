<%
//�������ƣ�EsDocManageInit.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("��ApplyIssueDocInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
//    initSubType();
    initCodeGrid();
  }
  catch(re)
  {
    alert("ApplyIssueDocInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��CodeGrid
 ************************************************************
 */
var CodeGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initCodeGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         //�п�
      iArray[0][2]=100;            //�����ֵ
      iArray[0][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������
      
	  iArray[1]=new Array();
      iArray[1][0]="ҵ������";     //����
      iArray[1][1]="50px";         //�п�
      iArray[1][2]=50;             //�����ֵ
      iArray[1][3]=0;              //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ҵ�����";     //����
      iArray[2][1]="80px";         //���
      iArray[2][2]=80;        		 //��󳤶�
      iArray[2][3]=0;         		 //�Ƿ�����¼�룬0--���ܣ�1--����

	  iArray[3]=new Array();
      iArray[3][0]="��������";     //����
      iArray[3][1]="50px";         //���
      iArray[3][2]=50;      		   //��󳤶�
      iArray[3][3]=0;         		 //�Ƿ�����¼�룬0--���ܣ�1--����
      
      iArray[4]=new Array();
      iArray[4][0]="ҳ��";         //����
      iArray[4][1]="30px";         //���
      iArray[4][2]=50;        		 //��󳤶�
      iArray[4][3]=0;         		 //�Ƿ�����¼�룬0--���ܣ�1--����

      iArray[5]=new Array();
      iArray[5][0]="��֤ID";       //����
      iArray[5][1]="0px";          //���
      iArray[5][2]=50;           	 //��󳤶�
      iArray[5][3]=0;        			 //�Ƿ�����¼�룬0--���ܣ�1--����
  
      iArray[6]=new Array();
      iArray[6][0]="�������";     //����
      iArray[6][1]="60px";         //���
      iArray[6][2]=50;           	 //��󳤶�
      iArray[6][3]=0;        			 //�Ƿ�����¼�룬0--���ܣ�1--����
  
      iArray[7]=new Array();
      iArray[7][0]="¼��״̬";     //����
      iArray[7][1]="50px";         //���
      iArray[7][2]=50;  		       //��󳤶�
      iArray[7][3]=0;        			 //�Ƿ�����¼�룬0--���ܣ�1--����

      iArray[8]=new Array();
      iArray[8][0]="ɨ������";     //����
      iArray[8][1]="50px";         //���
      iArray[8][2]=50;        		 //��󳤶�
      iArray[8][3]=0;        			 //�Ƿ�����¼�룬0--���ܣ�1--����

	  iArray[9]=new Array();
      iArray[9][0]="ɨ�����Ա";   //����
      iArray[9][1]="50px";         //���
      iArray[9][2]=50;	         	 //��󳤶�
      iArray[9][3]=0;        			 //�Ƿ�����¼�룬0--���ܣ�1--����
 
      CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CodeGrid.mulLineCount = 5;   
      CodeGrid.displayTitle = 1;
      CodeGrid.hiddenSubtraction=1;
      CodeGrid.hiddenPlus=1;
      CodeGrid.canChk=1;
      CodeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("��ʼ��CodeGridʱ����"+ ex);
    }
}
    
    
</script>
