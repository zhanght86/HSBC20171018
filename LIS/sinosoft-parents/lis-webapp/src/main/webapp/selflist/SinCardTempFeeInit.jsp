 <%
//�������ƣ�SinCardTempFeeInit.jsp
//�����ܣ�
//�������ڣ�2008-03-07
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
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
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

    document.all('ManageCom').value = '<%=ManageCom%>';
    document.all('PayDate').value = '<%=CurrentDate%>';
    document.all('AgentGroup').value = '';
    document.all('AgentCode').value = '';     
    document.all('AgentName').value = '';
    document.all('PolicyCom').value = '';
    document.all('TempFeeCount').value = 0;
    document.all('SumTempFee').value = 0.0 ; 

  }
  catch(ex)
  {
    alert("��SinCardTempFeeInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}


function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��SinCardTempFeeInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initTempGrid(); 
    initTempToGrid(); 
    initTempClassGrid(); 
    initTempClassToGrid(); 
  }
  catch(re)
  {
    alert("SinCardTempFeeInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

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
      iArray[3][0]="���ѽ��";      	   		//����
      iArray[3][1]="90px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[4]=new Array();
      iArray[4][0]="Ʊ�ݺ���";      	   		//����
      iArray[4][1]="140px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[4][9]="Ʊ�ݺ���|NULL|LEN>10";              //���Ի�����

      iArray[5]=new Array();
      iArray[5][0]="֧Ʊ����";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��������";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������


  
      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=40;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[8]=new Array();
      iArray[8][0]="�����˺�";      	   		//����
      iArray[8][1]="140px";            		//�п�
      iArray[8][2]=40;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����";      	   		//����
      iArray[9][1]="100px";            			//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
      iArray[10]=new Array();
      iArray[10][0]="�տ�����";      	   		//����
      iArray[10][1]="0px";            			//�п�
      iArray[10][2]=40;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[11]=new Array();
      iArray[11][0]="�տ������˺�";      	   		//����
      iArray[11][1]="140px";            		//�п�
      iArray[11][2]=40;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="�տ����л���";      	   		//����
      iArray[12][1]="0px";            			//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="���Ѽ��";      	   		//����
      iArray[13][1]="0px";            			//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[14]=new Array();
      iArray[14][0]="��������";      	   		//����
      iArray[14][1]="0px";            			//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������         

      iArray[15]=new Array();
      iArray[15][0]="���ѵ�λ";      	   		//����
      iArray[15][1]="0px";            			//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[16]=new Array();                                                  
      iArray[16][0]="֤������";      	   		//����                             
      iArray[16][1]="60px";            			//�п�                             
      iArray[16][2]=60;            			//�����ֵ                             
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      
      iArray[17]=new Array();
      iArray[17][0]="֤������";      	   		//����
      iArray[17][1]="120px";            			//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassGrid.mulLineCount = 5;   
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
function initTempGrid()
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
      iArray[1][0]="��Ʒ����";          		//����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][4]="portfolio";              	        //�Ƿ����ô���:null||""Ϊ������,ʹ�ò�Ʒ��ϵĲ�Ʒ����
      iArray[1][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[1][6]="0|1";              	        //��������з������ô����еڼ�λֵ


      iArray[2]=new Array();
      iArray[2][0]="��Ʒ����";         			//����
      iArray[2][1]="140px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ѽ��";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���ѽ��|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="0px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�ɷѷ�ʽ";      	   		//����
      iArray[5][1]="60px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="PayIntv";
      iArray[5][11]="0|^12|���|^0|����|^6|�����^3|����^1|�½�^-1|�����ڽ�";
      
      iArray[6]=new Array();
      iArray[6][0]="�ɷ�����";      	   		//����
      iArray[6][1]="60px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 5;   
      TempGrid.displayTitle = 1;
      TempGrid.locked=0;      
      TempGrid.loadMulLine(iArray);  
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
      iArray[1][1]="0px";      	      		//�п�
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
      iArray[4][1]="80px";      	      		//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";          		//����
      iArray[5][1]="80px";      	      		//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�������";          		//����
      iArray[6][1]="0px";      	      		//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="Ʊ�ݺ���";          		//����
      iArray[7][1]="100px";      	      		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="֧Ʊ����";          		//����
      iArray[8][1]="60px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��������";      	   		//����
      iArray[9][1]="60px";            			//�п�
      iArray[9][2]=40;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="�����˺�";      	   		//����
      iArray[10][1]="140px";            			//�п�
      iArray[10][2]=40;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="����";      	   		//����
      iArray[11][1]="60px";            			//�п�
      iArray[11][2]=10;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�տ�����";      	   		//����
      iArray[12][1]="0px";            			//�п�
      iArray[12][2]=40;            			//�����ֵ
      iArray[12][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�տ������˺�";      	   		//����
      iArray[13][1]="140px";            		//�п�
      iArray[13][2]=40;            			//�����ֵ
      iArray[13][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[14]=new Array();
      iArray[14][0]="�տ����л���";      	   		//����
      iArray[14][1]="0px";            			//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="�ɷѼ��";      	   		//����
      iArray[15][1]="0px";            			//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[16]=new Array();
      iArray[16][0]="�ɷ��ڼ�";      	   		//����
      iArray[16][1]="0px";            			//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[17]=new Array();
      iArray[17][0]="�����˻�";      	   		//����
      iArray[17][1]="0px";            			//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[18]=new Array();
      iArray[18][0]="֤������";      	   		//����
      iArray[18][1]="60px";            			//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[19]=new Array();
      iArray[19][0]="֤������";      	   		//����
      iArray[19][1]="140px";            			//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      TempClassToGrid = new MulLineEnter( "fmSave" , "TempClassToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassToGrid.mulLineCount = 5;   
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
      iArray[1][1]="0px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="���շ�����";          		//����
      iArray[2][1]="0px";      	      		//�п�
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
      iArray[6][1]="100px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��Ʒ����";          		//����
      iArray[7][1]="80px";      	      		//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="���������";          		//����
      iArray[8][1]="80px";      	      		//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="�����˱���";          		//����
      iArray[9][1]="80px";      	      		//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="��������";      	   	        //����
      iArray[10][1]="160px";            			//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//����

      iArray[11]=new Array();
      iArray[11][0]="������������";      	   	        //����
      iArray[11][1]="100px";            			//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=0;              			//����

      iArray[12]=new Array();
      iArray[12][0]="���Ѽ��";          		//����
      iArray[12][1]="80px";      	      		//�п�
      iArray[12][2]=20;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="�����ڼ�";      	   	        //����
      iArray[13][1]="80px";            			//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=0;              			//����

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
 
      TempToGrid = new MulLineEnter( "fmSave" , "TempToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempToGrid.mulLineCount = 5;   
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

function initDivPayMode()  //���һ�ʺ����Ϣ����ʼ��  
 {  
   var ii; 
   for(ii=1;ii<=6;ii++)
   {
   	if(ii==5)
   	break;  
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
    document.all("divPayMode"+ii).style.display="none";
   }
 }



  
</script>
