<%
//GEdorTypeXTInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>                       

<script language="JavaScript">  
                                    

function initForm()
{
    try
    {
       fm.GIManageCom.value= manageCom;
       fm.GLManageCom.value= manageCom;
       fm.GEManageCom.value= manageCom;
       fm.GEUManageCom.value= manageCom;
       showOneCodeName('station', 'GIManageCom', 'GIManageComName');
       showOneCodeName('station', 'GLManageCom', 'GLManageComName');
       showOneCodeName('station', 'GEManageCom', 'GEManageComName');
       showOneCodeName('station', 'GEUManageCom', 'GEUManageComName');
    }
    catch (ex)
    {
        alert("�� WFEdorNoscanAppInit.jsp --> initForm �����з����쳣:��ʼ��������� ");
    }
}

// ��Ϣ�б�ĳ�ʼ��
function initGrpSinglePolGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			  //�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="����������";    	  //����1
    iArray[1][1]="100px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�Ա�";         			//����3
    iArray[3][1]="40px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         		  //����4
    iArray[4][1]="60px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="֤������";         	//����5
    iArray[5][1]="60px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="����";         	//����6
    iArray[6][1]="100px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="��Ч����";         	//����7
    iArray[7][1]="60px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����"; //����8
    iArray[8][1]="100px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="����"; //����8
    iArray[9][1]="60px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="����"; //����8
    iArray[10][1]="100px";            	//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[11]=new Array();
    iArray[11][0]="����״̬"; //����8
    iArray[11][1]="100px";            	//�п�
    iArray[11][2]=100;            			//�����ֵ
    iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    GrpSinglePolGrid = new MulLineEnter( "fm" , "GrpSinglePolGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    GrpSinglePolGrid.mulLineCount = 0;    
    GrpSinglePolGrid.displayTitle = 1;
    //GrpSinglePolGrid.canChk = 0;
    //GrpSinglePolGrid.canSel = 1;
    GrpSinglePolGrid.hiddenPlus = 1; 
    GrpSinglePolGrid.hiddenSubtraction = 1;
    //LCInsuredGrid.selBoxEventFuncName = "queryPolGrid";
    GrpSinglePolGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    GrpSinglePolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// ��Ϣ�б�ĳ�ʼ��
function initGrpPolGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���屣�յ���";    	  //����1
    iArray[1][1]="100px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ӡˢ��";         		//����2
    iArray[2][1]="80px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����3
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         		  //����4
    iArray[4][1]="60px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="Ͷ����";         	//����5
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="����";         	//����6
    iArray[6][1]="50px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="����";         	//����7
    iArray[7][1]="60px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����";         	//����8
    iArray[8][1]="60px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="��Ч����";         		  //����9
    iArray[9][1]="80px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="�����ڼ�";        		//����10
    iArray[10][1]="60px";            	//�п�
    iArray[10][2]=100;            		//�����ֵ
    iArray[10][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[11]=new Array();
    iArray[11][0]="�ŵ�״̬";        		//����10
    iArray[11][1]="60px";            	//�п�
    iArray[11][2]=100;            		//�����ֵ
    iArray[11][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

    GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    GrpPolGrid.mulLineCount = 0;   
    GrpPolGrid.displayTitle = 1;
    //GrpPolGrid.canChk = 1; 
    GrpPolGrid.hiddenPlus = 1; 
    GrpPolGrid.hiddenSubtraction = 1;
    //GrpPolGrid.chkBoxEventFuncName = "reportDetail2Click"; 
    GrpPolGrid.detailInfo = "������ʾ��ϸ��Ϣ";
    GrpPolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

function initGrpSingleAccGrid()
{
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
    iArray[2][0]="����";         		//����2
    iArray[2][1]="60px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="���֤��";         			//����3
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         			//����3
    iArray[4][1]="60px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="���";         			//����3
    iArray[5][1]="60px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="��������";         			//����3
    iArray[6][1]="60px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="��������";         			//����3
    iArray[7][1]="80px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="�˻�����";         			//����3
    iArray[8][1]="150px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


    iArray[9]=new Array();
    iArray[9][0]="����Ա";         			//����3
    iArray[9][1]="60px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 

    GrpSingleAccGrid = new MulLineEnter("fm" ,"GrpSingleAccGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    GrpSingleAccGrid.mulLineCount = 0; 
    GrpSingleAccGrid.displayTitle = 1;
    GrpSingleAccGrid.hiddenSubtraction = 1;
    GrpSingleAccGrid.hiddenPlus = 1; 
    GrpSingleAccGrid.hiddenSubtraction = 1;
   	GrpSingleAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initGrpEdorGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="��ȫ��Ŀ";    	  //����1
    iArray[1][1]="50px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="���屣�յ���";    	  //����1
    iArray[2][1]="100px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="������";         		//����2
    iArray[3][1]="100px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    
    iArray[4]=new Array();
    iArray[4][0]="����";         		//����2
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="����";         			//����3
    iArray[5][1]="60px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="Ͷ����";         		  //����4
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[7]=new Array();
    iArray[7][0]="�ϼƽ��";         		  //����4
    iArray[7][1]="80px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[8]=new Array();
    iArray[8][0]="�漰����";         		  //����4
    iArray[8][1]="80px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[9]=new Array();
    iArray[9][0]="��������";         		  //����4
    iArray[9][1]="80px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[10]=new Array();
    iArray[10][0]="����Ա";         		  //����4
    iArray[10][1]="80px";            	//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[11]=new Array();
    iArray[11][0]="ȷ������";         		  //����4
    iArray[11][1]="80px";            	//�п�
    iArray[11][2]=100;            			//�����ֵ
    iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[12]=new Array();
    iArray[12][0]="��ȫʱЧ";         		  //����4
    iArray[12][1]="80px";            	//�п�
    iArray[12][2]=100;            			//�����ֵ
    iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������


    iArray[13]=new Array();
    iArray[13][0]="��ע";         		  //����4
    iArray[13][1]="80px";            	//�п�
    iArray[13][2]=100;            			//�����ֵ
    iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    GrpEdorGrid = new MulLineEnter("fm" ,"GrpEdorGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    GrpEdorGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    GrpEdorGrid.hiddenSubtraction = 1;
    GrpEdorGrid.hiddenPlus = 1; 
    GrpEdorGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    GrpEdorGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

//=================================
function initGrpLoanGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ŵ���";    	    //����1
      iArray[1][1]="140px";              //�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";    	//����2
      iArray[2][1]="120px";             //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����3
      iArray[3][1]="160px";            	//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˻����";        //����4
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="�����";      //����5
      iArray[5][1]="60px";            	//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="ӡ��˰";         	//����6
      iArray[6][1]="40px";            	//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[7]=new Array();
      iArray[7][0]="�������";         	//����6
      iArray[7][1]="80px";            	//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[8]=new Array();
      iArray[8][0]="������";         	//����6
      iArray[8][1]="80px";            	//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[9]=new Array();
      iArray[9][0]="������Ϣ";         	//����6
      iArray[9][1]="60px";            	//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[10]=new Array();
      iArray[10][0]="��������";         	//����6
      iArray[10][1]="80px";            	//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      iArray[11]=new Array();
      iArray[11][0]="��ע";         	//����6
      iArray[11][1]="60px";            	//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   

      
                  
      GrpLoanGrid = new MulLineEnter( "fm" , "GrpLoanGrid" ); 
      GrpLoanGrid.displayTitle = 1;
      GrpLoanGrid.hiddenPlus = 1;
      //GrpLoanGrid.canSel = 1;
      //GrpLoanGrid.selBoxEventFuncName = "queryAccInfo";
      GrpLoanGrid.hiddenSubtraction = 1;
      GrpLoanGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initGrpEdorUserGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          		  	//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�û�����";    	  //����1
    iArray[1][1]="60px";            	//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����";    	  //����1
    iArray[2][1]="60px";            	//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="����";         		//����2
    iArray[3][1]="80px";            	//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    
    iArray[4]=new Array();
    iArray[4][0]="Ȩ�޼���";         		//����2
    iArray[4][1]="50px";            	//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="��Ա״̬";         			//����3
    iArray[5][1]="60px";            	//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�Ƿ�ͨ������";         		  //����4
    iArray[6][1]="80px";            	//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[7]=new Array();
    iArray[7][0]="ע��ʱ��";         		  //����4
    iArray[7][1]="80px";            	//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[8]=new Array();
    iArray[8][0]="ע��ʱ��";         		  //����4
    iArray[8][1]="80px";            	//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[9]=new Array();
    iArray[9][0]="��ע";         		  //����4
    iArray[9][1]="80px";            	//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������


    GrpEdorUserGrid = new MulLineEnter("fm" ,"GrpEdorUserGrid" ); 
    
    //��Щ���Ա�����loadMulLineǰ
    GrpEdorUserGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    GrpEdorUserGrid.hiddenSubtraction = 1;
    GrpEdorUserGrid.hiddenPlus = 1; 
    GrpEdorUserGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "������ʾ��ϸ��Ϣ";
    GrpEdorUserGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

</script>
