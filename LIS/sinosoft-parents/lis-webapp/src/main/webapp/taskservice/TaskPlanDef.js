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
var turnPageItem = new turnPageClass();   
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

function refreshShowTask()
{
/*	var tSQL = " select taskcode,'任务:'||taskdescribe from ldtask "
				   + " union "
				   + " select taskgroupcode,'队列:'||taskdescribe from ldtaskgroup "
*/
 	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql11");  
	mySql.addSubPara("任务"); 
	mySql.addSubPara("队列"); 
	var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
//	var ttemp = (easyQueryVer3(tSQL, 1, 0, 1));
//	alert(ttemp);
	document.all('TaskCode').CodeData = ttemp;
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
	//增加邮箱非空的校验
	if($('#Msg_Type').attr("checked")==true)
	{
		var tValue = $('#MailAddress').val();
		//alert(tValue);
		if(tValue==null||tValue=='')
		{
			alert('请输入邮箱地址!');
			return false;
		}
	}
	
	
	if(!transPlanMode())
	{
		return false;
	}
	var Interval=0;
	if (document.all("TaskCode").value == '' || document.all("RunFlag").value == '')
	{
		alert("请输入 \'任务编码\'、\'是否启动\' !");
		return;
	}
	/*if(document.all("RecycleType").value=='72')
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
	}*/
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
	initParamGrid();
	initMoreParamGrid();
	initCrontabGrid();
	initSimpleTable();
	initMailMsg();
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

var tempTaskPlanSQLStr = "";//此处js不按顺序执行，导致错误，使用setTimeout来调整顺序，需要全局变量
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
/*	var strSQL = "select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.RecycleType, t.StartTime, t.EndTime, t.Interval, t.Times, t.RunState,nvl(planmodeflag,0),nvl(t.actionafterfail,'00') from LDTaskPlan t, LDTask b where t.TaskCode = b.TaskCode and exists(select * from LDTaskPlan a where TaskPlanCode <> '000000' and TaskPlanCode=t.TaskPlanCode) "
	           + " union "
	           + " select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.RecycleType, t.StartTime, t.EndTime, t.Interval, t.Times, t.RunState,nvl(planmodeflag,0),nvl(t.actionafterfail,'00') from LDTaskPlan t, LDTaskGroup b where t.TaskCode = b.TaskGroupCode and exists(select * from LDTaskPlan a where TaskPlanCode <> '000000'and TaskPlanCode=t.TaskPlanCode)  ";
*/
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql2");
	mySql.addSubPara("");
 	tempTaskPlanSQLStr = mySql.getString();
 	setTimeout("turnPagePlan.queryModal(tempTaskPlanSQLStr,TaskPlanGrid);",10);
//	turnPagePlan.queryModal(strSQL,TaskPlanGrid);
	
	

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
	document.all("RunAfterFlag").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 12);
	//document.all("Interval").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 8);
	//document.all("Times").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 9);
	
	//tongmeng 2008-08-07 modify
	initCrontabGrid();
/*	var tSQL_crontab = " select getStringBySymbol(contabconfig,'#',1),getStringBySymbol(contabconfig,'#',2), "
									 + " getStringBySymbol(contabconfig,'#',3),getStringBySymbol(contabconfig,'#',4), "
									 + " getStringBySymbol(contabconfig,'#',5),getStringBySymbol(contabconfig,'#',6) "
                   + " from ldtaskplan where taskplancode='"+document.all("TaskPlanCode").value+"' ";
*/ 
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql4");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
 // turnPage1.strQueryResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
  turnPage1.queryModal(mySql.getString(), CrontabGrid); 

  document.all("PlanModeFlag").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 11);
  $("input[@name=PlanModeFlag]][value="+document.all("PlanModeFlag").value+"]").attr("checked","checked");
	showPlanMode(document.all("PlanModeFlag").value);
	initSimpleTable();
	transCronStr(document.all("RecycleType").value);
	initMoreParamGrid();
	initParamGrid();
//	var strSQL = "select ParamName, ParamValue from LDTaskParam where TaskPlanCode = '" + document.all("TaskPlanCode").value + "' and TaskCode = '" + document.all("TaskCode").value + "' and paramname not in ('serverip:port','mailto')";
	
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql5");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
	mySql.addSubPara(document.all("TaskCode").value); 
	turnPage.queryModal(mySql.getString(), MoreParamGrid); 
	//turnPage.queryModal(strSQL, MoreParamGrid); 
	//turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
//	var strSQL = mySql.getString();
//  var strSQL1 = "select ParamName, ParamValue from LDTaskParam where TaskPlanCode = '" + document.all("TaskPlanCode").value + "' and TaskCode = '" + document.all("TaskCode").value + "' and paramname='serverip:port'";
    mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql6");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
	mySql.addSubPara(document.all("TaskCode").value); 
	turnPage1.queryModal(mySql.getString(), ParamGrid); 
//	turnPage1.queryModal(strSQL1, ParamGrid); 
 
 
 	//增加提醒信息的处理
	initMailMsg();
// 	var tSQL = "select ParamValue from LDTaskParam where TaskPlanCode = '" + document.all("TaskPlanCode").value + "' and TaskCode = '" + document.all("TaskCode").value + "' and paramname='mailto' ";
 	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql8");  
	mySql.addSubPara(document.all("TaskPlanCode").value); 
	mySql.addSubPara(document.all("TaskCode").value); 
 	var tMailAddress = easyExecSql(mySql.getString());
// 	var tMailAddress = easyExecSql(tSQL);
 	 	
 	if(tMailAddress!=null&&tMailAddress!='')
 	{
 		initLogItemGrid();
 		$('#Msg_Type').attr("checked",true);
		$('#MailAddress').val(tMailAddress);
		 chooseMail();
 	}
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
	document.all("CrontabStr").value = "";
	//document.all("Times").value = "";

	//document.all("BaseTaskCode").value = "";
	//document.all("TaskDescribe").value = "";
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

function showPlanMode(tMode)
{
	if(tMode=='0')
	{
		divSimpleMode.style.display = "";
		divProMode.style.display = "none";
	}
	else
	{
		divSimpleMode.style.display = "none";
		divProMode.style.display = "";
	}
}

/*
  转换简易模式和专家模式的函数
         0 - 将简易模式转换成专家模式的数据
         1 - 直接获取专家模式
*/
function transPlanMode()
{
	var tPlanMode =  $("input[name='PlanModeFlag']:checked").val();
	//alert(tPlanMode);
	var tCrontModeStr = "";
	//循环间隔
	var tIntv = "1";
	if(tPlanMode=='0')
	{
		//将简易模式转成专家模式
		//获取运行间隔
		var tRecycleType =  $("input[name='SimpleModeCho']:checked").val();
		//给隐藏的运行单位赋值
		$("#RecycleType").val(tRecycleType);

		if(tRecycleType=='11')
		{
			//按日循环
			//*/间隔 * * * * 
			var tTempVal = $("#intervalMin").val();     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalMin").val(tIntv);          
			}
			
			tCrontModeStr = "*/"+tIntv+"#*#*#*#*#*";
		
		}
		else if(tRecycleType=='21')
		{
			//按小时循环
			var tTempVal = $("#intervalHour").val();     
		//	alert('tTempVal:'+tTempVal);     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalHour").val(tIntv);          
			}
			
			//获得运行起 小时
			var tStart = $("#hourStart").val();   
			var tEnd = $("#hourEnd").val();   
			
			//$("#monthStart").combobox("setValue",12);  
			
			if((tStart==null||tStart=='')&&(tEnd==null||tEnd==''))
			{
				tCrontModeStr = "0#*/"+tIntv+"#*#*#*#*";
			}
			else
			{
				if(tStart==null||tStart=='')
				{
					tStart = 0;
					$("#hourStart").val(0);  
				}
			
				if(tEnd==null||tEnd=='')
				{
					tEnd = 24;
					//$("#hourEnd").combobox("setValue",24);  
					$("#hourEnd").val(24);  
				}
			
				if(Number(tStart)>Number(tEnd))
				{
					alert('起期不能大于止期');
					return false;
				}
			
			
				tCrontModeStr = "0#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*#*#*";
			}
		}
		
		else if(tRecycleType=='31')
		{
			//按天循环
			var tTempVal = $("#intervalDay").val();     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalDay").val(tIntv);          
			}
			
			//获得运行起 小时
			var tStart = $("#dayStart").val();   
			var tEnd = $("#dayEnd").val();   

			if((tStart==null||tStart=='')&&(tEnd==null||tEnd==''))
			{
				tCrontModeStr = "0#0#*/"+tIntv+"#*#*#*";
			}
			else
			{
				if(tStart==null||tStart=='')
				{
					tStart = 1;
					$("#dayStart").val(1);  
				}
			
				if(tEnd==null||tEnd=='')
				{
					tEnd = 31;
					$("#dayEnd").val(31);  
				}
			
				if(Number(tStart)>Number(tEnd))
				{
					alert('起期不能大于止期');
					return false;
				}
			
			
				tCrontModeStr = "0#0#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*#*";
			}
		}
		else if(tRecycleType=='41')
		{
			//按周循环
			//*/间隔 * * * * 
			
			var tStart = $("#weekStart").val();   
			var tEnd = $("#weekEnd").val();   

			if((tStart==null||tStart=='')&&(tEnd==null||tEnd==''))
			{
				tCrontModeStr = "0#0#*#*#*#*";
			}
			else
			{
				if(tStart==null||tStart=='')
				{
					tStart = 0;
					$("#weekStart").val(0);  
				}
			
				if(tEnd==null||tEnd=='')
				{
					tEnd = 6;
					$("#weekEnd").val(6);  
				}
			
				if(Number(tStart)>Number(tEnd))
				{
					alert('起期不能大于止期');
					return false;
				}
			
			
				tCrontModeStr = "0#0#*#*#*#"+tStart+"-"+tEnd;
		
			}
		}
		else if(tRecycleType=='51')
		{
			//按月循环
			var tTempVal = $("#intervalMonth").val();     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalMonth").val(tIntv);          
			}
			
			//获得运行起 小时
			var tStart = $("#monthStart").val();   
			var tEnd = $("#monthEnd").val();   

			if((tStart==null||tStart=='')&&(tEnd==null||tEnd==''))
			{
				tCrontModeStr = "0#0#1#*/"+tIntv+"#*#*";
			}
			else
			{
				if(tStart==null||tStart=='')
				{
					tStart = 1;
					$("#monthStart").val(1);  
				}
			
				if(tEnd==null||tEnd=='')
				{
					tEnd = 12;
					$("#monthEnd").val(12);  
				}
			
				if(Number(tStart)>Number(tEnd))
				{
					alert('起期不能大于止期');
					return false;
				}
			
			
				tCrontModeStr = "0#0#1#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*";
			}
		}
		
	}
	else
	{
		//专家模式
		$("#RecycleType").val('99');
		for(i=1;i<=6;i++)
		{
			if(i!=6)
			{
		 		tCrontModeStr = tCrontModeStr + CrontabGrid.getRowColData(0,i) + "#";
			}
			else
			{
				tCrontModeStr = tCrontModeStr + CrontabGrid.getRowColData(0,i);
			}
		}
		//alert('tCrontModeStr:'+tCrontModeStr);
	}
	
	//CrontabGrid.setRowColData(0,i,tCrontabArr[i-1]);

	setCrontabGrid(tCrontModeStr);
	return true;
}

//测试执行计划
function testPlan()
{
	//转换执行计划
	if(!transPlanMode())
	{
		return false;
	}
	//return;
	//lockPage("测试中,请稍候...........");
	var tCrontabStr = $("#CrontabStr").val();    
	tCrontabStr = tCrontabStr.replace(/#/g,"@");
	
	fmtest.action = "./TaskPlanTest.jsp?Cron="+tCrontabStr;
	fmtest.submit();
}

//按照字符串设置CrontabGrid
function setCrontabGrid(tCrontabStr)
{
	var tCrontabArr  = tCrontabStr.split("#");
	$("#CrontabStr").val(tCrontabStr);     
	
	//alert(tCrontabArr.length);
	for(i=1;i<=tCrontabArr.length;i++)
	{
		CrontabGrid.setRowColData(0,i,tCrontabArr[i-1]);
	}
}


//将Cron的字符串转换为简易模式的数据 
function transCronStr(tRecycleType,tCrontabStr)
{
	if(tRecycleType=='99')
	{
		//专家模式不做转换
	}
	else 
	{
		//var tCrontabArr = tCrontabStr.split("#");
		//alert(tCrontabArr.length);
	//	$("#RecycleType").val(tRecycleType);
		 $("input[@name=PlanModeFlag]][value="+tRecycleType+"]").attr("checked","checked");
		 
		 var tCrontab1 = CrontabGrid.getRowColData(0,1);
		 var tCrontab2 = CrontabGrid.getRowColData(0,2);
		 var tCrontab3 = CrontabGrid.getRowColData(0,3);
		 var tCrontab4 = CrontabGrid.getRowColData(0,4);
		 var tCrontab5 = CrontabGrid.getRowColData(0,5); 
		 var tCrontab6 = CrontabGrid.getRowColData(0,6);
		 
		if(tRecycleType=='11')
		{
			//按分循环
			$("#intervalMin").val(tCrontab1.split('/')[1]);
		}
		else if(tRecycleType=='21')
		{
			//按小时循环
			var tTempArr  = tCrontab2.split('/');
			$("#intervalHour").val(tTempArr[1]);
			if(tTempArr[0].indexOf('-')!=-1)
			{
				$("input[name='hourStart']").val(tTempArr[0].split('-')[0]);  
				$("input[name='hourEnd']").val(tTempArr[0].split('-')[1]);  
			}
		}
		else if(tRecycleType=='31')
		{
			//按天循环
			var tTempArr  = tCrontab3.split('/');
			$("#intervalDay").val(tTempArr[1]);
			//alert(tTempArr[0]);
			if(tTempArr[0].indexOf('-')!=-1)
			{
				$("#dayStart").val(tTempArr[0].split('-')[0]);  
				$("#dayEnd").val(tTempArr[0].split('-')[1]);  
			}
		}
		else if(tRecycleType=='41')
		{
			//按周循环
			var tTempArr  = tCrontab6;
			//$("#intervalDay").val(tTempArr[1]);
			if(tTempArr.indexOf('-')!=-1)
			{
				$("#weekStart").val(tTempArr.split('-')[0]);  
				$("#weekEnd").val(tTempArr.split('-')[1]);  
			}
		}
		else if(tRecycleType=='51')
		{
			//按月循环
			var tTempArr  = tCrontab4.split('/');
			$("#intervalMonth").val(tTempArr[1]);
			if(tTempArr[0].indexOf('-')!=-1)
			{
				$("#monthStart").val(tTempArr[0].split('-')[0]);  
				$("#monthEnd").val(tTempArr[0].split('-')[1]);  
			}
		}
  }
}



function initSimpleTable()
{
	$("#intervalMin").val('');
	
	$("#intervalHour").val('');
	$("#hourStart").val('');
	$("#hourEnd").val('');
	
	$("#intervalDay").val('');
	
	$("#dayStart").val('');
	$("#dayEnd").val('');

	$("#weekStart").val('');
	$("#weekEnd").val('');
	
	$("#monthStart").val('');
	$("#monthEnd").val('');

	$("#intervalMonth").val('');

}

//信息提醒
function chooseMail()
{
	if($('#Msg_Type').attr("checked")==true)
	{
		var jMonitorCode = document.all('TaskCode').value;
		if(jMonitorCode==null||jMonitorCode=='')
		{
			alert('请先选择\'基本任务/任务队列编码\' ');
			$('#Msg_Type').attr("checked",false);
			return false;
		}
		ItemQuery();
		$('#defMailDiv').show();
	}
	else
	{
		$('#defMailDiv').hide();
	}
}

function ItemQuery()
{
	// 初始化表格
	initLogItemGrid();

	// 书写SQL语句
/*	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql1");

	    mySql.addSubPara(fm.SubIDForItem.value);
	    mySql.addSubPara(fm.SubIDForItem.value);
	    mySql.addSubPara(fm.SubIDForItem.value);
*/
	var jMonitorCode = document.all('TaskCode').value;
	var jTaskPlanCode = document.all('TaskPlanCode').value;
//  var tSQL = "";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	if(jTaskPlanCode==null||!jTaskPlanCode=='')
	{
/*	  tSQL = "select A.x,A.y,A.z,( select codename from ldcode where codetype='logkeytype' and code=A.m),A.n from ("
	          + "select subjectid x,itemid y,(select itemdes from logitem where itemid=a.itemid) z,keytype m, "
	          + " 0 n "
	          + " from logdomaintoitem a where 1=1 "
	          + " and ( subjectid='TASK"+jMonitorCode+"' or subjectid in (select 'TASK'||taskcode from ldtaskgroupdetail where taskgroupcode='"+jMonitorCode+"')) "
	          + " and switch='Y' ) A"
	          + " order by A.x,A.y,A.z,A.m,A.n ";
*/
   		mySql.setSqlId("TaskServiceSql9");  
		mySql.addSubPara(jMonitorCode); 
		mySql.addSubPara(jMonitorCode); 
	 }
	 else
	 {
/*	  	tSQL = "select A.x,A.y,A.z,( select codename from ldcode where codetype='logkeytype' and code=A.m),A.n from ("
	          + "select subjectid x,itemid y,(select itemdes from logitem where itemid=a.itemid) z,keytype m, "
	          + " nvl((select 1 from ldtaskmsgitem where taskcode='"+jMonitorCode+"' and taskplancode='"+jTaskPlanCode+"' and subjectid=a.subjectid and itemid=a.itemid),0) n "
	          + " from logdomaintoitem a where 1=1 "
	          + " and ( subjectid='TASK"+jMonitorCode+"' or subjectid in (select 'TASK'||taskcode from ldtaskgroupdetail where taskgroupcode='"+jMonitorCode+"')) "
	          + " and switch='Y' ) A"
	          + " order by A.x,A.y,A.z,A.m,A.n ";
*/
		mySql.setSqlId("TaskServiceSql10");  
		mySql.addSubPara(jMonitorCode); 
		mySql.addSubPara(jTaskPlanCode); 
		mySql.addSubPara(jMonitorCode); 
		mySql.addSubPara(jMonitorCode); 
	}

		turnPageItem.queryModal(mySql.getString(), LogItemGrid);
//		turnPageItem.queryModal(tSQL, LogItemGrid);
		if(LogItemGrid.mulLineCount <= 0){
			alert("当前 任务/任务队列 未定义监控主题!");
			return false;
		}
		else
		{
			//处理勾选
			for(i=0;i<LogItemGrid.mulLineCount;i++)
			{
				var tCheckFlag = LogItemGrid.getRowColData(i,5);
				//alert(tCheckFlag);
				if(tCheckFlag!=null&&tCheckFlag=='1')
				{
					LogItemGrid.chkOneRow(i+1);
				}
			}
		}
	//divLogItemMain.style.display = "";

}

function initMailMsg()
{
	$('#Msg_Type').attr("checked",false);
	$('#defMailDiv').hide();
	$('#MailAddress').val("");
}

