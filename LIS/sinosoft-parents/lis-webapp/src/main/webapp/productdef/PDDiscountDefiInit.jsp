<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//PDDiscountDefiInit.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2011-03-03
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script type="text/javascript">
	
function initAll() {
    fm.all('DiscountType').value = '';
    fm.all('DiscountCode').value = '';
    //fm.all('CampaignCode').value = '';
    fm.all('AddFeeDiscFlag').value = '';
    fm.all('RiskCode').value = tRiskCode;
    fm.all('DutyCode').value = '';
    fm.all('StartDate').value = '';
    fm.all('EndDate').value = '';
    fm.all('DiscounTypeName').value = '';
		fm.all('DutyCodeName').value = '';
		fm.all('AddFeeDiscFlagName').value = '';
		fm.all('CalCode').value = '';
    
}

function initForm() {
  try {
	initAll();
	initPremDiscountGrid();
	queryPremDiscountGrid();
  }
  catch(re) {
    myAlert("PDDiscountDefiInit.jsp-->"+"��ʼ���������!");
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
      
      iArray[1]=new Array();
      iArray[1][0]="�ۿ�����";          		//����
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			//iArray[1][4]="discounttype";              	        //�Ƿ����ô���:null||""Ϊ������
      //iArray[1][9]="�ۿ�����|code:discounttype";
      //iArray[1][15]="1";  //�������� 
      //iArray[1][16]="1 and code=#D_PR#";//��������
      
      iArray[2]=new Array();
      iArray[2][0]="�ۿۺ���";          		//����
      iArray[2][1]="120px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      
      iArray[3]=new Array();
      iArray[3][0]="���������";         			//����
      iArray[3][1]="0px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ӷ��ۿ۱��";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[4][4]="yesno";              	        //�Ƿ����ô���:null||""Ϊ������
      //iArray[4][9]="�ӷ��ۿ۱��|code:yesno";

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";      	   		//����
      iArray[5][1]="0px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[5][4]="riskcode";              	        //�Ƿ����ô���:null||""Ϊ������

			var tSqlStr = "1 and riskcode=#" + tRiskCode + "#";
      iArray[6]=new Array();
      iArray[6][0]="���α���";      	   		//����
      iArray[6][1]="50px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
     	iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			//iArray[6][4]="pd_dutycode";              	        //�Ƿ����ô���:null||""Ϊ������
			//iArray[6][15]="1";  //�������� 
      //iArray[6][16]=tSqlStr;//��������

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
        myAlert(ex);
      }
}

</script>
