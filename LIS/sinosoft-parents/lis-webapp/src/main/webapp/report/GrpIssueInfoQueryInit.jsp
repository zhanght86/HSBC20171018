<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">
function initInpBox(){
  try{
    fm.ManageCom.value = comcode;
    fm.sManageComName.value = "";    
    fm.StartDate.value = "";
    fm.EndDate.value = "";    
  }
  catch(ex){
    alert("��GrpIssueInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initForm(){
  try{
    initInpBox();
	initIssueInfoGrid();
  }
  catch(re){
    alert("GrpIssueInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initIssueInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      	iArray[0][1]="35px";            		//�п�
    	iArray[0][2]=10;            			//�����ֵ
      	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      	
		
	  	//�����������
      	iArray[1]=new Array();
      	iArray[1][0]="�����������";         			//����
      	iArray[1][1]="80px";            		//�п�
      	iArray[1][2]=60;            			//�����ֵ
      	iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[1][4]="station";      	
      
   	  	//�����ͬ��
      	iArray[2]=new Array();
      	iArray[2][0]="�����ͬ��";         		//����
      	iArray[2][1]="130px";            		//�п�
      	iArray[2][2]=60;            			//�����ֵ
      	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������	  	
      	iArray[2][21]=2;
      
      	//Ͷ������
      	iArray[3]=new Array();
      	iArray[3][0]="Ͷ������";         			//����
      	iArray[3][1]="130px";            		//�п�
      	iArray[3][2]=0;            				//�����ֵ
      	iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[3][21]=2;

  	  	//���������
	  	iArray[4]=new Array();
      	iArray[4][0]="���������";         		//����
      	iArray[4][1]="65px";            		//�п�
      	iArray[4][2]=100;            			//�����ֵ
      	iArray[4][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[4][21]=2;
      
      	//���������
	  	iArray[5]=new Array();
      	iArray[5][0]="���������";         		//����
      	iArray[5][1]="300px";            		//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������      
 
   	  	//¼����
 	  	iArray[6]=new Array();
      	iArray[6][0]="¼����Ա";         			//����
      	iArray[6][1]="85px";            		//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[6][4]="UserCode"; 				//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[6][21]=2;     	
      
      	//������Ա
      	iArray[7]=new Array();
      	iArray[7][0]="������Ա";         			//����
      	iArray[7][1]="85px";            		//�п�
      	iArray[7][2]=100;            			//�����ֵ
      	iArray[7][3]=2;
      	iArray[7][4]="UserCode"; 				//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[7][21]=2; 			
      
      	//¼������
      	iArray[8]=new Array();
      	iArray[8][0]="¼������";         			//����
      	iArray[8][1]="80px";            		//�п�
      	iArray[8][2]=100;            			//�����ֵ
      	iArray[8][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[8][21]=2;     
      
      	//��������
      	iArray[9]=new Array();
      	iArray[9][0]="��������";         		//����
      	iArray[9][1]="80px";            		//�п�
      	iArray[9][2]=100;            			//�����ֵ
      	iArray[9][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������  
      	iArray[9][21]=2;   
      
      	//ǩ������
      	iArray[10]=new Array();
      	iArray[10][0]="ǩ������";         		//����
      	iArray[10][1]="80px";            		//�п�
      	iArray[10][2]=100;            			//�����ֵ
      	iArray[10][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������ 
      	iArray[10][21]=2;    
       
      	//����λ��
      	iArray[11]=new Array();
      	iArray[11][0]="����λ��";         		//����
      	iArray[11][1]="65px";            		//�п�
      	iArray[11][2]=100;            			//�����ֵ
      	iArray[11][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������    
      	iArray[11][4]="BackObj"; 				//�Ƿ���������,1��ʾ����0��ʾ������  	
      	iArray[11][21]=1;   			
      
      	//���ض���
      	iArray[12]=new Array();
      	iArray[12][0]="���ض���";         		//����
      	iArray[12][1]="85px";            		//�п�
      	iArray[12][2]=100;            			//�����ֵ
      	iArray[12][3]=2; 						//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[12][4]="UserCode"; 				//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[12][21]=2;
      	
		IssueInfoGrid = new MulLineEnter( "document" , "IssueInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		IssueInfoGrid.mulLineCount = 5;
		IssueInfoGrid.displayTitle = 1;
		IssueInfoGrid.hiddenPlus = 1;
		IssueInfoGrid.hiddenSubtraction = 1;
		IssueInfoGrid.canSel = 1;
		IssueInfoGrid.canChk = 1;
		IssueInfoGrid.locked = 1;
		IssueInfoGrid.canChk = 0;
		IssueInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
