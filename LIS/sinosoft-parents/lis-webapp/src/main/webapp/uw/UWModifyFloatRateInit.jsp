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
    
}

function initForm() {
  try {
	initRiskFloatRateGrid();
	initFloatRate();
	initSpecIdea();
  }
  catch(re) {
    alert("UWModifyFloatRateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHide()
{
	document.all('ContNo').value=contNo;
}

function initRiskFloatRateGrid()
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
      iArray[1][0]="��������";          		//����
      iArray[1][1]="90px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="�����ڼ�";          		//����
      iArray[2][1]="40px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="��ǰ��������";         			//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�޸ĸ�������";      	   		//����
      iArray[4][1]="40px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

     
      iArray[5]=new Array();
      iArray[5][0]="������";      	   		//����
      iArray[5][1]="0px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
      iArray[6]=new Array();
      iArray[6][0]="��ȡ����";      	   		//����
      iArray[6][1]="0px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

     
      RiskFloatRateGrid = new MulLineEnter( "fm" , "RiskFloatRateGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskFloatRateGrid.mulLineCount = 3;   
      RiskFloatRateGrid.displayTitle = 1;
      RiskFloatRateGrid.hiddenPlus=1;
      RiskFloatRateGrid.locked=0;
      RiskFloatRateGrid.canSel=0;
      RiskFloatRateGrid.canChk=1;
      RiskFloatRateGrid.selBoxEventFuncName="";
      RiskFloatRateGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


