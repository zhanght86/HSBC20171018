var showInfo;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;         
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;     
  }
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //alert(parent.opener.name);
  //ctrlButtonDisabled(fm.GrpContNo.value);
  parent.close();
}

//显示保险计划变更结论
function saveChangeResult()
{
	var cContNo = fm.GrpContNo.value; //团单号
	var cChangeResult = fm.ChangeIdea.value; //核保要求
	var cEdorNo=fm.EdorNo.value; //批单号
	var cMissionID=fm.MissionID.value; //任务ID
	var cPrtNo=fm.PrtNo.value;//印刷号
	
	if (trim(cChangeResult) == "")
	{
		alert("没有录入结论!");
		fm.ChangeIdea.focus();
		return;
	}
	
	var sqlid824101103="DSHomeContSql824101103";
var mySql824101103=new SqlClass();
mySql824101103.setResourceName("uw.UWGChangeResultInputSql");//指定使用的properties文件名
mySql824101103.setSqlId(sqlid824101103);//指定使用的Sql的id
mySql824101103.addSubPara(cContNo);//指定传入的参数
mySql824101103.addSubPara(cMissionID);//指定传入的参数
var sSQL=mySql824101103.getString();

//	var sSQL = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + cContNo +"'"	
//				 + " and LWMission.ActivityID = '0000002301'"
//				 + " and LWMission.ProcessID = '0000000004'"
//				 + " and LWMission.MissionID = '" + cMissionID +"'";
//==============add========liuxiaosong==========2006-11-15==============start=============

   //如批单号不为空，则为保全业务
   //2301的missionprop1为prtNo啊！！新契约的prtno和grpcontno一样？？
  
   if(!(cEdorNo=="" || cEdorNo==null || cEdorNo=="null")){
   	
   	var sqlid824101217="DSHomeContSql824101217";
var mySql824101217=new SqlClass();
mySql824101217.setResourceName("uw.UWGChangeResultInputSql");//指定使用的properties文件名
mySql824101217.setSqlId(sqlid824101217);//指定使用的Sql的id
mySql824101217.addSubPara(cPrtNo);//指定传入的参数
mySql824101217.addSubPara(cMissionID);//指定传入的参数
sSQL=mySql824101217.getString();
   	
//   		sSQL = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"	
//				 + " and LWMission.ActivityID = '0000002301'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" + cMissionID +"'";
   }
//==============add========liuxiaosong==========2006-11-15==============start=============
   //alert(sSQL);

   var arr=easyExecSql(sSQL);
   if(arr==null)
   {
      alert("查询团单新契约发送核保要求通知书节点数据失败!");	
      return;
   }
   fm.SubMissionID.value=arr[0][0];

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
  	fm.action = "./GrpUWChangPlanChk.jsp";
  	fm.submit(); //提交
}