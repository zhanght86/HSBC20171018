//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="1";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var mySql = new SqlClass();
//提交完成后所作操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    tSaveFlag ="0";
}

//初始化"获取调查任务队列"表格
//function easyQueryClick()
//{
//  /*  var strSQL = "";
//    //missionprop7为“指定的调查人”，用来获取队列，现在还没用该条件
//    //可能存在任务重新分配的情况，用DefaultOperator来查询准确，Modify by zhaorx 2006-03-11
//	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,"
//	       + "(select username from lduser where usercode = DefaultOperator),missionid,submissionid,activityid,"
//	       +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 " 	
//           + " and activityid in ('0000005145','0000009145') "
//           + " and processid in ('0000000005','0000000009')"
////           + " and missionprop7 = '" + fm.Operator.value+"' "
//           + " and DefaultOperator = '" + fm.Operator.value + "'" 
//           + " order by AcceptedDate,missionprop1";*/
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLInqCourseMissInputeSql");
//	mySql.setSqlId("LLInqCourseMissSql1");
//	mySql.addSubPara(fm.Operator.value ); 
//    turnPage.pageLineNum=5;    //每页5条
//    turnPage.queryModal(mySql.getString(),LLInqApplyGrid);
//    HighlightByRow();
//}

//若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<LLInqApplyGrid.mulLineCount; i++){
//    	var accepteddate = LLInqApplyGrid.getRowColData(i,11); //受理日期
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//    		   LLInqApplyGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}



//LLInqApplyGrid点击事件
//function LLInqApplyGridClick()
//{
//    HighlightByRow();
//    var i = LLInqApplyGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLInqApplyGrid.getRowColData(i,1);
//        var tInqNo = LLInqApplyGrid.getRowColData(i,2); 
//        var tMissionID = LLInqApplyGrid.getRowColData(i,8);
//        var tSubMissionID = LLInqApplyGrid.getRowColData(i,9);
//        var tActivityid = LLInqApplyGrid.getRowColData(i,10);
//        
//    }
//    var strUrl= "LLInqCourseInpute.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
//    //location.href="LLInqCourseInpute.jsp?";
//    location.href=strUrl;
//}

//modify by lzf 
function LLInqApplyGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tInqNo = PrivateWorkPoolGrid.getRowColData(i,2); 
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,12);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,13);
        var tActivityid = PrivateWorkPoolGrid.getRowColData(i,15);
        
    }
    var strUrl= "LLInqCourseInpute.jsp?ClmNo="+tClmNo+"&InqNo="+tInqNo+"&Activityid="+tActivityid+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
    //location.href="LLInqCourseInpute.jsp?";
    location.href=strUrl;
}