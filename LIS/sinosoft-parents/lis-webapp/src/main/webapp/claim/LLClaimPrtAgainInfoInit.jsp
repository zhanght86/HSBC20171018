<%
//程序名称：LLClaimPrtAgainInfoInit.jsp
//程序功能：理赔重要单证补打信息查询
//创建日期：2005-11-01 
//创建人  ：niuzj
//更新记录：
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo%>"); //赔案号
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// 输入框的初始化
function initInpBox()
{ 

  try
  {                                   
  }
  catch(ex)
  {
    alert("在LLClaimPrtAgainInfoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LLClaimPrtAgainInfoInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        


function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  
    //对JS页面上的初始化查询的初始化
    initLLClaimPrtAgainInfoGrid();
    querySelfGrid();
  }
  catch(re)
  {
    alert("LLClaimPrtAgainInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//清空页面
function emptyForm() 
{
	//emptyFormElements();     //清空页面所有输入框，在COMMON.JS中实现
   
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	
	spanDutyGrid.innerHTML="";
}

// 报案信息列表的初始化
function initLLClaimPrtAgainInfoGrid()
{                               
    var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                  //列最大值
    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[1]=new Array();
    iArray[1][0]="打印流水号";             //列名
    iArray[1][1]="100px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="赔案号";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="单证代码";             //列名
    iArray[3][1]="100px";                //列宽
    iArray[3][2]=100;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="单证名称";             //列名
    iArray[4][1]="300px";                //列宽
    iArray[4][2]=300;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许
    
    

//    iArray[4]=new Array();
//    iArray[4][0]="补打日期";             //列名
//    iArray[4][1]="100px";                //列宽
//    iArray[4][2]=100;                  //列最大值
//    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许
//
//    iArray[5]=new Array();
//    iArray[5][0]="补打操作人";             //列名
//    iArray[5][1]="100px";                //列宽
//    iArray[5][2]=100;                  //列最大值
//    iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许
//
    
    LLClaimPrtAgainInfoGrid = new MulLineEnter( "fm" , "LLClaimPrtAgainInfoGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimPrtAgainInfoGrid.mulLineCount = 0;   
    LLClaimPrtAgainInfoGrid.displayTitle = 1;
    LLClaimPrtAgainInfoGrid.locked = 0;
//    LLClaimPrtAgainInfoGrid.canChk = 1;
    LLClaimPrtAgainInfoGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimPrtAgainInfoGrid.selBoxEventFuncName ="LLClaimPrtAgainInfoGridClick"; //响应的函数名，不加扩号
//        LLClaimPrtAgainInfoGrid.selBoxEventFuncParm =""; //传入的参数,可以省略该项     
    LLClaimPrtAgainInfoGrid.hiddenPlus=1;
    LLClaimPrtAgainInfoGrid.hiddenSubtraction=1;
    LLClaimPrtAgainInfoGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
