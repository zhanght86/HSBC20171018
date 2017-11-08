<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//程序名称：UWscoreRReportInit.jsp
//程序功能：核保生调评分
//创建人  ：ln
//创建日期：2008-10-24
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String curDate = PubFun.getCurrentDate();
    String curTime = PubFun.getCurrentTime();
	//loggerDebug("UWscoreRReportInit",curDate);
%>
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {
  	//初始化按钮状态      
    if(tType =="2") 
    {
     // alert(tType);
      fm.Sure.disabled = true;
     // alert(2);
      //fm.Print.disabled = true;
     // alert(1);
    }    
                               
    document.all('ManageCom').value = '';
    document.all('CustomerNo').value = '';
    document.all('Name').value = '';
      
    document.all('SScore').value = "0";
    document.all('SScore1').value = "2";
    document.all('SScore2').value = "20";
    document.all('SScore3').value = "10";
    document.all('SScore4').value = "5";
    document.all('SScore5').value = "2";
    document.all('SScore6').value = "2";
    document.all('SScore7').value = "2";
    document.all('SScore8').value = "";
    document.all('AScore').value = "0";
    document.all('AScore1').value = "10";
    document.all('AScore2').value = "5";
    document.all('AScore3').value = "5";
    document.all('AScore4').value = "5";
    document.all('AScore5').value = "5";
    document.all('AScore6').value = "";
    document.all('Score').value = "100";
    document.all('Conclusion').value = '优秀';
    document.all('AssessOperator').value = operator;
    document.all('AssessDay').value = '<%=curDate%>';
    <%String unitbase = "unit";
    String unit = "";
    for(int i=1; i<15; i++)
    {
    	unit = unitbase + String.valueOf(i);
    //	loggerDebug("UWscoreRReportInit",unit);
    	%>
      document.all('<%=unit%>').value = "分";
      <%
    }
    %>
  }
  catch(ex)
  {
    alert("在UWscoreRReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}   

function initHide()
{
	try
  {
	    document.all('ScoreH').value = '100';
	    document.all('SScoreH').value = '0';
	    document.all('AScoreH').value = '0';	    
	    document.all('AssessTimeH').value = '<%=curTime%>';
	    document.all('ContNoH').value = tContNo;
  }
  catch(re)
  {
    alert("UWscoreRReportInit.jsp-->initHide函数中发生异常:初始化界面错误!");
  }  
}                                   

function initForm()
{
  try
  {
	initInpBox();
	initHide();
	easyQueryClickItem();	
  }
  catch(re)
  {
    alert("UWscoreRReportInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//计算得分
function calcuScore()
{	
    var tScore =0;
 
	  tScore = 100 - parseInt(document.all('SScore').value) + parseInt(document.all('AScore').value);
	  document.all('Score').value = tScore;
	  
	  //评估结论
	  if(tScore >= 90)
	     document.all('Conclusion').value = "优秀";
	  else if(90> tScore && tScore >= 80)
	     document.all('Conclusion').value = "良好";
	  else if(80> tScore && tScore >= 60)
	     document.all('Conclusion').value = "合格";
	  else 
	     document.all('Conclusion').value = "不合格";
} 

//计算扣分
function calcuSScore()
{	
      var tSScore = 0;
	  <%
	  
	  String Subtraction = "";
	  String SScore = "";
	  
	    for(int i=1; i<9; i++)
	    {
	    	Subtraction = "Subtraction" + String.valueOf(i);
	    	SScore = "SScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=Subtraction%>').checked)
	    	   tSScore = tSScore + parseInt(document.all('<%=SScore%>').value) ;  
	    	
	    <%}
	    %>
	    document.all('SScore').value = tSScore;
}

//计算加分
function calcuAScore()
{	   
      var tAScore = 0;	
	  <%
	  
	  String Addition = "";
	  String AScore = "";
	    for(int i=1; i<7; i++)
	    {
	    	Addition = "Addition" + String.valueOf(i);
	    	AScore = "AScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=Addition%>').checked)
	      		tAScore = tAScore + parseInt(document.all('<%=AScore%>').value) ;
	      <%
	      
	    }
	    %>
	    document.all('AScore').value = tAScore;

}

//计算校验用的得分
function calcuScoreH()
{	
    var tScoreH =0;
    var tSScoreH = 0;
    var tAScoreH = 0;

//计算扣分
	  <%
	  String SubtractionH = "";
	  String SScoreH = "";
	    for(int i=1; i<9; i++)
	    {
	    	SubtractionH = "Subtraction" + String.valueOf(i);
	    	SScoreH = "SScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=SubtractionH%>').checked)
	      		tSScoreH = tSScoreH + parseInt(document.all('<%=SScoreH%>').value) ;
	      <%
	    }
	    %>
	    document.all('SScoreH').value = tSScoreH;
//计算加分
      <%
      String AdditionH = "";
	  String AScoreH = "";
	    for(int i=1; i<7; i++)
	    {
	    	AdditionH = "Addition" + String.valueOf(i);
	    	AScoreH = "AScore" + String.valueOf(i);
	    	%>
	    	if(document.all('<%=AdditionH%>').checked)
	     		 tAScoreH = tAScoreH + parseInt(document.all('<%=AScoreH%>').value) ;
	      <%
	    }
	    %>
	    document.all('AScoreH').value = tAScoreH;
	    
//计算得分
		  tScoreH = 100 - parseInt(document.all('SScoreH').value) + parseInt(document.all('AScoreH').value);
		  document.all('ScoreH').value = tScoreH;
} 

</script>


