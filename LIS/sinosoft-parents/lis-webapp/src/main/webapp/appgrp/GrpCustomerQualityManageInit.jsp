<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWCustomerQualityInit.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script language="JavaScript">
	
function initAll() {
    //document.all('customername').value = '';
    document.all('CustomerNo').value = '';
    document.all('Name').value = '';
    document.all('Birthday').value = '';
    document.all('Sex').value = '';
    document.all('IDType').value = '';
    document.all('IDNumber').value = '';
    document.all('NameSel').value = '';
    document.all('CustomerNoSel').value = '';
    document.all('CQualityFlag').value = '';
    document.all('GrpCQualityFlagName').value = '';
    document.all('Reason').value = '';
    document.all('GrpNature').value = '';
}

function initForm() {
  try {
	initAll();
	//initData();
	initCustomerQualityGrid();
	initCustomerGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initCustomerQualityGrid()
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
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="90px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�ͻ���";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="Ʒ��״̬";         			//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="ԭ��";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��ͬ��";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[6]=new Array();
      iArray[6][0]="¼��Ա";      	   		//����
      iArray[6][1]="100px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="¼������";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
            
     
     
      CustomerQualityGrid = new MulLineEnter( "document" , "CustomerQualityGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerQualityGrid.mulLineCount = 5;   
      CustomerQualityGrid.displayTitle = 1;
      CustomerQualityGrid.hiddenPlus=1;
      CustomerQualityGrid.hiddenSubtraction = 1;
      CustomerQualityGrid.locked=0;
      CustomerQualityGrid.canSel=0;
      //CustomerQualityGrid.selBoxEventFuncName="showCustomerQuality";
      CustomerQualityGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initCustomerGrid()
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
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="90px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="Ʒ��״̬";         			//����
      iArray[3][1]="50px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="ԭ��";      	   		//����
      iArray[4][1]="250px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="¼��Ա";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="¼������";      	   		//����
      iArray[6][1]="100px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
            
     
     
      CustomerGrid = new MulLineEnter( "document" , "CustomerGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 5;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.hiddenPlus=1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.locked=0;
      CustomerGrid.canSel=1;
      CustomerGrid.selBoxEventFuncName="showCustomerQuality";
      CustomerGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


