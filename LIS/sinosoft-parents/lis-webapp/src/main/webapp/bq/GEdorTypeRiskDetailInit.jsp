<%
//GEdorTypeRiskDetailInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  

function initInpBox() { 

	//alert("This is GEdorTypeRiskDetailInit.jsp->initInpBox");
  try {        
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorNo').value       = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value    = top.opener.document.all('ContNoApp').value;		
	document.all('EdorType').value     = top.opener.document.all('EdorType').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
  document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
	document.all('ContNo').value       = "";
	document.all('CustomerNo').value   = "";
  }
  catch(ex) {
    alert("��GEdorTypeICInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

//================
function initForm() {

	//alert("This is GEdorTypeRiskDetailInit.jsp -> initForm");
  try {
    initInpBox();    
    initLCInsured0Grid();    
    queryClick0();
    initLCInsuredGrid();   
    initLCInsured2Grid();    
    QueryEdorInfo();
  }
  catch(re) {
    alert("GEdorTypeRiskDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//=====================

// ��Ϣ�б�ĳ�ʼ��
function initLCInsured0Grid() {

  var iArray = new Array();      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[1]=new Array();
    iArray[1][0]="�������ֱ�����";		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[1][1]="120px";         		//�п�
    iArray[1][2]=100;          				//�����ֵ
    iArray[1][3]=0;            				//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="���ֱ���";					//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[2][1]="100px";         		//�п�
    iArray[2][2]=100;          				//�����ֵ
    iArray[2][3]=0;            				//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="��������";    			//����
    iArray[3][1]="260px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����˴�";         	//����
    iArray[4][1]="120px";           	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������";       		//����
    iArray[5][1]="120px";          		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="����״̬";        	//����
    iArray[6][1]="120px";          		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    LCInsured0Grid = new MulLineEnter( "fm" , "LCInsured0Grid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LCInsured0Grid.mulLineCount = 0;    
    LCInsured0Grid.displayTitle = 1;
    LCInsured0Grid.canSel = 1;
    LCInsured0Grid.hiddenPlus = 1; 
    LCInsured0Grid.hiddenSubtraction = 1;
    LCInsured0Grid.selBoxEventFuncName = "reportDetail0Click";
    LCInsured0Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsured0Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert("Big error");
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
    iArray[1][0]="������";    				//����1
    iArray[1][1]="150px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����3
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         			//����4
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         	//����5
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         	//����6
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         	//����7
    iArray[7][1]="150px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="�������ֺ���";      //����8
    iArray[8][1]="0px";          		  //�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canSel = 1;
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
    iArray[1][0]="������";    				//����1
    iArray[1][1]="150px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����3
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         			//����4
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         	//����5
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         	//����6
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         	//����7
    iArray[7][1]="150px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="�������ֺ���";      //����8
    iArray[8][1]="0px";          		  //�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

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
