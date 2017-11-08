<%
//程序名称：EsDocManageInit.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {
  }
  catch(ex)
  {
    alert("在AcceptIssueDocInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
//    initSubType();
    initCodeGrid();
  }
  catch(re)
  {
    alert("AcceptIssueDocInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
var CodeGrid;          //定义为全局变量，提供给displayMultiline使用
function initCodeGrid()
{                               
    var iArray = new Array();
    
    try
    {
    	tArray = new Array();
    	tArray.push("序号");
    	tArray.push("30px");
    	tArray.push(100);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("业务号码");
    	tArray.push("80px");
    	tArray.push(80);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("业务子类");
    	tArray.push("50px");
    	tArray.push(50);
    	tArray.push(0);
    	iArray.push(tArray);
       	
    	tArray = new Array();
    	tArray.push("页数");
    	tArray.push("30px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("管理机构");
    	tArray.push("60px");
    	tArray.push(80);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("申请日期");
    	tArray.push("50px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	
    	tArray = new Array();
    	tArray.push("申请人");
    	tArray.push("50px");
    	tArray.push(30);
    	tArray.push(0);
    	iArray.push(tArray);
    	

      CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 
      //这些属性必须在loadMulLine前
      CodeGrid.mulLineCount = 5;   
      CodeGrid.displayTitle = 1;
      CodeGrid.hiddenSubtraction=1;
      CodeGrid.hiddenPlus=1;
      CodeGrid.canChk=1;
      CodeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("初始化CodeGrid时出错："+ ex);
    }
}
    
    
</script>
