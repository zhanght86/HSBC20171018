<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initForm(){
	initBnfGrid();
	initInsuredGrid();
	initPolGrid();
	initImpartGrid();
	initAgentImpartGrid();
	initInvestGrid();
	displayImpart();
	displayCont();
	setFocus();
}

//��������Ϣ
function initBnfGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

	iArray[1]=new Array();
    iArray[1][0]="������������"; 		//����
    iArray[1][1]="0px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][10]="SquesNo";			
    iArray[1][11]="0|^1|���������� ^2|�ڶ��������� ^3|������������";  
    iArray[1][19]=1;

    iArray[2]=new Array();
    iArray[2][0]="���"; 		//����
    iArray[2][1]="30px";		//�п�
    iArray[2][2]=40;			//�����ֵ
    iArray[2][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][10]="SquesNo";			
    iArray[2][11]="0|^0|����/���汣�ս������� ^1|��ʱ��ս�������";  
    iArray[2][19]=1;

    iArray[3]=new Array();
    iArray[3][0]="����"; 	//����
    iArray[3][1]="40px";		//�п�
    iArray[3][2]=30;			//�����ֵ
    iArray[3][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="40px";		//�п�
    iArray[4][2]=40;			//�����ֵ
    iArray[4][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][4]="IDType";
    iArray[4][9]="֤������|code:IDType";

    iArray[5]=new Array();
    iArray[5][0]="֤������"; 		//����
    iArray[5][1]="80px";		//�п�
    iArray[5][2]=80;			//�����ֵ
    iArray[5][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="�뱻�����˹�ϵ"; 	//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=60;			//�����ֵ
    iArray[6][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][4]="Relation";

    iArray[7]=new Array();
    iArray[7][0]="�������"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[8]=new Array();
    iArray[8][0]="����˳��"; 		//����
    iArray[8][1]="40px";		//�п�
    iArray[8][2]=100;			//�����ֵ
    iArray[8][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="סַ(�����)"; 		//����
    iArray[9][1]="30px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[10]=new Array();
    iArray[10][0]="��������պ���"; 		//����
    iArray[10][1]="0px";		//�п�
    iArray[10][2]=30;			//�����ֵ
    iArray[10][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="�����˿ͻ���"; 		//����
    iArray[11][1]="0px";		//�п�
    iArray[11][2]=30;			//�����ֵ
    iArray[11][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="���պ���"; 		//����
    iArray[12][1]="0px";		//�п�
    iArray[12][2]=30;			//�����ֵ
    iArray[12][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������

    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" );
    //��Щ���Ա�����loadMulLineǰ
    BnfGrid.mulLineCount = 1;
    BnfGrid.displayTitle = 1;
    BnfGrid.hiddenPlus = 1;
    BnfGrid.hiddenSubtraction = 1;   
    BnfGrid.loadMulLine(iArray);

  } catch(ex) {
    alert("��ProposalInit.jsp-->initBnfGrid�����з����쳣:��ʼ���������!");
  }
}

// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�Ա�";      	   		//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="Sex"; 


      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="���һ�������˹�ϵ";      	   		//����
      iArray[5][1]="100px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[5][9]="�����������˹�ϵ";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="�ͻ��ڲ���";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������";      
	  iArray[6][19]=1;

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      //InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//������������Ϣ�б��ʼ��
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[1]=new Array();
      iArray[1][0]="�������"; 		//����
      iArray[1][1]="50px";		//�п�
      iArray[1][2]=40;			//�����ֵ
      iArray[1][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	  iArray[2]=new Array();
      iArray[2][0]="������������"; 		//����
      iArray[2][1]="0px";		//�п�
      iArray[2][2]=40;			//�����ֵ
      iArray[2][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
	  
      iArray[3]=new Array();
      iArray[3][0]="Ͷ������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  iArray[3][4]="RiskCode";
      iArray[3][18]=300;
      iArray[3][19]=1;            			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���ս��(Ԫ)";         		//����
      iArray[4][1]="35px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="35px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����
      iArray[6][1]="35px";            		        //�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";         		//����
      iArray[7][1]="40px";            		        //�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="40px";            		        //�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="��׼����";         		//����
      iArray[9][1]="40px";            		        //�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="ְҵ�ӷ�";         		//����
      iArray[10][1]="40px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��ע";         		//����
      iArray[11][1]="40px";            		        //�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="�������ֺ���";         		//����
      iArray[12][1]="0px";            		        //�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[13]=new Array();
      iArray[13][0]="������պ���";         		//����
      iArray[13][1]="0px";            		        //�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[14]=new Array();
      iArray[14][0]="�����˿ͻ���";         		//����
      iArray[14][1]="0px";            		        //�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[15]=new Array();
      iArray[15][0]="���պ���";         		//����
      iArray[15][1]="0px";            		        //�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =0;
      //PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}

//������֪
function initImpartGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impartver";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="ѯ������";         		//����
      iArray[3][1]="370px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

 	  iArray[5]=new Array();
      iArray[5][0]="Ͷ���ˡ��ǡ��򡰷�";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="ForYN";
      iArray[5][11]="0|^0|�� ^1|��"; 

      iArray[6]=new Array();
      iArray[6][0]="�������ˡ��ǡ��򡰷�";         		//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][10]="ForYN";
      iArray[6][11]="0|^0|�� ^1|��"; 
      
      iArray[7]=new Array();
      iArray[7][0]="�ڶ��������ˡ��ǡ��򡰷�";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=90;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][10]="ForYN";
      iArray[7][11]="0|^0|�� ^1|��"; 
      
      iArray[8]=new Array();
      iArray[8][0]="�����������ˡ��ǡ��򡰷�";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=90;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][10]="ForYN";
      iArray[8][11]="0|^0|�� ^1|��"; 
      
      iArray[9]=new Array();
      iArray[9][0]="��պ���";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=90;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 5;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.loadMulLine(iArray);  
      ImpartGrid.locked = 1;
      ImpartGrid.canSel = 0;
      ImpartGrid.canChk = 0;
      ImpartGrid.hiddenPlus = 1;
      ImpartGrid.hiddenSubtraction = 1;       
      
      //��Щ����������loadMulLine����
    }
    catch(ex) {
      alert(ex);
    }
}

//ҵ��Ա��֪
function initAgentImpartGrid(){
	  
	  var iArray=new Array();
	  
	  try{
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;      					//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ѯ������";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="���ǡ��򡰷�";         		//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="ForYN";
      iArray[5][11]="0|^0|�� ^1|��"; 

      iArray[6]=new Array();
      iArray[6][0]="�������ˡ��ǡ��򡰷�";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=90;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][10]="ForYN";
      iArray[6][11]="0|^0|�� ^1|��"; 
      
      iArray[7]=new Array();
      iArray[7][0]="�ڶ��������ˡ��ǡ��򡰷�";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=90;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][10]="ForYN";
      iArray[7][11]="0|^0|�� ^1|��"; 
      
      iArray[8]=new Array();
      iArray[8][0]="�����������ˡ��ǡ��򡰷�";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=90;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][10]="ForYN";
      iArray[8][11]="0|^0|�� ^1|��"; 
      
      iArray[9]=new Array();
      iArray[9][0]="��պ���";         		//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=90;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AgentImpartGrid.mulLineCount = 0;   
      AgentImpartGrid.displayTitle = 1;
      AgentImpartGrid.loadMulLine(iArray);
      AgentImpartGrid.canSel = 0;
      AgentImpartGrid.canChk = 0;
      AgentImpartGrid.hiddenPlus = 1;
      AgentImpartGrid.hiddenSubtraction = 1;          
      }catch(ex){
      	alert("ִ��initAgentImpartGridʱ����");
      }
}

function initInvestGrid(){
	  
	  var iArray=new Array();
	  
	  try{
	  iArray[0]=new Array();
	  iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;      					//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="Ͷ���˻�����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ͷ���˻���ռ���շѱ���";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ���˻���ռ׷�ӱ��շ�";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  InvestGrid = new MulLineEnter( "fm" , "InvestGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InvestGrid.mulLineCount = 0;   
      InvestGrid.displayTitle = 1;
      InvestGrid.loadMulLine(iArray);
      InvestGrid.locked = 1;
      InvestGrid.canSel = 1;
      InvestGrid.canChk = 0;
      InvestGrid.hiddenPlus = 1;
      InvestGrid.hiddenSubtraction = 1;          
      }catch(ex){
      	alert("ִ��initAgentImpartGridʱ����");
      }
}

</script>
