<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-08-20
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
 String pageFlag = request.getParameter("PageFlag"); 
 String reContCode = request.getParameter("ReContCode"); 
 System.out.println("pageFlag: "+pageFlag);
 System.out.println("ql_ReContCode: "+reContCode);
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  	fm.ReContCode1.value = "<%=reContCode%>";
  	fm.PageFlag.value = "<%=pageFlag%>";
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
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initReContGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}

function initReContGrid()
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
	  iArray[1][0]="��ͬ���";    	
	  iArray[1][1]="120px";         
	  iArray[1][2]=100;            	
	  iArray[1][3]=0;              	
	
	  iArray[2]=new Array();
	  iArray[2][0]="��������";      
	  iArray[2][1]="100px";         
	  iArray[2][2]=100;            	
	  iArray[2][3]=3;    
	  
	  iArray[3]=new Array();
	  iArray[3][0]="��ͬ����";      
	  iArray[3][1]="100px";         
	  iArray[3][2]=100;            	
	  iArray[3][3]=0;              	
	
	  iArray[4]=new Array();
	  iArray[4][0]="��ͬ��������";  
	  iArray[4][1]="100px";          
	  iArray[4][2]=60;            	
	  iArray[4][3]=3;              	
	  
	  iArray[5]=new Array();
	  iArray[5][0]="��ͬ������������";  
	  iArray[5][1]="60px";          
	  iArray[5][2]=60;            	
	  iArray[5][3]=3;       
	  
	  iArray[6]=new Array();
	  iArray[6][0]="�ֱ�����";      
	  iArray[6][1]="100px";          
	  iArray[6][2]=60;            	
	  iArray[6][3]=3;              	
	  
	  iArray[7]=new Array();
	  iArray[7][0]="�ֱ���������";      
	  iArray[7][1]="100px";          
	  iArray[7][2]=60;            	
	  iArray[7][3]=0;   
	  
	  iArray[8]=new Array();
	  iArray[8][0]="��ͬ��Ч����"; 	
	  iArray[8][1]="100px";         
	  iArray[8][2]=1000;            
	  iArray[8][3]=0;              	
	  
	  iArray[9]=new Array();
	  iArray[9][0]="��ͬ��ֹ����";  
	  iArray[9][1]="100px";         
	  iArray[9][2]=1000;            
	  iArray[9][3]=0;              	
		
	  iArray[10]=new Array();
	  iArray[10][0]="��ͬ״̬����"; 
	  iArray[10][1]="100px";         
	  iArray[10][2]=1000;            
	  iArray[10][3]=3;              	
	  
	  iArray[11]=new Array();
	  iArray[11][0]="��ͬ״̬";
	  iArray[11][1]="100px";         
	  iArray[11][2]=1000;            
	  iArray[11][3]=0;    

	  iArray[12]=new Array();
	  iArray[12][0]="�˵����ڱ���";
	  iArray[12][1]="20px";         
	  iArray[12][2]=30;            
	  iArray[12][3]=3; 

	  iArray[13]=new Array();
	  iArray[13][0]="�˵�����";
	  iArray[13][1]="80px";         
	  iArray[13][2]=50;            
	  iArray[13][3]=0; 
	  	  		
	  ReContGrid = new MulLineEnter( "fm" , "ReContGrid" ); 
	  ReContGrid.mulLineCount = 0;   
	  ReContGrid.displayTitle = 1;
	  ReContGrid.canSel=1;
	  ReContGrid.locked = 1;	
	  ReContGrid.hiddenPlus = 1;
	  ReContGrid.hiddenSubtraction = 1;
	  ReContGrid.loadMulLine(iArray);  
	  ReContGrid.detailInfo="������ʾ��ϸ��Ϣ";
  
  }
  catch(ex)
  {
    myAlert(ex);
  }
}
</script>