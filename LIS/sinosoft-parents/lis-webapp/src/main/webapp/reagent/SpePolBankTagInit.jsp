<%
//�������ƣ�LAAscriptionInput.jsp
//�����ܣ�
//�������ڣ�2004-08-16 15:39:06
//������  ��CrtHtml���򴴽�
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
    document.all('MainPolNo').value ='';
    document.all('ContNo').value ='';
//  document.all('BranchType').value = getBranchType();    
  }
  catch(ex)
  {
    alert("��LRAscriptionInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();    
    initAscriptionGrid();    
  }
  catch(re)
  {
    alert("��LRAscriptionInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
  
// ��������Ϣ�ĳ�ʼ��
function initAscriptionGrid()
  {                               
    var iArray = new Array();      
      try
      {
      
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="20px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[1]=new Array();
      iArray[1][0]="�������";          		        //����
      iArray[1][1]="100px";      	      		//�п�
      iArray[1][2]=10;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
       

      iArray[2]=new Array();
      iArray[2][0]="���ձ�����";      	   		//����
      iArray[2][1]="100px";            			//�п�
      iArray[2][2]=10;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";      	   		//����
      iArray[3][1]="70px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="������Ч����";      	   		//����
      iArray[4][1]="70px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ����";      	   		//����
      iArray[5][1]="70px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=0;           			//�Ƿ���������,1��ʾ����0��ʾ������
      
          iArray[6]=new Array();
      iArray[6][0]="Ӧ�����";      	   		//����
      iArray[6][1]="70px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=0;           			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="���б���";      	   		//����
      iArray[7][1]="70px";            			//�п�
      iArray[7][2]=10;            			//�����ֵ
      iArray[7][3]=0;           			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[8]=new Array();
      iArray[8][0]="�ʺ�";      	   		//����
      iArray[8][1]="70px";            			//�п�
      iArray[8][2]=10;            			//�����ֵ
      iArray[8][3]=0;           			//�Ƿ���������,1��ʾ����0��ʾ������
 

      
      AscriptionGrid = new MulLineEnter( "fm" , "AscriptionGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AscriptionGrid.mulLineCount = 5;   
    AscriptionGrid.displayTitle = 1;
    AscriptionGrid.hiddenPlus = 1;
    AscriptionGrid.hiddenSubtraction = 1;
    AscriptionGrid.loadMulLine(iArray);
    AscriptionGrid.canChk =1; //��ѡ��   
      }
      catch(ex)
      {
        alert(ex);
      }
}
}
</script>
