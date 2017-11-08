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
//var mSwitch = parent.VD.gVSwitch;
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

 

function appendOne()
{   
    //判断日期格式
    var StartTime = document.all("StartTime").value;
    var EndTime = document.all("EndTime").value;
    var t = new Date(StartTime.replace(/\-/g,"/"));
    var w = new Date(EndTime.replace(/\-/g,"/"));
    if(!(StartTime == ""||StartTime == null))
    {
      if(isNaN(t))
      {
         alert("起始日期格式错误！");
         return false;
      }
      else
      {
         if(!(EndTime == ""||EndTime == null))
          {
            if(isNaN(w))
             {
                alert("终止日期格式错误！");
                return false;
             }
           }
      } 
    }
    
	mAction = "INSERT";
	var Interval=0;
	if (document.all("TaskCode").value == '' || document.all("RunFlag").value == '' || document.all("RecycleType").value == '')
	{
		alert("请输入任务编码，是否启动和循环类型信息！");
		return;
	}
	if(document.all("RecycleType").value=='72')
	{
		if(document.all("Interval").value=='')	
		{
			alert("请输入循环间隔的数值!");
			return;
		}else
		{
			Interval=parseInt(document.all("Interval").value);
			if(isNaN(Interval))
			{
				alert("请输入正确的数值!");
				return;	
			}
		}
	}
	document.all("Interval").value=Interval;
	document.all( 'fmAction' ).value = mAction;
    submitForm();
}


function deleteOne()
{
	mAction = "DELETE";
	if (document.all("TaskPlanCode").value == '')
	{
		alert("请选择一条任务计划！");
		return;
	}
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function activateOne()
{
	mAction = "ACTIVATE";
	if (document.all("TaskPlanCode").value == '')
	{
		alert("请选择一条任务计划！");
		return;
	}
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

function startEngine()
{
	mAction = "START";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

function stopEngine()
{
	mAction = "STOP";
	document.all( 'fmAction' ).value = mAction;
	submitForm();
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
	queryTaskPlanInfo();
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
function queryTaskPlanInfo()
{
	resetForm();
	initTaskPlanGrid();
	initMoreParamGrid();
	initParamGrid();
//	var strSQL = "select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.RecycleType, t.StartTime, t.EndTime, t.Interval, t.Times, t.RunState from LDTaskPlan t, LDTask b where t.TaskCode = b.TaskCode and exists("+document.all("RunString").value+" and TaskPlanCode=t.TaskPlanCode) order by t.TaskPlanCode";
//	//mySql = new SqlClass();
//	//mySql.setResourceName("taskservice.TaskServiceSql");
//	//mySql.setSqlId("TaskServiceSql2");
//	//mySql.addSubPara(document.all("RunString").value);  
//	//turnPagePlan.queryModal(mySql.getString(),TaskPlanGrid);
//	
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql20");  
	mySql.addSubPara(document.all("RunString").value); 
	var strSQL = mySql.getString();
	
	turnPagePlan.queryModal(strSQL,TaskPlanGrid);

}


function queryTaskInfo()
{
/*	var strSQL = "select TaskCode, TaskDescribe from LDTask order by TaskCode";
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	alert(turnPage.strQueryResult);

	fm.TaskCode.CodeData = "";
	//判断是否查询成功
	if (turnPage.strQueryResult == "")
	{
	return "";
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	var returnstr = "";
	var n = turnPage.arrDataCacheSet.length;
	if (n > 0)
	{
		for( i = 0;i < n; i++)
		{
			m = turnPage.arrDataCacheSet[i].length;
			if (m > 0)
			{
				for( j = 0; j< m; j++)
				{
					if (i == 0 && j == 0)
					{
						returnstr = "0|^" + turnPage.arrDataCacheSet[i][j];
					}
					if (i == 0 && j > 0)
					{
						returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
					}
					if (i > 0 && j == 0)
					{
						returnstr = returnstr + "^" + turnPage.arrDataCacheSet[i][j];
					}
					if (i > 0 && j > 0)
					{
						returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
					}
					
				}
			}
			else
			{
				return;
			}
		}
	}
	else
	{
	return;
	}

	fm.TaskCode.CodeData = returnstr;
//  alert(returnstr)
*/
	resetForm();
	initTaskGrid();
	k = k + 1;
	var strSQL = "";
	//var strSQL = "select TaskCode, TaskDescribe, TaskClass from LDTask where " + k + "=" + k + " order by TaskCode ";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql3");  
	mySql.addSubPara("");  
	turnPage2.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);//strSQL, 1, 0, 1);//
	fm.TaskCode.CodeData = turnPage2.strQueryResult;

	if (!turnPage2.strQueryResult) 
	{
	alert("11111111");
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
}


/*********************************************************************
 *  选择任务计划后的响应事件
 *  参数  ：  
 *  返回值：  无
 *********************************************************************
 */
function onTaskPlanSelected(parm1,parm2)
{
	document.all("TaskPlanCode").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 1);
	document.all("TaskCode").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 2);
	document.all("RunFlag").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 4);
	document.all("RecycleType").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 5);
	document.all("StartTime").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 6);
	document.all("EndTime").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 7);
	document.all("Interval").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 8);
	document.all("Times").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 9);
	
	//tongmeng 2008-08-07 modify
	initCrontabGrid();
	/*var tSQL_crontab = " select getStringBySymbol(contabconfig,'#',1),getStringBySymbol(contabconfig,'#',2), "
									 + " getStringBySymbol(contabconfig,'#',3),getStringBySymbol(contabconfig,'#',4), "
									 + " getStringBySymbol(contabconfig,'#',5),getStringBySymbol(contabconfig,'#',6) "
                   + " from ldtaskplan where taskplancode='"+document.all("TaskPlanCode").value+"' ";*/
   mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql4");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
   turnPage1.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
    turnPage1.queryModal(mySql.getString(), CrontabGrid); 
    /*if(!turnPage1.strQueryResult)
    {
        return;
    }
	//查询成功则拆分字符串，返回二维数组
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage1.pageDisplayGrid = CrontabGrid;
	//保存SQL语句
	turnPage1.strQuerySql = mySql.getString();//tSQL_crontab;
	//设置查询起始位置
	turnPage1.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage1.arrDataCacheSet, turnPage1.pageDisplayGrid);
*/

	initMoreParamGrid();
	initParamGrid();
	//var strSQL = "select ParamName, ParamValue from LDTaskParam where TaskPlanCode = '" + document.all("TaskPlanCode").value + "' and TaskCode = '" + document.all("TaskCode").value + "' and substr(paramvalue,0,4)='10.0'";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql5");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
	mySql.addSubPara(document.all("TaskCode").value); 
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
	var strSQL = mySql.getString();
    //var strSQL1 = "select ParamName, ParamValue from LDTaskParam where TaskPlanCode = '" + document.all("TaskPlanCode").value + "' and TaskCode = '" + document.all("TaskCode").value + "' and substr(paramvalue,0,4)!='10.0'";
    mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql6");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
	mySql.addSubPara(document.all("TaskCode").value); 
    turnPage1.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
    var strSQL1 =mySql.getString();
    if(!turnPage.strQueryResult)
    {
        return;
    }
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = ParamGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	
	//其他参数信息或添加
	if(!turnPage1.strQueryResult)
	{
	    return;
	}
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
	turnPage1.pageDisplayGrid = MoreParamGrid;
	turnPage1.strQuerySql = strSQL1;
	turnPage1.pageIndex = 0;
//	alert(turnPage1.arrDataCacheSet);
//	alert(turnPage1.pageDisplayGrid);
	displayMultiline(turnPage1.arrDataCacheSet,turnPage1.pageDisplayGrid);
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