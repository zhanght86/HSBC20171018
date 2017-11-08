//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//"查询"按钮
function LLClaimAuditGridQueryClick()
{
	LLClaimAuditGridQuery();
}
// 初始化表格LLClaimAuditGrid查询
function LLClaimAuditGridQuery()
{
    initLLClaimAuditGrid();
    var strSQL = "";
	/*strSQL = " select missionprop1, "
	       +" (select codename from ldcode where codetype='llclaimstate' and missionprop2=trim(code)), "
	       +" missionprop3,missionprop4, "
	       + " missionprop5,"
	       + " (case trim(missionprop5) when '0' then '男' when '1' then '女' else '不详' end) as SexNA,"
	       + " missionprop6,missionprop7,missionprop8,missionprop9,missionprop10,missionprop11,missionprop12,missionprop19,missionprop20,missionid,submissionid,activityid,missionprop18,missionprop2 from lwmission where 1=1 "
         + getWherePart( 'missionprop1','RptNo' )
		     + getWherePart( 'missionprop3','CustomerNo' )
         + getWherePart( 'missionprop4','CustomerName' )
                 + " and activityid = '0000005035'"
                 + " and processid = '0000000005'"
                 + " and missionprop12 = 'A'"  //来自报案
                 + " and (missionprop11 = '0' or missionprop11 is null)" //  未预付       
                 + " and DefaultOperator is null "   //必须需要该条件
                 + " and missionprop20 like '" + fm.ComCode.value + "%%'"  //本级
                 + " order by missionprop1";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPrepayMissInputSql");
	mySql.setSqlId("LLClaimPrepayMissSql1");
	mySql.addSubPara( fm.RptNo.value);
	mySql.addSubPara( fm.CustomerNo.value);
	mySql.addSubPara( fm.CustomerName.value);
	mySql.addSubPara( fm.ComCode.value);
//    alert(strSQL);       
    turnPage.queryModal(mySql.getString()
,LLClaimAuditGrid);
}

// 初始化表格LLClaimPrepayGrid查询预付处理队列，用于做“预付处理”
//function LLClaimPrepayGridQuery()
//{
//    //initLLClaimPrepayGrid();
//    var strSQL = "";
//	  /*strSQL = " select missionprop1, "
//	         + " (select codename from ldcode where codetype='llclaimstate' and missionprop2=trim(code)), "
//	         + " missionprop3,missionprop4,"
//	             + " missionprop5,"
//	             + " (case trim(missionprop5) when '0' then '男' when '1' then '女' else '不详' end) as SexNA,"
//	             + " missionprop6,missionid,submissionid,activityid,missionprop2,MissionProp13,"
//	             + " (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
//                 + " and activityid = '0000005025'"
//                 + " and processid = '0000000005'"
//                 + " and defaultoperator = '" + fm.Operator.value +"'"
//                 + " order by AcceptedDate,missionprop1";  */ 
//     mySql = new SqlClass();
//	mySql.setResourceName("claim.LLClaimPrepayMissInputSql");
//	mySql.setSqlId("LLClaimPrepayMissSql2");
//	mySql.addSubPara( fm.Operator.value);
//
//	  
//	//prompt("预付工作队列查询",strSQL);
//    turnPage2.queryModal(mySql.getString(),LLClaimPrepayGrid);
//    HighlightSelfByRow();
//}

//SelfLLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
//function HighlightSelfByRow(){
//    for(var i=0; i<LLClaimPrepayGrid.mulLineCount; i++){
//    	var accepteddate = LLClaimPrepayGrid.getRowColData(i,13); //受理日期
//    	if(accepteddate != null && accepteddate != "")
//    	{
//    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
//    	   {
//    		   LLClaimPrepayGrid.setRowClass(i,'warn'); 
//    	   }
//    	}
//    }  
//}

//LLClaimAuditGrid表格响应的函数名
function LLClaimAuditGridClick()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	fm.tRptNo.value = LLClaimAuditGrid.getRowColData(selno, 1);//"赔案号" 
	fm.tRptorState.value = LLClaimAuditGrid.getRowColData(selno, 20);//"报案状态代码"
	fm.tCustomerNo.value = LLClaimAuditGrid.getRowColData(selno, 3);//"出险人编码"
	fm.tCustomerName.value = LLClaimAuditGrid.getRowColData(selno, 4);//"出险人姓名 
	fm.tCustomerSex.value = LLClaimAuditGrid.getRowColData(selno, 5);//"出险人性别"
	fm.tAccDate.value = LLClaimAuditGrid.getRowColData(selno, 7);//"出险日期" 
	fm.tRgtClass.value = LLClaimAuditGrid.getRowColData(selno, 8);//"申请类型" 
	fm.tRgtObj.value = LLClaimAuditGrid.getRowColData(selno, 9);//"号码类型" 
	fm.tRgtObjNo.value = LLClaimAuditGrid.getRowColData(selno, 10);//"其他号码" 
	fm.tPopedom.value = LLClaimAuditGrid.getRowColData(selno, 11);//"核赔师权限" 
	fm.tPrepayFlag.value = LLClaimAuditGrid.getRowColData(selno, 12);//"预付标志" 
	fm.tComeWhere.value = LLClaimAuditGrid.getRowColData(selno, 13);//"来自" 
	fm.tAuditer.value = LLClaimAuditGrid.getRowColData(selno, 14);//"审核操作人" 
	fm.tMngCom.value = LLClaimAuditGrid.getRowColData(selno, 15);//"" 	
	fm.tComFlag.value = LLClaimAuditGrid.getRowColData(selno, 19);//"" 	权限跨级标志
	fm.tMissionID.value = LLClaimAuditGrid.getRowColData(selno, 16);
	fm.tSubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 17);
	fm.tActivityID.value = LLClaimAuditGrid.getRowColData(selno,18);	
}

//LLClaimPrepayGrid表格响应的函数名
//function LLClaimPrepayGridClick()
//{
//	HighlightSelfByRow();
//	var selno = LLClaimPrepayGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("请选择要作预付处理的赔案！");
//	      return;
//	}		
//	var tClmNO = LLClaimPrepayGrid.getRowColData(selno,1);//赔案号（立案号）
//	var tCustomerNo=LLClaimPrepayGrid.getRowColData(selno,3);//客户号
//	var tCustomerName=LLClaimPrepayGrid.getRowColData(selno,4);//客户姓名
//	var tCustomerSex=LLClaimPrepayGrid.getRowColData(selno,5);//性别
//	var tMissionID=LLClaimPrepayGrid.getRowColData(selno,8);//任务ID
//	var tSubMissionID=LLClaimPrepayGrid.getRowColData(selno,9);//子任务ID
//	var tActivityID=LLClaimPrepayGrid.getRowColData(selno,10);//当前活动ID	
//	var tAuditer=LLClaimPrepayGrid.getRowColData(selno,12);//审核人
//	//alert("tAuditer:"+tAuditer);
//	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&CustomerName="+tCustomerName+"&CustomerSex="+tCustomerSex+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Auditer="+tAuditer;
////    location.href="LLClaimPrepayInput.jsp?";
//    location.href=strUrl;
//  
//
//}

//"申请"按钮，从待审核队列中提起任务后，则将待审核节点消亡，产生预付节点，准备进行预付处理
function ApplyLLClaimAudit()
{
	var selno = LLClaimAuditGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请处理的报案！");
	      return;
	}
	fm.action = "./LLClaimPrepayMissSave.jsp";
	submitForm(); //提交
}

//公共提交方法
function submitForm()
{
    //提交数据
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

//提交后操作,返回
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
    //LLClaimAuditGridQuery();    // 初始化表格LLClaimAuditGrid查询
   // LLClaimPrepayGridQuery();//刷新查询预付处理队列，用于做“预付处理”
    jQuery("#privateSearch").click();//刷新工作队列 lzf 20130521
}

//modify by lzf 
function LLClaimPrepayGridClick()
{
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要作预付处理的赔案！");
	      return;
	}		
	var tClmNO = PrivateWorkPoolGrid.getRowColData(selno,1);//赔案号（立案号）
	var tCustomerNo=PrivateWorkPoolGrid.getRowColData(selno,3);//客户号
	var tCustomerName=PrivateWorkPoolGrid.getRowColData(selno,4);//客户姓名
	var tCustomerSex=PrivateWorkPoolGrid.getRowColData(selno,8);//性别
	var tMissionID=PrivateWorkPoolGrid.getRowColData(selno,19);//任务ID
	var tSubMissionID=PrivateWorkPoolGrid.getRowColData(selno,20);//子任务ID
	var tActivityID=PrivateWorkPoolGrid.getRowColData(selno,22);//当前活动ID	
	var tAuditer=PrivateWorkPoolGrid.getRowColData(selno,15);//审核人
	//alert("tAuditer:"+tAuditer);
	var strUrl="LLClaimPrepayInput.jsp?ClmNo="+tClmNO+"&CustomerNo="+tCustomerNo+"&CustomerName="+tCustomerName+"&CustomerSex="+tCustomerSex+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&Auditer="+tAuditer;
//    location.href="LLClaimPrepayInput.jsp?";
    location.href=strUrl;

}
//end by lzf