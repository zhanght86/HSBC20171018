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
//var mSwitch = parent.VD.gVSwitch;
var k = 0;
var turnPage = new turnPageClass();  
var turnPage1 = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
var turnPagePlan = new turnPageClass(); 
var turnPageItem = new turnPageClass();   
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

function refreshShowTask()
{
/*	var tSQL = " select taskcode,'����:'||taskdescribe from ldtask "
				   + " union "
				   + " select taskgroupcode,'����:'||taskdescribe from ldtaskgroup "
*/
 	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	mySql.setSqlId("TaskServiceSql11");  
	mySql.addSubPara("����"); 
	mySql.addSubPara("����"); 
	var ttemp = (easyQueryVer3(mySql.getString(), 1, 0, 1));
//	var ttemp = (easyQueryVer3(tSQL, 1, 0, 1));
//	alert(ttemp);
	document.all('TaskCode').CodeData = ttemp;
}


function appendOne()
{   
    //�ж����ڸ�ʽ
    var StartTime = document.all("StartTime").value;
    var EndTime = document.all("EndTime").value;
    var t = new Date(StartTime.replace(/\-/g,"/"));
    var w = new Date(EndTime.replace(/\-/g,"/"));
    if(!(StartTime == ""||StartTime == null))
    {
      if(isNaN(t))
      {
         alert("��ʼ���ڸ�ʽ����");
         return false;
      }
      else
      {
         if(!(EndTime == ""||EndTime == null))
          {
            if(isNaN(w))
             {
                alert("��ֹ���ڸ�ʽ����");
                return false;
             }
           }
      } 
    }
    
	mAction = "INSERT";
	//��������ǿյ�У��
	if($('#Msg_Type').attr("checked")==true)
	{
		var tValue = $('#MailAddress').val();
		//alert(tValue);
		if(tValue==null||tValue=='')
		{
			alert('�����������ַ!');
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
		alert("������ \'�������\'��\'�Ƿ�����\' !");
		return;
	}
	/*if(document.all("RecycleType").value=='72')
	{
		if(document.all("Interval").value=='')	
		{
			alert("������ѭ���������ֵ!");
			return;
		}else
		{
			Interval=parseInt(document.all("Interval").value);
			if(isNaN(Interval))
			{
				alert("��������ȷ����ֵ!");
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
		alert("��ѡ��һ������ƻ���");
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

function startEngine()
{
	mAction = "START";
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
	queryTaskPlanInfo();
	initParamGrid();
	initMoreParamGrid();
	initCrontabGrid();
	initSimpleTable();
	initMailMsg();
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

var tempTaskPlanSQLStr = "";//�˴�js����˳��ִ�У����´���ʹ��setTimeout������˳����Ҫȫ�ֱ���
/*********************************************************************
 *  ��ʾ����ƻ�
 *  ����  ��  
 *  ����ֵ��  ��
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
 
 
 	//����������Ϣ�Ĵ���
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
  ת������ģʽ��ר��ģʽ�ĺ���
         0 - ������ģʽת����ר��ģʽ������
         1 - ֱ�ӻ�ȡר��ģʽ
*/
function transPlanMode()
{
	var tPlanMode =  $("input[name='PlanModeFlag']:checked").val();
	//alert(tPlanMode);
	var tCrontModeStr = "";
	//ѭ�����
	var tIntv = "1";
	if(tPlanMode=='0')
	{
		//������ģʽת��ר��ģʽ
		//��ȡ���м��
		var tRecycleType =  $("input[name='SimpleModeCho']:checked").val();
		//�����ص����е�λ��ֵ
		$("#RecycleType").val(tRecycleType);

		if(tRecycleType=='11')
		{
			//����ѭ��
			//*/��� * * * * 
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
			//��Сʱѭ��
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
			
			//��������� Сʱ
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
					alert('���ڲ��ܴ���ֹ��');
					return false;
				}
			
			
				tCrontModeStr = "0#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*#*#*";
			}
		}
		
		else if(tRecycleType=='31')
		{
			//����ѭ��
			var tTempVal = $("#intervalDay").val();     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalDay").val(tIntv);          
			}
			
			//��������� Сʱ
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
					alert('���ڲ��ܴ���ֹ��');
					return false;
				}
			
			
				tCrontModeStr = "0#0#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*#*";
			}
		}
		else if(tRecycleType=='41')
		{
			//����ѭ��
			//*/��� * * * * 
			
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
					alert('���ڲ��ܴ���ֹ��');
					return false;
				}
			
			
				tCrontModeStr = "0#0#*#*#*#"+tStart+"-"+tEnd;
		
			}
		}
		else if(tRecycleType=='51')
		{
			//����ѭ��
			var tTempVal = $("#intervalMonth").val();     
			if(tTempVal!=null&&tTempVal!='')
			{
				tIntv = tTempVal;
			}
			else
			{
				$("#intervalMonth").val(tIntv);          
			}
			
			//��������� Сʱ
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
					alert('���ڲ��ܴ���ֹ��');
					return false;
				}
			
			
				tCrontModeStr = "0#0#1#"+tStart+"-"+tEnd+"/"+tIntv+"#*#*";
			}
		}
		
	}
	else
	{
		//ר��ģʽ
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

//����ִ�мƻ�
function testPlan()
{
	//ת��ִ�мƻ�
	if(!transPlanMode())
	{
		return false;
	}
	//return;
	//lockPage("������,���Ժ�...........");
	var tCrontabStr = $("#CrontabStr").val();    
	tCrontabStr = tCrontabStr.replace(/#/g,"@");
	
	fmtest.action = "./TaskPlanTest.jsp?Cron="+tCrontabStr;
	fmtest.submit();
}

//�����ַ�������CrontabGrid
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


//��Cron���ַ���ת��Ϊ����ģʽ������ 
function transCronStr(tRecycleType,tCrontabStr)
{
	if(tRecycleType=='99')
	{
		//ר��ģʽ����ת��
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
			//����ѭ��
			$("#intervalMin").val(tCrontab1.split('/')[1]);
		}
		else if(tRecycleType=='21')
		{
			//��Сʱѭ��
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
			//����ѭ��
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
			//����ѭ��
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
			//����ѭ��
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

//��Ϣ����
function chooseMail()
{
	if($('#Msg_Type').attr("checked")==true)
	{
		var jMonitorCode = document.all('TaskCode').value;
		if(jMonitorCode==null||jMonitorCode=='')
		{
			alert('����ѡ��\'��������/������б���\' ');
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
	// ��ʼ�����
	initLogItemGrid();

	// ��дSQL���
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
			alert("��ǰ ����/������� δ����������!");
			return false;
		}
		else
		{
			//����ѡ
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

