<%
//Name: LLCaseBackInit.jsp
//function：回退信息类
//author: wl
//Date: 2006-9-6 14:33
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
	
//接收回退页面传递过来的参数
function initParam()
{
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('BackTypeQ').value = nullToEmpty("<%= tBackType %>");

    //alert(document.all('BackTypeQ').value);
    
    if(document.all('BackTypeQ').value=='1')
    {
    	divLLReport2.style.display='none';
        operateButton1.style.display="none";
    }
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

//按钮可用选择处理
function ButtonSelected()
{
    var submitflag = <%=tSubmitFlag%>;
    if(submitflag == 0 )
        fm.CaseBackSubmitBt.disabled = true;
    else
        fm.CaseBackSubmitBt.disabled = false;
}

//表单初始化
function initForm()
{
  try
  {
      initParam();
      initLJsPayGrid();
      ButtonSelected();
      queryLJsPayGrid();
      selectCaseBack();
    
  }
  catch(re)
  {
    alert("在LLCaseBackInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//初始化应收总表
function initLJsPayGrid()
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
      iArray[1][0]="通知书号码";         //列名
      iArray[1][1]="100px";              //列宽
      iArray[1][2]=100;                  //列最大值
      iArray[1][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保人客户号码";     //列名
      iArray[2][1]="110px";              //列宽
      iArray[2][2]=100;                  //列最大值
      iArray[2][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="总应收金额";         //列名
      iArray[3][1]="120px";              //列宽
      iArray[3][2]=100;                  //列最大值
      iArray[3][3]=0;                    //是否允许输入,1表示允许，0表示不允许  

      iArray[4]=new Array();
      iArray[4][0]="交费日期";           //列名
      iArray[4][1]="80px";               //列宽
      iArray[4][2]=100;                  //列最大值
      iArray[4][3]=0;                    //是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="银行转帐成功标志";   //列名
      iArray[5][1]="80px";               //列宽
      iArray[5][2]=200;                  //列最大值
      iArray[5][3]=0; 

      
      LJsPayGrid = new MulLineEnter( "fm" , "LJsPayGrid" ); 
      //这些属性必须在loadMulLine前
      LJsPayGrid.mulLineCount      = 0;   
      LJsPayGrid.displayTitle      = 1;
      LJsPayGrid.locked            = 0;
      LJsPayGrid.canSel            = 1; 
      LJsPayGrid.hiddenPlus        = 1;
      LJsPayGrid.hiddenSubtraction = 1;
      LJsPayGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
