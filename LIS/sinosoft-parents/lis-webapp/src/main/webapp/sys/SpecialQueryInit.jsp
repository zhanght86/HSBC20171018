<%
//程序名称：SpecialQueryInit.jsp
//程序功能：特约查询
//创建日期：2005-09-29 11:10:36
//创建人  ：wuhao2
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
	
		initSpecGrid();
		initParam();
		LLSpecGrid();
	
  }
  catch(re)
  {
    alert("SpecialQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


// 特约信息列表的初始化
function initSpecGrid()
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
      iArray[1][0]="险种";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保人";         		//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许         
      
      iArray[5]=new Array();
      iArray[5][0]="特约内容";         		//列名
      iArray[5][1]="220px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 0;   
      SpecGrid.displayTitle = 1;
      SpecGrid.locked = 1;
      SpecGrid.canSel = 0;
      SpecGrid.hiddenPlus=1;
      SpecGrid.hiddenSubtraction=1;
      SpecGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>   
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
                 
