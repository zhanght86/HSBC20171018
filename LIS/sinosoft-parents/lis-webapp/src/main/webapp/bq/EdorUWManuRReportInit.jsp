<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuRReportInit.jsp
//程序功能：保全人工核保生存调查报告录入
//创建日期：2005-07-14 18:10:36
//创建人  ：liurx
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
  String tEdorNo = "";
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  tContNo = request.getParameter("ContNo");  
  tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tEdorNo = request.getParameter("EdorNo");
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
	//loggerDebug("EdorUWManuRReportInit","1:"+strOperator);
%>

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tContNo,tEdorNo)
{ 
try
  {                                   
    document.all('ContNo').value = tContNo;
    document.all('EdorNo').value = tEdorNo;
    document.all('Operator').value = '<%= strOperator %>';
    document.all('RReportReason').value = "";
    document.all('InsureNo').value = "";
    document.all('InsureNoname').value = "";
    document.all('RReportReasonname').value = "";
    document.all('Contente').value = "";
  }
  catch(ex)
  {
    alert("在EdorUWManuRReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在EdorUWManuRReportInit-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tEdorNo,tPrtNo,tMissionID,tSubMissionID)
{
  try
  { //alert(83);
	initInpBox(tContNo,tEdorNo);
	initHide(tMissionID,tPrtNo,tSubMissionID);
	initInvestigateGrid();
	
	easyQueryClickSingle();
    initInsureNo(tContNo,tEdorNo);

  }
  catch(re)
  {
    alert("EdorUWManuRReportInit-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide(tMissionID,tPrtNo,tSubMissionID)
{

	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('PrtNo').value=tPrtNo;

	
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
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      
      InvestigateGrid = new MulLineEnter( "fm" , "InvestigateGrid" ); 
      //这些属性必须在loadMulLine前                            
      InvestigateGrid.mulLineCount = 0;
      InvestigateGrid.displayTitle = 1;
      InvestigateGrid.canChk = 0;
      InvestigateGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>


