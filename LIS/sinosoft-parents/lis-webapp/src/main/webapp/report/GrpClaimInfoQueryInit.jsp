<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageCom.value = "";   
    fm.StartDate.value = "";
    fm.EndDate.value = "";
    fm.GrpContNo.value = "";
    fm.GrpName.value = "";
    fm.sRiskCode.value = "";
    fm.sBlacklist.value = "";
  }
  catch(ex){
    alert("GrpPlanInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initForm(){
  try{
    initInpBox();
	initClaimInfoGrid();
  }
  catch(re){
    alert("GrpPlanInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initClaimInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      	iArray[0][1]="35px";            		//�п�
    	iArray[0][2]=10;            			//�����ֵ
      	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
	  	//�����������
      	iArray[1]=new Array();
      	iArray[1][0]="�����������";         		//����
      	iArray[1][1]="80px";            		//�п�
      	iArray[1][2]=60;            			//�����ֵ
      	iArray[1][21]=2;       	
      
   	  	//�����������
      	iArray[2]=new Array();
      	iArray[2][0]="�����������";         		//����
      	iArray[2][1]="150px";            		//�п�
      	iArray[2][2]=60;            			//�����ֵ
      	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  	//�ŵ���ͬ��
      	iArray[3]=new Array();
      	iArray[3][0]="�ŵ���ͬ��";         		//����
      	iArray[3][1]="130px";            		//�п�
      	iArray[3][2]=60;            			//�����ֵ
      	iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[3][21]=2;
      
      	//���ִ���
      	iArray[4]=new Array();
      	iArray[4][0]="���ִ���";         			//����
      	iArray[4][1]="80px";            		//�п�
      	iArray[4][2]=0;            				//�����ֵ
      	iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[4][21]=2;

  	  	//��������
	  	iArray[5]=new Array();
      	iArray[5][0]="��������";         			//����
      	iArray[5][1]="300px";            		//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      
      	//����������
	  	iArray[6]=new Array();
      	iArray[6][0]="����������";         		//����
      	iArray[6][1]="85px";            		//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[6][21]=2;     
 
   	  	//Ͷ����λ����
 	  	iArray[7]=new Array();
      	iArray[7][0]="Ͷ����λ����";         		//����
      	iArray[7][1]="85px";            		//�п�
      	iArray[7][2]=100;            			//�����ֵ
      	iArray[7][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[7][21]=2;
      	
      	//Ͷ����λ����
 	  	iArray[8]=new Array();
      	iArray[8][0]="Ͷ����λ����";         		//����
      	iArray[8][1]="200px";            		//�п�
      	iArray[8][2]=100;            			//�����ֵ
      	iArray[8][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	//��ҵ�������
 	  	iArray[9]=new Array();
      	iArray[9][0]="��ҵ�������";         		//����
      	iArray[9][1]="150px";            		//�п�
      	iArray[9][2]=100;            			//�����ֵ
      	iArray[9][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	//�ۼƱ���
 	  	iArray[10]=new Array();
      	iArray[10][0]="�ۼƱ���";         		//����
      	iArray[10][1]="80px";            		//�п�
      	iArray[10][2]=100;            			//�����ֵ
      	iArray[10][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	//�ۼ��⸶���
 	  	iArray[11]=new Array();
      	iArray[11][0]="�ۼ��⸶���";         		//����
      	iArray[11][1]="80px";            		//�п�
      	iArray[11][2]=100;            			//�����ֵ
      	iArray[11][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	
      	//��Ч����
 	  	iArray[12]=new Array();
      	iArray[12][0]="��Ч����";         		//����
      	iArray[12][1]="80px";            		//�п�
      	iArray[12][2]=100;            			//�����ֵ
      	iArray[12][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[12][21]=2;
      	
      	//Ͷ����λ���������
 	  	iArray[13]=new Array();
      	iArray[13][0]="Ͷ����λ���������";         //����
      	iArray[13][1]="65px";            		//�п�
      	iArray[13][2]=100;            			//�����ֵ
      	iArray[13][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[13][4]="BlacklistFlag";      	
      	
		ClaimInfoGrid = new MulLineEnter( "document" , "ClaimInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ClaimInfoGrid.mulLineCount = 5;
		ClaimInfoGrid.displayTitle = 1;
		ClaimInfoGrid.hiddenPlus = 1;
		ClaimInfoGrid.hiddenSubtraction = 1;
		ClaimInfoGrid.canSel = 1;
		ClaimInfoGrid.canChk = 1;
		ClaimInfoGrid.locked = 1;
		ClaimInfoGrid.canChk = 0;
		ClaimInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
