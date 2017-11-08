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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  
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
    document.all('ContNo').value = ContNo; 
    document.all('subtype').value = '';
    document.all('subtypname').value = '';
} 

function initForm() {
  try {

	initAll();
	initImageGrid();  
	QueryImage();
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
      iArray[0][0]="序号";         		  	//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px";            		//列宽
      iArray[0][2]=30;            		  	//列最大值
      iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="单证号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			  //列最大值
      iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

  
      iArray[2]=new Array();
      iArray[2][0]="影像类别";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=200;             		 	//列最大值
      iArray[2][3]=0;              		  	//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="影像名称";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			  //列最大值
      iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="单证编号";         		//列名
      iArray[4][1]="0px";            		  //列宽
      iArray[4][2]=200;            			  //列最大值
      iArray[4][3]=3;              			  //是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="扫描批次号";         	//列名
      iArray[5][1]="100px";            		  //列宽
      iArray[5][2]=200;            			  //列最大值
      iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许 
      
      iArray[6]=new Array();
      iArray[6][0]="页数";         	//列名
      iArray[6][1]="80px";            		  //列宽
      iArray[6][2]=200;            			  //列最大值
      iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="扫描日期";         	//列名
      iArray[7][1]="60px";            		  //列宽
      iArray[7][2]=200;            			  //列最大值
      iArray[7][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="扫描时间";         	//列名
      iArray[8][1]="60px";            		  //列宽
      iArray[8][2]=200;            			  //列最大值
      iArray[8][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="扫描员工号";         	//列名
      iArray[9][1]="80px";            		  //列宽
      iArray[9][2]=200;            			  //列最大值
      iArray[9][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
       
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

  
