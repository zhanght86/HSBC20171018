<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ScoreRReportQueryInit.jsp
//程序功能：生调评分汇总
//创建日期：2008-10-27 11:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String curDate = PubFun.getCurrentDate();
	//loggerDebug("ScoreRReportQueryInit",curDate);
%> 
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {       
    fm.Button1.disabled = true; 
    fm.Button2.disabled = true;  
    fm.Button3.disabled = true;
                                
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AssessOperator').value = '';  
    document.all('CustomerNo').value = '';  
    document.all('Name').value = '';
    document.all('StartDate').value = '<%=curDate%>';
    document.all('EndDate').value = '<%=curDate%>';
 
  }
  catch(ex)
  {
    alert("ScoreRReportQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
} 

// 隐藏输入框的初始化
function initHide()
{ 
try
  {                                   
    document.all('ContNoH').value = '';   
    document.all('PrtNoH').value = '';
  }
  catch(ex)
  {
    alert("ScoreRReportQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}                                     

function initForm()
{
  try
  {
    
	initInpBox();
	initHide();	
	initScoreRReportGrid();
		
  }
  catch(re)
  {
    alert("ScoreRReportQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//查询同一被保险人未承保投保单
function initScoreRReportGrid()
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
      iArray[1][0]="合同号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="0px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="印刷号";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=130;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="管理机构";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="生调员姓名";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="生调员工号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="核保师工号";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="得分";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
             
      
      ScoreRReportGrid = new MulLineEnter( "fm" , "ScoreRReportGrid" ); 
      //这些属性必须在loadMulLine前                            
      ScoreRReportGrid.mulLineCount = 0;   
      ScoreRReportGrid.displayTitle = 1;
      ScoreRReportGrid.locked = 1;
      ScoreRReportGrid.canSel = 1;
      ScoreRReportGrid.hiddenPlus = 1;
      ScoreRReportGrid.hiddenSubtraction = 1;
      ScoreRReportGrid.loadMulLine(iArray);  
      ScoreRReportGrid.selBoxEventFuncName = "initButtonQuery";

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>


