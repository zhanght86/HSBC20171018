<%

%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
  document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  //alert(top.opener.document.all('EdorAcceptNo').value);
  //document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  //alert(top.opener.document.all('EdorNo').value);
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('ContNo').value = "";
  document.all('CustomerNo').value = "";	
  }
  catch(ex) {
    //alert("��GEdorTypeICInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

function initForm() {
  try {
 
    initInpBox();
    
    initLCInsuredGrid();
    
    initLCInsured2Grid();
    
    QueryEdorInfo();
    
    queryClick2();
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initLCInsuredGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    				//����1
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         			//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         		//����6
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         		//����6
    iArray[7][1]="150px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    //LCInsuredGrid.canSel = 1;
    LCInsuredGrid.hiddenPlus = 1; 
    LCInsuredGrid.hiddenSubtraction = 1;
    LCInsuredGrid.selBoxEventFuncName = "reportDetailClick";
    LCInsuredGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsuredGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initLCInsured2Grid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    				//����1
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         			//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         		//����6
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         		//����6
    iArray[7][1]="150px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    LCInsured2Grid = new MulLineEnter( "fm" , "LCInsured2Grid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCInsured2Grid.mulLineCount = 0;   
    LCInsured2Grid.displayTitle = 1;
    LCInsured2Grid.canSel = 1;
    LCInsured2Grid.hiddenPlus = 1; 
    LCInsured2Grid.hiddenSubtraction = 1;
    LCInsured2Grid.selBoxEventFuncName = "reportDetail2Click";
    LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsured2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
