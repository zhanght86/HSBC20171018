<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2007-2-7
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
 String tContType  	= request.getParameter("ContType");
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.RIcomCode.value='';
		fm.AccumulateDefNO.value='';
		fm.EventType.value="01";
		fm.EventTypeName.value="�µ���������";
		fm.ContType.value="<%=tContType%>";
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��LRGetBsnsDataInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initBusynessGrid();
    initEdorGrid();
    initClaimGrid();
  }
  catch(re)
  {
    myAlert("3��LRGetBsnsDataInit.jsp-->"+"��ʼ���������!");
  }
}

// �µ������б�ĳ�ʼ��
function initBusynessGrid()
{                               
	var iArray = new Array();   
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="80px";  
		iArray[1][2]=100;     
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="�����˿ͻ���";          		 
		iArray[2][1]="80px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="����������";          		 
		iArray[3][1]="80px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;  
		
		iArray[4]=new Array();
		iArray[4][0]="�Ա�";        		   
		iArray[4][1]="30px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0; 
		
		iArray[5]=new Array();
		iArray[5][0]="Ͷ������";        		   
		iArray[5][1]="50px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="����";        		   
		iArray[6][1]="80px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0; 
		
		iArray[7]=new Array();
		iArray[7][0]="���˱�����Ч��";
		iArray[7][1]="100px";            		 
		iArray[7][2]=85;            			   
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="������ֹ��";
		iArray[8][1]="80px";            		 
		iArray[8][2]=85;            			   
		iArray[8][3]=0;              			   
		
		iArray[9]=new Array();
		iArray[9][0]="���ֱ���";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  
		
		iArray[10]=new Array();
		iArray[10][0]="���δ���";         		   
		iArray[10][1]="0px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=3;  
		
		iArray[11]=new Array();
		iArray[11][0]="����";         		   
		iArray[11][1]="80px";            		 
		iArray[11][2]=100;            			   
		iArray[11][3]=0;      
		
		iArray[12]=new Array();
		iArray[12][0]="���ձ���";         		   
		iArray[12][1]="80px";            		 
		iArray[12][2]=100;            			   
		iArray[12][3]=0; 
		
		iArray[13]=new Array();
		iArray[13][0]="�ۼƷ��ձ���";  
		iArray[13][1]="80px";  
		iArray[13][2]=100;     
		iArray[13][3]=0;      
		
		iArray[14]=new Array();
		iArray[14][0]="�ֱ�����";  
		iArray[14][1]="80px";  
		iArray[14][2]=100;     
		iArray[14][3]=0;       
		
		iArray[15]=new Array();
		iArray[15][0]="�ֱ�����";  
		iArray[15][1]="80px";  
		iArray[15][2]=100;     
		iArray[15][3]=0; 
		
		iArray[16]=new Array();
		iArray[16][0]="̯��Ӷ��";  
		iArray[16][1]="80px";  
		iArray[16][2]=100;     
		iArray[16][3]=0; 
		
		iArray[17]=new Array();
		iArray[17][0]="�¼���";  
		iArray[17][1]="80px";  
		iArray[17][2]=100;     
		iArray[17][3]=3; 
		
		iArray[18]=new Array();
		iArray[18][0]="��ɻ�����";  
		iArray[18][1]="80px";  
		iArray[18][2]=100;     
		iArray[18][3]=0;		
		
		iArray[19]=new Array();
		iArray[19][0]="׼����";  
		iArray[19][1]="80px";  
		iArray[19][2]=100;     
		iArray[19][3]=0;
		
		iArray[20]=new Array();
		iArray[20][0]="�ֽ��ֵ";  
		iArray[20][1]="80px";  
		iArray[20][2]=100;     
		iArray[20][3]=0;
		
		iArray[21]=new Array();
		iArray[21][0]="�ɷ�����";  
		iArray[21][1]="80px";  
		iArray[21][2]=100;     
		iArray[21][3]=0;						
		
		iArray[22]=new Array();
		iArray[22][0]="ҵ������";
		iArray[22][1]="100px"; 
		iArray[22][2]=100; 
		iArray[22][3]=0;
				
		BusynessGrid = new MulLineEnter("fm","BusynessGrid"); 
		//��Щ���Ա�����loadMulLineǰ
		BusynessGrid.mulLineCount = 0;   
		BusynessGrid.displayTitle = 1;
		BusynessGrid.locked = 0;
		BusynessGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//BusynessGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
		BusynessGrid.hiddenPlus=1;
		BusynessGrid.hiddenSubtraction=1;
		BusynessGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
	    myAlert(ex);
	}
}

// ��ȫ�б�ĳ�ʼ��
function initEdorGrid()
{	
	var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="80px";  
		iArray[1][2]=100;     
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="�����˿ͻ���";          		 
		iArray[2][1]="80px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="����������";          		 
		iArray[3][1]="80px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;  
		
		iArray[4]=new Array();
		iArray[4][0]="�Ա�";        		   
		iArray[4][1]="30px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;		
		
		iArray[5]=new Array();
		iArray[5][0]="Ͷ������";        		   
		iArray[5][1]="50px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="����";        		   
		iArray[6][1]="80px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0; 
				
		iArray[7]=new Array();
		iArray[7][0]="���˱�����Ч��";
		iArray[7][1]="100px";            		 
		iArray[7][2]=85;            			   
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="������ֹ��";
		iArray[8][1]="80px";            		 
		iArray[8][2]=85;            			   
		iArray[8][3]=0;              			   
		
		iArray[9]=new Array();
		iArray[9][0]="���ֱ���";         		   
		iArray[9][1]="60px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=0;  
		
		iArray[10]=new Array();
		iArray[10][0]="���δ���";         		   
		iArray[10][1]="0px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=3;  
		
		iArray[11]=new Array();
		iArray[11][0]="����";         		   
		iArray[11][1]="80px";            		 
		iArray[11][2]=100;            			   
		iArray[11][3]=0;      
		
		iArray[12]=new Array();
		iArray[12][0]="���ձ���";         		   
		iArray[12][1]="80px";            		 
		iArray[12][2]=100;            			   
		iArray[12][3]=0; 
		
		iArray[13]=new Array();
		iArray[13][0]="�ֱ�����仯��";         		   
		iArray[13][1]="100px";            		 
		iArray[13][2]=100;            			   
		iArray[13][3]=0; 
		
		iArray[14]=new Array();
		iArray[14][0]="�ۼƷ��ձ���";  
		iArray[14][1]="80px";  
		iArray[14][2]=100;     
		iArray[14][3]=0;      
		
		iArray[15]=new Array();
		iArray[15][0]="�ֱ�����";  
		iArray[15][1]="80px";  
		iArray[15][2]=100;     
		iArray[15][3]=0;       
		
		iArray[16]=new Array();
		iArray[16][0]="�ֱ�����";  
		iArray[16][1]="80px";  
		iArray[16][2]=100;     
		iArray[16][3]=0; 
		
		iArray[17]=new Array();
		iArray[17][0]="̯��Ӷ��";  
		iArray[17][1]="80px";  
		iArray[17][2]=100;     
		iArray[17][3]=0;      
		
		iArray[18]=new Array();
		iArray[18][0]="�¼���";  
		iArray[18][1]="80px";  
		iArray[18][2]=100;     
		iArray[18][3]=3; 
		
		iArray[19]=new Array();
		iArray[19][0]="��ɻ�����";  
		iArray[19][1]="80px";  
		iArray[19][2]=100;     
		iArray[19][3]=0;		
		
		iArray[20]=new Array();
		iArray[20][0]="׼����";  
		iArray[20][1]="80px";  
		iArray[20][2]=100;     
		iArray[20][3]=0;
		
		iArray[21]=new Array();
		iArray[21][0]="�ֽ��ֵ";  
		iArray[21][1]="80px";  
		iArray[21][2]=100;     
		iArray[21][3]=0;
		
		iArray[22]=new Array();
		iArray[22][0]="�ɷ�����";  
		iArray[22][1]="80px";  
		iArray[22][2]=100;     
		iArray[22][3]=0;
				
		iArray[23]=new Array();
		iArray[23][0]="��ȫ�������";  
		iArray[23][1]="80px";  
		iArray[23][2]=100;     
		iArray[23][3]=0; 
		
		EdorGrid = new MulLineEnter( "fm" , "EdorGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		EdorGrid.mulLineCount = 0;   
		EdorGrid.displayTitle = 1;
		EdorGrid.locked = 0;
		EdorGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//EdorGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
		EdorGrid.hiddenPlus=1;
		EdorGrid.hiddenSubtraction=1;
		EdorGrid.loadMulLine(iArray); 
  }
  catch(ex)
  {
      myAlert(ex);
  }
}

// �����б�ĳ�ʼ��
function initClaimGrid()
{                               
   var iArray = new Array();   
   try
   {
			iArray[0]=new Array();
			iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1]="30px";            		//�п�
			iArray[0][2]=40;            			  //�����ֵ
			iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
			
			iArray[1]=new Array();
			iArray[1][0]="������";
			iArray[1][1]="80px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="�����˿ͻ���";          		 
			iArray[2][1]="80px";            		 
			iArray[2][2]=85;            			   
			iArray[2][3]=0;  
			
			iArray[3]=new Array();
			iArray[3][0]="����������";          		 
			iArray[3][1]="80px";            		 
			iArray[3][2]=85;            			   
			iArray[3][3]=0;  
			
			iArray[4]=new Array();
			iArray[4][0]="�Ա�";        		   
			iArray[4][1]="30px";            		 
			iArray[4][2]=85;            			   
			iArray[4][3]=0; 
			
			iArray[5]=new Array();
			iArray[5][0]="Ͷ������";        		   
			iArray[5][1]="50px";            		 
			iArray[5][2]=85;            			   
			iArray[5][3]=0; 
			
			iArray[6]=new Array();
			iArray[6][0]="����";        		   
			iArray[6][1]="80px";            		 
			iArray[6][2]=85;            			   
			iArray[6][3]=0; 			
			
			iArray[7]=new Array();
			iArray[7][0]="���˱�����Ч��";
			iArray[7][1]="100px";            		 
			iArray[7][2]=85;            			   
			iArray[7][3]=0;
			
			iArray[8]=new Array();
			iArray[8][0]="������ֹ��";
			iArray[8][1]="80px";            		 
			iArray[8][2]=85;            			   
			iArray[8][3]=0;              			   
			
			iArray[9]=new Array();
			iArray[9][0]="���ֱ���";         		   
			iArray[9][1]="60px";            		 
			iArray[9][2]=100;            			   
			iArray[9][3]=0;  
			
			iArray[10]=new Array();
			iArray[10][0]="���δ���";         		   
			iArray[10][1]="0px";            		 
			iArray[10][2]=100;            			   
			iArray[10][3]=3;  
			
			iArray[11]=new Array();
			iArray[11][0]="����";         		   
			iArray[11][1]="80px";            		 
			iArray[11][2]=100;            			   
			iArray[11][3]=0;      
			
			iArray[12]=new Array();
			iArray[12][0]="���ձ���";         		   
			iArray[12][1]="80px";            		 
			iArray[12][2]=100;            			   
			iArray[12][3]=0; 
			
			iArray[13]=new Array();
			iArray[13][0]="����̯��";
			iArray[13][1]="80px"; 
			iArray[13][2]=100; 
			iArray[13][3]=0;
				 
			iArray[14]=new Array();
			iArray[14][0]="�¼���";
			iArray[14][1]="100px"; 
			iArray[14][2]=100; 
			iArray[14][3]=3;
			
			iArray[15]=new Array();
			iArray[15][0]="��ɻ�����";  
			iArray[15][1]="80px";  
			iArray[15][2]=100;     
			iArray[15][3]=0;		
			
			iArray[16]=new Array();
			iArray[16][0]="׼����";  
			iArray[16][1]="80px";  
			iArray[16][2]=100;     
			iArray[16][3]=0;
			
			iArray[17]=new Array();
			iArray[17][0]="�ֽ��ֵ";  
			iArray[17][1]="80px";  
			iArray[17][2]=100;     
			iArray[17][3]=0;
			
			iArray[18]=new Array();
			iArray[18][0]="�ɷ�����";  
			iArray[18][1]="80px";  
			iArray[18][2]=100;     
			iArray[18][3]=0;			
			
			iArray[19]=new Array();
			iArray[19][0]="�⸶����";
			iArray[19][1]="100px"; 
			iArray[19][2]=100; 
			iArray[19][3]=0;
			
	 		ClaimGrid = new MulLineEnter("fm","ClaimGrid"); 
	 		//��Щ���Ա�����loadMulLineǰ
	 		ClaimGrid.mulLineCount = 0;   
	 		ClaimGrid.displayTitle = 1;
	 		ClaimGrid.locked = 0;
	 		ClaimGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	 		//ClaimGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������   
	 		ClaimGrid.hiddenPlus=1;
	 		ClaimGrid.hiddenSubtraction=1;
	 		ClaimGrid.loadMulLine(iArray); 
   }
   catch(ex)
   {
       myAlert(ex);
   }
}

</script>