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
    document.all('DiscountType').value = '';
    document.all('DiscountCode').value = '';
    document.all('CampaignCode').value = '';
    document.all('AddFeeDiscFlag').value = '';
    document.all('RiskCode').value = '';
    document.all('DutyCode').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('CalCode').value = '';
    
    document.all('DiscountTypeQ').value = '';
    document.all('DiscountCodeQ').value = '';
    document.all('CampaignCodeQ').value = '';
    document.all('AddFeeDiscFlagQ').value = '';
    document.all('RiskCodeQ').value = '';
    document.all('DutyCodeQ').value = '';
    document.all('StartDateQ').value = '';
    document.all('EndDateQ').value = '';
    
}

function initForm() {
  try {
	initAll();
	//initData();
	initPremDiscountGrid();
	//initCustomerGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initPremDiscountGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

/*iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="50px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[7][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[7][9]="����|code:Currency";*/
      
      iArray[1]=new Array();
      iArray[1][0]="�ۿ�����";          		//����
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			iArray[1][4]="discounttype";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][9]="�ۿ�����|code:discounttype";
      
      iArray[2]=new Array();
      iArray[2][0]="�ۿۺ���";          		//����
      iArray[2][1]="120px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="���������";         			//����
      iArray[3][1]="50px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ӷ��ۿ۱��";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[4][4]="yesno";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[4][9]="�ӷ��ۿ۱��|code:yesno";

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";      	   		//����
      iArray[5][1]="50px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[5][4]="riskcode";              	        //�Ƿ����ô���:null||""Ϊ������


      iArray[6]=new Array();
      iArray[6][0]="���α���";      	   		//����
      iArray[6][1]="50px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
     	iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[6][4]="dutycode";              	        //�Ƿ����ô���:null||""Ϊ������

      iArray[7]=new Array();
      iArray[7][0]="��ʼ����";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=8;                   //�Ƿ���������,1��ʾ����0��ʾ������      
            
      iArray[8]=new Array();
      iArray[8][0]="��ֹ����";      	   		//����
      iArray[8][1]="60px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=8;                   //�Ƿ���������,1��ʾ����0��ʾ������      
 
 		  iArray[9]=new Array();
      iArray[9][0]="�㷨����";      	   		//����
      iArray[9][1]="60px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
     
      PremDiscountGrid = new MulLineEnter( "fm" , "PremDiscountGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PremDiscountGrid.mulLineCount = 1;   
      PremDiscountGrid.displayTitle = 1;
      PremDiscountGrid.hiddenPlus=1;
      PremDiscountGrid.hiddenSubtraction = 1;
      PremDiscountGrid.locked=0;
      PremDiscountGrid.canSel=1;
      PremDiscountGrid.selBoxEventFuncName="showPremDiscountDetail";
      PremDiscountGrid.loadMulLine(iArray);  
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
      iArray[5][0]="¼��Ա";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="¼������";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
            
     
     
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 1;   
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


