<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
   String tGrpContNo="";
   tGrpContNo=request.getParameter("GrpContNo");
%>                            
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="UWReason.js"></SCRIPT>
<script language="JavaScript">


// 下拉框的初始化
function initForm(tGrpContNo)
{ 
  try
  {
  fm.GrpContNo.value=tGrpContNo;
  initUWReasonGrid();
	}
  catch(re)
  {
    alert("UWReasonInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 自核不通过原因框的初始化
function initUWReasonGrid()
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
      iArray[1][0]="个人合同号";    	//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="个人客户号";    	//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="个人客户姓名";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="险种编码";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="险种名称";         			//列名
      iArray[5][1]="180px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="核保顺序号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="核保意见";         		//列名
      iArray[7][1]="300px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="核保日期";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		  UWReasonGrid = new MulLineEnter( "fm" , "UWReasonGrid" );
		  //这些属性必须在loadMulLine前
		  UWReasonGrid.mulLineCount = 0;
		  UWReasonGrid.displayTitle = 1;
		  UWReasonGrid.hiddenPlus = 1;
		  UWReasonGrid.hiddenSubtraction = 1;
	  	//UWReasonGrid.canSel=1; //是否显示多选框
		  UWReasonGrid.loadMulLine(iArray);
      }
      
      catch(ex)
      {
        alert(ex);
      }
}


</script>


