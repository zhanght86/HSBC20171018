<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2007-10-17
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
 String accumulateNo = request.getParameter("AccumulateNo"); 
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  	fm.AccumulateNo.value = "<%=accumulateNo%>";
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
    initAccumulateGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}

function initAccumulateGrid()
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
	      iArray[1][0]="�ۼƷ��ձ��";    	
	      iArray[1][1]="120px";         
	      iArray[1][2]=100;            	
	      iArray[1][3]=0;              	
	
	      iArray[2]=new Array();
	      iArray[2][0]="�ۼƷ�������";      
	      iArray[2][1]="100px";         
	      iArray[2][2]=100;            	
	      iArray[2][3]=0;    
	      
	      iArray[3]=new Array();
	      iArray[3][0]="���α��"; 
	      iArray[3][1]="100px"; 
	      iArray[3][2]=100;     
	      iArray[3][3]=0;  
	      
	      iArray[4]=new Array();
	      iArray[4][0]="��������"; 
	      iArray[4][1]="100px"; 
	      iArray[4][2]=100;     
	      iArray[4][3]=0; 
	      
	      iArray[5]=new Array();
	      iArray[5][0]="GEB��־���"; 
	      iArray[5][1]="100px"; 
	      iArray[5][2]=100;     
	      iArray[5][3]=3; 
				
				iArray[6]=new Array();
	      iArray[6][0]="GEB��־"; 
	      iArray[6][1]="100px"; 
	      iArray[6][2]=100;     
	      iArray[6][3]=3; 
				
	      AccumulateGrid = new MulLineEnter( "fm" , "AccumulateGrid" ); 
	      AccumulateGrid.mulLineCount = 0;   
	      AccumulateGrid.displayTitle = 1;
	      AccumulateGrid.canSel=0;
	      AccumulateGrid.locked = 1;	
				AccumulateGrid.hiddenPlus = 1;
				AccumulateGrid.hiddenSubtraction = 1;
	      AccumulateGrid.loadMulLine(iArray);  
	      AccumulateGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>