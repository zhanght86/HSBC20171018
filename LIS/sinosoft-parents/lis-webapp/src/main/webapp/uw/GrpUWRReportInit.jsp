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
	 
	  String tFlag = "";
	  tContNo = request.getParameter("ContNo2");  
	   tPrtNo = request.getParameter("PrtNo");  
	
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
	//loggerDebug("GrpUWRReportInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox(tContNo,tPrtNo)
{ 
try
  {                                   
	
    document.all('ContNo').value = tContNo;
   
    document.all('PrtNo').value = tPrtNo;
   
  
    document.all('Operator').value = '<%= strOperator %>';
  }
  catch(ex)
  {
    alert("在PEdorUWManuRReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在PEdorUWManuRReportInit-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tPrtNo,tMissionID,tSubMissionID)
{
  var str = "";
  try
  {
	initInpBox(tContNo,tPrtNo);
 
	initHide(tContNo,tMissionID,tPrtNo,tSubMissionID);
	initInvestigateGrid();
	//easyQueryClick();
  }
  catch(re)
  {
    alert("PEdorUWManuRReportInit-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide(tContNo,tMissionID,tPrtNo,tSubMissionID)
{

	document.all('ProposalNoHide').value=tContNo;
	
	document.all('PrtNo').value=tPrtNo;
	document.all('Flag').value=<%=tFlag%>;
	
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
      iArray[1][0]="契调项目编号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4] = "RReportCode1";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][5]="1|2";     //引用代码对应第几列，'|'为分割符
      iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值
      iArray[1][18] = 500;

      iArray[2]=new Array();
      iArray[2][0]="契调项目名称";         			//列名
      iArray[2][1]="120px";            		//列宽
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


