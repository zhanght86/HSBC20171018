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
    initNoBonusRiskGrid();  
    initBonusRiskGrid();
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
    document.all('BonusCalYear').value =getCurrentDate("-").substring(0,4)-1;
    document.all('BonusCalRisk').value = '';
   
  }
  catch(ex)
  {
    alert("��NBonusRiskPreInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
  
  function initNoBonusRiskGrid()
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
      iArray[1][0]="��������";          	//����
      iArray[1][1]="50px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[3]=new Array();
      iArray[3][0]="״̬";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������



       NoBonusRiskGrid = new MulLineEnter( "document" , "NoBonusRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NoBonusRiskGrid.mulLineCount = 5;   
      NoBonusRiskGrid.displayTitle = 1;
      NoBonusRiskGrid.canSel =1;
      NoBonusRiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      NoBonusRiskGrid.hiddenSubtraction=1;
      
      NoBonusRiskGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
    function initBonusRiskGrid()
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
      iArray[1][0]="��������";          	//����
      iArray[1][1]="50px";      	      	//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
            
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


       BonusRiskGrid = new MulLineEnter( "document" , "BonusRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      BonusRiskGrid.mulLineCount = 5;   
      BonusRiskGrid.displayTitle = 1;
      BonusRiskGrid.canChk  =1;
      BonusRiskGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      BonusRiskGrid.hiddenSubtraction=1;
      
      BonusRiskGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
      alert(ex);
    }
  }
  
</script>
