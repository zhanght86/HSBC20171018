//�������ƣ�TaskService.js
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����

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
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}

function activateOne()
{
	mAction = "ACTIVATE";
	if (document.all("TaskPlanCode").value == '')
	{
		alert("��ѡ��һ������ƻ���");
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
		alert("��ѡ��һ������ƻ���");
		return;
	}
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	queryTaskPlanInfo();
	//queryTaskInfo();
}

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}


function showRunLog()
{
	var tSelNo = TaskPlanGrid.getSelNo();
	
	//alert(tSelNo);
	if(tSelNo<=0)
	{
		alert('����ѡ��һ��������߶���');
		return false;
	}
	
	var tTaskPlanCode = TaskPlanGrid.getRowColData(tSelNo-1, 1);
	var dialogURL = "./ShowTaskRunLogMain.jsp?TaskPlanCode="+tTaskPlanCode;
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1000;      //�������ڵĿ��; 
	var iHeight=500;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//var newWindow = OpenWindowNew(dialogURL,"","left");
	//document.all("TaskPlanCode").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 1);
}




function showMonitorLog()
{
	var tSelNo = TaskPlanGrid.getSelNo();
	
	//alert(tSelNo);
	if(tSelNo<=0)
	{
		alert('����ѡ��һ��������߶���');
		return false;
	}
	
	var tMonitorCode = TaskPlanGrid.getRowColData(tSelNo-1, 2);
	var dialogURL = "./ShowTaskMonitorLogMain.jsp?MonitorCode="+tMonitorCode;
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1000;      //�������ڵĿ��; 
	var iHeight=500;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//var newWindow = OpenWindowNew(dialogURL,"","left");
	//document.all("TaskPlanCode").value = TaskPlanGrid.getRowColData(TaskPlanGrid.getSelNo() - 1, 1);
}

/*********************************************************************
 *  ��ʾ����ƻ�
 *  ����  ��  
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryTaskPlanInfo()
{
	initTaskPlanGrid();
/*	var strSQL = "select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.RecycleType, t.StartTime, t.EndTime, t.Interval, t.Times, t.RunState,'T' from LDTaskPlan t, LDTask b where t.TaskCode = b.TaskCode and exists("+document.all("RunString").value+" and TaskPlanCode=t.TaskPlanCode) "
	           + " union "
	           + " select t.TaskPlanCode, t.TaskCode, b.TaskDescribe, t.RunFlag, t.RecycleType, t.StartTime, t.EndTime, t.Interval, t.Times, t.RunState,'G' from LDTaskPlan t, LDTaskGroup b where t.TaskCode = b.TaskGroupCode and exists("+document.all("RunString").value+" and TaskPlanCode=t.TaskPlanCode)  ";
*/
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql7");
	mySql.addSubPara("T");  
	mySql.addSubPara("G");
	turnPagePlan.queryModal(mySql.getString(),TaskPlanGrid);

}


function queryTaskInfo()
{
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
	//alert("11111111");
		return;
    }

	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage2.pageDisplayGrid = TaskGrid;
	//����SQL���
	turnPage2.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage2.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage2.arrDataCacheSet, turnPage2.pageDisplayGrid);
}


/*********************************************************************
 *  ѡ������ƻ������Ӧ�¼�
 *  ����  ��  
 *  ����ֵ��  ��
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
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage1.pageDisplayGrid = CrontabGrid;
	//����SQL���
	turnPage1.strQuerySql = mySql.getString();//tSQL_crontab;
	//���ò�ѯ��ʼλ��
	turnPage1.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
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
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = ParamGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
	
	//����������Ϣ�����
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
/***************************����Ϊ������ȼ��JS********************************/
/*********************************************************************
 *  ��ʼ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initMonitorData()
{
	queryTaskMonitorInfo();//�������
}
/*********************************************************************
 *  ��ȡ������Ϣ�����磨����ƻ�ID:����ID������ʾ�������
 *  ����  ��  
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryTaskMonitorInfo()
{
	var TaskMonitorCode = new Array();
	for(i=0;i<TaskPlanGrid.mulLineCount;i++)
		TaskMonitorCode[i] = TaskPlanGrid.getRowColData(i,1)+":"+TaskPlanGrid.getRowColData(i,2);
	document.getElementsByName("TaskMonitorCode")[0].value = TaskMonitorCode;
	fmMonitor.action = "TaskMonitorSave.jsp";
	fmMonitor.submit();	
}

/*********************************************************************
 *  �ύ����ID����󣬻�������������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterMonitor( FlagStr, content )
{
	if(FlagStr == "Succ")
	{
		setTaskMonitorData(content);
	}else if(FlagStr == "Fail")
	{
		alert("������������");
	}
}
/*********************************************************************
 *  �ύ����ID����󣬻������������飬չ���ڱ����
 *  ����  ��  �����������
 *  ����ֵ��  ��
 *********************************************************************
 */
function setTaskMonitorData( content )
{
	for(i=0;i<content.length;i++)
	{
		if(content[i]=="NOFOUND")content[i]="0/0";
		TaskPlanGrid.setRowColData(i,12,content[i]);
	}
}
var timer;//���ڶ�ʱˢ������
/*********************************************************************
 *  ����ˢ��ʱ��
 *  ����  ��  �����������
 *  ����ֵ��  ��
 *********************************************************************
 */

function setTaskMonitorDataInterval( second )
{
	queryTaskMonitorInfo();
	if(timer)clearInterval(timer);
	if(second != "0" && second != 0)
	timer = setInterval("queryTaskMonitorInfo();",10000);
}
/*********************����Rss**************************/
/*********************************************************************
 *  ����ˢ��ʱ��
 *  ����  ��  �����������
 *  ����ֵ��  ��
 *********************************************************************
 */

function orderRSS()
{
	fmRSS.action = "TaskServiceRSSSave.jsp";
	document.getElementsByName("fmRSS")[0].value = "AddressRSS";
	fmRSS.submit();
}