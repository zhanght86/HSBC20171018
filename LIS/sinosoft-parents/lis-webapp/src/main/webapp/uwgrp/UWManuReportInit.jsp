<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuReportInit.jsp
//程序功能：人工核保核保报告录入
//创建日期：
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tFlag = "";
  String tUWIdea = "";
  String str = "";
  String tflag = "";
  tContNo = request.getParameter("ContNo");
  tflag = request.getParameter("flag");
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
	//loggerDebug("UWManuReportInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox(tContNo)
{ 
try
  {                                   
	// 延长日期天数
    fm.all('ContNo').value = tContNo;
    fm.all('Operator').value = '<%= strOperator %>';
    fm.all('Content').value = '';
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo)
{
  var str = "";
  try
  {
	initInpBox(tContNo);
	//initRReportGrid();
	initHide(tContNo);
	initContent();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
// 责任信息列表的初始化
function initRReportGrid()
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
      iArray[1][0]="保险项目";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;
      iArray[1][10] = "DutyCode";             			//是否允许输入,1表示允许，0表示不允许
      iArray[1][11] = str;
      iArray[1][12] = "1|2|3";
      iArray[1][13] = "0|1|2";

      iArray[2]=new Array();
      iArray[2][0]="起始日期";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="终止日期";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="加费金额";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

                           

      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" ); 
      //这些属性必须在loadMulLine前                            
      SpecGrid.mulLineCount = 1;
      SpecGrid.displayTitle = 1;
      SpecGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo)
{
	var flag = '<%=tflag%>';
	
	if (flag == "1")
	{
		divButton.style.display = 'none';
	}
	
	fm.all('ProposalNoHide').value=tContNo;
}

</script>


