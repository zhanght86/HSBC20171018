<%
//GEdorTypeXTInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;	
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  }
  catch(ex) {
    alert("��GEdorTypeXTInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}                                     

function initForm() {
	lockScreen('lkscreen'); 
  try {
  	
    initInpBox();
    initLCInsuredGrid();  
    initLCInsured2Grid();
    QueryEdorInfo();
    queryClick();    
  	queryPEdorList(); 
  	queryGrpInfo(); 
		divGroupPol2.style.display='none';
		unlockScreen('lkscreen');
  }
  catch(re) {
    alert("GEdorTypeXTInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    unlockScreen('lkscreen');
  }
  
}

// ��Ϣ�б�ĳ�ʼ��
function initLCInsuredGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			  //�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    	  //����1
    iArray[1][1]="110px";            	//�п�
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
    iArray[4][0]="�Ա�";         		  //����4
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
    iArray[8][0]="���˱����ܱ���/Ԫ"; //����8
    iArray[8][1]="100px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
	iArray[9][0]="����";          
	iArray[9][1]="50px";              
	iArray[9][2]=60;             
	iArray[9][3]=2;               
	iArray[9][4]="Currency";                 
	iArray[9][9]="����|code:Currency";

    

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canChk = 0;
    LCInsuredGrid.canSel = 1;
    LCInsuredGrid.hiddenPlus = 1; 
    LCInsuredGrid.hiddenSubtraction = 1;
     LCInsuredGrid.selBoxEventFuncName = "queryPolGrid";
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
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    	  //����1
    iArray[1][1]="100px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����3
    iArray[3][1]="50px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         		  //����4
    iArray[4][1]="30px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         	//����5
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         	//����6
    iArray[6][1]="50px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         	//����7
    iArray[7][1]="150px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="������Ч����";         	//����8
    iArray[8][1]="80px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="���˱����ܱ���/Ԫ";         		  //����9
    iArray[9][1]="100px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="Э���˷ѽ��";        		//����10
    iArray[10][1]="100px";            	//�п�
    iArray[10][2]=100;            		//�����ֵ
    iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
	iArray[11][0]="����";          
	iArray[11][1]="50px";              
	iArray[11][2]=60;             
	iArray[11][3]=2;               
	iArray[11][4]="Currency";                 
	iArray[11][9]="����|code:Currency";

    

    LCInsured2Grid = new MulLineEnter( "fm" , "LCInsured2Grid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LCInsured2Grid.mulLineCount = 0;   
    LCInsured2Grid.displayTitle = 1;
    LCInsured2Grid.canChk = 1; 
    LCInsured2Grid.hiddenPlus = 1; 
    LCInsured2Grid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    LCInsured2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

function initLPGrpContGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���屣����";    	  //����1
    iArray[1][1]="100px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ԭ����";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="ԭ����";         			//����3
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ԭ����";         		  //����4
    iArray[4][1]="80px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="ԭͶ������";         	//����5
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�䶯�󱣷�";         	//����6
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�䶯�󱣶�";         	//����7
    iArray[7][1]="80px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="�䶯�����";         	//����8
    iArray[8][1]="80px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="�䶯��Ͷ������";  //����9
    iArray[9][1]="80px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
	iArray[10][0]="����";          
	iArray[10][1]="50px";              
	iArray[10][2]=60;             
	iArray[10][3]=2;               
	iArray[10][4]="Currency";                 
	iArray[10][9]="����|code:Currency";

    
 

    LPGrpContGrid = new MulLineEnter("fm" ,"LPGrpContGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LPGrpContGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    LPGrpContGrid.hiddenSubtraction = 1;
    LPGrpContGrid.hiddenPlus = 1; 
    LPGrpContGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
   	LPGrpContGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initLPGrpPolGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�������ֺ�";    	  //����1
    iArray[1][1]="100px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���ֱ��";    	  //����1
    iArray[2][1]="60px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="ԭ����";         		//����2
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ԭ����";         			//����3
    iArray[4][1]="80px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="ԭ����";         		  //����4
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[6]=new Array();
    iArray[6][0]="ԭ�α�����";         	//����5
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�䶯�󱣷�";         	//����6
    iArray[7][1]="80px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="�䶯�󱣶�";         	//����7
    iArray[8][1]="80px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="�䶯�����";         	//����8
    iArray[9][1]="80px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="�䶯��α�����";  //����9
    iArray[10][1]="80px";            	//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
	iArray[11][0]="����";          
	iArray[11][1]="50px";              
	iArray[11][2]=60;             
	iArray[11][3]=2;               
	iArray[11][4]="Currency";                 
	iArray[11][9]="����|code:Currency";

    
 

    LPGrpPolGrid = new MulLineEnter("fm" ,"LPGrpPolGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    LPGrpPolGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    LPGrpPolGrid.hiddenSubtraction = 1;
    LPGrpPolGrid.hiddenPlus = 1; 
    LPGrpPolGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    LPGrpPolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initLCPolGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          		  	//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�������ֺ�";    	  //����1
      iArray[1][1]="80px";             //�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";        	//����2
      iArray[2][1]="50px";              //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         	//����3
      iArray[3][1]="120px";            	//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�ͻ���";         		//����4
      iArray[4][1]="60px";            	//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="�ͻ�����";         	//����5
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��Ч��";        //����6
      iArray[6][1]="60px";            	//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[7]=new Array();
      iArray[7][0]="����/�ּ�";        //����6
      iArray[7][1]="60px";            	//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[8]=new Array();
      iArray[8][0]="����";        //����6
      iArray[8][1]="60px";            	//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[9]=new Array();
      iArray[9][0]="����";        //����6
      iArray[9][1]="60px";            	//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      
      iArray[10]=new Array();
      iArray[10][0]="Э���˷ѽ��";        //����6
      iArray[10][1]="70px";            	//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ��?
      iArray[10][9]="Э���˷�|NOTNULL&NUM";  
      
      iArray[11]=new Array();
	  iArray[11][0]="����";          
	  iArray[11][1]="50px";              
	  iArray[11][2]=60;             
	  iArray[11][3]=2;               
	  iArray[11][4]="Currency";                 
	  iArray[11][9]="����|code:Currency";

      
      
                  
      
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.hiddenPlus = 1;
      LCPolGrid.hiddenSubtraction = 1;
      //LCPolGrid.canSel = 1;
      //LCPolGrid.selBoxEventFuncName = "reportDetailClick";
      //LCInsureAccClassGrid.chkBoxEventFuncName = "reportDetailClick1";
      LCPolGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
