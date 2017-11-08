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
        var i = LLClaimSimpleGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimSimpleGrid.getRowColData(i,1);
            var tClmState = LLClaimSimpleGrid.getRowColData(i,2);
            var tMissionID = LLClaimSimpleGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,8);
            location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
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
    initLLClaimSimpleGrid();
   /* var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005065'"
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + " and missionprop20 like '" + fm.ManageCom.value + "%%'"
           + " order by missionprop1";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimSimpleMissInputSql");
mySql.setSqlId("LLClaimSimpleMissSql1");
mySql.addSubPara(fm.RptNo.value );   
mySql.addSubPara(fm.ClmState.value );   
mySql.addSubPara(fm.CustomerNo.value );   
mySql.addSubPara(fm.CustomerName.value );   
mySql.addSubPara(fm.customerSex.value );   
mySql.addSubPara(fm.AccidentDate2.value );   
mySql.addSubPara(fm.ManageCom.value );   
//    alert(strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimSimpleGrid);
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLClaimSimpleGrid();
   /* var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005065'"
           + " and processid = '0000000005'"
           + " and DefaultOperator = '" + fm.Operator.value
           + "' order by missionprop1";*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimSimpleMissInputSql");
mySql.setSqlId("LLClaimSimpleMissSql2");
mySql.addSubPara(fm.Operator.value );       
//    alert(strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLClaimSimpleGrid);
}

//LLClaimSimpleGrid点击事件
function LLClaimSimpleGridClick()
{
}

//SelfLLClaimSimpleGrid点击事件
function SelfLLClaimSimpleGridClick()
{
    var i = SelfLLClaimSimpleGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimSimpleGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimSimpleGrid.getRowColData(i,2);
        var tMissionID = SelfLLClaimSimpleGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLClaimSimpleGrid.getRowColData(i,8);
        location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

//[申请]按钮
function ApplyClaim()
{
	var selno = LLClaimSimpleGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.MissionID.value = LLClaimSimpleGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimSimpleGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimSimpleGrid.getRowColData(selno, 9);
	
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

    fm.submit(); //提交
    tSaveFlag ="0";
}