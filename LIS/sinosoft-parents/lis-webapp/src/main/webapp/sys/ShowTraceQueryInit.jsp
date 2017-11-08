<%
//程序名称：ShowTraceQueryInit.jsp
//程序功能：保单服务轨迹查询
//创建日期：2005-11-24 11:23
//创建人  ：关巍
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

//接收报案页面传递过来的参数
function initParam()
{
   document.all('ContNo').value= nullToEmpty("<%= tContNo %>");	
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
		initTraceGrid();
		//alert("initTraceGrid");
		initParam();
		//alert("initParam");
		LLTraceGrid();
		//alert("LLTraceGrid");
  }
  catch(re)
  {
    alert("ShowTraceQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initTraceGrid()
  {                               
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                 //列名
        iArray[0][1]="30px";                 //列名
        iArray[0][2]=100;                    //列名
        iArray[0][3]=0;                      //列名

        iArray[1]=new Array();
        iArray[1][0]="服务起始日期";         //列名
        iArray[1][1]="40px";                 //宽度
        iArray[1][2]=100;                    //最大长度
        iArray[1][3]=0;                      //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="服务终止日期";         //列名
        iArray[2][1]="40px";                 //宽度
        iArray[2][2]=100;                    //最大长度
        iArray[2][3]=0;        		           //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="服务人员编码";         //列名
        iArray[3][1]="40px";                 //宽度
        iArray[3][2]=100;                    //最大长度
        iArray[3][3]=0;                      //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="服务人员姓名";         //列名
        iArray[4][1]="40px";                 //宽度
        iArray[4][2]=100;                    //最大长度
        iArray[4][3]=0;                      //是否允许录入，0--不能，1--允许
  
        TraceGrid = new MulLineEnter( "fm" , "TraceGrid" ); 

        //这些属性必须在loadMulLine前
        TraceGrid.mulLineCount = 0;   
        TraceGrid.displayTitle = 1;
        TraceGrid.canSel=0;
        TraceGrid.locked=1;
	      TraceGrid.hiddenPlus=1;
	      TraceGrid.hiddenSubtraction=1;
        TraceGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("初始化TraceGrid时出错："+ ex);
      }
    }
</script>
