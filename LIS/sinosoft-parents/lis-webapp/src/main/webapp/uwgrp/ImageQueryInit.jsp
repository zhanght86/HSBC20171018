<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ImageQueryInit.jsp
//程序功能：影像资料查询
//创建日期：2005-07-07 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            
          
<script language="JavaScript">
	
function initAll() {
    fm.all('ContNo').value = ContNo; 
    fm.all('subtype').value = '';
    fm.all('subtypname').value = '';
} 

function initForm() {
  try {

	initAll();
	initImageGrid();  
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//被保人信息初始化
function initImageGrid()
  {                               
    var iArray = new Array();
       
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务号码";         		//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[2]=new Array();
      iArray[2][0]="影像类别";         		//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=200;             			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="影像名称";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="单证编号";         		//列名
      iArray[4][1]="250px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
       
      ImageGrid = new MulLineEnter( "fm" , "ImageGrid" );  
      //这些属性必须在loadMulLine前
      ImageGrid.mulLineCount = 5;   
      ImageGrid.displayTitle = 1;
      ImageGrid.locked = 1; 
      ImageGrid.canSel = 1;
      ImageGrid.hiddenPlus = 1;
      ImageGrid.hiddenSubtraction = 1;
      ImageGrid.loadMulLine(iArray);       
      ImageGrid.selBoxEventFuncName = "showImage";  
      //这些操作必须在loadMulLine后面
      //ImageGrid.setRowColData(1,1,"asdf");  
      } 
      catch(ex)
      {
        alert(ex);
      }
} 

</script>

  
