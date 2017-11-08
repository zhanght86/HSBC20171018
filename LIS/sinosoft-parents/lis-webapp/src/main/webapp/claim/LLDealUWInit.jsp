
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

function initForm()
{
  try
  { 
 	initParam();
 	
  	initLLUnfinishedContGrid();	// 未二次核保完成的合同单列表的初始化   	
    initLLContGrid();	// 已经二次核保完成的合同单列表的初始化  
    
    initQueryLLUnfinishedContGrid(); 
    initQueryLLContGrid();
  }
  catch(ex)
  {
    alter("在LLDealUWsecondInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//接收审核页面传递过来的参数
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");       
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

// 被保险人下未二次核保完成的合同单列表的初始化 
function initLLUnfinishedContGrid()
{                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";              //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";              //列宽
      iArray[0][2]=10;                  //列最大值
      iArray[0][3]=0;                   //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="130px";              //列宽
      iArray[1][2]=130;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保人号码";             //列名
      iArray[2][1]="80px";              //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人名称";           //列名
      iArray[3][1]="80px";              //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="管理机构";         //列名
      iArray[4][1]="80px";               //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="操作员";         //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      LLUnfinishedContGrid = new MulLineEnter( "document" , "LLUnfinishedContGrid" ); 
      //这些属性必须在loadMulLine前
      LLUnfinishedContGrid.mulLineCount      = 5;   
      LLUnfinishedContGrid.displayTitle      = 1;
      LLUnfinishedContGrid.locked            = 1;
      LLUnfinishedContGrid.canSel            = 0; // 1 显示 ；0 隐藏（缺省值）
      LLUnfinishedContGrid.hiddenPlus        = 1;
      LLUnfinishedContGrid.hiddenSubtraction = 1;
      LLUnfinishedContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 被保险人已经二次核保完成的合同单列表的初始化 
function initLLContGrid()
{                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";              //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";              //列宽
      iArray[0][2]=10;                  //列最大值
      iArray[0][3]=0;                   //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="赔案号";             //列名
      iArray[1][1]="130px";              //列宽
      iArray[1][2]=130;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批次号";             //列名
      iArray[2][1]="80px";              //列宽
      iArray[2][2]=80;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="管理机构";           //列名
      iArray[3][1]="80px";              //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="操作员";         //列名
      iArray[4][1]="80px";               //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="状态";         //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 
      
      LLContGrid = new MulLineEnter( "document" , "LLContGrid" ); 
      //这些属性必须在loadMulLine前
      LLContGrid.mulLineCount = 5;   
      LLContGrid.displayTitle = 1;
      LLContGrid.locked = 1;
      LLContGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLContGrid.selBoxEventFuncName = "showSecondUWInput"; //点击RadioBox时响应的函数名
      LLContGrid.hiddenPlus=1;
      LLContGrid.hiddenSubtraction=1;
      LLContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
