 <%
//�������ƣ�TempFeeInit.jsp
//�����ܣ�
//�������ڣ�2005-06-27 08:49:52
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>  
<script language="JavaScript">

function initForm()
{
  try
  {  
  	initTempToGrid();
  	initTempClassToGrid();
  	
  	QryTempFee();
  	
  	QryCustomer(); 
  	
  }
  catch(re)
  {
    alert("��UWTempFeeQryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{ 
  try
  { 
  }
  catch(ex)
  {
    alert("��UWTempFeeQryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}


////��ʼ�������ݽ��ѷ������ݵ�Grid
function initTempToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[1]=new Array();
      iArray[1][0]="�վݺ�/����Э�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="�վ�����";          		//����
      iArray[2][1]="20px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;
      
      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";          		//����
      iArray[3][1]="60px";      	      		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";          		//����
      iArray[5][1]="60px";      	      		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�������";      	   		//����
      iArray[6][1]="0px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempToGrid = new MulLineEnter( "fm" , "TempToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempToGrid.mulLineCount = 0;   
      TempToGrid.displayTitle = 1;
      TempToGrid.locked=1;
      TempToGrid.hiddenPlus=1;
      TempToGrid.hiddenSubtraction=1;      
      TempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//��ʼ�������ݽ��ѷ������ݵ�Grid
function initTempClassToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="�վݺ�/����Э�����";          		//����
      iArray[1][1]="100px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���ѷ�ʽ";          		//����
      iArray[2][1]="20px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ѽ��";          		//����
      iArray[3][1]="80px";      	      		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";          		//����
      iArray[4][1]="0px";      	      		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ʊ�ݺ���";          		//����
      iArray[5][1]="100px";      	      		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֧Ʊ����";          		//����
      iArray[6][1]="80px";      	      		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=40;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�����˺�";      	   		//����
      iArray[8][1]="100px";            			//�п�
      iArray[8][2]=40;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
      iArray[9]=new Array();
      iArray[9][0]="����";      	   		//����
      iArray[9][1]="60px";            			//�п�
      iArray[9][2]=10;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�տ������˺�";      	   		//����
      iArray[10][1]="140px";            		//�п�
      iArray[10][2]=40;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      TempClassToGrid = new MulLineEnter( "fm" , "TempClassToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassToGrid.mulLineCount = 0;   
      TempClassToGrid.displayTitle = 1;
      TempClassToGrid.locked=1;
      TempClassToGrid.hiddenPlus=1;
      TempClassToGrid.hiddenSubtraction=1;
      TempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}
</script>
       
