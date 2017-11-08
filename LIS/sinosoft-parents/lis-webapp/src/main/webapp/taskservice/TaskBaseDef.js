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

 


function appendTask()
{
	mAction = "INSERTTASK";
	if (document.all("TaskDescribe").value == '' || document.all("TaskClass").value == '')
	{
		alert("请输入任务描述和任务处理类");
		return;
	}
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function deleteTask()
{   
    BaseTaskCode = document.all("BaseTaskCode").value;
    //var strSql = "select * from ldtaskplan where taskcode= '"+BaseTaskCode+"'";
  
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql1");
	mySql.addSubPara(BaseTaskCode);  
    turnPage.strQueryResult = easyQueryVer3(mySql.getString(),1,0,1);
    if(turnPage.strQueryResult)
    {
       alert("请先删除改类的任务计划！");
       return;
    }
	mAction = "DELETETASK";
	if (document.all("BaseTaskCode").value == '')
	{
		alert("请选择一条任务！");
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
		var iHeight=350;     //弹出窗口的高度; 
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
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	//queryTaskPlanInfo();
	queryTaskInfo();
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


function queryTaskInfo()
{
	resetForm();
	initTaskGrid();
	//k = k + 1;
	//var strSQL = "";
	//var strSQL = "select TaskCode, TaskDescribe, TaskClass from LDTask  order by TaskCode ";
	mySql = new SqlClass();
  mySql.setResourceName("taskservice.TaskBaseDefSql");
	mySql.setSqlId("TaskBaseDefSql1");  
	mySql.addSubPara("1");  
	turnPage2.queryModal(mySql.getString(),TaskGrid);
	/*
	turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);//strSQL, 1, 0, 1);//
	//fm.TaskCode.CodeData = turnPage2.strQueryResult;

	if (!turnPage2.strQueryResult) 
	{
	//alert("11111111");
		return;
    }

	//查询成功则拆分字符串，返回二维数组
	turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage2.pageDisplayGrid = TaskGrid;
	//保存SQL语句
	turnPage2.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage2.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage2.arrDataCacheSet, turnPage2.pageDisplayGrid);
	*/
}




function onTaskSelected(parm1,parm2)
{
	document.all("BaseTaskCode").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 1);
	document.all("TaskDescribe").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 2);
	document.all("TaskClass").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 3);
}

function resetForm()
{
	document.all("BaseTaskCode").value = "";
	document.all("TaskDescribe").value = "";
	document.all("TaskClass").value = "";
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