<%
//程序名称：LLCaseBackMissInit.jsp
//程序功能：
//创建日期：2005-10-20 11:50
//创建人  ：wanzh
//更新记录：
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收参数
function initParam()
{
    document.all('Operator').value  = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value   = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
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
         initLLCaseBackGrid();
         initSelfLLCaseBackGrid();
         querySelfGrid();
    }
    catch(re)
    {
         alert("LLCaseBackMissInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

// 立案信息列表的初始化
function initLLCaseBackGrid()
{                               
    var iArray = new Array();
    
    try
    {
    
    iArray[0]=new Array();
    iArray[0][0]="序号";                //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";                //列宽
    iArray[0][2]=10;                    //列最大值
    iArray[0][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="赔案号";              //列名
    iArray[1][1]="150px";               //列宽
    iArray[1][2]=100;                   //列最大值
    iArray[1][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="申请人";              //列名
    iArray[2][1]="100px";               //列宽
    iArray[2][2]=100;                   //列最大值
    iArray[2][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="立案日期";  //列名
    iArray[3][1]="80px";               //列宽
    iArray[3][2]=80;                   //列最大值
    iArray[3][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="受托人姓名";          //列名
    iArray[4][1]="100px";               //列宽
    iArray[4][2]=500;                   //列最大值
    iArray[4][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="案件状态";          //列名
    iArray[5][1]="60px";               //列宽
    iArray[5][2]=60;                   //列最大值
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="事故日期";            //列名
    iArray[6][1]="80px";               //列宽
    iArray[6][2]=200;                   //列最大值
    iArray[6][3]=0;                     //是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="结案日期";             //列名
    iArray[7][1]="80px";                 //列宽
    iArray[7][2]=200;                    //列最大值
    iArray[7][3]=0;                      //是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="回退类型";             //列名
    iArray[8][1]="20px";                //列宽
    iArray[8][2]=20;                  //列最大值
    iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
    

    
    LLCaseBackGrid = new MulLineEnter( "document" , "LLCaseBackGrid" ); 
    //这些属性必须在loadMulLine前
    LLCaseBackGrid.mulLineCount      = 5;   
    LLCaseBackGrid.displayTitle      = 1;
    LLCaseBackGrid.locked            = 0;
    LLCaseBackGrid.canSel            = 1; 
    LLCaseBackGrid.hiddenPlus        = 1;
    LLCaseBackGrid.hiddenSubtraction = 1;
    LLCaseBackGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 案件信息列表的初始化
function initSelfLLCaseBackGrid()
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
    iArray[1][0]="回退号";             //列名
    iArray[1][1]="100px";                //列宽
    iArray[1][2]=100;                  //列最大值
    iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="赔案号";             //列名
    iArray[2][1]="100px";                //列宽
    iArray[2][2]=100;                  //列最大值
    iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="回退日期";             //列名
    iArray[3][1]="120px";                //列宽
    iArray[3][2]=200;                  //列最大值
    iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="回退描述";             //列名
    iArray[4][1]="200px";                //列宽
    iArray[4][2]=500;                  //列最大值
    iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="回退类型";             //列名
    iArray[5][1]="20px";                //列宽
    iArray[5][2]=20;                  //列最大值
    iArray[5][3]=3;                    //是否允许输入,1表示允许，0表示不允许

    
    SelfLLCaseBackGrid = new MulLineEnter( "document" , "SelfLLCaseBackGrid" ); 
    //这些属性必须在loadMulLine前
    SelfLLCaseBackGrid.mulLineCount = 5;   
    SelfLLCaseBackGrid.displayTitle = 1;
    SelfLLCaseBackGrid.locked = 0;
    SelfLLCaseBackGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    SelfLLCaseBackGrid.selBoxEventFuncName ="SelfLLCaseBackGridClick"; //响应的函数名，不加扩号
    SelfLLCaseBackGrid.hiddenPlus=1;
    SelfLLCaseBackGrid.hiddenSubtraction=1;
    SelfLLCaseBackGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

</script>
