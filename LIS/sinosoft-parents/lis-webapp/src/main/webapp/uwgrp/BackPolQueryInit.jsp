<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：BackPolQueryInit.jsp
//程序功能：撤单申请查询
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

  tProposalNo = request.getParameter("ProposalNo2");
  tFlag = request.getParameter("Flag");


  loggerDebug("BackPolQueryInit","ProposalNo:"+tProposalNo);
  loggerDebug("BackPolQueryInit","Flag:"+tFlag);


%>            
<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tProposalNo,tFlag)
{ 
try
  {                                   
    fm.all('ProposalNo').value = tProposalNo;
    fm.all('IfReply').value = '';
    if (tFlag == "5")
    {
    	fm.all('BackObj').value = '1';
    }
    else
    {
    	fm.all('BackObj').value = '';
    }
    
    fm.all('ManageCom').value = '';
    fm.all('OManageCom').value = '';
    
    if (tFlag == "5")
    {
    	fm.all('OperatePos').value = '5';
    }
    else if(tFlag == "3"||tFlag == "1")
    {
    	fm.all('OperatePos').value = '1';	    
    }
    else if(tFlag == "4")
    {
    	fm.all('OperatePos').value = '1';
    }
    else
    {
    	fm.all('OperatePos').value = '';
    }
    fm.all('Content').value = '';
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
	initBackPolGrid();		
	initHide(tProposalNo);
	easyQueryClick(tProposalNo);	
	

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 责任信息列表的初始化
function initBackPolGrid()
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
      iArray[1][0]="主险投保单号";    	//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="投保单号";         			//列名
      iArray[2][1]="180px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="撤单说明";         			//列名
      iArray[3][1]="180px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                           
      iArray[4]=new Array();
      iArray[4][0]="经办人";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="申请日期";         			//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                  
      BackPolGrid = new MulLineEnter( "fm" , "BackPolGrid" ); 
      //这些属性必须在loadMulLine前                            
      BackPolGrid.mulLineCount = 1;
      BackPolGrid.displayTitle = 1;
      BackPolGrid.canSel = 1;
      BackPolGrid.loadMulLine(iArray);
      
      BackPolGrid. selBoxEventFuncName = "easyQueryChoClick";
      
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tProposalNo)
{
	fm.all('ProposalNoHide').value=tProposalNo;		
}

</script>


