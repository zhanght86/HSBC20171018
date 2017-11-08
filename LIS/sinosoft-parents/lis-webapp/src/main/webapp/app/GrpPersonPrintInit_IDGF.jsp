<%
//程序名称：GrpPersonPrintInit_IDGF.jsp
//程序功能：
//创建日期：2007-04-06 10:00
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
  String strManageCom = globalInput.ComCode;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("在GrpPersonPrintInit_IDGF.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
  	
    initInpBox();
    initPolGrid();
    //document.all('ManageCom').value = <%=strManageCom%>;
  }
  catch(re)
  {
    alert("GrpPersonPrintInit_IDGF.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}                                                                 

var PolGrid;          //定义为全局变量，提供给displayMultiline使用
var GrpPolGrid;       //定义为全局变量，提供给displayMultiline使用

// 保单信息列表的初始化
function initGrpPolGrid()
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
      iArray[1][0]="团体保单号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="100px";                     //列宽
      iArray[2][2]=100;                        //列最大值
      iArray[2][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="险种编码";                 //列名
      iArray[3][1]="0px";                     //列宽
      iArray[3][2]=100;                        //列最大值
      iArray[3][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="投保人姓名";               //列名
      iArray[4][1]="180px";            	       //列宽
      iArray[4][2]=100;                        //列最大值
      iArray[4][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="被保人人数";               //列名
      iArray[5][1]="80px";            	       //列宽
      iArray[5][2]=100;                        //列最大值
      iArray[5][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      iArray[6]=new Array();
      iArray[6][0]="生效日期";         	       //列名
      iArray[6][1]="80px";            	       //列宽
      iArray[6][2]=100;                        //列最大值
      iArray[6][3]=0;                          //是否允许输入,1表示允许，0表示不允许

      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
      GrpPolGrid.mulLineCount = 1;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.canSel = 0;
      GrpPolGrid.canChk = 0;
 
      GrpPolGrid.loadMulLine(iArray);
      
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
  }
  catch(ex)
  {
    alert(ex);
  }
}

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
      iArray[1][0]="个人保单号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="团体保单号";         		//列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保人姓名";         		//列名
      iArray[5][1]="180px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="被保人姓名";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;                          //是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="生效日期";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;                          //是否允许输入,1表示允许，0表示不允许

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.canSel = 0;
      PolGrid.canChk = 1;
 
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
