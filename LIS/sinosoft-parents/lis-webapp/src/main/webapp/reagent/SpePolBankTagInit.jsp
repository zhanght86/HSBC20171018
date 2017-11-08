<%
//程序名称：LAAscriptionInput.jsp
//程序功能：
//创建日期：2004-08-16 15:39:06
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
    document.all('MainPolNo').value ='';
    document.all('ContNo').value ='';
//  document.all('BranchType').value = getBranchType();    
  }
  catch(ex)
  {
    alert("在LRAscriptionInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();    
    initAscriptionGrid();    
  }
  catch(re)
  {
    alert("在LRAscriptionInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
  
// 担保人信息的初始化
function initAscriptionGrid()
  {                               
    var iArray = new Array();      
      try
      {
      
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="20px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[1]=new Array();
      iArray[1][0]="管理机构";          		        //列名
      iArray[1][1]="100px";      	      		//列宽
      iArray[1][2]=10;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
       

      iArray[2]=new Array();
      iArray[2][0]="主险保单号";      	   		//列名
      iArray[2][1]="100px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";      	   		//列名
      iArray[3][1]="70px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="保单生效日期";      	   		//列名
      iArray[4][1]="70px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="投保人";      	   		//列名
      iArray[5][1]="70px";            			//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=0;           			//是否允许输入,1表示允许，0表示不允许
      
          iArray[6]=new Array();
      iArray[6][0]="应交金额";      	   		//列名
      iArray[6][1]="70px";            			//列宽
      iArray[6][2]=10;            			//列最大值
      iArray[6][3]=0;           			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="银行编码";      	   		//列名
      iArray[7][1]="70px";            			//列宽
      iArray[7][2]=10;            			//列最大值
      iArray[7][3]=0;           			//是否允许输入,1表示允许，0表示不允许
     
      iArray[8]=new Array();
      iArray[8][0]="帐号";      	   		//列名
      iArray[8][1]="70px";            			//列宽
      iArray[8][2]=10;            			//列最大值
      iArray[8][3]=0;           			//是否允许输入,1表示允许，0表示不允许
 

      
      AscriptionGrid = new MulLineEnter( "fm" , "AscriptionGrid" ); 
      //这些属性必须在loadMulLine前
      AscriptionGrid.mulLineCount = 5;   
    AscriptionGrid.displayTitle = 1;
    AscriptionGrid.hiddenPlus = 1;
    AscriptionGrid.hiddenSubtraction = 1;
    AscriptionGrid.loadMulLine(iArray);
    AscriptionGrid.canChk =1; //复选框   
      }
      catch(ex)
      {
        alert(ex);
      }
}
}
</script>
