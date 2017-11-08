<%
//程序名称：ProposalApproveInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('ProposalNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
  }
  catch(ex)
  {
    alert("在ProposalApproveInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ProposalApproveInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
	initInpBox();  
	initSelBox();       
	initPolGrid();
	initSelfPolGrid();	
}

// 保单信息列表的初始化
function initPolGrid()
  {                               
    
    bg = new CGrid("document", "PolGrid", turnPage1);
  	bg.setActivityId("0000001001");
  	bg.setProcessId("0000000003");
  	bg.addColumn("ContNo,PrtNo,AppntName,Operator,MakeDate");
  	bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	bg.addColumn("ManageCom,AgentCode");
  	bg.addParam("ManageCom","86",'like');
  	bg.setDefaultOperator(null);
  	bg.initGrid();
  	PolGrid = bg.grid;
}

// 个人保单信息列表的初始化
function initSelfPolGrid()
{                               
	cg = new CGrid("document", "SelfPolGrid", turnPage2);
	cg.setActivityId("0000001001");
	cg.setProcessId("0000000003");
	cg.addColumn("ContNo,PrtNo,AppntName,Operator,MakeDate");
	cg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
	cg.addColumn("ManageCom,AgentCode");
	cg.setRadioFunction("InitshowApproveDetail");
	cg.showEmergency(1, 1, 1);
	cg.initGrid();
	cg.queryData();
	SelfPolGrid = cg.grid;
}


</script>
