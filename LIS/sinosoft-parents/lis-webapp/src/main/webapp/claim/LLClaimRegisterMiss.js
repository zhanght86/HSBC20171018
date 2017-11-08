//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var  mySql=new SqlClass();	 //for i18n

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
        //直接取得数据跳转到立案界面
        var i = LLClaimRegisterGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimRegisterGrid.getRowColData(i,1);
            var tClmState = LLClaimRegisterGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = LLClaimRegisterGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimRegisterGrid.getRowColData(i,8);
            location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
  	var backstr=document.all("ContNo").value;
  	//alert(backstr+"backstr");
  	mSwitch.deleteVar("ContNo");
	mSwitch.addVar("ContNo", "", backstr);
	mSwitch.updateVar("ContNo", "", backstr);
  	location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("开发中")
    return;
	var selno = SelfPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}

	var MissionID = SelfPolGrid.getRowColData(selno, 6);
	var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
	var ActivityID = SelfPolGrid.getRowColData(selno, 8);
	var PrtNo = SelfPolGrid.getRowColData(selno, 2);
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}
	var NoType = "1";

	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

}

//显示报案页面
function newReport()
{
    location.href="LLClaimRegisterInput.jsp?isNew=0";
}

// 初始化表格1
//function queryGrid()
//{
//	initSelfLLClaimRegisterGrid();
//    /* //for i18n
//    var strSQL = "";
//           
//	strSQL = "select missionprop1,'',missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
//		   +"  (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,missionprop21 from lwmission where 1=1 "
//           + " and activityid = '0000005015'"
//           + " and processid = '0000000005'"
//           + " and DefaultOperator = '" + fm.Operator.value +"'"
//           + getWherePart( 'missionprop1' ,'RptNo')
//           + getWherePart( 'missionprop3' ,'CustomerNo')
//           + getWherePart( 'missionprop5' ,'customerSex')
//           + getWherePart( 'missionprop6' ,'AccidentDate')
//	
//	*/
//	 mySql=new SqlClass();	 
//	 mySql.setResourceName("claim.LLClaimRegisterMissInputSql");
//	 mySql.setSqlId("LLClaimRegisterMissSql1");
//	 mySql.addSubPara(fm.Operator.value);
//	 mySql.addSubPara(fm.RptNo.value);
//	 mySql.addSubPara(fm.CustomerNo.value);
//	 mySql.addSubPara(fm.customerSex.value);
//	 mySql.addSubPara(fm.AccidentDate.value);
//	 	
//    if (!(fm.CustomerName.value == "" || fm.CustomerName.value == null))
//    {
//    
// 	   //strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
// 	     mySql=new SqlClass();	 
//		 mySql.setResourceName("claim.LLClaimRegisterMissInputSql");
//		 mySql.setSqlId("LLClaimRegisterMissSql2");
//		 mySql.addSubPara(fm.Operator.value);
//		 mySql.addSubPara(fm.RptNo.value);
//		 mySql.addSubPara(fm.CustomerNo.value);
//		 mySql.addSubPara(fm.customerSex.value);
//		 mySql.addSubPara(fm.AccidentDate.value);
//		 mySql.addSubPara(fm.CustomerName.value);
// 	   
//    }
//    
//    //将以下这句直接添加到上述sql语句中
//    //strSQL = strSQL + " order by missionprop21,missionprop1 ";
//
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimRegisterGrid);
//    HighlightByRow();
//}
//
//// 初始化表格2
//function querySelfGrid()
//{
// //   var CurDate = fm.CurDate.value;//当前时间
//    initSelfLLClaimRegisterGrid();
//    /*
//    var strSQL = "";
//	strSQL = "select missionprop1,'',missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
//		   +" (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,missionprop21 from lwmission where 1=1 "
//        + " and activityid = '0000005015'"
//        + " and processid = '0000000005'"
//        + " and DefaultOperator = '" + fm.Operator.value
//        + "' order by missionprop21,missionprop1";
//     */   
//	 mySql=new SqlClass();	 
//	 mySql.setResourceName("claim.LLClaimRegisterMissInputSql");
//	 mySql.setSqlId("LLClaimRegisterMissSql3");
//	 mySql.addSubPara(fm.Operator.value);
//		 
//    //prompt("立案初始界面查询",strSQL);
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimRegisterGrid);
//    HighlightByRow();
//}

////若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimRegisterGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimRegisterGrid.getRowColData(i,12); //受理日期    	
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//			  SelfLLClaimRegisterGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimRegisterGrid点击事件
function LLClaimRegisterGridClick()
{
}

////SelfLLClaimRegisterGrid点击事件，选择需要立案的案件所响应的函数
//function SelfLLClaimRegisterGridClick()
//{
//	  HighlightByRow();
//    var i = SelfLLClaimRegisterGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
//        var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
//        tClmState = tClmState.substring(0,2);
//        var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
//        var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
//        location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
//    }
//}

//SelfLLClaimRegisterGrid点击事件，选择需要立案的案件所响应的函数  modify by lzf 2013-5-16
function SelfLLClaimRegisterGridClick()
{
	  HighlightByRow();
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tClmState = PrivateWorkPoolGrid.getRowColData(i,6);
        tClmState = tClmState.substring(0,2);
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,13);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,14);
        var tActivityID = PrivateWorkPoolGrid.getRowColData(i,16);
        alert(tActivityID);
        location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
    }
}

//若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,11); //受理日期    	
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}
//end by lzf
//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}