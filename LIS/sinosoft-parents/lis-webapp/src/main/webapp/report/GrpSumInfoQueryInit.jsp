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
    alert("��GrpSumInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initForm(){
  try{
    initInpBox();
	initSumInfoGrid();
  }
  catch(re){
    alert("GrpSumInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
function initSumInfoGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
      	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      	iArray[0][1]="35px";            		//�п�
    	iArray[0][2]=10;            			//�����ֵ
      	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
	  	//���ִ���
      	iArray[1]=new Array();
      	iArray[1][0]="���ִ���";         			//����
      	iArray[1][1]="85px";            		//�п�
      	iArray[1][2]=60;            			//�����ֵ
      	iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[1][4]="riskcode"; 
      	      
   	  	//�����������
      	iArray[2]=new Array();
      	iArray[2][0]="�����������";         		//����
      	iArray[2][1]="85px";            		//�п�
      	iArray[2][2]=60;            			//�����ֵ
      	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	iArray[2][21]=2; 

	  	//�����������
      	iArray[3]=new Array();
      	iArray[3][0]="�����������";         		//����
      	iArray[3][1]="180px";            		//�п�
      	iArray[3][2]=60;            			//�����ֵ
      	iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      	//��������
      	iArray[4]=new Array();
      	iArray[4][0]="��������";         			//����
      	iArray[4][1]="50px";            		//�п�
      	iArray[4][2]=0;            				//�����ֵ
      	iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

  	  	//�б�����
	  	iArray[5]=new Array();
      	iArray[5][0]="�б�����";         			//����
      	iArray[5][1]="50px";            		//�п�
      	iArray[5][2]=100;            			//�����ֵ
      	iArray[5][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������
      
      	//�ܱ���
	  	iArray[6]=new Array();
      	iArray[6][0]="�ܱ���";         			//����
      	iArray[6][1]="65px";            		//�п�
      	iArray[6][2]=100;            			//�����ֵ
      	iArray[6][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������      
 
   	  	//�ܱ���
 	  	iArray[7]=new Array();
      	iArray[7][0]="�ܱ���";         			//����
      	iArray[7][1]="65px";            		//�п�
      	iArray[7][2]=100;            			//�����ֵ
      	iArray[7][3]=0; 						//�Ƿ���������,1��ʾ����0��ʾ������      	     
      	 
      	
		SumInfoGrid = new MulLineEnter( "document" , "SumInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		SumInfoGrid.mulLineCount = 5;
		SumInfoGrid.displayTitle = 1;
		SumInfoGrid.hiddenPlus = 1;
		SumInfoGrid.hiddenSubtraction = 1;
		SumInfoGrid.canSel = 1;
		SumInfoGrid.canChk = 1;
		SumInfoGrid.locked = 1;
		SumInfoGrid.canChk = 0;
		SumInfoGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
