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


function queryTaskGroupInfo()
{
	resetForm();
	initTaskGroupGrid();
	//k = k + 1;
	//var strSQL = "";
	//var strSQL = "select taskgroupcode,taskdescribe from LDTaskGroup order by taskgroupcode ";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskGroupDefSql");
	mySql.setSqlId("TaskGroupDefSql2");  
	mySql.addSubPara("1");  
	turnPage2.queryModal(mySql.getString(),TaskGroupGrid);
	
}

function onTaskGroupSelected(parm1,parm2)
{
	document.all("TaskGroupCode").value = TaskGroupGrid.getRowColData(TaskGroupGrid.getSelNo() - 1, 1);
	document.all("TaskGroupDescribe").value = TaskGroupGrid.getRowColData(TaskGroupGrid.getSelNo() - 1, 2);
	//document.all("TaskClass").value = TaskGrid.getRowColData(TaskGrid.getSelNo() - 1, 3);
	
	//
	//select taskcode,dependtaskcode,describe,dependtype,actionafterfail,taskorder from ldtaskgroupdetail
	//where taskgroupcode=''
	initTaskGroupDetailGrid();
	//var strSQL = "select taskcode,dependtaskcode,describe,dependtype,actionafterfail,taskorder from ldtaskgroupdetail "
	//           + " where taskgroupcode='"+document.all("TaskGroupCode").value+"' order by  taskorder,dependtaskcode ";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskGroupDefSql");
	mySql.setSqlId("TaskGroupDefSql3");  
	mySql.addSubPara(document.all("TaskGroupCode").value);  
	turnPage1.queryModal(mySql.getString(),TaskGroupDetailGrid);
	

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