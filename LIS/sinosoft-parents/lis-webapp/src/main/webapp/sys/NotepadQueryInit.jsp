<%
//程序名称：NotepadQueryInit.jsp
//程序功能：记事本查询
//创建日期：2005-09-29 16:10:36
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
   document.all('PrtNo').value= nullToEmpty("<%= tPrtNo %>");	
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
 
//窗体初始化	
function initForm()
{
  try
  {
	
		initQuestGrid();
		initParam();
		LLQuestGrid();

	
  }
  catch(re)
  {
    alert("NotepadQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
} 	
 	
 	
function initQuestGrid() 
{                              
  var iArray = new Array();
    
  try  {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="印刷号";    	//列名
    iArray[1][1]="90px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="记事本内容";         			//列名
    iArray[2][1]="280px";            		//列宽
    iArray[2][2]=400;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="录入位置";         			//列名
    iArray[3][1]="120px";            		//列宽
    iArray[3][2]=40;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                             
    iArray[4]=new Array();
    iArray[4][0]="录入员";         			//列名
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=40;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="录入日期";         			//列名
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=50;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="录入时间";         			//列名
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=50;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //这些属性必须在loadMulLine前                         
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.selBoxEventFuncName = "QuestGridClick"; 
    QuestGrid.loadMulLine(iArray); 
 
 
  }
  catch(ex) {
    alert(ex);
  }
}
</script> 	
 	
 	
 	
 	
 	
 	
 	
 	
