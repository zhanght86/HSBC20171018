<%
//程序名称：在LLSecondManuUWInit.jsp
//程序功能：理赔人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// 接收传入数据
function initParm()
{
	fm.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
    fm.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
    fm.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
    
   	fm.all('tCaseNo').value =nullToEmpty("<%=tCaseNo%>"); //赔案号		
    fm.all('tBatNo').value =nullToEmpty("<%=tBatNo%>"); //批次号
    fm.all('tInsuredNo').value =nullToEmpty("<%=tInsuredNo%>"); //出险人客户号 
    fm.all('tClaimRelFlag').value =nullToEmpty("<%=tClaimRelFlag%>"); //赔案相关标志 	
    
	 fm.all('tMissionid').value =nullToEmpty("<%=tMissionid%>");   //任务ID 
	 fm.all('tSubmissionid').value =nullToEmpty("<%=tSubmissionid%>"); //子任务ID 
	 fm.all('tActivityid').value =nullToEmpty("<%=tActivityid%>"); //节点号 	
//	 alert(fm.all('tMissionid').value);
//	 alert(fm.all('tSubmissionid').value);
//	 alert(fm.all('tActivityid').value);
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

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
	try
	{                                   
	
	}
	catch(ex)
	{
		alert("在LLSecondManuUWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}      
}
// 保单基本信息显示框的初始化（单记录部分）
function initPolBox()
{ 
  try
  {                                   

  }
  catch(ex)
  {
    alert("在在PManuUWInit.jsp-->InitPolBox函数中发生异常:初始化界面错误!");
  }  
} 

function initForm()
{
  try
  {
 	 initLLCUWBatchGrid();
     initParm();
     initLLCUWBatchGridQuery();
  }
  catch(re)
  {
    alert("在PManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

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
		iArray[1][0]="合同号码";         		//列名
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		//列名
		iArray[2][1]="120px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[3]=new Array();
		iArray[3][0]="投保人客户号";         		//列名
		iArray[3][1]="100px";            		//列宽
		iArray[3][2]=200;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
		iArray[4]=new Array();
		iArray[4][0]="投保人姓名";         		//列名
		iArray[4][1]="100px";            		//列宽
		iArray[4][2]=200;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="出险人客户号";
		iArray[5][1]="100px";
		iArray[5][2]=60;
		iArray[5][3]=0;
	
		iArray[6]=new Array();
		iArray[6][0]="出险人姓名";
		iArray[6][1]="100px";
		iArray[6][2]=120;
		iArray[6][3]=0;
	
		iArray[7]=new Array();
		iArray[7][0]="管理机构";
		iArray[7][1]="80px";
		iArray[7][2]=120;
		iArray[7][3]=0;
	
		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		LLCUWBatchGrid.mulLineCount = 1;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.loadMulLine(iArray);       
		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
	}
	catch(ex)
	{
		alert(ex);
	}
}


</script>