<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestQueryInit.jsp
//程序功能：问题件查询
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
<%
  String tProposalNo = "";
  String tFlag = "";

  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");


  loggerDebug("QuestQueryInit","ProposalNo:"+tProposalNo);
  loggerDebug("QuestQueryInit","Flag:"+tFlag);

  String curDate = PubFun.getCurrentDate();



%>            
<script language="JavaScript">

function initContent()
{
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
    document.all('PrintType').value='';
}

function initForm()
{
  try
  {
	initQuestGrid();
  QuestQuery();
  document.all('StartDate').value = '<%=curDate%>';
		document.all('EndDate').value = '<%=curDate%>';
  
	//query();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initQuestGrid()
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
      iArray[1][0]="印刷号";    	//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="问题代码";         			//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="问题内容";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           
      iArray[4]=new Array();
      iArray[4][0]="回复内容";         			//列名
      iArray[4][1]="50px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="录入员";         			//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="录入日期";         			//列名
      iArray[6][1]="70px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="操作位置";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[8]=new Array();
      iArray[8][0]="返回对象";         			//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=40;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[9]=new Array();
      iArray[9][0]="是否下发";         			//列名
      iArray[9][1]="50px";            		//列宽
      iArray[9][2]=40;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[10]=new Array();
      iArray[10][0]="流水号";         			//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=40;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
      
      iArray[11]=new Array();
      iArray[11][0]="下发日期";         			//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=40;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
 
      iArray[12]=new Array();
      iArray[12][0]="回复日期";         			//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=40;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
 
      iArray[13]=new Array();
      iArray[13][0]="是否计入差错";         			//列名
      iArray[13][1]="50px";            		//列宽
      iArray[13][2]=40;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[14]=new Array();
      iArray[14][0]="初审";         			//列名
      iArray[14][1]="80px";            		//列宽
      iArray[14][2]=40;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

 
      QuestGrid = new MulLineEnter( "document" , "QuestGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestGrid.mulLineCount = 5;
      QuestGrid.displayTitle = 1;
      QuestGrid.canSel = 1;
      QuestGrid.hiddenPlus = 1;
      QuestGrid.hiddenSubtraction = 1;
      QuestGrid.loadMulLine(iArray); 
      
   
      
      QuestGrid. selBoxEventFuncName = "queryone";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


