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
  GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");
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
    document.all('TempFeeNo').value='';
    document.all('bComCode').value=  "<%=tGI.ComCode%>";
		if(document.all('bComCode').value.trim().length>4)
		{
			document.all('bComCode').value = document.all('bComCode').value.substring(0,4);
	  }
              
  }
  catch(ex)
  {
    alert("��TempFeeInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();  
    initTempGrid();  
    initTempClassGrid(); 
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
      iArray[1][0]="���վݺ�";      	   		//����
      iArray[1][1]="120px";            			//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";          		//����
      iArray[2][1]="80px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[2][4]="RiskCodeFin";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[2][9]="���ֱ���|code:RiskCode&NOTNULL";
      iArray[2][18]=300;
      //iArray[2][19]=1;


      iArray[3]=new Array();
      iArray[3][0]="���ѽ��";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���ѽ��|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="0px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��������|DATE|NULL";
      //iArray[4][14]="<%=CurrentDate%>";
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ������ӡˢ��/������";      	   		//����
      iArray[5][1]="140px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1; 

      iArray[6]=new Array();
      iArray[6][0]="�ɷѷ�ʽ";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=2;                   //�Ƿ���������,1��ʾ����0��ʾ������      
      iArray[6][10]="payintv";
      //iArray[6][11]="0|^0|����^1|�½�^12|���";     
      iArray[6][11]="0|^-1|�����ڽ�^0|����^1|�½�^3|����^6|�����^12|���";  
      
      
      iArray[7]=new Array();
      iArray[7][0]="�ɷ�����";      	   		//����
      iArray[7][1]="80px";            			//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������           
      iArray[7][4]="standyear";
      iArray[7][15]="riskcode";
      iArray[7][17]="2";


      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempGrid.mulLineCount = 1;   
      TempGrid.displayTitle = 1;
      //TempGrid.locked=1;      
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
      iArray[1][0]="���վݺ�";      	   		//����
      iArray[1][1]="120px";            			//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ѷ�ʽ";          		//����
      iArray[2][1]="40px";      	      		//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[2][4]="chargepaymode";              	        //�Ƿ����ô���:null||""Ϊ������
//      iArray[2][5]="1|2";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
//      iArray[2][6]="0|1";              	        //��������з������ô����еڼ�λֵ
      //iArray[2][9]="���ѷ�ʽ|code:paymode&NOTNULL";


      iArray[3]=new Array();
      iArray[3][0]="���ѽ��";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���ѽ��|NUM&NOTNULL";
      
      iArray[4]=new Array();
      iArray[4][0]="��������";      	   		//����
      iArray[4][1]="0px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��������|DATE|NULL";
      //iArray[4][14]="<%=CurrentDate%>";
      
      
      iArray[5]=new Array();
      iArray[5][0]="Ʊ�ݺ���";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
//      iArray[5][9]="Ʊ�ݺ���|NULL|LEN>10";              //���Ի�����

      iArray[6]=new Array();
      iArray[6][0]="��������";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="50px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][4]="bank";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[7][9]="��������|code:bank";
      //iArray[7][18]=150;
      iArray[7][15]="1";
      iArray[7][16]= "#1#  and comcode like #"+document.all('bComCode').value+"%#";
      
      iArray[8]=new Array();
      iArray[8][0]="�����˺�";      	   		//����
      iArray[8][1]="100px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����";      	   		//����
      iArray[9][1]="60px";            			//�п�
      iArray[9][2]=10;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="Ͷ������";      	   		//����
      iArray[10][1]="0px";            			//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="������������";      	   		//����
      iArray[11][1]="0px";            			//�п�
      iArray[11][2]=10;            			//�����ֵ
      iArray[11][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="֤������";      	   		//����
      iArray[12][1]="40px";            			//�п�
      iArray[12][2]=10;            			//�����ֵ
      iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[12][4]="IDType";
      iArray[12][9]="֤������|code:IDType";
      iArray[12][18]=300;
      
      iArray[13]=new Array();
      iArray[13][0]="֤������";      	   		//����
      iArray[13][1]="110px";            			//�п�
      iArray[13][2]=20;            			//�����ֵ
      iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������      
           
      TempClassToGrid = new MulLineEnter( "fm" , "TempClassToGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      TempClassToGrid.mulLineCount = 1;   
      TempClassToGrid.displayTitle = 1;     
      TempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

  
</script>
