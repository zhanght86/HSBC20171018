<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageComName.value = "";    
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.ContPlanCode.value = "";    
  }
  catch(ex){
    alert("GrpPlanInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initForm(){
  try{
    initInpBox();
	initPlanInfoGrid();
  }
  catch(re){
    alert("GrpPlanInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initPlanInfoGrid(){
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
      	iArray[2][1]="100px";            		//�п�
      	iArray[2][2]=60;            			//�����ֵ
      	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[2][21]=2; 

	  	//�����ͬ��
      	iArray[3]=new Array();
      	iArray[3][0]="�����ͬ��";         		//����
      	iArray[3][1]="150px";            		//�п�
      	iArray[3][2]=60;            			//�����ֵ
      	iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[3][21]=2; 
      
      	//Ͷ������
      	iArray[4]=new Array();
      	iArray[4][0]="Ͷ������";         			//����
      	iArray[4][1]="150px";            		//�п�
      	iArray[4][2]=0;            				//�����ֵ
      	iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[4][21]=2;

  	  	//����������
	  	iArray[5]=new Array();
      	iArray[5][0]="����������";         		//����
      	iArray[5][1]="80px";            		//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      
      	//�Ա�
	  	iArray[6]=new Array();
      	iArray[6][0]="�Ա�";         			//����
      	iArray[6][1]="35px";            		//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[6][4]="sex";         	
 
   	  	//��������
 	  	iArray[7]=new Array();
      	iArray[7][0]="��������";         			//����
      	iArray[7][1]="80px";            		//�п�
      	iArray[7][2]=100;            			//�����ֵ
      	iArray[7][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[7][21]=2; 
      	//iArray[7][10]="RateType";
      	//iArray[7][11]="0|^D|������^M|������^Y|������";  
      
      	//�ײͼƻ�����
      	iArray[8]=new Array();
      	iArray[8][0]="�ײͼƻ�����";         		//����
      	iArray[8][1]="80px";            		//�п�
      	iArray[8][2]=100;            			//�����ֵ
      	iArray[8][3]=2; 
      	iArray[8][4]="ContPlanCode";		      
      
      	//����
      	iArray[9]=new Array();
      	iArray[9][0]="����";         			//����
      	iArray[9][1]="80px";            		//�п�
      	iArray[9][2]=100;            			//�����ֵ
      	iArray[9][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	    
      
      	//����
      	iArray[10]=new Array();
      	iArray[10][0]="����";         			//����
      	iArray[10][1]="55px";            		//�п�
      	iArray[10][2]=100;            			//�����ֵ
      	iArray[10][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������     
       
      	//��Ч����
      	iArray[11]=new Array();
      	iArray[11][0]="��Ч����";         		//����
      	iArray[11][1]="80px";            		//�п�
      	iArray[11][2]=100;            			//�����ֵ
      	iArray[11][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[11][21]=2;    
      
      	//ǩ������
      	iArray[12]=new Array();
      	iArray[12][0]="ǩ������";         		//����
      	iArray[12][1]="80px";            		//�п�
      	iArray[12][2]=100;            			//�����ֵ
      	iArray[12][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[12][21]=2;    
      	      	 
		PlanInfoGrid = new MulLineEnter( "document" , "PlanInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PlanInfoGrid.mulLineCount = 5;
		PlanInfoGrid.displayTitle = 1;
		PlanInfoGrid.hiddenPlus = 1;
		PlanInfoGrid.hiddenSubtraction = 1;
		PlanInfoGrid.canSel = 1;
		PlanInfoGrid.canChk = 1;
		PlanInfoGrid.locked = 1;
		PlanInfoGrid.canChk = 0;
		PlanInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
