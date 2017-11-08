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

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //提交
}

 
function ItemQuery()
{
	// 初始化表格
	initLogItemGrid();

	// 书写SQL语句
/*  var tSQL = "select subjectid,itemid,(select itemdes from logitem where itemid=logdomaintoitem.itemid),keytype,Switch "
           + " from logdomaintoitem where 1=1 "
           + " and ( subjectid='TASK"+jMonitorCode+"' or subjectid in (select 'TASK'||taskcode from ldtaskgroupdetail where taskgroupcode='"+jMonitorCode+"')) "
           + " and switch='Y'";
	turnPageItem.queryModal(tSQL, LogItemGrid);
*/
	mySql=new SqlClass();
    mySql.setResourceName("taskservice.TaskServiceSql");
    mySql.setSqlId("TaskServiceSql13");

    mySql.addSubPara(jMonitorCode);
    mySql.addSubPara(jMonitorCode);
	turnPageItem.queryModal(mySql.getString(), LogItemGrid);
	if(LogItemGrid.mulLineCount <= 0){
		alert("未查询到该主题对应控制点信息！");
		return false;
	}
	//divLogItemMain.style.display = "";

}

function ShowItem()
{
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	
	var tControlID = LogItemGrid.getRowColData(tSel-1,1);
	var tItemID =  LogItemGrid.getRowColData(tSel-1,2);
	
	document.all('hiddenItemID').value=tItemID;
	document.all('hiddenControlID').value=tControlID;
	initTaskRunLogTodayGrid(tItemID);
	queryTaskRunLogTodayGrid(tItemID,tControlID);
	initTaskRunLogBeforeGrid(tItemID);
	//queryTaskRunLogBefore(tItemID);
	
	divTaskRunLogToday.style.display="";
	divTaskRunLogBefore.style.display="";
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


function queryTaskRunLogTodayGrid(tItemID,tControlID)
{
	//resetForm();
	initTaskRunLogTodayGrid(tItemID);
	//k = k + 1;
	//var strSQL = "";
	var strSQL = "";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	if(tItemID=='01'||tItemID=='04')
	{
/*		strSQL = "select logno,serialno,keyno,state,itemdes,remark,makedate,maketime,modifydate,modifytime from  LogBusinessState "
	         + " where (subjectid='"+tControlID+"' ) "
	         + " and itemid='"+tItemID+"' "
	         + " and makedate = to_char(sysdate,'yyyy-mm-dd') "
	         + " order by logno ";
*/
		mySql.setSqlId("TaskServiceSql14");
		mySql.addSubPara(tControlID);
		mySql.addSubPara(tItemID);     
	}
	else
	{
/*		strSQL = " select logno,serialno,keyno,itemdes,remark,makedate,maketime  from LogTrackResult "
 					 + " where (subjectid='"+tControlID+"' ) "
 					 + " and itemid='"+tItemID+"' "
 					 + " and makedate = to_char(sysdate,'yyyy-mm-dd') "
	    		     + " order by logno ";
*/
		mySql.setSqlId("TaskServiceSql15");
		mySql.addSubPara(tControlID);
		mySql.addSubPara(tItemID); 
	}

	//mySql.setSqlId("TaskServiceSql4");  
	//mySql.addSubPara("");  
//	turnPage.queryModal(strSQL,TaskRunLogTodayGrid);
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
	  	alert('请输入\'日志起期\'和\'日志止期\'后再查询');
	  	return false;
	  }
	  var tItemID = document.all('hiddenItemID').value;
	initTaskRunLogBeforeGrid(tItemID);
	
	var tControlID = document.all('hiddenControlID').value;
	//k = k + 1;
	var strSQL = "";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	if(tItemID=='01'||tItemID=='04')
	{
/*		strSQL = "select logno,serialno,keyno,state,itemdes,remark,makedate,maketime,modifydate,modifytime from  LogBusinessState "
	         + " where (subjectid='"+tControlID+"'  ) "
	         + " and itemid='"+tItemID+"' "
	         + " and makedate>='"+tStartDate+"' "
           + " and maketime<='"+tEndDate+"' "
	         + " order by logno ";
*/
	    mySql.setSqlId("TaskServiceSql16"); 
	    mySql.addSubPara(tControlID); 
	    mySql.addSubPara(tItemID); 
	    mySql.addSubPara(tStartDate); 
	    mySql.addSubPara(tEndDate);  
  }
  else
  {
/*  	strSQL = " select logno,serialno,keyno,itemdes,remark,makedate,maketime  from LogTrackResult "
 					 + " where (subjectid='"+tControlID+"' ) "
 					 + " and itemid='"+tItemID+"' "
 					 + " and makedate>='"+tStartDate+"' "
           + " and maketime<='"+tEndDate+"' "
	         + " order by logno ";
*/
	    mySql.setSqlId("TaskServiceSql17"); 
	    mySql.addSubPara(tControlID); 
	    mySql.addSubPara(tItemID); 
	    mySql.addSubPara(tStartDate); 
	    mySql.addSubPara(tEndDate); 
  }
//	turnPage1.queryModal(strSQL,TaskRunLogBeforeGrid);
	turnPage1.queryModal(mySql.getString(),TaskRunLogBeforeGrid);
}

