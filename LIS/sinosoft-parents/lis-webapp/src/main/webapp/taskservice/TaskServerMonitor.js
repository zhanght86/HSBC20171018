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
		alert('����ѡ��һ������ڵ�');
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
	queryTaskServerPlanGrid();
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



/*********************************************************************
 *  ��ʾ����ƻ�
 *  ����  ��  
 *  ����ֵ��  ��
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
 *  ѡ������ƻ������Ӧ�¼�
 *  ����  ��  
 *  ����ֵ��  ��
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
