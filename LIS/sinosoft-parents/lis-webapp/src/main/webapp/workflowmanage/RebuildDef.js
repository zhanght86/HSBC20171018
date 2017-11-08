//               该文件中包含客户端需要处理的函数和事件
/*******************************************************************************
 * <p>Title       : 任务分配</p>
 * <p>Description : 用来将系统中已经申请的任务分配给其它员工</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


//******************************************************************


function query()
{
	var arr=new Array();
	var strSQL = "";
 		
  mySql = new SqlClass();
  mySql.setResourceName("workflow.RebuildDefSql");
  mySql.setSqlId("RebuildDefSql1");
  mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value);   	  
  mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value);   
  
  initWorkFlowGrid();
	turnPage.queryModal(mySql.getString(), WorkFlowGrid);
}


var dialogURL="";

function rebuildClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要重构的流程！");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);
  //jiyongtian 2012-7-12
  var version =WorkFlowGrid.getRowColData(selno,7);

//  dialogURL="../wfpic/workflow.jsp?action=rebuild&flowId="+processId+"&Version="+version;  
//	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	 dialogURL="../workflow/workflow.jsp?action=rebuild&flowId="+processId+"&Version="+version;  
	 window.open(dialogURL);
}

function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("请先选择一个业务类型")
		return false;
	}
}