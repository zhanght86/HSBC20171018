<%
//程序名称：ReProposalPrtInit.jsp
//程序功能：
//创建日期：2003-04-3 11:10:36
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<%
GlobalInput globalInput = (GlobalInput)session.getValue("GI");
String strManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('ContNo').value = '';
    document.all('ManageCom').value = <%=tGI.ComCode%>;
    document.all('Reason').value = '';
    document.all('NeedPay').value = '';
    document.all('BatchNo').value = '';
    //document.all('AgentGroup').value = '';
    //document.all('RiskCode').value = '';
    
  }
  catch(ex)
  {
    alert("在ReProposalPrtInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  	manageCom = '<%= strManageCom %>';
    initInpBox();
	  initPolGrid();
  }
  catch(re)
  {
    alert("ReProposalPrtInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
var PolGrid;          //定义为全局变量，提供给displayMultiline使用

// 保单信息列表的初始化
// 保单信息列表的初始化
function initPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单号";         		//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人姓名";         		//列名
      iArray[4][1]="160px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="签单日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[6]=new Array();
      iArray[6][0]="机构";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      

	  	iArray[7]=new Array();
	  	iArray[7][0]="打印标记";
	  	iArray[7][1]="0px";
	  	iArray[7][2]=100;
	  	iArray[7][3]=3;
	  	
	  	iArray[8]=new Array();
	  	iArray[8][0]="保单类型";
	  	iArray[8][1]="0px";
	  	iArray[8][2]=100;
	  	iArray[8][3]=3;
	  	
	  	iArray[9]=new Array();
	  	iArray[9][0]="家庭单号";
	  	iArray[9][1]="0px";
	  	iArray[9][2]=100;
	  	iArray[9][3]=3;

      
      PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canChk = 1;
      //PolGrid.canSel=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
