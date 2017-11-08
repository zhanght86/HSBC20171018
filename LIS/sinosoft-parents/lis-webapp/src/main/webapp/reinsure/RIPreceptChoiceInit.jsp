<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-21
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
 	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 	String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);

 %>
<script type="text/javascript">
	var x=0;
	var y=0;
	var com=new Array(y);
	var line=new Array(x);
	
function initInpBox()
{
  try
  {  
    fm.all('OperateNo').value = <%=request.getParameter("OperateNo")%>;   
    fm.all('CodeType').value = <%=request.getParameter("CodeType")%>;
    fm.all('SerialNo').value = <%=request.getParameter("SerialNo")%>;
    fm.all('RIPolno').value = <%=request.getParameter("RIPolno")%>;

  }         
  catch(ex) 
  {
    myAlert("进行初始化是出现错误！！！！");
  }
}

// 下拉列表的初始化
function initSelBox()
{
	try
	{
		fm.RelaTempPolBut.style.display='';	  	
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
        intPreceptSearchGrid();  
    	intPreceptInfoGrid(); 
		queryClick();
    
  	}
  	catch(re)
	{
		myAlert("ReContManageInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

//再保方案查询 mulline初始值设定
function intPreceptSearchGrid(){
	var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
			
			iArray[1]=new Array();
			iArray[1][0]="再保合同号码";
			iArray[1][1]="90px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="再保方案号码";
			iArray[2][1]="90px";  
			iArray[2][2]=100;     
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="再保方案名称";
			iArray[3][1]="100px"; 
			iArray[3][2]=100;
			iArray[3][3]=0;
			
			iArray[4]=new Array();
			iArray[4][0]="累计方案编码";
			iArray[4][1]="80px";
			iArray[4][2]=100;
			iArray[4][3]=0;
			
			iArray[5]=new Array();
			iArray[5][0]="累计方案名称";
			iArray[5][1]="80px";
			iArray[5][2]=100;
			iArray[5][3]=0;
			
			iArray[6]=new Array();
			iArray[6][0]="分保方案类型代码"; //01：合同分保；02：临时分保
			iArray[6][1]="0px";  
			iArray[6][2]=100;     
			iArray[6][3]=3;
			
			iArray[7]=new Array();
			iArray[7][0]="分保方案类型";
			iArray[7][1]="80px";  
			iArray[7][2]=100;     
			iArray[7][3]=0;
	
			iArray[8]=new Array();
			iArray[8][0]="方案状态";
			iArray[8][1]="80px";  
			iArray[8][2]=100;     
			iArray[8][3]=0;
			
			iArray[9]=new Array();
			iArray[9][0]="方案状态代码"; //01 生效  02 未生效  
			iArray[9][1]="0px";  
			iArray[9][2]=100;     
			iArray[9][3]=3;
			
	  	PreceptSearchGrid = new MulLineEnter( "fm" , "PreceptSearchGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	PreceptSearchGrid.mulLineCount = 10;   
	  	PreceptSearchGrid.displayTitle = 1;
	  	PreceptSearchGrid.locked = 0;
	  	PreceptSearchGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	  	PreceptSearchGrid.selBoxEventFuncName ="showPrecept"; //响应的函数名，不加扩号   
	  	PreceptSearchGrid.hiddenPlus=1; 
	  	PreceptSearchGrid.hiddenSubtraction=1;
	  	PreceptSearchGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
//再保方案查询 mulline初始值设定
function intPreceptInfoGrid(){
	var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
			
			iArray[1]=new Array();
			iArray[1][0]="方案名称";
			iArray[1][1]="90px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="再保公司";
			iArray[2][1]="90px";  
			iArray[2][2]=100;     
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="临分限额";
			iArray[3][1]="0px"; 
			iArray[3][2]=100;
			iArray[3][3]=3;
		
			iArray[4]=new Array();
			iArray[4][0]="临分比例";
			iArray[4][1]="80px";
			iArray[4][2]=100;
			iArray[4][3]=0;

			iArray[5]=new Array();
			iArray[5][0]="费率表";
			iArray[5][1]="80px";
			iArray[5][2]=100;
			iArray[5][3]=0;
			
			iArray[6]=new Array();
			iArray[6][0]="佣金率表";
			iArray[6][1]="80px";
			iArray[6][2]=100;
			iArray[6][3]=0;
			
						
	  	PreceptInfoGrid = new MulLineEnter( "fm" , "PreceptInfoGrid" ); 
	  	//这些属性必须在loadMulLine前   
	  	PreceptInfoGrid.displayTitle = 1;
	  	PreceptInfoGrid.locked = 0;
	  	PreceptInfoGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）  
	  	PreceptInfoGrid.hiddenPlus=1; 
	  	PreceptInfoGrid.hiddenSubtraction=1;
	  	PreceptInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>


