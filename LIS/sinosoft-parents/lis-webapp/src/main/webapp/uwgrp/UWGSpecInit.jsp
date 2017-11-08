<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecInit.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%
  String tProposalNo = "";
  String tFlag = "";
  String tUWIdea = "";
  String str = "";
  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");
  tUWIdea = request.getParameter("UWIdea");
  loggerDebug("UWGSpecInit","Flag:"+tFlag);
  loggerDebug("UWGSpecInit","UWIdea:"+tUWIdea);
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	// 延长日期天数
    //fm.all('Prem').value = '';
    //fm.all('SumPrem').value = '';
    //fm.all('Mult').value = '';
    //fm.all('RiskAmnt').value = '';
    fm.all('Remark').value = '';
    fm.all('Reason').value = '';
    fm.all('SpecReason').value = '';
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

function initForm(tProposalNo,tFlag,tUWIdea)
{
  var str = "";
  try
  {
	initInpBox();
	str = initlist(tProposalNo);
	initUWSpecGrid(str);
	initHide(tProposalNo,tFlag,tUWIdea);
	QueryGrid(tProposalNo);		
	QueryAddReason(tProposalNo);
	
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
// 责任信息列表的初始化
function initUWSpecGrid(str)
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
      iArray[1][12] = "1|3|4";
      iArray[1][13] = "0|1|2";
      iArray[1][19] = "1";

      iArray[2]=new Array();
      iArray[2][0]="保费项目";    	//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=2;
      iArray[2][10] = "PlanPay";             			//是否允许输入,1表示允许，0表示不允许
      iArray[2][11] = "0|^1|健康加费^2|职业加费";
      iArray[2][12] = "2";
      iArray[2][13] = "0";
             
      iArray[3]=new Array();
      iArray[3][0]="起始日期";         			//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="终止日期";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="加费金额";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许


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

function initHide(tProposalNo,tFlag,tUWIdea)
{
	fm.all('ProposalNoHide').value=tProposalNo;
	fm.all('Flag').value=tFlag;
	fm.all('UWIdea').value= tUWIdea;
	//alert("pol:"+tProposalNo);
}

</script>


