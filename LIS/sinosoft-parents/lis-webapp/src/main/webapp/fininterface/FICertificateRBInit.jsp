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
    alert("FICertificateRBAppInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {    
    initInpBox();
    initResultGrid();
    queryResultGrid();
  }
  catch(re)
  {
    alert("FICertificateRBAppInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[1][0]="红冲申请号码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="凭证类型编号";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="业务号码编号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=0;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="业务号码";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=0;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="红冲原因类型";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=170;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="细节描述";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=170;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="申请日期";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=170;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="申请人";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=170;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="申请状态";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=170;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      RBResultGrid = new MulLineEnter( "document" , "RBResultGrid" ); 
      //这些属性必须在loadMulLine前
      RBResultGrid.mulLineCount = 0;   
      RBResultGrid.displayTitle = 1;
      RBResultGrid.locked = 1;
      RBResultGrid.canSel = 1;
      RBResultGrid.canChk = 0;
      RBResultGrid.selBoxEventFuncName = "ReturnData";      
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
