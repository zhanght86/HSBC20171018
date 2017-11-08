<%
//程序名称：LLGrpClaimSimpleAllInit.jsp
//程序功能：
//创建日期：2006-01-18
//创建人  ：wanzh
//更新记录：
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收参数
function initParam()
{
    fm.all('Operator').value  = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value   = nullToEmpty("<%= tGlobalInput.ComCode %>");
    fm.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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
                                 

function initForm()
{
  try
  {
    initParam();
    initSelfLLClaimSimpleAllGrid();
    querySelfGrid();
    
  }
  catch(re)
  {
    alert("LLGrpClaimSimpleAllInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


// 报案信息列表的初始化
function initSelfLLClaimSimpleAllGrid()
{                               
    var iArray = new Array();
    
    try
    {
		    iArray[0]=new Array();
		    iArray[0][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
		    iArray[0][1]="30px";               //列宽
		    iArray[0][2]=10;                   //列最大值
		    iArray[0][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		
		    iArray[1]=new Array();
		    iArray[1][0]="赔案号";             //列名
		    iArray[1][1]="100px";               //列宽
		    iArray[1][2]=100;                  //列最大值
		    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		
		    iArray[2]=new Array();
		    iArray[2][0]="状态";               //列名
		    iArray[2][1]="100px";              //列宽
		    iArray[2][2]=100;                  //列最大值
		    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		
		    iArray[3]=new Array();
		    iArray[3][0]="团体号";             //列名
		    iArray[3][1]="100px";              //列宽
		    iArray[3][2]=200;                  //列最大值
		    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		
		    iArray[4]=new Array();
		    iArray[4][0]="单位名称";             //列名
		    iArray[4][1]="100px";                //列宽
		    iArray[4][2]=500;                    //列最大值
		    iArray[4][3]=0;                      //是否允许输入,1表示允许，0表示不允许
		
		    iArray[5]=new Array();
		    iArray[5][0]="投保总人数";           //列名
		    iArray[5][1]="80px";                 //列宽
		    iArray[5][2]=200;                    //列最大值
		    iArray[5][3]=0; 
		
		
		    iArray[6]=new Array();
		    iArray[6][0]="出险日期";             //列名
		    iArray[6][1]="100px";                //列宽
		    iArray[6][2]=200;                  //列最大值
		    iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		    
		    iArray[7]=new Array();
		    iArray[7][0]="立案操作人";             //列名
		    iArray[7][1]="100px";                //列宽
		    iArray[7][2]=200;                  //列最大值
		    iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
		    
		    iArray[8]=new Array();
		    iArray[8][0]="机构";             //列名
		    iArray[8][1]="80px";                //列宽
		    iArray[8][2]=200;                  //列最大值
		    iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许   
		    
		
		    SelfLLClaimSimpleAllGrid = new MulLineEnter( "fm" , "SelfLLClaimSimpleAllGrid" ); 
		    SelfLLClaimSimpleAllGrid.displayTitle        = 1;
		    SelfLLClaimSimpleAllGrid.locked              = 0;
		    SelfLLClaimSimpleAllGrid.canSel              = 1; 
		    SelfLLClaimSimpleAllGrid.selBoxEventFuncName = "SelfLLClaimSimpleGridClick"; 
		    SelfLLClaimSimpleAllGrid.hiddenPlus          = 1;
		    SelfLLClaimSimpleAllGrid.hiddenSubtraction   = 1;
		    SelfLLClaimSimpleAllGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
