<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRAccRDInit.jsp
//function :Manage 
//Creator :
//date :2007-3-5
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
    String tAccumulateDefNO=request.getParameter("accumulatedefno");
    System.out.println(tAccumulateDefNO);
 %>
<script type="text/javascript">
function initInpBox(){
  try{
  	fm.AccumulateDefNO.value="<%=tAccumulateDefNO%>";
  	fm.UNIONACCNO.value="";
  	//fm.AccumulateMode.value="02";
  	//fm.AccumulateModeName.value="";
  	//fm.RiskAmntFlag.value="";
  	//fm.RiskAmntFlagName.value="";
  	//fm.GetDutyFlag.value="02";
  	//fm.GetDutyFlagName.value="";
  
  
    
      
  }
  catch(ex){
    myAlert("初始化界面错误!");
  }
}
;

// 下拉框的初始化
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm(){
  try{
    initInpBox();
    
    initSelBox();
    initRelateGrid();
    initRGrid();
       
    qeuryGrid();
  }
  catch(re){
    myAlert(re+"3CertifyDescInit.jsp-->"+"初始化界面错误!");
  }
}

function initRelateGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

	iArray[1]=new Array();
    iArray[1][0]="累计风险编码";    //列名
    iArray[1][1]="50px";        //列宽
    iArray[1][2]=200;           //列最大值
    iArray[1][3]=1;             //是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="联合累计编码";    //列名
    iArray[2][1]="50px";        //列宽
    iArray[2][2]=200;           //列最大值
    iArray[2][3]=1;             //是否允许输入,1表示允许，0表示不允许
    
    
    
    
    
    RelateGrid = new MulLineEnter( "fm" , "RelateGrid" );
    RelateGrid.mulLineCount = 1;
    RelateGrid.displayTitle = 1;
    RelateGrid.locked=1;
    RelateGrid.canSel=1;
    RelateGrid.selBoxEventFuncName ="showRela"; //响应的函数名，不加扩号   
    RelateGrid.loadMulLine(iArray);
    RelateGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}

function initRGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="序号";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

	iArray[1]=new Array();
    iArray[1][0]="险种编码"; 
    iArray[1][1]="30px"; 
    iArray[1][2]=100; 
    iArray[1][3]=2; 
    iArray[1][4]="lrrecode"; 
		iArray[1][5]="1|2"; 	 			//将引用代码分别放在第1、2
		iArray[1][6]="0|1";	
		iArray[1][15]="accumulatedefno";
		iArray[1][16]=fm.all("AccumulateDefNO").value;
		iArray[1][19]=1; 

    iArray[2]=new Array();
    iArray[2][0]="险种名称";    //列名
    iArray[2][1]="50px";        //列宽
    iArray[2][2]=200;           //列最大值
    iArray[2][3]=1;             //是否允许输入,1表示允许，0表示不允许
    
    
    
    
    
    RGrid = new MulLineEnter( "fm" , "RGrid" );
    RGrid.mulLineCount = 1;
    RGrid.displayTitle = 1;
    //RelateGrid.canSel=1;
    RGrid.loadMulLine(iArray);
    RGrid.detailInfo="单击显示详细信息";
    
  
    
  }
  catch(ex)
  {
    myAlert("初始化时出错:"+ex);
  }
}



</script>


