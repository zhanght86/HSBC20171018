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
function UWAssignSubmit()
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


function UWAssignUpdate()
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
 
function UWStartSel()
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
function UWStartAll()
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
function UWStopSel()
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
function UWStopAll()
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

function allstop()
{
   if(!checkplan())
    return false;
   
   var sqlid831162730="DSHomeContSql831162730";
var mySql831162730=new SqlClass();
mySql831162730.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831162730.setSqlId(sqlid831162730);//指定使用的Sql的id
mySql831162730.addSubPara();//指定传入的参数
var sql=mySql831162730.getString();
    
//   var sql = "select count(*) from ldassignplan where state = '0' ";
   var tCount = easyExecSql(sql);
   if(tCount <=0)
   {
      alert("所有分配计划都已经终止，目没有启动的计划！");
      return false;
   }
   return true;
}




function InitAssign()
{
   //初始化相关的数据
   var sqlid831162834="DSHomeContSql831162834";
var mySql831162834=new SqlClass();
mySql831162834.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831162834.setSqlId(sqlid831162834);//指定使用的Sql的id
mySql831162834.addSubPara();//指定传入的参数
var sqluwamount=mySql831162834.getString();
   
//   var sqluwamount = "select count(*) from lwmission where activityid = '0000001100' and defaultoperator is null ";
   document.all('UWPolSum').value = easyExecSql(sqluwamount);
  //时间初始化
  var sqlid831162920="DSHomeContSql831162920";
var mySql831162920=new SqlClass();
mySql831162920.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831162920.setSqlId(sqlid831162920);//指定使用的Sql的id
mySql831162920.addSubPara();//指定传入的参数
var sqltime=mySql831162920.getString();
  
//  var sqltime = "select taskstarttime,taskendtime from ldassignplan where rownum='1' and activityid in ('0000001100') ";
  var arrResult = easyExecSql(sqltime);
  if(arrResult !=null)
  {
     document.all('StartTime').value = arrResult[0][0];
     document.all('EndTime').value = arrResult[0][1];
  }
  
  //mulLine初始化
//  var sql = "  select distinct(a.assignno) ,'操作员姓名' ,"
//           +"  nvl((select b.planamount from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),0),"
//           +"  nvl((select c.planamount from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),0),"
//           +"  nvl((select d.planamount from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),0),"
//           +"  a.state "
//           +"  from ldassignplan a order by a.assignno ";
  
  var sqlid831163016="DSHomeContSql831163016";
var mySql831163016=new SqlClass();
mySql831163016.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831163016.setSqlId(sqlid831163016);//指定使用的Sql的id
mySql831163016.addSubPara();//指定传入的参数
var sqlAssign=mySql831163016.getString();
  
//  var sqlAssign = " select a.assignno,b.username,b.uwpopedom,'1',a.planamount,nvl((select actuallyamount from ldautoassign where taskno = a.taskno and activityid = a.activityid and assignno = a.assignno),0),a.state from ldassignplan a,lduser b where  a.activityid in ('0000001100') and b.usercode=a.assignno and b.usercode in (select usercode from lduwuser) order by a.assignno ";
  
  //查询成功则拆分字符串，返回二维数组
  turnPage1.queryModal(sqlAssign, AssignGrid);
  //判断是否查询成功
  if (!turnPage1.strQueryResult) {
    AssignGrid.clearData("AssignGrid");  	
    alert("没有查询到数据！");
    return false;
  }
  
  //判断能够新增
  var sqlid831163105="DSHomeContSql831163105";
var mySql831163105=new SqlClass();
mySql831163105.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831163105.setSqlId(sqlid831163105);//指定使用的Sql的id
mySql831163105.addSubPara();//指定传入的参数
var sqlPlan=mySql831163105.getString();

  
//  var sqlPlan = "select distinct 1 from ldassignplan where activityid in ('0000001100')";
  var Exists = easyExecSql(sqlPlan);
  if(Exists=="1"){
    //有关于异常件、问题件修改或人工合并的分配计划 所以只能通过修改来提交分配计划
    fm.addNew.disabled = true;
  }else{
    fm.addNew.disabled = false;
  } 
}

function afterCodeSelect(cCodeName, Field)
{
   if(cCodeName =="uwuser")
   //alert(12312);
	if(cCodeName=="uwtype"||cCodeName=="contype")
	{
		fm.ActivityID.value="";
	}
  if (cCodeName == "uwtype") 
  {
       getActivityID("uwtype");
  }
   if(cCodeName == "contype")
   {
     getActivityID("contype");
   }
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

function  allstart()
{
   if(!checkplan())
    return false;
    
    var sqlid831163141="DSHomeContSql831163141";
var mySql831163141=new SqlClass();
mySql831163141.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831163141.setSqlId(sqlid831163141);//指定使用的Sql的id
mySql831163141.addSubPara();//指定传入的参数
var sql=mySql831163141.getString();
    
//   var sql = "select count(*) from ldassignplan where state ='1'";
   var tCount = easyExecSql(sql);
   if(tCount<=0)
    {
       alert("所有分配计划都已经启动，没有终止的计划！");
       return false;
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

function checkplan()
{
  var sqlid831163238="DSHomeContSql831163238";
var mySql831163238=new SqlClass();
mySql831163238.setResourceName("app.SelUWAssignDutySql");//指定使用的properties文件名
mySql831163238.setSqlId(sqlid831163238);//指定使用的Sql的id
mySql831163238.addSubPara();//指定传入的参数
var sql=mySql831163238.getString();
  
//  var sql = "select count(*) from ldassignplan ";
  var tCount = easyExecSql(sql);
  if(tCount<=0)
   {
      alert("目前没有任何计划！");
      return false;
   }
   return true;
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
