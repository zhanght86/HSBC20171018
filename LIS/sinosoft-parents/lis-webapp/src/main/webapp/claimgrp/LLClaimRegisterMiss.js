//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();

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
function queryGrid()
{
    initLLClaimRegisterGrid();
    var strSQL = "";
    //----------------报案号------------------------------------------------------------报案状态-------------------------------------------出险人编码---出险人姓名---出险人性别---出险日期
	strSQL = "select missionprop1,(case missionprop2 when '10' then '10-已报案' when '20' then '20-已立案' when '25' then '20-延迟立案' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005015'"
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + " and missionprop5 is not null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
//           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           //如果输入出险人姓名则在全国范围内查找
           if (fm.CustomerName.value == "" || fm.CustomerName.value == null)
           {
               strSQL = strSQL + " and missionprop20 like '" + fm.ManageCom.value + "'"
           }
           else
           {
               strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
           }
    strSQL = strSQL + " order by missionprop2,missionid";
//    alert(strSQL);
    turnPage.queryModal(strSQL,LLClaimRegisterGrid);
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLClaimRegisterGrid();
    var strSQL = "";
	strSQL = "select missionprop1,(case missionprop2 when '10' then '10-已报案' when '20' then '20-已立案' when '25' then '20-延迟立案' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(select UserName from LLClaimUser where UserCode = lwmission.lastoperator),missionprop20 from lwmission where 1=1 "
           + " and activityid = '0000005015'"
           + " and processid = '0000000005'"
           + " and missionprop5 is not null "
           + " and DefaultOperator = '" + fm.Operator.value
           + "' order by missionprop2,missionid";
//    alert(strSQL);
    turnPage2.queryModal(strSQL,SelfLLClaimRegisterGrid);
}

//LLClaimRegisterGrid点击事件
function LLClaimRegisterGridClick()
{
}

//SelfLLClaimRegisterGrid点击事件
function SelfLLClaimRegisterGridClick()
{
    var i = SelfLLClaimRegisterGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
        tClmState = tClmState.substring(0,2);
        var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
        location.href="LLClaimRegisterInput.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    }
}

//[申请]按钮
function ApplyClaim()
{
	var selno = LLClaimRegisterGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);
	
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