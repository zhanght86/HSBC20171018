//程序名称：EdorApprove.js
//程序功能：保全复核
//创建日期：2005-05-08 15:20:22
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();


/**
*  查询保全申请信息
*  描述: 查询保全申请信息
*/
function initQuery()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var MissionID       = document.all('MissionID').value
    var SubMissionID    = document.all('SubMissionID').value

    if (EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    if (MissionID == null || MissionID == "")
    {
        alert("任务号为空！");
        return;
    }
    if (SubMissionID == null || SubMissionID == "")
    {
        alert("子任务号为空！");
        return;
    }

//    var strSQL;

    //查询保全申请信息
//    strSQL =  " select OtherNo, (select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'edornotype' and code = OtherNoType), "
//    + " GetMoney,EdorAppName, "
//    + " (select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'edorapptype' and code = Apptype), "
//    + " (select concat(concat(trim(code),'-'),trim(codename)) from ldcode where codetype = 'station' and code = ManageCom),edorstate,othernotype, "
//    + " GetInterest,Apptype,ManageCom,Operator,(select username from lduser where usercode = LPEdorApp.Operator) "
//    + " from LPEdorApp "
//    + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";
    
    var sqlid1="EdorApproveSql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("bq.EdorApproveSql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(EdorAcceptNo);//指定传入参数
 	var strSQL = mySql1.getString();

    var brr = easyExecSql(strSQL, 1, 0,"","",1);
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoTypeName.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.GetMoney.value    = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.EdorAppName.value = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.ApptypeName.value     = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.ManageComName.value   = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.EdorState.value   = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.OtherNoType.value = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.GetInterest.value = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.Apptype.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.ManageCom.value   = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.SysOperator.value   = brr[0][11];
        brr[0][12]==null||brr[0][12]=='null'?'未知':fm.SysOperatorName.value   = brr[0][12];
    }
    else
    {
        alert("保全申请查询失败！");
        return;
    }

    //查询保全申请信息
//    strSQL =  " select missionprop11, missionprop12 from lwmission where activityid = '0000000007' and missionid = '" + MissionID + "' and submissionid = '" + SubMissionID + "'  ";
    
    var sqlid2="EdorApproveSql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("bq.EdorApproveSql");
 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
 	mySql2.addSubPara(MissionID);//指定传入参数
 	mySql2.addSubPara(SubMissionID);
 	var strSQL = mySql2.getString();
    
    var drr = easyExecSql(strSQL, 1, 0, "","",1);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.AppntName.value     = drr[0][0];
        drr[0][1]==null||drr[0][1]=='null'?'0':fm.PaytoDate.value = drr[0][1];
    }
    else
    {
        fm.AppntName.value = "";
        fm.PaytoDate.value = "";
    }
    showEdorMainList();
}

/**
*  查询保全项目列表
*  描述: 查询保全项目列表
*/
function showEdorItemListAuto()
{
    var tSel = 0;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var ContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 10);
    document.all('EdorNo').value = EdorNo;
    document.all('ContNo').value = ContNo;
    document.all('EdorMainState').value = EdorMainState;

//    var strSQL;
//    strSQL =  " select p.EdorNo, "
//    + " (select distinct concat(concat(edorcode,'-'),edorname) from lmedoritem m where m.appobj in ('I', 'B') and  trim(m.edorcode) = trim(edortype)), "
//    + " ContNo,  InsuredNo, "
//    + " PolNo, EdorAppDate, EdorValiDate, (case when GetMoney is not null then GetMoney else 0.00 end),(case when GetInterest is not null then GetInterest else 0.00 end), "
//    + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//    + " EdorState, MakeDate, MakeTime, EdorType, "
//    + " (case when (select (case a.PwdFlag when '1' then '是' else '否' end) "
//    +         "from LMEdorItem a "
//    +        "where 1 = 1 "
//    +          "and a.EdorCode = p.EdorType "
//    +          "and a.AppObj in ('I', 'B') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LDSysVar "
//    +                "where 1 = 1 "
//    +                  "and SysVar = 'EnableEdorContPwd' "
//    +                  "and SysVarValue = '1') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LCCont "
//    +                "where 1 = 1 "
//    +                  "and ContNo in "
//    +                      "(select ContNo "
//    +                         "from LPEdorItem "
//    +                        "where 1 = 1 "
//    +                          "and EdorNo = p.EdorNo "
//    +                          "and EdorType = p.EdorType "
//    +                      ") "
//    +                  "and Password is not null)) is not null then (select (case a.PwdFlag when '1' then '是' else '否' end) "
//    +         "from LMEdorItem a "
//    +        "where 1 = 1 "
//    +          "and a.EdorCode = p.EdorType "
//    +          "and a.AppObj in ('I', 'B') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LDSysVar "
//    +                "where 1 = 1 "
//    +                  "and SysVar = 'EnableEdorContPwd' "
//    +                  "and SysVarValue = '1') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LCCont "
//    +                "where 1 = 1 "
//    +                  "and ContNo in "
//    +                      "(select ContNo "
//    +                         "from LPEdorItem "
//    +                        "where 1 = 1 "
//    +                          "and EdorNo = p.EdorNo "
//    +                          "and EdorType = p.EdorType "
//    +                      ") "
//    +                  "and Password is not null)) else '否' end) "
//    + " from LPEdorItem p "
//    + " where p.EdorNo = '" + EdorNo + "'" ;
    
    var sqlid3="EdorApproveSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("bq.EdorApproveSql");
 	mySql3.setSqlId(sqlid3); //指定使用SQL的id
 	mySql3.addSubPara(EdorNo);//指定传入参数
 	var strSQL = mySql3.getString();
    
    var drr = easyExecSql(strSQL, 1, 0, "", "",1);
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
        turnPage2.pageDivName = "divTurnPageEdorItemGrid";
        turnPage2.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display="";
    }
}

/**
*  查询保全项目列表
*  描述: 查询保全项目列表
*/
function showEdorItemList()
{
    var tSel = EdorMainGrid.getSelNo() - 1;

    var EdorNo = EdorMainGrid.getRowColData(tSel, 1);
    var ContNo = EdorMainGrid.getRowColData(tSel, 2);
    var EdorMainState = EdorMainGrid.getRowColData(tSel, 10);
    document.all('EdorNo').value = EdorNo;
    document.all('ContNo').value = ContNo;
    document.all('EdorMainState').value = EdorMainState;

//    var strSQL;
//    strSQL =  " select EdorNo, "
//    + " (select distinct concat(concat(edorcode,'-'),edorname) from lmedoritem m where m.appobj in ('I', 'B') and trim(m.edorcode) = trim(edortype)), "
//    + " ContNo,  InsuredNo, "
//    + " PolNo, EdorAppDate, EdorValiDate, (case when GetMoney is not null then GetMoney else 0.00 end), (case when GetInterest is not null then GetInterest else 0.00 end), "
//    + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(EdorState)), "
//    + " EdorState, MakeDate, MakeTime, EdorType, "
//    + " (case when (select (case a.PwdFlag when '1' then '是' else '否' end) "
//    +         "from LMEdorItem a "
//    +        "where 1 = 1 "
//    +          "and a.EdorCode = p.EdorType "
//    +          "and a.AppObj in ('I', 'B') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LDSysVar "
//    +                "where 1 = 1 "
//    +                  "and SysVar = 'EnableEdorContPwd' "
//    +                  "and SysVarValue = '1') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LCCont "
//    +                "where 1 = 1 "
//    +                  "and ContNo in "
//    +                      "(select ContNo "
//    +                         "from LPEdorItem "
//    +                        "where 1 = 1 "
//    +                          "and EdorNo = p.EdorNo "
//    +                          "and EdorType = p.EdorType "
//    +                      ") "
//    +                  "and Password is not null)) is not null then (select (case a.PwdFlag when '1' then '是' else '否' end) "
//    +         "from LMEdorItem a "
//    +        "where 1 = 1 "
//    +          "and a.EdorCode = p.EdorType "
//    +          "and a.AppObj in ('I', 'B') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LDSysVar "
//    +                "where 1 = 1 "
//    +                  "and SysVar = 'EnableEdorContPwd' "
//    +                  "and SysVarValue = '1') "
//    +          "and exists "
//    +              "(select 'X' "
//    +                 "from LCCont "
//    +                "where 1 = 1 "
//    +                  "and ContNo in "
//    +                      "(select ContNo "
//    +                         "from LPEdorItem "
//    +                        "where 1 = 1 "
//    +                          "and EdorNo = p.EdorNo "
//    +                          "and EdorType = p.EdorType "
//    +                      ") "
//    +                  "and Password is not null)) else '否' end) "
//    + " from LPEdorItem p "
//    + " where p.EdorNo = '" + EdorNo + "'" ;
    
    var sqlid4="EdorApproveSql4";
 	var mySql4=new SqlClass();
 	mySql4.setResourceName("bq.EdorApproveSql");
 	mySql4.setSqlId(sqlid4); //指定使用SQL的id
 	mySql4.addSubPara(EdorNo);//指定传入参数
 	var strSQL = mySql4.getString();
    
    var drr = easyExecSql(strSQL, 1, 0,"","",1);
    if ( !drr )
    {
        alert("申请批单下没有保全项目！");
        return;
    }
    else
    {
        turnPage2.pageDivName = "divTurnPageEdorItemGrid";
        turnPage2.queryModal(strSQL, EdorItemGrid);
        divEdorItemInfo.style.display='';
    }
}

/**
*  查询保全批单列表
*  描述: 查询保全批单列表
*/
function showEdorMainList()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;

    //查询保全批改信息
    if(_DBT==_DBO){
//    	 strSQL =  " select a.EdorNo, a.ContNo, "
//    		    + " (select distinct AppntName from LCCont where ContNo= a.ContNo), "
//    		    + " (select paytodate from lcpol p where p.contno = a.contno and p.polno = p.mainpolno and rownum = 1), "
//    		    + " a.EdorAppDate, a.EdorValiDate, (case when a.GetMoney is not null then a.GetMoney else 0 end), (case when GetInterest is not null then GetInterest else 0 end), "
//    		    + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//    		    + " a.EdorState ,a.operator"
//    		    + " from LPEdorMain a , LCCont b "
//    		    + " where 1=1 and a.ContNo  = b.ContNo "
//    		    + " and a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
    	 
    	    var sqlid5="EdorApproveSql5";
    	 	var mySql5=new SqlClass();
    	 	mySql5.setResourceName("bq.EdorApproveSql");
    	 	mySql5.setSqlId(sqlid5); //指定使用SQL的id
    	 	mySql5.addSubPara(EdorAcceptNo);//指定传入参数
    	 	strSQL = mySql5.getString();
    	 
    }else if(_DBT==_DBM){
//    	 strSQL =  " select a.EdorNo, a.ContNo, "
//    		    + " (select distinct AppntName from LCCont where ContNo= a.ContNo), "
//    		    + " (select paytodate from lcpol p where p.contno = a.contno and p.polno = p.mainpolno limit 1), "
//    		    + " a.EdorAppDate, a.EdorValiDate, (case when a.GetMoney is not null then a.GetMoney else 0 end), (case when GetInterest is not null then GetInterest else 0 end), "
//    		    + " (select c.codename from ldcode c where c.codetype = 'edorstate' and trim(c.code)=trim(a.EdorState)), "
//    		    + " a.EdorState ,a.operator"
//    		    + " from LPEdorMain a , LCCont b "
//    		    + " where 1=1 and a.ContNo  = b.ContNo "
//    		    + " and a.EdorAcceptNo = '" + EdorAcceptNo + "' ";
    	 
    	var sqlid6="EdorApproveSql6";
 	 	var mySql6=new SqlClass();
 	 	mySql6.setResourceName("bq.EdorApproveSql");
 	 	mySql6.setSqlId(sqlid6); //指定使用SQL的id
 	 	mySql6.addSubPara(EdorAcceptNo);//指定传入参数
 	 	strSQL = mySql6.getString();
    	 
    }
   
    var crr = easyExecSql(strSQL, 1, 0,"","",1);
    if ( !crr )
    {
        alert("保全申请下没有批单！");
        return;
    }
    else
    {
        turnPage1.pageDivName = "divTurnPageEdorMainGrid";
        turnPage1.queryModal(strSQL, EdorMainGrid);
        document.all('operator').value=EdorMainGrid.getRowColData(0,11);
        //alert(document.all('operator').value);
        divEdorMainInfo.style.display='';
        showEdorItemListAuto();
    }
}

/**
*  取出保全项目信息
*  描述: 取出保全项目信息
*/
function getEdorItemInfo()
{
    var tSel = EdorItemGrid.getSelNo() - 1;

    fm.EdorNo.value         = EdorItemGrid.getRowColData(tSel, 1);
    fm.EdorType.value       = EdorItemGrid.getRowColData(tSel, 14);
    fm.ContNo.value         = EdorItemGrid.getRowColData(tSel, 3);
    fm.InsuredNo.value      = EdorItemGrid.getRowColData(tSel, 4);
    fm.PolNo.value          = EdorItemGrid.getRowColData(tSel, 5);
    fm.EdorItemAppDate.value       = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorAppDate.value           = EdorItemGrid.getRowColData(tSel, 6);
    fm.EdorValiDate.value          = EdorItemGrid.getRowColData(tSel, 7);
    fm.EdorItemState.value  = EdorItemGrid.getRowColData(tSel, 11);
    fm.MakeDate.value       = EdorItemGrid.getRowColData(tSel, 12);
    fm.MakeTime.value       = EdorItemGrid.getRowColData(tSel, 13);

    //alert(fm.EdorNo.value);
    //alert(fm.ContNo.value);
    //alert(fm.EdorType.value);
    //alert(fm.InsuredNo.value);
    //alert(fm.PolNo.value);
}

/**
*  保全复核提交
*  描述: 保全撤销提交
*/
function ApproveSubmit()
{
    if (!verifyInput2()) return;

    if (fm.EdorAcceptNo.value == "" )
    {
        alert("无法获取保全受理号码信息。保全复核失败！");
        return;
    }
    
//    var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.ContNo.value+"' and StandbyFlag3='3' and othernotype='02' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
    
    var sqlid7="EdorApproveSql7";
 	var mySql7=new SqlClass();
 	mySql7.setResourceName("bq.EdorApproveSql");
 	mySql7.setSqlId(sqlid7); //指定使用SQL的id
 	mySql7.addSubPara(fm.ContNo.value);//指定传入参数
 	mySql7.addSubPara(fm.EdorAcceptNo.value);
 	var tSQL = mySql7.getString();
    
    
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次复核操作有未回复的问题件,请等待回复后再下复核意见!");
  		return;
    }

    var tApproveFlag     = fm.ApproveFlag.value;
    var tApproveContent  = fm.ApproveContent.value;
    var sModifyReason    = fm.ModifyReason.value;
		var tErrorChkFlag				= fm.ErrorChkFlag.value;
		//alert(tErrorChkFlag);
    //<!-- XinYQ added on 2005-11-28 : 复核修改原因 : BGN -->
    if (tApproveFlag == "2" && (sModifyReason == null || sModifyReason.trim() == ""))
    {
        alert("请录入复核修改的原因！ ");
        return;
    }
    if (tApproveFlag == "2" && sModifyReason=="2"&& tApproveContent.trim() == "")
    {
        alert("请录入复核修改的原因！ ");
        return;
    }
     if (tApproveFlag == "3" && (tApproveContent == null || tApproveContent.trim() == ""))
    {
        alert("请录入保全拒绝的原因！ ");
        return;
    }
               

    if (!verifyInput2()) return;
    //<!-- XinYQ added on 2005-11-28 : 复核修改原因 : END -->

  //  if (tApproveContent == null || tApproveContent == "")
  //  {
  //    alert("请录入保全复核意见!");
  //    return ;
  //  }

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('ActionFlag').value = "APPROVESUBMIT";
    fm.action = "./EdorApproveSave.jsp";
    fm.target="fraSubmit";  //提交给制定的框架
    document.getElementById("fm").submit();
}

/**
*  保全复核取消
*  描述: 保全撤销取消
*/
function ApproveCancel()
{
    document.all('ApproveFlag').value = "";
    document.all('ApproveContent').value = "";
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent, OtherFlag)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            showEdorMainList();
            //checkPrintNotice();
            checkEdorPrint();
            top.opener.jQueryClickSelf();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
* showCodeList 的回调函数
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorapproveidea")
    {
        if (oCodeListField.value == "2")
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "";
                document.all("divApproveMofiyReasonInput").style.display = "";
                document.all("divPayNotice").style.display = "";
                document.all("divApproveErrorChk").style.display = "";  
            }
            catch (ex) {}
        }
        else
        {
            try
            {
                document.all("divApproveMofiyReasonTitle").style.display = "none";
                document.all("divApproveMofiyReasonInput").style.display = "none";
                document.all("divApproveErrorChk").style.display = "none";  
                document.all("divPayNotice").style.display = "none";

            }
            catch (ex) {}
        }
        if (oCodeListField.value == "3")
        {
            try
            {
								document.all("divApproveErrorChk").style.display = ""; 
                document.all("divPayNotice").style.display = "";
            }
            catch (ex) {}
        }
        else
        {
            try
            { 
                document.all("divPayNotice").style.display = "none";
            }
            catch (ex) {}
        }
    } //EdorApproveReason
}

function setErrorFlag(){
	if (fm.ErrorChk.checked)
	{
		
			document.all('ErrorChkFlag').value = "Y";
			//alert(document.all('InsuGetFlag').value);
	
	}else{
		
		document.all('ErrorChkFlag').value = "N";
	}
	//alert(document.all('ErrorChkFlag').value);
	
}

/*============================================================================*/

/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

/**
*  根据EdorAcceptNo查阅批单信息
*/
function EndorseDetail()
{
    fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
    fm.target="f1print";
    document.getElementById("fm").submit();
}

/**
* 扫描影像资料
*/
function ScanDetail()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
    OpenWindowNew(tUrl,"保全扫描影像","left");
}

/**
* 核保状态查询
*/
function  UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorErrorUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1");
}

/**
 * 直接进入保全项目明细
 * XinYQ added on 2006-03-14
 */
function EdorDetailQuery()
{
    var nSelNo, sEdorType, sEdorState;
    try
    {
        nSelNo = EdorItemGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查看的申请项目！ ");
        return;
    }
    else
    {
        var sEdorType, sEdorState;
        try
        {
            sEdorType = document.getElementsByName("EdorType")[0].value;
            sEdorState = document.getElementsByName("EdorState")[0].value;
        }
        catch (ex) {}
        if (sEdorType == null || sEdorType.trim() == "")
        {
            alert("警告：无法获取保全批改项目类型信息。查询保全明细失败！ ");
            return;
        }
        if (sEdorState != null && trim(sEdorState) == "0")
        {
            OpenWindowNew("../bqs/PEdorType" + sEdorType.trim() + ".jsp", "保全项目明细查询", "left");    //bqs
        }
        else
        {
            OpenWindowNew("../bq/PEdorType" + sEdorType.trim() + ".jsp", "保全项目明细查询", "left");    //bq
        }
    }
}

function GetNotice()
{
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ32' and otherno = '"+EdorAcceptNo+"'";
    
    var sqlid8="EdorApproveSql8";
 	var mySql8=new SqlClass();
 	mySql8.setResourceName("bq.EdorApproveSql");
 	mySql8.setSqlId(sqlid8); //指定使用SQL的id
 	mySql8.addSubPara(EdorAcceptNo);//指定传入参数
 	var strSQL = mySql8.getString();
    
    var sResult;
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询付费通知书信息失败！");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}

function InvaliNotice()
{
    var tContNo    = document.all('ContNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ33' and otherno = '"+tContNo+"' and standbyflag1='"+EdorAcceptNo+"'";
    
    var sqlid9="EdorApproveSql9";
 	var mySql9=new SqlClass();
 	mySql9.setResourceName("bq.EdorApproveSql");
 	mySql9.setSqlId(sqlid9); //指定使用SQL的id
 	mySql9.addSubPara(tContNo);//指定传入参数
 	mySql9.addSubPara(EdorAcceptNo);
 	var strSQL = mySql9.getString();
    
    var sResult;
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询保全拒绝通知书信息失败,请先保存复核结论");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}

function InvaliPassNotice()
{
    var tContNo    = document.all('ContNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ37' and otherno = '"+tContNo+"' and standbyflag1='"+EdorAcceptNo+"'";
    
    var sqlid10="EdorApproveSql10";
 	var mySql10=new SqlClass();
 	mySql10.setResourceName("bq.EdorApproveSql");
 	mySql10.setSqlId(sqlid10); //指定使用SQL的id
 	mySql10.addSubPara(tContNo);//指定传入参数
 	mySql10.addSubPara(EdorAcceptNo);
 	var strSQL = mySql10.getString();
    
    var sResult;
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询保全复核通知书信息失败,请先保存复核结论");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    document.getElementById("fm").submit();
}

/**
*  保全操作轨迹查询
*  描述: 保全操作轨迹查询
*/
function MissionQuery()
{
    var pMissionID=fm.MissionID.value;
    window.open("EdorMissionFrame.jsp?MissionID="+pMissionID,"window3");
}

/**
 * 判断是否显示补费通知书或付费通知书
 * XinYQ rewrote on 2007-05-18
 */
function checkPrintNotice()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select (case when SumDuePayMoney is not null then SumDuePayMoney else 0 end) "
//             +   "from LJSPay "
//             +  "where 1 = 1 "
//             +     getWherePart("OtherNo", "EdorAcceptNo")
//             +    "and OtherNoType = '10'";
    
	var  EdorAcceptNo1 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    
    var sqlid11="EdorApproveSql11";
 	var mySql11=new SqlClass();
 	mySql11.setResourceName("bq.EdorApproveSql");
 	mySql11.setSqlId(sqlid11); //指定使用SQL的id
 	mySql11.addSubPara(EdorAcceptNo1);//指定传入参数
 	QuerySQL = mySql11.getString();
    
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：查询保全补退费信息出现异常！ ");
        return;
    }
    if (arrResult != null && arrResult[0][0] != "")
    {
        try
        {
            var sGetMoney = arrResult[0][0];
            var fGetMoney = parseFloat(sGetMoney);
            if (fGetMoney > 0)
            {
                document.all("divPayNotice").style.display = "block";
                //document.all("divGetNotice").style.display = "none";
            }
            else if (fGetMoney < 0)
            {
                document.all("divPayNotice").style.display = "none";
                //document.all("divGetNotice").style.display = "block";
            }
            else
            {
                document.all("divPayNotice").style.display = "none";
                //document.all("divGetNotice").style.display = "none";
            }
        }
        catch (ex) {}
    } //arrResult != null
}

/**
 * 检查是否需要打印批单
 * XinYQ added on 2005-11-09
 */
function checkEdorPrint()
{
    var QuerySQL, arrResult;
//    QuerySQL = "select EdorNo "
//             +   "from LPEdorPrint "
//             +  "where 1 = 1 "
//             +    "and EdorNo in "
//             +        "(select EdorNo "
//             +           "from LPEdorItem "
//             +          "where 1 = 1 "
//             +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//             +        ")"
//             +    "and exists "
//             +        "(select 'X' "
//             +           "from LPEdorApp "
//             +          "where 1 = 1 "
//             +             getWherePart("EdorAcceptNo", "EdorAcceptNo")
//             +            "and ApproveFlag = '1' "
//             +            "and EdorState = '0')";
    
    var  EdorAcceptNo2 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    var  EdorAcceptNo3 = window.document.getElementsByName(trim("EdorAcceptNo"))[0].value;
    
    var sqlid12="EdorApproveSql12";
 	var mySql12=new SqlClass();
 	mySql12.setResourceName("bq.EdorApproveSql");
 	mySql12.setSqlId(sqlid12); //指定使用SQL的id
 	mySql12.addSubPara(EdorAcceptNo2);//指定传入参数
 	mySql12.addSubPara(EdorAcceptNo3);
 	QuerySQL = mySql12.getString();
    
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查是否需要打印批单信息出现异常！ ");
        return;
    }
    if (arrResult != null)
    {
        if (confirm("是否打印本次保全变更的批单？ "))
        {
            document.forms(0).action = "../f1print/AppEndorsementUptF1PJ1.jsp";
            document.forms(0).target="f1print";
            document.forms(0).submit();
        }
    }
}

function queryAskMsg()
{
		// 初始化表格
	fm.AskInfo.value="";        
  fm.ReplyInfo.value="";
	initAgentGrid();
	
	// 书写SQL语句
	var strSQL = "";

	
//	strSQL = "select PrtSeq,OtherNo,StandbyFlag1,StandbyFlag2,StandbyFlag4,(case StandbyFlag3 when '3' then '保全复核问题件' else '其它' end),"
//		+"concat(concat(concat(concat(to_char(MakeDate,'yyyy-mm-dd'),' '),maketime),StandbyFlag7),' '),DoneTime,StandbyFlag5,StandbyFlag6,"
//		+"(case stateflag when 'A' then '回复中' when 'R' then '已回复' else '' end) from LOPRTManager where 1=1 "
//		+ " and othernotype='02' and StandbyFlag1 = '"+fm.EdorAcceptNo.value+"'"
//		+" and StandbyFlag3 = '3' and Code = 'BQ38' order by PrtSeq";
		
	var sqlid13="EdorApproveSql13";
 	var mySql13=new SqlClass();
 	mySql13.setResourceName("bq.EdorApproveSql");
 	mySql13.setSqlId(sqlid13); //指定使用SQL的id
 	mySql13.addSubPara(fm.EdorAcceptNo.value);//指定传入参数
 	strSQL = mySql13.getString();
	
	
	turnPage3.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage3.strQueryResult) {
    alert("无记录!");
    divAgentGrid.style.display="none";
    return;
    }
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage3.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage3.arrDataCacheSet = arrDataSet;
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage3.pageDisplayGrid = AgentGrid;    
          
  //保存SQL语句
  turnPage3.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage3.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage3.getData(turnPage3.arrDataCacheSet, turnPage3.pageIndex, MAXSCREENLINES);
  //调用MULTILINE对象显示查询结果
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage3.pageDisplayGrid);
   divAgentGrid.style.display="";
  
}

function ShowAskInfo()
{
    var i = AgentGrid.getSelNo();

    if (i != '0')
    {
        i = i - 1;
        
        var tAskInfo = AgentGrid.getRowColData(i,9); 
        fm.AskInfo.value=tAskInfo;
        var tReplyInfo = AgentGrid.getRowColData(i,10);
         fm.ReplyInfo.value=tReplyInfo;
    }	
}

function sendAskMsg()
{

//		var tSQL = "select distinct 1 from LOPRTManager where otherno = '"+fm.ContNo.value+"' and StandbyFlag3='3' and code='BQ38' and StateFlag = 'A' and StandByFlag1 = '"+fm.EdorAcceptNo.value+"'";
		
		var sqlid14="EdorApproveSql14";
	 	var mySql14=new SqlClass();
	 	mySql14.setResourceName("bq.EdorApproveSql");
	 	mySql14.setSqlId(sqlid14); //指定使用SQL的id
	 	mySql14.addSubPara(fm.ContNo.value);//指定传入参数
	 	mySql14.addSubPara(fm.EdorAcceptNo.value);
	 	var tSQL = mySql14.getString();
		
		var arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]=="1")
  	{
  		alert("本次复核操作已经有下发未回复的问题件,不能再次下发!");
  		return;
    }
    
//    tSQL = "select edorstate from lpedorapp where edoracceptno = '"+fm.EdorAcceptNo.value+"'";
    
    var sqlid15="EdorApproveSql15";
 	var mySql15=new SqlClass();
 	mySql15.setResourceName("bq.EdorApproveSql");
 	mySql15.setSqlId(sqlid15); //指定使用SQL的id
 	mySql15.addSubPara(fm.EdorAcceptNo.value);//指定传入参数
 	tSQL = mySql15.getString();
    
    arrResult = easyExecSql(tSQL, 1, 0, 1);
		if(arrResult!=null&&arrResult[0][0]!="2")
  	{
  		alert("本次保全已经不在复核岗位置，不能下发问题件！");
  		return;
    }
    var tMyReply = fm.AskContent.value;
		if(tMyReply==null ||trim(tMyReply) =="")
		{
			alert("请录入问题件内容!");
			return;
		}
	  var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.forms[0].action = "EdorApproveNoticeSave.jsp?AskOperate=INSERT";
    document.forms[0].target = "fraSubmit";
    document.forms[0].submit();
}
