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
	fm.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
    fm.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
    fm.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
    fm.all('ContNo').value = nullToEmpty("<%=tContNo%>");
	fm.all('InsuredNo').value =nullToEmpty("<%=tInsuredNo%>");
	fm.all('CaseNo').value = nullToEmpty("<%=tCaseNo%>");
	fm.all('BatNo').value = nullToEmpty("<%=tBatNo%>");
	fm.all('SendFlag').value = nullToEmpty("<%=tSendFlag%>");
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
		initParam();
		queryLLRiskGridInfo();
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
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单险种号码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="主险保单号码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="险种名称";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[5]=new Array();
      iArray[5][0]="保费";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保额";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="保险起期";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="保险止期";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[9]=new Array();
      iArray[9][0]="交费间隔(月)";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="交费年期";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
           
      LLRiskGrid = new MulLineEnter( "fm","LLRiskGrid" ); 
      //这些属性必须在loadMulLine前
      LLRiskGrid.mulLineCount = 3;   
      LLRiskGrid.displayTitle = 1;
      LLRiskGrid.locked = 1;
      LLRiskGrid.canSel = 1;
      LLRiskGrid.hiddenPlus = 1;
      LLRiskGrid.hiddenSubtraction = 1;
      LLRiskGrid.loadMulLine(iArray);     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>