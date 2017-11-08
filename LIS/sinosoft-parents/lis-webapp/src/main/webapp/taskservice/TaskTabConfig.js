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
	queryLDUserGridInfo();
	initUserTabDetailGrid();
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


function queryLDUserGridInfo()
{
	//resetForm();
	initLDUserGrid();
	//k = k + 1;
	//var strSQL = "";
	//var strSQL = "select usercode,username from lduser where userstate='0' ";
	var tUserCode = document.all('UserCode').value;
	var tUserName = document.all('UserName').value;
	//if(tUserCode!=null&&tUserCode!='')
	//{
	//	strSQL = strSQL + " and usercode='"+tUserCode+"' ";
	//}
	//if(tUserName!=null&&tUserName!='')
	//{
	//	strSQL = strSQL + " and username='"+tUserName+"' ";
	//}
	
	
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskTabConfigSql");
	mySql.setSqlId("TaskTabConfigSql2");
	mySql.addSubPara(tUserCode);  
	mySql.addSubPara(tUserName);  
	turnPage2.queryModal(mySql.getString(),LDUserGrid);
	
}

function onLDUserSelected(parm1,parm2)
{
	document.all("UserCode").value = LDUserGrid.getRowColData(LDUserGrid.getSelNo() - 1, 1);
	document.all("UserName").value = LDUserGrid.getRowColData(LDUserGrid.getSelNo() - 1, 2);
	
	initUserTabDetailGrid();
	//var strSQL = " select tasktabid,(select tasktabname from LDTaskTabConfig where tasktabid=a.tasktabid) from LDUSERToTaskTab a where 1=1 "
	//           + " and userid='"+document.all("UserCode").value+"' ";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskTabConfigSql");
	mySql.setSqlId("TaskTabConfigSql3");
	mySql.addSubPara(document.all("UserCode").value);  
	
	turnPage1.queryModal(mySql.getString(),UserTabDetailGrid);
	

}

function resetForm()
{
	document.all("UserCode").value = "";
	document.all("UserName").value = "";
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


function appendTaskTabDetail()
{
	
	if(LDUserGrid.getSelNo()<=0)
	{
		alert('请先选择需要配置的用户');
		return false;
	}
	UserTabDetailGrid.delBlankLine();
	var rowNum=UserTabDetailGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('没有为用户'+document.all('USerCode').value+'配置明细,无需保存!');
		return false;
	}
	mAction = "INSERTUSERTAB";
	document.all('fmAction').value = "INSERTUSERTAB";	
	submitForm();
}

function deleteTaskTabDetail()
{   
	mAction = "DELETEUSERTAB";
	if(LDUserGrid.getSelNo()<=0)
	{
		alert('请先选择需要配置的用户!');
		return false;
	}
	
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}