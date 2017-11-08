<%
//程序名称：WriteToFileInit.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI"); %>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<script>
 var Type = "<%=request.getParameter("DealType")%>";	

</script>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initForm() {
  try 
  {	
        fm.cManageCom.value = <%= tGI.ManageCom %>;
  	initMoveDataGrid();  	
  	initQuery(); 	
  }
  catch(re) 
  {
    alert("InitForm 函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
var MoveDataGrid;
function initMoveDataGrid() {                               
  var iArray = new Array();
  try {
  
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][2]=10;            			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="批次号";         		//列名
    iArray[1][1]="120px";            	//列宽
    iArray[1][2]=10;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="生成日期";      	   		//列名
    iArray[2][1]="100px";            			//列宽
    iArray[2][2]=10;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="提数区间";      	   		//列名
    iArray[3][1]="130px";            			//列宽
    iArray[3][2]=10;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="操作员";      	   		//列名
    iArray[4][1]="60px";            			//列宽
    iArray[4][2]=10;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    
    MoveDataGrid = new MulLineEnter( "document" , "MoveDataGrid" ); 
    //这些属性必须在loadMulLine前
    MoveDataGrid.mulLineCount = 5;   
    MoveDataGrid.displayTitle = 1;
    MoveDataGrid.hiddenPlus = 1;
    MoveDataGrid.hiddenSubtraction = 1;
    MoveDataGrid.canSel = 1;
    MoveDataGrid.canChk = 1;
    MoveDataGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
  }
 
</script>

	
