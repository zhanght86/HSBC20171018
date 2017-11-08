<%
	//程序名称：LLSecondUWAllInit.jsp
	//程序功能：理赔人工核保获取队列
	//创建日期：2005-1-28 11:10:36
	//创建人  ：zhangxing
	//更新记录：  更新人  yuejw  更新日期     更新原因/内容
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>            
<script language="JavaScript">

//接受上页传入的数据 
function initParam()
{
	fm.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
    fm.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
    fm.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
	try
	{
		initParam(); //
		initLLCUWBatchGrid();  //
		initSelfLLCUWBatchGrid();
		initSelfLLCUWBatchGridQuery();  //          
	}
	catch(re)
	{
		alert("在UWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

// 保单信息列表的初始化
function initLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="赔案号";         		//列名
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		//列名
		iArray[2][1]="120px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="被保险人号码";         		//列名
		iArray[3][1]="120px";            		//列宽
		iArray[3][2]=100;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[4]=new Array();
		iArray[4][0]="被保险人名称";         		//列名
		iArray[4][1]="120px";            		//列宽
		iArray[4][2]=100;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="赔案相关标志";         		//列名
		iArray[5][1]="100px";            		//列宽
		iArray[5][2]=100;            			//列最大值
		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
	
		iArray[6]=new Array();                                                       
		iArray[6][0]="机构";         		//列名                                     
		iArray[6][1]="120px";            		//列宽                                   
		iArray[6][2]=100;            			//列最大值                                 
		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
	
	    iArray[7]=new Array();
	    iArray[7][0]="Missionid";             //列名
	    iArray[7][1]="0px";                //列宽
	    iArray[7][2]=200;                  //列最大值
	    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	
	    iArray[8]=new Array();
	    iArray[8][0]="Submissionid";             //列名
	    iArray[8][1]="0px";                //列宽
	    iArray[8][2]=200;                  //列最大值
	    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	    
	    iArray[9]=new Array();
	    iArray[9][0]="Activityid";             //列名
	    iArray[9][1]="0px";                //列宽
	    iArray[9][2]=200;                  //列最大值
	    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		LLCUWBatchGrid.mulLineCount = 0;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);     
//		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}

// 保单信息列表的初始化
function initSelfLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="赔案号";         		//列名
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		//列名
		iArray[2][1]="120px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="被保险人号码";         		//列名
		iArray[3][1]="120px";            		//列宽
		iArray[3][2]=100;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[4]=new Array();
		iArray[4][0]="被保险人名称";         		//列名
		iArray[4][1]="120px";            		//列宽
		iArray[4][2]=100;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="赔案相关标志";         		//列名
		iArray[5][1]="100px";            		//列宽
		iArray[5][2]=100;            			//列最大值
		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
	
		iArray[6]=new Array();                                                       
		iArray[6][0]="机构";         		//列名                                     
		iArray[6][1]="120px";            		//列宽                                   
		iArray[6][2]=100;            			//列最大值                                 
		iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
	
	    iArray[7]=new Array();
	    iArray[7][0]="Missionid";             //列名
	    iArray[7][1]="0px";                //列宽
	    iArray[7][2]=200;                  //列最大值
	    iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	
	    iArray[8]=new Array();
	    iArray[8][0]="Submissionid";             //列名
	    iArray[8][1]="0px";                //列宽
	    iArray[8][2]=200;                  //列最大值
	    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	    
	    iArray[9]=new Array();
	    iArray[9][0]="Activityid";             //列名
	    iArray[9][1]="0px";                //列宽
	    iArray[9][2]=200;                  //列最大值
	    iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许      
	
		SelfLLCUWBatchGrid = new MulLineEnter( "fm" , "SelfLLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		SelfLLCUWBatchGrid.mulLineCount =0;   
		SelfLLCUWBatchGrid.displayTitle = 1;
		SelfLLCUWBatchGrid.locked = 1;
		SelfLLCUWBatchGrid.canSel = 1;
		SelfLLCUWBatchGrid.hiddenPlus = 1;
		SelfLLCUWBatchGrid.hiddenSubtraction = 1;
		SelfLLCUWBatchGrid.loadMulLine(iArray);     
		SelfLLCUWBatchGrid.selBoxEventFuncName = "SelfLLCUWBatchGridClick";
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>