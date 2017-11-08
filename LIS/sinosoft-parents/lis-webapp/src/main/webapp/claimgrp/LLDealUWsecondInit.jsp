<%
//文件名：LLDealUWsecondInit.jsp
//功能：二核结论
//日期：2004-12-23 16:49:22
//建立人：zhangxing  更改：yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
    fm.all('CaseNo').value = nullToEmpty("<%= tCaseNo %>");
    fm.all('InsuredNo').value = nullToEmpty("<%= tInsuredNo %>");
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
    initLLContGrid();
    initLLPolGrid();
    initParam();
    initQueryLLContGrid();
  }
  catch(ex)
  {
    alter("在LLDealUWsecondInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}




// 调查结论表的初始化
function initLLContGrid()
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
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="80px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批次号";             //列名
      iArray[2][1]="80px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="合同号码";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="投保人号码";             //列名
      iArray[4][1]="0px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="投保人名称";             //列名
      iArray[5][1]="80px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="核保状态";             //列名
      iArray[6][1]="80px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="赔案相关标志";             //列名
      iArray[7][1]="80px";                //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="投保书健康告知栏询问号";             //列名
      iArray[8][1]="0px";                //列宽
      iArray[8][2]=200;                  //列最大值
      iArray[8][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="体检健康告知栏询问号";             //列名
      iArray[9][1]="0px";                //列宽
      iArray[9][2]=200;                  //列最大值
      iArray[9][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="对应未告知情况";             //列名
      iArray[10][1]="0px";                //列宽
      iArray[10][2]=200;                  //列最大值
      iArray[10][3]=3;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="出险人目前健康状况介绍";             //列名
      iArray[11][1]="0px";                //列宽
      iArray[11][2]=200;                  //列最大值
      iArray[11][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      LLContGrid = new MulLineEnter( "fm" , "LLContGrid" ); 
      //这些属性必须在loadMulLine前
      LLContGrid.mulLineCount = 0;   
      LLContGrid.displayTitle = 1;
      LLContGrid.locked = 1;
      LLContGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLContGrid.selBoxEventFuncName = "LLContGridClick"; //点击RadioBox时响应的函数名
      LLContGrid.hiddenPlus=1;
      LLContGrid.hiddenSubtraction=1;
      LLContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


//调查申请表初始化
function initLLPolGrid()
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
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="100px";                //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="合同号";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="总单投保单号码";             //列名
      iArray[3][1]="80px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保单险种号码";             //列名
      iArray[4][1]="100px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="核保顺序号";             //列名
      iArray[5][1]="100px";                //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 


      iArray[6]=new Array();
      iArray[6][0]="核保结论";             //列名
      iArray[6][1]="60px";                //列宽
      iArray[6][2]=200;                  //列最大值
      iArray[6][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="核保意见";             //列名
      iArray[7][1]="0px";                //列宽
      iArray[7][2]=100;                  //列最大值
      iArray[7][3]=3;                    //是否允许输入,1表示允许，0表示不允许

      LLPolGrid = new MulLineEnter( "fm" , "LLPolGrid" ); 
      //这些属性必须在loadMulLine前
      LLPolGrid.mulLineCount = 0;   
      LLPolGrid.displayTitle = 1;
      LLPolGrid.locked = 1;
      LLPolGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLPolGrid.selBoxEventFuncName = "LLPolGridClick"; //点击RadioBox时响应的函数名
      LLPolGrid.hiddenPlus=1;
      LLPolGrid.hiddenSubtraction=1;
      LLPolGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
