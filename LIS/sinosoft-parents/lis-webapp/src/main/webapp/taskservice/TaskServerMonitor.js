//程序名称：TaskService.js
//程序功能：
//创建日期：2004-12-15 
//创建人  ：ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";

var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass(); 
 var turnPage2 = new turnPageClass(); 
var turnPagePlan = new turnPageClass();  
  var mySql = new SqlClass();
/*********************************************************************
 *  提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	document.getElementById("fm").submit(); //提交
}

function saveServerConfig()
{
	mAction = "ServerConfig";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function saveTaskServerConfig()
{
	var tSelNo = TaskServerPlanGrid.getSelNo();
	
	//alert(tSelNo);
	if(tSelNo<=0)
	{
		alert('请先选择一个服务节点');
		return false;
	}
	
	mAction = "TaskServerConfig";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}


function deactivateOne()
{
	mAction = "DEACTIVATE";
	if (document.all("TaskPlanCode").value == '')
	{
		alert("请选择一条任务计划！");
		return;
	}
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	try{
		showInfo.close();
	}
	catch(e)
	{
		}
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	queryTaskServerPlanGrid();
	//queryTaskInfo();
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}



/*********************************************************************
 *  显示任务计划
 *  参数  ：  
 *  返回值：  无
 *********************************************************************
 */
function queryTaskServerPlanGrid()
{
	initTaskServerPlanGrid();

	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServerMonitorSql");
	mySql.setSqlId("TaskServerMonitorSql1");
	mySql.addSubPara("1");  
	turnPagePlan.queryModal(mySql.getString(),TaskServerPlanGrid);

}

function queryServerGrid()
{
	initServerGrid();

	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServerMonitorSql");
	mySql.setSqlId("TaskServerMonitorSql2");
	mySql.addSubPara("1");  
	turnPage.queryModal(mySql.getString(),ServerGrid);
	

}



/*********************************************************************
 *  选择任务计划后的响应事件
 *  参数  ：  
 *  返回值：  无
 *********************************************************************
 */
function onTaskServerSelected(parm1,parm2)
{
	var tServerIp = TaskServerPlanGrid.getRowColData(TaskServerPlanGrid.getSelNo() - 1, 1);
	var tServerPort = TaskServerPlanGrid.getRowColData(TaskServerPlanGrid.getSelNo() - 1, 2);
	document.all('hiddenServerIP').value=tServerIp;
	document.all('hiddenServerPort').value=tServerPort;
	initTaskServerConfigGrid();
	
 	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServerMonitorSql");
	mySql.setSqlId("TaskServerMonitorSql3");
	mySql.addSubPara(tServerIp);  
	mySql.addSubPara(tServerPort);  
	
  turnPage2.queryModal(mySql.getString(), TaskServerConfigGrid); 

}

function onTaskSelected(parm1,parm2)
{
	document.all("BaseTaskCode").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 1);
	document.all("TaskDescribe").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 2);
	document.all("TaskClass").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 3);
}

function resetForm()
{
	document.all("TaskPlanCode").value = "";
	document.all("TaskCode").value = "";
	document.all("RunFlag").value = "";
	document.all("RecycleType").value = "";
	document.all("StartTime").value = "";
	document.all("EndTime").value = "";
	document.all("Interval").value = "";
	document.all("Times").value = "";

	document.all("BaseTaskCode").value = "";
	document.all("TaskDescribe").value = "";
	document.all("TaskClass").value = "";
}

function refreshTask()
{
	initForm();
}
