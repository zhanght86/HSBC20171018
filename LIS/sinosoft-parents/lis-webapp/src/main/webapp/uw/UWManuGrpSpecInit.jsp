<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuGrpSpecInit.jsp
//程序功能：人工核保条件承保
//创建日期：2007-06-15 11:10:36
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%
  String tGrpContNo = "";
  tGrpContNo = request.getParameter("GrpContNo");
%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {           
        fm.GrpContNo.value="<%=tGrpContNo%>";                        
  }
  catch(ex)
  {
    alert("在UWManuGrpSpecInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}
                                      


function initForm()
{
  try
  {
    initInpBox();
    initUWSpecGrid();
    QueryGrpSpecGrid();
    //getGrpSpec();
  }
  catch(re)
  {
    alert("UWManuGrpSpecInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initUWSpecGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="0px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="集体合同号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="印刷号";         		//列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      


      UWSpecGrid = new MulLineEnter("fm" ,"UWSpecGrid"); 
      //这些属性必须在loadMulLine前
      UWSpecGrid.mulLineCount = 3;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 0;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid. hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getGrpSpec";
     
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


