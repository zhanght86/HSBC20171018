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
var mSwitch = parent.VD.gVSwitch;
var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass(); 
 var turnPage2 = new turnPageClass(); 
  var turnPage3 = new turnPageClass(); 
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
  
	if( document.all('hiddenAction').value == "")
		return;

//	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //�ύ
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
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

	}
	
	mAction = ""; 
	queryLockGroup();
	queryAllLockModule();
	queryAllLockGroup();
	//refreshTask();
	document.all('hiddenAction').value = "";	
	divLockConfig.style.display = "none";
	document.all('hiddenLockGroupConfig').value="";
//	queryTaskPlanInfo();
//	queryTaskInfo();
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

function onTaskSelected(parm1,parm2)
{
	divLockConfig.style.display = "";
	initLockGroupConfigGrid();
	var tGroupID = LockGroupGrid.getRowColData(LockGroupGrid.getSelNo() - 1, 1);
	document.all('hiddenLockGroupConfig').value=tGroupID;
	/*var tSQL = "select lockgroup,lockmodule,(select modulename from lockbase where lockmodule=a.lockmodule) "
	         + " from LockConfig a where lockgroup='"+tGroupID+"' "
           + " order by lockmodule ";*/
    
	mySql = new SqlClass();
	mySql.setResourceName("sys.PubLockConfigSql");
	mySql.setSqlId("PubLockConfigSql1");
	mySql.addSubPara(tGroupID); 
    turnPage3.queryModal(mySql.getString(),LockGroupConfigGrid,null,null,50);
//    turnPage.queryModal(ImpartSql, ImpartGrid,null,null,33);
 /*           
 	  turnPage3.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);
    
    if(!turnPage3.strQueryResult)
    {
    	 //alert('����������'+document.all('hiddenLockGroupConfig').value+'û�����ÿ���ģ��');
        return;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage3.arrDataCacheSet = decodeEasyQueryResult(turnPage3.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage3.pageDisplayGrid = LockGroupConfigGrid;
	//����SQL���
	turnPage3.strQuerySql = tSQL;
	//���ò�ѯ��ʼλ��
	turnPage3.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage3.arrDataCacheSet, turnPage3.pageDisplayGrid);
	*/
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

function lockGroupManage()
{
		divAllLockInfo.style.display = "none";
	divAllLockInfoButton.style.display = "none";
	divLockBase.style.display = "none";
	divLockBaseButton.style.display = "none";
		divLockGroup.style.display = "";
	divLockGroupButton.style.display = "";
	divLockConfig.style.display = "none";
}

function lockBaseManage()
{
		divAllLockInfo.style.display = "none";
	divAllLockInfoButton.style.display = "none";
	divUnLockReason.style.display="none";
	divLockBase.style.display = "";
	divLockBaseButton.style.display = "";
		divLockGroup.style.display = "none";
	divLockGroupButton.style.display = "none";
	divLockConfig.style.display = "none";
}

function lockDataManage()
{
		divAllLockInfo.style.display = "";
	divAllLockInfoButton.style.display = "";
	divLockBase.style.display = "none";
	divLockBaseButton.style.display = "none";
		divLockGroup.style.display = "none";
	divLockGroupButton.style.display = "none";
	divLockConfig.style.display = "none";
}
function queryLockGroup()
{
	//fm1.submit();
	//alert('1');
	/*var tSQL = " select operatedno,lockgroup,makedate,maketime,to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-to_date(to_char(makedate,'yyyy-mm-dd'),'yyyy-mm-dd'),changeTime('','11:18:01')-changeTime('',maketime)  from LockAppGroup "
							;*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.PubLockConfigSql");
	mySql.setSqlId("PubLockConfigSql2");
	mySql.addSubPara("1"); 						
   turnPage.queryModal(mySql.getString(),AllLockInfoGrid);
}
/*
function displayData(tStrArr)
{ 
	//alert(tStrArr);
	 //ʹ��ģ������Դ
  turnPage.useSimulation   = 1;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.strQueryResult = tStrArr;
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = arrDataSet;
  //alert(turnPage.arrDataCacheSet);


  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = AllLockInfoGrid;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 //���뽫������������Ϊһ�����ݿ�
 //alert(turnPage.queryAllRecordCount);
// alert(turnPage.pageLineNum);
 //alert(turnPage.queryAllRecordCount / turnPage.pageLineNum);
 //alert(turnPage.queryAllRecordCount % turnPage.pageLineNum)
 turnPage.dataBlockNum = turnPage.queryAllRecordCount;
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;

} 
*/
function queryAllLockModule()
{
	//var tSQL = "select lockmodule,modulename,remark from LockBase order by lockmodule";
	  mySql = new SqlClass();
	mySql.setResourceName("sys.PubLockConfigSql");
	mySql.setSqlId("PubLockConfigSql3");
	mySql.addSubPara("1"); 	
	  turnPage1.queryModal(mySql.getString(),LockBaseGrid);
	  /*
	  turnPage1.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);
    
    if(!turnPage1.strQueryResult)
    {
        return;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage1.pageDisplayGrid = LockBaseGrid;
	//����SQL���
	turnPage1.strQuerySql = tSQL;
	//���ò�ѯ��ʼλ��
	turnPage1.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage1.arrDataCacheSet, turnPage1.pageDisplayGrid);
	*/
}

function queryAllLockGroup()
{
	//var tSQL = " select LockGroup,LockGroupName,Remark from LockGroup order by LockGroup ";
	 mySql = new SqlClass();
	mySql.setResourceName("sys.PubLockConfigSql");
	mySql.setSqlId("PubLockConfigSql4");
	mySql.addSubPara("1"); 	
	 turnPage2.queryModal(mySql.getString(),LockGroupGrid);
	 /*
	  turnPage2.strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);
    
    if(!turnPage2.strQueryResult)
    {
        return;
    }
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage2.pageDisplayGrid = LockGroupGrid;
	//����SQL���
	turnPage2.strQuerySql = tSQL;
	//���ò�ѯ��ʼλ��
	turnPage2.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage2.arrDataCacheSet, turnPage2.pageDisplayGrid);
	*/
}


function onAllLockInfoSelected(parm1,parm2)
{
	//alert(document.all( parm1 ).all('AllLockInfoGrid1').value);
	document.all("hiddenLockData").value = AllLockInfoGrid.getRowColData(AllLockInfoGrid.getSelNo() - 1, 1);
	document.all("hiddenLockGroup").value = AllLockInfoGrid.getRowColData(AllLockInfoGrid.getSelNo() - 1, 2);
	document.all("hiddenSelectGrid").value = 'AllLockInfoGrid';
	divUnLockReason.style.display="";
}

/**
* �ֶ���������
*/
function unLockManual()
{
	document.all('hiddenAction').value = "UnLockManual";	
	submitForm();
}

/**
* ��������ģ��
*/
function addNewLockBase()
{
	//alert(document.all('BaseModuleCode').value);
	if(document.all('BaseModuleCode').value==null||document.all('BaseModuleCode').value=='')
	{
		alert("������'����ģ�����'");
		return false;
	}
	if(document.all('BaseModuleName').value==null||document.all('BaseModuleName').value=='')
	{
		alert("������'����ģ������'");
		return false;
	}
	if(document.all('ModuleDescribe').value==null||document.all('ModuleDescribe').value=='')
	{
		alert("������'����ģ������'!");
		return false;
	}
	document.all('hiddenAction').value = "addNewLockBase";	
	submitForm();
}

function addNewGroupConfig(parm1,parm2)
{
	//�ж��Ƿ�ѡ��
	//alert(LockGroupGrid.getSelNo());
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('����ѡ����Ҫ���õĲ���������');
	}
	//if(document.all('hiddenLockGroupConfig').value==null||document.all('hiddenLockGroupConfig').value=''
	//)
}

function saveLockGroupConfig()
{
	//alert(document.all('hiddenLockGroupConfig').value);
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('����ѡ����Ҫ���õĲ���������');
		return false;
	}
	LockGroupConfigGrid.delBlankLine();
	var rowNum=LockGroupConfigGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('û��Ϊ����������'+document.all('hiddenLockGroupConfig').value+'���ÿ���ģ��,���豣��!');
		return false;
	}
	
	document.all('hiddenAction').value = "SaveLockGroupConfig";	
	submitForm();
}

//���ӿ�����
function appendTask()
{
	if(document.all('LockGroupName').value==null||document.all('LockGroupName').value=='')
	{
		alert("������'��������������'");
		return false;
	}
	if(document.all('LockGroupDescribe').value==null||document.all('LockGroupDescribe').value=='')
	{
		alert("������'��������������'!");
		return false;
	}
	
	document.all('hiddenAction').value = "AddLockGroup";
	submitForm();
}

function deleteTask()
{
	
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('����ѡ����Ҫɾ���Ĳ���������');
		return false;
	}
	if (!confirm("��ȷʵ��ɾ���ü�¼��?"))
 	{  	
    return false;
	}
	document.all('hiddenAction').value = "DeleteLockGroup";
	submitForm();
}