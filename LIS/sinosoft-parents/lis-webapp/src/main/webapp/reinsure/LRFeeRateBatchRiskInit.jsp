<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2008-06-04
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
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 		String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);  
    String tAccumulateDefNO=request.getParameter("AccumulateDefNO"); 
    String tRIPreceptNo=request.getParameter("RIPreceptNo");
    String tIncomeCompanyNo=request.getParameter("IncomeCompanyNo");
    String tAreaId=request.getParameter("AreaId");
    String tUpperLimit=request.getParameter("UpperLimit");
    String tLowerLimit=request.getParameter("LowerLimit");
 %>
<script type="text/javascript">
function initInpBox()
{
  try
  {	
  	fm.AccumulateDefNO.value 	=	<%=tAccumulateDefNO%>;
  	fm.RIPreceptNo.value 			=	<%=tRIPreceptNo%>;
  	fm.IncomeCompanyNo.value 	=	<%=tIncomeCompanyNo%>;  
  	fm.AreaId.value						= <%=tAreaId%>;
  	fm.UpperLimit.value				= <%=tUpperLimit%>;
  	fm.LowerLimit.value				= <%=tLowerLimit%>;
  } 
  catch(ex) 
  {
    myAlert("���г�ʼ��ʱ���ִ���");
  }
}
;

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("��LRContInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initFeeRateBatchGrid();
    queryFeeRateBatch();
    
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"��ʼ���������!");
  }
}

//�ٱ���ͬǩ�����б��ʼ��
function initFeeRateBatchGrid() 
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;
    
    iArray[1]=new Array();    
    iArray[1][0]="�ۼƷ�������";
    iArray[1][1]="60px";
    iArray[1][2]=100;         
    iArray[1][3]=0;  
    
    iArray[2]=new Array();    
    iArray[2][0]="�ۼ���ϸ��־����";
    iArray[2][1]="0px";      
    iArray[2][2]=100;         
    iArray[2][3]=3;
    
    iArray[3]=new Array();    
    iArray[3][0]="�ۼ���ϸ��־";
    iArray[3][1]="60px";      
    iArray[3][2]=100;         
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="�ٱ���������";
    iArray[4][1]="60px";
    iArray[4][2]=100;
    iArray[4][3]=0;
    
    iArray[5]=new Array();
    iArray[5][0]="���ֱ���/���δ���";
    iArray[5][1]="70px";
    iArray[5][2]=100;
    iArray[5][3]=0;
    
    iArray[6]=new Array();
    iArray[6][0]="������";
    iArray[6][1]="40px";
    iArray[6][2]=100;
    iArray[6][3]=0;
    
    iArray[7]=new Array();
    iArray[7][0]="�ֱ���˾���";
    iArray[7][1]="0px";
    iArray[7][2]=100;
    iArray[7][3]=3;
    
    iArray[8]=new Array();
    iArray[8][0]="�ֱ���˾";
    iArray[8][1]="60px";
    iArray[8][2]=100;
    iArray[8][3]=0;
    
    iArray[9]=new Array();
    iArray[9][0]="��������";
    iArray[9][1]="60px";
    iArray[9][2]=100;
    iArray[9][3]=0;
    
    iArray[10]=new Array();
    iArray[10][0]="��������";
    iArray[10][1]="60px";
    iArray[10][2]=100;
    iArray[10][3]=0;
		
    iArray[11]=new Array();
		iArray[11][0]="�ֱ����ʱ���";
		iArray[11][1]="60px";  
		iArray[11][2]=100;  
		iArray[11][3]=2;
		iArray[11][4]="FeeRate"; 	
		iArray[11][5]="11|12"; 
		iArray[11][6]="0|1";								          
		iArray[11][9]="�ֱ����ʱ���|NOTNULL"; 
	  iArray[11][19]=1;
		
		iArray[12]=new Array();
		iArray[12][0]="�ֱ����ʱ�����";
		iArray[12][1]="120px";
		iArray[12][2]=100;
		iArray[12][3]=0;
    
    iArray[13]=new Array();
		iArray[13][0]="�ֱ�Ӷ���ʱ���";
		iArray[13][1]="60px";
		iArray[13][2]=100;
		iArray[13][3]=2;
		iArray[13][4]="FeeRate";
		iArray[13][5]="13|14";
		iArray[13][6]="0|1";
	  iArray[13][19]=1;
		
		iArray[14]=new Array();
		iArray[14][0]="�ֱ�Ӷ���ʱ�����";
		iArray[14][1]="120";
		iArray[14][2]=100;     
		iArray[14][3]=0;   
		
    FeeRateBatchGrid = new MulLineEnter( "fm" , "FeeRateBatchGrid" );
    //FeeRateBatchGrid.mulLineCount = 0;   
	  FeeRateBatchGrid.displayTitle = 1;
	  FeeRateBatchGrid.canSel =0; 
		FeeRateBatchGrid.selBoxEventFuncName = "ShowBatch"; 
		FeeRateBatchGrid.hiddenPlus=1; 
		FeeRateBatchGrid.hiddenSubtraction=1;
	  FeeRateBatchGrid.loadMulLine(iArray); 
	  
  }
  
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script> 