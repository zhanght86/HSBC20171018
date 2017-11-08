<%
//程序名称：RePrintInit.jsp
//程序功能：
//创建日期：2003-03-31
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	String strManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">

var PolGrid;          //定义为全局变量，提供给displayMultiline使用

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
      
}

function initForm()
{
  try
  {
  	//manageCom = '<%= strManageCom %>';
    initInpBox();
	  initPolGrid();
  }
  catch(re)
  {
    alert("RePrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initPolGrid()
{                               
  var iArray = new Array();
      
  try {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="流水号";         		//列名
	  iArray[1][1]="160px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[2]=new Array();
	  iArray[2][0]="打印类型";       		//列名
	  iArray[2][1]="100px";            	//列宽
	  iArray[2][2]=100;           			//列最大值
	  iArray[2][3]=0;
	
	  iArray[3]=new Array();
	  iArray[3][0]="保单号";       		//列名
	  iArray[3][1]="160px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[4]=new Array();
	  iArray[4][0]="管理机构";         	//列名
	  iArray[4][1]="80px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=0;
	
	  iArray[5]=new Array();
	  iArray[5][0]="代理人编码";        //列名
	  iArray[5][1]="100px";            	//列宽
	  iArray[5][2]=200;            			//列最大值
	  iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许     

	
	  PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
	  //这些属性必须在loadMulLine前
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 1;
    PolGrid.locked = 1;
    PolGrid.hiddenPlus = 1;
    PolGrid.hiddenSubtraction = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>
