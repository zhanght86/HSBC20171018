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
        var i = LLClaimConfirmGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimConfirmGrid.getRowColData(i,1);
            var tClmState = LLClaimConfirmGrid.getRowColData(i,2);
            var tMissionID = LLClaimConfirmGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimConfirmGrid.getRowColData(i,8);
            var tBudgetFlag = LLClaimConfirmGrid.getRowColData(i,10);
            var tAuditPopedom = LLClaimConfirmGrid.getRowColData(i,11);
            var tAuditer = LLClaimConfirmGrid.getRowColData(i,12);
            location.href="LLClaimConfirmInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&Auditer="+tAuditer;
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
    //---------------------------------------------------------------------------BEG2005-8-8 15:05
    //查询操作人审批权限
    //---------------------------------------------------------------------------
   /* var tSql = "select substr(a.UWLevel,2,2) from LLClaimUser a where 1=1 "
             + " and a.UWFlag = '1' "
             + " and a.usercode = '" + fm.Operator.value + "'"*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimConfirmMissInputSql");
	mySql.setSqlId("LLClaimConfirmMissSql1");
	mySql.addSubPara(fm.Operator.value ); 
    var tCheckLevel = easyExecSql(mySql.getString());
    //---------------------------------------------------------------------------End
    
    //---------------------------------------------------------------------------BEG2005-8-14 16:31
    //查询操作人机构层次,按comcode
    //---------------------------------------------------------------------------
    var tRank = "";
    var tComCode = fm.ComCode.value;
    if (tComCode.length == 2)
    {
        tRank = "1";//总公司
    }
    else
    {
        if (tComCode.length == 4)
        {
            tRank = "2";//分公司
        }
    }
    //---------------------------------------------------------------------------End
    
    initLLClaimConfirmGrid();
    var strSQL = "";
    //--------------------1------------------------------------------------------------------2----------------------------------------------------------3------------4----------------------------------------5-------------------------------------------------------6-----------7--------8------------9----------10------------11------------12--------
    /*strSQL = "select missionprop1,(case missionprop2 when '10' then '报案' when '20' then '立案'  when '30' then '审核' when '40' then '审批' end),"
       +" missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
       +" missionprop6,missionid,submissionid,activityid,MissionProp11,MissionProp12,"
       +" MissionProp13,missionprop10 ,MakeDate,(select UserName from LLClaimUser where trim(UserCode) = lwmission.MissionProp13),missionprop20," 
       + " (select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0') "
       +" from lwmission where 1=1 "
           + " and activityid = '0000009055'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')*/
/*
           + " and (missionprop20 = '" + fm.ManageCom.value + "'"  //本级
           + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //本级已下
           + " and missionprop18 = '" + tRank + "'))"  
           + " order by missionprop10 desc,missionid ";
*/
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLClaimConfirmMissInputSql");
		mySql.setSqlId("LLClaimConfirmMissSql12");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.CustomerName.value ); 
		mySql.addSubPara(fm.customerSex.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
         if(fm.ManageCom.value.length >= 2){
        // strSQL += " and missionprop20 like '" + fm.ManageCom.value + "%%'" 
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLClaimConfirmMissInputSql");
		mySql.setSqlId("LLClaimConfirmMissSql13");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.CustomerName.value ); 
		mySql.addSubPara(fm.customerSex.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
		mySql.addSubPara(fm.ManageCom.value ); 
         }
         //strSQL += " order by missionprop10 desc,missionid ";
         
    //prompt("团险审批管理共享池查询",strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimConfirmGrid);
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLClaimConfirmGrid();
    var strSQL = "";
    var tManageCom = fm.ManageCom.value;
    var tOperator  = fm.Operator.value;
    strSQL = "select missionprop1,(case missionprop2 when '10' then '报案' when '20' then '立案'  when '30' then '审核' when '40' then '审批' end),"
       +" missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
       +" missionprop6,missionid,submissionid,activityid,MissionProp11,MissionProp12,"
       +" MissionProp13,missionprop10,MakeDate,(select UserName from LLClaimUser where trim(UserCode) = lwmission.MissionProp13),missionprop20,"
       + " (select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'), " 
       + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
       + " and activityid = '0000009055'"
       + " and processid = '0000000009'"
       + " and DefaultOperator = '" + tOperator+ "'";
      /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLClaimConfirmMissInputSql");
		mySql.setSqlId("LLClaimConfirmMissSql14");
		mySql.addSubPara(tOperator); */
       if(fm.ManageCom.value.length >= 2){
       strSQL += " and missionprop20 like '"+tManageCom+"%%'"
       /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLClaimConfirmMissInputSql");
		mySql.setSqlId("LLClaimConfirmMissSql15");
		mySql.addSubPara(tOperator); 
		mySql.addSubPara(tManageCom); */
       }
       strSQL += " order by AcceptedDate,missionprop10 desc,missionid ";
       
    //prompt("团险审批管理任务队列查询",strSQL);
    turnPage2.queryModal(strSQL,SelfLLClaimConfirmGrid);
    
    HighlightByRow();
}

//SelfLLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<SelfLLClaimConfirmGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimConfirmGrid.getRowColData(i,18); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   SelfLLClaimConfirmGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//LLClaimConfirmGrid点击事件
function LLClaimConfirmGridClick()
{
}

//SelfLLClaimConfirmGrid点击事件
function SelfLLClaimConfirmGridClick()
{
	HighlightByRow();
    var i = SelfLLClaimConfirmGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLClaimConfirmGrid.getRowColData(i,1);
        var tClmState = SelfLLClaimConfirmGrid.getRowColData(i,2);
        var tMissionID = SelfLLClaimConfirmGrid.getRowColData(i,7);
        var tSubMissionID = SelfLLClaimConfirmGrid.getRowColData(i,8);
        var tBudgetFlag = SelfLLClaimConfirmGrid.getRowColData(i,10);
        var tAuditPopedom = SelfLLClaimConfirmGrid.getRowColData(i,11);
        var tAuditer = SelfLLClaimConfirmGrid.getRowColData(i,12);
        location.href="LLClaimConfirmInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&Auditer="+tAuditer;
    }
}

//[申请]按钮
function ApplyClaim()
{
    var selno = LLClaimConfirmGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("请选择要申请处理的报案！");
          return;
    }
    fm.MissionID.value = LLClaimConfirmGrid.getRowColData(selno, 7);
    fm.SubMissionID.value = LLClaimConfirmGrid.getRowColData(selno, 8);
    fm.ActivityID.value = LLClaimConfirmGrid.getRowColData(selno, 9);
    
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