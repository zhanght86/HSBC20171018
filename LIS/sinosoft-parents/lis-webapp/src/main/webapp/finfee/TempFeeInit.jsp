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
GlobalInput tGI = new GlobalInput(); //repair:
tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
String ManageCom = tGI.ComCode;
%>  
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
    Frame1.style.display='';
    Frame2.style.display=''; 
    document.all('TempFeeNo').value ='';            
    document.all('ManageCom').value = '<%=ManageCom%>';
    document.all('PayDate').value = '<%=CurrentDate%>';
    document.all('AgentGroup').value = '';
    document.all('AgentCode').value = '';     
    document.all('AgentName').value = '';
    document.all('PolicyCom').value = '';
    document.all('TempFeeType').value = '';
    document.all('TempFeeTypeName').value = '';          
    document.all('TempFeeCount').value = 0;
    //document.all('SumTempFee').value = 0.0 ; 
    document.all('TempFeeType').value =''; 
    document.all('TempFeeTypeName').value =''; 
    initFee();


  
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initFee()
{ 
  try
  { 
       
    document.all('CertifyFlag').value = '';   
    document.all('CertifyFlagName').value = '';  
    document.all('InputNo1').value = '';
    document.all('InputNo2').value = ''; 
    document.all('InputNoB').value = ''; 
    document.all('InputNoB1').value = ''; 
    document.all('CardCode').value = '';
    document.all('InputNoC').value = '';
    document.all('InputNo3').value = '';
    document.all('XQCardNo').value = '';
    document.all('InputNo4').value = '';
    document.all('InputNo5').value = '';
    document.all('InputNo7').value = '';
    document.all('InputNo8').value = '';
    document.all('InputNo11').value = '';
    document.all('InputNo12').value = '';
    document.all('InputNo13').value = '';
    document.all('InputNo14').value = '';
    document.all('InputNo99').value = '';
    document.all('InputNo19').value = ''; 
    document.all('CertifyFlag9').value = ''; 
    document.all('InputNoH11').value = '';
    document.all('InputNoH12').value = '';
    document.all('PayMode').value='';
    document.all('PayModeName').value='';

  
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->initFee�����з����쳣:��ʼ���������!");
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

    initTempGrid();  
    initTempClassGrid(); 
    initSumTempGrid();
    initTempToGrid();  
    initTempClassToGrid(); 
    initDivPayMode();
    initFinFeeQueryGrid();
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initTempGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      //iArray[1][4]="RiskCodeFin";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][4]="RiskCode";
      iArray[1][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";              	        //��������з������ô����еڼ�λֵ
      iArray[1][9]="���ֱ���|code:RiskCode&NOTNULL";


      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="currency";
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
      iArray[3][9]="����|code:currency&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="���ѽ��|NUM&NOTNULL";
      iArray[4][22]="col3";					//�ɱ��־���

      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="60px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���Ѽ��";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2;                   //�Ƿ���������,1��ʾ����0��ʾ������      
      iArray[6][4]="payintv";
      //iArray[5][11]="0|^0|����^1|�½�^12|���^A|������";              			
            
      iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������  
     
      
      iArray[8]=new Array();
      iArray[8][0]="���ѵ�λ";      	   		//����
      iArray[8][1]="0px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="���ѵ�λ";      	   		//����
      iArray[9][1]="0px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=0;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ���շѷ����б�ĳ�ʼ��
function initTempClassGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ѷ�ʽ";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�



      iArray[2]=new Array();
      iArray[2][0]="��������";         		        //����
      iArray[2][1]="70px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="90px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";
	  iArray[4][23]="0";

      
      iArray[5]=new Array();
      iArray[5][0]="Ʊ�ݺ���";      	   		//����
      iArray[5][1]="140px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][9]="Ʊ�ݺ���|NULL|LEN>10";              //���Ի�����

      iArray[6]=new Array();
      iArray[6][0]="֧Ʊ����";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=10;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


  
      iArray[8]=new Array();
      iArray[8][0]="��������";      	   		//����
      iArray[8][1]="60px";            			//�п�
      iArray[8][2]=40;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[9]=new Array();
      iArray[9][0]="�����˺�";      	   		//����
      iArray[9][1]="140px";            		//�п�
      iArray[9][2]=40;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="����";      	   		//����
      iArray[10][1]="100px";            			//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
      iArray[11]=new Array();
      iArray[11][0]="�տ�����";      	   		//����
      iArray[11][1]="0px";            			//�п�
      iArray[11][2]=40;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[12]=new Array();
      iArray[12][0]="�տ������˺�";      	   		//����
      iArray[12][1]="140px";            		//�п�
      iArray[12][2]=40;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="�տ����л���";      	   		//����
      iArray[13][1]="0px";            			//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="���Ѽ��";      	   		//����
      iArray[14][1]="0px";            			//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[15]=new Array();
      iArray[15][0]="��������";      	   		//����
      iArray[15][1]="0px";            			//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������         

      iArray[16]=new Array();
      iArray[16][0]="���ѵ�λ";      	   		//����
      iArray[16][1]="0px";            			//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[17]=new Array();                                                  
      iArray[17][0]="֤������";      	   		//����                             
      iArray[17][1]="60px";            			//�п�                             
      iArray[17][2]=60;            			//�����ֵ                             
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      
      iArray[18]=new Array();
      iArray[18][0]="֤������";      	   		//����
      iArray[18][1]="120px";            			//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[19]=new Array();
      iArray[19][0]="�ֽ��շѷ�ʽ";      	   		//����
      iArray[19][1]="50px";            			//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassGrid.mulLineCount = 0;   
      TempClassGrid.displayTitle = 1;     
      TempClassGrid.hiddenPlus=1;
      TempClassGrid.canSel=1;
      TempClassGrid.selBoxEventFuncName="showMul";
      TempClassGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
  }
  
// ���շ���Ϣ�б�ĳ�ʼ��
function initSumTempGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="����";      	   		//����
      iArray[1][1]="80px";            			//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѽ��";      	   		//����
      iArray[2][1]="80px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[2][22]="col1";
	  iArray[2][23]="0";    
      
      SumTempGrid = new MulLineEnter( "fm" , "SumTempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SumTempGrid.mulLineCount = 0;   
      SumTempGrid.displayTitle = 1;
      SumTempGrid.locked=1;
      SumTempGrid.hiddenPlus=1;
      SumTempGrid.hiddenSubtraction=1;       
      SumTempGrid.loadMulLine(iArray);      
      }
      catch(ex)
      {
        alert(ex);
      }
}

////��ʼ�������ݽ��ѷ������ݵ�Grid
function initTempToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����վݺ�";          		//����
      iArray[1][1]="160px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="���շ�����";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;
            
      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ѽ��";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][22]="col4";
	  iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="��������";          		//����
      iArray[6][1]="0px";      	      		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ֱ���";          		//����
      iArray[8][1]="80px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���������";          		//����
      iArray[9][1]="80px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����˱���";          		//����
      iArray[10][1]="80px";      	      		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��������";      	   	        //����
      iArray[11][1]="160px";            			//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//����

      iArray[12]=new Array();
      iArray[12][0]="������������";      	   	        //����
      iArray[12][1]="100px";            			//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0;              			//����

      iArray[13]=new Array();
      iArray[13][0]="���Ѽ��";          		//����
      iArray[13][1]="80px";      	      		//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�����ڼ�";      	   	        //����
      iArray[14][1]="80px";            			//�п�
      iArray[14][2]=20;            			//�����ֵ
      iArray[14][3]=0;              			//����

      iArray[15]=new Array();
      iArray[15][0]="�ɷѵ�λ";      	   	        //����
      iArray[15][1]="0px";            			//�п�
      iArray[15][2]=20;            			//�����ֵ
      iArray[15][3]=3;              			//����

      iArray[16]=new Array();
      iArray[16][0]="Ͷ��������";      	   	   //����
      iArray[16][1]="100px";            			//�п�
      iArray[16][2]=20;            			//�����ֵ
      iArray[16][3]=3;              			//����

      iArray[17]=new Array();
      iArray[17][0]="��ע";      	   	        //����
      iArray[17][1]="100px";            			//�п�
      iArray[17][2]=500;            			//�����ֵ
      iArray[17][3]=3;              			//����
      

      TempToGrid = new MulLineEnter( "fmSave" , "TempToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempToGrid.mulLineCount = 0;   
      TempToGrid.displayTitle = 1;
      TempToGrid.locked=1;
      TempToGrid.hiddenPlus=1;
      TempToGrid.hiddenSubtraction=1;      
      TempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}



//��ʼ�������ݽ��ѷ������ݵ�Grid
function initTempClassToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����վݺ�";          		//����
      iArray[1][1]="160px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���ѷ�ʽ";          		//����
      iArray[2][1]="60px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";          		//����
      iArray[3][1]="80px";      	      		//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ѽ��";          		//����
      iArray[5][1]="80px";      	      		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][22]="col4";
	  iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="��������";          		//����
      iArray[6][1]="80px";      	      		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������";          		//����
      iArray[7][1]="0px";      	      		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="Ʊ�ݺ���";          		//����
      iArray[8][1]="100px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="֧Ʊ����";          		//����
      iArray[9][1]="60px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="��������";      	   		//����
      iArray[10][1]="60px";            			//�п�
      iArray[10][2]=40;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�����˺�";      	   		//����
      iArray[11][1]="140px";            			//�п�
      iArray[11][2]=40;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="����";      	   		//����
      iArray[12][1]="60px";            			//�п�
      iArray[12][2]=10;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�տ�����";      	   		//����
      iArray[13][1]="0px";            			//�п�
      iArray[13][2]=40;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�տ������˺�";      	   		//����
      iArray[14][1]="140px";            		//�п�
      iArray[14][2]=40;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[15]=new Array();
      iArray[15][0]="�տ����л���";      	   		//����
      iArray[15][1]="0px";            			//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[16]=new Array();
      iArray[16][0]="���Ѽ��";      	   		//����
      iArray[16][1]="0px";            			//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[17]=new Array();
      iArray[17][0]="�����ڼ�";      	   		//����
      iArray[17][1]="0px";            			//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[18]=new Array();
      iArray[18][0]="�����˻�";      	   		//����
      iArray[18][1]="0px";            			//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[19]=new Array();
      iArray[19][0]="֤������";      	   		//����
      iArray[19][1]="60px";            			//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[20]=new Array();
      iArray[20][0]="֤������";      	   		//����
      iArray[20][1]="140px";            			//�п�
      iArray[20][2]=60;            			//�����ֵ
      iArray[20][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[21]=new Array();
      iArray[21][0]="�ֽ��շѷ�ʽ";      	   		//����
      iArray[21][1]="140px";            			//�п�
      iArray[21][2]=60;            			//�����ֵ
      iArray[21][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      TempClassToGrid = new MulLineEnter( "fmSave" , "TempClassToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassToGrid.mulLineCount = 0;   
      TempClassToGrid.displayTitle = 1;
      TempClassToGrid.locked=1;
      TempClassToGrid.hiddenPlus=1;
      TempClassToGrid.hiddenSubtraction=1;
      TempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initTempGridReadOnly()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="���ѽ��|NUM&NOTNULL";
      iArray[4][22]="col3";
	  iArray[4][23]="0";
      
      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }      
  }

// ���շ���Ϣ�б�ĳ�ʼ��
//��ͬ����ʱ����ʾ������Ϣ
function initTempGridReadOnlyCont()
  {   
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";
	  iArray[4][23]="0";

      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }      
  }

//5��������
function initTempGridReadOnlyDo()
  {   
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="currency";
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
      iArray[3][9]="����|code:currency&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";					//�ɱ��־���

      iArray[5]=new Array();
      iArray[5][0]="�ͻ�����";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�ͻ�����";      	   		//����
      iArray[6][1]="160px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����������";      	   		//����
      iArray[7][1]="160px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="��ע��Ϣ";      	   		//����
      iArray[8][1]="400px";            			//�п�
      iArray[8][2]=500;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }      
  }




// ���շ���Ϣ�б�ĳ�ʼ��(����ݽ�������Ϊ3-�����ڽ���)
function initTempGridReadOnlyType()
{                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="currency";
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
      iArray[3][9]="����|code:currency&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";					//�ɱ��־���

      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }  
 }    

// ���շѷ�����Ϣ�б�ĳ�ʼ��(����ݽ�������Ϊ5-���пۿ�)
// ���շѷ����б�ĳ�ʼ��
function initTempClassGridType5()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ѷ�ʽ";          		//����
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][14]="4";              	        //�Ƿ����ô���:null||""Ϊ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		        //����
      iArray[2][1]="70px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][14]="����ת��";
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="currency";
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
      iArray[3][9]="����|code:currency&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="90px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="���ѽ��|NUM&NOTNULL";
      iArray[4][22]="col3";					//�ɱ��־���
      
      iArray[5]=new Array();
      iArray[5][0]="Ʊ�ݺ���";      	   		//����
      iArray[5][1]="0px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";      	   		//����
      iArray[6][1]="0px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=40;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][4]="FINAbank";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[7][9]="��������|code:FINAbank&NOTNULL";
      
      iArray[8]=new Array();
      iArray[8][0]="�����˺�";       	   		//����
      iArray[8][1]="140px";             			//�п�
      iArray[8][2]=40;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][9]="�����˺�|NOTNULL";              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����";      	   		//����
      iArray[9][1]="100px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][9]="����|NOTNULL";              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassGrid.mulLineCount = 0;   
      TempClassGrid.displayTitle = 1;
      TempClassGrid.locked = 1;     
      TempClassGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }
function initXTempToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����վݺ�";          		//����
      iArray[1][1]="100px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="�վ�����";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;
            
      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="����";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ѽ��";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][22]="col4";					//�ɱ��־���
      iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="��������";          		//����
      iArray[6][1]="0px";      	      		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�������";      	   		//����
      iArray[7][1]="80px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���ֱ���";          		//����
      iArray[8][1]="60px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���������";          		//����
      iArray[9][1]="80px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����˱���";          		//����
      iArray[10][1]="80px";      	      		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��������";      	   	        //����
      iArray[11][1]="100px";            			//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//����

      iArray[12]=new Array();
      iArray[12][0]="������������";      	   	        //����
      iArray[12][1]="100px";            			//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0;              			//����


      TempToGrid = new MulLineEnter( "fmSave" , "TempToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempToGrid.mulLineCount = 0;   
      TempToGrid.displayTitle = 1;
      TempToGrid.locked=1;
      TempToGrid.hiddenPlus=1;
      TempToGrid.hiddenSubtraction=1;      
      TempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}

//Add For �ڲ�ת��
 var QueryLJAGetGrid ;
function initFinFeeQueryGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ʵ������";   		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            		//�����ֵ
      iArray[1][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=60;            		//�����ֵ
      iArray[2][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=200;            	        //�����ֵ
      iArray[4][3]=7;                   	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";
	  iArray[4][23]="0";

      iArray[5]=new Array();
      iArray[5][0]="��ȡ��";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            	        //�����ֵ
      iArray[5][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[6]=new Array();
      iArray[6][0]="��ȡ�����֤";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=200;            	        //�����ֵ
      iArray[6][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������        
       
      QueryLJAGetGrid = new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      QueryLJAGetGrid.mulLineCount = 2;   
      QueryLJAGetGrid.displayTitle = 1;
      QueryLJAGetGrid.hiddenPlus = 1;
      QueryLJAGetGrid.hiddenSubtraction = 1;
      QueryLJAGetGrid.canSel = 1;
      QueryLJAGetGrid.loadMulLine(iArray);
      QueryLJAGetGrid.selBoxEventFuncName = "GetRecord";  

      }
      catch(ex)
      {
        alert(ex);
      }
}

//����ɷ�

function initBalanceGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            		//�����ֵ
      iArray[0][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="RiskCodeFin";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[1][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";              	        //��������з������ô����еڼ�λֵ

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���";   		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            		//�����ֵ
      iArray[4][3]=7;              		//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";
	  iArray[4][23]="0";

      
      iArray[5]=new Array();
      iArray[5][0]="�������";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=200;            	        //�����ֵ
      iArray[5][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������

       
      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ 
      TempGrid.mulLineCount = 0; 
      TempGrid.displayTitle = 1; 
      TempGrid.canSel = 1; 
      TempGrid.loadMulLine(iArray);

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initDivPayMode()  //���һ�ʺ����Ϣ����ʼ��  
 {  
   var ii; 
   for(ii=1;ii<=6;ii++)
   {  
     document.all("PayFee"+ii).value = '';
     var Mode = document.all("divPayMode"+ii).value;
     if ((Mode=='3')||(Mode=='2'))
     { 
       try
         {
	          document.all("ChequeNo"+ii).value ='';
	  	      document.all("ChequeDate"+ii).value ='';
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';
         }
       catch(ex)
         {
         }
  	 }
     if (Mode=='4')
      {
       try
         {
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';
	  	      document.all('IDType').value='';
	  	      document.all('IDNo').value='';
         }
       catch(ex)
         {
         }    
     }
     if (Mode=='5')
      {
	      try
	        {
		         fm.ChequeNo5.value ='';
		       //  fm.PolNo.value ='';
		         fm.OtherNo5.value ='';
		         fm.Drawer5.value ='';
	         
	        }
      catch(ex)
        {
        }
      }
      if (Mode=='6')
      {
       try
         {
	  	      document.all("BankCode"+ii).value ='';
	  	      document.all("BankCode"+ii+"Name").value ='';
	  	      document.all("BankAccNo"+ii).value ='';
	  	      document.all("AccName"+ii).value ='';

         }
       catch(ex)
         {
         }    
     }
     if(Mode=='1')
     {
     	document.all('CashType').value='';
     	document.all('CashTypeName').value='';
     }
    document.all("divPayMode"+ii).style.display="none";
   }
 }
 
 // ���շ���Ϣ�б�ĳ�ʼ��
function initTempGridYS()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="currency";
      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
      iArray[3][9]="����|code:currency&NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="100px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="���ѽ��|NUM&NOTNULL";
      iArray[4][22]="col3";					//�ɱ��־���
      
      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }      
  }
 
 // ���շ���Ϣ�б�ĳ�ʼ��(����ݽ���¼��)
function initTempGridReadOnly6()
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
      iArray[1][1]="50px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="��������";         			//����
      iArray[2][1]="160px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="60px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][22]="col3";
		iArray[4][23]="0";

      iArray[5]=new Array();
      iArray[5][0]="��������";      	   		//����
      iArray[5][1]="160px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���Ѽ��";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][10]="PayIntv";
      iArray[6][11]="0|^12|���|^0|����|^6|�����^3|����^1|�½�^-1|�����ڽ�";
      
      iArray[7]=new Array();
      iArray[7][0]="�����ڼ�";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


      //��Щ���Ա�����loadMulLineǰ 
      TempGrid.mulLineCount = 0; 
      TempGrid.displayTitle = 1; 
      TempGrid.canSel = 1; 
      TempGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }      
}
</script>
