<%
//程序名称：LDMthMidRateQueryInit.jsp
//程序功能：
//创建日期：2009-10-12 19:31:48
//创建人  ：ZhanPeng程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {    
  	document.all('CurrCode').value = '';
    document.all('Per').value = '';
    document.all('DestCode').value = '';
    document.all('DestCodeName').value = '';
    document.all('Average').value = '';
    document.all('ValidYear').value = '';
    document.all('ValidMonth').value = '';
    document.all('CurrCodeName').value = '';             
  }
  catch(ex)
  {
    alert("在LDMthMidRateQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("LDMthMidRateQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
function initCodeGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="100px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

		    iArray[1]=new Array();
		    iArray[1][0]="外汇币种代码";         //列名
		    iArray[1][1]="100px";         //宽度
		    iArray[1][2]=100;         //最大长度
		    iArray[1][3]=0;         //是否允许录入，0--不能，1--允许
		
		    iArray[2]=new Array();
		    iArray[2][0]="外币数额单位";         //列名
		    iArray[2][1]="100px";         //宽度
		    iArray[2][2]=100;         //最大长度
		    iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
		
		    iArray[3]=new Array();
		    iArray[3][0]="目标币种代码";         //列名
		    iArray[3][1]="100px";         //宽度
		    iArray[3][2]=100;         //最大长度
		    iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
		    
		    iArray[4]=new Array();
		    iArray[4][0]="月平均中间价";         //列名
		    iArray[4][1]="100px";         //宽度
		    iArray[4][2]=100;         //最大长度
		    iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
		    
		    iArray[5]=new Array();
		    iArray[5][0]="作用年月";         //列名
		    iArray[5][1]="100px";         //宽度
		    iArray[5][2]=100;         //最大长度
		    iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
		    
		    iArray[6]=new Array();
		    iArray[6][0]="作用日期";         //列名
		    iArray[6][1]="100px";         //宽度
		    iArray[6][2]=100;         //最大长度
		    iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
		    

	   		CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 
	      //这些属性必须在loadMulLine前
	      CodeGrid.mulLineCount = 5;   
	      CodeGrid.displayTitle = 1;
	      CodeGrid.canSel = 1;
	      CodeGrid.hiddenPlus = 1;
	      CodeGrid.hiddenSubtraction = 1; 
	      CodeGrid.loadMulLine(iArray);  
	      CodeGrid.selBoxEventFuncName = "selectItem";

      }
      catch(ex)
      {
        alert("初始化CodeGrid时出错："+ ex);
      }
    }


</script>
