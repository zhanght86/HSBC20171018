<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.lis.certify.SysOperatorNoticeBL"%>
<%
	//程序名称：
	//程序功能：
	//创建日期：
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	String CurrentDate = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	
	
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//添加页面控件的初始化。
%>
<script type="text/javascript">
function initForm()
{
  try
  {
 		initMulline9Grid();
 		//initRiskGrid();

  }
  catch(re)
  {
    myAlert(""+"InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
function initMulline9Grid()
  {                               
    var iArray = new Array();      
    try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="编码";      	   		//列名
      iArray[1][1]="80px";            			//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="名称";          		//列名
      iArray[2][1]="200px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
 

      iArray[3]=new Array();
      iArray[3][0]="申请日期";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="状态";      	   		//列名
      iArray[4][1]="100px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[5]=new Array();
      iArray[5][0]="当前操作人";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=3; 
      
       iArray[6]=new Array();
      iArray[6][0]="上线日期";      	   		//列名
      iArray[6][1]="100px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=3; 
      
       iArray[7]=new Array();
      iArray[7][0]="是否有问题件";      	   		//列名
      iArray[7][1]="100px";            			//列宽
      iArray[7][2]=10;            			//列最大值
      iArray[7][3]=1; 
      
    Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

	Mulline9Grid.mulLineCount=0;
	Mulline9Grid.displayTitle=1;
	Mulline9Grid.canSel=1;
	Mulline9Grid.canChk=0;
	Mulline9Grid.hiddenPlus=1;
	Mulline9Grid.hiddenSubtraction=1;

	Mulline9Grid.selBoxEventFuncName="ShowDetail";
	Mulline9Grid.loadMulLine(iArray);
    }
    catch(ex)
    {
      myAlert(ex);
    }
}

function initRiskGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
		
		RiskGrid.mulLineCount=1;
		RiskGrid.displayTitle=1;
		RiskGrid.canSel=1;
		RiskGrid.canChk=0;
		RiskGrid.hiddenPlus=1;
		RiskGrid.hiddenSubtraction=1;
		
		RiskGrid.selBoxEventFuncName="ShowDetail";

		RiskGrid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
