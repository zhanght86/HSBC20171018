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
	queryLDUserGridInfo();
	initUserTabDetailGrid();
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
		alert('����ѡ����Ҫ���õ��û�');
		return false;
	}
	UserTabDetailGrid.delBlankLine();
	var rowNum=UserTabDetailGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('û��Ϊ�û�'+document.all('USerCode').value+'������ϸ,���豣��!');
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
		alert('����ѡ����Ҫ���õ��û�!');
		return false;
	}
	
	document.all( 'fmAction' ).value = mAction;
	submitForm();
}