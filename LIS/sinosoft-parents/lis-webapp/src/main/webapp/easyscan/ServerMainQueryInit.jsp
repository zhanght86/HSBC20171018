<%
//程序名称：EsDocMainQuery.js
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initForm()
{
  try
  {
	initInpBox();
//	initSelBox();    
	initCodeGrid();
  }
  catch(re)
  {
	alert("ServerMainQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	document.all('ManageCom').value = '';
  }
  catch(ex)
  {
	alert("在ServerMainQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在ServerMainQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        


/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
var CodeGrid;		       //定义为全局变量，提供给displayMultiline使用
function initCodeGrid()
{                               
      var iArray = new Array();
      
      try
      {
      	iArray[0]=new Array();
        iArray[0][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";         //列宽
        iArray[0][2]=100;            //列最大值
        iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
        
		iArray[1]=new Array();
        iArray[1][0]="公司名称";         //列名
        iArray[1][1]="60px";         //列宽
        iArray[1][2]=100;            //列最大值
        iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="服务器名称";    //列名
        iArray[2][1]="150px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
 
        CodeGrid = new MulLineEnter( "fm" , "CodeGrid" ); 

        //这些属性必须在loadMulLine前
        CodeGrid.mulLineCount = 5;   
        CodeGrid.displayTitle = 1;
        CodeGrid.canSel=1;
	//对象初始化区：调用对象初始化方法，属性必须在此前设置
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
