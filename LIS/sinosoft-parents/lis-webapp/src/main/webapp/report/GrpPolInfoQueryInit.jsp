<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageCom.value = "";
    //fm.sAgentCode.value = "";
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.sSaleChnl.value = "";
    fm.sAgentType.value = "";
    fm.sRiskType.value = "";
    fm.sRiskCode.value = "";
    fm.sBlacklist.value = "";
    fm.sInsuranceMoney.value = ""; 
    fm.sql.value = ""; 
  }
  catch(ex){
    alert("��GrpPolInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initForm(){
  try{
    initInpBox();
	initPolInfoGrid();
  }
  catch(re){
    alert("GrpPolInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initPolInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      	iArray[0][1]="35px";            		//�п�
    	iArray[0][2]=10;            			//�����ֵ
      	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
	  	//�������
      	iArray[1]=new Array();
      	iArray[1][0]="�������";         			//����
      	iArray[1][1]="150px";            		//�п�
      	iArray[1][2]=60;            			//�����ֵ
      	iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
   	  	//�����������
      	iArray[2]=new Array();
      	iArray[2][0]="�����������";         		//����
      	iArray[2][1]="80px";            		//�п�
      	iArray[2][2]=60;            			//�����ֵ
      	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[2][21]=2; 

	  	//��������
      	iArray[3]=new Array();
      	iArray[3][0]="��������";         			//����
      	iArray[3][1]="55px";            		//�п�
      	iArray[3][2]=60;            			//�����ֵ
      	iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,3��ʾ����
      	iArray[3][4]="SaleChnl";				//����ѡ��ֵ      	
      	
      
      	//������������
      	iArray[4]=new Array();
      	iArray[4][0]="��������";         			//����
      	iArray[4][1]="55px";            		//�п�
      	iArray[4][2]=0;            				//�����ֵ
      	iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,3��ʾ����
      	iArray[4][4]="AgentType";				//����ѡ��ֵ      	

  	  	//�����ͬ��
	  	iArray[5]=new Array();
      	iArray[5][0]="�����ͬ��";         		//����
      	iArray[5][1]="130px";            		//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[5][21]=2; 
      
      	//Ͷ������
	  	iArray[6]=new Array();
      	iArray[6][0]="Ͷ������";         			//����
      	iArray[6][1]="130px";            		//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[6][21]=2;     
 
   	   //Ͷ����λ����
   	 	iArray[7]=new Array();
      	iArray[7][0]="Ͷ����λ����";         			//����
      	iArray[7][1]="120px";            		//�п�
      	iArray[7][2]=100;            			//�����ֵ
      	iArray[7][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������     
      
   	 
   	 
   	 
   	  	//���ִ���
 	  	iArray[8]=new Array();
      	iArray[8][0]="���ִ���";         			//����
      	iArray[8][1]="100px";            		//�п�
      	iArray[8][2]=100;            			//�����ֵ
      	iArray[8][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,3��ʾ����
      	iArray[8][4]="RiskCode";				//����ѡ��ֵ      	
      	
      	//�ܱ���
      	iArray[9]=new Array();
      	iArray[9][0]="�ܱ���";         			//����
      	iArray[9][1]="90px";            		//�п�
      	iArray[9][2]=100;            			//�����ֵ
      	iArray[9][3]=0; 			
      
      	//�ܱ���
      	iArray[10]=new Array();
      	iArray[10][0]="�ܱ���";         			//����
      	iArray[10][1]="80px";            		//�п�
      	iArray[10][2]=100;            			//�����ֵ
      	iArray[10][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������     
      
      	//������������
      	iArray[11]=new Array();
      	iArray[11][0]="������������";         		//����
      	iArray[11][1]="85px";            		//�п�
      	iArray[11][2]=100;            			//�����ֵ
      	iArray[11][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������     
      
      	//��Ч��
      	iArray[12]=new Array();
      	iArray[12][0]="��Ч��";         			//����
      	iArray[12][1]="80px";            		//�п�
      	iArray[12][2]=100;            			//�����ֵ
      	iArray[12][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[12][21]=2;     
       
      	//ǩ����
      	iArray[13]=new Array();
      	iArray[13][0]="ǩ����";         			//����
      	iArray[13][1]="80px";            		//�п�
      	iArray[13][2]=100;            			//�����ֵ
      	iArray[13][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[13][21]=2;     
      
      	//�����˱���
      	iArray[14]=new Array();
      	iArray[14][0]="�����˱���";         		//����
      	iArray[14][1]="85px";            		//�п�
      	iArray[14][2]=100;            			//�����ֵ
      	iArray[14][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	//iArray[13][3]=2;              		//�Ƿ���������,1��ʾ����0��ʾ������,3��ʾ����
      	//iArray[13][4]="AgentCode";			//����ѡ��ֵ  
      	iArray[14][21]=2;    
      
      	//����������
      	iArray[15]=new Array();
      	iArray[15][0]="����������";         		//����
      	iArray[15][1]="85px";            		//�п�
      	iArray[15][2]=100;            			//�����ֵ
      	iArray[15][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������  
      
      	//�������
      	iArray[16]=new Array();
      	iArray[16][0]="�������";         		//����
      	iArray[16][1]="55px";            		//�п�
      	iArray[16][2]=100;            			//�����ֵ
      	iArray[16][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[16][4]="RiskType";
      
      	//���������
      	iArray[17]=new Array();
      	iArray[17][0]="���������";         		//����
      	iArray[17][1]="80px";            		//�п�
      	iArray[17][2]=100;            			//�����ֵ
      	iArray[17][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[17][4]="Blacklist";       	
      	
		
		PolInfoGrid = new MulLineEnter( "document" , "PolInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PolInfoGrid.mulLineCount = 5;
		PolInfoGrid.displayTitle = 1;
		PolInfoGrid.hiddenPlus = 1;
		PolInfoGrid.hiddenSubtraction = 1;
		PolInfoGrid.canSel = 1;
		PolInfoGrid.canChk = 1;
		PolInfoGrid.locked = 1;
		PolInfoGrid.canChk = 0;
		PolInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
