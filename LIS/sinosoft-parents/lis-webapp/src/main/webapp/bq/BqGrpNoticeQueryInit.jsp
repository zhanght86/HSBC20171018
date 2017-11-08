<script language="JavaScript">

                     

function initForm()
{
  try
  {

    initAgentGrid();
    //easyQueryClick();
  }
  catch(re)
  {
    alert("AgentQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化AgentGrid
 ************************************************************
 */
function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="问题件流水号";         //列名
        iArray[1][1]="120px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="团单号码";         //列名
        iArray[2][1]="120px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="保全受理号";         //列名
        iArray[3][1]="120px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="问题人";         //列名
        iArray[4][1]="60px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[5]=new Array();
        iArray[5][0]="问题件类型";         //列名
        iArray[5][1]="60px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[6]=new Array();
        iArray[6][0]="下发日期";         //列名
        iArray[6][1]="80px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="问题件内容";         //列名
        iArray[7][1]="0px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=3;         //是否允许录入，0--不能，1--允许
        
  
        AgentGrid = new MulLineEnter( "document" , "AgentGrid" ); 

        //这些属性必须在loadMulLine前
        //AgentGrid.mulLineCount = 1;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
//        AgentGrid.canChk=0;
        AgentGrid.locked=1;
	      AgentGrid.hiddenPlus=1;
	      AgentGrid.hiddenSubtraction=1;
	      AgentGrid.selBoxEventFuncName = "ShowAskInfo"; //单击RadioBox时响应函数
        AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }


</script>
