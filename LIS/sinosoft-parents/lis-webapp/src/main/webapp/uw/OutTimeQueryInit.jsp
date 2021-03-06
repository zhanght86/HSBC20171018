<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：OutTimeQueryInit.jsp
//程序功能：催办超时查询
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
<%
  String tProposalNo = "";
  String tFlag = "";

  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");


  loggerDebug("OutTimeQueryInit","ProposalNo:"+tProposalNo);
  loggerDebug("OutTimeQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tProposalNo)
{ 
try
  {                                   
    document.all('ProposalNo').value = tProposalNo;
  }
  catch(ex)
  {
    alert("在RReportQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

function initForm(tProposalNo)
{
  try
  {	
  	initInpBox(tProposalNo);
	initPolGrid();		
	initHide(tProposalNo);
	easyQueryClick();	
	

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initPolGrid()
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
      iArray[1][0]="投保单号";    	//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="催办类型";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10] = "UrgeNoticeKind";
      iArray[2][11] = "0|^10|首期交费通知书^11|体检通知书^12|核保通知书^15|缴费催办通知书(签单处发放)";
      iArray[2][12] = "2";
      iArray[2][19] = "0";

      iArray[3]=new Array();
      iArray[3][0]="回复状态";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "ReplyState2";
      iArray[3][11] = "0|^0|未打印^1|已打印^2|已回复";
      iArray[3][12] = "3";
      iArray[3][19] = "0";
      
      
      iArray[4]=new Array();
      iArray[4][0]="催办日期";         			//列名
      iArray[4][1]="180px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           
      iArray[5]=new Array();
      iArray[5][0]="失效日期";         			//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="当前日期";         			//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                  
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前                            
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.canSel = 0;
      PolGrid.loadMulLine(iArray);      
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo)
{
	document.all('ProposalNoHide').value=tProposalNo;		
}

</script>


