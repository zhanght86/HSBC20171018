<%
//GEdorTypeDetailInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
  document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
  document.all('ContNo').value = top.opener.document.all('ContNo').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('CustomerNo').value = "";	
  }
  catch(ex) {
    //alert("��GEdorTypeICInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

function initForm() {
  try {
    initInpBox();
    initLCInsuAccGrid();
    QueryEdorInfo();
    initLPInsuAccGrid();
    QueryBussiness();
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initLCInsuAccGrid() {
  var iArray = new Array();
      
  try {
   iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="������";    				//����1
    iArray[1][1]="130px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0; 
                 			         //�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2]=new Array();
    iArray[2][0]="���ֺ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;   
    
    
    iArray[3]=new Array();
    iArray[3][0]="��������";         			//����2
    iArray[3][1]="220px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;   

 
 
    iArray[4]=new Array();
    iArray[4][0]="�ͻ���";         			//����2
    iArray[4][1]="80px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="����";         			//����8
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�Ա�";         			//����5
    iArray[6][1]="50px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][4]="Sex";              	        //�Ƿ����ô���:null||""Ϊ������
    iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[6][9]="�Ա�|code:Sex&NOTNULL";
    iArray[6][18]=250;
    iArray[6][19]= 0 ;

    iArray[7]=new Array();
    iArray[7][0]="��������";         		//����6
    iArray[7][1]="80px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="֤������";         		//����6
    iArray[8][1]="80px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][4]="IDType";              	        //�Ƿ����ô���:null||""Ϊ������
    iArray[8][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
    iArray[8][9]="֤������|code:IDType&NOTNULL";
    iArray[8][18]=250;
    iArray[8][19]= 0 ;
    

    iArray[9]=new Array();
    iArray[9][0]="֤������";         		//����6
    iArray[9][1]="150px";            		//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
  
  
    LCInsuAccGrid = new MulLineEnter( "fm" , "LCInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsuAccGrid.mulLineCount = 0;   
    LCInsuAccGrid.displayTitle = 1;
    LCInsuAccGrid.canSel = 1;
    LCInsuAccGrid.hiddenPlus = 1; 
    LCInsuAccGrid.hiddenSubtraction = 1;
    //LCInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LCInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsuAccGrid.loadMulLine(iArray);  
    LCInsuAccGrid = new MulLineEnter( "fm" , "LCInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsuAccGrid.mulLineCount = 0;   
    LCInsuAccGrid.displayTitle = 1;
    LCInsuAccGrid.canSel = 1;
    LCInsuAccGrid.hiddenPlus = 1; 
    LCInsuAccGrid.hiddenSubtraction = 1;
    //LCInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LCInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}


// ��Ϣ�б�ĳ�ʼ��
function initLPInsuAccGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="��ؿͻ�����";    				//����1
    iArray[1][1]="50px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="��ر������ֺ���";    				//����1
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="���Ͷ���˻�";         			//����2
    iArray[3][1]="50px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="��ؽ����˻�";         			//����8
    iArray[4][1]="0px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    
    iArray[5]=new Array();
    iArray[5][0]="��ص�λ";    				//����1
    iArray[5][1]="50px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="��ؽ��";    				//����1
    iArray[6][1]="0px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="��������";    				//����1
    iArray[7][1]="50px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����ʱ��";    				//����1
    iArray[8][1]="50px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="������ˮ��";         		//����6
    iArray[9][1]="0px";            		//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    LPInsuAccGrid = new MulLineEnter( "fm" , "LPInsuAccGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LPInsuAccGrid.mulLineCount = 0;   
    LPInsuAccGrid.displayTitle = 1;
    LPInsuAccGrid.canSel = 1;
    LPInsuAccGrid.hiddenPlus = 1; 
    LPInsuAccGrid.hiddenSubtraction = 1;
    //LPInsuAccGrid.selBoxEventFuncName = "reportDetail2Click";
    LPInsuAccGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LPInsuAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>