<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  

<script type="text/javascript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
	
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  { 
  }
  catch(ex)
  {
    myAlert("��ReInsureInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try{
	  initInpBox();
		initSelBox();
		initRiskInfoGrid();
		initPreceptGrid();
  }
  catch(re){
    myAlert("ReInsureInit.jsp-->"+"��ʼ���������!");
  }
}


// ������Ϣ�б�ĳ�ʼ��
function initRiskInfoGrid()
{                               
    var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��������";    			//����
      iArray[1][1]="80px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         	//����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="���α���";         	//����
      iArray[3][1]="60px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			
      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         			//����
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";         		//����
      iArray[6][1]="80px";            	//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����״̬";         	//����
      iArray[7][1]="80px";            	//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����״̬����";      //����
      iArray[8][1]="80px";            	//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
     	
     	iArray[9]=new Array();
      iArray[9][0]="ProposalNo";        //����
      iArray[9][1]="80px";            	//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�ٷֽ���";         //����
      iArray[10][1]="120px";            //�п�
      iArray[10][2]=100;            		//�����ֵ
      iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��������";       //����
      iArray[11][1]="120px";            //�п�
      iArray[11][2]=100;            		//�����ֵ
      iArray[11][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
     	iArray[12]=new Array();
     	iArray[12][0]="�������˱���";     //����
     	iArray[12][1]="120px";            //�п�
     	iArray[12][2]=100;            		//�����ֵ
     	iArray[12][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������

      RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskInfoGrid.mulLineCount = 0;   
      RiskInfoGrid.displayTitle = 1;
      RiskInfoGrid.locked = 1;
      RiskInfoGrid.hiddenPlus = 1;
      RiskInfoGrid.canSel = 1;
      RiskInfoGrid.hiddenSubtraction = 1;
      RiskInfoGrid.selBoxEventFuncName = "showPrecept";
      RiskInfoGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

function initPreceptGrid(){
	var iArray = new Array();
    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         		//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ٱ���ͬ����";    //����
      iArray[1][1]="80px";            //�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ٱ���������";    //����
      iArray[2][1]="80px";            //�п�
      iArray[2][2]=100;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="�ۼƷ�������";    //����
      iArray[3][1]="80px";            //�п�
      iArray[3][2]=100;            		//�����ֵ
      iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ٷֱ�־";    //����
      iArray[4][1]="60px";            //�п�
      iArray[4][2]=100;            		//�����ֵ
      iArray[4][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������

      PreceptGrid = new MulLineEnter( "fm" , "PreceptGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PreceptGrid.mulLineCount = 0;   
      PreceptGrid.displayTitle = 1;
      PreceptGrid.locked = 1;
      PreceptGrid.hiddenPlus = 1;
      PreceptGrid.canChk = 1; 
      //PreceptGrid.canSel = 1;
      PreceptGrid.hiddenSubtraction = 1;
      //PreceptGrid.selBoxEventFuncName = "";
      PreceptGrid.loadMulLine(iArray);  
   }
   catch(ex){
     myAlert(ex);
   }
}

</script>
