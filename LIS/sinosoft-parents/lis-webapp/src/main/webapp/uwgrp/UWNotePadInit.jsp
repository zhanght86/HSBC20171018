<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWNotePadInit.jsp
//程序功能：记事本查询
//创建日期：2002-09-24 11:10:36
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
           
<script language="JavaScript">
function initContent() {
    fm.all('Content').value = '';
}

function initForm() {
  try {
	initQuestGrid();
  
	queryClick();
  }
  catch(re) {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initQuestGrid() {                              
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
  
    if(OperatePos == 1)
    {
    iArray[2]=new Array();
    iArray[2][0]="投保单号";         			//列名
    iArray[2][1]="120px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }
    if(OperatePos == 3)
    {
    iArray[2]=new Array();
    iArray[2][0]="批单号";         			//列名
    iArray[2][1]="120px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }

    iArray[3]=new Array();
    iArray[3][0]="记事本内容";         			//列名
    iArray[3][1]="370px";            		//列宽
    iArray[3][2]=400;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                         
    iArray[4]=new Array();
    iArray[4][0]="录入员";         			//列名
    iArray[4][1]="40px";            		//列宽
    iArray[4][2]=40;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="录入日期";         			//列名
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=50;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="录入时间";         			//列名
    iArray[6][1]="50px";            		//列宽
    iArray[6][2]=50;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    QuestGrid = new MulLineEnter( "fm" , "QuestGrid" ); 
    //这些属性必须在loadMulLine前                            
    QuestGrid.mulLineCount = 0;
    QuestGrid.displayTitle = 1;
    QuestGrid.canSel = 1;
    QuestGrid.hiddenPlus = 1;
    QuestGrid.hiddenSubtraction = 1;
    QuestGrid.loadMulLine(iArray); 
    
    QuestGrid.selBoxEventFuncName = "showOne";   
  }
  catch(ex) {
    alert(ex);
  }
}

</script>


