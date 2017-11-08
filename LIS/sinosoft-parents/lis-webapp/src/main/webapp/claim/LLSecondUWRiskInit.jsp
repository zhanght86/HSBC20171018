<%
//程序名称：LLSecondUWRiskInit.jsp
//程序功能：险种核保信息界面初始化-------理赔部分
//创建日期：2005-01-06 11:10:36
//创建人  ：HYQ
//更新记录：  更新人 yuejw   更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
//接受上页传入的数据 
function initParam()
{
	document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
    document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
    document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
    document.all('ContNo').value = nullToEmpty("<%=tContNo%>");
	document.all('InsuredNo').value =nullToEmpty("<%=tInsuredNo%>");
	document.all('CaseNo').value = nullToEmpty("<%=tCaseNo%>");
	document.all('BatNo').value = nullToEmpty("<%=tBatNo%>");
	document.all('SendFlag').value = nullToEmpty("<%=tSendFlag%>");
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
//[初始化函数]---被Input页面调用
function initForm()
{
	try
	{ 
		initLLRiskGrid();
		
		initLLUWSubGrid();
		initParam();
		queryLLRiskGridInfo();
		queryInsuredInfo();
	}
	catch(re)
	{
		alert("在LLSecondUWRiskInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

// 保单险种信息列表的初始化
function initLLRiskGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名 
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许		 

      iArray[1]=new Array();
      iArray[1][0]="保单险种号码";         		 
      iArray[1][1]="10px";            		 
      iArray[1][2]=100;            			 
      iArray[1][3]=3;              			 

      iArray[2]=new Array();
      iArray[2][0]="保单号";         		 
      iArray[2][1]="140px";            		 
      iArray[2][2]=100;            			 
      iArray[2][3]=0;              			 

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		 
      iArray[3][1]="80px";            		 
      iArray[3][2]=100;            			 
      iArray[3][3]=0;              			 
      
      iArray[4]=new Array();
      iArray[4][0]="险种名称";         		 
      iArray[4][1]="200px";            		 
      iArray[4][2]=100;            			 
      iArray[4][3]=0;              			       

      iArray[5]=new Array();
      iArray[5][0]="保费";         		 
      iArray[5][1]="60px";            		 
      iArray[5][2]=100;            			 
      iArray[5][3]=0;              			 

      iArray[6]=new Array();
      iArray[6][0]="保额";         		 
      iArray[6][1]="60px";            		 
      iArray[6][2]=100;            			 
      iArray[6][3]=0;              			 

      iArray[7]=new Array();
      iArray[7][0]="保险起期";         		 
      iArray[7][1]="80px";            		 
      iArray[7][2]=100;            			 
      iArray[7][3]=0;              			 
      
      iArray[8]=new Array();
      iArray[8][0]="保险止期";         		 
      iArray[8][1]="80px";            		 
      iArray[8][2]=100;            			 
      iArray[8][3]=0;              			 
      
      
      iArray[9]=new Array();
      iArray[9][0]="交费间隔(月)";         		 
      iArray[9][1]="80px";            		 
      iArray[9][2]=100;            			 
      iArray[9][3]=0;              			 
      
      iArray[10]=new Array();
      iArray[10][0]="交费年期";         		 
      iArray[10][1]="80px";            		 
      iArray[10][2]=100;            			 
      iArray[10][3]=0;              			       
      
      iArray[11]=new Array();
      iArray[11][0]="投保单险种号码";         		 
      iArray[11][1]="80px";            		 
      iArray[11][2]=100;            			 
      iArray[11][3]=3;  
      
      iArray[12]=new Array(); //Modify by zhaorx 2006-09-23
      iArray[12][0]="主附险标记";         		 
      iArray[12][1]="80px";            		 
      iArray[12][2]=100;            			 
      iArray[12][3]=3;      
            
      LLRiskGrid = new MulLineEnter( "fm","LLRiskGrid" ); 
      //这些属性必须在loadMulLine前
      LLRiskGrid.mulLineCount = 0;   
      LLRiskGrid.displayTitle = 1;
      LLRiskGrid.locked = 1;
      LLRiskGrid.canSel = 1;
      LLRiskGrid.hiddenPlus = 1;
      LLRiskGrid.hiddenSubtraction = 1;
      LLRiskGrid.selBoxEventFuncName="LLRiskGridClick";
      LLRiskGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

//个人理陪险种核保核保轨迹表
function initLLUWSubGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名 
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许		 

      iArray[1]=new Array();
      iArray[1][0]="分案号";         		 
      iArray[1][1]="0px";            		 
      iArray[1][2]=100;            			 
      iArray[1][3]=3;              			 

      iArray[2]=new Array();
      iArray[2][0]="批次号";         		 
      iArray[2][1]="100px";            		 
      iArray[2][2]=100;            			 
      iArray[2][3]=0;              			 

      iArray[3]=new Array();
      iArray[3][0]="保单号";         		 
      iArray[3][1]="120px";            		 
      iArray[3][2]=100;            			 
      iArray[3][3]=0;              			 
      
      iArray[4]=new Array();
      iArray[4][0]="保单险种号码";         		 
      iArray[4][1]="0px";            		 
      iArray[4][2]=100;            			 
      iArray[4][3]=3;              			       

      iArray[5]=new Array();
      iArray[5][0]="核保顺序号";         		 
      iArray[5][1]="60px";            		 
      iArray[5][2]=100;            			 
      iArray[5][3]=0;              			 

      iArray[6]=new Array();
      iArray[6][0]="核保标志";         		 
      iArray[6][1]="60px";            		 
      iArray[6][2]=100;            			 
      iArray[6][3]=0;              			 

      iArray[7]=new Array();
      iArray[7][0]="核保结论";         		 
      iArray[7][1]="80px";            		 
      iArray[7][2]=100;            			 
      iArray[7][3]=0;              			 
      
      iArray[8]=new Array();
      iArray[8][0]="核保意见";         		 
      iArray[8][1]="250px";            		 
      iArray[8][2]=100;            			 
      iArray[8][3]=0;              			   
      
      iArray[9]=new Array();
      iArray[9][0]="核保日期";         		 
      iArray[9][1]="80px";            		 
      iArray[9][2]=100;            			 
      iArray[9][3]=0;             
      
		iArray[10]=new Array();
		iArray[10][0]="核保时间";         		 
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			 
		iArray[10][3]=0;    			      
           
      LLUWSubGrid = new MulLineEnter( "fm","LLUWSubGrid" ); 
      //这些属性必须在loadMulLine前
      LLUWSubGrid.mulLineCount = 0;   
      LLUWSubGrid.displayTitle = 1;
      LLUWSubGrid.locked = 1;
      LLUWSubGrid.canSel = 0;
      LLUWSubGrid.hiddenPlus = 1;
      LLUWSubGrid.hiddenSubtraction = 1;
      LLUWSubGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
