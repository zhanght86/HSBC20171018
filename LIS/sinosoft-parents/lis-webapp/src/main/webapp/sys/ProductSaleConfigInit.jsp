<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>  
<script language="JavaScript">

function initForm()
{

  try
  { 
    initInpBox();    
    initProductSaleTraceGrid();  
    initProductSaleConfigGrid();
    queryRiskConfig();
    //queryData('1');
    //alert("000000000000000000000");     
  }
  catch(re)
  {
    alert("��BonusRiskPreInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{ 
  try
  {     
    document.all('RiskCode').value = '';
    document.all('ComGroup').value = '';
    document.all('ComGroupName').value = '';
    document.all('RiskCodeName').value = '';
    document.all('SaleChnl').value = '';
    document.all('SaleChnlName').value = '';
    document.all('PolApplyDateStart').value = '';
    document.all('PolApplyDateEnd').value = '';
    document.all('ScanDateStart').value = '';
    document.all('ScanDateEnd').value = '';
    document.all('ScanTimeStart').value = '';
    document.all('ScanTimeEnd').value = '';
   
  }
  catch(ex)
  {
    alert("��NBonusRiskPreInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
  
  function initProductSaleTraceGrid()
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
      iArray[1][0]="���ֱ���";          	//����
      iArray[1][1]="50px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���������";          	//����
      iArray[3][1]="50px";      	      	//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[4]=new Array();
      iArray[4][0]="Ͷ������";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ��ֹ��";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="ɨ������";         		//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="ɨ����ʼʱ��";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[8]=new Array();
      iArray[8][0]="ɨ��ֹ��";         		//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ɨ���ֹʱ��";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="������Ա";         		//����
      iArray[10][1]="50px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�ϴ��޸�����";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�ϴ��޸�ʱ��";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[13]=new Array();
      iArray[13][0]="�޸�����";         		//����
      iArray[13][1]="50px";            		//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�޸�ʱ��";         		//����
      iArray[14][1]="50px";            		//�п�
      iArray[14][2]=20;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      ProductSaleTraceGrid = new MulLineEnter( "document" , "ProductSaleTraceGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ProductSaleTraceGrid.mulLineCount = 5;   
      ProductSaleTraceGrid.displayTitle = 1;
      ProductSaleTraceGrid.canSel =0;
      ProductSaleTraceGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ProductSaleTraceGrid.hiddenSubtraction=1;
      
      ProductSaleTraceGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initProductSaleConfigGrid()
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
      iArray[1][0]="���ֱ���";          	//����
      iArray[1][1]="50px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���������";          	//����
      iArray[3][1]="50px";      	      	//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[4]=new Array();
      iArray[4][0]="Ͷ������";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ��ֹ��";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="ɨ������";         		//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="ɨ����ʼʱ��";         		//����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[8]=new Array();
      iArray[8][0]="ɨ��ֹ��";         		//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="ɨ���ֹʱ��";         		//����
      iArray[9][1]="50px";            		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="������Ա";         		//����
      iArray[10][1]="50px";            		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="�޸�����";         		//����
      iArray[11][1]="50px";            		//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�޸�ʱ��";         		//����
      iArray[12][1]="50px";            		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      ProductSaleConfigGrid = new MulLineEnter( "document" , "ProductSaleConfigGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ProductSaleConfigGrid.mulLineCount = 5;   
      ProductSaleConfigGrid.displayTitle = 1;
      ProductSaleConfigGrid.canChk  =0;
      ProductSaleConfigGrid.canSel =1;
      ProductSaleConfigGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      ProductSaleConfigGrid.hiddenSubtraction=1;
      ProductSaleConfigGrid. selBoxEventFuncName ="getConfigDetail" ; 
      
      ProductSaleConfigGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
