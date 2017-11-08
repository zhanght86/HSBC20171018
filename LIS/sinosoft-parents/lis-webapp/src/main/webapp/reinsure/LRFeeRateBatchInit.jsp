<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2008-06-04
%>
<!--用户校验类-->

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
    String tFeeTableNo=request.getParameter("FeeTableNo"); 
 %>
<script type="text/javascript">
function initInpBox()
{
  try
  {	
	fm.FeeTableNo.value 	=	<%=tFeeTableNo%>;
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateBatchInitSql"); //指定使用的properties文件名
		mySql100.setSqlId("LRFeeRateBatchInitSql100");//指定使用的Sql的id
		mySql100.addSubPara(fm.FeeTableNo.value);//指定传入的参数
	var strSQL=mySql100.getString();
	
	//var strSQL="select a.feetablename from RIFeeRateTableDef a where FeeTableNo='"+fm.FeeTableNo.value+"'";
	var arrResult=easyExecSql(strSQL);
	
	if (arrResult!=null||arrResult!="")
	{
		fm.FeeTableName.value =	arrResult[0][0];
	}
	
	fm.State.value =	"02";
	fm.stateName.value =	"未生效";
  }         
  catch(ex) 
  {
    myAlert("进行初始化时出现错误！");
  }
}
;

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFeeRateBatchGrid();
    queryFeeRateBatch();
    
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"初始化界面错误!");
  }
}

//再保合同签订人列表初始化
function initFeeRateBatchGrid() 
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;
    
    iArray[1]=new Array();    
    iArray[1][0]="费率表编号";
    iArray[1][1]="60px";
    iArray[1][2]=100;         
    iArray[1][3]=0;  
    
    iArray[2]=new Array();    
    iArray[2][0]="费率表名称";
    iArray[2][1]="60px";      
    iArray[2][2]=100;         
    iArray[2][3]=0;
    
    iArray[3]=new Array();
    iArray[3][0]="费率表批次号";
    iArray[3][1]="80px";
    iArray[3][2]=100;
    iArray[3][3]=0;
    
    iArray[4]=new Array();
    iArray[4][0]="费率表生效日期";
    iArray[4][1]="80px";
    iArray[4][2]=100;
    iArray[4][3]=0;
		
    iArray[5]=new Array();
    iArray[5][0]="费率表失效日期";
    iArray[5][1]="80px";
    iArray[5][2]=200;
    iArray[5][3]=0;
		
		iArray[6]=new Array();
    iArray[6][0]="存储表名称";
    iArray[6][1]="80px";
    iArray[6][2]=200;
    iArray[6][3]=3;
    
    iArray[7]=new Array();
    iArray[7][0]="费率表导入标记";
    iArray[7][1]="60px";
    iArray[7][2]=200;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="费率表状态";
    iArray[8][1]="0px";
    iArray[8][2]=200;
    iArray[8][3]=0;
		
    FeeRateBatchGrid = new MulLineEnter( "fm" , "FeeRateBatchGrid" );
    FeeRateBatchGrid.mulLineCount = 0;   
	  FeeRateBatchGrid.displayTitle = 1;
	  FeeRateBatchGrid.canSel =1; 
		FeeRateBatchGrid.selBoxEventFuncName = "ShowBatch"; 
	  FeeRateBatchGrid.loadMulLine(iArray); 
  }
  
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}

</script> 