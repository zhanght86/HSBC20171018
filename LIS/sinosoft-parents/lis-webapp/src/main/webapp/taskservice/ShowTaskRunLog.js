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

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //提交
}

 


function appendTaskGroup()
{
	mAction = "INSERTTASKGROUP";
	if (document.all("TaskGroupDescribe").value == '')
	{
		alert("请输入任务队列描述!");
		return;
	}
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function deleteTask()
{   
	mAction = "DELETETASKGROUP";
	if (document.all("TaskGroupCode").value == '')
	{
		alert("请选择一条任务队列!");
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
	showInfo.close();
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
	//queryTaskPlanInfo();
	queryTaskGroupInfo();
	initTaskGroupDetailGrid();
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


function queryTaskRunLogTodayGrid()
{
	//resetForm();
	//initTaskRunLogTodayGrid();
	//k = k + 1;
	//var strSQL = "";
/*	var strSQL = "select a.taskplancode,a.taskgroupcode,a.taskcode,a.executedate,a.executetime,a.finishdate,a.finishtime,"
	           + " a.executestate,a.executeresult,a.serverinfo from ldtaskrunlog a where taskplancode='"+jTaskPlanCode+"' "
             + " and a.executedate=to_char(sysdate,'yyyy-mm-dd') "
             + " order by a.serialno";
	turnPage.queryModal(strSQL,TaskRunLogTodayGrid);
*/
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql18");  
	mySql.addSubPara(jTaskPlanCode);	
	turnPage.queryModal(mySql.getString(),TaskRunLogTodayGrid);
}

function queryTaskRunLogBefore()
{
	//resetForm();
	var tStartDate = document.all('RunLogDateStart').value;
	var tEndDate = document.all('RunLogDateEnd').value;
	
	if(tStartDate==null||tStartDate==''
	   ||tEndDate==null||tEndDate=='')
	  {
	  	alert('请输入\'日志起期\'和\'日志止期\'后再查询');
	  	return false;
	  }
	initTaskRunLogBeforeGrid();
	
	//k = k + 1;
	//var strSQL = "";
/*	var strSQL = "select a.taskplancode,a.taskgroupcode,a.taskcode,a.executedate,a.executetime,a.finishdate,a.finishtime,"
	           + " a.executestate,a.executeresult,a.serverinfo from ldtaskrunlog a where taskplancode='"+jTaskPlanCode+"' "
             + " and a.executedate>='"+tStartDate+"' "
             + " and a.executedate<='"+tEndDate+"' "
             + " order by a.serialno";            
 	turnPage1.queryModal(strSQL,TaskRunLogBeforeGrid);
*/
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql19");  
	mySql.addSubPara(jTaskPlanCode);  
	mySql.addSubPara(tStartDate);
	mySql.addSubPara(tEndDate);	
	turnPage1.queryModal(mySql.getString(),TaskRunLogBeforeGrid);
}


function resetForm()
{
	document.all("TaskGroupCode").value = "";
	document.all("TaskGroupDescribe").value = "";
	//document.all("TaskClass").value = "";
}

function refreshTask()
{
	initForm();
}

function taskPlanManage()
{
	divTaskPlan.style.display = "";
	divTaskPlanButton.style.display = "";
	divTask.style.display = "none";
	divTaskButton.style.display = "none";
}

function taskManage()
{
	divTaskPlan.style.display = "none";
	divTaskPlanButton.style.display = "none";
	divTask.style.display = "";
	divTaskButton.style.display = "";
	queryTaskInfo();
}


function appendTaskGroupDetail()
{
	
	if(TaskGroupGrid.getSelNo()<=0)
	{
		alert('请先选择需要配置的任务队列');
		return false;
	}
	TaskGroupDetailGrid.delBlankLine();
	var rowNum=TaskGroupDetailGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('没有为任务队列'+document.all('TaskGroupCode').value+'配置任务明细,无需保存!');
		return false;
	}
	mAction = "INSERTTASKGROUPDETAIL";
	document.all('fmAction').value = "INSERTTASKGROUPDETAIL";	
	submitForm();
}

function deleteTaskGroupDetail()
{   
	mAction = "DELETETASKGROUPDETAIL";
	if(TaskGroupGrid.getSelNo()<=0)
	{
		alert('请先选择任务队列!');
		return false;
	}
	
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}