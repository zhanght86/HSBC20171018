 <%
//�������ƣ�TempFeeInit.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
       
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    
    initBnfGrid();
    initSubInsuredGrid();
    initDutyGrid();   
    initPremGrid();
    initFactorGrid();
  
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initRiskGrid()
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
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="100px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 

      iArray[4]=new Array();
      iArray[4][0]="���㷽��";      	   		//����
      iArray[4][1]="160px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0; 
               			//�Ƿ���������,1��ʾ����0��ʾ������

      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskGrid.mulLineCount = 0;   
      RiskGrid.displayTitle = 1;
      RiskGrid.canSel = 1;
      RiskGrid.canChk=0
      RiskGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      RiskGrid. hiddenSubtraction=1;

      RiskGrid.loadMulLine(iArray);  
      
      RiskGrid.addOne("RiskGrid");     
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,1,'111302');
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,2,"[��������]");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,3,"0");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,4,"P");
     RiskGrid.addOne("RiskGrid");     
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,1,'211601');
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,2,"[��������]");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,3,"0");
     RiskGrid.setRowColData(RiskGrid.mulLineCount-1,4,"P");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// ��������Ϣ�б�ĳ�ʼ��
function initSubInsuredGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���������˿ͻ���";    	//����
      iArray[1][1]="180px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //�Ƿ�ʹ���Լ���д�ĺ��� 

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�Ա�";         			//����
      iArray[3][1]="140px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="140px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�뱻���˹�ϵ";         		//����
      iArray[5][1]="160px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SubInsuredGrid.mulLineCount = 0;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ��������Ϣ�б�ĳ�ʼ��

function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[1]=new Array();
    iArray[1][0]="���������"; 		//����
    iArray[1][1]="60px";		//�п�
    iArray[1][2]=40;			//�����ֵ
    iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="BnfType";
    iArray[1][9]="���������|notnull&code:BnfType";
   
    iArray[2]=new Array();
    iArray[2][0]="����"; 	//����
    iArray[2][1]="30px";		//�п�
    iArray[2][2]=30;			//�����ֵ
    iArray[2][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][9]="����|len<=20"; //У��
  
    iArray[3]=new Array();
    iArray[3][0]="֤������"; 		//����
    iArray[3][1]="40px";		//�п�
    iArray[3][2]=40;			//�����ֵ
    iArray[3][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][4]="IDType";
    iArray[3][9]="֤������|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="120px";		//�п�
    iArray[4][2]=80;			//�����ֵ
    iArray[4][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][9]="֤������|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="�뱻���˹�ϵ"; 	//����
    iArray[5][1]="60px";		//�п�
    iArray[5][2]=60;			//�����ֵ
    iArray[5][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][4]="Relation";
    iArray[5][9]="�뱻���˹�ϵ|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="�������"; 		//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=40;			//�����ֵ
    iArray[6][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][9]="�������|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="����˳��"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[7][4]="OccupationType";
    iArray[7][9]="����˳��|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="סַ������ţ�"; 		//����
    iArray[8][1]="160px";		//�п�
    iArray[8][2]=100;			//�����ֵ
    iArray[8][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][9]="סַ|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="����"; 		//����
    iArray[9][1]="30px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][4]="customertype";
  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray); 
  
    //��Щ����������loadMulLine����
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}


// �ر�Լ����Ϣ�б�ĳ�ʼ��
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="0px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��Լ����";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��Լ����";         		//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��Լ����";         		//����
      iArray[3][1]="540px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="��Լ����|len<=255";
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //��Щ���Ա�����loadMulLineǰ
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 0;
      SpecGrid.hiddenPlus = 1;
      SpecGrid.hiddenSubtraction = 1;
      SpecGrid.loadMulLine(iArray);           
      //��Щ����������loadMulLine����
      //SpecGrid.setRowColData(1,1,"asdf");
      SpecGrid.setRowColData(0,1,"1");
      SpecGrid.setRowColData(0,2,"1");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//�����б�
function initDutyGrid()

{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
     iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="DutyCode";
    iArray[1][9]="���α���|notnull&code:DutyCode";

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���ڵ�λ";         			//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="��ȡ����";         			//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ڵ�λ";         			//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;             			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[9]=new Array();
      iArray[9][0]="��������";         			//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="���ڵ�λ";         			//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="���ѷ�ʽ";         			//����
      iArray[11][1]="40px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[12]=new Array();
      iArray[12][0]="����ѱ���";         			//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 1;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
//�������б�
function initPremGrid()
{
    var iArray = new Array();
      try 
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
       iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="DutyCode";
    iArray[1][9]="���α���|notnull&code:DutyCode";

      iArray[2]=new Array();
      iArray[2][0]="���ѱ���";         			//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
        iArray[2][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][4]="PlanCode";
    iArray[2][9]="���ѱ���|notnull&code:PlanCode";

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         			//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ʻ���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[7]=new Array();
      iArray[7][0]="�ʻ��������";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[8]=new Array();
      iArray[8][0]="����ѱ���";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //��Щ���Ա�����loadMulLineǰ
	 	     PremGrid.mulLineCount = 1;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}
// ҪԼ��Ϣ�б�ĳ�ʼ��
function initFactorGrid() {  
                             
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="Ҫ�����";    	        //����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
       iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="DutyCode";
    iArray[1][9]="Ҫ�����|notnull&code:DutyCode";
      
      iArray[2]=new Array();
      iArray[2][0]="Ҫ��Ŀ�����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ҫ�ؼ������";         		//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    	iArray[3][4]="DutyCode";
    iArray[3][9]="Ҫ�ؼ������|notnull&code:DutyCode";

      iArray[4]=new Array();
      iArray[4][0]="Ҫ������";         		//����
      iArray[4][1]="300px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ҫ��ֵ";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=150;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=150;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      iArray[6][4]="RiskCode";

      FactorGrid = new MulLineEnter( "fm" , "FactorGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      FactorGrid.mulLineCount = 1;   
      FactorGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      FactorGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}


  
</script>
