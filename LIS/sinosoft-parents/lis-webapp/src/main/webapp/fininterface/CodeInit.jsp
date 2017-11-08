<%
//程序名称：CodeInit.jsp
//程序功能：
//创建日期：2002-08-16 17:44:43
//创建人  ：CrtHtml程序创建
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
    document.all('CodeType').value = '';
    document.all('Code').value = '';
    document.all('CodeName').value = '';
    document.all('CodeAlias').value = '';
    document.all('OtherSign').value = '';


  }
  catch(ex)
  {
    alert("在CodeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                                       
function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();   
  }
  catch(re)
  {
    alert("CodeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

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
        iArray[1][0]="编码类型";         //列名
        iArray[1][1]="120px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=1;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="编码";         //列名
        iArray[2][1]="120px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=1;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="编码名称";         //列名
        iArray[3][1]="150px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=1;         //是否允许录入，0--不能，1--允许
    
        iArray[4]=new Array();
        iArray[4][0]="编码别名";         //列名
        iArray[4][1]="150px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=1;         //是否允许录入，0--不能，1--允许

        
        iArray[5]=new Array();
        iArray[5][0]="其它标志";         //列名
        iArray[5][1]="100px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=1;         //是否允许录入，0--不能，1--允许


  
        CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 

        //这些属性必须在loadMulLine前
        CodeGrid.mulLineCount = 1;   
        CodeGrid.displayTitle = 1;
		
        CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化CodeGrid时出错："+ ex);
      }
}
</script>
