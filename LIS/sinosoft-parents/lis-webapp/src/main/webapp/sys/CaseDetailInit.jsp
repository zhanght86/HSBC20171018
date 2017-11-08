<%
//程序名称：CaseInfoInit.jsp
//程序功能：
//创建日期：2004-2-18
//创建人  ：LiuYansong
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">

function initInpBox()
{
  try
  {
    
  }
  catch(ex)
  {
    alert("在ClaimGetQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ClaimGetQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
  	initInpBox();
  	initSelBox();
		ShowCaseDetail();
  }
  catch(re)
  {
    alert("SyS/CaseInfoInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 报案信息列表的初始化
function initCheckGrid()
{
  var iArray = new Array();
  try
  {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="审核类型";    	//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="理赔进程";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="理算审核人";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[4]=new Array();
      iArray[4][0]="次数";         			//列名
      iArray[4][1]="30px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;
      
      CheckGrid = new MulLineEnter( "fm" , "CheckGrid" ); 
      CheckGrid.mulLineCount = 0;   
      CheckGrid.displayTitle = 1;
      CheckGrid.locked = 1;
      CheckGrid.canSel=1;
      CheckGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
    	alert("aaaaaaaaaa");
      alert(ex);
    }
}
</script>
