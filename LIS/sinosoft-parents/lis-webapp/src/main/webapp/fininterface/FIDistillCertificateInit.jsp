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
    alert("FIDistillCertificateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {   
    initInpBox();
    initResultGrid();
  }
  catch(re)
  {
    alert("FIDistillCertificateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


 function initResultGrid()
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
      iArray[1][1]="70px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="生成日期";      	   		//列名
      iArray[2][1]="100px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[3]=new Array();
      iArray[3][0]="提数区间";      	   		//列名
      iArray[3][1]="130px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[4]=new Array();
      iArray[4][0]="操作员";      	   		//列名
      iArray[4][1]="60px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      ResultGrid = new MulLineEnter( "document" , "ResultGrid" ); 
      //这些属性必须在loadMulLine前
     ResultGrid.mulLineCount = 5;   
      ResultGrid.displayTitle = 1;
      ResultGrid.locked = 1;
      ResultGrid.canSel = 1;
      ResultGrid.canChk = 0;
      ResultGrid.hiddenSubtraction = 1;
      ResultGrid.hiddenPlus = 1;
      ResultGrid.loadMulLine(iArray);  
      

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
