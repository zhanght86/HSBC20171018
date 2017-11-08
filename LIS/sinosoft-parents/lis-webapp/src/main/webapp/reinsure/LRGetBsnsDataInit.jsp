<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2007-2-7
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
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
		fm.StartDate.value='';
		fm.EndDate.value='';
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2在LRGetBsnsDataInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initBusynessGrid();
  }
  catch(re)
  {
    myAlert("3在LRGetBsnsDataInit.jsp-->"+"初始化界面错误!");
  }
}

// 单证信息列表的初始化
function initBusynessGrid()
{                               
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="保单号";
		iArray[1][1]="80px";  
		iArray[1][2]=100;     
		iArray[1][3]=0;       
		
		iArray[2]=new Array();
		iArray[2][0]="契约控制编号";          		 
		iArray[2][1]="80px";            		 
		iArray[2][2]=85;            			   
		iArray[2][3]=0;  
		
		iArray[3]=new Array();
		iArray[3][0]="客户编码";
		iArray[3][1]="80px";            		 
		iArray[3][2]=85;            			   
		iArray[3][3]=0;              			   
		
		iArray[4]=new Array();
		iArray[4][0]="生效日期";        		   
		iArray[4][1]="80px";            		 
		iArray[4][2]=85;            			   
		iArray[4][3]=0;              			   
		
		iArray[5]=new Array();
		iArray[5][0]="身份证号";          		 
		iArray[5][1]="150px";            		 
		iArray[5][2]=100;            		 	   
		iArray[5][3]=0;              			   
		
		iArray[6]=new Array();
		iArray[6][0]="投保年龄";         		   
		iArray[6][1]="60px";            		 
		iArray[6][2]=100;            			   
		iArray[6][3]=0;              			   
		
		iArray[7]=new Array();
		iArray[7][0]="性别";          		   
		iArray[7][1]="40px";            		 
		iArray[7][2]=60;            			   
		iArray[7][3]=0;              			   

		iArray[8]=new Array();
		iArray[8][0]="职业类别";         		   
		iArray[8][1]="60px";            		 
		iArray[8][2]=100;            			   
		iArray[8][3]=0;   
		
		iArray[9]=new Array();
		iArray[9][0]="次标准体加费率";         		   
		iArray[9][1]="50px";            		 
		iArray[9][2]=100;            			   
		iArray[9][3]=3;  
	
		iArray[10]=new Array();
		iArray[10][0]="出生日期";         		   
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			   
		iArray[10][3]=0;      
		
		iArray[11]=new Array();
		iArray[11][0]="险种";  
		iArray[11][1]="60px";  
		iArray[11][2]=100;     
		iArray[11][3]=0;       

		iArray[12]=new Array();
		iArray[12][0]="保额";  
		iArray[12][1]="80px";  
		iArray[12][2]=100;     
		iArray[12][3]=0;       
		
		iArray[13]=new Array();
		iArray[13][0]="保费";  
		iArray[13][1]="80px";  
		iArray[13][2]=100;     
		iArray[13][3]=0;  
	
		iArray[14]=new Array();
		iArray[14][0]="准备金";
		iArray[14][1]="80px";  
		iArray[14][2]=100;     
		iArray[14][3]=0;      
		
		iArray[15]=new Array();		
		iArray[15][0]="保单状态"; 
		iArray[15][1]="60px";     
		iArray[15][2]=100;        
		iArray[15][3]=0;    
		
		iArray[16]=new Array();		
		iArray[16][0]="签单日期"; 
		iArray[16][1]="80px";     
		iArray[16][2]=100;        
		iArray[16][3]=0;          
		           
	  BusynessGrid = new MulLineEnter( "fm" , "BusynessGrid" ); 
	  //这些属性必须在loadMulLine前
	  BusynessGrid.mulLineCount = 0;   
	  BusynessGrid.displayTitle = 1;
	  BusynessGrid.locked = 0;
	  BusynessGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  //BusynessGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	  BusynessGrid.hiddenPlus=1;
	  BusynessGrid.hiddenSubtraction=1;
	  BusynessGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

</script>