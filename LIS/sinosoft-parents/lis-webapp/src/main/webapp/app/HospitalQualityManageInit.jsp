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
    document.all('HospitalCode').value = '';
    document.all('HospitalName').value = '';
    document.all('ManageCom').value = '';
    document.all('ManageComName').value = '';
    document.all('QualityFlag').value = '';
    document.all('QualityFlagName').value = '';
    document.all('QualityFlagType').value = '';
    document.all('QualityFlagTypeName').value = '';
    document.all('Reason').value = '';
}

function initForm() {
  try {
	//initAll();
	//initData();
	//initCustomerQualityGrid();
	initHospitalGrid();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHospitalGrid()
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
      iArray[1][0]="ҽԺ����";          		//����
      iArray[1][1]="90px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="ҽԺ����";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="Ʒ��״̬";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ǩԼ״̬";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="ԭ��";      	   		//����
      iArray[6][1]="100px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
      
      
      iArray[7]=new Array();
      iArray[7][0]="¼����Ա";      	   		//����
      iArray[7][1]="50px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      
    
      iArray[8]=new Array();
      iArray[8][0]="ԭ�����";      	   		//����
      iArray[8][1]="0px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[9]=new Array();
      iArray[9][0]="¼������";      	   		//����
      iArray[9][1]="80px";            			//�п�
      iArray[9][2]=80;            			//�����ֵ
      iArray[9][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������  
     
      HospitalGrid = new MulLineEnter( "document" , "HospitalGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      HospitalGrid.mulLineCount = 5;   
      HospitalGrid.displayTitle = 1;
      HospitalGrid.hiddenPlus=1;
      HospitalGrid.hiddenSubtraction = 1;
      HospitalGrid.locked=0;
      HospitalGrid.canSel=1;
      HospitalGrid.selBoxEventFuncName="showHospitalDetail";
      HospitalGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


