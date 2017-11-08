<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuRReportInit.jsp
//程序功能：新契约人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
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
  String tContNo = "";
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  tContNo = request.getParameter("ContNo");  
   tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	
	String strOperator = globalInput.Operator;
	//loggerDebug("UWManuUpReportReplyInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox(tPrtNo)
{ 
try
  {                                   
     document.all('PrtNo').value=tPrtNo;
  }
  catch(ex)
  {
    alert("在UWManuUpReportReplyInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在UWManuUpReportReplyInit-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tPrtNo,tMissionID,tSubMissionID)
{
  var str = "";
  try
  {
 
	initInpBox(tPrtNo);

	initHide(tContNo,tMissionID,tContNo,tSubMissionID);

	initTraceGrid();
	querytrace();
	easyQueryClick();
	
	
	

  }
  catch(re)
  {
    alert("UWManuUpReportReplyInit-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide(tContNo,tMissionID,tContNo,tSubMissionID)
{

	document.all('ProposalNoHide').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('ContNo').value=tContNo;
	document.all('Flag').value=<%=tFlag%>;
	
}


function initTraceGrid()
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
      iArray[1][0]="呈报原因";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="再保结论";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="原因描述";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="再保备注";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[5]=new Array();
      iArray[5][0]="发送时间";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="发送人";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="回复时间";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="回复人";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
           
      TraceGrid = new MulLineEnter( "fm" , "TraceGrid" ); 
      //这些属性必须在loadMulLine前
      TraceGrid.mulLineCount = 1;   
      TraceGrid.displayTitle = 1;
      TraceGrid.locked = 1;
      TraceGrid.canSel = 0;
      TraceGrid.hiddenPlus = 1;
      TraceGrid.hiddenSubtraction = 1;
      TraceGrid.loadMulLine(iArray);     
      
    //  RiskGrid. selBoxEventFuncName = "showRiskResult";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}




</script>


