<%@include file="../i18n/language.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">	

function initForm()
{
  try
  {
	  initRiskStateInfoGrid();
  }
  catch(re)
  {
      myAlert(""+"��ʼ���������!");
  }
}

//��ѯԭ֪ͨ����Ϣ��Ϣ�б�ĳ�ʼ��
function initRiskStateInfoGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
	  iArray[0][1]="30px";            	//�п�
	  iArray[0][2]=10;            			//�����ֵ
	  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[1]=new Array();
	  iArray[1][0]="���ֱ���";         //����
	  iArray[1][1]="60px";            	//�п�
	  iArray[1][2]=100;            		//�����ֵ
	  iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[2]=new Array();
	  iArray[2][0]="��������";       		//����
	  iArray[2][1]="100px";            	//�п�
	  iArray[2][2]=100;            		//�����ֵ
	  iArray[2][3]=0; 
	
	  iArray[3]=new Array();
	  iArray[3][0]="���������";       	//����
	  iArray[3][1]="100px";            	//�п�
	  iArray[3][2]=100;            		//�����ֵ
	  iArray[3][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[4]=new Array();
	  iArray[4][0]="���������";         	//����
	  iArray[4][1]="120px";            	//�п�
	  iArray[4][2]=200;            		//�����ֵ
	  iArray[4][3]=0; 
	
	  iArray[5]=new Array();
	  iArray[5][0]="�����";        		//����
	  iArray[5][1]="50px";            	//�п�
	  iArray[5][2]=100;            		//�����ֵ
	  iArray[5][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������
    	  
	  iArray[6]=new Array();
	  iArray[6][0]="������";        		//����
	  iArray[6][1]="50px";            	//�п�
	  iArray[6][2]=200;            		//�����ֵ
	  iArray[6][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������    	  
	  
	  iArray[7]=new Array();
	  iArray[7][0]="��ǰ״̬";        		//����
	  iArray[7][1]="50px";            	//�п�
	  iArray[7][2]=200;            		//�����ֵ
	  iArray[7][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������    	  

	  iArray[8]=new Array();
	  iArray[8][0]="���ʱ��";        		//����
	  iArray[8][1]="60px";            	//�п�
	  iArray[8][2]=200;            		//�����ֵ
	  iArray[8][3]=0; 					//�Ƿ���������,1��ʾ����0��ʾ������    	  	  
	
	  RiskStateInfoGrid = new MulLineEnter( "fm" , "RiskStateInfoGrid" ); 
	  //��Щ���Ա�����loadMulLineǰ
	  RiskStateInfoGrid.mulLineCount      = 0;   
	  RiskStateInfoGrid.displayTitle      = 1;
	  RiskStateInfoGrid.canSel            = 1;
      RiskStateInfoGrid.locked            = 1;
      RiskStateInfoGrid.hiddenPlus        = 1;
      RiskStateInfoGrid.hiddenSubtraction = 1;
	  RiskStateInfoGrid.loadMulLine(iArray);  	
	} 
	catch(ex) 
	{
		myAlert(ex);
	}
}

</script>