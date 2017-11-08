//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//提交完成后所作操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //直接取得数据跳转到立案界面
        var i = LLClaimEndCaseGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimEndCaseGrid.getRowColData(i,1);
            var tClmState = LLClaimEndCaseGrid.getRowColData(i,2);
            var tMissionID = LLClaimEndCaseGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimEndCaseGrid.getRowColData(i,8);
            location.href="LLClaimEndCaseInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
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

// 初始化表格1
function queryGrid()
{
    initLLClaimEndCaseGrid();
    var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '40' then '审批' when '50' then '结案' end),missionprop3,missionprop4,"
		   //+ "(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
		   + " missionprop5,"
		   + " missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,"
		   + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           + " and activityid = '0000009075'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + " and missionprop20 like '" + fm.ManageCom.value + "%'"
           + " order by AcceptedDate,missionprop1";
//    prompt("",strSQL);
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseMissInputSql");
	mySql.setSqlId("LLClaimEndCaseMissSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.ClmState.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.CustomerName.value ); 
	mySql.addSubPara(fm.customerSex.value ); 
	mySql.addSubPara(fm.AccidentDate2.value ); 
	mySql.addSubPara(fm.ManageCom.value ); */
    turnPage.queryModal(strSQL,LLClaimEndCaseGrid);
    HighlightByRow();
}

//LLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<LLClaimEndCaseGrid.mulLineCount; i++){
    	var accepteddate = LLClaimEndCaseGrid.getRowColData(i,12); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   LLClaimEndCaseGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLClaimEndCaseGrid();
    var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '40' then '审批' when '50' then '结案' end),missionprop3,missionprop4,"
		   //+ " (case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
		   + " missionprop5,"
		   + " missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20,"
		   + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           + " and activityid = '0000009075'"
           + " and processid = '0000000009'"
           + " and DefaultOperator = '" + fm.Operator.value
           + "' order by AcceptedDate,missionprop1";
    /*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimEndCaseMissInputSql");
	mySql.setSqlId("LLClaimEndCaseMissSql2");
	mySql.addSubPara(fm.Operator.value );   */     
//    alert(strSQL);
    turnPage2.queryModal(strSQL,SelfLLClaimEndCaseGrid);
    HighlightSelfByRow();
}


//SelfLLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightSelfByRow(){
    for(var i=0; i<SelfLLClaimEndCaseGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimEndCaseGrid.getRowColData(i,12); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   SelfLLClaimEndCaseGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//LLClaimEndCaseGrid点击事件
function LLClaimEndCaseGridClick()
{
	HighlightByRow();
}

//SelfLLClaimEndCaseGrid点击事件
function SelfLLClaimEndCaseGridClick()
{
    HighlightSelfByRow();
    var i = SelfLLClaimEndCaseGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimEndCaseGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimEndCaseGrid.getRowColData(i,2);
        var tMissionID = SelfLLClaimEndCaseGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLClaimEndCaseGrid.getRowColData(i,8);
        location.href="LLClaimEndCaseInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

//[申请]按钮
function ApplyClaim()
{
	var selno = LLClaimEndCaseGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.MissionID.value = LLClaimEndCaseGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimEndCaseGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimEndCaseGrid.getRowColData(selno, 9);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //提交
}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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