 <%
//�������ƣ�TempFeeInit.jsp
//�����ܣ�
//�������ڣ�2009-11-14 08:49:52
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������ �ű�    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
String CurrentDate = PubFun.getCurrentDate();
String CurrentTime = PubFun.getCurrentTime();
GlobalInput tGI = new GlobalInput(); 			//repair:
tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
String ManageCom = tGI.ComCode;
String Operator = tGI.Operator;
loggerDebug("TempFinFeeInit","Operator: "+Operator);
%>  
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
    //Frame1.style.display='';
    //Frame2.style.display=''; 
    document.all('ManageCom').value = '<%=ManageCom%>';
    ////document.all('ManageComName').value = '';
    document.all('PayDate').value = '<%=CurrentDate%>';
    //document.all('AgentGroup').value = '';
    //document.all('AgentCode').value = '0078903448';
    //document.all('TempFeeType').value = '';
    //document.all('TempFeeTypeName').value = '';         
    ////document.all('InputNo').value = '';     
    document.all('Operator').value = '<%=Operator%>';
    //document.all('SumTempFee').value = 0.0 ;    
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
		initQueryLJAGetGrid();
    initFinFeeGrid();  
    
    initTempToGrid(); 
    //����2010-2-26
    initTTempToGrid();  
    initTTempClassToGrid(); 
    //����2010-2-26
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ���շ���Ϣ�б�ĳ�ʼ��
function initFinFeeGrid()
{
    var iArray = new Array();      
    try
    {
    	iArray[0]=new Array();
    	iArray[0][0]="���";         					//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    	iArray[0][1]="0px"; 	           			//�п�
    	iArray[0][2]=1;            						//�����ֵ
    	iArray[0][3]=3;              					//�Ƿ���������,1��ʾ����0��ʾ������
			
    	iArray[1]=new Array();
    	iArray[1][0]="���ѷ�ʽ";          		//����
    	iArray[1][1]="60px";      	      		//�п�
    	iArray[1][2]=20;            					//�����ֵ
    	iArray[1][3]=0;             					//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
			
    	iArray[2]=new Array();
    	iArray[2][0]="���ѷ�ʽ����";         	//����
    	iArray[2][1]="80px";            			//�п�
    	iArray[2][2]=80;            					//�����ֵ
    	iArray[2][3]=0;              					//�Ƿ���������,1��ʾ����0��ʾ������
			
    	iArray[3]=new Array();
    	iArray[3][0]="���ѽ��";      	   		//����
    	iArray[3][1]="60px";            			//�п�
    	iArray[3][2]=20;            					//�����ֵ
    	iArray[3][3]=0;              					//�Ƿ���������,1��ʾ����0��ʾ������
    	iArray[3][9]="���ѽ��|NUM&NOTNULL";
			
    	iArray[4]=new Array();
    	iArray[4][0]="����";      	   				//����
    	iArray[4][1]="0px";            			//�п�
    	iArray[4][2]=40;            					//�����ֵ
    	iArray[4][3]=3;              					//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[5]=new Array();
    	iArray[5][0]="Ʊ�ݺ���";      	   		//����
    	iArray[5][1]="60px";            			//�п�
    	iArray[5][2]=20;            					//�����ֵ
    	iArray[5][3]=0;                   		//�Ƿ���������,1��ʾ����0��ʾ������
    	      
    	iArray[6]=new Array();
    	iArray[6][0]="Ʊ������";      	   		//����
    	iArray[6][1]="60px";            			//�п�
    	iArray[6][2]=20;            					//�����ֵ
    	iArray[6][3]=0;              					//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[7]=new Array();
    	iArray[7][0]="��������";      	   		//����
    	iArray[7][1]="60px";            			//�п�
    	iArray[7][2]=20;            					//�����ֵ
    	iArray[7][3]=0;             					//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
    	
    	iArray[8]=new Array();
    	iArray[8][0]="��������";      	   		//����
    	iArray[8][1]="0px";            				//�п�
    	iArray[8][2]=20;            					//�����ֵ
    	iArray[8][3]=0;              					//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[9]=new Array();
    	iArray[9][0]="�����˺�";      	   		//����
    	iArray[9][1]="90px";            			//�п�
    	iArray[9][2]=20;            					//�����ֵ
    	iArray[9][3]=0;              					//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[10]=new Array();
    	iArray[10][0]="����";      	   				//����
    	iArray[10][1]="60px";            			//�п�
    	iArray[10][2]=20;            					//�����ֵ
    	iArray[10][3]=0;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[11]=new Array();
    	iArray[11][0]="�տ����к���";      	  //����
    	iArray[11][1]="60px";            			//�п� 0
    	iArray[11][2]=40;            					//�����ֵ
    	iArray[11][3]=0;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[12]=new Array();
    	iArray[12][0]="�տ������˺�";      	  //����
    	iArray[12][1]="100px";            			//�п�
    	iArray[12][2]=20;            					//�����ֵ
    	iArray[12][3]=0;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[13]=new Array();
    	iArray[13][0]="�տ����л���";      	  //����
    	iArray[13][1]="0px";            			//�п� 0
    	iArray[13][2]=60;            					//�����ֵ
    	iArray[13][3]=3;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[14]=new Array();
    	iArray[14][0]="֤������";      	   		//����
    	iArray[14][1]="0px";            			//�п� 0
    	iArray[14][2]=60;            					//�����ֵ
    	iArray[14][3]=3;              				//�Ƿ���������,1��ʾ����0��ʾ������ 
    	
    	iArray[15]=new Array();
    	iArray[15][0]="֤������";      	   		//����
    	iArray[15][1]="0px";            			//�п� 0
    	iArray[15][2]=60;            					//�����ֵ
    	iArray[15][3]=3;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	
    	iArray[16]=new Array();
    	iArray[16][0]="��������";      	   		//����
    	iArray[16][1]="0px";            			//�п� 0
    	iArray[16][2]=60;            					//�����ֵ
    	iArray[16][3]=3;              				//�Ƿ���������,1��ʾ����0��ʾ������
    	iArray[16][9]="��������|DATE";
    	      
    	iArray[17]=new Array();
    	iArray[17][0]="Ӧ������";    					//����
    	iArray[17][1]="0px";            			//�п�
    	iArray[17][2]=500;            				//�����ֵ
    	iArray[17][3]=3;              				//���� 3
    	
    	iArray[18]=new Array();
    	iArray[18][0]="����������";      			//����
    	iArray[18][1]="0px";            			//�п�
    	iArray[18][2]=20;            					//�����ֵ
    	iArray[18][3]=3;              				//���� 3
			
    	iArray[19]=new Array();
    	iArray[19][0]="��ʱ�վݺ�";    				//����
    	iArray[19][1]="0px";            			//�п�
    	iArray[19][2]=500;            				//�����ֵ
    	iArray[19][3]=3;              				//���� 3
    	
    	iArray[20]=new Array();
    	iArray[20][0]="����";      	   				//����
    	iArray[20][1]="40px";            			//�п�
    	iArray[20][2]=40;            					//�����ֵ
    	iArray[20][3]=0;  
    	
    	FinFeeGrid = new MulLineEnter( "fm" , "FinFeeGrid" ); 
    	//��Щ���Ա�����loadMulLineǰ
    	FinFeeGrid.mulLineCount = 1;   
    	FinFeeGrid.displayTitle = 1;
    	FinFeeGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    	FinFeeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    	FinFeeGrid.locked=0;      
    	FinFeeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initQueryLJAGetGrid()
{                               
   var iArray = new Array();
   
   try
   {
   	iArray[0]=new Array();
   	iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
   	iArray[0][1]="30px";            		//�п�
   	iArray[0][2]=10;            				//�����ֵ
   	iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   	
   	iArray[1]=new Array();
   	iArray[1][0]="Ӧ������";   				//����
   	iArray[1][1]="160px";            	//�п�
   	iArray[1][2]=100;            			//�����ֵ
   	iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   	
   	iArray[2]=new Array();
   	iArray[2][0]="ҵ�����";						//����
   	iArray[2][1]="160px";            	//�п�
   	iArray[2][2]=60;            				//�����ֵ
   	iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   	
   	iArray[3]=new Array();
   	iArray[3][0]="�������";         	//����
   	iArray[3][1]="80px";            		//�п�
   	iArray[3][2]=200;            	    //�����ֵ
   	iArray[3][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
   	
   	iArray[4]=new Array();
   	iArray[4][0]="������";         		//����
   	iArray[4][1]="80px";            		//�п�
   	iArray[4][2]=200;            	    //�����ֵ
   	iArray[4][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������  
   	
   	iArray[5]=new Array();
   	iArray[5][0]="���������֤";       //����
   	iArray[5][1]="120px";            	//�п�
   	iArray[5][2]=200;            	    //�����ֵ
   	iArray[5][3]=0;                   	//�Ƿ���������,1��ʾ����0��ʾ������
   	
   	QueryLJAGetGrid= new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
   	//��Щ���Ա�����loadMulLineǰ
   	QueryLJAGetGrid.mulLineCount = 1;   
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

// ���շѷ����б�ĳ�ʼ��
function initTempToGrid()
{                               
  var iArray = new Array();
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px"; 	           	//�п�
    iArray[0][2]=1;            				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="֪ͨ�����";      //����
    iArray[1][1]="80px";      	      //�п�
    iArray[1][2]=20;            			//�����ֵ
    iArray[1][3]=0;										//�Ƿ���������,1��ʾ����0��ʾ������ 3
    
    iArray[2]=new Array();
    iArray[2][0]="���ѷ�ʽ";          		//����
    iArray[2][1]="60px";      	      		//�п�
    iArray[2][2]=20;            			//�����ֵ
    iArray[2][3]=0;										//�Ƿ���������,1��ʾ����0��ʾ������ 3
          
    iArray[3]=new Array();
    iArray[3][0]="��������";      	   		//����
    iArray[3][1]="80px";            			//�п�
    iArray[3][2]=10;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
    iArray[4]=new Array();
    iArray[4][0]="���ѽ��";      	  //����
    iArray[4][1]="80px";            	//�п�
    iArray[4][2]=10;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="����";      	  //����
    iArray[5][1]="80px";            	//�п�
    iArray[5][2]=10;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
    iArray[6]=new Array();
    iArray[6][0]="��������";          //����
    iArray[6][1]="0px";      	      //�п�
    iArray[6][2]=20;            			//�����ֵ
    iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 3
		
    iArray[7]=new Array();
    iArray[7][0]="�������";      	  //����
    iArray[7][1]="100px";            	//�п�
    iArray[7][2]=20;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 3
		
    iArray[8]=new Array();
    iArray[8][0]="���ֱ���";          //����
    iArray[8][1]="0px";      	      	//�п�
    iArray[8][2]=20;            			//�����ֵ
    iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 3
		
    iArray[9]=new Array();
    iArray[9][0]="���������";        //����
    iArray[9][1]="100px";      	      //�п�
    iArray[9][2]=20;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 3
		
    iArray[10]=new Array();
    iArray[10][0]="�����˱���";        //����
    iArray[10][1]="100px";      	      //�п�
    iArray[10][2]=20;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
    iArray[11]=new Array();
    iArray[11][0]="ҵ�����";      	  //����
    iArray[11][1]="100px";            //�п�
    iArray[11][2]=20;            			//�����ֵ
    iArray[11][3]=0;              		//����
		
    iArray[12]=new Array();
    iArray[12][0]="ҵ������";     //����
    iArray[12][1]="60px";            //�п�
    iArray[12][2]=20;            			//�����ֵ
    iArray[12][3]=3;              		//����

    iArray[13]=new Array();
    iArray[13][0]="�ɷѼ��";         //����
    iArray[13][1]="0px";      	      //�п�
    iArray[13][2]=20;            			//�����ֵ
    iArray[13][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������ 3

    iArray[14]=new Array();
    iArray[14][0]="�ɷ��ڼ�";      	  //����
    iArray[14][1]="0px";            	//�п�
    iArray[14][2]=20;            			//�����ֵ
    iArray[14][3]=3;              		//���� 3

    iArray[15]=new Array();
    iArray[15][0]="�ɷѵ�λ";      	  //����
    iArray[15][1]="0px";            	//�п�
    iArray[15][2]=20;            			//�����ֵ
    iArray[15][3]=3;              		//���� 3

    iArray[16]=new Array();
    iArray[16][0]="Ͷ����";      			//����
    iArray[16][1]="60px";            	//�п�
    iArray[16][2]=20;            			//�����ֵ
    iArray[16][3]=0;              		//���� 3

    iArray[17]=new Array();
    iArray[17][0]="��ע";      	   	  //����
    iArray[17][1]="0px";            	//�п�
    iArray[17][2]=500;            		//�����ֵ
    iArray[17][3]=3;              		//���� 3
    
    iArray[18]=new Array();
    iArray[18][0]="�վ�����";      	  //����
    iArray[18][1]="60px";            	//�п�
    iArray[18][2]=20;            			//�����ֵ
    iArray[18][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
           
    iArray[19]=new Array();
    iArray[19][0]="��������";     //����
    iArray[19][1]="60px";            	//�п�
    iArray[19][2]=20;            			//�����ֵ
    iArray[19][3]=3;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[20]=new Array();
    iArray[20][0]="���պ���";     //����
    iArray[20][1]="60px";            	//�п�
    iArray[20][2]=20;            			//�����ֵ
    iArray[20][3]=0;              		//�Ƿ���������,1��ʾ����0��ʾ������
                    
    TempToGrid = new MulLineEnter( "fm" , "TempToGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    TempToGrid.mulLineCount = 1;   
    TempToGrid.displayTitle = 1;
    TempToGrid.locked=0;
    TempToGrid.canSel = 1; //�Ƿ����ӵ�ѡ��,1 ��ʾ ��0 ����(ȱʡֵ)
    TempToGrid.hiddenPlus=1;
    TempToGrid.hiddenSubtraction=0;
    TempToGrid.selBoxEventFuncName = "showInform"; // mulLine�е��radiobox��������, ��д��JS����������������
    TempToGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert(ex);
    }	
}


//����2010-2-26
//��ʼ�������ݽ������ݵ�Grid
function initTTempToGrid()
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
      iArray[1][0]="���պ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="�շ�����";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=0;
            
      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ѽ��";      	   		//����
      iArray[4][1]="80px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";          		//����
      iArray[5][1]="0px";      	      		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�������";      	   		//����
      iArray[6][1]="0px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="���ֱ���";          		//����
      iArray[7][1]="60px";      	      		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���������";          		//����
      iArray[8][1]="80px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�����˱���";          		//����
      iArray[9][1]="80px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="ҵ�����";      	   	        //����
      iArray[10][1]="100px";            			//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//����

      iArray[11]=new Array();
      iArray[11][0]="ҵ������";      	   	        //����
      iArray[11][1]="100px";            			//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//����

      iArray[12]=new Array();
      iArray[12][0]="�ɷѼ��";          		//����
      iArray[12][1]="0px";      	      		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="�ɷ��ڼ�";      	   	        //����
      iArray[13][1]="0px";            			//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=3;              			//����

      iArray[14]=new Array();
      iArray[14][0]="�ɷѵ�λ";      	   	        //����
      iArray[14][1]="0px";            			//�п�
      iArray[14][2]=20;            			//�����ֵ
      iArray[14][3]=3;              			//����

      iArray[15]=new Array();
      iArray[15][0]="Ͷ��������";      	   	   //����
      iArray[15][1]="100px";            			//�п�
      iArray[15][2]=20;            			//�����ֵ
      iArray[15][3]=3;              			//����

      iArray[16]=new Array();
      iArray[16][0]="��ע";      	   	        //����
      iArray[16][1]="100px";            			//�п�
      iArray[16][2]=500;            			//�����ֵ
      iArray[16][3]=3;              			//����
      
      iArray[17]=new Array();
      iArray[17][0]="����֪ͨ����";      	   	        //����
      iArray[17][1]="100px";            			//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//����   
      
      iArray[18]=new Array();
      iArray[18][0]="����";      	   	        //����
      iArray[18][1]="100px";            			//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=3;              			//����          

      TTempToGrid = new MulLineEnter( "fmSave" , "TTempToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TTempToGrid.mulLineCount = 1;   
      TTempToGrid.displayTitle = 1;
      TTempToGrid.locked=0;
      TTempToGrid.hiddenPlus=1;
      TTempToGrid.hiddenSubtraction=1;      
      TTempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}



//��ʼ�������ݽ��ѷ������ݵ�Grid
function initTTempClassToGrid()
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
      iArray[1][0]="���պ���";          		//����
      iArray[1][1]="60px";      	      		//�п�
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
      iArray[4][0]="���ѽ��";          		//����
      iArray[4][1]="60px";      	      		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ѱ���";      	   		//����
      iArray[5][1]="60px";            			//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="��������";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[7]=new Array();
      iArray[7][0]="��������";          		//����
      iArray[7][1]="0px";      	      		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�������";          		//����
      iArray[8][1]="0px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="Ʊ�ݺ���";          		//����
      iArray[9][1]="100px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="֧Ʊ����";          		//����
      iArray[10][1]="80px";      	      		//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��������";      	   		//����
      iArray[11][1]="60px";            			//�п�
      iArray[11][2]=40;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�����˺�";      	   		//����
      iArray[12][1]="100px";            			//�п�
      iArray[12][2]=40;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="����";      	   		//����
      iArray[13][1]="60px";            			//�п�
      iArray[13][2]=10;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�տ�����";      	   		//����
      iArray[14][1]="0px";            			//�п�
      iArray[14][2]=40;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="�տ������˺�";      	   		//����
      iArray[15][1]="150px";            		//�п�
      iArray[15][2]=40;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[16]=new Array();
      iArray[16][0]="�տ����л���";      	   		//����
      iArray[16][1]="0px";            			//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[17]=new Array();
      iArray[17][0]="�ɷѼ��";      	   		//����
      iArray[17][1]="0px";            			//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[18]=new Array();
      iArray[18][0]="�ɷ��ڼ�";      	   		//����
      iArray[18][1]="0px";            			//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[19]=new Array();
      iArray[19][0]="�����˻�";      	   		//����
      iArray[19][1]="0px";            			//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[20]=new Array();
      iArray[20][0]="֤������";      	   		//����
      iArray[20][1]="0px";            			//�п�
      iArray[20][2]=60;            			//�����ֵ
      iArray[20][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[21]=new Array();
      iArray[21][0]="֤������";      	   		//����
      iArray[21][1]="0px";            			//�п�
      iArray[21][2]=60;            			//�����ֵ
      iArray[21][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[22]=new Array();
      iArray[22][0]="ҵ�����";      	   		//����
      iArray[22][1]="60px";            			//�п�
      iArray[22][2]=20;            			//�����ֵ
      iArray[22][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[23]=new Array();
      iArray[23][0]="ҵ������";      	   		//����
      iArray[23][1]="60px";            			//�п�
      iArray[23][2]=20;            			//�����ֵ
      iArray[23][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������     
      
    	iArray[24]=new Array();
    	iArray[24][0]="����������";      			//����
    	iArray[24][1]="0px";            			//�п�
    	iArray[24][2]=20;            					//�����ֵ
    	iArray[24][3]=3;              				//���� 3       
      
     
      TTempClassToGrid = new MulLineEnter( "fmSave" , "TTempClassToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TTempClassToGrid.mulLineCount = 1;   
      TTempClassToGrid.displayTitle = 1;
      TTempClassToGrid.locked=0;
      TTempClassToGrid.hiddenPlus=1;
      TTempClassToGrid.hiddenSubtraction=1;
      TTempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}
//����2010-2-26

</script>
