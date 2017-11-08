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
var mSwitch = parent.VD.gVSwitch;
var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass(); 
 var turnPage2 = new turnPageClass(); 
  var turnPage3 = new turnPageClass(); 
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
  
	if( document.all('hiddenAction').value == "")
		return;

//	showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");  
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.getElementById("fm").submit(); //提交
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
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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
    	 //alert('并发控制组'+document.all('hiddenLockGroupConfig').value+'没有配置控制模块');
        return;
    }
	//查询成功则拆分字符串，返回二维数组
	turnPage3.arrDataCacheSet = decodeEasyQueryResult(turnPage3.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage3.pageDisplayGrid = LockGroupConfigGrid;
	//保存SQL语句
	turnPage3.strQuerySql = tSQL;
	//设置查询起始位置
	turnPage3.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
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
	 //使用模拟数据源
  turnPage.useSimulation   = 1;
  //查询成功则拆分字符串，返回二维数组
  turnPage.strQueryResult = tStrArr;
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  
  
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = arrDataSet;
  //alert(turnPage.arrDataCacheSet);


  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = AllLockInfoGrid;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 //必须将所有数据设置为一个数据块
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
	//查询成功则拆分字符串，返回二维数组
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage1.pageDisplayGrid = LockBaseGrid;
	//保存SQL语句
	turnPage1.strQuerySql = tSQL;
	//设置查询起始位置
	turnPage1.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
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
	//查询成功则拆分字符串，返回二维数组
	turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage2.pageDisplayGrid = LockGroupGrid;
	//保存SQL语句
	turnPage2.strQuerySql = tSQL;
	//设置查询起始位置
	turnPage2.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	//调用MULTILINE对象显示查询结果
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
* 手动解锁功能
*/
function unLockManual()
{
	document.all('hiddenAction').value = "UnLockManual";	
	submitForm();
}

/**
* 新增基本模块
*/
function addNewLockBase()
{
	//alert(document.all('BaseModuleCode').value);
	if(document.all('BaseModuleCode').value==null||document.all('BaseModuleCode').value=='')
	{
		alert("请输入'控制模块编码'");
		return false;
	}
	if(document.all('BaseModuleName').value==null||document.all('BaseModuleName').value=='')
	{
		alert("请输入'控制模块名称'");
		return false;
	}
	if(document.all('ModuleDescribe').value==null||document.all('ModuleDescribe').value=='')
	{
		alert("请输入'控制模块描述'!");
		return false;
	}
	document.all('hiddenAction').value = "addNewLockBase";	
	submitForm();
}

function addNewGroupConfig(parm1,parm2)
{
	//判断是否选择
	//alert(LockGroupGrid.getSelNo());
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('请先选择需要配置的并发控制组');
	}
	//if(document.all('hiddenLockGroupConfig').value==null||document.all('hiddenLockGroupConfig').value=''
	//)
}

function saveLockGroupConfig()
{
	//alert(document.all('hiddenLockGroupConfig').value);
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('请先选择需要配置的并发控制组');
		return false;
	}
	LockGroupConfigGrid.delBlankLine();
	var rowNum=LockGroupConfigGrid.mulLineCount;
	if(rowNum==0)
	{
		alert('没有为并发控制组'+document.all('hiddenLockGroupConfig').value+'配置控制模块,无需保存!');
		return false;
	}
	
	document.all('hiddenAction').value = "SaveLockGroupConfig";	
	submitForm();
}

//增加控制组
function appendTask()
{
	if(document.all('LockGroupName').value==null||document.all('LockGroupName').value=='')
	{
		alert("请输入'并发控制组名称'");
		return false;
	}
	if(document.all('LockGroupDescribe').value==null||document.all('LockGroupDescribe').value=='')
	{
		alert("请输入'并发控制组描述'!");
		return false;
	}
	
	document.all('hiddenAction').value = "AddLockGroup";
	submitForm();
}

function deleteTask()
{
	
	if(LockGroupGrid.getSelNo()<=0)
	{
		alert('请先选择需要删除的并发控制组');
		return false;
	}
	if (!confirm("您确实想删除该记录吗?"))
 	{  	
    return false;
	}
	document.all('hiddenAction').value = "DeleteLockGroup";
	submitForm();
}