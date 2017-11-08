<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称： 
//程序功能： 
//创建日期： 
//创建人  jw
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
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
    alert("FinDistillRollBackInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {
    
    initInpBox();
 
    initRBResultGrid() ;
  }
  catch(re)
  {
    alert("FinDistillRollBackInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 

 
function initRBResultGrid()  
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
      iArray[1][0]="批次号";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="采集事件号码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=170;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许  

      iArray[3]=new Array();
      iArray[3][0]="数据采集时间";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=170;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
       
      RBResultGrid = new MulLineEnter( "document" , "RBResultGrid" ); 
      //这些属性必须在loadMulLine前
      RBResultGrid.mulLineCount = 5;   
      RBResultGrid.displayTitle = 1;
      RBResultGrid.locked = 1;
      RBResultGrid.canSel = 1;
      RBResultGrid.canChk = 0;
      RBResultGrid.hiddenSubtraction = 1;
      RBResultGrid.hiddenPlus = 1;
      RBResultGrid.loadMulLine(iArray);  

     }
     catch(ex)      
     {
        alert(ex);
     }
}
</script>
