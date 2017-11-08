<%
//程序名称：PolQueryInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
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
  String tContNo = "";
 
  String tMissionID = "";
  String tSubMissionID = "";
  String tCustomerNo = "";
  String tPrtSeq = "";
 
 
  tContNo = request.getParameter("ContNo");  
  tCustomerNo =request.getParameter("CustomerNo");
  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtSeq = request.getParameter("PrtSeq");
 
  
%>                            

 <SCRIPT src="../common/javascript/Common.js"></SCRIPT>                  
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("在PolQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在PolQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq)
{

  try
  {

    initInpBox();
    initInvestigateGrid();
    initQuestionTypeGrid();
    initSelBox();    
    initHidden(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq);  
    returnParent();
    easyQueryClick();
   // initLWMission();
  
  
  }
  catch(re)
  {
    alert("PolQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!"+re.message);
  }
}
function initHidden(tContNo,tMissionID,tSubMissionID,tCustomerNo,tPrtSeq)
{
	fm.ContNo.value = tContNo;
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;

	fm.CustomerNo.value = tCustomerNo;
	fm.PrtSeq.value = tPrtSeq;
	


}

function initInvestigateGrid()
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
      iArray[1][0]="生调项目编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "RReportCode1";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="生调项目名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="备注";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      InvestigateGrid = new MulLineEnter( "fm" , "InvestigateGrid" ); 
      //这些属性必须在loadMulLine前                            
      InvestigateGrid.mulLineCount = 0;
      InvestigateGrid.displayTitle = 1;
      InvestigateGrid.canChk = 0;
      InvestigateGrid.hiddenPlus = 1;
      InvestigateGrid.hiddenSubtraction = 1;
      InvestigateGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initQuestionTypeGrid()
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
      iArray[1][0]="问卷类型编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "rreporttype";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="问卷类型名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      QuestionTypeGrid = new MulLineEnter( "fm" , "QuestionTypeGrid" ); 
      //这些属性必须在loadMulLine前                            
      QuestionTypeGrid.mulLineCount = 0;
      QuestionTypeGrid.displayTitle = 1;
      QuestionTypeGrid.canChk = 0;
      QuestionTypeGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>

      
