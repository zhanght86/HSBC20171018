<%
//程序名称：LLClaimExemptInit.jsp
//程序功能：豁免处理初始化
//创建日期：2005-07-19
//创建人  ：yuejw
//更新记录：
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                   
<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}

//把null的字符串转为空
function nullToEmpty(String)
{
	if ((String == "null") || (String == "undefined"))
	{
		String = "";
	}
	return String;
}

function initInpBox()
{ 
    try
    {                                   
		ClearPagedata();
    }
    catch(ex)
    {
        alert("在LLClaimExemptInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }      
}

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initLLClaimExemptGrid();  
        LLClaimExemptGridQuery();
    }
    catch(re)
    {
        alert("LLClaimExemptInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//豁免信息表初始化
function initLLClaimExemptGrid()
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
      iArray[2][0]="集体合同号码";             //列名
      iArray[2][1]="100px";                //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="合同号码";             //列名
      iArray[3][1]="100px";                //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保单险种号码";             //列名
      iArray[4][1]="100px";                //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="责任编码";             //列名
      iArray[5][1]="50px";                //列宽
      iArray[5][2]=100;                  //列最大值
      iArray[5][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="交费计划编码";             //列名
      iArray[6][1]="50px";                //列宽
      iArray[6][2]=100;                  //列最大值
      iArray[6][3]=0; 

      iArray[7]=new Array();
      iArray[7][0]="交费计划类型";             //列名
      iArray[7][1]="80px";                //列宽
      iArray[7][2]=200;                  //列最大值
      iArray[7][3]=0;                    //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="投保人类型";             //列名
      iArray[8][1]="60px";                //列宽
      iArray[8][2]=100;                  //列最大值
      iArray[8][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="投保人客户号码";             //列名
      iArray[9][1]="100px";                //列宽
      iArray[9][2]=100;                  //列最大值
      iArray[9][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      LLClaimExemptGrid = new MulLineEnter( "fm" , "LLClaimExemptGrid" ); 
      //这些属性必须在loadMulLine前
      LLClaimExemptGrid.mulLineCount = 0;   
      LLClaimExemptGrid.displayTitle = 1;
      LLClaimExemptGrid.locked = 1;
      LLClaimExemptGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
      LLClaimExemptGrid.selBoxEventFuncName = "LLClaimExemptGridClick"; //点击RadioBox时响应的函数名 
      LLClaimExemptGrid.hiddenPlus=1;
      LLClaimExemptGrid.hiddenSubtraction=1;
      LLClaimExemptGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
