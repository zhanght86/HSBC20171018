<%
//程序名称：SRiskOutLisInit.jsp
//程序功能：
//创建日期：2003-11-04
//创建人  ：SunXY
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

var PolGrid;          //定义为全局变量，提供给displayMultiline使用

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  	fm.reset();                                   
  }
  catch(ex)
  {
    alert("在ReportLisInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  initInpBox();
	initPolGrid();
	
	fm.all('ManageCom').value = <%=strManageCom%>;
	 
  }
  catch(re)
  {
    alert("MeetInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 投保单信息列表的初始化
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
	  iArray[1][0]="投保单号码";         		//列名
	  iArray[1][1]="140px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="保单号码";       		//列名
	  iArray[2][1]="140px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array(); 
	  iArray[3][0]="投保人";         	//列名 
	  iArray[3][1]="100px";            	//列宽 iArray[3][2]=100; 
	  iArray[3][2]=200 ;
	  iArray[3][3]= 0 ;
	
	  iArray[4]=new Array();
	  iArray[4][0]="被保人";        //列名
	  iArray[4][1]="160px";            	//列宽
	  iArray[4][2]=200;            			//列最大值
	  iArray[4][3]=0; 
     
      
    iArray[5]=new Array();
	  iArray[5][0]="保费";        //列名
	  iArray[5][1]="80px";            	//列宽
	  iArray[5][2]=200;            			//列最大值
	  iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	  
    iArray[6]=new Array();
	  iArray[6][0]="业务员";         	//列名
	  iArray[6][1]="120px";            	//列宽
	  iArray[6][2]=200; 
	  iArray[6][3]=0;
	  
	
      
	  PolGrid = new MulLineEnter( "fmSave" , "PolGrid" ); 
	  //这些属性必须在loadMulLine前
	  PolGrid.mulLineCount = 10;   
	  PolGrid.displayTitle = 1;
	  PolGrid.canSel = 0;
    PolGrid.locked = 1;
	  PolGrid.loadMulLine(iArray);  
	
	} catch(ex) {
		alert(ex);
	}
}

</script>