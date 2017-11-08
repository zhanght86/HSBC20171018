<%
//程序名称：LDPersonQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
String CustomerNo = request.getParameter("CustomerNo");
String GrpName = StrTool.unicodeToGBK(request.getParameter("GrpName"));
String GrpContNo = request.getParameter("GrpContNo");



%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 保单查询条件
      	  fm.CustomerNo.value = '<%=CustomerNo %>';
	  fm.GrpName.value = '<%= GrpName %>';
	  fm.GrpContNo.value = '<%= GrpContNo %>'; 
  }
  catch(ex)
  {
    alert("在LCGrpQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LCGrpQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();  
    initGrpContGrid();
    initQuery();
  }
  catch(re)
  {
    alert("在LCGrpQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpContGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团体客户号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="单位名称";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号码";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="投保人数";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      GrpContGrid = new MulLineEnter( "fm" , "GrpContGrid" ); 
      //这些属性必须在loadMulLine前
      GrpContGrid.mulLineCount = 10;   
      GrpContGrid.displayTitle = 1;
      GrpContGrid.locked = 1;
      GrpContGrid.canSel = 1;
      GrpContGrid.hiddenPlus=1;
      GrpContGrid.hiddenSubtraction=1;
      GrpContGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
