<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {    
    
 
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initAgentGrid();
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
        iArray[1][0]="机构编码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="管理机构";         //列名
        iArray[2][1]="80px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="机构名称";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        

  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 

        //这些属性必须在loadMulLine前
        AgentGrid.mulLineCount = 10;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
//        AgentGrid.canChk=0;
        AgentGrid.locked=1;
	      AgentGrid.hiddenPlus=1;
	      AgentGrid.hiddenSubtraction=1;
        AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }


</script>
