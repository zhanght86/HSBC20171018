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

////初始化"接收呈报队列"表格
//function easyQueryClick()
//{
//   /* var strSQL = "";
//    //strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionid,submissionid,activityid from Lwmission where activityid = '0000005105' and processid = '0000000005' and missionprop6 = '1'";
//	strSQL = "select missionprop1,missionprop2,missionprop3,missionprop4,missionprop5,missionprop6,missionprop7,missionid,submissionid,activityid from lwmission where 1=1 "
//           + " and (activityid = '0000005105' or activityid = '0000009105')"
//           + " and (processid = '0000000005' or processid = '0000000009')"
//           + " and missionprop6 = '1'"
//           + " order by missionprop1";*/
//    
//	mySql = new SqlClass();
//	mySql.setResourceName("claim.LLSubmitApplyHeadDealMissInputSql");
//	mySql.setSqlId("LLSubmitApplyHeadDealMissSql1");
//	mySql.addSubPara("1");
//	
//    turnPage.pageLineNum=5;    //每页5条
//    turnPage.queryModal(mySql.getString(),LLSubmitApplyGrid);
//}


//LLSubmitApplyGrid点击事件
//function LLSubmitApplyGridClick()
//{
//    var i = LLSubmitApplyGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLSubmitApplyGrid.getRowColData(i,1);
//        var tSubNo = LLSubmitApplyGrid.getRowColData(i,2); 
//        var tSubCount=LLSubmitApplyGrid.getRowColData(i,3);  
//        var tMissionID = LLSubmitApplyGrid.getRowColData(i,8);
//        var tSubMissionID = LLSubmitApplyGrid.getRowColData(i,9);
//    }
//    var strUrl= "LLSubmitApplyHeadDealInput.jsp?ClmNo="+tClmNo+"&SubNo="+tSubNo+"&SubCount="+tSubCount+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;    
//    //location.href="LLSubmitApplyDealInput.jsp?";
//    location.href=strUrl;
//}
function LLSubmitApplyGridClick()
{
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tSubNo = PrivateWorkPoolGrid.getRowColData(i,2); 
        var tSubCount=PrivateWorkPoolGrid.getRowColData(i,3);  
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,10);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,11);
        var tActivityID = PrivateWorkPoolGrid.getRowColData(i,13);
    }
    var strUrl= "LLSubmitApplyHeadDealInput.jsp?ClmNo="+tClmNo+"&SubNo="+tSubNo+"&SubCount="+tSubCount+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;    
    //location.href="LLSubmitApplyDealInput.jsp?";
    location.href=strUrl;
}
