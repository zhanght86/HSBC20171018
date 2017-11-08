 //程序名称：SelAssignDuty.js
//程序功能：
//创建日期：20048-09-26
//创建人  :  liuqh
//更新记录：  更新人    更新日期     更新原因/内容

//var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass();  
/*********************************************************************
 *  提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function AssignSubmit()
{
    if(checkData()==false)
     return false;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "INSERT";
    document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}


function AssignUpdate()
{
    if(!selsome())
     return false;
    if(checkData()==false)
     return false;
   	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "UPDATE";
    document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}
 
function StartSel()
{
    if(!selsome())
     return false;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "STARTSEL";
    document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}
function StartAll()
{

   if(!allstart())
       return false;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "STARTALL";
    document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}
function StopSel()
{
   if(!selsome())
     return false;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "TERMINATESEL";
    document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}
function StopAll()
{
    if(!allstop())
      return false
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "TERMINATEALL";
	document.all('Action').value = mAction;
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //提交
}

function checkData()
{


  if(verifyInput()==false)
    return false;
  //清除所有空行
  AssignGrid.delBlankLine();
  var m =AssignGrid.mulLineCount;
  for(i=1;i<m;i++)
  {
     for (j=0;j<i;j++)
     {
         if(AssignGrid.getRowColData(j,1)==AssignGrid.getRowColData(i,1))
         {
           alert("操作员"+AssignGrid.getRowColData(j,1)+"存在多条记录！请将相同的操作员的工作量录在一行中！");
           return false;
         }
     }
  }
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
    return true;
}

function afterSubmit(FlagStr,content)
{
   	            
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

      InitAssign();

}

function InitAssign()
{
   //异常件
   var sqlid831163543="DSHomeContSql831163543";
var mySql831163543=new SqlClass();
mySql831163543.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163543.setSqlId(sqlid831163543);//指定使用的Sql的id
mySql831163543.addSubPara();//指定传入的参数
var sqlq=mySql831163543.getString();
   
//  var sqlq = "select count(*) from lwmission where activityid='0000001090' and defaultoperator is null ";
  document.all('QuestModify').value = easyExecSql(sqlq);
  //问题件
  var sqlid831163621="DSHomeContSql831163621";
var mySql831163621=new SqlClass();
mySql831163621.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163621.setSqlId(sqlid831163621);//指定使用的Sql的id
mySql831163621.addSubPara();//指定传入的参数
var sqla=mySql831163621.getString();
  
//  var sqla = "select count(*) from lwmission where activityid='0000001002' and defaultoperator is null ";
  document.all('ApproveModify').value = easyExecSql(sqla);
  //人工合并
  var sqlid831163650="DSHomeContSql831163650";
var mySql831163650=new SqlClass();
mySql831163650.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163650.setSqlId(sqlid831163650);//指定使用的Sql的id
mySql831163650.addSubPara();//指定传入的参数
var sqlc=mySql831163650.getString();
  
//  var sqlc = "select count(*) from lwmission where activityid='0000001404' and defaultoperator is null ";
  document.all('CustomerMerge').value = easyExecSql(sqlc);
  
  //时间初始化
  var sqlid831163723="DSHomeContSql831163723";
var mySql831163723=new SqlClass();
mySql831163723.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163723.setSqlId(sqlid831163723);//指定使用的Sql的id
mySql831163723.addSubPara();//指定传入的参数
var sqltime=mySql831163723.getString();
  
//  var sqltime = "select taskstarttime,taskendtime from ldassignplan where rownum='1'and activityid in ('0000001090','0000001002','0000001404')";
  var arrResult = easyExecSql(sqltime);
  if(arrResult !=null)
  {
     document.all('StartTime').value = arrResult[0][0];
     document.all('EndTime').value = arrResult[0][1];
  }
  //判断能够新增
  var sqlid831163752="DSHomeContSql831163752";
var mySql831163752=new SqlClass();
mySql831163752.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163752.setSqlId(sqlid831163752);//指定使用的Sql的id
mySql831163752.addSubPara();//指定传入的参数
var sqlPlan=mySql831163752.getString();
  
//  var sqlPlan = "select distinct 1 from ldassignplan where activityid in ('0000001090','0000001002','0000001404')";
  var Exists = easyExecSql(sqlPlan);
  if(Exists=="1"){
    //有关于异常件、问题件修改或人工合并的分配计划 所以只能通过修改来提交分配计划
    fm.addNew.disabled = true;
  }else{
    fm.addNew.disabled = false;
  }
  
  
  //mulLine初始化
//  var sql = "  select distinct(a.assignno) ,'操作员姓名' ,"
//           +"  nvl((select b.planamount from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),0),"
//           +"  nvl((select c.planamount from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),0),"
//           +"  nvl((select d.planamount from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),0),"
//           +"  a.state "
//           +"  from ldassignplan a order by a.assignno ";

	var sqlid831163834="DSHomeContSql831163834";
var mySql831163834=new SqlClass();
mySql831163834.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163834.setSqlId(sqlid831163834);//指定使用的Sql的id
mySql831163834.addSubPara();//指定传入的参数
var sql=mySql831163834.getString();

//  var sql = "  select distinct(a.assignno) ,(select codename from ldcode where codetype ='appalluser' and code=a.assignno) ,"
//           +"  (select decode(b.planamount,null,'',b.planamount) from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),"
//           +"  (select b.state from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),"
//           +"  (select decode(c.planamount,null,'',c.planamount) from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002'),"
//           +"  (select c.state from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),"
//           +"  (select decode(d.planamount,null,'',d.planamount) from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),"
//           +"  (select d.state from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' )"
//           +"  from ldassignplan a "
//           +"  where activityid in ('0000001090','0000001404','0000001002') order by a.assignno ";
  turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
  if(turnPage.strQueryResult){
  turnPage.queryModal(sql, AssignGrid);
  }
  
}

function  allstart()
{
   if(!checkplan())
    return false;
    
    var sqlid831163933="DSHomeContSql831163933";
var mySql831163933=new SqlClass();
mySql831163933.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831163933.setSqlId(sqlid831163933);//指定使用的Sql的id
mySql831163933.addSubPara();//指定传入的参数
var sql=mySql831163933.getString();

    
//   var sql = "select count(*) from ldassignplan where state ='1'";
   var tCount = easyExecSql(sql);
   if(tCount<=0)
    {
       alert("所有分配计划都已经启动，没有终止的计划！");
       return false;
    }
    return true;
}

function selsome()
{
  var checkedRowNum = 0 ;
  var rowNum = AssignGrid.mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  {
  	if(AssignGrid.getChkNo(i)) {
  	   checkedRowNum = checkedRowNum + 1;
      }
  }
  if(checkedRowNum<1)
    {
       alert("请在您要修改的记录前面打[√]");
       return false;
    }
    return true;
}

function allstop()
{
   if(!checkplan())
    return false;
    
    var sqlid831164006="DSHomeContSql831164006";
var mySql831164006=new SqlClass();
mySql831164006.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831164006.setSqlId(sqlid831164006);//指定使用的Sql的id
mySql831164006.addSubPara();//指定传入的参数
var sql=mySql831164006.getString();
    
//   var sql = "select count(*) from ldassignplan where state = '0' ";
   var tCount = easyExecSql(sql);
   if(tCount <=0)
   {
      alert("所有分配计划都已经终止，目没有启动的计划！");
      return false;
   }
   return true;
}

function checkplan()
{
  var sqlid831164035="DSHomeContSql831164035";
var mySql831164035=new SqlClass();
mySql831164035.setResourceName("app.SelAssignDutySql");//指定使用的properties文件名
mySql831164035.setSqlId(sqlid831164035);//指定使用的Sql的id
mySql831164035.addSubPara();//指定传入的参数
var sql=mySql831164035.getString();
  
//  var sql = "select count(*) from ldassignplan ";
  var tCount = easyExecSql(sql);
  if(tCount<=0)
   {
      alert("目前没有任何计划！");
      return false;
   }
   return true;
}

function displayData(tStrArr)
{ 
	//showInfo.close();
	
  if(tStrArr == "0|0")
  {
  	alert("查询无记录！");
    return true;
  }
  alert(123);
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
  turnPage.pageDisplayGrid = AssignGrid;

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
/*  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }*/
  //必须将所有数据设置为一个数据块  
} 
