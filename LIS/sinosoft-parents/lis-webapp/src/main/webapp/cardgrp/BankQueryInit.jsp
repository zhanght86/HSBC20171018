<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {    
    fm.all('BankCode').value = '';
    fm.all('BankName').value = '';
 
  }
  catch(ex)
  {
    alert("在BankQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在BankQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initBankGrid();
  }
  catch(re)
  {
    alert("在BankQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化AgentGrid
 ************************************************************
 */
function initBankGrid()
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
        iArray[1][0]="银行编码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=80;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="银行名称";         //列名
        iArray[2][1]="80px";         //宽度
        iArray[2][2]=80;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

  
        BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 

        //这些属性必须在loadMulLine前
        BankGrid.mulLineCount = 10;   
        BankGrid.displayTitle = 1;
        BankGrid.canSel=1;
//        BankGrid.canChk=0;
        BankGrid.locked=1;
	      BankGrid.hiddenPlus=1;
	      BankGrid.hiddenSubtraction=1;
        BankGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化BankGrid时出错："+ ex);
      }
    }


</script>
