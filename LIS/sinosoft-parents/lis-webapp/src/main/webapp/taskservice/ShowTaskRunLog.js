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

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}

 


function appendTaskGroup()
{
	mAction = "INSERTTASKGROUP";
	if (document.all("TaskGroupDescribe").value == '')
	{
		alert("�����������������!");
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
		alert("��ѡ��һ���������!");
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	
	mAction = ""; 
	//queryTaskPlanInfo();
	queryTaskGroupInfo();
	initTaskGroupDetailGrid();
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


/*********************************************************************
 *  ��ʾ����ƻ�
 *  ����  ��  
 *  ����ֵ��  ��
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
	  	alert('������\'��־����\'��\'��־ֹ��\'���ٲ�ѯ');
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
		alert('����ѡ����Ҫ���õ��������');
		return false;
	}
	TaskGroupDetailGrid.delBlankLine();
	var rowNum=TaskGroupDetailGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('û��Ϊ�������'+document.all('TaskGroupCode').value+'����������ϸ,���豣��!');
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
		alert('����ѡ���������!');
		return false;
	}
	
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}