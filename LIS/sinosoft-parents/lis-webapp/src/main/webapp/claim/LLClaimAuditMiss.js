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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
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
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //直接取得数据跳转到立案界面
        var i = LLClaimAuditGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimAuditGrid.getRowColData(i,1);
            var tClmState = LLClaimAuditGrid.getRowColData(i,2);
            var tMissionID = LLClaimAuditGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimAuditGrid.getRowColData(i,8);
            var tBudgetFlag = LLClaimAuditGrid.getRowColData(i,14);
            var tAuditPopedom = LLClaimAuditGrid.getRowColData(i,13);
            location.href="LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
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
    //查询操作人审核权限
    //---------------------------------------------------------------------------
    /*
    var tSql = "select substr(a.checklevel,2,2) from LLClaimUser a where 1=1 "
             + " and a.checkflag = '1' "
             + " and a.usercode = '" + fm.Operator.value + "'"
*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql1");
	mySql.addSubPara( fm.Operator.value );

    var tCheckLevel = easyExecSql(mySql.getString());
//    var tLevel = parseInt(tCheckLevel);
//    alert(tLevel);
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
    
    initLLClaimAuditGrid();
    var strSQL = "";
    //---------------------1-------------------------------------------2-----------------------------------3---------------------4-----------------------------------------------------5-----------------------------------------6----------7-----------8----------9-------------------------------------------10--------------------------------------------------------------------------11---------------------------------------------12------------------------------------------------------------------------------------13-----------------------------------14
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),(case MissionProp7 when '1' then '个人' when '2' then '团体' end),(case MissionProp12 when 'A' then '立案' when 'B' then '审批金额为正' when 'C' then '审批金额为负' when 'D' then '简易案件' end),MissionProp10,MissionProp11 from lwmission where 1=1 "
	/*
	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),"
		   + "missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
		   + "missionprop6,missionid,submissionid,activityid,"
		   + "(case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),"
		   + "(case MissionProp7 when '1' then '个人' when '2' then '团体' end),"
		   + "(case MissionProp12 when 'A' then '立案' when 'B' then '审批金额为正' when 'C' then '审批金额为负' when 'D' then '简易案件' end),"
		   + "MissionProp10,MissionProp11,MakeDate,"
//		   + " (select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = lwmission.missionprop1)),"
		   + " (select UserName from LLClaimUser where UserCode = (select defaultoperator from lbmission where activityid = '0000005015' and processid = '0000000005' and missionprop1 = lwmission.missionprop1 and rownum = 1)),"
		   + " missionprop20 from lwmission where 1=1 "  
           + " and activityid = '0000005035'"   
           + " and processid = '0000000005'"
           + " and DefaultOperator is null "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop4' ,'CustomerName','like')
           + getWherePart( 'missionprop5' ,'customerSex')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + " and MissionProp19 = '0'"     //没有原审核人
           + " and substr(missionprop10,2,2) <= '" + tCheckLevel + "'" //审核权限判断
           + " and (missionprop20 = '" + fm.ManageCom.value + "'"  //本级
//           + " or (substr(missionprop20,1," + tComCode.length + ") like '" + fm.ManageCom.value + "'"  //本级已下
           + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //本级已下
           + " and missionprop18 = '" + tRank + "'))"
           + " and missionprop8 = '1'"   
           + " order by missionprop1";
    */       
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql2");
	mySql.addSubPara( fm.RptNo.value );
	mySql.addSubPara( fm.ClmState.value );
	mySql.addSubPara( fm.CustomerNo.value );
	mySql.addSubPara( fm.CustomerName.value );
	mySql.addSubPara( fm.customerSex.value );
	mySql.addSubPara( fm.AccidentDate2.value );
	mySql.addSubPara( tCheckLevel );
	mySql.addSubPara( fm.ManageCom.value );
	mySql.addSubPara( fm.ManageCom.value );
	mySql.addSubPara( tRank  );
	
	
    turnPage.queryModal(mySql.getString(),LLClaimAuditGrid);
//    alert(strSQL);
}

// 初始化表格2
//function querySelfGrid()
//{
//    initSelfLLClaimAuditGrid();
//    var strSQL = "";
////	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),missionprop6,missionid,submissionid,activityid,(case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),(case MissionProp7 when '1' then '个人' when '2' then '团体' end),(case MissionProp12 when 'A' then '立案' when 'B' then '审批金额为正' when 'C' then '审批金额为负' when 'D' then '简易案件' end),MissionProp10,MissionProp11 from lwmission where 1=1 "
//	/*
//	strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),"
//		   +" missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
//		   +" (select rgtdate from llregister where rgtno=missionprop1),missionid,submissionid,activityid,"
//		   +" (case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),"
//		   +" (case MissionProp7 when '1' then '个人' when '2' then '团体' end),"
//		   +" (case MissionProp12 when 'A' then '立案' when 'B' then '审核' when 'C' then '审批' when 'D' then '简易案件' end),"
//		   +" MissionProp10,MissionProp11,MakeDate,"
////		   +" (select UserName from LLClaimUser where UserCode = (select operator from llregister where trim(rgtno) = lwmission.missionprop1)),"
//		   +" (select UserName from LLClaimUser where UserCode = (select defaultoperator from lbmission where activityid = '0000005015' and processid = '0000000005' and missionprop1 = lwmission.missionprop1 and rownum = 1)),"
//		   +" missionprop20,(select accepteddate from llregister where rgtno=lwmission.missionprop1) Accepteddate,"
//		   +" (Case (select 1 from llaffix where rgtno=lwmission.missionprop1 And Rownum=1)"
//		   +" When 1 Then (Case (select 1 from llaffix where rgtno=lwmission.missionprop1 And (subflag is null or subflag='1') And Rownum=1) When 1 Then '未全部回销' Else '已回销'  End) Else '未发起' End ) from lwmission where 1=1 "  
//           + " and activityid = '0000005035'"
//           + " and processid = '0000000005'"
//           + " and (DefaultOperator = '" + fm.Operator.value + "'"
//           + " or MissionProp19 = '" + fm.Operator.value + "')"
//           + " and missionprop8 = '1'"
//           + " order by AcceptedDate,missionprop1  ";
//    */       
//    mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
//	mySql.setSqlId("LLClaimAuditMissSql3");
//	mySql.addSubPara( fm.Operator.value );
//	mySql.addSubPara( fm.Operator.value );
//	
//	
//    //prompt("审核界面初始化查询",strSQL);
//    turnPage2.queryModal(mySql.getString(),SelfLLClaimAuditGrid);
//    HighlightByRow();
//}

////若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightByRow(){
//    for(var i=0; i<SelfLLClaimAuditGrid.mulLineCount; i++){
//    	var accepteddate = SelfLLClaimAuditGrid.getRowColData(i,18); //受理日期
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//    	    	SelfLLClaimAuditGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimAuditGrid点击事件
function LLClaimAuditGridClick()
{
}

////SelfLLClaimAuditGrid点击事件
//function SelfLLClaimAuditGridClick()
//{
//    HighlightByRow();
//    var i = SelfLLClaimAuditGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = SelfLLClaimAuditGrid.getRowColData(i,1);
//        var tClmState = SelfLLClaimAuditGrid.getRowColData(i,2);
//        var tCustomerno = SelfLLClaimAuditGrid.getRowColData(i,3);
//        
//        var tMissionID = SelfLLClaimAuditGrid.getRowColData(i,7);
//        var tSubMissionID = SelfLLClaimAuditGrid.getRowColData(i,8);
//        var tActivityid = SelfLLClaimAuditGrid.getRowColData(i,9);
//        
//        var tBudgetFlag = SelfLLClaimAuditGrid.getRowColData(i,14);
//        var tAuditPopedom = SelfLLClaimAuditGrid.getRowColData(i,13);
//
//        if (checkAudit(tClmNo))
//        {
//        	//转向审核
//        	location.href="../claim/LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Activityid="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
//            
//        } 
//        else
//        {
//        	//转向预付
//            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tCustomerno+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&RgtClass=1&RgtObj=1";      	
//        }
//    }
//}

//[申请]按钮
function ApplyClaim()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.MissionID.value = LLClaimAuditGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 8);
	fm.ActivityID.value = LLClaimAuditGrid.getRowColData(selno, 9);
	
	fm.action = "./LLReportMissSave.jsp";
	submitForm(); //提交
}

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


/**
 * 2009-01-04
 * zhangzheng
 * 判断是转向预付界面还是审核界面
 * return false 转向预付
 * return true 转向审核
**/
function checkAudit(pClmNo)
{/*
    var strSql ="select * from LLPrepayClaim c"
                + " where c.ClmNo ='" + pClmNo + "'";
  */              
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql4");
	mySql.addSubPara( pClmNo );

	
    //prompt("strSql",strSql);
    var tFlag = easyExecSql(mySql.getString());
  
    if (tFlag)
    {/*
        strSql = "select nvl(sum(d.Pay),0) from ljagetclaim d where othernotype='5'  and feeoperationtype='B'"
                   + " and d.otherno ='" + pClmNo + "'";
        */
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimAuditMissInputSql");
		mySql.setSqlId("LLClaimAuditMissSql5");
		mySql.addSubPara( pClmNo );
	
        //prompt("校验是否可以完成预付操作的sql:",strSql);
        
        tFlag = easyExecSql(mySql.getString());
        
        //alert("tFlag[0][0]:"+tFlag[0][0]);

        //为空表示没有完成
        if (tFlag==null||tFlag[0][0]=='0')
        {
            return false;
        }
    }
     
    return true;
}

//modify by lzf
//若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<PrivateWorkPoolGrid.mulLineCount; i++){
    	var accepteddate = PrivateWorkPoolGrid.getRowColData(i,20); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   PrivateWorkPoolGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}

//SelfLLClaimAuditGrid点击事件
function SelfLLClaimAuditGridClick()
{
    HighlightByRow();
    var i = PrivateWorkPoolGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = PrivateWorkPoolGrid.getRowColData(i,1);
        var tClmState = PrivateWorkPoolGrid.getRowColData(i,2);
        var tCustomerno = PrivateWorkPoolGrid.getRowColData(i,3);
        
        var tMissionID = PrivateWorkPoolGrid.getRowColData(i,22);
        var tSubMissionID = PrivateWorkPoolGrid.getRowColData(i,23);
        var tActivityid = PrivateWorkPoolGrid.getRowColData(i,25);
        
        var tBudgetFlag = PrivateWorkPoolGrid.getRowColData(i,17);//MissionProp11
        var tAuditPopedom = PrivateWorkPoolGrid.getRowColData(i,16);//missionprop10

        if (checkAudit(tClmNo))
        {
        	//转向审核
        	location.href="../claim/LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&Activityid="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&GrpRptNo=&RgtClass=1&RgtObj=1";
            
        } 
        else
        {
        	//转向预付
            location.href="../claim/LLClaimPrepayAuditMain.jsp?ClmNo="+tClmNo+"&CustomerNo="+tCustomerno+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityid+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom+"&RgtClass=1&RgtObj=1";      	
        }
    }
}

//end